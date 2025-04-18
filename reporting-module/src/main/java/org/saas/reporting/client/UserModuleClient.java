package org.saas.reporting.client;

import org.saas.core.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "user-module", url = "http://localhost:8081")
public interface UserModuleClient {

    @GetMapping("/api/internal/users/moderators")
    List<UserResponse> getModerators(@RequestParam("schema") String schema);
}