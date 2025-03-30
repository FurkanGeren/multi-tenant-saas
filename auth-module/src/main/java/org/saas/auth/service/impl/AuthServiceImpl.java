package org.saas.auth.service.impl;

import org.saas.auth.client.UserModuleClient;
import org.saas.auth.dto.LoginRequest;
import org.saas.auth.dto.LoginResponse;
import org.saas.auth.dto.RefreshTokenRequest;
import org.saas.auth.dto.RefreshTokenResponse;
import org.saas.auth.security.JwtTokenProvider;
import org.saas.auth.service.AuthService;
import org.saas.auth.service.RefreshTokenService;
import org.saas.core.dto.AuthUser;
import org.saas.core.dto.AuthUserRequest;
import org.saas.core.tenant.TenantContext;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {


    private final UserModuleClient userModuleClient;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;


    public AuthServiceImpl(UserModuleClient userModuleClient, JwtTokenProvider jwtTokenProvider, RefreshTokenService refreshTokenService) {
        this.userModuleClient = userModuleClient;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenService = refreshTokenService;
    }


    @Override
    public LoginResponse login(LoginRequest request) {
        System.out.println(">> [AuthService] Login çağrıldı: " + request.email());
        String schema = TenantContext.getTenantSchema();

        AuthUser authUser = userModuleClient.getByEmail(new AuthUserRequest(request.email(), request.password(), schema));

        String accessToken = jwtTokenProvider.generateToken(authUser);
        RefreshTokenResponse refreshToken = refreshTokenService.generateTokens(authUser);

        return new LoginResponse(accessToken, refreshToken.refreshToken());
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        return refreshTokenService.refreshAccessToken(request);
    }

    @Override
    public void logout(String refreshToken) {
        refreshTokenService.logout(refreshToken);
    }
}
