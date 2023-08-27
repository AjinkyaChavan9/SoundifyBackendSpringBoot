package com.soundify.daos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.soundify.entities.Artist;

public interface ArtistDao extends JpaRepository<Artist, Long> {
	// add a finder method for artist's signin
	Optional<Artist> findByEmailAndPassword(String ar, String pass);

	@Modifying
	@Query(value = "DELETE FROM artist_follower WHERE artist_id = :artistId", nativeQuery = true)
	void removeFollowersFromArtist(Long artistId);

}
