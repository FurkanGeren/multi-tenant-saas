package org.saas.core.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.saas.core.annotation.ModuleAccess;
import org.saas.core.config.ModuleAccessResolverFeignClient;
import org.saas.core.domain.enums.ModuleType;
import org.saas.core.exception.BusinessException;
import org.saas.core.context.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ModuleAccessAspect {

    private final ModuleAccessResolverFeignClient moduleAccessResolver;

    @Autowired
    public ModuleAccessAspect(ModuleAccessResolverFeignClient moduleAccessResolver) {
        this.moduleAccessResolver = moduleAccessResolver;
    }

    @Before("@annotation(moduleAccess)")
    public void checkModuleAccess(JoinPoint joinPoint, ModuleAccess moduleAccess) {
        if (moduleAccess == null) {
            moduleAccess = joinPoint.getTarget().getClass().getAnnotation(ModuleAccess.class);
        }

        ModuleType moduleType = moduleAccess.value();
        String tenantId = TenantContext.getTenantSchema();

        System.out.println("🔥 AOP Triggered — Tenant: " + tenantId + ", Module: " + moduleType.name());

        if (!moduleAccessResolver.isModuleEnabledForTenant(tenantId, moduleType.name())) {
            throw new BusinessException("Tenant '" + tenantId + "' has no access to module: " + moduleType.name());
        }
    }
}
