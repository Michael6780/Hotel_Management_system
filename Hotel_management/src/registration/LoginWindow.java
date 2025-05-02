package registration;

import registration.User;
import registration.AdminController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class LoginWindow extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    
    public LoginWindow() {
        setTitle("Hotel Management - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window
        initUI();
    }
    
    private void initUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Header
        JLabel lblHeader = new JLabel("HOTEL MANAGEMENT LOGIN");
        lblHeader.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblHeader, gbc);
        
        // Username
        gbc.gridwidth = 1; gbc.gridy = 1;
        panel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1;
        txtUsername = new JTextField(15);
        panel.add(txtUsername, gbc);
        
        // Password
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1;
        txtPassword = new JPasswordField(15);
        panel.add(txtPassword, gbc);
        
        // Login Button
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(this::performLogin);
        panel.add(btnLogin, gbc);
        
        add(panel);
    }
    
    private void performLogin(ActionEvent e) {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Username and password cannot be empty!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            User user = Authcontroller.authenticate(username, password);
            if (user != null) {
                openDashboard(user);
                dispose(); // Close login window
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Invalid username or password", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Database error: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void openDashboard(User user) {
        if (user.isAdmin()) {
            new AdminDashboard().setVisible(true); // Open admin panel
        } else {
            // For customers (or other roles)
            JOptionPane.showMessageDialog(this,
                "Welcome, " + user.getName() + "!\nCustomer features coming soon.",
                "Login Successful",
                JOptionPane.INFORMATION_MESSAGE);
            // Here you would later open CustomerDashboard()
        }
    }
    }
