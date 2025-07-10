package model;

/*TO DO:
    - rivedere la visibilità di metodi e attributi
    - rivedere tipi di ritorno metodi

 */

import java.sql.Date;

public class Arriving extends Flight{

    private String origin;
    private int arrival_delay;

    public Arriving(String par_id, String par_company_name, Date par_date, String par_departure_time,
                    String par_arrival_time, int par_max_seats, String par_origin) {
        super(par_id, par_company_name, par_origin, par_date, par_departure_time, par_arrival_time, par_max_seats);
        this.origin = par_origin;
        this.arrival_delay = 0;
    }

    public String get_origin() {
        return this.origin;
    }

    public int set_origin(String par_origin) {
        this.origin = par_origin;
        return 0;
    }

    public int set_arrival_delay(int par_arrival_delay) {
        this.arrival_delay = par_arrival_delay;
        return 0;
    }

    public int set_arrival_delay(int hours_arrival_delay, int min_arrival_delay) {
        this.arrival_delay = 60*hours_arrival_delay + min_arrival_delay;
        return 0;
    }

    public int get_arrival_delay() {
        return this.arrival_delay;
    }

    public void print_arrival_delay(){
        System.out.println("Volo in ritardo di: " + this.arrival_delay/60 + " ore e "+ this.arrival_delay%60 + " minuti.");
    }

}
