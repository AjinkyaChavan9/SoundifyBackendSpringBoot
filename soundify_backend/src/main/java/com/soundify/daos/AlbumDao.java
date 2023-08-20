package com.soundify.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soundify.entities.Album;

public interface AlbumDao extends JpaRepository<Album, Long> {

}
