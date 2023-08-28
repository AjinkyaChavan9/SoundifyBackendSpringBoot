package com.soundify.controllers;

import static org.springframework.http.MediaType.IMAGE_GIF_VALUE;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
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

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.soundify.aws_S3.AWSS3Config;
import com.soundify.dtos.ApiResponse;
import com.soundify.dtos.ApiResponseWithBody;
import com.soundify.dtos.SongMetadataUploadDTO;
import com.soundify.dtos.artists.ArtistResponseDTO;
import com.soundify.dtos.artists.ArtistSigninRequestDTO;
import com.soundify.dtos.artists.ArtistSigninResponseDTO;
import com.soundify.dtos.artists.ArtistSignupRequestDTO;
import com.soundify.dtos.artists.ArtistSignupResponseDTO;
import com.soundify.dtos.song.SongDTO;
//import com.soundify.dtos.user.UserResponseDTO;
//import com.soundify.entities.Artist;
import com.soundify.services.ArtistService;
import com.soundify.services.SongFileHandlingService;
import com.soundify.dtos.song.SongUpdateMetadataDTO;

@RestController
@RequestMapping("/api/artists")
@CrossOrigin(origins = "http://localhost:3000")

public class ArtistController {

	@Value("${cloud.aws.credentials.access-key}")
	private String accessKeyId;

	@Value("${cloud.aws.credentials.secret-key}")
	private String accessKeySecret;

	@Value("${cloud.aws.region.static}")
	private String s3RegionName;

	@Value("${cloud.aws.bucketname}")
	private String s3BucketName;

	@Value("${cloud.aws.upload.folder}")
	private String songFolderLocationS3;

	@Autowired
	AWSS3Config awsS3;

	@Autowired
	private SongFileHandlingService songFileService;

	@Autowired
	private ArtistService artistService;

	public ArtistController() {
		System.out.println("in ctor of " + getClass());
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateArtist(@PathVariable Long id,
			@RequestBody ArtistSignupResponseDTO updatedArtist) {

		ArtistSigninResponseDTO updatedResponse = artistService.updateArtist(updatedArtist, id);
		return ResponseEntity.ok(new ApiResponseWithBody("success", "artist updated successfully",updatedResponse));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> addNewArt(@RequestBody @Valid ArtistSignupRequestDTO requestDTO) {
		System.out.println("in add new artist " + requestDTO);
		ArtistSignupResponseDTO response = artistService.addArtDetails(requestDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseWithBody("success", "artist Sign Up successfull",response));
	}

	@PostMapping("/signin")
	public ResponseEntity<?> signInArtist(@RequestBody @Valid ArtistSigninRequestDTO request) {
		System.out.println("auth req " + request);
		// try {
		ArtistSigninResponseDTO resp = artistService.signInArtist(request);
		return ResponseEntity.ok(new ApiResponseWithBody("success", "artist Sign In successful",resp));

	}

	@PostMapping(value = "/aws/{artistId}/song", consumes = "multipart/form-data")
	public ResponseEntity<?> uploadSongAWS(@PathVariable Long artistId, @RequestBody MultipartFile file,
			@RequestParam String songName, @RequestParam String releaseDate, @RequestParam String duration)
			throws IOException, Exception {
		if (file != null) {
			ObjectMetadata obectMetadata = new ObjectMetadata();
			obectMetadata.setContentType(file.getContentType());

			String path = songFolderLocationS3.concat(file.getOriginalFilename());
			awsS3.getAmazonS3Client()
					.putObject(new PutObjectRequest(s3BucketName, path, file.getInputStream(), obectMetadata)
							.withCannedAcl(CannedAccessControlList.PublicRead));

			// String duration = getDuration(file);

			SongMetadataUploadDTO songmetadata = new SongMetadataUploadDTO();
			songmetadata.setSongName(songName);
			songmetadata.setDuration(Time.valueOf(duration));
			songmetadata.setReleaseDate(LocalDate.parse(releaseDate));
			songmetadata.setSongPath(path);

			return ResponseEntity.status(HttpStatus.CREATED)
					.body(songFileService.uploadSongOnS3(songmetadata, artistId));
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sorry coudn't upload your file");
	}
	
	@PostMapping(value = "/songfile/{artistId}", consumes = MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadSongFileOnServer(@PathVariable Long artistId, @RequestBody MultipartFile songFile, @RequestParam String songName,
			@RequestParam String releaseDate, @RequestParam String duration) throws IOException, Exception {
		// String duration = getDuration(songFile);

		SongMetadataUploadDTO songmetadata = new SongMetadataUploadDTO();
		songmetadata.setSongName(songName);
		songmetadata.setDuration(Time.valueOf(duration));
		songmetadata.setReleaseDate(LocalDate.parse(releaseDate));
		System.out.println("in song upload " + songmetadata);
		// invoke image service method
		return ResponseEntity.status(HttpStatus.CREATED).body(songFileService.uploadSongOnServer(songmetadata, songFile, artistId));
	}


	@GetMapping("/{artistId}/song")
	public ResponseEntity<?> getAllSongsOfArtist(@PathVariable Long artistId) {

		Set<SongDTO> allSongOfArtist = artistService.getAllSongsOfArtist(artistId);
		return ResponseEntity.ok(allSongOfArtist);
	}

//	@PostMapping("/{artistId}/song/{songId}")
//	public ResponseEntity<ApiResponse> addSongToArtist(@PathVariable Long artistId, @PathVariable Long songId) {
//		artistService.addSongToArtist(artistId, songId);
//		return ResponseEntity.ok(new ApiResponse("Song added to artist successfully."));
//	}

	@PutMapping("/{artistId}/song/{songId}")
	public ResponseEntity<ApiResponse> updateSongMetadata(@PathVariable Long artistId, @PathVariable Long songId,
			@RequestBody SongUpdateMetadataDTO songUpdateMetadataDTO) {
		artistService.updateSongMetadata(artistId, songId, songUpdateMetadataDTO);
		return ResponseEntity.ok(new ApiResponse("success","Song Metadata updated successfully."));
	}

	@DeleteMapping("/{artistId}/song/{songId}")
	public ResponseEntity<ApiResponse> removeSongFromArtist(@PathVariable Long artistId, @PathVariable Long songId) {
		artistService.removeSongFromArtist(artistId, songId);
		return ResponseEntity.ok(new ApiResponse("success","Song removed from artist successfully."));
	}

	@GetMapping("/{artistId}")
	public ArtistResponseDTO getArtistDetails(@PathVariable Long artistId) {
		System.out.println("in get artist details " + artistId);
		return artistService.getArtistDetails(artistId);
	}

	@PostMapping(value = "/{artistId}/image", consumes = "multipart/form-data")
	public ResponseEntity<?> uploadArtistProfileImage(@PathVariable Long artistId, @RequestBody MultipartFile imageFile)
			throws IOException {
		System.out.println("in img upload " + artistId+"image:"+imageFile);
		
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
	@GetMapping(value = "/{artistId}/image", produces = { IMAGE_GIF_VALUE, IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE })
	public ResponseEntity<?> getArtistImage(@PathVariable Long artistId) throws IOException {
		System.out.println("in artist img download " + artistId);
		return ResponseEntity.ok(artistService.getArtistImage(artistId));
	}

}
