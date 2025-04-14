package com.unicesumar.repository;

import com.unicesumar.entities.Product;
import com.unicesumar.entities.Sale;
import com.unicesumar.service.payment.PaymentMethodFactory;
import com.unicesumar.service.payment.PaymentType;

import java.sql.*;
import java.util.*;

public class SaleRepository implements EntityRepository<Sale> {

    private final Connection connection;

    public SaleRepository(Connection connection) {
        this.connection = connection;
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
                UUID saleId = UUID.fromString(resultSet.getString("uuid"));

                Optional<List<Product>> optListProducts = this.findProductsBySaleId(saleId);

                PaymentType paymentType = PaymentType.valueOf(resultSet.getString("payment_method"));

                return Optional.of(new Sale(
                        UUID.fromString(resultSet.getString("uuid")),
                        UUID.fromString(resultSet.getString("user_id")),
                        PaymentMethodFactory.create(paymentType),
                        resultSet.getDate("sale_date"),
                        optListProducts.orElse(null)
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    private Optional<List<Product>> findProductsBySaleId(UUID saleId) {
        List<Product> products = new LinkedList<>();

        String query = "SELECT p.uuid, p.name, p.price FROM products p LEFT JOIN sale_products sp ON p.uuid = sp.product_id WHERE uuid in (SELECT product_id from sp WHERE sale_id = ?)";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1, saleId.toString());
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Product product = new Product(
                        UUID.fromString(resultSet.getString("uuid")),
                        resultSet.getString("name"),
                        resultSet.getDouble("price")
                );
                products.add(product);
            }

            return Optional.of(products);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Sale> findAll() {
        String query = "SELECT * FROM sales";
        List<Sale> sales = new LinkedList<>();
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                UUID saleId = UUID.fromString(resultSet.getString("uuid"));

                Optional<List<Product>> optListProducts = this.findProductsBySaleId(saleId);
                PaymentType paymentType = PaymentType.valueOf(resultSet.getString("payment_method"));

                sales.add(new Sale(
                        UUID.fromString(resultSet.getString("uuid")),
                        UUID.fromString(resultSet.getString("user_id")),
                        PaymentMethodFactory.create(paymentType),
                        resultSet.getDate("sale_date"),
                        optListProducts.orElse(null)
                ));
            }

            return sales;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(UUID id) {
        String saleQuery = "DELETE FROM sale WHERE uuid = ?";
        String saleProductsQuery = "DELETE FROM sale_products WHERE sale_id = ?";
        try {
            PreparedStatement saleProductsStmt = this.connection.prepareStatement(saleProductsQuery);
            saleProductsStmt.setString(1, id.toString());
            saleProductsStmt.executeUpdate();

            PreparedStatement stmt = this.connection.prepareStatement(saleQuery);
            stmt.setString(1, id.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
