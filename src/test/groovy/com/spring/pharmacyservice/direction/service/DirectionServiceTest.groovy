package com.spring.pharmacyservice.direction.service

import com.spring.pharmacyservice.api.dto.DocumentDto
import com.spring.pharmacyservice.api.dto.KakaoApiResponseDto
import com.spring.pharmacyservice.api.dto.MetaDto
import com.spring.pharmacyservice.api.service.KakaoCategorySearchService
import com.spring.pharmacyservice.direction.repository.DirectionRepository
import com.spring.pharmacyservice.pharmacy.dto.PharmacyDto
import com.spring.pharmacyservice.pharmacy.service.PharmacySearchService
import spock.lang.Specification
import spock.lang.Subject

class DirectionServiceTest extends Specification {
    private PharmacySearchService pharmacySearchService = Mock()
    private DirectionRepository directionRepository = Mock()
    private KakaoCategorySearchService kakaoCategorySearchService = Mock()
    private final Base62Service base62Service = Mock()
    @Subject
    private DirectionService directionService = new DirectionService(pharmacySearchService, directionRepository, kakaoCategorySearchService, base62Service)

    private List<PharmacyDto> pharmacyList

    def setup() {
        pharmacyList = new ArrayList<>()
        pharmacyList.addAll(
                PharmacyDto.builder()
                        .id(1L)
                        .pharmacyName("돌곶이온누리약국")
                        .pharmacyAddress("주소1")
                        .latitude(37.61040424)
                        .longitude(127.0569046)
                        .build(),
                PharmacyDto.builder()
                        .id(2L)
                        .pharmacyName("호수온누리약국")
                        .pharmacyAddress("주소2")
                        .latitude(37.60894036)
                        .longitude(127.029052)
                        .build()
        )

    }

    def "buildDirectionList - 거리를 기준으로 정렬"() {
        given:
        def addressName = "서울 성북구 종암로10길"
        double inputLatitude = 37.5960650456809
        double inputLongitude = 127.037033003036

        def documentDto = DocumentDto.builder()
                .addressName(addressName)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build()

        when:
        pharmacySearchService.searchPharmacyDtoList() >> pharmacyList
        def results = directionService.buildDirectionList(documentDto)

        then:
        results.size() == 2
        results.get(0).targetPharmacyName == "호수온누리약국"
        results.get(1).targetPharmacyName == "돌곶이온누리약국"
        String.format("%.1f", results.get(0).distance) == "1.6"
        String.format("%.1f", results.get(1).distance) == "2.4"
    }

    def "buildDirectionList - 반경 10km 내의 약국을 검색"() {
        given:
        pharmacyList.add(
                PharmacyDto.builder()
                        .id(3L)
                        .pharmacyName("경기약국")
                        .pharmacyAddress("주소3")
                        .latitude(37.3825107393401)
                        .longitude(127.236707811313)
                        .build())

        def addressName = "서울 성북구 종암로10길"
        double inputLatitude = 37.5960650456809
        double inputLongitude = 127.037033003036

        def documentDto = DocumentDto.builder()
                .addressName(addressName)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build()

        when:
        pharmacySearchService.searchPharmacyDtoList() >> pharmacyList
        def results = directionService.buildDirectionList(documentDto)

        then:
        results.size() == 2
        results.get(0).targetPharmacyName == "호수온누리약국"
        results.get(1).targetPharmacyName == "돌곶이온누리약국"
    }

    def "buildDirectionListByCategoryApi - 반경 10km 내의 약국을 검색"() {
        given:
        pharmacyList.add(
                PharmacyDto.builder()
                        .id(3L)
                        .pharmacyName("경기약국")
                        .pharmacyAddress("주소3")
                        .latitude(37.3825107393401)
                        .longitude(127.236707811313)
                        .build())

        def addressName = "서울 성북구 종암로10길"
        double inputLatitude = 37.5960650456809
        double inputLongitude = 127.037033003036

        def documentDto = DocumentDto.builder()
                .placeName("호수온누리약국")
                .addressName(addressName)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build()

        def kakaoApiResponsedto = KakaoApiResponseDto.builder()
                .metaDto(new MetaDto(1))
                .documentList(List.of(documentDto))
                .build()

        when:
        kakaoCategorySearchService.requestPharmacyCategorySearch(inputLatitude, inputLongitude, 10.0) >> kakaoApiResponsedto
        def results = directionService.buildDirectionListByCategoryApi(documentDto)

        then:
        results.size() == 1
        results.get(0).targetPharmacyName == "호수온누리약국"
    }
}
