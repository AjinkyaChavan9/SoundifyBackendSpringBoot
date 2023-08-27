package com.soundify.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.soundify.services.ArtistService;
import com.soundify.services.UserService;

import com.soundify.dtos.ApiResponse;
import com.soundify.services.GenreService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

	@Autowired
	private ArtistService artistService;

	@Autowired
	private UserService userService;

	@Autowired
	GenreService genreService;

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

	// GENRE CRUD

	@GetMapping("/genre/{genreId}")
	public ResponseEntity<?> getGenreById(@PathVariable Long genreId) {

		return ResponseEntity.ok(genreService.getGenreById(genreId));
	}

	@PostMapping("/genre")
	public ResponseEntity<?> addGenre(@RequestParam String genreName) {
		genreService.addGenre(genreName);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("success", "Genre Added Successfully"));
	}

//	@PutMapping("/genre/{genreId}")
//	public ResponseEntity<?> updateGenreName(@PathVariable Long genreId, @RequestParam String updatedGenreName) {
//		genreService.updateGenreName(genreId, updatedGenreName);
//		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("success","GenreName Updated Successfully"));
//	
	public ResponseEntity<?> updateGenreName(@PathVariable Long genreId, @RequestBody String updatedGenreName) {
	    genreService.updateGenreName(genreId, updatedGenreName);
	    return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("success","GenreName Updated Successfully"));
	}

	@DeleteMapping("/genre/{genreId}")
	public ResponseEntity<?> deleteGenre(@PathVariable Long genreId) {
		genreService.deleteGenre(genreId);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("success","Genre Deleted Successfully"));
	}

}
