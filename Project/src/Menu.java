package Project.src;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

// This class represents the menu containing multiple menu items
public class Menu {
    private List<MenuItem> items; // List of menu items
    public LinkedHashMap<Integer, MenuItem> menuList; // Map of item ID to ItemInfo (preserves insertion order)

    public Menu() {
        items = new ArrayList<>(); {
            items.add(new MenuItem("YumBurger", 50.00, "Just a regular burger"));
            items.add(new MenuItem("Jolly Spaghetti", 120.00, "Spaghetti"));
            items.add(new MenuItem("Chicken Tender", 60.00, "Chicken"));
            items.add(new MenuItem("Regular Fries", 40.00, "Fries"));
        }
        
        menuList = new LinkedHashMap<>();
        int id = 1;
        for (MenuItem item : items) {
            menuList.put(id++, item); // Assign IDs starting from 1
        }
    }
    public Map<Integer, MenuItem> getMenuList() {
    return menuList;
    }
    public MenuItem getItemById(int id) {
        return menuList.get(id); // Retrieve item by ID
    }
    public void displayMenu() {
        System.out.printf("|%s%s%s|%n", "==================================", "MENU", "==================================");
        for (Map.Entry<Integer, MenuItem> entry : menuList.entrySet()) {
            MenuItem item = entry.getValue();
            System.out.printf("| [%d]%s%n", entry.getKey(), item.itemInfo());
        }
        System.out.println("|========================================================================|");
    }
}