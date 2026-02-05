package com.CloudCredits.Talkdesk.service;

import java.util.List;

import com.CloudCredits.Talkdesk.Exception.ChatException;
import com.CloudCredits.Talkdesk.Exception.MessageException;
import com.CloudCredits.Talkdesk.Exception.UserException;
import com.CloudCredits.Talkdesk.Modal.Message;
import com.CloudCredits.Talkdesk.Modal.User;
import com.CloudCredits.Talkdesk.Request.SendMessageRequest;

public interface MessageService {
	
	
	public  Message sendMessage(SendMessageRequest req) throws UserException, ChatException;
	
	public List<Message> getChatsMessage(Integer chatId, User reqUser ) throws ChatException, UserException;
	
	public Message findMessageById(Integer messageId) throws MessageException;
	
	public void deleteMessage(Integer messageId, User reqUser) throws MessageException, UserException;

}
