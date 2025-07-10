package model;

/*TO DO:
    - rivedere la visibilità di metodi e attributi
    - rivedere tipi di ritorno metodi
    - rivedere tipo delay //attenzione anche nel costruttore
 */

import java.sql.Date;
import java.sql.Time;

public class Departing extends Flight{

    private String destination;
    private int departureDelay;
    private Gate gate;

    public Departing(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                     Time parArrivalTime, int parMaxSeats, String destination) {

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parMaxSeats);
        this.destination = destination;
        this.departureDelay = 0;
        this.gate = new Gate((byte)-1);
    }

    public Departing(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, String parDestination){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats);

        this.destination = parDestination;
        this.departureDelay = 0;
        this.gate = new Gate((byte)-1);

    }

    public Departing(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, String parDestination, int parDepartureDelay){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats);

        this.destination = parDestination;
        this.departureDelay =  parDepartureDelay;
        this.gate = new Gate((byte)-1);

    }

    public String get_destination() {
        return this.destination;
    }

    public int get_departure_delay() {
        return this.departureDelay;
    }

    public Gate get_gate() {
        return this.gate;
    }

    public int set_destination(String par_destination) {
        this.destination = par_destination;
        return 0;
    }

    public int set_departure_delay(int par_departure_delay) {
        this.departureDelay = par_departure_delay;
        return 0;
    }

    public void print_departure_delay(){
        System.out.println("Volo in ritardo di: " + this.departureDelay /60 + " ore e "+ this.departureDelay %60 + " minuti.");
    }

    public int set_gate(Gate par_gate) {
        this.gate = par_gate;
        return 0;
    }
}
