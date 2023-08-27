package com.ccompanion.inventoryservice.controller;

import com.ccompanion.inventoryservice.response.CCompanionResponse;
import com.ccompanion.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    InventoryService inventoryService;
    @GetMapping("/{sku-code}")
    public ResponseEntity isInStock(@PathVariable("sku-code") String skuCode) throws InterruptedException {
    return CCompanionResponse.successResponse(inventoryService.isInStock(skuCode));
    }
}
