package com.soundify.services;

import com.soundify.entities.Genre;
import com.soundify.entities.Song;
import com.soundify.daos.SongDao;
import com.soundify.dtos.song.SongDTO;
import com.soundify.daos.GenreDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SongServiceImpl implements SongService {

	


	@Autowired
	private SongDao songDao;

	@Override
	public List<SongDTO> findSongsByGenreName(String genreName) {
		
		  List<Song> songs = songDao.findByGenresGenreName(genreName); 

		 
		List<SongDTO> songDTOs = new ArrayList<>();

	    for (Song song : songs) {
	        songDTOs.add(convertToDto(song));
	    }

	    return songDTOs;
	}

	private SongDTO convertToDto(Song song) {
	    SongDTO songDTO = new SongDTO();
	    songDTO.setId(song.getId());
	    songDTO.setSongName(song.getSongName());
	    songDTO.setDuration(song.getDuration());
	    songDTO.setReleaseDate(song.getReleaseDate());
	    songDTO.setSongPath(song.getSongPath());
	    songDTO.setSongImagePath(song.getSongImagePath());
	  
	    return songDTO;
	}

}
