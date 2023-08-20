package com.soundify.daos;

import com.soundify.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongDao extends JpaRepository<Song, Long> {
	List<Song> findByGenresGenreName(String genreName);

	List<Song> findByArtistsName(String artistName);

	List<Song> findByArtistsFirstNameOrArtistsLastName(String firstName, String lastName);

	List<Song> findByGenresContaining(Genre genre);

	List<Song> findBySongNameContainingIgnoreCase(String songName);

}
