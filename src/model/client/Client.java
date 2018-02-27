package model.client;

import java.util.LinkedList;
import java.util.List;

public class Client {

    private final String name;
    private final int id;
    private List<Order> listOrder;

    public Client(String name, int id) {
        this.name = name;
        this.id = id;
        listOrder = new LinkedList<>();
    }

    public void addOrder(Order order) {
        this.listOrder.add(order);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Integer) {
            return this.id == (int)obj;
        }
        return super.equals(obj);
    }

}
