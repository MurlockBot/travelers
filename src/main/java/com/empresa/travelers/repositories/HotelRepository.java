package com.empresa.travelers.repositories;

import com.empresa.travelers.entities.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


public interface HotelRepository extends JpaRepository<HotelEntity, Long> {

    Set<HotelEntity> findByPriceLessThan(BigDecimal price);
    Set<HotelEntity> findByPriceIsBetween(BigDecimal minPrice, BigDecimal maxPrice);
    Set<HotelEntity> findByRatingGreaterThan(Integer rating);
    Optional<HotelEntity> findByReservationsId(UUID id);
}
