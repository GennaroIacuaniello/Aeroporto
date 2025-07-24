package dao;

import java.sql.SQLException;

/**
 * Exception thrown when attempting to create or update a user account with credentials that already exist in the system.
 * <p>
 * This exception extends {@link SQLException} and is specifically designed to handle duplicate user account
 * scenarios within the airport management system's user authentication and registration processes.
 * It is thrown by DAO implementations when username or email address uniqueness constraints are violated
 * during user account operations.
 * </p>
 * <p>
 * The exception is used across both customer and administrator account management operations to maintain
 * data integrity and prevent duplicate accounts. It ensures that usernames and email addresses remain
 * unique across the entire user base, including both {@code Customer} and {@code Admin} account types.
 * </p>
 * <p>
 * The exception is thrown by the following DAO operations:
 * </p>
 * <ul>
 *   <li>{@link CustomerDAO#insertNewCustomer(String, String, String)} - when creating duplicate customer accounts</li>
 *   <li>{@link CustomerDAO#updateCustomer(Integer, String, String, String)} - when updating to existing credentials</li>
 *   <li>{@link AdminDAO#insertNewAdmin(String, String, String)} - when creating duplicate administrator accounts</li>
 *   <li>{@link AdminDAO#updateAdmin(Integer, String, String)} - when updating to existing credentials</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see SQLException
 * @see CustomerDAO
 * @see AdminDAO
 * @see implementazioni_postgres_dao.CustomerDAOImpl
 * @see implementazioni_postgres_dao.AdminDAOImpl
 */
public class UserAlreadyExistsException extends SQLException {
    
    /**
     * Constructs a new UserAlreadyExistsException with the specified detail message.
     *
     * @param message the detail message explaining the specific user account conflict
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}