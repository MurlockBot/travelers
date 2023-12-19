package com.empresa.travelers.utils;

import com.empresa.travelers.entities.*;
import com.empresa.travelers.repositories.ReservationRepository;
import com.empresa.travelers.repositories.TicketRepository;
import com.empresa.travelers.services.implementations.TicketServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Transactional
@Component
public class TourHelper {

    private final TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;

    public TourHelper(TicketRepository ticketRepository, ReservationRepository reservationRepository) {
        this.ticketRepository = ticketRepository;
        this.reservationRepository = reservationRepository;
    }

    public Set<TicketEntity> createTickets(Set<FlyEntity> flights, CustomerEntity customer) {
        var response = new HashSet<TicketEntity>(flights.size());

        flights.forEach(fly -> {
            var ticketPersist = TicketEntity.builder()
                    .id(UUID.randomUUID())
                    .fly(fly)
                    .customer(customer)
                    .price(fly.getPrice().multiply(TicketServiceImpl.pricePercentage).add(fly.getPrice()))
                    .purchaseDate(LocalDateTime.now())
                    .departureDate(TravelersUtility.getRandomSoon())
                    .arrivalDate(TravelersUtility.getRandomLater())
                    .build();
            response.add(ticketRepository.save(ticketPersist));
        });
        return response;
    }

    public Set<ReservationEntity> createReservations(HashMap<HotelEntity, Integer> hotels, CustomerEntity customer) {
        var response = new HashSet<ReservationEntity>(hotels.size());

        hotels.forEach((hotel, totalDays) -> {

            var dateStart = TravelersUtility.getRandomDaySoon();
            var dateEnd = dateStart.plusDays(totalDays);
            var totalPrice = hotel.getPrice().multiply(BigDecimal.valueOf(totalDays));

            var reservationPersist = ReservationEntity.builder()
                    .id(UUID.randomUUID())
                    .dateTimeReservation(LocalDateTime.now())
                    .dateStart(dateStart)
                    .dateEnd(dateEnd)
                    .totalDays(totalDays)
                    .price(totalPrice)
                    .hotel(hotel)
                    .customer(customer)
                    .build();

            response.add(reservationRepository.save(reservationPersist));
        });
        return response;
    }
}
