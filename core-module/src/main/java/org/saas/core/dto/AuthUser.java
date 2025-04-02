package org.saas.core.dto;

public record AuthUser(
        Long id,
        String email,
        String password,
        String username,
        String role
) {}
