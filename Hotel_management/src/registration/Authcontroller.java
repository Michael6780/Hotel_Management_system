package registration;

import registration.User;
import java.security.MessageDigest;
import java.sql.*;
import java.util.Base64;

import app.DBHelper;

public class Authcontroller {
    private static final String PEPPER = "HotelSystemPepper123!"; // Adds extra security
    
    /**
     * Authenticates a user using username and password
     * @return User if successful, null if failed
     */
    public static User authenticate(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";
        
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                String salt = rs.getString("salt");
                String inputHash = hashPassword(password, salt);
                
                if (storedHash.equals(inputHash)) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        storedHash,
                        rs.getString("role"),
                        rs.getString("name")
                    );
                }
            }
            return null; // Authentication failed
        }
    }
    
    /**
     * Hashes a password with SHA-256 + salt + pepper
     */
    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String saltedPepperedPassword = PEPPER + password + salt;
            byte[] hash = md.digest(saltedPepperedPassword.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Password hashing failed", e);
        }
    }
    
    /**
     * Generates a random salt (for new user registration)
     */
    public static String generateSalt() {
        byte[] salt = new byte[16];
        new java.security.SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}