package com.soundify.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import com.soundify.dtos.ApiResponse;
import com.soundify.dtos.SongMetadataUploadDTO;
import com.soundify.dtos.artists.ArtistResponseDTO;
import com.soundify.dtos.artists.ArtistSigninRequestDTO;
import com.soundify.dtos.artists.ArtistSigninResponseDTO;
import com.soundify.dtos.artists.ArtistSignupRequestDTO;
import com.soundify.dtos.artists.ArtistSignupResponseDTO;
import com.soundify.dtos.song.SongDTO;
import com.soundify.dtos.user.UserResponseDTO;
import com.soundify.entities.Artist;
import com.soundify.services.ArtistService;
import com.soundify.dtos.song.SongUpdateMetadataDTO;

@RestController
@RequestMapping("/api/artists")
@CrossOrigin(origins = "http://localhost:3000")

public class ArtistController {
	@Autowired
	private ArtistService artistService;
	
	@Autowired
    private SongsController songsController; // Inject the SongsController

	public ArtistController() {
		System.out.println("in ctor of " + getClass());
	}

	@PutMapping("/{id}")
	public ResponseEntity<ArtistSigninResponseDTO> updateArtist(@PathVariable Long id,
			@RequestBody ArtistSignupResponseDTO updatedArtist) {

		ArtistSigninResponseDTO updatedResponse = artistService.updateArtist(updatedArtist, id);
		return ResponseEntity.ok(updatedResponse);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> addNewArt(@RequestBody @Valid ArtistSignupRequestDTO requestDTO) {
		System.out.println("in add new artist " + requestDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(artistService.addArtDetails(requestDTO));
	}

	@PostMapping("/signin")
	public ResponseEntity<?> signInArtist(@RequestBody @Valid ArtistSigninRequestDTO request) {
		System.out.println("auth req " + request);
		// try {
		ArtistSigninResponseDTO resp = artistService.signInArtist(request);
		return ResponseEntity.ok(resp);

	}


//    @PostMapping("/{artistId}/upload-song")
//    public ResponseEntity<?> uploadSongForArtist(
//            @PathVariable Long artistId,
//            @RequestParam("file") MultipartFile file,
//            @RequestParam String songName,
//            @RequestParam String releaseDate) {
//        try {
//            ResponseEntity<?> response = songsController.uploadSong(file, songName, releaseDate);
//            //also add song to the artist
//            return response;
//        } catch (Exception e) {           
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
//        }
//    }


	@GetMapping("/{artistId}/song")
	public ResponseEntity<?> getAllSongsOfArtist(@PathVariable Long artistId) {
		
		Set<SongDTO> allSongOfArtist = artistService.getAllSongsOfArtist(artistId);
		return ResponseEntity.ok(allSongOfArtist);
	}
	
	@PostMapping("/{artistId}/song/{songId}")
	public ResponseEntity<ApiResponse> addSongToArtist(@PathVariable Long artistId, @PathVariable Long songId) {
		artistService.addSongToArtist(artistId, songId);
		return ResponseEntity.ok(new ApiResponse("Song added to artist successfully."));
	}
	
	@PutMapping("/{artistId}/song/{songId}")
	public ResponseEntity<ApiResponse> updateSongMetadata(@PathVariable Long artistId, @PathVariable Long songId,
			@RequestBody SongUpdateMetadataDTO  songUpdateMetadataDTO  ) {
		artistService.updateSongMetadata(artistId, songId, songUpdateMetadataDTO );
		return ResponseEntity.ok(new ApiResponse("Song Metadata updated successfully."));
	}

	@DeleteMapping("/{artistId}/song/{songId}")
	public ResponseEntity<ApiResponse> removeSongFromArtist(@PathVariable Long artistId, @PathVariable Long songId) {
		artistService.removeSongFromArtist(artistId, songId);
		return ResponseEntity.ok(new ApiResponse("Song removed from artist successfully."));
	}

	@GetMapping("/{artistId}")
	public ArtistResponseDTO getArtistDetails(@PathVariable Long artistId) {
		System.out.println("in get artist details " + artistId);
		return artistService.getArtistDetails(artistId);
	}
	
	@PostMapping(value = "/{artistId}/image", consumes = "multipart/form-data")
	public ResponseEntity<?> uploadArtistProfileImage(@PathVariable Long artistId, @RequestBody MultipartFile imageFile)
			throws IOException {
		System.out.println("in img upload " + artistId);
		// invoke image service method
		return ResponseEntity.status(HttpStatus.CREATED).body(artistService.uploadArtistImage(artistId, imageFile));
	}
    
	@PutMapping(value = "/{artistId}/image", consumes = "multipart/form-data")
	public ResponseEntity<?> editArtistProfileImage(@PathVariable Long artistId, @RequestBody MultipartFile imageFile)
			throws IOException {
		System.out.println("in img upload " + artistId);
		// invoke image service method
		return ResponseEntity.status(HttpStatus.CREATED).body(artistService.editArtistImage(artistId, imageFile));
	}
	
	
    
	
}
