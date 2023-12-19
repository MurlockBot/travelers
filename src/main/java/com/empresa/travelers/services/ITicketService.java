package com.empresa.travelers.services;

import com.empresa.travelers.dto.ticket.TicketRequest;
import com.empresa.travelers.dto.ticket.TicketResponse;

import java.math.BigDecimal;
import java.util.UUID;

public interface ITicketService extends CrudService<TicketRequest, TicketResponse, UUID>{

    BigDecimal findPriceTicket(Long idFly);
}
