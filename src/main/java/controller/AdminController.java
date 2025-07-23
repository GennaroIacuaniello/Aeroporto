package controller;

import model.Admin;

public class AdminController {
    private Integer loggedAdminId = null;
    private Admin loggedAdmin;

    public void setAdmin(String username, String email, String hashedPassword, Integer id) {
        loggedAdmin = new Admin(username, email, hashedPassword);
        loggedAdminId = id;
    }

    public void setLoggedAdmin(Admin loggedAdmin, Integer id) {
        this.loggedAdmin = loggedAdmin;
        this.loggedAdminId = id;
    }

    public Admin getLoggedAdmin() {
        return this.loggedAdmin;
    }

    public Integer getLoggedAdminId(){ return this.loggedAdminId; }
}
