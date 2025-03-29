package org.saas.tenant.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.saas.core.annotation.ModuleAccess;
import org.saas.core.domain.enums.ModuleType;
import org.saas.core.security.ModuleAccessResolver;
import org.springframework.stereotype.Component;

import java.util.Set;

@Aspect
@Component
public class ModuleAccessAspect{

    private final ModuleAccessResolver moduleAccessResolver;

    public ModuleAccessAspect(ModuleAccessResolver moduleAccessResolver) {
        this.moduleAccessResolver = moduleAccessResolver;
    }

    @Before("@annotation(org.saas.core.annotation.ModuleAccess)")
    public void checkAccess(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        ModuleAccess moduleAccess = signature.getMethod().getAnnotation(ModuleAccess.class);

        ModuleType required = moduleAccess.value();
        Set<ModuleType> allowed = moduleAccessResolver.getAccessibleModulesForCurrentTenant();

        if (!allowed.contains(required)) {
            throw new RuntimeException("Bu modüle erişim yetkiniz yok: " + required);
        }
    }
}
