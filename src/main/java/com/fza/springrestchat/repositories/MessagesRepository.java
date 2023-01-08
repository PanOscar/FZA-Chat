package com.fza.springrestchat.repositories;

import com.fza.springrestchat.models.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends JpaRepository<Messages, Integer> {
    Messages findMessagesByToUsernameAndFromUsername(String fromUsername, String toUsername);
}
