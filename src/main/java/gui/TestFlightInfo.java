package gui;

import controller.Controller;
import model.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;

public class TestFlightInfo {

    private static JFrame frame;
    private static Controller controller;
    private static Booking bookingTest;
    private static Flight flight;
    private static ArrayList<Passenger> passengers;

    public static void main(String[] args) throws InvalidPassengerNumber {

        controller = new Controller();

        //set frame
        frame = new JFrame("Test FlightInfo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //creo passeggeri
        passengers = new ArrayList<>();

        passengers.add (new Passenger("anna", "marino", "qwe", 3));
        passengers.add (new Passenger("maria", "giglio", "rty", 5));
        passengers.add (new Passenger("carmela", "tedesco", "uio", 9));
        passengers.add (new Passenger("sara", "zumbolo", "asd", 2));
        passengers.add (new Passenger("carla", "chirico", "fgh", 0));
        passengers.add (new Passenger("chiara", "russo", "jkl", 6));
        passengers.add (new Passenger("rosa", "manzo", "zxc", 4));

        //sistemo il controller
        controller.getFlightController().setFlight("03", "che ne so", new Date(2025, 2, 14), "12:25",
                "14:10", 19);

        controller.getBookingController().setBooking(new Customer("pippo", "pluto"), controller.getFlightController().getFlight(), passengers);

        controller.getFlightController().getFlight().add_booking(controller.getBookingController().getBooking());

        controller.setCustomerNUser("pippo", "pluto");

        ArrayList<Passenger> passengers2 = new ArrayList<Passenger>();
        passengers2.add (new Passenger("a", "a", "a", 14));
        passengers2.add (new Passenger("a", "a", "a", 15));
        passengers2.add (new Passenger("a", "a", "a", 16));

        controller.getFlightController().getFlight().add_booking(new Booking(new Customer("x", "x"), controller.getFlightController().getFlight(), passengers2, BookingStatus.cancelled));

        //chiamo FlightInfo
        ArrayList<DisposableObject> callingObjects = new ArrayList<DisposableObject>();
        new Book (callingObjects, controller);
    }
}
