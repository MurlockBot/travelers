package com.empresa.travelers.services.implementations;

import com.empresa.travelers.dto.HotelResponse;
import com.empresa.travelers.dto.ReservationRequest;
import com.empresa.travelers.dto.ReservationResponse;
import com.empresa.travelers.entities.ReservationEntity;
import com.empresa.travelers.repositories.CustomerRepository;
import com.empresa.travelers.repositories.HotelRepository;
import com.empresa.travelers.repositories.ReservationRepository;
import com.empresa.travelers.services.IReservationService;
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
public class ReservationServiceImpl implements IReservationService {

    private final CustomerRepository customerRepository;

    private final ReservationRepository reservationRepository;

    private final HotelRepository hotelRepository;


    public ReservationServiceImpl(CustomerRepository customerRepository, ReservationRepository reservationRepository, HotelRepository hotelRepository) {
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    public ReservationResponse create(ReservationRequest reservationRequest) {
        var customerDB = customerRepository.findById(reservationRequest.getIdClient()).orElseThrow( );
        var hotelDB = hotelRepository.findById(reservationRequest.getIdHotel()).orElseThrow();

        var dateStart = TravelersUtility.getRandomDaySoon();
        var dateEnd = dateStart.plusDays(reservationRequest.getTotalDays());
        var totalPrice = hotelDB.getPrice().multiply(BigDecimal.valueOf(reservationRequest.getTotalDays()));

        var reservationPersist = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(dateStart)
                .dateEnd(dateEnd)
                .totalDays(reservationRequest.getTotalDays())
                .price(totalPrice)
                .hotel(hotelDB)
                .customer(customerDB)
                .build();

        var reservationPersisted = reservationRepository.save(reservationPersist);
        return entityToResponse(reservationPersisted);
    }

    @Override
    public ReservationResponse read(UUID uuid) {
        var reservationDB = reservationRepository.findById(uuid).orElseThrow();
        return entityToResponse(reservationDB);
    }

    @Override
    public ReservationResponse update(ReservationRequest reservationRequest, UUID uuid) {

        var reservationBD = reservationRepository.findById(uuid).orElseThrow();
        var hotelBD = hotelRepository.findById(reservationRequest.getIdHotel()).orElseThrow();
        var customerBD = customerRepository.findById(reservationRequest.getIdClient()).orElseThrow();

        var newPrice = hotelBD.getPrice().add(BigDecimal.valueOf(reservationRequest.getTotalDays()));

        reservationBD.setHotel(hotelBD);
        reservationBD.setCustomer(customerBD);
        reservationBD.setTotalDays(reservationRequest.getTotalDays());
        reservationBD.setPrice(newPrice);

        reservationRepository.save(reservationBD);
        return entityToResponse(reservationBD);
    }

    @Override
    public void delete(UUID uuid) {
        var entityToDelete = reservationRepository.findById(uuid).orElseThrow();
        reservationRepository.delete(entityToDelete);
    }

    public ReservationResponse entityToResponse(ReservationEntity request) {
        var response = new ReservationResponse();
        BeanUtils.copyProperties(request, response);
        var hotel = new HotelResponse();
        BeanUtils.copyProperties(request.getHotel(), hotel);
        response.setHotel(hotel);
        return response;


    }
}
