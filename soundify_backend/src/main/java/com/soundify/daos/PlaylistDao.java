package com.soundify.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.soundify.entities.Playlist;

public interface PlaylistDao extends JpaRepository<Playlist, Long> {
	// Optional<Playlist> findByPlaylistName(String playlistName);
	@Modifying
	@Query(value = "DELETE FROM playlist_song WHERE song_id = :songId", nativeQuery = true)
	void removeSongsFromPlaylist(Long songId);
}