package com.fooddeliveryAPIs.food_orders.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="item_name", nullable = false)
    private String itemName;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name="unit_price", precision = 12, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;

    // Getters & setters
    // ... (generate via Lombok or IDE)
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public String getItemName(){return itemName;}
    public void setItemName(String itemName){this.itemName=itemName;}
    public Integer getQuantity(){return quantity;}
    public void setQuantity(Integer quantity){this.quantity=quantity;}
    public java.math.BigDecimal getUnitPrice(){return unitPrice;}
    public void setUnitPrice(java.math.BigDecimal unitPrice){this.unitPrice=unitPrice;}
    public Order getOrder(){return order;}
    public void setOrder(Order order){this.order=order;}
}
