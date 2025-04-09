package org.saas.core.audit;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.saas.core.annotation.Auditable;
import org.saas.core.context.ActorContext;
import org.saas.core.context.TenantContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class AuditAspect {

    private final AuditLogger auditLogger;

    public AuditAspect(AuditLogger auditLogger) {
        this.auditLogger = auditLogger;
    }



    @AfterReturning(pointcut = "@annotation(org.saas.core.annotation.Auditable)")
    public void logAudit(JoinPoint joinPoint) {
        System.out.println("🚨 AuditAspect tetiklendi");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Auditable auditable = method.getAnnotation(Auditable.class);

        if (auditable != null) {
            String actor =  ActorContext.getActor() != null ? ActorContext.getActor() : getCurrentActor();
            String action = auditable.action();
            String resource = auditable.resource();

            String schema = TenantContext.getTenantSchema();


            System.out.println("🎯 Logging audit: actor=" + actor + ", action=" + auditable.action() +
                    ", resource=" + auditable.resource() + ", tenant=" + schema);

            auditLogger.log(actor, action, resource, "", schema);
        }

    }

    private String getCurrentActor() {
        return "SYSTEM";
    }

}
