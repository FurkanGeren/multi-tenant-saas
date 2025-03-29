package org.saas.core.domain;

public record SubscriptionInfo(
        String tenantName,
        String schema,
        int maxUsers
) {}
