package com.empresa.travelers.services.implementations;

import com.empresa.travelers.dto.TourRequest;
import com.empresa.travelers.dto.TourResponse;
import com.empresa.travelers.entities.*;
import com.empresa.travelers.repositories.CustomerRepository;
import com.empresa.travelers.repositories.FlyRepository;
import com.empresa.travelers.repositories.HotelRepository;
import com.empresa.travelers.repositories.TourRepository;
import com.empresa.travelers.services.ITourService;
import com.empresa.travelers.utils.TourHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class TourServiceImpl implements ITourService {

    private final TourRepository tourRepository;
    private final FlyRepository flyRepository;
    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;

    private final TourHelper tourHelper;


    public TourServiceImpl(TourRepository tourRepository, FlyRepository flyRepository, HotelRepository hotelRepository, CustomerRepository customerRepository, TourHelper tourHelper) {
        this.tourRepository = tourRepository;
        this.flyRepository = flyRepository;
        this.hotelRepository = hotelRepository;
        this.customerRepository = customerRepository;
        this.tourHelper = tourHelper;
    }

    @Override
    public TourResponse create(TourRequest tourRequest) {
        var customer = customerRepository.findById(tourRequest.getCustomerId()).orElseThrow();
        var flights = new HashSet<FlyEntity>();
        var hotels = new HashMap<HotelEntity, Integer>();

        tourRequest.getFlights().forEach(fly -> {
            flights.add(flyRepository.findById(fly.getId()).orElseThrow());
        });

        tourRequest.getHotels().forEach(hotel -> {
            hotels.put(hotelRepository.findById(hotel.getId()).orElseThrow(), hotel.getTotalDays());
        });

        var tourToSave = TourEntity.builder()
                .tickets(this.tourHelper.createTickets(flights, customer))
                .reservations(this.tourHelper.createReservations(hotels, customer))
                .customer(customer)
                .build();

        var tourSaved = tourRepository.save(tourToSave);
        return TourResponse.builder()
                .reservationsIds(tourSaved.getReservations().stream().map(ReservationEntity::getId).collect(Collectors.toSet()))
                .ticketsId(tourSaved.getTickets().stream().map(TicketEntity::getId).collect(Collectors.toSet()))
                .id(tourSaved.getId())
                .build();
    }

    @Override
    public TourResponse read(Long aLong) {
        var tourFromDb = tourRepository.findById(aLong).orElseThrow();
        return TourResponse.builder()
                .reservationsIds(tourFromDb.getReservations().stream().map(ReservationEntity::getId).collect(Collectors.toSet()))
                .ticketsId(tourFromDb.getTickets().stream().map(TicketEntity::getId).collect(Collectors.toSet()))
                .id(tourFromDb.getId())
                .build();
    }

    @Override
    public void delete(Long aLong) {
        var tourToDelete = tourRepository.findById(aLong).orElseThrow();
        tourRepository.delete(tourToDelete);
    }

    @Override
    public void removeTicket(UUID ticketId, Long tourId) {

    }

    @Override
    public UUID addTicket(Long flyId, Long tourId) {
        return null;
    }

    @Override
    public void removeReservation(UUID reservationId, Long tourId) {

    }

    @Override
    public UUID addReservation(Long reservationId, Long tourId) {
        return null;
    }
}
