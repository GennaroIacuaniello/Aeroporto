package model;

/*TO DO:
    - rivedere la visibilità di metodi e attributi
    - rivedere tipi di ritorno metodi
    - rivedere tipo delay //attenzione anche nel costruttore
 */

import java.sql.Date;

public class Departing extends Flight{

    private String destination;
    private int departure_delay;
    private Gate gate;

    public Departing(String par_id, String par_company_name, Date par_date, String par_departure_time,
                     String par_arrival_time, int par_max_seats, String destination) {

        super(par_id, par_company_name, destination, par_date, par_departure_time, par_arrival_time, par_max_seats);
        this.destination = destination;
        this.departure_delay = 0;
        this.gate = new Gate((byte)-1);
    }

    public String get_destination() {
        return this.destination;
    }

    public int get_departure_delay() {
        return this.departure_delay;
    }

    public Gate get_gate() {
        return this.gate;
    }

    public int set_destination(String par_destination) {
        this.destination = par_destination;
        return 0;
    }

    public int set_departure_delay(int par_departure_delay) {
        this.departure_delay = par_departure_delay;
        return 0;
    }

    public void print_departure_delay(){
        System.out.println("Volo in ritardo di: " + this.departure_delay/60 + " ore e "+ this.departure_delay%60 + " minuti.");
    }

    public int set_gate(Gate par_gate) {
        this.gate = par_gate;
        return 0;
    }
}
