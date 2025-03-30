package org.saas.core.dto;

public record AuthUserRequest(String email, String password, String schema) {
}
