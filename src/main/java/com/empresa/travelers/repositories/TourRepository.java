package com.empresa.travelers.repositories;

import com.empresa.travelers.entities.TourEntity;
import org.springframework.data.repository.CrudRepository;

public interface TourRepository extends CrudRepository<TourEntity, Long> {
}
