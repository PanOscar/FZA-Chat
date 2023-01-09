package com.fza.springrestchat.controllers;

import com.fza.springrestchat.models.Messages;
import com.fza.springrestchat.repositories.MessagesRepository;
import com.fza.springrestchat.services.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RestController
public class StompMessageController {

    private final MessagesRepository messagesRepository;

    private final SimpMessagingTemplate template;
    @Autowired
    EntityManager entityManager;
    @Autowired
    MessagesService messagesService;

    @Autowired
    public StompMessageController(MessagesRepository repository, SimpMessagingTemplate template) {
        this.messagesRepository = repository;
        this.template = template;
    }

    //////////////////////////
    //----------- WEB SOCKET
    ////////////////////////

    @MessageMapping("/message")
    @SendTo("/topic/message")
    public Messages receiveMessage(@Payload Messages messages) {
        return messages;
    }

    @MessageMapping("/send")
    public Messages sendMessage(@Payload Messages messages) {
        template.convertAndSend(messages);
        return messages;
    }
}
