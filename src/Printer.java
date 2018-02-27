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

    public void printProducts(List storeList) {
        out.println();
        out.println("=====================================================================================");
        out.println("PRODUTOS");
        out.println("\tProduct\t\t\t\tPreço Unitário\t\tEstoque");
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

        out.println("\tProduct\t\t\t\tPreço Unitário\t\tQuantidade\t\tSubtotal");
        double total = 0;
        listItems.forEach(o -> out.println(String.format("\t%s\t\tR$ %.2f\t\t\t%d\t\t\t\tR$ %.2f",
                ((AdjustableProductHolder)o).getProductName(),
                ((AdjustableProductHolder)o).getProductPrice(),
                ((AdjustableProductHolder)o).getQtt(),
                ((AdjustableProductHolder)o).getSubtotal())));
        out.println(String.format("\t\t\t\t\t\t\t\t\t\t\tTotal:\t\t\tR$ %.2f", total));
        out.println();
    }

    public void printBilletBarCode(List listBarCode) {
        out.println("Código de barras: ");
        for (int i = 0; i < listBarCode.size(); i++) {
            String actualPart = String.valueOf(listBarCode.get(i));
            out.print(actualPart + " ");
        }
        out.println();
    }

}
