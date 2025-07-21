package controller;

import dao.UserAlreadyExistsException;
import gui.FloatingMessage;
import implementazioniPostgresDAO.AdminDAOImpl;
import implementazioniPostgresDAO.CustomerDAOImpl;
import model.User;

import javax.swing.*;
import java.sql.SQLException;
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

    //this would be a more robust function in a real world application (probably send an email and ask user to verify)
    //right now, it only checks that it has at least one letter/digit, if it has a dot/underscore it needs
    // at least another letter/digit before the @; then, after the @ character, it follows the same rules as before,
    // but it needs to end with a dot followed by at least one letter.
    public boolean isValidMail(String mail){
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

    public void registerUser(String mail, String username, String hashedPassword, JButton registerButton){
        if(!isValidMail(mail)){
            new FloatingMessage("<html>Mail non valida</html>", registerButton, FloatingMessage.WARNING_MESSAGE);
            return;
        }
        if(!isValidUsername(username)){
            new FloatingMessage("<html>Username non valido.<br>Il nome utente deve iniziare con una lettera, " +
                    "finire con una lettera o un numero e può contenere solo lettere, numeri, trattini (-), underscore(_) e punti(.)</html>",
                    registerButton, FloatingMessage.WARNING_MESSAGE);
            return;
        }

        try{
            if (mail.contains("@aeroportodinapoli.it") || mail.contains("@adn.it")){
                AdminDAOImpl adminDAO = new AdminDAOImpl();
                adminDAO.insertNewAdmin(mail, username, hashedPassword);
            } else{
                CustomerDAOImpl customerDAO = new CustomerDAOImpl();
                customerDAO.insertNewCustomer(mail, username, hashedPassword);
            }

            new FloatingMessage("<html>Sei stato registrato con successo!<br>Procedi a fare il login</html>", registerButton, FloatingMessage.SUCCESS_MESSAGE);

        } catch(UserAlreadyExistsException e){
            new FloatingMessage("<html>" + e.getMessage() + "</html>", registerButton, FloatingMessage.WARNING_MESSAGE);
        } catch (SQLException e){
            new FloatingMessage("<html>Errore nel collegamento al DB(Customer)<br>" + e.getMessage() + "</html>", registerButton, FloatingMessage.ERROR_MESSAGE);
        }
    }

}
