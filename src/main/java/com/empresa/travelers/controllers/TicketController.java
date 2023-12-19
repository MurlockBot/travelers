package com.empresa.travelers.controllers;

import com.empresa.travelers.dto.TicketRequest;
import com.empresa.travelers.services.implementations.TicketServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "ticket")
@Tag(name = "Ticket")
public class TicketController {

    private final TicketServiceImpl ticketService;

    public TicketController(TicketServiceImpl ticketService) {
        this.ticketService = ticketService;
    }


    @Operation(summary = "Obtiene el ticket proporcionado por ID.")
    @GetMapping(path = "{id}")
    public ResponseEntity<?> getTicket(@PathVariable UUID id) {
        return ResponseEntity.ok(ticketService.read(id));
    }

    @Operation(summary = "Busca los tickets por el precio proporcionado o menor.")
    @GetMapping("price")
    public ResponseEntity<Map<String, BigDecimal>> getTicketFinalPrice(@RequestParam Long idFly) {
        return ResponseEntity.ok(Collections.singletonMap("price", ticketService.findPriceTicket(idFly)));
    }

    @Operation(summary = "Agrega un ticket con el request proporcionado.")
    @PostMapping
    public ResponseEntity<?> postTicket(@RequestBody TicketRequest request) {
        return ResponseEntity.ok(ticketService.create(request));
    }

    @Operation(summary = "Modifica un ticket con su ID correspondiente y su estructura.")
    @PutMapping("{id}")
    public ResponseEntity<?> putTicket(@PathVariable UUID id, @RequestBody TicketRequest request) {
        return ResponseEntity.ok(ticketService.update(request, id));
    }

    @Operation(summary = "Elimina un ticket correspondiente mediante su ID.")
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable UUID id) {
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
