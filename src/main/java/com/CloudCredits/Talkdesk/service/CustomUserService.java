package com.CloudCredits.Talkdesk.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.CloudCredits.Talkdesk.Modal.User;
import com.CloudCredits.Talkdesk.Repository.UserRepository;
@Service
public class CustomUserService implements UserDetailsService{
	
	private UserRepository repository;

	public CustomUserService( UserRepository repository) {
		this.repository=repository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	 User user=repository.findByEmail(username);
	 if(user==null) {
		 throw new UsernameNotFoundException("User not found with username :"+username);
	 }
		List<GrantedAuthority> authorities=new ArrayList<>();
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
	}

}
