package model;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * The type Arriving.
 */
public class Arriving extends Flight{

    private String origin;
    private int arrivalDelay = 0;

    /**
     * Instantiates a new Arriving.
     *
     * @param parId            the par id
     * @param parCompanyName   the par company name
     * @param parDate          the par date
     * @param parDepartureTime the par departure time
     * @param parArrivalTime   the par arrival time
     * @param parMaxSeats      the par max seats
     * @param parOrigin        the par origin
     */
    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, int parMaxSeats, String parOrigin){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parMaxSeats);
        this.origin = parOrigin;

    }

    /**
     * Instantiates a new Arriving.
     *
     * @param parId            the par id
     * @param parCompanyName   the par company name
     * @param parDate          the par date
     * @param parDepartureTime the par departure time
     * @param parArrivalTime   the par arrival time
     * @param parStatus        the par status
     * @param parMaxSeats      the par max seats
     * @param parFreeSeats     the par free seats
     * @param parOrigin        the par origin
     */
    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, String parOrigin){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats);
        this.origin = parOrigin;

    }


    /**
     * Instantiates a new Arriving.
     *
     * @param parId            the par id
     * @param parCompanyName   the par company name
     * @param parDate          the par date
     * @param parDepartureTime the par departure time
     * @param parArrivalTime   the par arrival time
     * @param parStatus        the par status
     * @param parMaxSeats      the par max seats
     * @param parFreeSeats     the par free seats
     * @param parOrigin        the par origin
     * @param parArrivalDelay  the par arrival delay
     */
    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                     Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, String parOrigin, int parArrivalDelay){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats);
        this.origin = parOrigin;
        this.arrivalDelay = parArrivalDelay;

    }

    /**
     * Instantiates a new Arriving.
     *
     * @param parId            the par id
     * @param parCompanyName   the par company name
     * @param parDate          the par date
     * @param parDepartureTime the par departure time
     * @param parArrivalTime   the par arrival time
     * @param parStatus        the par status
     * @param parMaxSeats      the par max seats
     * @param parFreeSeats     the par free seats
     * @param parOrigin        the par origin
     * @param parArrivalDelay  the par arrival delay
     * @param parGate          the par gate
     */
    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, String parOrigin, int parArrivalDelay, Gate parGate){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats, parGate);
        this.origin = parOrigin;
        this.arrivalDelay = parArrivalDelay;

    }

    /**
     * Instantiates a new Arriving.
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
     * @param parOrigin        the par origin
     */
    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats,
                    List<Booking> parBookings, List<Ticket> parTickets, String parOrigin){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats, parBookings, parTickets);
        this.origin = parOrigin;

    }

    /**
     * Instantiates a new Arriving.
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
     * @param parOrigin        the par origin
     * @param parArrivalDelay  the par arrival delay
     */
    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats,
                  List<Booking> parBookings, List<Ticket> parTickets, String parOrigin, int parArrivalDelay){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats, parBookings, parTickets);
        this.origin = parOrigin;
        this.arrivalDelay = parArrivalDelay;

    }

    /**
     * Instantiates a new Arriving.
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
     * @param parOrigin        the par origin
     * @param parArrivalDelay  the par arrival delay
     */
    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats,
                  List<Booking> parBookings, List<Ticket> parTickets, Gate parGate, String parOrigin, int parArrivalDelay){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats, parBookings, parTickets, parGate);
        this.origin = parOrigin;
        this.arrivalDelay = parArrivalDelay;

    }

    /**
     * Gets origin.
     *
     * @return the origin
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Sets origin.
     *
     * @param origin the origin
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * Gets arrival delay.
     *
     * @return the arrival delay
     */
    public int getArrivalDelay() {
        return arrivalDelay;
    }

    /**
     * Sets arrival delay.
     *
     * @param arrivalDelay the arrival delay
     */
    public void setArrivalDelay(int arrivalDelay) {
        this.arrivalDelay = arrivalDelay;
    }


}
