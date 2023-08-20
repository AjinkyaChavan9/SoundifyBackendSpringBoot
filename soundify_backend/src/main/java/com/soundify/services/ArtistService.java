package com.soundify.services;


import java.util.List;

import com.soundify.dtos.artists.ArtistSigninRequestDTO;
import com.soundify.dtos.artists.ArtistSigninResponseDTO;
import com.soundify.dtos.artists.ArtistSignupRequestDTO;
import com.soundify.dtos.artists.ArtistSignupResponseDTO;
import com.soundify.entities.Artist;


public interface ArtistService {
	
	// add a method to add new emp details (MODIFIED)
		ArtistSignupResponseDTO addArtDetails(ArtistSignupRequestDTO art);// i/p : dto , with no id field 
	
	//add a method for signin
	ArtistSigninResponseDTO signInArtist(ArtistSigninRequestDTO request);
	
	
	
	public ArtistSigninResponseDTO updateArtist(ArtistSignupResponseDTO artist, Long Id) ;

	void addSongToArtist(Long artistId, Long songId);

	void removeSongFromArtist(Long artistId, Long songId);

}
