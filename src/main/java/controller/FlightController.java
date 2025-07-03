package controller;

import model.*;

import java.util.Date;
import java.util.ArrayList;

public class FlightController {
    private Flight flight;

    public void setFlight(String id, String companyName, Date date, String departureTime,
                          String arrivalTime, int maxSeats) {
        flight = new Flight(id, companyName, date, departureTime, arrivalTime, maxSeats);
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
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

    public int getBookingsSize () {
        return flight.get_bookings().size();
    }

    public int getBookingSize (int index) {
        return flight.get_bookings().get(index).get_passengers().size();
    }

    public String getPassengerNameFromBooking (int bookingIndex, int passengerIndex) {
        return flight.get_bookings().get(bookingIndex).get_passengers().get(passengerIndex).get_First_name();
    }

    public String getPassengerSurnameFromBooking (int bookingIndex, int passengerIndex) {
        return flight.get_bookings().get(bookingIndex).get_passengers().get(passengerIndex).get_Last_name();
    }

    public String getPassengerCFFromBooking (int bookingIndex, int passengerIndex) {
        return flight.get_bookings().get(bookingIndex).get_passengers().get(passengerIndex).get_SSN();
    }

    public String getPassengerTicketNumberFromBooking (int bookingIndex, int passengerIndex) {
        return flight.get_bookings().get(bookingIndex).get_passengers().get(passengerIndex).get_Ticket_number();
    }

    public int getPassengerSeatFromBooking (int bookingIndex, int passengerIndex) {
        return flight.get_bookings().get(bookingIndex).get_passengers().get(passengerIndex).get_Seat();
    }

    public ArrayList<Integer> getPassengerLuggagesTypesFromBooking(int bookingIndex, int passengerIndex) {

        ArrayList<Integer> types = new ArrayList<Integer>();

        for (Luggage luggage : flight.get_bookings().get(bookingIndex).get_passengers().get(passengerIndex).get_Luggages()) {
            switch (luggage.get_type()) {
                case LuggageType.carry_on -> types.add(0);
                case LuggageType.checked -> types.add(1);
            }
        }

        return types;
    }

    public int getPassengerSeat (int index) {
        return flight.get_passengers().get(index).get_Seat();
    }

    public String getPassengerName (int index) {
        return flight.get_passengers().get(index).get_First_name();
    }

    public String getPassengerSurname (int index) {
        return flight.get_passengers().get(index).get_Last_name();
    }

    public String getPassengerCF (int index) {
        return flight.get_passengers().get(index).get_SSN();
    }

    public String getPassengerTicketNumber (int index) {
        return flight.get_passengers().get(index).get_Ticket_number();
    }

    public ArrayList<Integer> getPassengerLuggagesTypes(int index) {

        ArrayList<Integer> types = new ArrayList<Integer>();

        for (Luggage luggage : getPassengerLuggages(index)) {
            switch (luggage.get_type()) {
                case LuggageType.carry_on -> types.add(0);
                case LuggageType.checked -> types.add(1);
            }
        }

        return types;
    }

    public ArrayList<Luggage> getPassengerLuggages (int index) {

        return flight.get_passengers().get(index).get_Luggages();
    }

    public String getDateString () {
        return flight.get_date().toString();
    }

    public String getStatusString () {
        return flight.get_status().toString();
    }

    public boolean checkBookingConfirm (int index) {

        return flight.get_bookings().get(index).get_status().equals(BookingStatus.confirmed);
    }
}
