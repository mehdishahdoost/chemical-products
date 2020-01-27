package com.asellion.product.exception;

import com.asellion.product.model.dto.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * ExceptionHandling -
 *
 * @author Mehdi Shahdoost
 * @version 2020/1/27
 * @since 1.0.0
 */
@ControllerAdvice
public class ExceptionHandling {

	/**
	 * Listens to all {@link ProductNotFoundException} that raises in application and catch them,
	 * produces suitable response to client.
	 *
	 * @param exception catches all {@link ProductNotFoundException} as a exception listener.
	 * @return {@link ProductResponse} to client.
	 */
	@ExceptionHandler(value = ProductNotFoundException.class)
	public ResponseEntity<ProductResponse> exception(ProductNotFoundException exception) {
		ProductResponse response = new ProductResponse();
		response.setMessage(exception.getMessage());
		return ResponseEntity.ok(response);
	}

}
