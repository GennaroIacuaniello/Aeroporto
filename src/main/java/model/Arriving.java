package model;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class Arriving extends Flight{

    private String origin;
    private int arrivalDelay = 0;

    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, int parMaxSeats, String parOrigin){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parMaxSeats);
        this.origin = parOrigin;

    }

    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, String parOrigin){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats);
        this.origin = parOrigin;

    }


    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                     Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, String parOrigin, int parArrivalDelay){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats);
        this.origin = parOrigin;
        this.arrivalDelay = parArrivalDelay;

    }

    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, String parOrigin, int parArrivalDelay, Gate parGate){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats, parGate);
        this.origin = parOrigin;
        this.arrivalDelay = parArrivalDelay;

    }

    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats,
                    List<Booking> parBookings, List<Ticket> parTickets, String parOrigin){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats, parBookings, parTickets);
        this.origin = parOrigin;

    }

    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats,
                  List<Booking> parBookings, List<Ticket> parTickets, String parOrigin, int parArrivalDelay){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats, parBookings, parTickets);
        this.origin = parOrigin;
        this.arrivalDelay = parArrivalDelay;

    }

    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats,
                  List<Booking> parBookings, List<Ticket> parTickets, Gate parGate, String parOrigin, int parArrivalDelay){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats, parBookings, parTickets, parGate);
        this.origin = parOrigin;
        this.arrivalDelay = parArrivalDelay;

    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getArrivalDelay() {
        return arrivalDelay;
    }

    public void setArrivalDelay(int arrivalDelay) {
        this.arrivalDelay = arrivalDelay;
    }


}
