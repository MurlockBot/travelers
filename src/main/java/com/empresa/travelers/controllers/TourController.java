package com.empresa.travelers.controllers;

import com.empresa.travelers.dto.TourRequest;
import com.empresa.travelers.services.implementations.TourServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tour")
@Deprecated
@Tag(name = "Tour")
public class TourController {

    private final TourServiceImpl tourService;

    public TourController(TourServiceImpl tourService) {
        this.tourService = tourService;
    }

    @PostMapping
    public ResponseEntity<?> addTour(@RequestBody TourRequest tourRequest) {
        return ResponseEntity.ok(tourService.create(tourRequest));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getTour(@PathVariable Long id) {
        return ResponseEntity.ok(tourService.read(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> removeTour(@PathVariable Long id) {
        tourService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
