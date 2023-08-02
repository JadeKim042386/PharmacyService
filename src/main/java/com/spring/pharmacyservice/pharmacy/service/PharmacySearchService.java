package com.spring.pharmacyservice.pharmacy.service;

import com.spring.pharmacyservice.pharmacy.cache.PharmacyRedisTemplateService;
import com.spring.pharmacyservice.pharmacy.dto.PharmacyDto;
import com.spring.pharmacyservice.pharmacy.entity.Pharmacy;
import com.spring.pharmacyservice.pharmacy.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacySearchService {
    private final PharmacyRepository pharmacyRepository;
    private final PharmacyRedisTemplateService pharmacyRedisTemplateService;

    public List<PharmacyDto> searchPharmacyDtoList() {
        List<PharmacyDto> pharmacyDtoList = pharmacyRedisTemplateService.findAll();
        if (!pharmacyDtoList.isEmpty()) return pharmacyDtoList;

        return pharmacyRepository.findAll()
                .stream()
                .map(PharmacyDto::from)
                .toList();
    }
}
