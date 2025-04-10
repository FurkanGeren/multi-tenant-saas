package org.saas.order.dto;

import org.saas.core.domain.enums.InvoiceStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record InvoiceResponse(
        Long id,
        String invoiceNumber,
        Long orderId,
        BigDecimal total,
        LocalDate issueDate,
        LocalDate dueDate,
        InvoiceStatus status,
        String notes,
        List<InvoiceItemResponse> items
) {
}
