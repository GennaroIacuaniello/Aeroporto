package model;

/**
 * The User class represents a base user in the airport management system.
 * This class serves as the foundation for all user types in the system, providing
 * common attributes and behaviors such as username, email, and password management.
 * It is extended by specific user types like Admin and Customer to provide specialized functionality.
 */
public class User {

    private String username;
    private String email;
    private final String hashedPassword;

    /**
     * Instantiates a new User with username and password only.
     * This constructor creates a user without an email address, which may be set later.
     *
     * @param parUsername       the user's username for login identification
     * @param parHashedPassword the user's password in hashed format for security
     */
    public User(String parUsername, String parHashedPassword){
        this.username = parUsername;
        this.hashedPassword = parHashedPassword;
    }

    /**
     * Instantiates a new User with complete information.
     * This constructor creates a user with username, email, and password.
     *
     * @param parUsername       the user's username for login identification
     * @param parEmail          the user's email address for contact and notifications
     * @param parHashedPassword the user's password in hashed format for security
     */
    public User(String parUsername, String parEmail, String parHashedPassword){
        this.username = parUsername;
        this.email = parEmail;
        this.hashedPassword = parHashedPassword;
    }

    /**
     * Gets the user's username.
     * The username is used for login identification and display purposes.
     *
     * @return the user's username as a String
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * Sets or updates the user's username.
     * This method allows changing the username while maintaining the same user account.
     *
     * @param parUsername the new username to set for this user
     */
    public void setUsername(String parUsername){
        this.username = parUsername;
    }

    /**
     * Gets the user's hashed password.
     * The password is stored in a hashed format for security purposes.
     * Note: This method should be used carefully to avoid exposing password information.
     *
     * @return the user's hashed password as a String
     */
    public String getPassword(){
        return this.hashedPassword;
    }

    /**
     * Gets the user's email address.
     * The email is used for contact, notifications, and account recovery.
     *
     * @return the user's email address as a String, or null if not set
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets or updates the user's email address.
     * This method allows changing the email while maintaining the same user account.
     *
     * @param email the new email address to set for this user
     */
    public void setEmail(String email) {
        this.email = email;
    }

}
