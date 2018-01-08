import parser.Collector;
import parser.ParseHTML;

public class MainTest {
    public static void main(String[] args) {
        long t = System.currentTimeMillis();
        new Collector().start();
        System.out.println((System.currentTimeMillis() - t) / 1000 + "s");
    }
}
