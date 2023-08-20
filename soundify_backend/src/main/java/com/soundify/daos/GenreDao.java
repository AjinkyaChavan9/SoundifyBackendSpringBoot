package com.soundify.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soundify.entities.Genre;

public interface GenreDao extends JpaRepository<Genre, Long> {

}
