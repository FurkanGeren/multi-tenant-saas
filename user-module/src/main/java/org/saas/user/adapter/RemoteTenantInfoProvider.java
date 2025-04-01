package org.saas.user.adapter;

import org.saas.core.domain.SubscriptionInfo;
import org.saas.core.context.TenantContext;
import org.saas.core.tenant.TenantInfoProvider;
import org.saas.user.client.TenantClient;
import org.springframework.stereotype.Component;

@Component
public class RemoteTenantInfoProvider implements TenantInfoProvider {

    private final TenantClient tenantClient;

    public RemoteTenantInfoProvider(TenantClient tenantClient) {
        this.tenantClient = tenantClient;
    }

    @Override
    public SubscriptionInfo getCurrentTenantInfo() {
        String schema = TenantContext.getTenantSchema();
        return tenantClient.getTenantInfo(schema);
    }
}