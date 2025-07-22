package controller;

import dao.FlightDAO;
import gui.FloatingMessage;
import gui.PassengerPanel;
import implementazioni_postgres_dao.FlightDAOImpl;
import model.*;

import javax.swing.*;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class FlightController {

    private Flight flight;
    private ArrayList<Flight> searchResult;
    private ArrayList<Flight> searchBookingResult;

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

        flight = new Departing(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parDestination);

    }

    public void searchFlightCustomer(String departingCity, String arrivingCity, LocalDate initialDate, LocalDate finalDate, LocalTime initialTime, LocalTime finalTime,
                                                  List<String> ids, List<String> companyNames, List<Date> dates, List<Time> departureTimes, List<Time> arrivalTimes,
                                                  List<Integer> delays, List<String> status, List<Integer> maxSeats, List<Integer> freeSeats, List<String> cities,
                                                  JButton searchButton){

        ArrayList<Boolean> types = new ArrayList<>();

        try{
            FlightDAO flightDAO = new FlightDAOImpl();

            flightDAO.searchFlight(departingCity, arrivingCity, initialDate, finalDate, initialTime, finalTime, ids, companyNames,
                                    dates, departureTimes, arrivalTimes, delays, status, maxSeats, freeSeats, cities, types);

            searchResult = new ArrayList<>(0);

        } catch (SQLException e) {
            new FloatingMessage("Errore nella connessione al Database!", searchButton, FloatingMessage.ERROR_MESSAGE);
        }



        for(int i = 0; i < ids.size(); i++){

            boolean type = types.get(i);

            if(type){   //alloco Departing

                searchResult.add(new Departing( ids.get(i), companyNames.get(i), dates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                                                FlightStatus.valueOf(status.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i), delays.get(i)));

            }else{              //alloco Arriving

                searchResult.add(new Arriving( ids.get(i), companyNames.get(i), dates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                                               FlightStatus.valueOf(status.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i), delays.get(i)));


            }

        }

    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public void setFlight(int index) {

        this.flight = searchResult.get(index);

    }

    public void setBookingResultSelectedFlight(String flightId) {

        for (Flight value : searchBookingResult) {
            if (value.getId().equals(flightId)){
                this.flight = value;
                break;
            }

        }

    }

    public void setBookingResultSelectedFlight(int index) {

        this.flight = searchBookingResult.get(index);

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

    public String getCompanyName (int index) {
        return searchResult.get(index).getCompanyName();
    }

    public String getBookingResultSelectedFlightCompanyName (int index) {
        return searchBookingResult.get(index).getCompanyName();
    }

    public Date getFlightDate () {
        return flight.getDate();
    }

    public Time getDepartureTime () {
        return flight.getDepartureTime();
    }

    public Time getDepartureTime (int index) {
        return searchResult.get(index).getDepartureTime();
    }

    public Time getBookingResultSelectedFlightDepartureTime (String flightId) {

        for (Flight value : searchBookingResult) {
            if (value.getId().equals(flightId))
                return value.getDepartureTime();
        }
        return null;
    }

    public Time getArrivalTime () {
        return flight.getArrivalTime();
    }

    public Time getArrivalTime (int index) {
        return searchResult.get(index).getArrivalTime();
    }

    public Time getBookingResultSelectedFlightArrivalTime (String flightId) {

        for (Flight value : searchBookingResult) {
            if (value.getId().equals(flightId))
                return value.getArrivalTime();
        }
        return null;
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
        return flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getPassenger().getPassengerSSN();
    }

    public String getPassengerTicketNumberFromBooking (int bookingIndex, int passengerIndex) {
        return flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getTicketNumber();
    }

    public int getPassengerSeatFromBooking (int bookingIndex, int passengerIndex) {
        return flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getSeat();
    }

    public Date getPassengerDateFromBooking (int bookingIndex, int passengerIndex) {
        return flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getPassenger().getBirthDate();
    }

    public List<Integer> getPassengerLuggagesTypesFromBooking(int bookingIndex, int passengerIndex) {

        ArrayList<Integer> types = new ArrayList<>();

        for (Luggage luggage : flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getLuggages()) {
            switch (luggage.getType()) {
                case LuggageType.CARRY_ON -> types.add(0);
                case LuggageType.CHECKED -> types.add(1);
            }
        }

        return types;
    }

    public List<String> getPassengerLuggagesTicketsFromBooking(int bookingIndex, int passengerIndex) {

        ArrayList<String> tickets = new ArrayList<>();

        for (Luggage luggage : flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getLuggages()) tickets.add(luggage.getId());

        return tickets;
    }

    public List<String> getPassengerLuggagesStatusFromBooking(int bookingIndex, int passengerIndex) {

        ArrayList<String> status = new ArrayList<>();

        for (Luggage luggage : flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getLuggages()) status.add(luggage.getStatus().name());

        return status;
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
        return flight.getTickets().get(index).getPassenger().getPassengerSSN();
    }

    public String getPassengerTicketNumber (int index) {
        return flight.getTickets().get(index).getTicketNumber();
    }

    public List<Integer> getPassengerLuggagesTypes(int index) {

        ArrayList<Integer> types = new ArrayList<>();

        for (Luggage luggage : getPassengerLuggages(index)) {
            switch (luggage.getType()) {
                case LuggageType.CARRY_ON -> types.add(0);
                case LuggageType.CHECKED -> types.add(1);
            }
        }

        return types;
    }

    public List<Luggage> getPassengerLuggages (int index) {

        return flight.getTickets().get(index).getLuggages();
    }

    public String getDateString () {
        return flight.getDate().toString();
    }

    public String getStatusString () {
        return flight.getStatus().toString();
    }

    public String getStatusString (int index) {
        return searchResult.get(index).getStatus().toString();
    }

    public String getBookingResultSelectedFlightStatusString (String flightId) {

        for (Flight value : searchBookingResult) {
            if (value.getId().equals(flightId))
                return value.getStatus().toString();
        }
        return null;
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

    public String getCity (int index) {

        if(searchResult.get(index) instanceof Arriving)
            return ((Arriving) searchResult.get(index)).getOrigin();
        else
            return ((Departing) searchResult.get(index)).getDestination();
    }

    public String getBookingResultSelectedFlightCity (String flightId) {

        for (Flight value : searchBookingResult) {
            if (value.getId().equals(flightId))
                if (value instanceof Arriving) {
                    return ((Arriving) value).getOrigin();
                } else {
                    return ((Departing) value).getDestination();
                }
        }
        return null;
    }

    public Date getDate (int index){
        return searchResult.get(index).getDate();
    }

    public Date getBookingResultSelectedFlightDate (String flightId){

        for (Flight value : searchBookingResult) {
            if (value.getId().equals(flightId))
                return value.getDate();
        }

        return null;
    }

    public int startCheckin () {

        try {
            FlightDAOImpl flightDAO = new FlightDAOImpl();

            return flightDAO.startCheckin(flight.getId());

        } catch (SQLException e) {
            //Controller.getLogger().log(Level.SEVERE, e.getSQLState());
            return -1;
        }
    }


    public void setCheckins (ArrayList<PassengerPanel> truePassengers, ArrayList<PassengerPanel> falsePassengers) {

        FlightDAOImpl flightDAO = new FlightDAOImpl();

        ArrayList<String> trueTickets = new ArrayList<String>();
        ArrayList<String> falseTickets = new ArrayList<String>();

        for (PassengerPanel passengerPanel : truePassengers) trueTickets.add(passengerPanel.getTicketNumber());
        for (PassengerPanel passengerPanel : falsePassengers) falseTickets.add(passengerPanel.getTicketNumber());


        flightDAO.setCheckins(trueTickets, falseTickets);
    }

    public boolean getFlightType() {

        return flight instanceof Departing;

    }

    public boolean getFlightType(int index) {

        return searchResult.get(index) instanceof Departing;

    }

    public boolean getBookingResultSelectedFlightFlightType(String flightId) {

        for (Flight value : searchBookingResult) {
            if (value.getId().equals(flightId))
                return value instanceof Departing;
        }

        return false;
    }

    public List<Flight> getSearchBookingResult() {
        return searchBookingResult;
    }

    public void setSearchBookingResult( List<Flight> searchBookingResult ) {
        this.searchBookingResult = (ArrayList<Flight>) searchBookingResult;
    }

    public List<String> getSearchBookingResultIds() {

        ArrayList<String> flightIds = new ArrayList<>();

        for(Flight f: searchBookingResult){
            flightIds.add(f.getId());
        }

        return flightIds;
    }

    public List<Flight> getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(List<Flight> searchResult) {
        this.searchResult = (ArrayList<Flight>) searchResult;
    }

    public Flight getSearchBookingResultFlightById(String id) {

        for (Flight value : searchBookingResult) {

            if (value.getId().equals(id)) {
                return value;
            }

        }
        return null;

    }


    public boolean addFlight(String flightId, String companyName, LocalDate flightDate, LocalTime departureTime, LocalTime arrivalTime, int maxSeats, String otherCity, boolean flightType, JButton confirmButton) {


        Timestamp departureTimestamp;
        Timestamp arrivalTimestamp;

        LocalDate arrivalDate;

        if (arrivalTime.isBefore(departureTime)) {

            arrivalDate = flightDate.plusDays(1);

        } else {

            arrivalDate = flightDate;

        }

        departureTimestamp = Timestamp.valueOf(flightDate.atTime(departureTime));
        arrivalTimestamp = Timestamp.valueOf(arrivalDate.atTime(arrivalTime));


        try{

            FlightDAO flightDAO = new FlightDAOImpl();

            flightDAO.InsertAFlight(flightId, companyName, departureTimestamp, arrivalTimestamp, maxSeats, otherCity, flightType);



        } catch (SQLException e) {

            Controller.getLogger().log(Level.SEVERE, e.getSQLState());
            new FloatingMessage("Errore nella connessione al Database!", confirmButton, FloatingMessage.ERROR_MESSAGE);
            return false;

        }

        return true;
    }
    public int setFlightStatus (Object flightStatus) {

        FlightDAOImpl flightDAO = new FlightDAOImpl();

        return flightDAO.setStatus((String) flightStatus, flight.getId());

    }

    public int addDelay (int delay) throws NumberFormatException {

        if (delay < 0) throw new NumberFormatException();

        FlightDAOImpl flightDAO = new FlightDAOImpl();

        return flightDAO.addDelay(delay, flight.getId());
    }
}
