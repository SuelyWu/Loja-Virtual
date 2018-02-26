
public class PaymentOption {

    private PaymentOptionType paymentOptionType;
    private final double total;
    private final int timesToPay;
    private final double valorParcela;

    public PaymentOption(final double total, final int timesToPay) {
        this.total = total;
        this.timesToPay = timesToPay;
        this.valorParcela = calculateValorParcela();
    }

    public PaymentOptionType getPaymentOptionType() {
        return paymentOptionType;
    }

    protected void setPaymentOptionType(PaymentOptionType paymentOptionType) {
        this.paymentOptionType = paymentOptionType;
    }

    private double calculateValorParcela() {
        return (double) Math.round(total * 100) / 100;
    }

}
