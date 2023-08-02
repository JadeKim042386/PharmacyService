package com.spring.pharmacyservice.pharmacy.controller;

import com.spring.pharmacyservice.pharmacy.cache.PharmacyRedisTemplateService;
import com.spring.pharmacyservice.pharmacy.dto.PharmacyDto;
import com.spring.pharmacyservice.pharmacy.repository.PharmacyRepository;
import com.spring.pharmacyservice.pharmacy.service.PharmacyRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PharmacyController {
    private final PharmacyRepository pharmacyRepository;
    private final PharmacyRedisTemplateService pharmacyRedisTemplateService;

    //temporary method (may be deleted)
    @GetMapping("/redis/save")
    public String save() {
        List<PharmacyDto> pharmacyDtoList = pharmacyRepository.findAll()
                .stream()
                .map(PharmacyDto::from)
                .toList();

        pharmacyDtoList.forEach(pharmacyRedisTemplateService::save);

        return "success";
    }
}
