package controller;

import model.Admin;

public class AdminController {
    Admin admin;

    public AdminController() {}

    public void setAdmin(String username, String email, String hashedPassword) {
        admin = new Admin(username, email, hashedPassword);
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Admin getAdmin() {
        return this.admin;
    }
}
