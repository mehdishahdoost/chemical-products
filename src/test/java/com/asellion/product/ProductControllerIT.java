package com.asellion.product;

import com.asellion.product.model.dto.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Date;

/**
 * ProductControllerIT -
 *
 * @author Mehdi Shahdoost
 * @version 2020/1/26
 * @since 1.0.0
 */
@SpringBootTest(classes = ProductAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIT {

	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void getProducts_successful() {
		// Creates JWT token with calling authenticate
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUsername("asellion");
		authRequest.setPassword("123456");
		ResponseEntity<AuthResponse> tokenResponse = this.restTemplate.postForEntity(
				"http://localhost:" + port + "/api/authenticate", authRequest, AuthResponse.class);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + tokenResponse.getBody().getToken());
		ResponseEntity<ProductResponse> response = this.restTemplate.exchange("http://localhost:" + port +
				"/api/products",HttpMethod.GET, new HttpEntity<Object>(headers), ProductResponse.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertEquals(response.getBody().getProducts().size(), 6);
	}


	@Test
	public void getProduct_successful() {
		// Creates JWT token with calling authenticate
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUsername("asellion");
		authRequest.setPassword("123456");
		ResponseEntity<AuthResponse> tokenResponse = this.restTemplate.postForEntity(
				"http://localhost:" + port + "/api/authenticate", authRequest, AuthResponse.class);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + tokenResponse.getBody().getToken());
		ResponseEntity<ProductResponse> response = this.restTemplate.exchange("http://localhost:" + port +
				"/api/products/1",HttpMethod.GET, new HttpEntity<Object>(headers), ProductResponse.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertEquals(response.getBody().getProducts().size(), 1);
		Assertions.assertEquals(response.getBody().getProducts().get(0).getName(), "2-Aminoindan");
	}

	@Test
	public void getProduct_exception() {
		// Creates JWT token with calling authenticate
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUsername("asellion");
		authRequest.setPassword("123456");
		ResponseEntity<AuthResponse> tokenResponse = this.restTemplate.postForEntity(
				"http://localhost:" + port + "/api/authenticate", authRequest, AuthResponse.class);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + tokenResponse.getBody().getToken());
		ResponseEntity<ProductResponse> response = this.restTemplate.exchange("http://localhost:" + port +
				"/api/products/10",HttpMethod.GET, new HttpEntity<Object>(headers), ProductResponse.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertEquals(response.getBody().getProducts(), null);
		Assertions.assertEquals(response.getBody().getMessage(), "Product not found!");
	}

	@Test
	public void updateProduct() {
		// Creates JWT token with calling authenticate
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUsername("asellion");
		authRequest.setPassword("123456");
		ResponseEntity<AuthResponse> tokenResponse = this.restTemplate.postForEntity(
				"http://localhost:" + port + "/api/authenticate", authRequest, AuthResponse.class);

		ProductDto productDto = new ProductDto(2L, "update", "20", new Date());
		ProductRequest productRequest = new ProductRequest(productDto);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + tokenResponse.getBody().getToken());
		HttpEntity<ProductRequest> entity = new HttpEntity<ProductRequest>(productRequest, headers);
		restTemplate.put("http://localhost:" + port + "/api/products/2", entity);
		ResponseEntity<ProductResponse> response = restTemplate.exchange("http://localhost:" +
				port + "/api/products/2",HttpMethod.GET,new HttpEntity<Object>(headers), ProductResponse.class,
				headers);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertEquals(response.getBody().getProducts().size(), 1);
		Assertions.assertEquals(response.getBody().getProducts().get(0).getName(), "update");
	}

	@Test
	public void createProduct() {
		// Creates JWT token with calling authenticate
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUsername("asellion");
		authRequest.setPassword("123456");
		ResponseEntity<AuthResponse> tokenResponse = this.restTemplate.postForEntity(
				"http://localhost:" + port + "/api/authenticate", authRequest, AuthResponse.class);

		ProductDto productDto = new ProductDto(2L, "update", "20", new Date());
		ProductRequest productRequest = new ProductRequest(productDto);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + tokenResponse.getBody().getToken());
		HttpEntity<ProductRequest> entity = new HttpEntity<ProductRequest>(productRequest, headers);
		ResponseEntity<ProductResponse> response = restTemplate.postForEntity("http://localhost:" + port + "/api" +
				"/products", entity, ProductResponse.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertEquals(response.getBody().getProducts().size(), 1);
		Assertions.assertEquals(response.getBody().getProducts().get(0).getId(), 7L);
	}

}
