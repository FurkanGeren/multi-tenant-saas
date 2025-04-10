package org.saas.order.service;

import org.saas.core.domain.Order;

public interface InvoiceService {
    void generateInvoiceForOrder(Long orderId);
}
