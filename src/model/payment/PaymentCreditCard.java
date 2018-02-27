package model.payment;

import java.math.BigInteger;

public class PaymentCreditCard extends Payment {

    private final BigInteger cardNumber; // 16 digitos

    public PaymentCreditCard(final double total, final int timesToPay, final BigInteger cardNumber) {
        super(total, timesToPay);
        this.cardNumber = cardNumber;
        setPaymentOption(PaymentOption.CREDIT_CARD);
    }

    public BigInteger getCardNumber() {
        return cardNumber;
    }


}
