package model;

/*TO DO:
    - rivedere la visibilità di metodi e attributi
    - rivedere tipi di ritorno metodi
    -rivedere tutti i controlli su username e password una volta implementata la comunicazione col databse

 */

public class User {

    private String username;
    private String hashedPassword;

    public User(String par_username, String parHashedPassword){
        this.username = par_username;
        this.hashedPassword = parHashedPassword;
        //Più avanti: cercare l'username nel database e verificare che la password sia corretta
    }

    public String get_username(){
        return this.username;
    }

    public int set_username(String par_username){
        this.username = par_username;
        return 0;
    }

    public String get_password(){
        return this.hashedPassword;
    }

    public int set_password(String parNewHashedPassword){
        this.hashedPassword = parNewHashedPassword;
        return 0;
    }

    //private void login(){};
    //private void search_flight(){};

}
