import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ShoppingCart {

    private List<AdjustableProductHolder> items = new LinkedList<>();

    private AdjustableProductHolder getAdjustableProductHolder(Product product) {
        Optional<AdjustableProductHolder> adjustableProductHolder = items.stream()
                .filter(productHolder -> productHolder.equals(product.getSerialNumber())).findFirst();
        return adjustableProductHolder.orElseThrow(() -> new RuntimeException("ProductNotFoundException")); // ProductNotFoundException
    }

    public void adjustItemQtt(Product product, int newQtt, Store store) {
        AdjustableProductHolder holder = getAdjustableProductHolder(product);
        int oldQtt = holder.getQtt();
        if (newQtt < oldQtt) {
            decreaseItem(product, oldQtt-newQtt);
        } else {
            increaseItem(product, newQtt-oldQtt, store);
        }
    }

    public void increaseItem(Product product, int qtt, Store store) {
        AdjustableProductHolder holder = getAdjustableProductHolder(product);
        if (store.hasProdEnough(product, holder.getQtt()+qtt)) {
            holder.increaseQtt(qtt);
        }
        throw new RuntimeException("ProductNotEnoughException"); // product insuficiente
    }

    private void decreaseItem(Product product, int qtt) {
        AdjustableProductHolder holder = getAdjustableProductHolder(product);
        if (holder.getQtt()-qtt > 0) {
            holder.decreaseQtt(qtt);
        } else if (holder.getQtt()-qtt == 0) {
            this.items.remove(holder);
        } else {
            throw new IllegalArgumentException("Produto não deve ter valor negativo"); // qtt solicitado eh negativo
        }
    }

    public List getItems() {
        return Collections.unmodifiableList(items);
    }

    public Product getProdByName(String productNome) {
        AdjustableProductHolder holder = this.items.stream()
                .filter(productHolder -> productHolder.getProductName().equalsIgnoreCase(productNome)).findFirst().orElseThrow(() -> new RuntimeException("ProductNotFoundException"));
        return holder.getProduct();
    }

    public double getShoppingCartTotal() {
         double total = 0;
         for (AdjustableProductHolder item : items) {
             total += item.getSubtotal();
         }
         return total;
    }

    public void cleanCart() {
        this.items = new LinkedList<>();
    }
}
