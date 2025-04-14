package com.unicesumar.service.payment;

import com.unicesumar.service.payment.paymentMethods.BoletoPayment;
import com.unicesumar.service.payment.paymentMethods.CreditCardPayment;
import com.unicesumar.service.payment.paymentMethods.PaymentMethod;
import com.unicesumar.service.payment.paymentMethods.PixPayment;

public class PaymentMethodFactory {
    public static PaymentMethod create(PaymentType type) {
        switch (type) {
            case PIX:
                return new PixPayment();
            case CARTAO:
                return new CreditCardPayment();
            case BOLETO:
                return new BoletoPayment();
            default:
                return new PixPayment();
        }
    }
}