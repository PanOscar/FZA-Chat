package com.fza.springrestchat.services;

import com.fza.springrestchat.models.Messages;
import com.fza.springrestchat.repositories.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessagesService {
    @Autowired
    MessagesRepository messagesRepository;
    @Autowired
    SimpMessagingTemplate template;

    public Messages getMessages(String fromUsername, String toUsername) {
        return messagesRepository.findByFromUsernameLikeAndToUsernameLike(fromUsername, toUsername);
    }

    public void saveChatMessage(Messages messages) {
        messagesRepository.save(messages);
    }
}
