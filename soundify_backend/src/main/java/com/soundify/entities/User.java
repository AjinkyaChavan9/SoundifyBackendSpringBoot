package com.soundify.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Playlist> playlists = new ArrayList<>();
	
	
	public void likeSong(Song song, Set<Song> likedSongs,  Set<User> userWhoLiked )
	{
	
		 likedSongs.add(song);
		 this.setSongsLiked(likedSongs);
		 userWhoLiked.add(this);
		 song.setUsers(userWhoLiked);
	}
	
	public void removeLikedSong(Song song) {
	    songsLiked.remove(song);
	    song.getUsers().remove(this);
	}
	
	public void followArtist(Artist artist, Set<Artist> artistsFollowed, Set<User> followers)
	{
		 
		 artistsFollowed.add(artist);
		 this.setArtistsFollowed(artistsFollowed);
		 followers.add(this);
		 artist.setFollowers(followers);
	}
	
	public void createPlaylist(Playlist newPlaylist) {
		playlists.add(newPlaylist);
		newPlaylist.setUser(this);
	}
	
	public void deletePlaylist(Playlist removePlaylist) {
		playlists.remove(removePlaylist);
		removePlaylist.setUser(this);
	}
	
	
}
