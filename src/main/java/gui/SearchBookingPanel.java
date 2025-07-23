package gui;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;
import controller.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SearchBookingPanel extends JPanel {

    private JScrollPane resultsScrollPane;

    private final Constraints constraints;


    //Contiene il filtro attuale, può essere: "NONE", "FLIGHT", "PASSENGER"
    private String activeFilter = "NONE";


    private JTextField fromField;
    private JTextField toField;
    private DatePicker dateFrom;
    private DatePicker dateTo;
    private TimePicker timeFrom;
    private TimePicker timeTo;


    private JTextField firstNameField;

    private JTextField lastNameField;

    private JTextField passengerSSNField;
    private JTextField ticketNumberField;


    private JButton searchButton;

    private boolean searchPerformed = false;

    ArrayList<Date> bookingDates = new ArrayList<>();
    ArrayList<String> bookingStatus = new ArrayList<>();
    ArrayList<String> flightIds = new ArrayList<>();

    public SearchBookingPanel(List<DisposableObject> callingObjects, Controller controller, boolean ifOpenedFromMenu) {

        super();

        this.setLayout(new GridBagLayout());
        this.setBackground(Color.WHITE);

        this.constraints = new Constraints();

        Border lineBorder = BorderFactory.createLineBorder(new Color(200, 200, 200));
        Border emptyBorder = BorderFactory.createEmptyBorder(40, 50, 40, 50);

        this.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));

        setComponents(callingObjects, controller, ifOpenedFromMenu);
    }

    private void setComponents(List<DisposableObject> callingObjects, Controller controller, boolean ifOpenedFromMenu) {

        JPanel parametersPanel = createMainFilterPanel();

        searchButton = createSearchButton(callingObjects, controller);

        resultsScrollPane = new JScrollPane();


        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.NORTH, 1.0f, 0.0f, new Insets(0, 0, 0, 0));
        this.add(parametersPanel, constraints.getGridBagConstraints());

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER, 1.0f, 0.0f, new Insets(30, 0, 30, 0));
        this.add(searchButton, constraints.getGridBagConstraints());

        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.CENTER, 1.0f, 1.0f, new Insets(0, 0, 0, 0));
        this.add(resultsScrollPane, constraints.getGridBagConstraints());

        if(ifOpenedFromMenu){
            controller.getAllBooksLoogedCustomer(bookingDates, bookingStatus, flightIds, searchButton);
        }else{
            this.bookingDates = (ArrayList<Date>) controller.getBookingController().getSearchBookingResultDates();
            this.bookingStatus = (ArrayList<String>) controller.getBookingController().getSearchBookingResultStatus();
            this.flightIds = (ArrayList<String>) controller.getFlightController().getSearchBookingResultIds();
        }

        updateResultsPanel(callingObjects, controller);
    }

    private JPanel createMainFilterPanel() {
        JButton passengerButton;
        JButton flightButton;
        JPanel passengerFilterPanel;
        JPanel flightFilterPanel;

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);


        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 40, 0));
        buttonPanel.setOpaque(false);

        flightButton = new JButton("Filtra per voli");
        setButtonApperance(flightButton);
        buttonPanel.add(flightButton);

        passengerButton = new JButton("Filtra per passeggeri");
        setButtonApperance(passengerButton);
        buttonPanel.add(passengerButton);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.CENTER, 1.0f, 0.0f, new Insets(0, 0, 35, 0));
        mainPanel.add(buttonPanel, constraints.getGridBagConstraints());


        flightFilterPanel = createFlightFilterPanel();
        passengerFilterPanel = createPassengerFilterPanel();

        //Imposto i panel per applicare i filtri inizialmente non visibili, compariranno quando si scegli per cosa filtrare
        flightFilterPanel.setVisible(false);
        passengerFilterPanel.setVisible(false);

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.CENTER, 1.0f, 0.0f, new Insets(0, 0, 0, 0));

        //I panel di filtri sono sovrapposti, ma visibili solo uno per volta
        mainPanel.add(flightFilterPanel, constraints.getGridBagConstraints());
        mainPanel.add(passengerFilterPanel, constraints.getGridBagConstraints());


        flightButton.addActionListener(e -> {
            activeFilter = "FLIGHT";
            flightFilterPanel.setVisible(true);
            passengerFilterPanel.setVisible(false);
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        passengerButton.addActionListener(e -> {
            activeFilter = "PASSENGER";
            passengerFilterPanel.setVisible(true);
            flightFilterPanel.setVisible(false);
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        return mainPanel;
    }

    private JPanel createFlightFilterPanel() {

        JLabel timeSep;
        JLabel timeLabel;
        JLabel dateSep;
        JLabel dateLabel;
        JLabel toLabel;
        JLabel fromLabel;

        JPanel container = new JPanel(new GridLayout(1, 2, 40, 0));
        container.setOpaque(false);

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setOpaque(false);

        fromLabel = new JLabel("Da:");
        setLabelApperance(fromLabel);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(fromLabel, constraints.getGridBagConstraints());

        fromField = new JTextField(15);
        fromField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        constraints.setConstraints(1, 0, 3, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(0, 0, 30, 0));
        leftPanel.add(fromField, constraints.getGridBagConstraints());

        dateLabel = new JLabel("Range date:");
        setLabelApperance(dateLabel);

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(dateLabel, constraints.getGridBagConstraints());

        dateFrom = new DatePicker();
        dateFrom.getComponentDateTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));

        constraints.setConstraints(1, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.CENTER, 0.5f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(dateFrom, constraints.getGridBagConstraints());

        dateSep = new JLabel("--");
        setLabelApperance(dateSep);

        constraints.setConstraints(2, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(dateSep, constraints.getGridBagConstraints());

        dateTo = new DatePicker();
        dateTo.getComponentDateTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));

        constraints.setConstraints(3, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.CENTER, 0.5f, 0.0f, new Insets(0, 0, 30, 0));
        leftPanel.add(dateTo, constraints.getGridBagConstraints());


        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);

        toLabel = new JLabel("A:");
        setLabelApperance(toLabel);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(toLabel, constraints.getGridBagConstraints());

        toField = new JTextField(15);
        toField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        constraints.setConstraints(1, 0, 3, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(0, 0, 30, 0));
        rightPanel.add(toField, constraints.getGridBagConstraints());

        timeLabel = new JLabel("Fascia oraria:");
        setLabelApperance(timeLabel);

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(timeLabel, constraints.getGridBagConstraints());

        timeFrom = new TimePicker();
        timeFrom.getComponentTimeTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));

        constraints.setConstraints(1, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.CENTER, 0.5f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(timeFrom, constraints.getGridBagConstraints());

        timeSep = new JLabel("--");
        setLabelApperance(timeSep);

        constraints.setConstraints(2, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(timeSep, constraints.getGridBagConstraints());

        timeTo = new TimePicker();
        timeTo.getComponentTimeTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));

        constraints.setConstraints(3, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.CENTER, 0.5f, 0.0f, new Insets(0, 0, 30, 0));
        rightPanel.add(timeTo, constraints.getGridBagConstraints());

        container.add(leftPanel);
        container.add(rightPanel);

        return container;
    }

    private JPanel createPassengerFilterPanel() {

        JLabel passengerSSNLabel;
        JLabel lastNameLabel;
        JLabel firstNameLabel;

        JPanel container = new JPanel(new GridLayout(1, 2, 40, 0));
        container.setOpaque(false);


        firstNameLabel = new JLabel("Nome:");
        setLabelApperance(firstNameLabel);

        firstNameField = new JTextField(15);
        firstNameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        lastNameLabel = new JLabel("Cognome:");
        setLabelApperance(lastNameLabel);

        lastNameField = new JTextField(15);
        lastNameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        passengerSSNLabel = new JLabel("Codice Fiscale:");
        setLabelApperance(passengerSSNLabel);

        passengerSSNField = new JTextField(15);
        passengerSSNField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel ticketNumberLabel = new JLabel("N. Biglietto:");
        setLabelApperance(ticketNumberLabel);

        ticketNumberField = new JTextField(15);
        ticketNumberField.setFont(new Font("Segoe UI", Font.PLAIN, 16));


        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setOpaque(false);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(firstNameLabel, constraints.getGridBagConstraints());

        constraints.setConstraints(1, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(0, 0, 30, 0));
        leftPanel.add(firstNameField, constraints.getGridBagConstraints());

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(passengerSSNLabel, constraints.getGridBagConstraints());

        constraints.setConstraints(1, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(0, 0, 30, 0));
        leftPanel.add(passengerSSNField, constraints.getGridBagConstraints());


        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(lastNameLabel, constraints.getGridBagConstraints());

        constraints.setConstraints(1, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(0, 0, 30, 0));
        rightPanel.add(lastNameField, constraints.getGridBagConstraints());

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(ticketNumberLabel, constraints.getGridBagConstraints());

        constraints.setConstraints(1, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(0, 0, 30, 0));
        rightPanel.add(ticketNumberField, constraints.getGridBagConstraints());

        container.add(leftPanel);
        container.add(rightPanel);

        return container;
    }

    private JButton createSearchButton(List<DisposableObject> callingObjects, Controller controller) {

        searchButton = new JButton("Cerca");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 18));

        searchButton.setBackground(new Color(0, 120, 215));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);

        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        searchButton.setPreferredSize(new Dimension(200, 50));

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (activeFilter) {
                    case "NONE":
                        new FloatingMessage("Selezionare un tipo di filtro prima di cercare.", searchButton, FloatingMessage.ERROR_MESSAGE);
                        break;
                    case "FLIGHT":
                        filteredFlightSearch(callingObjects, controller, searchButton);
                        break;
                    case "PASSENGER":
                        filteredPassengerSearch(callingObjects, controller, searchButton);
                        break;
                    default:
                        break;
                }

            }
        });

        searchPerformed = true;
        controller.setErrorButton(searchButton);

        return searchButton;
    }

    public void filteredFlightSearch(List<DisposableObject> callingObjects, Controller controller, JButton searchButton) {

        String origin = fromField.getText();
        String destination = toField.getText();
        LocalDate dateBefore = dateFrom.getDate();
        LocalDate dateAfter = dateTo.getDate();
        LocalTime timeBefore = timeFrom.getTime();
        LocalTime timeAfter = timeTo.getTime();

        if ((fromField.getText().isEmpty() && !toField.getText().isEmpty()) || (!fromField.getText().isEmpty() && toField.getText().isEmpty())) {

            new FloatingMessage("Se si specifica una città, vanno specificate entrambe!", searchButton, FloatingMessage.ERROR_MESSAGE);

        } else if ((dateBefore != null && dateAfter == null) || (dateBefore == null && dateAfter != null)) {

            new FloatingMessage("Errore nel range di date!", searchButton, FloatingMessage.ERROR_MESSAGE);

        } else if (dateBefore != null && dateAfter.isBefore(dateBefore)) {

            new FloatingMessage("La seconda data deve essere successiva alla prima!", searchButton, FloatingMessage.ERROR_MESSAGE);

        } else if ((timeBefore != null && timeAfter == null) || (timeBefore == null && timeAfter != null)) {

            new FloatingMessage("Errore nella fascia oraria!", searchButton, FloatingMessage.ERROR_MESSAGE);

        } else {

            if (controller != null) {

                bookingDates = new ArrayList<>();
                bookingStatus = new ArrayList<>();
                flightIds = new ArrayList<>();

                controller.searchBooksLoogedCustomerFilteredFlights(origin, destination, dateBefore, dateAfter, timeBefore, timeAfter, bookingDates, bookingStatus, flightIds, searchButton);

                updateResultsPanel(callingObjects, controller);

            }
            return;
        }

        updateResultsPanel(callingObjects, controller);
    }

    public void filteredPassengerSearch(List<DisposableObject> callingObjects, Controller controller, JButton searchButton) {

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String passengerSSN = passengerSSNField.getText();
        String ticketNumber = ticketNumberField.getText();

        if (controller != null) {

            bookingDates = new ArrayList<>();
            bookingStatus = new ArrayList<>();
            flightIds = new ArrayList<>();

            controller.searchBooksLoogedCustomerFilteredPassengers(firstName, lastName, passengerSSN, ticketNumber, bookingDates, bookingStatus, flightIds, searchButton);

            updateResultsPanel(callingObjects, controller);
        }
    }


    private void updateResultsPanel(List<DisposableObject> callingObjects, Controller controller) {

        SearchBookingResultPanel resultsPanel = new SearchBookingResultPanel(callingObjects, controller,
                                                                             bookingDates, bookingStatus, flightIds);

        resultsScrollPane.setViewportView(resultsPanel);

        resultsScrollPane.setBorder(BorderFactory.createEmptyBorder());

        resultsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        resultsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        resultsScrollPane.getVerticalScrollBar().setUnitIncrement(30);

        resultsScrollPane.revalidate();
        resultsScrollPane.repaint();
    }

    private void setButtonApperance(JButton button) {

        button.setFocusPainted(false);
        button.setBackground(new Color(235, 235, 235));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(button.getPreferredSize().width, 35));

    }

    private void setLabelApperance(JLabel label) {

        label.setFont(new Font("Segoe UI", Font.BOLD, 14));

    }

    public boolean isSearchPerformed() {
        return searchPerformed;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public String getActiveFilter() {
        return activeFilter;
    }

}