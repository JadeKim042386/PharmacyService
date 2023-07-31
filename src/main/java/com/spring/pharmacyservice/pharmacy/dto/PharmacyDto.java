package com.spring.pharmacyservice.pharmacy.dto;

import com.spring.pharmacyservice.pharmacy.entity.Pharmacy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PharmacyDto {
    private Long id;
    private String pharmacyName;
    private String pharmacyAddress;
    private double latitude;
    private double longitude;

    public static PharmacyDto from(Pharmacy pharmacy) {
        return new PharmacyDto(
            pharmacy.getId(),
            pharmacy.getPharmacyName(),
            pharmacy.getPharmacyAddress(),
            pharmacy.getLatitude(),
            pharmacy.getLongitude()
        );
    }
}
