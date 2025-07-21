package controller;

import dao.BookingDAO;
import dao.LuggageDAO;
import dao.TicketDAO;
import dao.UserNotFoundException;
import gui.DisposableObject;
import gui.FloatingMessage;

import gui.LuggagePanel;
import gui.PassengerPanel;
import implementazioniPostgresDAO.*;
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

public class Controller {

    private AdminController adminController;
    private ArrivingController arrivingController;
    private BookingController bookingController;
    private CustomerController customerController;
    private DepartingController departingController;
    private FlightController flightController;
    private GateController gateController;
    private LuggageController luggageController;
    private PassengerController passengerController;
    private UserController userController;
    private FlightStatusController flightStatusController;
    private BookingStatusController bookingStatusController;
    private TicketController ticketController;
    private JButton errorButton;


    public Controller() {
        adminController = new AdminController();
        arrivingController = new ArrivingController();
        bookingController = new BookingController();
        customerController = new CustomerController();
        departingController = new DepartingController();
        flightController = new FlightController();
        gateController = new GateController();
        luggageController = new LuggageController();
        passengerController = new PassengerController();
        userController = new UserController();
        flightStatusController = new FlightStatusController();
        bookingStatusController = new BookingStatusController();
        ticketController = new TicketController();
    }

    public Object[][] getImminentArrivingFlights(){

        ArrayList<Arriving> arrivingFlights = new ArrayList<Arriving>();
        Object[][] result = new Object[10][6];

        arrivingFlights.add(new Arriving("01", "Compagnia", new Date(0),
                new Time(1), new Time(1), 100, "Dubai"));
        arrivingFlights.add(new Arriving("02", "Compagnia", new Date(1),
                new Time(1), new Time(1), 100, "Dubai"));
        arrivingFlights.add(new Arriving("03", "Compagnia", new Date(2),
                new Time(1), new Time(1), 100, "Dubai"));

        for (int i = 0; i < arrivingFlights.size(); i++) {
            result[i][0] = arrivingFlights.get(i).getId();
            result[i][1] = arrivingFlights.get(i).getCompanyName();
            result[i][2] = arrivingFlights.get(i).getOrigin();
            result[i][3] = Integer.valueOf(arrivingFlights.get(i).getDate().getDate()).toString() +
                            " " + arrivingFlights.get(i).getMonthName();
            result[i][4] = arrivingFlights.get(i).getArrivalTime();
            result[i][5] = "\uD83D\uDEC8";
        }

        return result;
    }

    public Object[][] getImminentDepartingFlights(){

        ArrayList<Departing> departingFlights = new ArrayList<Departing>();
        Object[][] result = new Object[10][5];

        departingFlights.add(new Departing("01", "Compagnia", new Date(3),
                new Time(1), new Time(1), 100, "Dubai"));
        departingFlights.add(new Departing("02", "Compagnia", new Date(4),
                new Time(1), new Time(1), 100, "Dubai"));
        departingFlights.add(new Departing("03", "Compagnia", new Date(5),
                new Time(1), new Time(1), 100, "Dubai"));

        for (int i = 0; i < departingFlights.size(); i++) {

            try {departingFlights.get(i).setGate(new Gate((byte)i));}
            catch (InvalidGate e) {
                e.printStackTrace();
            }

            result[i][0] = departingFlights.get(i).getCompanyName();
            result[i][1] = departingFlights.get(i).getDestination();
            result[i][2] = Integer.valueOf(departingFlights.get(i).getDate().getDate()).toString() +
                    " " + departingFlights.get(i).getMonthName();
            result[i][3] = departingFlights.get(i).getDepartureTime();
            result[i][4] = Integer.valueOf(departingFlights.get(i).getGate().getId()).toString();
        }

        return result;
    }


    public AdminController getAdminController() {
        return adminController;
    }

    public ArrivingController getArrivingController() {
        return arrivingController;
    }

    public BookingController getBookingController() {
        return bookingController;
    }

    public CustomerController getCustomerController() {
        return customerController;
    }

    public DepartingController getDepartingController() {
        return departingController;
    }

    public FlightController getFlightController() {
        return flightController;
    }

    public GateController getGateController() {
        return gateController;
    }

    public LuggageController getLuggageController() {
        return luggageController;
    }

    public PassengerController getPassengerController() {
        return passengerController;
    }

    public UserController getUserController() {
        return userController;
    }

    public FlightStatusController getFlightStatusController() {
        return flightStatusController;
    }

    public BookingStatusController getBookingStatusController() {
        return bookingStatusController;
    }

    public TicketController getTicketController(){
        return ticketController;
    }

    public void setAdminNUser (String username, String email, String hashedPassword) {
        adminController.setAdmin (username, email, hashedPassword, 0);
        userController.setLoggedUser (new Admin( username, email, hashedPassword), 0);
    }

    public void setCustomerNUser (String username, String mail, String hashedPassword, Integer id) {
        customerController.setLoggedCustomer(username, mail, hashedPassword, id);
        userController.setLoggedUser (new Customer(username, mail, hashedPassword), id);
    }

    public boolean checkBooking (int index) {
        if (getBookingController().getBooking() != null && getBookingController().getBooking() == getFlightController().getFlight().getBookings().get(index)) return false;
        if (getFlightController().getFlight().getBookings().get(index).getStatus() == BookingStatus.CANCELLED) return false;

        return true;
    }

    public void goHome (ArrayList<DisposableObject> callingObjects) {

        Dimension sourceDimension = callingObjects.getLast().getFrame().getSize();
        Point sourceLocation = callingObjects.getLast().getFrame().getLocation();
        int sourceExtendedState = callingObjects.getLast().getFrame().getExtendedState();

        for (int i = callingObjects.size() - 1; i > 1; i--) {

            callingObjects.get(i).doOnDispose(callingObjects, this);
            callingObjects.getLast().getFrame().dispose();
            callingObjects.removeLast();
        }

        if(sourceExtendedState != JFrame.MAXIMIZED_BOTH){ //if frame is maximized size and location are automatic
            callingObjects.getLast().getFrame().setSize(sourceDimension);
            callingObjects.getLast().getFrame().setLocation(sourceLocation);
        }
        callingObjects.getLast().getFrame().setExtendedState(sourceExtendedState);

        callingObjects.getLast().doOnRestore(callingObjects, this);

        callingObjects.getLast().getFrame().setVisible(true);
    }

    public void goBack (ArrayList<DisposableObject> callingObjects) {

        Dimension sourceDimension = callingObjects.getLast().getFrame().getSize();
        Point sourceLocation = callingObjects.getLast().getFrame().getLocation();
        int sourceExtendedState = callingObjects.getLast().getFrame().getExtendedState();

        callingObjects.getLast().doOnDispose(callingObjects, this);
        callingObjects.getLast().getFrame().dispose();
        callingObjects.removeLast();

        if(sourceExtendedState != JFrame.MAXIMIZED_BOTH){ //if frame is maximized size and location are automatic
            callingObjects.getLast().getFrame().setSize(sourceDimension);
            callingObjects.getLast().getFrame().setLocation(sourceLocation);
        }
        callingObjects.getLast().getFrame().setExtendedState(sourceExtendedState);

        callingObjects.getLast().doOnRestore(callingObjects, this);

        callingObjects.getLast().getFrame().setVisible(true);
    }

    public void logOut (ArrayList<DisposableObject> callingObjects) {

        for (int i = callingObjects.size() - 1; i > 0; i--) {
            callingObjects.getLast().doOnDispose(callingObjects, this);
            callingObjects.getLast().getFrame().dispose();
            callingObjects.removeLast();
        }

        callingObjects.getLast().doOnRestore(callingObjects, this);
        callingObjects.getLast().getFrame().setVisible(true);
    }

    public void addBooking(ArrayList<PassengerPanel> passengerPanels, BookingStatus bookingStatus) {

        try {

            BookingDAOImpl bookingDAO = new BookingDAOImpl();

            ArrayList<String> ticketsNumbers = new ArrayList<String>();
            ArrayList<Integer> seats = new ArrayList<Integer>();
            ArrayList<String> firstNames = new ArrayList<String>();
            ArrayList<String> lastNames = new ArrayList<String>();
            ArrayList<Date> birthDates = new ArrayList<Date>();
            ArrayList<String> SSNs = new ArrayList<String>();
            ArrayList<String> luggagesTypes = new ArrayList<String>();
            ArrayList<String> ticketsForLuggagesTypes = new ArrayList<String>();

            preparePassengers(passengerPanels, ticketsNumbers, seats, firstNames, lastNames, birthDates, SSNs, luggagesTypes, ticketsForLuggagesTypes);

            bookingDAO.addBooking(getUserController().getLoggedUserId(), flightController.getId(), bookingStatus.name(), ticketsNumbers,
                    seats, firstNames, lastNames, birthDates, SSNs, luggagesTypes, ticketsForLuggagesTypes);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifyBooking (ArrayList<PassengerPanel> passengerPanels, BookingStatus bookingStatus) {

        try {

            BookingDAOImpl bookingDAO = new BookingDAOImpl();

            ArrayList<String> ticketsNumbers = new ArrayList<String>();
            ArrayList<Integer> seats = new ArrayList<Integer>();
            ArrayList<String> firstNames = new ArrayList<String>();
            ArrayList<String> lastNames = new ArrayList<String>();
            ArrayList<Date> birthDates = new ArrayList<Date>();
            ArrayList<String> SSNs = new ArrayList<String>();
            ArrayList<String> luggagesTypes = new ArrayList<String>();
            ArrayList<String> ticketsForLuggagesTypes = new ArrayList<String>();

            preparePassengers(passengerPanels, ticketsNumbers, seats, firstNames, lastNames, birthDates, SSNs, luggagesTypes, ticketsForLuggagesTypes);

            bookingDAO.modifyBooking(this, flightController.getId(), getBookingController().getId(), ticketsNumbers,
                    seats, firstNames, lastNames, birthDates, SSNs, luggagesTypes, ticketsForLuggagesTypes, generateTicketNumber());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void preparePassengers (ArrayList<PassengerPanel> passengerPanels, ArrayList<String> ticketsNumbers, ArrayList<Integer> seats, ArrayList<String> firstNames, ArrayList<String> lastNames,
                                    ArrayList<Date> birthDates, ArrayList<String> SSNs, ArrayList<String> luggagesTypes, ArrayList<String> ticketsForLuggagesTypes) {

        for (PassengerPanel passengerPanel : passengerPanels) {

            if(passengerPanel.getTicketNumber() == null) ticketsNumbers.add(generateTicketNumber());
            else ticketsNumbers.add(passengerPanel.getTicketNumber());

            seats.add(passengerPanel.getSeat());
            firstNames.add(passengerPanel.getPassengerName());
            lastNames.add(passengerPanel.getPassengerSurname());
            birthDates.add(passengerPanel.getPassengerDate());
            SSNs.add(passengerPanel.getPassengerCF());

            for (LuggagePanel luggagePanel : passengerPanel.getLuggagesPanels()) {

                if (luggagePanel.getComboBox().getSelectedIndex() != 0) {
                    luggagesTypes.add(luggagePanel.getComboBox().getSelectedItem().toString());
                    ticketsForLuggagesTypes.add(ticketsNumbers.getLast());
                }
            }
        }
    }

    public String generateTicketNumber () {

        return "ticketsNumber";
    }

    public JButton getErrorButton() {
        return errorButton;
    }

    public void setErrorButton(JButton errorButton) {
        this.errorButton = errorButton;
    }

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

                if(types.get(i)){   //alloco Departing

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
                    new FloatingMessage("Errore nella connessione al Database (Biglietti)!", searchButton, FloatingMessage.ERROR_MESSAGE);
                }
            }

            ticketController.getSearchBookingResult().addAll(bookingController.getSearchBookingResult().getLast().getTickets());
            for(Ticket x: bookingController.getSearchBookingResult().getLast().getTickets()){
                if(!actualSSNs.contains(x.getPassenger().getSSN())){
                    actualSSNs.add(x.getPassenger().getSSN());
                    passengerController.getSearchBookingResult().add(x.getPassenger());
                }

            }

        }

    }

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

                if(types.get(i)){   //alloco Departing

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
                    new FloatingMessage("Errore nella connessione al Database (Biglietti)!", searchButton, FloatingMessage.ERROR_MESSAGE);
                }
            }

            ticketController.getSearchBookingResult().addAll(bookingController.getSearchBookingResult().getLast().getTickets());
            for(Ticket x: bookingController.getSearchBookingResult().getLast().getTickets()){
                if(!actualSSNs.contains(x.getPassenger().getSSN())){
                    actualSSNs.add(x.getPassenger().getSSN());
                    passengerController.getSearchBookingResult().add(x.getPassenger());
                }

            }

        }


    }

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

                if(types.get(i)){   //alloco Departing

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
                    new FloatingMessage("Errore nella connessione al Database (Biglietti)!", searchButton, FloatingMessage.ERROR_MESSAGE);
                }
            }

            ticketController.getSearchBookingResult().addAll(bookingController.getSearchBookingResult().getLast().getTickets());
            for(Ticket x: bookingController.getSearchBookingResult().getLast().getTickets()){
                if(!actualSSNs.contains(x.getPassenger().getSSN())){
                    actualSSNs.add(x.getPassenger().getSSN());
                    passengerController.getSearchBookingResult().add(x.getPassenger());
                }

            }

        }

    }

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
                    new FloatingMessage("Errore nella connessione al Database (Biglietti)!", errorButton, FloatingMessage.ERROR_MESSAGE);
                }
            }

            ticketController.getSearchBookingResult().addAll(bookingController.getSearchBookingResult().getLast().getTickets());
            for(Ticket x: bookingController.getSearchBookingResult().getLast().getTickets()){
                if(!actualSSNs.contains(x.getPassenger().getSSN())){
                    actualSSNs.add(x.getPassenger().getSSN());
                    passengerController.getSearchBookingResult().add(x.getPassenger());
                }

            }

        }

        return !bookingIds.isEmpty();

    }

    public void clearSearchBookingResultCache() {

        bookingController.setSearchBookingResult(null);
        bookingController.setSearchBookingResultIds(null);
        flightController.setSearchBookingResult(null);

    }

    public void clearSearchFlightsResultCache() {

        flightController.setSearchResult(null);

    }

    public boolean verifyUser(String loggingInfo, String hashedPassword, JButton loginButton){

        //Avoid opening DB if it is obvious that it won't contain the user
        if(loggingInfo.contains("@")){
            if (!userController.isValidMail(loggingInfo)){
                new FloatingMessage("<html>User o mail non valida</html>", loginButton, FloatingMessage.WARNING_MESSAGE);
                return false;
            }
        } else if(!userController.isValidUsername(loggingInfo)){
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

    public void setBookedSeats (ArrayList<Integer> bookedSeats) {
        FlightDAOImpl flightDAO = new FlightDAOImpl();

        flightDAO.getBookedSeats(flightController.getId(), bookingController.getId(), bookedSeats);
    }

    public boolean isLoggedAdmin() {

        return userController.getLoggedUser() instanceof Admin;

    }

    public void getLostLuggages(List<String> flightIds, List<Date> bookingDates, List<String> firstNames, List<String> lastNames, List<String> passengerSSNs, List<String> luggageIds) {

        ArrayList<String> companyNames = new ArrayList<>();
        ArrayList<Date> flightDates = new ArrayList<>();
        ArrayList<Time> departureTimes = new ArrayList<>();
        ArrayList<Time> arrivalTimes = new ArrayList<>();
        ArrayList<String> flightStatus = new ArrayList<>();
        ArrayList<Integer> maxSeats = new ArrayList<>();
        ArrayList<Integer> freeSeats = new ArrayList<>();
        ArrayList<String> cities = new ArrayList<>();

        ArrayList<Boolean> flightTypes = new ArrayList<>();

        ArrayList<Integer> bookingIds = new ArrayList<>();
        ArrayList<String> bookingStatus = new ArrayList<>();

        ArrayList<String> ticketNumbers = new ArrayList<>();
        ArrayList<Integer> seats = new ArrayList<>();
        ArrayList<Boolean> checkedIns = new ArrayList<>();

        ArrayList<Date> birthDates = new ArrayList<>();

        ArrayList<String> luggageTypes = new ArrayList<>();
        ArrayList<String> luggageStatus = new ArrayList<>();

        try{
            LuggageDAO luggageDAO = new LuggageDAOImpl();

            luggageDAO.getAllLostLuggages(flightIds, companyNames, flightDates, departureTimes, arrivalTimes,
                                          flightStatus, maxSeats, freeSeats, cities, flightTypes,
                                          bookingDates, bookingStatus, bookingIds,
                                          ticketNumbers, seats, checkedIns,
                                          firstNames, lastNames, passengerSSNs, birthDates,
                                          luggageIds, luggageTypes, luggageStatus);



        } catch (SQLException e) {
            new FloatingMessage("Errore nella connessione al Database (Prenotazioni)!", errorButton, FloatingMessage.ERROR_MESSAGE);
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

                if(flightTypes.get(i)){   //alloco Departing

                    //flightController.getSearchBookingResult().add(new Departing( flightIds.get(i), companyNames.get(i), dates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                      //      FlightStatus.valueOf(status.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i)));

                }else{              //alloco Arriving

                    //flightController.getSearchBookingResult().add(new Arriving( flightIds.get(i), companyNames.get(i), dates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                      //      FlightStatus.valueOf(status.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i)));


                }
            }



            TicketDAO ticketDao = new TicketDAOImpl();

            try{

                ticketDao.getAllTicketBooking(bookingIds.get(i), ticketNumbers, seats, checkedIns, passengerSSNs, firstNames, lastNames, birthDates);

            } catch (SQLException e) {

                new FloatingMessage("Errore nella connessione al Database (Biglietti)!", errorButton, FloatingMessage.ERROR_MESSAGE);
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
                new FloatingMessage("Errore nella connessione al Database (Biglietti)!", errorButton, FloatingMessage.ERROR_MESSAGE);
            }

            for(int j = 1; j < ticketNumbers.size(); j++){
                try{
                    bookingController.getSearchBookingResult().getLast().getTickets().add(new Ticket(ticketNumbers.get(j), seats.get(j), checkedIns.get(j),
                            flightController.getSearchBookingResult().getLast(), bookingController.getSearchBookingResult().getLast(),
                            firstNames.get(j), lastNames.get(j), passengerSSNs.get(j), birthDates.get(j)));

                }catch (Exception e){
                    new FloatingMessage("Errore nella connessione al Database (Biglietti)!", errorButton, FloatingMessage.ERROR_MESSAGE);
                }
            }

            ticketController.getSearchBookingResult().addAll(bookingController.getSearchBookingResult().getLast().getTickets());
            for(Ticket x: bookingController.getSearchBookingResult().getLast().getTickets()){
                if(!actualSSNs.contains(x.getPassenger().getSSN())){
                    actualSSNs.add(x.getPassenger().getSSN());
                    passengerController.getSearchBookingResult().add(x.getPassenger());
                }

            }

        }

    }
}
