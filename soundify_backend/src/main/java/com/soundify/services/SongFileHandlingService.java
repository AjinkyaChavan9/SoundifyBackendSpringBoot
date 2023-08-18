package com.soundify.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.soundify.dtos.ApiResponse;

public interface SongFileHandlingService {
	//add a method to upload bin content to server side folder
		//1st arg : emp id 
		//2nd arg : represents uploaded file contents received in multipart request
		ApiResponse uploadImage(Long empId,MultipartFile file) throws IOException;

		byte[] downloadImage(Long empId) throws IOException;
}
