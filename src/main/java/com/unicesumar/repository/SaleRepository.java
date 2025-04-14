package com.unicesumar.repository;

import com.unicesumar.entities.Product;
import com.unicesumar.entities.Sale;
import com.unicesumar.service.payment.PaymentMethodFactory;
import com.unicesumar.service.payment.PaymentType;

import java.sql.*;
import java.util.*;

public class SaleRepository implements EntityRepository<Sale> {

    private final Connection connection;
    private final ProductRepository productRepository;

    public SaleRepository(Connection connection, ProductRepository productRepository) {
        this.connection = connection;
        this.productRepository = productRepository;
    }

    @Override
    public void save(Sale entity) {
        String saleQuery = "INSERT INTO sales VALUES (?, ?, ?)";
        String saleProductsQuery = "INSERT INTO sale_products VALUES (?, ?)";

        try {
            PreparedStatement saleStmt = this.connection.prepareStatement(saleQuery);
            saleStmt.setString(1, entity.getUuid().toString());
            saleStmt.setString(2, entity.getUserId().toString());
            saleStmt.setString(3, entity.getPaymentMethod().toString());
            saleStmt.executeUpdate();

            entity.getProducts().forEach(product -> {
                try {
                    PreparedStatement saleProductsStmt = this.connection.prepareStatement(saleProductsQuery);
                    saleProductsStmt.setString(1, entity.getUuid().toString());
                    saleProductsStmt.setString(2, product.getUuid().toString());
                    saleProductsStmt.executeUpdate();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Sale> findById(UUID id) {
        String query = " SELECT * FROM sales s WHERE s.uuid = ? ";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1, id.toString());
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                // converter a funcão do productRepository para receber apenas o id do Sale e fazer um join com o Product
                Array productIds = resultSet.getArray("product_ids");

                Optional<List<Product>> listProducts = this.productRepository.findAllByListId(productIds);

                PaymentType paymentType = PaymentType.valueOf(resultSet.getString("payment_method"));

                return Optional.of(new Sale(
                        UUID.fromString(resultSet.getString("uuid")),
                        UUID.fromString(resultSet.getString("user_id")),
                        PaymentMethodFactory.create(paymentType),
                        resultSet.getDate("sale_date"),
                        listProducts.orElse(null)
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<Sale> findAll() {
        String query = "SELECT * FROM sales";
        List<Sale> sales = new LinkedList<>();
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            UUID saleId = UUID.fromString(resultSet.getString("uuid"));

            Optional<List<Product>> optListProducts = this.productRepository.findAllBySaleId(saleId);

//            while (resultSet.next()) {
//                Sale sale = new Sale(
////                        UUID.fromString(resultSet.getString("uuid")),
////                        resultSet.getString("name"),
////                        resultSet.get("price"),
////                        resultSet.getDate("date"),
////                        optListProducts.orElse(null)
//                );
//                sales.add(sale);
//            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return sales;
    }

    @Override
    public void deleteById(UUID id) {

    }
}
