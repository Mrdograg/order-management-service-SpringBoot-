package com.fooddeliveryAPIs.food_orders.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class OrderItemRequest {
    @NotBlank
    private String itemName;

    @NotNull @Min(1)
    private Integer quantity;

    @NotNull
    private BigDecimal unitPrice;

    // getters/setters
    public String getItemName(){return itemName;}
    public void setItemName(String itemName){this.itemName=itemName;}
    public Integer getQuantity(){return quantity;}
    public void setQuantity(Integer quantity){this.quantity=quantity;}
    public BigDecimal getUnitPrice(){return unitPrice;}
    public void setUnitPrice(BigDecimal unitPrice){this.unitPrice=unitPrice;}
}
