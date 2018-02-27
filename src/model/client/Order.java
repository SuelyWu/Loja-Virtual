package model.client;

import model.payment.Payment;
import model.product.ProductHolder;

import java.util.List;

public class Order {

    private Client client;
    private List<ProductHolder> purchasedItems;
    private Payment payment;

    public Order(Client client, List<ProductHolder> purchasedItems, Payment payment) {
        this.client = client;
        this.purchasedItems = purchasedItems;
        this.payment = payment;
    }

    public Payment getPayment() {
        return payment;
    }
}
