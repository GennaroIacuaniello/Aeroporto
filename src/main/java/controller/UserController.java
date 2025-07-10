package controller;

import model.Customer;
import model.User;

public class UserController {
    private User user;

    public String getUsername() {
        return user.get_username();
    }

    public void setUser (String username, String hashedPassword) {
        user = new User(username, hashedPassword);
    }

    public void setUser (User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}
