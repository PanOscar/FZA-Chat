package com.fza.springrestchat.controllers;

import com.fza.springrestchat.models.Messages;
import com.fza.springrestchat.repositories.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(value = "/messages")
public class MessagesController {

    private final MessagesRepository messagesRepository;

    @Autowired
    public MessagesController(MessagesRepository repository) {
        this.messagesRepository = repository;
    }

    @GetMapping(value = "/")
    public List<Messages> getAll() {
        return StreamSupport.stream(messagesRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Messages> getById(@PathVariable("id") int id) {
        var message = messagesRepository.findById(id);

        return new ResponseEntity<>(
                message.orElse(new Messages()),
                message.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/newMessage", consumes = {"application/json"})
    public ResponseEntity<Messages> create(@RequestBody Messages messages) {
        return new ResponseEntity<>(messagesRepository.save(messages), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") int id, @RequestBody Messages messages) {
        if (messages.getId() != id) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!messagesRepository.existsById(id)) {
            messagesRepository.save(messages);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        messagesRepository.save(messages);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        messagesRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
