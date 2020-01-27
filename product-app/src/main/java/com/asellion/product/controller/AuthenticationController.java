package com.asellion.product.controller;

import com.asellion.product.exception.RegistrationException;
import com.asellion.product.mapper.UserMapper;
import com.asellion.product.model.dto.AuthRequest;
import com.asellion.product.model.dto.AuthResponse;
import com.asellion.product.model.dto.UserRequest;
import com.asellion.product.model.dto.UserResponse;
import com.asellion.product.model.entity.User;
import com.asellion.product.security.AuthTokenUtil;
import com.asellion.product.service.impl.AuthUserDetailsService;
import com.asellion.product.service.impl.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * AuthenticationController -
 *
 * @author Mehdi Shahdoost
 * @version 2020/1/27
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "/api")
@CrossOrigin
@Slf4j
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private AuthTokenUtil authTokenUtil;
	@Autowired
	private AuthUserDetailsService userDetailsService;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RegistrationService registrationService;

	/**
	 * Returns user information to client. after authenticate user, this method retrieve user information (name, family, ...)
	 *
	 * @param authRequest use username in this object to retrieve user object.
	 * @return userResponse that contains name, family of user.
	 */
	@RequestMapping(value = "/user/info", method = RequestMethod.POST)
	public ResponseEntity<UserResponse> getUserInformation(@RequestBody AuthRequest authRequest) {
		log.info("Retrieves user information for userName: {}", authRequest.getUsername());
		Optional<User> user = this.registrationService.getUser(authRequest);
		if (user.isPresent()) {
			UserResponse userResponse = userMapper.toUserResponse(user.get());
			return ResponseEntity.ok(userResponse);
		} else {
			UserResponse userResponse = new UserResponse();
			userResponse.setMessage("User not found!");
			return ResponseEntity.ok(userResponse);
		}
	}

	/**
	 * Returns JWT token for registered user. all APIs must pass this token to server to get results.
	 *
	 * @param authRequest contains userName and password of user
	 * @return JWT token for authenticating APIs.
	 * @throws Exception raises if user is not exists or username or password os not correct.
	 */
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<AuthResponse> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
		log.info("Creates token for userName: {}", authRequest.getUsername());
		this.authenticate(authRequest.getUsername(), authRequest.getPassword());
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authRequest.getUsername());
		final String token = authTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthResponse(token));
	}

	/**
	 * Registers new user.
	 *
	 * @param user contains user information to register.
	 * @return if registration is successful returns user information.
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<UserResponse> saveUser(@RequestBody UserRequest user) throws RegistrationException {
		log.info("Register user with a username: {}", user.getUsername());
		User registeredUser = this.registrationService.register(user);
		UserResponse userResponse = this.userMapper.toUserResponse(registeredUser);
		userResponse.setMessage("Registration is successful!");
		return ResponseEntity.ok(userResponse);
	}

	private void authenticate(String username, String password) throws RegistrationException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			log.error("disabled user", e);
			throw new RegistrationException("User disabled");
		} catch (BadCredentialsException e) {
			log.error("Invalid credentials", e);
			throw new RegistrationException("Invalid credential");
		}
	}
}

