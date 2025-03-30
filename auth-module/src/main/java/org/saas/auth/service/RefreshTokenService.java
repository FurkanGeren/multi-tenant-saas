package org.saas.auth.service;

import org.saas.auth.dto.RefreshTokenRequest;
import org.saas.auth.dto.RefreshTokenResponse;
import org.saas.core.dto.AuthUser;

public interface RefreshTokenService {
    RefreshTokenResponse generateTokens(AuthUser user);

    RefreshTokenResponse refreshAccessToken(RefreshTokenRequest request);
}
