package gui;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;
import controller.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class SearchBookingPanel extends JPanel {

    private JScrollPane resultsScrollPane;

    private Constraints constraints;


    private JPanel flightFilterPanel;
    private JPanel passengerFilterPanel;

    //Contiene il filtro attuale, può essere: "NONE", "FLIGHT", "PASSENGER"
    private String activeFilter = "NONE";


    private JLabel fromLabel;
    private JTextField fromField;
    private JLabel toLabel;
    private JTextField toField;
    private JLabel dateLabel;
    private DatePicker dateFrom;
    private JLabel dateSep;
    private DatePicker dateTo;
    private JLabel timeLabel;
    private TimePicker timeFrom;
    private JLabel timeSep;
    private TimePicker timeTo;


    private JLabel firstNameLabel;
    private JTextField firstNameField;
    private JLabel lastNameLabel;
    private JTextField lastNameField;
    private JLabel SSNLabel;
    private JTextField SSNField;
    private JLabel ticketNumberLabel;
    private JTextField ticketNumberField;


    private JButton flightButton;
    private JButton passengerButton;


    ArrayList<Date> bookingDates = new ArrayList<>();
    ArrayList<String> bookingStatus = new ArrayList<>();
    ArrayList<String> flightIds = new ArrayList<>();

    public SearchBookingPanel(ArrayList<DisposableObject> callingObjects, Controller controller, boolean ifOpenedFromMenu) {

        super();

        this.setLayout(new GridBagLayout());
        this.setBackground(Color.WHITE);

        this.constraints = new Constraints();

        Border lineBorder = BorderFactory.createLineBorder(new Color(200, 200, 200));
        Border emptyBorder = BorderFactory.createEmptyBorder(40, 50, 40, 50);

        this.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));

        setComponents(callingObjects, controller, ifOpenedFromMenu);
    }

    private void setComponents(ArrayList<DisposableObject> callingObjects, Controller controller, boolean ifOpenedFromMenu) {

        JPanel parametersPanel = createMainFilterPanel();

        JButton searchButton = createSearchButton(callingObjects, controller);

        resultsScrollPane = new JScrollPane();


        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.NORTH, 1.0f, 0.0f, new Insets(0, 0, 0, 0));
        this.add(parametersPanel, constraints.getConstraints());

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER, 1.0f, 0.0f, new Insets(30, 0, 30, 0));
        this.add(searchButton, constraints.getConstraints());

        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.CENTER, 1.0f, 1.0f, new Insets(0, 0, 0, 0));
        this.add(resultsScrollPane, constraints.getConstraints());

        if(ifOpenedFromMenu){
            controller.getAllBooksLoogedCustomer(bookingDates, bookingStatus, flightIds, searchButton);
        }else{
            this.bookingDates = (ArrayList<Date>) controller.getBookingController().getSearchBookingResultDates();
            this.bookingStatus = (ArrayList<String>) controller.getBookingController().getSearchBookingResultStatus();
            this.flightIds = (ArrayList<String>) controller.getFlightController().getSearchBookingResultIds();
        }

        updateResultsPanel(callingObjects, controller, false);
    }

    private JPanel createMainFilterPanel() {

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
        mainPanel.add(buttonPanel, constraints.getConstraints());


        flightFilterPanel = createFlightFilterPanel();
        passengerFilterPanel = createPassengerFilterPanel();

        //Imposto i panel per applicare i filtri inizialmente non visibili, compariranno quando si scegli per cosa filtrare
        flightFilterPanel.setVisible(false);
        passengerFilterPanel.setVisible(false);

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.CENTER, 1.0f, 0.0f, new Insets(0, 0, 0, 0));

        //I panel di filtri sono sovrapposti, ma visibili solo uno per volta
        mainPanel.add(flightFilterPanel, constraints.getConstraints());
        mainPanel.add(passengerFilterPanel, constraints.getConstraints());


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

        JPanel container = new JPanel(new GridLayout(1, 2, 40, 0));
        container.setOpaque(false);

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setOpaque(false);

        fromLabel = new JLabel("Da:");
        setLabelApperance(fromLabel);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(fromLabel, constraints.getConstraints());

        fromField = new JTextField(15);
        fromField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        constraints.setConstraints(1, 0, 3, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(0, 0, 30, 0));
        leftPanel.add(fromField, constraints.getConstraints());

        dateLabel = new JLabel("Range date:");
        setLabelApperance(dateLabel);

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(dateLabel, constraints.getConstraints());

        dateFrom = new DatePicker();
        dateFrom.getComponentDateTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));

        constraints.setConstraints(1, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.CENTER, 0.5f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(dateFrom, constraints.getConstraints());

        dateSep = new JLabel("--");
        setLabelApperance(dateSep);

        constraints.setConstraints(2, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(dateSep, constraints.getConstraints());

        dateTo = new DatePicker();
        dateTo.getComponentDateTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));

        constraints.setConstraints(3, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.CENTER, 0.5f, 0.0f, new Insets(0, 0, 30, 0));
        leftPanel.add(dateTo, constraints.getConstraints());


        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);

        toLabel = new JLabel("A:");
        setLabelApperance(toLabel);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(toLabel, constraints.getConstraints());

        toField = new JTextField(15);
        toField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        constraints.setConstraints(1, 0, 3, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(0, 0, 30, 0));
        rightPanel.add(toField, constraints.getConstraints());

        timeLabel = new JLabel("Fascia oraria:");
        setLabelApperance(timeLabel);

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(timeLabel, constraints.getConstraints());

        timeFrom = new TimePicker();
        timeFrom.getComponentTimeTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));

        constraints.setConstraints(1, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.CENTER, 0.5f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(timeFrom, constraints.getConstraints());

        timeSep = new JLabel("--");
        setLabelApperance(timeSep);

        constraints.setConstraints(2, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(timeSep, constraints.getConstraints());

        timeTo = new TimePicker();
        timeTo.getComponentTimeTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));

        constraints.setConstraints(3, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.CENTER, 0.5f, 0.0f, new Insets(0, 0, 30, 0));
        rightPanel.add(timeTo, constraints.getConstraints());

        container.add(leftPanel);
        container.add(rightPanel);

        return container;
    }

    private JPanel createPassengerFilterPanel() {

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

        SSNLabel = new JLabel("Codice Fiscale:");
        setLabelApperance(SSNLabel);

        SSNField = new JTextField(15);
        SSNField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        ticketNumberLabel = new JLabel("N. Biglietto:");
        setLabelApperance(ticketNumberLabel);

        ticketNumberField = new JTextField(15);
        ticketNumberField.setFont(new Font("Segoe UI", Font.PLAIN, 16));


        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setOpaque(false);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(firstNameLabel, constraints.getConstraints());

        constraints.setConstraints(1, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(0, 0, 30, 0));
        leftPanel.add(firstNameField, constraints.getConstraints());

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(SSNLabel, constraints.getConstraints());

        constraints.setConstraints(1, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(0, 0, 30, 0));
        leftPanel.add(SSNField, constraints.getConstraints());


        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(lastNameLabel, constraints.getConstraints());

        constraints.setConstraints(1, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(0, 0, 30, 0));
        rightPanel.add(lastNameField, constraints.getConstraints());

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(ticketNumberLabel, constraints.getConstraints());

        constraints.setConstraints(1, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(0, 0, 30, 0));
        rightPanel.add(ticketNumberField, constraints.getConstraints());

        container.add(leftPanel);
        container.add(rightPanel);

        return container;
    }

    private JButton createSearchButton(ArrayList<DisposableObject> callingObjects, Controller controller) {

        JButton searchButton = new JButton("Cerca");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 18));

        searchButton.setBackground(new Color(0, 120, 215));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);

        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        searchButton.setPreferredSize(new Dimension(200, 50));

        searchButton.addActionListener(e -> {
            if (activeFilter.equals("NONE")) {
                new FloatingMessage("Selezionare un tipo di filtro prima di cercare.", searchButton, FloatingMessage.ERROR_MESSAGE);
                return;
            }

            if (activeFilter.equals("FLIGHT")) {

                filteredFlightSearch(callingObjects, controller, searchButton);

            } else if (activeFilter.equals("PASSENGER")) {

                filteredPassengerSearch(callingObjects, controller, searchButton);

            }
        });

        controller.setErrorButton(searchButton);

        return searchButton;
    }

    private void filteredFlightSearch(ArrayList<DisposableObject> callingObjects, Controller controller, JButton searchButton) {

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

                updateResultsPanel(callingObjects, controller, true);

            }
            return;
        }

        updateResultsPanel(callingObjects, controller, true);
    }

    private void filteredPassengerSearch(ArrayList<DisposableObject> callingObjects, Controller controller, JButton searchButton) {

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String passengerSSN = SSNField.getText();
        String ticketNumber = ticketNumberField.getText();

        if (controller != null) {

            bookingDates = new ArrayList<>();
            bookingStatus = new ArrayList<>();
            flightIds = new ArrayList<>();

            controller.searchBooksLoogedCustomerFilteredPassengers(firstName, lastName, passengerSSN, ticketNumber, bookingDates, bookingStatus, flightIds, searchButton);

            updateResultsPanel(callingObjects, controller, true);
        }
    }


    private void updateResultsPanel(ArrayList<DisposableObject> callingObjects, Controller controller, boolean ifSearched) {

        SearchBookingResultPanel resultsPanel = new SearchBookingResultPanel(callingObjects, controller,
                                                                             bookingDates, bookingStatus, flightIds);

        resultsScrollPane.setViewportView(resultsPanel);

        resultsScrollPane.setBorder(BorderFactory.createEmptyBorder());

        resultsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        resultsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

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
}