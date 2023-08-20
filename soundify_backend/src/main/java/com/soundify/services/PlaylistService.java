package com.soundify.services;



public interface PlaylistService {

	void addSongToPlaylist(Long playlistId, Long songId);
	 void removeSongFromPlaylist(Long playlistId, Long songId);
	void updatePlaylistName(Long playlistId, String playlistName);

}
