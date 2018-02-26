public class Product {

    private final ProductType productType;
    private final String name;
    private final double price;

    public Product(final ProductType productType, final String name, final double price) {
        this.productType = productType;
        this.name = name;
        this.price = price;
    }

    public ProductType getProductType() {
        return productType;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }


}
