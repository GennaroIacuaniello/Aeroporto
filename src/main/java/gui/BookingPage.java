package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

public abstract class BookingPage extends DisposableObject {

    protected JFrame mainFrame;

        protected JPanel topPanel;

            protected TitlePanel titlePanel;
            protected NavigatorBarPanel navigatorBarPanel;
            protected UserPanel userPanel;

        protected JPanel mainPanel;

            protected JPanel flightInfoPanel;

                protected JTable flightInfoTable;

            protected JPanel searchPanel;

                protected JTextField searchField;
                protected JButton searchButton;

            protected JPanel passengerPage;

                protected ArrayList<Integer> bookedSeats;
                protected ArrayList<PassengerPanel> passengerPanels;

            protected JPanel modifyPanel;

                protected JPanel flowPanel;

                    protected int currPage = 0;
                    protected JButton prevPageButton;
                    protected JButton nextPageButton;
                    protected JLabel currentPageLabel;

    protected Constraints constraints;
    protected boolean controllerDisposeFlag = true;

    protected BookingPage (List<DisposableObject> callingObjects, Controller controller,
                        Dimension dimension, Point point, int fullScreen) {

        super();

        constraints = new Constraints();

        //makes this the operating frame
        setMainFrame(callingObjects, dimension, point, fullScreen);

        //setting top panels
        addTopPanel(callingObjects, controller);

        //setting mainPanel
        addMainPanel(callingObjects, controller);

        goToPage(0);

        mainFrame.setVisible(true);
    }

    protected void setMainFrame (List<DisposableObject> callingObjects, Dimension dimension, Point point, int fullScreen) {

        mainFrame = new JFrame("BookingPage");

        mainFrame.setSize(dimension);
        mainFrame.setLocation(point);
        mainFrame.setExtendedState(fullScreen);

        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mainFrame.setLayout(new GridBagLayout());

        callingObjects.addLast(this);
    }

    protected void addTopPanel (List<DisposableObject> callingObjects, Controller controller) {

        topPanel = new JPanel();

        topPanel.setLayout(new GridBagLayout());

        addTitlePanel();
        addNavigatorBarPanel (callingObjects, controller);

        addUserPanel(callingObjects, controller);

        constraints.setConstraints (0, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START);
        mainFrame.add(topPanel, constraints.getGridBagConstraints());

        topPanel.setVisible(true);
    }

    protected void addTitlePanel () {

        titlePanel = new TitlePanel("AEROPORTO DI NAPOLI");

        constraints.setConstraints(0, 0, 2, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START);
        topPanel.add(titlePanel, constraints.getGridBagConstraints());

        titlePanel.setVisible(true);
    }

    protected void addNavigatorBarPanel (List<DisposableObject> callingObjects, Controller controller) {

        navigatorBarPanel = new NavigatorBarPanel (callingObjects, controller);

        constraints.setConstraints (0, 1, 2, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.CENTER);
        topPanel.add (navigatorBarPanel, constraints.getGridBagConstraints());

        navigatorBarPanel.setOpaque(false);
        navigatorBarPanel.setVisible (true);
    }

    protected void addUserPanel(List<DisposableObject> callingObjects, Controller controller) {

        userPanel = new UserPanel(callingObjects, controller);

        constraints.setConstraints(1, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.FIRST_LINE_END);
        topPanel.add(userPanel, constraints.getGridBagConstraints());

        userPanel.setVisible (true);
    }

    protected void addMainPanel (List<DisposableObject> callingObjects, Controller controller) {

        mainPanel = new JPanel();

        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);

        //inizializzo componenti
        prevPageButton = new JButton("←");
        nextPageButton = new JButton("→");
        currentPageLabel = new JLabel(Integer.toString(currPage + 1));

        addFlightInfoPanel (controller);
        addSearchPanel ();
        addPassengerPage (controller);
        addModifyPanel ();
        addConfirmPanel (callingObjects, controller);

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.PAGE_START, 1, 1);
        mainFrame.add (mainPanel, constraints.getGridBagConstraints());

        mainPanel.setVisible (true);
    }

    protected void addFlightInfoPanel (Controller controller) {

        flightInfoPanel = new JPanel(new BorderLayout());

        flightInfoPanel.setOpaque(false);

        String[] columnNames = {"COMPANY", "CITY", "DATE", "DEPARTURE TIME", "ARRIVAL TIME", "DURATION", "STATUS", "FREE SEATS"};

        Object[][] data = new Object[1][columnNames.length];

        data[0][0] = controller.getFlightController().getCompanyName();
        data[0][1] = controller.getFlightController().getCity();
        data[0][2] = controller.getFlightController().getDateString();
        data[0][3] = controller.getFlightController().getDepartureTime();
        data[0][4] = controller.getFlightController().getArrivalTime();
        LocalTime departureTime = controller.getFlightController().getDepartureTime().toLocalTime();
        LocalTime arrivalTime = controller.getFlightController().getArrivalTime().toLocalTime();
        data[0][5] = Duration.between(departureTime, arrivalTime);
        data[0][6] = controller.getFlightController().getFlightStatus();
        data[0][7] = controller.getFlightController().getFreeSeats();

        flightInfoTable = new JTable(data, columnNames);
        flightInfoTable.setEnabled(false);
        flightInfoTable.getTableHeader().setReorderingAllowed(false);

        flightInfoPanel.add(flightInfoTable.getTableHeader(), BorderLayout.NORTH);
        flightInfoPanel.add(flightInfoTable, BorderLayout.CENTER);

        constraints.setConstraints(0, 0, 1, 1,
                GridBagConstraints.HORIZONTAL, 0, 0, GridBagConstraints.CENTER);
        mainPanel.add(flightInfoPanel, constraints.getGridBagConstraints());
    }

    protected void addSearchPanel () {

        searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        searchPanel.setOpaque(false);

        searchField = new JTextField(16);

        searchButton = new JButton("Search");
        searchButton.addActionListener (new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {

                searchPassenger();
            }
        });
        searchButton.setFocusable(false);

        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        constraints.setConstraints(0, 1, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_START);
        mainPanel.add(searchPanel, constraints.getGridBagConstraints());

        searchPanel.setVisible (true);
    }

    protected void searchPassenger () {

        boolean flag = true;

        for (int i = 0; (i < passengerPanels.size()) && flag; i++) {

            if (passengerPanels.get(i).getTicketNumber().equals(searchField.getText())) {

                goToPage(i / 3);
                flag = false;
            }
        }

        if (flag) new FloatingMessage("Nessun passeggero trovato con ticket number: " + searchField.getText(), searchButton, FloatingMessage.ERROR_MESSAGE);
    }

    protected void goToPage (int page) {

        //sistemo visibilità
        for (int i = 0; i < 3; i++) {

            if (i + currPage * 3 < passengerPanels.size()) passengerPanels.get(i + currPage * 3).setVisible(false);
            if (i + page * 3 < passengerPanels.size()) passengerPanels.get(i + page * 3).setVisible(true);
        }

        //sistemo currPage
        currPage = page;
        currentPageLabel.setText(Integer.toString(currPage + 1));

        //sistemo attivabilità bottoni
        prevPageButton.setEnabled(currPage > 0);
        nextPageButton.setEnabled(currPage < ((passengerPanels.size() - 1) / 3));
    }

    protected void addPassengerPage (Controller controller) {

        passengerPage = new JPanel();

        passengerPage.setLayout(new GridBagLayout());

        passengerPage.setOpaque(false);

        bookedSeats = new ArrayList<>();
        passengerPanels = new ArrayList<>();

        setBookedSeats(controller);
        insertPassengers(controller);

        constraints.setConstraints (0, 2, 1, 1,
                GridBagConstraints.BOTH, 0, 0, GridBagConstraints.CENTER, 0.9f, 0.9f);
        mainPanel.add (passengerPage, constraints.getGridBagConstraints());
    }

    protected void setBookedSeats (Controller controller) {
        controller.setBookedSeats(bookedSeats);
    }

    protected abstract void insertPassengers (Controller controller);

    protected void insertPassengerPanel (PassengerPanel passengerPanel) {

        constraints.setConstraints(0, (passengerPanels.size() % 3), 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        passengerPage.add(passengerPanel, constraints.getGridBagConstraints());

        passengerPanels.addLast(passengerPanel);

        passengerPanel.setVisible(passengerPanels.size() < 4);

        passengerPanel.setPanelEnabled(false);
    }

    protected void addModifyPanel () {

        modifyPanel = new JPanel();

        modifyPanel.setLayout(new GridBagLayout());

        modifyPanel.setOpaque(false);

        flowPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        flowPanel.setOpaque(false);

        constraints.setConstraints (1, 0, 1, 1,
                GridBagConstraints.HORIZONTAL, 0, 0, GridBagConstraints.LINE_END);
        modifyPanel.add(flowPanel, constraints.getGridBagConstraints());

        flowPanel.setVisible(true);

        addPageChangeButtons();

        constraints.setConstraints (0, 3, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.CENTER);
        mainPanel.add (modifyPanel, constraints.getGridBagConstraints());
        modifyPanel.setVisible (true);
    }

    protected void addPageChangeButtons () {

        prevPageButton.setFocusable(false);
        nextPageButton.setFocusable(false);

        //aggiungo action listeners
        prevPageButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {

                goToPage(currPage - 1);

            }
        });

        nextPageButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {

                goToPage(currPage + 1);

            }
        });

        prevPageButton.setEnabled (false);
        nextPageButton.setEnabled(passengerPanels.size() > 3);

        //aggiungo bottoni
        flowPanel.add (prevPageButton);
        flowPanel.add (currentPageLabel);
        flowPanel.add (nextPageButton);
    }

    protected abstract void addConfirmPanel (List<DisposableObject> callingObjects, Controller controller);

    public void setControllerDisposeFlag (boolean flag) {

        controllerDisposeFlag = flag;
    }

    protected void decreaseCurrPage () {
        currPage--;
        currentPageLabel.setText (Integer.toString(currPage + 1));

        if (currPage == 0) prevPageButton.setEnabled (false);
    }

    protected int getCurrPage () {

        return currPage;
    }

    protected JButton getNextButton () {

        return nextPageButton;
    }

    protected JPanel getPassengerPage () {

        return passengerPage;
    }

    @Override
    public void doOnDispose (List<DisposableObject> callingObjects, Controller controller) {

        if (controllerDisposeFlag) {

            controller.getFlightController().setFlight(null);
            controller.getBookingController().setBooking(null);
        }

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
