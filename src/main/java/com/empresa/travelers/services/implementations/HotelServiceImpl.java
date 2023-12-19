package com.empresa.travelers.services.implementations;

import com.empresa.travelers.dto.HotelResponse;
import com.empresa.travelers.entities.HotelEntity;
import com.empresa.travelers.enums.SortType;
import com.empresa.travelers.repositories.HotelRepository;
import com.empresa.travelers.services.IHotelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class HotelServiceImpl implements IHotelRepository {

    private final HotelRepository hotelRepository;

    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public Page<HotelResponse> readAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = null;
        switch (sortType) {
            case NONE -> pageRequest = PageRequest.of(page, size);
            case UPPER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case LOWER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }

        return hotelRepository.findAll(pageRequest).map(this::entityToResponse);
    }

    @Override
    public Set<HotelResponse> readLessPrice(BigDecimal price) {
        return hotelRepository.findByPriceLessThan(price).stream().map(this::entityToResponse).collect(Collectors.toSet());
    }

    @Override
    public Set<HotelResponse> readBetweenPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        return hotelRepository.findByPriceIsBetween(minPrice, maxPrice).stream().map(this::entityToResponse).collect(Collectors.toSet());
    }

    @Override
    public Set<HotelResponse> readGreaterThan(Integer rating) {
        return hotelRepository.findByRatingGreaterThan(rating).stream().map(this::entityToResponse).collect(Collectors.toSet());
    }

    public HotelResponse entityToResponse(HotelEntity hotelEntity) {
        HotelResponse response = new HotelResponse();
        BeanUtils.copyProperties(hotelEntity, response);
        return response;
    }
}
