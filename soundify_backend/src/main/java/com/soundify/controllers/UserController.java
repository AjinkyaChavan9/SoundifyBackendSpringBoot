package com.soundify.controllers;

import com.soundify.dtos.user.UserSignInRequestDTO;
import com.soundify.dtos.user.UserSignInResponseDTO;
import com.soundify.dtos.user.UserSignUpRequestDTO;
import com.soundify.dtos.user.UserSignupResponseDTO;
import com.soundify.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    
    @DeleteMapping("/{userId}/disliked-songs/{songId}")
    public ResponseEntity<?> disLikeSong(@PathVariable Long userId, @PathVariable Long songId){
    	userService.unLikeSong(userId, songId);
    	return ResponseEntity.ok("Song disliked successfully");
    }
    
}

