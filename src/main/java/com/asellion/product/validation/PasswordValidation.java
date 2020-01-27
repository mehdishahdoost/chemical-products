package com.asellion.product.validation;

import com.asellion.product.exception.RegistrationException;
import com.asellion.product.model.dto.UserRequest;

/**
 * PasswordValidation -
 *
 * @author Mehdi Shahdoost
 * @version 2020/1/27
 * @since 1.0.0
 */
public class PasswordValidation implements ValidationRule {
	@Override
	public void validate(UserRequest userRequest) throws RegistrationException {
		if (userRequest.getPassword() == null) {
			throw new RegistrationException("Your password is null!. Fill it with suitable words");
		}

		if(userRequest.getPassword().length() < 4) {
			throw new RegistrationException("Your password length is not suitable. choose a password with more than 3 words ");
		}
	}
}
