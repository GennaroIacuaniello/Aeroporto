package controller;

import dao.BookingDAO;
import gui.DisposableObject;
import gui.FloatingMessage;

import gui.LuggagePanel;
import gui.PassengerPanel;
import implementazioniPostgresDAO.BookingDAOImpl;
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

    public void setAdminNUser (String username, String email, String hashedPassword) {
        adminController.setAdmin (username, email, hashedPassword, 0);
        userController.setUser (username, email, hashedPassword);
    }

    public void setCustomerNUser (String username, String hashedPassword, int id) {
        customerController.setCustomer (username, hashedPassword, id);
        userController.setUser (username, hashedPassword, id);
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
            ArrayList<Date> birthDate = new ArrayList<Date>();
            ArrayList<String> SSNs = new ArrayList<String>();
            ArrayList<String> luggagesTypes = new ArrayList<String>();
            ArrayList<String> ticketsForLuggagesTypes = new ArrayList<String>();

            for (PassengerPanel passengerPanel : passengerPanels) {

                ticketsNumbers.add(generateTicketsNumber());
                seats.add(passengerPanel.getSeat());
                firstNames.add(passengerPanel.getPassengerName());
                lastNames.add(passengerPanel.getPassengerSurname());
                birthDate.add(passengerPanel.getPassengerDate());
                SSNs.add(passengerPanel.getPassengerCF());

                for (LuggagePanel luggagePanel : passengerPanel.getLuggagesPanels()) {

                    if (luggagePanel.getComboBox().getSelectedIndex() != 0) {
                        luggagesTypes.add(luggagePanel.getComboBox().getSelectedItem().toString());
                        ticketsForLuggagesTypes.add(ticketsNumbers.getLast());
                    }
                }
            }

            bookingDAO.addBooking(getUserController().getId(), flightController.getId(), bookingStatus.name(), ticketsNumbers,
                    seats, firstNames, lastNames, birthDate, SSNs, luggagesTypes, ticketsForLuggagesTypes);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifyBooking (ArrayList<PassengerPanel> passengerPanels, BookingStatus bookingStatus) {}

    public String generateTicketsNumber () {

        return "ticketsNumber";
    }

    public void getAllBooksLoogedCustomer(List<Date> bookingDates, List<String> bookingStatus, List<String> flightIds, JButton searchButton) {


        ArrayList<Flight>  searchBookingFlightsResult = new ArrayList<>(0);

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
            new FloatingMessage("Errore nella connessione al Database!", searchButton, FloatingMessage.ERROR_MESSAGE);
        }

        ArrayList<String> actualIds = new ArrayList<>();

        for(int i = 0; i < flightIds.size(); i++){

            if(types.get(i)){   //alloco Departing

                //getFlightController().getsearchBookingResult().add(new Departing( ids.get(i), companyNames.get(i), dates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                  //      FlightStatus.valueOf(status.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i), delays.get(i)));

            }else{              //alloco Arriving

                //getFlightController().getsearchBookingResult().add(new Arriving( ids.get(i), companyNames.get(i), dates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                  //      FlightStatus.valueOf(status.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i), delays.get(i)));


            }

        }

    }

}
