package controller;

import model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public void setLoggedUser(User loggedUser, Integer id) {
        this.loggedUser = loggedUser;
        this.loggedUserId = id;
    }

    public User getLoggedUser() {
        return this.loggedUser;
    }

    public boolean isValidMail(String mail){
        //this would be a more robust function in a real world application
        //right now, it only checks that it has at least one word character, if it has a dot it needs
        //at least another word character then you need a @ character, then a word character, a dot and
        //another word character, so it allows stuff like _@_._
        if(mail.length() <= 50){
            Pattern pattern = Pattern.compile("[a-zA-Z0-9]+([.|_][a-zA-Z0-9]+)*@[a-zA-Z0-9]+([.|_][a-zA-Z0-9]+)*[.][a-zA-Z]+");
            Matcher matcher = pattern.matcher(mail);
            return matcher.matches();

        }
        return false;
    }

    public boolean isValidUsername(String username) {
        if(username.length() >= 4 && username.length() <= 20){
            Pattern pattern = Pattern.compile("[a-zA-Z]+(\\w|\\.|-)*[a-zA-Z0-9]");
            Matcher matcher = pattern.matcher(username);
            return matcher.matches();
        }
        return false;
    }
}
