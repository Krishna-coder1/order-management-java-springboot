package com.ccompanion.orderservice.controller;

import com.ccompanion.orderservice.model.Order;
import com.ccompanion.orderservice.model.OrderLineItems;
import com.ccompanion.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
@RequestMapping("/api/order")
public class OrderController {
@Autowired
OrderService orderService;
    @PostMapping
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name= "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody List<OrderLineItems> orderLineItems){
        return  CompletableFuture.supplyAsync(()->orderService.saveOrder(orderLineItems));
    }

    public CompletableFuture<String> fallbackMethod(List<OrderLineItems> orderLineItems, RuntimeException runtimeException){
       log.info("EXCEPTION_MESSAGE {}",runtimeException.getMessage());
        return CompletableFuture.supplyAsync(()->"Something went wrong, please try after sometime") ;
    }
}
