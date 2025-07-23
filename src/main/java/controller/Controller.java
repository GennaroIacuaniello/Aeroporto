package controller;

import dao.UserAlreadyExistsException;
import dao.UserNotFoundException;
import gui.FloatingMessage;
import implementazioni_postgres_dao.AdminDAOImpl;
import implementazioni_postgres_dao.CustomerDAOImpl;
import model.Admin;
import model.Customer;
import model.User;
import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * The Controller class serves as the main coordinator for all application operations.
 * <p>
 * This class acts as a central controller that manages interactions between the GUI layer,
 * business logic, and data access layer. It coordinates operations across different specialized
 * controllers (AdminController, CustomerController, UserController) and handles authentication,
 * user management, and application-wide functionality.
 * </p>
 * <p>
 * The Controller implements the Facade pattern, providing a simplified interface to the
 * complex subsystems of the airport management application. It manages user sessions,
 * handles login/logout operations, and coordinates between different user types and their
 * respective operations.
 * </p>
 * 
 * @author Your Name
 * @version 1.0
 * @since 1.0
 */
public class Controller {

    /**
     * The AdminController instance for managing administrator-specific operations.
     * <p>
     * This controller handles admin authentication, session management, and
     * admin-specific functionality within the application.
     * </p>
     */
    private AdminController adminController;
    
    /**
     * The CustomerController instance for managing customer-specific operations.
     * <p>
     * This controller handles customer authentication, session management, and
     * customer-specific functionality including booking operations.
     * </p>
     */
    private CustomerController customerController;
    
    /**
     * The UserController instance for managing common user operations.
     * <p>
     * This controller handles operations common to all user types, including
     * validation, authentication, and general user management functionality.
     * </p>
     */
    private UserController userController;
    
    /**
     * Static logger instance for logging application events and errors.
     * <p>
     * This logger is used throughout the controller to record important events,
     * errors, and debugging information for monitoring and troubleshooting purposes.
     * </p>
     */
    private static final Logger logger = Logger.getLogger(Controller.class.getName());

    /**
     * Constructs a new Controller instance.
     * <p>
     * Initializes all the specialized controllers (AdminController, CustomerController,
     * UserController) that this main controller coordinates. This constructor sets up
     * the foundation for all application operations.
     * </p>
     */
    public Controller() {
        this.adminController = new AdminController();
        this.customerController = new CustomerController();
        this.userController = new UserController();
    }

    /**
     * Verifies user credentials and performs authentication.
     * <p>
     * This method handles the complete authentication process for both admin and customer users.
     * It first validates the input format (email or username), then attempts to authenticate
     * the user against the database. The method supports login with either email or username
     * and automatically determines the user type (Admin or Customer) during authentication.
     * </p>
     * <p>
     * The authentication process follows this sequence:
     * 1. Validate input format (email or username)
     * 2. Attempt admin authentication first
     * 3. If admin authentication fails, attempt customer authentication
     * 4. Set appropriate user session if authentication succeeds
     * 5. Display error messages for failed attempts
     * </p>
     *
     * @param loggingInfo   the login identifier - can be either email address or username
     * @param hashedPassword the user's password in hashed format for security
     * @param loginButton   the login button component for displaying validation messages
     * 
     * @return true if authentication succeeds and user session is established, false otherwise
     * 
     * @throws IllegalArgumentException if loggingInfo or hashedPassword is null
     * 
     * @see AdminController#setLoggedAdmin(Admin, Integer)
     * @see CustomerController#setLoggedCustomer(Customer, Integer)
     * @see UserController#setLoggedUser(User, Integer)
     */
    public boolean verifyUser(String loggingInfo, String hashedPassword, JButton loginButton) {
        if (loggingInfo == null || hashedPassword == null) {
            throw new IllegalArgumentException("Login info and password cannot be null");
        }
        
        // Validate input format before database operations
        if (loggingInfo.contains("@")) {
            if (!userController.isValidEmail(loggingInfo)) {
                new FloatingMessage("<html>User o mail non valida</html>", 
                    loginButton, FloatingMessage.WARNING_MESSAGE);
                return false;
            }
        } else if (!userController.isValidUsername(loggingInfo)) {
            new FloatingMessage("<html>User o mail non valida</html>", 
                loginButton, FloatingMessage.WARNING_MESSAGE);
            return false;
        }
        
        ArrayList<Integer> userID = new ArrayList<>();
        ArrayList<String> mail = new ArrayList<>();
        ArrayList<String> username = new ArrayList<>();
        
        try {
            // Attempt admin authentication first
            AdminDAOImpl adminDAO = new AdminDAOImpl();
            if (loggingInfo.contains("@")) {
                adminDAO.searchUserByMail(userID, username, loggingInfo, hashedPassword);
                adminController.setLoggedAdmin(new Admin(username.getFirst(), loggingInfo, hashedPassword), userID.getFirst());
                userController.setLoggedUser(new Admin(username.getFirst(), loggingInfo, hashedPassword), userID.getFirst());
            } else {
                adminDAO.searchUserByUsername(userID, loggingInfo, mail, hashedPassword);
                adminController.setLoggedAdmin(new Admin(loggingInfo, mail.getFirst(), hashedPassword), userID.getFirst());
                userController.setLoggedUser(new Admin(loggingInfo, mail.getFirst(), hashedPassword), userID.getFirst());
            }
            logger.info("Admin user successfully authenticated: " + loggingInfo);
            return true;
            
        } catch (UserNotFoundException e) {
            try {
                // Attempt customer authentication if admin authentication fails
                CustomerDAOImpl customerDAO = new CustomerDAOImpl();
                if (loggingInfo.contains("@")) {
                    customerDAO.searchUserByMail(userID, username, loggingInfo, hashedPassword);
                    customerController.setLoggedCustomer(new Customer(username.getFirst(), loggingInfo, hashedPassword), userID.getFirst());
                    userController.setLoggedUser(new Customer(username.getFirst(), loggingInfo, hashedPassword), userID.getFirst());
                } else {
                    customerDAO.searchUserByUsername(userID, loggingInfo, mail, hashedPassword);
                    customerController.setLoggedCustomer(new Customer(loggingInfo, mail.getFirst(), hashedPassword), userID.getFirst());
                    userController.setLoggedUser(new Customer(loggingInfo, mail.getFirst(), hashedPassword), userID.getFirst());
                }
                logger.info("Customer user successfully authenticated: " + loggingInfo);
                return true;
                
            } catch (UserNotFoundException customerException) {
                logger.warning("Authentication failed for user: " + loggingInfo);
                new FloatingMessage("<html>Credenziali non valide</html>", 
                    loginButton, FloatingMessage.ERROR_MESSAGE);
                return false;
            } catch (SQLException sqlException) {
                logger.severe("Database error during customer authentication: " + sqlException.getMessage());
                new FloatingMessage("<html>Errore di connessione al database</html>", 
                    loginButton, FloatingMessage.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException sqlException) {
            logger.severe("Database error during admin authentication: " + sqlException.getMessage());
            new FloatingMessage("<html>Errore di connessione al database</html>", 
                loginButton, FloatingMessage.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Updates the username of the currently logged-in user.
     * <p>
     * This method allows users to change their username while maintaining their session.
     * It validates the new username format, checks for uniqueness in the database,
     * and updates both the database record and the current session information.
     * The method handles both Admin and Customer users appropriately.
     * </p>
     * <p>
     * The update process includes:
     * 1. Username format validation
     * 2. Database update operation
     * 3. Session information refresh
     * 4. Controller state synchronization
     * </p>
     *
     * @param username the new username - must be valid format and unique
     * @param hashedPassword the user's current hashed password for verification
     * @param button the UI button for displaying validation messages
     * 
     * @return true if username update succeeds, false otherwise
     * 
     * @throws IllegalArgumentException if username or hashedPassword is null
     * 
     * @see UserController#isValidUsername(String)
     * @see AdminController#setLoggedAdmin(Admin, Integer)
     * @see CustomerController#setLoggedCustomer(Customer, Integer)
     */
    public boolean updateUsername(String username, String hashedPassword, JButton button) {
        if (username == null || hashedPassword == null) {
            throw new IllegalArgumentException("Username and password cannot be null");
        }
        
        // Validate username format
        if (!userController.isValidUsername(username)) {
            new FloatingMessage("<html>Username non valido. Deve iniziare e " +
                "finire con una lettera o un numero e può contenere solo lettere, numeri, " +
                "trattini (-), underscore(_) e punti(.)</html>",
                button, FloatingMessage.WARNING_MESSAGE);
            return false;
        }
        
        try {
            if (userController.getLoggedUser() instanceof Admin) {
                // Update admin username
                AdminDAOImpl adminDAO = new AdminDAOImpl();
                adminDAO.updateAdmin(userController.getLoggedUserId(), username, hashedPassword);
                
                // Update session information
                String email = userController.getLoggedUser().getEmail();
                adminController.setLoggedAdmin(new Admin(username, email, hashedPassword), 
                    userController.getLoggedUserId());
                userController.setLoggedUser(new Admin(username, email, hashedPassword), 
                    userController.getLoggedUserId());
                    
                logger.info("Admin username updated successfully: " + username);
                
            } else {
                // Update customer username
                CustomerDAOImpl customerDAO = new CustomerDAOImpl();
                String email = userController.getLoggedUser().getEmail();
                customerDAO.updateCustomer(userController.getLoggedUserId(), email, username, hashedPassword);
                
                // Update session information
                customerController.setLoggedCustomer(new Customer(username, email, hashedPassword), 
                    userController.getLoggedUserId());
                userController.setLoggedUser(new Customer(username, email, hashedPassword), 
                    userController.getLoggedUserId());
                    
                logger.info("Customer username updated successfully: " + username);
            }
            
            new FloatingMessage("<html>Username aggiornato con successo</html>", 
                button, FloatingMessage.SUCCESS_MESSAGE);
            return true;
            
        } catch (UserAlreadyExistsException e) {
            logger.warning("Username update failed - username already exists: " + username);
            new FloatingMessage("<html>Username già esistente</html>", 
                button, FloatingMessage.WARNING_MESSAGE);
            return false;
        } catch (SQLException e) {
            logger.severe("Database error during username update: " + e.getMessage());
            new FloatingMessage("<html>Errore di database durante l'aggiornamento</html>", 
                button, FloatingMessage.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Retrieves the AdminController instance.
     * <p>
     * This method provides access to the admin-specific controller for operations
     * that require direct interaction with admin functionality. It's typically used
     * by GUI components that need to access admin-specific methods or data.
     * </p>
     *
     * @return the AdminController instance used by this main controller
     */
    public AdminController getAdminController() {
        return adminController;
    }

    /**
     * Retrieves the CustomerController instance.
     * <p>
     * This method provides access to the customer-specific controller for operations
     * that require direct interaction with customer functionality. It's typically used
     * by GUI components that need to access customer-specific methods or data.
     * </p>
     *
     * @return the CustomerController instance used by this main controller
     */
    public CustomerController getCustomerController() {
        return customerController;
    }

    /**
     * Retrieves the UserController instance.
     * <p>
     * This method provides access to the general user controller for operations
     * that are common to all user types. It's used for validation, authentication,
     * and general user management functionality.
     * </p>
     *
     * @return the UserController instance used by this main controller
     */
    public UserController getUserController() {
        return userController;
    }

    /**
     * Gets the static logger instance.
     * <p>
     * This method provides access to the application's main logger for components
     * that need to perform logging operations. The logger is configured to handle
     * application-wide logging needs including errors, warnings, and informational messages.
     * </p>
     *
     * @return the Logger instance for this controller class
     */
    public static Logger getLogger() {
        return logger;
    }

    /**
     * Performs a complete logout operation for the current user.
     * <p>
     * This method clears all user session data across all controllers, ensuring
     * that no user information remains in memory after logout. It coordinates
     * the logout process across AdminController, CustomerController, and UserController
     * to maintain consistency throughout the application.
     * </p>
     * <p>
     * The logout process includes:
     * 1. Clearing UserController session data
     * 2. Clearing AdminController session data
     * 3. Clearing CustomerController session data
     * 4. Logging the logout event
     * </p>
     */
    public void performLogout() {
        String userInfo = userController.getLoggedUser() != null ? 
            userController.getLoggedUser().getUsername() : "Unknown";
        
        userController.logout();
        adminController.logout();
        customerController.logout();
        
        logger.info("User logged out successfully: " + userInfo);
    }

    /**
     * Checks if any user is currently logged in to the application.
     * <p>
     * This method provides a convenient way to check the overall authentication
     * state of the application. It delegates to the UserController to determine
     * if there's an active user session.
     * </p>
     *
     * @return true if a user (admin or customer) is currently logged in, false otherwise
     */
    public boolean isUserLoggedIn() {
        return userController.isUserLoggedIn();
    }

    /**
     * Determines if the currently logged-in user has administrative privileges.
     * <p>
     * This method is used throughout the application to control access to
     * admin-only features and to customize the user interface based on user type.
     * </p>
     *
     * @return true if the current user is an administrator, false otherwise
     */
    public boolean isCurrentUserAdmin() {
        return userController.isCurrentUserAdmin();
    }

    /**
     * Gets a string representation of the current user's type.
     * <p>
     * This method is useful for logging, debugging, and display purposes
     * where a string representation of the user type is needed.
     * </p>
     *
     * @return "Admin", "Customer", or "None" depending on the current user state
     */
    public String getCurrentUserType() {
        return userController.getUserType();
    }
}