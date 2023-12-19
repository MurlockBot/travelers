package com.empresa.travelers.controllers;

import com.empresa.travelers.enums.SortType;
import com.empresa.travelers.services.implementations.HotelServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;

@RestController
@RequestMapping("hotel")
@Tag(name = "Hotel")
public class HotelController {
    private final HotelServiceImpl hotelService;

    public HotelController(HotelServiceImpl hotelService) {
        this.hotelService = hotelService;
    }

    @Operation(summary = "Obtiene la lista de hoteles ordenados o no por medios de páginas.")
    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestHeader(required = false) SortType sortType
    ) {
        if (Objects.isNull(sortType)) {
            sortType = SortType.NONE;
        }
        var response = hotelService.readAll(page, size, sortType);

        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtiene la lista de hoteles con precios menor al proporcionado")
    @GetMapping("/price/less")
    public ResponseEntity<?> getLessPrice(@RequestParam(required = true) BigDecimal price) {
        var response = hotelService.readLessPrice(price);

        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtiene la lista de hoteles que su precio se encuentre entre dos valores")
    @GetMapping("/price/between")
    public ResponseEntity<?> getLessBetween(
            @RequestParam(required = true) BigDecimal minPrice,
            @RequestParam(required = true) BigDecimal maxPrice) {
        var response = hotelService.readBetweenPrice(minPrice, maxPrice);

        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtiene la lista de hoteles que tengan la puntuación proporcionada")
    @GetMapping("/rating")
    public ResponseEntity<?> getLessBetween(
            @RequestParam(required = true) Integer rating) {
        var response = hotelService.readGreaterThan(rating);

        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

}
