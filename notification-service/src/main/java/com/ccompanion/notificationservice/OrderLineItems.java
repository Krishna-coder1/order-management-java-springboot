package com.ccompanion.notificationservice;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderLineItems {

    private Long id;

    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
