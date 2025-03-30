package org.saas.core.config;

import org.saas.core.domain.enums.ModuleType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tenant-module", url = "http://localhost:8080")
public interface ModuleAccessResolverFeignClient {

   // @Override
    @GetMapping("/api/internal/plan/{tenantId}")
    boolean isModuleEnabledForTenant(
            @PathVariable("tenantId") String tenantId,
            @RequestParam("module") String moduleType
    );
}
