package com.empresa.travelers.controllers;


import com.empresa.travelers.dto.ReservationRequest;
import com.empresa.travelers.services.implementations.ReservationServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("reservation")
public class ReservationController {
    private final ReservationServiceImpl reservationService;

    public ReservationController(ReservationServiceImpl reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getReservation(@PathVariable UUID id){
        return ResponseEntity.ok(reservationService.read(id));
    }

    @PostMapping
    public ResponseEntity<?> postReservation(@RequestBody ReservationRequest request){
        return ResponseEntity.ok(reservationService.create(request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable UUID id){
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{id}")
    public ResponseEntity<?> updateReservation(@RequestBody ReservationRequest request, @PathVariable UUID id){
        return ResponseEntity.ok(reservationService.update(request, id));
    }
}
