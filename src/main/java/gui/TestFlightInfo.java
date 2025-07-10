package gui;

import com.formdev.flatlaf.FlatLightLaf;
import controller.Controller;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;

public class TestFlightInfo {

    private static JFrame frame;
    private static Controller controller;
    private static Booking bookingTest;
    private static Flight flight;
    private static ArrayList<Passenger> passengers;

    public static void main(String[] args) throws InvalidPassengerNumber {

        try{
            UIManager.setLookAndFeel(new FlatLightLaf());
        }
        catch (UnsupportedLookAndFeelException e){
            String[] options = {"Continua", "Chiudi"};
            int action = JOptionPane.showOptionDialog(null,  "<html><center>Il tuo device non supporta FlatLaf,<br>" +
                            "utilizzerai un'altra versione dell'app,<br>" +
                            "tutte le funzioni rimarranno invariate.</center></html>",
                    "Title", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, null);
            if(action == 1 || action == JOptionPane.CLOSED_OPTION) {
                return;
            }
        }

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

        passengers.getFirst().add_luggage(new Luggage(LuggageType.carry_on, passengers.getFirst(), 1));
        passengers.getFirst().set_check_in(true);

        for (Passenger passenger : passengers) {
            passenger.set_Ticket_number(Integer.valueOf(passenger.get_First_name().hashCode()).toString());
        }

        //sistemo il controller
        controller.getFlightController().setDepartingFlight("03", "che ne so", new Date(2025, 2, 14), new Time(1,1,1),
                new Time(1,1,1), FlightStatus.programmed, 19, 10, "Barcellona", 0);

        controller.getFlightController().setFlightStatus(FlightStatus.programmed);

        controller.getBookingController().setBooking(new Customer("pippo", "notAnActualHash"), controller.getFlightController().getFlight(), passengers);

        Booking booking = new Booking(new Customer("pippo", "thisShouldBeAHash"), controller.getFlightController().getFlight(), passengers);
        booking.set_status(BookingStatus.confirmed);

        controller.getFlightController().getFlight().addBooking(booking);

        controller.setCustomerNUser("pippo", "aren'tWeUsingHashesNow?");

        ArrayList<Passenger> passengers2 = new ArrayList<Passenger>();
        passengers2.add(new Passenger("a", "a", "a", 14));
        passengers2.add(new Passenger("a", "a", "a", 15));
        passengers2.add(new Passenger("a", "a", "a", 16));

        controller.getFlightController().getFlight().addBooking(new Booking(new Customer("x", "shouldPutAHashHere"), controller.getFlightController().getFlight(), passengers2, BookingStatus.pending));

        //chiamo FlightInfo
        ArrayList<DisposableObject> callingObjects = new ArrayList<DisposableObject>();
        new BookingPageCustomer (callingObjects, controller, new Dimension(800, 800), new Point(10, 10), JFrame.MAXIMIZED_BOTH);
    }

}
