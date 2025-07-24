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
 * Customer-focused flight search results display a panel providing comprehensive flight information and booking capabilities for the airport management system.
 * <p>
 * This class extends {@link JPanel} to provide a customer interface for displaying flight
 * search results with integrated booking functionality.
 * </p>
 * <p>
 * The SearchFlightResultPanel class provides customer flight result capabilities including:
 * </p>
 * <ul>
 *   <li><strong>Flight Information Display:</strong> Complete flight details with company, route, schedule, and availability information</li>
 *   <li><strong>Booking Integration:</strong> Direct booking initiation with availability validation and workflow coordination</li>
 *   <li><strong>Professional Table Presentation:</strong> Alternating row colors, center alignment, and optimized typography for readability</li>
 *   <li><strong>Interactive Booking Buttons:</strong> Conditional button rendering based on flight availability and status</li>
 *   <li><strong>Empty State Management:</strong> Professional messaging for no-results scenarios with centered display</li>
 *   <li><strong>Status Localization:</strong> Italian language translation for all flight status information</li>
 *   <li><strong>Navigation Integration:</strong> Seamless integration with customer booking workflows and interface hierarchy</li>
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
 * @see MyBookingsCustomerMainFrame
 * @see Book
 */
public class SearchFlightResultPanel extends JPanel {

    /**
     * Primary results table for displaying flight search results with integrated booking functionality.
     * <p>
     * This final JTable component serves as the main display interface for flight search results,
     * </p>
     */
    private final JTable resultsTable;

    /**
     * Custom table model providing flight data management and presentation logic for customer interfaces.
     */
    private final FlightTableModel tableModel;

    /**
     * Constructs a new SearchFlightResultPanel with comprehensive flight results display and integrated booking capabilities for customer workflows.
     * <p>
     * This constructor initializes the complete customer flight results interface by establishing table
     * configuration, data model setup, event handling integration, and visual styling for optimal
     * customer experience during flight result review and booking operations. The constructor creates
     * a fully functional results panel ready for immediate integration within customer flight search
     * workflows with support for both populated results and empty state scenarios.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li><strong>Layout Configuration:</strong> BorderLayout establishment with proper component organization</li>
     *   <li><strong>Visual Styling:</strong> White background configuration for professional appearance and optimal readability</li>
     *   <li><strong>Table Model Setup:</strong> FlightTableModel initialization with controller integration and data binding</li>
     *   <li><strong>Empty State Handling:</strong> Conditional table creation based on result availability with specialized messaging</li>
     *   <li><strong>Event Handler Integration:</strong> MouseAdapter setup for interactive booking access and navigation</li>
     *   <li><strong>Appearance Configuration:</strong> Professional table styling through setTableApperance method delegation</li>
     *   <li><strong>Header Management:</strong> Table header configuration and visibility setup for column identification</li>
     *   <li><strong>Component Assembly:</strong> BorderLayout component placement for optimal interface organization</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and workflow coordination
     * @param controller the system controller providing access to flight information services and booking management functionality
     * @param ids the list of unique flight identifiers for result correlation and detailed flight access
     * @param companyNames the list of airline company names for carrier identification and display
     * @param dates the list of flight dates for scheduling information and temporal organization
     * @param departureTimes the list of flight departure times for schedule display and customer planning
     * @param arrivalTimes the list of flight arrival times for complete schedule information and travel planning
     * @param delays the list of flight delay information in minutes for current status presentation
     * @param status the list of flight operational status information for customer awareness and booking validation
     * @param maxSeats the list of maximum seat capacity for each flight for availability context
     * @param freeSeats the list of available seat counts for booking decision support and validation
     * @param cities the list of destination cities for route information and travel planning
     * @param ifSearched the boolean flag indicating whether a search has been performed for proper header visibility and interface state management
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
     * Configures comprehensive table appearance for flight results display.
     * <p>
     * This private method establishes complete table visual configuration including typography,
     * colors, alignment, and renderer setup for customer flight result display operations.
     * </p>
     * <p>
     * The table appearance configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Header Styling:</strong> Bold typography, background colors, and reordering prevention for professional presentation</li>
     *   <li><strong>Row Configuration:</strong> Height optimization and selection behavior setup for enhanced user interaction</li>
     *   <li><strong>Cell Rendering:</strong> Custom renderer setup with alternating row colors for improved content scanning</li>
     *   <li><strong>Button Integration:</strong> Specialized button renderer configuration for booking column functionality</li>
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
     * Custom table model providing comprehensive flight data management and Italian-localized presentation for customer flight results display.
     * <p>
     * This private static class extends {@link AbstractTableModel} to provide flight data
     * management and presentation capabilities for customer flight results.
     * </p>
     * <p>
     * The table model functionality includes:
     * </p>
     * <ul>
     *   <li><strong>Data Management:</strong> Comprehensive flight information storage and retrieval with controller integration</li>
     *   <li><strong>Temporal Formatting:</strong> Time presentation with zero-padding and HH:MM format consistency</li>
     *   <li><strong>Route Information:</strong> Directional route display with Naples integration and arrow indicators</li>
     *   <li><strong>Availability Display:</strong> Seat availability presentation with free/total seat formatting</li>
     *   <li><strong>Status Management:</strong> Comprehensive flight status handling with delay information presentation</li>
     *   <li><strong>Booking Integration:</strong> Access methods for availability validation and booking workflow coordination</li>
     * </ul>
     */
    private static class FlightTableModel extends AbstractTableModel {

        /**
         * System controller providing access to flight information services.
         */
        private final Controller controller;

        /**
         * Collection of unique flight identifiers for result correlation and detailed flight access.
         */
        private final ArrayList<String> ids;

        /**
         * Collection of airline company names for carrier identification and customer information display.
         */
        private final ArrayList<String> companyNames;

        /**
         * Collection of flight dates for scheduling information and temporal organization.
         */
        private final ArrayList<Date> dates;

        /**
         * Collection of flight departure times for schedule display.
         */
        private final ArrayList<Time> departureTimes;

        /**
         * Collection of flight arrival times for complete schedule information.
         */
        private final ArrayList<Time> arrivalTimes;

        /**
         * Collection of flight delay information in minutes for current status presentation.
         */
        private final ArrayList<Integer> delays;

        /**
         * Collection of flight operational status information for booking validation throughout planning workflows.
         */
        private final ArrayList<String> status;

        /**
         * Collection of maximum seat capacity information for flight availability context.
         */
        private final ArrayList<Integer> maxSeats;

        /**
         * Collection of available seat counts for real-time booking availability.
         */
        private final ArrayList<Integer> freeSeats;

        /**
         * Collection of destination city names for route information display.
         */
        private final ArrayList<String> cities;

        /**
         * Array of column names for table headers for customer interface.
         */
        private final String[] colNames = {"Compagnia", "Tratta", "Data", "Partenza", "Ritardo", "Arrivo", "Stato", "Posti", "Prenotazione"};

        /**
         * Constructs a new FlightTableModel with flight data integration and customer search result presentation.
         * <p>
         * This constructor initializes the complete flight table model by establishing data binding
         * with provided flight information collections and controller integration.
         * The constructor creates a fully functional table model ready for immediate
         * integration within the customer flight result display.
         * </p>
         * <p>
         * The initialization process includes:
         * </p>
         * <ul>
         *   <li><strong>Controller Integration:</strong> System controller reference establishment for business logic access</li>
         *   <li><strong>Data Binding:</strong> Comprehensive flight information collection assignment with type casting</li>
         *   <li><strong>Collection Management:</strong> ArrayList references establishment for all flight data categories</li>
         *   <li><strong>Business Logic Access:</strong> Controller integration for flight type determination and route formatting</li>
         * </ul>
         *
         * @param controller the system controller providing access to flight information services and business logic coordination
         * @param parIds the list of unique flight identifiers for result correlation and detailed flight access
         * @param parCompanyNames the list of airline company names for carrier identification and customer information display
         * @param parDates the list of flight dates for scheduling information and temporal organization
         * @param parDepartureTimes the list of flight departure times for schedule display and customer travel planning
         * @param parArrivalTimes the list of flight arrival times for complete schedule information and travel coordination
         * @param parDelays the list of flight delay information in minutes for current status presentation
         * @param parStatus the list of flight operational status information for customer awareness and booking validation
         * @param parMaxSeats the list of maximum seat capacity for flight availability context and planning support
         * @param parFreeSeats the list of available seat counts for real-time booking availability and decision support
         * @param parCities the list of destination cities for route information display and travel planning coordination
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
         * Returns the collection of available seat counts for booking validation and availability.
         * @return the list of available seat counts for booking validation and customer availability assessment
         */
        public List<Integer> getFreeSeats(){
            return freeSeats;
        }

        /**
         * Returns the collection of flight operational status information for booking validation.
         * @return the list of flight operational status information for booking validation and customer status awareness
         */
        public List<String> getStatus(){
            return status;
        }

        /**
         * Returns the total number of flight results for table row management and display coordination.
         * @return the total number of flight results for table row management and display coordination
         */
        @Override
        public int getRowCount() {
            return ids.size();
        }

        /**
         * Returns the total number of table columns for display management and header coordination.
         * @return the total number of table columns for display management and header coordination
         */
        @Override
        public int getColumnCount() {
            return colNames.length;
        }

        /**
         * Returns the column name for table header display.
         *
         * @param column the column index for header name retrieval and display coordination
         * @return the column name for table header display
         */
        @Override
        public String getColumnName(int column) {
            return colNames[column];
        }

        /**
         * Returns the formatted cell value for comprehensive flight information display.
         * <p>
         * This overridden method from AbstractTableModel provides cell content generation
         * for flight information display including company names, routes, schedules, delays, status,
         * and availability information.
         * </p>
         * <p>
         * The content generation includes:
         * </p>
         * <ul>
         *   <li><strong>Column 0:</strong> Airline company name for carrier identification and customer information</li>
         *   <li><strong>Column 1:</strong> Directional route display with Naples integration and arrow indicators for travel clarity</li>
         *   <li><strong>Column 2:</strong> Flight date with standard toString formatting for schedule reference</li>
         *   <li><strong>Column 3:</strong> Departure time with professional HH:MM formatting and zero-padding for consistency</li>
         *   <li><strong>Column 4:</strong> Delay information with minute display or "--" for no delays for customer awareness</li>
         *   <li><strong>Column 5:</strong> Arrival time with professional HH:MM formatting and zero-padding for schedule completion</li>
         *   <li><strong>Column 6:</strong> Flight status for booking validation</li>
         *   <li><strong>Column 7:</strong> Seat availability with free/total format for booking decision support</li>
         *   <li><strong>Column 8:</strong> Booking button text ("Prenota") for reservation action identification</li>
         * </ul>
         *
         * @param row the row index for flight data retrieval and content generation
         * @param col the column index for specific information type determination and formatting
         * @return the formatted cell value for comprehensive flight information display
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
                    return "Prenota";
                default:
                    return null;
            }
        }
    }

    /**
     * Custom button renderer providing conditional booking button display based on flight availability and operational status.
     * <p>
     * This private static class extends {@link JButton} and implements {@link TableCellRenderer} to provide
     * booking button rendering with conditional enablement based on flight availability and
     * operational status. The ButtonRenderer ensures that booking buttons are only enabled for flights
     * with available seats and "PROGRAMMED" status.
     * </p>
     * <p>
     * The button renderer functionality includes:
     * </p>
     * <ul>
     *   <li><strong>Conditional Enablement:</strong> Button state management based on seat availability and flight status</li>
     *   <li><strong>Selection Integration:</strong> Proper color coordination with table selection states</li>
     *   <li><strong>Availability Validation:</strong> Real-time availability checking for button state determination</li>
     *   <li><strong>Status Awareness:</strong> Flight operational status integration for booking validation</li>
     * </ul>
     */
    private static class ButtonRenderer extends JButton implements TableCellRenderer {

        /**
         * Constructs a new ButtonRenderer with configuration for booking button display.
         */
        public ButtonRenderer() {
            setOpaque(true);
            setFont(new Font("Segoe UI", Font.BOLD, 12));
        }

        /**
         * Returns the configured button component with conditional enablement and professional styling for booking column display.
         * <p>
         * This overridden method from TableCellRenderer provides button configuration
         * including text assignment, conditional enablement based on flight availability and status,
         * and color coordination with table selection states. The method ensures that booking buttons
         * are only enabled for available flights.
         * </p>
         * <p>
         * The button configuration includes:
         * </p>
         * <ul>
         *   <li><strong>Text Assignment:</strong> Button text setting from cell value with null handling</li>
         *   <li><strong>Availability Validation:</strong> Conditional enablement based on free seats and flight status</li>
         *   <li><strong>Color Coordination:</strong> Proper foreground and background color assignment based on selection state</li>
         *   <li><strong>Visual Consistency:</strong> Consistent appearance throughout different interaction states</li>
         * </ul>
         *
         * @param table the JTable component for context and color coordination
         * @param value the cell value for button text assignment and display
         * @param isSelected the selection state for color coordination and visual consistency
         * @param hasFocus the focus state for visual presentation management
         * @param row the row index for flight data access and availability validation
         * @param column the column index for renderer coordination and display management
         * @return the configured button component with conditional enablement and professional styling for booking column display
         */
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

    /**
     * Specialized table component providing professional empty state messaging for no-results scenarios in customer flight search displays.
     * <p>
     * This private static class extends {@link JTable} to provide empty state handling
     * with centered messaging when flight search operations return no matching results.
     * </p>
     * <p>
     * The specialized table functionality includes:
     * </p>
     * <ul>
     *   <li><strong>Empty State Detection:</strong> Automatic detection of zero-row conditions for message display activation</li>
     *   <li><strong>Messaging:</strong> Centered message emptiness display</li>
     * </ul>
     *
     */
    private static class JTableWithEmptyMessage extends JTable {

        /**
         * Message text for empty state display in no-results scenarios.
         */
        private final String emptyMessage;

        /**
         * Constructs a new JTableWithEmptyMessage with specialized empty state messaging for no-results scenarios.
         * <p>
         * The initialization includes:
         * </p>
         * <ul>
         *   <li><strong>Table Model Integration:</strong> Standard AbstractTableModel assignment for data management</li>
         *   <li><strong>Message Storage:</strong> Empty state message text storage for display functionality</li>
         * </ul>
         *
         * @param model the AbstractTableModel providing data management and table functionality
         * @param emptyMessage the Italian message text for empty state display and customer guidance
         */
        public JTableWithEmptyMessage(AbstractTableModel model, String emptyMessage) {

            super(model);
            this.emptyMessage = emptyMessage;

        }

        /**
         * Renders custom empty state messaging with professional typography and centered alignment for optimal customer communication.
         * <p>
         * This overridden method from JTable provides sophisticated custom painting functionality
         * that displays centered Italian messages when the table contains no flight results. The
         * method includes standard table painting followed by conditional empty state message
         * rendering with anti-aliasing, professional typography, and precise center alignment
         * for optimal customer guidance during no-results scenarios throughout flight search workflows.
         * </p>
         * <p>
         * The custom painting process includes:
         * </p>
         * <ul>
         *   <li><strong>Standard Painting:</strong> Parent paintComponent invocation for normal table rendering</li>
         *   <li><strong>Empty State Detection:</strong> Row count verification for message display activation</li>
         *   <li><strong>Graphics Enhancement:</strong> Graphics2D casting and anti-aliasing activation for smooth text rendering</li>
         *   <li><strong>Typography Configuration:</strong> Italic Segoe UI font setup with appropriate sizing and color</li>
         *   <li><strong>Center Alignment:</strong> Precise message positioning calculations for optimal visual presentation</li>
         *   <li><strong>Message Rendering:</strong> Professional text drawing with proper coordinate calculation</li>
         * </ul>
         * <p>
         * Standard painting includes calling the parent paintComponent method to ensure proper
         * table rendering for normal scenarios while preparing the graphics context for additional
         * custom painting operations during empty state scenarios.
         * </p>
         * <p>
         * Empty state detection verifies that the table row count is zero before proceeding
         * with empty state message rendering. The detection ensures that custom messaging
         * is only displayed when appropriate while maintaining standard functionality for
         * populated table scenarios throughout customer flight result presentation.
         * </p>
         * <p>
         * Graphics enhancement includes Graphics2D casting for advanced rendering capabilities
         * and anti-aliasing activation for smooth text presentation. The enhancement ensures
         * professional text rendering quality and optimal readability throughout empty state
         * message display operations within the airport management system interface.
         * </p>
         * <p>
         * Typography configuration establishes italic Segoe UI font (16pt) with gray color
         * for subtle yet clear message presentation. The typography maintains consistency
         * with airport management system design standards while providing appropriate
         * visual weight for customer guidance messaging.
         * </p>
         * <p>
         * Center alignment includes sophisticated coordinate calculations using FontMetrics
         * to determine precise message positioning for optimal visual presentation. The
         * calculations ensure that empty state messages are properly centered both horizontally
         * and vertically within the available table space.
         * </p>
         * <p>
         * Message rendering utilizes precise coordinate calculation with string width and height
         * determination for optimal positioning. The rendering includes proper baseline adjustment
         * and ensures clear message presentation throughout empty result scenarios and customer
         * guidance operations within the airport management system interface.
         * </p>
         *
         * @param g the Graphics context for painting operations and custom message rendering
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