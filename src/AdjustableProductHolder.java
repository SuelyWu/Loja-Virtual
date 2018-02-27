public class AdjustableProductHolder extends ProductHolder {

    public AdjustableProductHolder(Product product, int qtt) {
        super(product, qtt);
    }

    public void decreaseQtt(int qtt) {
        setQtt(getQtt()-qtt);
    }

    public void increaseQtt(int qtt) {
        setQtt(getQtt()+qtt);
    }
}
