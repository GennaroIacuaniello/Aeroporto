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
 * Common scenarios that trigger this exception include:
 * </p>
 * <ul>
 *   <li>Attempting to register a new customer or administrator with an existing username</li>
 *   <li>Attempting to register a new account with an email address already in use</li>
 *   <li>Updating an existing user's credentials to match another user's credentials</li>
 *   <li>Cross-table validation failures where customer usernames conflict with admin usernames</li>
 * </ul>
 * <p>
 * This exception is typically caught by service layer components and user interface controllers
 * to provide appropriate error messages to users during registration and account modification processes.
 * The exception helps maintain the system's referential integrity by preventing duplicate user accounts
 * that could lead to authentication ambiguity or security issues.
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
     * <p>
     * This constructor creates an exception instance with a descriptive error message
     * that explains the specific nature of the user account conflict. The message
     * typically indicates which credential (username or email) is already in use
     * and provides context for error handling and user feedback.
     * </p>
     * <p>
     * The message parameter should clearly describe the conflict scenario to help
     * with debugging and provide meaningful feedback to end users. Common message
     * formats include descriptions of which specific credential is duplicated
     * and suggestions for resolution.
     * </p>
     *
     * @param message the detail message explaining the specific user account conflict
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}