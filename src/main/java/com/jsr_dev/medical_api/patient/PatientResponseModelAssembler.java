package com.jsr_dev.medical_api.patient;

import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PatientResponseModelAssembler implements RepresentationModelAssembler<PatientResponse, EntityModel<PatientResponse>> {
    @Override
    @NotNull
    public EntityModel<PatientResponse> toModel(@NotNull PatientResponse response) {
        return EntityModel.of(response);
    }
}
