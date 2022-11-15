package com.fza.springrestchat.repositories;

import com.fza.springrestchat.models.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Integer> {
}
