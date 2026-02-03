package com.CloudCredits.Talkdesk.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CloudCredits.Talkdesk.Config.TokenProvider;
import com.CloudCredits.Talkdesk.Exception.UserException;
import com.CloudCredits.Talkdesk.Modal.User;
import com.CloudCredits.Talkdesk.Repository.UserRepository;
import com.CloudCredits.Talkdesk.Request.LoginRequest;
import com.CloudCredits.Talkdesk.Response.AuthResponse;
import com.CloudCredits.Talkdesk.service.CustomUserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private UserRepository repository;
	private PasswordEncoder passwordEncoder;
	private TokenProvider tokenprovider;
	private CustomUserService customUserService;
	
	public AuthController(UserRepository repository,PasswordEncoder passwordEncoder,TokenProvider tokenprovider,CustomUserService customUserService) {
		this.repository=repository;
		this.passwordEncoder=passwordEncoder;
		this.customUserService=customUserService;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException{
		String email=user.getEmail();
	    String full_name=user.getFull_name();
		String password=user.getPassword();
		
		User isUser=repository.findByEmail(email);
		if(isUser!=null) {
			throw new UserException("Email is user with another account"+email);
		}
		
	User createdUser=new User();
	createdUser.setEmail(email);
	createdUser.setFull_name(full_name);
	createdUser.setPassword(passwordEncoder.encode(password));
	
	repository.save(createdUser);
	
	Authentication authentication=new UsernamePasswordAuthenticationToken(email, password);
	SecurityContextHolder.getContext().setAuthentication(authentication);
	
	 String jwt=tokenprovider.generateToken(authentication);
	 
	 AuthResponse res=new AuthResponse(jwt, true);
	 return new ResponseEntity<AuthResponse>(res,HttpStatus.ACCEPTED);
	 
}
	
	public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest req){
		String email=req.getEmail();
		String password=req.getPassword();
		Authentication authentication=authenticate(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt=tokenprovider.generateToken(authentication);
		 
		AuthResponse res=new AuthResponse(jwt, true);
		return new ResponseEntity<AuthResponse>(res,HttpStatus.ACCEPTED);
		
	}
	
	public Authentication authenticate(String username, String password) {

	    UserDetails userDetails = customUserService.loadUserByUsername(username);

	    if (userDetails == null) {
	        throw new BadCredentialsException("invalid username");
	    }

	    if (!passwordEncoder.matches(password, userDetails.getPassword())) {
	        throw new BadCredentialsException("invalid password or username");
	    }

	    return new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
	            
	           
	}

}