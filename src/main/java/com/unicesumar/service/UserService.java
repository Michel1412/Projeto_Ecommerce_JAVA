package com.unicesumar.service;

import com.unicesumar.entities.User;
import com.unicesumar.entities.dto.UserDTO;
import com.unicesumar.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAll() {
        List<User> userList = this.repository.findAll();

        if (userList.isEmpty()) {
            System.out.println("Operacao cancelada: Nao existem usuarios cadastrados");
            return new ArrayList<User>();
        }

        return userList;
    }

    public void createUser(UserDTO dto) {
        User user = new User(dto);

        this.repository.save(user);
    }
}
