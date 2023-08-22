package com.soundify.services;

import java.util.List;
import java.util.Optional;

import com.soundify.dtos.genres.GenreResponseDTO;
import com.soundify.entities.Genre;

public interface GenreService {
	Optional<Genre> findGenreByName(String genreName);

	List<GenreResponseDTO> getAllGenre();

	void addGenre(String genreName);

	void updateGenreName(Long genreId, String updatedGenreName);

	void deleteGenre(Long genreId);

	void addSongToGenre(Long genreId, Long songId);

	void removeSongFromGenre(Long genreId, Long songId);

	GenreResponseDTO getGenreById(Long genreId);
}
