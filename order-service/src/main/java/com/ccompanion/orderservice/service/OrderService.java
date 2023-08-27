package com.ccompanion.orderservice.service;

import com.ccompanion.orderservice.dto.OrderDto;
import com.ccompanion.orderservice.model.Order;
import com.ccompanion.orderservice.model.OrderLineItems;
import com.ccompanion.orderservice.repository.OrderRepository;
import com.ccompanion.orderservice.response.CCompanionResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    WebClient.Builder webClientBuilder;

    public OrderService(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public String saveOrder(List<OrderLineItems> orderLineItems) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderLineItemsList(orderLineItems);
        String sku = orderLineItems.stream().map(val -> val.getSkuCode()).toList().get(0);
        log.info("Calling inventory service");
        CCompanionResponseData<Boolean> result = webClientBuilder.build().get().
                uri("http://inventory-service/api/inventory/" + sku).
                retrieve().bodyToMono(CCompanionResponseData.class).block();
        if (result.getBody() == true) {
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic", order.getOrderNumber() );
            log.info("Order {} has been placed", order.toString());
            return "Order placed successfully";}
         else {
            throw new IllegalArgumentException("Product is not in stock, please try later  ");
        }

    }

}
