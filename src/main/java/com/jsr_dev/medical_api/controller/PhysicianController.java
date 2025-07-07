package com.jsr_dev.medical_api.controller;

import com.jsr_dev.medical_api.physician.PhysicianMapper;
import com.jsr_dev.medical_api.physician.PhysicianRepository;
import com.jsr_dev.medical_api.physician.PhysicianRequest;
import com.jsr_dev.medical_api.physician.PhysicianResponse;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/physicians")
public class PhysicianController {

    private final PhysicianRepository repository;

    public PhysicianController(PhysicianRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @PostMapping
    public void addPhysician(@RequestBody @Valid PhysicianRequest physicianRequest) {
        repository.save(PhysicianMapper.mapToPhysician(physicianRequest));
    }

    @GetMapping
    public List<PhysicianResponse> getAllPhysicians() {
        return repository.findAll().stream()
                .map(PhysicianMapper::mapToPhysicianResponse)
                .collect(Collectors.toList());
    }
}
