package com.empresa.travelers.repositories;

import com.empresa.travelers.entities.CustomerEntity;
import com.empresa.travelers.entities.FlyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
}
