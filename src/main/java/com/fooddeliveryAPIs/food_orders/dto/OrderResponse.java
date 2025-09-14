package com.fooddeliveryAPIs.food_orders.dto;

import com.fooddeliveryAPIs.food_orders.model.OrderStatus;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class OrderResponse {
    private Long id;
    private String customerName;
    private List<OrderItemRequest> items; // reuse request DTO for simplicity
    private BigDecimal totalAmount;
    private OffsetDateTime orderTime;
    private OrderStatus status;

    // getters/setters
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public String getCustomerName(){return customerName;}
    public void setCustomerName(String customerName){this.customerName=customerName;}
    public List<OrderItemRequest> getItems(){return items;}
    public void setItems(List<OrderItemRequest> items){this.items=items;}
    public BigDecimal getTotalAmount(){return totalAmount;}
    public void setTotalAmount(BigDecimal totalAmount){this.totalAmount=totalAmount;}
    public OffsetDateTime getOrderTime(){return orderTime;}
    public void setOrderTime(OffsetDateTime orderTime){this.orderTime=orderTime;}
    public OrderStatus getStatus(){return status;}
    public void setStatus(OrderStatus status){this.status=status;}
}
