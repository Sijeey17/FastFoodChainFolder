package Project.lib;
import java.util.Scanner;
import Project.src.Menu;
import Project.src.Payment;
import Project.src.Order;

public interface Assets {
    Scanner input = new Scanner(System.in);

    // Instantiation of classes
    public Menu menu = new Menu();
    public Payment payment = new Payment();
    public Order order = new Order();

    public static void showSpinner() {
        char[] spinner = {'|', '/', '-', '\\'}; // rotating characters
        int delay = 200; // speed of rotation (ms)
        int duration = 15; // total spins before done

        for (int i = 0; i < duration; i++) {
            System.out.print("\r[" + spinner[i % spinner.length] + "]");
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } 
        System.out.print("\rDone!                                         \n");
    }
}