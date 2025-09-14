package com.fooddeliveryAPIs.food_orders.controller;

import com.fooddeliveryAPIs.food_orders.dto.CreateOrderRequest;
import com.fooddeliveryAPIs.food_orders.dto.OrderResponse;
import com.fooddeliveryAPIs.food_orders.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService svc;

    public OrderController(OrderService svc) {
        this.svc = svc;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@Valid @RequestBody CreateOrderRequest req) {
        OrderResponse res = svc.createOrder(req);
        return ResponseEntity.status(201).body(res);
    }

    @GetMapping
    public Page<OrderResponse> listOrders(Pageable pageable) {
        return svc.listOrders(pageable);
    }

    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable Long id) {
        return svc.getOrder(id);
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<String> getStatus(@PathVariable Long id) {
        OrderResponse r = svc.getOrder(id);
        return ResponseEntity.ok(r.getStatus().name());
    }

    @PatchMapping("/{id}/status")
    public OrderResponse updateStatus(@PathVariable Long id, @RequestParam String status) {
        return svc.updateOrderStatus(id, status);
    }
}
