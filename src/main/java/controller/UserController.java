package controller;

import model.User;

public class UserController {
    private User user;

    public String getUsername() {
        return user.get_username();
    }

    public void setUser (String username, char[] password) {
        user = new User(username, password);
    }
}
