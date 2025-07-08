package com.jsr_dev.medical_api.controller;

import com.jsr_dev.medical_api.physician.PhysicianMapper;
import com.jsr_dev.medical_api.physician.PhysicianRepository;
import com.jsr_dev.medical_api.physician.PhysicianRequest;
import com.jsr_dev.medical_api.physician.PhysicianResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    public Page<PhysicianResponse> getAllPhysicians(@PageableDefault(size = 15) Pageable pageable) {
        return repository.findAll(pageable)
                .map(PhysicianMapper::mapToPhysicianResponse);
    }
}
