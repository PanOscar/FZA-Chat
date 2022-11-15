package com.fza.springrestchat.controllers;

import com.fza.springrestchat.models.Message;
import com.fza.springrestchat.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(value = "/messages")
public class MessageController {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageRepository repository) {
        this.messageRepository = repository;
    }

    @GetMapping(value = "/")
    public List<Message> getAll() {
        return StreamSupport.stream(messageRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Message> getById(@PathVariable("id") int id) {
        var message = messageRepository.findById(id);

        return new ResponseEntity<>(
                message.orElse(new Message()),
                message.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<Message> create(@RequestBody Message message) {
        return new ResponseEntity<>(
                messageRepository.save(message),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") int id, @RequestBody Message message) {
        if (message.getId() != id) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!messageRepository.existsById(id)) {
            messageRepository.save(message);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        messageRepository.save(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        messageRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
