package com.syskan.ems.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.syskan.ems.model.User;
import com.syskan.ems.model.UserPrincipal;
import com.syskan.ems.repo.UserRepo;

/**
 * UserInfoService provides user-related services including loading user details
 * and managing user data in the repository.
 */
@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// Loads a user's details given their userName.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findByUserName(username);
		return user.map(UserPrincipal::new)
				.orElseThrow(() -> new UsernameNotFoundException("UserName not found: " + username));
	}

	// Adds a new user to the repository and encrypting password before saving it.
	public String addUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user);
		return "user added successfully";
	}

}
