package com.asellion.product.controller;

import com.asellion.product.exception.ProductNotFoundException;
import com.asellion.product.mapper.ProductDataMapper;
import com.asellion.product.model.dto.ProductDto;
import com.asellion.product.model.dto.ProductRequest;
import com.asellion.product.model.dto.ProductResponse;
import com.asellion.product.model.entity.Product;
import com.asellion.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * ProductController -
 *
 * @author Mehdi Shahdoost
 * @version 2020/1/26
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/products")
@Slf4j
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductDataMapper mapper;

	/**
	 * Returns list of {@link ProductDto}
	 *
	 * @return ProductResponse
	 */
	@GetMapping
	public ResponseEntity<ProductResponse> getProducts() {
		log.debug("Returns list of products");
		List<ProductDto> productDtoList = mapper.toProductDtoList(productService.getProducts());
		ProductResponse productResponse = new ProductResponse();
		productResponse.setProducts(productDtoList);
		log.debug("Returns list of products: {}", productResponse);
		return ResponseEntity.ok(productResponse);
	}

	/**
	 * Returns {@link ProductResponse} by given product's id.
	 *
	 * @param id id of {@link com.asellion.product.model.entity.Product}
	 * @return ProductResponse
	 * @throws ProductNotFoundException
	 */
	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> getProduct(@PathVariable("id") Long id) throws ProductNotFoundException {
		log.info("Get product info id: {}", id);
		ProductDto productDto = mapper.toProductDto(productService.getProduct(id));
		ProductResponse productResponse = new ProductResponse();
		productResponse.setProducts(Collections.singletonList(productDto));
		log.info("Returns reponse for client: {}", productResponse);
		return ResponseEntity.ok(productResponse);
	}

	/**
	 * Updates {@link com.asellion.product.model.entity.Product} by given id.
	 *
	 * @param id
	 * @param request
	 * @return ProductResponse
	 * @throws ProductNotFoundException
	 */
	@PutMapping("/{id}")
	public ResponseEntity<ProductResponse> updateProduct(@PathVariable("id") Long id, @RequestBody
			ProductRequest request) throws ProductNotFoundException {
		log.info("Update product for product's id: {}", id);
		ProductDto productDto = mapper.toProductDto(productService.updateProduct(mapper.toProduct(request.getProduct())));
		ProductResponse productResponse = new ProductResponse();
		productResponse.setProducts(Collections.singletonList(productDto));
		log.info("Updated product: {}", productDto);
		return ResponseEntity.ok(productResponse);
	}

	/**
	 * Creates product
	 *
	 * @param request
	 * @return ProductResponse
	 * @throws ProductNotFoundException
	 */
	@PostMapping
	public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {
		Product product = productService.createProduct(mapper.toProduct(request.getProduct()));
		ProductResponse productResponse = new ProductResponse();
		productResponse.setProducts(Collections.singletonList(mapper.toProductDto(product)));
		log.info("create product: {}", productResponse.getProducts());
		return ResponseEntity.ok(productResponse);
	}

}
