package com.soundify.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soundify.daos.GenreDao;
import com.soundify.dtos.genres.GenreResponseDTO;
import com.soundify.entities.Genre;

@Service
@Transactional
public class GenreServiceImpl implements GenreService {

	@Autowired
	private GenreDao genreDao;

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

		return allGenre.stream()
				.map(genre -> mapper.map(genre, GenreResponseDTO.class))
				.collect(Collectors.toList());
	}

}
