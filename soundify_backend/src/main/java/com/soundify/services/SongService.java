package com.soundify.services;

import com.soundify.dtos.song.SongDTO;
import com.soundify.entities.Song;

import java.util.List;

public interface SongService {

	List<SongDTO> findSongsByGenreName(String genreName);

	List<SongDTO> findSongByArtistsName(String name);

	List<SongDTO> findSongsBySongName(String songName);

	List<SongDTO> getAllSongs();

}
