package com.unicesumar.service;

import com.unicesumar.entities.Product;
import com.unicesumar.entities.dto.ProductDTO;
import com.unicesumar.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAll() {
        List<Product> productList = this.repository.findAll();

        if (productList.isEmpty()) {
            System.out.println("Operacao cancelada: Nao existem produtos cadastrados");
            return new ArrayList<Product>();
        }

        return productList;
    }

    public void createProduct(ProductDTO dto) {
        Product product = new Product(dto);

        this.repository.save(product);
    }
}
