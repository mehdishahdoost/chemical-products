package com.asellion.product.validation;

import com.asellion.product.exception.RegistrationException;
import com.asellion.product.model.dto.UserRequest;

/**
 * ValidationRule -
 *
 * @author Mehdi Shahdoost
 * @version 2020/1/27
 * @since 1.0.0
 */
public interface ValidationRule {
	void validate(UserRequest userRequest) throws RegistrationException;
}
