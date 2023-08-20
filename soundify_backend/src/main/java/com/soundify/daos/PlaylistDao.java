package com.soundify.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soundify.entities.Playlist;
import java.util.List;
import java.util.Optional;


public interface PlaylistDao extends JpaRepository<Playlist, Long>{
	 //Optional<Playlist> findByPlaylistName(String playlistName);
}
