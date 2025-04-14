package com.unicesumar.entities;

import java.util.UUID;

public class User extends Entity {

    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(UUID uuid, String name, String email, String password) {
        super(uuid);
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String toString() {
        String userString = String.format("|  %s - %s", this.name, this.email);

        while (userString.length() < 41) {
            userString = userString.concat(" ");
        }

        return userString.concat("|");
    }
}
