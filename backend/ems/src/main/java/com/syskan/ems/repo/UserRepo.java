package com.syskan.ems.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.syskan.ems.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
	/**
	 * findByName method is used to retrieve a user by their username. It returns an
	 * Optional of UserInfo, which will be empty if no user is found.
	 */
	Optional<User> findByUserName(String userName);
}