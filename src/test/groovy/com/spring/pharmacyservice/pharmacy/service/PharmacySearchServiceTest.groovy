package com.spring.pharmacyservice.pharmacy.service

import com.spring.pharmacyservice.pharmacy.cache.PharmacyRedisTemplateService
import com.spring.pharmacyservice.pharmacy.entity.Pharmacy
import com.spring.pharmacyservice.pharmacy.repository.PharmacyRepository
import org.assertj.core.util.Lists
import spock.lang.Specification

class PharmacySearchServiceTest extends Specification {
    private PharmacySearchService pharmacySearchService

    private PharmacyRepository pharmacyRepository = Mock()
    private PharmacyRedisTemplateService pharmacyRedisTemplateService = Mock()

    private List<Pharmacy> pharmacyList

    def setup() {
        pharmacySearchService = new PharmacySearchService(pharmacyRepository, pharmacyRedisTemplateService)

        pharmacyList = Lists.newArrayList(
                Pharmacy.builder()
                        .id(1L)
                        .pharmacyName("돌곶이온누리약국")
                        .latitude(37.61040424)
                        .longitude(127.0569046)
                        .build(),
                Pharmacy.builder()
                        .id(2L)
                        .pharmacyName("호수온누리약국")
                        .latitude(37.60894036)
                        .longitude(127.029052)
                        .build()
        )
    }

    def "Redis 장애시 DB를 이용하여 약국 데이터 조회"() {
        when:
        pharmacyRedisTemplateService.findAll() >> []
        pharmacyRepository.findAll() >> pharmacyList

        def result = pharmacySearchService.searchPharmacyDtoList()

        then:
        result.size() == 2
    }
}
