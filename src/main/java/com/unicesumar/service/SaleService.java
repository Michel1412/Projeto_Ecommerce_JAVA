package com.unicesumar.service;

import com.unicesumar.entities.Product;
import com.unicesumar.entities.Sale;
import com.unicesumar.entities.User;
import com.unicesumar.repository.ProductRepository;
import com.unicesumar.repository.SaleRepository;
import com.unicesumar.repository.UserRepository;
import com.unicesumar.service.payment.PaymentMethodFactory;
import com.unicesumar.service.payment.PaymentType;
import com.unicesumar.service.payment.paymentMethods.PaymentMethod;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import java.util.ArrayList;

public class SaleService {

    private final SaleRepository repository;
    private final UserService userService;
    private final ProductService productService;

    public SaleService(SaleRepository repository, UserService userService, ProductService productService) {
        this.repository = repository;
        this.userService = userService;
        this.productService = productService;
    }

    public Optional<Sale> createSale(User user, List<Product> products, double amount, PaymentMethod paymentMethod) {
        paymentMethod.pay(amount);

        Sale sale = new Sale(user, paymentMethod);

        products.forEach(sale::addProduct);

        this.repository.save(sale);
        return Optional.of(sale);
    }

    public List<Sale> getAll() {
        List<Sale> saleList = this.repository.findAll();

        saleList.forEach(sale -> {
            List<Product> products = this.productService.getBySaleId(sale.getUuid());

            products.forEach(sale::addProduct);
        });

        if (saleList.isEmpty()) {
            System.out.println("Operacao cancelada: Nao existem vendas cadastrados");
            return new ArrayList<Sale>();
        }

        return saleList;
    }

    public Optional<List<Product>> validProductList(List<UUID> uuids) {
        List<Product> products = this.productService.getAllIfPossible(uuids);

        if (products.isEmpty()) {
            System.out.println("Nao foram encontrados os produto(s) selecionado(s)");
            return Optional.empty();
        }

        return Optional.of(products);
    }
}
