package com.soundify.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soundify.daos.GenreDao;
import com.soundify.entities.Genre;

@Service
public class GenreServiceImpl implements GenreService {

	 @Autowired
	    private GenreDao genreDao;

	@Override
	public Optional<Genre> findGenreByName(String genreName) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}


}
