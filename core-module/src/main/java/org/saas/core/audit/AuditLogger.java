package org.saas.core.audit;


import org.saas.core.config.RabbitMQConfig;
import org.saas.core.context.TenantContext;
import org.saas.core.event.AuditEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class AuditLogger {

    private final RabbitTemplate rabbitTemplate;

    public AuditLogger(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        System.out.println("🔥 Injected RabbitTemplate: " + rabbitTemplate.getMessageConverter().getClass().getName());
    }
    public void log(String actor, String action, String resource, String details, String tenantSchema) {
        AuditEvent event = new AuditEvent(actor, action, resource, details, tenantSchema);
        System.out.println("Current message converter: " + rabbitTemplate.getMessageConverter().getClass().getName());
        rabbitTemplate.convertAndSend(RabbitMQConfig.AUDIT_QUEUE, event);
    }
}