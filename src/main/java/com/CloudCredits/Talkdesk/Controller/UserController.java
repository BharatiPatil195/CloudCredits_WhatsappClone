package com.CloudCredits.Talkdesk.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CloudCredits.Talkdesk.Exception.UserException;
import com.CloudCredits.Talkdesk.Modal.User;
import com.CloudCredits.Talkdesk.Request.UpdateUserRequest;
import com.CloudCredits.Talkdesk.Response.ApiResponse;
import com.CloudCredits.Talkdesk.service.UserService;

@RestController
@RequestMapping("/app/users")
public class UserController {

	private UserService service;
	
	public UserController(UserService service) {
		this.service=service;
	}
	
	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String token) throws UserException{
		
		User user=service.findUserProfile(token);
		
		return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
		
	}
	@GetMapping("/{query}")
	public ResponseEntity<List<User>> searchUserHandler(@PathVariable("query") String q){
		List<User> users=service.searchUser(q);
		
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
		
	}
	@PutMapping("/update")
	public ResponseEntity<ApiResponse>updateUserHandler(@RequestBody UpdateUserRequest req, @RequestHeader("Authorization") String token) throws UserException{
	
	User user=service.findUserProfile(token);
	service.updateUser(user.getId(),req);
	
	ApiResponse res=new ApiResponse("user updated successfully",true);
	
	return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
	
}
}
