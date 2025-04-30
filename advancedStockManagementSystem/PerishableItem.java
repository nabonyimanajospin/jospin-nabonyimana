import java.time.LocalDate;

public class PerishableItem extends StockItem {
    private LocalDate expirationDate;
    private int shelfLifeDays;
    private double discount;

    public PerishableItem(String itemId, String itemName, int quantityInStock, double pricePerUnit, String category, String supplier, LocalDate expirationDate, int shelfLifeDays) {
        super(itemId, itemName, quantityInStock, pricePerUnit, category, supplier);
        if (shelfLifeDays <= 0 || shelfLifeDays > 14) {
            throw new IllegalArgumentException("Shelf life must be between 1 and 14 days.");
        }
        this.expirationDate = expirationDate;
        this.shelfLifeDays = shelfLifeDays;
        this.discount = 0; // Default discount
    }

    // Getter for discount
    public double getDiscount() {
        return discount;
    }

    // Setter for discount with validation
    public void setDiscount(double discount) {
        if (discount < 0 || discount > 50) {
            throw new IllegalArgumentException("Discount must be between 0% and 50%.");
        }
        this.discount = discount;
    }

    // Calculate stock value with discount applied
    @Override
    public double calculateStockValue() {
        double discountedPrice = pricePerUnit * (1 - discount / 100);
        return discountedPrice * quantityInStock;
    }

    // Validate stock based on expiration date
    @Override
    public boolean validateStock() {
        return expirationDate.isAfter(LocalDate.now());
    }

    // Generate a detailed stock report for the perishable item
    @Override
    public void generateStockReport() {
        System.out.println("Perishable Item Report:");
        System.out.println("ID: " + itemId + ", Name: " + itemName + ", Stock: " + quantityInStock + ", Price: " + pricePerUnit + ", Discount: " + discount + "%, Expiration Date: " + expirationDate + ", Shelf Life: " + shelfLifeDays + " days");
    }

    // Update stock quantity
    @Override
    public void updateStock(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.quantityInStock += quantity;
    }
}