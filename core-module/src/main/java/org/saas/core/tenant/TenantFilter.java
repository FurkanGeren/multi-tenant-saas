package org.saas.core.tenant;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TenantFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest http = (HttpServletRequest) request;
        String tenant = http.getHeader("X-Tenant-ID");

        if (tenant != null && !tenant.trim().isEmpty()) {
            TenantContext.setTenantSchema(tenant);
        } else {
            TenantContext.setTenantSchema("public");
        }

        try {
            chain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}