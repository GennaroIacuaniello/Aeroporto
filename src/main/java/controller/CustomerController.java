package controller;

import model.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for managing customer user operations and session state in the airport management system.
 * <p>
 * This class serves as the specialized controller for customer-related operations within the MVC
 * architecture of the airport management system. It maintains the current customer session state,
 * manages customer search results, and provides comprehensive methods for accessing customer
 * information and managing customer-related operations.
 * </p>
 * <p>
 * The CustomerController is responsible for:
 * </p>
 * <ul>
 *   <li>Maintaining the currently logged-in customer's session information</li>
 *   <li>Managing customer search results from booking and administrative operations</li>
 *   <li>Providing access to customer profile data during the session</li>
 *   <li>Supporting customer authentication state and user identification</li>
 *   <li>Facilitating customer-specific operations and permissions</li>
 *   <li>Managing collections of customers retrieved from various search operations</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Customer
 * @see Controller
 */
public class CustomerController {
    
    /**
     * The unique database identifier for the currently logged-in customer.
     * This field is null when no customer is logged in and contains the
     * customer's database ID when a session is active.
     */
    private Integer loggedCustomerId;
    
    /**
     * The {@link Customer} object representing the currently logged-in customer.
     * Contains the customer's profile information including username, email,
     * hashed password, and associated bookings. This field is null when no customer is logged in.
     */
    private Customer loggedCustomer;
    
    /**
     * Collection of customer objects returned from search operations.
     * Used primarily for administrative functions and booking management operations
     * where multiple customers need to be displayed or processed.
     */
    private ArrayList<Customer> searchBookingResultCustomers;
    
    /**
     * Collection of database identifiers corresponding to customers in searchBookingResultCustomers.
     * Maintains synchronized indexing to enable efficient customer retrieval and selection
     * for administrative and booking management operations.
     */
    private ArrayList<Integer> searchBookingResultCustomersIds;

    /**
     * Creates and sets a customer session using individual profile components.
     * <p>
     * This method creates a new {@link Customer} object using the provided profile information
     * and establishes a customer session. It is typically used during the authentication
     * process when customer credentials have been verified against the database.
     * </p>
     *
     * @param username the customer's username for the session
     * @param mail the customer's email address for the session (can be null for customers)
     * @param hashedPassword the customer's hashed password for the session
     * @param id the unique database identifier for the customer
     */
    public void setLoggedCustomer(String username, String mail, String hashedPassword, Integer id) {
        loggedCustomer = new Customer(username, mail, hashedPassword);
        loggedCustomerId = id;
    }

    /**
     * Sets the customer session using an existing {@link Customer} object.
     * <p>
     * This method establishes a customer session using a pre-constructed and validated
     * {@link Customer} object and the corresponding database identifier. It is typically
     * used when a {@link Customer} object has already been created and needs to be
     * set as the current session.
     * </p>
     *
     * @param customer the {@link Customer} object to set as the current session
     * @param id the unique database identifier for the customer
     */
    public void setLoggedCustomer(Customer customer, Integer id) {
        loggedCustomer = customer;
        loggedCustomerId = id;
    }

    /**
     * Retrieves the currently logged-in customer object.
     *
     * @return the {@link Customer} object for the current session, or null if no customer is logged in
     */
    public Customer getLoggedCustomer() {
        return loggedCustomer;
    }

    /**
     * Retrieves the database identifier for the currently logged-in customer.
     *
     * @return the unique database identifier for the current customer session, or null if no customer is logged in
     */
    public Integer getLoggedCustomerId() {
        return loggedCustomerId;
    }

    /**
     * Retrieves the collection of customer objects from search results.
     *
     * @return list of {@link Customer} objects from search operations, or null if no results are available
     */
    public List<Customer> getSearchBookingResultCustomers() {
        return searchBookingResultCustomers;
    }

    /**
     * Sets the collection of customer objects from search operations.
     *
     * @param searchBookingResultCustomers list of {@link Customer} objects to set as search results
     */
    public void setSearchBookingResultCustomers(List<Customer> searchBookingResultCustomers) {
        this.searchBookingResultCustomers = (ArrayList<Customer>) searchBookingResultCustomers;
    }

    /**
     * Retrieves the collection of database IDs corresponding to customer search results.
     *
     * @return list of database identifiers corresponding to search result customers, or null if no results are available
     */
    public List<Integer> getSearchBookingResultCustomersIds() {
        return searchBookingResultCustomersIds;
    }

    /**
     * Sets the collection of database IDs corresponding to customer search results.
     *
     * @param searchBookingResultCustomersIds list of database identifiers to set for search results
     */
    public void setSearchBookingResultCustomersIds(List<Integer> searchBookingResultCustomersIds) {
        this.searchBookingResultCustomersIds = (ArrayList<Integer>) searchBookingResultCustomersIds;
    }

    /**
     * Retrieves a specific customer from search results by their database ID.

     *
     * @param id the database identifier of the customer to retrieve
     * @return the {@link Customer} object with the specified ID, or null if not found or if search results are empty
     */
    public Customer getSearchBookingResultCustomerById (Integer id){

        for(int i = 0; i < searchBookingResultCustomers.size(); i++){

            if(searchBookingResultCustomersIds.get(i).equals(id)){
                return searchBookingResultCustomers.get(i);
            }

        }
        return null;
    }
}