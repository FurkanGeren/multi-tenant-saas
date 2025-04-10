package org.saas.tenant.service;



import org.saas.core.domain.SubscriptionInfo;
import org.saas.tenant.dto.TenantFromKeyResponse;
import org.saas.tenant.dto.TenantRequest;
import org.saas.tenant.dto.TenantResponse;
import org.saas.tenant.entity.SubscriptionPlan;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TenantService {
    TenantResponse createTenant(TenantRequest request);

    TenantResponse updateTenantSubscription(Long tenantId, Long subscriptionPlanId);

    List<TenantResponse> getAllTenants();

    List<String> getAllSchemas();

    TenantResponse getTenantById(Long id);

    void deleteTenant(Long id);

    SubscriptionInfo getTenantInfoBySchema(String schemaName);

    SubscriptionPlan getPlanForTenant(String tenantId);

    TenantFromKeyResponse getTenantNameFromKey(String key);
}
