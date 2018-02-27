package model.purchase;

import database.Store;
import model.product.AdjustableProductHolder;
import model.product.Product;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ShoppingCart {

    private List<AdjustableProductHolder> items = new LinkedList<>();

    private AdjustableProductHolder getAdjustableProductHolder(Product product) {
        Optional<AdjustableProductHolder> adjustableProductHolder = items.stream()
                .filter(productHolder -> productHolder.equals(product.getSerialNumber())).findFirst();
        return adjustableProductHolder.orElseThrow(() -> new RuntimeException("ProductNotFoundException"));
    }

    public void adjustItemQtt(Product product, int newQtt, Store store) {
        AdjustableProductHolder holder = getAdjustableProductHolder(product);
        int oldQtt = holder.getQtt();
        if (newQtt < oldQtt) {
            decreaseItem(product, newQtt);
        } else {
            increaseItem(product, newQtt, store);
        }
    }

    public void increaseItem(Product product, int newQtt, Store store) {
        AdjustableProductHolder holder = items.stream().filter(productHolder -> productHolder.equals(product.getSerialNumber()))
                        .findFirst().orElseGet(() -> new AdjustableProductHolder(product, newQtt));
        if (!(holder.getQtt() == newQtt) && store.hasProdEnough(product, newQtt)) {
            holder.increaseQtt(newQtt-holder.getQtt());
            return;
        }
        items.add(holder);
    }

    private void decreaseItem(Product product, int newQtt) {
        AdjustableProductHolder holder = getAdjustableProductHolder(product);
        int oldQtt = holder.getQtt();
        if (newQtt == 0) {
            this.items.remove(holder);
        } else if (oldQtt-newQtt > 0) {
            holder.decreaseQtt(oldQtt-newQtt);
        } else {
            throw new IllegalArgumentException("Produto não deve ter valor negativo"); // qtt solicitado eh negativo
        }
    }

    public List getItems() {
        return Collections.unmodifiableList(items);
    }

    public double getShoppingCartTotal() {
         return items.stream().mapToDouble(model.product.ProductHolder::getSubtotal).sum();
    }

    public void cleanCart() {
        this.items = new LinkedList<>();
    }
}
