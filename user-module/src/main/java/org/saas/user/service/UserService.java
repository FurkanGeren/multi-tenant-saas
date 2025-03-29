package org.saas.user.service;

import org.saas.user.dto.CreateUserRequest;
import org.saas.user.dto.UserResponse;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);
}
