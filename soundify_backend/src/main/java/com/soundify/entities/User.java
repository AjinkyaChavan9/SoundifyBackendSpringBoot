package com.soundify.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users") // to specify table name
@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@ToString
public class User extends BaseEntity {
	@Column(name = "first_name", length=50)
	private String firstName;
	@Column(name = "last_name", length=50)
	private String lastName;
	@Column(length=60, unique = true)
	private String email;
	@Column(length=50)
	private String password;
	@Column(name="date_of_birth")
	private LocalDate dateOfBirth;
	
	
	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;
	

	@ManyToMany(mappedBy = "followers")
	private Set<Artist> artistsFollowed = new HashSet<>();

	@ManyToMany(mappedBy = "users" )
	private Set<Song> songsLiked = new HashSet<>();
	
	@ManyToMany
	@JoinTable(name="user_playlist", joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name="playlist_id"))
	private Set<Playlist> playlists = new HashSet<>();
	
	
	public void likeSong(Song song)
	{
		 Set<Song> likedSongs = new HashSet<>();
		 likedSongs.add(song);
		 this.setSongsLiked(likedSongs);
		 Set<User> userWhoLiked = new HashSet<>();
		 userWhoLiked.add(this);
		 song.setUsers(userWhoLiked);
	}
	
	
	
}
