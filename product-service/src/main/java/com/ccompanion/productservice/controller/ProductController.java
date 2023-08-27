package com.ccompanion.productservice.controller;

import com.ccompanion.productservice.dto.ProductDto;
import com.ccompanion.productservice.response.CCompanionResponse;
import com.ccompanion.productservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("/api/product")
public class ProductController{
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public  ResponseEntity createProduct(@RequestBody ProductDto productDto){
        if(productDto.getName() ==null||productDto.getPrice() ==null){
            return CCompanionResponse.failedResponse("Please add all the required fields");
        }
      ProductDto createdProduct = productService.createProduct(productDto);
        return CCompanionResponse.successResponse(createdProduct);
    }

    @GetMapping
    public ResponseEntity getAllProducts(){
        return  CCompanionResponse.successResponse(productService.getAllProducts());
    }

}