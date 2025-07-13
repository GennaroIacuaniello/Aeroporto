package model;

/*TO DO:
    - rivedere la visibilità di metodi e attributi
    - rivedere tipi di ritorno metodi
    -rivedere tutti i controlli su username e password una volta implementata la comunicazione col databse

 */

public class User {

    private String username;
    private String email;
    private String hashedPassword;

    public User(String par_username, String parHashedPassword){
        this.username = par_username;
        this.hashedPassword = parHashedPassword;
        //Più avanti: cercare l'username nel database e verificare che la password sia corretta
    }

    public String get_username(){
        return this.username;
    }

    public void set_username(String par_username){
        this.username = par_username;
    }

    public String get_password(){

        return this.hashedPassword;

    }

    //non penso proprio vada fatto setPassword, la si inizializza solo nel costruttore, se poi si deve fare
    //il cambio password la si aggiorna nel DB e poi si costruisce un nuovo oggetto
    /*public int set_password(String parNewHashedPassword){
        this.hashedPassword = parNewHashedPassword;
        return 0;
    }*/

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //private void login(){};
    //private void search_flight(){};

}
