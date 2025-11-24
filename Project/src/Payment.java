package Project.src;
import java.util.InputMismatchException;
import java.util.Map;
import Project.lib.Assets;
import java.text.DecimalFormat;

// This class handles payment processing
public class Payment implements Assets{
    private double payment;
    private double change = 0;

    public boolean processPayment(String paymentMethod, Order order) {
        // Simulate payment processing logic
        double remaining = order.totalAmount;
        double totalPaid = 0;
        double paid;

        if (paymentMethod.equals("CASH")) {
            while(true) {
                while(true){
                    try {
                        System.out.print("Enter payment: ");
                        paid = input.nextDouble();
                        input.nextLine();
                        if (paid < 0) {
                            System.out.println("Payment cannot be negative. Please try again.");
                            continue;
                        }
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid amount.");
                        input.nextLine();
                    }
                }

                totalPaid += paid;
                remaining = order.totalAmount - totalPaid;

                if (remaining <= 0) {
                    payment = totalPaid;
                    if (remaining < 0) change = -remaining;
                    
                    System.out.printf("    Processing %s payment of Php%s...", paymentMethod, new DecimalFormat("#,##0.00").format(payment));
                    Assets.showSpinner();
                    break;
                } else {
                    System.out.printf("Insufficient payment. Please add Php %s%n" , new DecimalFormat("#,##0.00").format(remaining));
                }
            }
            return true;
        } else if (paymentMethod.equals("CARD")) {
            payment = order.totalAmount;
            System.out.printf("    Processing %s payment of Php%s...", paymentMethod, new DecimalFormat("#,##0.00").format(payment));
            Assets.showSpinner();
            return true;
        }
        return false;
    }

    // Print receipt
    public void printReceipt(Order order) {
        System.out.print("----------------------------------------------------------\n");
        System.out.printf("|\t\t          %-31s|%n","RECEIPT");
        System.out.print("----------------------------------------------------------\n");
        System.out.printf("| %-24s | %-12s | %-12s |%n", "Name", "Qty", "Price");
        System.out.print("|--------------------------|--------------|--------------|\n");

        // Iterate itemQuantities (canonical source of quantity)
        for (Map.Entry<MenuItem, Integer> entry : order.itemQuantities.entrySet()) {
            MenuItem mi = entry.getKey();
            int qty = entry.getValue();
            System.out.printf("| %-24s | %-12s | Php%-10s|%n", mi.getName(), new DecimalFormat().format(qty), new DecimalFormat("#,###.00").format(mi.getPrice() * qty));
        }
        System.out.print("----------------------------------------------------------\n");
        System.out.printf("| \u001B[1m%s%20s%26s\u001B[0m|%n", "SUB TOTAL", "", new DecimalFormat("#,##0.00").format((order.totalAmount - order.totalVat)));
        System.out.printf("| VAT%26s%26s|%n", "", new DecimalFormat("#,##0.00").format(order.totalVat));
        System.out.printf("| \u001B[1m%s%24s%26s\u001B[0m|%n", "TOTAL", "", new DecimalFormat("#,##0.00").format(order.totalAmount));
        System.out.printf("| CASH%25s%26s|%n", "", new DecimalFormat("#,##0.00").format(payment));
        System.out.printf("| CHANGE%23s%26s|%n", "", new DecimalFormat("#,##0.00").format(change));
        System.out.print("----------------------------------------------------------\n");
        System.out.printf("%n%33s%n%39s", "THANK YOU!", "Glad to see you again!");
    }
}