import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCart {

    private List<OrderItem> items = new LinkedList<>();

    public void adjustItemQtt(Product product, int newQtt) {
        OrderItem orderItem = new OrderItem(product, newQtt);
        List<OrderItem> matchedItems = items.stream().filter(item -> item.equals(orderItem)).collect(Collectors.toList());
        OrderItem oldItem = matchedItems.get(0);
        if (oldItem.getQtt() < newQtt) {
            addItem(product, newQtt - oldItem.getQtt());
        } else {
            delItem(product, oldItem.getQtt() - newQtt);
        }
    }

    public void addItem(Product product, int qtt) {
        OrderItem itemAdd = new OrderItem(product, qtt);
        List<OrderItem> matchedItems = items.stream().filter(item -> item.equals(itemAdd)).collect(Collectors.toList());
        if (matchedItems.isEmpty()) {
            items.add(itemAdd);
        } else {
            OrderItem orderItem = matchedItems.get(0);
            items.remove(orderItem);
            orderItem.increaseQtt(qtt);
            items.add(orderItem);
        }
    }

    private boolean delItem(Product product, int qtt) {
        OrderItem itemDel = new OrderItem(product, qtt);
        List<OrderItem> matchedItems = items.stream().filter(item -> item.equals(itemDel)).collect(Collectors.toList());
        if (!matchedItems.isEmpty()) {
            OrderItem orderItem = matchedItems.get(0);
            OrderItem orderItem1 = orderItem;
            items.remove(orderItem);
            if (!orderItem.decreaseQtt(qtt)) {
                items.add(orderItem1);
                return false;
            }
            items.add(orderItem);
            return true;
        }
        return false;
    }

    public List getItems() {
        return Collections.unmodifiableList(items);
    }

    public Product getProdItemsByNome(String productNome) {
        List matchedItem = items.stream().filter(item -> item.getProdName().equalsIgnoreCase(productNome)).collect(Collectors.toList());
        if (matchedItem.isEmpty()) {
            return null;
        }
        OrderItem orderItem = (OrderItem) matchedItem.get(0);
        return orderItem.getProduct();
    }

    public double getCartTotal() {
         double total = 0;
         for (OrderItem item : items) {
             total += item.getSubtotal();
         }
         return total;
    }

    public void cleanCart() {
        this.items = new LinkedList<>();
    }
}
