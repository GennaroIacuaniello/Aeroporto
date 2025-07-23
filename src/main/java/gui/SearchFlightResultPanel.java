package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * The SearchFlightResultPanel class displays search results for flights in a tabular format.
 * This panel presents flight information including company name, route, date, departure and arrival times,
 * delay information, flight status, seat availability, and booking options. It uses a custom table model
 * to manage the data and provides interactive booking buttons for available flights.
 * The panel also handles the case of empty search results by displaying an appropriate message.
 */
public class SearchFlightResultPanel extends JPanel {

    /**
     * The table component that displays the flight search results.
     * This may be a standard JTable or a custom JTableWithEmptyMessage depending on whether
     * there are search results to display.
     */
    private final JTable resultsTable;

    /**
     * The data model for the results table.
     * This model manages the flight data and defines how it should be displayed in the table.
     */
    private final FlightTableModel tableModel;

    /**
     * Instantiates a new Search flight result panel with the specified flight data.
     * This constructor creates and configures the table to display flight search results.
     * It sets up the table model with the provided flight data, configures the table appearance,
     * and adds a mouse listener to handle booking actions when a user clicks on a booking button.
     * If no search results are found, it displays an appropriate message.
     *
     * @param callingObjects the list of disposable objects in the application hierarchy,
     *                       used for navigation when booking a flight
     * @param controller     the main controller that manages application state and business logic
     * @param ids            the list of flight IDs for the search results
     * @param companyNames   the list of airline company names for each flight
     * @param dates          the list of flight dates
     * @param departureTimes the list of departure times for each flight
     * @param arrivalTimes   the list of arrival times for each flight
     * @param delays         the list of delay durations in minutes for each flight
     * @param status         the list of flight statuses (e.g., PROGRAMMED, CANCELLED)
     * @param maxSeats       the list of maximum seat capacities for each flight
     * @param freeSeats      the list of available seats for each flight
     * @param cities         the list of destination/origin cities for each flight
     * @param ifSearched     boolean indicating whether a search has been performed,
     *                       used to determine whether to show the table header
     */
    public SearchFlightResultPanel(List<DisposableObject> callingObjects, Controller controller, List<String> ids, List<String> companyNames, List<Date> dates, List<Time> departureTimes, List<Time> arrivalTimes,
                                   List<Integer> delays, List<String> status, List<Integer> maxSeats, List<Integer> freeSeats, List<String> cities, boolean ifSearched) {

        super(new BorderLayout());
        this.setBackground(Color.WHITE);

        //se searchedFlights fosse null, darebbe nullPointerException, quindi gli passo una lista vuota
        tableModel = new FlightTableModel( controller, ids, companyNames, dates, departureTimes, arrivalTimes,
                                          delays, status, maxSeats, freeSeats, cities);

        boolean hasResults = (ids != null && !ids.isEmpty());

        if (ifSearched && !hasResults)
            resultsTable = new JTableWithEmptyMessage(tableModel, "Nessun risultato per i parametri di ricerca impostati");
        else
            resultsTable = new JTable(tableModel);

        resultsTable.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                int col = table.columnAtPoint(point);
                if (table.getSelectedRow() != -1 && row != -1 && col == tableModel.getColumnCount() - 1) {


                    int index = table.rowAtPoint(point);   //index of the selectedFlight


                    if(freeSeats.get(index) > 0 && status.get(index).equalsIgnoreCase("PROGRAMMED")){

                        controller.getFlightController().setFlight(index);

                        if(controller.loadAndCheckIfOpenMyBookingsOrNewBooking()){
                            new MyBookingsCustomerMainFrame(callingObjects, controller, callingObjects.getLast().getFrame().getSize(),
                                    callingObjects.getLast().getFrame().getLocation(), callingObjects.getLast().getFrame().getExtendedState(), false);
                        }else{
                            new Book(callingObjects, controller, callingObjects.getLast().getFrame().getSize(),
                                    callingObjects.getLast().getFrame().getLocation(), callingObjects.getLast().getFrame().getExtendedState());
                        }

                        callingObjects.get(callingObjects.size() - 2).getFrame().setVisible(false);

                    }

                }
            }
        });

        setTableApperance();

        JTableHeader header = resultsTable.getTableHeader();

        //Per mostrare l'intestazione solo dopo avere effettivamente premuto "Cerca" almeno una volta
        header.setVisible(ifSearched);

        this.add(header, BorderLayout.NORTH);
        this.add(resultsTable, BorderLayout.CENTER);

    }

    /**
     * Configures the visual appearance of the results table.
     * This method sets fonts, colors, row heights, and other visual properties of the table
     * to create a consistent and user-friendly display. It also configures cell renderers
     * to center-align text and alternate row colors for better readability, and sets up
     * a special renderer for the booking button column.
     */
    private void setTableApperance() {

        resultsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        resultsTable.getTableHeader().setBackground(new Color(230, 230, 230));
        resultsTable.getTableHeader().setReorderingAllowed(false);

        resultsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        resultsTable.setRowHeight(35);
        resultsTable.setRowSelectionAllowed(false);
        resultsTable.setFillsViewportHeight(true);
        resultsTable.setGridColor(new Color(220, 220, 220));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    //alternanza di colori nel risultato della ricerca
                    c.setBackground(row % 2 == 0 ? new Color(248, 248, 248) : Color.WHITE);
                }
                return c;
            }
        };
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < resultsTable.getColumnCount() - 1; i++) {
            resultsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        int bookColumnIndex = tableModel.getColumnCount() - 1;
        resultsTable.getColumnModel().getColumn(bookColumnIndex).setCellRenderer(new ButtonRenderer());

    }

    /**
     * The FlightTableModel class provides the data model for the flight results table.
     * This class extends AbstractTableModel and manages the flight data to be displayed in the table.
     * It defines the structure of the table (columns and their names), provides access to the data,
     * and formats the data appropriately for display (e.g., converting time values to formatted strings,
     * translating flight status codes to user-friendly text).
     */
    private static class FlightTableModel extends AbstractTableModel {

        /**
         * Reference to the main controller for accessing application state and business logic.
         */
        private final Controller controller;

        /**
         * List of flight IDs for the search results.
         */
        private final ArrayList<String> ids;

        /**
         * List of airline company names for each flight.
         */
        private final ArrayList<String> companyNames;

        /**
         * List of flight dates.
         */
        private final ArrayList<Date> dates;

        /**
         * List of departure times for each flight.
         */
        private final ArrayList<Time> departureTimes;

        /**
         * List of arrival times for each flight.
         */
        private final ArrayList<Time> arrivalTimes;

        /**
         * List of delay durations in minutes for each flight.
         */
        private final ArrayList<Integer> delays;

        /**
         * List of flight statuses (e.g., PROGRAMMED, CANCELLED).
         */
        private final ArrayList<String> status;

        /**
         * List of maximum seat capacities for each flight.
         */
        private final ArrayList<Integer> maxSeats;

        /**
         * List of available seats for each flight.
         */
        private final ArrayList<Integer> freeSeats;

        /**
         * List of destination/origin cities for each flight.
         */
        private final ArrayList<String> cities;

        /**
         * Array of column names for the table header.
         */
        private final String[] colNames = {"Compagnia", "Tratta", "Data", "Partenza", "Ritardo", "Arrivo", "Stato", "Posti", "Prenotazione"};

        /**
         * Instantiates a new Flight table model with the specified flight data.
         * This constructor initializes the model with lists of flight information that will be
         * displayed in the table. It stores references to all the provided data lists and
         * the controller for later use in rendering the table cells.
         *
         * @param controller        the main controller that provides access to application state
         *                          and business logic, particularly flight type information
         * @param parIds            the list of flight IDs for the search results
         * @param parCompanyNames   the list of airline company names for each flight
         * @param parDates          the list of flight dates
         * @param parDepartureTimes the list of departure times for each flight
         * @param parArrivalTimes   the list of arrival times for each flight
         * @param parDelays         the list of delay durations in minutes for each flight
         * @param parStatus         the list of flight statuses (e.g., PROGRAMMED, CANCELLED)
         * @param parMaxSeats       the list of maximum seat capacities for each flight
         * @param parFreeSeats      the list of available seats for each flight
         * @param parCities         the list of destination/origin cities for each flight
         */
        public FlightTableModel( Controller controller, List<String> parIds, List<String> parCompanyNames, List<Date> parDates, List<Time> parDepartureTimes, List<Time> parArrivalTimes,
                                List<Integer> parDelays, List<String> parStatus, List<Integer> parMaxSeats, List<Integer> parFreeSeats, List<String> parCities) {

            this.controller = controller;
            this.ids = (ArrayList<String>) parIds;
            this.companyNames= (ArrayList<String>) parCompanyNames;
            this.dates = (ArrayList<Date>) parDates;
            this.departureTimes = (ArrayList<Time>) parDepartureTimes;
            this.arrivalTimes = (ArrayList<Time>) parArrivalTimes;
            this.delays = (ArrayList<Integer>) parDelays;
            this.status = (ArrayList<String>) parStatus;
            this.maxSeats = (ArrayList<Integer>) parMaxSeats;
            this.freeSeats = (ArrayList<Integer>) parFreeSeats;
            this.cities = (ArrayList<String>) parCities;

        }

        /**
         * Gets the list of available seats for each flight.
         * This method provides access to the freeSeats list, which contains the number
         * of seats still available for booking on each flight. This information is used
         * by the ButtonRenderer to determine whether booking buttons should be enabled.
         *
         * @return the list of available seats for each flight
         */
        public List<Integer> getFreeSeats(){
            return freeSeats;
        }

        /**
         * Gets the list of flight statuses.
         * This method provides access to the status list, which contains the current status
         * of each flight (e.g., PROGRAMMED, CANCELLED, DELAYED). This information is used
         * by the ButtonRenderer to determine whether booking buttons should be enabled,
         * as only flights with certain statuses can be booked.
         *
         * @return the list of status codes for each flight
         */
        public List<String> getStatus(){
            return status;
        }

        @Override
        public int getRowCount() {
            return ids.size();
        }

        @Override
        public int getColumnCount() {
            return colNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return colNames[column];
        }

        @Override
        public Object getValueAt(int row, int col) {

            int hours;
            int minutes;

            switch (col) {
                case 0:
                    return companyNames.get(row);
                case 1:
                    if (controller.getFlightController().getFlightType(row))
                        return "Napoli → " + cities.get(row);
                    else
                        return cities.get(row) + " → Napoli";

                case 2:
                    return dates.get(row).toString();
                case 3:
                    hours = departureTimes.get(row).getHours();
                    minutes = departureTimes.get(row).getMinutes();

                    if(hours < 10){
                        if(minutes < 10)
                            return  "0" + hours +  ":" + "0" + minutes;
                        else
                            return  "0" + hours +  ":" + minutes;
                    }else{
                        if(minutes < 10)
                            return  hours +  ":" + "0" + minutes;
                        else
                            return  hours +  ":" + minutes;
                    }

                case 4:
                    int delay = delays.get(row);
                    return delay > 0 ? delay + " min" : "--";
                case 5:
                    hours = arrivalTimes.get(row).getHours();
                    minutes = arrivalTimes.get(row).getMinutes();

                    if(hours < 10){
                        if(minutes < 10)
                            return  "0" + hours +  ":" + "0" + minutes;
                        else
                            return  "0" + hours +  ":" + minutes;
                    }else{
                        if(minutes < 10)
                            return  hours +  ":" + "0" + minutes;
                        else
                            return  hours +  ":" + minutes;
                    }
                case 6:
                    switch (status.get(row).toUpperCase()){
                        case "PROGRAMMED":
                            return "In programma";
                        case "CANCELLED":
                            return "Cancellato";
                        case "DELAYED":
                            return "In ritardo";
                        case "ABOUT_TO_DEPART":
                            return "In partenza";
                        case "DEPARTED":
                            return "Partito";
                        case "ABOUT_TO_ARRIVE":
                            return "In arrivo";
                        case "LANDED":
                            return "Atterrato";
                        default:
                            return null;
                    }
                case 7:
                    return freeSeats.get(row) + "/" + maxSeats.get(row);
                case 8:
                    return "Prenota";
                default:
                    return null;
            }
        }
    }


    private static class ButtonRenderer extends JButton implements TableCellRenderer {

        /**
         * Instantiates a new Button renderer.
         */
        public ButtonRenderer() {
            setOpaque(true);
            setFont(new Font("Segoe UI", Font.BOLD, 12));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {


            setText((value == null) ? "" : value.toString());

            FlightTableModel model = (FlightTableModel) table.getModel();

            setEnabled(model.getFreeSeats().get(row) > 0 && (model.getStatus().get(row)).equalsIgnoreCase("PROGRAMMED"));

            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }

            return this;
        }
    }


    private static class JTableWithEmptyMessage extends JTable {

        private final String emptyMessage;

        /**
         * Instantiates a new J table with empty message.
         *
         * @param model        the model
         * @param emptyMessage the empty message
         */
        public JTableWithEmptyMessage(AbstractTableModel model, String emptyMessage) {

            super(model);
            this.emptyMessage = emptyMessage;

        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            if (getRowCount() == 0) {

                Graphics2D g2d = (Graphics2D) g;

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.GRAY);

                g2d.setFont(new Font("Segoe UI", Font.ITALIC, 16));

                FontMetrics fm = g2d.getFontMetrics();

                int stringWidth = fm.stringWidth(emptyMessage);
                int stringHeight = fm.getAscent();
                int x = (getWidth() - stringWidth) / 2;
                int y = (getHeight() - stringHeight) / 2 + fm.getAscent();

                g2d.drawString(emptyMessage, x, y);
            }
        }
    }


}
