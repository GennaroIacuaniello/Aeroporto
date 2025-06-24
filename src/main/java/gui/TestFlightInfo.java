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
        passengers.add (new Passenger("carmela", "tedesco", "uio", 14));
        passengers.add (new Passenger("sara", "zumbolo", "asd", 57));
        passengers.add (new Passenger("carla", "chirico", "fgh", 26));
        passengers.add (new Passenger("chiara", "russo", "jkl", 32));
        passengers.add (new Passenger("rosa", "manzo", "zxc", 15));

        //creo volo
        flight = new Flight("03", "che ne so", (java.sql.Date) new Date(2025, 2, 14), "12:25",
                "14:10", 10);

        //creo prenotazione
        bookingTest = new Booking(new Customer("pippo", "pluto"), flight, passengers);

        //aggiungo prenotazione al volo
        flight.add_booking(bookingTest);

        //chiamo FlightInfo
        ArrayList<JFrame> callingFrames = new ArrayList<JFrame>();
        callingFrames.add(frame);
        new FlightInfo (callingFrames, controller, bookingTest);
    }
}
