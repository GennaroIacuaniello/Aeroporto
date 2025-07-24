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
 * This class serves as the central controller for user-related operations within the architecture of the airport
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
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see User
 * @see Admin
 * @see model.Customer
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
     * The actual type is either {@link Admin} or {@link model.Customer}.
     */
    private User loggedUser;

    /**
     * Retrieves the database identifier of the currently logged-in user.
     *
     * @return the database identifier for the current user, or null if no user is logged in
     */
    public Integer getLoggedUserId() {
        return loggedUserId;
    }

    /**
     * Retrieves the username of the currently logged-in user.
     *
     * @return the username of the currently logged-in user
     */
    public String getUsername() {
        return loggedUser.getUsername();
    }

    /**
     * Retrieves the email address of the currently logged-in user.
     *
     * @return the email address of the currently logged-in user
     */
    public String getMail(){ 
        return loggedUser.getEmail();
    }

    /**
     * Retrieves the hashed password of the currently logged-in user.
     *
     * @return the hashed password of the currently logged-in user
     */
    public String getHashedPassword(){ 
        return loggedUser.getPassword();
    }

    /**
     * Establishes a user session with the provided user object and database ID.
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