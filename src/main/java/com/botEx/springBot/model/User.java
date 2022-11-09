package com.botEx.springBot.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Entity(name = "users")
public class User {

    @Id
    private Long idChat;

    private String username;

    private String firstName;

    private String listName;

    private Timestamp timestampAt;



}
