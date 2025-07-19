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

public class SearchBookingPanel extends JPanel {

    private JScrollPane resultsScrollPane;

    private Constraints constraints;

    // Pannelli per i filtri
    private JPanel flightFilterPanel;
    private JPanel passengerFilterPanel;
    private String activeFilter = "NONE"; // Tiene traccia del filtro attivo: "NONE", "FLIGHT", "PASSENGER"

    // Componenti per il filtro voli
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

    // Componenti per il filtro passeggeri
    private JLabel firstNameLabel;
    private JTextField firstNameField;
    private JLabel lastNameLabel;
    private JTextField lastNameField;
    private JLabel SSNLabel;
    private JTextField SSNField;
    private JLabel ticketNumberLabel;
    private JTextField ticketNumberField;

    // Pulsanti principali di filtro
    private JButton flightButton;
    private JButton passengerButton;

    // Dati per i risultati
    ArrayList<Date> bookingDates = new ArrayList<>();
    ArrayList<Integer> numPassengers = new ArrayList<>();
    ArrayList<String> ids = new ArrayList<>();

    public SearchBookingPanel(ArrayList<DisposableObject> callingObjects, Controller controller) {
        super();
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.WHITE);
        this.constraints = new Constraints();

        Border lineBorder = BorderFactory.createLineBorder(new Color(200, 200, 200));
        Border emptyBorder = BorderFactory.createEmptyBorder(40, 50, 40, 50);
        this.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));

        setComponents(callingObjects, controller);
    }

    private void setComponents(ArrayList<DisposableObject> callingObjects, Controller controller) {
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

        updateResultsPanel(callingObjects, controller, false);
    }

    private JPanel createMainFilterPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);

        // --- Pannello con i pulsanti di selezione filtro ---
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

        // --- Creazione dei pannelli di filtro specifici ---
        flightFilterPanel = createFlightFilterPanel();
        passengerFilterPanel = createPassengerFilterPanel();

        // Inizialmente i pannelli di filtro non sono visibili
        flightFilterPanel.setVisible(false);
        passengerFilterPanel.setVisible(false);

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.CENTER, 1.0f, 0.0f, new Insets(0, 0, 0, 0));
        mainPanel.add(flightFilterPanel, constraints.getConstraints());
        mainPanel.add(passengerFilterPanel, constraints.getConstraints()); // Aggiunti allo stesso posto, la visibilità li gestisce

        // --- Action Listener per i pulsanti di filtro ---
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

        // --- Pannello Sinistro (Da, Range Date) ---
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setOpaque(false);

        fromLabel = new JLabel("Da:");
        setLabelApperance(fromLabel);
        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(fromLabel, constraints.getConstraints());

        fromField = new JTextField(15);
        fromField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        constraints.setConstraints(1, 0, 3, 1, GridBagConstraints.HORIZONTAL, 0, 10, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(0, 0, 30, 0));
        leftPanel.add(fromField, constraints.getConstraints());

        dateLabel = new JLabel("Range date:");
        setLabelApperance(dateLabel);
        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(dateLabel, constraints.getConstraints());

        dateFrom = new DatePicker();
        dateFrom.getComponentDateTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));
        constraints.setConstraints(1, 1, 1, 1, GridBagConstraints.HORIZONTAL, 0, 10, GridBagConstraints.CENTER, 0.5f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(dateFrom, constraints.getConstraints());

        dateSep = new JLabel("--");
        setLabelApperance(dateSep);
        constraints.setConstraints(2, 1, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(dateSep, constraints.getConstraints());

        dateTo = new DatePicker();
        dateTo.getComponentDateTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));
        constraints.setConstraints(3, 1, 1, 1, GridBagConstraints.HORIZONTAL, 0, 10, GridBagConstraints.CENTER, 0.5f, 0.0f, new Insets(0, 0, 30, 0));
        leftPanel.add(dateTo, constraints.getConstraints());

        // --- Pannello Destro (A, Fascia Oraria) ---
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);

        toLabel = new JLabel("A:");
        setLabelApperance(toLabel);
        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(toLabel, constraints.getConstraints());

        toField = new JTextField(15);
        toField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        constraints.setConstraints(1, 0, 3, 1, GridBagConstraints.HORIZONTAL, 0, 10, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(0, 0, 30, 0));
        rightPanel.add(toField, constraints.getConstraints());

        timeLabel = new JLabel("Fascia oraria:");
        setLabelApperance(timeLabel);
        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(timeLabel, constraints.getConstraints());

        timeFrom = new TimePicker();
        timeFrom.getComponentTimeTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));
        constraints.setConstraints(1, 1, 1, 1, GridBagConstraints.HORIZONTAL, 0, 10, GridBagConstraints.CENTER, 0.5f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(timeFrom, constraints.getConstraints());

        timeSep = new JLabel("--");
        setLabelApperance(timeSep);
        constraints.setConstraints(2, 1, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(timeSep, constraints.getConstraints());

        timeTo = new TimePicker();
        timeTo.getComponentTimeTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));
        constraints.setConstraints(3, 1, 1, 1, GridBagConstraints.HORIZONTAL, 0, 10, GridBagConstraints.CENTER, 0.5f, 0.0f, new Insets(0, 0, 30, 0));
        rightPanel.add(timeTo, constraints.getConstraints());

        container.add(leftPanel);
        container.add(rightPanel);
        return container;
    }

    private JPanel createPassengerFilterPanel() {
        JPanel container = new JPanel(new GridLayout(1, 2, 40, 0));
        container.setOpaque(false);

        // --- Inizializzazione componenti passeggero ---
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

        // --- Pannello Sinistro (Nome, Codice Fiscale) ---
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setOpaque(false);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(firstNameLabel, constraints.getConstraints());
        constraints.setConstraints(1, 0, 1, 1, GridBagConstraints.HORIZONTAL, 0, 10, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(0, 0, 30, 0));
        leftPanel.add(firstNameField, constraints.getConstraints());

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(SSNLabel, constraints.getConstraints());
        constraints.setConstraints(1, 1, 1, 1, GridBagConstraints.HORIZONTAL, 0, 10, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(0, 0, 30, 0));
        leftPanel.add(SSNField, constraints.getConstraints());

        // --- Pannello Destro (Cognome, N. Biglietto) ---
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(lastNameLabel, constraints.getConstraints());
        constraints.setConstraints(1, 0, 1, 1, GridBagConstraints.HORIZONTAL, 0, 10, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(0, 0, 30, 0));
        rightPanel.add(lastNameField, constraints.getConstraints());

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(ticketNumberLabel, constraints.getConstraints());
        constraints.setConstraints(1, 1, 1, 1, GridBagConstraints.HORIZONTAL, 0, 10, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(0, 0, 30, 0));
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
                handleFlightSearch(callingObjects, controller, searchButton);
            } else if (activeFilter.equals("PASSENGER")) {
                handlePassengerSearch(callingObjects, controller, searchButton);
            }
        });

        return searchButton;
    }

    private void handleFlightSearch(ArrayList<DisposableObject> callingObjects, Controller controller, JButton searchButton) {
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
                numPassengers = new ArrayList<>();
                ids = new ArrayList<>();
                //controller.getFlightController().searchFlightCustomer(origin, destination, dateBefore, dateAfter, timeBefore, timeAfter,
                  //      bookingDates, numPassengers, ids, searchButton);

                bookingDates.add(new Date(1));
                numPassengers.add(1);
                ids.add("Ciao");
                updateResultsPanel(callingObjects, controller, true);
            }
            return; // Uscita anticipata per evitare l'aggiornamento pannello vuoto
        }

        updateResultsPanel(callingObjects, controller, true); // Aggiorna pannello in caso di errore
    }

    private void handlePassengerSearch(ArrayList<DisposableObject> callingObjects, Controller controller, JButton searchButton) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String ssn = SSNField.getText();
        String ticketNumber = ticketNumberField.getText();

        if (firstName.isEmpty() && lastName.isEmpty() && ssn.isEmpty() && ticketNumber.isEmpty()) {
            new FloatingMessage("Specificare almeno un criterio di ricerca per il passeggero.", searchButton, FloatingMessage.ERROR_MESSAGE);
            updateResultsPanel(callingObjects, controller, true);
            return;
        }

        if (controller != null) {
            // TODO: Chiamare il metodo del controller appropriato per la ricerca per passeggero.
            // Esempio: controller.getBookingController().searchByPassenger(firstName, lastName, ssn, ticketNumber, ...);

            // Logica di placeholder
            bookingDates = new ArrayList<>();
            numPassengers = new ArrayList<>();
            ids = new ArrayList<>();
            //controller.getFlightController().searchFlightCustomer(origin, destination, dateBefore, dateAfter, timeBefore, timeAfter,
             //       bookingDates, numPassengers, ids, searchButton);

            bookingDates.add(new Date(1));
            numPassengers.add(1);
            ids.add("Ciao");
            updateResultsPanel(callingObjects, controller, true);

            //new FloatingMessage("La ricerca per passeggero non è ancora implementata.", searchButton, FloatingMessage.Er);
            //updateResultsPanel(callingObjects, controller, true);
        }
    }


    private void updateResultsPanel(ArrayList<DisposableObject> callingObjects, Controller controller, boolean ifSearched) {
        // Assumiamo esista una classe SearchBookingResultPanel simile a quella per i voli
        SearchBookingResultPanel resultsPanel = new SearchBookingResultPanel(callingObjects, controller,
                bookingDates, numPassengers, ids);

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