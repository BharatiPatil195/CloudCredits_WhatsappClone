package com.CloudCredits.Talkdesk.service;

import java.util.List;

import com.CloudCredits.Talkdesk.Exception.ChatException;
import com.CloudCredits.Talkdesk.Exception.UserException;
import com.CloudCredits.Talkdesk.Modal.Chat;
import com.CloudCredits.Talkdesk.Modal.User;

public interface ChatService {

	public Chat creatChat(User reqUser, Integer userId2) throws UserException;
	
	public Chat findChatById(Integer ChatId) throws ChatException;
	
	public List<Chat> findAllChatByUserId(Integer userId) throws UserException;
	
	public Chat createGroup(GroupChatRequest req, User reqUser) throws UserException;
	
	public Chat addUserToGroup(Integer userId, Integer chatId,User reqUser) throws UserException, ChatException;
	
	public Chat renameGroup(Integer chatId, String groupName, User reqUserId) throws ChatException,UserException;
	
	public Chat removeFromGroup(Integer chatId, Integer userId,User reqUser) throws UserException, ChatException;
	
	public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException;
}
