package model;

/**
 * Represents a user in the airport management system.
 * <p>
 * This class serves as the base class for different types of users in the system:
 * administrators and customers.
 * </p>
 * <p>
 * The password is stored as a final field to ensure immutability after object creation,
 * while username and email can be modified through their respective setter methods.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 */
public class User {

    /**
     * The username of the user.
     * <p>
     * This field represents the unique identifier that the user uses to log into the system.
     * It can be modified after user creation through the {@link #setUsername(String)} method.
     * </p>
     */
    private String username;

    /**
     * The email address of the user.
     * <p>
     * This field stores the user's email address, which can be used for communication
     * and as an alternative login method. It can be modified after user creation
     * through the {@link #setEmail(String)} method.
     * </p>
     */
    private String email;

    /**
     * The hashed password of the user.
     * <p>
     * This field stores the user's password in hashed format for security purposes.
     * It is declared as final to ensure that the password cannot be changed after
     * object creation, maintaining data integrity and security.
     * </p>
     */
    private final String hashedPassword;

    /**
     * Constructs a new User with username, email, and hashed password.
     *
     * @param parUsername       the username for the new user. Must not be null or empty.
     * @param parEmail          the email address for the new user. Must not be null or empty.
     * @param parHashedPassword the hashed password for the new user. Must not be null or empty.
     */
    public User(String parUsername, String parEmail, String parHashedPassword) {
        this.username = parUsername;
        this.email = parEmail;
        this.hashedPassword = parHashedPassword;
    }

    /**
     * Gets the username of this user.
     * <p>
     * Returns the current username that identifies this user in the system.
     * </p>
     *
     * @return the username of this user, never null or empty.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets the username for this user.
     * <p>
     * Updates the username that identifies this user in the system.
     * This method allows for username changes after user creation.
     * </p>
     *
     * @param parUsername the new username to set.  never null or empty.
     */
    public void setUsername(String parUsername) {
        this.username = parUsername;
    }

    /**
     * Gets the hashed password of this user.
     * <p>
     * Returns the hashed password stored for this user. The password is returned
     * in its hashed form for security purposes and cannot be converted back to
     * the original plaintext password.
     * </p>
     *
     * @return the hashed password of this user, never null or empty.
     */
    public String getPassword() {

        return this.hashedPassword;

    }

    /**
     * Gets the email address of this user.
     * <p>
     * Returns the current email address associated with this user account.
     * The email can be used for communication and as an alternative login method.
     * </p>
     *
     * @return the email address of this user, or null if no email was provided
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address for this user.
     * <p>
     * Updates the email address associated with this user account.
     * This method allows for email changes after user creation.
     * </p>
     *
     * @param email the new email address to set. Can be null to remove the email.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}