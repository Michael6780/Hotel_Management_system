package app;

import Booking.Room;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/hotel_management";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    
    // Initialize database connection
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }
    
    // Close connection
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
    
    // ROOM CRUD OPERATIONS
    
    /**
     * Get all rooms from database
     */
    public static List<Room> getAllRooms() throws SQLException {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM rooms";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Room room = new Room(
                    rs.getInt("id"),
                    rs.getString("number"),
                    rs.getString("type"),
                    rs.getDouble("price"),
                    rs.getString("status"),
                    rs.getInt("capacity"),
                    rs.getString("description"),
                    rs.getString("amenities")
                );
                rooms.add(room);
            }
        }
        return rooms;
    }
    
    /**
     * Get a single room by ID
     */
    public static Room getRoomById(int id) throws SQLException {
        String query = "SELECT * FROM rooms WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Room(
                        rs.getInt("id"),
                        rs.getString("number"),
                        rs.getString("type"),
                        rs.getDouble("price"),
                        rs.getString("status"),
                        rs.getInt("capacity"),
                        rs.getString("description"),
                        rs.getString("amenities")
                    );
                }
            }
        }
        return null;
    }
    
    /**
     * Add a new room to database
     */
    public static boolean addRoom(Room room) throws SQLException {
        String query = "INSERT INTO rooms (number, type, price, status, capacity, description, amenities) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, room.getNumber());
            pstmt.setString(2, room.getType());
            pstmt.setDouble(3, room.getPrice());
            pstmt.setString(4, room.getStatus());
            pstmt.setInt(5, room.getCapacity());
            pstmt.setString(6, room.getDescription());
            pstmt.setString(7, room.getAmenities());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                return false;
            }
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    room.setId(generatedKeys.getInt(1));
                }
            }
            return true;
        }
    }
    
    /**
     * Update existing room
     */
    public static boolean updateRoom(Room room) throws SQLException {
        String query = "UPDATE rooms SET number = ?, type = ?, price = ?, status = ?, " +
                      "capacity = ?, description = ?, amenities = ? WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, room.getNumber());
            pstmt.setString(2, room.getType());
            pstmt.setDouble(3, room.getPrice());
            pstmt.setString(4, room.getStatus());
            pstmt.setInt(5, room.getCapacity());
            pstmt.setString(6, room.getDescription());
            pstmt.setString(7, room.getAmenities());
            pstmt.setInt(8, room.getId());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }
    
    /**
     * Delete room by ID
     */
    public static boolean deleteRoom(int id) throws SQLException {
        String query = "DELETE FROM rooms WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }
    
    /**
     * Check if room number already exists
     */
    public static boolean roomNumberExists(String number) throws SQLException {
        String query = "SELECT COUNT(*) FROM rooms WHERE number = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, number);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    /**
     * Get available rooms of a specific type
     */
    public static List<Room> getAvailableRoomsByType(String type) throws SQLException {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM rooms WHERE type = ? AND status = 'AVAILABLE'";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, type);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Room room = new Room(
                        rs.getInt("id"),
                        rs.getString("number"),
                        rs.getString("type"),
                        rs.getDouble("price"),
                        rs.getString("status"),
                        rs.getInt("capacity"),
                        rs.getString("description"),
                        rs.getString("amenities")
                    );
                    rooms.add(room);
                }
            }
        }
        return rooms;
    }
}