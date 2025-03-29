package org.saas.tenant.entity;


import jakarta.persistence.*;
import org.saas.core.domain.Auditable;
import org.saas.core.domain.enums.SubscriptionPlanType;

@Entity
@Table(name = "subscription_plans")
public class SubscriptionPlan extends Auditable {

    @Enumerated(EnumType.STRING)  // Enum tipini string olarak veritabanında saklayacağız
    @Column(name = "type", nullable = false)
    private SubscriptionPlanType type;

    @Column(name = "max_users")
    private int maxUsers;

    @Column(name = "has_advanced_reporting")
    private boolean hasAdvancedReporting;

    @Column(name = "has_full_audit_logging")
    private boolean hasFullAuditLogging;

    @Column(name = "has_custom_integrations")
    private boolean hasCustomIntegrations;

    @Column(name = "has_api_access")
    private boolean hasApiAccess;

    @Column(name = "has_webhook_support")
    private boolean hasWebhookSupport;

    @Column(name = "is_schema_isolated")
    private boolean isSchemaIsolated;

    // Constructors
    public SubscriptionPlan() {}

    public SubscriptionPlan(SubscriptionPlanType type, int maxUsers, boolean hasAdvancedReporting, boolean hasFullAuditLogging, boolean hasCustomIntegrations, boolean hasApiAccess, boolean hasWebhookSupport, boolean isSchemaIsolated) {
        this.type = type;
        this.maxUsers = maxUsers;
        this.hasAdvancedReporting = hasAdvancedReporting;
        this.hasFullAuditLogging = hasFullAuditLogging;
        this.hasCustomIntegrations = hasCustomIntegrations;
        this.hasApiAccess = hasApiAccess;
        this.hasWebhookSupport = hasWebhookSupport;
        this.isSchemaIsolated = isSchemaIsolated;
    }


    // Getters and Setters
    // (İsteğe bağlı olarak Lombok @Data da kullanabilirsin ama kullanmadığını söylemiştin)


    public SubscriptionPlanType getType() {
        return type;
    }

    public void setType(SubscriptionPlanType type) {
        this.type = type;
    }

    public int getMaxUsers() { return maxUsers; }

    public void setMaxUsers(int maxUsers) { this.maxUsers = maxUsers; }

    public boolean isHasAdvancedReporting() { return hasAdvancedReporting; }

    public void setHasAdvancedReporting(boolean hasAdvancedReporting) { this.hasAdvancedReporting = hasAdvancedReporting; }

    public boolean isHasFullAuditLogging() { return hasFullAuditLogging; }

    public void setHasFullAuditLogging(boolean hasFullAuditLogging) { this.hasFullAuditLogging = hasFullAuditLogging; }

    public boolean isHasCustomIntegrations() { return hasCustomIntegrations; }

    public void setHasCustomIntegrations(boolean hasCustomIntegrations) { this.hasCustomIntegrations = hasCustomIntegrations; }

    public boolean isHasApiAccess() { return hasApiAccess; }

    public void setHasApiAccess(boolean hasApiAccess) { this.hasApiAccess = hasApiAccess; }

    public boolean isHasWebhookSupport() { return hasWebhookSupport; }

    public void setHasWebhookSupport(boolean hasWebhookSupport) { this.hasWebhookSupport = hasWebhookSupport; }

    public boolean isSchemaIsolated() { return isSchemaIsolated; }

    public void setSchemaIsolated(boolean schemaIsolated) { isSchemaIsolated = schemaIsolated; }
}