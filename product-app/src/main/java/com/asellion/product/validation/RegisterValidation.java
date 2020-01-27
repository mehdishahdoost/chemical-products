package com.asellion.product.validation;

import com.asellion.product.exception.RegistrationException;
import com.asellion.product.model.dto.UserRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * RegisterValidation -
 *
 * @author Mehdi Shahdoost
 * @version 2020/1/27
 * @since 1.0.0
 */
@Component
public class RegisterValidation {

	private List<ValidationRule> rules;

	public RegisterValidation() {
		rules = new ArrayList<>();
		rules.add(new PasswordValidation());
		rules.add(new RePasswordValidation());
		rules.add(new UserNameValidation());
	}

	public void validate(UserRequest userRequest) throws RegistrationException {
		for(ValidationRule validationRule: rules) {
			validationRule.validate(userRequest);
		}
	}
}
