public class Product {

    private final String serialNumber; // --- --- > categoria.produtoNome
    private final String name;
    private final float price;

    public Product(final String serialNumber, final String name, final float price) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof String) {
            return this.name.equalsIgnoreCase((String)obj);
        }
        return super.equals(obj);
    }
}