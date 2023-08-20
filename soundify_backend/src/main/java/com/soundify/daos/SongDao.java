package com.soundify.daos;
import java.util.List;
import com.soundify.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import com.soundify.entities.Song;
public interface SongDao extends JpaRepository<Song, Long> {
	List<Song> findBySongNameContaining(String searchKey);
}
