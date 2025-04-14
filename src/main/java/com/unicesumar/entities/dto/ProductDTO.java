package com.unicesumar.entities.dto;

public class ProductDTO {

    private String nome;
    private double preco;

    public ProductDTO(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }
}
