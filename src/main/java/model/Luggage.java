package model;

/*TO DO:
    - rivedere la visibilità di metodi e attributi
    - rivedere tipi di ritorno metodi

 */

/*POSSIBLE
    inserire attributi e relative gestioni per il peso del bagaglio
    private int grammi;     //e poi stampiamo grammi/1000 per stampare il peso in kg
    eventuale nuovo possibile status bagaglio ex. "preso in carico",
    se si modificare lo status assengato nel costruttore
 */

public class Luggage {

    private int id;
    private LuggageType type;
    private LuggageStatus status;
    private Passenger passenger;
    private Flight flight;

    public Luggage(String par_luggage_type, Flight par_flight, Passenger par_passenger,int par_id) {

        this.luggage_type = par_luggage_type;
        this.flight = par_flight;
        this.passenger = par_passenger;
        this.id = par_id;
        this.status = LuggageStatus.booked;
    }

    public Flight get_Flight() {

        return this.flight;
    }

    public Passenger get_Passenger() {

        return this.passenger;
    }

    public int get_Id() {

        return this.id;
    }

    public LuggageStatus get_Status() {

        return this.status;
    }

    public int set_Flight(Flight par_flight) {

        this.flight = par_flight;
        return 0;
    }

    public int set_Passenger(Passenger par_passenger) {

        this.passenger = par_passenger;
        return 0;
    }

    public int set_Id(int par_id) {

        this.id = par_id;
        return 0;
    }

    public int set_Status(LuggageStatus par_status) {

        this.status = par_status;
        return 0;
    }
}
