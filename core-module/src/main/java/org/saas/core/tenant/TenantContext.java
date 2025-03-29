package org.saas.core.tenant;

public class TenantContext {
    private static final ThreadLocal<String> TENANT_SCHEMA = new ThreadLocal<>();

    public static void setTenantSchema(String schema) {
        TENANT_SCHEMA.set(schema);
    }

    public static String getTenantSchema() {
        return TENANT_SCHEMA.get();
    }

    public static void clear() {
        TENANT_SCHEMA.remove();
    }}
