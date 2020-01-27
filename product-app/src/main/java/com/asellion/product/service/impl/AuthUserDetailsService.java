package com.asellion.product.service.impl;

import com.asellion.product.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

/**
 * AuthUserDetailsService -
 *
 * @author Mehdi Shahdoost
 * @version 2020/1/27
 * @since 1.0.0
 */
@Service("AuthUserDetailsService")
public class AuthUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<com.asellion.product.model.entity.User> user = this.userRepository.findByUsername(username);
		if (user.isPresent()) {
			return new User(user.get().getUsername(), user.get().getPassword(),
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

}
