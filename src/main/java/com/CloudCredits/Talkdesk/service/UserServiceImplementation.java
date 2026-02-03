package com.CloudCredits.Talkdesk.service;


import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.CloudCredits.Talkdesk.Config.TokenProvider;
import com.CloudCredits.Talkdesk.Exception.UserException;
import com.CloudCredits.Talkdesk.Modal.User;
import com.CloudCredits.Talkdesk.Repository.UserRepository;
import com.CloudCredits.Talkdesk.Request.UpdateUserRequest;

@Service
public class UserServiceImplementation implements UserService{

	private UserRepository repository;
	private TokenProvider provider;
	
	public UserServiceImplementation(UserRepository repository, TokenProvider provider) {
	  this.repository=repository;
	  this.provider=provider;
	}
	@Override
	public User findUserById(Integer id) throws UserException {
     Optional<User> opt=repository.findById(id);
     
     if(opt.isPresent()) {
    	 return opt.get();    
    	 }
     throw new UserException("User not found with id"+id);
     
	}

	@Override
	public User findUserProfile(String jwt) throws UserException {
		String email=provider.getEmailfromToken(jwt);
		
		if(email!=null) {
			throw new BadCredentialsException("recived invalid token---");
		
		}
		User user=repository.findByEmail(email);
		if(user==null) {
			throw new UserException("user not found with email"+email);
		}
		return user;
	}

	@Override
	public  User updateUser(Integer userId, UpdateUserRequest req) throws UserException {
	User user=findUserById(userId);
	
	if(req.getFull_name()!=null) {
		user.setFull_name(req.getFull_name());
	}
	if(req.getProfile_picture()!=null) {
		user.setProfile_picture(req.getProfile_picture());
	}
		return repository.save(user);
	}

	@Override
	public List<User> searchUser(String query) {
		List<User> users=repository.searchUser(query);
		return users;
	}

}
