package com.syskan.product.config;
//

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//	@Bean
//	public BCryptPasswordEncoder encodePwd() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//		http.csrf().disable()
//				.authorizeHttpRequests(
//						authorize -> authorize.requestMatchers("/h2-console/**").permitAll().requestMatchers("/admin")
//								.hasRole("ADMIN").requestMatchers("/").permitAll().anyRequest().authenticated())
//				.formLogin().permitAll();
//
//		return http.build();
//	}
//
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("user").password(encodePwd().encode("password")).roles("USER").and()
//				.withUser("admin").password(encodePwd().encode("password")).roles("ADMIN");
//
//	}
//
//}

//@Configuration
//@EnableMethodSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//	@Bean
//	public static PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Bean
//	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		// disables the X-Frame-Options header to allow the H2 console to be displayed in an iframe
//        http.headers().frameOptions().disable();
//		
//		return http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(authorize -> authorize
//				.requestMatchers(HttpMethod.GET, "api/v1/products", "/h2-console/**").permitAll().anyRequest().authenticated())
//				.httpBasic(Customizer.withDefaults()).build();
//	}
//
//	@Bean
//	UserDetailsService userDetailsService() {
//		UserDetails anar = User.builder().username("test1").password(passwordEncoder().encode("test1")).roles("USER")
//				.build();
//
//		UserDetails rufat = User.builder().username("test2").password(passwordEncoder().encode("test2")).roles("ADMIN")
//				.build();
//		return new InMemoryUserDetailsManager(anar, rufat);
//	}
//}

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.syskan.product.filter.JwtFilter;
import com.syskan.product.service.UserService;

@Configuration
@EnableWebSecurity
@CrossOrigin
public class SecurityConfig {

	@Autowired
	private JwtFilter jwtFilter;

	// Defines a UserDetailsService bean for user authentication
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserService();
	}

	// Configures the security filter chain
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.cors(Customizer.withDefaults()) // Apply CORS
				.csrf(csrf -> csrf.disable()) // Disable CSRF protection
				.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/addUser", "/auth/login","/api/v1/products").permitAll()
						// Permit all requests to certain URLs
						.anyRequest().authenticated()) // Require authentication for all other requests
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				// Set session management to stateless
				.authenticationProvider(authenticationProvider()) // Register the authentication provider
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Add the JWT filter before
																						// processing the request
				.build();
	}

	// Creates a DaoAuthenticationProvider to handle user authentication
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	// Defines a PasswordEncoder bean that uses bcrypt hashing by default for
	// password encoding
	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	// Defines an AuthenticationManager bean to manage authentication processes
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

}
