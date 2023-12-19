package com.empresa.travelers.services;

import com.empresa.travelers.dto.TourRequest;
import com.empresa.travelers.dto.TourResponse;
import com.empresa.travelers.services.abstractservices.SimpleCrudServices;

import java.util.UUID;

public interface ITourService extends SimpleCrudServices<TourRequest, TourResponse, Long> {

    void removeTicket(UUID ticketId, Long tourId);

    UUID addTicket(Long flyId, Long tourId);

    void removeReservation(UUID reservationId, Long tourId);

    UUID addReservation(Long reservationId, Long tourId);

}
