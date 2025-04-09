package org.saas.order.dto;

import java.util.List;

public record CreateOrderRequest(
        String description,
        List<OrderItemRequest> items
) {
}
