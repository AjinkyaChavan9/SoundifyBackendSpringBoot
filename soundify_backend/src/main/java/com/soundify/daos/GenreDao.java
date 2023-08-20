package com.soundify.daos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soundify.entities.Genre;

public interface GenreDao extends JpaRepository<Genre, Long> {
	Optional<Genre> findByGenreName(String genreName);
}
