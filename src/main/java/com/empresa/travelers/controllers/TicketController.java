package com.empresa.travelers.controllers;

import com.empresa.travelers.dto.TicketRequest;
import com.empresa.travelers.services.implementations.TicketServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "ticket")
public class TicketController {

    private final TicketServiceImpl ticketService;

    public TicketController(TicketServiceImpl ticketService) {
        this.ticketService = ticketService;
    }


    @GetMapping(path = "{id}")
    public ResponseEntity<?> getTicket(@PathVariable UUID id){
        return ResponseEntity.ok(ticketService.read(id));
    }

    @GetMapping("price")
    public ResponseEntity<Map<String, BigDecimal>> getTicketFinalPrice(@RequestParam Long idFly){
        return ResponseEntity.ok(Collections.singletonMap("price", ticketService.findPriceTicket(idFly)));
    }

    @PostMapping
    public ResponseEntity<?> postTicket(@RequestBody TicketRequest request){
        return ResponseEntity.ok(ticketService.create(request));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> putTicket(@PathVariable UUID id, @RequestBody TicketRequest request){
        return ResponseEntity.ok(ticketService.update(request, id));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable UUID id){
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
