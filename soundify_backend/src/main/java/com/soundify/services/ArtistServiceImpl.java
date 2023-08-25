package com.soundify.services;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.io.FileUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.soundify.custom_exceptions.ResourceNotFoundException;
import com.soundify.daos.ArtistDao;
import com.soundify.daos.RoleDao;
import com.soundify.daos.SongDao;
import com.soundify.daos.UserDao;
import com.soundify.dtos.artists.ArtistResponseDTO;
import com.soundify.dtos.ApiResponse;
import com.soundify.dtos.artists.ArtistSigninRequestDTO;
import com.soundify.dtos.artists.ArtistSigninResponseDTO;
import com.soundify.dtos.artists.ArtistSignupRequestDTO;
import com.soundify.dtos.artists.ArtistSignupResponseDTO;
import com.soundify.dtos.song.SongDTO;
import com.soundify.dtos.song.SongUpdateMetadataDTO;
import com.soundify.entities.Artist;
import com.soundify.entities.Role;
import com.soundify.entities.Song;
import com.soundify.entities.User;

@Service
@Transactional
public class ArtistServiceImpl implements ArtistService {
	@Autowired
	private ArtistDao artDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private SongDao songDao;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private SongFileHandlingService songFileHandlingService;

	@Value("${cloud.aws.upload.folder}")
	private String songFolderLocationS3;
	@Autowired
	private ModelMapper mapper;

	@Value("${artist.image.upload.location}")
	private String artistImageFolderLocation;

	@PostConstruct
	public void init() {

		System.out.println("in init " + artistImageFolderLocation);
		// chk if folder exists
		File imagefolder = new File(artistImageFolderLocation);
		if (imagefolder.exists())
			System.out.println("folder alrdy exists !");
		else {
			imagefolder.mkdir(); // creates a new folder
			System.out.println("created a new folder...");
		}

	}

	@Override
	public ArtistSignupResponseDTO addArtDetails(ArtistSignupRequestDTO artDTO) {
		System.out.println("request  " + artDTO);
		// Since we need entity for persistence , map , dto ----> Entity --> then invoke
		// save

		Artist persistentArt = artDao.save(mapper.map(artDTO, Artist.class));
		Role artistRole = roleDao.findById((long) 2)
				.orElseThrow(() -> new ResourceNotFoundException("Role with id 3 not found"));
		persistentArt.setRole(artistRole);
		// If we send directly entity to REST clnt , it will contain ,
		// un necessary fields(eg : password , dept , address, projects...) ,
		// so as a standard suggestion , map entity --> dto
		return mapper.map(persistentArt, ArtistSignupResponseDTO.class);
	}

	@Override
	public ArtistSigninResponseDTO signInArtist(ArtistSigninRequestDTO artSignInRequestDTO) {
		System.out.println("request  " + artSignInRequestDTO);
		// Since we need entity for persistence , map , dto ----> Entity --> then invoke
		// save
		Artist persistentArt = artDao
				.findByEmailAndPassword(artSignInRequestDTO.getEmail(), artSignInRequestDTO.getPassword())
				.orElseThrow(() -> new ResourceNotFoundException("artist not found "));
		// If we send directly entity to REST clnt , it will contain ,
		// un necessary fields(eg : password , dept , address, projects...) ,
		// so as a standard suggestion , map entity --> dto
		return mapper.map(persistentArt, ArtistSigninResponseDTO.class);

	}

	@Override
	public ArtistSigninResponseDTO updateArtist(ArtistSignupResponseDTO updatedArtist, Long Id) {
		Artist existingArtist = artDao.findById(Id)
				.orElseThrow(() -> new ResourceNotFoundException("Artist not found"));

		// Update user properties based on the updatedUser DTO
		existingArtist.setName(updatedArtist.getName());
		existingArtist.setFirstName(updatedArtist.getFirstName());
		existingArtist.setLastName(updatedArtist.getLastName());
		existingArtist.setEmail(updatedArtist.getEmail());
		existingArtist.setDateOfBirth(updatedArtist.getDateOfBirth());

		// Update other properties as needed

		// Save the updated user
		existingArtist = artDao.save(existingArtist);

		return mapper.map(existingArtist, ArtistSigninResponseDTO.class);

	}

	@Override
	public void updateSongMetadata(Long artistId, Long songId, SongUpdateMetadataDTO songUpdateMetadataDTO) {
		Artist artist = artDao.findById(artistId).orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
		List<Song> artistSongs = artist.getSongs();

		Song songToUpdate = null;
		for (Song song : artistSongs) {
			if (song.getId().equals(songId)) {
				songToUpdate = song;
				break;
			}
		}

		if (songToUpdate == null) {
			throw new ResourceNotFoundException("Song not found for the given artist");
		}

		// Update song metadata based on the DTO
		songToUpdate.setSongName(songUpdateMetadataDTO.getSongName());
		songToUpdate.setDuration(songUpdateMetadataDTO.getDuration());
		songToUpdate.setReleaseDate(songUpdateMetadataDTO.getReleaseDate());

		// Save the updated song

		// songDao.save(song);
	}

	@Override
	public Set<SongDTO> getAllSongsOfArtist(Long artistId) {
		Artist artist = artDao.findById(artistId).orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
		List<Song> allArtistSongs = artist.getSongs();
		return allArtistSongs.stream().map(song -> mapper.map(song, SongDTO.class)).collect(Collectors.toSet());
	}

	public void addSongToArtist(Long artistId, Long songId) {
		Artist artist = artDao.findById(artistId).orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
		Song song = songDao.findById(songId).orElseThrow(() -> new ResourceNotFoundException("Song not found"));

		artist.addSong(song);
		artDao.save(artist);
	}

	public void removeSongFromArtist(Long artistId, Long songId) {
		Artist artist = artDao.findById(artistId).orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
		Song song = songDao.findById(songId).orElseThrow(() -> new ResourceNotFoundException("Song not found"));

		artist.removeSong(song);
		artDao.save(artist);
	}

	@Override
	public ArtistResponseDTO getArtistDetails(Long artistId) {
		Artist artist = artDao.findById(artistId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Artist ID !!!!!"));
		return mapper.map(artist, ArtistResponseDTO.class);
	}

	public List<ArtistResponseDTO> getArtists() {
		List<Artist> artists = artDao.findAll();
		return artists.stream().map(artist -> mapper.map(artist, ArtistResponseDTO.class)).collect(Collectors.toList());
	}

	@Override
	public ApiResponse deleteArtistById(Long artistId) {
		Artist artist = artDao.findById(artistId).orElseThrow(() -> new ResourceNotFoundException("Artist not found"));

		// Remove the artist from each follower's artistsFollowed set
//		Set<User> followersCopy = new HashSet<>(artist.getFollowers());	
//		for (User follower : followersCopy) {
//			 follower.removeFollowedArtist(artist); // Calling a method to remove artist from followers
//			 //to avoid [java.util.ConcurrentModificationException]
//			 entityManager.persist(follower);
//	    }
//		Set<User> followers = artist.getFollowers();
//	    Iterator<User> followerIterator = followers.iterator();
//	    while (followerIterator.hasNext()) {
//	        User follower = followerIterator.next();
//	        //follower.removeArtistAssociations();
//	        entityManager.persist(follower);
//	        followerIterator.remove(); // Use iterator's remove method
//	    }
	    Set<User> followersCopy = new HashSet<>(artist.getFollowers());


		for (User follower : followersCopy) {
			follower.removeFollowedArtist(artist);
			userDao.save(follower); // Save the changes to the follower
		}
		artist.getFollowers().clear(); // Clear the followers set in the artist

		List<Song> songs = artist.getSongs();
		songs.forEach((song) -> {
			if (song.getSongPath().contains(songFolderLocationS3))
				songFileHandlingService.deleteSongOnS3(song.getId());
			else
				songFileHandlingService.deleteSongOnServer(song.getId());
		});
		artDao.delete(artist);
		return new ApiResponse("success", "Artist deleted successfully");
	}

	@Override
	public ApiResponse uploadArtistImage(Long artistId, MultipartFile imageFile) throws IOException {
		// chk if song exists by id
		Artist artist = artDao.findById(artistId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid song id !!!!!"));
		// song : persistent
		// save uploaded file contents in server side folder.
		// create the path to store the file
		String path = artistImageFolderLocation.concat(artist.getId() + imageFile.getOriginalFilename());
		System.out.println("path " + path);
		// FileUtils class : to read byte[] from multpart file ---> server side folder
		// API : public void writeByteArrayToFile(File file, byte[] data) throws
		// IOException
		FileUtils.writeByteArrayToFile(new File(path), imageFile.getBytes());
		// file saved successfully !
		// set image path in db
		artist.setArtistImagePath(path);
		// In case of storing the uploaded file contents in DB :
		// song.setImage(file.getBytes());
		return new ApiResponse("success", "artist Image File uploaded n stored in server side folder");
	}

	@Override
	public ApiResponse editArtistImage(Long artistId, MultipartFile imageFile) throws IOException {

		Artist artist = artDao.findById(artistId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid song id !!!!!"));

		deleteFile(artist.getArtistImagePath());

		String path = artistImageFolderLocation.concat(imageFile.getOriginalFilename());
		System.out.println("path " + path);

		FileUtils.writeByteArrayToFile(new File(path), imageFile.getBytes());

		artist.setArtistImagePath(path);

		return new ApiResponse("success", "artist Image File edited n stored in server side folder");
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
