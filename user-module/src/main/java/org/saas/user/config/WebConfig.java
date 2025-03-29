package org.saas.user.config;


import org.saas.core.interceptor.TenantInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// core-module/src/main/java/org/saas/core/config/WebConfig.java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final TenantInterceptor tenantInterceptor;

    public WebConfig(TenantInterceptor tenantInterceptor) {
        this.tenantInterceptor = tenantInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tenantInterceptor)
                .addPathPatterns("/api/tenants/internal/**");
    }

}