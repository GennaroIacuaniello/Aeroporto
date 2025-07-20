package controller;

import model.User;

public class UserController {
    private Integer loggedUserId;
    private User loggedUser;

    public Integer getLoggedUserId() {
        return loggedUserId;
    }

    public void setLoggedUserId(Integer id) {
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

    public void setLoggedUser(User loggedUser, Integer id) {
        this.loggedUser = loggedUser;
        this.loggedUserId = id;
    }

    public User getLoggedUser() {
        return this.loggedUser;
    }
}
