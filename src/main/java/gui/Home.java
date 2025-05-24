package gui;

import controller.Controller;
import model.Customer;
import model.Flight;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class Home {
    private JPanel mainPanel;
    private static JFrame frameHome;
    private static Controller controller;

    public static void main(String[] args) {
        controller = new Controller();
        frameHome = new JFrame("Home");
        frameHome.setContentPane(new Home().mainPanel);
        frameHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameHome.pack();
        frameHome.setVisible(true);

        Customer customer = new Customer("Tramontana", "1234");
        Flight flight = new Flight("01", "Compagnia", new Date(),
                "00:00", "00:00", 100);

        ArrayList<JFrame> callingFrames = new ArrayList<JFrame>();
        callingFrames.add(frameHome);
        new Book(callingFrames, controller, customer, flight);
    }

    public Home() {
        // Add action listeners or other initialization code here

    }
}
