package com.jsr_dev.medical_api.controller;

import com.jsr_dev.medical_api.patient.PatientRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patients")
public class PatientController {
    @PostMapping
    public void addPatient(@RequestBody PatientRequest patientRequest) {
        System.out.println(patientRequest);
    }
}
