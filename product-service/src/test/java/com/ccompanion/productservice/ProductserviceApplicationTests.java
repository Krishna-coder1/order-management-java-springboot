package com.ccompanion.productservice;
import com.ccompanion.productservice.model.Product;
import com.ccompanion.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.ccompanion.productservice.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductserviceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
	private ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private MockMvc mockMvc;
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
	}
	@Test
	void shouldCreateProduct() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product").contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(ProductDto.builder().name("Thumsup").price(BigDecimal.valueOf(204))
				.build()))).andExpect(status().isOk());
		Assertions.assertEquals(productRepository.findAll().size(), 1);
	}
	@Test
	void shouldFetchOnly10Products() throws Exception {
		for (int i = 1; i <= 25; i++) {
			Product product = new Product();
			product.setName("Product " + i);
			product.setPrice(BigDecimal.valueOf(200));
			productRepository.save(product);
		}

		mockMvc.perform(MockMvcRequestBuilders.get("/api/product"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.body.content.length()").value(10)) // Ensure there are 10 items in the response
				.andExpect(jsonPath("$.body.pageable.pageNumber").value(0)) // Ensure it's the first page (page number 0)
				.andExpect(jsonPath("$.body.pageable.pageSize").value(10)) // Ensure the page size is 10
				.andExpect(jsonPath("$.body.totalPages").value(3)) // Ensure there are 3 pages in total
				.andExpect(jsonPath("$.body.totalElements").value(25)) // Ensure there are 25 elements in total
				.andExpect(jsonPath("$.body.first").value(true)) // Ensure it's the first page
				.andExpect(jsonPath("$.body.last").value(false)); // Ensure it's not the last page
	}


}
