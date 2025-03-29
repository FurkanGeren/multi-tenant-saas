package org.saas.tenant.security;

import org.saas.core.domain.enums.ModuleType;
import org.saas.core.security.ModuleAccessResolver;
import org.saas.tenant.entity.SubscriptionPlan;
import org.saas.tenant.entity.Tenant;
import org.saas.core.tenant.TenantContext;
import org.saas.tenant.repository.TenantRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DefaultModuleAccessResolver implements ModuleAccessResolver {

    private final TenantRepository tenantRepository;

    public DefaultModuleAccessResolver(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    public Set<ModuleType> getAccessibleModulesForCurrentTenant() {
        String tenantSchema = TenantContext.getTenantSchema();
        Tenant tenant = tenantRepository.findByDatabaseName(tenantSchema)
                .orElseThrow(() -> new RuntimeException("Tenant bulunamadı"));

        SubscriptionPlan plan = tenant.getSubscriptionPlan();
        Set<ModuleType> modules = new HashSet<>();

        if (plan.isHasAdvancedReporting()) modules.add(ModuleType.REPORTING);
        if (plan.isHasFullAuditLogging()) modules.add(ModuleType.AUDIT);
        if (plan.isHasCustomIntegrations()) modules.add(ModuleType.INTEGRATION);
        if (plan.isHasApiAccess()) modules.add(ModuleType.AUTH);
        if (plan.isHasWebhookSupport()) modules.add(ModuleType.INTEGRATION);
        if (plan.isSchemaIsolated()) modules.add(ModuleType.TENANT);
        // vs...
        modules.add(ModuleType.USER);

        return modules;
    }
}
