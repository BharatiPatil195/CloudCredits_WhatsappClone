package com.CloudCredits.Talkdesk.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.CloudCredits.Talkdesk.Modal.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByEmail(String email);
	
	@Query("select u from User u where u.full_name Like %:query% 0r u.email %:query% ")
	public List<User>searchUser(@Param("query") String query);
	
}
