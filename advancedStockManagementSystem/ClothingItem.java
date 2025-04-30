import java.util.Set;

public class ClothingItem extends StockItem {
    private Set<String> availableSizes;
    private Set<String> availableColors;
    private double discountPercentage;

    public ClothingItem(String itemId, String itemName, int quantityInStock, double pricePerUnit, String category, String supplier, Set<String> availableSizes, Set<String> availableColors, double discountPercentage) {
        super(itemId, itemName, quantityInStock, pricePerUnit, category, supplier);
        if (discountPercentage < 0 || discountPercentage > 50) throw new IllegalArgumentException("Discount must be between 0% and 50%.");
        this.availableSizes = availableSizes;
        this.availableColors = availableColors;
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
        System.out.println("Clothing Item Report:");
        System.out.println("ID: " + itemId + ", Name: " + itemName + ", Stock: " + quantityInStock + ", Price: " + pricePerUnit + ", Discount: " + discountPercentage + "%");
        System.out.println("Available Sizes: " + availableSizes + ", Available Colors: " + availableColors);
    }

    @Override
    public boolean validateStock() {
        return quantityInStock > 0;
    }
}