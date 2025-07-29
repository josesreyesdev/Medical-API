package com.jsr_dev.medical_api.controller;

import com.jsr_dev.medical_api.domain.appointment.AddAppointmentRequest;
import com.jsr_dev.medical_api.domain.appointment.AppointmentBookingService;
import com.jsr_dev.medical_api.domain.appointment.AppointmentResponse;
import com.jsr_dev.medical_api.domain.physician.Specialty;
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

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters // Simulate JSON requests and responses
class AppointmentControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<AddAppointmentRequest> requestJacksonTester;

    @Autowired
    private JacksonTester<AppointmentResponse> responseJacksonTester;

    @MockitoBean // Skip interaction with db
    private AppointmentBookingService appointmentBookingService;

    @Test
    @DisplayName("Returns HTTP 400 (BAD REQUEST) when the request has no data.")
    @WithMockUser // Skip authentication configuration for requests
    void addScenario1() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/appointments"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Returns HTTP 201 (Created) with Location header when appointment is successfully created.")
    @WithMockUser // Skip authentication configuration for requests
    void addScenario2() throws Exception {

        // GIVEN
        LocalDateTime date = LocalDateTime.now().plusHours(1); // in 1 hour
        Specialty specialty = Specialty.CARDIOLOGY;
        AppointmentResponse appointmentResponse =
                new AppointmentResponse(123L,2L, 5L, date);

        // WHEN
        when(appointmentBookingService.reserveAppointment(any())).thenReturn(appointmentResponse);

        AddAppointmentRequest request = new AddAppointmentRequest(2L, 5L, date, specialty);

        MockHttpServletResponse response = mockMvc.perform(post("/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJacksonTester.write(request).getJson())
                ).andReturn().getResponse();

        // THEN
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        // Verify expected JSON
        String expectedJSON = responseJacksonTester.write(appointmentResponse).getJson();
        assertThat(response.getContentAsString()).isEqualTo(expectedJSON);

        // Verify location header
        String expectedLocation = "/appointments/" + appointmentResponse.id();
        assertThat(response.getHeader("Location")).endsWith(expectedLocation);
    }
}