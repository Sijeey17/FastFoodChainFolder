package Project.src;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.text.DecimalFormat;

// This class represents a customer's order
public class Order {
    public List<OrderItem> orderItems;
    public LinkedHashMap<MenuItem, Integer> itemQuantities;
    public double totalAmount;
    double totalVat = 0, vat = 0.12;
    double subTotal;

    // Constructor
    public Order() {
        orderItems = new ArrayList<>();
        itemQuantities = new LinkedHashMap<>();
        totalAmount = 0.0;
        subTotal = 0.0;
    }

    // Add item to order
    public void addItem(MenuItem item, int quantity) {
        // Keep a single OrderItem per distinct MenuItem and track quantity in itemQuantities
        if (!itemQuantities.containsKey(item)) {
            orderItems.add(new OrderItem(item));
        }
        itemQuantities.put(item, itemQuantities.getOrDefault(item, 0) + quantity);
        calculateTotal();
    }

    // Remove item from order
    public void removeItem(MenuItem item, int qtyToRemove) {
        if (itemQuantities.containsKey(item)) {
            int currentQuantity = itemQuantities.get(item);
            if (qtyToRemove >= currentQuantity) {
                itemQuantities.remove(item);
                // remove corresponding OrderItem using iterator to avoid ConcurrentModificationException
                Iterator<OrderItem> iterator = orderItems.iterator();
                while (iterator.hasNext()) {
                    OrderItem orderItem = iterator.next();
                    if (orderItem.getItem().equals(item)) {
                        iterator.remove();
                        break;
                    }
                }
                System.out.println(item.getName() + " removed from your order.");
            } else {
                itemQuantities.put(item, currentQuantity - qtyToRemove);
                System.out.println(qtyToRemove + " of " + item.getName() + " removed from your order.");
            }
        }
        calculateTotal(); // Recalculate total after removal
    }

    // Calculate total amount (compute subtotal, VAT, then total)
    public void calculateTotal() {
        double total = 0;
        for (Map.Entry<MenuItem, Integer> entry : itemQuantities.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        totalAmount = total;
        totalVat = totalAmount * vat;
    }

    // Display order summary (accepts a map of menu items to print item IDs)
    public void displayOrder(Map<Integer, MenuItem> menuList) {
        System.out.printf("|%s%s%s|%n", "======================", "Current order", "=====================");
        System.out.printf("| %-24s | %-12s | %-12s |%n", "Item", "Quantity", "Amount");
        System.out.print("----------------------------------------------------------\n");

        for (Map.Entry<Integer, MenuItem> entry : menuList.entrySet()) {
            MenuItem menuItem = entry.getValue();
            if (itemQuantities.containsKey(menuItem)) {
                int quantity = itemQuantities.get(menuItem);
                double amount = menuItem.getPrice() * quantity;
                System.out.printf("| [%d]%-21s | %-12s | Php%-10s|%n", entry.getKey(), menuItem.getName(), new DecimalFormat("#,###").format(quantity), new DecimalFormat("#,##0.00").format(amount));
            }
        }
        System.out.print("----------------------------------------------------------\n");
        System.out.printf("| %-42sPhp%-10s|%n%n", "Total Amount:", new DecimalFormat("#,##0.00").format(totalAmount));
    }
}