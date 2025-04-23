package model;
import java.util.ArrayList;
import java.util.Date;
/*TO DO:
    - rivedere la visibilità di metodi e attributi
    - rivedere tipi di ritorno metodi
    - rivedere add/remove booking e passenger
    - vedere se tenere luggages anche in Flight o lasciarli solo in Passenger
 */

abstract public class Flight {

    protected String id;
    protected String company_name;
    protected Date date;
    protected String departure_time;
    protected String arrival_time;
    protected FlightStatus status;
    protected int max_seats;
    protected int free_seats;
    protected ArrayList<Booking> bookings;
    protected ArrayList<Passenger> passengers;

    public Flight(String par_id, String par_company_name, Date par_date, String par_departure_time, String par_arrival_time, int par_max_seats){

        this.id = par_id;
        this.company_name = par_company_name;
        this.date = par_date;
        this.departure_time = par_departure_time;
        this.arrival_time = par_arrival_time;
        this.status = FlightStatus.programmed;
        this.max_seats = par_max_seats;
        this.free_seats = this.max_seats;
        this.bookings = new ArrayList<Booking>(0);
        this.passengers = new ArrayList<Passenger>(0);
    }

    public String get_id(){
        return this.id;
    }

    public int set_id(String par_id){
        this.id = par_id;
        return 0;
    }

    public String get_company_name(){
        return this.company_name;
    }

    public int set_company_name(String par_company_name){
        this.company_name = par_company_name;
        return 0;
    }

    public Date get_date(){
        return this.date;
    }

    public int set_date(Date par_date){
        this.date = par_date;
        return 0;
    }

    public String get_departure_time(){
        return this.departure_time;
    }

    public int set_departure_time(String par_departure_time){
        this.departure_time = par_departure_time;
        return 0;
    }

    public String get_arrival_time(){
        return this.arrival_time;
    }

    public int set_arrival_time (String par_arrival_time) {
        this.arrival_time = par_arrival_time;
        return 0;
    }

    public int get_max_seats(){
        return this.max_seats;
    }

    public int set_max_seats(int par_max_seats){
        this.max_seats = par_max_seats;
        return 0;
    }

    public int get_free_seats(){
        return this.free_seats;
    }

    public FlightStatus get_status(){
        return this.status;
    }

    public int set_status(FlightStatus par_status){
        this.status = par_status;
        return 0;
    }

    public void roll_back_add_booking(int count){
        for(int i=0; i < count; i++) {
            this.passengers.removeLast();
        }
        this.bookings.removeLast();
    }

    public int add_booking(Booking par_booking){
        this.bookings.add(par_booking);
        int control = 0, count = 0;
        for(Passenger x : par_booking.passengers){

            control = this.add_passenger(x);
            if(control != 0){
                roll_back_add_booking(count);
                return control;
            }
            count++;
        }
        return 0;
    }

    public int add_passenger(Passenger par_passenger){
        if(this.free_seats > 0){
            this.free_seats--;
            this.passengers.add(par_passenger);
            return 0;
        }

        return -1;
    }

    public int remove_booking(Booking par_booking){
        boolean control = this.bookings.remove(par_booking);
        if(control){
            for(Passenger x : par_booking.passengers){
                this.remove_passenger(x);
            }
            return 0;
        }

        return -1;

    }

    public int remove_passenger(Passenger par_passenger){
        boolean control = this.passengers.remove(par_passenger);
        if(control) {
            this.free_seats++;
            return 0;
        }

        return -1;

    }

}
