package com.yunt.order.service;

import com.yunt.order.entity.Order;
import com.yunt.order.exception.CustomException;
import com.yunt.order.external.client.PaymentService;
import com.yunt.order.external.client.ProductService;
import com.yunt.order.model.OrderRequest;
import com.yunt.order.model.OrderResponse;
import com.yunt.order.model.PaymentRequest;
import com.yunt.order.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Log4j2
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        log.info("Placing order...");
        productService.reduceProductQuantity(orderRequest.getProductId(), orderRequest.getQuantity());
        log.info("Creating order with status Created");
        Order order = Order.builder()
                .productId(orderRequest.getProductId())
                .amount(orderRequest.getTotalAmount())
                .quantity(orderRequest.getQuantity())
                .orderStatus("Created")
                .orderDate(Instant.now())
                .build();
        order = orderRepository.save(order);
        log.info("Order created with id: {}", order.getId());

        PaymentRequest paymentRequest =
                PaymentRequest.builder()
                        .orderId(order.getId())
                        .paymentMode(orderRequest.getPaymentMode())
                        .amount(orderRequest.getTotalAmount())
                        .build();
        String orderStatus = null;
        try{
            paymentService.doPayment(paymentRequest);
            log.info("Payment done successfully, Changing the order status to Placed");
            orderStatus = "Placed";
        }catch (Exception e){
            log.error("Error occurred in Payment, Changing the order status to Failed");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        log.info("Order placed successfully with id: {}", order.getId());

        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {
        log.info("Get order details for order id: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new CustomException("Order not found for given id",
                        "NOT_FOUND", 404));

        OrderResponse orderResponse = OrderResponse.builder()
                .orderId(orderId)
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .orderDate(order.getOrderDate())
                .build();
        return orderResponse;
    }
}
