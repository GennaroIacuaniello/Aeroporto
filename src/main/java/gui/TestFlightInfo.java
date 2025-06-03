package gui;

import controller.Controller;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class TestFlightInfo {

    private static JFrame frame;
    private static Controller controller;
    private static Booking bookingTest;
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

        passengers.add (new Passenger("anna", "marino", "qwe", "3a"));
        passengers.add (new Passenger("maria", "giglio", "rty", "1b"));
        passengers.add (new Passenger("carmela", "tedesco", "uio", "4c"));
        passengers.add (new Passenger("sara", "zumbolo", "asd", "7l"));
        passengers.add (new Passenger("carla", "chirico", "fgh", "6d"));
        passengers.add (new Passenger("chiara", "russo", "jkl", "2a"));
        passengers.add (new Passenger("rosa", "manzo", "zxc", "1y"));


        //creo prenotazione
        bookingTest = new Booking(new Customer("pippo", "pluto"),
                                  new Flight("03", "che ne so", new Date(2025, 2, 14), "12:25",
                                             "14:10", 90),
                                  passengers);

        //chiamo FlightInfo
        ArrayList<JFrame> callingFrames = new ArrayList<JFrame>();
        callingFrames.add(frame);
        new FlightInfo (callingFrames, controller, bookingTest);
    }
}
