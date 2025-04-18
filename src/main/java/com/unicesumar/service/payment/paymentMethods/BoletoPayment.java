package com.unicesumar.service.payment.paymentMethods;

public class BoletoPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.print("Pagamento efetuado com sucesso via boleto.");
    }

    @Override
    public String getName() {
        return "Boleto";
    }
}