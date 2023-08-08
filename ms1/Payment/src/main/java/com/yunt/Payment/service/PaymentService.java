package com.yunt.Payment.service;

import com.yunt.Payment.model.PaymentRequest;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);
}
