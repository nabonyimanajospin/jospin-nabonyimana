public class Warehouse {
    private String warehouseId;
    private String location;
    private int capacity;
    private String managerName;

    public Warehouse(String warehouseId, String location, int capacity, String managerName) {
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be empty.");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }
        if (managerName == null || managerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Manager name cannot be empty.");
        }
        this.warehouseId = warehouseId;
        this.location = location;
        this.capacity = capacity;
        this.managerName = managerName;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public String getLocation() {
        return location;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setCapacity(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }
        this.capacity = capacity;
    }
}