package com.soundify.services;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.soundify.custom_exceptions.ResourceNotFoundException;
import com.soundify.daos.SongDao;
import com.soundify.dtos.ApiResponse;
import com.soundify.dtos.SongMetadataUploadDTO;
import com.soundify.entities.Song;

@Service
@Transactional
public class SongFileHandlingServiceImpl implements SongFileHandlingService {

	@Autowired
	private SongDao songDao;
	
	@Autowired
	private ModelMapper mapper;
	// to inject the value of a property : "upload.location" , from app property
	// file
	// Field level DI , for injecting the value : using SpEL (spring expression language)
	@Value("${song.upload.location}")
	private String songFolderLocation;
	
	
	
	@PostConstruct
	public void init() {
		System.out.println("in init " + songFolderLocation);
		// chk if folder exists
		File folder = new File(songFolderLocation);
		if (folder.exists())
			System.out.println("folder alrdy exists !");
		else {
			folder.mkdir(); // creates a new folder
			System.out.println("created a new folder...");
		}
		

	}
	
	@Override
	public ApiResponse uploadSongOnServer( SongMetadataUploadDTO songMetadata, MultipartFile file) throws IOException {
		// chk if song exists by id
		
		Song song =songDao.save(mapper.map(songMetadata, Song.class));
		//Song song = songDao.findById(songId).orElseThrow(() -> new ResourceNotFoundException("Invalid song id !!!!!"));
		// song : persistent
		// save uploaded file contents in server side folder.
		// create the path to store the file
		String path = songFolderLocation.concat(file.getOriginalFilename());
		System.out.println("path " + path);
		// FileUtils class : to read byte[] from multpart file ---> server side folder
		// API : public void writeByteArrayToFile(File file, byte[] data) throws
		// IOException
		FileUtils.writeByteArrayToFile(new File(path), file.getBytes());
		// file saved successfully !
		// set image path in db
		song.setSongPath(path);
		
		return new ApiResponse("SongFile uploaded n stored in server side folder");
		
	}

	@Override
	public byte[] downloadSong(Long songId) throws IOException {
		// get song from DB
				Song song = songDao.
						findById(songId).orElseThrow(() -> new ResourceNotFoundException("Invalid song id !!!!!"));
				
				// => song exists !
				// chk if song path exists
				if (song.getSongPath() != null) {
					// song exists , read file contents in to byte[]
					return FileUtils.readFileToByteArray(new File(song.getSongPath()));
				}
				throw new ResourceNotFoundException("Song not yet assigned!!!!");
		
	}

	@Override
	public ApiResponse uploadSongOnS3(SongMetadataUploadDTO songmetadata) throws IOException {
		Song song =songDao.save(mapper.map(songmetadata,Song.class));
		
		
		return new ApiResponse("SongFile uploaded n stored on AWS S3");
	}

	@Override
	public Song getSongById(Long songId) {
	
	return	songDao.findById(songId).orElseThrow(()-> new ResourceNotFoundException("Song Not found by id = "+songId));
		 
	}

}
