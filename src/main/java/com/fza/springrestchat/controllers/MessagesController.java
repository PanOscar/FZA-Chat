package com.fza.springrestchat.controllers;

import com.fza.springrestchat.models.Messages;
import com.fza.springrestchat.repositories.MessagesRepository;
import com.fza.springrestchat.services.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(value = "/messages")
public class MessagesController {

    private final MessagesRepository messagesRepository;

    @Autowired
    EntityManager entityManager;


    @Autowired
    public MessagesController(MessagesRepository repository, MessagesService service) {
        this.messagesRepository = repository;
    }

    @GetMapping(value = "/")
    public List<Messages> getAll() {
        return StreamSupport.stream(messagesRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/find")
    public ResponseEntity<List<Messages>> getById(@RequestParam String fromUsername, @RequestParam String toUsername) {
        String sql = "SELECT * FROM message WHERE from_username = ? AND to_username = ?";
        Query query = entityManager.createNativeQuery(sql, Messages.class);
        query.setParameter(1, fromUsername);
        query.setParameter(2, toUsername);
        List<Messages> messages = query.getResultList();

        return new ResponseEntity<>(
                messages,
                messages != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
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
