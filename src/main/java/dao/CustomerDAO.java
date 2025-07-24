package dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Data Access Object interface for managing customer user operations in the airport management system.
 * <p>
 * This interface defines the contract for all customer-specific database operations, providing
 * methods for authentication, user management, and customer account lifecycle management.
 * It serves as the primary interface for customer data persistence and retrieval operations,
 * supporting both self-service customer operations and administrative customer management functions.
 * </p>
 * <p>
 * The CustomerDAO interface provides comprehensive customer management capabilities including:
 * </p>
 * <ul>
 *   <li>Customer authentication and login verification</li>
 *   <li>Customer account creation and registration</li>
 *   <li>Customer profile updates and modifications</li>
 *   <li>Customer account deactivation and deletion</li>
 *   <li>Username and email uniqueness validation across user types</li>
 *   <li>Password management and security enforcement</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see model.Customer
 * @see implementazioni_postgres_dao.CustomerDAOImpl
 * @see UserAlreadyExistsException
 * @see UserNotFoundException
 * @see SQLException
 */
public interface CustomerDAO {

    /**
     * Searches for a customer by username and validates the provided password.
     * <p>
     * This method performs customer authentication by searching for a user with the
     * specified username and verifying the provided password hash. Upon successful
     * authentication, it retrieves the customer's ID and email address for
     * session management and user identification purposes.
     * </p>
     * <p>
     * The method populates the provided lists with matching customer data:
     * </p>
     * <ul>
     *   <li>userID list receives the customer's unique identifier</li>
     *   <li>mail list receives the customer's email address (can be null)</li>
     * </ul>
     * <p>
     * Only active (non-deleted) customer accounts are considered during the search.
     * The password parameter should be provided in hashed format for security purposes.
     * </p>
     *
     * @param userID list to be populated with the customer's unique identifier
     * @param username the customer's username to search for, must not be null
     * @param mail list to be populated with the customer's email address
     * @param password the hashed password to verify against the stored password
     * @throws SQLException if a database access error occurs during the operation
     * @throws UserNotFoundException if no customer is found with the specified username and password combination
     */
    void searchUserByUsername(List<Integer> userID, String username, List<String> mail, String password) throws SQLException;

    /**
     * Searches for a customer by email address and validates the provided password.
     * <p>
     * This method performs customer authentication by searching for a user with the
     * specified email address and verifying the provided password hash. Upon successful
     * authentication, it retrieves the customer's ID and username for session
     * management and user identification purposes.
     * </p>
     * <p>
     * The method populates the provided lists with matching customer data:
     * </p>
     * <ul>
     *   <li>userID list receives the customer's unique identifier</li>
     *   <li>username list receives the customer's username</li>
     * </ul>
     * <p>
     * Only active (non-deleted) customer accounts are considered during the search.
     * The password parameter should be provided in hashed format for security purposes.
     * </p>
     *
     * @param userID list to be populated with the customer's unique identifier
     * @param username list to be populated with the customer's username
     * @param mail the customer's email address to search for, must not be null
     * @param password the hashed password to verify against the stored password
     * @throws SQLException if a database access error occurs during the operation
     * @throws UserNotFoundException if no customer is found with the specified email and password combination
     */
    void searchUserByMail(List<Integer> userID, List<String> username, String mail, String password) throws SQLException;

    /**
     * Creates a new customer account in the system.
     * <p>
     * This method creates a new customer user with the specified credentials.
     * Before creating the account, it performs comprehensive validation to ensure
     * that the username and email address (if provided) are unique across both
     * customer and administrator accounts to prevent conflicts and maintain data integrity.
     * </p>
     * <p>
     * The method enforces the following business rules:
     * </p>
     * <ul>
     *   <li>Username must be unique across all user types (Customer and Admin)</li>
     *   <li>Email address must be unique across all user types if provided</li>
     *   <li>Email address is optional for customer accounts (can be null)</li>
     *   <li>Password must be provided in hashed format</li>
     * </ul>
     * <p>
     * The newly created customer account will be active by default and can
     * immediately be used for authentication, flight booking, and other customer services.
     * </p>
     *
     * @param mail the email address for the new customer account, can be null for customers
     * @param username the username for the new customer account, must not be null and must be unique
     * @param password the hashed password for the new customer account, must not be null
     * @throws SQLException if a database access error occurs during the operation
     * @throws UserAlreadyExistsException if the username or email address is already in use by another user
     */
    void insertNewCustomer(String mail, String username, String password) throws SQLException;

    /**
     * Updates an existing customer's account information.
     * <p>
     * This method modifies the email, username, and password for an existing customer
     * account identified by the provided user ID. Before performing the update,
     * it validates that the new username and email address are unique across all
     * user accounts to prevent conflicts.
     * </p>
     * <p>
     * The method enforces the following validation rules:
     * </p>
     * <ul>
     *   <li>The new username must be unique across all user types (Customer and Admin)</li>
     *   <li>A new email address must be unique across all user types if provided</li>
     *   <li>Username and email cannot be the same as any existing user except the current user</li>
     *   <li>Only active (non-deleted) accounts are considered during uniqueness validation</li>
     *   <li>Password must be provided in hashed format</li>
     * </ul>
     *
     * @param userID the unique identifier of the customer account to update
     * @param mail the new email address for the customer account, can be null
     * @param username the new username for the customer account, must be unique
     * @param password the new hashed password for the customer account
     * @throws SQLException if a database access error occurs during the operation
     * @throws UserAlreadyExistsException if the new username or email address is already in use by another user
     */
    void updateCustomer(Integer userID, String mail, String username, String password) throws SQLException;

    /**
     * Marks a customer account as deleted in the system.
     * <p>
     * This method performs a soft delete operation on the specified customer
     * account by marking it as deleted rather than physically removing the record
     * from the database. This approach preserves data integrity, audit trails,
     * and historical booking information while preventing the account from being
     * used for authentication or new operations.
     * </p>
     * <p>
     * After deletion, the customer account:
     * </p>
     * <ul>
     *   <li>Cannot be used for authentication or login</li>
     *   <li>Will not appear in active user searches</li>
     *   <li>Username and email become available for reuse</li>
     *   <li>Historical bookings and travel data are preserved</li>
     *   <li>Associated booking records remain intact for reporting</li>
     * </ul>
     * <p>
     * The deletion operation is irreversible through normal application operations
     * and would require direct database access to restore the account. This ensures
     * data consistency while maintaining historical travel records for business
     * intelligence and customer service purposes.
     * </p>
     *
     * @param userID the unique identifier of the customer account to delete
     * @throws SQLException if a database access error occurs during the operation
     */
    void deleteCustomer(Integer userID) throws SQLException;

}