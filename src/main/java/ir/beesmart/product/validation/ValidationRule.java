package ir.beesmart.product.validation;

import ir.beesmart.product.exception.RegistrationException;
import ir.beesmart.product.model.dto.UserRequest;

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
