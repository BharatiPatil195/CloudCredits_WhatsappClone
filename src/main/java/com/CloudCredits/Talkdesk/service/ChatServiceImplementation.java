package com.CloudCredits.Talkdesk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.CloudCredits.Talkdesk.Exception.ChatException;
import com.CloudCredits.Talkdesk.Exception.UserException;
import com.CloudCredits.Talkdesk.Modal.Chat;
import com.CloudCredits.Talkdesk.Modal.User;
import com.CloudCredits.Talkdesk.Repository.ChatRepository;

@Service
public class ChatServiceImplementation implements ChatService{
	
	private ChatRepository chatRepository;
	private UserService userservice;

	
	public ChatServiceImplementation(ChatRepository chatRepository,UserService userservice) {
		this.chatRepository=chatRepository;
		this.userservice=userservice;
	}
	@Override
	public Chat creatChat(User reqUser, Integer userId2) throws UserException {
		
		User user=userservice.findUserById(userId2);
		
		Chat isChatExist=chatRepository.findSingleChatByUserIds(user, reqUser);
		
		if(isChatExist!=null) {
			return isChatExist;
		}
		
		Chat chat=new Chat();
		chat.setCreatedBy(reqUser);
		chat.getUsers().add(user);
		chat.getUsers().add(reqUser);
		chat.setGroup(false);
				
		return chat;
	}

	@Override
	public Chat findChatById(Integer ChatId) throws ChatException {
		Optional<Chat> chat=chatRepository.findById(ChatId);
		
		if(chat.isPresent()) {
			return chat.get();	
		}
		throw new ChatException("Chat not found with id"+ChatId);
		
	}

	@Override
	public List<Chat> findAllChatByUserId(Integer userId) throws UserException {
		
		User user=userservice.findUserById(userId);
		
		List<Chat> chats=chatRepository.findChatByUserid(user.getId());

		return chats;
	}

	@Override
	public Chat createGroup(GroupChatRequest req, User reqUser) throws UserException {
		Chat group=new Chat();
		group.setGroup(true);
		group.setChat_image(req.getChat_image());
		group.setChat_name(req.getChat_name());
		group.setCreatedBy(reqUser);
		group.getAdmins().add(reqUser);
		
		for(Integer userId:req.getUserIds()) {
			User user=userservice.findUserById(userId);
			group.getUsers().add(user);
			
		}
		return group;
	}

	@Override
	public Chat addUserToGroup(Integer userId, Integer chatId,User reqUser) throws UserException, ChatException {
		Optional<Chat> opt=chatRepository.findById(chatId);
		User user=userservice.findUserById(userId);
		
		if(opt.isPresent()) {
			Chat chat=opt.get();
			
			if(chat.getAdmins().contains(reqUser)) {
				chat.getUsers().add(user);
				return chat;
			}
			else {
				throw new UserException("you are not admin");
			}
			
		}
			
		throw new ChatException("chat not found with id"+chatId);
		
	}

	@Override
	public Chat renameGroup(Integer chatId, String groupName, Integer reqUserId) throws ChatException, UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Chat removeFromGroup(Integer chatId, Integer userId, Integer reqUser) throws UserException, ChatException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Chat deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {
		// TODO Auto-generated method stub
		return null;
	}

}
