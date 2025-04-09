package org.saas.order.service.impl;

import org.saas.core.context.ActorContext;
import org.saas.core.context.TenantContext;
import org.saas.core.domain.Order;
import org.saas.core.domain.OrderItem;
import org.saas.core.domain.enums.OrderStatus;
import org.saas.core.exception.BusinessException;
import org.saas.core.utils.JwtUtil;
import org.saas.order.dto.CreateOrderRequest;
import org.saas.order.dto.OrderResponse;
import org.saas.order.mapper.OrderMapper;
import org.saas.order.repository.OrderRepository;
import org.saas.order.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static org.saas.core.context.TenantContext.setTenantSchema;

@Service
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final JwtUtil jwtUtil;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, JwtUtil jwtUtil) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.jwtUtil = jwtUtil;
    }


    @Override
    @Transactional
    public Long create(CreateOrderRequest request) {
        setTenantSchema();

        Order order = new Order();
        order.setOrderCode(generateOrderCode());
        order.setDescription(request.description());

        List<OrderItem> items = request.items().stream().map(item -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(item.productId());
            orderItem.setProductNameSnapshot(item.productNameSnapshot());
            orderItem.setPriceSnapshot(item.priceSnapshot());
            orderItem.setQuantity(item.quantity());
            orderItem.setOrder(order);
            return orderItem;
        }).toList();

        order.setItems(items);

        BigDecimal total = items.stream()
                .map(i -> i.getPriceSnapshot().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotal(total);

        ActorContext.setActor(jwtUtil.extractUsername());
        System.out.println("🧾 setActor çağrıldı: " + jwtUtil.extractUsername());

        orderRepository.save(order);
        return order.getId();
    }

    @Override
    public List<OrderResponse> getAll() {
        setTenantSchema();
        return orderRepository.findAll().stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Override
    public OrderResponse getById(Long id) {
        setTenantSchema();
        return orderMapper.toResponse(
                orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Hata")) // TODO
        );
    }

    @Override
    @Transactional
    public void markAsPaid(Long id) {
        setTenantSchema();
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Sipariş bulunamadı")); // TODO

        if (order.getStatus() == OrderStatus.PAID) {
            throw new BusinessException("Sipariş zaten ödenmiş.");
        }

        order.setStatus(OrderStatus.PAID);
        order.setPaidAt(LocalDateTime.now());
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void markAsCancelled(Long id) {
        setTenantSchema();
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Sipariş bulunamadı")); // TODO

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new BusinessException("Sipariş zaten iptal edilmis.");
        }

        order.setStatus(OrderStatus.CANCELLED);
        order.setPaidAt(null);
        orderRepository.save(order);
    }


    private String generateOrderCode() {
        String datePart = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE); // 20250409
        String randomPart = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "ORD-" + datePart + "-" + randomPart;
    }


    // PRIVATE FUNC
    private void setTenantSchema() {
        String schema = TenantContext.getTenantSchema();
        if (schema == null) {
            throw new BusinessException("Tenant bilgisi bulunamadı.");
        }
        TenantContext.setTenantSchema(schema);
    }
}
