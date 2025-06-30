package com.jsr_dev.medical_api.controller;

import com.jsr_dev.medical_api.physician.PhysicianRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/physicians")
public class PhysicianController {

    @PostMapping
    public void addPhysician(@RequestBody PhysicianRequest physicianRequest) {
        System.out.println(physicianRequest);
    }
}
