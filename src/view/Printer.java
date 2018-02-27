package view;

import model.payment.Payment;
import model.payment.PaymentBillet;
import model.payment.PaymentCreditCard;
import model.product.AdjustableProductHolder;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

public class Printer {

    private PrintStream out;

    public Printer(OutputStream outputStream) {
        out = new PrintStream(outputStream);
    }

    public void print(Object obj) {
        out.print(obj);
    }

    public void println(Object obj) {
        out.println(obj);
    }

    public void printProducts(List storeList) {
        out.println();
        out.println("=====================================================================================");
        out.println("PRODUTOS");
        out.println("\tmodel.product.Product\t\t\t\tPreço Unitário\t\tEstoque");
        storeList.forEach(o -> out.println(String.format("\t%s\t\tR$ %.2f\t\t\t%d",
                        ((AdjustableProductHolder)o).getProductName(),
                        ((AdjustableProductHolder)o).getProductPrice(),
                        ((AdjustableProductHolder)o).getQtt())));
        out.println();
    }

    public void printShoppingCart(List listItems) {
        out.println();
        out.println("=====================================================================================");
        out.println("Carrinho de Compras:");

        if (listItems.isEmpty()){
            out.println("Seu carrinho está vazio!\n");
            return;
        }

        out.println("\tmodel.product.Product\t\t\t\tPreço Unitário\t\tQuantidade\t\tSubtotal");
        double total = 0;
        listItems.forEach(o -> out.println(String.format("\t%s\t\tR$ %.2f\t\t\t%d\t\t\t\tR$ %.2f",
                ((AdjustableProductHolder)o).getProductName(),
                ((AdjustableProductHolder)o).getProductPrice(),
                ((AdjustableProductHolder)o).getQtt(),
                ((AdjustableProductHolder)o).getSubtotal())));
        for (Object o : listItems) {
            total += ((AdjustableProductHolder)o).getSubtotal();
        }
        out.println(String.format("\t\t\t\t\t\t\t\t\t\t\tTotal:\t\t\tR$ %.2f", total));
        out.println();
    }

    public void printPayment(Payment payment) {
        switch (payment.getPaymentOption()) {
            case CREDIT_CARD:
                printCreditCard((PaymentCreditCard)payment);
                return;
            case BILLET:
                printBillet((PaymentBillet)payment);
        }
    }

    private void printCreditCard(PaymentCreditCard payment) {
        out.println();
        out.println("Número de cartão: " + payment.getCardNumber());
        out.println("Quantidade parcelas: " + payment.getTimesToPay());
        out.println("Valor parcela: R$ %.2f" + payment.getValorParcela());
    }

    private void printBillet(PaymentBillet payment) {
        out.println();
        List listBarCode = payment.getBarCode();
        out.println("Código de barras: ");
        for (int i = 0; i < listBarCode.size(); i++) {
            String actualPart = String.valueOf(listBarCode.get(i));
            out.print(actualPart + " ");
        }
        out.println(String.format("\nTotal a pagar: R$ %.2f", payment.getValorParcela()));
        out.println();
    }

}
