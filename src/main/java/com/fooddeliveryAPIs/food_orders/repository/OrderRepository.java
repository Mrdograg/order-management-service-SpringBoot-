package com.fooddeliveryAPIs.food_orders.repository;

import com.fooddeliveryAPIs.food_orders.model.Order;
import com.fooddeliveryAPIs.food_orders.model.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByStatus(OrderStatus status, Pageable pageable);
}
