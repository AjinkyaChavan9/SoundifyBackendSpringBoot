package com.soundify.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soundify.dtos.ApiResponse;
import com.soundify.services.GenreService;

@RestController
@RequestMapping("/api/genre")
@CrossOrigin(origins = "*")
public class GenreController {

	@Autowired
	GenreService genreService;

	@GetMapping
	public ResponseEntity<?> getAllGenre() {

		return ResponseEntity.ok(genreService.getAllGenre());
	}

	@GetMapping("/{genreId}")
	public ResponseEntity<?> getGenreById(@PathVariable Long genreId) {

		return ResponseEntity.ok(genreService.getGenreById(genreId));
	}

	@PutMapping("/{genreId}/song/{songId}")
	public ResponseEntity<?> addSongToPlaylist(@PathVariable Long genreId, @PathVariable Long songId) {
		genreService.addSongToGenre(genreId, songId);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("success","Song Added To Genre"));
	}

	@DeleteMapping("/{genreId}/song/{songId}")
	public ResponseEntity<?> removeSongFromPlaylist(@PathVariable Long genreId, @PathVariable Long songId) {
		genreService.removeSongFromGenre(genreId, songId);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("success","Song Removed from Genre"));
	}
}
