package org.saas.order.controller;


import org.saas.core.annotation.Auditable;
import org.saas.core.annotation.ModuleAccess;
import org.saas.core.domain.enums.ModuleType;
import org.saas.order.dto.CancelInvoiceRequest;
import org.saas.order.dto.CreateOrderRequest;
import org.saas.order.dto.OrderResponse;
import org.saas.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {


    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    @Auditable(action = "CREATE", resource = "ORDER")
    @ModuleAccess(ModuleType.USER)
    public ResponseEntity<Void> create(@RequestBody CreateOrderRequest request) {
        Long orderId = orderService.create(request);
        return ResponseEntity
                .created(URI.create("/api/orders/" + orderId))
                .build();
    }

    @GetMapping("/get/all")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    @ModuleAccess(ModuleType.USER)
    public ResponseEntity<List<OrderResponse>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    @ModuleAccess(ModuleType.USER)
    public ResponseEntity<OrderResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @PatchMapping("/{id}/pay")
    @Auditable(action = "PAYMENT_CONFIRMED", resource = "ORDER")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    @ModuleAccess(ModuleType.USER)
    public ResponseEntity<Void> markAsPaid(@PathVariable Long id) {
        orderService.markAsPaid(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancel")
    @Auditable(action = "PAYMENT_CANCELLED", resource = "ORDER")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    @ModuleAccess(ModuleType.USER)
    public ResponseEntity<Void> markAsCancelled(@PathVariable Long id){
        orderService.markAsCancelled(id);
        return ResponseEntity.noContent().build();
    }
}

