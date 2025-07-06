package com.jsr_dev.medical_api.physician;

import com.jsr_dev.medical_api.address.AddressRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PhysicianRequest(
        @NotBlank
        String name,
        String avatar,
        @NotBlank @Email
        String email,
        @NotBlank
        String phoneNumber,
        @NotBlank @Pattern(regexp = "\\d{7,9}")
        String document,
        @NotNull
        Specialty specialty,
        @NotNull @Valid
        AddressRequest addressRequest
) {
}