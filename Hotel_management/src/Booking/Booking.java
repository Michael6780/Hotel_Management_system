package Booking;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Booking class representing a hotel room reservation.
 */
public class Booking {
    private int bookingId;
    private int customerId;
    private int roomNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numberOfGuests;
    private double totalAmount;
    private String bookingStatus; // CONFIRMED, CHECKED_IN, CHECKED_OUT, CANCELLED
    private String paymentStatus; // PENDING, PARTIAL, PAID
    private String specialRequests;
    
    /**
     * Default constructor
     */
    public Booking() {
    }
    
    /**
     * Parameterized constructor
     */
    public Booking(int bookingId, int customerId, int roomNumber, LocalDate checkInDate, 
                  LocalDate checkOutDate, int numberOfGuests, double totalAmount, 
                  String bookingStatus, String paymentStatus, String specialRequests) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.roomNumber = roomNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
        this.totalAmount = totalAmount;
        this.bookingStatus = bookingStatus;
        this.paymentStatus = paymentStatus;
        this.specialRequests = specialRequests;
    }
    
    // Getters and Setters
    public int getBookingId() {
        return bookingId;
    }
    
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public int getRoomNumber() {
        return roomNumber;
    }
    
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    public LocalDate getCheckInDate() {
        return checkInDate;
    }
    
    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }
    
    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }
    
    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
    
    public int getNumberOfGuests() {
        return numberOfGuests;
    }
    
    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public String getBookingStatus() {
        return bookingStatus;
    }
    
    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
    
    public String getPaymentStatus() {
        return paymentStatus;
    }
    
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    
    public String getSpecialRequests() {
        return specialRequests;
    }
    
    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }
    
    /**
     * Calculate number of nights for this booking
     * @return number of nights
     */
    public long getNumberOfNights() {
        return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }
    
    /**
     * Calculate booking amount based on per night price
     * @param pricePerNight room's price per night
     */
    public void calculateTotalAmount(double pricePerNight) {
        this.totalAmount = pricePerNight * getNumberOfNights();
    }
    
    @Override
    public String toString() {
        return "Booking{" +
               "bookingId=" + bookingId +
               ", customerId=" + customerId +
               ", roomNumber=" + roomNumber +
               ", checkInDate=" + checkInDate +
               ", checkOutDate=" + checkOutDate +
               ", numberOfGuests=" + numberOfGuests +
               ", totalAmount=" + totalAmount +
               ", bookingStatus='" + bookingStatus + '\'' +
               ", paymentStatus='" + paymentStatus + '\'' +
               ", specialRequests='" + specialRequests + '\'' +
               '}';
    }
}