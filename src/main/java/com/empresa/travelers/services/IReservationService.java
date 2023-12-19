package com.empresa.travelers.services;

import com.empresa.travelers.dto.ReservationRequest;
import com.empresa.travelers.dto.ReservationResponse;

import java.util.UUID;

public interface IReservationService extends CrudService<ReservationRequest, ReservationResponse, UUID> {
}
