package com.soundify.services;

import java.util.List;

import com.soundify.dtos.ApiResponse;
import com.soundify.dtos.playlists.PlaylistResponseDTO;
import com.soundify.dtos.user.UserResponseDTO;
import com.soundify.dtos.song.SongDTO;
import com.soundify.dtos.user.UserSignInRequestDTO;
import com.soundify.dtos.user.UserSignInResponseDTO;
import com.soundify.dtos.user.UserSignUpRequestDTO;
import com.soundify.dtos.user.UserSignupResponseDTO;

import java.util.Set;

import com.soundify.dtos.artists.*;

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

	UserResponseDTO getUserDetails(Long userId);

	List<UserSignupResponseDTO> getUsers();

	ApiResponse deleteUserById(Long userId);

	void deletePlaylist(Long userId, Long playlistId);

	Set<ArtistResponseDTO> getFollowedArtists(Long userId);

	Set<SongDTO> getLikedSongs(Long userId);

	UserSignInResponseDTO updateUserPassword(Long userId, String currentPassword, String newPassword);
	
}
