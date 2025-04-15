package com.unicesumar.entities;

import com.unicesumar.service.payment.paymentMethods.PaymentMethod;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Sale extends Entity {

    private UUID user_id;
    private PaymentMethod payment_method;
    private Date sale_date;
    private List<Product> products;

    public Sale(UUID user_id, PaymentMethod payment_method) {
        this.user_id = user_id;
        this.payment_method = payment_method;
    }

    public Sale(UUID uuid, UUID user_id, PaymentMethod payment_method, Date sale_date, List<Product> products) {
        super(uuid);
        this.user_id = user_id;
        this.payment_method = payment_method;
        this.sale_date = sale_date;
        this.products = products;
    }

    public UUID getUserId() {
        return user_id;
    }

    public PaymentMethod getPaymentMethod() {
        return payment_method;
    }

    public Date getSaleDate() {
        return sale_date;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }

    public String toString() {
        return "Sale.toString() : Falhouu";
    }

    public double total(){
        double total = 0;
        for(Product product:products){
            total+=product.getPrice();
        }
        return total;
    }


}
