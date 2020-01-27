package com.asellion.product.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * AuthRequest -
 *
 * @author Mehdi Shahdoost
 * @version 2020/1/27
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest implements Serializable {
	private String username;
	private String password;
}
