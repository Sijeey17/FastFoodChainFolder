package Project.src;
import java.util.InputMismatchException;
import Project.lib.Assets;

// Main RestaurantKiosk Class
public class RestaurantKiosk implements Assets{
    // Main Method
    public static void main(String[] args) {
        System.out.println("==========================================================================");
        System.out.printf("|%50s%23s%n", "Welcome to Restaurant Kiosk", "|");
        System.out.println("==========================================================================");

        StartSystem();
        input.close();
    }

    // Start the ordering system
    public static void StartSystem() {
         // Move order initialization outside the loop
        boolean ordering = true;

        while (ordering) {
            menu.displayMenu(); // Display the menu each time
            try {
                System.out.print("1. Create Order\n2. Exit\n");
                System.out.print("Enter your choice: ");
                int choice = input.nextInt();
                input.nextLine();

                switch (choice) {
                    case 1:
                        createOrder(order);
                        ordering = false;
                        break;
                    case 2:
                        System.out.println("Exiting the system. Thank you!");
                        ordering = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine();
            }
        }
    }

    // Create order
    public static void createOrder(Order order) {
        boolean ordering = true;

        while (ordering) {
            try {
                System.out.print("Enter Product Number (1-4), R to remove an item, or F to finish: ");
                String itemId = input.nextLine().toUpperCase();

                switch (itemId) {
                    case "F":
                        if (order.orderItems.isEmpty()) {
                            System.out.print("No items ordered. \nEnter [C] to Cancel order: ");
                            char finishOrder = input.nextLine().toUpperCase().charAt(0);

                            if (finishOrder == 'C') {
                                System.out.println("Order canceled.");
                                return;
                            } else {
                                System.out.println("Order not canceled. Going back...");
                            }
                            continue;
                        }
                        System.out.print("    Proceeding to payment...");
                        Assets.showSpinner();
                        ordering = false;
                        break;
                    case "R":
                        System.out.print("Enter Product Number to remove: ");
                        int removeId = input.nextInt();
                        System.out.print("Enter quantity to remove: ");
                        int qtyToRemove = input.nextInt();
                        input.nextLine();

                        MenuItem toRemove = menu.getItemById(removeId);
                        if (toRemove == null) {
                            System.out.println("Invalid product number to remove.");
                        } else {
                            order.removeItem(toRemove, qtyToRemove); // Remove specified quantity
                        }
                        order.displayOrder(menu.getMenuList());
                        break;
                    default:
                        int productId;
                        try {
                            productId = Integer.parseInt(itemId);
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid input! Enter 1-4, R, or F.");
                            break;
                        }
                        addItemToOrder(productId, order);
                        break;
                }
            } catch (InputMismatchException e) {
                order.displayOrder(menu.getMenuList());
                System.err.println("Invalid input! Please enter a number.");
                input.nextLine(); // Clear invalid input
            }
        }
        processPayment(order);
    }

    // Add item to order
    private static void addItemToOrder(int itemId, Order order) {
        MenuItem item = menu.getItemById(itemId); // Retrieve the item by ID
        if (item == null) {
            System.out.println("Item not found.");
            return;
        }

        System.out.printf("Enter qty for %s: ", item.getName());
        int qty = input.nextInt();
        input.nextLine();

        order.addItem(item, qty);
        System.out.println(item.getName() + " added to your order.");
        order.displayOrder(menu.getMenuList());
    }

    // Process payment
    private static void processPayment(Order order) {
        String paymentMethod;
        while (true) {
            try {
                System.out.print("Select payment method:\n[1] Cash\n[2] Card\nEnter method: ");
                int selectMethod = input.nextInt();
                input.nextLine();

                switch (selectMethod) {
                    case 1:
                        paymentMethod = "CASH";
                        break;
                    case 2:
                        paymentMethod = "CARD";
                        break;
                    default:
                        System.out.println("Select 1 or 2 only.");
                        continue; 
                }
                break;
            } catch (InputMismatchException e) {
                System.err.println("Invalid payment method.");
                input.nextLine();
            }
        }
        if (payment.processPayment(paymentMethod, order)) {
            payment.printReceipt(order);
        }
    }
}
