package org.saas.order.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long productId,
        String productNameSnapshot,
        BigDecimal priceSnapshot,
        int quantity
) {
}
