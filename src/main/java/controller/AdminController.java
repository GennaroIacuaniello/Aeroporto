package controller;

import model.Admin;

/**
 * Controller class for managing administrator user operations and session state in the airport management system.
 * <p>
 * This class serves as the specialized controller for administrator-related operations within the MVC
 * architecture of the airport management system. It maintains the current administrator session state
 * and provides methods for managing administrator authentication and profile information.
 * </p>
 * <p>
 * The AdminController is responsible for:
 * </p>
 * <ul>
 *   <li>Maintaining the currently logged-in administrator's session information</li>
 *   <li>Providing access to administrator profile data during the session</li>
 *   <li>Managing administrator authentication state and user identification</li>
 *   <li>Supporting administrator session management across the application</li>
 *   <li>Facilitating administrator-specific operations and permissions</li>
 * </ul>
 * <p>
 * The class follows the singleton-like pattern for session management, storing a single administrator
 * session at a time. This ensures that only one administrator can be logged in per application instance
 * and provides consistent access to administrator information throughout the system.
 * </p>
 * <p>
 * Administrator sessions include both the {@link Admin} object containing profile information
 * (username, email, hashed password) and the unique administrator ID for database operations.
 * This dual approach ensures that both object-oriented operations and database queries can be
 * performed efficiently.
 * </p>
 * <p>
 * The controller integrates with the main {@link Controller} class and other system components
 * to provide administrator-specific functionality such as flight management, system administration,
 * passenger check-in operations, and administrative reporting.
 * </p>
 * <p>
 * Session state is maintained in memory and is not persisted across application restarts,
 * requiring administrators to re-authenticate when the application is restarted.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Admin
 * @see Controller
 */
public class AdminController {
    
    /**
     * The unique database identifier for the currently logged-in administrator.
     * This field is null when no administrator is logged in and contains the
     * administrator's database ID when a session is active.
     */
    private Integer loggedAdminId = null;
    
    /**
     * The {@link Admin} object representing the currently logged-in administrator.
     * Contains the administrator's profile information including username, email,
     * and hashed password. This field is null when no administrator is logged in.
     */
    private Admin loggedAdmin;

    /**
     * Creates and sets an administrator session using individual profile components.
     * <p>
     * This method creates a new {@link Admin} object using the provided profile information
     * and establishes an administrator session. It is typically used during the authentication
     * process when administrator credentials have been verified against the database.
     * </p>
     * <p>
     * The method constructs the {@link Admin} object internally, ensuring that the
     * administrator session is properly initialized with all required information.
     * Both the administrator object and the database ID are stored for use throughout
     * the session.
     * </p>
     * <p>
     * This method is commonly used by authentication workflows where individual
     * profile components are retrieved from the database during login verification.
     * </p>
     *
     * @param username the administrator's username for the session
     * @param email the administrator's email address for the session
     * @param hashedPassword the administrator's hashed password for the session
     * @param id the unique database identifier for the administrator
     */
    public void setAdmin(String username, String email, String hashedPassword, Integer id) {
        loggedAdmin = new Admin(username, email, hashedPassword);
        loggedAdminId = id;
    }

    /**
     * Sets the administrator session using an existing {@link Admin} object.
     * <p>
     * This method establishes an administrator session using a pre-constructed
     * {@link Admin} object and the corresponding database identifier. It is typically
     * used when an {@link Admin} object has already been created and needs to be
     * set as the current session.
     * </p>
     * <p>
     * This approach is useful for session management operations such as updating
     * administrator information, transferring sessions between components, or
     * restoring session state from cached objects.
     * </p>
     * <p>
     * The method directly assigns the provided {@link Admin} object and ID,
     * making them immediately available for use throughout the application.
     * </p>
     *
     * @param loggedAdmin the {@link Admin} object to set as the current session
     * @param id the unique database identifier for the administrator
     */
    public void setLoggedAdmin(Admin loggedAdmin, Integer id) {
        this.loggedAdmin = loggedAdmin;
        this.loggedAdminId = id;
    }

    /**
     * Retrieves the currently logged-in administrator object.
     * <p>
     * This method returns the {@link Admin} object representing the current
     * administrator session. The returned object contains the administrator's
     * profile information including username, email, and hashed password.
     * </p>
     * <p>
     * The method is used throughout the application to access administrator
     * information for display purposes, permission checking, and business
     * logic operations that require administrator context.
     * </p>
     * <p>
     * If no administrator is currently logged in, this method returns null.
     * Calling code should check for null values to handle cases where no
     * administrator session is active.
     * </p>
     *
     * @return the {@link Admin} object for the current session, or null if no administrator is logged in
     */
    public Admin getLoggedAdmin() {
        return this.loggedAdmin;
    }

    /**
     * Retrieves the database identifier for the currently logged-in administrator.
     * <p>
     * This method returns the unique database identifier for the current administrator
     * session. The ID is used for database operations that require administrator
     * identification, such as logging administrative actions, updating administrator
     * profiles, or associating administrative operations with specific users.
     * </p>
     * <p>
     * The database ID provides an efficient way to reference the administrator
     * in database queries without needing to pass the entire {@link Admin} object
     * or perform username-based lookups.
     * </p>
     * <p>
     * If no administrator is currently logged in, this method returns null.
     * Database operations should check for null values and handle the case
     * where no administrator session is active.
     * </p>
     *
     * @return the unique database identifier for the current administrator session, or null if no administrator is logged in
     */
    public Integer getLoggedAdminId(){ 
        return this.loggedAdminId; 
    }
}