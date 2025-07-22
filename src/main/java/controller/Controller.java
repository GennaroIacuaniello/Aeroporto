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
import java.util.logging.Logger;

public class Controller {

    private final AdminController adminController;
    private final ArrivingController arrivingController;
    private final BookingController bookingController;
    private final CustomerController customerController;
    private final DepartingController departingController;
    private final FlightController flightController;
    private final GateController gateController;
    private final LuggageController luggageController;
    private final PassengerController passengerController;
    private final UserController userController;
    private final TicketController ticketController;
    private JButton errorButton;
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());



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
        ticketController = new TicketController();
    }

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
            return null;
        }

        try{
            for (int i = 0; i < flightId.size(); i++) {

                if(gate.get(i) != null){
                    arrivingFlights.add(new Arriving(flightId.get(i), companyName.get(i), flightDate.get(i), departureTime.get(i),
                            arrivalTime.get(i), FlightStatus.valueOf(status.get(i)), maxSeats.get(i), freeSeats.get(i),
                            origin.get(i), arrivalDelay.get(i), new Gate(gate.get(i).byteValue())));
                }else{
                    arrivingFlights.add(new Arriving(flightId.get(i), companyName.get(i), flightDate.get(i), departureTime.get(i),
                            arrivalTime.get(i), FlightStatus.valueOf(status.get(i)), maxSeats.get(i), freeSeats.get(i),
                            origin.get(i), arrivalDelay.get(i)));
                }

            }
        } catch (Exception e){
            return null;
        }

        for (int i = 0; i < arrivingFlights.size(); i++) {
            result[i][0] = arrivingFlights.get(i).getId();
            result[i][1] = arrivingFlights.get(i).getCompanyName();
            result[i][2] = arrivingFlights.get(i).getDate();
            result[i][3] = arrivingFlights.get(i).getOrigin() + " -> Napoli";
            result[i][4] = arrivingFlights.get(i).getArrivalTime().toLocalTime().plusMinutes(arrivingFlights.get(i).getArrivalDelay());
            result[i][5] = translateFlightStatus(arrivingFlights.get(i).getStatus());
            if(arrivingFlights.get(i).getGate() != null){
                result[i][6] = arrivingFlights.get(i).getGate().getId();
            }else{
                result[i][6] = "Non assegnato";
            }


        }

        return result;
    }

    public String translateFlightStatus(FlightStatus status){

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

    public Object[][] getImminentDepartingFlights(){

        ArrayList<Departing> departingFlights = new ArrayList<>();
        Object[][] result = new Object[10][5];

        departingFlights.add(new Departing("01", "Compagnia", new Date(3),
                new Time(1), new Time(1), 100, "Dubai"));
        departingFlights.add(new Departing("02", "Compagnia", new Date(4),
                new Time(1), new Time(1), 100, "Dubai"));
        departingFlights.add(new Departing("03", "Compagnia", new Date(5),
                new Time(1), new Time(1), 100, "Dubai"));

        for (int i = 0; i < departingFlights.size(); i++) {

            try {departingFlights.get(i).setGate(new Gate((byte)(i + 1)));}
            catch (InvalidGate e) {
                e.printStackTrace();
            }

            result[i][0] = departingFlights.get(i).getCompanyName();
            result[i][1] = departingFlights.get(i).getDestination();
            result[i][2] = Integer.valueOf(departingFlights.get(i).getDate().getDate()).toString() +
                    " " + departingFlights.get(i).getMonthName();
            result[i][3] = departingFlights.get(i).getDepartureTime();
            if(departingFlights.get(i).getGate() != null){
                result[i][4] = Integer.valueOf(departingFlights.get(i).getGate().getId()).toString();
            }else{
                result[i][4] = "Non assegnato";
            }

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

    public void addBooking(ArrayList<PassengerPanel> passengerPanels, String bookingStatus) {

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

            bookingDAO.addBooking(getUserController().getLoggedUserId(), flightController.getId(), bookingStatus, ticketsNumbers,
                    seats, firstNames, lastNames, birthDates, SSNs, luggagesTypes, ticketsForLuggagesTypes);

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void modifyBooking (ArrayList<PassengerPanel> passengerPanels, String bookingStatus) {

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

            bookingDAO.modifyBooking(flightController.getId(), getBookingController().getId(), ticketsNumbers,
                    seats, firstNames, lastNames, birthDates, SSNs, luggagesTypes, ticketsForLuggagesTypes, generateTicketNumber(passengerPanels.size() + 1), bookingStatus);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void preparePassengers (ArrayList<PassengerPanel> passengerPanels, ArrayList<String> ticketsNumbers, ArrayList<Integer> seats, ArrayList<String> firstNames, ArrayList<String> lastNames,
                                    ArrayList<Date> birthDates, ArrayList<String> SSNs, ArrayList<String> luggagesTypes, ArrayList<String> ticketsForLuggagesTypes) {

        int i = 0;

        for (PassengerPanel passengerPanel : passengerPanels) {

            if(passengerPanel.getTicketNumber() == null) ticketsNumbers.add(generateTicketNumber(i++));
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

    public String generateTicketNumber (int offset) {

        TicketDAOImpl ticketDAO = new TicketDAOImpl();

        return ticketDAO.generateTicketNumber(offset);
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
                if(!actualSSNs.contains(x.getPassenger().getPassengerSSN())){
                    actualSSNs.add(x.getPassenger().getPassengerSSN());
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
                if(!actualSSNs.contains(x.getPassenger().getPassengerSSN())){
                    actualSSNs.add(x.getPassenger().getPassengerSSN());
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
                if(!actualSSNs.contains(x.getPassenger().getPassengerSSN())){
                    actualSSNs.add(x.getPassenger().getPassengerSSN());
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
                if(!actualSSNs.contains(x.getPassenger().getPassengerSSN())){
                    actualSSNs.add(x.getPassenger().getPassengerSSN());
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
            if (userController.isValidMail(loggingInfo)){
                new FloatingMessage("<html>User o mail non valida</html>", loginButton, FloatingMessage.WARNING_MESSAGE);
                return false;
            }
        } else if(userController.isValidUsername(loggingInfo)){
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
            new FloatingMessage("Errore nella connessione al Database (Prenotazioni)!", errorButton, FloatingMessage.ERROR_MESSAGE);
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

                    if(flightTypes.get(i)){   //alloco Departing

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
            e.printStackTrace();
            new FloatingMessage("Errore nella connessione al Database (Bagagli smmarriti)!", errorButton, FloatingMessage.ERROR_MESSAGE);

        }


    }

    public void setBookingResultSelectedFlightForLostLuaggages(Integer luggageIndex) {

        flightController.setFlight(luggageController.getSearchBookingResult().get(luggageIndex).getTicket().getFlight());

        flightController.getFlight().getBookings().add(luggageController.getSearchBookingResult().get(luggageIndex).getTicket().getBooking());

    }

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
            new FloatingMessage("Errore nella connessione al Database (Prenotazioni)!", errorButton, FloatingMessage.ERROR_MESSAGE);
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
            e.printStackTrace();
            new FloatingMessage("Errore nella connessione al Database!", errorButton, FloatingMessage.ERROR_MESSAGE);

        }


    }

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
            e.printStackTrace();
            new FloatingMessage("Errore nella connessione al Database (Prenotazioni)!", errorButton, FloatingMessage.ERROR_MESSAGE);
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
            e.printStackTrace();
            new FloatingMessage("Errore nella connessione al Database (Bagagli smmarriti)!", errorButton, FloatingMessage.ERROR_MESSAGE);

        }

    }

    public static Logger getLogger(){ return LOGGER;}
}
