package org.saas.user.service;

import org.saas.core.dto.AuthUser;
import org.saas.core.dto.AuthUserRequest;
import org.saas.user.dto.CreateUserRequest;
import org.saas.user.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);

    AuthUser getByEmail(AuthUserRequest authUserRequest);

    List<org.saas.core.dto.UserResponse> getModerators(String schema);
}
