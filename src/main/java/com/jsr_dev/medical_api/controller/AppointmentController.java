package com.jsr_dev.medical_api.controller;

import com.jsr_dev.medical_api.domain.appointment.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentBookingService appointmentBookingService;

    public AppointmentController(AppointmentBookingService appointmentBookingService) {
        this.appointmentBookingService = appointmentBookingService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<AppointmentResponse> add(
            @RequestBody @Valid AddAppointmentRequest addAppointmentRequest,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        AppointmentResponse appointmentResponse = appointmentBookingService
                .reserveAppointment(addAppointmentRequest);

        URI uri = uriComponentsBuilder.path("/appointments/{id}").buildAndExpand(appointmentResponse.id()).toUri();

        return ResponseEntity.created(uri).body(appointmentResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentBookingService.getAppointmentById(id));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Boolean> delete(
            @RequestBody @Valid AppointmentCancellationRequest cancellationRequest
    ) {
        appointmentBookingService.cancel(cancellationRequest);
        return ResponseEntity.noContent().build();
    }
}
