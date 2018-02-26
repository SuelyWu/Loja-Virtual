import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class OrderFinisher {

    private Store store;
    private ShoppingCart shoppingCart;
    private Client client;
    private PaymentOptionType paymentOptionType;
    private int timesToPay = 0;
    private BigInteger cardNumber;

    public OrderFinisher(ShoppingCart shoppingCart, Store store) {
        this.shoppingCart = shoppingCart;
        this.store = store;
    }

    public Order finish(Map args) {
        treatArgs(args);
        PaymentOption paymentOption = getPayment();
        Order order = new Order(client, shoppingCart.getItems(), paymentOption);
        client.addOrder(order);
        List items = shoppingCart.getItems();
        for (Object obj : items) {
            Product product = ((OrderItem) obj).getProduct();
            store.decreaseProduct(product, ((OrderItem) obj).getQtt());
        }
        shoppingCart.cleanCart();
        return order;
    }

    private void treatArgs(Map args) {
        client = (Client) args.get(Client.class);
        paymentOptionType = (PaymentOptionType) args.get(PaymentOptionType.class);
        if (paymentOptionType.equals(PaymentOptionType.CREDIT_CARD)) {
            this.timesToPay = (int) args.get(Integer.class);
            String stringNumCartao = String.valueOf(args.get(String.class));
            this.cardNumber = new BigInteger(stringNumCartao);
        }
    }

    private PaymentOption getPayment() {
        PaymentOption paymentOption;
        if (paymentOptionType.equals(PaymentOptionType.BILLET)) {
            paymentOption = new PaymentBillet(shoppingCart.getCartTotal());
        } else {
            paymentOption = new PaymentCreditCard(shoppingCart.getCartTotal(), timesToPay, cardNumber);
        }
        return paymentOption;
    }

}
