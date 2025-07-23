package controller;

import model.Admin;

/**
 * The type Admin controller.
 */
public class AdminController {
    private Integer loggedAdminId = null;
    private Admin loggedAdmin;

    /**
     * Sets admin.
     *
     * @param username       the username
     * @param email          the email
     * @param hashedPassword the hashed password
     * @param id             the id
     */
    public void setAdmin(String username, String email, String hashedPassword, Integer id) {
        loggedAdmin = new Admin(username, email, hashedPassword);
        loggedAdminId = id;
    }

    /**
     * Sets logged admin.
     *
     * @param loggedAdmin the logged admin
     * @param id          the id
     */
    public void setLoggedAdmin(Admin loggedAdmin, Integer id) {
        this.loggedAdmin = loggedAdmin;
        this.loggedAdminId = id;
    }

    /**
     * Gets logged admin.
     *
     * @return the logged admin
     */
    public Admin getLoggedAdmin() {
        return this.loggedAdmin;
    }

    /**
     * Get logged admin id integer.
     *
     * @return the integer
     */
    public Integer getLoggedAdminId(){ return this.loggedAdminId; }
}







