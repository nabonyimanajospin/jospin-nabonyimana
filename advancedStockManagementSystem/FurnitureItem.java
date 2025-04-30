public class FurnitureItem extends StockItem {
    private double weight;
    private boolean isWellPacked;

    public FurnitureItem(String itemId, String itemName, int quantityInStock, double pricePerUnit, String category, String supplier, double weight, boolean isWellPacked) {
        super(itemId, itemName, quantityInStock, pricePerUnit, category, supplier);
        if (weight <= 0) throw new IllegalArgumentException("Weight must be greater than zero.");
        this.weight = weight;
        this.isWellPacked = isWellPacked;
    }

    @Override
    public void updateStock(int quantity) {
        if (quantity < 0) throw new IllegalArgumentException("Quantity cannot be negative.");
        this.quantityInStock += quantity;
    }

    @Override
    public double calculateStockValue() {
        return pricePerUnit * quantityInStock;
    }

    @Override
    public void generateStockReport() {
        System.out.println("Furniture Item Report:");
        System.out.println("ID: " + itemId + ", Name: " + itemName + ", Stock: " + quantityInStock + ", Price: " + pricePerUnit + ", Weight: " + weight + "kg, Well Packed: " + isWellPacked);
    }

    @Override
    public boolean validateStock() {
        return isWellPacked;
    }
}