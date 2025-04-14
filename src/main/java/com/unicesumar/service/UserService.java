package com.unicesumar.service;

import com.unicesumar.entities.User;
import com.unicesumar.repository.UserRepository;
import com.unicesumar.views.UserView;

import java.util.List;

public class UserService {

    private final UserRepository repository;
    private final UserView view;

    public UserService(UserRepository repository, UserView view) {
        this.repository = repository;
        this.view = view;
    }

    public void showAllUsers() {
        List<User> userList = this.repository.findAll();

        if (userList.isEmpty()) {
            System.out.println("Operacao cancelada: Nao existem usuarios cadastrados");
            return;
        }

        this.view.showUsers(userList);
    }

    public void createUser() {
        this.repository.save(this.view.requestUser());
    }
}
