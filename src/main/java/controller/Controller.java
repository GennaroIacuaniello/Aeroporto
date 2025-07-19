package controller;

import dao.BookingDAO;
import dao.FlightDAO;
import gui.DisposableObject;
import gui.FloatingMessage;
import gui.PassengerPanel;
import implementazioniPostgresDAO.BookingDAOImpl;
import implementazioniPostgresDAO.FlightDAOImpl;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.sql.Time;
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

    public boolean developerMode = false;

    public Object[][] getImminentArrivingFlights(){

        ArrayList<Arriving> arrivingFlights = new ArrayList<Arriving>();
        Object[][] result = new Object[10][4];

        arrivingFlights.add(new Arriving("01", "Compagnia", new Date(0),
                new Time(1), new Time(1), 100, "Dubai"));
        arrivingFlights.add(new Arriving("02", "Compagnia", new Date(1),
                new Time(1), new Time(1), 100, "Dubai"));
        arrivingFlights.add(new Arriving("03", "Compagnia", new Date(2),
                new Time(1), new Time(1), 100, "Dubai"));

        for (int i = 0; i < arrivingFlights.size(); i++) {
            result[i][0] = arrivingFlights.get(i).getCompanyName();
            result[i][1] = arrivingFlights.get(i).getOrigin();
            result[i][2] = Integer.valueOf(arrivingFlights.get(i).getDate().getDate()).toString() +
                            " " + arrivingFlights.get(i).getMonthName();
            result[i][3] = arrivingFlights.get(i).getArrivalTime();
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

    public void setAdminNUser (String username, String email, String hashedPassword) {
        adminController.setAdmin (username, email, hashedPassword);
        userController.setUser (username, email, hashedPassword);
    }

    public void setCustomerNUser (String username, String hashedPassword) {
        customerController.setCustomer (username, hashedPassword);
        userController.setUser (username, hashedPassword);
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

            //bookingDAO.addBooking();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifyBooking (ArrayList<PassengerPanel> passengerPanels, BookingStatus bookingStatus) {}

    public ArrayList<String> generateTicketsNumbers (int lenght) {

        ArrayList<String> ticketsNumbers = new ArrayList<>();

        for (int i = 0; i < lenght; i++) {

            ticketsNumbers.add("i");
        }

        return ticketsNumbers;
    }

    public void getAllBooksLoogedCustomer(List<Date> bookingDates, List<Integer> numPassengers, List<String> ids) {

        ArrayList<Boolean> types = new ArrayList<>();
        ArrayList<Flight>  searchBookingFlightsResult = new ArrayList<>(0);

        ArrayList<String> companyNames = new ArrayList<>();
        ArrayList<Date> dates = new ArrayList<>();
        ArrayList<Time> departureTimes = new ArrayList<>();
        ArrayList<Time> arrivalTimes = new ArrayList<>();
        ArrayList<Integer> delays = new ArrayList<>();
        ArrayList<String> status = new ArrayList<>();
        ArrayList<Integer> maxSeats = new ArrayList<>();
        ArrayList<Integer> freeSeats = new ArrayList<>();
        ArrayList<String> cities = new ArrayList<>();

        try{
            BookingDAO bookingDAO = new BookingDAOImpl();

            bookingDAO.getAllBooksCustomer(loggedUser, ids, companyNames, dates, departureTimes, arrivalTimes, delays, status, maxSeats, freeSeats,
                    cities, types);



        } catch (SQLException e) {
            new FloatingMessage("Errore nella connessione al Database!", searchButton, FloatingMessage.ERROR_MESSAGE);
        }

        ArrayList<String> actualIds = new ArrayList<>();

        for(int i = 0; i < ids.size(); i++){

            if(types.get(i)){   //alloco Departing

                searchResult.add(new Departing( ids.get(i), companyNames.get(i), dates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                        FlightStatus.valueOf(status.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i), delays.get(i)));

            }else{              //alloco Arriving

                searchResult.add(new Arriving( ids.get(i), companyNames.get(i), dates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                        FlightStatus.valueOf(status.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i), delays.get(i)));


            }

        }

    }

}
