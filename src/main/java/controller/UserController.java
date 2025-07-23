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
 * The UserController class manages user authentication, registration, and account management.
 * <p>
 * This controller handles operations common to all user types (both Admin and Customer),
 * including login session management, user validation, registration, and account deletion.
 * It maintains information about the currently logged-in user and provides methods to
 * access and modify user data securely.
 * </p>
 * <p>
 * This class serves as a base controller for user operations, working in conjunction with
 * more specific controllers like {@link AdminController} and {@link CustomerController}
 * to provide comprehensive user management functionality.
 * </p>
 * 
 * @author Your Name
 * @version 1.0
 * @since 1.0
 */
public class UserController {
    
    /**
     * The unique identifier of the currently logged-in user.
     * <p>
     * This ID corresponds to the user's record in the database and is used
     * for database operations related to the user. It is set during login
     * and cleared during logout operations.
     * </p>
     */
    private Integer loggedUserId;
    
    /**
     * The User object representing the currently logged-in user.
     * <p>
     * This object contains the user's information such as username, email,
     * and hashed password. It may be either an Admin or a Customer instance,
     * depending on the type of user that is currently logged in.
     * </p>
     */
    private User loggedUser;
    
    /**
     * Regular expression pattern for email validation.
     * This pattern ensures that email addresses follow the standard format
     * with proper domain structure and character restrictions.
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
        "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );
    
    /**
     * Regular expression pattern for username validation.
     * This pattern ensures usernames meet security requirements:
     * - Start and end with alphanumeric characters
     * - Can contain letters, numbers, hyphens, underscores, and dots
     * - Must be between 3 and 30 characters long
     */
    private static final Pattern USERNAME_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9]([a-zA-Z0-9._-]*[a-zA-Z0-9])?$"
    );

    /**
     * Sets the currently logged-in user with the provided User object and ID.
     * <p>
     * This method stores a reference to the provided User object along with the user's ID.
     * Used during authentication when a User object already exists and needs to be set
     * as the current logged-in user. The User object can be either an Admin or Customer instance.
     * </p>
     *
     * @param user the User object representing the logged-in user - must not be null
     * @param id   the user's unique identifier in the database - must not be null
     * 
     * @throws IllegalArgumentException if user or id is null
     */
    public void setLoggedUser(User user, Integer id) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        
        this.loggedUser = user;
        this.loggedUserId = id;
    }
    
    /**
     * Sets the logged user ID.
     * <p>
     * This method sets only the user ID, typically used in scenarios where
     * the user object is set separately or when only the ID needs to be updated.
     * </p>
     *
     * @param id the user's unique identifier in the database - must not be null
     * 
     * @throws IllegalArgumentException if id is null
     */
    public void setLoggedUserId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        this.loggedUserId = id;
    }
    
    /**
     * Retrieves the currently logged-in user.
     * <p>
     * Returns the User object that was previously set using the setLoggedUser method.
     * This method is used throughout the application to access the current user's information.
     * The returned object may be an instance of Admin or Customer.
     * </p>
     *
     * @return the User object representing the currently logged-in user,
     *         or null if no user is logged in
     */
    public User getLoggedUser() {
        return this.loggedUser;
    }
    
    /**
     * Retrieves the unique identifier of the currently logged-in user.
     * <p>
     * This ID corresponds to the user's record in the database and is used
     * for database operations that require the user's identifier. The ID is
     * set during the login process and cleared when the user logs out.
     * </p>
     *
     * @return the Integer ID of the currently logged-in user,
     *         or null if no user is logged in
     */
    public Integer getLoggedUserId() {
        return this.loggedUserId;
    }
    
    /**
     * Validates an email address format.
     * <p>
     * This method uses a regular expression to check if the provided email
     * address follows the standard email format. It checks for proper structure
     * including the presence of @ symbol, domain structure, and valid characters.
     * </p>
     *
     * @param email the email address to validate - must not be null
     * @return true if the email format is valid, false otherwise
     * 
     * @throws IllegalArgumentException if email is null
     */
    public boolean isValidEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
    
    /**
     * Validates a username format.
     * <p>
     * This method checks if the provided username meets the security requirements:
     * - Must start and end with alphanumeric characters
     * - Can contain letters, numbers, hyphens, underscores, and dots
     * - Must be between 3 and 30 characters long
     * </p>
     *
     * @param username the username to validate - must not be null
     * @return true if the username format is valid, false otherwise
     * 
     * @throws IllegalArgumentException if username is null
     */
    public boolean isValidUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }
        if (username.length() < 3 || username.length() > 30) {
            return false;
        }
        Matcher matcher = USERNAME_PATTERN.matcher(username);
        return matcher.matches();
    }
    
    /**
     * Validates password strength.
     * <p>
     * This method checks if the provided password meets security requirements:
     * - Minimum length of 8 characters
     * - Contains at least one uppercase letter
     * - Contains at least one lowercase letter
     * - Contains at least one digit
     * - Contains at least one special character
     * </p>
     *
     * @param password the password to validate - must not be null
     * @return true if the password meets security requirements, false otherwise
     * 
     * @throws IllegalArgumentException if password is null
     */
    public boolean isValidPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        
        if (password.length() < 8) {
            return false;
        }
        
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }
        }
        
        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
    }
    
    /**
     * Clears the current user session data.
     * <p>
     * This method logs out the current user by setting both the logged user
     * and user ID to null. Should be called when the user logs out or when
     * the session needs to be cleared for security reasons.
     * </p>
     */
    public void logout() {
        this.loggedUser = null;
        this.loggedUserId = null;
    }
    
    /**
     * Checks if a user is currently logged in.
     * <p>
     * This method provides a convenient way to check if there's an active user session
     * without having to check for null values directly.
     * </p>
     *
     * @return true if a user is currently logged in, false otherwise
     */
    public boolean isUserLoggedIn() {
        return this.loggedUser != null && this.loggedUserId != null;
    }
    
    /**
     * Checks if the currently logged-in user is an administrator.
     * <p>
     * This method determines if the current user has administrative privileges
     * by checking if the logged user is an instance of the Admin class.
     * </p>
     *
     * @return true if the current user is an admin, false otherwise or if no user is logged in
     */
    public boolean isCurrentUserAdmin() {
        return this.loggedUser instanceof Admin;
    }
    
    /**
     * Gets the type of the currently logged-in user as a string.
     * <p>
     * This method returns a string representation of the user type,
     * useful for logging, display purposes, or conditional logic.
     * </p>
     *
     * @return "Admin" if current user is an admin, "Customer" if current user is a customer,
     *         or "None" if no user is logged in
     */
    public String getUserType() {
        if (this.loggedUser == null) {
            return "None";
        } else if (this.loggedUser instanceof Admin) {
            return "Admin";
        } else {
            return "Customer";
        }
    }
}