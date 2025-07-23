package model;
import java.util.ArrayList;
import java.util.List;

/**
 * The Customer class represents a customer user in the airport management system.
 * 
 * <p>This class extends the base {@link User} class and adds customer-specific functionality
 * such as booking management. Customers can create, view, and manage their flight bookings
 * through the system. Each customer maintains a list of their bookings for tracking
 * and management purposes.</p>
 * 
 * <p>Key features include:</p>
 * <ul>
 *   <li>Inherits basic user functionality (username, email, password)</li>
 *   <li>Manages a collection of flight bookings</li>
 *   <li>Provides access to booking history and status</li>
 * </ul>
 * 
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see User
 * @see Booking
 */
public class Customer extends User {
    
    /**
     * List of bookings made by this customer.
     * Contains all flight bookings associated with this customer account,
     * including active, completed, and cancelled bookings.
     */
    private ArrayList<Booking> bookings = new ArrayList<>(0);

    /**
     * Constructs a new Customer with username and password.
     * 
     * <p>Creates a customer account without an email address. The email
     * can be set later using the inherited {@link User#setEmail(String)} method.
     * The customer starts with an empty booking list.</p>
     *
     * @param parUsername the customer's username for login identification
     * @param parHashedPassword the customer's password in hashed format for security
     */
    public Customer(String parUsername, String parHashedPassword) {
        super(parUsername, parHashedPassword);
    }

    /**
     * Constructs a new Customer with complete account information.
     * 
     * <p>Creates a customer account with username, email, and password.
     * This is the preferred constructor when all customer information is available.
     * The customer starts with an empty booking list.</p>
     *
     * @param parUsername the customer's username for login identification
     * @param parEmail the customer's email address for contact and notifications
     * @param parHashedPassword the customer's password in hashed format for security
     */
    public Customer(String parUsername, String parEmail, String parHashedPassword) {
        super(parUsername, parEmail, parHashedPassword);
    }

    /**
     * Constructs a new Customer with complete account information and its bookings.
     * 
     * <p>Creates a customer account with username, email, and password and its bookings.
     * This is the preferred constructor when all customer information is available (also bookings).</p>
     *
     * @param parUsername       the par username
     * @param parEmail          the par email
     * @param parHashedPassword the par hashed password
     * @param parBookings       the par bookings
     */
    public Customer(String parUsername, String parEmail, String parHashedPassword, List<Booking> parBookings){
        super(parUsername, parEmail, parHashedPassword);
        this.bookings = (ArrayList<Booking>) parBookings;
    }

    /**
     * Retrieves all bookings associated with this customer.
     * 
     * <p>Returns the complete list of bookings made by this customer,
     * including bookings in all states (active, completed, cancelled).
     * The returned list can be used for displaying booking history,
     * calculating statistics, or managing customer bookings.</p>
     *
     * @return an unmodifiable view of the customer's bookings list
     * @see Booking
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Updates the customer's booking list.
     * 
     * <p>Replaces the current booking list with the provided list.
     * This method is typically used when loading customer data from
     * the database or when bulk updating bookings. Individual booking
     * modifications should use booking-specific methods.</p>
     *
     * @param bookings the new list of bookings to associate with this customer
     * @see Booking
     */
    public void setBookings(List<Booking> bookings) {
        
        this.bookings = (ArrayList<Booking>) bookings;
    }
}