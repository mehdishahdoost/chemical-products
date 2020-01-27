package com.asellion.product.service.impl;

import com.asellion.product.exception.RegistrationException;
import com.asellion.product.mapper.UserMapper;
import com.asellion.product.model.dto.AuthRequest;
import com.asellion.product.model.dto.UserRequest;
import com.asellion.product.model.entity.User;
import com.asellion.product.repository.UserRepository;
import com.asellion.product.validation.RegisterValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * RegistrationService -
 *
 * @author Mehdi Shahdoost
 * @version 2020/1/27
 * @since 1.0.0
 */
@Service
public class RegistrationService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RegisterValidation registerValidation;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public User register(UserRequest userRequest) throws RegistrationException {
		this.registerValidation.validate(userRequest);
		Optional<User> existsUser = userRepository.findByUsername(userRequest.getUsername());
		if (existsUser.isPresent()) {
			throw new RegistrationException("This username is exists! Choose another one.");
		} else {
			User user = userMapper.toUser(userRequest);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			return this.userRepository.save(user);
		}
	}

	public Optional<User> getUser(AuthRequest authRequest) {
		Optional<User> user = this.userRepository.findByUsername(authRequest.getUsername());
		if (user.isPresent()) {
			return Optional.of(user.get());
		} else {
			return Optional.empty();
		}
	}

}
