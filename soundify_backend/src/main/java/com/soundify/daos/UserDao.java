package com.soundify.daos;

import com.soundify.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {
	// Add a finder method for user's signin
	Optional<User> findByEmailAndPassword(String email, String password);

	@Modifying
	@Query(value = "DELETE FROM artist_follower WHERE user_id = :userId", nativeQuery = true)
	void removeFollowedArtistsFromUser(Long userId);
	
	@Modifying
	@Query(value = "DELETE FROM song_likes WHERE song_id = :songId", nativeQuery = true)
	void removeLikedSongsFromUser(Long songId);
	
	
}
