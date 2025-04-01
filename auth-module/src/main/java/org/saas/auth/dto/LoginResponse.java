package org.saas.auth.dto;


public record LoginResponse(
        String fullName,
        String accessToken,
        String refreshToken
) {}
