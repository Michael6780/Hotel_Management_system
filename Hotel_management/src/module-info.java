/**
 * 
 */
/**
 * 
 */
module Hotel_management {
    requires java.sql;
    requires java.desktop;  // For Swing components
    
    opens app;
    opens Booking;
    opens registration;
}