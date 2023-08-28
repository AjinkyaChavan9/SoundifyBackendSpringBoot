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

import com.soundify.aws_S3.AWSS3Config;
import com.soundify.custom_exceptions.ResourceNotFoundException;
import com.soundify.daos.ArtistDao;
import com.soundify.daos.GenreDao;
import com.soundify.daos.PlaylistDao;
import com.soundify.daos.SongDao;
import com.soundify.daos.UserDao;
import com.soundify.dtos.ApiResponse;
import com.soundify.dtos.SongMetadataUploadDTO;
import com.soundify.entities.Artist;
import com.soundify.entities.Genre;
import com.soundify.entities.Playlist;
import com.soundify.entities.Song;
import com.soundify.entities.User;

@Service
@Transactional
public class SongFileHandlingServiceImpl implements SongFileHandlingService {

	@Autowired
	private SongDao songDao;

	@Autowired
	private ArtistDao artistdao;
	
	@Autowired
	private PlaylistDao playlistDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private GenreDao genreDao;
	
	@Autowired
	private ModelMapper mapper;
	// to inject the value of a property : "upload.location" , from app property
	// file
	// Field level DI , for injecting the value : using SpEL (spring expression
	// language)
	@Value("${song.upload.location}")
	private String songFolderLocation;

	@Value("${song.image.upload.location}")
	private String songCoverImageFolderLocation;

	@Value("${cloud.aws.bucketname}")
	private String s3BucketName;

	@Autowired
	AWSS3Config awsS3;

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

		System.out.println("in init " + songCoverImageFolderLocation);
		// chk if folder exists
		File imagefolder = new File(songCoverImageFolderLocation);
		if (imagefolder.exists())
			System.out.println("folder alrdy exists !");
		else {
			imagefolder.mkdir(); // creates a new folder
			System.out.println("created a new folder...");
		}

	}

	@Override
	public ApiResponse uploadSongOnServer(SongMetadataUploadDTO songMetadata, MultipartFile file, Long artistId) throws IOException {
		// chk if song exists by id

		Song song = songDao.save(mapper.map(songMetadata, Song.class));
		// Song song = songDao.findById(songId).orElseThrow(() -> new
		// ResourceNotFoundException("Invalid song id !!!!!"));
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
		song.setArtist(
				artistdao.findById(artistId).orElseThrow(() -> new ResourceNotFoundException("Artist Not Found!!")));
		

		return new ApiResponse("success","SongFile uploaded n stored in server side folder");

	}

	@Override
	public byte[] downloadSong(Long songId) throws IOException {
		// get song from DB
		Song song = songDao.findById(songId).orElseThrow(() -> new ResourceNotFoundException("Invalid song id !!!!!"));

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
		Song song = songDao.save(mapper.map(songmetadata, Song.class));

		return new ApiResponse("success","SongFile uploaded n stored on AWS S3 ");
	}

	@Override
	public ApiResponse uploadSongOnS3(SongMetadataUploadDTO songmetadata, Long artistId) throws IOException {
		Song song = songDao.save(mapper.map(songmetadata, Song.class));
		song.setArtist(
				artistdao.findById(artistId).orElseThrow(() -> new ResourceNotFoundException("Artist Not Found!!")));
		return new ApiResponse("success","SongFile uploaded n stored on AWS S3 ");
	}

	@Override
	public Song getSongById(Long songId) {

		return songDao.findById(songId)
				.orElseThrow(() -> new ResourceNotFoundException("Song Not found by id = " + songId));

	}

	@Override
	public ApiResponse uploadSongCoverImage(Long songId, MultipartFile file) throws IOException {
		// chk if song exists by id
		Song song = songDao.findById(songId).orElseThrow(() -> new ResourceNotFoundException("Invalid song id !!!!!"));
		// song : persistent
		// save uploaded file contents in server side folder.
		// create the path to store the file
		String path = songCoverImageFolderLocation.concat(songId + file.getOriginalFilename());
		System.out.println("path " + path);
		// FileUtils class : to read byte[] from multpart file ---> server side folder
		// API : public void writeByteArrayToFile(File file, byte[] data) throws
		// IOException
		FileUtils.writeByteArrayToFile(new File(path), file.getBytes());
		// file saved successfully !
		// set image path in db
		song.setSongImagePath(path);
		// In case of storing the uploaded file contents in DB :
		// song.setImage(file.getBytes());
		return new ApiResponse("success","Song Image File uploaded n stored in server side folder");
	}// update

	@Override
	public byte[] downloadSongImage(Long songId) throws IOException {
		// get song from DB
		Song song = songDao.findById(songId).orElseThrow(() -> new ResourceNotFoundException("Invalid artist id !!!!!"));

		// => song exists !
		// chk if song image path exists
		if (song.getSongImagePath() != null) {
			// song img exists , read file contents in to byte[]
			return FileUtils.readFileToByteArray(new File(song.getSongImagePath()));
		}
		throw new ResourceNotFoundException("Image not yet assigned!!!!");
	}

	@Override
	public ApiResponse deleteSongOnS3(Long songId) {
//		songDao.deleteById(Id);
//		return new ApiResponse("Song deleted successfully");
		Song songToDelete = songDao.findById(songId).orElseThrow(() -> new ResourceNotFoundException("Song Not Found"));

		if (songToDelete == null) {
			return new ApiResponse("error","Song not found");
		}

		String key = songToDelete.getSongPath();
		awsS3.getAmazonS3Client().deleteObject(s3BucketName, key);

		deleteFile(songToDelete.getSongImagePath());
		// deleteFile(songToDelete.getSongPath());

		// Remove references from related entities (e.g., artists, playlists, genres)
		Artist artist = songToDelete.getArtist();
		artist.removeSong(songToDelete);

//		for (Playlist playlist : songToDelete.getPlaylists()) {
//			playlist.removeSong(songToDelete);
//		}		
		playlistDao.removeSongsFromPlaylist(songId);
		

//		for (Genre genre : songToDelete.getGenres()) {
//			genre.removeSong(songToDelete);
//		}
		genreDao.removeSongsFromGenre(songId);

//		
		userDao.removeLikedSongsFromUser(songId);

		
		if(songDao.existsById(songId))
		{
			songDao.deleteById(songId);
		}

		return new ApiResponse("success","Song deleted successfully");

	}

	@Override
	public ApiResponse deleteSongOnServer(Long songId) {
//		songDao.deleteById(Id);
//		return new ApiResponse("Song deleted successfully");
		Song songToDelete = songDao.findById(songId).orElseThrow(() -> new ResourceNotFoundException("Song Not Found"));

		if (songToDelete == null) {
			return new ApiResponse("error","Song not found");
		}

		deleteFile(songToDelete.getSongImagePath());
		deleteFile(songToDelete.getSongPath());

		// Remove references from related entities (e.g., artists, playlists, genres)
		Artist artist = songToDelete.getArtist();
		artist.removeSong(songToDelete);

//		for (Playlist playlist : songToDelete.getPlaylists()) {
//			playlist.removeSong(songToDelete);
//		}
		


		for (Genre genre : songToDelete.getGenres()) {
			genre.removeSong(songToDelete);
		}

		for (User user : songToDelete.getUsers()) {
			user.removeLikedSong(songToDelete);
		}

		songDao.deleteById(songId);

		return new ApiResponse("success","Song deleted successfully");

	}

	public static void deleteFile(String filePath) {
		if (filePath == null) {
			System.out.println("File doesn't exist.");
			return;
		}
		File fileToDelete = new File(filePath);

		if (fileToDelete.exists()) {
			if (fileToDelete.delete()) {
				System.out.println("File deleted successfully.");
			} else {
				System.out.println("Failed to delete the file.");
			}
		} else {
			System.out.println("File doesn't exist.");
		}
	}

}
