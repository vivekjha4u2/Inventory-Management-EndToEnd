package com.yunt.Payment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "transaction_details")
public class TransactionDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "order_id")
    private long orderId;
    @Column(name = "payment_mode")
    private String paymentMode;
    @Column(name = "reference_number")
    private String referenceNumber;
    @Column(name = "payment_date")
    private Instant paymentDate;
    @Column(name = "payment_status")
    private String paymentStatus;
    @Column(name = "amount")
    private long amount;
}
