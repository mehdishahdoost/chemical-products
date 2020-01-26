package com.asellion.product.controller;

import com.asellion.product.mapper.ProductDataMapper;
import com.asellion.product.model.dto.ProductDto;
import com.asellion.product.model.dto.ProductResponse;
import com.asellion.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}