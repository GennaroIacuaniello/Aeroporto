package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class Home extends DisposableObject{
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

        controller.setCustomerNUser("Tramontana", new char[]{'1', '2'});
        controller.getFlightController().setFlight("01", "Compagnia", new Date(),
                "00:00", "00:00", 66);

        ArrayList<DisposableObject> callingObjects = new ArrayList<DisposableObject>();
        //callingObjects.add(this);
        new Book(callingObjects, controller, new Dimension(800, 800), new Point (0, 0));
    }

    public Home() {
        // Add action listeners or other initialization code here

    }

    @Override
    public JFrame getFrame() {
        return frameHome;
    }
}
