package org.saas.core.tenant;

import org.saas.core.domain.SubscriptionInfo;

public interface TenantInfoProvider {
    SubscriptionInfo getCurrentTenantInfo();
}