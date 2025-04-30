public class ElectronicsItem extends StockItem {
    private int warrantyMonths;
    private double discountPercentage;

    public ElectronicsItem(String itemId, String itemName, int quantityInStock, double pricePerUnit, String category, String supplier, int warrantyMonths, double discountPercentage) {
        super(itemId, itemName, quantityInStock, pricePerUnit, category, supplier);
        if (warrantyMonths < 0 || warrantyMonths > 60) throw new IllegalArgumentException("Warranty must be between 0 and 60 months.");
        if (discountPercentage < 0 || discountPercentage > 50) throw new IllegalArgumentException("Discount must be between 0% and 50%.");
        this.warrantyMonths = warrantyMonths;
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
        System.out.println("Electronics Item Report:");
        System.out.println("ID: " + itemId + ", Name: " + itemName + ", Stock: " + quantityInStock + ", Price: " + pricePerUnit + ", Discount: " + discountPercentage + "%, Warranty: " + warrantyMonths + " months");
    }

    @Override
    public boolean validateStock() {
        return quantityInStock > 0;
    }
}