package com.spring.pharmacyservice.pharmacy.service;

import com.spring.pharmacyservice.api.dto.DocumentDto;
import com.spring.pharmacyservice.api.dto.KakaoApiResponseDto;
import com.spring.pharmacyservice.api.service.KakaoAddressSearchService;
import com.spring.pharmacyservice.direction.dto.OutputDto;
import com.spring.pharmacyservice.direction.entity.Direction;
import com.spring.pharmacyservice.direction.service.Base62Service;
import com.spring.pharmacyservice.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    private final Base62Service base62Service;

    @Value("${pharmacy.recommendation.base.url}")
    private String baseUrl;

    public List<OutputDto> recommendPharmacyList(String address) {
        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);
        if (Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())) {
            log.error("[PharmacyRecommendationService recommendPharmacyList fail] Input address: {}", address);
            return List.of();
        }

        DocumentDto documentDto = kakaoApiResponseDto.getDocumentList().get(0);

//        List<Direction> directions = directionService.buildDirectionListByCategoryApi(documentDto);
        List<Direction> directions = directionService.buildDirectionList(documentDto);

        return directionService.saveALl(directions)
                .stream()
                .map(direction -> OutputDto.fromDirection(direction, baseUrl + base62Service.encodeDirectionId(direction.getId())))
                .toList();
    }
}
