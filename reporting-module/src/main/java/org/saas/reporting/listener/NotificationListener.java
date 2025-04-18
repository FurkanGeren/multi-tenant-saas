package org.saas.reporting.listener;



import org.saas.core.config.RabbitMQConfig;
import org.saas.core.context.TenantContext;
import org.saas.core.event.InvoiceReminderEvent;
import org.saas.reporting.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    private final NotificationService notificationService;

    public NotificationListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_QUEUE)
    public void handleInvoiceReminder(InvoiceReminderEvent event) {
        try {
            TenantContext.setTenantSchema(event.getTenantSchema());
            notificationService.sendInvoiceNotification(event);
        } finally {
            TenantContext.clear();
        }
    }
}