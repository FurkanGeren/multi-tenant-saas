package org.saas.order.dto;

import java.math.BigDecimal;

public record OrderItemRequest(
        Long productId,
        String productNameSnapshot,
        BigDecimal priceSnapshot,
        int quantity
) {
}
