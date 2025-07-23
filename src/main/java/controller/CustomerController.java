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
 * <p>
 * The class follows a dual approach for data management:
 * </p>
 * <ul>
 *   <li><strong>Session Management:</strong> Maintains a single active customer session with database ID</li>
 *   <li><strong>Search Results:</strong> Manages collections of customer search results with corresponding IDs</li>
 * </ul>
 * <p>
 * Session management includes both the {@link Customer} object containing profile information
 * (username, email, hashed password, bookings) and the unique customer ID for database operations.
 * This dual approach ensures that both object-oriented operations and database queries can be
 * performed efficiently.
 * </p>
 * <p>
 * Search result management maintains synchronized collections of {@link Customer} objects and their
 * corresponding database IDs, enabling efficient retrieval and correlation of customer data for
 * administrative operations, booking management, and reporting purposes.
 * </p>
 * <p>
 * The controller integrates with the main {@link Controller} class and other system components
 * to provide customer-specific functionality such as booking management, profile updates,
 * authentication operations, and customer service support.
 * </p>
 * <p>
 * All customer data is maintained in memory during the session and is not persisted across
 * application restarts, requiring customers to re-authenticate when the application is restarted.
 * Search results are temporary and are cleared when new search operations are performed.
 * </p>
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
     * <p>
     * The method constructs the {@link Customer} object internally, ensuring that the
     * customer session is properly initialized with all required information.
     * Both the customer object and the database ID are stored for use throughout
     * the session.
     * </p>
     * <p>
     * This method is commonly used by authentication workflows where individual
     * profile components are retrieved from the database during login verification.
     * The customer is initialized with an empty bookings list, which can be populated
     * later through booking management operations.
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
     * <p>
     * This approach is useful for session management operations such as updating
     * customer information, transferring sessions between components, or restoring
     * session state from cached objects. The customer object may already contain
     * populated booking information from previous operations.
     * </p>
     * <p>
     * The method directly assigns the provided {@link Customer} object and ID,
     * making them immediately available for use throughout the application.
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
     * <p>
     * This method returns the {@link Customer} object representing the current
     * customer session. The returned object contains the customer's profile
     * information including username, email, hashed password, and associated
     * bookings if they have been loaded.
     * </p>
     * <p>
     * The method is used throughout the application to access customer information
     * for display purposes, booking operations, permission checking, and business
     * logic operations that require customer context.
     * </p>
     * <p>
     * If no customer is currently logged in, this method returns null. Calling
     * code should check for null values to handle cases where no customer
     * session is active.
     * </p>
     *
     * @return the {@link Customer} object for the current session, or null if no customer is logged in
     */
    public Customer getLoggedCustomer() {
        return loggedCustomer;
    }

    /**
     * Retrieves the database identifier for the currently logged-in customer.
     * <p>
     * This method returns the unique database identifier for the current customer
     * session. The ID is used for database operations that require customer
     * identification, such as booking operations, profile updates, customer
     * service operations, and administrative functions.
     * </p>
     * <p>
     * The database ID provides an efficient way to reference the customer in
     * database queries without needing to pass the entire {@link Customer} object
     * or perform username-based lookups. It is essential for maintaining data
     * relationships in the booking and reservation systems.
     * </p>
     * <p>
     * If no customer is currently logged in, this method returns null. Database
     * operations should check for null values and handle the case where no
     * customer session is active.
     * </p>
     *
     * @return the unique database identifier for the current customer session, or null if no customer is logged in
     */
    public Integer getLoggedCustomerId() {
        return loggedCustomerId;
    }

    /**
     * Retrieves the collection of customer objects from search results.
     * <p>
     * This method returns the list of {@link Customer} objects that were retrieved
     * from search operations, typically used for administrative functions, booking
     * management, or customer service operations where multiple customers need to
     * be displayed or processed.
     * </p>
     * <p>
     * The search results maintain synchronized indexing with the corresponding
     * database IDs, enabling efficient data correlation and selection operations.
     * These results are commonly used for displaying customer lists in administrative
     * interfaces or for processing multiple customers in batch operations.
     * </p>
     * <p>
     * The returned list may be null if no search operations have been performed
     * or if the results have been cleared. Calling code should handle null values
     * appropriately.
     * </p>
     *
     * @return list of {@link Customer} objects from search operations, or null if no results are available
     */
    public List<Customer> getSearchBookingResultCustomers() {
        return searchBookingResultCustomers;
    }

    /**
     * Sets the collection of customer objects from search operations.
     * <p>
     * This method establishes the list of {@link Customer} objects returned from
     * search operations, converting the input list to an ArrayList for internal
     * consistency and performance optimization. The search results are used for
     * administrative operations, customer management, and booking-related functions.
     * </p>
     * <p>
     * The method ensures that search results are properly stored and available
     * for subsequent retrieval and manipulation operations. The customer objects
     * should maintain correlation with their corresponding database IDs for
     * proper data integrity.
     * </p>
     * <p>
     * This method is typically called by search operations in the main controller
     * or by administrative functions that need to populate customer result sets
     * for display or processing purposes.
     * </p>
     *
     * @param searchBookingResultCustomers list of {@link Customer} objects to set as search results
     */
    public void setSearchBookingResultCustomers(List<Customer> searchBookingResultCustomers) {
        this.searchBookingResultCustomers = (ArrayList<Customer>) searchBookingResultCustomers;
    }

    /**
     * Retrieves the collection of database IDs corresponding to customer search results.
     * <p>
     * This method returns the list of database identifiers that correspond to the
     * customer objects in the search results. The IDs maintain synchronized indexing
     * with the customer objects, enabling efficient correlation between display
     * data and database operations.
     * </p>
     * <p>
     * The database IDs are essential for performing operations on selected customers,
     * such as administrative actions, booking modifications, or detailed information
     * retrieval. They provide the link between user interface selections and
     * database records.
     * </p>
     * <p>
     * The returned list may be null if no search operations have been performed
     * or if the results have been cleared. The list should maintain the same
     * size and order as the corresponding customer objects list.
     * </p>
     *
     * @return list of database identifiers corresponding to search result customers, or null if no results are available
     */
    public List<Integer> getSearchBookingResultCustomersIds() {
        return searchBookingResultCustomersIds;
    }

    /**
     * Sets the collection of database IDs corresponding to customer search results.
     * <p>
     * This method establishes the list of database identifiers that correspond to
     * customer search results, converting the input list to an ArrayList for internal
     * consistency. The IDs must maintain synchronized indexing with the customer
     * objects to ensure proper correlation between display data and database operations.
     * </p>
     * <p>
     * Proper synchronization between customer objects and their database IDs is
     * essential for maintaining data integrity and enabling correct customer
     * selection and operation targeting in administrative and booking management
     * functions.
     * </p>
     * <p>
     * This method is typically called in conjunction with setting the customer
     * search results to ensure that both collections are properly initialized
     * and correlated.
     * </p>
     *
     * @param searchBookingResultCustomersIds list of database identifiers to set for search results
     */
    public void setSearchBookingResultCustomersIds(List<Integer> searchBookingResultCustomersIds) {
        this.searchBookingResultCustomersIds = (ArrayList<Integer>) searchBookingResultCustomersIds;
    }

    /**
     * Retrieves a specific customer from search results by their database ID.
     * <p>
     * This method searches through the customer search results to find a customer
     * with the specified database ID, returning the corresponding {@link Customer}
     * object if found. This enables efficient customer retrieval based on database
     * identifiers without requiring index-based access.
     * </p>
     * <p>
     * The method performs a linear search through the search results, comparing
     * database IDs to find the matching customer. It maintains synchronization
     * between the customer objects and their database identifiers for accurate
     * retrieval operations.
     * </p>
     * <p>
     * This functionality is essential for administrative operations that need to
     * locate specific customers based on database references, such as processing
     * booking operations, updating customer information, or correlating customers
     * with related data records like bookings or transactions.
     * </p>
     * <p>
     * The method handles cases where the search results may be empty or null,
     * and where the specified ID may not exist in the current result set.
     * </p>
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