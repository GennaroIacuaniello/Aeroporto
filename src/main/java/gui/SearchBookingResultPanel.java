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
import java.util.ArrayList;
import java.util.List;

/**
 * Comprehensive booking search results display panel providing detailed booking information presentation and interactive booking management for the airport management system.
 * <p>
 * This class extends {@link JPanel} to provide sophisticated booking search result display functionality within the
 * {@link SearchBookingPanel} interface. The SearchBookingResultPanel serves as the primary result presentation
 * component for displaying customer booking information in a comprehensive, tabular format with interactive
 * elements for detailed booking management and navigation throughout customer booking workflows.
 * </p>
 * <p>
 * The SearchBookingResultPanel class provides comprehensive booking result display functionality including:
 * </p>
 * <ul>
 *   <li><strong>Tabular Data Presentation:</strong> Professional table display with comprehensive booking information columns</li>
 *   <li><strong>Empty Result Handling:</strong> Specialized empty message display for no-result scenarios with user-friendly messaging</li>
 *   <li><strong>Interactive Elements:</strong> Clickable info buttons for detailed booking access and management operations</li>
 *   <li><strong>Visual Styling:</strong> Professional table appearance with alternating row colors and consistent typography</li>
 *   <li><strong>Italian Localization:</strong> Complete Italian language support for booking status, flight status, and interface labels</li>
 *   <li><strong>Navigation Integration:</strong> Seamless integration with booking detail pages and customer management workflows</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see JPanel
 * @see SearchBookingPanel
 * @see BookingPageCustomer
 * @see Controller
 * @see AbstractTableModel
 * @see JTable
 * @see MouseAdapter
 */
public class SearchBookingResultPanel extends JPanel {

    /**
     * Primary results display a table for comprehensive booking information presentation.
     * <p>
     * This final JTable component serves as the main display interface for booking search results,
     * presenting booking information in a structured, tabular format with professional styling
     * and interactive elements. The table integrates with the BookingTableModel for data
     * management and includes event handling for customer interaction.
     * </p>
     */
    private final JTable resultsTable;
    
    /**
     * Comprehensive table model for booking data management and presentation control.
     * <p>
     * This final BookingTableModel provides the data management layer for the results table,
     * handling booking information, retrieval, formatting, and presentation. The model includes
     * Italian localization, status translation.
     * </p>
     */
    private final BookingTableModel tableModel;

    /**
     * Constructs a new SearchBookingResultPanel with comprehensive booking result display and interactive management capabilities.
     * <p>
     * This constructor initializes the complete booking result display interface by establishing table
     * configuration, data model setup, event handling integration, and visual styling for optimal
     * customer experience during booking result review and management operations. The constructor
     * creates a fully functional result panel ready for immediate integration within booking search
     * workflows with support for both populated results and empty state scenarios.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li><strong>Layout Configuration:</strong> BorderLayout establishment with proper component organization</li>
     *   <li><strong>Visual Styling:</strong> White background configuration for professional appearance and optimal readability</li>
     *   <li><strong>Table Model Setup:</strong> BookingTableModel initialization with controller integration and data binding</li>
     *   <li><strong>Empty State Handling:</strong> Conditional table creation based on result availability with specialized messaging</li>
     *   <li><strong>Event Handler Integration:</strong> MouseAdapter setup for interactive booking access and navigation</li>
     *   <li><strong>Appearance Configuration:</strong> Professional table styling through setTableApperance method delegation</li>
     *   <li><strong>Header Management:</strong> Table header configuration and visibility setup for column identification</li>
     *   <li><strong>Component Assembly:</strong> BorderLayout component placement for optimal interface organization</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and workflow coordination
     * @param controller the system controller providing access to booking data management and navigation functionality
     * @param bookingDates the list of booking creation dates for temporal information display and organization
     * @param bookingStatus the list of booking status strings for current booking state presentation and customer awareness
     * @param ids the list of booking identifiers for result correlation and detailed booking access coordination
     */
    public SearchBookingResultPanel(List<DisposableObject> callingObjects, Controller controller, List<Date> bookingDates, List<String> bookingStatus,
                                    List<String> ids) {
                                    //la lista di id è in parallelo con quella delle prenotazioni, e in base a quelli prendo poi il volo associato dal FlightController

        super(new BorderLayout());
        this.setBackground(Color.WHITE);

        tableModel = new BookingTableModel( controller, bookingDates, bookingStatus);

        boolean hasResults = (ids != null && !ids.isEmpty());

        if (!hasResults)
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


                    int index = table.rowAtPoint(point);   //index of the selectedBooking

                    controller.getAllLuggagesForABooking(index);

                    new BookingPageCustomer(callingObjects, controller, callingObjects.getLast().getFrame().getSize(),
                                            callingObjects.getLast().getFrame().getLocation(), callingObjects.getLast().getFrame().getExtendedState());


                    callingObjects.get(callingObjects.size() - 2).getFrame().setVisible(false);

                }
            }
        });

        setTableApperance();

        JTableHeader header = resultsTable.getTableHeader();

        header.setVisible(true);

        this.add(header, BorderLayout.NORTH);
        this.add(resultsTable, BorderLayout.CENTER);

    }

    /**
     * Configures comprehensive table appearance with professional styling and optimal visual presentation.
     * <p>
     * This private method establishes complete table visual configuration including typography,
     * colors, alignment, and renderer setup to ensure professional appearance and optimal
     * readability throughout booking result display operations. The method implements consistent
     * styling standards that align with the airport management system's visual design principles
     * and enhances customer experience during booking result review and interaction.
     * </p>
     * <p>
     * The table appearance configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Header Styling:</strong> Bold typography, background colors, and reordering prevention for professional presentation</li>
     *   <li><strong>Content Typography:</strong> Segoe UI font configuration with appropriate sizing for optimal readability</li>
     *   <li><strong>Row Configuration:</strong> Height optimization and selection behavior setup for enhanced user interaction</li>
     *   <li><strong>Visual Enhancement:</strong> Grid colors, viewport filling, and professional color scheme application</li>
     *   <li><strong>Cell Rendering:</strong> Custom renderer setup with alternating row colors and center alignment</li>
     *   <li><strong>Button Integration:</strong> Specialized button renderer for interactive info column presentation</li>
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
     * Comprehensive table model for booking data management with Italian localization and professional data presentation.
     * <p>
     * This private static class extends {@link AbstractTableModel} to provide sophisticated booking data
     * management and presentation capabilities for the results table. The BookingTableModel includes
     * comprehensive Italian localization for all status fields, proper data formatting for temporal
     * information, and dynamic content generation based on booking data retrieved through controller
     * integration throughout customer booking result display operations.
     * </p>
     * <p>
     * The table model functionality includes:
     * </p>
     * <ul>
     *   <li><strong>Data Management:</strong> Comprehensive booking and flight information storage and retrieval</li>
     *   <li><strong>Italian Localization:</strong> Complete status translation for booking and flight states</li>
     *   <li><strong>Temporal Formatting:</strong> Professional date and time presentation with zero-padding</li>
     *   <li><strong>Route Information:</strong> Flight route display with directional indicators and Naples integration</li>
     *   <li><strong>Status Translation:</strong> Comprehensive Italian language support for all status indicators</li>
     *   <li><strong>Interactive Elements:</strong> Info button column for detailed booking access functionality</li>
     * </ul>
     */
    private static class BookingTableModel extends AbstractTableModel {

        /**
         * System controller reference for comprehensive booking and flight data access.
         */
        private final Controller controller;
        
        /**
         * Collection of booking creation dates for temporal information display and organization.
         */
        private final ArrayList<Date> bookingDates;
        
        /**
         * Collection of booking status information for the current booking state presentation.
         */
        private final ArrayList<String> bookingStatus;

        /**
         * Column header names with comprehensive Italian localization for customer-friendly interface presentation.
         */
        private final String[] colNames = {"Data prenotazione", "Stato prenotazione", "Compagnia", "Tratta", "Data volo", "Partenza", "Arrivo", "Stato del volo", "Info"};

        /**
         * Constructs a new BookingTableModel with comprehensive data management and controller integration.
         * <p>
         * This constructor initializes the booking table model with complete data binding
         * and controller integration for dynamic content generation and real-time information
         * access. The constructor establishes proper data storage, type casting, and reference
         * management for optimal table performance and data consistency throughout booking
         * result display operations within the airport management system.
         * </p>
         * <p>
         * The initialization process includes:
         * </p>
         * <ul>
         *   <li><strong>Controller Integration:</strong> System controller reference establishment for data access</li>
         *   <li><strong>Date Collection Setup:</strong> Booking dates ArrayList casting and storage for temporal information</li>
         *   <li><strong>Status Collection Setup:</strong> Booking status ArrayList casting and storage for state information</li>
         *   <li><strong>Data Validation:</strong> Implicit validation through final field assignment and type safety</li>
         * </ul>
         * <p>
         * Controller integration establishes the system controller reference that enables
         * comprehensive access to booking information, flight details, and status data
         * throughout table operations. The integration supports dynamic content generation
         * and real-time information retrieval for accurate result presentation.
         * </p>
         * <p>
         * Data collection setup includes proper ArrayList casting for type safety and
         * performance optimization. The casting ensures consistent data access patterns
         * and supports efficient table operations throughout booking result display
         * and customer interaction scenarios.
         * </p>
         * <p>
         * The constructor establishes a fully functional table model ready for immediate
         * integration with JTable components, providing comprehensive booking data
         * presentation capabilities with Italian localization and professional formatting
         * throughout customer booking search and management workflows.
         * </p>
         *
         * @param controller the system controller providing access to comprehensive booking and flight data management services
         * @param parBookingDates the list of booking creation dates for temporal information display and organization
         * @param bookingStatus the list of booking status strings for current booking state presentation and customer awareness
         */
        public BookingTableModel( Controller controller, List<Date> parBookingDates, List<String> bookingStatus) {

            this.controller = controller;
            this.bookingDates = (ArrayList<Date>) parBookingDates;
            this.bookingStatus = (ArrayList<String>) bookingStatus;

        }

        /**
         * Returns the total number of booking rows for table display management and navigation support.
         * <p>
         * This method provides the row count for table display operations by returning the size
         * of the booking dates collection. The row count enables proper table sizing, scrolling
         * behavior, and navigation support throughout booking result presentation and customer
         * interaction with the results table interface.
         * </p>
         *
         * @return the total number of booking entries available for display in the results table
         */
        @Override
        public int getRowCount() {
            return bookingDates.size();
        }

        /**
         * Returns the total number of table columns for consistent table structure and header management.
         * <p>
         * This method provides the column count for table display operations by returning the
         * length of the column names array. The column count ensures consistent table structure,
         * proper header display, and accurate column management throughout booking result
         * presentation and table rendering operations.
         * </p>
         *
         * @return the total number of columns defined for the booking results table display
         */
        @Override
        public int getColumnCount() {
            return colNames.length;
        }

        /**
         * Returns the localized column header name for table column identification and customer navigation.
         * <p>
         * This method provides Italian-language column header names for customer-friendly table
         * presentation and clear column identification. The method supports proper table header
         * display and ensures consistent localization throughout booking result presentation
         * and customer interaction with the results interface.
         * </p>
         *
         * @param column the zero-based column index for header name retrieval
         * @return the Italian-language column header name for customer-friendly table presentation
         */
        @Override
        public String getColumnName(int column) {
            return colNames[column];
        }

        /**
         * Returns formatted booking data values with comprehensive Italian localization and professional presentation.
         * <p>
         * This method provides dynamic content generation for table cell display by retrieving
         * and formatting booking information based on row and column coordinates. The method
         * includes comprehensive Italian localization for all status fields, professional
         * temporal formatting with zero-padding, intelligent route presentation, and proper
         * data validation throughout booking result display operations.
         * </p>
         * <p>
         * The content generation includes:
         * </p>
         * <ul>
         *   <li><strong>Column 0:</strong> Booking creation date with standard toString() formatting</li>
         *   <li><strong>Column 1:</strong> Booking status with Italian translation (CONFIRMED→Confermata, PENDING→In attesa, CANCELLED→Cancellata)</li>
         *   <li><strong>Column 2:</strong> Flight company status with comprehensive Italian localization for all flight states</li>
         *   <li><strong>Column 3:</strong> Flight route with directional arrows and Naples integration based on flight type</li>
         *   <li><strong>Column 4:</strong> Flight date with consistent formatting for scheduling reference</li>
         *   <li><strong>Column 5:</strong> Departure time with HH:MM format and proper zero-padding for hours and minutes</li>
         *   <li><strong>Column 6:</strong> Arrival time with consistent HH:MM format and zero-padding for professional presentation</li>
         *   <li><strong>Column 7:</strong> Flight status with comprehensive Italian translations for all operational states</li>
         *   <li><strong>Column 8:</strong> Info button text for interactive booking access functionality</li>
         * </ul>
         * <p>
         * Booking status translation provides comprehensive Italian localization with proper
         * case handling and fallback null return for unknown status values. The translation
         * ensures clear customer understanding of booking states throughout result display
         * and supports professional interface presentation.
         * </p>
         * <p>
         * Flight company status includes sophisticated controller integration to retrieve
         * current flight status information with comprehensive Italian translation covering
         * all operational states including PROGRAMMED, CANCELLED, DELAYED, ABOUT_TO_DEPART,
         * DEPARTED, ABOUT_TO_ARRIVE, and LANDED with appropriate localized descriptions.
         * </p>
         * <p>
         * Route presentation utilizes intelligent flight type detection to determine proper
         * directional formatting with arrow indicators (→). The presentation shows either
         * "Napoli → Destination" for departing flights or "Origin → Napoli" for arriving
         * flights, providing immediate visual clarity for travel direction.
         * </p>
         * <p>
         * Temporal formatting includes sophisticated time presentation with proper zero-padding
         * for both hours and minutes. The formatting handles all time value scenarios with
         * conditional logic for hours less than 10 and minutes less than 10, ensuring
         * consistent HH:MM presentation throughout all time displays.
         * </p>
         * <p>
         * Controller integration enables comprehensive data access through multiple controller
         * methods including flight information retrieval, status determination, and temporal
         * data access. The integration provides real-time information accuracy and supports
         * dynamic content generation based on current system state.
         * </p>
         * <p>
         * The method ensures comprehensive data presentation with professional formatting,
         * complete Italian localization, and accurate information display throughout
         * customer booking result browsing and management operations within the airport
         * management system interface.
         * </p>
         *
         * @param row the zero-based row index for booking data retrieval and content generation
         * @param col the zero-based column index for specific data field identification and formatting
         * @return the formatted and localized data value for the specified table cell position
         */
        @Override
        public Object getValueAt(int row, int col) {

            int hours;
            int minutes;

            switch (col) {
                case 0:
                    return bookingDates.get(row).toString();
                case 1:
                    switch (bookingStatus.get(row).toUpperCase()){
                        case "CONFIRMED":
                            return "Confermata";
                        case "PENDING":
                            return "In attesa";
                        case "CANCELLED":
                            return "Cancellata";
                        default:
                            return null;
                    }
                case 2:
                    return controller.getBookingController().getSearchBookingResult().get(row).getBookedFlight().getCompanyName();
                case 3:
                    if (controller.getFlightController().getBookingResultSelectedFlightFlightType(controller.getBookingController().getSearchBookingResult().get(row).getBookedFlight().getId()))
                        return "Napoli → " + controller.getFlightController().getBookingResultSelectedFlightCity(controller.getBookingController().getSearchBookingResult().get(row).getBookedFlight().getId());
                    else
                        return controller.getFlightController().getBookingResultSelectedFlightCity(controller.getBookingController().getSearchBookingResult().get(row).getBookedFlight().getId()) + " → Napoli";
                case 4:
                    return controller.getFlightController().getBookingResultSelectedFlightDate(controller.getBookingController().getSearchBookingResult().get(row).getBookedFlight().getId()).toString();
                case 5:
                    hours = controller.getFlightController().getBookingResultSelectedFlightDepartureTime(controller.getBookingController().getSearchBookingResult().get(row).getBookedFlight().getId()).getHours();
                    minutes = controller.getFlightController().getBookingResultSelectedFlightDepartureTime(controller.getBookingController().getSearchBookingResult().get(row).getBookedFlight().getId()).getMinutes();

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
                    hours = controller.getFlightController().getBookingResultSelectedFlightArrivalTime(controller.getBookingController().getSearchBookingResult().get(row).getBookedFlight().getId()).getHours();
                    minutes = controller.getFlightController().getBookingResultSelectedFlightArrivalTime(controller.getBookingController().getSearchBookingResult().get(row).getBookedFlight().getId()).getMinutes();

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

                case 7:
                    switch (controller.getFlightController().getBookingResultSelectedFlightStatusString(controller.getBookingController().getSearchBookingResult().get(row).getBookedFlight().getId()).toUpperCase()){
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

                case 8:
                    return "Info";
                default:
                    return null;
            }
        }
    }


    /**
     * Professional button renderer for interactive info column presentation with consistent styling and state management.
     * <p>
     * This private static class extends {@link JButton} and implements {@link TableCellRenderer} to provide
     * specialized button rendering for the info column in the booking results table. The ButtonRenderer
     * ensures professional button appearance with proper state management, consistent typography, and
     * appropriate visual feedback for customer interaction throughout booking result navigation operations.
     * </p>
     * <p>
     * The button renderer functionality includes:
     * </p>
     * <ul>
     *   <li><strong>Professional Styling:</strong> Consistent typography and visual appearance for button elements</li>
     *   <li><strong>State Management:</strong> Proper selection state handling with appropriate color coordination</li>
     *   <li><strong>Visual Integration:</strong> Seamless integration with table styling and color schemes</li>
     *   <li><strong>Typography Consistency:</strong> Bold Segoe UI font configuration for clear button text presentation</li>
     * </ul>
     */
    private static class ButtonRenderer extends JButton implements TableCellRenderer {

        /**
         * Constructs a new ButtonRenderer with professional styling and consistent visual presentation.
         * <p>
         * This constructor initializes the button renderer with optimal visual configuration
         * including opaque rendering and professional typography setup. The constructor establishes
         * the foundation for consistent button appearance throughout info column rendering
         * and customer interaction with booking result navigation elements.
         * </p>
         * <p>
         * The initialization includes:
         * </p>
         * <ul>
         *   <li><strong>Opacity Configuration:</strong> Opaque rendering setup for proper visual integration</li>
         *   <li><strong>Typography Setup:</strong> Bold Segoe UI font configuration for clear text presentation</li>
         * </ul>
         */
        public ButtonRenderer() {
            setOpaque(true);
            setFont(new Font("Segoe UI", Font.BOLD, 12));
        }

        /**
         * Returns a configured button component with proper styling and state management for table cell rendering.
         * <p>
         * This method provides comprehensive button configuration for table cell rendering operations
         * including text setup, state-based color coordination, and visual appearance management.
         * The method ensures professional button presentation with appropriate visual feedback
         * for different selection states throughout customer interaction with booking result
         * navigation elements and info column functionality.
         * </p>
         * <p>
         * The rendering configuration includes:
         * </p>
         * <ul>
         *   <li><strong>Text Configuration:</strong> Proper button text setup with null value handling</li>
         *   <li><strong>Selection State Management:</strong> Color coordination based on selection status</li>
         *   <li><strong>Visual Integration:</strong> Appropriate color schemes for selected and unselected states</li>
         *   <li><strong>Professional Presentation:</strong> Consistent styling throughout different rendering scenarios</li>
         * </ul>
         *
         * @param table the JTable component containing the button cell for context and styling coordination
         * @param value the cell value for button text configuration and content presentation
         * @param isSelected the selection state flag for appropriate color coordination and visual feedback
         * @param hasFocus the focus state flag for proper visual state management and customer interaction
         * @param row the zero-based row index for cell position identification and context management
         * @param column the zero-based column index for cell position identification and rendering coordination
         * @return the configured button component ready for table cell rendering with professional styling
         */
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {


            setText((value == null) ? "" : value.toString());

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
     * Specialized table implementation with custom empty message display for enhanced user experience during no-result scenarios.
     * <p>
     * This private static class extends {@link JTable} to provide specialized functionality for displaying
     * user-friendly empty state messages when booking search operations return no results. The
     * JTableWithEmptyMessage includes custom painting logic that presents centered Italian-language
     * messages with professional styling and optimal visual presentation throughout empty result
     * scenarios within the airport management system interface.
     * </p>
     * <p>
     * The specialized table functionality includes:
     * </p>
     * <ul>
     *   <li><strong>Empty State Detection:</strong> Automatic detection of zero-row scenarios for message display activation</li>
     *   <li><strong>Custom Message Painting:</strong> Professional message rendering with center alignment and appropriate typography</li>
     *   <li><strong>Italian Localization:</strong> User-friendly Italian-language empty state messaging for customer guidance</li>
     *   <li><strong>Visual Integration:</strong> Seamless integration with table styling and professional appearance standards</li>
     *   <li><strong>Typography Management:</strong> Italic Segoe UI font configuration for clear message presentation</li>
     * </ul>
     */
    private static class JTableWithEmptyMessage extends JTable {

        /**
         * Empty state message text for user-friendly guidance during no-result scenarios.
         */
        private final String emptyMessage;

        /**
         * Constructs a new JTableWithEmptyMessage with custom empty state messaging and professional presentation.
         * <p>
         * This constructor initializes the specialized table with comprehensive empty state
         * functionality including custom message storage and table model integration. The
         * constructor establishes the foundation for user-friendly empty result presentation
         * with Italian-language messaging and professional visual styling throughout booking
         * search scenarios that return no matching results.
         * </p>
         * <p>
         * The initialization process includes:
         * </p>
         * <ul>
         *   <li><strong>Parent Initialization:</strong> Standard JTable construction with provided table model</li>
         *   <li><strong>Message Storage:</strong> Empty state message storage for custom painting operations</li>
         * </ul>
         *
         * @param model the AbstractTableModel providing table data management and structure for empty state detection
         * @param emptyMessage the Italian-language message text for user-friendly empty state presentation and customer guidance
         */
        public JTableWithEmptyMessage(AbstractTableModel model, String emptyMessage) {

            super(model);
            this.emptyMessage = emptyMessage;

        }

        /**
         * Performs custom painting with empty state message display and professional visual presentation.
         * <p>
         * This method overrides the standard JTable painting behavior to provide enhanced empty
         * state presentation with custom message display when no table rows are available. The
         * painting implementation includes sophisticated Graphics2D rendering with anti-aliasing,
         * center alignment calculations, and professional typography for optimal user experience
         * during no-result scenarios throughout booking search operations.
         * </p>
         * <p>
         * The custom painting process includes:
         * </p>
         * <ul>
         *   <li><strong>Standard Painting:</strong> Parent component painting for normal table rendering</li>
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
         * Empty state detection utilizes getRowCount() verification to determine when custom
         * message display should be activated. The detection ensures that empty state messaging
         * only appears when no booking results are available, maintaining proper interface
         * behavior throughout different search result scenarios.
         * </p>
         * <p>
         * Graphics enhancement includes Graphics2D casting for advanced rendering capabilities
         * and anti-aliasing activation (RenderingHints.VALUE_ANTIALIAS_ON) to ensure smooth
         * text rendering. The enhancement provides professional visual quality with smooth
         * text edges that enhance overall interface appearance and readability.
         * </p>
         * <p>
         * Typography configuration establishes italic Segoe UI font (16pt) with gray color
         * for clear message presentation that distinguishes empty state messaging from regular
         * table content. The typography ensures optimal readability while maintaining professional
         * appearance standards throughout empty result presentation.
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