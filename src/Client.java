import java.util.LinkedList;
import java.util.List;

public class Client {

    private final String nome;
    private final int id;
    private List<Order> listOrder;

    public Client(String nome, int id) {
        this.nome = nome;
        this.id = id;
        listOrder = new LinkedList<>();
    }

    public void addOrder(Order order) {
        this.listOrder.add(order);
    }

    public boolean equals(int id) {
        return this.id == id;
    }
}
