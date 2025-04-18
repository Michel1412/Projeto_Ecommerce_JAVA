package com.unicesumar.views;

import com.unicesumar.entities.Product;
import com.unicesumar.entities.Sale;
import com.unicesumar.service.payment.PaymentType;

import java.util.*;

public class SaleView {

    private static final Scanner sc = new Scanner(System.in);

    public void showSales(List<Sale> sales) {
        sales.forEach(this::showResume);
    }

    public String requestEmail() {
        System.out.print("\nDigite o email do Usuario: ");
        return sc.nextLine();
    }

    public List<UUID> requestProducts() {
        List<UUID> productIds = new ArrayList<>();
        String input;

        System.out.println("Digite o UUID dos produtos que deseja adicionar (digite '0' para finalizar):");

        while(true) {
            System.out.print("UUID do produto: ");
            input = sc.nextLine();

            if (input.equals("0")) break;

            try {
                UUID productId = UUID.fromString(input);
                productIds.add(productId);
            } catch (IllegalArgumentException e) {
                System.out.println("UUID inválido, tente novamente.");
            }
        }

        return productIds;
    }

    public void showResume(Sale sale) {
        System.out.println(" Codigo de validacao da venda: " + sale.getUuid().toString());

        System.out.println("\nResumo da venda:");
        System.out.println("Cliente: " + sale.getUserName());
        System.out.println("Produtos: ");
        sale.getProducts().forEach(p -> System.out.println(" - " + p.getName()));
        System.out.println("Valor total: " + sale.total());
        System.out.println("Pagamento: " + sale.getPaymentMethod().getName());

    }

    public Optional<PaymentType> requestPaymentMethod() {
        System.out.println("\nEscolha a forma de pagamento:  \n1 - Cartão de Crédito  \n2 - Boleto  \n3 - PIX  \nOpção: ");
        int opcao = sc.nextInt();

        return switch(opcao) {
            case 1 -> Optional.of(PaymentType.CARTAO);
            case 2 -> Optional.of(PaymentType.BOLETO);
            case 3 -> Optional.of(PaymentType.PIX);
            default -> Optional.empty();
        };
    }
}
