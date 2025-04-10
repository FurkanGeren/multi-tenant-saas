package org.saas.order.event;


public record OrderCreatedEvent(Long orderId, String schema) {

}
