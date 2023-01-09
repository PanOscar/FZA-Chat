package com.fza.springrestchat.repositories;

import com.fza.springrestchat.models.Messages;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends CrudRepository<Messages, Integer> {
    Messages findByFromUsernameLikeAndToUsernameLike(String fromUsername, Object toUsername);

    Messages findMessagesByToUsernameAndFromUsername(String fromUsername, String toUsername);
}
