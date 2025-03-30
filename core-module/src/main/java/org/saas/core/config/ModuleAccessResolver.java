package org.saas.core.config;

import org.saas.core.domain.enums.ModuleType;
import org.springframework.stereotype.Component;


public interface ModuleAccessResolver {
    boolean isModuleEnabledForTenant(String tenantId, ModuleType moduleType);
}
