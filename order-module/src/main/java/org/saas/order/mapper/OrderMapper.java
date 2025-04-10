package org.saas.order.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.saas.core.domain.Invoice;
import org.saas.core.domain.Order;
import org.saas.core.domain.OrderItem;
import org.saas.order.dto.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderResponse toResponse(Order order);

    OrderItemResponse toResponse(OrderItem item);

    List<OrderItemResponse> toItemResponseList(List<OrderItem> items);

    OrderItem toEntity(OrderItemRequest request);

    List<OrderItem> toEntityList(List<OrderItemRequest> requests);

    @Mapping(source = "order.id", target = "orderId")
    @Mapping(target = "items", expression = "java(mapOrderItems(invoice))")
    InvoiceResponse toResponse(Invoice invoice);

    default List<InvoiceItemResponse> mapOrderItems(Invoice invoice) {
        if (invoice.getOrder() == null || invoice.getOrder().getItems() == null) return List.of();

        OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

        return invoice.getOrder().getItems().stream()
                .map(item -> new InvoiceItemResponse(
                        item.getProductNameSnapshot(),
                        item.getQuantity(),
                        item.getPriceSnapshot()
                )).toList();
    }

}