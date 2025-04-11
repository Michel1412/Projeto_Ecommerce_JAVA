package com.unicesumar.views;

import com.unicesumar.entities.User;

import java.util.List;
import java.util.Scanner;

public class UserView {

    private static final Scanner sc = new Scanner(System.in);

    public void showUsers(List<User> users) {
        System.out.println("\nLista de Usuarios:\n|----------------------------------------|");
        users.forEach(this::showUser);
    }

    private void showUser(User user) {
        System.out.println(user.toString());
        System.out.println("|----------------------------------------|");
    }

    public User requestUser() {
        System.out.print("Criando novo Usuario:\nDigite o nome do Usuario: ");
        String nome = sc.nextLine();

        System.out.print("\nDigite o email do Usuario: ");
        String email = sc.nextLine();

        System.out.print("\nDigite o senha do Usuario: ");
        String senha = sc.nextLine();

        return new User(nome, email, senha);
    }
}
