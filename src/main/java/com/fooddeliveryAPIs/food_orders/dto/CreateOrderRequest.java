package com.fooddeliveryAPIs.food_orders.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class CreateOrderRequest {
    @NotBlank
    private String customerName;

    @NotNull @Size(min = 1)
    @Valid
    private List<OrderItemRequest> items;

    @NotNull @PositiveOrZero
    private BigDecimal totalAmount;

    @NotNull
    private OffsetDateTime orderTime;

    private String idempotencyKey;

    // getters/setters
    public String getCustomerName(){return customerName;}
    public void setCustomerName(String customerName){this.customerName=customerName;}
    public List<OrderItemRequest> getItems(){return items;}
    public void setItems(List<OrderItemRequest> items){this.items=items;}
    public BigDecimal getTotalAmount(){return totalAmount;}
    public void setTotalAmount(BigDecimal totalAmount){this.totalAmount=totalAmount;}
    public OffsetDateTime getOrderTime(){return orderTime;}
    public void setOrderTime(OffsetDateTime orderTime){this.orderTime=orderTime;}
    public String getIdempotencyKey(){return idempotencyKey;}
    public void setIdempotencyKey(String idempotencyKey){this.idempotencyKey=idempotencyKey;}
}
