package ir.beesmart.product.service.impl;

import ir.beesmart.product.exception.RegistrationException;
import ir.beesmart.product.mapper.UserMapper;
import ir.beesmart.product.model.dto.AuthRequest;
import ir.beesmart.product.model.dto.UserRequest;
import ir.beesmart.product.model.entity.User;
import ir.beesmart.product.repository.UserRepository;
import ir.beesmart.product.validation.RegisterValidation;
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

	private UserRepository userRepository;
	private UserMapper userMapper;
	private RegisterValidation registerValidation;
	private PasswordEncoder passwordEncoder;

	public RegistrationService(UserRepository userRepository, UserMapper userMapper,
							   RegisterValidation registerValidation, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.registerValidation = registerValidation;
		this.passwordEncoder = passwordEncoder;
	}

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
