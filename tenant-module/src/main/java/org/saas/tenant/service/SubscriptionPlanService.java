package org.saas.tenant.service;

import org.saas.core.domain.enums.SubscriptionPlanType;
import org.saas.tenant.entity.SubscriptionPlan;

import java.util.List;

public interface SubscriptionPlanService {
    SubscriptionPlan createSubscriptionPlan(SubscriptionPlanType type, int maxUsers, boolean hasAdvancedReporting,
                                            boolean hasFullAuditLogging, boolean hasCustomIntegrations,
                                            boolean hasApiAccess, boolean hasWebhookSupport,
                                            boolean isSchemaIsolated);

    List<SubscriptionPlan> getAllSubscriptionPlans();

    SubscriptionPlan getSubscriptionPlanById(Long id);
}
