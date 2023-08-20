package com.soundify.services;

import com.soundify.dtos.user.UserSignupResponseDTO;

public interface PlaylistService {

	void addSongToPlaylist(Long playlistId, Long songId);
	 void removeSongFromPlaylist(Long playlistId, Long songId);

}
