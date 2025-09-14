package com.fooddeliveryAPIs.food_orders.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    // Getters & setters (or use Lombok)
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="customer_name", nullable = false)
    private String customerName;

    @Column(name="total_amount", precision = 12, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    @Column(name="order_time", nullable = false)
    private OffsetDateTime orderTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

    @Column(name = "idempotency_key", unique = true)
    private String idempotencyKey;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @Column(name="created_at", insertable = false, updatable = false)
    private java.sql.Timestamp createdAt;

    @Column(name="updated_at", insertable = false, updatable = false)
    private java.sql.Timestamp updatedAt;

    public void setId(Long id){this.id=id;}
    public String getCustomerName(){return customerName;}
    public void setCustomerName(String customerName){this.customerName=customerName;}
    public java.math.BigDecimal getTotalAmount(){return totalAmount;}
    public void setTotalAmount(java.math.BigDecimal totalAmount){this.totalAmount=totalAmount;}
    public OffsetDateTime getOrderTime(){return orderTime;}
    public void setOrderTime(OffsetDateTime orderTime){this.orderTime=orderTime;}
    public OrderStatus getStatus(){return status;}
    public void setStatus(OrderStatus status){this.status=status;}
    public String getIdempotencyKey(){return idempotencyKey;}
    public void setIdempotencyKey(String idempotencyKey){this.idempotencyKey=idempotencyKey;}
    public List<OrderItem> getItems(){return items;}
    public void setItems(List<OrderItem> items){
        this.items.clear();
        if(items!=null){
            items.forEach(i -> {
                i.setOrder(this);
                this.items.add(i);
            });
        }
    }
}
