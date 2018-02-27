package database;

import model.product.AdjustableProductHolder;
import model.product.Product;

import java.util.*;

public class Store {

    private List<AdjustableProductHolder> productHolderList;

    public Store(){
        productHolderList = new LinkedList<>();
        generateInitialProducts();
    }

    private void generateInitialProducts() {
        registerProduct("MESA001", "Mesa redonda", 300, 20);
        registerProduct("MESA002", "Mesa retangular", 400, 20);
        registerProduct("ROUPA001", "Camiseta branca", 20, 20);
        registerProduct("ROUPA002", "Camiseta preta", 25, 20);
    }

    private void registerProduct(String serialNumber, String name, float price, int qtt) {
        Product product = new Product(serialNumber, name, price);
        AdjustableProductHolder holder = new AdjustableProductHolder(product, qtt);
        productHolderList.add(holder);
    }

    private int getProductQtt(Product product) {
        AdjustableProductHolder holder = getAdjustableProductHolder(product);
        return holder.getQtt();
    }

    public void decreaseProduct(Product product, int qtt) {
        AdjustableProductHolder holder = getAdjustableProductHolder(product);
        holder.decreaseQtt(qtt);
    }

    private AdjustableProductHolder getAdjustableProductHolder(Product product) {
        Optional<AdjustableProductHolder> adjustableProductHolder = productHolderList.stream()
                .filter(productHolder -> productHolder.equals(product.getSerialNumber())).findFirst();
        return adjustableProductHolder.orElseThrow(() -> new RuntimeException()); // ProductNotFound  nao existe o produto pesquisado
    }

    public List getProducts() {
        return Collections.unmodifiableList(productHolderList);
    }

    public Product getProductByProductName(String productName) {
        Optional<AdjustableProductHolder> holderOpt = productHolderList.stream().filter(productHolder -> productHolder.getProduct().equals(productName)).findFirst();
        if (holderOpt.isPresent()) {
            return holderOpt.get().getProduct();
        } else {
            throw new RuntimeException("ProductNotFound"); // ProductNotFound
        }
    }

    public boolean hasProdEnough(Product product, int qttRequest) {
        return getProductQtt(product) >= qttRequest;
    }



}
