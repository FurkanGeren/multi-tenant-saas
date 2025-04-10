package org.saas.order.service;

import org.saas.core.domain.Order;
import org.saas.order.dto.InvoiceResponse;

import java.util.List;

public interface InvoiceService {
    void generateInvoiceForOrder(Long orderId);

    void markAsPaid(Long id);

    void cancelInvoice(Long id, String reason);

    List<InvoiceResponse> getAll();

    InvoiceResponse getById(Long id);
}
