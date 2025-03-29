package org.saas.core.security;

import org.saas.core.domain.enums.ModuleType;

import java.util.Set;

public interface ModuleAccessResolver {
    Set<ModuleType> getAccessibleModulesForCurrentTenant();
}
