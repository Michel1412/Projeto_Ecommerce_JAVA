package com.unicesumar.service;

import com.unicesumar.entities.Product;
import com.unicesumar.repository.ProductRepository;
import com.unicesumar.views.ProductView;

import java.util.List;

public class ProductService {

    private final ProductRepository repository;
    private final ProductView view;

    public ProductService(ProductRepository repository, ProductView view) {
        this.repository = repository;
        this.view = view;
    }

    public void printAllProducts() {
        List<Product> productList = this.repository.findAll();

        if (productList.isEmpty()) {
            System.out.println("Operacao cancelada: Nao existem produtos cadastrados");
            return;
        }

        this.view.showProducts(productList);
    }

    public void createProduct() {
        this.repository.save(this.view.requestProduct());
    }
}
