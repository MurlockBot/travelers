package com.empresa.travelers.controllers;


import com.empresa.travelers.dto.ReservationRequest;
import com.empresa.travelers.services.implementations.ReservationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("reservation")
@Tag(name = "Reservation")
public class ReservationController {
    private final ReservationServiceImpl reservationService;

    public ReservationController(ReservationServiceImpl reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(summary = "Obtiene la reservación por número de ID si existe.")
    @GetMapping("{id}")
    public ResponseEntity<?> getReservation(@PathVariable UUID id) {
        return ResponseEntity.ok(reservationService.read(id));
    }

    @Operation(summary = "Crea una reservación por ID")
    @PostMapping
    public ResponseEntity<?> postReservation(@RequestBody ReservationRequest request) {
        return ResponseEntity.ok(reservationService.create(request));
    }

    @Operation(summary = "Elimina una reservación por ID")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable UUID id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Modifica una reservación por ID obteniendo también campos a modificar.")
    @PostMapping("{id}")
    public ResponseEntity<?> updateReservation(@RequestBody ReservationRequest request, @PathVariable UUID id) {
        return ResponseEntity.ok(reservationService.update(request, id));
    }
}
