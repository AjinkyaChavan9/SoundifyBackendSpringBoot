package com.soundify.services;

import java.util.List;

import com.soundify.dtos.playlists.PlaylistResponseDTO;
import com.soundify.dtos.song.SongDTO;

public interface PlaylistService {

	void addSongToPlaylist(Long playlistId, Long songId);

	List<PlaylistResponseDTO> getAllplaylistsByUserId(Long userId);

	List<SongDTO> getAllSongsByPlaylistId(Long playlistId);

	void removeSongFromPlaylist(Long playlistId, Long songId);

	void updatePlaylistName(Long playlistId, String playlistName);

}
