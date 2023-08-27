package com.soundify.daos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.soundify.entities.Genre;

public interface GenreDao extends JpaRepository<Genre, Long> {
	Optional<Genre> findByGenreName(String genreName);
	
	@Modifying
	@Query(value = "DELETE FROM song_genre WHERE song_id = :songId", nativeQuery = true)
	void removeSongsFromGenre(Long songId);
	
	
}
