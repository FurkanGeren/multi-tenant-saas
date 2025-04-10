package org.saas.order.service.impl;

import org.saas.core.context.ActorContext;
import org.saas.core.context.TenantContext;
import org.saas.core.domain.Order;
import org.saas.core.domain.OrderItem;
import org.saas.core.domain.enums.OrderStatus;
import org.saas.core.exception.BusinessException;
import org.saas.core.utils.JwtUtil;
import org.saas.order.event.OrderCreatedEvent;
import org.saas.order.dto.CreateOrderRequest;
import org.saas.order.dto.OrderResponse;
import org.saas.order.mapper.OrderMapper;
import org.saas.order.repository.OrderRepository;
import org.saas.order.service.OrderService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;


@Service
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final JwtUtil jwtUtil;
    private final ApplicationEventPublisher eventPublisher;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, JwtUtil jwtUtil, ApplicationEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.jwtUtil = jwtUtil;
        this.eventPublisher = eventPublisher;
    }


    @Override
    @Transactional
    public Long create(CreateOrderRequest request) {
        setTenantSchema();

        Order order = new Order();
        order.setOrderCode(generateOrderCode());
        order.setDescription(request.description());

        List<OrderItem> items = orderMapper.toEntityList(request.items());
        items.forEach(i -> i.setOrder(order));
        order.setItems(items);

        BigDecimal total = items.stream()
                .map(i -> i.getPriceSnapshot().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotal(total);

        ActorContext.setActor(jwtUtil.extractUsername());
        System.out.println("🧾 setActor çağrıldı: " + jwtUtil.extractUsername());

        orderRepository.save(order);

        eventPublisher.publishEvent(new OrderCreatedEvent(order.getId(), TenantContext.getTenantSchema()));

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
                getOrderById(id)
        );
    }

    @Override
    @Transactional
    public void markAsPaid(Long id) {
        setTenantSchema();
        Order order = getOrderById(id);

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
        Order order = getOrderById(id);

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new BusinessException("Sipariş zaten iptal edilmis.");
        }

        order.setStatus(OrderStatus.CANCELLED);
        order.setPaidAt(null);
        orderRepository.save(order);
    }

    private Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Sipariş bulunamadı")); // TODO
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
