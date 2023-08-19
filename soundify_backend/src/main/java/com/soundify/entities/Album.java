package com.soundify.entities;

import java.time.LocalDate;
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
@Table(name = "albums") // to specify table name
@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@ToString
public class Album extends BaseEntity {
   
	@Column(name = "album_name", length = 50)
	private String albumName;
	
	@Column(name = "album_release_date")
	private LocalDate albumReleaseDate;
	
	@ManyToMany
	@JoinTable(name="album_song", joinColumns = @JoinColumn(name="album_id"), inverseJoinColumns = @JoinColumn(name="song_id"))
	private Set<Song> songs = new HashSet<>();
	
}
