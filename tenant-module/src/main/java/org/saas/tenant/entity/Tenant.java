package org.saas.tenant.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.saas.core.domain.Auditable;

@Entity
@Table(name = "tenants")
public class Tenant extends Auditable {

    @Column(name = "name",nullable = false, unique = true)
    private String name;

    @Column(name = "database_name", nullable = false, unique = true)
    private String databaseName;

    @ManyToOne
    @JoinColumn(name = "subscription_plan_id")
    private SubscriptionPlan subscriptionPlan;

    public Tenant() {}

    public Tenant(String name, String databaseName, SubscriptionPlan subscriptionPlan) {
        this.name = name;
        this.databaseName = databaseName;
        this.subscriptionPlan = subscriptionPlan;
    }

    public Tenant(String name, String databaseName) {
        this.name = name;
        this.databaseName = databaseName;
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
