package com.empresa.travelers.repositories;

import com.empresa.travelers.entities.FlyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface FlyRepository extends JpaRepository<FlyEntity, Long> {

    /// Búsqueda de vuelos por precio menor a: searchPrice Param.
    @Query("select f from fly f where f.price < :searchPrice")
    Set<FlyEntity> selectLessPrice(BigDecimal searchPrice);

    /// Búsqueda de vuelos entre dos valores: minPrice and maxPrice
    @Query("select f from fly f where f.price between :minPrice and :maxPrice")
    Set<FlyEntity> selectBetweenPrice(BigDecimal minPrice, BigDecimal maxPrice);

    ///Búsqueda de vuelos desde hasta exactos
    @Query("select f from fly f where f.originName = :origin and f.destinyName = :destiny")
    Set<FlyEntity> selectOriginDestiny(String origin, String destiny);

    ///Búsqueda por número de ticket el vuelo que le corresponde
    @Query("select f from fly f join fetch f.tickets t where t.id = :id")
    Optional<FlyEntity> findByTicketId(UUID id);
}
