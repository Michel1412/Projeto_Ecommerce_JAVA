package com.unicesumar.service.payment;
import com.unicesumar.service.payment.paymentMethods.PaymentMethod;

public class PaymentManager {
    private PaymentMethod paymentMethod;

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void pay(double amount) {
        this.paymentMethod.pay(amount);
    }
}
