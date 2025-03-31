package org.saas.core.audit;


import org.saas.core.config.RabbitMQConfig;
import org.saas.core.event.AuditEvent;
import org.saas.core.tenant.TenantContext;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class AuditLogger {

    private final RabbitTemplate rabbitTemplate;

    public AuditLogger(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void log(String actor, String action, String resource, String details, String tenantSchema) {
        AuditEvent event = new AuditEvent(actor, action, resource, details, tenantSchema);
        rabbitTemplate.convertAndSend(RabbitMQConfig.AUDIT_QUEUE, event);
    }
}