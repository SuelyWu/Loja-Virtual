package model;

import database.ListClients;
import database.Store;
import model.client.Client;
import model.client.Order;
import model.client.OrderFinisher;
import model.payment.PaymentOption;
import model.product.Product;
import model.purchase.ShoppingCart;
import view.Printer;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ShopFacade {

    private Scanner scanner;
    private Printer printer;
    private ListClients listClients;
    private Store store;
    private ShoppingCart shoppingCart;

    public ShopFacade(InputStream in, OutputStream out) {
        scanner = new Scanner(in);
        printer = new Printer(out);
        listClients = new ListClients();
        store = new Store();
        shoppingCart = new ShoppingCart();
    }

    public void startShop() {
        printer.println("Bem vindo a Loja Virtual");
        sessionShowProducts();
    }

    private void sessionShowProducts() {
        printer.printProducts(store.getProducts());
        int option = getValidOption(2, "Você quer:\n1-Adicionar item\n2-Ver carrinho de compras");
        if (option==1) {
            sessionAddItem();
        } else {
            sessionShowShoppingCart();
        }
    }

    private void sessionShowShoppingCart() {
        printer.printShoppingCart(shoppingCart.getItems());
        int option = getValidOption(3, "Você quer:\n1-Ver lista produto\n2-Ajustar quantidade de um item\n3-Finalizar compra");
        switch (option) {
            case 1: sessionShowProducts();
            case 2: sessionAdjustQttItem();
            case 3: sessionFinishOrder();
        }
    }

    private void sessionAddItem() {
        printer.print("Digite o nome do produto: ");
        Product product = store.getProductByProductName(scanner.nextLine());
        printer.print("Digite a quantidade: ");
        int qtt = Integer.parseInt(scanner.nextLine());
        shoppingCart.increaseItem(product, qtt, store);
        sessionShowShoppingCart();
    }

    private void sessionAdjustQttItem() {
        printer.print("Digite o nome do produto: ");
        Product product = store.getProductByProductName(scanner.nextLine());
        printer.print("Digite a quantidade: ");
        int qtdNova = Integer.parseInt(scanner.nextLine());
        shoppingCart.adjustItemQtt(product, qtdNova, store);
        sessionShowShoppingCart();
    }

    private void sessionFinishOrder() {
        Map args = new HashMap();
        Client client = getValidClient();
        PaymentOption paymentOption = choosePaymentOption();
        args.put(Client.class, client);
        args.put(PaymentOption.class, paymentOption);
        if (paymentOption.equals(PaymentOption.CREDIT_CARD)) {
            args.putAll(getArgsToPaymentCreditCard());
        }

        OrderFinisher orderFinisher = new OrderFinisher(shoppingCart, store);
        Order order = orderFinisher.finalizeOrder(args);

        printer.printPayment(order.getPayment());

        sessionShowShoppingCart();
    }

    private Client getValidClient() {
        printer.print("Digite seu id: ");
        int id = Integer.parseInt(scanner.nextLine());
        while (!listClients.hasClient(id)) {
            printer.print("Erro! Digite seu id novamente: ");
            id = Integer.parseInt(scanner.nextLine());
        }
        return listClients.getClientById(id);
    }

    private int getValidOption(int maxOption, String msg) {
        printer.println(msg);
        int option = Integer.parseInt(scanner.nextLine());
        while (option < 1 || option > maxOption) {
            printer.println("Opção inválida!");
            printer.println(msg);
                option = Integer.parseInt(scanner.nextLine());
        }
        return option;
    }

    private PaymentOption choosePaymentOption() {
        int option = getValidOption(2, "Escolha um tipo de model.payment:\n1-Boleto\n2-Cartão");
        if (option==1) return PaymentOption.BILLET;
        if (option==2) return PaymentOption.CREDIT_CARD;
        return null;
    }

    private Map getArgsToPaymentCreditCard() {
        Map argPagCartao = new HashMap();
        printer.print("Digite o número do cartão: ");
        String numCartao = scanner.nextLine();
        printer.print("Digite a quantidade de parcelas: ");
        int qtdParcelas = Integer.parseInt(scanner.nextLine());
        argPagCartao.put(String.class, numCartao);
        argPagCartao.put(Integer.class, qtdParcelas);
        return argPagCartao;
    }


}