package controller;

import model.Flight;
import model.FlightStatus;
import model.Passenger;

import java.util.Date;
import java.util.ArrayList;

public class FlightController {
    private Flight flight;

    public void setFlight(String id, String companyName, Date date, String departureTime,
                          String arrivalTime, int maxSeats) {
        flight = new Flight(id, companyName, date, departureTime, arrivalTime, maxSeats);
    }

    public Flight getFlight() {
        return flight;
    }

    public String getId () {
        return flight.get_id();
    }

    public String getCompanyName () {
        return flight.get_company_name();
    }

    public Date getFlightDate () {
        return flight.get_date();
    }

    public String getDepartureTime () {
        return flight.get_departure_time();
    }

    public String getArrivalTime () {
        return flight.get_arrival_time();
    }

    public int getMaxSeats () {
        return flight.get_max_seats();
    }

    public int getFreeSeats () {
        return flight.get_free_seats();
    }

    public FlightStatus getFlightStatus () {
        return flight.get_status();
    }

    public int getPassengersSize () {
        return flight.get_passengers().size();
    }

    public int getPassengerSeat (int index) {
        return flight.get_passengers().get(index).get_Seat();
    }

    public String getDateString () {
        return flight.get_date().toString();
    }

    public String getStatusString () {
        return flight.get_status().toString();
    }
}
