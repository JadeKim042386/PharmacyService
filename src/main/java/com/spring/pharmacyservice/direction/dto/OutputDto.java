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

    public static OutputDto fromDirection(Direction direction) {
        return OutputDto.builder()
                .pharmacyName(direction.getTargetPharmacyName())
                .pharmacyAddress(direction.getTargetAddress())
                .directionUrl("todo")
                .roadViewUrl("todo")
                .distance(String.format("%.2f km", direction.getDistance()))
                .build();
    }
}
