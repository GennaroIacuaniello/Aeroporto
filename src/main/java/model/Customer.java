package model;

/*TO DO:
    - rivedere la visibilità di metodi e attributi
    - rivedere tipi di ritorno metodi
    -attenzione alle return messe solo per evitare errori

 */

public class Customer extends User{

    private void book_flight(){};
    private Booking search_booking(){
        return new Booking();
    };
    private void update_boooking(){};
    private BookingStatus check_booking_status(){
        return BookingStatus.pending;
    };
    private Passenger search_passenger(){
        return new Passenger();
    };
    private Luggage search_luggage(){
        return new Luggage();
    };
    private void report_missing_luggage(){};

}
