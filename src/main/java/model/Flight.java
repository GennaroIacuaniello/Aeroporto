package model;
import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 * The type Flight.
 */
public class Flight {

    private final String id;
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

    /**
     * Instantiates a new Flight.
     *
     * @param parId            the par id
     * @param parCompanyName   the par company name
     * @param parDate          the par date
     * @param parDepartureTime the par departure time
     * @param parArrivalTime   the par arrival time
     * @param parMaxSeats      the par max seats
     */
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

    /**
     * Instantiates a new Flight.
     *
     * @param parId            the par id
     * @param parCompanyName   the par company name
     * @param parDate          the par date
     * @param parDepartureTime the par departure time
     * @param parArrivalTime   the par arrival time
     * @param parStatus        the par status
     * @param parMaxSeats      the par max seats
     */
    public Flight(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, FlightStatus parStatus, int parMaxSeats){

        this.id = parId;
        this.companyName = parCompanyName;
        this.date = parDate;
        this.departureTime = parDepartureTime;
        this.arrivalTime = parArrivalTime;
        this.status = parStatus;
        this.maxSeats = parMaxSeats;
        this.freeSeats = this.maxSeats;

    }

    /**
     * Instantiates a new Flight.
     *
     * @param parId            the par id
     * @param parCompanyName   the par company name
     * @param parDate          the par date
     * @param parDepartureTime the par departure time
     * @param parArrivalTime   the par arrival time
     * @param parStatus        the par status
     * @param parMaxSeats      the par max seats
     * @param parFreeSeats     the par free seats
     */
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

    /**
     * Instantiates a new Flight.
     *
     * @param parId            the par id
     * @param parCompanyName   the par company name
     * @param parDate          the par date
     * @param parDepartureTime the par departure time
     * @param parArrivalTime   the par arrival time
     * @param parStatus        the par status
     * @param parMaxSeats      the par max seats
     * @param parFreeSeats     the par free seats
     * @param parGate          the par gate
     */
    public Flight(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, Gate parGate ){

        this.id = parId;
        this.companyName = parCompanyName;
        this.date = parDate;
        this.departureTime = parDepartureTime;
        this.arrivalTime = parArrivalTime;
        this.status = parStatus;
        this.maxSeats = parMaxSeats;
        this.freeSeats = parFreeSeats;
        this.gate = parGate;

    }

    /**
     * Instantiates a new Flight.
     *
     * @param parId            the par id
     * @param parCompanyName   the par company name
     * @param parDate          the par date
     * @param parDepartureTime the par departure time
     * @param parArrivalTime   the par arrival time
     * @param parStatus        the par status
     * @param parMaxSeats      the par max seats
     * @param parFreeSeats     the par free seats
     * @param parBookings      the par bookings
     * @param parTickets       the par tickets
     */
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

    /**
     * Instantiates a new Flight.
     *
     * @param parId            the par id
     * @param parCompanyName   the par company name
     * @param parDate          the par date
     * @param parDepartureTime the par departure time
     * @param parArrivalTime   the par arrival time
     * @param parStatus        the par status
     * @param parMaxSeats      the par max seats
     * @param parFreeSeats     the par free seats
     * @param parBookings      the par bookings
     * @param parTickets       the par tickets
     * @param parGate          the par gate
     */
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

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets company name.
     *
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets company name.
     *
     * @param companyName the company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets departure time.
     *
     * @return the departure time
     */
    public Time getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets departure time.
     *
     * @param departureTime the departure time
     */
    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Gets arrival time.
     *
     * @return the arrival time
     */
    public Time getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets arrival time.
     *
     * @param arrivalTime the arrival time
     */
    public void setArrivalTime(Time arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public FlightStatus getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    /**
     * Gets max seats.
     *
     * @return the max seats
     */
    public int getMaxSeats() {
        return maxSeats;
    }

    /**
     * Sets max seats.
     *
     * @param maxSeats the max seats
     */
    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    /**
     * Gets free seats.
     *
     * @return the free seats
     */
    public int getFreeSeats() {
        return freeSeats;
    }

    /**
     * Sets free seats.
     *
     * @param freeSeats the free seats
     */
    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    /**
     * Gets bookings.
     *
     * @return the bookings
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Sets bookings.
     *
     * @param bookings the bookings
     */
    public void setBookings(List<Booking> bookings) {
        this.bookings = (ArrayList<Booking>) bookings;
    }

    /**
     * Gets tickets.
     *
     * @return the tickets
     */
    public List<Ticket> getTickets() {
        return tickets;
    }

    /**
     * Sets tickets.
     *
     * @param tickets the tickets
     */
    public void setTickets(List<Ticket> tickets) {
        this.tickets = (ArrayList<Ticket>) tickets;
    }

    /**
     * Gets gate.
     *
     * @return the gate
     */
    public Gate getGate() {
        return gate;
    }

    /**
     * Sets gate.
     *
     * @param gate the gate
     */
    public void setGate(Gate gate) {
        this.gate = gate;
    }

    /**
     * Get month name string.
     *
     * @return the string
     */
    public String getMonthName(){

        String[] monthNames = {"Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"};

        return monthNames[this.getDate().getMonth()];
    }

}
