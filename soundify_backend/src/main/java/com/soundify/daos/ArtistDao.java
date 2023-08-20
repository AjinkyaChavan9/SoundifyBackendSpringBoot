package com.soundify.daos;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.soundify.entities.Artist;

public interface ArtistDao extends JpaRepository<Artist, Long>  {
	//add a finder method for artist's signin
		Optional<Artist> findByEmailAndPassword(String ar,String pass);
		
		
}
