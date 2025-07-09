package com.jsr_dev.medical_api.physician;

import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PhysicianResponseModelAssembler implements RepresentationModelAssembler<PhysicianResponse, EntityModel<PhysicianResponse>> {
    @Override
    @NotNull
    public EntityModel<PhysicianResponse> toModel(@NotNull PhysicianResponse response) {
        return EntityModel.of(response);
    }
}
