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

    public Luggage(LuggageType par_type, Passenger par_passenger,int par_id) {
        this.id = par_id;
        this.type = par_type;
        this.status = LuggageStatus.booked;
        this.passenger = par_passenger;
    }



    public int set_Id(int par_id) {

        this.id = par_id;
        return 0;
    }

    public int get_Id() {

        return this.id;
    }

    public int set_type(LuggageType par_type){
        this.type = par_type;
        return 0;
    }

    public LuggageType get_type(){
        return this.type;
    }

    public int set_Status(LuggageStatus par_status) {

        this.status = par_status;
        return 0;
    }
    public LuggageStatus get_Status() {

        return this.status;
    }

    public int set_Passenger(Passenger par_passenger) {

        this.passenger = par_passenger;
        return 0;
    }

    public Passenger get_Passenger() {

        return this.passenger;
    }



}
