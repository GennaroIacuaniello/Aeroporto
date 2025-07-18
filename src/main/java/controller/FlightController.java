package controller;

import dao.FlightDAO;
import gui.FloatingMessage;
import gui.PassengerPanel;
import implementazioniPostgresDAO.FlightDAOImpl;
import model.*;

import javax.swing.*;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

    public void setDepartingFlight(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                                   Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, String parDestination) {

        flight = new Departing(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parMaxSeats, parDestination);

    }

    public void searchFlightCustomer(String departingCity, String arrivingCity, LocalDate initialDate, LocalDate finalDate, LocalTime initialTime, LocalTime finalTime,
                                                  List<String> ids, List<String> companyNames, List<Date> dates, List<Time> departureTimes, List<Time> arrivalTimes,
                                                  List<Integer> delays, List<FlightStatus> status, List<Integer> maxSeats, List<Integer> freeSeats, List<String> cities,
                                                  JButton searchButton){

        try{
            FlightDAO flightDAO = new FlightDAOImpl();

            flightDAO.

        } catch (SQLException e) {
            new FloatingMessage("Errore nella connessione al Database!", searchButton, FloatingMessage.ERROR_MESSAGE);
        }



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

    public int getTicketsSize () {
        return flight.getTickets().size();
    }

    public int getBookingsSize () {
        return flight.getBookings().size();
    }

    public int getBookingSize (int index) {
        return flight.getBookings().get(index).getTickets().size();
    }

    public String getPassengerNameFromBooking (int bookingIndex, int passengerIndex) {
        return flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getPassenger().getFirstName();
    }

    public String getPassengerSurnameFromBooking (int bookingIndex, int passengerIndex) {
        return flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getPassenger().getLastName();
    }

    public String getPassengerCFFromBooking (int bookingIndex, int passengerIndex) {
        return flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getPassenger().getSSN();
    }

    public String getPassengerTicketNumberFromBooking (int bookingIndex, int passengerIndex) {
        return flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getTicketNumber();
    }

    public int getPassengerSeatFromBooking (int bookingIndex, int passengerIndex) {
        return flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getSeat();
    }

    public ArrayList<Integer> getPassengerLuggagesTypesFromBooking(int bookingIndex, int passengerIndex) {

        ArrayList<Integer> types = new ArrayList<Integer>();

        for (Luggage luggage : flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getLuggages()) {
            switch (luggage.getType()) {
                case LuggageType.CARRY_ON -> types.add(0);
                case LuggageType.CHECKED -> types.add(1);
            }
        }

        return types;
    }

    public int getPassengerSeat (int index) {
        return flight.getTickets().get(index).getSeat();
    }

    public String getPassengerName (int index) {
        return flight.getTickets().get(index).getPassenger().getFirstName();
    }

    public String getPassengerSurname (int index) {
        return flight.getTickets().get(index).getPassenger().getLastName();
    }

    public String getPassengerCF (int index) {
        return flight.getTickets().get(index).getPassenger().getSSN();
    }

    public String getPassengerTicketNumber (int index) {
        return flight.getTickets().get(index).getTicketNumber();
    }

    public ArrayList<Integer> getPassengerLuggagesTypes(int index) {

        ArrayList<Integer> types = new ArrayList<Integer>();

        for (Luggage luggage : getPassengerLuggages(index)) {
            switch (luggage.getType()) {
                case LuggageType.CARRY_ON -> types.add(0);
                case LuggageType.CHECKED -> types.add(1);
            }
        }

        return types;
    }

    public ArrayList<Luggage> getPassengerLuggages (int index) {

        return flight.getTickets().get(index).getLuggages();
    }

    public String getDateString () {
        return flight.getDate().toString();
    }

    public String getStatusString () {
        return flight.getStatus().toString();
    }

    public boolean checkBookingConfirm (int index) {

        return flight.getBookings().get(index).getStatus().equals(BookingStatus.CONFIRMED);
    }

    public void setFlightStatus (FlightStatus flightStatus) {
        this.flight.setStatus(flightStatus);
    }

    public boolean getPassengerCheckedin (int index) {
        return flight.getTickets().get(index).isCheckedIn();
    }

    public String getCity () {

        if(flight instanceof Arriving)
            return ((Arriving) flight).getOrigin();
        else
            return ((Departing) flight).getDestination();
    }

    public void startCheckin () {}

    public void setCheckins (ArrayList<PassengerPanel> passengerPanels, JButton callingButton) {}
}
