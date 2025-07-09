package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class BookingPage extends DisposableObject {

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

                protected JPanel flowPanel;

                    protected int currPage = 0;
                    protected JButton prevPageButton;
                    protected JButton nextPageButton;
                    protected JLabel currentPageLabel;

        protected FooterPanel footerPanel;

    protected Constraints constraints;
    protected boolean controllerDisposeFlag = true;

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

    public BookingPage (ArrayList<DisposableObject> callingObjects, Controller controller,
                        Dimension dimension, Point point, int fullScreen, boolean flag) {

        this(callingObjects, controller, dimension, point, fullScreen);

        setControllerDisposeFlag(flag);
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
        //addMenuPanel(callingObjects, controller);
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

    protected void addSearchPanel (Controller controller) {

        searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

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
        mainPanel.add(searchPanel, constraints.getConstraints());

        searchPanel.setVisible (true);
    }

    protected void searchPassenger () {

        boolean flag = true;

        for (int i = 0; (i < passengerPanels.size()) && flag; i++) {

            if (passengerPanels.get(i).getTicketNumber().equals(searchField.getText())) {

                System.out.println("trovato");
                goToPage(i / 3);
                flag = false;
            }
        }

        if (flag) new ErrorMessage("Nessun passeggero trovato con ticket number: " + searchField.getText(), searchButton);
    }

    protected void goToPage (int page) {

        //sistemo visibilità
        for (int i = 0; i < 3; i++) {

            passengerPanels.get(i + currPage * 3).setVisible(false);
            passengerPanels.get(i + page * 3).setVisible(true);
        }

        //sistemo currPage
        currPage = page;
        currentPageLabel.setText(Integer.valueOf(currPage + 1).toString());

        //sistemo attivabilità bottoni
        prevPageButton.setEnabled(currPage > 0);
        nextPageButton.setEnabled(currPage < (passengerPanels.size() / 3));
    }

    protected void addPassengerPage (Controller controller) {

        passengerPage = new JPanel();

        passengerPage.setLayout(new GridBagLayout());

        passengerPanels = new ArrayList<PassengerPanel> ();

        insertPassengers(controller);

        constraints.setConstraints (0, 2, 1, 1,
                GridBagConstraints.BOTH, 0, 0, GridBagConstraints.CENTER, 0.9f, 0.9f);
        mainPanel.add (passengerPage, constraints.getConstraints());
    }

    abstract protected void insertPassengers (Controller controller);

    protected void insertPassengerPanel (Controller controller, PassengerPanel passengerPanel) {

        constraints.setConstraints(0, (passengerPanels.size() % 3), 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        passengerPage.add(passengerPanel, constraints.getConstraints());

        passengerPanels.addLast(passengerPanel);

        passengerPanel.setVisible(passengerPanels.size() < 4);

        passengerPanel.setPanelEnabled(false);
    }

    private void addModifyPanel (ArrayList<DisposableObject> callingObjects, Controller controller) {

        modifyPanel = new JPanel();

        modifyPanel.setLayout(new GridBagLayout());

        flowPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        constraints.setConstraints (1, 0, 1, 1,
                GridBagConstraints.HORIZONTAL, 0, 0, GridBagConstraints.LINE_END);
        modifyPanel.add(flowPanel, constraints.getConstraints());

        flowPanel.setVisible(true);

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

                goPreviousPage();
            }
        });

        nextPageButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {

                goNextPage();
            }
        });

        prevPageButton.setEnabled (false);
        nextPageButton.setEnabled(passengerPanels.size() > 3);

        //aggiungo bottoni
        flowPanel.add (prevPageButton);
        flowPanel.add (currentPageLabel);
        flowPanel.add (nextPageButton);
    }

    protected void goPreviousPage () {

        if (currPage != (passengerPanels.size() - 1) / 3) { //non sto all'ultima pagina quindi sono 3

            for (int i = 0; i < 3; i++)
                passengerPanels.get ((currPage * 3) + i).setVisible (false);

        } else { //sto all'ultima pagina quindi non so quanti sono

            for (int i = 0; i <= (passengerPanels.size() - 1) % 3; i++)
                passengerPanels.get ((passengerPanels.size() - i - 1)).setVisible (false);
        }

        currPage--;
        currentPageLabel.setText (Integer.valueOf(currPage + 1).toString());

        for (int i = 0; i < 3; i++) //vado indietro quindi sono 3
            passengerPanels.get ((currPage * 3) + i).setVisible (true);

        nextPageButton.setEnabled (true);

        if (currPage == 0) prevPageButton.setEnabled (false);
    }

    protected void goNextPage () {

        for (int i = 0; i < 3; i++) //metto a false la pagina corrente
            passengerPanels.get ((currPage * 3) + i).setVisible (false);

        if (currPage + 1 == (passengerPanels.size() - 1) / 3) { //sto andando all'ultima pagina quindi non so quanti sono

            for (int i = 0; i <= (passengerPanels.size() - 1) % 3; i++)
                passengerPanels.get(passengerPanels.size() - i - 1).setVisible(true);

            nextPageButton.setEnabled (false);
        } else { //la prossima pagina ne ha 3

            for (int i = 0; i < 3; i++)
                passengerPanels.get (((currPage + 1) * 3) + i).setVisible (true);
        }

        currPage++;
        currentPageLabel.setText (Integer.valueOf(currPage + 1).toString());
        prevPageButton.setEnabled (true);
    }

    abstract protected void addConfirmPanel (ArrayList<DisposableObject> callingObjects, Controller controller);

    protected void addFooterPanel () {

    }

    public void setControllerDisposeFlag (boolean flag) {

        controllerDisposeFlag = flag;
    }

    protected void decreaseCurrPage () {
        currPage--;
        currentPageLabel.setText (Integer.valueOf(currPage + 1).toString());

        if (currPage == 0) prevPageButton.setEnabled (false);
    }

    protected int getCurrPage () {

        return currPage;
    }

    protected JButton getPrevButton () {

        return prevPageButton;
    }

    protected JButton getNextButton () {

        return nextPageButton;
    }

    protected JPanel getPassengerPage () {

        return passengerPage;
    }

    @Override
    public void doOnDispose (ArrayList<DisposableObject> callingObjects, Controller controller) {

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
