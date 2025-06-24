package controller;

import model.Admin;

public class AdminController {
    Admin admin;

    public AdminController() {}

    public void setAdmin(String username, String password) {
        admin = new Admin(username, password);
    }
}
