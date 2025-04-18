package org.saas.reporting.service;

import org.saas.core.event.InvoiceReminderEvent;

public interface NotificationService {
    void sendInvoiceNotification(InvoiceReminderEvent event);
}
