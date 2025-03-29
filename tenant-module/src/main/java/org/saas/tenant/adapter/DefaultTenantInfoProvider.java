package org.saas.tenant.adapter;

import org.saas.core.domain.SubscriptionInfo;
import org.saas.core.tenant.TenantInfoProvider;
import org.saas.tenant.entity.Tenant;
import org.saas.core.tenant.TenantContext;
import org.saas.tenant.repository.TenantRepository;
import org.springframework.stereotype.Component;

@Component
public class DefaultTenantInfoProvider implements TenantInfoProvider {

    private final TenantRepository tenantRepository;

    public DefaultTenantInfoProvider(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    public SubscriptionInfo getCurrentTenantInfo() {
        String schema = TenantContext.getTenantSchema();
        Tenant tenant = tenantRepository.findByDatabaseName(schema)
                .orElseThrow(() -> new RuntimeException("Tenant bulunamadı"));

        return new SubscriptionInfo(
                tenant.getName(),
                tenant.getDatabaseName(),
                tenant.getSubscriptionPlan().getMaxUsers()
        );
    }
}