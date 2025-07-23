package model;

/**
 * The type User.
 */
public class User {

    private String username;
    private String email;
    private final String hashedPassword;

    /**
     * Instantiates a new User.
     *
     * @param parUsername       the par username
     * @param parHashedPassword the par hashed password
     */
    public User(String parUsername, String parHashedPassword){
        this.username = parUsername;
        this.hashedPassword = parHashedPassword;
    }

    /**
     * Instantiates a new User.
     *
     * @param parUsername       the par username
     * @param parEmail          the par email
     * @param parHashedPassword the par hashed password
     */
    public User(String parUsername, String parEmail, String parHashedPassword){
        this.username = parUsername;
        this.email = parEmail;
        this.hashedPassword = parHashedPassword;
    }

    /**
     * Get username string.
     *
     * @return the string
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * Set username.
     *
     * @param parUsername the par username
     */
    public void setUsername(String parUsername){
        this.username = parUsername;
    }

    /**
     * Get password string.
     *
     * @return the string
     */
    public String getPassword(){

        return this.hashedPassword;

    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

}
