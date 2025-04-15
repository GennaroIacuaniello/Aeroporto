package model;

/*TO DO:
    - rivedere la visibilità di metodi e attributi
    - rivedere tipi di ritorno metodi
    - rivedere tipo delay //attenzione anche nel costruttore
 */

import java.util.Date;

public class Departing extends Flight{

    private String destination;
    private String departure_delay;
    private Gate gate;

    public Departing(String par_id, String par_company_name, Date par_date, String par_departure_time, String par_arrival_time, int par_max_seats, String destination) {
        super(par_id, par_company_name, par_date, par_departure_time, par_arrival_time, par_max_seats);
        this.destination = destination;
        this.departure_delay = "";
        this.gate = null;
    }

    public String get_destination() {
        return this.destination;
    }

    public String get_departure_delay() {
        return this.departure_delay;
    }

    public Gate get_gate() {
        return this.gate;
    }

    public int set_destination(String par_destination) {
        this.destination = par_destination;
        return 0;
    }

    public int set_departure_delay(String par_departure_delay) {
        this.departure_delay = par_departure_delay;
        return 0;
    }

    public int set_gate(Gate par_gate) {
        this.gate = par_gate;
        return 0;
    }
}
