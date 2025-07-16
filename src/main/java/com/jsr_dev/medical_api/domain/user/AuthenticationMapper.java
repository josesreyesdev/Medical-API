package com.jsr_dev.medical_api.domain.user;

public class AuthenticationMapper {
    public static AuthenticationResponse mapToAuthResponse(String token) {
        return new AuthenticationResponse(token);
    }
}
