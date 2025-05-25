package gui;

import controller.Controller;
import model.Customer;
import model.User;
import model.Flight;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Book {

    private JFrame mainFrame;

    private JPanel topPanel;
    private TitlePanel titlePanel;
    private NavigatorBarPanel navigatorBarPanel;
    private HamburgerPanel hamburgerPanel;
    private UserPanel userPanel;
    private JPanel flightInfoPanel;

    private JPanel mainPanel;
    private JScrollPane scrollPane;
    private JPanel modifyPanel;
    private int currPage = 0;
    private JButton prevPageButton;
    private JButton nextPageButton;
    private JLabel currentPageLabel;

    private FooterPanel footerPanel;

    private Constraints constraints;

    public Book(ArrayList<JFrame> callingFrames, Controller controller, Customer customer, Flight flight) {

        super();

        constraints = new Constraints();

        //makes this the operating frame
        this.setMainframe(callingFrames);

        //setting top panels
        this.addTopPanel (callingFrames, controller, customer);

        //setting mainPanel
        this.addMainPanel (flight);

        //this.addFooterPanel();

        mainFrame.setVisible(true);
    }

    private void setMainframe(ArrayList<JFrame> callingFrames) {
        mainFrame = new JFrame("Book");
        callingFrames.addLast(mainFrame);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridBagLayout());
        mainFrame.setSize(1080, 720);

        mainFrame.setBackground(Color.YELLOW);
    }

    private void addTopPanel (ArrayList<JFrame> callingFrames, Controller controller, Customer customer)
    {
        topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        topPanel.setBackground(Color.WHITE);

        addTitlePanel("AEROPORTO DI NAPOLI");
        addNavigatorBarPanel (callingFrames);
        addHamburgerPanel(callingFrames, controller);
        addUserPanel(callingFrames, controller, customer);

        constraints.setConstraints (0, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START);
        mainFrame.add(topPanel, constraints.getConstraints());
        topPanel.setVisible (true);
    }

    private void addTitlePanel(String title) {
        titlePanel = new TitlePanel(title);
        constraints.setConstraints(0, 0, 2, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START);
        topPanel.add(titlePanel, constraints.getConstraints());
    }

    private void addNavigatorBarPanel (ArrayList<JFrame> callingFrames)
    {
        navigatorBarPanel = new NavigatorBarPanel (callingFrames);
        constraints.setConstraints (0, 1, 2, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.CENTER);
        topPanel.add (navigatorBarPanel, constraints.getConstraints ());
        navigatorBarPanel.setVisible (true);
    }

    private void addHamburgerPanel(ArrayList<JFrame> callingFrames, Controller controller) {
        hamburgerPanel = new HamburgerPanel(callingFrames, controller);
        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.FIRST_LINE_START);
        topPanel.add(hamburgerPanel, constraints.getConstraints());
        hamburgerPanel.setVisible (true);
    }

    private void addUserPanel(ArrayList<JFrame> callingFrames, Controller controller, Customer customer) {
        userPanel = new UserPanel(callingFrames, controller, customer);
        constraints.setConstraints(1, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.FIRST_LINE_END);
        topPanel.add(userPanel, constraints.getConstraints());
        userPanel.setVisible (true);
    }

    private void addMainPanel (Flight flight)
    {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.GREEN);

        addFlightInfoPanel (flight);

        ArrayList<PassengerPanel> passengerPanels = new ArrayList<PassengerPanel> ();
        ArrayList<JButton> removePassengerButtons = new ArrayList<JButton> ();
        JPanel passengerPage = new JPanel();
        passengerPage.setLayout(new GridBagLayout());

        PassengerPanel newPassenger = new PassengerPanel();
        JButton newremovePassengerButton = new JButton("RIMUOVI PASSEGGERO");
        newremovePassengerButton.setFocusable(false);

        constraints.setConstraints (0, 0, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END);
        passengerPage.add (newPassenger, constraints.getConstraints());
        passengerPanels.add (newPassenger);

        constraints.setConstraints (1, 0, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_START);
        passengerPage.add (newremovePassengerButton, constraints.getConstraints());
        removePassengerButtons.add (newremovePassengerButton);

        constraints.setConstraints (0, 1, 1, 1, GridBagConstraints.BOTH, 0, 0, GridBagConstraints.CENTER);
        mainPanel.add (passengerPage, constraints.getConstraints ());

        addModifyPanel (passengerPanels, removePassengerButtons, passengerPage);

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.PAGE_START, 1, 1);
        mainFrame.add (mainPanel, constraints.getConstraints());
        constraints.resetWeight ();
        mainFrame.setVisible (true);
    }

    private void addFlightInfoPanel(Flight flight)
    {
        flightInfoPanel = new JPanel();
        flightInfoPanel.setLayout(new GridBagLayout());
        flightInfoPanel.setBackground(Color.ORANGE);

        setLabels(flight);

        constraints.setConstraints (0, 0, 3, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START);

        mainPanel.add (flightInfoPanel, constraints.getConstraints());
        flightInfoPanel.setVisible(true);
    }

    private void setLabels(Flight flight)
    {
        setTitleLabels(flight);
        setInfoLabels(flight);
    }


    private void setTitleLabels(Flight flight) {

        ArrayList<JLabel> titleLabels = new ArrayList<JLabel>();

        titleLabels.add(new JLabel("   "));
        titleLabels.add(new JLabel("Company"));
        titleLabels.add(new JLabel("City"));
        titleLabels.add(new JLabel("Day"));
        titleLabels.add(new JLabel("Departure Time"));
        titleLabels.add(new JLabel("Arrival Time"));
        titleLabels.add(new JLabel("Duration"));
        titleLabels.add(new JLabel("Status"));
        titleLabels.add(new JLabel("Availability"));
        titleLabels.add(new JLabel("   "));

        for (int i = 0; i < titleLabels.size(); i++) {
            constraints.setConstraints(i, 0, 1, 1, GridBagConstraints.NONE,
                    0, 0, GridBagConstraints.CENTER);
            flightInfoPanel.add(titleLabels.get(i), constraints.getConstraints());
        }
    }


    private void setInfoLabels(Flight flight) {
        ArrayList<JLabel> infoLabels = new ArrayList<JLabel>();

        infoLabels.add(new JLabel("   "));
        infoLabels.add(new JLabel(flight.get_company_name()));
        infoLabels.add(new JLabel("/"));
        infoLabels.add(new JLabel(flight.get_date().toString()));
        infoLabels.add(new JLabel(flight.get_departure_time()));
        infoLabels.add(new JLabel(flight.get_arrival_time()));
        infoLabels.add(new JLabel("/"));
        infoLabels.add(new JLabel(flight.get_status().toString()));
        infoLabels.add(new JLabel("/"));
        infoLabels.add(new JLabel("   "));

        for (int i = 0; i < infoLabels.size(); i++) {
            constraints.setConstraints(i, 1, 1, 1, GridBagConstraints.NONE,
                    0, 0, GridBagConstraints.CENTER);
            flightInfoPanel.add(infoLabels.get(i), constraints.getConstraints());
        }
    }

    private void addAddPassengerButton (ArrayList<PassengerPanel> passengersPanels, ArrayList<JButton> removePassengerButtons , JPanel modifyPanel, JPanel passengerPage)
    {
        JButton addPassengerButton = new JButton("AGGIUNGI PASSEGGERO");
        addPassengerButton.setFocusable(false);

        addPassengerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                PassengerPanel newPassengerPanel = new PassengerPanel();
                constraints.setConstraints(0, (passengersPanels.size() % 3), 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END);
                passengerPage.add (newPassengerPanel, constraints.getConstraints());

                JButton newRemovePassengerButton = new JButton("RIMUOVI PASSEGGERO");
                constraints.setConstraints(1, (passengersPanels.size() % 3), 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_START);
                passengerPage.add (newRemovePassengerButton, constraints.getConstraints());

                if (currPage != (passengersPanels.size() - 1) / 3) //non siamo all'ultima pagina quindi sono 3
                {
                    for (int i = 0; i < 3; i++)
                    {
                        passengersPanels.get ((currPage * 3) + i).setVisible (false);
                        removePassengerButtons.get ((currPage * 3) + i).setVisible (false);
                    }

                    for (int i = passengersPanels.size() % 3; i > 0; i--) //aggiungo all'ultima pagina quindi non so quanti sono
                    {
                        passengersPanels.get ((passengersPanels.size() - i)).setVisible (true);
                        removePassengerButtons.get ((passengersPanels.size() - i)).setVisible (true);
                    }

                    currPage = passengersPanels.size() / 3;
                    currentPageLabel.setText(Integer.toString(currPage + 1));
                    if (currPage != 0) prevPageButton.setEnabled (true);

                } else //siamo all'ultima pagina quindi non so quanti sono
                {
                    if (passengersPanels.size () % 3 == 0) //l'ultima pagina è piena quindi ne creo un'altra
                    {
                        for (int i = 0; i < 3; i++)
                        {
                            passengersPanels.get ((currPage * 3) + i).setVisible (false);
                            removePassengerButtons.get ((currPage * 3) + i).setVisible (false);
                        }

                        currPage = passengersPanels.size() / 3;
                        currentPageLabel.setText(Integer.toString(currPage + 1));
                        if (currPage != 0) prevPageButton.setEnabled (true);

                    }
                }

                nextPageButton.setEnabled (false);

                passengersPanels.addLast (newPassengerPanel);
                removePassengerButtons.add (newRemovePassengerButton);

                passengerPage.setVisible (false);
                passengerPage.setVisible (true);
            }
        });

        modifyPanel.add (addPassengerButton);
    }

    private void addModifyPanel (ArrayList<PassengerPanel> passengerPanels, ArrayList<JButton> removePassengerButtons, JPanel passengerPage)
    {
        modifyPanel = new JPanel();
        modifyPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        modifyPanel.setBackground(Color.BLUE);

        addAddPassengerButton(passengerPanels, removePassengerButtons, modifyPanel, passengerPage);
        addPageChangeButtons (passengerPanels, removePassengerButtons, modifyPanel, passengerPage);


        JButton confirmButton = new JButton("CONFERMA");
        confirmButton.setFocusable(false);
        modifyPanel.add (confirmButton);

        constraints.setConstraints (0, 2, 2, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_END);
        mainPanel.add (modifyPanel, constraints.getConstraints());
        modifyPanel.setVisible (true);
    }

    private void addPageChangeButtons (ArrayList<PassengerPanel> passengerPanels, ArrayList<JButton> removePassengerButtons, JPanel modifyPanel, JPanel passengerPage)
    {
        prevPageButton = new JButton("←");
        nextPageButton = new JButton("→");
        currentPageLabel = new JLabel(Integer.valueOf(currPage + 1).toString());

        prevPageButton.setFocusable(false);
        nextPageButton.setFocusable(false);

        prevPageButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed (ActionEvent e)
                {
                    if (currPage != (passengerPanels.size() - 1) / 3) //non sto all'ultima pagina quindi sono 3
                    {
                        for (int i = 0; i < 3; i++)
                        {
                            passengerPanels.get ((currPage * 3) + i).setVisible (false);
                            removePassengerButtons.get ((currPage * 3) + i).setVisible (false);
                        }
                    } else //sto all'ultima pagina quindi non so quanti sono
                    {
                        for (int i = 0; i <= (passengerPanels.size() - 1) % 3; i++)
                        {
                            passengerPanels.get ((passengerPanels.size() - i - 1)).setVisible (false);
                            removePassengerButtons.get ((passengerPanels.size() - i - 1)).setVisible (false);
                        }
                    }

                    currPage--;
                    currentPageLabel.setText (Integer.valueOf(currPage + 1).toString());

                    for (int i = 0; i < 3; i++) //vado indietro quindi sono 3
                    {
                        passengerPanels.get ((currPage * 3) + i).setVisible (true);
                        removePassengerButtons.get ((currPage * 3) + i).setVisible (true);
                    }

                    nextPageButton.setEnabled (true);

                    if (currPage == 0) prevPageButton.setEnabled (false);
                }
            });

        prevPageButton.setEnabled (false);

        modifyPanel.add (prevPageButton);

        modifyPanel.add (currentPageLabel);

        nextPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                for (int i = 0; i < 3; i++) //metto a false la pagina corrente
                {
                    passengerPanels.get ((currPage * 3) + i).setVisible (false);
                    removePassengerButtons.get ((currPage * 3) + i).setVisible (false);
                }

                if (currPage + 1 == (passengerPanels.size() - 1) / 3) //sto andando all'ultima pagina quindi non so quanti sono
                {
                    for (int i = 0; i <= (passengerPanels.size() - 1) % 3; i++)
                    {
                        passengerPanels.get(passengerPanels.size() - i - 1).setVisible(true);
                        removePassengerButtons.get (passengerPanels.size() - i - 1).setVisible(true);
                    }

                    nextPageButton.setEnabled (false);
                } else //la prossima pagina ne ha 3
                {
                    for (int i = 0; i < 3; i++)
                    {
                        passengerPanels.get (((currPage + 1) * 3) + i).setVisible (true);
                        removePassengerButtons.get (((currPage + 1) * 3) + i).setVisible (true);
                    }
                }

                currPage++;
                currentPageLabel.setText (Integer.valueOf(currPage + 1).toString());
                prevPageButton.setEnabled (true);
            }
        });

        nextPageButton.setEnabled (false);
        modifyPanel.add (nextPageButton);
    }

    private void addFooterPanel()
    {
        footerPanel = new FooterPanel();
        constraints.setConstraints(0, 2, 3, 1, GridBagConstraints.BOTH,
                0, 75, GridBagConstraints.PAGE_END);
        //passengersPanel.add (footerPanel, constraints.getConstraints());
    }
}
