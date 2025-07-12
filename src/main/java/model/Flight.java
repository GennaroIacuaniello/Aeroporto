package model;
import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
/*TO DO:
    - rivedere la visibilità di metodi e attributi
    - rivedere tipi di ritorno metodi
    - rivedere add/remove booking e passenger
    - vedere se tenere luggages anche in Flight o lasciarli solo in Passenger
 */

public class Flight {

    private String id;
    private String companyName;
    private Date date;
    private Time departureTime;
    private Time arrivalTime;
    private FlightStatus status;
    private int maxSeats;
    private int freeSeats;
    private ArrayList<Booking> bookings;
    private ArrayList<Passenger> passengers;

    public Flight(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, int parMaxSeats){

        this.id = parId;
        this.companyName = parCompanyName;
        //this.city = parCity;
        this.date = parDate;
        this.departureTime = parDepartureTime;
        this.arrivalTime = parArrivalTime;
        this.status = FlightStatus.programmed;
        this.maxSeats = parMaxSeats;
        this.freeSeats = this.maxSeats;
        this.bookings = new ArrayList<Booking>(0);
        this.passengers = new ArrayList<Passenger>(0);
    }

    public Flight(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats){

        this.id = parId;
        this.companyName = parCompanyName;
        //this.city = parCity;
        this.date = parDate;
        this.departureTime = parDepartureTime;
        this.arrivalTime = parArrivalTime;
        this.status = parStatus;
        this.maxSeats = parMaxSeats;
        this.freeSeats = parFreeSeats;
        this.bookings = new ArrayList<Booking>(0);
        this.passengers = new ArrayList<Passenger>(0);

    }

    public String getId(){
        return this.id;
    }

    public int setId(String parId){
        this.id = parId;
        return 0;
    }

    public String getCompanyName(){
        return this.companyName;
    }

    public int setCompanyName(String parCompanyName){
        this.companyName = parCompanyName;
        return 0;
    }

    /*public String getCity(){
        return this.city;
    }

    public int setCity(String parCity){
        this.city = parCity;
        return 0;
    }*/

    public Date getDate(){
        return this.date;
    }

    public int setDate(Date parDate){
        this.date = parDate;
        return 0;
    }

    public Time getDepartureTime(){
        return this.departureTime;
    }

    public int setDepartureTime(Time parDepartureTime){
        this.departureTime = parDepartureTime;
        return 0;
    }

    public Time getArrivalTime(){
        return this.arrivalTime;
    }

    public int setArrivalTime(Time parArrivalTime) {
        this.arrivalTime = parArrivalTime;
        return 0;
    }

    public int getMaxSeats(){
        return this.maxSeats;
    }

    public int setMaxSeats(int parMaxSeats){
        this.maxSeats = parMaxSeats;
        return 0;
    }

    public int getFreeSeats(){
        return this.freeSeats;
    }

    public FlightStatus getStatus(){
        return this.status;
    }

    public int setStatus(FlightStatus parStatus){
        this.status = parStatus;
        return 0;
    }

    public void rollBackAddBooking(int count){
        for(int i=0; i < count; i++) {
            this.passengers.removeLast();
        }
        this.bookings.removeLast();
    }

    public int addBooking(Booking parBooking){
        this.bookings.add(parBooking);
        int control = 0, count = 0;
        for(Passenger x : parBooking.get_passengers()){

            control = this.addPassenger(x);
            if(control != 0){
                rollBackAddBooking(count);
                return control;
            }
            count++;
        }
        return 0;
    }

    public int addPassenger(Passenger parPassenger){
        if(this.freeSeats > 0){
            this.freeSeats--;
            this.passengers.add(parPassenger);
            return 0;
        }

        return -1;
    }

    public int removeBooking(Booking parBooking){
        boolean control = this.bookings.remove(parBooking);
        if(control){
            for(Passenger x : parBooking.passengers){
                this.removePassenger(x);
            }
            return 0;
        }

        return -1;

    }

    public int removePassenger(Passenger parPassenger){
        boolean control = this.passengers.remove(parPassenger);
        if(control) {
            this.freeSeats++;
            return 0;
        }

        return -1;
    }

    public String getMonthName(){

        String[] monthNames = {"Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"};

        return monthNames[this.getDate().getMonth()];
    }

    public ArrayList<Passenger> getPassengers(){

        return this.passengers;
    }

    public ArrayList<Booking> getBookings() {
        return this.bookings;
    }
}
