package gui;

import controller.Controller;
import model.Flight;
import model.User;

import javax.swing.*;
import java.util.Date;

public class Home {
    private JPanel mainPanel;
    private static JFrame frameHome;
    private static Controller controller;

    public static void main(String[] args){
        controller = new Controller();
        frameHome = new JFrame("Home");
        frameHome.setContentPane(new Home().mainPanel);
        frameHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameHome.pack();
        frameHome.setVisible(true);

        User user = new User("Tramontana", "1234");
        Flight flight = new Flight("01", "Compagnia", new Date(),
                "00:00", "00:00", 100);

        new Book(frameHome, controller, user, flight);
    }

    public Home(){
        // Add action listeners or other initialization code here

    }


}
