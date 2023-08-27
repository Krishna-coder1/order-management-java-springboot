package com.ccompanion.productservice.repository;

import com.ccompanion.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product,String> {
    
}
