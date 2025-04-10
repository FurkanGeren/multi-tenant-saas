package org.saas.order.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "invoice-module", url = "http://localhost:8080")
public interface InvoiceModuleClient {

    @GetMapping("/api/internal/tenants/schemas")
    List<String> getTenantSchemas();
}
