package app;

import registration.LoginWindow;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        // Set system look and feel
        setLookAndFeel();
        
        // Initialize database connection pool
        initializeDatabase();
        
        // Show login window
        showLoginWindow();
    }

    private static void setLookAndFeel() {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Custom UI improvements
            UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
            UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, 12));
            UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 14));
        } catch (Exception e) {
            System.err.println("Error setting look and feel: " + e.getMessage());
        }
    }

    private static void initializeDatabase() {
        try {
            // Test database connection
            Connection testConn = DBHelper.getConnection();
            if (testConn != null) {
                System.out.println("Database connection established successfully");
                DBHelper.closeConnection(testConn);
                
                // Check if tables exist, create if not
                checkAndCreateTables();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                "Failed to connect to database:\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private static void checkAndCreateTables() {
        try (Connection conn = DBHelper.getConnection()) {
            DatabaseMetaData dbm = conn.getMetaData();
            
            // Check if rooms table exists
            ResultSet tables = dbm.getTables(null, null, "rooms", null);
            if (!tables.next()) {
                // Create rooms table if it doesn't exist
                createRoomsTable(conn);
            }
            
            // Check if users table exists (for authentication)
            tables = dbm.getTables(null, null, "users", null);
            if (!tables.next()) {
                // Create users table if it doesn't exist
                createUsersTable(conn);
            }
        } catch (SQLException e) {
            System.err.println("Error checking/creating tables: " + e.getMessage());
        }
    }

    private static void createRoomsTable(Connection conn) throws SQLException {
        String createTableSQL = "CREATE TABLE rooms ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "number VARCHAR(10) UNIQUE NOT NULL,"
                + "type VARCHAR(20) NOT NULL,"
                + "price DECIMAL(10,2) NOT NULL,"
                + "status VARCHAR(20) NOT NULL,"
                + "capacity INT NOT NULL,"
                + "description TEXT,"
                + "amenities TEXT)";
        
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Created 'rooms' table");
            
            // Insert sample data if needed
            insertSampleRooms(conn);
        }
    }

    private static void createUsersTable(Connection conn) throws SQLException {
        String createTableSQL = "CREATE TABLE users ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "username VARCHAR(50) UNIQUE NOT NULL,"
                + "password VARCHAR(100) NOT NULL,"
                + "name VARCHAR(100) NOT NULL,"
                + "email VARCHAR(100) UNIQUE NOT NULL,"
                + "role ENUM('admin','staff','customer') NOT NULL,"
                + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Created 'users' table");
            
            // Insert admin user if needed
            insertAdminUser(conn);
        }
    }

    private static void insertSampleRooms(Connection conn) throws SQLException {
        String insertSQL = "INSERT INTO rooms (number, type, price, status, capacity, description, amenities) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            // Sample room 1
            pstmt.setString(1, "101");
            pstmt.setString(2, "DELUXE");
            pstmt.setDouble(3, 150.0);
            pstmt.setString(4, "AVAILABLE");
            pstmt.setInt(5, 2);
            pstmt.setString(6, "Spacious deluxe room with king bed");
            pstmt.setString(7, "TV, AC, Mini-bar, WiFi");
            pstmt.addBatch();
            
            // Sample room 2
            pstmt.setString(1, "102");
            pstmt.setString(2, "STANDARD");
            pstmt.setDouble(3, 100.0);
            pstmt.setString(4, "AVAILABLE");
            pstmt.setInt(5, 2);
            pstmt.setString(6, "Standard room with queen bed");
            pstmt.setString(7, "TV, AC, WiFi");
            pstmt.addBatch();
            
            pstmt.executeBatch();
            System.out.println("Inserted sample rooms");
        }
    }

    private static void insertAdminUser(Connection conn) throws SQLException {
        String insertSQL = "INSERT INTO users (username, password, name, email, role) "
                + "VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            // Default admin user (in real app, password should be hashed)
            pstmt.setString(1, "admin");
            pstmt.setString(2, "admin123");
            pstmt.setString(3, "System Administrator");
            pstmt.setString(4, "admin@hotel.com");
            pstmt.setString(5, "admin");
            
            pstmt.executeUpdate();
            System.out.println("Created default admin user");
        }
    }

    private static void showLoginWindow() {
        SwingUtilities.invokeLater(() -> {
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.setVisible(true);
            
            // Center the window
            loginWindow.setLocationRelativeTo(null);
            
            // Set application icon
            setApplicationIcon(loginWindow);
        });
    }

    private static void setApplicationIcon(JFrame frame) {
        try {
            // Load icon from resources (you should add an icon file to your project)
            ImageIcon icon = new ImageIcon(Main.class.getResource("/images/hotel-icon.png"));
            frame.setIconImage(icon.getImage());
        } catch (Exception e) {
            System.err.println("Error loading application icon: " + e.getMessage());
        }
    }
}