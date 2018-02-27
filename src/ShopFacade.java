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

    public void enterTheShop() {
        printer.println("Bem vindo a Loja Virtual");
        sessionShowProducts();
    }

    private void sessionShowProducts() {
        printer.printProducts(store.getProducts());
        int option = getValidOption(2, "Você quer:\n1-Adicionar item\n2-Ver carrinho de compras");
        if (option==1) {
            sessionAddItem();
        } else {
            sessionShoppingCart();
        }
    }

    private void sessionShoppingCart() {
        printer.printShoppingCart(shoppingCart.getItems());
        int option = getValidOption(3, "Você quer:\n1-Ver lista produto\n2-Ajustar quantidade de um item\n3-Finalizar compra");
        switch (option) {
            case 1: sessionShowProducts();
            case 2: sessionAdjustQttItem();
            case 3: sessionFinishOrder();
        }
    }

    private void sessionAddItem() {
        Product product = getValidProductName();
        int qtd = getValidQtt();
        ////
        if (store.hasProdEnough(product, qtd)) {
            shoppingCart.increaseItem(product, qtd, store);
        }
        ////
        sessionShoppingCart();
    }

    private void sessionAdjustQttItem() {
        Product product = getValidProductName();
        int qtdNova = getValidQtt();
        shoppingCart.adjustItemQtt(product, qtdNova, store);
        sessionShoppingCart();
    }

    private void sessionFinishOrder() {
        Map args = new HashMap();
        Client client = getValidClient();
        PaymentOptionType paymentOptionType = choosePaymentOption();
        args.put(Client.class, client);
        args.put(PaymentOptionType.class, paymentOptionType);
        if (paymentOptionType.equals(PaymentOptionType.CREDIT_CARD)) {
            args.putAll(getArgsToPaymentCreditCard());
        }

        OrderFinisher orderFinisher = new OrderFinisher(shoppingCart, store);
        Order order = orderFinisher.finalizeOrder(args);

        if (paymentOptionType.equals(PaymentOptionType.BILLET)) {
            printBilletBarCode(order);
        }
        sessionShoppingCart();
    }



    private int getValidOption(int maxOption, String msg) {
        printer.println(msg);

        int opcao = 0;
        try {
            opcao = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {

        }

        while (opcao < 1 || opcao > maxOption) {
            printer.println("Opção inválida!");
            printer.println(msg);
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {

            }
        }
        return opcao;
    }

    private Product getValidProductName() {
        printer.print("Digite o nome do produto: ");
        Product product = shoppingCart.getProdByName(scanner.nextLine());
        while (product == null) {
            printer.println("Nome do product errado!");
            printer.print("Digite o nome do produto: ");
            product = store.getProductByProductName(scanner.nextLine());
        }
        return product;
    }

    private int getValidQtt() {
        printer.print("Digite a quantidade: ");
        String entrada = scanner.nextLine();
        int qtd = 0;
        while (!(qtd >= 1)) {
            try {
                qtd = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                printer.println("Quantidade inválida!");
                printer.print("Digite a quantidade: ");
            }
        }
        return qtd;
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

    private void printBilletBarCode(Order order) {
        printer.printBilletBarCode(order.getBarCode());
    }

    private PaymentOptionType choosePaymentOption() {
        int option = getValidOption(2, "Escolha um tipo de paymentOption:\n1-Boleto\n2-Cartão");
        if (option==1) return PaymentOptionType.BILLET;
        if (option==2) return PaymentOptionType.CREDIT_CARD;
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