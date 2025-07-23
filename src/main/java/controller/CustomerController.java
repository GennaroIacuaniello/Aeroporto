package controller;
import model.Customer;
import java.util.ArrayList;
import java.util.List;

/**
 * The CustomerController class manages customer authentication, session data, and search operations.
 * <p>
 * This controller handles operations related to customer users, including login, session management,
 * retrieving customer information, and managing search results for booking operations.
 * It maintains the state of the currently logged-in customer and provides methods to access
 * and modify customer session data securely.
 * </p>
 * 
 * @author Your Name
 * @version 1.0
 * @since 1.0
 */
public class CustomerController {
    
    /**
     * The unique identifier of the currently logged-in customer.
     * This ID corresponds to the customer's record in the database and is used
     * for database operations related to the customer.
     * Set to null when no customer is logged in.
     */
    private Integer loggedCustomerId;
    
    /**
     * The Customer object representing the currently logged-in customer.
     * This object contains the customer's information such as username, email,
     * and hashed password. Set to null when no customer is logged in.
     */
    private Customer loggedCustomer;
    
    /**
     * List of Customer objects resulting from booking search operations.
     * This list contains customers found during booking-related searches,
     * typically used by administrators to manage customer bookings.
     */
    private ArrayList<Customer> searchBookingResultCustomers;
    
    /**
     * List of customer IDs corresponding to the search results.
     * This list contains the database IDs of customers found during booking searches,
     * maintaining a parallel structure with searchBookingResultCustomers.
     */
    private ArrayList<Integer> searchBookingResultCustomersIds;

    /**
     * Sets the currently logged-in customer with the provided credentials and ID.
     * <p>
     * This method creates a new Customer object with the provided credentials and stores it 
     * along with the customer's ID. This is typically used during the authentication process
     * when customer credentials are verified against the database.
     * </p>
     *
     * @param username       the customer's username - must not be null or empty
     * @param mail           the customer's email address - must be a valid email format
     * @param hashedPassword the customer's hashed password (for security) - must not be null
     * @param id             the customer's unique identifier in the database - must not be null
     * 
     * @throws IllegalArgumentException if any parameter is null or if username/email is empty
     */
    public void setLoggedCustomer(String username, String mail, String hashedPassword, Integer id) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (mail == null || mail.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (hashedPassword == null) {
            throw new IllegalArgumentException("Hashed password cannot be null");
        }
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        
        loggedCustomer = new Customer(username, mail, hashedPassword);
        loggedCustomerId = id;
    }
    
    /**
     * Sets the currently logged-in customer using an existing Customer object and ID.
     * <p>
     * This method stores a reference to the provided Customer object along with the customer's ID.
     * Used primarily during authentication and session management when a Customer object
     * already exists and needs to be set as the current logged-in customer.
     * </p>
     *
     * @param customer the Customer object representing the logged-in customer - must not be null
     * @param id       the customer's unique identifier in the database - must not be null
     * 
     * @throws IllegalArgumentException if customer or id is null
     */
    public void setLoggedCustomer(Customer customer, Integer id) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        
        loggedCustomer = customer;
        loggedCustomerId = id;
    }
    
    /**
     * Retrieves the currently logged-in customer.
     * <p>
     * Returns the Customer object that was previously set using one of the setLoggedCustomer methods.
     * This method is used throughout the application to access the current customer's information.
     * </p>
     *
     * @return the Customer object representing the currently logged-in customer,
     *         or null if no customer is logged in
     */
    public Customer getLoggedCustomer() {
        return loggedCustomer;
    }
    
    /**
     * Retrieves the unique identifier of the currently logged-in customer.
     * <p>
     * This ID corresponds to the customer's record in the database and is used
     * for database operations that require the customer's identifier. The ID is
     * set during the login process and cleared when the customer logs out.
     * </p>
     *
     * @return the Integer ID of the currently logged-in customer,
     *         or null if no customer is logged in
     */
    public Integer getLoggedCustomerId() {
        return loggedCustomerId;
    }
    
    /**
     * Sets the search results for booking-related customer searches.
     * <p>
     * This method stores both the Customer objects and their corresponding IDs
     * from a booking search operation. Typically used by administrators when
     * searching for customers in the context of booking management.
     * </p>
     *
     * @param customers the list of Customer objects found in the search - must not be null
     * @param customerIds the list of corresponding customer IDs - must not be null and same size as customers
     * 
     * @throws IllegalArgumentException if either list is null or if sizes don't match
     */
    public void setSearchBookingResults(ArrayList<Customer> customers, ArrayList<Integer> customerIds) {
        if (customers == null) {
            throw new IllegalArgumentException("Customers list cannot be null");
        }
        if (customerIds == null) {
            throw new IllegalArgumentException("Customer IDs list cannot be null");
        }
        if (customers.size() != customerIds.size()) {
            throw new IllegalArgumentException("Customers and IDs lists must have the same size");
        }
        
        this.searchBookingResultCustomers = customers;
        this.searchBookingResultCustomersIds = customerIds;
    }
    
    /**
     * Retrieves the customer search results from booking operations.
     *
     * @return the list of Customer objects from the last booking search,
     *         or null if no search has been performed
     */
    public ArrayList<Customer> getSearchBookingResultCustomers() {
        return searchBookingResultCustomers;
    }
    
    /**
     * Retrieves the customer ID search results from booking operations.
     *
     * @return the list of customer IDs from the last booking search,
     *         or null if no search has been performed
     */
    public ArrayList<Integer> getSearchBookingResultCustomersIds() {
        return searchBookingResultCustomersIds;
    }
    
    /**
     * Clears the current customer session data.
     * <p>
     * This method logs out the current customer by setting both the logged customer
     * and customer ID to null. Should be called when the customer logs out or when
     * the session needs to be cleared for security reasons.
     * </p>
     */
    public void logout() {
        this.loggedCustomer = null;
        this.loggedCustomerId = null;
    }
    
    /**
     * Checks if a customer is currently logged in.
     * <p>
     * This method provides a convenient way to check if there's an active customer session
     * without having to check for null values directly.
     * </p>
     *
     * @return true if a customer is currently logged in, false otherwise
     */
    public boolean isCustomerLoggedIn() {
        return this.loggedCustomer != null && this.loggedCustomerId != null;
    }
    
    /**
     * Clears the search results from booking operations.
     * <p>
     * This method clears both the customer objects and their IDs from previous
     * search operations, typically called when starting a new search or when
     * search data is no longer needed.
     * </p>
     */
    public void clearSearchResults() {
        this.searchBookingResultCustomers = null;
        this.searchBookingResultCustomersIds = null;
    }
}