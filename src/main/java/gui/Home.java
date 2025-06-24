package gui;

import controller.Controller;

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

        controller.getCustomerController().setCustomer("Tramontana", "1234");
        controller.getUserController().setUser("Tramontana", "1234");
        controller.getFlightController().setFlight("01", "Compagnia", new Date(),
                "00:00", "00:00", 66);

        ArrayList<JFrame> callingFrames = new ArrayList<JFrame>();
        callingFrames.add(frameHome);
        new Book(callingFrames, controller);
    }

    public Home() {
        // Add action listeners or other initialization code here

    }
}
