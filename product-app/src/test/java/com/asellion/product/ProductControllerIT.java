package com.asellion.product;

import com.asellion.product.model.dto.ProductResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
		ResponseEntity<ProductResponse> response = this.restTemplate.getForEntity("http://localhost:" + port +
				"/api/products", ProductResponse.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertEquals(response.getBody().getProducts().size(), 6);
	}


	@Test
	public void getProduct_successful() {
		ResponseEntity<ProductResponse> response = this.restTemplate.getForEntity("http://localhost:" + port +
				"/api/products/1", ProductResponse.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertEquals(response.getBody().getProducts().size(), 1);
		Assertions.assertEquals(response.getBody().getProducts().get(0).getName(), "2-Aminoindan");
	}


}
