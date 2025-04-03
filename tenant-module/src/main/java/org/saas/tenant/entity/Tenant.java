package org.saas.tenant.entity;

import jakarta.persistence.*;
import org.saas.core.domain.Auditable;

@Entity
@Table(name = "tenants")
public class Tenant extends Auditable {

    @Column(name = "name",nullable = false, unique = true)
    private String name;

    @Column(name = "database_name", nullable = false, unique = true)
    private String databaseName;

    @Column(name = "tenant_key", nullable = false, unique = true)
    private String tenantKey;

    @ManyToOne
    @JoinColumn(name = "subscription_plan_id")
    private SubscriptionPlan subscriptionPlan;

    public Tenant() {}


    public Tenant(String name, String databaseName, String tenantKey, SubscriptionPlan subscriptionPlan) {
        this.name = name;
        this.databaseName = databaseName;
        this.tenantKey = tenantKey;
        this.subscriptionPlan = subscriptionPlan;
    }

    public Tenant(String name, String databaseName) {
        this.name = name;
        this.databaseName = databaseName;
    }

    public String getTenantKey() {
        return tenantKey;
    }

    public void setTenantKey(String tenantKey) {
        this.tenantKey = tenantKey;
    }

    public SubscriptionPlan getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }

    public String getName() {
        return name;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", databaseName='" + databaseName + '\'' +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}
