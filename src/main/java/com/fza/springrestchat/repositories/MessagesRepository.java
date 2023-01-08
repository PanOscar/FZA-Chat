package com.fza.springrestchat.repositories;

import com.fza.springrestchat.models.Messages;
import org.springframework.data.repository.CrudRepository;

public interface MessagesRepository extends CrudRepository<Messages, Integer> {
}
