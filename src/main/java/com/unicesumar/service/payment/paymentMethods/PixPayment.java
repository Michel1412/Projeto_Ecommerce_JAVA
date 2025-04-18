package com.unicesumar.service.payment.paymentMethods;

public class PixPayment implements PaymentMethod {

    public void pay(double amount) {
        System.out.println("Pagamento efetuado com sucesso via PIX");
    }

    @Override
    public String getName() {
        return "Pix";
    }
}