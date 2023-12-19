package com.empresa.travelers.services.implementations;

import com.empresa.travelers.dto.FlyResponse;
import com.empresa.travelers.dto.TicketRequest;
import com.empresa.travelers.dto.TicketResponse;
import com.empresa.travelers.entities.TicketEntity;
import com.empresa.travelers.repositories.CustomerRepository;
import com.empresa.travelers.repositories.FlyRepository;
import com.empresa.travelers.repositories.TicketRepository;
import com.empresa.travelers.services.ITicketService;
import com.empresa.travelers.utils.TravelersUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Transactional
@Service
@Slf4j
public class TicketServiceImpl implements ITicketService {

    public static final BigDecimal pricePercentage = BigDecimal.valueOf(0.25);
    private final FlyRepository flyRepository;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;

    public TicketServiceImpl(FlyRepository flyRepository, CustomerRepository customerRepository, TicketRepository ticketRepository) {
        this.flyRepository = flyRepository;
        this.customerRepository = customerRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public TicketResponse create(TicketRequest ticketRequest) {

        var fly = flyRepository.findById(ticketRequest.getIdFly()).orElseThrow();
        var customer = customerRepository.findById(ticketRequest.getIdClient()).orElseThrow();

        var ticketPersist = TicketEntity.builder()
                .id(UUID.randomUUID())
                .fly(fly)
                .customer(customer)
                .price(fly.getPrice().multiply(pricePercentage).add(fly.getPrice()))
                .purchaseDate(LocalDateTime.now())
                .departureDate(TravelersUtility.getRandomSoon())
                .arrivalDate(TravelersUtility.getRandomLater())
                .build();

        var ticketPersisted = ticketRepository.save(ticketPersist);

        log.info(String.valueOf(ticketPersisted));

        return entityToResponse(ticketPersisted);
    }

    @Override
    public TicketResponse read(UUID uuid) {

        var ticketFromDB = ticketRepository.findById(uuid).orElseThrow();

        return entityToResponse(ticketFromDB);
    }

    @Override
    public TicketResponse update(TicketRequest ticketRequest, UUID uuid) {

        var ticketToUpdate = ticketRepository.findById(uuid).orElseThrow();
        var fly = flyRepository.findById(ticketRequest.getIdFly()).orElseThrow();

        ticketToUpdate.setFly(fly);
        ticketToUpdate.setPrice(fly.getPrice().multiply(pricePercentage).add(fly.getPrice()));
        ticketToUpdate.setArrivalDate(TravelersUtility.getRandomSoon());
        ticketToUpdate.setArrivalDate(TravelersUtility.getRandomLater());

        var ticketUpdated = ticketRepository.save(ticketToUpdate);
        return entityToResponse(ticketUpdated);
    }

    @Override
    public BigDecimal findPriceTicket(Long idFly) {
        var fly = flyRepository.findById(idFly).orElseThrow();

        return fly.getPrice().multiply(pricePercentage).add(fly.getPrice());
    }

    @Override
    public void delete(UUID uuid) {
        var ticketToDelete = ticketRepository.findById(uuid).orElseThrow();

        ticketRepository.delete(ticketToDelete);
    }

    private TicketResponse entityToResponse(TicketEntity entity) {
        var response = new TicketResponse();
        BeanUtils.copyProperties(entity, response);
        var flyResponse = new FlyResponse();
        BeanUtils.copyProperties(entity.getFly(), flyResponse);
        response.setFly(flyResponse);
        return response;
    }


}
