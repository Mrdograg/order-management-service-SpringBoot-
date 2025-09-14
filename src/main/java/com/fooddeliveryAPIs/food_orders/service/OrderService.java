package com.fooddeliveryAPIs.food_orders.service;

import com.fooddeliveryAPIs.food_orders.dto.CreateOrderRequest;
import com.fooddeliveryAPIs.food_orders.dto.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponse createOrder(CreateOrderRequest req);
    OrderResponse getOrder(Long id);
    Page<OrderResponse> listOrders(Pageable pageable);
    OrderResponse updateOrderStatus(Long id, String status);

}
