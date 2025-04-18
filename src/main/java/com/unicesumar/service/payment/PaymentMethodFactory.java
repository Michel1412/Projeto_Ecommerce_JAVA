package com.unicesumar.service.payment;

import com.unicesumar.service.payment.paymentMethods.BoletoPayment;
import com.unicesumar.service.payment.paymentMethods.CreditCardPayment;
import com.unicesumar.service.payment.paymentMethods.PaymentMethod;
import com.unicesumar.service.payment.paymentMethods.PixPayment;

public class PaymentMethodFactory {

    public static PaymentMethod create(PaymentType type) {
        return switch (type) {
            case PIX -> new PixPayment();
            case CARTAO -> new CreditCardPayment();
            case BOLETO -> new BoletoPayment();
        };
    }

    public static PaymentMethod getByName(String methodName) {
        return switch (methodName) {
            case "Pix" -> new PixPayment();
            case "CreditCard" -> new CreditCardPayment();
            case "Boleto" -> new BoletoPayment();
            default -> null;
        };
    }
}