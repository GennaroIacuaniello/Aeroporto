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
 * Administrative flight search results display a panel providing comprehensive flight management capabilities for the airport management system.
 * <p>
 * This class extends {@link JPanel} to provide an administrative interface for displaying flight
 * search results with integrated flight management functionality. The SearchFlightResultPanelAdmin offers
 * flight information presentation, operational management capabilities, and direct access
 * to detailed flight administration through a table-based interface for airport management operations.
 * </p>
 * <p>
 * The SearchFlightResultPanelAdmin class provides comprehensive administrative flight result capabilities including:
 * </p>
 * <ul>
 *   <li><strong>Flight Information Display:</strong> Complete flight details with company, route, schedule, delay, and capacity information</li>
 *   <li><strong>Management Integration:</strong> Direct flight administration access with comprehensive operational control capabilities</li>
 *   <li><strong>Interactive Management Buttons:</strong> Universal button rendering for all flights regardless of status or availability</li>
 *   <li><strong>Empty State Management:</strong> Messaging for no-results scenarios with centered administrative guidance</li>
 *   <li><strong>Navigation Integration:</strong> Seamless integration with administrative management workflows and interface hierarchy</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see JPanel
 * @see Controller
 * @see AbstractTableModel
 * @see MouseAdapter
 * @see BorderLayout
 * @see BookingPageAdmin
 */
public class SearchFlightResultPanelAdmin extends JPanel {

    /**
     * Primary administrative results table for displaying flight search results with integrated management functionality.
     */
    private final JTable resultsTable;

    /**
     * Custom table model providing flight data management and presentation logic for administrative interfaces.
     */
    private final FlightTableModel tableModel;

    /**
     * Constructs a new SearchFlightResultPanelAdmin with comprehensive flight results display and integrated management capabilities for administrative workflows.
     * <p>
     * This constructor initializes the complete administrative flight results interface by establishing table
     * configuration, data model setup, event handling integration. The constructor creates
     * a fully functional administrative results panel ready for integration within administrative flight
     * management workflows with support for both populated results and empty state scenarios.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li><strong>Layout Configuration:</strong> BorderLayout establishment with proper administrative component organization</li>
     *   <li><strong>Table Model Setup:</strong> FlightTableModel initialization with controller integration and comprehensive data binding</li>
     *   <li><strong>Empty State Handling:</strong> Conditional table creation based on result availability with specialized administrative messaging</li>
     *   <li><strong>Event Handler Integration:</strong> MouseAdapter setup for interactive management access and administrative navigation</li>
     *   <li><strong>Appearance Configuration:</strong> Professional table styling through setTableApperance method delegation for administrative presentation</li>
     *   <li><strong>Header Management:</strong> Table header configuration and visibility setup for administrative column identification</li>
     *   <li><strong>Component Assembly:</strong> BorderLayout component placement for optimal administrative interface organization</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and administrative workflow coordination
     * @param controller the system controller providing access to flight information services and comprehensive administrative management functionality
     * @param ids the list of unique flight identifiers for result correlation and detailed administrative flight access
     * @param companyNames the list of airline company names for carrier identification and administrative display
     * @param dates the list of flight dates for scheduling information and administrative temporal organization
     * @param departureTimes the list of flight departure times for schedule display and administrative planning coordination
     * @param arrivalTimes the list of flight arrival times for complete schedule information and administrative travel coordination
     * @param delays the list of flight delay information in minutes for current status presentation and administrative awareness
     * @param status the list of flight operational status information for administrative awareness and management validation
     * @param maxSeats the list of maximum seat capacity for each flight for administrative availability context and resource planning
     * @param freeSeats the list of available seat counts for administrative decision support and capacity management
     * @param cities the list of destination cities for route information display and administrative travel planning coordination
     * @param ifSearched the boolean flag indicating whether a search has been performed for proper administrative header visibility and interface state management
     */
    public SearchFlightResultPanelAdmin(List<DisposableObject> callingObjects, Controller controller, List<String> ids, List<String> companyNames, List<Date> dates, List<Time> departureTimes, List<Time> arrivalTimes,
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

                    controller.getAllForAFlight(index);

                    new BookingPageAdmin(callingObjects, controller, callingObjects.getLast().getFrame().getSize(),
                            callingObjects.getLast().getFrame().getLocation(), callingObjects.getLast().getFrame().getExtendedState());

                    callingObjects.get(callingObjects.size() - 2).getFrame().setVisible(false);


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
     * Configures comprehensive administrative table appearance with professional styling and optimal administrative readability for flight results display.
     * <p>
     * This private method establishes complete administrative table visual configuration including typography,
     * colors, alignment, and renderer setup for administrative flight result display operations.
     * </p>
     * <p>
     * The administrative table appearance configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Header Styling:</strong> Bold typography, background colors, and reordering prevention for professional administrative presentation</li>
     *   <li><strong>Row Configuration:</strong> Height optimization and selection behavior setup for enhanced administrative user interaction</li>
     *   <li><strong>Cell Rendering:</strong> Custom renderer setup with alternating row colors for improved administrative content scanning</li>
     *   <li><strong>Button Integration:</strong> Specialized button renderer configuration for management column functionality and administrative access</li>
     * </ul>
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
     * Custom table model providing comprehensive flight data management and presentation for administrative flight results display.
     * <p>
     * This private static class extends {@link AbstractTableModel} to provide flight data
     * management and presentation capabilities for administrative flight results.
     * </p>
     * <p>
     * The administrative table model functionality includes:
     * </p>
     * <ul>
     *   <li><strong>Data Management:</strong> Comprehensive flight information storage and retrieval with administrative controller integration</li>
     *   <li><strong>Temporal Formatting:</strong> Professional time presentation with zero-padding and HH:MM format consistency for operational scheduling</li>
     *   <li><strong>Route Information:</strong> Directional route display with Naples integration and arrow indicators for operational clarity</li>
     *   <li><strong>Capacity Display:</strong> Clear seat capacity presentation with free/total seat formatting for administrative resource management</li>
     *   <li><strong>Status Management:</strong> Comprehensive flight status handling with delay information presentation for operational oversight</li>
     *   <li><strong>Management Integration:</strong> Administrative access methods for comprehensive flight management workflow coordination</li>
     * </ul>
     */
    private static class FlightTableModel extends AbstractTableModel {

        /**
         * System controller providing access to flight information services.
         */
        private final Controller controller;

        /**
         * Collection of unique flight identifiers for administrative result correlation and detailed flight access.
         */
        private final ArrayList<String> ids;

        /**
         * Collection of airline company names for carrier identification and administrative information display.
         */
        private final ArrayList<String> companyNames;

        /**
         * Collection of flight dates for scheduling information and administrative temporal organization throughout operational planning.
         */
        private final ArrayList<Date> dates;

        /**
         * Collection of flight departure times for schedule display and administrative operational planning coordination.
         */
        private final ArrayList<Time> departureTimes;

        /**
         * Collection of flight arrival times for complete schedule information and administrative operational coordination.
         */
        private final ArrayList<Time> arrivalTimes;

        /**
         * Collection of flight delay information in minutes for current status presentation and administrative operational awareness.
         */
        private final ArrayList<Integer> delays;

        /**
         * Collection of flight operational status information for administrative awareness and management validation throughout operational workflows.
         */
        private final ArrayList<String> status;

        /**
         * Collection of maximum seat capacity information for flight availability context and administrative resource planning support.
         */
        private final ArrayList<Integer> maxSeats;

        /**
         * Collection of available seat counts for administrative capacity management and operational decision support.
         */
        private final ArrayList<Integer> freeSeats;

        /**
         * Collection of destination city names for route information display and administrative operational planning coordination.
         */
        private final ArrayList<String> cities;

        /**
         * Array of column names providing localized administrative table headers for administrative interface.
         */
        private final String[] colNames = {"Compagnia", "Tratta", "Data", "Partenza", "Ritardo", "Arrivo", "Stato", "Posti", "Gestione"};

        /**
         * Constructs a new FlightTableModel with flight data integration for administrative result presentation.
         * <p>
         * This constructor initializes the complete administrative flight table model by establishing data binding
         * with provided flight information collections and controller integration.
         * The constructor creates a fully functional administrative table model ready for immediate
         * integration within the administrative flight result display.
         * </p>
         * <p>
         * The initialization process includes:
         * </p>
         * <ul>
         *   <li><strong>Controller Integration:</strong> System controller reference establishment for administrative business logic access</li>
         *   <li><strong>Data Binding:</strong> Comprehensive flight information collection assignment with administrative type casting</li>
         *   <li><strong>Collection Management:</strong> ArrayList references establishment for all administrative flight data categories</li>
         *   <li><strong>Logic Access:</strong> Controller integration for flight type determination and administrative route formatting</li>
         * </ul>
         *
         * @param controller the system controller providing access to flight information services and administrative business logic coordination
         * @param parIds the list of unique flight identifiers for administrative result correlation and detailed flight access
         * @param parCompanyNames the list of airline company names for carrier identification and administrative information display
         * @param parDates the list of flight dates for scheduling information and administrative temporal organization
         * @param parDepartureTimes the list of flight departure times for schedule display and administrative operational planning
         * @param parArrivalTimes the list of flight arrival times for complete schedule information and administrative coordination
         * @param parDelays the list of flight delay information in minutes for current status presentation and administrative awareness
         * @param parStatus the list of flight operational status information for administrative awareness and management validation
         * @param parMaxSeats the list of maximum seat capacity for administrative availability context and resource planning support
         * @param parFreeSeats the list of available seat counts for administrative capacity management and operational decision support
         * @param parCities the list of destination cities for route information display and administrative operational planning coordination
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
         * Returns the total number of flight results for administrative table row management and display coordination.
         *
         * @return the total number of flight results for administrative table row management and display coordination
         */
        @Override
        public int getRowCount() {
            return ids.size();
        }

        /**
         * Returns the total number of administrative table columns for display management and header coordination.
         *
         * @return the total number of administrative table columns for display management and header coordination
         */
        @Override
        public int getColumnCount() {
            return colNames.length;
        }

        /**
         * Returns the Italian-localized column name for administrative table header display and administrative interface accessibility.
         *
         * @param column the column index for administrative header name retrieval and display coordination
         * @return the Italian-localized column name for administrative table header display and administrative interface accessibility
         */
        @Override
        public String getColumnName(int column) {
            return colNames[column];
        }

        /**
         * Returns the formatted cell value for administrative flight information display.
         * <p>
         * This overridden method from AbstractTableModel provides cell content generation
         * for administrative flight information display including company names, routes, schedules, delays, status,
         * and capacity information.
         * </p>
         * <p>
         * The administrative content generation includes:
         * </p>
         * <ul>
         *   <li><strong>Column 0:</strong> Airline company name for carrier identification and administrative information</li>
         *   <li><strong>Column 1:</strong> Directional route display with Naples integration and arrow indicators for operational clarity</li>
         *   <li><strong>Column 2:</strong> Flight date with standard toString formatting for administrative schedule reference</li>
         *   <li><strong>Column 3:</strong> Departure time with professional HH:MM formatting and zero-padding for administrative consistency</li>
         *   <li><strong>Column 4:</strong> Delay information with minute display or "--" for no delays for administrative awareness</li>
         *   <li><strong>Column 5:</strong> Arrival time with professional HH:MM formatting and zero-padding for administrative schedule completion</li>
         *   <li><strong>Column 6:</strong> Italian-localized flight status for administrative understanding and management validation</li>
         *   <li><strong>Column 7:</strong> Seat capacity with free/total format for administrative resource management and capacity planning</li>
         *   <li><strong>Column 8:</strong> Management button text ("Gestisci") for administrative action identification and workflow access</li>
         * </ul>
         *
         * @param row the row index for flight data retrieval and administrative content generation
         * @param col the column index for specific information type determination and administrative formatting
         * @return the formatted cell value for comprehensive administrative flight information display with Italian localization and professional formatting
         */
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
                    return "Gestisci";
                default:
                    return null;
            }
        }
    }


    /**
     * Custom button renderer providing universal management button display for comprehensive administrative flight management access.
     * <p>
     * This private static class extends {@link JButton} and implements {@link TableCellRenderer} to provide
     * management button rendering with universal enablement for all flights regardless of
     * availability or operational status.
     * </p>
     * <p>
     * The administrative button renderer functionality includes:
     * </p>
     * <ul>
     *   <li><strong>Selection Integration:</strong> Proper color coordination with administrative table selection states</li>
     *   <li><strong>Management Access:</strong> Management button availability for comprehensive administrative oversight</li>
     *   <li><strong>Administrative Consistency:</strong> Standardized button presentation throughout administrative management workflows</li>
     * </ul>
     */
    private static class ButtonRenderer extends JButton implements TableCellRenderer {

        /**
         * Constructs a new ButtonRenderer with pconfiguration for administrative management button display.
         */
        public ButtonRenderer() {
            setOpaque(true);
            setFont(new Font("Segoe UI", Font.BOLD, 12));
        }

        /**
         * Returns the configured button component with universal enablement and professional styling for administrative management column display.
         * <p>
         * This overridden method from TableCellRenderer provides comprehensive button configuration
         * including text assignment, and color coordination with administrative table selection states.
         * </p>
         * <p>
         * The administrative button configuration includes:
         * </p>
         * <ul>
         *   <li><strong>Text Assignment:</strong> Button text setting from cell value with administrative null handling</li>
         *   <li><strong>Color Coordination:</strong> Proper foreground and background color assignment based on administrative selection state</li>
         *   <li><strong>Visual Consistency:</strong> Consistent administrative appearance throughout different interaction states</li>
         * </ul>
         *
         * @param table the JTable component for administrative context and color coordination
         * @param value the cell value for administrative button text assignment and display
         * @param isSelected the selection state for administrative color coordination and visual consistency
         * @param hasFocus the focus state for administrative visual presentation management
         * @param row the row index for flight data access and administrative management coordination
         * @param column the column index for administrative renderer coordination and display management
         * @return the configured button component with universal enablement and professional styling for administrative management column display
         */
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {


            setText((value == null) ? "" : value.toString());

            setEnabled(true);

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


    /**
     * Specialized table component providing professional empty state messaging for no-results scenarios in administrative flight search displays.
     * <p>
     * This private static class extends {@link JTable} to provide sophisticated empty state handling
     * with centered messaging when flight search operations return no matching results.
     * </p>
     * <p>
     * The specialized administrative table functionality includes:
     * </p>
     * <ul>
     *   <li><strong>Empty State Detection:</strong> Automatic detection of zero-row conditions for administrative message display activation</li>
     *   <li><strong>Messaging:</strong> Centered message display with appropriate typography and administrative styling</li>
     * </ul>
     */
    private static class JTableWithEmptyMessage extends JTable {

        /**
         * Message text for administrative empty state display and administrative guidance during no-results scenarios.
         */
        private final String emptyMessage;

        /**
         * Constructs a new JTableWithEmptyMessage with specialized empty state messaging for administrative no-results scenarios.
         * <p>
         * The initialization includes:
         * </p>
         * <ul>
         *   <li><strong>Table Model Integration:</strong> Standard AbstractTableModel assignment for administrative data management</li>
         *   <li><strong>Message Storage:</strong> Empty state message text storage for administrative display functionality</li>
         * </ul>
         *
         * @param model the AbstractTableModel providing administrative data management and table functionality
         * @param emptyMessage the Italian message text for administrative empty state display and administrative guidance
         */
        public JTableWithEmptyMessage(AbstractTableModel model, String emptyMessage) {

            super(model);
            this.emptyMessage = emptyMessage;

        }

        /**
         * Renders custom administrative empty state messaging with professional typography and centered alignment for optimal administrative communication.
         * <p>
         * This overridden method from JTable provides custom painting functionality
         * that displays centered Italian messages when the administrative table contains no flight results.
         * </p>
         * <p>
         * The custom administrative painting process includes:
         * </p>
         * <ul>
         *   <li><strong>Standard Painting:</strong> Parent paintComponent invocation for normal administrative table rendering</li>
         *   <li><strong>Empty State Detection:</strong> Row count verification for administrative message display activation</li>
         * </ul>
         *
         * @param g the Graphics context for administrative painting operations and custom message rendering
         */
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