import java.time.LocalDate;

public class GroceryItem extends StockItem {
    private LocalDate expirationDate;
    private double discountPercentage;

    public GroceryItem(String itemId, String itemName, int quantityInStock, double pricePerUnit, String category, String supplier, LocalDate expirationDate, double discountPercentage) {
        super(itemId, itemName, quantityInStock, pricePerUnit, category, supplier);
        if (discountPercentage < 0 || discountPercentage > 50) throw new IllegalArgumentException("Discount must be between 0% and 50%.");
        this.expirationDate = expirationDate;
        this.discountPercentage = discountPercentage;
    }

    @Override
    public void updateStock(int quantity) {
        if (quantity < 0) throw new IllegalArgumentException("Quantity cannot be negative.");
        this.quantityInStock += quantity;
    }

    @Override
    public double calculateStockValue() {
        double discountedPrice = pricePerUnit * (1 - discountPercentage / 100);
        return discountedPrice * quantityInStock;
    }

    @Override
    public void generateStockReport() {
        System.out.println("Grocery Item Report:");
        System.out.println("ID: " + itemId + ", Name: " + itemName + ", Stock: " + quantityInStock + ", Price: " + pricePerUnit + ", Discount: " + discountPercentage + "%, Expiration Date: " + expirationDate);
    }

    @Override
    public boolean validateStock() {
        return expirationDate.isAfter(LocalDate.now());
    }
}