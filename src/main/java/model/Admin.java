package model;

/**
 * The Admin class represents an administrator user in the airport management system.
 * Administrators have special privileges and can perform operations that regular users cannot,
 * such as managing flights, gates, and viewing system-wide data.
 * This class extends the base User class, inheriting basic user properties and behaviors.
 */
public class Admin extends User{

    /**
     * Instantiates a new Admin with the specified credentials.
     * Creates an administrator user with the provided username, email, and password.
     *
     * @param parUsername       the administrator's username for login
     * @param parEmail          the administrator's email address for contact and notifications
     * @param parHashedPassword the administrator's password in hashed format for security
     */
    public Admin(String parUsername, String parEmail, String parHashedPassword){
        super(parUsername, parEmail, parHashedPassword);
    }

}
