package dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Data Access Object interface for managing administrator user operations in the airport management system.
 * <p>
 * This interface defines the contract for all administrator-specific database operations, providing
 * methods for authentication, user management, and administrative account lifecycle management.
 * It extends the standard user management functionality with administrator-specific requirements
 * and permissions within the airport management system.
 * </p>
 * <p>
 * The AdminDAO interface provides comprehensive administrator management capabilities including:
 * </p>
 * <ul>
 *   <li>Administrator authentication and login verification</li>
 *   <li>Administrator account creation and registration</li>
 *   <li>Administrator profile updates and modifications</li>
 *   <li>Administrator account deactivation and deletion</li>
 *   <li>Username and email uniqueness validation</li>
 *   <li>Password management and security enforcement</li>
 * </ul>
 * <p>
 * All administrator operations are subject to enhanced security requirements compared to regular
 * customer operations. Administrators must provide valid email addresses and are subject to
 * stricter validation rules to ensure system security and proper administrative oversight.
 * </p>
 * <p>
 * The interface follows the DAO pattern to provide a clean separation between business logic
 * and data persistence layer, enabling different implementations for various database systems
 * while maintaining consistent functionality across the application.
 * </p>
 * <p>
 * Implementation classes should handle all database-specific operations, connection management,
 * error handling, and ensure proper transaction handling for data consistency and integrity.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see model.Admin
 * @see implementazioni_postgres_dao.AdminDAOImpl
 * @see UserAlreadyExistsException
 * @see UserNotFoundException
 * @see SQLException
 */
public interface AdminDAO {

    /**
     * Searches for an administrator by username and validates the provided password.
     * <p>
     * This method performs administrator authentication by searching for a user with the
     * specified username and verifying the provided password hash. Upon successful
     * authentication, it retrieves the administrator's ID and email address for
     * session management and user identification purposes.
     * </p>
     * <p>
     * The method populates the provided lists with matching administrator data:
     * </p>
     * <ul>
     *   <li>userID list receives the administrator's unique identifier</li>
     *   <li>mail list receives the administrator's email address</li>
     * </ul>
     * <p>
     * Only active (non-deleted) administrator accounts are considered during the search.
     * The password parameter should be provided in hashed format for security purposes.
     * </p>
     *
     * @param userID list to be populated with the administrator's unique identifier
     * @param username the administrator's username to search for, must not be null
     * @param mail list to be populated with the administrator's email address
     * @param password the hashed password to verify against the stored password
     * @throws SQLException if a database access error occurs during the operation
     * @throws UserNotFoundException if no administrator is found with the specified username and password combination
     */
    void searchUserByUsername(List<Integer> userID, String username, List<String> mail, String password) throws SQLException;

    /**
     * Searches for an administrator by email address and validates the provided password.
     * <p>
     * This method performs administrator authentication by searching for a user with the
     * specified email address and verifying the provided password hash. Upon successful
     * authentication, it retrieves the administrator's ID and username for session
     * management and user identification purposes.
     * </p>
     * <p>
     * The method populates the provided lists with matching administrator data:
     * </p>
     * <ul>
     *   <li>userID list receives the administrator's unique identifier</li>
     *   <li>username list receives the administrator's username</li>
     * </ul>
     * <p>
     * Only active (non-deleted) administrator accounts are considered during the search.
     * The password parameter should be provided in hashed format for security purposes.
     * </p>
     *
     * @param userID list to be populated with the administrator's unique identifier
     * @param username list to be populated with the administrator's username
     * @param mail the administrator's email address to search for, must not be null
     * @param password the hashed password to verify against the stored password
     * @throws SQLException if a database access error occurs during the operation
     * @throws UserNotFoundException if no administrator is found with the specified email and password combination
     */
    void searchUserByMail(List<Integer> userID, List<String> username, String mail, String password) throws SQLException;

    /**
     * Creates a new administrator account in the system.
     * <p>
     * This method creates a new administrator user with the specified credentials.
     * Before creating the account, it performs validation to ensure that the
     * username and email address are unique across both administrator and customer
     * accounts to prevent conflicts and maintain data integrity.
     * </p>
     * <p>
     * The method enforces the following business rules:
     * </p>
     * <ul>
     *   <li>Username must be unique across all user types (Admin and Customer)</li>
     *   <li>Email address must be unique across all user types</li>
     *   <li>Email address is mandatory for administrator accounts</li>
     *   <li>Password must be provided in hashed format</li>
     * </ul>
     * <p>
     * The newly created administrator account will be active by default and can
     * immediately be used for authentication and system access.
     * </p>
     *
     * @param mail the email address for the new administrator account, must not be null and must be unique
     * @param username the username for the new administrator account, must not be null and must be unique
     * @param password the hashed password for the new administrator account, must not be null
     * @throws SQLException if a database access error occurs during the operation
     * @throws UserAlreadyExistsException if the username or email address is already in use by another user
     */
    void insertNewAdmin(String mail, String username, String password) throws SQLException;

    /**
     * Updates an existing administrator's account information.
     * <p>
     * This method modifies the username and password for an existing administrator
     * account identified by the provided user ID. Before performing the update,
     * it validates that the new username is unique across all user accounts to
     * prevent conflicts.
     * </p>
     * <p>
     * The method enforces the following validation rules:
     * </p>
     * <ul>
     *   <li>New username must be unique across all user types (Admin and Customer)</li>
     *   <li>Username cannot be the same as any existing user except the current user</li>
     *   <li>Only active (non-deleted) accounts are considered during uniqueness validation</li>
     *   <li>Password must be provided in hashed format</li>
     * </ul>
     * <p>
     * Note that email addresses cannot be updated through this method, as they are
     * considered immutable for administrator accounts once created.
     * </p>
     *
     * @param userID the unique identifier of the administrator account to update
     * @param username the new username for the administrator account, must be unique
     * @param password the new hashed password for the administrator account
     * @throws SQLException if a database access error occurs during the operation
     * @throws UserAlreadyExistsException if the new username is already in use by another user
     */
    void updateAdmin(Integer userID, String username, String password) throws SQLException;

    /**
     * Marks an administrator account as deleted in the system.
     * <p>
     * This method performs a soft delete operation on the specified administrator
     * account by marking it as deleted rather than physically removing the record
     * from the database. This approach preserves data integrity and audit trails
     * while preventing the account from being used for authentication or other
     * operations.
     * </p>
     * <p>
     * After deletion, the administrator account:
     * </p>
     * <ul>
     *   <li>Cannot be used for authentication or login</li>
     *   <li>Will not appear in active user searches</li>
     *   <li>Username and email become available for reuse</li>
     *   <li>Historical data and associations are preserved</li>
     * </ul>
     * <p>
     * The deletion operation is irreversible through normal application operations
     * and would require direct database access to restore the account.
     * </p>
     *
     * @param userID the unique identifier of the administrator account to delete
     * @throws SQLException if a database access error occurs during the operation
     */
    void deleteAdmin(Integer userID) throws SQLException;
}