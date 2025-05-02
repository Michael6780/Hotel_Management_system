package Booking;

public class Room {
    private int id;
    private String number;
    private String type;
    private double price;
    private String status;
    private int capacity;
    private String description;
    private String amenities;

    // Constructors
    public Room() {
        // Default constructor
    }

    public Room(int id, String number, String type, double price, String status) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.price = price;
        this.status = status;
    }

    public Room(int id, String number, String type, double price, String status, 
                int capacity, String description, String amenities) {
        this(id, number, type, price, status);
        this.capacity = capacity;
        this.description = description;
        this.amenities = amenities;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        if (number == null || number.trim().isEmpty()) {
            throw new IllegalArgumentException("Room number cannot be empty");
        }
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Room type cannot be empty");
        }
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be empty");
        }
        this.status = status;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    // Business Logic Methods
    public boolean isAvailable() {
        return "AVAILABLE".equalsIgnoreCase(status);
    }

    public boolean isUnderMaintenance() {
        return "MAINTENANCE".equalsIgnoreCase(status);
    }

    // Override methods
    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", capacity=" + capacity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id && number.equals(room.number);
    }

    @Override
    public int hashCode() {
        return 31 * id + number.hashCode();
    }

    // Static utility methods
    public static String[] getAllRoomTypes() {
        return new String[]{"STANDARD", "DELUXE", "SUITE", "FAMILY", "EXECUTIVE"};
    }

    public static String[] getAllStatuses() {
        return new String[]{"AVAILABLE", "OCCUPIED", "MAINTENANCE", "RESERVED"};
    }
}