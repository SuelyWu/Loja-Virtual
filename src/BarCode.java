
import java.math.BigInteger;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BarCode {

    private List barCode;

    public BarCode(double total) {
        barCode = new LinkedList();
        barCode.add(getRandomXDig(5) + "." + getRandomXDig(5));
        barCode.add(getRandomXDig(5) + "." + getRandomXDig(5));
        barCode.add(getRandomXDig(5) + "." + getRandomXDig(5));
        barCode.add(getRandomXDig(1));
        barCode.add(getRandom14Dig(total));
    }

    private String getRandomXDig(int xDig) {
        int min = (int) Math.pow(10, xDig-1);
        int max = (int) Math.pow(10, xDig);
        return String.valueOf(ThreadLocalRandom.current().nextLong(min, max));
    }

    private String getRandom14Dig(double total) {
        BigInteger bigInteger = BigInteger.valueOf(ThreadLocalRandom.current().nextInt(1000, 10000));
        bigInteger = bigInteger.multiply(new BigInteger("10000000000"));
        bigInteger = bigInteger.add(new BigInteger(String.valueOf((int)(total*100))));
        return String.valueOf(bigInteger);
    }

    public List getBarCode() {
        return Collections.unmodifiableList(barCode);
    }


}
