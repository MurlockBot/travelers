package com.empresa.travelers.repositories;

import com.empresa.travelers.entities.FlyEntity;
import com.empresa.travelers.entities.TourEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface TourRepository extends CrudRepository<TourEntity, Long> {
}
