package model;

/*TO DO:
    - rivedere la visibilità di metodi e attributi
    - rivedere tipi di ritorno metodi

 */

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class Arriving extends Flight{

    private String origin;
    private int arrivalDelay;

    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, int parMaxSeats, String parOrigin) {
        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parMaxSeats);
        this.origin = parOrigin;
        this.arrivalDelay = 0;
    }

    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, String parOrigin){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats);

        this.origin = parOrigin;
        this.arrivalDelay = 0;

    }

    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, String parOrigin, int parArrivalDelay){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats);

        this.origin = parOrigin;
        this.arrivalDelay = parArrivalDelay;

    }

    public String get_origin() {
        return this.origin;
    }

    public int set_origin(String par_origin) {
        this.origin = par_origin;
        return 0;
    }

    public int set_arrival_delay(int par_arrival_delay) {
        this.arrivalDelay = par_arrival_delay;
        return 0;
    }

    public int set_arrival_delay(int hours_arrival_delay, int min_arrival_delay) {
        this.arrivalDelay = 60*hours_arrival_delay + min_arrival_delay;
        return 0;
    }

    public int get_arrival_delay() {
        return this.arrivalDelay;
    }

    public void print_arrival_delay(){
        System.out.println("Volo in ritardo di: " + this.arrivalDelay /60 + " ore e "+ this.arrivalDelay %60 + " minuti.");
    }

}
