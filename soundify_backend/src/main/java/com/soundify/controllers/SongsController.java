package com.soundify.controllers;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;

import java.sql.Time;
import java.time.LocalDate;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import static org.springframework.http.MediaType.*;

import com.soundify.aws_S3.AWSS3Config;
import com.soundify.dtos.ApiResponse;
import com.soundify.dtos.SongMetadataUploadDTO;
import com.soundify.entities.Song;
import com.soundify.services.SongFileHandlingService;




@RestController // =@Controller at cls level + @ResponseBody added over ret
@RequestMapping("/api/songs")
@CrossOrigin(origins = "http://localhost:3000")
public class SongsController {
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

	@PostMapping(value = "/aws",consumes = "multipart/form-data")
	public ResponseEntity<?> uploadSong(@RequestBody MultipartFile file,@RequestParam String songName,
	        @RequestParam String releaseDate) throws IOException,Exception{
		if(file != null) {
		ObjectMetadata obectMetadata = new ObjectMetadata();
		obectMetadata.setContentType(file.getContentType());
		
		String path = songFolderLocationS3.concat(file.getOriginalFilename());
		awsS3.getAmazonS3Client().putObject(new PutObjectRequest(s3BucketName,path,file.getInputStream(),obectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
	    
		String duration = getDuration(file);

		SongMetadataUploadDTO songmetadata = new SongMetadataUploadDTO();
		    songmetadata.setSongName(songName);
		    songmetadata.setDuration(Time.valueOf(duration));
		    songmetadata.setReleaseDate(LocalDate.parse(releaseDate));
		    songmetadata.setSongPath(path);
		return ResponseEntity.status(HttpStatus.CREATED).body(songService.uploadSongOnS3(songmetadata));
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sorry coudn't upload your file");
	}

		@Autowired
		private SongFileHandlingService songService;
		// http://localhost:8080/api/songs/{songId}/songfile
		// songId : path var
		// method : POST
		// multipart file : request parameter (standard part of HTTP specifications)
		@PostMapping(value = "/songfile", consumes = MULTIPART_FORM_DATA_VALUE)
		public ResponseEntity<?> uploadSongFile( @RequestBody MultipartFile songFile,  @RequestParam String songName,
		        @RequestParam String releaseDate)
				throws IOException,Exception
		{
			String duration = getDuration(songFile);
			 
			SongMetadataUploadDTO songmetadata = new SongMetadataUploadDTO();
			    songmetadata.setSongName(songName);
			    songmetadata.setDuration(Time.valueOf(duration));
			    songmetadata.setReleaseDate(LocalDate.parse(releaseDate));
			System.out.println("in song upload " + songmetadata);
			// invoke image service method
			return ResponseEntity.status(HttpStatus.CREATED).body(songService.uploadSongOnServer(songmetadata, songFile));
		}
		
		// http://localhost:8080/api/songs/{songId}/songfile , method=GET
		// serve(download) song
		@GetMapping(value = "/{songId}/songfile", produces = "audio/mpeg")
		public ResponseEntity<?> downloadSongFileFromServer(@PathVariable Long songId) throws IOException {
			System.out.println("in SONG download " + songId);
			return ResponseEntity.ok(songService.downloadSong(songId));
		}
		
		@GetMapping(value = "/{songId}/aws", produces = "audio/mpeg")
		public ResponseEntity<?> downloadSongFileFromS3(@PathVariable Long songId) {
			System.out.println("in SONG download " + songId);
			Song song = songService.getSongById(songId);
			String key = song.getSongPath();
			
			S3Object s3Object = awsS3.getAmazonS3Client().getObject(s3BucketName, key);
			
			 S3ObjectInputStream inputStream = s3Object.getObjectContent();

		        return ResponseEntity.ok()
		                .header("Content-Type", "audio/mpeg") // Set the appropriate content type
		                .header("Content-Disposition", "inline; filename=" + song.getSongName())
		                .body(new InputStreamResource(inputStream));

		}
		

		/*Image Handling */
		// http://localhost:8080/api/songs/{songId}/image
		// songId : path var
		// method : POST
		// multipart file : request parameter (standard part of HTTP specifications)
		@PostMapping(value = "/{songId}/image", consumes = "multipart/form-data")
		public ResponseEntity<?> uploadEmpImage(@PathVariable Long songId, @RequestBody MultipartFile imageFile)
				throws IOException {
			System.out.println("in img upload " + songId);
			// invoke image service method
			return ResponseEntity.status(HttpStatus.CREATED).body(songService.uploadSongCoverImage(songId, imageFile));
		}

		// http://localhost:8080/api/songs/{songId}/image , method=GET
		// serve(download) image
		@GetMapping(value = "/{songId}/image", produces = { IMAGE_GIF_VALUE, 
				IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE })
		public ResponseEntity<?> downloadEmpImage(@PathVariable Long songId) throws IOException {
			System.out.println("in song img download " + songId);
			return ResponseEntity.ok(songService.downloadSongImage(songId));
		}
		
		 @DeleteMapping("value =/{songId}/aws")
		    public ResponseEntity<?> deleteSongFromS3(@PathVariable Long songId) {
		         Song song =songService.getSongById(songId);
		         String key = song.getSongPath();
		         awsS3.getAmazonS3Client().deleteObject(s3BucketName, key);
		         
		         return ResponseEntity.ok(songService.deleteSong(songId));
		    }

		private String getDuration(MultipartFile file) throws Exception {
			File tempFile = File.createTempFile("temp1", file.getOriginalFilename());
			file.transferTo(tempFile);
			
			AudioFile audioFile = AudioFileIO.read(tempFile);
			
			AudioHeader audioHeader = audioFile.getAudioHeader();
			
	        int durationInSeconds = audioHeader.getTrackLength();
	        int hours = durationInSeconds / 3600;
	        int minutes = (durationInSeconds % 3600) / 60;
	        int seconds = durationInSeconds % 60;

	        String duration = String.format("%02d:%02d:%02d", hours, minutes, seconds);
	        
	        tempFile.delete();
	        audioFile.delete();

	        
	        return duration;

		}

}

