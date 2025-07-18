package controller;

import model.User;

public class UserController {
    private User user;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id  = id;
    }

    public String getUsername() {
        return user.getUsername();
    }

    public void setUser (String username, String hashedPassword) {
        user = new User(username, hashedPassword);
    }

    public void setUser (String username, String email, String hashedPassword) {
        user = new User(username, email, hashedPassword);
    }

    public void setUser (User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}
