package org.saas.auth.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.saas.core.context.TenantContext;
import org.springframework.stereotype.Component;

@Component
public class TenantFeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        String schema = TenantContext.getTenantSchema();
        if (schema != null) {
            requestTemplate.header("X-Tenant-ID", schema);
        }
    }
}
