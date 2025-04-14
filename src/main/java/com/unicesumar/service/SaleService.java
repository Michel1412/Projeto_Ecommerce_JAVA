package com.unicesumar.service;

import com.unicesumar.entities.Sale;
import com.unicesumar.repository.SaleRepository;

public class SaleService {

    private final SaleRepository repository;

    public SaleService(SaleRepository repository) {
        this.repository = repository;
    }

    public boolean existsEmail(String email) {
        return true;
    }

    public Sale createSale() {



        return null;
    }


    public Object getAll() {
        return null;
    }
}
