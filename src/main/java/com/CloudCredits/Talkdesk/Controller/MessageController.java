package com.CloudCredits.Talkdesk.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CloudCredits.Talkdesk.Exception.ChatException;
import com.CloudCredits.Talkdesk.Exception.MessageException;
import com.CloudCredits.Talkdesk.Exception.UserException;
import com.CloudCredits.Talkdesk.Modal.Message;
import com.CloudCredits.Talkdesk.Modal.User;
import com.CloudCredits.Talkdesk.Request.SendMessageRequest;
import com.CloudCredits.Talkdesk.Response.ApiResponse;
import com.CloudCredits.Talkdesk.service.MessageService;
import com.CloudCredits.Talkdesk.service.UserService;

@RestController
@RequestMapping("/api/message")
public class MessageController {
	
	private MessageService messageService;
	private UserService userService;
	
	public MessageController(MessageService messageService,UserService userService) {
		this.messageService=messageService;
		this.userService=userService;
	}

	@PostMapping("/create")
	public ResponseEntity<Message> sendMessageHandler(@RequestBody SendMessageRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ChatException{
		User user=userService.findUserProfile(jwt);
		
		req.setUserId(user.getId());
		Message message=messageService.sendMessage(req);
		
		return new ResponseEntity<Message>(message,HttpStatus.OK);
		
	}
	
	@GetMapping("/chat/{chatId}")
	public ResponseEntity<List<Message>> getChatMessageHandler(@PathVariable Integer chatId,@RequestBody SendMessageRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ChatException{
		User user=userService.findUserProfile(jwt);
		
		List<Message> messages=messageService.getChatsMessage(chatId, user);
		
		return new ResponseEntity<>(messages,HttpStatus.OK);
		
	}
	@DeleteMapping("/{messageId}")
	public ResponseEntity<ApiResponse> deleteChatMessageHandler(@PathVariable Integer messageId,@RequestBody SendMessageRequest req, @RequestHeader("Authorization") String jwt) throws UserException, MessageException{
		User user=userService.findUserProfile(jwt);
		
		messageService.deleteMessage(messageId, user);
		ApiResponse res=new ApiResponse("message deleted successfully", false);
		
		return new ResponseEntity<>(res,HttpStatus.OK);
		
	}
}
