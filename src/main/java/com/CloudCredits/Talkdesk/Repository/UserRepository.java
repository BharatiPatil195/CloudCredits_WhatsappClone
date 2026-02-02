package com.CloudCredits.Talkdesk.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CloudCredits.Talkdesk.Modal.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
