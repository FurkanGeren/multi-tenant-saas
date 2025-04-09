package org.saas.order.mapper;

import org.mapstruct.Mapper;
import org.saas.core.domain.Order;
import org.saas.core.domain.OrderItem;
import org.saas.order.dto.OrderItemResponse;
import org.saas.order.dto.OrderResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderResponse toResponse(Order order);

    OrderItemResponse toResponse(OrderItem item);

    List<OrderItemResponse> toItemResponseList(List<OrderItem> items);
}