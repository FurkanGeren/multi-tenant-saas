package org.saas.core.dto;

public record UserResponse(
        Long id,
        String username,
        String email
) {}