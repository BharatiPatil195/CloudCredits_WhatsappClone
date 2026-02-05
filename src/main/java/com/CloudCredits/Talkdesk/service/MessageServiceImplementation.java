package com.CloudCredits.Talkdesk.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.CloudCredits.Talkdesk.Exception.ChatException;
import com.CloudCredits.Talkdesk.Exception.MessageException;
import com.CloudCredits.Talkdesk.Exception.UserException;
import com.CloudCredits.Talkdesk.Modal.Chat;
import com.CloudCredits.Talkdesk.Modal.Message;
import com.CloudCredits.Talkdesk.Modal.User;
import com.CloudCredits.Talkdesk.Repository.MessageRepository;
import com.CloudCredits.Talkdesk.Request.SendMessageRequest;

@Service
public class MessageServiceImplementation implements MessageService{
	
	private MessageRepository messageRepository;
	private UserService userService;
	private ChatService chatService;
	
	public MessageServiceImplementation( MessageRepository messageRepository,UserService userService, ChatService chatService) {
		this.messageRepository=messageRepository;
		this.userService=userService;
		this.chatService=chatService;
	}

	@Override
	public Message sendMessage(SendMessageRequest req) throws UserException, ChatException {
		
		User user=userService.findUserById(req.getUserId());
		Chat chat=chatService.findChatById(req.getChatId());
		
		Message message=new Message();
		message.setChat(chat);
		message.setUser(user);
		message.setContent(req.getContent());
		message.setTimestamp(LocalDateTime.now());

		return message;
	}

	@Override
	public List<Message> getChatsMessage(Integer chatId,User reqUser) throws ChatException, UserException {
		Chat chat=chatService.findChatById(chatId);
		
		if(!chat.getUsers().contains(reqUser)) {
			throw new UserException("you are not releted to this chat "+chat.getId());
		}
		List<Message> message=messageRepository.findByChatId(chat.getId());

		return message;
	}

	@Override
	public Message findMessageById(Integer messageId) throws MessageException {
          Optional<Message> opt=messageRepository.findById(messageId);
          if(opt.isPresent()) {
        	  return opt.get();
          }
          throw new MessageException("message not found with id "+ messageId);

	}

	@Override
	public void deleteMessage(Integer messageId,User reqUser) throws MessageException, UserException {
         Message message=findMessageById(messageId);
         if(message.getUser().getId().equals(reqUser.getId())) {
        	 messageRepository.deleteById(messageId);
         }
         throw new UserException("you can't delete another user's message"+ reqUser.getFull_name());
	}

}
