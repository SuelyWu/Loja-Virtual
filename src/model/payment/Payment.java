package model.payment;

public class Payment {

    private PaymentOption paymentOption;
    private final double total;
    private final int timesToPay;

    public Payment(final double total, final int timesToPay) {
        this.total = total;
        this.timesToPay = timesToPay;
    }

    protected void setPaymentOption(PaymentOption paymentOption) {
        this.paymentOption = paymentOption;
    }

    public PaymentOption getPaymentOption() {
        return paymentOption;
    }

    public int getTimesToPay() {
        return timesToPay;
    }

    public double getValorParcela() {
        return ((double) Math.round(total * 100) / 100)/timesToPay;
    }

}
