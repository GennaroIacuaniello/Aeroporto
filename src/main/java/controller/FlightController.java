package controller;

import gui.PassengerPanel;
import model.*;

import javax.swing.*;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class FlightController {

    private Flight flight;
    private ArrayList<Flight> searchResult;

    public void setArrivingFlight(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                                  Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, String parOrigin, int parArrivalDelay) {

            flight = new Arriving(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats, parOrigin, parArrivalDelay);

    }
    public void setDepartingFlight(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                                   Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, String parDestination, int parDepartureDelay) {

        flight = new Departing(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats, parDestination, parDepartureDelay);

    }

    public ArrayList<Flight> searchFlightCustomer(String departingCity, String arrivingCity, Date initialDate, Date finalDate, String initialTime, String finalTime){

        ArrayList<Flight> res = new ArrayList<>(0);

        res.add(new Arriving("01", "Ciao", new Date(6),
                new Time(1), new Time(1), 100, "Dubai"));
        res.add(new Arriving("02", "IO", new Date(7),
                new Time(1), new Time(1), 100, "Dubai"));
        res.add(new Arriving("03", "TU", new Date(8),
                new Time(1), new Time(1), 100, "Dubai"));

        res.add(new Departing("04", "HELLO", new Date(9),
                new Time(1), new Time(1), 100, "Dubai"));
        res.add(new Departing("05", "ME", new Date(10),
                new Time(1), new Time(1), 100, "Dubai"));
        res.add(new Departing("06", "YOU", new Date(11),
                new Time(1), new Time(1), 100, "Dubai"));

        return res;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Flight getFlight() {
        return flight;
    }

    public String getId () {
        return flight.getId();
    }

    public String getCompanyName () {
        return flight.getCompanyName();
    }

    public Date getFlightDate () {
        return flight.getDate();
    }

    public Time getDepartureTime () {
        return flight.getDepartureTime();
    }

    public Time getArrivalTime () {
        return flight.getArrivalTime();
    }

    public int getMaxSeats () {
        return flight.getMaxSeats();
    }

    public int getFreeSeats () {
        return flight.getFreeSeats();
    }

    public FlightStatus getFlightStatus () {
        return flight.getStatus();
    }

    public int getPassengersSize () {
        return flight.getPassengers().size();
    }

    public int getBookingsSize () {
        return flight.getBookings().size();
    }

    public int getBookingSize (int index) {
        return flight.getBookings().get(index).get_passengers().size();
    }

    public String getPassengerNameFromBooking (int bookingIndex, int passengerIndex) {
        return flight.getBookings().get(bookingIndex).get_passengers().get(passengerIndex).get_First_name();
    }

    public String getPassengerSurnameFromBooking (int bookingIndex, int passengerIndex) {
        return flight.getBookings().get(bookingIndex).get_passengers().get(passengerIndex).get_Last_name();
    }

    public String getPassengerCFFromBooking (int bookingIndex, int passengerIndex) {
        return flight.getBookings().get(bookingIndex).get_passengers().get(passengerIndex).get_SSN();
    }

    public String getPassengerTicketNumberFromBooking (int bookingIndex, int passengerIndex) {
        return flight.getBookings().get(bookingIndex).get_passengers().get(passengerIndex).get_Ticket_number();
    }

    public int getPassengerSeatFromBooking (int bookingIndex, int passengerIndex) {
        return flight.getBookings().get(bookingIndex).get_passengers().get(passengerIndex).get_Seat();
    }

    public ArrayList<Integer> getPassengerLuggagesTypesFromBooking(int bookingIndex, int passengerIndex) {

        ArrayList<Integer> types = new ArrayList<Integer>();

        for (Luggage luggage : flight.getBookings().get(bookingIndex).get_passengers().get(passengerIndex).get_Luggages()) {
            switch (luggage.get_type()) {
                case LuggageType.carry_on -> types.add(0);
                case LuggageType.checked -> types.add(1);
            }
        }

        return types;
    }

    public int getPassengerSeat (int index) {
        return flight.getPassengers().get(index).get_Seat();
    }

    public String getPassengerName (int index) {
        return flight.getPassengers().get(index).get_First_name();
    }

    public String getPassengerSurname (int index) {
        return flight.getPassengers().get(index).get_Last_name();
    }

    public String getPassengerCF (int index) {
        return flight.getPassengers().get(index).get_SSN();
    }

    public String getPassengerTicketNumber (int index) {
        return flight.getPassengers().get(index).get_Ticket_number();
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

        return flight.getPassengers().get(index).get_Luggages();
    }

    public String getDateString () {
        return flight.getDate().toString();
    }

    public String getStatusString () {
        return flight.getStatus().toString();
    }

    public boolean checkBookingConfirm (int index) {

        return flight.getBookings().get(index).get_status().equals(BookingStatus.confirmed);
    }

    public void setFlightStatus (FlightStatus flightStatus) {
        this.flight.setStatus(flightStatus);
    }

    public boolean getPassengerCheckedin (int index) {
        return flight.getPassengers().get(index).get_check_in();
    }

    public String getCity () {

        if(flight instanceof Arriving)
            return ((Arriving) flight).get_origin();
        else
            return ((Departing) flight).get_destination();
    }

    public void startCheckin () {}

    public void setCheckins (ArrayList<PassengerPanel> passengerPanels, JButton callingButton) {}
}
