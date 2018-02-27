package model.client;

import database.Store;
import model.purchase.ShoppingCart;
import model.payment.Payment;
import model.payment.PaymentBillet;
import model.payment.PaymentCreditCard;
import model.payment.PaymentOption;
import model.product.Product;
import model.product.ProductHolder;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class OrderFinisher {

    private Store store;
    private ShoppingCart shoppingCart;
    private Client client;
    private PaymentOption paymentOption;
    private int timesToPay = 0;
    private BigInteger cardNumber;

    public OrderFinisher(ShoppingCart shoppingCart, Store store) {
        this.shoppingCart = shoppingCart;
        this.store = store;
    }

    public Order finalizeOrder(Map args) {
        treatArgs(args);
        Payment payment = getPayment();
        Order order = new Order(client, shoppingCart.getItems(), payment);
        client.addOrder(order);
        List items = shoppingCart.getItems();
        for (Object obj : items) {
            Product product = ((ProductHolder) obj).getProduct();
            store.decreaseProduct(product, ((ProductHolder) obj).getQtt());
        }
        shoppingCart.cleanCart();
        return order;
    }

    private void treatArgs(Map args) {
        client = (Client) args.get(Client.class);
        paymentOption = (PaymentOption) args.get(PaymentOption.class);
        if (paymentOption.equals(PaymentOption.CREDIT_CARD)) {
            this.timesToPay = (int) args.get(Integer.class);
            String stringCardNumber = String.valueOf(args.get(String.class));
            this.cardNumber = new BigInteger(stringCardNumber);
        }
    }

    private Payment getPayment() {
        Payment payment;
        if (paymentOption.equals(PaymentOption.BILLET)) {
            payment = new PaymentBillet(shoppingCart.getShoppingCartTotal());
        } else {
            payment = new PaymentCreditCard(shoppingCart.getShoppingCartTotal(), timesToPay, cardNumber);
        }
        return payment;
    }

}
