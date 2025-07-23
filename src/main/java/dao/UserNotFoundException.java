package dao;

import java.sql.SQLException;

/**
 * Exception thrown when attempting to authenticate or retrieve a user account that does not exist in the system.
 * <p>
 * This exception extends {@link SQLException} and is specifically designed to handle user authentication
 * failures and user lookup operations within the airport management system's user authentication and
 * authorization processes. It is thrown by DAO implementations when authentication credentials do not
 * match any existing user accounts or when user lookup operations fail to find the requested user.
 * </p>
 * <p>
 * The exception is used across both customer and administrator authentication operations to maintain
 * security and provide appropriate feedback when login attempts fail due to invalid credentials or
 * non-existent accounts. It ensures that authentication processes can distinguish between invalid
 * credentials and system errors.
 * </p>
 * <p>
 * Common scenarios that trigger this exception include:
 * </p>
 * <ul>
 *   <li>Attempting to authenticate with a username that does not exist in the system</li>
 *   <li>Attempting to authenticate with an email address that is not registered</li>
 *   <li>Providing correct username/email but incorrect password combinations</li>
 *   <li>Attempting to access deleted or deactivated user accounts</li>
 *   <li>User lookup operations for accounts that have been removed from the system</li>
 * </ul>
 * <p>
 * This exception is typically caught by service layer components, authentication controllers,
 * and user interface elements to provide appropriate error messages to users during login
 * processes. The exception helps maintain system security by preventing information disclosure
 * about which usernames or email addresses exist in the system while providing clear feedback
 * about authentication failures.
 * </p>
 * <p>
 * The exception is thrown by the following DAO operations:
 * </p>
 * <ul>
 *   <li>{@link CustomerDAO#searchUserByUsername(java.util.List, String, java.util.List, String)} - when customer authentication fails</li>
 *   <li>{@link CustomerDAO#searchUserByMail(java.util.List, java.util.List, String, String)} - when customer email-based authentication fails</li>
 *   <li>{@link AdminDAO#searchUserByUsername(java.util.List, String, java.util.List, String)} - when administrator authentication fails</li>
 *   <li>{@link AdminDAO#searchUserByMail(java.util.List, java.util.List, String, String)} - when administrator email-based authentication fails</li>
 * </ul>
 * <p>
 * The exception ensures that only active (non-deleted) user accounts are considered during
 * authentication processes, maintaining data integrity and security across the user management
 * system. It provides a consistent error handling mechanism for all user-related database
 * operations that involve user identification and authentication.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see SQLException
 * @see CustomerDAO
 * @see AdminDAO
 * @see CustomerDAOImpl
 * @see AdminDAOImpl
 * @see UserAlreadyExistsException
 */
public class UserNotFoundException extends SQLException {
    
    /**
     * Constructs a new UserNotFoundException with the specified detail message.
     * <p>
     * This constructor creates an exception instance with a descriptive error message
     * that explains the specific nature of the user authentication or lookup failure.
     * The message typically indicates that the requested user could not be found in
     * the system and provides context for error handling and user feedback.
     * </p>
     * <p>
     * The message parameter should clearly describe the authentication failure scenario
     * to help with debugging and provide meaningful feedback to end users. Common message
     * formats include generic authentication failure messages that do not reveal specific
     * information about which credentials were invalid, maintaining system security.
     * </p>
     * <p>
     * For security purposes, the message should not reveal whether a username or email
     * address exists in the system, instead providing generic authentication failure
     * messages that protect user privacy and system information.
     * </p>
     *
     * @param message the detail message explaining the specific user authentication or lookup failure
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}