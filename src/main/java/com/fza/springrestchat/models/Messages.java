package com.fza.springrestchat.models;

import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "message")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date created_date;

    @Column(length = 1000, nullable = false)
    private String message;

    @Column(nullable = false)
    private String fromUsername;

    @Column(nullable = false)
    private String toUsername;

    public Messages() {
        this.created_date = new Date();
    }

    public static Messages of(String text) {
        Messages messages = new Messages();
        messages.message = text;
        return messages;
    }
}
