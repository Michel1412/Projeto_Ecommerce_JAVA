package com.unicesumar.service;

import com.unicesumar.entities.User;
import com.unicesumar.entities.dto.UserDTO;
import com.unicesumar.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public boolean existsByEmail(String userEmail) {
        return this.repository.existeByEmail(userEmail);
    }

    public Optional<User> findByEmail(String email) {
        return this.repository.findByEmail(email);
    }
}
