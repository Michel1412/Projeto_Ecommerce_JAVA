package com.unicesumar.service;

import com.unicesumar.entities.Sale;
import com.unicesumar.repository.SaleRepository;
import com.unicesumar.views.SaleView;

public class SaleService {

    private final SaleRepository repository;
    private final SaleView view;

    public SaleService(SaleRepository repository, SaleView view) {
        this.repository = repository;
        this.view = view;
    }

    public Sale createSale() {



        return null;
    }


}
