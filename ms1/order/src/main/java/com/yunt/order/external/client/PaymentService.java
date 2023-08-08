package com.yunt.order.external.client;

import com.yunt.order.model.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "Payment-Service/payment")
public interface PaymentService {

    @PostMapping
    public ResponseEntity<Long> doPayment(PaymentRequest paymentRequest);
}
