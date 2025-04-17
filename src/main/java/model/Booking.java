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

    public Booking(Customer par_owner, Flight par_booked_flight){
        this.status = BookingStatus.pending;
        this.owner = par_owner;
        this.booked_flight = par_booked_flight;
        this.passengers = new ArrayList<Passenger>(0);
    }

    public BookingStatus get_status(){
        return this.status;
    }

    public int set_status(BookingStatus par_status){
        this.status = par_status;
        return 0;
    }

    public Customer get_owner(){
        return this.owner;
    }

    public int set_owner(Customer par_owner){
        this.owner = par_owner;
        return 0;
    }

    public Flight get_booked_flight(){
        return this.booked_flight;
    }

    public int set_booked_flight(Flight par_booked_flight){
        this.booked_flight = par_booked_flight;
        return 0;
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
