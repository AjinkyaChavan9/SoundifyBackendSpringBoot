package com.soundify.services;

import com.soundify.entities.*;
import com.soundify.custom_exceptions.ResourceNotFoundException;
import com.soundify.daos.ArtistDao;
import com.soundify.daos.PlaylistDao;
import com.soundify.daos.RoleDao;
import com.soundify.daos.SongDao;
import com.soundify.daos.UserDao;
import com.soundify.dtos.ApiResponse;
import com.soundify.dtos.artists.ArtistResponseDTO;
import com.soundify.dtos.artists.ArtistSigninResponseDTO;
import com.soundify.dtos.playlists.PlaylistResponseDTO;
import com.soundify.dtos.song.SongDTO;
import com.soundify.dtos.user.UserSignInRequestDTO;
import com.soundify.dtos.user.UserSignInResponseDTO;
import com.soundify.dtos.user.UserSignUpRequestDTO;
import com.soundify.dtos.user.UserSignupResponseDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao; // Inject RoleDao

	@Autowired
	private SongDao songDao;

	@Autowired
	private ArtistDao artistDao;

	@Autowired
	private PlaylistDao playlistDao;

	@Autowired
	private ModelMapper mapper;

	@Override
	public UserSignupResponseDTO addUser(UserSignUpRequestDTO user) {
		User newUser = mapper.map(user, User.class);

		Role customerRole = roleDao.findById((long) 2)
				.orElseThrow(() -> new ResourceNotFoundException("Role with id 2 not found"));

		newUser.setRole(customerRole);
		newUser = userDao.save(newUser);
		return mapper.map(newUser, UserSignupResponseDTO.class);
	}

	@Override
	public UserSignInResponseDTO signInUser(UserSignInRequestDTO request) {
		User user = userDao.findByEmailAndPassword(request.getEmail(), request.getPassword())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Email or Password !!!!"));
		return mapper.map(user, UserSignInResponseDTO.class);
	}

	@Override
	public UserSignInResponseDTO updateUser(UserSignupResponseDTO user, Long userId) {
		User existingUser = userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

		// Update user properties based on the updatedUser DTO
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setEmail(user.getEmail());
		existingUser.setDateOfBirth(user.getDateOfBirth());
		// Update other properties as needed

		// Save the updated user
		existingUser = userDao.save(existingUser);

		return mapper.map(existingUser, UserSignInResponseDTO.class);
	}

	@Override
	public UserSignInResponseDTO updateUserPassword(Long userId, String givenPassword, String newPassword) {
		User user = userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));
//		System.out.println("in updateuserpassword");
//		System.out.println("current password" + user.getPassword());
//		System.out.println("given password" + givenPassword);
		if (!user.getPassword().equals(givenPassword)) {
			throw new ResourceAccessException("User Password Incorrect");
		} else {
			user.setPassword(newPassword);
		}
		return mapper.map(user, UserSignInResponseDTO.class);
	}

	public void likeSong(Long userId, Long songId) {
		User user = userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));
		Song song = songDao.findById(songId).orElseThrow(() -> new ResourceNotFoundException("Song Not Found!"));
		Set<Song> songsLiked = user.getSongsLiked();
		Set<User> usersWhoLiked = song.getUsers();
		user.likeSong(song, songsLiked, usersWhoLiked);

	}

	public void unLikeSong(Long userId, Long songId) {
		User user = userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));
		Song dislikedsong = songDao.findById(songId)
				.orElseThrow(() -> new ResourceNotFoundException("Song Not Found!"));

		Set<Song> songsLiked = user.getSongsLiked();
		Set<User> usersWhoLiked = dislikedsong.getUsers();

		if (!(songsLiked.remove(dislikedsong))) {
			throw new ResourceNotFoundException("Song is Not Liked By User!!");
		}
		if (!(usersWhoLiked.remove(user))) {
			throw new ResourceNotFoundException("Song is Not Liked By User!!");
		}

	}

	@Override
	public void followArtist(Long userId, Long artistId) {
		User user = userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));
		Set<Artist> artistsFollowed = user.getArtistsFollowed();

		Artist artist = artistDao.findById(artistId)
				.orElseThrow(() -> new ResourceNotFoundException("Artist Not Found!"));
		Set<User> followers = artist.getFollowers();
		user.followArtist(artist, artistsFollowed, followers);

	}

	@Override
	public void unFollowArtist(Long userId, Long artistId) {
		User user = userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));

		Set<Artist> artistFollowed = user.getArtistsFollowed();
		Artist unfollowedArtist = artistDao.findById(artistId)
				.orElseThrow(() -> new ResourceNotFoundException("Artist Not Found!"));

		if (!artistFollowed.contains(unfollowedArtist)) {
			throw new ResourceNotFoundException("Artist is not followed by the user");
		}

		Set<User> usersWhoFollowed = unfollowedArtist.getFollowers();
		usersWhoFollowed.remove(user);
		artistFollowed.remove(unfollowedArtist);
	}

	@Override
	public PlaylistResponseDTO createPlaylist(Long userId, String playlistName) {
		User user = userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));
		Playlist newPlaylist = new Playlist();
		newPlaylist.setPlaylistName(playlistName);
		Playlist savedPlaylist = playlistDao.save(newPlaylist);
		savedPlaylist.setUser(user);
		return mapper.map(savedPlaylist, PlaylistResponseDTO.class);
	}

	@Override
	public void deletePlaylist(Long userId, Long playlistId) {
		User user = userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));
		Playlist removePlaylist = playlistDao.findById(playlistId)
				.orElseThrow(() -> new ResourceNotFoundException("Playlist Not Found!"));

		user.deletePlaylist(removePlaylist);

	}

	@Override
	public List<UserSignupResponseDTO> getUsers() {
		List<User> users = userDao.findAll();
		return users.stream().map(user -> mapper.map(user, UserSignupResponseDTO.class)).collect(Collectors.toList());
	}

	@Override
	public ApiResponse deleteUserById(Long userId) {
		User user = userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));
		userDao.delete(user);
		return new ApiResponse("user deleted successfully");
	}

	@Override
	public Set<ArtistResponseDTO> getFollowedArtists(Long userId) {
		User user = userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));

		Set<Artist> followedArtists = user.getArtistsFollowed();
		if (followedArtists.isEmpty()) {
			throw new ResourceNotFoundException("No followed artists found for given user.");
		}
		return followedArtists.stream().map(artist -> mapper.map(artist, ArtistResponseDTO.class))
				.collect(Collectors.toSet());
	}

	@Override
	public Set<SongDTO> getLikedSongs(Long userId) {
		User user = userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User  Not Found!"));
		Set<Song> likedSongs = user.getSongsLiked();
		if (likedSongs.isEmpty()) {
			throw new ResourceNotFoundException("No liked songs found for given user.");
		}
		return likedSongs.stream().map(song -> mapper.map(song, SongDTO.class)).collect(Collectors.toSet());

	}

}
