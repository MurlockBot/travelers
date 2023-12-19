package com.empresa.travelers.services;

import com.empresa.travelers.dto.reservation.ReservationRequest;
import com.empresa.travelers.dto.reservation.ReservationResponse;

import java.util.UUID;

public interface IReservationService extends CrudService<ReservationRequest, ReservationResponse, UUID> {
}
