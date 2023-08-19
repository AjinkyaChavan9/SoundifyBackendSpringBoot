package com.soundify.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soundify.entities.Song;
public interface SongDao extends JpaRepository<Song, Long> {
	
}
