package ir.beesmart.product.validation;

import ir.beesmart.product.exception.RegistrationException;
import ir.beesmart.product.model.dto.UserRequest;

/**
 * UserNameValidation -
 *
 * @author Mehdi Shahdoost
 * @version 2020/1/27
 * @since 1.0.0
 */
public class UserNameValidation implements ValidationRule {
	@Override
	public void validate(UserRequest userRequest) throws RegistrationException {
		if (userRequest.getUsername() == null) {
			throw new RegistrationException("Your username is null. filled it with some words.");
		}
	}
}
