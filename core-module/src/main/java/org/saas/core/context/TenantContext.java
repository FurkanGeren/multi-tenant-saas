package org.saas.core.context;



public class TenantContext {
    private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();

    public static void setTenantSchema(String schema) {
        System.out.println("🟡 Tenant set: " + schema);
        currentTenant.set(schema);
    }

    public static String getTenantSchema() {
        return currentTenant.get();
    }

    public static void clear() {
        System.out.println("🔵 Tenant cleared");
        currentTenant.remove();
    }
}
