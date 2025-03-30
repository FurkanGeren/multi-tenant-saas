package org.saas.tenant.security;

import org.saas.core.config.ModuleAccessResolver;
import org.saas.core.domain.enums.ModuleType;
import org.saas.core.exception.BusinessException;
import org.saas.tenant.entity.SubscriptionPlan;
import org.saas.tenant.repository.TenantRepository;
import org.saas.tenant.service.TenantService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class ModuleAccessResolverImpl implements ModuleAccessResolver {
    private final TenantRepository tenantRepository;

    public ModuleAccessResolverImpl(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    public boolean isModuleEnabledForTenant(String tenantId, ModuleType moduleType) {
        SubscriptionPlan plan = tenantRepository
                .findByDatabaseName(tenantId)
                .orElseThrow(() -> new BusinessException("Tenant not found: " + tenantId))
                .getSubscriptionPlan();
        return switch (moduleType) {
            case USER, TENANT, AUTH -> true;
            case REPORTING -> plan.isHasAdvancedReporting();
            case AUDIT -> plan.isHasFullAuditLogging();
            case INTEGRATION -> plan.isHasCustomIntegrations();
        };
    }
}
