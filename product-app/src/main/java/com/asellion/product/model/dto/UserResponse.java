package com.asellion.product.model.dto;

import lombok.Data;
import lombok.ToString;

/**
 * UserResponse -
 *
 * @author Mehdi Shahdoost
 * @version 2020/1/27
 * @since 1.0.0
 */
@Data
@ToString
public class UserResponse {

	private String username;
	private String name;
	private String family;
	private String message;

}
