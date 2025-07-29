package com.jsr_dev.medical_api.controller;

import com.jsr_dev.medical_api.domain.physician.AddPhysicianRequest;
import com.jsr_dev.medical_api.domain.physician.PhysicianRepository;
import com.jsr_dev.medical_api.domain.physician.PhysicianResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class PhysicianControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<AddPhysicianRequest> requestJacksonTester;

    @Autowired
    private JacksonTester<PhysicianResponse> responseJacksonTester;

    @MockitoBean
    private PhysicianRepository physicianRepository;

    @Test
    @DisplayName("Should return 400 BAD_REQUEST when request body is missing or invalid")
    @WithMockUser
    void register_scenario1() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/physicians"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}