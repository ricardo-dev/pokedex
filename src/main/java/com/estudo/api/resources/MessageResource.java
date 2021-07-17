package com.estudo.api.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.estudo.api.dto.MessageDto;

@RestController
public class MessageResource {
	
	@Autowired
	private SimpMessagingTemplate simp;
	
	@MessageMapping("/chat/{chatId}")
	public void sendMessage(@DestinationVariable Long chatId, @Payload MessageDto message) {
		System.out.println("handling send message: "+message+ " to: "+chatId);
		//simp.convertAndSend("/queue/messages/"+chatId, this.chatService.salvarMensagem(to, message));
		simp.convertAndSend("/queue/messages/"+chatId, message);
	}

}
