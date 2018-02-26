import java.util.Collections;
import java.util.List;

public class PaymentBillet extends PaymentOption {

    private BarCode barCode;

    public PaymentBillet(final double total) {
        super(total, 1);
        barCode = new BarCode(total);
        setPaymentOptionType(PaymentOptionType.BILLET);
    }

    public List getBarCode() {
        return Collections.unmodifiableList(barCode.getBarCode());
    }

}
