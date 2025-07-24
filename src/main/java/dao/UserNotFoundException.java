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
 * @see implementazioni_postgres_dao.CustomerDAOImpl
 * @see implementazioni_postgres_dao.AdminDAOImpl
 * @see UserAlreadyExistsException
 */
public class UserNotFoundException extends SQLException {
    
    /**
     * Constructs a new UserNotFoundException with the specified detail message.
     *
     * @param message the detail message explaining the specific user authentication or lookup failure
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}