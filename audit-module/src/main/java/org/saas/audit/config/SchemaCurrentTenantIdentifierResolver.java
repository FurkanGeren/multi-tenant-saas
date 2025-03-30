package org.saas.audit.config;


import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.saas.core.tenant.TenantContext;
import org.springframework.stereotype.Component;

@Component
public class SchemaCurrentTenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    private static final String DEFAULT_TENANT = "public";

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenant = TenantContext.getTenantSchema();
        System.out.println("🎯 Tenant resolved: " + tenant);
        return tenant != null ? tenant : DEFAULT_TENANT;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
