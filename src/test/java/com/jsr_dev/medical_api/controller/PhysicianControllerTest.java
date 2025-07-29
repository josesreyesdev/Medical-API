package com.jsr_dev.medical_api.controller;

import com.jsr_dev.medical_api.domain.address.AddAddressRequest;
import com.jsr_dev.medical_api.domain.address.AddressMapper;
import com.jsr_dev.medical_api.domain.physician.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
    @DisplayName("Returns 400 when request body is missing or invalid")
    @WithMockUser
    void register_scenario1() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/physicians"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Returns 201 CREATED when physician registration is successful")
    @WithMockUser
    void register_scenario2() throws Exception {
        // Arrange
        var physicianRequest = new AddPhysicianRequest(
                "Physician",
                null,
                "physician@example.com",
                "61999999999",
                "1234567",
                Specialty.CARDIOLOGY,
                addressRequest());

        when(physicianRepository.save(any())).thenReturn(PhysicianMapper.mapToPhysician(physicianRequest));

        // Act
        MockHttpServletResponse response = mockMvc.perform(post("/physicians")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJacksonTester.write(physicianRequest).getJson()))
                .andReturn().getResponse();

        // Assert
        var physicianResponse = new PhysicianResponse(
                null,
                physicianRequest.name(),
                null,
                physicianRequest.email(),
                physicianRequest.phoneNumber(),
                physicianRequest.specialty(),
                physicianRequest.document(),
                AddressMapper.mapToAddressResponse(
                        AddressMapper.mapToAddress(physicianRequest.addAddressRequest()))
        );
        String expectedJSON = responseJacksonTester.write(physicianResponse).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedJSON);
    }

    private AddAddressRequest addressRequest() {
        return new AddAddressRequest(
                "street 404",
                "Mexico City",
                "San Carlos",
                "Mexico City",
                "14700",
                "Mexico",
                "123-A",
                "NA",
                "Complement"
        );
    }
}