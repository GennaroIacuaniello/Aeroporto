package gui;

import model.Customer;
import controller.Controller;
import model.User;

import javax.swing.*;
import java.awt.*;

public class MainCustomerScreen{

    private JFrame mainFrame;
    private TitlePanel titlePanel;
    private HamburgerPanel hamburgerPanel;
    private UserPanel userPanel;
    private FooterPanel footerPanel;
    Constraints constraints;

    public MainCustomerScreen(JFrame callingFrame, Controller controller, Customer customer){

        super();

        constraints = new Constraints();

        //makes this the operating frame
        this.setMainFrame();
        callingFrame.setVisible(false);
        callingFrame.dispose();

        //Setting surrounding panels
        this.addTitlePanel("AEROPORTO DI NAPOLI");
        this.addHamburgerPanel(mainFrame, controller);
        this.addUserPanel(mainFrame, controller, customer);
        this.addFooterPanel();

        mainFrame.setVisible(true);
    }

    private void setMainFrame(){

        mainFrame = new JFrame("Home");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridBagLayout());
        mainFrame.setSize(1080, 720);
        mainFrame.setBackground(Color.BLACK);
    }

    private void addTitlePanel(String title){

        titlePanel = new TitlePanel(title);
        constraints.setConstraints(0, 0, 2, 1, GridBagConstraints.BOTH,
                0, 125, GridBagConstraints.PAGE_START);
        mainFrame.add(titlePanel, constraints.getConstraints());
    }

    private void addHamburgerPanel(JFrame callingFrame, Controller controller){

        hamburgerPanel = new HamburgerPanel(callingFrame, controller);
        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.FIRST_LINE_START);
        mainFrame.add(hamburgerPanel, constraints.getConstraints());
    }

    private void addUserPanel(JFrame callingFrame, Controller controller, User customer){

        userPanel = new UserPanel(callingFrame, controller, customer);
        constraints.setConstraints(1, 1, 1, 1, GridBagConstraints.VERTICAL,
                0, 0, GridBagConstraints.LINE_END);
        mainFrame.add(userPanel, constraints.getConstraints());
    }

    private void addFooterPanel(){

        footerPanel = new FooterPanel ();
        constraints.setConstraints(0, 5, 3, 1, GridBagConstraints.BOTH,
                0, 75, GridBagConstraints.PAGE_END);
        mainFrame.add (footerPanel, constraints.getConstraints());
    }

}
