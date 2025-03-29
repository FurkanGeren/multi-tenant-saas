package org.saas.user.multitenancy;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.saas.core.tenant.TenantContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TenantInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String schema = request.getHeader("X-Tenant-ID");
        if (schema != null && !schema.isEmpty()) {
            TenantContext.setTenantSchema(schema);
        } else {
            throw new RuntimeException("Tenant bilgisi header'da bulunamadı!");
        }
        return true;
    }
}