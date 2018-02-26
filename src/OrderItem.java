public class OrderItem {



    private final Product product;
    private int qtt;

    public OrderItem(Product product, int qtt) {
        this.product = product;
        this.qtt = qtt;
    }


    // verificar se estoque tem estoque suficiente
    public void increaseQtt(int qtd) {
        this.qtt += qtd;
    }

    public boolean decreaseQtt(int qtd) {
        int oldQtt = this.qtt;
        this.qtt -= qtd;
        if (this.qtt < 0) {
            this.qtt = oldQtt;
            return false;
        }
        return true;
    }

    public double getProdPrice() {
        return product.getPrice();
    }

    public String getProdName() {
        return product.getName();
    }

    public int getQtt() {
        return qtt;
    }

    public double getSubtotal() {
        return qtt * product.getPrice();
    }

    /*public Product getProduct() {
        return new Product(productType, getProdName(), getProdPrice());
    }*/

    public boolean equals(OrderItem orderItem) {
        return this.product.getName().equalsIgnoreCase(orderItem.getProductName());
    }

    private String getProductName() {
        return this.product.getName();
    }



}