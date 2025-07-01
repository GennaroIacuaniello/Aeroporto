package controller;

import gui.DisposableObject;
import gui.LogInScreen;
import gui.PassengerPanel;
import model.Arriving;
import model.BookingStatus;
import model.Departing;
import model.Flight;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

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
    }

    public boolean developerMode = false;

    public ArrayList<Arriving> getImminentArrivingFlights(){

        ArrayList<Arriving> arrivingFlights = new ArrayList<Arriving>();

        arrivingFlights.add(new Arriving("01", "Compagnia", new Date(),
                "00:00", "00:00", 100, "Dubai"));
        arrivingFlights.add(new Arriving("02", "Compagnia", new Date(),
                "00:00", "00:01", 100, "Dubai"));
        arrivingFlights.add(new Arriving("03", "Compagnia", new Date(),
                "00:00", "00:02", 100, "Dubai"));

        return arrivingFlights;
    }

    public ArrayList<Departing> getImminentDepartingFlights(){

        ArrayList<Departing> departingFlights = new ArrayList<Departing>();

        departingFlights.add(new Departing("01", "Compagnia", new Date(),
                "00:00", "00:00", 100, "Dubai"));
        departingFlights.add(new Departing("02", "Compagnia", new Date(),
                "00:00", "00:01", 100, "Dubai"));
        departingFlights.add(new Departing("03", "Compagnia", new Date(),
                "00:00", "00:02", 100, "Dubai"));

        return departingFlights;
    }

    public ArrayList<Flight> search_flight_customer(String departing_city, String arriving_city, String date, String initial_time, String final_time){

        ArrayList<Flight> res = new ArrayList<Flight>(0);

        res.add(new Arriving("01", "Ciao", new Date(),
                "00:00", "00:00", 100, "Dubai"));
        res.add(new Arriving("02", "IO", new Date(),
                "00:00", "00:01", 100, "Dubai"));
        res.add(new Arriving("03", "TU", new Date(),
                "00:00", "00:02", 100, "Dubai"));

        res.add(new Departing("04", "HELLO", new Date(),
                "00:00", "00:00", 100, "Dubai"));
        res.add(new Departing("05", "ME", new Date(),
                "00:00", "00:01", 100, "Dubai"));
        res.add(new Departing("06", "YOU", new Date(),
                "00:00", "00:02", 100, "Dubai"));

        return res;
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

    public void setAdminNUser (String username, String password) {
        adminController.setAdmin (username, password);
        userController.setUser (username, password);
    }

    public void setCustomerNUser (String username, String password) {
        customerController.setCustomer (username, password);
        userController.setUser (username, password);
    }

    public boolean checkBooking (int index) {
        if (getBookingController().getBooking() != null && getBookingController().getBooking() == getFlightController().getFlight().get_bookings().get(index)) return false;
        if (getFlightController().getFlight().get_bookings().get(index).get_status() == BookingStatus.cancelled) return false;

        return true;
    }

    public void goHome (ArrayList<DisposableObject> callingObjects) {

        Dimension dimension = callingObjects.getLast().getFrame().getSize();
        Point point = callingObjects.getLast().getFrame().getLocation();

        for (int i = callingObjects.size() - 1; i > 1; i--) {

            callingObjects.get(i).doOnDispose(callingObjects, this);
            callingObjects.getLast().getFrame().dispose();
            callingObjects.removeLast();
        }

        callingObjects.getLast().getFrame().setSize(dimension);
        callingObjects.getLast().getFrame().setLocation(point);

        callingObjects.getLast().doOnRestore(callingObjects, this);

        callingObjects.getLast().getFrame().setVisible(true);
    }

    public void goBack (ArrayList<DisposableObject> callingObjects) {

        Dimension dimension = callingObjects.getLast().getFrame().getSize();
        Point point = callingObjects.getLast().getFrame().getLocation();

        callingObjects.getLast().doOnDispose(callingObjects, this);
        callingObjects.getLast().getFrame().dispose();
        callingObjects.removeLast();

        callingObjects.getLast().getFrame().setSize(dimension);
        callingObjects.getLast().getFrame().setLocation(point);

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

    }
}
