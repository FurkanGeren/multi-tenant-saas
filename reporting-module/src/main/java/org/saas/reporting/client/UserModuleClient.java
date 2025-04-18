package org.saas.reporting.client;

import org.saas.core.dto.UserResponse;
import org.saas.reporting.config.FeignTenantInterceptorConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "user-module",
        url = "http://localhost:8081",
        configuration = FeignTenantInterceptorConfig.class
)
public interface UserModuleClient {

    @GetMapping("/api/internal/users/moderators")
    List<UserResponse> getModerators(@RequestParam("schema") String schema);
}