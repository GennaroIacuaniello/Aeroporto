package model;
import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
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
    private FlightStatus status = FlightStatus.PROGRAMMED;
    private int maxSeats;
    private int freeSeats;
    private ArrayList<Booking> bookings = new ArrayList<>(0);
    private ArrayList<Ticket> tickets = new ArrayList<>(0);
    private Gate gate = null;

    public Flight(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, int parMaxSeats){

        this.id = parId;
        this.companyName = parCompanyName;
        this.date = parDate;
        this.departureTime = parDepartureTime;
        this.arrivalTime = parArrivalTime;
        this.maxSeats = parMaxSeats;
        this.freeSeats = this.maxSeats;

    }

    public Flight(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats){

        this.id = parId;
        this.companyName = parCompanyName;
        this.date = parDate;
        this.departureTime = parDepartureTime;
        this.arrivalTime = parArrivalTime;
        this.status = parStatus;
        this.maxSeats = parMaxSeats;
        this.freeSeats = parFreeSeats;

    }

    public Flight(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats,
                  List<Booking> parBookings, List<Ticket> parTickets ){

        this.id = parId;
        this.companyName = parCompanyName;
        this.date = parDate;
        this.departureTime = parDepartureTime;
        this.arrivalTime = parArrivalTime;
        this.status = parStatus;
        this.maxSeats = parMaxSeats;
        this.freeSeats = parFreeSeats;
        this.bookings = (ArrayList<Booking>) parBookings;
        this.tickets = (ArrayList<Ticket>) parTickets;

    }

    public Flight(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats,
                  List<Booking> parBookings, List<Ticket> parTickets, Gate parGate ){

        this.id = parId;
        this.companyName = parCompanyName;
        this.date = parDate;
        this.departureTime = parDepartureTime;
        this.arrivalTime = parArrivalTime;
        this.status = parStatus;
        this.maxSeats = parMaxSeats;
        this.freeSeats = parFreeSeats;
        this.bookings = (ArrayList<Booking>) parBookings;
        this.tickets = (ArrayList<Ticket>) parTickets;
        this.gate = parGate;

    }

    public String getId() {
        return id;
    }

    //non si può modificare l'id
    /*
    public void setId(String id) {
        this.id = id;
    }*/

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    public Time getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Time arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    public int getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = (ArrayList<Booking>) bookings;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = (ArrayList<Ticket>) tickets;
    }

    public Gate getGate() {
        return gate;
    }

    public void setGate(Gate gate) {
        this.gate = gate;
    }

    public String getMonthName(){

        String[] monthNames = {"Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"};

        return monthNames[this.getDate().getMonth()];
    }

}
