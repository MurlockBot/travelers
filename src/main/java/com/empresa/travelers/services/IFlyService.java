package com.empresa.travelers.services;

import com.empresa.travelers.dto.FlyResponse;

import java.util.Set;

public interface IFlyService extends CatalogServices<FlyResponse> {

    Set<FlyResponse> readByOriginDestiny(String origin, String destiny);
}
