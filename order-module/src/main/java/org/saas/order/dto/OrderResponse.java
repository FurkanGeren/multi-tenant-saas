package org.saas.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        String orderCode,
        BigDecimal total,
        String status,
        LocalDateTime createdAt,
        LocalDateTime paidAt,
        String description,
        List<OrderItemResponse> items

) {
}
