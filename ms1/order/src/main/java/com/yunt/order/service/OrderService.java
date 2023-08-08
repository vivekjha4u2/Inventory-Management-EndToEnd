package com.yunt.order.service;

import com.yunt.order.model.OrderRequest;
import com.yunt.order.model.OrderResponse;
import org.springframework.stereotype.Service;


public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
}
