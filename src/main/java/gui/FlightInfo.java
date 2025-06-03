package gui;

import controller.Controller;
import model.Customer;
import model.Flight;

import javax.swing.*;
import java.util.ArrayList;

public class FlightInfo {

    private JFrame mainFrame;

    private JPanel topPanel;
    private TitlePanel titlePanel;
    private NavigatorBarPanel navigatorBarPanel;
    private MenuPanelCustomer hamburgerPanel;
    private UserPanel userPanel;
    private JPanel flightInfoPanel;

    private JPanel mainPanel;
    private JPanel modifyPanel;
    private int currPage = 0;
    private JButton prevPageButton;
    private JButton nextPageButton;
    private JLabel currentPageLabel;

    private FooterPanel footerPanel;

    private Constraints constraints;

    public FlightInfo(ArrayList<JFrame> callingFrames, Controller controller, Customer customer, Flight flight) {

        super();

        constraints = new Constraints();

        //makes this the operating frame
        this.setMainframe(callingFrames, controller);

        //setting top panels
        this.addTopPanel (callingFrames, controller, customer);

        //setting mainPanel
        this.addMainPanel (flight, controller);

        //this.addFooterPanel();
        addFooterPanel();

        mainFrame.setVisible(true);
    }
}
