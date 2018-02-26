import java.util.LinkedList;
import java.util.List;

public class ProductHolder {

    private final Product product;
    private int qtt;

    public ProductHolder(final Product product, int qtt) {
        this.product = product;
        this.qtt = qtt;
    }

    public void increaseQtt(int qtt) {
        this.qtt += qtt;
    }

    public void decreaseQtt(int qtt) {
        if (this.qtt - qtt < 0) {
            throw new RuntimeException(); // ProductStoreNotEnoughException
        }
        this.qtt -= qtt;
    }

    public int getQtt() {
        return this.qtt;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof String) {
            return this.product.getSerialNumber().equalsIgnoreCase((String)obj);
        }
        return super.equals(obj);
    }




}