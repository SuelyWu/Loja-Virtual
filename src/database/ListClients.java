package database;

import model.client.Client;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ListClients {

    private List<Client> clientList;

    public ListClients() {
        clientList = new LinkedList<>();
        generateInitialClients();
    }

    public boolean hasClient(int clientId) {
        return !clientList.stream().filter(c -> c.equals(clientId)).collect(Collectors.toList()).isEmpty();
    }

    public Client getClientById(int clientId) {
        return clientList.stream().filter(c -> c.equals(clientId)).findFirst().orElseThrow(() -> new RuntimeException("ClientNotFound"));
    }

    private boolean addClient(String nome, int id) {
        if (!hasClient(id)) {
            clientList.add(new Client(nome, id));
            return true;
        }
        return false;
    }

    private void generateInitialClients(){
        addClient("Ana", 10001);
        addClient("Beatriz", 10002);
        addClient("Carlos", 10003);
        addClient("Daniel", 10004);
    }

}