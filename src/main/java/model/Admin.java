package model;

/**
 * The type Admin.
 */
public class Admin extends User{

    /**
     * Instantiates a new Admin.
     *
     * @param parUsername       the par username
     * @param parEmail          the par email
     * @param parHashedPassword the par hashed password
     */
    public Admin(String parUsername, String parEmail, String parHashedPassword){
        super(parUsername, parEmail, parHashedPassword);
    }

}
