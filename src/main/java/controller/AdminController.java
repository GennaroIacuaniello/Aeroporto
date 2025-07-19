package controller;

import model.Admin;

public class AdminController {
    private int loggedAdminId;
    private Admin loggedAdmin;

    public AdminController() {}

    public void setAdmin(String username, String email, String hashedPassword, int id) {
        loggedAdmin = new Admin(username, email, hashedPassword);
        loggedAdminId = id;
    }

    public void setLoggedAdmin(Admin loggedAdmin) {
        this.loggedAdmin = loggedAdmin;
    }

    public Admin getLoggedAdmin() {
        return this.loggedAdmin;
    }
}
