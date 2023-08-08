package com.yunt.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name ="order_details")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "product_id")
    private long productId;
    @Column(name = "quantity")
    private long quantity;

    @Column(name = "order_date")
    private Instant orderDate;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "total_amount")
    private long amount;



}
