package com.soundify.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soundify.custom_exceptions.ResourceNotFoundException;
import com.soundify.daos.GenreDao;
import com.soundify.daos.SongDao;
import com.soundify.dtos.genres.GenreResponseDTO;
import com.soundify.entities.Genre;
import com.soundify.entities.Song;

@Service
@Transactional
public class GenreServiceImpl implements GenreService {

	@Autowired
	private GenreDao genreDao;

	@Autowired
	private SongDao songDao;

	@Autowired
	private ModelMapper mapper;

	@Override
	public Optional<Genre> findGenreByName(String genreName) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<GenreResponseDTO> getAllGenre() {
		List<Genre> allGenre = genreDao.findAll();

		return allGenre.stream().map(genre -> mapper.map(genre, GenreResponseDTO.class)).collect(Collectors.toList());
	}

	@Override
	public void addGenre(String genreName) {
		Genre genre = new Genre();
		genre.setGenreName(genreName);
		genreDao.save(genre);

	}

	@Override
	public GenreResponseDTO getGenreById(Long genreId) {
		Genre genre = genreDao.findById(genreId).orElseThrow(() -> new ResourceNotFoundException("Genre Not Found!"));

		return mapper.map(genre, GenreResponseDTO.class);
	}

	@Override
	public void updateGenreName(Long genreId, String updatedGenreName) {
		Genre genre = genreDao.findById(genreId).orElseThrow(() -> new ResourceNotFoundException("Genre Not Found!"));
		genre.setGenreName(updatedGenreName);
	}

	@Override
	public void deleteGenre(Long genreId) {
		Genre genre = genreDao.findById(genreId).orElseThrow(() -> new ResourceNotFoundException("Genre Not Found!"));
		genreDao.delete(genre);

	}

	@Override
	public void addSongToGenre(Long genreId, Long songId) {
		Genre genre = genreDao.findById(genreId).orElseThrow(() -> new ResourceNotFoundException("Genre Not Found!!"));
		Song song = songDao.findById(songId).orElseThrow(() -> new ResourceNotFoundException("Playlist Not Found!!"));

		genre.addSong(song);

	}

	@Override
	public void removeSongFromGenre(Long genreId, Long songId) {
		Genre genre = genreDao.findById(genreId).orElseThrow(() -> new ResourceNotFoundException("Genre Not Found!!"));
		Song song = songDao.findById(songId).orElseThrow(() -> new ResourceNotFoundException("Playlist Not Found!!"));
		genre.removeSong(song);

	}

}
