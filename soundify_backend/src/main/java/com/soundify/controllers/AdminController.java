package com.soundify.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soundify.services.ArtistService;
import com.soundify.services.UserService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

	@Autowired
	private ArtistService artistService;

	@Autowired
	private UserService userService;

	@GetMapping("/artists")
	public ResponseEntity<?> getAllAtrists() {

		return ResponseEntity.ok(artistService.getArtists());
	}

	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers() {

		return ResponseEntity.ok(userService.getUsers());
	}

	@DeleteMapping("/user/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable Long userId) {

		return ResponseEntity.ok(userService.deleteUserById(userId));
	}
	
	@DeleteMapping("/artist/{artistId}")
	public ResponseEntity<?> deleteArtist(@PathVariable Long artistId) {

		return ResponseEntity.ok(artistService.deleteArtistById(artistId));
	}
}
