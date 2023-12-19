package com.empresa.travelers.services.implementations;

import com.empresa.travelers.dto.fly.FlyResponse;
import com.empresa.travelers.entities.FlyEntity;
import com.empresa.travelers.enums.SortType;
import com.empresa.travelers.repositories.FlyRepository;
import com.empresa.travelers.services.IFlyService;
import lombok.AllArgsConstructor;
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


@Transactional
@Service
@Slf4j
public class FlyServiceImpl implements IFlyService {

    private final FlyRepository flyRepository;

    public FlyServiceImpl(FlyRepository flyRepository) {
        this.flyRepository = flyRepository;
    }
    @Override
    public Page<FlyResponse> readAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = null;
        switch (sortType){
            case NONE -> pageRequest = PageRequest.of(page, size);
            case LOWER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(page,size, Sort.by(FIELD_BY_SORT).descending());
        }
        return flyRepository.findAll(pageRequest).map(this::entityToResponse);
    }

    @Override
    public Set<FlyResponse> readLessPrice(BigDecimal price) {
        return flyRepository.selectLessPrice(price).stream().map(this::entityToResponse).collect(Collectors.toSet());
    }

    @Override
    public Set<FlyResponse> readBetweenPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        return flyRepository.selectBetweenPrice(minPrice, maxPrice).stream().map(this::entityToResponse).collect(Collectors.toSet());
    }

    @Override
    public Set<FlyResponse> readByOriginDestiny(String origin, String destiny) {
        return flyRepository.selectOriginDestiny(origin, destiny).stream().map(this::entityToResponse).collect(Collectors.toSet());
    }

    //TestEntityToResponse
    private FlyResponse entityToResponse(FlyEntity entity){
        FlyResponse response = new FlyResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }
}
