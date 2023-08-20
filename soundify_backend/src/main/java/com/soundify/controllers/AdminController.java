package com.soundify.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soundify.services.GenreService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {
	
	
	@Autowired
	GenreService genreService;
	
	
	//GENRE CRUD
	@GetMapping("/genre")
	 public ResponseEntity<?> getAllGenre(){
		 
		return ResponseEntity.ok(genreService.getAllGenre());
	 }
}
