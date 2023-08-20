package com.soundify.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soundify.custom_exceptions.ResourceNotFoundException;
import com.soundify.daos.ArtistDao;
import com.soundify.daos.SongDao;
import com.soundify.dtos.artists.ArtistSigninRequestDTO;
import com.soundify.dtos.artists.ArtistSigninResponseDTO;
import com.soundify.dtos.artists.ArtistSignupRequestDTO;
import com.soundify.dtos.artists.ArtistSignupResponseDTO;
import com.soundify.entities.Artist;
import com.soundify.entities.Song;

@Service
@Transactional
public class ArtistServiceImpl implements ArtistService {
	@Autowired
	private ArtistDao artDao;
	
	@Autowired
	private SongDao songDao;

	@Autowired
	private ModelMapper mapper;

	@Override
	public ArtistSignupResponseDTO addArtDetails(ArtistSignupRequestDTO artDTO) {
		System.out.println("request  " + artDTO);
		// Since we need entity for persistence , map , dto ----> Entity --> then invoke
		// save
		Artist persistentArt = artDao.save(mapper.map(artDTO, Artist.class));
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
	public ArtistSigninResponseDTO updateArtist(ArtistSignupResponseDTO artist, Long Id) {
		Artist existingArtist = artDao.findById(Id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

		// Update user properties based on the updatedUser DTO
		existingArtist.setFirstName(artist.getFirstName());
		existingArtist.setLastName(artist.getLastName());
		existingArtist.setEmail(artist.getEmail());
		existingArtist.setDateOfBirth(artist.getDateOfBirth());
		// Update other properties as needed

		// Save the updated user
		existingArtist = artDao.save(existingArtist);

		return mapper.map(existingArtist, ArtistSigninResponseDTO.class);

	}


	// MODIFIED to add DTO pattern

	
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

}
