package model;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * The type Departing.
 */
public class Departing extends Flight{

    private String destination;
    private int departureDelay = 0;

    /**
     * Instantiates a new Departing.
     *
     * @param parId            the par id
     * @param parCompanyName   the par company name
     * @param parDate          the par date
     * @param parDepartureTime the par departure time
     * @param parArrivalTime   the par arrival time
     * @param parMaxSeats      the par max seats
     * @param parDestination   the par destination
     */
    public Departing(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, int parMaxSeats, String parDestination){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parMaxSeats);
        this.destination = parDestination;

    }

    /**
     * Instantiates a new Departing.
     *
     * @param parId            the par id
     * @param parCompanyName   the par company name
     * @param parDate          the par date
     * @param parDepartureTime the par departure time
     * @param parArrivalTime   the par arrival time
     * @param parStatus        the par status
     * @param parMaxSeats      the par max seats
     * @param parDestination   the par destination
     */
    public Departing(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                     Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, String parDestination){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats);
        this.destination = parDestination;

    }

    /**
     * Instantiates a new Departing.
     *
     * @param parId            the par id
     * @param parCompanyName   the par company name
     * @param parDate          the par date
     * @param parDepartureTime the par departure time
     * @param parArrivalTime   the par arrival time
     * @param parStatus        the par status
     * @param parMaxSeats      the par max seats
     * @param parFreeSeats     the par free seats
     * @param parDestination   the par destination
     */
    public Departing(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, String parDestination){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats);
        this.destination = parDestination;

    }

    /**
     * Instantiates a new Departing.
     *
     * @param parId             the par id
     * @param parCompanyName    the par company name
     * @param parDate           the par date
     * @param parDepartureTime  the par departure time
     * @param parArrivalTime    the par arrival time
     * @param parStatus         the par status
     * @param parMaxSeats       the par max seats
     * @param parFreeSeats      the par free seats
     * @param parDestination    the par destination
     * @param parDepartureDelay the par departure delay
     */
    public Departing(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, String parDestination, int parDepartureDelay){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats);
        this.destination = parDestination;
        this.departureDelay = parDepartureDelay;

    }

    /**
     * Instantiates a new Departing.
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
     * @param parDestination   the par destination
     */
    public Departing(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats,
                    List<Booking> parBookings, List<Ticket> parTickets, String parDestination){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats, parBookings, parTickets);
        this.destination = parDestination;

    }

    /**
     * Instantiates a new Departing.
     *
     * @param parId             the par id
     * @param parCompanyName    the par company name
     * @param parDate           the par date
     * @param parDepartureTime  the par departure time
     * @param parArrivalTime    the par arrival time
     * @param parStatus         the par status
     * @param parMaxSeats       the par max seats
     * @param parFreeSeats      the par free seats
     * @param parBookings       the par bookings
     * @param parTickets        the par tickets
     * @param parDestination    the par destination
     * @param parDepartureDelay the par departure delay
     */
    public Departing(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats,
                    List<Booking> parBookings, List<Ticket> parTickets, String parDestination, int parDepartureDelay){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats, parBookings, parTickets);
        this.destination = parDestination;
        this.departureDelay = parDepartureDelay;

    }

    /**
     * Instantiates a new Departing.
     *
     * @param parId             the par id
     * @param parCompanyName    the par company name
     * @param parDate           the par date
     * @param parDepartureTime  the par departure time
     * @param parArrivalTime    the par arrival time
     * @param parStatus         the par status
     * @param parMaxSeats       the par max seats
     * @param parFreeSeats      the par free seats
     * @param parBookings       the par bookings
     * @param parTickets        the par tickets
     * @param parGate           the par gate
     * @param parDestination    the par destination
     * @param parDepartureDelay the par departure delay
     */
    public Departing(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats,
                    List<Booking> parBookings, List<Ticket> parTickets, Gate parGate, String parDestination, int parDepartureDelay){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats, parBookings, parTickets, parGate);
        this.destination = parDestination;
        this.departureDelay = parDepartureDelay;

    }

    /**
     * Gets destination.
     *
     * @return the destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets destination.
     *
     * @param destination the destination
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Gets departure delay.
     *
     * @return the departure delay
     */
    public int getDepartureDelay() {
        return departureDelay;
    }

    /**
     * Sets departure delay.
     *
     * @param departureDelay the departure delay
     */
    public void setDepartureDelay(int departureDelay) {
        this.departureDelay = departureDelay;
    }


}
