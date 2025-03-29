package org.saas.tenant.service.impl;

import org.saas.core.domain.enums.SubscriptionPlanType;
import org.saas.tenant.entity.SubscriptionPlan;
import org.saas.tenant.repository.SubscriptionPlanRepository;
import org.saas.tenant.service.SubscriptionPlanService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    public SubscriptionPlanServiceImpl(SubscriptionPlanRepository subscriptionPlanRepository) {
        this.subscriptionPlanRepository = subscriptionPlanRepository;
    }

    @Override
    public SubscriptionPlan createSubscriptionPlan(SubscriptionPlanType type, int maxUsers, boolean hasAdvancedReporting,
                                                   boolean hasFullAuditLogging, boolean hasCustomIntegrations,
                                                   boolean hasApiAccess, boolean hasWebhookSupport,
                                                   boolean isSchemaIsolated) {
        SubscriptionPlan plan = new SubscriptionPlan(type, maxUsers, hasAdvancedReporting, hasFullAuditLogging,
                hasCustomIntegrations, hasApiAccess, hasWebhookSupport, isSchemaIsolated);
        return subscriptionPlanRepository.save(plan);
    }

    @Override
    public List<SubscriptionPlan> getAllSubscriptionPlans() {
        return subscriptionPlanRepository.findAll();
    }

    @Override
    public SubscriptionPlan getSubscriptionPlanById(Long id) {
        return subscriptionPlanRepository.findById(id).orElseThrow(() -> new RuntimeException("Plan not found"));
    }

}
