package controller;

import dao.UserAlreadyExistsException;
import gui.FloatingMessage;
import implementazioni_postgres_dao.AdminDAOImpl;
import implementazioni_postgres_dao.CustomerDAOImpl;
import model.Admin;
import model.User;

import javax.swing.*;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller class for managing general user operations, authentication state, and input validation in the airport management system.
 * <p>
 * This class serves as the central controller for user-related operations within the MVC architecture of the airport
 * management system. It handles user session management, input validation, account registration, and account deletion
 * operations for both customers and administrators through a unified interface.
 * </p>
 * <p>
 * The UserController is responsible for:
 * </p>
 * <ul>
 *   <li>Managing the currently logged-in user's session information and database ID</li>
 *   <li>Providing unified access to user profile data regardless of user type (Admin/Customer)</li>
 *   <li>Performing comprehensive input validation for email addresses and usernames</li>
 *   <li>Handling user registration processes with appropriate type determination</li>
 *   <li>Managing account deletion operations for both user types</li>
 *   <li>Supporting authentication workflows and session management</li>
 * </ul>
 * <p>
 * The class follows a polymorphic approach using the {@link User} base class to handle both
 * {@link Admin} and {@link Customer} user types uniformly. This design enables consistent user
 * management operations while maintaining type-specific functionality through proper inheritance.
 * </p>
 * <p>
 * Session management includes both the {@link User} object containing profile information
 * (username, email, hashed password) and the unique user ID for database operations. This dual
 * approach ensures that both object-oriented operations and database queries can be performed
 * efficiently across different user types.
 * </p>
 * <p>
 * Input validation is performed using regular expressions that enforce business rules for:
 * </p>
 * <ul>
 *   <li><strong>Email validation:</strong> Standard email format with domain requirements</li>
 *   <li><strong>Username validation:</strong> Alphanumeric with limited special characters, length constraints</li>
 *   <li><strong>Format enforcement:</strong> Usernames must start with letters and end with letters or numbers</li>
 * </ul>
 * <p>
 * User registration automatically determines user type based on email domain:
 * </p>
 * <ul>
 *   <li><strong>Administrator accounts:</strong> Created for @aeroportodinapoli.it and @adn.it domains</li>
 *   <li><strong>Customer accounts:</strong> Created for all other valid email domains</li>
 * </ul>
 * <p>
 * The controller integrates with the database layer through appropriate DAO implementations
 * ({@link AdminDAOImpl} and {@link CustomerDAOImpl}) and provides user feedback through
 * {@link FloatingMessage} components for all operations including validation failures,
 * registration success/failure, and account deletion results.
 * </p>
 * <p>
 * All user operations maintain data integrity and provide comprehensive error handling with
 * appropriate user feedback. Session state is maintained in memory and is not persisted across
 * application restarts, requiring users to re-authenticate when the application is restarted.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see User
 * @see Admin
 * @see Customer
 * @see AdminDAOImpl
 * @see CustomerDAOImpl
 * @see FloatingMessage
 * @see Controller
 */
public class UserController {
    
    /**
     * The unique database identifier for the currently logged-in user.
     * This field is null when no user is logged in and contains the
     * user's database ID when a session is active.
     */
    private Integer loggedUserId;
    
    /**
     * The {@link User} object representing the currently logged-in user.
     * Contains the user's profile information including username, email,
     * and hashed password. This field is null when no user is logged in.
     * The actual type is either {@link Admin} or {@link Customer}.
     */
    private User loggedUser;

    /**
     * Retrieves the database identifier of the currently logged-in user.
     * <p>
     * This method returns the unique database ID for the current user session,
     * which is used for database operations, user identification, and correlation
     * with other data records throughout the system.
     * </p>
     * <p>
     * The ID is essential for performing user-specific database operations
     * such as booking management, profile updates, and account maintenance
     * across both administrator and customer user types.
     * </p>
     *
     * @return the database identifier for the current user, or null if no user is logged in
     */
    public Integer getLoggedUserId() {
        return loggedUserId;
    }

    /**
     * Sets the database identifier for the currently logged-in user.
     * <p>
     * This method updates the user ID for the current session, typically
     * used during authentication processes when establishing user sessions
     * or when user information needs to be updated during the session.
     * </p>
     * <p>
     * The ID must correspond to a valid user record in the database and
     * should be synchronized with the user object information to maintain
     * data consistency throughout the session.
     * </p>
     *
     * @param id the database identifier to set for the current user session
     */
    public void setLoggedUserId(Integer id) {
        this.loggedUserId  = id;
    }

    /**
     * Retrieves the username of the currently logged-in user.
     * <p>
     * This convenience method provides direct access to the username of the
     * current user without requiring explicit navigation through the user object.
     * The username is used for display purposes, user identification, and
     * various user interface operations.
     * </p>
     * <p>
     * The username is guaranteed to follow the system's validation rules
     * including format constraints and length requirements as enforced
     * during the registration and authentication processes.
     * </p>
     *
     * @return the username of the currently logged-in user
     */
    public String getUsername() {
        return loggedUser.getUsername();
    }

    /**
     * Retrieves the email address of the currently logged-in user.
     * <p>
     * This convenience method provides direct access to the email address
     * of the current user without requiring explicit navigation through the
     * user object. The email is used for communication, user identification,
     * and determining user type (Admin vs Customer).
     * </p>
     * <p>
     * The email address is guaranteed to follow valid email format patterns
     * as enforced during the registration and validation processes.
     * </p>
     *
     * @return the email address of the currently logged-in user
     */
    public String getMail(){ 
        return loggedUser.getEmail();
    }

    /**
     * Retrieves the hashed password of the currently logged-in user.
     * <p>
     * This method provides access to the user's hashed password for authentication
     * and security operations. The password is stored in hashed format for security
     * purposes and should never be used to display or transmit plaintext passwords.
     * </p>
     * <p>
     * The hashed password is used for authentication verification, password
     * update operations, and maintaining session security throughout the
     * user's interaction with the system.
     * </p>
     *
     * @return the hashed password of the currently logged-in user
     */
    public String getHashedPassword(){ 
        return loggedUser.getPassword();
    }

    /**
     * Establishes a user session with the provided user object and database ID.
     * <p>
     * This method sets both the user object and database ID simultaneously,
     * ensuring that user session information is properly synchronized and
     * available for all user-related operations throughout the application.
     * </p>
     * <p>
     * The method is typically used during authentication processes when user
     * credentials have been verified and a session needs to be established
     * with complete user information and database correlation.
     * </p>
     * <p>
     * Both the user object and ID must be valid and properly correlated to
     * ensure session consistency and enable correct user-specific operations
     * throughout the system.
     * </p>
     *
     * @param loggedUser the {@link User} object (Admin or Customer) to set as the current session
     * @param id the database identifier corresponding to the user
     */
    public void setLoggedUser(User loggedUser, Integer id) {
        this.loggedUser = loggedUser;
        this.loggedUserId = id;
    }

    /**
     * Retrieves the currently logged-in user object.
     * <p>
     * This method returns the {@link User} object representing the current user
     * session. The actual object type will be either {@link Admin} or {@link Customer}
     * depending on the user type, enabling polymorphic operations while maintaining
     * type-specific functionality.
     * </p>
     * <p>
     * The user object contains complete profile information including username,
     * email, and hashed password, providing access to all user-related data
     * needed for system operations and user interface display.
     * </p>
     *
     * @return the current {@link User} object, or null if no user is logged in
     */
    public User getLoggedUser() {
        return this.loggedUser;
    }

    /**
     * Validates email address format according to system requirements.
     * <p>
     * This method performs comprehensive email validation using regular expressions
     * to ensure that email addresses meet the system's format requirements and
     * length constraints. The validation is designed to be practical while
     * maintaining reasonable security standards.
     * </p>
     * <p>
     * Email validation rules include:
     * </p>
     * <ul>
     *   <li>Maximum length of 50 characters to prevent database overflow</li>
     *   <li>Must contain alphanumeric characters before and after the @ symbol</li>
     *   <li>Supports dots and underscores within the local and domain parts</li>
     *   <li>Requires proper domain structure with at least one dot followed by letters</li>
     *   <li>Prevents consecutive special characters and ensures proper placement</li>
     * </ul>
     * <p>
     * The validation pattern: {@code [a-zA-Z0-9]+([.|_][a-zA-Z0-9]+)*@[a-zA-Z0-9]+([.|_][a-zA-Z0-9]+)*[.][a-zA-Z]+}
     * ensures that emails follow a practical format while preventing common malformed
     * email patterns that could cause system issues.
     * </p>
     * <p>
     * Note: In a production environment, this validation would typically be enhanced
     * with additional verification mechanisms such as email confirmation workflows
     * to ensure email deliverability and user identity verification.
     * </p>
     *
     * @param mail the email address string to validate
     * @return true if the email format is invalid or exceeds length limits, false if valid
     */
    public boolean isInvalidMail(String mail){
        if(mail.length() <= 50){
            Pattern pattern = Pattern.compile("[a-zA-Z0-9]+([.|_][a-zA-Z0-9]+)*@[a-zA-Z0-9]+([.|_][a-zA-Z0-9]+)*[.][a-zA-Z]+");
            Matcher matcher = pattern.matcher(mail);
            return !matcher.matches();

        }
        return true;
    }

    /**
     * Validates username format according to system requirements and constraints.
     * <p>
     * This method performs comprehensive username validation using regular expressions
     * to ensure that usernames meet the system's format requirements, length constraints,
     * and character restrictions. The validation enforces business rules for username
     * creation and modification.
     * </p>
     * <p>
     * Username validation rules include:
     * </p>
     * <ul>
     *   <li>Length must be between 4 and 20 characters inclusive</li>
     *   <li>Must start with a letter (a-z, A-Z)</li>
     *   <li>Must end with a letter or number (a-z, A-Z, 0-9)</li>
     *   <li>Middle characters can include letters, numbers, dots, hyphens, and underscores</li>
     *   <li>Cannot consist entirely of special characters</li>
     * </ul>
     * <p>
     * The validation pattern: {@code [a-zA-Z]+(\\w|\\.|-)*[a-zA-Z0-9]} ensures that
     * usernames are readable, professional, and compatible with system requirements
     * while preventing potentially problematic character combinations.
     * </p>
     * <p>
     * This validation helps maintain consistent username standards across the system
     * and prevents issues with database storage, user interface display, and system
     * integration that could arise from improperly formatted usernames.
     * </p>
     *
     * @param username the username string to validate
     * @return true if the username format is invalid or doesn't meet length requirements, false if valid
     */
    public boolean isInvalidUsername(String username) {
        if(username.length() >= 4 && username.length() <= 20){
            Pattern pattern = Pattern.compile("[a-zA-Z]+(\\w|\\.|-)*[a-zA-Z0-9]");
            Matcher matcher = pattern.matcher(username);
            return !matcher.matches();
        }
        return true;
    }

    /**
     * Registers a new user account with automatic type determination based on email domain.
     * <p>
     * This method handles the complete user registration process including input validation,
     * user type determination, database persistence, and user feedback. It automatically
     * determines whether to create an administrator or customer account based on the
     * provided email domain.
     * </p>
     * <p>
     * The registration process includes:
     * </p>
     * <ul>
     *   <li>Email format validation using {@link #isInvalidMail(String)}</li>
     *   <li>Username format validation using {@link #isInvalidUsername(String)}</li>
     *   <li>User type determination based on email domain patterns</li>
     *   <li>Database insertion through appropriate DAO implementations</li>
     *   <li>Comprehensive error handling and user feedback</li>
     * </ul>
     * <p>
     * User type determination logic:
     * </p>
     * <ul>
     *   <li><strong>Administrator accounts:</strong> Created for emails containing "@aeroportodinapoli.it" or "@adn.it"</li>
     *   <li><strong>Customer accounts:</strong> Created for all other valid email domains</li>
     * </ul>
     * <p>
     * The method provides comprehensive user feedback through {@link FloatingMessage} components:
     * </p>
     * <ul>
     *   <li><strong>Warning messages:</strong> For validation failures and existing user conflicts</li>
     *   <li><strong>Success messages:</strong> For successful registration with login instructions</li>
     *   <li><strong>Error messages:</strong> For database connectivity or system issues</li>
     * </ul>
     * <p>
     * Error handling includes specific exception types to provide appropriate user feedback:
     * </p>
     * <ul>
     *   <li>{@link UserAlreadyExistsException}: When username or email already exists in the system</li>
     *   <li>{@link SQLException}: For database connectivity or constraint violation issues</li>
     * </ul>
     *
     * @param mail the email address for the new user account
     * @param username the desired username for the new account
     * @param hashedPassword the pre-hashed password for the account
     * @param button the UI button reference for displaying feedback messages
     * @return true if registration was successful, false if validation failed or errors occurred
     */
    public boolean registerUser(String mail, String username, String hashedPassword, JButton button) {
        if (isInvalidMail(mail)) {
            new FloatingMessage("<html>Mail non valida</html>", button, FloatingMessage.WARNING_MESSAGE);
            return false;
        }
        if (isInvalidUsername(username)) {
            new FloatingMessage("<html>Username non valido.<br>Il nome utente deve iniziare con una lettera, " +
                    "finire con una lettera o un numero e può contenere solo lettere, numeri, trattini (-), underscore(_) e punti(.)</html>",
                    button, FloatingMessage.WARNING_MESSAGE);
            return false;
        }

        try {
            if (mail.contains("@aeroportodinapoli.it") || mail.contains("@adn.it")) {
                AdminDAOImpl adminDAO = new AdminDAOImpl();
                adminDAO.insertNewAdmin(mail, username, hashedPassword);
            } else {
                CustomerDAOImpl customerDAO = new CustomerDAOImpl();
                customerDAO.insertNewCustomer(mail, username, hashedPassword);
            }

            new FloatingMessage("<html>Sei stato registrato con successo!<br>Procedi a fare il login</html>", button, FloatingMessage.SUCCESS_MESSAGE);
            return true;
        } catch (UserAlreadyExistsException e) {
            new FloatingMessage("<html>" + e.getMessage() + "</html>", button, FloatingMessage.WARNING_MESSAGE);
        } catch (SQLException e) {
            new FloatingMessage("<html>Errore nel collegamento al DB(Customer) o DB(admin)<br>" + e.getMessage() + "</html>", button, FloatingMessage.ERROR_MESSAGE);
        }

        return false;
    }

    /**
     * Deletes the currently logged-in user's account from the system.
     * <p>
     * This method handles account deletion for both administrator and customer users
     * by determining the user type and calling the appropriate DAO method for account
     * removal. The deletion is permanent and removes all user data from the system.
     * </p>
     * <p>
     * The deletion process includes:
     * </p>
     * <ul>
     *   <li>User type determination using instanceof checking</li>
     *   <li>Appropriate DAO method selection based on user type</li>
     *   <li>Database deletion operation with the current user's ID</li>
     *   <li>Error handling and user feedback for operation results</li>
     * </ul>
     * <p>
     * Account deletion is handled through type-specific DAO implementations:
     * </p>
     * <ul>
     *   <li><strong>Administrator accounts:</strong> Deleted through {@link AdminDAOImpl#deleteAdmin(Integer)}</li>
     *   <li><strong>Customer accounts:</strong> Deleted through {@link CustomerDAOImpl#deleteCustomer(Integer)}</li>
     * </ul>
     * <p>
     * The method provides error feedback through {@link FloatingMessage} components
     * when database operations fail, ensuring that users are informed of any issues
     * that prevent successful account deletion.
     * </p>
     * <p>
     * Note: This operation is irreversible and will permanently remove all user data
     * including bookings, preferences, and historical information. In a production
     * environment, this might be enhanced with confirmation dialogs and data archiving.
     * </p>
     *
     * @param button the UI button reference for displaying error messages if deletion fails
     * @return true if the account was successfully deleted, false if database errors occurred
     */
    public boolean deleteAccount(JButton button){
        try{
            if(loggedUser instanceof Admin){
                AdminDAOImpl adminDAO = new AdminDAOImpl();
                adminDAO.deleteAdmin(loggedUserId);
            }
            else{
                CustomerDAOImpl customerDAO = new CustomerDAOImpl();
                customerDAO.deleteCustomer(loggedUserId);
            }
        } catch (SQLException e){
            new FloatingMessage("<html>Problemi nell'accesso al DB(Admin) o DB(Customer)", button, FloatingMessage.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}