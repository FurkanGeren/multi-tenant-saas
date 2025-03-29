package org.saas.user.adapter;

import org.saas.core.domain.enums.ModuleType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient(name = "tenant-module", url = "http://localhost:8080")
public interface TenantModuleClient {
    @GetMapping("/internal/modules/{tenantSchema}/accessible")
    Set<ModuleType> getAccessibleModules(@PathVariable("tenantSchema") String tenantSchema);
}