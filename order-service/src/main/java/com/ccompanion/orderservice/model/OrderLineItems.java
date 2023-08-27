package com.ccompanion.orderservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "t_order_line_items",indexes = {@Index(name="sku_index", columnList ="sku_code", unique = false )})
public class OrderLineItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="sku_code")
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
