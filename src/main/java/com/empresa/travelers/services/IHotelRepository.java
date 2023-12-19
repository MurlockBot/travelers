package com.empresa.travelers.services;

import com.empresa.travelers.dto.HotelResponse;
import com.empresa.travelers.services.abstractservices.CatalogServices;

import java.util.Set;

public interface IHotelRepository extends CatalogServices<HotelResponse> {
    Set<HotelResponse> readGreaterThan(Integer rating);
}
