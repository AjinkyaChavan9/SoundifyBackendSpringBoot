package com.soundify.services;
import com.soundify.entities.*;
import com.soundify.custom_exceptions.ResourceNotFoundException;
import com.soundify.daos.RoleDao;
import com.soundify.daos.SongDao;
import com.soundify.daos.UserDao;
import com.soundify.dtos.user.UserSignInRequestDTO;
import com.soundify.dtos.user.UserSignInResponseDTO;
import com.soundify.dtos.user.UserSignUpRequestDTO;
import com.soundify.dtos.user.UserSignupResponseDTO;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		 User existingUser = userDao.findById(userId)
		            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

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
	
	 public void likeSong(Long userId, Long songId){
		 User user = userDao.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found!"));
		 Song song = songDao.findById(songId).orElseThrow(()->new ResourceNotFoundException("Song Not Found!"));
		 Set<Song> songsLiked = user.getSongsLiked();
		 Set<User> usersWhoLiked = song.getUsers();
		 user.likeSong(song,songsLiked, usersWhoLiked);
		 
	 }
	 
	 public void unLikeSong(Long userId, Long songId){
		 User user = userDao.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found!"));
		 Song dislikedsong = songDao.findById(songId).orElseThrow(()->new ResourceNotFoundException("Song Not Found!"));
		 
		 Set<Song> songsLiked = user.getSongsLiked();
		 Set<User> usersWhoLiked = dislikedsong.getUsers();
		 
		 if(!(songsLiked.remove(dislikedsong))) {
			 throw new ResourceNotFoundException("Song is Not Liked By User!!");
		 }
		 if( !(usersWhoLiked.remove(user)))
		 {
			 throw new ResourceNotFoundException("Song is Not Liked By User!!");
		 }
		
		 
	 }
	    	
	    	

	
	

	}



