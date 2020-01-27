package com.asellion.product.model.dto;

import lombok.Data;

/**
 * UserRequest -
 *
 * @author Mehdi Shahdoost
 * @version 2020/1/27
 * @since 1.0.0
 */
@Data
public class UserRequest {
	private String username;
	private String password;
	private String rePassword;
	private String name;
	private String family;

}
