package com.syskan.order.config;
//

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

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

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// disables the X-Frame-Options header to allow the H2 console to be displayed in an iframe
        http.headers().frameOptions().disable();
		
		return http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(authorize -> authorize
				.requestMatchers(HttpMethod.GET, "api/v1/orders", "/h2-console/**").permitAll().anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults()).build();
	}

	@Bean
	UserDetailsService userDetailsService() {
		UserDetails anar = User.builder().username("test1").password(passwordEncoder().encode("test1")).roles("USER")
				.build();

		UserDetails rufat = User.builder().username("test2").password(passwordEncoder().encode("test2")).roles("ADMIN")
				.build();
		return new InMemoryUserDetailsManager(anar, rufat);
	}
}