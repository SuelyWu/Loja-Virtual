import java.util.*;

public class Store {
    private Map<ProductType, List<Product>> store;


    public Store(){
        store = new HashMap<>();
        generateInitialProducts();
    }

    private void generateInitialProducts() {
        List<Product> productList = new LinkedList<>();
        productList.add(new Product(ProductType.MESA_REDONDA, "mesa redonda", 300));
        productList.add(new Product(ProductType.MESA_RETANGULAR, "mesa retangular", 400));
        productList.add(new Product(ProductType.ROUPA_BRANCA, "camiseta branca", 20));
        productList.add(new Product(ProductType.ROUPA_PRETA, "camiseta preta", 25));
        for (Product product : productList) {
            for (int i = 0; i < 20; i++) {
                increaseProduct(product);
            }
        }
    }

    private void increaseProduct(Product product) {
        ProductType productType = product.getProductType();
        List<Product> list = store.get(productType);
        if (list == null) {
            list = new LinkedList<>();
        }
        list.add(product);
        store.put(productType, list);
    }

    private int getProductStock(Product product) {
        List<Product> list = store.get(product.getProductType());
        return list.size();
    }

    public void decreaseProduct(Product product, int qtt) {
        ProductType productType = product.getProductType();

        List<Product> list = store.get(productType);

        for (int i = 0; i < qtt; i++) {
            list.remove(0);
        }
        store.put(productType, list);

    }

    public Map getProducts() {
        return Collections.unmodifiableMap(store);
    }

    public Product getProductByNome(String productName) {
        List listExistTypes = Arrays.asList(store.keySet().toArray());
        for (int i = 0; i < listExistTypes.size(); i++) {
            ProductType productType = (ProductType)listExistTypes.get(i);
            List listProd = store.get(productType);
            for (int j = 0; j < listProd.size(); j++) {
                Product product = (Product) listProd.get(j);
                if (product.getName().equalsIgnoreCase(productName)) {
                    return product;
                }
            }
        }
        return null;
    }

    public boolean hasProdEnough(Product product, int qttRequest) {
        return getProductStock(product) >= qttRequest;
    }



}
