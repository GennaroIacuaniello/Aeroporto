package model;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer user in the airport management system.
 * <p>
 * This class extends the {@link User} class to provide customer-specific functionality
 * within the airport management system. Customers are regular users who can book flights,
 * manage their bookings, and perform standard passenger operations.
 * </p>
 * <p>
 * This class maintains a collection of bookings associated with the customer,
 *  providing easy access to their travel history and active bookings.
 * </p>
 * <p>
 * Customer users can access customer-specific interfaces and perform operations such as:
 * </p>
 * <ul>
 *   <li>Searching and viewing flights</li>
 *   <li>Creating new flight bookings</li>
 *   <li>Managing existing bookings (view, modify, cancel)</li>
 *   <li>Checking-in for flights</li>
 *   <li>Managing luggage associated with bookings</li>
 *   <li>Viewing booking history and status</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see User
 * @see Booking
 */
public class Customer extends User{

    /**
     * The list of bookings associated with this customer.
     * <p>
     * This field stores all flight bookings made by the customer, including confirmed,
     * pending, and cancelled bookings. The collection is implemented as an
     * {@link ArrayList} for efficient access and modification operations.
     * </p>
     * <p>
     * The bookings list is initialized as an empty collection when a customer is
     * created without existing bookings, and can be populated through the
     * {@link #setBookings(List)} method or by adding individual bookings through
     * the booking management system.
     * </p>
     *
     * @see Booking
     */
    private ArrayList<Booking> bookings;

    /**
     * Constructs a new Customer with a username, an email, and a hashed password.
     * <p>
     * Creates a customer user account with the specified credentials. The customer
     * is initialized with an empty bookings list.
     * This constructor is typically used for new customer registration.
     * </p>
     * <p>
     * The password should be provided in hashed format for security purposes and
     * cannot be changed after object creation due to the immutable nature of the
     * password field inherited from the parent {@link User} class.
     * </p>
     *
     * @param parUsername the username for the customer account. Must not be null or empty.
     *                   This serves as the primary identifier for login purposes.
     * @param parEmail the email address for the customer account. Must not be null or empty.
     *
     * @param parHashedPassword the hashed password for the customer account. Must not be null or empty.
     *                         The password is stored in a hashed format for security purposes.
     * @see User#User(String, String, String)
     */
    public Customer(String parUsername, String parEmail, String parHashedPassword){
        super(parUsername, parEmail, parHashedPassword);
        this.bookings = new ArrayList<>(0);
    }

    /**
     * Constructs a new Customer with username, email, hashed password, and existing bookings.
     * <p>
     * Creates a customer user account with the specified credentials and associates
     * it with an existing list of bookings. This constructor is typically used when
     * loading customer data from persistent storage.
     * </p>
     *
     * @param parUsername the username for the customer account. Must not be null or empty.
     *                   This serves as the primary identifier for login purposes.
     * @param parEmail the email address for the customer account. Must not be null or empty.
     *
     * @param parHashedPassword the hashed password for the customer account. Must not be null or empty.
     *                         The password is stored in a hashed format for security purposes.
     * @param parBookings the list of existing bookings to associate with this customer.
     *                   Must not be null, but can be empty.
     *
     * @see User#User(String, String, String)
     * @see Booking
     */
    public Customer(String parUsername, String parEmail, String parHashedPassword, List<Booking> parBookings){
        super(parUsername, parEmail, parHashedPassword);
        this.bookings = (ArrayList<Booking>) parBookings;
    }

    /**
     * Gets the list of bookings associated with this customer.
     * <p>
     * Returns the complete list of flight bookings made by this customer,
     * including active, completed, and cancelled reservations. The returned
     * list provides read access to the customer's booking history and can
     * be used for displaying booking information in user interfaces.
     * </p>
     * <p>
     * The returned list is the actual internal collection, so modifications
     * to the returned list will affect the customer's booking data. For
     * safe modification of bookings, use the {@link #setBookings(List)} method
     * or work through the booking management system.
     * </p>
     *
     * @return the list of bookings associated with this customer, never null
     *         but may be empty if the customer has no bookings
     * @see Booking
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Sets the list of bookings for this customer.
     * <p>
     * Updates the complete list of flight bookings associated with this customer.
     * This method replaces the entire booking collection with the provided list,
     * which is useful for bulk updates or when loading customer data from storage.
     * </p>
     * <p>
     * The provided list is cast to an {@link ArrayList} for internal storage
     * consistency. This ensures optimal performance for booking access and
     * modification operations within the airport management system.
     * </p>
     *
     * @param bookings the new list of bookings to associate with this customer.
     *                Must not be null, but can be empty to clear all bookings.
     *                The list will be converted to an ArrayList for internal storage.
     * @see Booking
     */
    public void setBookings(List<Booking> bookings) {
        this.bookings = (ArrayList<Booking>) bookings;
    }

}