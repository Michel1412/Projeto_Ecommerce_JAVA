package com.unicesumar.entities;

import com.unicesumar.service.payment.paymentMethods.PaymentMethod;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Sale extends Entity {

    private User user;
    private PaymentMethod payment_method;
    private Date sale_date;
    private List<Product> products = new ArrayList<Product>();

    public Sale(User user, PaymentMethod payment_method) {
        this.user = user;
        this.payment_method = payment_method;
        this.sale_date = new Date(System.currentTimeMillis());
    }

    public Sale(UUID uuid, User user, PaymentMethod payment_method, Date sale_date) {
        super(uuid);
        this.user = user;
        this.payment_method = payment_method;
        this.sale_date = sale_date;
    }

    public static double sumAmount(List<Product> products) {
        double amount = products.stream()
                .mapToDouble(Product::getPrice)
                .sum();

        System.out.println("\nSoma dos Produtos: " + amount);
        return amount;
    }

    public UUID getUserId() {
        return this.user.getUuid();
    }

    public PaymentMethod getPaymentMethod() {
        return this.payment_method;
    }

    public Date getSaleDate() {
        return this.sale_date;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public double total() {
        double total = 0;
        for(Product product:products){
            total+=product.getPrice();
        }
        return total;
    }

    public String getUserName() {
        return this.user.getName();
    }
}
