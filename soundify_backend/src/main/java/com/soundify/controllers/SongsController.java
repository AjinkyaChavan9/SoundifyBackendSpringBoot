package com.soundify.controllers;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import static org.springframework.http.MediaType.*;

import com.soundify.dtos.SongMetadataUploadDTO;
import com.soundify.entities.Song;
import com.soundify.services.SongFileHandlingService;



@RestController // =@Controller at cls level + @ResponseBody added over ret
@RequestMapping("/api/songs")
@CrossOrigin(origins = "http://localhost:3000")
public class SongsController {

		@Autowired
		private SongFileHandlingService songService;
		// http://localhost:8080/api/songs/{songId}/songfile
		// songId : path var
		// method : POST
		// multipart file : request parameter (standard part of HTTP specifications)
		@PostMapping(value = "/songfile", consumes = MULTIPART_FORM_DATA_VALUE)
		public ResponseEntity<?> uploadSongFile( @RequestParam MultipartFile songFile,  @RequestParam String songName,
		        @RequestParam String duration,
		        @RequestParam String releaseDate)
				throws IOException 
		{
			 SongMetadataUploadDTO songmetadata = new SongMetadataUploadDTO();
			    songmetadata.setSongName(songName);
			    songmetadata.setDuration(Time.valueOf(duration));
			    songmetadata.setReleaseDate(LocalDate.parse(releaseDate));
			System.out.println("in song upload " + songmetadata);
			// invoke image service method
			return ResponseEntity.status(HttpStatus.CREATED).body(songService.uploadSong(songmetadata, songFile));
		}
		
		// http://localhost:8080/api/songs/{songId}/songfile , method=GET
		// serve(download) song
		@GetMapping(value = "/{songId}/songfile", produces = "audio/mpeg")
		public ResponseEntity<?> downloadSongFile(@PathVariable Long songId) throws IOException {
			System.out.println("in SONG download " + songId);
			return ResponseEntity.ok(songService.downloadSong(songId));
		}
		
}
