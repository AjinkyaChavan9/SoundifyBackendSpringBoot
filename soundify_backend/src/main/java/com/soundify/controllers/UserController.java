package com.soundify.controllers;

import com.soundify.dtos.playlists.PlaylistResponseDTO;
import com.soundify.dtos.user.UserResponseDTO;
import com.soundify.dtos.song.SongDTO;
import com.soundify.dtos.user.UserSignInRequestDTO;
import com.soundify.dtos.user.UserSignInResponseDTO;
import com.soundify.dtos.user.UserSignUpRequestDTO;
import com.soundify.dtos.user.UserSignupResponseDTO;
import com.soundify.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.soundify.dtos.artists.*;

import java.util.Set;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> addUser(@RequestBody @Valid UserSignUpRequestDTO user) {
        UserSignupResponseDTO response = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signInUser(@RequestBody @Valid UserSignInRequestDTO request) {
    	System.out.println("auth req " + request);
        UserSignInResponseDTO response = userService.signInUser(request);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
	public ResponseEntity<UserSignInResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserSignupResponseDTO updatedUser) {
	    UserSignInResponseDTO updatedResponse = userService.updateUser(updatedUser, id);
	    return ResponseEntity.ok(updatedResponse);
	}
    @PostMapping("/{userId}/liked-songs/{songId}")
    public ResponseEntity<?> likeSong(@PathVariable Long userId, @PathVariable Long songId){
    	userService.likeSong(userId, songId);
    	return ResponseEntity.ok("Song liked successfully");
    }
    
    @DeleteMapping("/{userId}/unliked-songs/{songId}")
    public ResponseEntity<?> disLikeSong(@PathVariable Long userId, @PathVariable Long songId){
    	userService.unLikeSong(userId, songId);
    	return ResponseEntity.ok("Song disliked successfully");
    }
    
    @PostMapping("/{userId}/followed-artist/{artistId}")
    public ResponseEntity<?> followArtist(@PathVariable Long userId, @PathVariable Long artistId){
    	userService.followArtist(userId, artistId);
    	return ResponseEntity.ok("Artist followed successfully");
    }
    
    @DeleteMapping("/{userId}/unfollowed-artist/{artistId}")
    public ResponseEntity<?> unfollowArtist(@PathVariable Long userId, @PathVariable Long artistId){
    	userService.unFollowArtist(userId, artistId);
    	return ResponseEntity.ok("Artist Unfollowed successfully");
    }
    
    @PostMapping("/{userId}/playlist/")
    public ResponseEntity<?> createPlaylist(@PathVariable Long userId, @RequestBody String playlistName){
    	PlaylistResponseDTO response = userService.createPlaylist(userId, playlistName);
    	return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @DeleteMapping("/{userId}/playlist/{playlistId}")
    public ResponseEntity<?> deletePlaylist(@PathVariable Long userId, @PathVariable Long playlistId){
    	userService.deletePlaylist(userId, playlistId);
    	return ResponseEntity.ok("Playlist Deleted successfully");
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

