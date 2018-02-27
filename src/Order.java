import java.util.List;

public class Order {

    Client client;
    List<ProductHolder> purchasedItems;
    PaymentOption paymentOption;

    public Order(Client client, List<ProductHolder> purchasedItems, PaymentOption paymentOption) {
        this.client = client;
        this.purchasedItems = purchasedItems;
        this.paymentOption = paymentOption;
    }

    public List getBarCode() {
        return ((PaymentBillet)paymentOption).getBarCode();
    }
}
