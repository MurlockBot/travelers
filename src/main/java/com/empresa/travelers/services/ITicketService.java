package com.empresa.travelers.services;

import com.empresa.travelers.dto.TicketRequest;
import com.empresa.travelers.dto.TicketResponse;
import com.empresa.travelers.services.abstractservices.CrudService;

import java.math.BigDecimal;
import java.util.UUID;

public interface ITicketService extends CrudService<TicketRequest, TicketResponse, UUID> {

    BigDecimal findPriceTicket(Long idFly);
}
