package org.saas.auth.service;

import jakarta.validation.constraints.NotBlank;
import org.saas.auth.dto.LoginRequest;
import org.saas.auth.dto.LoginResponse;
import org.saas.auth.dto.RefreshTokenRequest;
import org.saas.auth.dto.RefreshTokenResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);

    RefreshTokenResponse refreshToken(RefreshTokenRequest request);

    void logout(String refreshToken);
}
