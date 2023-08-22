package com.soundify.daos;

import com.soundify.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {
	// Add a finder method for user's signin
	Optional<User> findByEmailAndPassword(String email, String password);

	// Add any additional finder methods or custom queries as needed
}
