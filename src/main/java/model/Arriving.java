package model;

/*TO DO:
    - rivedere la visibilità di metodi e attributi
    - rivedere tipi di ritorno metodi

 */

import java.util.Date;

public class Arriving extends Flight{

    private String origin;
    private String arrival_delay;

    public Arriving(String par_id, String par_company_name, Date par_date, String par_departure_time, String par_arrival_time, int par_max_seats, String par_origin) {
        super(par_id, par_company_name, par_date, par_departure_time, par_arrival_time, par_max_seats);
        this.origin = par_origin;
        this.arrival_delay = "";
    }

    public String get_origin() {
        return this.origin;
    }

    public String get_arrival_delay() {
        return this.arrival_delay;
    }

    public int set_origin(String par_origin) {
        this.origin = par_origin;
        return 0;
    }

    public int set_arrival_delay(String par_arrival_delay) {
        this.arrival_delay = par_arrival_delay;
        return 0;
    }
}
