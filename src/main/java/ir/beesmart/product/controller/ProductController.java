package ir.beesmart.product.controller;

import ir.beesmart.product.exception.ProductNotFoundException;
import ir.beesmart.product.mapper.ProductDataMapper;
import ir.beesmart.product.model.dto.ProductDto;
import ir.beesmart.product.model.dto.ProductRequest;
import ir.beesmart.product.model.dto.ProductResponse;
import ir.beesmart.product.model.entity.Product;
import ir.beesmart.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
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

	private ProductService productService;
	private ProductDataMapper mapper;

	public ProductController(ProductService productService, ProductDataMapper mapper) {
		this.productService = productService;
		this.mapper = mapper;
	}

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
	 * @param id id of {@link Product}
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
	 * Updates {@link Product} by given id.
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
