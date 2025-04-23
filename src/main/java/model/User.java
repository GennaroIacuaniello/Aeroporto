package model;

/*TO DO:
    - rivedere la visibilità di metodi e attributi
    - rivedere tipi di ritorno metodi
    -rivedere tutti i controlli su username e password una volta implementata la comunicazione col databse

 */

abstract public class User {

    protected String username;
    protected String password;

    public User(String par_username, String par_password){
        this.username = par_username;
        this.password = par_password;
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
        return this.password;
    }

    public int set_password(String par_password){
        this.username = par_password;
        return 0;
    }

    private void login(){};
    private void search_flight(){};

}
