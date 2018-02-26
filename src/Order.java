

import java.util.LinkedList;
import java.util.List;

public class Order {

    Client client;
    List<OrderItem> purchasedItems;
    PaymentOption paymentOption;

    public Order(Client client, List<OrderItem> purchasedItems, PaymentOption paymentOption) {
        this.client = client;
        this.purchasedItems = purchasedItems;
        this.paymentOption = paymentOption;
    }

    public List getCodBarras() {
        if (paymentOption.getPaymentOptionType().equals(PaymentOptionType.BILLET)) {
            return ((PaymentBillet) paymentOption).getBarCode();
        }
        return new LinkedList();
    }
}
