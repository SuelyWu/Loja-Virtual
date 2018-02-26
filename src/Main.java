import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static ListClients listClients;
    private static Store store;
    private static ShoppingCart shoppingCart;
    private static Printer printer;

    public static void main(String[] args) {

        listClients = new ListClients();
        store = new Store();
        shoppingCart = new ShoppingCart();
        printer = new Printer(System.out);

        printer.println("Bem vindo a Loja Virtual");
        printer.println("");
        sessionShowProducts();

    }

    private static void sessionShowProducts() {
        printer.printProducts(store.getProducts());
        int opcao = receberOpcaoValida(2, "Você quer:\n1-Adicionar item\n2-Ver carrinho de compras");
        if (opcao==1) {
            sessionAddItem();
        } else {
            sessionShoppingCart();
        }
    }

    private static void sessionShoppingCart() {
        printer.printShoppingCart(shoppingCart.getItems());
        int opcao = receberOpcaoValida(3, "Você quer:\n1-Ver lista produto\n2-Ajustar quantidade de um item\n3-Finalizar compra");
        switch (opcao) {
            case 1: sessionShowProducts();
            case 2: sessionAdjustQttItem();
            case 3: sessionFinishOrder();
        }
    }

    private static void sessionAddItem() {
        Product product = receberProdutoValido();
        int qtd = receberQtdValida();
        ////
        if (store.hasProdEnough(product, qtd)) {
            shoppingCart.addItem(product, qtd);
        }
        ////
        sessionShoppingCart();
    }

    private static void sessionAdjustQttItem() {
        Product product = receberProdutoValido();
        int qtdNova = receberQtdValida();
        shoppingCart.adjustItemQtt(product, qtdNova);
        sessionShoppingCart();
    }

    private static void sessionFinishOrder() {
        Map argsFinalizacao = new HashMap();
        Client client = receberClienteValido();
        PaymentOptionType paymentOptionType = escolherTipoPag();
        argsFinalizacao.put(Client.class, client);
        argsFinalizacao.put(PaymentOptionType.class, paymentOptionType);
        if (paymentOptionType.equals(PaymentOptionType.CREDIT_CARD)) {
            argsFinalizacao.putAll(coletaArgPagCartao());
        }

        OrderFinisher orderFinisher = new OrderFinisher(shoppingCart, store);
        Order order = orderFinisher.finish(argsFinalizacao);

        if (paymentOptionType.equals(PaymentOptionType.BILLET)) {
            imprimirBoleto(order);
        }
        sessionShoppingCart();
    }



    private static int receberOpcaoValida(int opcaoMax, String msg) {
        printer.println(msg);

        int opcao = 0;
        try {
            opcao = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {

        }

        while (opcao < 1 || opcao > opcaoMax) {
            printer.println("Opção inválida!");
            printer.println(msg);
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {

            }
        }
        return opcao;
    }

    private static Product receberProdutoValido() {
        printer.print("Digite o nome do product: ");
        Product product = shoppingCart.getProdItemsByNome(scanner.nextLine());
        while (product == null) {
            printer.println("Nome do product errado!");
            printer.print("Digite o nome do product: ");
            product = store.getProductByNome(scanner.nextLine());
        }
        return product;
    }

    private static int receberQtdValida() {
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

    private static Client receberClienteValido() {
        printer.print("Digite seu id: ");
        int id = Integer.parseInt(scanner.nextLine());
        while (!listClients.hasClient(id)) {
            printer.print("Erro! Digite seu id novamente: ");
            id = Integer.parseInt(scanner.nextLine());
        }
        return listClients.getClientById(id);
    }

    private static void imprimirBoleto(Order order) {
        printer.printBilletBarCode(order.getCodBarras());
    }

    private static PaymentOptionType escolherTipoPag() {
        int opcao = receberOpcaoValida(2, "Escolha um tipo de paymentOption:\n1-Boleto\n2-Cartão");
        if (opcao==1) return PaymentOptionType.BILLET;
        if (opcao==2) return PaymentOptionType.CREDIT_CARD;
        return null;
    }

    private static Map coletaArgPagCartao() {
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
