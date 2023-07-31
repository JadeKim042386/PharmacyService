package com.spring.pharmacyservice.pharmacy.service

import com.spring.pharmacyservice.AbstractIntegrationContainerBaseTest
import com.spring.pharmacyservice.pharmacy.entity.Pharmacy
import com.spring.pharmacyservice.pharmacy.repository.PharmacyRepository
import org.springframework.beans.factory.annotation.Autowired

class PharmacyRepositoryServiceTest extends AbstractIntegrationContainerBaseTest {
    @Autowired private PharmacyRepositoryService pharmacyRepositoryService;
    @Autowired private PharmacyRepository pharmacyRepository;

    def setup() {
        pharmacyRepository.deleteAll()
    }

    def "PharmacyRepository update - dirty checking success"() {
        given:
        String address = "서울 특별시 성북구 종암동"
        String modifiedAddress = "서울 광진구 구의동"
        String name = "은혜 약국"

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .build()

        when:
        def savedPharmacy = pharmacyRepository.save(pharmacy)
        pharmacyRepositoryService.updateAddress(savedPharmacy.getId(), modifiedAddress)

        def result = pharmacyRepository.findAll()

        then:
        result.get(0).getPharmacyAddress() == modifiedAddress
    }
}
