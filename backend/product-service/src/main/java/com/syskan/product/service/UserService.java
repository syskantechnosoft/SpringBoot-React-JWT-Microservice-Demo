package com.syskan.product.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.RestTemplate;

import com.syskan.product.dto.User;
import com.syskan.product.dto.UserPrincipal;

public class UserService implements UserDetailsService {

	@Autowired
	private RestTemplate restTemplate;

	// Loads a user's details given their userName.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = Optional
				.of(restTemplate.getForObject("http://authservice/api/v1/users/" + username, User.class));
		return user.map(UserPrincipal::new)
				.orElseThrow(() -> new UsernameNotFoundException("UserName not found: " + username));
	}

}
