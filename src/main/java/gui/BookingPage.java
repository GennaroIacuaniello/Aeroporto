package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BookingPage extends DisposableObject {

    protected JFrame mainFrame;

        protected JPanel topPanel;

            protected TitlePanel titlePanel;
            protected NavigatorBarPanel navigatorBarPanel;
            protected MenuPanelCustomer menuPanel;
            protected UserPanel userPanel;

        protected JPanel mainPanel;

            protected JPanel flightInfoPanel;

                protected JTable flightInfoTable;

            protected JPanel searchPanel;

                protected JTextField searchField;
                protected JButton searchButton;

            protected JPanel passengerPage;

                protected ArrayList<PassengerPanel> passengerPanels;

            protected JPanel modifyPanel;

                protected int currPage = 0;
                protected JButton prevPageButton;
                protected JButton nextPageButton;
                protected JLabel currentPageLabel;

            protected JPanel confirmPanel;

        protected FooterPanel footerPanel;

    protected Constraints constraints;

    public BookingPage (ArrayList<DisposableObject> callingObjects, Controller controller,
                        Dimension dimension, Point point, int fullScreen) {

        super();

        constraints = new Constraints();

        //makes this the operating frame
        setMainFrame(callingObjects, controller, dimension, point, fullScreen, "BookingPage");

        //setting top panels
        addTopPanel(callingObjects, controller);

        //setting mainPanel
        addMainPanel(callingObjects, controller);

        //this.addFooterPanel();
        addFooterPanel();

        mainFrame.setVisible(true);
    }

    public BookingPage () {}

    protected void setMainFrame (ArrayList<DisposableObject> callingObjects, Controller controller, Dimension dimension, Point point, int fullScreen, String title) {

        mainFrame = new JFrame(title);

        mainFrame.setSize(dimension);
        mainFrame.setLocation(point);
        mainFrame.setExtendedState(fullScreen);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainFrame.setLayout(new GridBagLayout());

        callingObjects.addLast(this);
    }

    protected void addTopPanel (ArrayList<DisposableObject> callingObjects, Controller controller) {

        topPanel = new JPanel();

        topPanel.setLayout(new GridBagLayout());

        addTitlePanel("AEROPORTO DI NAPOLI", controller);
        addNavigatorBarPanel (callingObjects, controller);
        addMenuPanel(callingObjects, controller);
        addUserPanel(callingObjects, controller);

        constraints.setConstraints (0, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START);
        mainFrame.add(topPanel, constraints.getConstraints());

        topPanel.setVisible(true);
    }

    protected void addTitlePanel (String title, Controller controller) {

        titlePanel = new TitlePanel(title, controller);

        constraints.setConstraints(0, 0, 2, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START);
        topPanel.add(titlePanel, constraints.getConstraints());

        titlePanel.setVisible(true);
    }

    protected void addNavigatorBarPanel (ArrayList<DisposableObject> callingObjects, Controller controller) {

        navigatorBarPanel = new NavigatorBarPanel (callingObjects, controller);

        constraints.setConstraints (0, 1, 2, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.CENTER);
        topPanel.add (navigatorBarPanel, constraints.getConstraints ());

        navigatorBarPanel.setVisible (true);
    }

    protected void addMenuPanel (ArrayList<DisposableObject> callingObjects, Controller controller) {

         menuPanel = new MenuPanelCustomer(callingObjects, controller);

        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.FIRST_LINE_START);
        topPanel.add(menuPanel, constraints.getConstraints());

        menuPanel.setVisible (true);
    }

    protected void addUserPanel(ArrayList<DisposableObject> callingObjects, Controller controller) {

        userPanel = new UserPanel(callingObjects, controller);

        constraints.setConstraints(1, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.FIRST_LINE_END);
        topPanel.add(userPanel, constraints.getConstraints());

        userPanel.setVisible (true);
    }

    protected void addMainPanel (ArrayList<DisposableObject> callingObjects, Controller controller) {

        mainPanel = new JPanel();

        mainPanel.setLayout(new GridBagLayout());

        addFlightInfoPanel (controller);
        addSearchPanel (controller);
        addPassengerPage (controller);
        addModifyPanel (callingObjects , controller);
        addConfirmPanel (callingObjects, controller);

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.PAGE_START, 1, 1);
        mainFrame.add (mainPanel, constraints.getConstraints());

        mainPanel.setVisible (true);
    }

    protected void addFlightInfoPanel (Controller controller) {

        flightInfoPanel = new JPanel(new BorderLayout());

        String[] columnNames = {"COMPANY", "CITY", "DATE", "DEPARTURE TIME", "ARRIVAL TIME", "DURATION", "STATUS", "FREE SEATS"};

        Object[][] data = new Object[1][columnNames.length];

        data[0][0] = controller.getFlightController().getCompanyName();
        data[0][1] = controller.getFlightController().getCity();
        data[0][2] = controller.getFlightController().getDateString();
        data[0][3] = controller.getFlightController().getDepartureTime();
        data[0][4] = controller.getFlightController().getArrivalTime();
        data[0][5] = "da implementare";
        data[0][6] = controller.getFlightController().getFlightStatus();
        data[0][7] = controller.getFlightController().getFreeSeats();

        flightInfoTable = new JTable(data, columnNames);
        flightInfoTable.setEnabled(false);

        flightInfoPanel.add(flightInfoTable.getTableHeader(), BorderLayout.NORTH);
        flightInfoPanel.add(flightInfoTable, BorderLayout.CENTER);

        constraints.setConstraints(0, 0, 1, 1,
                GridBagConstraints.HORIZONTAL, 0, 0, GridBagConstraints.CENTER);
        mainPanel.add(flightInfoPanel, constraints.getConstraints());
    }

    protected void addSearchPanel (Controller controller) {}

    protected void addPassengerPage (Controller controller) {

        passengerPage = new JPanel();

        passengerPage.setLayout(new GridBagLayout());

        passengerPanels = new ArrayList<PassengerPanel> ();

        insertPassengers(controller);

        constraints.setConstraints (0, 2, 1, 1,
                GridBagConstraints.BOTH, 0, 0, GridBagConstraints.CENTER, 0.9f, 0.9f);
        mainPanel.add (passengerPage, constraints.getConstraints());
    }

    protected void insertPassengers (Controller controller) {

        for (int j = 0; j < controller.getFlightController().getBookingsSize(); j++) {

            if (controller.getFlightController().checkBookingConfirm(j)) {

                for (int i = 0; i < controller.getFlightController().getBookingSize(j); i++) {
                    PassengerPanel passengerPanel = new PassengerPanelAdmin(controller, passengerPanels);

                    passengerPanel.setPassengerName(controller.getFlightController().getPassengerNameFromBooking(j, i));
                    passengerPanel.setPassengerSurname(controller.getFlightController().getPassengerSurnameFromBooking(j, i));
                    passengerPanel.setPassengerCF(controller.getFlightController().getPassengerCFFromBooking(j, i));
                    passengerPanel.setSeat(controller.getFlightController().getPassengerSeatFromBooking(j, i));
                    passengerPanel.setTicketNumber(controller.getFlightController().getPassengerTicketNumberFromBooking(j, i));
                    passengerPanel.setLuggagesTypes(controller.getFlightController().getPassengerLuggagesTypesFromBooking(j, i), controller);

                    insertPassengerPanel(controller, passengerPanel);
                }
            }
        }
    }

    protected void insertPassengerPanel (Controller controller, PassengerPanel passengerPanel) {

        constraints.setConstraints(0, (passengerPanels.size() % 3), 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        passengerPage.add(passengerPanel, constraints.getConstraints());

        passengerPanel.setPanelEnabled(false);

        passengerPanels.addLast(passengerPanel);

        passengerPanel.setVisible(passengerPanels.size() < 4);
    }

    private void addModifyPanel (ArrayList<DisposableObject> callingObjects, Controller controller) {

        modifyPanel = new JPanel();

        modifyPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        addPageChangeButtons();

        constraints.setConstraints (0, 3, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.CENTER);
        mainPanel.add (modifyPanel, constraints.getConstraints());
        modifyPanel.setVisible (true);
    }

    protected void addPageChangeButtons () {

        //inizializzo componenti
        prevPageButton = new JButton("←");
        nextPageButton = new JButton("→");
        currentPageLabel = new JLabel(Integer.valueOf(currPage + 1).toString());

        prevPageButton.setFocusable(false);
        nextPageButton.setFocusable(false);

        //aggiungo action listeners
        prevPageButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {

                goNextPage();
            }
        });

        nextPageButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {

                goPreviousPage();
            }
        });

        prevPageButton.setEnabled (false);
        nextPageButton.setEnabled(passengerPanels.size() > 3);

        //aggiungo bottoni
        modifyPanel.add (prevPageButton);
        modifyPanel.add (currentPageLabel);
        modifyPanel.add (nextPageButton);
    }

    protected void goNextPage () {

        if (currPage != (passengerPanels.size() - 1) / 3) //non sto all'ultima pagina quindi sono 3
        {
            for (int i = 0; i < 3; i++)
            {
                passengerPanels.get ((currPage * 3) + i).setVisible (false);
            }
        } else //sto all'ultima pagina quindi non so quanti sono
        {
            for (int i = 0; i <= (passengerPanels.size() - 1) % 3; i++)
            {
                passengerPanels.get ((passengerPanels.size() - i - 1)).setVisible (false);
            }
        }

        currPage--;
        currentPageLabel.setText (Integer.valueOf(currPage + 1).toString());

        for (int i = 0; i < 3; i++) //vado indietro quindi sono 3
        {
            passengerPanels.get ((currPage * 3) + i).setVisible (true);
        }

        nextPageButton.setEnabled (true);

        if (currPage == 0) prevPageButton.setEnabled (false);
    }

    protected void goPreviousPage () {

        for (int i = 0; i < 3; i++) //metto a false la pagina corrente
            passengerPanels.get ((currPage * 3) + i).setVisible (false);

        if (currPage + 1 == (passengerPanels.size() - 1) / 3) //sto andando all'ultima pagina quindi non so quanti sono
        {
            for (int i = 0; i <= (passengerPanels.size() - 1) % 3; i++)
                passengerPanels.get(passengerPanels.size() - i - 1).setVisible(true);

            nextPageButton.setEnabled (false);
        } else //la prossima pagina ne ha 3
        {
            for (int i = 0; i < 3; i++)
                passengerPanels.get (((currPage + 1) * 3) + i).setVisible (true);
        }

        currPage++;
        currentPageLabel.setText (Integer.valueOf(currPage + 1).toString());
        prevPageButton.setEnabled (true);
    }

    protected void addConfirmPanel (ArrayList<DisposableObject> callingObjects, Controller controller) {
        confirmPanel = new JPanel();

        //pulsanti per modifica
    }

    protected void addFooterPanel () {

    }

    @Override
    public void doOnDispose (ArrayList<DisposableObject> callingObjects, Controller controller) {
        controller.getFlightController().setFlight(null);
        controller.getBookingController().setBooking(null);

        for (PassengerPanel passengerPanel : passengerPanels) {
            if (passengerPanel.getSeatChooser() != null) passengerPanel.getSeatChooser().dispose();

            if (passengerPanel.getLuggagesView() != null) passengerPanel.getLuggagesView().dispose();
        }
    }

    @Override
    public JFrame getFrame() {

        return this.mainFrame;
    }
}
