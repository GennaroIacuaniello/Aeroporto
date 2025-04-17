package model;
import java.util.ArrayList;

/*TO DO:
    - rivedere la visibilità di metodi e attributi
    - rivedere tipi di ritorno metodi
    -attenzione alle return messe solo per evitare errori

 */

public class Customer extends User{

    private ArrayList<Booking> bookings;

    public Customer(String par_username, String par_password){
        super(par_username, par_password);
        this.bookings = new ArrayList<Booking>(0);
    }

    private void book_flight(){};
    //private Booking search_booking(){};   è commentata perchè non sappiamo ancora come implementarla e darebbe errore di compilazione
    private void update_boooking(){};
    private BookingStatus check_booking_status(){
        return BookingStatus.pending;
    };
    /*private Passenger search_passenger(){
        return new Passenger();
    };
    private Luggage search_luggage(){
        return new Luggage();
    };
     */
    private void report_missing_luggage(){};

    public int add_booking(Booking par_booking){
        this.bookings.add(par_booking);
        return 0;
    }

    public int remove_booking(Booking par_booking){
        boolean control = this.bookings.remove(par_booking);
        if(control){
            return 0;
        }else{
            return -1;
        }
    }

}
