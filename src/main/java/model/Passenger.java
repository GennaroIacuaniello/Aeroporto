package model;
import java.util.ArrayList;

/*TO DO:
    - rivedere la visibilità di metodi e attributi
    - rivedere tipi di ritorno metodi
    - controllare associazione con Booking
 */

public class Passenger {

    private String first_name;
    private String last_name;
    private String SSN;
    private String ticket_number;
    private String seat;
    private ArrayList<Luggage> luggages;

    public Passenger(String first_name, String last_name, String SSN, String ticket_number, String seat) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.SSN = SSN;
        this.ticket_number = ticket_number;
        this.seat = seat;
        this.luggages = new ArrayList<Luggage>(0);
    }

    public String get_First_name() {

        return this.first_name;
    }

    public String get_Last_name() {

        return this.last_name;
    }

    public String get_SSN() {

        return this.SSN;
    }

    public String get_Ticket_number() {

        return this.ticket_number;
    }

    public String get_Seat() {

        return this.seat;
    }

    public int set_First_name(String par_first_name) {

        this.first_name = par_first_name;
        return 0;
    }

    public int set_Last_name(String par_last_name) {

        this.last_name = par_last_name;
        return 0;
    }

    public int set_SSN(String par_SSN) {

        this.SSN = par_SSN;
        return 0;
    }

    public int set_Ticket_number(String par_ticket_number) {

        this.ticket_number = par_ticket_number;
        return 0;
    }

    public int set_Seat(String par_seat) {

        this.seat = par_seat;
        return 0;
    }

    public int add_luggage(Luggage par_luggage){
        this.luggages.add(par_luggage);
        return 0;
    }

    public int remove_luggage(Luggage par_luggage){
        boolean control = this.luggages.remove(par_luggage);
        if(control) {
            return 0;
        }else{
            return -1;
        }
    }
}
