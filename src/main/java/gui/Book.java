package gui;

import controller.BookingController;
import controller.BookingStatusController;
import controller.Controller;
import controller.FlightController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Book extends DisposableObject {

    protected JFrame mainFrame;
    protected JPanel topPanel;
    protected TitlePanel titlePanel;
    protected NavigatorBarPanel navigatorBarPanel;
    private MenuPanelCustomer hamburgerPanel;
    protected UserPanel userPanel;
    protected JPanel flightInfoPanel;
    protected JPanel mainPanel;
    protected JPanel modifyPanel;
    protected int currPage = 0;
    protected RoundedButton prevPageButton;
    protected RoundedButton nextPageButton;
    protected JLabel currentPageLabel;
    protected JPanel confirmPanel;
    private ArrayList<RoundedButton> confirmButtons;
    protected ArrayList<PassengerPanel> passengerPanels;
    private ArrayList<RemovePassengerButton> removePassengerButtons;
    protected JPanel passengerPage;
    private FooterPanel footerPanel;
    protected Constraints constraints;
    private RoundedButton savePendingButton;

    public Book(ArrayList<DisposableObject> callingObjects, Controller controller,
                Dimension dimension, Point point, int fullScreen) {

        super();

        constraints = new Constraints();

        //makes this the operating frame
        this.setMainFrame(callingObjects, controller, dimension, point, fullScreen, "Book");

        //setting top panels
        this.addTopPanel(callingObjects, controller);

        //setting mainPanel
        this.addMainPanel(callingObjects, controller);

        //this.addFooterPanel();
        addFooterPanel();

        mainFrame.setVisible(true);
    }

    protected Book() {

        super();
    }

    protected void setMainFrame(ArrayList<DisposableObject> callingObjects, Controller controller, Dimension dimension, Point point, int fullScreen, String title) {
        mainFrame = new JFrame(title);
        mainFrame.setSize(dimension);
        mainFrame.setLocation(point);
        mainFrame.setExtendedState(fullScreen);
        callingObjects.addLast(this);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridBagLayout());
        //mainFrame.setSize(1080, 720);

        if (controller.developerMode) mainFrame.setBackground(Color.YELLOW);
    }

    protected void addTopPanel(ArrayList<DisposableObject> callingObjects, Controller controller) {
        topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());

        addTitlePanel("AEROPORTO DI NAPOLI", controller);
        addNavigatorBarPanel(callingObjects, controller);
        addHamburgerPanel(callingObjects, controller);
        addUserPanel(callingObjects, controller);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START);
        mainFrame.add(topPanel, constraints.getConstraints());
        topPanel.setVisible(true);
    }

    protected void addTitlePanel(String title, Controller controller) {
        titlePanel = new TitlePanel(title, controller);
        constraints.setConstraints(0, 0, 2, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START);
        topPanel.add(titlePanel, constraints.getConstraints());
    }

    protected void addNavigatorBarPanel(ArrayList<DisposableObject> callingObjects, Controller controller) {
        navigatorBarPanel = new NavigatorBarPanel(callingObjects, controller);
        constraints.setConstraints(0, 1, 2, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.CENTER);
        topPanel.add(navigatorBarPanel, constraints.getConstraints());
        navigatorBarPanel.setVisible(true);
    }

    protected void addHamburgerPanel(ArrayList<DisposableObject> callingObjects, Controller controller) {
        hamburgerPanel = new MenuPanelCustomer(callingObjects, controller);
        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.FIRST_LINE_START);
        topPanel.add(hamburgerPanel, constraints.getConstraints());
        hamburgerPanel.setVisible(true);
    }

    protected void addUserPanel(ArrayList<DisposableObject> callingObjects, Controller controller) {
        userPanel = new UserPanel(callingObjects, controller);
        constraints.setConstraints(1, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.FIRST_LINE_END);
        topPanel.add(userPanel, constraints.getConstraints());
        userPanel.setVisible(true);
    }

    private void addMainPanel(ArrayList<DisposableObject> callingObjects, Controller controller) {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        //if(controller.developerMode) mainPanel.setBackground(Color.GREEN);

        addFlightInfoPanel(controller);

        passengerPanels = new ArrayList<PassengerPanel>();
        removePassengerButtons = new ArrayList<RemovePassengerButton>();
        passengerPage = new JPanel();
        passengerPage.setLayout(new GridBagLayout());

        prevPageButton = new RoundedButton("←");
        nextPageButton = new RoundedButton("→");

        currentPageLabel = new JLabel(Integer.valueOf(currPage + 1).toString());

        PassengerPanel newPassenger = new PassengerPanel(controller, passengerPanels);
        RemovePassengerButton newremovePassengerButton = new RemovePassengerButton(this, controller, passengerPanels, removePassengerButtons, 0, passengerPage, nextPageButton);
        newremovePassengerButton.setFocusable(false);
        newremovePassengerButton.setEnabled(false);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END);
        passengerPage.add(newPassenger, constraints.getConstraints());
        passengerPanels.add(newPassenger);

        constraints.setConstraints(1, 0, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_START);
        passengerPage.add(newremovePassengerButton, constraints.getConstraints());
        removePassengerButtons.add(newremovePassengerButton);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.BOTH, 0, 0, GridBagConstraints.CENTER);
        mainPanel.add(passengerPage, constraints.getConstraints());

        addModifyPanel(callingObjects, controller);
        addConfirmPanel(callingObjects, controller);

        if (alreadyBooked(controller)) insertPassengers(controller);

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.PAGE_START, 1, 1);
        mainFrame.add(mainPanel, constraints.getConstraints());
        constraints.resetWeight();
        mainFrame.setVisible(true);
    }

    protected void addFlightInfoPanel(Controller controller) {
        flightInfoPanel = new JPanel();
        flightInfoPanel.setLayout(new GridBagLayout());
        /*if(controller.developerMode)*/
        flightInfoPanel.setBackground(Color.GRAY);

        setLabels(controller.getFlightController());

        constraints.setConstraints(0, 0, 3, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START);

        mainPanel.add(flightInfoPanel, constraints.getConstraints());
        flightInfoPanel.setVisible(true);
    }

    protected void setLabels(FlightController flightController) {
        setTitleLabels();
        setInfoLabels(flightController);
    }


    protected void setTitleLabels() {

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

    protected void setInfoLabels(FlightController flightController) {
        ArrayList<JLabel> infoLabels = new ArrayList<JLabel>();

        infoLabels.add(new JLabel("   "));
        infoLabels.add(new JLabel(flightController.getCompanyName()));
        infoLabels.add(new JLabel("/"));
        infoLabels.add(new JLabel(flightController.getDateString()));
        infoLabels.add(new JLabel(flightController.getDepartureTime()));
        infoLabels.add(new JLabel(flightController.getArrivalTime()));
        infoLabels.add(new JLabel("/"));
        infoLabels.add(new JLabel(flightController.getStatusString()));
        infoLabels.add(new JLabel("/"));
        infoLabels.add(new JLabel("   "));

        for (int i = 0; i < infoLabels.size(); i++) {
            constraints.setConstraints(i, 1, 1, 1, GridBagConstraints.NONE,
                    0, 0, GridBagConstraints.CENTER);
            flightInfoPanel.add(infoLabels.get(i), constraints.getConstraints());
        }
    }

    private void addModifyPanel(ArrayList<DisposableObject> callingObjects, Controller controller) {
        modifyPanel = new JPanel();
        modifyPanel.setLayout(new GridBagLayout());
        if (controller.developerMode) modifyPanel.setBackground(Color.BLUE);

        JPanel flowPanel = new JPanel();
        flowPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        constraints.setConstraints(1, 0, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END);
        modifyPanel.add(flowPanel, constraints.getConstraints());

        addAddPassengerButton(this, controller, flowPanel);
        addPageChangeButtons(flowPanel);
        if (controller.getBookingController().checkPendingButton()) addSavePendingButton(callingObjects, controller);

        flowPanel.setVisible(true);

        constraints.setConstraints(0, 2, 2, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.PAGE_END);
        mainFrame.add(modifyPanel, constraints.getConstraints());
        modifyPanel.setVisible(true);
    }

    protected void addConfirmPanel(ArrayList<DisposableObject> callingObjects, Controller controller) {
        String buttonTitle = "Conferma Prenotazione";
        if (alreadyBooked(controller)) buttonTitle = "Conferma Modifiche";

        confirmButtons = new ArrayList<RoundedButton>();
        confirmPanel = new JPanel();
        confirmPanel.setLayout(new GridLayout());
        if (controller.developerMode) modifyPanel.setBackground(Color.BLUE);

        for (int i = 0; i < 3; i++) {

            confirmButtons.add(new RoundedButton(buttonTitle));

            int finalI = i;

            confirmButtons.getLast().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    controller.addBooking(passengerPanels, BookingStatusController.confirmed);

                    controller.goBack(callingObjects);

                    //new GoodMessage("La tua richiesta di prenotazione è stata presa in carico", confirmButtons.get(finalI));
                }
            });

            confirmButtons.getLast().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    checkConfirmButton(finalI);
                }
            });

            constraints.setConstraints(i * 2, 0, 1, 1, GridBagConstraints.NONE,
                    0, 0, GridBagConstraints.CENTER, 0.015f, 0.015f);
            confirmPanel.add(confirmButtons.getLast(), constraints.getConstraints());

            if (i != 2) {
                constraints.setConstraints(i * 2 + 1, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                        0, 0, GridBagConstraints.CENTER, 0.015f, 0.015f);
                confirmPanel.add(new JPanel(), constraints.getConstraints());
            }

            confirmButtons.getLast().setFocusable(false);
            confirmButtons.getLast().setVisible(false);
        }
        confirmButtons.get(1).setVisible(true);

        constraints.setConstraints(0, 3, 3, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.CENTER);
        mainFrame.add(confirmPanel, constraints.getConstraints());
        confirmPanel.setVisible(true);
    }

    private void addAddPassengerButton(Book book, Controller controller, JPanel flowPanel) {

        RoundedButton addPassengerButton = new RoundedButton("AGGIUNGI PASSEGGERO");
        addPassengerButton.setFocusable(false);

        addPassengerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int bonus = 0;
                if (alreadyBooked(controller)) bonus = controller.getBookingController().getPassengersSize();

                if (controller.getFlightController().getFreeSeats() - passengerPanels.size() + bonus > 0) {
                    insertPassengerPanel(controller, book, new PassengerPanel(controller, passengerPanels));
                } else {
                    //showMessageDialog(new JPanel(), "Non ci sono altri posti disponibili per questo volo", "Error", JOptionPane.ERROR_MESSAGE);
                    new ErrorMessage("Non ci sono altri posti disponibili per questo volo", addPassengerButton);
                }
            }
        });

        flowPanel.add(addPassengerButton);
    }

    protected void addPageChangeButtons(JPanel flowPanel) {

        prevPageButton.setFocusable(false);
        nextPageButton.setFocusable(false);

        prevPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currPage != (passengerPanels.size() - 1) / 3) //non sto all'ultima pagina quindi sono 3
                {
                    for (int i = 0; i < 3; i++) {
                        passengerPanels.get((currPage * 3) + i).setVisible(false);
                        removePassengerButtons.get((currPage * 3) + i).setVisible(false);
                    }
                } else //sto all'ultima pagina quindi non so quanti sono
                {
                    for (int i = 0; i <= (passengerPanels.size() - 1) % 3; i++) {
                        passengerPanels.get((passengerPanels.size() - i - 1)).setVisible(false);
                        removePassengerButtons.get((passengerPanels.size() - i - 1)).setVisible(false);
                    }
                }

                currPage--;
                currentPageLabel.setText(Integer.valueOf(currPage + 1).toString());

                for (int i = 0; i < 3; i++) //vado indietro quindi sono 3
                {
                    passengerPanels.get((currPage * 3) + i).setVisible(true);
                    removePassengerButtons.get((currPage * 3) + i).setVisible(true);
                }

                nextPageButton.setEnabled(true);

                if (currPage == 0) prevPageButton.setEnabled(false);
            }
        });

        prevPageButton.setEnabled(false);

        flowPanel.add(prevPageButton);

        flowPanel.add(currentPageLabel);

        nextPageButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 3; i++) //metto a false la pagina corrente
                {
                    passengerPanels.get((currPage * 3) + i).setVisible(false);
                    removePassengerButtons.get((currPage * 3) + i).setVisible(false);
                }

                if (currPage + 1 == (passengerPanels.size() - 1) / 3) //sto andando all'ultima pagina quindi non so quanti sono
                {
                    for (int i = 0; i <= (passengerPanels.size() - 1) % 3; i++) {
                        passengerPanels.get(passengerPanels.size() - i - 1).setVisible(true);
                        removePassengerButtons.get(passengerPanels.size() - i - 1).setVisible(true);
                    }

                    nextPageButton.setEnabled(false);
                } else //la prossima pagina ne ha 3
                {
                    for (int i = 0; i < 3; i++) {
                        passengerPanels.get(((currPage + 1) * 3) + i).setVisible(true);
                        removePassengerButtons.get(((currPage + 1) * 3) + i).setVisible(true);
                    }
                }

                currPage++;
                currentPageLabel.setText(Integer.valueOf(currPage + 1).toString());
                prevPageButton.setEnabled(true);
            }
        });

        nextPageButton.setEnabled(false);
        flowPanel.add(nextPageButton);
    }

    private void addSavePendingButton(ArrayList<DisposableObject> callingObjects, Controller controller) {
        savePendingButton = new RoundedButton("Salva in attesa");
        savePendingButton.setFocusable(false);

        savePendingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkSavePendingButton(controller)) {

                    controller.addBooking(passengerPanels, BookingStatusController.pending);

                    controller.goBack(callingObjects);

                    //new GoodMessage("La tua prenotazione è in attesa di conferma", savePendingButton);
                } else {
                    new ErrorMessage("Impossibile aggiungere una prenotazione vuota", savePendingButton);
                }
            }
        });

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END, 0.5f, 0.5f);
        modifyPanel.add(savePendingButton, constraints.getConstraints());

        savePendingButton.setVisible(true);
    }

    protected void addFooterPanel() {
        footerPanel = new FooterPanel();
        constraints.setConstraints(0, 4, 1, 1, GridBagConstraints.BOTH,
                0, 10, GridBagConstraints.PAGE_END);
        mainFrame.add(footerPanel, constraints.getConstraints());
    }

    protected void decreaseCurrPage() {
        currPage--;
        currentPageLabel.setText(Integer.valueOf(currPage + 1).toString());

        if (currPage == 0) prevPageButton.setEnabled(false);
    }

    protected int getCurrPage() {
        return currPage;
    }

    private void checkConfirmButton(int index) {
        boolean flag = true;

        for (PassengerPanel passengerPanel : passengerPanels) {
            if (passengerPanel.checkPassengerName() || passengerPanel.checkPassengerSurname() || passengerPanel.checkPassengerCF()) {
                flag = false;
            }

            for (LuggagePanel luggagePanel : passengerPanel.getLuggagesPanels()) {
                if (luggagePanel.checkLuggage()) {
                    flag = false;
                }
            }
        }

        if (!flag) {
            confirmButtons.get(index).setVisible(false);

            int random = new Random().nextInt(2);

            if (index == 0) {
                random++;
            } else if (index == 1) {
                random *= 2;
            }

            confirmButtons.get(random).setVisible(true);

            new ErrorMessage("I dati dei passeggeri sono incompleti", confirmButtons.get(random));
        }
    }

    protected void insertPassengers(Controller controller) {

        passengerPanels.getFirst().setPassengerName(controller.getBookingController().getPassengerName(0));
        passengerPanels.getFirst().setPassengerSurname(controller.getBookingController().getPassengerLastName(0));
        passengerPanels.getFirst().setPassengerCF(controller.getBookingController().getPassengerSSN(0));
        passengerPanels.getFirst().setSeat(controller.getBookingController().getPassengerSeat(0));
        passengerPanels.getFirst().setLuggagesTypes(controller.getBookingController().getPassengerLuggagesTypes(0), controller);
        passengerPanels.getFirst().setVisible(false);
        passengerPanels.getFirst().setVisible(true);

        for (int i = 1; i < controller.getBookingController().getPassengersSize(); i++) {
            PassengerPanel passengerPanel = new PassengerPanel(controller, passengerPanels);

            passengerPanel.setPassengerName(controller.getBookingController().getPassengerName(i));
            passengerPanel.setPassengerSurname(controller.getBookingController().getPassengerLastName(i));
            passengerPanel.setPassengerCF(controller.getBookingController().getPassengerSSN(i));
            passengerPanel.setSeat(controller.getBookingController().getPassengerSeat(i));

            passengerPanel.setLuggagesTypes(controller.getBookingController().getPassengerLuggagesTypes(i), controller);

            insertPassengerPanel(controller, this, passengerPanel);
        }
    }

    protected void insertPassengerPanel(Controller controller, Book book, PassengerPanel newPassengerPanel) {

        constraints.setConstraints(0, (passengerPanels.size() % 3), 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END);
        passengerPage.add(newPassengerPanel, constraints.getConstraints());

        RemovePassengerButton newRemovePassengerButton = new RemovePassengerButton(book, controller, passengerPanels, removePassengerButtons, removePassengerButtons.size(), passengerPage, nextPageButton);
        constraints.setConstraints(1, (passengerPanels.size() % 3), 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_START);
        passengerPage.add(newRemovePassengerButton, constraints.getConstraints());

        if (currPage != (passengerPanels.size() - 1) / 3) //non siamo all'ultima pagina quindi sono 3
        {
            for (int i = 0; i < 3; i++) {
                passengerPanels.get((currPage * 3) + i).setVisible(false);
                removePassengerButtons.get((currPage * 3) + i).setVisible(false);
            }

            for (int i = passengerPanels.size() % 3; i > 0; i--) //aggiungo all'ultima pagina quindi non so quanti sono
            {
                passengerPanels.get((passengerPanels.size() - i)).setVisible(true);
                removePassengerButtons.get((passengerPanels.size() - i)).setVisible(true);
            }

            currPage = passengerPanels.size() / 3;
            currentPageLabel.setText(Integer.toString(currPage + 1));
            if (currPage != 0) prevPageButton.setEnabled(true);

        } else //siamo all'ultima pagina quindi non so quanti sono
        {
            if (passengerPanels.size() % 3 == 0) //l'ultima pagina è piena quindi ne creo un'altra
            {
                for (int i = 0; i < 3; i++) {
                    passengerPanels.get((currPage * 3) + i).setVisible(false);
                    removePassengerButtons.get((currPage * 3) + i).setVisible(false);
                }

                currPage = passengerPanels.size() / 3;
                currentPageLabel.setText(Integer.toString(currPage + 1));
                if (currPage != 0) prevPageButton.setEnabled(true);

            }
        }

        nextPageButton.setEnabled(false);

        passengerPanels.addLast(newPassengerPanel);
        removePassengerButtons.add(newRemovePassengerButton);

        passengerPage.setVisible(false);
        passengerPage.setVisible(true);

        removePassengerButtons.getFirst().setEnabled(true);
    }

    private boolean alreadyBooked(Controller controller) {
        return controller.getBookingController().getBooking() != null;
    }

    private boolean checkSavePendingButton(Controller controller) {
        for (PassengerPanel passengerPanel : passengerPanels) {
            if (!passengerPanel.checkPassengerName()) return true;
            if (!passengerPanel.checkPassengerSurname()) return true;
            if (!passengerPanel.checkPassengerCF()) return true;
            if (!passengerPanel.checkPassengerSeat()) return true;

            for (LuggagePanel luggagePanel : passengerPanel.getLuggagesPanels()) {
                if (!luggagePanel.checkLuggage()) return true;
            }
        }

        return false;
    }

    @Override
    public void doOnDispose(ArrayList<DisposableObject> callingObjects, Controller controller) {
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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    }
}
