package com.soundify.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soundify.custom_exceptions.ResourceNotFoundException;
import com.soundify.daos.PlaylistDao;
import com.soundify.daos.SongDao;
import com.soundify.entities.Playlist;
import com.soundify.entities.Song;


@Service
@Transactional
public class PlaylistServiceImpl implements PlaylistService {
	
		@Autowired
		PlaylistDao playlistDao;
		
		@Autowired
		SongDao songDao;
		
		
		@Override
		public void addSongToPlaylist(Long playlistId, Long songId) {
			Playlist playlist = playlistDao
					.findById(playlistId).orElseThrow(()-> new ResourceNotFoundException("Playlist Not Found!!"));
			Song song = songDao
					.findById(songId).orElseThrow(()-> new ResourceNotFoundException("Playlist Not Found!!"));
			
			playlist.addSong(song);
			
		}
		@Override
		public void removeSongFromPlaylist(Long playlistId, Long songId) {
			Playlist playlist = playlistDao
					.findById(playlistId).orElseThrow(()-> new ResourceNotFoundException("Playlist Not Found!!"));
			Song song = songDao
					.findById(songId).orElseThrow(()-> new ResourceNotFoundException("Playlist Not Found!!"));
			
			playlist.removeSong(song);
			
		}
		
}
