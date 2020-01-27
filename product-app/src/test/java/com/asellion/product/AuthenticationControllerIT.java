package com.asellion.product;

import com.asellion.product.model.dto.AuthRequest;
import com.asellion.product.model.dto.AuthResponse;
import com.asellion.product.model.dto.UserRequest;
import com.asellion.product.model.dto.UserResponse;
import com.asellion.product.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

/**
 * AuthenticationControllerIT -
 *
 * @author Mehdi Shahdoost
 * @version 2020/1/27
 * @since 1.0.0
 */
@SpringBootTest(classes = ProductAppApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerIT {

	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate restTemplate;
	private final String username = "asellion";
	private final String password = "asellion2020";
	private final String name = "mehdi";
	private final String family = "shahdoost";
	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	public void cleanDatabase() {
		this.userRepository.deleteAll();
	}

	/**
	 * Gets JWT token when database is empty. This test must be failed.
	 */
	@Test
	public void authenticate_failed() {
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUsername(this.username);
		authRequest.setPassword(this.password);
		ResponseEntity<AuthResponse> response = this.restTemplate.postForEntity(
				"http://localhost:" + port + "/api/authenticate", authRequest, AuthResponse.class);
		Assertions.assertEquals(response.getBody().getMessage(), "Invalid credential");
	}

	/**
	 * Checks registration is successful for new user
	 */
	@Test
	public void register_successful() {
		UserRequest userRequest = new UserRequest();
		userRequest.setName(this.name);
		userRequest.setFamily(this.family);
		userRequest.setUsername(this.username);
		userRequest.setRePassword(this.password);
		userRequest.setPassword(this.password);
		ResponseEntity<UserResponse> response = this.restTemplate.postForEntity(
				"http://localhost:" + port + "/api/register", userRequest, UserResponse.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertEquals(response.getBody().getName(), this.name);
		Assertions.assertEquals(response.getBody().getFamily(), this.family);
		Assertions.assertEquals(response.getBody().getUsername(), this.username);
	}

	/**
	 * Checks registration is failed when password and repassword is not equal.
	 */
	@Test
	public void register_repassword_is_incorrect_failed() {
		UserRequest userRequest = new UserRequest();
		userRequest.setName(this.name);
		userRequest.setFamily(this.family);
		userRequest.setUsername(this.username);
		userRequest.setRePassword(this.password + "1");
		userRequest.setPassword(this.password);
		ResponseEntity<UserResponse> response = this.restTemplate.postForEntity(
				"http://localhost:" + port + "/api/register", userRequest, UserResponse.class);
		Assertions.assertEquals(response.getBody().getMessage(), "Your password with re-entered password is not equal.");
	}

	/**
	 * Checks registration is failed when username is null.
	 */
	@Test
	public void register_username_is_null_failed() {
		UserRequest userRequest = new UserRequest();
		userRequest.setName(this.name);
		userRequest.setFamily(this.family);
		userRequest.setUsername(null);
		userRequest.setRePassword(this.password);
		userRequest.setPassword(this.password);
		ResponseEntity<UserResponse> response = this.restTemplate.postForEntity(
				"http://localhost:" + port + "/api/register", userRequest, UserResponse.class);
		Assertions.assertEquals(response.getBody().getMessage(), "Your username is null. filled it with some words.");
	}

	/**
	 * Retrieves user info without JWT token must be failed.
	 */
	@Test
	public void retrieve_user_info_without_token_failed() {
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUsername(this.username);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer incorrecttoken");

		HttpEntity<AuthRequest> entity = new HttpEntity<AuthRequest>(authRequest, headers);
		ResponseEntity<AuthResponse> response = this.restTemplate.postForEntity(
				"http://localhost:" + port + "/api/user/info", entity, AuthResponse.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
		Assertions.assertNull(response.getBody());
	}

	/**
	 * 1- Register User
	 * 2- Authenticate user (get JWT token)
	 * 3- Retrieve user info with JWT token
	 */
	@Test
	public void register_authenticate_retrieve_user_info_successful() {
		// Registers user at first place
		UserRequest userRequest = new UserRequest();
		userRequest.setName(this.name);
		userRequest.setFamily(this.family);
		userRequest.setUsername(this.username);
		userRequest.setRePassword(this.password);
		userRequest.setPassword(this.password);
		ResponseEntity<UserResponse> response = this.restTemplate.postForEntity(
				"http://localhost:" + port + "/api/register", userRequest, UserResponse.class);

		// Creates JWT token with calling authenticate
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUsername(this.username);
		authRequest.setPassword(this.password);
		ResponseEntity<AuthResponse> tokenResponse = this.restTemplate.postForEntity(
				"http://localhost:" + port + "/api/authenticate", authRequest, AuthResponse.class);

		//Retrieves user information with JWT token
		AuthRequest authUserRequest = new AuthRequest();
		authUserRequest.setUsername(this.username);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + tokenResponse.getBody().getToken());

		HttpEntity<AuthRequest> entity = new HttpEntity<AuthRequest>(authUserRequest, headers);
		ResponseEntity<UserResponse> userInfo = this.restTemplate.postForEntity(
				"http://localhost:" + port + "/api/user/info", entity, UserResponse.class);
		Assertions.assertEquals(userInfo.getStatusCode(), HttpStatus.OK);
		Assertions.assertEquals(userInfo.getBody().getUsername(), this.username);
	}


}
