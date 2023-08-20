package com.soundify.daos;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.soundify.entities.Artist;
import com.soundify.entities.Song;

public interface ArtistDao extends JpaRepository<Artist, Long>  {
	//add a finder method for artist's signin
		Optional<Artist> findByEmailAndPassword(String ar,String pass);
	//	Optional<Artist> findByArtistName(String artistName);
		
}
