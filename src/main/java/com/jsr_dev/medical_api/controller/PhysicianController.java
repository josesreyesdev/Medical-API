package com.jsr_dev.medical_api.controller;

import com.jsr_dev.medical_api.physician.PhysicianMapper;
import com.jsr_dev.medical_api.physician.PhysicianRepository;
import com.jsr_dev.medical_api.physician.PhysicianRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/physicians")
public class PhysicianController {

    private final PhysicianRepository repository;

    public PhysicianController(PhysicianRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public void addPhysician(@RequestBody PhysicianRequest physicianRequest) {
        repository.save(PhysicianMapper.mapToPhysician(physicianRequest));
    }
}
