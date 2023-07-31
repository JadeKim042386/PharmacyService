package com.spring.pharmacyservice.pharmacy.service;

import com.spring.pharmacyservice.api.dto.DocumentDto;
import com.spring.pharmacyservice.api.dto.KakaoApiResponseDto;
import com.spring.pharmacyservice.api.service.KakaoAddressSearchService;
import com.spring.pharmacyservice.direction.entity.Direction;
import com.spring.pharmacyservice.direction.service.DirectionService;
import com.spring.pharmacyservice.pharmacy.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyRecommendationService {
    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;

    public void recommendPharmacyList(String address) {
        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);
        if (Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())) {
            log.error("[PharmacyRecommendationService recommendPharmacyList fail] Input address: {}", address);
            return;
        }

        DocumentDto documentDto = kakaoApiResponseDto.getDocumentList().get(0);

        List<Direction> directions = directionService.buildDirectionList(documentDto);
        directionService.saveALl(directions);
    }
}
