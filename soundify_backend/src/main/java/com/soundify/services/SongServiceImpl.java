package com.soundify.services;

import com.soundify.entities.Song;
import com.soundify.custom_exceptions.ResourceNotFoundException;
import com.soundify.daos.SongDao;
import com.soundify.dtos.song.SongDTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongServiceImpl implements SongService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private SongDao songDao;

	@Override
	public List<SongDTO> findSongsByGenreName(String genreName) {
		List<Song> songs = songDao.findByGenresGenreName(genreName);

		if (songs.isEmpty()) {
			throw new ResourceNotFoundException("Songs not found for genre: " + genreName);
		}

		return songs.stream().map(song -> mapper.map(song, SongDTO.class)).collect(Collectors.toList());
	}

	@Override
	public List<SongDTO> findSongByArtistsName(String name) {
		List<Song> songs = songDao.findByArtistsName(name);
		if (songs.isEmpty()) {
			throw new ResourceNotFoundException("Songs not found for Artist : " + name);
		}
		// mapper map

		return songs.stream().map(song -> mapper.map(song, SongDTO.class)).collect(Collectors.toList());

	}

	@Override
	public List<SongDTO> findSongsBySongName(String songName) {
		List<Song> songs = songDao.findBySongNameContainingIgnoreCase(songName);

		return songs.stream().map(song -> mapper.map(song, SongDTO.class)).collect(Collectors.toList());

	}

	@Override
	public List<SongDTO> getAllSongs() {
		List<Song> songs =songDao.findAll();
		return songs.stream().map(song -> mapper.map(song, SongDTO.class)).collect(Collectors.toList());

	}

	
}
