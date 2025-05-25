package gui;

import controller.Controller;
import model.Customer;
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
    private MenuPanelCustomer hamburgerPanel;
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
        this.setMainframe(callingFrames, controller);

        //setting top panels
        this.addTopPanel (callingFrames, controller, customer);

        //setting mainPanel
        this.addMainPanel (flight, controller);

        //this.addFooterPanel();
        addFooterPanel();

        mainFrame.setVisible(true);
    }

    private void setMainframe(ArrayList<JFrame> callingFrames, Controller controller) {
        mainFrame = new JFrame("Book");
        callingFrames.addLast(mainFrame);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridBagLayout());
        mainFrame.setSize(1080, 720);

        if(controller.developerMode) mainFrame.setBackground(Color.YELLOW);
    }

    private void addTopPanel (ArrayList<JFrame> callingFrames, Controller controller, Customer customer)
    {
        topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());

        addTitlePanel("AEROPORTO DI NAPOLI", controller);
        addNavigatorBarPanel (callingFrames);
        addHamburgerPanel(callingFrames, controller, customer);
        addUserPanel(callingFrames, controller, customer);

        constraints.setConstraints (0, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START);
        mainFrame.add(topPanel, constraints.getConstraints());
        topPanel.setVisible (true);
    }

    private void addTitlePanel(String title, Controller controller) {
        titlePanel = new TitlePanel(title, controller);
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

    private void addHamburgerPanel(ArrayList<JFrame> callingFrames, Controller controller, Customer customer) {
        hamburgerPanel = new MenuPanelCustomer(callingFrames, controller, customer);
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

    private void addMainPanel (Flight flight, Controller controller)
    {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        /*if(controller.developerMode)*/ //mainPanel.setBackground(Color.WHITE);

        addFlightInfoPanel (flight, controller);

        ArrayList<PassengerPanel> passengerPanels = new ArrayList<PassengerPanel> ();
        ArrayList<RemovePassengerButton> removePassengerButtons = new ArrayList<RemovePassengerButton> ();
        JPanel passengerPage = new JPanel();
        passengerPage.setLayout(new GridBagLayout());

        PassengerPanel newPassenger = new PassengerPanel(controller);
        RemovePassengerButton newremovePassengerButton = new RemovePassengerButton(this, controller, passengerPanels, removePassengerButtons, 0, passengerPage, currPage, nextPageButton);
        newremovePassengerButton.setFocusable(false);
        newremovePassengerButton.setEnabled(false);

        constraints.setConstraints (0, 0, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END);
        passengerPage.add (newPassenger, constraints.getConstraints());
        passengerPanels.add (newPassenger);

        constraints.setConstraints (1, 0, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_START);
        passengerPage.add (newremovePassengerButton, constraints.getConstraints());
        removePassengerButtons.add (newremovePassengerButton);

        constraints.setConstraints (0, 1, 1, 1, GridBagConstraints.BOTH, 0, 0, GridBagConstraints.CENTER);
        mainPanel.add (passengerPage, constraints.getConstraints ());

        addModifyPanel (passengerPanels, removePassengerButtons, passengerPage, controller);

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.PAGE_START, 1, 1);
        mainFrame.add (mainPanel, constraints.getConstraints());
        constraints.resetWeight ();
        mainFrame.setVisible (true);
    }

    private void addFlightInfoPanel(Flight flight, Controller controller)
    {
        flightInfoPanel = new JPanel();
        flightInfoPanel.setLayout(new GridBagLayout());
        /*if(controller.developerMode)*/ flightInfoPanel.setBackground(Color.GRAY);

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

    private void addModifyPanel (ArrayList<PassengerPanel> passengerPanels, ArrayList<RemovePassengerButton> removePassengerButtons, JPanel passengerPage, Controller controller)
    {
        modifyPanel = new JPanel();
        modifyPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        if(controller.developerMode) modifyPanel.setBackground(Color.BLUE);

        addAddPassengerButton(this, passengerPanels, removePassengerButtons, modifyPanel, passengerPage, controller);
        addPageChangeButtons (passengerPanels, removePassengerButtons, modifyPanel, passengerPage);


        JButton confirmButton = new JButton("CONFERMA");
        confirmButton.setFocusable(false);
        modifyPanel.add (confirmButton);

        constraints.setConstraints (0, 2, 2, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_END);
        mainPanel.add (modifyPanel, constraints.getConstraints());
        modifyPanel.setVisible (true);
    }

    private void addAddPassengerButton (Book book, ArrayList<PassengerPanel> passengersPanels, ArrayList<RemovePassengerButton> removePassengerButtons , JPanel modifyPanel, JPanel passengerPage, Controller controller)
    {
        JButton addPassengerButton = new JButton("AGGIUNGI PASSEGGERO");
        addPassengerButton.setFocusable(false);

        addPassengerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                PassengerPanel newPassengerPanel = new PassengerPanel(controller);
                constraints.setConstraints(0, (passengersPanels.size() % 3), 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END);
                passengerPage.add (newPassengerPanel, constraints.getConstraints());

                RemovePassengerButton newRemovePassengerButton = new RemovePassengerButton(book, controller, passengersPanels, removePassengerButtons, removePassengerButtons.size(), passengerPage, currPage, nextPageButton);
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

                removePassengerButtons.getFirst().setEnabled(true);
            }
        });

        modifyPanel.add (addPassengerButton);
    }

    private void addPageChangeButtons (ArrayList<PassengerPanel> passengerPanels, ArrayList<RemovePassengerButton> removePassengerButtons, JPanel modifyPanel, JPanel passengerPage)
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
        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.BOTH,
                0, 10, GridBagConstraints.PAGE_END);
        mainFrame.add (footerPanel, constraints.getConstraints());
    }

    public void decreaseCurrPage () {
        currPage--;
        currentPageLabel.setText (Integer.valueOf(currPage + 1).toString());

        if (currPage == 0) prevPageButton.setEnabled (false);
    }
}
