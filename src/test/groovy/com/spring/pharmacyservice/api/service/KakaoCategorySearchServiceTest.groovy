package com.spring.pharmacyservice.api.service

import com.spring.pharmacyservice.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired

class KakaoCategorySearchServiceTest extends AbstractIntegrationContainerBaseTest {
    @Autowired
    private KakaoCategorySearchService kakaoCategorySearchService

    def "위도, 경도, 반경으로 requestPharmacyCategorySearch 정상 반환"() {
        given:
        def latitude = 37.61040424
        def longitude = 127.0569046
        def radius = 10 //km

        when:
        def result = kakaoCategorySearchService.requestPharmacyCategorySearch(latitude, longitude, radius)

        then:
        result.documentList.size() > 0
        result.documentList.get(0).placeName == "돌곶이온누리약국"
    }
}
