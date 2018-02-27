package model.payment;

import java.util.Collections;
import java.util.List;

public class PaymentBillet extends Payment {

    private BarCode barCode;

    public PaymentBillet(final double total) {
        super(total, 1);
        barCode = new BarCode(total);
        setPaymentOption(PaymentOption.BILLET);
    }

    public List getBarCode() {
        return Collections.unmodifiableList(barCode.getBarCode());
    }

}
