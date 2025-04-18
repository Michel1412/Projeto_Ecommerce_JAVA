package com.unicesumar.views;

import com.unicesumar.entities.Product;
import com.unicesumar.entities.dto.ProductDTO;

import java.util.List;
import java.util.Scanner;

public class ProductView {

    private static final Scanner sc = new Scanner(System.in);

    public void showProducts(List<Product> listProduct) {
        System.out.println("\nLista de Produtos:\n|-----------------------------------------------------------------------------------|");
        listProduct.forEach(this::showProduct);
    }

    private void showProduct(Product product) {
        System.out.println(product.toString());
        System.out.println("|-----------------------------------------------------------------------------------|");
    }

    public ProductDTO requestProduct() {
        System.out.print("Criando um novo Produto:\nDigite o nome do Produto: ");
        String nome = sc.nextLine();

        System.out.print("\nDigite o preco do Produto: ");
        double preco = sc.nextDouble();
        sc.nextLine();

        return new ProductDTO(nome, preco);
    }
}
