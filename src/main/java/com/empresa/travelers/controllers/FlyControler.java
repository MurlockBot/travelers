package com.empresa.travelers.controllers;

import com.empresa.travelers.enums.SortType;
import com.empresa.travelers.services.implementations.FlyServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;

@RestController
@RequestMapping("fly")
@Tag(name = "Fly")
public class FlyControler {
    private final FlyServiceImpl flyService;


    public FlyControler(FlyServiceImpl flyService) {
        this.flyService = flyService;
    }

    @Operation(summary = "Obtiene la lista de vuelos ordenados o no en diferentes páginas.")
    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestHeader(required = false) SortType sortType
    ) {
        if (Objects.isNull(sortType)) {
            sortType = SortType.NONE;
        }

        var response = flyService.readAll(page, size, sortType);

        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtiene los vuelos con precios menores al proporcionado.")
    @GetMapping("/price/less")
    public ResponseEntity<?> getLessPrice(@RequestParam(required = true) BigDecimal price) {
        var response = flyService.readLessPrice(price);

        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtiene los vuelos con precios entre dos valores.")
    @GetMapping("/price/between")
    public ResponseEntity<?> getLessBetween(
            @RequestParam(required = true) BigDecimal minPrice,
            @RequestParam(required = true) BigDecimal maxPrice) {
        var response = flyService.readBetweenPrice(minPrice, maxPrice);

        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtiene los vuelos que cumplan con el Origin y destino proporcionados si existen.")
    @GetMapping("/trajectory")
    public ResponseEntity<?> getLessBetween(
            @RequestParam(required = true) String origin,
            @RequestParam(required = true) String destiny) {
        var response = flyService.readByOriginDestiny(origin, destiny);

        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }
}
