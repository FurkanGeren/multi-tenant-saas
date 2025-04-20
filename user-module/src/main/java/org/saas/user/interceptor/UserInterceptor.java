package org.saas.user.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.saas.core.context.TenantContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String tenant = request.getHeader("X-Tenant-ID");
        if (tenant != null) {
            TenantContext.setTenantSchema(tenant);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String tenant = TenantContext.getTenantSchema();
        System.out.println(tenant + "Girdi afterCompletion");
        TenantContext.clear();
        System.out.println(TenantContext.getTenantSchema() + "Girdi afterCompletion");
    }
}
