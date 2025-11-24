package Project.src;
// OrderItem Class to track individual items (no quantity here; the Order's itemQuantities map is canonical)
public class OrderItem{
    private final MenuItem item;

    public OrderItem(MenuItem item) {
        this.item = item;
    }
    public MenuItem getItem() {
        return item;
    }
    public double getTotalPrice(int quantity) {
        return item.getPrice() * quantity; // Calculate total price based on passed quantity
    }
}