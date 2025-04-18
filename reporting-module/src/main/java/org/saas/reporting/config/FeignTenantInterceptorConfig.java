package org.saas.reporting.config;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.saas.core.context.TenantContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignTenantInterceptorConfig {

    @Bean
    public RequestInterceptor tenantRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                String tenant = TenantContext.getTenantSchema();
                if (tenant != null) {
                    template.header("X-Tenant-ID", tenant);
                    System.out.println("📤 Feign ile header gönderiliyor → X-Tenant-ID: " + tenant);
                }
            }
        };
    }
}