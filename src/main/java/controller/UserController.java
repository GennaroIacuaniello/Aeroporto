package controller;

import model.User;

public class UserController {
    private int loggedUserId;
    private User loggedUser;

    public int getLoggedUserId() {
        return loggedUserId;
    }

    public void setLoggedUserId(int id) {
        this.loggedUserId  = id;
    }

    public String getUsername() {
        return loggedUser.getUsername();
    }

    public void setUser (String username, String hashedPassword, int id) {
        loggedUser = new User(username, hashedPassword);
        loggedUserId = id;
    }

    public void setUser (String username, String email, String hashedPassword) {
        loggedUser = new User(username, email, hashedPassword);
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public User getLoggedUser() {
        return this.loggedUser;
    }
}
