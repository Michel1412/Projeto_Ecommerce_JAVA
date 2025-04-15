package com.unicesumar.service;

import com.unicesumar.entities.Product;
import com.unicesumar.entities.Sale;
import com.unicesumar.repository.SaleRepository;

import java.util.ArrayList;
import java.util.List;

public class SaleService {

    private final SaleRepository repository;

    public SaleService(SaleRepository repository) {
        this.repository = repository;
    }

    public boolean existsEmail(String email) {
        System.out.println("Usuario encontrado" + //nome usuario//);
        return repository.existsByEmail(email);
    }

    public Sale createSale() {
        return null;
    }


    public void createSale(String email, List<Product> products) {
        Sale sale = new Sale(email,products);
        this.repository.save(sale);


    }


    public List<Sale> getAll() {
        List<Sale> saleList = this.repository.findAll();

        if (saleList.isEmpty()) {
            System.out.println("Operacao cancelada: Nao existem vendas cadastrados");
            return new ArrayList<Sale>();
        }

        return saleList;
    }
}
