package com.syskan.ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syskan.ems.model.User;
import com.syskan.ems.service.JwtService;
import com.syskan.ems.service.UserService;

@RestController
@RequestMapping("/auth")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;

	@PostMapping("/addUser")
	public String adduser(@RequestBody User user) {
		return userService.addUser(user);
	}

	@PostMapping("/login")
	public String generateToken(@RequestBody User user) {
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
		if (authenticate.isAuthenticated()) {
			return jwtService.generateToken(user.getUserName());
		} else {
			return "Error in User Authentication";
		}
//		return "hello";
	}
}
