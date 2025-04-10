package org.saas.order.service;

import org.saas.core.context.TenantContext;
import org.saas.order.event.OrderCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderCreatedListener {

    private final InvoiceService invoiceService;

    public OrderCreatedListener(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }


    @EventListener
    @Async
    public void handleOrderCreated(OrderCreatedEvent event) {
        try {
            TenantContext.setTenantSchema(event.schema());
            invoiceService.generateInvoiceForOrder(event.orderId());
        } finally {
            TenantContext.clear();
        }
    }
}
