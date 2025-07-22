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
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class SearchFlightPanel extends JPanel {

    private JScrollPane resultsScrollPane;

    private Constraints constraints;

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
    private JButton arrivingButton;
    private JButton departingButton;

    private JButton searchButton;

    ArrayList<String> ids = new ArrayList<>();
    ArrayList<String> companyNames = new ArrayList<>();
    ArrayList<Date> dates = new ArrayList<>();
    ArrayList<Time> departureTimes = new ArrayList<>();
    ArrayList<Time> arrivalTimes = new ArrayList<>();
    ArrayList<Integer> delays = new ArrayList<>();
    ArrayList<String> status = new ArrayList<>();
    ArrayList<Integer> maxSeats = new ArrayList<>();
    ArrayList<Integer> freeSeats = new ArrayList<>();
    ArrayList<String> cities = new ArrayList<>();

    //private boolean searchPerformed = false;

    public SearchFlightPanel(ArrayList<DisposableObject> callingObjects, Controller controller) {

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

        JPanel parametersPanel = createSymmetricFormPanel(controller);

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

        controller.setErrorButton(searchButton);
        updateResultsPanel(callingObjects, controller, false);
    }

    private JPanel createSymmetricFormPanel(Controller controller) {

        JPanel container = new JPanel(new GridLayout(1, 2, 40, 0));
        container.setOpaque(false);

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setOpaque(false);

        arrivingButton = new JButton("Cerca voli in arrivo");
        setButtonApperance(arrivingButton);

        constraints.setConstraints(0, 0, 4, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.CENTER, 1.0f, 0.0f, new Insets(0, 0, 35, 0));
        leftPanel.add(arrivingButton, constraints.getConstraints());

        fromLabel = new JLabel("Da:");
        setLabelApperance(fromLabel);

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(fromLabel, constraints.getConstraints());

        fromField = new JTextField(15);
        fromField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        constraints.setConstraints(1, 1, 3, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(0, 0, 30, 0));
        leftPanel.add(fromField, constraints.getConstraints());

        dateLabel = new JLabel("Range date:");
        setLabelApperance(dateLabel);

        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(dateLabel, constraints.getConstraints());

        dateFrom = new DatePicker();
        dateFrom.getComponentDateTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));

        constraints.setConstraints(1, 2, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.CENTER, 0.5f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(dateFrom, constraints.getConstraints());

        dateSep = new JLabel("--");
        setLabelApperance(dateSep);

        constraints.setConstraints(2, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(dateSep, constraints.getConstraints());

        dateTo = new DatePicker();
        dateTo.getComponentDateTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));

        constraints.setConstraints(3, 2, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.CENTER, 0.5f, 0.0f, new Insets(0, 0, 30, 0));
        leftPanel.add(dateTo, constraints.getConstraints());


        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);

        departingButton = new JButton("Cerca voli in partenza");
        setButtonApperance(departingButton);

        constraints.setConstraints(0, 0, 4, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.CENTER, 1.0f, 0.0f, new Insets(0, 0, 35, 0));
        rightPanel.add(departingButton, constraints.getConstraints());

        toLabel = new JLabel("A:");
        setLabelApperance(toLabel);

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(toLabel, constraints.getConstraints());

        toField = new JTextField(15);
        toField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        constraints.setConstraints(1, 1, 3, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(0, 0, 30, 0));
        rightPanel.add(toField, constraints.getConstraints());

        timeLabel = new JLabel("Fascia oraria:");
        setLabelApperance(timeLabel);

        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(timeLabel, constraints.getConstraints());

        timeFrom = new TimePicker();
        timeFrom.getComponentTimeTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));

        constraints.setConstraints(1, 2, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.CENTER, 0.5f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(timeFrom, constraints.getConstraints());

        timeSep = new JLabel("--");
        setLabelApperance(timeSep);

        constraints.setConstraints(2, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(timeSep, constraints.getConstraints());

        timeTo = new TimePicker();
        timeTo.getComponentTimeTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));

        constraints.setConstraints(3, 2, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.CENTER, 0.5f, 0.0f, new Insets(0, 0, 30, 0));
        rightPanel.add(timeTo, constraints.getConstraints());

        container.add(leftPanel);
        container.add(rightPanel);


        arrivingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toField.setText("Napoli");
                //toField.setEnabled(false);
                fromField.setText("");
                //fromField.setEnabled(true);
                fromField.requestFocusInWindow();
            }
        });

        departingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fromField.setText("Napoli");
                //fromField.setEnabled(false);
                toField.setText("");
                //toField.setEnabled(true);
                toField.requestFocusInWindow();
            }
        });

        return container;
    }

    private JButton createSearchButton(ArrayList<DisposableObject> callingObjects, Controller controller) {

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

                executeResearch(callingObjects, controller, searchButton);

            }
        });

        return searchButton;
    }

    private void updateResultsPanel(ArrayList<DisposableObject> callingObjects, Controller controller, boolean ifSearched) {

        if(controller.isLoggedAdmin()){

            SearchFlightResultPanelAdmin resultsPanel = new SearchFlightResultPanelAdmin(callingObjects, controller,
                                                                ids, companyNames, dates, departureTimes, arrivalTimes,
                                                                delays, status, maxSeats, freeSeats, cities, ifSearched);

            resultsScrollPane.setViewportView(resultsPanel);

        }else{
            SearchFlightResultPanel resultsPanel = new SearchFlightResultPanel(callingObjects, controller,
                                                        ids, companyNames, dates, departureTimes, arrivalTimes,
                                                        delays, status, maxSeats, freeSeats, cities, ifSearched);

            resultsScrollPane.setViewportView(resultsPanel);
        }


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

    public void executeResearch(ArrayList<DisposableObject> callingObjects, Controller controller, JButton searchButton){

        String origin = fromField.getText();
        String destination = toField.getText();
        LocalDate dateBefore = dateFrom.getDate();
        LocalDate dateAfter = dateTo.getDate();
        LocalTime timeBefore = timeFrom.getTime();
        LocalTime timeAfter = timeTo.getTime();

        controller.setErrorButton(searchButton);

        if( (fromField.getText().isEmpty() && !toField.getText().isEmpty()) || (!fromField.getText().isEmpty() && toField.getText().isEmpty())){

            new FloatingMessage("Se si specifica una città, vanno specificate entrambe!", searchButton, FloatingMessage.ERROR_MESSAGE);
            updateResultsPanel(callingObjects, controller, true);

        }else if( (dateBefore != null && dateAfter == null) || (dateBefore == null && dateAfter != null) ){

            new FloatingMessage("Errore nel range di date!", searchButton, FloatingMessage.ERROR_MESSAGE);
            updateResultsPanel(callingObjects, controller, true);

        } else if (dateBefore != null /*&& dateAfter != null*/ && dateAfter.isBefore(dateBefore)) {

            new FloatingMessage("La seconda data deve essere successiva alla prima!", searchButton, FloatingMessage.ERROR_MESSAGE);
            updateResultsPanel(callingObjects, controller,true);

        } else if ((timeBefore != null && timeAfter == null) || (timeBefore == null && timeAfter != null)) {

            new FloatingMessage("Errore nella fascia oraria!", searchButton, FloatingMessage.ERROR_MESSAGE);
            updateResultsPanel(callingObjects, controller,true);
        }else{

            if (controller != null) {

                ids = new ArrayList<>();
                companyNames = new ArrayList<>();
                dates = new ArrayList<>();
                departureTimes = new ArrayList<>();
                arrivalTimes = new ArrayList<>();
                delays = new ArrayList<>();
                status = new ArrayList<>();
                maxSeats = new ArrayList<>();
                freeSeats = new ArrayList<>();
                cities = new ArrayList<>();

                controller.getFlightController().searchFlightCustomer(origin, destination, dateBefore, dateAfter, timeBefore, timeAfter,
                        ids, companyNames, dates, departureTimes, arrivalTimes, delays, status,
                        maxSeats, freeSeats, cities, searchButton);

                //searchPerformed = true;
                updateResultsPanel(callingObjects, controller, true);
            }

        }

    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public void setSearchButton(JButton searchButton) {
        this.searchButton = searchButton;
    }
}