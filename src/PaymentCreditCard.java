import java.math.BigInteger;

public class PaymentCreditCard extends PaymentOption {

    private final BigInteger cardNumber; // 16 digitos

    public PaymentCreditCard(final double total, final int timesToPay, final BigInteger cardNumber) {
        super(total, timesToPay);
        this.cardNumber = cardNumber;
        setPaymentOptionType(PaymentOptionType.CREDIT_CARD);
    }

    public PaymentCreditCard(final double total, final int timesToPay, final String cardNumber) {
        super(total, timesToPay);
        this.cardNumber = BigInteger.valueOf(Long.getLong(cardNumber));
        setPaymentOptionType(PaymentOptionType.CREDIT_CARD);
    }
}
