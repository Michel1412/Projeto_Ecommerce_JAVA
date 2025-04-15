package com.unicesumar;

import com.unicesumar.entities.Sale;
import com.unicesumar.repository.ProductRepository;
import com.unicesumar.repository.SaleRepository;
import com.unicesumar.repository.UserRepository;
import com.unicesumar.service.ProductService;
import com.unicesumar.service.SaleService;
import com.unicesumar.service.UserService;
import com.unicesumar.views.ProductView;
import com.unicesumar.views.SaleView;
import com.unicesumar.views.UserView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProductRepository productRepository = null;
        UserRepository userRepository = null;
        SaleRepository saleRepository = null;

        Connection conn = null;
        
        // Parâmetros de conexão
        String url = "jdbc:sqlite:database.sqlite";

        // Tentativa de conexão
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                productRepository = new ProductRepository(conn);
                userRepository = new UserRepository(conn);
                saleRepository = new SaleRepository(conn);

            } else {
                System.out.println("Falha na conexão.");
                System.exit(1);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            System.exit(1);
        }

        Scanner scanner = new Scanner(System.in);

        // views
        ProductView productView = new ProductView();
        UserView userView = new UserView();
        SaleView saleView = new SaleView();

        //services
        ProductService productService = new ProductService(productRepository);
        UserService userService = new UserService(userRepository);
        SaleService saleService = new SaleService(saleRepository);

        int option;

        do {
            System.out.println("\n---MENU---");
            System.out.println("1 - Cadastrar Produto");
            System.out.println("2 - Listas Produtos");
            System.out.println("3 - Cadastrar Usuário");
            System.out.println("4 - Listar Usuários");
            System.out.println("5 - Fazer uma Venda");
            System.out.println("6 - Listar Vendas");
            System.out.println("7 - Sair");
            System.out.println("Escolha uma opção: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    productService.createProduct(productView.requestProduct());
                    break;
                case 2:
                    productView.showProducts(productService.getAll());
                    break;
                case 3:
                    userService.createUser(userView.requestUser());
                    break;
                case 4:
                    userView.showUsers(userService.getAll());
                    break;
                case 5:
                    String userEmail = saleView.requestEmail();
                    Sale sale = null;

                    if (saleService.existsEmail(userEmail)) {

                        saleService.createSale(userEmail, saleView.requestProducts());
                    }

                    saleView.showResume(sale);
                    break;
                case 6:
                    saleView.showSales(saleService.getAll());
                    break;
                case 7:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente");

            }

        } while (option != 5);

        scanner.close();
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
