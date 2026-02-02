package com.CloudCredits.Talkdesk.service;

import java.util.List;

import com.CloudCredits.Talkdesk.Exception.UserException;
import com.CloudCredits.Talkdesk.Modal.User;
import com.CloudCredits.Talkdesk.Request.UpdateUserRequest;

public interface UserService {
	
	public User findUserById(Integer id);
	
	public User findUserProfile(String jwt);
	
	public User updateUser(Integer userId, UpdateUserRequest req) throws UserException;
	
	public List<User> searchUser(String query);
	
	

}
