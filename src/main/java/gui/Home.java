package gui;

import controller.Controller;

import javax.swing.*;
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

        controller.setCustomerNUser("Tramontana", "1234");
        controller.getFlightController().setFlight("01", "Compagnia", new Date(),
                "00:00", "00:00", 66);

        ArrayList<DisposableObject> callingObjects = new ArrayList<DisposableObject>();

        new Book(callingObjects, controller);
    }

    public Home() {
        // Add action listeners or other initialization code here

    }

    @Override
    public void doOnDispose (ArrayList<DisposableObject> callingObjects, Controller controller) {}

    @Override
    public JFrame getFrame() {
        return frameHome;
    }
}
