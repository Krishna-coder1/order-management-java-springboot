package com.ccompanion.productservice.service;

import com.ccompanion.productservice.dto.ProductDto;
import com.ccompanion.productservice.model.Product;
import com.ccompanion.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public ProductDto createProduct(ProductDto productDto) {
        Product product = Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product {} is saved", product.getId());

        ProductDto savedProduct = ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();

        return savedProduct;
    }

    public Page<ProductDto> getAllProducts() {
        int pageNumber = 0; // Page number, starting from 0
        int pageSize = 10;  // Number of items per page
        Sort sort = Sort.by(Sort.Direction.ASC, "name"); // Sort by name in ascending order
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(this::mapToProductDto);
    }

    private ProductDto mapToProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
