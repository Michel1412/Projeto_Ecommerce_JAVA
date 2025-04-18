package com.unicesumar;

import com.unicesumar.entities.Product;
import com.unicesumar.entities.Sale;
import com.unicesumar.entities.User;
import com.unicesumar.repository.ProductRepository;
import com.unicesumar.repository.SaleRepository;
import com.unicesumar.repository.UserRepository;
import com.unicesumar.service.ProductService;
import com.unicesumar.service.SaleService;
import com.unicesumar.service.UserService;
import com.unicesumar.service.payment.PaymentMethodFactory;
import com.unicesumar.service.payment.PaymentType;
import com.unicesumar.service.payment.paymentMethods.PaymentMethod;
import com.unicesumar.views.ProductView;
import com.unicesumar.views.SaleView;
import com.unicesumar.views.UserView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
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
        SaleService saleService = new SaleService(saleRepository, userService, productService);

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
                    Optional<User> optUser = userService.findByEmail(userEmail);

                    if (optUser.isEmpty()) {
                        System.out.println("Operacao cancelada: Usuario invalido para email: " + userEmail);
                        break;
                    }

                    productView.showProducts(productService.getAll());

                    Optional<List<Product>> optProducts = saleService.validProductList(saleView.requestProducts());

                    if (optProducts.isEmpty()) {
                        System.out.println("Operacao cancelada: Erro ao buscar os produtos selecionado");
                        break;
                    }

                    double amount = Sale.sumAmount(List.copyOf(optProducts.get()));

                    Optional<PaymentType> optPaymentType = saleView.requestPaymentMethod();

                    if (optPaymentType.isEmpty()) {
                        System.out.println("Operacao cancelada: Erro ao selecionar o tipo do pagamento");
                        break;
                    }
                    PaymentMethod paymentMethod = PaymentMethodFactory.create(optPaymentType.get());

                    Optional<Sale> optSale = saleService.createSale(optUser.get(), optProducts.get(), amount, paymentMethod);

                    if (optSale.isEmpty()) {
                        System.out.println("Operacao cancelada: Erro ao criar uma venda verifique os dados passados");
                        break;
                    }

                    saleView.showResume(optSale.get());
                    System.out.println("\nVenda registrada com sucesso!");

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

        } while (option != 7);

        scanner.close();
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
