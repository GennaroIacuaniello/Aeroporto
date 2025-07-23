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
 * The type Flight controller.
 */
public class FlightController {

    private Flight flight;
    private ArrayList<Flight> searchResult;
    private ArrayList<Flight> searchBookingResult;

    /**
     * Sets arriving flight.
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
    public void setArrivingFlight(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                                  Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, String parOrigin, int parArrivalDelay) {

            flight = new Arriving(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats, parOrigin, parArrivalDelay);

    }

    /**
     * Sets departing flight.
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
    public void setDepartingFlight(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                                   Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, String parDestination, int parDepartureDelay) {

        flight = new Departing(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats, parDestination, parDepartureDelay);

    }

    /**
     * Sets departing flight.
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
    public void setDepartingFlight(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                                   Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, String parDestination) {

        flight = new Departing(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parDestination);

    }

    /**
     * Search flight customer.
     *
     * @param departingCity  the departing city
     * @param arrivingCity   the arriving city
     * @param initialDate    the initial date
     * @param finalDate      the final date
     * @param initialTime    the initial time
     * @param finalTime      the final time
     * @param ids            the ids
     * @param companyNames   the company names
     * @param dates          the dates
     * @param departureTimes the departure times
     * @param arrivalTimes   the arrival times
     * @param delays         the delays
     * @param status         the status
     * @param maxSeats       the max seats
     * @param freeSeats      the free seats
     * @param cities         the cities
     * @param searchButton   the search button
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
     * Sets flight.
     *
     * @param flight the flight
     */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    /**
     * Sets flight.
     *
     * @param index the index
     */
    public void setFlight(int index) {

        this.flight = searchResult.get(index);

    }

    /**
     * Sets booking result selected flight.
     *
     * @param flightId the flight id
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
     * Sets booking result selected flight.
     *
     * @param index the index
     */
    public void setBookingResultSelectedFlight(int index) {

        this.flight = searchBookingResult.get(index);

    }

    /**
     * Gets flight.
     *
     * @return the flight
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId () {
        return flight.getId();
    }

    /**
     * Gets company name.
     *
     * @return the company name
     */
    public String getCompanyName () {
        return flight.getCompanyName();
    }

    /**
     * Gets company name.
     *
     * @param index the index
     * @return the company name
     */
    public String getCompanyName (int index) {
        return searchResult.get(index).getCompanyName();
    }

    /**
     * Gets booking result selected flight company name.
     *
     * @param index the index
     * @return the booking result selected flight company name
     */
    public String getBookingResultSelectedFlightCompanyName (int index) {
        return searchBookingResult.get(index).getCompanyName();
    }

    /**
     * Gets flight date.
     *
     * @return the flight date
     */
    public Date getFlightDate () {
        return flight.getDate();
    }

    /**
     * Gets departure time.
     *
     * @return the departure time
     */
    public Time getDepartureTime () {
        return flight.getDepartureTime();
    }

    /**
     * Gets departure time.
     *
     * @param index the index
     * @return the departure time
     */
    public Time getDepartureTime (int index) {
        return searchResult.get(index).getDepartureTime();
    }

    /**
     * Gets booking result selected flight departure time.
     *
     * @param flightId the flight id
     * @return the booking result selected flight departure time
     */
    public Time getBookingResultSelectedFlightDepartureTime (String flightId) {

        for (Flight value : searchBookingResult) {
            if (value.getId().equals(flightId))
                return value.getDepartureTime();
        }
        return null;
    }

    /**
     * Gets arrival time.
     *
     * @return the arrival time
     */
    public Time getArrivalTime () {
        return flight.getArrivalTime();
    }

    /**
     * Gets arrival time.
     *
     * @param index the index
     * @return the arrival time
     */
    public Time getArrivalTime (int index) {
        return searchResult.get(index).getArrivalTime();
    }

    /**
     * Gets booking result selected flight arrival time.
     *
     * @param flightId the flight id
     * @return the booking result selected flight arrival time
     */
    public Time getBookingResultSelectedFlightArrivalTime (String flightId) {

        for (Flight value : searchBookingResult) {
            if (value.getId().equals(flightId))
                return value.getArrivalTime();
        }
        return null;
    }

    /**
     * Gets max seats.
     *
     * @return the max seats
     */
    public int getMaxSeats () {
        return flight.getMaxSeats();
    }

    /**
     * Gets free seats.
     *
     * @return the free seats
     */
    public int getFreeSeats () {
        return flight.getFreeSeats();
    }

    /**
     * Gets flight status.
     *
     * @return the flight status
     */
    public FlightStatus getFlightStatus () {
        return flight.getStatus();
    }

    /**
     * Gets tickets size.
     *
     * @return the tickets size
     */
    public int getTicketsSize () {
        return flight.getTickets().size();
    }

    /**
     * Gets bookings size.
     *
     * @return the bookings size
     */
    public int getBookingsSize () {
        return flight.getBookings().size();
    }

    /**
     * Gets booking size.
     *
     * @param index the index
     * @return the booking size
     */
    public int getBookingSize (int index) {
        return flight.getBookings().get(index).getTickets().size();
    }

    /**
     * Gets passenger name from booking.
     *
     * @param bookingIndex   the booking index
     * @param passengerIndex the passenger index
     * @return the passenger name from booking
     */
    public String getPassengerNameFromBooking (int bookingIndex, int passengerIndex) {
        return flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getPassenger().getFirstName();
    }

    /**
     * Gets passenger surname from booking.
     *
     * @param bookingIndex   the booking index
     * @param passengerIndex the passenger index
     * @return the passenger surname from booking
     */
    public String getPassengerSurnameFromBooking (int bookingIndex, int passengerIndex) {
        return flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getPassenger().getLastName();
    }

    /**
     * Gets passenger cf from booking.
     *
     * @param bookingIndex   the booking index
     * @param passengerIndex the passenger index
     * @return the passenger cf from booking
     */
    public String getPassengerCFFromBooking (int bookingIndex, int passengerIndex) {
        return flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getPassenger().getPassengerSSN();
    }

    /**
     * Gets passenger ticket number from booking.
     *
     * @param bookingIndex   the booking index
     * @param passengerIndex the passenger index
     * @return the passenger ticket number from booking
     */
    public String getPassengerTicketNumberFromBooking (int bookingIndex, int passengerIndex) {
        return flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getTicketNumber();
    }

    /**
     * Gets passenger seat from booking.
     *
     * @param bookingIndex   the booking index
     * @param passengerIndex the passenger index
     * @return the passenger seat from booking
     */
    public int getPassengerSeatFromBooking (int bookingIndex, int passengerIndex) {
        return flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getSeat();
    }

    /**
     * Gets passenger date from booking.
     *
     * @param bookingIndex   the booking index
     * @param passengerIndex the passenger index
     * @return the passenger date from booking
     */
    public Date getPassengerDateFromBooking (int bookingIndex, int passengerIndex) {
        return flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getPassenger().getBirthDate();
    }

    /**
     * Gets passenger luggages types from booking.
     *
     * @param bookingIndex   the booking index
     * @param passengerIndex the passenger index
     * @return the passenger luggages types from booking
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
     * Gets passenger luggages tickets from booking.
     *
     * @param bookingIndex   the booking index
     * @param passengerIndex the passenger index
     * @return the passenger luggages tickets from booking
     */
    public List<String> getPassengerLuggagesTicketsFromBooking(int bookingIndex, int passengerIndex) {

        ArrayList<String> tickets = new ArrayList<>();

        for (Luggage luggage : flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getLuggages()) tickets.add(luggage.getId());

        return tickets;
    }

    /**
     * Gets passenger luggages status from booking.
     *
     * @param bookingIndex   the booking index
     * @param passengerIndex the passenger index
     * @return the passenger luggages status from booking
     */
    public List<String> getPassengerLuggagesStatusFromBooking(int bookingIndex, int passengerIndex) {

        ArrayList<String> status = new ArrayList<>();

        for (Luggage luggage : flight.getBookings().get(bookingIndex).getTickets().get(passengerIndex).getLuggages()) status.add(luggage.getStatus().name());

        return status;
    }

    /**
     * Gets passenger seat.
     *
     * @param index the index
     * @return the passenger seat
     */
    public int getPassengerSeat (int index) {
        return flight.getTickets().get(index).getSeat();
    }

    /**
     * Gets passenger name.
     *
     * @param index the index
     * @return the passenger name
     */
    public String getPassengerName (int index) {
        return flight.getTickets().get(index).getPassenger().getFirstName();
    }

    /**
     * Gets passenger surname.
     *
     * @param index the index
     * @return the passenger surname
     */
    public String getPassengerSurname (int index) {
        return flight.getTickets().get(index).getPassenger().getLastName();
    }

    /**
     * Gets passenger cf.
     *
     * @param index the index
     * @return the passenger cf
     */
    public String getPassengerCF (int index) {
        return flight.getTickets().get(index).getPassenger().getPassengerSSN();
    }

    /**
     * Gets passenger ticket number.
     *
     * @param index the index
     * @return the passenger ticket number
     */
    public String getPassengerTicketNumber (int index) {
        return flight.getTickets().get(index).getTicketNumber();
    }

    /**
     * Gets passenger luggages types.
     *
     * @param index the index
     * @return the passenger luggages types
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
     * Gets passenger luggages.
     *
     * @param index the index
     * @return the passenger luggages
     */
    public List<Luggage> getPassengerLuggages (int index) {

        return flight.getTickets().get(index).getLuggages();
    }

    /**
     * Gets date string.
     *
     * @return the date string
     */
    public String getDateString () {
        return flight.getDate().toString();
    }

    /**
     * Gets status string.
     *
     * @return the status string
     */
    public String getStatusString () {
        return flight.getStatus().toString();
    }

    /**
     * Gets status string.
     *
     * @param index the index
     * @return the status string
     */
    public String getStatusString (int index) {
        return searchResult.get(index).getStatus().toString();
    }

    /**
     * Gets booking result selected flight status string.
     *
     * @param flightId the flight id
     * @return the booking result selected flight status string
     */
    public String getBookingResultSelectedFlightStatusString (String flightId) {

        for (Flight value : searchBookingResult) {
            if (value.getId().equals(flightId))
                return value.getStatus().toString();
        }
        return null;
    }

    /**
     * Check booking confirm boolean.
     *
     * @param index the index
     * @return the boolean
     */
    public boolean checkBookingConfirm (int index) {

        return flight.getBookings().get(index).getStatus().equals(BookingStatus.CONFIRMED);
    }

    /**
     * Sets flight status.
     *
     * @param flightStatus the flight status
     */
    public void setFlightStatus (FlightStatus flightStatus) {
        this.flight.setStatus(flightStatus);
    }

    /**
     * Gets passenger checkedin.
     *
     * @param index the index
     * @return the passenger checkedin
     */
    public boolean getPassengerCheckedin (int index) {
        return flight.getTickets().get(index).isCheckedIn();
    }

    /**
     * Gets city.
     *
     * @return the city
     */
    public String getCity () {

        if(flight instanceof Arriving)
            return ((Arriving) flight).getOrigin();
        else
            return ((Departing) flight).getDestination();
    }

    /**
     * Gets city.
     *
     * @param index the index
     * @return the city
     */
    public String getCity (int index) {

        if(searchResult.get(index) instanceof Arriving)
            return ((Arriving) searchResult.get(index)).getOrigin();
        else
            return ((Departing) searchResult.get(index)).getDestination();
    }

    /**
     * Gets booking result selected flight city.
     *
     * @param flightId the flight id
     * @return the booking result selected flight city
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
     * Get date date.
     *
     * @param index the index
     * @return the date
     */
    public Date getDate (int index){
        return searchResult.get(index).getDate();
    }

    /**
     * Get booking result selected flight date date.
     *
     * @param flightId the flight id
     * @return the date
     */
    public Date getBookingResultSelectedFlightDate (String flightId){

        for (Flight value : searchBookingResult) {
            if (value.getId().equals(flightId))
                return value.getDate();
        }

        return null;
    }

    /**
     * Start checkin int.
     *
     * @return the int
     */
    public int startCheckin () {

        try {
            FlightDAOImpl flightDAO = new FlightDAOImpl();

            return flightDAO.startCheckin(flight.getId());

        } catch (SQLException e) {
            //Controller.getLogger().log(Level.SEVERE, e.getSQLState());
            return -1;
        }
    }


    /**
     * Sets checkins.
     *
     * @param truePassengers  the true passengers
     * @param falsePassengers the false passengers
     * @return the checkins
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
     * Gets flight type.
     *
     * @return the flight type
     */
    public boolean getFlightType() {

        return flight instanceof Departing;

    }

    /**
     * Gets flight type.
     *
     * @param index the index
     * @return the flight type
     */
    public boolean getFlightType(int index) {

        return searchResult.get(index) instanceof Departing;

    }

    /**
     * Gets booking result selected flight flight type.
     *
     * @param flightId the flight id
     * @return the booking result selected flight flight type
     */
    public boolean getBookingResultSelectedFlightFlightType(String flightId) {

        for (Flight value : searchBookingResult) {
            if (value.getId().equals(flightId))
                return value instanceof Departing;
        }

        return false;
    }

    /**
     * Gets search booking result.
     *
     * @return the search booking result
     */
    public List<Flight> getSearchBookingResult() {
        return searchBookingResult;
    }

    /**
     * Sets search booking result.
     *
     * @param searchBookingResult the search booking result
     */
    public void setSearchBookingResult( List<Flight> searchBookingResult ) {
        this.searchBookingResult = (ArrayList<Flight>) searchBookingResult;
    }

    /**
     * Gets search booking result ids.
     *
     * @return the search booking result ids
     */
    public List<String> getSearchBookingResultIds() {

        ArrayList<String> flightIds = new ArrayList<>();

        for(Flight f: searchBookingResult){
            flightIds.add(f.getId());
        }

        return flightIds;
    }

    /**
     * Gets search result.
     *
     * @return the search result
     */
    public List<Flight> getSearchResult() {
        return searchResult;
    }

    /**
     * Sets search result.
     *
     * @param searchResult the search result
     */
    public void setSearchResult(List<Flight> searchResult) {
        this.searchResult = (ArrayList<Flight>) searchResult;
    }

    /**
     * Gets search booking result flight by id.
     *
     * @param id the id
     * @return the search booking result flight by id
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
     * Add flight boolean.
     *
     * @param flightId      the flight id
     * @param companyName   the company name
     * @param flightDate    the flight date
     * @param departureTime the departure time
     * @param arrivalTime   the arrival time
     * @param maxSeats      the max seats
     * @param otherCity     the other city
     * @param flightType    the flight type
     * @param confirmButton the confirm button
     * @return the boolean
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
     * Sets flight status.
     *
     * @param flightStatus the flight status
     * @return the flight status
     */
    public int setFlightStatus (Object flightStatus) {

        FlightDAOImpl flightDAO = new FlightDAOImpl();

        return flightDAO.setStatus((String) flightStatus, flight.getId());

    }

    /**
     * Add delay int.
     *
     * @param delay the delay
     * @return the int
     * @throws NumberFormatException the number format exception
     */
    public int addDelay (int delay) throws NumberFormatException {

        if (delay < 0) throw new NumberFormatException();

        FlightDAOImpl flightDAO = new FlightDAOImpl();

        return flightDAO.addDelay(delay, flight.getId());
    }


    /**
     * Get imminent arriving flights object [ ] [ ].
     *
     * @return the object [ ] [ ]
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
     * Get imminent departing flights object [ ] [ ].
     *
     * @return the object [ ] [ ]
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
