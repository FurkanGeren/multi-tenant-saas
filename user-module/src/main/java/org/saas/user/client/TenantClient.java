package org.saas.user.client;

import org.saas.core.domain.SubscriptionInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "tenantClient", url = "http://localhost:8080") // tenant-module'ın base URL’i
public interface TenantClient {

    @GetMapping("/api/tenants/internal/{schema}/info")
    SubscriptionInfo getTenantInfo(@PathVariable("schema") String schema);
}
