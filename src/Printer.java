import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public void printProducts(Map mapProducts) {
        out.println();
        out.println("=====================================================================================");
        out.println("PRODUTOS");
        out.println("\tProduct\t\t\t\tPreço Unitário\t\tStore");
        List listExistTypes = Arrays.asList(mapProducts.keySet().toArray());
        listExistTypes = (List) listExistTypes.stream().sorted().collect(Collectors.toList());
        for (int i = 0; i < listExistTypes.size(); i++) {
            ProductType productType = (ProductType)listExistTypes.get(i);
            List listProd = (List)mapProducts.get(productType);
            Product product = (Product)listProd.get(i);
            out.println(String.format("\t%s\t\tR$ %.2f\t\t\t%d", product.getName(), product.getPrice(), listProd.size()));
        }
        out.println();
    }

    public void printShoppingCart(List listItems) {
        out.println();
        out.println("=====================================================================================");
        out.println("Carrinho de Compras:");

        if (listItems.isEmpty()){
            out.println("Seu carrinho está vazio!");
            return;
        }

        out.println("\tProduct\t\t\t\tPreço Unitário\t\tQuantidade\t\tSubtotal");
        double total = 0;
        for (int i = 0; i < listItems.size(); i++){
            OrderItem orderItem = (OrderItem) listItems.get(i);
            total += orderItem.getSubtotal();
            out.println(String.format("\t%s\t\tR$ %.2f\t\t\t%d\t\t\t\tR$ %.2f",
                    orderItem.getProdName(), orderItem.getProdPrice(), orderItem.getQtt(), orderItem.getSubtotal()));
        }
        out.println(String.format("\t\t\t\t\t\t\t\t\t\t\tTotal:\t\t\tR$ %.2f", total));
        out.println();
    }

    public void printBilletBarCode(List listBarCode) {
        out.println("Código de barras:");
        for (int i = 0; i < listBarCode.size(); i++) {
            String parteAtual = String.valueOf(listBarCode.get(i));
            out.print(parteAtual + " ");
        }
        out.println();
    }

}
