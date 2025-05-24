package gui;

import model.Customer;
import controller.Controller;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SearchFlightCustomerMainFrame {

    private JFrame mainFrame;
    private TitlePanel titlePanel;
    private NavigatorBarPanel navigatorBarPanel;
    //private HamburgerPanel hamburgerPanel;
    private MenuPanel menu;
    private UserPanel userPanel;
    private FooterPanel footerPanel;
    private SearchPanel search_panel;
    Constraints constraints;


    public SearchFlightCustomerMainFrame(ArrayList<JFrame> callingFrames, Controller controller, Customer customer){

        super();

        constraints = new Constraints();

        //makes this the operating frame
        this.setMainFrame(callingFrames);
        //callingFrame.dispose();

        //Setting surrounding panels
        this.addTitlePanel("AEROPORTO DI NAPOLI");
        this.addNavigatorBarPanel(callingFrames);
        //this.addHamburgerPanel(callingFrames, controller);
        this.add_menu_panel(callingFrames, controller);
        this.addUserPanel(callingFrames, controller, customer);
        this.addFooterPanel();
        this.add_search_panel(callingFrames, controller);

        mainFrame.setVisible(true);
    }

    private void setMainFrame(ArrayList<JFrame> callingFrames){

        mainFrame = new JFrame("Cerca voli");
        callingFrames.addLast(mainFrame);
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
        titlePanel.setVisible(true);
    }

    private void addNavigatorBarPanel(ArrayList<JFrame> callingFrames){

        navigatorBarPanel = new NavigatorBarPanel(callingFrames);
        constraints.setConstraints(0, 1, 2, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.PAGE_START);
        mainFrame.add(navigatorBarPanel, constraints.getConstraints());
        navigatorBarPanel.setVisible(true);
    }

    //private void addHamburgerPanel(ArrayList<JFrame> callingFrames, Controller controller){

        //hamburgerPanel = new HamburgerPanel(callingFrames, controller);
        //constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.NONE,
        //        0, 0, GridBagConstraints.FIRST_LINE_START);
        //mainFrame.add(hamburgerPanel, constraints.getConstraints());
        //hamburgerPanel.setVisible(true);
    //}

    private void add_menu_panel(ArrayList<JFrame> callingFrames, Controller controller){

        menu = new MenuPanel(callingFrames, controller);
        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.FIRST_LINE_START);
        mainFrame.add(menu, constraints.getConstraints());
        menu.setVisible(true);
    }

    private void addUserPanel(ArrayList<JFrame> callingFrames, Controller controller, User customer){

        userPanel = new UserPanel(callingFrames, controller, customer);
        constraints.setConstraints(1, 2, 1, 1, GridBagConstraints.VERTICAL,
                0, 0, GridBagConstraints.LINE_END);
        mainFrame.add(userPanel, constraints.getConstraints());
        userPanel.setVisible(true);
    }

    private void addFooterPanel(){

        footerPanel = new FooterPanel ();
        constraints.setConstraints(0, 4, 3, 1, GridBagConstraints.BOTH,
                0, 75, GridBagConstraints.PAGE_END);
        mainFrame.add (footerPanel, constraints.getConstraints());
        footerPanel.setVisible(true);
    }

    private void add_search_panel(ArrayList<JFrame> callingFrames, Controller controller){

        search_panel = new SearchPanel(callingFrames, controller);
        constraints.setConstraints(0, 3, 3, 1, GridBagConstraints.HORIZONTAL,
                0, 75, GridBagConstraints.CENTER);

        mainFrame.add (search_panel, constraints.getConstraints());
        search_panel.setVisible(true);
    }

}
