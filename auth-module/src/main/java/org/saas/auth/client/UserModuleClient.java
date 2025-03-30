package org.saas.auth.client;

import org.saas.core.dto.AuthUser;
import org.saas.core.dto.AuthUserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = "user-module", url = "http://localhost:8081")
public interface UserModuleClient{

    @PostMapping("/api/internal/users/email")
    AuthUser getByEmail(AuthUserRequest authUserRequest);

}




