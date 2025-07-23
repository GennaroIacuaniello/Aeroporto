package controller;

import dao.*;
import gui.DisposableObject;
import gui.FloatingMessage;

import gui.LuggagePanel;
import gui.PassengerPanel;
import implementazioni_postgres_dao.*;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Controller.
 */
public class Controller {

    private final AdminController adminController;
    private final BookingController bookingController;
    private final CustomerController customerController;
    private final FlightController flightController;
    private final GateController gateController;
    private final LuggageController luggageController;
    private final PassengerController passengerController;
    private final UserController userController;
    private final TicketController ticketController;
    private JButton errorButton;
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());

    /**
     * Instantiates a new Controller.
     */
    public Controller() {
        adminController = new AdminController();
        bookingController = new BookingController();
        customerController = new CustomerController();
        flightController = new FlightController();
        gateController = new GateController();
        luggageController = new LuggageController();
        passengerController = new PassengerController();
        userController = new UserController();
        ticketController = new TicketController();
    }

    /**
     * Gets booking controller.
     *
     * @return the booking controller
     */
    public BookingController getBookingController() {
        return bookingController;
    }

    /**
     * Gets customer controller.
     *
     * @return the customer controller
     */
    public CustomerController getCustomerController() {
        return customerController;
    }

    /**
     * Gets flight controller.
     *
     * @return the flight controller
     */
    public FlightController getFlightController() {
        return flightController;
    }

    /**
     * Gets gate controller.
     *
     * @return the gate controller
     */
    public GateController getGateController() {
        return gateController;
    }

    /**
     * Gets luggage controller.
     *
     * @return the luggage controller
     */
    public LuggageController getLuggageController() {
        return luggageController;
    }

    /**
     * Gets user controller.
     *
     * @return the user controller
     */
    public UserController getUserController() {
        return userController;
    }

    /**
     * Go to login.
     *
     * @param callingObjects the calling objects
     */
    public void goToLogin(List<DisposableObject> callingObjects){

        Dimension sourceDimension = callingObjects.getLast().getFrame().getSize();
        Point sourceLocation = callingObjects.getLast().getFrame().getLocation();
        int sourceExtendedState = callingObjects.getLast().getFrame().getExtendedState();

        for (int i = callingObjects.size() - 1; i > 0; i--) {

            callingObjects.get(i).doOnDispose(callingObjects, this);
            callingObjects.getLast().getFrame().dispose();
            callingObjects.removeLast();
        }

        if(sourceExtendedState != Frame.MAXIMIZED_BOTH){ //if frame is maximized size and location are automatic
            callingObjects.getLast().getFrame().setSize(sourceDimension);
            callingObjects.getLast().getFrame().setLocation(sourceLocation);
        }
        callingObjects.getLast().getFrame().setExtendedState(sourceExtendedState);

        callingObjects.getLast().doOnRestore(callingObjects, this);

        callingObjects.getLast().getFrame().setVisible(true);
    }

    /**
     * Go home.
     *
     * @param callingObjects the calling objects
     */
    public void goHome (List<DisposableObject> callingObjects) {

        Dimension sourceDimension = callingObjects.getLast().getFrame().getSize();
        Point sourceLocation = callingObjects.getLast().getFrame().getLocation();
        int sourceExtendedState = callingObjects.getLast().getFrame().getExtendedState();

        for (int i = callingObjects.size() - 1; i > 1; i--) {

            callingObjects.get(i).doOnDispose(callingObjects, this);
            callingObjects.getLast().getFrame().dispose();
            callingObjects.removeLast();
        }

        if(sourceExtendedState != Frame.MAXIMIZED_BOTH){ //if frame is maximized size and location are automatic
            callingObjects.getLast().getFrame().setSize(sourceDimension);
            callingObjects.getLast().getFrame().setLocation(sourceLocation);
        }
        callingObjects.getLast().getFrame().setExtendedState(sourceExtendedState);

        callingObjects.getLast().doOnRestore(callingObjects, this);

        callingObjects.getLast().getFrame().setVisible(true);

    }

    /**
     * Go back.
     *
     * @param callingObjects the calling objects
     */
    public void goBack (List<DisposableObject> callingObjects) {

        Dimension sourceDimension = callingObjects.getLast().getFrame().getSize();
        Point sourceLocation = callingObjects.getLast().getFrame().getLocation();
        int sourceExtendedState = callingObjects.getLast().getFrame().getExtendedState();

        callingObjects.getLast().doOnDispose(callingObjects, this);
        callingObjects.getLast().getFrame().dispose();
        callingObjects.removeLast();

        if(sourceExtendedState != Frame.MAXIMIZED_BOTH){ //if frame is maximized size and location are automatic
            callingObjects.getLast().getFrame().setSize(sourceDimension);
            callingObjects.getLast().getFrame().setLocation(sourceLocation);
        }
        callingObjects.getLast().getFrame().setExtendedState(sourceExtendedState);

        callingObjects.getLast().doOnRestore(callingObjects, this);

        callingObjects.getLast().getFrame().setVisible(true);

    }

    /**
     * Log out.
     *
     * @param callingObjects the calling objects
     */
    public void logOut (List<DisposableObject> callingObjects) {

        for (int i = callingObjects.size() - 1; i > 0; i--) {
            callingObjects.getLast().doOnDispose(callingObjects, this);
            callingObjects.getLast().getFrame().dispose();
            callingObjects.removeLast();
        }

        callingObjects.getLast().doOnRestore(callingObjects, this);
        callingObjects.getLast().getFrame().setVisible(true);
    }

    /**
     * Add booking.
     *
     * @param passengerPanels the passenger panels
     * @param bookingStatus   the booking status
     */
    public void addBooking(List<PassengerPanel> passengerPanels, String bookingStatus) {

        try {

            BookingDAOImpl bookingDAO = new BookingDAOImpl();

            ArrayList<String> ticketsNumbers = new ArrayList<>();
            ArrayList<Integer> seats = new ArrayList<>();
            ArrayList<String> firstNames = new ArrayList<>();
            ArrayList<String> lastNames = new ArrayList<>();
            ArrayList<Date> birthDates = new ArrayList<>();
            ArrayList<String> passengerSSNs = new ArrayList<>();
            ArrayList<String> luggagesTypes = new ArrayList<>();
            ArrayList<String> ticketsForLuggagesTypes = new ArrayList<>();

            preparePassengers(passengerPanels, ticketsNumbers, seats, firstNames, lastNames, birthDates, passengerSSNs, luggagesTypes, ticketsForLuggagesTypes);

            bookingDAO.addBooking(getUserController().getLoggedUserId(), flightController.getId(), bookingStatus, ticketsNumbers,
                    seats, firstNames, lastNames, birthDates, passengerSSNs, luggagesTypes, ticketsForLuggagesTypes);

        } catch (SQLException e) {
            Controller.getLogger().log(Level.SEVERE, e.getSQLState());
        }
    }

    /**
     * Translate flight status string.
     *
     * @param status the status
     * @return the string
     */
    public static String translateFlightStatus(FlightStatus status){

        switch (status.toString()){
            case "PROGRAMMED":
                return "In programma";
            case "CANCELLED":
                return "Cancellato";
            case "DELAYED":
                return "In ritardo";
            case "ABOUT_TO_DEPART":
                return "In partenza";
            case "DEPARTED":
                return "Partito";
            case "ABOUT_TO_ARRIVE":
                return "In arrivo";
            case "LANDED":
                return "Atterrato";
            default:
                return null;
        }
    }

    /**
     * Modify booking.
     *
     * @param passengerPanels the passenger panels
     * @param bookingStatus   the booking status
     */
    public void modifyBooking (List<PassengerPanel> passengerPanels, String bookingStatus) {

        try {

            BookingDAOImpl bookingDAO = new BookingDAOImpl();

            ArrayList<String> ticketsNumbers = new ArrayList<>();
            ArrayList<Integer> seats = new ArrayList<>();
            ArrayList<String> firstNames = new ArrayList<>();
            ArrayList<String> lastNames = new ArrayList<>();
            ArrayList<Date> birthDates = new ArrayList<>();
            ArrayList<String> passengerSSNs = new ArrayList<>();
            ArrayList<String> luggagesTypes = new ArrayList<>();
            ArrayList<String> ticketsForLuggagesTypes = new ArrayList<>();

            preparePassengers(passengerPanels, ticketsNumbers, seats, firstNames, lastNames, birthDates, passengerSSNs, luggagesTypes, ticketsForLuggagesTypes);

            bookingDAO.modifyBooking(flightController.getId(), getBookingController().getId(), ticketsNumbers,
                    seats, firstNames, lastNames, birthDates, passengerSSNs, luggagesTypes, ticketsForLuggagesTypes, generateTicketNumber(passengerPanels.size() + 1), bookingStatus);

        } catch (SQLException e) {
            Controller.getLogger().log(Level.SEVERE, e.getSQLState());
        }
    }

    private void preparePassengers (List<PassengerPanel> passengerPanels, List<String> ticketsNumbers, List<Integer> seats, List<String> firstNames, List<String> lastNames,
                                    List<Date> birthDates, List<String> passengerSSNs, List<String> luggagesTypes, List<String> ticketsForLuggagesTypes) {

        int i = 0;

        for (PassengerPanel passengerPanel : passengerPanels) {

            if(passengerPanel.getTicketNumber() == null) ticketsNumbers.add(generateTicketNumber(i++));
            else ticketsNumbers.add(passengerPanel.getTicketNumber());

            seats.add(passengerPanel.getSeat());
            firstNames.add(passengerPanel.getPassengerName());
            lastNames.add(passengerPanel.getPassengerSurname());
            birthDates.add(passengerPanel.getPassengerDate());
            passengerSSNs.add(passengerPanel.getPassengerCF());

            for (LuggagePanel luggagePanel : passengerPanel.getLuggagesPanels()) {

                if (luggagePanel.getComboBox().getSelectedIndex() != 0 && luggagePanel.getComboBox().getSelectedItem() != null){
                        luggagesTypes.add(luggagePanel.getComboBox().getSelectedItem().toString());
                        ticketsForLuggagesTypes.add(ticketsNumbers.getLast());
                    }


            }
        }
    }

    /**
     * Generate ticket number string.
     *
     * @param offset the offset
     * @return the string
     */
    public String generateTicketNumber (int offset) {

        TicketDAOImpl ticketDAO = new TicketDAOImpl();

        return ticketDAO.generateTicketNumber(offset);
    }

    /**
     * Sets error button.
     *
     * @param errorButton the error button
     */
    public void setErrorButton(JButton errorButton) {
        this.errorButton = errorButton;
    }

    /**
     * Gets all books looged customer.
     *
     * @param bookingDates  the booking dates
     * @param bookingStatus the booking status
     * @param flightIds     the flight ids
     * @param searchButton  the search button
     */
    public void getAllBooksLoogedCustomer(List<Date> bookingDates, List<String> bookingStatus, List<String> flightIds, JButton searchButton) {


        ArrayList<String> companyNames = new ArrayList<>();
        ArrayList<Date> dates = new ArrayList<>();
        ArrayList<Time> departureTimes = new ArrayList<>();
        ArrayList<Time> arrivalTimes = new ArrayList<>();
        ArrayList<String> status = new ArrayList<>();
        ArrayList<Integer> maxSeats = new ArrayList<>();
        ArrayList<Integer> freeSeats = new ArrayList<>();
        ArrayList<String> cities = new ArrayList<>();

        ArrayList<Boolean> types = new ArrayList<>();

        ArrayList<Integer> bookingIds = new ArrayList<>();

        try{
            BookingDAO bookingDAO = new BookingDAOImpl();

            bookingDAO.getAllBooksCustomer(getCustomerController().getLoggedCustomerId(), flightIds, companyNames, dates, departureTimes, arrivalTimes, status, maxSeats, freeSeats,
                    cities, types, bookingDates, bookingStatus, bookingIds);


        } catch (SQLException e) {
            new FloatingMessage("Errore nella connessione al Database (Prenotazioni)!", searchButton, FloatingMessage.ERROR_MESSAGE);
        }

        ArrayList<String> actualFlightIds = new ArrayList<>();

        bookingController.setSearchBookingResult(new ArrayList<>());
        bookingController.setSearchBookingResultIds(new ArrayList<>());
        flightController.setSearchBookingResult(new ArrayList<>());

        ticketController.setSearchBookingResult(new ArrayList<>());
        passengerController.setSearchBookingResult(new ArrayList<>());

        ArrayList<String> actualSSNs = new ArrayList<>();

        for(int i = 0; i < flightIds.size(); i++){

            if(!actualFlightIds.contains(flightIds.get(i))){

                actualFlightIds.add(flightIds.get(i));

                if(Boolean.TRUE.equals(types.get(i))){   //alloco Departing

                    flightController.getSearchBookingResult().add(new Departing( flightIds.get(i), companyNames.get(i), dates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                            FlightStatus.valueOf(status.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i)));

                }else{              //alloco Arriving

                    flightController.getSearchBookingResult().add(new Arriving( flightIds.get(i), companyNames.get(i), dates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                            FlightStatus.valueOf(status.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i)));


                }
            }

            ArrayList<String> ticketNumbers = new ArrayList<>();
            ArrayList<Integer> seats = new ArrayList<>();
            ArrayList<Boolean> checkedIns = new ArrayList<>();
            ArrayList<String> passengerSSNs = new ArrayList<>();
            ArrayList<String> firstNames = new ArrayList<>();
            ArrayList<String> lastNames = new ArrayList<>();
            ArrayList<Date> birthDates = new ArrayList<>();

            TicketDAO ticketDao = new TicketDAOImpl();

            try{

                ticketDao.getAllTicketBooking(bookingIds.get(i), ticketNumbers, seats, checkedIns, passengerSSNs, firstNames, lastNames, birthDates);

            } catch (SQLException e) {

                new FloatingMessage("Errore nella connessione al Database (Biglietti)!", searchButton, FloatingMessage.ERROR_MESSAGE);
            }

            try{
                if(!ticketNumbers.isEmpty()) {

                    bookingController.getSearchBookingResult().add(new Booking(BookingStatus.valueOf(bookingStatus.get(i)), bookingDates.get(i),
                            customerController.getLoggedCustomer(), flightController.getSearchBookingResult().getLast(),
                            ticketNumbers.getFirst(), seats.getFirst(), checkedIns.getFirst(),
                            firstNames.getFirst(), lastNames.getFirst(), passengerSSNs.getFirst(), birthDates.getFirst()));
                    bookingController.getSearchBookingResultIds().add(bookingIds.get(i));
                }else{
                    throw new InvalidTicket("");
                }

            }catch (Exception e){
                new FloatingMessage("Errore nella connessione al Database (Biglietti)!", searchButton, FloatingMessage.ERROR_MESSAGE);
            }

            for(int j = 1; j < ticketNumbers.size(); j++){
                try{
                    bookingController.getSearchBookingResult().getLast().getTickets().add(new Ticket(ticketNumbers.get(j), seats.get(j), checkedIns.get(j),
                            flightController.getSearchBookingResult().getLast(), bookingController.getSearchBookingResult().getLast(),
                            firstNames.get(j), lastNames.get(j), passengerSSNs.get(j), birthDates.get(j)));

                }catch (Exception e){
                    new FloatingMessage("Errore nella connessione al Database!", searchButton, FloatingMessage.ERROR_MESSAGE);
                }
            }

            ticketController.getSearchBookingResult().addAll(bookingController.getSearchBookingResult().getLast().getTickets());
            for(Ticket x: bookingController.getSearchBookingResult().getLast().getTickets()){
                if(!actualSSNs.contains(x.getPassenger().getPassengerSSN())){
                    actualSSNs.add(x.getPassenger().getPassengerSSN());
                    passengerController.getSearchBookingResult().add(x.getPassenger());
                }

            }

        }

    }

    /**
     * Search books looged customer filtered flights.
     *
     * @param origin        the origin
     * @param destination   the destination
     * @param dateBefore    the date before
     * @param dateAfter     the date after
     * @param timeBefore    the time before
     * @param timeAfter     the time after
     * @param bookingDates  the booking dates
     * @param bookingStatus the booking status
     * @param flightIds     the flight ids
     * @param searchButton  the search button
     */
    public void searchBooksLoogedCustomerFilteredFlights(String origin, String destination, LocalDate dateBefore, LocalDate dateAfter, LocalTime timeBefore, LocalTime timeAfter,
                                                         List<Date> bookingDates, List<String> bookingStatus, List<String> flightIds, JButton searchButton) {

        ArrayList<String> companyNames = new ArrayList<>();
        ArrayList<Date> dates = new ArrayList<>();
        ArrayList<Time> departureTimes = new ArrayList<>();
        ArrayList<Time> arrivalTimes = new ArrayList<>();
        ArrayList<String> status = new ArrayList<>();
        ArrayList<Integer> maxSeats = new ArrayList<>();
        ArrayList<Integer> freeSeats = new ArrayList<>();
        ArrayList<String> cities = new ArrayList<>();

        ArrayList<Boolean> types = new ArrayList<>();

        ArrayList<Integer> bookingIds = new ArrayList<>();

        try{
            BookingDAO bookingDAO = new BookingDAOImpl();

            bookingDAO.searchBooksCustomerFilteredFlights(origin, destination, dateBefore, dateAfter, timeBefore, timeAfter,
                    getCustomerController().getLoggedCustomerId(), flightIds, companyNames, dates, departureTimes, arrivalTimes, status, maxSeats, freeSeats,
                    cities, types, bookingDates, bookingStatus, bookingIds);


        } catch (SQLException e) {
            new FloatingMessage("Errore nella connessione al Database (Prenotazioni)!", searchButton, FloatingMessage.ERROR_MESSAGE);
        }

        ArrayList<String> actualFlightIds = new ArrayList<>();

        bookingController.setSearchBookingResult(new ArrayList<>());
        bookingController.setSearchBookingResultIds(new ArrayList<>());
        flightController.setSearchBookingResult(new ArrayList<>());

        ticketController.setSearchBookingResult(new ArrayList<>());
        passengerController.setSearchBookingResult(new ArrayList<>());

        ArrayList<String> actualSSNs = new ArrayList<>();

        for(int i = 0; i < flightIds.size(); i++){

            if(!actualFlightIds.contains(flightIds.get(i))){

                actualFlightIds.add(flightIds.get(i));

                if(Boolean.TRUE.equals(types.get(i))){   //alloco Departing

                    flightController.getSearchBookingResult().add(new Departing( flightIds.get(i), companyNames.get(i), dates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                            FlightStatus.valueOf(status.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i)));

                }else{              //alloco Arriving

                    flightController.getSearchBookingResult().add(new Arriving( flightIds.get(i), companyNames.get(i), dates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                            FlightStatus.valueOf(status.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i)));


                }
            }

            ArrayList<String> ticketNumbers = new ArrayList<>();
            ArrayList<Integer> seats = new ArrayList<>();
            ArrayList<Boolean> checkedIns = new ArrayList<>();
            ArrayList<String> passengerSSNs = new ArrayList<>();
            ArrayList<String> firstNames = new ArrayList<>();
            ArrayList<String> lastNames = new ArrayList<>();
            ArrayList<Date> birthDates = new ArrayList<>();

            TicketDAO ticketDao = new TicketDAOImpl();

            try{

                ticketDao.getAllTicketBooking(bookingIds.get(i), ticketNumbers, seats, checkedIns, passengerSSNs, firstNames, lastNames, birthDates);

            } catch (SQLException e) {

                new FloatingMessage("Errore nella connessione al Database (Biglietti)!", searchButton, FloatingMessage.ERROR_MESSAGE);
            }

            try{
                if(!ticketNumbers.isEmpty()) {

                    bookingController.getSearchBookingResult().add(new Booking(BookingStatus.valueOf(bookingStatus.get(i)), bookingDates.get(i),
                            customerController.getLoggedCustomer(), flightController.getSearchBookingResult().getLast(),
                            ticketNumbers.getFirst(), seats.getFirst(), checkedIns.getFirst(),
                            firstNames.getFirst(), lastNames.getFirst(), passengerSSNs.getFirst(), birthDates.getFirst()));
                    bookingController.getSearchBookingResultIds().add(bookingIds.get(i));
                }else{
                    throw new InvalidTicket("");
                }

            }catch (Exception e){
                new FloatingMessage("Errore nella connessione al Database!", searchButton, FloatingMessage.ERROR_MESSAGE);
            }

            for(int j = 1; j < ticketNumbers.size(); j++){
                try{
                    bookingController.getSearchBookingResult().getLast().getTickets().add(new Ticket(ticketNumbers.get(j), seats.get(j), checkedIns.get(j),
                            flightController.getSearchBookingResult().getLast(), bookingController.getSearchBookingResult().getLast(),
                            firstNames.get(j), lastNames.get(j), passengerSSNs.get(j), birthDates.get(j)));

                }catch (Exception e){
                    new FloatingMessage("Errore nella connessione al Database!", searchButton, FloatingMessage.ERROR_MESSAGE);
                }
            }

            ticketController.getSearchBookingResult().addAll(bookingController.getSearchBookingResult().getLast().getTickets());
            for(Ticket x: bookingController.getSearchBookingResult().getLast().getTickets()){
                if(!actualSSNs.contains(x.getPassenger().getPassengerSSN())){
                    actualSSNs.add(x.getPassenger().getPassengerSSN());
                    passengerController.getSearchBookingResult().add(x.getPassenger());
                }

            }

        }


    }

    /**
     * Search books looged customer filtered passengers.
     *
     * @param firstName     the first name
     * @param lastName      the last name
     * @param passengerSSN  the passenger ssn
     * @param ticketNumber  the ticket number
     * @param bookingDates  the booking dates
     * @param bookingStatus the booking status
     * @param flightIds     the flight ids
     * @param searchButton  the search button
     */
    public void searchBooksLoogedCustomerFilteredPassengers(String firstName, String lastName, String passengerSSN, String ticketNumber,
                                                            List<Date> bookingDates, List<String> bookingStatus, List<String> flightIds, JButton searchButton) {

        ArrayList<String> companyNames = new ArrayList<>();
        ArrayList<Date> dates = new ArrayList<>();
        ArrayList<Time> departureTimes = new ArrayList<>();
        ArrayList<Time> arrivalTimes = new ArrayList<>();
        ArrayList<String> status = new ArrayList<>();
        ArrayList<Integer> maxSeats = new ArrayList<>();
        ArrayList<Integer> freeSeats = new ArrayList<>();
        ArrayList<String> cities = new ArrayList<>();

        ArrayList<Boolean> types = new ArrayList<>();

        ArrayList<Integer> bookingIds = new ArrayList<>();

        try{
            BookingDAO bookingDAO = new BookingDAOImpl();

            bookingDAO.searchBooksCustomerFilteredPassengers(firstName, lastName, passengerSSN, ticketNumber,
                    getCustomerController().getLoggedCustomerId(), flightIds, companyNames, dates, departureTimes, arrivalTimes, status, maxSeats, freeSeats,
                    cities, types, bookingDates, bookingStatus, bookingIds);


        } catch (SQLException e) {
            new FloatingMessage("Errore nella connessione al Database (Prenotazioni)!", searchButton, FloatingMessage.ERROR_MESSAGE);
        }

        ArrayList<String> actualFlightIds = new ArrayList<>();

        bookingController.setSearchBookingResult(new ArrayList<>());
        bookingController.setSearchBookingResultIds(new ArrayList<>());
        flightController.setSearchBookingResult(new ArrayList<>());

        ticketController.setSearchBookingResult(new ArrayList<>());
        passengerController.setSearchBookingResult(new ArrayList<>());

        ArrayList<String> actualSSNs = new ArrayList<>();

        for(int i = 0; i < flightIds.size(); i++){

            if(!actualFlightIds.contains(flightIds.get(i))){

                actualFlightIds.add(flightIds.get(i));

                if(Boolean.TRUE.equals(types.get(i))){   //alloco Departing

                    flightController.getSearchBookingResult().add(new Departing( flightIds.get(i), companyNames.get(i), dates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                            FlightStatus.valueOf(status.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i)));

                }else{              //alloco Arriving

                    flightController.getSearchBookingResult().add(new Arriving( flightIds.get(i), companyNames.get(i), dates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                            FlightStatus.valueOf(status.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i)));


                }
            }

            ArrayList<String> ticketNumbers = new ArrayList<>();
            ArrayList<Integer> seats = new ArrayList<>();
            ArrayList<Boolean> checkedIns = new ArrayList<>();
            ArrayList<String> passengerSSNs = new ArrayList<>();
            ArrayList<String> firstNames = new ArrayList<>();
            ArrayList<String> lastNames = new ArrayList<>();
            ArrayList<Date> birthDates = new ArrayList<>();

            TicketDAO ticketDao = new TicketDAOImpl();

            try{

                ticketDao.getAllTicketBooking(bookingIds.get(i), ticketNumbers, seats, checkedIns, passengerSSNs, firstNames, lastNames, birthDates);

            } catch (SQLException e) {

                new FloatingMessage("Errore nella connessione al Database (Biglietti)!", searchButton, FloatingMessage.ERROR_MESSAGE);
            }

            try{
                if(!ticketNumbers.isEmpty()) {

                    bookingController.getSearchBookingResult().add(new Booking(BookingStatus.valueOf(bookingStatus.get(i)), bookingDates.get(i),
                            customerController.getLoggedCustomer(), flightController.getSearchBookingResult().getLast(),
                            ticketNumbers.getFirst(), seats.getFirst(), checkedIns.getFirst(),
                            firstNames.getFirst(), lastNames.getFirst(), passengerSSNs.getFirst(), birthDates.getFirst()));
                    bookingController.getSearchBookingResultIds().add(bookingIds.get(i));
                }else{
                    throw new InvalidTicket("");
                }

            }catch (Exception e){
                new FloatingMessage("Errore nella connessione al Database (Biglietti)!", searchButton, FloatingMessage.ERROR_MESSAGE);
            }

            for(int j = 1; j < ticketNumbers.size(); j++){
                try{
                    bookingController.getSearchBookingResult().getLast().getTickets().add(new Ticket(ticketNumbers.get(j), seats.get(j), checkedIns.get(j),
                            flightController.getSearchBookingResult().getLast(), bookingController.getSearchBookingResult().getLast(),
                            firstNames.get(j), lastNames.get(j), passengerSSNs.get(j), birthDates.get(j)));

                }catch (Exception e){
                    new FloatingMessage("Errore nella connessione al Database!", searchButton, FloatingMessage.ERROR_MESSAGE);
                }
            }

            ticketController.getSearchBookingResult().addAll(bookingController.getSearchBookingResult().getLast().getTickets());
            for(Ticket x: bookingController.getSearchBookingResult().getLast().getTickets()){
                if(!actualSSNs.contains(x.getPassenger().getPassengerSSN())){
                    actualSSNs.add(x.getPassenger().getPassengerSSN());
                    passengerController.getSearchBookingResult().add(x.getPassenger());
                }

            }

        }

    }

    /**
     * Load and check if open my bookings or new booking boolean.
     *
     * @return the boolean
     */
    public boolean loadAndCheckIfOpenMyBookingsOrNewBooking() {

        String flightId = flightController.getId();

        ArrayList<Integer> bookingIds = new ArrayList<>();
        ArrayList<Date> bookingDates = new ArrayList<>();
        ArrayList<String> bookingStatus = new ArrayList<>();

        try{
            BookingDAO bookingDAO = new BookingDAOImpl();

            bookingDAO.searchBooksCustomerForAFlight(flightId, getCustomerController().getLoggedCustomerId(),
                                                        bookingDates, bookingStatus, bookingIds);


        } catch (SQLException e) {
            new FloatingMessage("Errore nella connessione al Database (Prenotazioni)!", errorButton, FloatingMessage.ERROR_MESSAGE);
        }

        flightController.setSearchBookingResult(new ArrayList<>());
        flightController.getSearchBookingResult().add(flightController.getFlight());

        bookingController.setSearchBookingResult(new ArrayList<>());
        bookingController.setSearchBookingResultIds(new ArrayList<>());

        ticketController.setSearchBookingResult(new ArrayList<>());
        passengerController.setSearchBookingResult(new ArrayList<>());

        ArrayList<String> actualSSNs = new ArrayList<>();

        for(int i = 0; i < bookingIds.size(); i++){

            ArrayList<String> ticketNumbers = new ArrayList<>();
            ArrayList<Integer> seats = new ArrayList<>();
            ArrayList<Boolean> checkedIns = new ArrayList<>();
            ArrayList<String> passengerSSNs = new ArrayList<>();
            ArrayList<String> firstNames = new ArrayList<>();
            ArrayList<String> lastNames = new ArrayList<>();
            ArrayList<Date> birthDates = new ArrayList<>();

            TicketDAO ticketDao = new TicketDAOImpl();

            try{

                ticketDao.getAllTicketBooking(bookingIds.get(i), ticketNumbers, seats, checkedIns, passengerSSNs, firstNames, lastNames, birthDates);

            } catch (SQLException e) {

                new FloatingMessage("Errore nella connessione al Database (Biglietti)!", errorButton, FloatingMessage.ERROR_MESSAGE);
            }

            try{
                if(!ticketNumbers.isEmpty()) {

                    bookingController.getSearchBookingResult().add(new Booking(BookingStatus.valueOf(bookingStatus.get(i)), bookingDates.get(i),
                            customerController.getLoggedCustomer(), flightController.getFlight(),
                            ticketNumbers.getFirst(), seats.getFirst(), checkedIns.getFirst(),
                            firstNames.getFirst(), lastNames.getFirst(), passengerSSNs.getFirst(), birthDates.getFirst()));
                    bookingController.getSearchBookingResultIds().add(bookingIds.get(i));
                }else{
                    throw new InvalidTicket("");
                }

            }catch (Exception e){
                new FloatingMessage("Errore nella connessione al Database (Biglietti)!", errorButton, FloatingMessage.ERROR_MESSAGE);
            }

            for(int j = 1; j < ticketNumbers.size(); j++){
                try{
                    bookingController.getSearchBookingResult().getLast().getTickets().add(new Ticket(ticketNumbers.get(j), seats.get(j), checkedIns.get(j),
                            flightController.getFlight(), bookingController.getSearchBookingResult().getLast(),
                            firstNames.get(j), lastNames.get(j), passengerSSNs.get(j), birthDates.get(j)));

                }catch (Exception e){
                    new FloatingMessage("Errore nella connessione al Database!", errorButton, FloatingMessage.ERROR_MESSAGE);
                }
            }

            ticketController.getSearchBookingResult().addAll(bookingController.getSearchBookingResult().getLast().getTickets());
            for(Ticket x: bookingController.getSearchBookingResult().getLast().getTickets()){
                if(!actualSSNs.contains(x.getPassenger().getPassengerSSN())){
                    actualSSNs.add(x.getPassenger().getPassengerSSN());
                    passengerController.getSearchBookingResult().add(x.getPassenger());
                }

            }

        }

        return !bookingIds.isEmpty();

    }

    /**
     * Clear search booking result cache.
     */
    public void clearSearchBookingResultCache() {

        bookingController.setSearchBookingResult(null);
        bookingController.setSearchBookingResultIds(null);
        flightController.setSearchBookingResult(null);

    }

    /**
     * Clear search flights result cache.
     */
    public void clearSearchFlightsResultCache() {

        flightController.setSearchResult(null);

    }

    /**
     * Verify user boolean.
     *
     * @param loggingInfo    the logging info
     * @param hashedPassword the hashed password
     * @param loginButton    the login button
     * @return the boolean
     */
    public boolean verifyUser(String loggingInfo, String hashedPassword, JButton loginButton){

        //Avoid opening DB if it is obvious that it won't contain the user
        if(loggingInfo.contains("@")){
            if (userController.isInvalidMail(loggingInfo)){
                new FloatingMessage("<html>User o mail non valida</html>", loginButton, FloatingMessage.WARNING_MESSAGE);
                return false;
            }
        } else if(userController.isInvalidUsername(loggingInfo)){
            new FloatingMessage("<html>User o mail non valida</html>", loginButton, FloatingMessage.WARNING_MESSAGE);
            return false;
        }

        ArrayList<Integer> userID = new ArrayList<>();
        ArrayList<String> mail = new ArrayList<>();
        ArrayList<String> username = new ArrayList<>();

        try{
            AdminDAOImpl adminDAO = new AdminDAOImpl();
            if(loggingInfo.contains("@")){
                adminDAO.searchUserByMail(userID, username, loggingInfo, hashedPassword);
                adminController.setLoggedAdmin(new Admin(username.getFirst(), loggingInfo, hashedPassword), userID.getFirst());
                userController.setLoggedUser(new Admin(username.getFirst(), loggingInfo, hashedPassword), userID.getFirst());

            }else{
                adminDAO.searchUserByUsername(userID, loggingInfo, mail, hashedPassword);
                adminController.setLoggedAdmin(new Admin(loggingInfo, mail.getFirst(), hashedPassword), userID.getFirst());
                userController.setLoggedUser(new Admin(loggingInfo, mail.getFirst(), hashedPassword), userID.getFirst());
            }
        } catch (UserNotFoundException e){
            try{
                CustomerDAOImpl customerDAO = new CustomerDAOImpl();
                if(loggingInfo.contains("@")){
                    customerDAO.searchUserByMail(userID, username, loggingInfo, hashedPassword);
                    customerController.setLoggedCustomer(new Customer(username.getFirst(), loggingInfo, hashedPassword), userID.getFirst());
                    userController.setLoggedUser(new Customer (username.getFirst(), loggingInfo, hashedPassword), userID.getFirst());

                }else{
                    customerDAO.searchUserByUsername(userID, loggingInfo, mail, hashedPassword);
                    customerController.setLoggedCustomer(new Customer(loggingInfo, mail.getFirst(), hashedPassword), userID.getFirst());
                    userController.setLoggedUser(new Customer (loggingInfo, mail.getFirst(), hashedPassword), userID.getFirst());
                }
            } catch (UserNotFoundException ex){
                new FloatingMessage("<html>User o password errati</html>", loginButton, FloatingMessage.WARNING_MESSAGE);
                return false;
            } catch (SQLException ex){
                new FloatingMessage("<html>Errore nel collegamento al DB(Customer)" + ex.getMessage() + "</html>", loginButton, FloatingMessage.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e){
            new FloatingMessage("<html>Errore nel collegamento al DB(Admin)" + e.getMessage() + "</html>", loginButton, FloatingMessage.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Sets booked seats.
     *
     * @param bookedSeats the booked seats
     */
    public void setBookedSeats (List<Integer> bookedSeats) {
        FlightDAOImpl flightDAO = new FlightDAOImpl();

        flightDAO.getBookedSeats(flightController.getId(), bookingController.getId(), bookedSeats);
    }

    /**
     * Is logged admin boolean.
     *
     * @return the boolean
     */
    public boolean isLoggedAdmin() {

        return userController.getLoggedUser() instanceof Admin;

    }

    /**
     * Gets lost luggages.
     *
     * @param flightIds              the flight ids
     * @param bookingDates           the booking dates
     * @param firstNames             the first names
     * @param lastNames              the last names
     * @param passengerSSNs          the passenger ss ns
     * @param luggageIdsAfterCheckin the luggage ids after checkin
     */
    public void getLostLuggages(List<String> flightIds, List<Date> bookingDates, List<String> firstNames, List<String> lastNames, List<String> passengerSSNs, List<String> luggageIdsAfterCheckin) {

        ArrayList<String> companyNames = new ArrayList<>();
        ArrayList<Date> flightDates = new ArrayList<>();
        ArrayList<Time> departureTimes = new ArrayList<>();
        ArrayList<Time> arrivalTimes = new ArrayList<>();
        ArrayList<String> flightStatus = new ArrayList<>();
        ArrayList<Integer> maxSeats = new ArrayList<>();
        ArrayList<Integer> freeSeats = new ArrayList<>();
        ArrayList<String> cities = new ArrayList<>();

        ArrayList<Boolean> flightTypes = new ArrayList<>();

        ArrayList<Integer> buyerIds = new ArrayList<>();
        ArrayList<String> usernames = new ArrayList<>();
        ArrayList<String> mails = new ArrayList<>();
        ArrayList<String> hashedPasswords = new ArrayList<>();

        ArrayList<Integer> bookingIds = new ArrayList<>();
        ArrayList<String> bookingStatus = new ArrayList<>();

        ArrayList<String> ticketNumbers = new ArrayList<>();
        ArrayList<Integer> seats = new ArrayList<>();
        ArrayList<Boolean> checkedIns = new ArrayList<>();

        ArrayList<Date> birthDates = new ArrayList<>();

        ArrayList<Integer> luggageIds = new ArrayList<>();
        ArrayList<String> luggageTypes = new ArrayList<>();
        ArrayList<String> luggageStatus = new ArrayList<>();

        try{
            LuggageDAO luggageDAO = new LuggageDAOImpl();

            luggageDAO.getAllLostLuggages(flightIds, companyNames, flightDates, departureTimes, arrivalTimes,
                                          flightStatus, maxSeats, freeSeats, cities, flightTypes,
                                          buyerIds, usernames, mails, hashedPasswords,
                                          bookingDates, bookingStatus, bookingIds,
                                          ticketNumbers, seats, checkedIns,
                                          firstNames, lastNames, passengerSSNs, birthDates,
                                          luggageIds, luggageTypes, luggageStatus, luggageIdsAfterCheckin);



        } catch (SQLException e) {
            new FloatingMessage("Errore nella connessione al Database (Bagagli)!", errorButton, FloatingMessage.ERROR_MESSAGE);
        }


        flightController.setSearchBookingResult(new ArrayList<>());

        customerController.setSearchBookingResultCustomers(new ArrayList<>());
        customerController.setSearchBookingResultCustomersIds(new ArrayList<>());

        bookingController.setSearchBookingResult(new ArrayList<>());
        bookingController.setSearchBookingResultIds(new ArrayList<>());


        ticketController.setSearchBookingResult(new ArrayList<>());
        passengerController.setSearchBookingResult(new ArrayList<>());

        luggageController.setSearchBookingResult(new ArrayList<>());
        luggageController.setSearchBookingResultIds(new ArrayList<>());


        ArrayList<String> actualFlightIds = new ArrayList<>();
        ArrayList<Integer> actualBuyersIds = new ArrayList<>();
        ArrayList<Integer> actualBookingIds = new ArrayList<>();
        ArrayList<String> actualTicketNumbers = new ArrayList<>();
        ArrayList<String> actualSSNs = new ArrayList<>();

        try{

            for(int i = 0; i < luggageIds.size(); i++){

                if(!actualFlightIds.contains(flightIds.get(i))){

                    actualFlightIds.add(flightIds.get(i));

                    if(Boolean.TRUE.equals(flightTypes.get(i))){   //alloco Departing

                        flightController.getSearchBookingResult().add(new Departing( flightIds.get(i), companyNames.get(i), flightDates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                                FlightStatus.valueOf(flightStatus.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i)));

                    }else{              //alloco Arriving

                        flightController.getSearchBookingResult().add(new Arriving( flightIds.get(i), companyNames.get(i), flightDates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                                FlightStatus.valueOf(flightStatus.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i)));


                    }
                }

                if(!actualBuyersIds.contains(buyerIds.get(i))){

                    actualBuyersIds.add(buyerIds.get(i));

                    customerController.getSearchBookingResultCustomers().add(new Customer(usernames.get(i), mails.get(i), hashedPasswords.get(i)));
                    customerController.getSearchBookingResultCustomersIds().add(buyerIds.get(i));
                }


                if(!actualBookingIds.contains(bookingIds.get(i))){

                    actualBookingIds.add(bookingIds.get(i));

                    bookingController.getSearchBookingResult().add(new Booking(BookingStatus.valueOf(bookingStatus.get(i)), bookingDates.get(i),
                            customerController.getSearchBookingResultCustomerById(buyerIds.get(i)), flightController.getSearchBookingResultFlightById(flightIds.get(i)),
                            ticketNumbers.get(i), seats.get(i), checkedIns.get(i),
                            firstNames.get(i), lastNames.get(i), passengerSSNs.get(i), birthDates.get(i)));

                    bookingController.getSearchBookingResultIds().add(bookingIds.get(i));

                    actualTicketNumbers.add(ticketNumbers.get(i));
                    ticketController.getSearchBookingResult().add(bookingController.getSearchBookingResult().getLast().getTickets().getLast());
                    Passenger tmp = ticketController.getSearchBookingResult().getLast().getPassenger();
                    if(!actualSSNs.contains(tmp.getPassengerSSN())){
                        actualSSNs.add(tmp.getPassengerSSN());
                        passengerController.getSearchBookingResult().add(tmp);
                    }
                }else{
                    if(!actualTicketNumbers.contains(ticketNumbers.get(i))) {
                        actualTicketNumbers.add(ticketNumbers.get(i));
                        ticketController.getSearchBookingResult().add(new Ticket(ticketNumbers.get(i), seats.get(i), checkedIns.get(i), flightController.getSearchBookingResultFlightById(flightIds.get(i)),
                                bookingController.getSearchBookingResultBooksById(bookingIds.get(i)),
                                firstNames.get(i), lastNames.get(i), passengerSSNs.get(i), birthDates.get(i)));
                        Passenger tmp = ticketController.getSearchBookingResult().getLast().getPassenger();
                        if (!actualSSNs.contains(tmp.getPassengerSSN())) {
                            actualSSNs.add(tmp.getPassengerSSN());
                            passengerController.getSearchBookingResult().add(tmp);
                        }
                    }
                }

                if(luggageTypes.get(i) != null){
                    luggageController.getSearchBookingResult().add(new Luggage(luggageIdsAfterCheckin.get(i), LuggageType.valueOf(luggageTypes.get(i)), LuggageStatus.valueOf(luggageStatus.get(i)),
                            ticketController.getSearchBookingResultTicketByTicketNumber(ticketNumbers.get(i))));
                }else{
                    luggageController.getSearchBookingResult().add(new Luggage(luggageIdsAfterCheckin.get(i), LuggageStatus.valueOf(luggageStatus.get(i)),
                            ticketController.getSearchBookingResultTicketByTicketNumber(ticketNumbers.get(i))));
                }

                ticketController.getSearchBookingResultTicketByTicketNumber(ticketNumbers.get(i)).getLuggages().add(luggageController.getSearchBookingResult().getLast());


            }

            luggageController.getSearchBookingResultIds().addAll(luggageIds);

        }catch (Exception e){
            Controller.getLogger().log(Level.SEVERE, e.getMessage());
            new FloatingMessage("Errore nella connessione al Database (Bagagli smmarriti)!", errorButton, FloatingMessage.ERROR_MESSAGE);

        }


    }

    /**
     * Sets booking result selected flight for lost luaggages.
     *
     * @param luggageIndex the luggage index
     */
    public void setBookingResultSelectedFlightForLostLuaggages(Integer luggageIndex) {

        flightController.setFlight(luggageController.getSearchBookingResult().get(luggageIndex).getTicket().getFlight());

        flightController.getFlight().getBookings().add(luggageController.getSearchBookingResult().get(luggageIndex).getTicket().getBooking());

    }

    /**
     * Gets all for a flight.
     *
     * @param index the index
     */
    public void getAllForAFlight(Integer index) {

        String flightId = flightController.getSearchResult().get(index).getId();

        flightController.setFlight(flightController.getSearchResult().get(index));

        ArrayList<Integer> flightGates = new ArrayList<>();

        ArrayList<Integer> buyerIds = new ArrayList<>();
        ArrayList<String> usernames = new ArrayList<>();
        ArrayList<String> mails = new ArrayList<>();
        ArrayList<String> hashedPasswords = new ArrayList<>();

        ArrayList<Integer> bookingIds = new ArrayList<>();
        ArrayList<String> bookingStatus = new ArrayList<>();
        ArrayList<Date> bookingDates = new ArrayList<>();

        ArrayList<String> ticketNumbers = new ArrayList<>();
        ArrayList<Integer> seats = new ArrayList<>();
        ArrayList<Boolean> checkedIns = new ArrayList<>();

        ArrayList<String> firstNames = new ArrayList<>();
        ArrayList<String> lastNames = new ArrayList<>();
        ArrayList<String> passengerSSNs = new ArrayList<>();
        ArrayList<Date> birthDates = new ArrayList<>();

        ArrayList<Integer> luggageIds = new ArrayList<>();
        ArrayList<String> luggageIdsAfterCheckin = new ArrayList<>();
        ArrayList<String> luggageTypes = new ArrayList<>();
        ArrayList<String> luggageStatus = new ArrayList<>();

        try{
            FlightDAO flightDAO = new FlightDAOImpl();


            flightDAO.getAllDataForAFlight(flightId, flightGates, buyerIds, usernames, mails, hashedPasswords,
                                           bookingDates, bookingStatus, bookingIds,
                                           ticketNumbers, seats, checkedIns,
                                           firstNames, lastNames, passengerSSNs, birthDates,
                                           luggageIds, luggageTypes, luggageStatus, luggageIdsAfterCheckin);



        } catch (SQLException e) {
            new FloatingMessage("Errore nella connessione al Database (Voli)!", errorButton, FloatingMessage.ERROR_MESSAGE);
        }

        customerController.setSearchBookingResultCustomers(new ArrayList<>());
        customerController.setSearchBookingResultCustomersIds(new ArrayList<>());

        bookingController.setSearchBookingResult(new ArrayList<>());
        bookingController.setSearchBookingResultIds(new ArrayList<>());


        ticketController.setSearchBookingResult(new ArrayList<>());
        passengerController.setSearchBookingResult(new ArrayList<>());

        luggageController.setSearchBookingResult(new ArrayList<>());
        luggageController.setSearchBookingResultIds(new ArrayList<>());


        ArrayList<Integer> actualBuyersIds = new ArrayList<>();
        ArrayList<Integer> actualBookingIds = new ArrayList<>();
        ArrayList<String> actualTicketNumbers = new ArrayList<>();
        ArrayList<String> actualSSNs = new ArrayList<>();

        try{
            if(!flightGates.isEmpty()){
                if(flightGates.getFirst() != null){
                    flightController.getFlight().setGate(new Gate(flightGates.getFirst().byteValue()));
                }
            }


            for(int i = 0; i < luggageIds.size(); i++){


                if(!actualBuyersIds.contains(buyerIds.get(i))){

                    actualBuyersIds.add(buyerIds.get(i));

                    customerController.getSearchBookingResultCustomers().add(new Customer(usernames.get(i), mails.get(i), hashedPasswords.get(i)));
                    customerController.getSearchBookingResultCustomersIds().add(buyerIds.get(i));
                }


                if(!actualBookingIds.contains(bookingIds.get(i))){

                    actualBookingIds.add(bookingIds.get(i));

                    bookingController.getSearchBookingResult().add(new Booking(BookingStatus.valueOf(bookingStatus.get(i)), bookingDates.get(i),
                            customerController.getSearchBookingResultCustomerById(buyerIds.get(i)), flightController.getFlight(),
                            ticketNumbers.get(i), seats.get(i), checkedIns.get(i),
                            firstNames.get(i), lastNames.get(i), passengerSSNs.get(i), birthDates.get(i)));

                    bookingController.getSearchBookingResultIds().add(bookingIds.get(i));

                    actualTicketNumbers.add(ticketNumbers.get(i));
                    ticketController.getSearchBookingResult().add(bookingController.getSearchBookingResult().getLast().getTickets().getLast());
                    Passenger tmp = ticketController.getSearchBookingResult().getLast().getPassenger();
                    if(!actualSSNs.contains(tmp.getPassengerSSN())){
                        actualSSNs.add(tmp.getPassengerSSN());
                        passengerController.getSearchBookingResult().add(tmp);
                    }
                }else{
                    if(!actualTicketNumbers.contains(ticketNumbers.get(i))) {
                        actualTicketNumbers.add(ticketNumbers.get(i));
                        ticketController.getSearchBookingResult().add(new Ticket(ticketNumbers.get(i), seats.get(i), checkedIns.get(i), flightController.getFlight(),
                                bookingController.getSearchBookingResultBooksById(bookingIds.get(i)),
                                firstNames.get(i), lastNames.get(i), passengerSSNs.get(i), birthDates.get(i)));
                        Passenger tmp = ticketController.getSearchBookingResult().getLast().getPassenger();
                        if (!actualSSNs.contains(tmp.getPassengerSSN())) {
                            actualSSNs.add(tmp.getPassengerSSN());
                            passengerController.getSearchBookingResult().add(tmp);
                        }
                        bookingController.getSearchBookingResultBooksById(bookingIds.get(i)).getTickets().add(ticketController.getSearchBookingResult().getLast());
                    }
                }

                if(luggageIds.get(i) != null){
                    if(luggageTypes.get(i) != null){
                        luggageController.getSearchBookingResult().add(new Luggage(luggageIdsAfterCheckin.get(i), LuggageType.valueOf(luggageTypes.get(i)), LuggageStatus.valueOf(luggageStatus.get(i)),
                                ticketController.getSearchBookingResultTicketByTicketNumber(ticketNumbers.get(i))));
                    }else{
                        luggageController.getSearchBookingResult().add(new Luggage(luggageIdsAfterCheckin.get(i), LuggageStatus.valueOf(luggageStatus.get(i)),
                                ticketController.getSearchBookingResultTicketByTicketNumber(ticketNumbers.get(i))));
                    }

                    ticketController.getSearchBookingResultTicketByTicketNumber(ticketNumbers.get(i)).getLuggages().add(luggageController.getSearchBookingResult().getLast());
                }

            }

            flightController.getFlight().getBookings().addAll(bookingController.getSearchBookingResult());
            flightController.getFlight().getTickets().addAll(ticketController.getSearchBookingResult());

            luggageController.getSearchBookingResultIds().addAll(luggageIds);

        }catch (Exception e){
            Controller.getLogger().log(Level.SEVERE, e.getMessage());
            new FloatingMessage("Errore nella connessione al Database!", errorButton, FloatingMessage.ERROR_MESSAGE);

        }


    }

    /**
     * Gets all luggages for a booking.
     *
     * @param index the index
     */
    public void getAllLuggagesForABooking(Integer index) {


        flightController.setBookingResultSelectedFlight(bookingController.getSearchBookingResult().get(index).getBookedFlight().getId());

        bookingController.setBookingResultSelectedBooking(index);

        ArrayList<String> ticketNumbers = new ArrayList<>();
        ArrayList<Integer> luggageIds = new ArrayList<>();
        ArrayList<String> luggageIdsAfterCheckin = new ArrayList<>();
        ArrayList<String> luggageTypes = new ArrayList<>();
        ArrayList<String> luggageStatus = new ArrayList<>();

        try{
            LuggageDAO luggageDAO = new LuggageDAOImpl();


            luggageDAO.getAllLuggagesOfBooking(bookingController.getId(), ticketNumbers, luggageIds, luggageTypes, luggageStatus, luggageIdsAfterCheckin);


        } catch (SQLException e) {
            Controller.getLogger().log(Level.SEVERE, e.getSQLState());
            new FloatingMessage("Errore nella connessione al Database (Bagagli)!", errorButton, FloatingMessage.ERROR_MESSAGE);
        }

        try{

            luggageController.setSearchBookingResult(new ArrayList<>());
            luggageController.setSearchBookingResultIds(new ArrayList<>());

            for(int i = 0; i < ticketNumbers.size(); i++){

                if(luggageIds.get(i) != null){

                    if(luggageTypes.get(i) != null){
                        luggageController.getSearchBookingResult().add(new Luggage(luggageIdsAfterCheckin.get(i), LuggageType.valueOf(luggageTypes.get(i)), LuggageStatus.valueOf(luggageStatus.get(i)),
                                ticketController.getSearchBookingResultTicketByTicketNumber(ticketNumbers.get(i))));
                    }else{
                        luggageController.getSearchBookingResult().add(new Luggage(luggageIdsAfterCheckin.get(i), LuggageStatus.valueOf(luggageStatus.get(i)),
                                ticketController.getSearchBookingResultTicketByTicketNumber(ticketNumbers.get(i))));
                    }

                    ticketController.getSearchBookingResultTicketByTicketNumber(ticketNumbers.get(i)).getLuggages().add(luggageController.getSearchBookingResult().getLast());

                }
            }

            luggageController.getSearchBookingResultIds().addAll(luggageIds);

        }catch (Exception e){
            Controller.getLogger().log(Level.SEVERE, e.getMessage());
            new FloatingMessage("Errore nella connessione al Database (Bagagli)!", errorButton, FloatingMessage.ERROR_MESSAGE);

        }

    }

    /**
     * Get logger logger.
     *
     * @return the logger
     */
    public static Logger getLogger(){ return LOGGER;}

    /**
     * Update user boolean.
     *
     * @param mail           the mail
     * @param username       the username
     * @param hashedPassword the hashed password
     * @param button         the button
     * @return the boolean
     */
    public boolean updateUser(String mail, String username, String hashedPassword, JButton button){
        if (userController.isInvalidMail(mail)) {
            new FloatingMessage("<html>Mail non valida</html>", button, FloatingMessage.WARNING_MESSAGE);
            return false;
        }
        if (userController.isInvalidUsername(username)) {
            new FloatingMessage("<html>Username non valido.<br>Il nome utente deve iniziare con una lettera, " +
                    "finire con una lettera o un numero e può contenere solo lettere, numeri, trattini (-), underscore(_) e punti(.)</html>",
                    button, FloatingMessage.WARNING_MESSAGE);
            return false;
        }

        try {
            if (userController.getLoggedUser() instanceof Admin) {
                AdminDAOImpl adminDAO = new AdminDAOImpl();
                adminDAO.updateAdmin(userController.getLoggedUserId(), username, hashedPassword);
                adminController.setLoggedAdmin(new Admin(username, userController.getLoggedUser().getEmail(), hashedPassword), userController.getLoggedUserId());
                userController.setLoggedUser(new Admin(username, userController.getLoggedUser().getEmail(), hashedPassword), userController.getLoggedUserId());
            } else {
                CustomerDAOImpl customerDAO = new CustomerDAOImpl();
                customerDAO.updateCustomer(userController.getLoggedUserId(), mail, username, hashedPassword);
                customerController.setLoggedCustomer(new Customer(username, userController.getLoggedUser().getEmail(), hashedPassword), userController.getLoggedUserId());
                userController.setLoggedUser(new Customer(username, userController.getLoggedUser().getEmail(), hashedPassword), userController.getLoggedUserId());
            }

            new FloatingMessage("<html>Informazioni aggiornate con successo</html>", button, FloatingMessage.SUCCESS_MESSAGE);
            return true;
        } catch (SQLException e) {
            new FloatingMessage("<html>Errore nel collegamento al DB(Customer) o DB(Admin)<br>" + e.getMessage() + "</html>", button, FloatingMessage.ERROR_MESSAGE);
            return false;
        }

    }
}
