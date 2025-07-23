package model;

public class User {

    private String username;
    private String email;
    private final String hashedPassword;

    public User(String parUsername, String parHashedPassword){
        this.username = parUsername;
        this.hashedPassword = parHashedPassword;
    }

    public User(String parUsername, String parEmail, String parHashedPassword){
        this.username = parUsername;
        this.email = parEmail;
        this.hashedPassword = parHashedPassword;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String parUsername){
        this.username = parUsername;
    }

    public String getPassword(){

        return this.hashedPassword;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
