package gui;

import controller.Controller;
import model.Customer;
import model.User;
import model.Flight;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
 
public class Book
{

    private JFrame mainFrame;

    private TitlePanel titlePanel;
    private NavigatorBarPanel navigatorBarPanel;
    private HamburgerPanel hamburgerPanel;
    private UserPanel userPanel;

    private JPanel mainPanel;
    private JPanel flightInfoPanel;
    private JPanel passengersPanel;

    private FooterPanel footerPanel;

    private Constraints constraints;

    public Book (ArrayList<JFrame> callingFrames, Controller controller, Customer customer, Flight flight){

        super();

        constraints = new Constraints();

        //makes this the operating frame
        this.setMainframe(callingFrames);
        //callingFrame.dispose();

        //setting surrounding panels
        this.addTitlePanel("AEROPORTO DI NAPOLI");
        this.addNavigatorBarPanel (callingFrames);
        this.addHamburgerPanel(callingFrames, controller);
        this.addUserPanel(callingFrames, controller, customer);
        this.addFooterPanel();
      
        //setting main panels
        this.addFlightInfoPanel(flight);
        this.addPassengersPanel();

        mainFrame.setVisible(true);
    }

    private void setMainframe(ArrayList<JFrame> callingFrames)
    {
        mainFrame = new JFrame("Book");
        callingFrames.addLast (mainFrame);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridBagLayout ());
        mainFrame.setSize(1080, 720);
        mainFrame.setBackground(Color.BLACK);
    }

    private void addTitlePanel(String title)
    {
        titlePanel = new TitlePanel(title);
        constraints.setConstraints(0, 0, 3, 1, GridBagConstraints.BOTH,
                0, 125, GridBagConstraints.PAGE_START);
        mainFrame.add(titlePanel, constraints.getConstraints());
    }

    private void addNavigatorBarPanel (ArrayList<JFrame> callingFrames)
    {
        navigatorBarPanel = new NavigatorBarPanel (callingFrames);
        constraints.setConstraints (0, 1, 3, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.CENTER);
        mainFrame.add (navigatorBarPanel, constraints.getConstraints ());
        navigatorBarPanel.setVisible (true);
    }

    private void addHamburgerPanel(ArrayList<JFrame> callingFrames, Controller controller)
    {
        hamburgerPanel = new HamburgerPanel(callingFrames, controller);
        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.FIRST_LINE_START);
        mainFrame.add(hamburgerPanel, constraints.getConstraints());
    }

    private void addUserPanel(ArrayList<JFrame> callingFrames, Controller controller, Customer customer)
    {
        userPanel = new UserPanel(callingFrames, controller, customer);
        constraints.setConstraints(2, 2, 1, 1, GridBagConstraints.VERTICAL,
                0, 0, GridBagConstraints.LINE_END);
        mainFrame.add(userPanel, constraints.getConstraints());
    }

    private void addFlightInfoPanel(Flight flight){
        
        flightInfoPanel = new JPanel();
        flightInfoPanel.setLayout(new GridBagLayout());
        flightInfoPanel.setBackground(Color.ORANGE);

        setLabels(flight);

        constraints.setConstraints (0, 3, 3, 1, GridBagConstraints.BOTH, 0, 60, GridBagConstraints.PAGE_START);        

        flightInfoPanel.setVisible(true);
        mainFrame.add (flightInfoPanel, constraints.getConstraints());
    }

    private void setLabels(Flight flight){
        
        setTitleLabels(flight);
        setInfoLabels(flight);
    }

    private void setTitleLabels(Flight flight){
        
        ArrayList<JLabel> titleLabels = new ArrayList<JLabel>();

        titleLabels.add(new JLabel("   "));
        titleLabels.add(new JLabel("Company"));
        titleLabels.add(new JLabel("City"));
        titleLabels.add(new JLabel("Day"));
        titleLabels.add(new JLabel("Departure Time"));
        titleLabels.add(new JLabel("Arrival Time"));
        titleLabels.add(new JLabel("Duration"));
        titleLabels.add(new JLabel("Status"));
        titleLabels.add(new JLabel("Disponibility"));
        titleLabels.add(new JLabel("   "));

        for(int i = 0; i < titleLabels.size(); i++){
            constraints.setConstraints(i, 0, 1, 1, GridBagConstraints.NONE,
                    0, 0, GridBagConstraints.CENTER);
            flightInfoPanel.add(titleLabels.get(i), constraints.getConstraints());
        }
    }

    private void setInfoLabels (Flight flight){
        ArrayList<JLabel> infoLabels = new ArrayList<JLabel>();

        infoLabels.add(new JLabel("   "));
        infoLabels.add(new JLabel(flight.get_company_name()));
        infoLabels.add(new JLabel("/"));
        infoLabels.add(new JLabel(flight.get_date ().toString()));
        infoLabels.add(new JLabel(flight.get_departure_time()));
        infoLabels.add(new JLabel(flight.get_arrival_time()));
        infoLabels.add(new JLabel("/"));
        infoLabels.add(new JLabel(flight.get_status().toString()));
        infoLabels.add(new JLabel("/"));
        infoLabels.add(new JLabel("   "));

        for (int i = 0; i < infoLabels.size(); i++){
            constraints.setConstraints(i, 1, 1, 1, GridBagConstraints.NONE,
                    0, 0, GridBagConstraints.CENTER);
            flightInfoPanel.add (infoLabels.get(i), constraints.getConstraints ());
        }
    }

    private void addPassengersPanel(){
        
        passengersPanel = new JPanel();
        passengersPanel.setLayout(new GridBagLayout());
        passengersPanel.setBackground(Color.BLUE);

        constraints.setConstraints (0, 4, 3, 1, GridBagConstraints.BOTH, 0, 500, GridBagConstraints.PAGE_START);

        passengersPanel.setVisible(true);
        mainFrame.add(passengersPanel, constraints.getConstraints());
    }

    private void addFooterPanel(){
        
        footerPanel = new FooterPanel();
        constraints.setConstraints(0, 5, 3, 1, GridBagConstraints.BOTH,
                0, 75, GridBagConstraints.PAGE_END);
        mainFrame.add (footerPanel, constraints.getConstraints());
    }
}
