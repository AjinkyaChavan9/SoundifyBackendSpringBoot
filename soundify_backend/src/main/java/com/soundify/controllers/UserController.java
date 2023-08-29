package com.soundify.controllers;

import com.soundify.dtos.playlists.PlaylistResponseDTO;
import com.soundify.dtos.user.UserResponseDTO;
import com.soundify.dtos.song.SongDTO;
import com.soundify.dtos.user.PasswordChangeRequestDTO;
import com.soundify.dtos.user.UserSignInRequestDTO;
import com.soundify.dtos.user.UserSignInResponseDTO;
import com.soundify.dtos.user.UserSignUpRequestDTO;
import com.soundify.dtos.user.UserSignupResponseDTO;
import com.soundify.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.soundify.dtos.ApiResponse;
import com.soundify.dtos.ApiResponseWithBody;
import com.soundify.dtos.artists.*;

import java.util.Set;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<?> addUser(@RequestBody @Valid UserSignUpRequestDTO user) {
		UserSignupResponseDTO response = userService.addUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseWithBody("success", "User Sign Up Successfull",response));
	}

	@PostMapping("/signin")
	public ResponseEntity<?> signInUser(@RequestBody UserSignInRequestDTO request) {
		System.out.println("auth req " + request);
		UserSignInResponseDTO response = userService.signInUser(request);
		return ResponseEntity.ok(new ApiResponseWithBody("success", "User Signed in Successfully!",response));
	}

	@PutMapping("/{userId}")
	public ResponseEntity<?> updateUser(@PathVariable Long userId,
			@RequestBody UserSignupResponseDTO updatedUser) {
		UserSignInResponseDTO updatedResponse = userService.updateUser(updatedUser, userId);
		return ResponseEntity.ok(new ApiResponseWithBody("success","user profile updated successfully",  updatedResponse));
	}

	@PostMapping("/{userId}")
	public ResponseEntity<?> updateUserPassword(@PathVariable Long userId, @RequestBody PasswordChangeRequestDTO dto) {
		System.out.println("old password" + dto.getOldPassword());
		System.out.println("new password" + dto.getNewPassword());
		userService.updateUserPassword(userId, dto.getOldPassword(), dto.getNewPassword());// UserSignInResponseDTO
																							// updatedResponse =
		return ResponseEntity.ok(new ApiResponse("success","Password Updated Successfully"));
	}

	@PostMapping("/{userId}/liked-songs/{songId}")
	public ResponseEntity<?> likeSong(@PathVariable Long userId, @PathVariable Long songId) {
		userService.likeSong(userId, songId);
		return ResponseEntity.ok("Song liked successfully");
	}

	@DeleteMapping("/{userId}/unliked-songs/{songId}")
	public ResponseEntity<?> disLikeSong(@PathVariable Long userId, @PathVariable Long songId) {
		userService.unLikeSong(userId, songId);
		return ResponseEntity.ok("Song disliked successfully");
	}

	@PostMapping("/{userId}/followed-artist/{artistId}")
	public ResponseEntity<?> followArtist(@PathVariable Long userId, @PathVariable Long artistId) {
		userService.followArtist(userId, artistId);
		return ResponseEntity.ok("Artist followed successfully");
	}

	@DeleteMapping("/{userId}/unfollowed-artist/{artistId}")
	public ResponseEntity<?> unfollowArtist(@PathVariable Long userId, @PathVariable Long artistId) {
		userService.unFollowArtist(userId, artistId);
		return ResponseEntity.ok("Artist Unfollowed successfully");
	}

	@PostMapping("/{userId}/playlist/{playlistName}")
	public ResponseEntity<?> createPlaylist(@PathVariable Long userId, @PathVariable String playlistName) {
		PlaylistResponseDTO response = userService.createPlaylist(userId, playlistName);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@DeleteMapping("/{userId}/playlist/{playlistId}")
	public ResponseEntity<?> deletePlaylist(@PathVariable Long userId, @PathVariable Long playlistId) {
		userService.deletePlaylist(userId, playlistId);
		return ResponseEntity.ok(new ApiResponse("success","playlist Deleted Successfully"));
	}

	@GetMapping("/{userId}")
	public UserResponseDTO getUserDetails(@PathVariable Long userId) {
		System.out.println("in get user details " + userId);
		return userService.getUserDetails(userId);
	}

	@GetMapping("/{userId}/followed-artists")
	public ResponseEntity<Set<ArtistResponseDTO>> getFollowedArtists(@PathVariable Long userId) {
		Set<ArtistResponseDTO> followedArtists = userService.getFollowedArtists(userId);
		return ResponseEntity.ok(followedArtists);
	}

	@GetMapping("/{userId}/liked-songs")
	public ResponseEntity<Set<SongDTO>> getLikedSongs(@PathVariable Long userId) {
		Set<SongDTO> likedSongs = userService.getLikedSongs(userId);
		return ResponseEntity.ok(likedSongs);
	}

}
