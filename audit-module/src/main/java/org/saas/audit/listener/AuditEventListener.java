package org.saas.audit.listener;

import org.saas.core.config.RabbitMQConfig;
import org.saas.core.event.AuditEvent;
import org.saas.audit.service.AuditLogService;
import org.saas.core.tenant.TenantContext;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AuditEventListener {

    private final AuditLogService auditLogService;

    public AuditEventListener(AuditLogService auditLogService) {
        System.out.println("✅ AuditEventListener bean oluşturuldu");
        this.auditLogService = auditLogService;
    }

    @RabbitListener(queues = RabbitMQConfig.AUDIT_QUEUE)
    public void handleAuditEvent(AuditEvent event) {
        try {
            TenantContext.setTenantSchema(event.getTenantSchema()); // 💡 tenant'ı thread'e set et
            auditLogService.log(
                    event.getActor(),
                    event.getAction(),
                    event.getResource(),
                    event.getDetails()
            );
        } finally {
            TenantContext.clear(); // 💣 memory leak'i önlemek için her zaman temizle
        }
    }
}