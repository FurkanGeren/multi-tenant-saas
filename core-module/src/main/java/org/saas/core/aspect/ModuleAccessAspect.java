package org.saas.core.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.saas.core.annotation.ModuleAccess;
import org.saas.core.config.ModuleAccessResolver;
import org.saas.core.domain.enums.ModuleType;
import org.saas.core.exception.BusinessException;
import org.saas.core.tenant.TenantContext;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ModuleAccessAspect {

    private final ModuleAccessResolver moduleAccessResolver;


    public ModuleAccessAspect(ModuleAccessResolver moduleAccessResolver) {
        this.moduleAccessResolver = moduleAccessResolver;
    }

    @Before("@annotation(moduleAccess)")
    public void checkModuleAccess(JoinPoint joinPoint, ModuleAccess moduleAccess) {
        if (moduleAccess == null) {
            moduleAccess = joinPoint.getTarget().getClass().getAnnotation(ModuleAccess.class);
        }

        ModuleType moduleType = moduleAccess.value();
        String tenantId = TenantContext.getTenantSchema();

        System.out.println("🔥 AOP Triggered — Tenant: " + tenantId + ", Module: " + moduleType);

        if (!moduleAccessResolver.isModuleEnabledForTenant(tenantId, moduleType)) {
            throw new BusinessException("Tenant '" + tenantId + "' has no access to module: " + moduleType.name());
        }
    }
}
