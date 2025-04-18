package com.unicesumar.repository;

import com.unicesumar.entities.Product;

import java.sql.*;
import java.util.*;

public class ProductRepository implements EntityRepository<Product> {
    private final Connection connection;

    public ProductRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Product entity) {
        String query = "INSERT INTO products VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1, entity.getUuid().toString());
            stmt.setString(2, entity.getName());
            stmt.setDouble(3, entity.getPrice());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Product> findById(UUID id) {
        String query = "SELECT * FROM products WHERE uuid = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1, id.toString());
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                Product product = new Product(
                        UUID.fromString(resultSet.getString("uuid")),
                        resultSet.getString("name"),
                        resultSet.getDouble("price")
                );
                return Optional.of(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();

    }

    @Override
    public List<Product> findAll() {
        String query = "SELECT * FROM products";
        List<Product> products = new LinkedList<>();
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Product product = new Product(
                        UUID.fromString(resultSet.getString("uuid")),
                        resultSet.getString("name"),
                        resultSet.getDouble("price")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;

    }

    @Override
    public void deleteById(UUID id) {
        String productQuery = "DELETE FROM products WHERE uuid = ?";
        String saleProductsQuery = "DELETE FROM sale_products WHERE product_id = ?";
        try {
            PreparedStatement saleProductsStmt = this.connection.prepareStatement(saleProductsQuery);
            saleProductsStmt.setString(1, id.toString());
            saleProductsStmt.executeUpdate();

            PreparedStatement stmt = this.connection.prepareStatement(productQuery);
            stmt.setString(1, id.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<UUID> getIdBySaleId(UUID saleId) {
        String query = "SELECT product_id FROM sale_products WHERE sale_id = ?";
        List<UUID> productIds = new LinkedList<>();

        try {
            PreparedStatement saleProductsStmt = this.connection.prepareStatement(query);
            saleProductsStmt.setString(1, saleId.toString());
            ResultSet result = saleProductsStmt.executeQuery();

            while (result.next()) {
                productIds.add(UUID.fromString(result.getString("product_id")));
            }

            return productIds;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
