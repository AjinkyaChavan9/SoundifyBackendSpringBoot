package com.soundify.services;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.soundify.dtos.artists.ArtistResponseDTO;
import com.soundify.dtos.ApiResponse;
import com.soundify.dtos.artists.ArtistSigninRequestDTO;
import com.soundify.dtos.artists.ArtistSigninResponseDTO;
import com.soundify.dtos.artists.ArtistSignupRequestDTO;
import com.soundify.dtos.artists.ArtistSignupResponseDTO;
import com.soundify.dtos.song.SongDTO;
import com.soundify.dtos.song.SongUpdateMetadataDTO;

public interface ArtistService {

	// add a method to add new emp details (MODIFIED)
	ArtistSignupResponseDTO addArtDetails(ArtistSignupRequestDTO art);// i/p : dto , with no id field

	// add a method for signin
	ArtistSigninResponseDTO signInArtist(ArtistSigninRequestDTO request);

	public ArtistSigninResponseDTO updateArtist(ArtistSignupResponseDTO artist, Long Id);

	void addSongToArtist(Long artistId, Long songId);

	void removeSongFromArtist(Long artistId, Long songId);

	ArtistResponseDTO getArtistDetails(Long artistId);

	List<ArtistResponseDTO> getArtists();

	ApiResponse deleteArtistById(Long artistId);

	Set<SongDTO> getAllSongsOfArtist(Long artistId);

	void updateSongMetadata(Long artistId, Long songId, SongUpdateMetadataDTO songMetadataUpdateDTO);

	public ApiResponse uploadArtistImage(Long artistId, MultipartFile imageFile) throws IOException;

	public ApiResponse editArtistImage(Long artistId, MultipartFile imageFile) throws IOException;

}
