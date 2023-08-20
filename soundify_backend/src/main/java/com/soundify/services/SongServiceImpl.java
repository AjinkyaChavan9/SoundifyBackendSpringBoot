package com.soundify.services;

import com.soundify.entities.Song;
import com.soundify.custom_exceptions.ResourceNotFoundException;
import com.soundify.daos.SongDao;
import com.soundify.dtos.song.SongDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongServiceImpl implements SongService {

	


	@Autowired
	private SongDao songDao;

	@Override
	public List<SongDTO> findSongsByGenreName(String genreName) {
	    List<Song> songs = songDao.findByGenresGenreName(genreName);

	    if (songs.isEmpty()) {
	        throw new ResourceNotFoundException("Songs not found for genre: " + genreName);
	    }

	    List<SongDTO> songDTOs = new ArrayList<>();

	    for (Song song : songs) {
	        SongDTO songDTO = new SongDTO();
	        songDTO.setId(song.getId());
	        songDTO.setSongName(song.getSongName());
	        songDTO.setDuration(song.getDuration());
	        songDTO.setReleaseDate(song.getReleaseDate());
	        songDTO.setSongPath(song.getSongPath());
	        songDTO.setSongImagePath(song.getSongImagePath());
	        songDTOs.add(songDTO);
	    }

	    return songDTOs;
	}

	
	  
	       
	    
	 @Autowired
	    public SongServiceImpl(SongDao songDao) {
	        this.songDao = songDao;
	    }

	@Override
	public List<SongDTO> findByArtistsName(String name) {
		 List<Song> songs = songDao.findByArtistsName(name);
	        List<SongDTO> songDTOs = new ArrayList<>();
	        if (songs.isEmpty()) {
	            throw new ResourceNotFoundException("Songs not found for Artist : " +name);
	        }
	        for (Song song : songs) {
	            SongDTO songDTO = new SongDTO();
	            songDTO.setId(song.getId());
	            songDTO.setSongName(song.getSongName());
	            songDTO.setDuration(song.getDuration());
	            songDTOs.add(songDTO);
	        }

	        return songDTOs;
	}
	}


