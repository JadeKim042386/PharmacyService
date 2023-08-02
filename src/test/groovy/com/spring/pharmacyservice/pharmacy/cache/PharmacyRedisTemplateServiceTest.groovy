package com.spring.pharmacyservice.pharmacy.cache

import com.spring.pharmacyservice.AbstractIntegrationContainerBaseTest
import com.spring.pharmacyservice.pharmacy.dto.PharmacyDto
import org.springframework.beans.factory.annotation.Autowired

class PharmacyRedisTemplateServiceTest extends AbstractIntegrationContainerBaseTest {
    @Autowired
    private PharmacyRedisTemplateService pharmacyRedisTemplateService

    def setup() {
        pharmacyRedisTemplateService.findAll()
        .forEach(dto -> {
            pharmacyRedisTemplateService.delete(dto.getId())
        })
    }

    def "save success"() {
        given:
        String pharmacyName = "name"
        String pharmacyAddress = "address"
        def pharmacyDto = PharmacyDto.builder()
                .id(1L)
                .pharmacyName(pharmacyName)
                .pharmacyAddress(pharmacyAddress)
                .build()

        when:
        pharmacyRedisTemplateService.save(pharmacyDto)
        def result = pharmacyRedisTemplateService.findAll()

        then:
        result.size() == 1
        result.get(0).id == 1L
        result.get(0).pharmacyName == pharmacyName
        result.get(0).pharmacyAddress == pharmacyAddress
    }

    def "save fail"() {
        given:
        def dto = PharmacyDto.builder().build()

        when:
        pharmacyRedisTemplateService.save(dto)
        def result = pharmacyRedisTemplateService.findAll()

        then:
        result.size() == 0
    }

    def "delete"() {
        given:
        String pharmacyName = "name"
        String pharmacyAddress = "address"
        def pharmacyDto = PharmacyDto.builder()
                .id(1L)
                .pharmacyName(pharmacyName)
                .pharmacyAddress(pharmacyAddress)
                .build()

        when:
        pharmacyRedisTemplateService.save(pharmacyDto)
        pharmacyRedisTemplateService.delete(pharmacyDto.getId())
        def result = pharmacyRedisTemplateService.findAll()

        then:
        result.size() == 0
    }
}
