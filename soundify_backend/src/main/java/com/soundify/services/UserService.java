package com.soundify.services;

import com.soundify.dtos.playlists.PlaylistResponseDTO;
import com.soundify.dtos.user.UserSignInRequestDTO;
import com.soundify.dtos.user.UserSignInResponseDTO;
import com.soundify.dtos.user.UserSignUpRequestDTO;
import com.soundify.dtos.user.UserSignupResponseDTO;

public interface UserService {
	// List<UserSignupResponseDTO> getAllUsers();

	UserSignupResponseDTO addUser(UserSignUpRequestDTO user);

	// UserSignupResponseDTO getUserById(Long userId);

	UserSignInResponseDTO updateUser(UserSignupResponseDTO user, Long userId);

	// void deleteUser(Long userId);

	UserSignInResponseDTO signInUser(UserSignInRequestDTO request);


	void followArtist(Long userId, Long artistId);

	void unFollowArtist(Long userId, Long artistId);


	
	 void likeSong(Long userId, Long songId);
	 
	 void unLikeSong(Long userId, Long songId);

	 PlaylistResponseDTO createPlaylist(Long userId, String playlistName);

	void deletePlaylist(Long userId, Long playlistId);
}
