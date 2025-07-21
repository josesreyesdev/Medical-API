package com.jsr_dev.medical_api.controller;

import com.jsr_dev.medical_api.domain.appointment.AddAppointmentRequest;
import com.jsr_dev.medical_api.domain.appointment.AppointmentResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultations")
public class AppointmentController {

    @PostMapping
    @Transactional
    public ResponseEntity<AppointmentResponse> add(@RequestBody @Valid AddAppointmentRequest addAppointmentRequest) {
        return ResponseEntity.ok(new AppointmentResponse(null, null, null, null));
    }
}
