package org.saas.order.messaging;

import org.saas.core.context.TenantContext;
import org.saas.core.domain.Invoice;
import org.saas.core.event.InvoiceReminderEvent;
import org.saas.core.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class InvoiceReminderPublisher {

    private final RabbitTemplate rabbitTemplate;

    public InvoiceReminderPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishReminder(Invoice invoice) {
        InvoiceReminderEvent event = new InvoiceReminderEvent();
        event.setInvoiceId(invoice.getId());
        event.setInvoiceNumber(invoice.getInvoiceNumber());
        event.setDueDate(invoice.getDueDate());
        event.setTotal(invoice.getTotal());
        event.setTenantSchema(TenantContext.getTenantSchema());

        rabbitTemplate.convertAndSend(RabbitMQConfig.NOTIFICATION_QUEUE, event);
    }
}