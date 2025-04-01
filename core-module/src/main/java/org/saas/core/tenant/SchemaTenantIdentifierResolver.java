package org.saas.core.tenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.saas.core.context.TenantContext;

public class SchemaTenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    private static final String DEFAULT_SCHEMA = "public";

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenant = TenantContext.getTenantSchema();
        return (tenant != null) ? tenant : DEFAULT_SCHEMA;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}