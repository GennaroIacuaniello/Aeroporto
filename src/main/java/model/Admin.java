package model;

/*TO DO:
    - rivedere la visibilità di metodi e attributi
    - rivedere tipi di ritorno metodi
    - attenzione alle return messe solo per evitare errori //da errore lo stesso
 */

public class Admin extends User{

    public Admin(String par_username, String par_password){
        super(par_username, par_password);
    }

    private void insert_flight(){};
    private void update_flight(){};
    /*
    private Passenger search_passenger(){};
    private Luggage search_luggage(){};
     */
    private void check_in_passenger(){};
    private void check_missing_reports(){};
    private void update_gate(){};

}
