package com.asellion.product.validation;

import com.asellion.product.exception.RegistrationException;
import com.asellion.product.model.dto.UserRequest;

/**
 * RePasswordValidation -
 *
 * @author Mehdi Shahdoost
 * @version 2020/1/27
 * @since 1.0.0
 */
public class RePasswordValidation implements ValidationRule {

	@Override
	public void validate(UserRequest userRequest) throws RegistrationException {
		if (!userRequest.getPassword().equals(userRequest.getRePassword())) {
			throw new RegistrationException("Your password with re-entered password is not equal.");
		}
	}
}
