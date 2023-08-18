package com.soundify.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "playlist") // to specify table name
@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@ToString
public class Playlist extends BaseEntity {

	@Column(name = "playlist_name", length = 50)
	private String playlistName;
	
	@ManyToMany(mappedBy = "playlists" )
	private Set<User> users = new HashSet<>();
	
	@ManyToMany
	@JoinTable(name="playlist_song", joinColumns = @JoinColumn(name="playlist_id"), inverseJoinColumns = @JoinColumn(name="song_id"))
	private Set<Song> songs = new HashSet<>();
}
