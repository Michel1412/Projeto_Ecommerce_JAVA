package com.unicesumar.entities;

import com.unicesumar.entities.dto.UserDTO;

import java.util.UUID;

public class User extends Entity {

    private String name;
    private String email;
    private String password;

    public User(UserDTO dto) {
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
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
        String userString = String.format("| %s - %s", this.name, this.email);

        while (userString.length() < 41) {
            userString = userString.concat(" ");
        }

        return userString.concat("|");
    }
}
