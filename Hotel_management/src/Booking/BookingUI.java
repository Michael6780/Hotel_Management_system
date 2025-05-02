package Booking;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class BookingUI extends JFrame {
    private JComboBox<Room.RoomType> roomTypeCombo;
    private JSpinner guestsSpinner;
    private JButton checkAvailabilityBtn;

    public BookingUI() {
        setTitle("Room Booking");
        setSize(500, 300);
        initComponents();
    }

    private void initComponents() {
        roomTypeCombo = new JComboBox<>(Room.RoomType.values());
        guestsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 4, 1));
        checkAvailabilityBtn = new JButton("Check Availability");

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.add(new JLabel("Room Type:"));
        panel.add(roomTypeCombo);
        panel.add(new JLabel("Guests:"));
        panel.add(guestsSpinner);
        panel.add(new JLabel(""));
        panel.add(checkAvailabilityBtn);

        add(panel, BorderLayout.CENTER);
    }
}