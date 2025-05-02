package registration;

import Booking.Room;
import app.DBHelper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminController {
    
    // CREATE - Add a new room
    public boolean addRoom(String roomNumber, Room.RoomType type, double price) throws SQLException {
        if (roomExists(roomNumber)) {
            return false;
        }
        
        String sql = "INSERT INTO rooms (room_number, type, price_per_night, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, roomNumber);
            stmt.setString(2, type.name());
            stmt.setDouble(3, price);
            stmt.setString(4, Room.RoomStatus.AVAILABLE.name());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating room failed, no rows affected.");
            }
            
            return true;
        }
    }

    // READ - Get all rooms
    public List<Room> getAllRooms() throws SQLException {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms ORDER BY room_number";
        try (Connection conn = DBHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                rooms.add(mapRowToRoom(rs));
            }
        }
        return rooms;
    }

    // READ - Get single room by ID
    public Room getRoomById(int roomId) throws SQLException {
        String sql = "SELECT * FROM rooms WHERE room_id = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, roomId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToRoom(rs);
                }
            }
        }
        return null;
    }

    // READ - Get rooms by status
    public List<Room> getRoomsByStatus(Room.RoomStatus status) throws SQLException {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE status = ? ORDER BY room_number";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status.name());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    rooms.add(mapRowToRoom(rs));
                }
            }
        }
        return rooms;
    }

    // UPDATE - Room details
    public boolean updateRoom(int roomId, String roomNumber, Room.RoomType type, double price) throws SQLException {
        String sql = "UPDATE rooms SET room_number = ?, type = ?, price_per_night = ? WHERE room_id = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, roomNumber);
            stmt.setString(2, type.name());
            stmt.setDouble(3, price);
            stmt.setInt(4, roomId);
            
            return stmt.executeUpdate() > 0;
        }
    }

    // UPDATE - Room status
    public boolean updateRoomStatus(int roomId, Room.RoomStatus newStatus) throws SQLException {
        String sql = "UPDATE rooms SET status = ? WHERE room_id = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus.name());
            stmt.setInt(2, roomId);
            
            return stmt.executeUpdate() > 0;
        }
    }

    // DELETE - Remove a room
    public boolean deleteRoom(int roomId) throws SQLException {
        String sql = "DELETE FROM rooms WHERE room_id = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, roomId);
            
            return stmt.executeUpdate() > 0;
        }
    }

    // HELPER - Check if room exists by number
    public boolean roomExists(String roomNumber) throws SQLException {
        String sql = "SELECT COUNT(*) FROM rooms WHERE room_number = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, roomNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    // HELPER - Check if room exists by ID
    public boolean roomExists(int roomId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM rooms WHERE room_id = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, roomId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    // HELPER - Map ResultSet row to Room object
 // HELPER - Map ResultSet row to Room object
    private Room mapRowToRoom(ResultSet rs) throws SQLException {
        try {
            return new Room(
                rs.getInt("room_id"),
                rs.getString("room_number"),
                Room.RoomType.valueOf(rs.getString("type").toUpperCase()), // Convert to uppercase
                rs.getDouble("price_per_night"),
                Room.RoomStatus.valueOf(rs.getString("status").toUpperCase()) // Convert to uppercase
            );
        } catch (IllegalArgumentException e) {
            // Handle case where database contains invalid enum value
            throw new SQLException("Invalid room type or status in database", e);
        }
    }}