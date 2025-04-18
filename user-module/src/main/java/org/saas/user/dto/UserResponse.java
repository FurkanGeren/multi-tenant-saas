package org.saas.user.dto;


public record UserResponse(
        Long id,
        String username,
        String email,
        String fullName,
        String roleName
) {}