package com.spring.pharmacyservice.direction.controller

import com.spring.pharmacyservice.direction.service.DirectionService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class DirectionControllerTest extends Specification {
    private MockMvc mvc
    private DirectionService directionService = Mock()

    def setup() {
        mvc = MockMvcBuilders.standaloneSetup(new DirectionController(directionService)).build()
    }

    def "[GET] /dir/{encodedId}"() {
        given:
        String encodedId = "r"
        String redirectURL = "https://map.kakao.com/link/map/pharmacy,38.11,128.11"

        when:
        directionService.findDirectionUrlById(encodedId) >> redirectURL

        def result = mvc.perform(get("/dir/{encodedId}", encodedId))

        then:
        result.andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl(redirectURL))
            .andDo(print())
    }
}
