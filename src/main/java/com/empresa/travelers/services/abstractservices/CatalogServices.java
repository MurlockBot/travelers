package com.empresa.travelers.services.abstractservices;

import com.empresa.travelers.enums.SortType;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Set;

public interface CatalogServices<Response> {

    String FIELD_BY_SORT = "price";

    Page<Response> readAll(Integer page, Integer size, SortType sortType);

    Set<Response> readLessPrice(BigDecimal price);

    Set<Response> readBetweenPrice(BigDecimal minPrice, BigDecimal maxPrice);



}
