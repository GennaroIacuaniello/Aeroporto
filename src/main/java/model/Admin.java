package model;

/**
 * Represents an administrator user in the airport management system.
 * <p>
 * This class extends the {@link User} class to provide administrator-specific functionality
 * within the airport management system. Administrators have elevated privileges compared to
 * regular customers and can perform administrative tasks such as flight management,
 * passenger check-in operations, and system configuration.
 * </p>
 * <p>
 * Unlike regular customers, administrators are required to have an email address for
 * account creation and communication purposes. This class inherits all basic user
 * functionality from the {@link User} parent class while serving as a marker class
 * to distinguish administrative users from regular customers in the system.
 * </p>
 * <p>
 * Administrative users can access specialized interfaces and perform operations such as:
 * </p>
 * <ul>
 *   <li>Managing flight schedules and status</li>
 *   <li>Processing passenger check-ins</li>
 *   <li>Handling lost luggage reports</li>
 *   <li>Adding new flights to the system</li>
 *   <li>Managing booking confirmations</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see User
 */
public class Admin extends User{

    /**
     * Constructs a new Admin with the specified username, email, and hashed password.
     * <p>
     * Creates an administrator user account with all required information. Unlike regular
     * customers, administrators must provide an email address during account creation
     * as it is used for administrative communications and system notifications.
     * </p>
     * <p>
     * The password should be provided in hashed format for security purposes and
     * cannot be changed after object creation due to the immutable nature of the
     * password field inherited from the parent {@link User} class.
     * </p>
     *
     * @param parUsername the username for the administrator account. Must not be null or empty.
     *                   This serves as the primary identifier for login purposes.
     * @param parEmail the email address for the administrator account. Must not be null or empty.
     *                This is required for administrative communications and system notifications.
     * @param parHashedPassword the hashed password for the administrator account. Must not be null or empty.
     *                         The password is stored in hashed format for security purposes.
     * @throws IllegalArgumentException if any parameter is null or empty
     * @see User#User(String, String, String)
     */
    public Admin(String parUsername, String parEmail, String parHashedPassword){
        super(parUsername, parEmail, parHashedPassword);
    }

}