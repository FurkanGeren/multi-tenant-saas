package org.saas.tenant.config;


import jakarta.annotation.PostConstruct;
import org.saas.core.domain.enums.SubscriptionPlanType;
import org.saas.tenant.entity.SubscriptionPlan;
import org.saas.tenant.repository.SubscriptionPlanRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubscriptionPlanSeeder {

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    public SubscriptionPlanSeeder(SubscriptionPlanRepository subscriptionPlanRepository) {
        this.subscriptionPlanRepository = subscriptionPlanRepository;
    }

    @PostConstruct
    public void seedPlans() {
        if (subscriptionPlanRepository.count() == 0) {
            subscriptionPlanRepository.saveAll(List.of(
                    new SubscriptionPlan(SubscriptionPlanType.STARTER,  5, false, false, false, false, false, false),
                    new SubscriptionPlan(SubscriptionPlanType.PROFESSIONAL,  100, true, true, false, true, true, true),
                    new SubscriptionPlan(SubscriptionPlanType.ENTERPRISE,  Integer.MAX_VALUE, true, true, true, true, true, true)
            ));
        }
    }
}