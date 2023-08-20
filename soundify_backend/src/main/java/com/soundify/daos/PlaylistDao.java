package com.soundify.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soundify.entities.Playlist;

public interface PlaylistDao extends JpaRepository<Playlist, Long>{

}
