package com.spring.pharmacyservice.direction.dto;

import com.spring.pharmacyservice.direction.entity.Direction;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OutputDto {
    private String pharmacyName;
    private String pharmacyAddress;
    private String directionUrl;
    private String roadViewUrl;
    private String distance;

    private static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";

    public static OutputDto fromDirection(Direction direction, String directionUrl) {

        return OutputDto.builder()
                .pharmacyName(direction.getTargetPharmacyName())
                .pharmacyAddress(direction.getTargetAddress())
                .directionUrl(directionUrl)
                .roadViewUrl(ROAD_VIEW_BASE_URL + direction.getTargetLatitude() + "," + direction.getTargetLongitude())
                .distance(String.format("%.2f km", direction.getDistance()))
                .build();
    }
}
