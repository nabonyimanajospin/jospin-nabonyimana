import java.time.LocalDate;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<StockItem> stockItems = new ArrayList<>();
    private static final Set<String> productNames = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("--- Advanced Stock Management System ---");
        boolean running = true;
        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Electronics Item");
            System.out.println("2. Add Clothing Item");
            System.out.println("3. Add Grocery Item");
            System.out.println("4. Add Furniture Item");
            System.out.println("5. Add Perishable Item");
            System.out.println("6. Generate Inventory Report");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = getIntInput("");
            switch (choice) {
                case 1 -> addElectronicsItem();
                case 2 -> addClothingItem();
                case 3 -> addGroceryItem();
                case 4 -> addFurnitureItem();
                case 5 -> addPerishableItem();
                case 6 -> generateInventoryReport();
                case 7 -> running = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
        System.out.println("Exiting...");
    }

    private static void addElectronicsItem() {
        System.out.println("Adding Electronics Item...");
        String id = getInput("Enter Item ID: ");
        String name = getUniqueNameInput("Enter Item Name: ");
        int qty = getPositiveIntInput("Enter Quantity In Stock: ");
        double price = getPositiveDoubleInput("Enter Price Per Unit: ");
        String supplier = getInput("Enter Supplier: ");
        int warranty = getIntInputInRange("Enter Warranty (months, 0-60): ", 0, 60);
        double discount = getDoubleInputInRange("Enter Discount (%): ", 0, 50);

        ElectronicsItem item = new ElectronicsItem(id, name, qty, price, "Electronics", supplier, warranty, discount);
        stockItems.add(item);
        System.out.println("Electronics item added successfully.");
    }

    private static void addClothingItem() {
        System.out.println("Adding Clothing Item...");
        String id = getInput("Enter Item ID: ");
        String name = getUniqueNameInput("Enter Item Name: ");
        int qty = getPositiveIntInput("Enter Quantity In Stock: ");
        double price = getPositiveDoubleInput("Enter Price Per Unit: ");
        String supplier = getInput("Enter Supplier: ");
        Set<String> sizes = getSetInput("Enter Sizes (comma-separated): ");
        Set<String> colors = getSetInput("Enter Colors (comma-separated): ");
        double discount = getDoubleInputInRange("Enter Discount (%): ", 0, 50);

        ClothingItem item = new ClothingItem(id, name, qty, price, "Clothing", supplier, sizes, colors, discount);
        stockItems.add(item);
        System.out.println("Clothing item added successfully.");
    }

    private static void addGroceryItem() {
        System.out.println("Adding Grocery Item...");
        String id = getInput("Enter Item ID: ");
        String name = getUniqueNameInput("Enter Item Name: ");
        int qty = getPositiveIntInput("Enter Quantity In Stock: ");
        double price = getPositiveDoubleInput("Enter Price Per Unit: ");
        String supplier = getInput("Enter Supplier: ");
        LocalDate expirationDate = getDateInput("Enter Expiration Date (yyyy-mm-dd): ");
        double discount = getDoubleInputInRange("Enter Discount (%): ", 0, 50);

        GroceryItem item = new GroceryItem(id, name, qty, price, "Groceries", supplier, expirationDate, discount);
        stockItems.add(item);
        System.out.println("Grocery item added successfully.");
    }

    private static void addFurnitureItem() {
        System.out.println("Adding Furniture Item...");
        String id = getInput("Enter Item ID: ");
        String name = getUniqueNameInput("Enter Item Name: ");
        int qty = getPositiveIntInput("Enter Quantity In Stock: ");
        double price = getPositiveDoubleInput("Enter Price Per Unit: ");
        String supplier = getInput("Enter Supplier: ");
        double weight = getPositiveDoubleInput("Enter Weight (kg): ");
        boolean isWellPacked = getBooleanInput("Is the item well packed? (true/false): ");

        if (!isWellPacked) {
            System.out.println("Furniture item must be well packed to be added.");
            return;
        }

        FurnitureItem item = new FurnitureItem(id, name, qty, price, "Furniture", supplier, weight, isWellPacked);
        stockItems.add(item);
        System.out.println("Furniture item added successfully.");
    }

    private static void addPerishableItem() {
        System.out.println("Adding Perishable Item...");
        String id = getInput("Enter Item ID: ");
        String name = getUniqueNameInput("Enter Item Name: ");
        int qty = getPositiveIntInput("Enter Quantity In Stock: ");
        double price = getPositiveDoubleInput("Enter Price Per Unit: ");
        String supplier = getInput("Enter Supplier: ");
        LocalDate expirationDate;

        while (true) {
            expirationDate = getDateInput("Enter Expiration Date (yyyy-mm-dd): ");
            if (expirationDate.isAfter(LocalDate.now())) {
                break;
            }
            System.out.println("Expiration date must be in the future. Please try again.");
        }

        int shelfLife = getIntInputInRange("Enter Shelf Life (days, 1-14): ", 1, 14);

        PerishableItem item = new PerishableItem(id, name, qty, price, "Perishable", supplier, expirationDate, shelfLife);
        stockItems.add(item);
        System.out.println("Perishable item added successfully.");
    }

    private static void generateInventoryReport() {
        System.out.println("\n--- Inventory Report ---");
        double totalValue = 0;

        if (stockItems.isEmpty()) {
            System.out.println("No items in inventory to generate a report.");
            return;
        }

        System.out.println("\n--- All Items ---");
        for (StockItem item : stockItems) {
            item.generateStockReport();
            totalValue += item.calculateStockValue();
        }
        System.out.println("\nTotal Inventory Value: " + totalValue);

        System.out.println("\n--- Expired or Nearly Expired Items ---");
        for (StockItem item : stockItems) {
            if (item instanceof PerishableItem p && !p.validateStock()) {
                System.out.println("[EXPIRED/NEAR EXPIRY] " + p.getItemName());
            }
        }

        System.out.println("\n--- Up-to-Date Items ---");
        for (StockItem item : stockItems) {
            if (item instanceof PerishableItem p && p.validateStock()) {
                System.out.println("[UP-TO-DATE] " + p.getItemName() + " | Current Discount: " + p.getDiscount() + "%");
                System.out.print("Would you like to increase the discount for this item? (yes/no): ");
                String response = scanner.nextLine().trim().toLowerCase();
                if (response.equals("yes")) {
                    double newDiscount = getDoubleInputInRange("Enter new discount (%): ", p.getDiscount(), 50);
                    p.setDiscount(newDiscount);
                    System.out.println("Discount updated for " + p.getItemName() + " to " + newDiscount + "%.");
                }
            }
        }

        System.out.println("\n--- Discounts and Sales Performance ---");
        for (StockItem item : stockItems) {
            System.out.println(item.getItemName() + " | Discounted Stock Value: " + item.calculateStockValue());
        }
    }

    private static String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static String getUniqueNameInput(String prompt) {
        while (true) {
            String input = getInput(prompt);
            if (productNames.add(input)) return input;
            System.out.println("Item name must be unique. Try again.");
        }
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(getInput(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    private static double getPositiveDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (!input.matches("\\d+(\\.\\d+)?")) {
                    throw new NumberFormatException("Invalid input. Only numeric values are allowed.");
                }
                double value = Double.parseDouble(input);
                if (value > 0) return value;
                System.out.println("Value must be positive.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static int getPositiveIntInput(String prompt) {
        while (true) {
            int value = getIntInput(prompt);
            if (value >= 0) return value;
            System.out.println("Value must be zero or positive.");
        }
    }

    private static int getIntInputInRange(String prompt, int min, int max) {
        while (true) {
            int value = getIntInput(prompt);
            if (value >= min && value <= max) return value;
            System.out.println("Value must be between " + min + " and " + max + ".");
        }
    }

    private static double getDoubleInputInRange(String prompt, double min, double max) {
        while (true) {
            double value = getPositiveDoubleInput(prompt);
            if (value >= min && value <= max) return value;
            System.out.println("Value must be between " + min + " and " + max + ".");
        }
    }

    private static boolean getBooleanInput(String prompt) {
        while (true) {
            String input = getInput(prompt).toLowerCase();
            if (input.equals("true") || input.equals("false")) {
                return Boolean.parseBoolean(input);
            }
            System.out.println("Invalid input. Please enter 'true' or 'false'.");
        }
    }

    private static Set<String> getSetInput(String prompt) {
        while (true) {
            String input = getInput(prompt);
            if (!input.isEmpty()) {
                Set<String> set = new HashSet<>(Arrays.asList(input.split(",")));
                return set;
            }
            System.out.println("Input cannot be empty. Try again.");
        }
    }

    private static LocalDate getDateInput(String prompt) {
        while (true) {
            try {
                return LocalDate.parse(getInput(prompt));
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use yyyy-mm-dd.");
            }
        }
    }
}

