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
@Table(name = "artists") // to specify table name
@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@ToString
public class Artist extends BaseEntity{
	
	@Column(name = "artist_first_name", length=50)
	private String firstName;
	@Column(name = "artist_last_name", length=50)
	private String lastName;
	@Column(length=60, unique = true)
	private String email;
	@Column(length=50)
	private String password;
	@Column(name="date_of_birth")
	private LocalDate dateOfBirth;
	
	@ManyToMany
	@JoinTable(name="artist_song", joinColumns = @JoinColumn(name="artist_id"), inverseJoinColumns = @JoinColumn(name="song_id"))
	private Set<Song> songs = new HashSet<>();
	
	@ManyToMany
	@JoinTable(name="artist_follower", joinColumns = @JoinColumn(name="artist_id"), inverseJoinColumns = @JoinColumn(name="user_id"))
	private Set<User> followers = new HashSet<>();
		
	public void addSong(Song song) {
        songs.add(song);
        song.getArtists().add(this);
    }

    public void removeSong(Song song) {
        songs.remove(song);
        song.getArtists().remove(this);
    }
}
