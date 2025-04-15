package com.unicesumar.views;

import com.unicesumar.entities.Product;
import com.unicesumar.entities.Sale;
import com.unicesumar.entities.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class SaleView {

    private static final Scanner sc = new Scanner(System.in);

    public void showSales(List<Sale> sales) {
        sales.forEach(System.out::println);
    }

    public String requestEmail() {
        System.out.print("\nDigite o email do Usuario: ");
        return sc.nextLine();
    }

    public List<Product> requestProducts() {
    List<Product> productIds = new ArrayList<>();
    String input;

    System.out.println("Digite o UUID dos produtos que deseja adicionar (digite '0' para finalizar):");

    while(true)

    {
        System.out.print("UUID do produto: ");
        input = sc.nextLine();

        if (input.equals("0")) break;

        try {
            UUID productId = UUID.fromString(input);
            productIds.add();
        } catch (IllegalArgumentException e) {
            System.out.println("UUID inválido, tente novamente.");
        }
    }

    return productIds;
    }



    public void showResume() {
        System.out.println("Resumo da venda:");
        System.out.println("Cliente: " +  );
        System.out.println("Produtos: " +);
        System.out.println("Valor total: " +);
        System.out.println("Pagamento:" +);
        System.out.println("Venda registrada com sucesso!");
    }
}
