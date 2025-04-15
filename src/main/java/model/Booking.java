package model;
import java.util.ArrayList;

/*TO DO:
    - rivedere la visibilità di metodi e attributi
    - rivedere tipi di ritorno metodi
    - rivedere lo status nel costruttore

 */



public class Booking {

    private BookingStatus status;
    private Customer owner;
    private Flight booked_flight;
    protected ArrayList<Passenger> passengers;

    public Booking(BookingStatus par_status, Customer par_owner, Flight par_booked_flight){
        this.status = par_status;
        this.owner = par_owner;
        this.booked_flight = par_booked_flight;
        this.passengers = new ArrayList<Passenger>(0);
    }

    public int add_passenger(Passenger par_passenger){

        this.passengers.add(par_passenger);

        int control = this.booked_flight.add_passenger(par_passenger);

        if(control != 0) {
            return -1;
        }

        return -1;


    }

    public int remove_passenger(Passenger par_passenger){
        boolean control = this.passengers.remove(par_passenger);
        if(control) {
            this.booked_flight.remove_passenger(par_passenger);
            return 0;
        }
        else{
            return -1;
        }

    }

}
