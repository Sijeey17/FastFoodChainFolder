package Project.src;
import java.text.DecimalFormat;

// This class represents a menu item in the restaurant
public class MenuItem {
    private String name;
    private double price;
    private String description;

    public MenuItem(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public String getDesc() {
        return description;
    }
    public String itemInfo() {
        return String.format(" %-20s | Php%-7s | %-30s |", name, new DecimalFormat("#,###.##").format(price), description);
    }
}