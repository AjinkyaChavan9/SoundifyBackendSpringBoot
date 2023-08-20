package com.soundify.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.soundify.dtos.ApiResponse;
import com.soundify.dtos.SongMetadataUploadDTO;
import com.soundify.entities.Song;

public interface SongFileHandlingService {
	//add a method to upload bin content to server side folder
		//1st arg : song id 
		//2nd arg : represents uploaded file contents received in multipart request
		ApiResponse uploadSongOnServer(SongMetadataUploadDTO songmetadata, MultipartFile file) throws IOException;

		byte[] downloadSong(Long songId) throws IOException;
		
		ApiResponse uploadSongOnS3(SongMetadataUploadDTO songmetadata) throws IOException;
		
		Song getSongById(Long songId);

		public ApiResponse uploadSongCoverImage(Long songId, MultipartFile file) throws IOException;
		
		public byte[] downloadSongImage(Long songId) throws IOException;
		
		ApiResponse deleteSong(Long Id);
}
