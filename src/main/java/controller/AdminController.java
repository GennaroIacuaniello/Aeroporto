package controller;

import model.Admin;

/**
 * The AdminController class manages administrator authentication and session data.
 * <p>
 * This controller handles operations related to admin users, including login, session management,
 * and retrieving admin information. It maintains the state of the currently logged-in administrator
 * and provides methods to access and modify admin session data securely.
 * </p>
 * 
 * @author Your Name
 * @version 1.0
 * @since 1.0
 */
public class AdminController {
    
    /**
     * The unique identifier of the currently logged-in administrator.
     * This ID corresponds to the admin's record in the database and is used
     * for database operations related to the administrator.
     * Set to null when no admin is logged in.
     */
    private Integer loggedAdminId = null;
    
    /**
     * The Admin object representing the currently logged-in administrator.
     * This object contains the admin's information such as username, email,
     * and hashed password. Set to null when no admin is logged in.
     */
    private Admin loggedAdmin;

    /**
     * Sets the currently logged-in admin with the provided credentials and ID.
     * <p>
     * This method creates a new Admin object with the provided credentials and stores it 
     * along with the admin's ID. This is typically used during the authentication process
     * when admin credentials are verified against the database.
     * </p>
     *
     * @param username       the admin's username - must not be null or empty
     * @param email          the admin's email address - must be a valid email format
     * @param hashedPassword the admin's hashed password (for security) - must not be null
     * @param id             the admin's unique identifier in the database - must not be null
     * 
     * @throws IllegalArgumentException if any parameter is null or if username/email is empty
     */
    public void setAdmin(String username, String email, String hashedPassword, Integer id) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (hashedPassword == null) {
            throw new IllegalArgumentException("Hashed password cannot be null");
        }
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        
        loggedAdmin = new Admin(username, email, hashedPassword);
        loggedAdminId = id;
    }

    /**
     * Sets the currently logged-in admin using an existing Admin object and ID.
     * <p>
     * This method stores a reference to the provided Admin object along with the admin's ID.
     * Used primarily during authentication and session management when an Admin object
     * already exists and needs to be set as the current logged-in admin.
     * </p>
     *
     * @param loggedAdmin the Admin object representing the logged-in administrator - must not be null
     * @param id          the admin's unique identifier in the database - must not be null
     * 
     * @throws IllegalArgumentException if loggedAdmin or id is null
     */
    public void setLoggedAdmin(Admin loggedAdmin, Integer id) {
        if (loggedAdmin == null) {
            throw new IllegalArgumentException("Logged admin cannot be null");
        }
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        
        this.loggedAdmin = loggedAdmin;
        this.loggedAdminId = id;
    }

    /**
     * Retrieves the currently logged-in admin.
     * <p>
     * Returns the Admin object that was previously set using {@link #setAdmin(String, String, String, Integer)}
     * or {@link #setLoggedAdmin(Admin, Integer)} methods. This method is used throughout the application
     * to access the current admin's information.
     * </p>
     *
     * @return the Admin object representing the currently logged-in administrator,
     *         or null if no admin is logged in
     */
    public Admin getLoggedAdmin() {
        return this.loggedAdmin;
    }

    /**
     * Retrieves the unique identifier of the currently logged-in admin.
     * <p>
     * This ID corresponds to the admin's record in the database and is used
     * for database operations that require the admin's identifier. The ID is
     * set during the login process and cleared when the admin logs out.
     * </p>
     *
     * @return the Integer ID of the currently logged-in administrator,
     *         or null if no admin is logged in
     */
    public Integer getLoggedAdminId() { 
        return this.loggedAdminId; 
    }
    
    /**
     * Clears the current admin session data.
     * <p>
     * This method logs out the current admin by setting both the logged admin
     * and admin ID to null. Should be called when the admin logs out or when
     * the session needs to be cleared for security reasons.
     * </p>
     */
    public void logout() {
        this.loggedAdmin = null;
        this.loggedAdminId = null;
    }
    
    /**
     * Checks if an admin is currently logged in.
     * <p>
     * This method provides a convenient way to check if there's an active admin session
     * without having to check for null values directly.
     * </p>
     *
     * @return true if an admin is currently logged in, false otherwise
     */
    public boolean isAdminLoggedIn() {
        return this.loggedAdmin != null && this.loggedAdminId != null;
    }
}