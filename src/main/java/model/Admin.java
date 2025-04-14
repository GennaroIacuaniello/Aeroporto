package model;

/*TO DO:
    - rivedere la visibilità di metodi e attributi
    - rivedere tipi di ritorno metodi
    -attenzione alle return messe solo per evitare errori
 */

public class Admin extends User{

    private void insert_flight(){};
    private void update_flight(){};
    private Passenger search_passenger(){
        return new Passenger();
    };
    private Luggage search_luggage(){
        return new Luggage();
    };
    private void check_in_passenger(){};
    private void check_missing_reports(){};

}
