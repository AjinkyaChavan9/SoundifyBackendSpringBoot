package com.soundify.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soundify.custom_exceptions.ResourceNotFoundException;
import com.soundify.daos.PlaylistDao;
import com.soundify.daos.SongDao;
import com.soundify.daos.UserDao;
import com.soundify.dtos.playlists.PlaylistResponseDTO;
import com.soundify.dtos.song.SongDTO;
import com.soundify.entities.Playlist;
import com.soundify.entities.Song;
import com.soundify.entities.User;

@Service
@Transactional
public class PlaylistServiceImpl implements PlaylistService {

	@Autowired
	private PlaylistDao playlistDao;

	@Autowired
	private SongDao songDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private ModelMapper mapper;

	@Override
	public void addSongToPlaylist(Long playlistId, Long songId) {
		Playlist playlist = playlistDao.findById(playlistId)
				.orElseThrow(() -> new ResourceNotFoundException("Playlist Not Found!!"));
		Song song = songDao.findById(songId).orElseThrow(() -> new ResourceNotFoundException("Playlist Not Found!!"));

		playlist.addSong(song);

	}

	@Override
	public void removeSongFromPlaylist(Long playlistId, Long songId) {
		Playlist playlist = playlistDao.findById(playlistId)
				.orElseThrow(() -> new ResourceNotFoundException("Playlist Not Found!!"));
		Song song = songDao.findById(songId).orElseThrow(() -> new ResourceNotFoundException("Playlist Not Found!!"));

		playlist.removeSong(song);

	}

	@Override
	public List<PlaylistResponseDTO> getAllplaylistsByUserId(Long userId) {
		User user = userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User dose not exists"));
		List<Playlist> playlists = user.getPlaylists();

		return playlists.stream().map(playlist -> mapper.map(playlist, PlaylistResponseDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<SongDTO> getAllSongsByPlaylistId(Long playlistId) {
		Playlist playlist = playlistDao.findById(playlistId)
				.orElseThrow(() -> new ResourceNotFoundException("Playlist not dose exists"));
		Set<Song> songs = playlist.getSongs();

		return songs.stream().map(song -> mapper.map(song, SongDTO.class)).collect(Collectors.toList());
	}

	public void updatePlaylistName(Long playlistId, String playlistName) {
		Playlist playlist = playlistDao.findById(playlistId)
				.orElseThrow(() -> new ResourceNotFoundException("Playlist Not Found!!"));
		playlist.setPlaylistName(playlistName);

	}

}
