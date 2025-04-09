package org.saas.order.service;

import org.saas.order.dto.CreateOrderRequest;
import org.saas.order.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    Long create(CreateOrderRequest request);

    List<OrderResponse> getAll();

    OrderResponse getById(Long id);
}
