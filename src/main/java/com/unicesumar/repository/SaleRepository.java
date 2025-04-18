package com.unicesumar.repository;

import com.unicesumar.entities.Sale;
import com.unicesumar.entities.User;
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
        String saleQuery = "INSERT INTO sales VALUES (?, ?, ?, ?)";
        String saleProductsQuery = "INSERT INTO sale_products VALUES (?, ?)";

        try {
            PreparedStatement saleStmt = this.connection.prepareStatement(saleQuery);
            saleStmt.setString(1, entity.getUuid().toString());
            saleStmt.setString(2, entity.getUserId().toString());
            saleStmt.setString(3, entity.getPaymentMethod().getName());
            saleStmt.setDate(4, entity.getSaleDate());
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
                return Optional.of(new Sale(
                        UUID.fromString(resultSet.getString("uuid")),
                        this.findUserByUserId(UUID.fromString(resultSet.getString("user_id"))).orElse(null),
                        PaymentMethodFactory.getByName(resultSet.getString("payment_method")),
                        resultSet.getDate("sale_date")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    private Optional<User> findUserByUserId(UUID userId) {
        String query = " SELECT * FROM users WHERE uuid = ? ";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1, userId.toString());ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new User(
                        UUID.fromString(resultSet.getString("uuid")),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
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

            while (resultSet.next()) {
                sales.add(new Sale(
                    UUID.fromString(resultSet.getString("id")),
                    this.findUserByUserId(UUID.fromString(resultSet.getString("user_id"))).orElse(null),
                    PaymentMethodFactory.getByName(resultSet.getString("payment_method")),
                    resultSet.getDate("sale_date")
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
