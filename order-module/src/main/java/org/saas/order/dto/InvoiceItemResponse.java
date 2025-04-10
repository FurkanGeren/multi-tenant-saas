package org.saas.order.dto;

import java.math.BigDecimal;

public record InvoiceItemResponse(
        String productName,
        int quantity,
        BigDecimal price
) {
}
