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

/**
 * Controller class for managing flight operations in the airport management system.
 * <p>
 * This class serves as the specialized controller for flight-related operations within
 * the airport management system. It maintains the current flight session state,
 * manages flight search results, and provides comprehensive methods for accessing flight
 * information, passenger details, and flight management operations.
 * </p>
 * <p>
 * The FlightController is responsible for:
 * </p>
 * <ul>
 *   <li>Managing the currently selected flight session information</li>
 *   <li>Handling flight search results and associated metadata</li>
 *   <li>Providing access to flight details, schedules, and capacity information</li>
 *   <li>Managing passenger information within flight contexts</li>
 *   <li>Coordinating flight operations such as check-in, status updates, and delay management</li>
 *   <li>Supporting both arriving and departing flight types with specialized handling</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Flight
 * @see Arriving
 * @see Departing
 * @see FlightDAOImpl
 * @see Controller
 */
public class FlightController {

    /**
     * The currently active flight session object containing complete flight information
     * including schedules, capacity, status, and associated bookings and tickets.
     */
    private Flight flight;
    
    /**
     * Collection of flight objects returned from customer search operations.
     * Used for displaying available flights and enabling customer selection.
     */
    private ArrayList<Flight> searchResult;
    
    /**
     * Collection of flight objects associated with booking search operations.
     * Maintains flights related to specific booking queries and customer reservations.
     */
    private ArrayList<Flight> searchBookingResult;

    /**
     * Performs comprehensive flight search operations for customer flight selection.
     * <p>
     * This method executes a flight search operation using multiple filtering
     * criteria to find flights matching customer requirements. It interfaces with the
     * database through {@link FlightDAOImpl} to retrieve flight information and processes
     * the results to create appropriate flight objects for customer selection.
     * </p>
     * <p>
     * The search supports flexible filtering criteria, including:
     * </p>
     * <ul>
     *   <li>Departure city - flights leaving from a specific location</li>
     *   <li>Arrival city - flights going to a specific destination</li>
     *   <li>Date range - flights within specified date boundaries</li>
     *   <li>Time range - flights departing within specific time windows</li>
     * </ul>
     *
     * @param departingCity the departure city name for filtering (null for no filter)
     * @param arrivingCity the arrival city name for filtering (null for no filter)
     * @param initialDate the start date for date range filtering (null for no filter)
     * @param finalDate the end date for date range filtering (null for no filter)
     * @param initialTime the start time for time range filtering (null for no filter)
     * @param finalTime the end time for time range filtering (null for no filter)
     * @param ids list to be populated with flight identifiers from search results
     * @param companyNames list to be populated with airline company names
     * @param dates list to be populated with flight dates
     * @param departureTimes list to be populated with departure times
     * @param arrivalTimes list to be populated with arrival times
     * @param delays list to be populated with flight delay information
     * @param status list to be populated with flight status values
     * @param maxSeats list to be populated with maximum seat capacities
     * @param freeSeats list to be populated with available seat counts
     * @param cities list to be populated with destination or origin city names
     * @param searchButton the button component to use as reference for error message display
     */
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
 /**
     * Sets the current flight session using an existing {@link Flight} object.
     *
     * @param flight the {@link Flight} object to set as the current session
     */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    /**
     * Sets the current flight session using a flight from the search results by index.
     *
     * @param index the zero-based index of the flight in the search results to set as active
     */
    public void setFlight(int index) {
        this.flight = searchResult.get(index);
    }

    /**
     * Sets the current flight session using a flight from booking results by flight ID.
     *
     * @param flightId the unique identifier of the flight to set as the active session
     */
    public void setBookingResultSelectedFlight(String flightId) {
        for (Flight value : searchBookingResult) {
            if (value.getId().equals(flightId)){
                this.flight = value;
                break;
            }
        }
    }

    /**
     * Retrieves the currently active flight object.
     *
     * @return the {@link Flight} object for the current session, or null if no flight is active
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * Retrieves the unique identifier of the currently active flight.
     *
     * @return the unique identifier string for the current flight
     */
    public String getId () {
        return flight.getId();
    }

    /**
     * Retrieves the airline company name for the currently active flight.
     *
     * @return the airline company name for the current flight
     */
    public String getCompanyName () {
        return flight.getCompanyName();
    }

    /**
     * Retrieves the airline company name for a specific flight in the search results.
     *
     * @param index the zero-based index of the flight in the search results
     * @return the airline company name for the flight at the specified index
     */
    public String getCompanyName (int index) {
        return searchResult.get(index).getCompanyName();
    }

    /**
     * Retrieves the scheduled departure time of the currently active flight.
     *
     * @return the scheduled departure time of the current flight
     */
    public Time getDepartureTime () {
        return flight.getDepartureTime();
    }

    /**
     * Retrieves the scheduled departure time for a specific flight in the search results.
     *
     * @param index the zero-based index of the flight in the search results
     * @return the scheduled departure time for the flight at the specified index
     */
    public Time getDepartureTime (int index) {
        return searchResult.get(index).getDepartureTime();
    }

    /**
     * Retrieves the scheduled departure time for a flight in booking results by flight ID.
     *
     * @param flightId the unique identifier of the flight to retrieve departure time for
     * @return the scheduled departure time for the flight with the specified ID, or null if not found
     */
    public Time getBookingResultSelectedFlightDepartureTime (String flightId) {
        for (Flight value : searchBookingResult) {
            if (value.getId().equals(flightId))
                return value.getDepartureTime();
        }
        return null;
    }

    /**
     * Retrieves the scheduled arrival time of the currently active flight.
     *
     * @return the scheduled arrival time of the current flight
     */
    public Time getArrivalTime () {
        return flight.getArrivalTime();
    }

    /**
     * Retrieves the scheduled arrival time for a specific flight in the search results.
     *
     * @param index the zero-based index of the flight in the search results
     * @return the scheduled arrival time for the flight at the specified index
     */
    public Time getArrivalTime (int index) {
        return searchResult.get(index).getArrivalTime();
    }

    /**
     * Retrieves the scheduled arrival time for a flight in booking results by flight ID.
     *
     * @param flightId the unique identifier of the flight to retrieve arrival time for
     * @return the scheduled arrival time for the flight with the specified ID, or null if not found
     */
    public Time getBookingResultSelectedFlightArrivalTime (String flightId) {
        for (Flight value : searchBookingResult) {
            if (value.getId().equals(flightId))
                return value.getArrivalTime();
        }
        return null;
    }

    /**
     * Retrieves the maximum passenger capacity of the currently active flight.
     *
     * @return the maximum number of passenger seats on the current flight
     */
    public int getMaxSeats () {
        return flight.getMaxSeats();
    }

    /**
     * Retrieves the number of available seats for the currently active flight.
     *
     * @return the number of available seats on the current flight
     */
    public int getFreeSeats () {
        return flight.getFreeSeats();
    }

    /**
     * Retrieves the current operational status of the active flight.
     *
     * @return the {@link FlightStatus} of the current flight
     */
    public FlightStatus getFlightStatus () {
        return flight.getStatus();
    }

    /**
     * Retrieves the total number of tickets associated with the currently active flight.
     *
     * @return the total number of tickets for the current flight
     */
    public int getTicketsSize () {
        return flight.getTickets().size();
    }

    /**
     * Retrieves the total number of bookings associated with the currently active flight.
     *
     * @return the total number of bookings for the current flight
     */
    public int getBookingsSize () {
        return flight.getBookings().size();
    }

    /**
     * Retrieves the number of tickets within a specific booking for the current flight.
     *
     * @param index the zero-based index of the booking within the flight's booking collection
     * @return the number of tickets in the specified booking
     */
    public int getBookingSize (int index) {
        return flight.getBookings().get(index).getTickets().size();
    }

    /**
     * Retrieves the first name of a passenger within a specific booking.
     *
     * @param bookingIndex the zero-based index of the booking within the flight
     * @param passengerIndex the zero-based index of the passenger within the booking
     * @return the first name of the passenger at the specified indices
     */
    public String getPassengerNameFromBooking (int bookingIndex, int passengerIndex) {
        return flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getPassenger().getFirstName();
    }

    /**
     * Retrieves the last name of a passenger within a specific booking.
     *
     * @param bookingIndex the zero-based index of the booking within the flight
     * @param passengerIndex the zero-based index of the passenger within the booking
     * @return the last name of the passenger at the specified indices
     */
    public String getPassengerSurnameFromBooking (int bookingIndex, int passengerIndex) {
        return flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getPassenger().getLastName();
    }

    /**
     * Retrieves the SSN (Social Security Number) of a passenger within a specific booking.
     *
     * @param bookingIndex the zero-based index of the booking within the flight
     * @param passengerIndex the zero-based index of the passenger within the booking
     * @return the SSN of the passenger at the specified indices
     */
    public String getPassengerCFFromBooking (int bookingIndex, int passengerIndex) {
        return flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getPassenger().getPassengerSSN();
    }

    /**
     * Retrieves the ticket number for a passenger within a specific booking.
     *
     * @param bookingIndex the zero-based index of the booking within the flight
     * @param passengerIndex the zero-based index of the passenger within the booking
     * @return the ticket number for the passenger at the specified indices
     */
    public String getPassengerTicketNumberFromBooking (int bookingIndex, int passengerIndex) {
        return flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getTicketNumber();
    }

    /**
     * Retrieves the seat assignment for a passenger within a specific booking.
     *
     * @param bookingIndex the zero-based index of the booking within the flight
     * @param passengerIndex the zero-based index of the passenger within the booking
     * @return the seat assignment for the passenger, or -1 if no seat is assigned
     */
    public int getPassengerSeatFromBooking (int bookingIndex, int passengerIndex) {
        return flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getSeat();
    }

    /**
     * Retrieves the birthdate of a passenger within a specific booking.
     *
     * @param bookingIndex the zero-based index of the booking within the flight
     * @param passengerIndex the zero-based index of the passenger within the booking
     * @return the birthdate of the passenger at the specified indices
     */
    public Date getPassengerDateFromBooking (int bookingIndex, int passengerIndex) {
        return flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getPassenger().getBirthDate();
    }

    /**
     * Retrieves luggage type indicators for all luggage associated with a passenger within a specific booking.
     *
     * @param bookingIndex the zero-based index of the booking within the flight
     * @param passengerIndex the zero-based index of the passenger within the booking
     * @return list of integers representing luggage types (0 = CARRY_ON, 1 = CHECKED)
     */
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

    /**
     * Retrieves luggage tracking identifiers for all luggage associated with a passenger within a specific booking.
     *
     * @param bookingIndex the zero-based index of the booking within the flight
     * @param passengerIndex the zero-based index of the passenger within the booking
     * @return list of luggage tracking identifiers for the passenger at the specified indices
     */
    public List<String> getPassengerLuggagesTicketsFromBooking(int bookingIndex, int passengerIndex) {

        ArrayList<String> tickets = new ArrayList<>();

        for (Luggage luggage : flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getLuggages()) tickets.add(luggage.getId());

        return tickets;
    }

    /**
     * Retrieves luggage status information for all luggage associated with a passenger within a specific booking.
     *
     * @param bookingIndex the zero-based index of the booking within the flight
     * @param passengerIndex the zero-based index of the passenger within the booking
     * @return list of luggage status strings for the passenger at the specified indices
     */
    public List<String> getPassengerLuggagesStatusFromBooking(int bookingIndex, int passengerIndex) {

        ArrayList<String> status = new ArrayList<>();

        for (Luggage luggage : flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getLuggages()) status.add(luggage.getStatus().name());

        return status;
    }

    /**
     * Retrieves the first name of a passenger at the specified ticket index.
     *
     * @param index the zero-based index of the ticket/passenger within the flight
     * @return the first name of the passenger at the specified index
     */
    public String getPassengerName (int index) {
        return flight.getTickets().get(index).getPassenger().getFirstName();
    }

    /**
     * Retrieves the last name of a passenger at the specified ticket index.
     *
     * @param index the zero-based index of the ticket/passenger within the flight
     * @return the last name of the passenger at the specified index
     */
    public String getPassengerSurname (int index) {
        return flight.getTickets().get(index).getPassenger().getLastName();
    }

    /**
     * Retrieves the SSN (Social Security Number) of a passenger at the specified ticket index.
     *
     * @param index the zero-based index of the ticket/passenger within the flight
     * @return the SSN of the passenger at the specified index
     */
    public String getPassengerCF (int index) {
        return flight.getTickets().get(index).getPassenger().getPassengerSSN();
    }

    /**
     * Retrieves the ticket number for a passenger at the specified ticket index.
     *
     * @param index the zero-based index of the ticket/passenger within the flight
     * @return the unique ticket number for the passenger at the specified index
     */
    public String getPassengerTicketNumber (int index) {
        return flight.getTickets().get(index).getTicketNumber();
    }

    /**
     * Retrieves luggage type indicators for all luggage associated with a passenger at the specified ticket index.
     *
     * @param index the zero-based index of the ticket/passenger within the flight
     * @return list of integers representing luggage types (0 = CARRY_ON, 1 = CHECKED)
     */
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

    /**
     * Retrieves all luggage items associated with a passenger at the specified ticket index.
     *
     * @param index the zero-based index of the ticket/passenger within the flight
     * @return list of {@link Luggage} objects associated with the passenger at the specified index
     */
    public List<Luggage> getPassengerLuggages (int index) {

        return flight.getTickets().get(index).getLuggages();
    }

    /**
     * Retrieves the scheduled date of the currently active flight as a formatted string.
     *
     * @return the scheduled date of the current flight as a formatted string
     */
    public String getDateString () {
        return flight.getDate().toString();
    }

    /**
     * Retrieves the operational status of a flight in booking results as a formatted string.
     *
     * @param flightId the unique identifier of the flight to retrieve status for
     * @return the status of the flight with the specified ID as a formatted string, or null if not found
     */
    public String getBookingResultSelectedFlightStatusString (String flightId) {

        for (Flight value : searchBookingResult) {
            if (value.getId().equals(flightId))
                return value.getStatus().toString();
        }
        return null;
    }

    /**
     * Checks if a specific booking within the current flight has confirmed status.
     *
     * @param index the zero-based index of the booking within the flight's booking collection
     * @return true if the booking at the specified index has CONFIRMED status, false otherwise
     */
    public boolean checkBookingConfirm (int index) {

        return flight.getBookings().get(index).getStatus().equals(BookingStatus.CONFIRMED);
    }

    /**
     * Updates the operational status of the currently active flight.
     *
     * @param flightStatus the new {@link FlightStatus} to set for the current flight
     */
    public void setFlightStatus (FlightStatus flightStatus) {
        this.flight.setStatus(flightStatus);
    }

    /**
     * Checks if a specific passenger is checked in for the current flight.
     *
     * @param index the zero-based index of the ticket/passenger within the flight
     * @return true if the passenger at the specified index is checked in, false otherwise
     */
    public boolean getPassengerCheckedin (int index) {
        return flight.getTickets().get(index).isCheckedIn();
    }

    /**
     * Retrieves the destination or origin city for the currently active flight.
     *
     * @return the destination city for departing flights, or origin city for arriving flights
     */
    public String getCity () {

        if(flight instanceof Arriving)
            return ((Arriving) flight).getOrigin();
        else
            return ((Departing) flight).getDestination();
    }

    /**
     * Retrieves the destination or origin city for a flight in booking results by flight ID.
     *
     * @param flightId the unique identifier of the flight to retrieve city information for
     * @return the destination city for departing flights, or origin city for arriving flights with the specified ID, or null if not found
     */
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

    /**
     * Retrieves the scheduled date for a flight in the search results.
     *
     * @param index the zero-based index of the flight in the search results
     * @return the scheduled date of the flight at the specified index
     */
    public Date getDate (int index){
        return searchResult.get(index).getDate();
    }

    /**
     * Retrieves the scheduled date for a flight in booking results by flight ID.
     *
     * @param flightId the unique identifier of the flight to retrieve date information for
     * @return the scheduled date of the flight with the specified ID, or null if not found
     */
    public Date getBookingResultSelectedFlightDate (String flightId){

        for (Flight value : searchBookingResult) {
            if (value.getId().equals(flightId))
                return value.getDate();
        }

        return null;
    }

    /**
     * Initiates the check-in process for the currently active flight.
     * <p>
     * This method attempts to start the check-in procedure for the current flight
     * by interfacing with the database through {@link FlightDAOImpl}. The check-in
     * process typically involves status validation, timing verification, and
     * system preparation for passenger check-in operations.
     * </p>
     *
     * @return status code indicating check-in initiation success (positive values) or failure (negative values)
     */
    public int startCheckin () {

        try {
            FlightDAOImpl flightDAO = new FlightDAOImpl();

            return flightDAO.startCheckin(flight.getId());

        } catch (SQLException e) {
            return -1;
        }
    }

    /**
     * Processes passenger check-in status updates and returns luggage check-in information.
     * <p>
     * This method handles bulk check-in status updates for passengers, processing
     * two collections of passenger panels representing those who should be checked
     * in and those who should not be checked in. It coordinates with the database
     * to update check-in statuses and retrieves associated luggage check-in information.
     * </p>
     *
     * @param truePassengers collection of {@link PassengerPanel} objects representing passengers to be checked in
     * @param falsePassengers collection of {@link PassengerPanel} objects representing passengers not to be checked in
     * @return nested list structure containing luggage check-in information for processed passengers
     */
    public ArrayList<ArrayList<String>> setCheckins (ArrayList<PassengerPanel> truePassengers, ArrayList<PassengerPanel> falsePassengers) {

        FlightDAOImpl flightDAO = new FlightDAOImpl();

        ArrayList<String> trueTickets = new ArrayList<String>();
        ArrayList<String> falseTickets = new ArrayList<String>();

        for (PassengerPanel passengerPanel : truePassengers) trueTickets.add(passengerPanel.getTicketNumber());
        for (PassengerPanel passengerPanel : falsePassengers) falseTickets.add(passengerPanel.getTicketNumber());


        flightDAO.setCheckins(trueTickets, falseTickets);

        return flightDAO.getLuggagesCheckins(trueTickets);
    }

    /**
     * Determines if a flight in the search results is a departing flight.
     *
     * @param index the zero-based index of the flight in the search results
     * @return true if the flight at the specified index is a departing flight, false if it is an arriving flight
     */
    public boolean getFlightType(int index) {

        return searchResult.get(index) instanceof Departing;

    }

    /**
     * Determines if a flight in booking results is a departing flight by flight ID.
     *
     * @param flightId the unique identifier of the flight to check type for
     * @return true if the flight with the specified ID is a departing flight, false if it is an arriving flight or not found
     */
    public boolean getBookingResultSelectedFlightFlightType(String flightId) {

        for (Flight value : searchBookingResult) {
            if (value.getId().equals(flightId))
                return value instanceof Departing;
        }

        return false;
    }

    /**
     * Retrieves the collection of flight objects from booking search results.
     *
     * @return list of {@link Flight} objects from booking search operations
     */
    public List<Flight> getSearchBookingResult() {
        return searchBookingResult;
    }

    /**
     * Sets the collection of flight objects for booking search results.
     *
     * @param searchBookingResult list of {@link Flight} objects to set as booking search results
     */
    public void setSearchBookingResult( List<Flight> searchBookingResult ) {
        this.searchBookingResult = (ArrayList<Flight>) searchBookingResult;
    }

    /**
     * Retrieves the collection of flight identifiers from booking search results.
     *
     * @return list of flight identifier strings from booking search results
     */
    public List<String> getSearchBookingResultIds() {

        ArrayList<String> flightIds = new ArrayList<>();

        for(Flight f: searchBookingResult){
            flightIds.add(f.getId());
        }

        return flightIds;
    }

    /**
     * Retrieves the collection of flight objects from customer search results.
     *
     * @return list of {@link Flight} objects from customer search operations
     */
    public List<Flight> getSearchResult() {
        return searchResult;
    }

    /**
     * Sets the collection of flight objects for customer search results.
     *
     * @param searchResult list of {@link Flight} objects to set as customer search results
     */
    public void setSearchResult(List<Flight> searchResult) {
        this.searchResult = (ArrayList<Flight>) searchResult;
    }

    /**
     * Retrieves a specific flight from booking search results by its database ID.
     *
     * @param id the database identifier of the flight to retrieve
     * @return the {@link Flight} object with the specified ID, or null if not found
     */
    public Flight getSearchBookingResultFlightById(String id) {

        for (Flight value : searchBookingResult) {

            if (value.getId().equals(id)) {
                return value;
            }

        }
        return null;

    }


    /**
     * Creates and adds a new flight to the airport management system.
     * <p>
     * This method processes the creation of a new flight by handling scheduling
     * information, calculating appropriate timestamps, and interfacing with the
     * database through {@link FlightDAOImpl} to persist the flight data. It supports
     * both arriving and departing flight types based on the flightType parameter.
     * </p>
     *
     * @param flightId unique identifier for the new flight
     * @param companyName airline company name operating the flight
     * @param flightDate scheduled flight date
     * @param departureTime scheduled departure time
     * @param arrivalTime scheduled arrival time (may be next day for overnight flights)
     * @param maxSeats maximum passenger capacity for the flight
     * @param otherCity destination city for departing flights, origin city for arriving flights
     * @param flightType true for departing flights, false for arriving flights
     * @param confirmButton UI button reference for error message display
     * @return true if flight creation was successful, false if database errors occurred
     */
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

    /**
     * Updates the operational status of the currently active flight in the database.
     *
     * @param flightStatus the new flight status as an Object (typically String) to be set
     * @return integer status code indicating operation success or failure
     */
    public int setFlightStatus (Object flightStatus) {

        FlightDAOImpl flightDAO = new FlightDAOImpl();

        return flightDAO.setStatus((String) flightStatus, flight.getId());

    }

    /**
     * Adds delay information to the currently active flight.
     * <p>
     * This method updates the delay information for the current flight by
     * interfacing with the database through {@link FlightDAOImpl}. It includes
     * validation to ensure that delay values are non-negative, throwing a
     * NumberFormatException for invalid input values.
     * </p>
     *
     * @param delay the delay amount in minutes to add to the flight (must be non-negative)
     * @return integer status code indicating operation success or failure
     * @throws NumberFormatException if the delay value is negative
     */
    public int addDelay (int delay) throws NumberFormatException {

        if (delay < 0) throw new NumberFormatException();

        FlightDAOImpl flightDAO = new FlightDAOImpl();

        return flightDAO.addDelay(delay, flight.getId());
    }

    /**
     * Retrieves imminent arriving flight information for display purposes.
     * <p>
     * This method queries the database for flights that are scheduled to arrive
     * in the near future and formats the information into a two-dimensional array
     * suitable for table display in user interfaces. It specifically handles
     * arriving flights with comprehensive information including delays and gate assignments.
     * </p>
     * <p>
     * The method performs several operations:
     * </p>
     * <ul>
     *   <li>Database query through {@link FlightDAOImpl} for imminent arrivals</li>
     *   <li>Flight object creation with complete operational information</li>
     *   <li>Data formatting for GUI table display with localized status translation</li>
     *   <li>Delay calculation and time adjustment for accurate arrival predictions</li>
     *   <li>Gate information formatting with "N/A" fallback for unassigned gates</li>
     * </ul>
     *
     * @return two-dimensional Object array containing formatted arriving flight information,
     *         or empty array if database errors occur
     */
    public Object[][] getImminentArrivingFlights(){

        ArrayList<Arriving> arrivingFlights = new ArrayList<>();
        Object[][] result = new Object[6][7];

        ArrayList<String> flightId = new ArrayList<>();
        ArrayList<String> companyName = new ArrayList<>();
        ArrayList<Date> flightDate = new ArrayList<>();
        ArrayList<Time> departureTime = new ArrayList<>();
        ArrayList<Time> arrivalTime = new ArrayList<>();
        ArrayList<String> status = new ArrayList<>();
        ArrayList<Integer> maxSeats = new ArrayList<>();
        ArrayList<Integer> freeSeats = new ArrayList<>();
        ArrayList<String> origin = new ArrayList<>();
        ArrayList<Integer> arrivalDelay = new ArrayList<>();
        ArrayList<Integer> gate = new ArrayList<>();

        try{
            FlightDAOImpl arrivingDao = new FlightDAOImpl();
            arrivingDao.getImminentArrivingFlights(flightId, companyName, flightDate, departureTime, arrivalTime, status,
                    maxSeats, freeSeats, origin, arrivalDelay, gate);
        } catch (SQLException e){
            return new Object[0][0];
        }

        if (!setFlightArray(arrivingFlights, flightId, companyName, flightDate, departureTime, arrivalTime, status,
                maxSeats, freeSeats, origin, arrivalDelay, gate))
            return new Object[0][0];

        for (int i = 0; i < arrivingFlights.size(); i++) {
            result[i][0] = arrivingFlights.get(i).getId();
            result[i][1] = arrivingFlights.get(i).getCompanyName();
            result[i][2] = arrivingFlights.get(i).getDate();
            result[i][3] = arrivingFlights.get(i).getOrigin() + " -> Napoli";
            result[i][4] = arrivingFlights.get(i).getArrivalTime().toLocalTime().plusMinutes(arrivingFlights.get(i).getArrivalDelay());
            result[i][5] = Controller.translateFlightStatus(arrivingFlights.get(i).getStatus());
            if(arrivingFlights.get(i).getGate() != null){
                result[i][6] = arrivingFlights.get(i).getGate().getId();
            }else{
                result[i][6] = "N/A";
            }
        }

        return result;
    }

    /**
     * Retrieves imminent departing flight information for display purposes.
     * <p>
     * This method queries the database for flights that are scheduled to depart
     * in the near future and formats the information into a two-dimensional array
     * suitable for table display in user interfaces. It specifically handles
     * departing flights with comprehensive information including delays and gate assignments.
     * </p>
     * <p>
     * The method performs operations similar to imminent arrivals but focuses on
     * departing flight characteristics:
     * </p>
     * <ul>
     *   <li>Database query through {@link FlightDAOImpl} for imminent departures</li>
     *   <li>Flight object creation using arriving flight objects for data consistency</li>
     *   <li>Route formatting showing "Napoli -> destination" for departure context</li>
     *   <li>Departure time adjustment based on delay information</li>
     *   <li>Status translation and gate assignment formatting</li>
     * </ul>
     *
     * @return two-dimensional Object array containing formatted departing flight information,
     *         or empty array if database errors occur
     */
    public Object[][] getImminentDepartingFlights(){

        ArrayList<Arriving> departingFlights = new ArrayList<>();
        Object[][] result = new Object[6][7];

        ArrayList<String> flightId = new ArrayList<>();
        ArrayList<String> companyName = new ArrayList<>();
        ArrayList<Date> flightDate = new ArrayList<>();
        ArrayList<Time> departureTime = new ArrayList<>();
        ArrayList<Time> arrivalTime = new ArrayList<>();
        ArrayList<String> status = new ArrayList<>();
        ArrayList<Integer> maxSeats = new ArrayList<>();
        ArrayList<Integer> freeSeats = new ArrayList<>();
        ArrayList<String> origin = new ArrayList<>();
        ArrayList<Integer> departingDelay = new ArrayList<>();
        ArrayList<Integer> gate = new ArrayList<>();

        try{
            FlightDAOImpl departingDao = new FlightDAOImpl();
            departingDao.getImminentDepartingFlights(flightId, companyName, flightDate, departureTime, arrivalTime, status,
                    maxSeats, freeSeats, origin, departingDelay, gate);
        } catch (SQLException e){
            return new Object[0][0];
        }

        if (!setFlightArray(departingFlights, flightId, companyName, flightDate, departureTime, arrivalTime, status,
                maxSeats,freeSeats, origin, departingDelay, gate))
            return new Object[0][0];

        for (int i = 0; i < departingFlights.size(); i++) {
            result[i][0] = departingFlights.get(i).getId();
            result[i][1] = departingFlights.get(i).getCompanyName();
            result[i][2] = departingFlights.get(i).getDate();
            result[i][3] = "Napoli -> " + departingFlights.get(i).getOrigin();
            result[i][4] = departingFlights.get(i).getDepartureTime().toLocalTime().plusMinutes(departingFlights.get(i).getArrivalDelay());
            result[i][5] = Controller.translateFlightStatus(departingFlights.get(i).getStatus());
            if(departingFlights.get(i).getGate() != null){
                result[i][6] = departingFlights.get(i).getGate().getId();
            }else{
                result[i][6] = "N/A";
            }
        }

        return result;
    }

    /**
     * Creates flight objects from database query results with comprehensive error handling.
     * <p>
     * This private utility method processes collections of flight data retrieved from
     * database queries and creates appropriate {@link Arriving} flight objects with
     * complete operational information including gate assignments and delay data.
     * </p>
     *
     * @param flights list to be populated with created {@link Arriving} flight objects
     * @param flightId list of flight identifiers from database query
     * @param companyName list of airline company names from database query
     * @param flightDate list of flight dates from database query
     * @param departureTime list of departure times from database query
     * @param arrivalTime list of arrival times from database query
     * @param status list of flight status values from database query
     * @param maxSeats list of maximum seat capacities from database query
     * @param freeSeats list of available seat counts from database query
     * @param origin list of origin/destination cities from database query
     * @param delay list of delay values from database query
     * @param gate list of gate assignments from database query (may contain null values)
     * @return true if all flight objects were created successfully, false if any errors occurred
     */
    private boolean setFlightArray(List<Arriving> flights, List<String> flightId, List<String> companyName,
                                   List<Date> flightDate, List<Time> departureTime, List<Time> arrivalTime,
                                   List<String> status, List<Integer> maxSeats, List<Integer> freeSeats,
                                   List<String> origin, List<Integer> delay, List<Integer> gate) {
        try{
            for (int i = 0; i < flightId.size(); i++) {

                if(gate.get(i) != null){
                    flights.add(new Arriving(flightId.get(i), companyName.get(i), flightDate.get(i), departureTime.get(i),
                            arrivalTime.get(i), FlightStatus.valueOf(status.get(i)), maxSeats.get(i), freeSeats.get(i),
                            origin.get(i), delay.get(i), new Gate(gate.get(i).byteValue())));
                }else{
                    flights.add(new Arriving(flightId.get(i), companyName.get(i), flightDate.get(i), departureTime.get(i),
                            arrivalTime.get(i), FlightStatus.valueOf(status.get(i)), maxSeats.get(i), freeSeats.get(i),
                            origin.get(i), delay.get(i)));
                }

            }
        } catch (Exception e){
            return false;
        }
        return true;
    }
}