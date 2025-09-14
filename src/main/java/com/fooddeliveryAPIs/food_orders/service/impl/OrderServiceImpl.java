package com.fooddeliveryAPIs.food_orders.service.impl;

import com.fooddeliveryAPIs.food_orders.dto.CreateOrderRequest;
import com.fooddeliveryAPIs.food_orders.dto.OrderItemRequest;
import com.fooddeliveryAPIs.food_orders.dto.OrderResponse;
import com.fooddeliveryAPIs.food_orders.model.Order;
import com.fooddeliveryAPIs.food_orders.model.OrderItem;
import com.fooddeliveryAPIs.food_orders.model.OrderStatus;
import com.fooddeliveryAPIs.food_orders.repository.OrderRepository;
import com.fooddeliveryAPIs.food_orders.sqs.OrderProducer;
import com.fooddeliveryAPIs.food_orders.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    public OrderServiceImpl(OrderRepository orderRepository, OrderProducer orderProducer) {
        this.orderRepository = orderRepository;
        this.orderProducer = orderProducer;
    }

    @Override
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest req) {
        // validate total matches sum(items)
        BigDecimal calculated = req.getItems().stream()
                .map(i -> i.getUnitPrice().multiply(new BigDecimal(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (calculated.compareTo(req.getTotalAmount()) != 0) {
            throw new IllegalArgumentException("total_amount must equal sum(items.quantity * unit_price)");
        }

        Order order = new Order();
        order.setCustomerName(req.getCustomerName());
        order.setTotalAmount(req.getTotalAmount());
        order.setOrderTime(req.getOrderTime());
        order.setIdempotencyKey(req.getIdempotencyKey());

        // map items
        order.setItems(req.getItems().stream().map(i -> {
            OrderItem oi = new OrderItem();
            oi.setItemName(i.getItemName());
            oi.setQuantity(i.getQuantity());
            oi.setUnitPrice(i.getUnitPrice());
            return oi;
        }).collect(Collectors.toList()));

        order.setStatus(OrderStatus.PENDING);
        Order saved = orderRepository.save(order);

        // publish to queue (send minimal payload)
        orderProducer.sendOrderId(saved.getId());

        return toResponse(saved);
    }

    @Override
    public OrderResponse getOrder(Long id) {
        Order o = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return toResponse(o);
    }

    @Override
    public Page<OrderResponse> listOrders(Pageable pageable) {
        Page<Order> page = orderRepository.findAll(pageable);
        return page.map(this::toResponse);
    }

    @Override
    @Transactional
    public OrderResponse updateOrderStatus(Long id, String statusStr) {
        Order o = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        OrderStatus status = OrderStatus.valueOf(statusStr);
        o.setStatus(status);
        Order saved = orderRepository.save(o);
        return toResponse(saved);
    }

    private OrderResponse toResponse(Order o) {
        OrderResponse r = new OrderResponse();
        r.setId(o.getId());
        r.setCustomerName(o.getCustomerName());
        r.setOrderTime(o.getOrderTime());
        r.setTotalAmount(o.getTotalAmount());
        r.setStatus(o.getStatus());
        r.setItems(o.getItems().stream().map(i -> {
            OrderItemRequest dto = new OrderItemRequest();
            dto.setItemName(i.getItemName());
            dto.setQuantity(i.getQuantity());
            dto.setUnitPrice(i.getUnitPrice());
            return dto;
        }).collect(Collectors.toList()));
        return r;
    }
}
