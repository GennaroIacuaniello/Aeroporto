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
 * Modal dialog for managing lost luggage items in the airport management system.
 * <p>
 * This class provides a comprehensive interface for administrators to view, search, and manage
 * all lost luggage items reported throughout the airport system. It displays detailed information
 * about lost luggage including associated passenger details, flight information, and booking data
 * in a structured table format with interactive management capabilities.
 * </p>
 * <p>
 * The LostLuggageDialog class supports comprehensive lost luggage management functionality including:
 * </p>
 * <ul>
 *   <li><strong>Comprehensive Data Display:</strong> Complete lost luggage information with passenger and flight details</li>
 *   <li><strong>Interactive Management:</strong> Click-to-manage functionality for individual luggage items</li>
 *   <li><strong>Professional Table Layout:</strong> Structured display with alternating row colors and proper alignment</li>
 *   <li><strong>Empty State Handling:</strong> Custom empty message display when no lost luggage items exist</li>
 *   <li><strong>Navigation Integration:</strong> Seamless integration with administrative booking management interfaces</li>
 *   <li><strong>Responsive Design:</strong> Adaptive sizing and layout for different screen configurations</li>
 * </ul>
 * <p>
 * The interface is designed with administrative workflow optimization, providing operators with:
 * </p>
 * <ul>
 *   <li><strong>Complete Luggage Overview:</strong> All lost luggage items displayed in a single, organized view</li>
 *   <li><strong>Passenger Identification:</strong> Full passenger details including names and fiscal codes for identification</li>
 *   <li><strong>Flight Context:</strong> Flight IDs and booking dates for operational context and tracking</li>
 *   <li><strong>Direct Management Access:</strong> One-click access to detailed luggage management interfaces</li>
 *   <li><strong>Professional Presentation:</strong> Clean, organized display optimized for administrative use</li>
 * </ul>
 * <p>
 * Data display architecture provides comprehensive lost luggage information through seven columns:
 * flight ID for operational context, booking date for timeline tracking, passenger names for
 * identification, fiscal codes for verification, luggage IDs for tracking, and management
 * actions for direct operational access.
 * </p>
 * <p>
 * Interactive management capabilities enable administrators to click on any luggage item's
 * management button to access detailed administrative interfaces including booking management,
 * passenger information, and luggage recovery operations through seamless integration with
 * the {@link BookingPageAdmin} interface.
 * </p>
 * <p>
 * Visual design features include:
 * </p>
 * <ul>
 *   <li><strong>Professional Styling:</strong> Consistent fonts, colors, and spacing matching application standards</li>
 *   <li><strong>Alternating Row Colors:</strong> Enhanced readability through subtle row color variations</li>
 *   <li><strong>Center-Aligned Content:</strong> Optimal text alignment for structured data presentation</li>
 *   <li><strong>Interactive Buttons:</strong> Styled management buttons with clear action indicators</li>
 *   <li><strong>Responsive Layout:</strong> Adaptive sizing maintaining usability across different displays</li>
 * </ul>
 * <p>
 * The dialog integrates seamlessly with the airport management system's navigation hierarchy,
 * supporting proper resource management and maintaining operational context during luggage
 * management workflows. Navigation integration ensures smooth transitions between lost luggage
 * overview and detailed management interfaces.
 * </p>
 * <p>
 * Table functionality includes specialized handling for empty states through the custom
 * {@link JTableWithEmptyMessage} component that displays user-friendly messages when no
 * lost luggage items are present, preventing confusion and providing clear system status.
 * </p>
 * <p>
 * The modal design ensures focused administrative attention on lost luggage management
 * tasks while maintaining integration with the parent administrative interface. The dialog
 * supports standard window management operations including sizing, positioning, and closure.
 * </p>
 * <p>
 * Resource management follows standard dialog lifecycle patterns with proper disposal
 * and cleanup during navigation transitions, ensuring optimal system performance and
 * preventing resource leaks during extended administrative sessions.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Controller
 * @see BookingPageAdmin
 * @see DisposableObject
 * @see LuggageController
 * @see AbstractTableModel
 */
public class LostLuggageDialog extends JDialog {

    /**
     * Main table component displaying lost luggage information and management options.
     * <p>
     * This JTable displays comprehensive information about all lost luggage items
     * in the system, including passenger details, flight information, and interactive
     * management buttons. The table supports custom rendering and empty state handling
     * for optimal administrative user experience.
     * </p>
     */
    private final JTable luggageTable;
    
    /**
     * Custom table model managing lost luggage data display and structure.
     * <p>
     * This specialized AbstractTableModel handles the organization and presentation
     * of lost luggage data including column definitions, data access methods, and
     * proper integration with the table display components for comprehensive
     * lost luggage management functionality.
     * </p>
     */
    private final LostBaggageTableModel tableModel;

    /**
     * Constructs a new LostLuggageDialog for comprehensive lost luggage management and overview.
     * <p>
     * This constructor initializes the complete lost luggage management interface by retrieving
     * all lost luggage data from the system, configuring the display table with proper styling
     * and interactive capabilities, and establishing navigation integration for seamless
     * administrative workflows. The constructor creates a fully functional modal dialog
     * ready for immediate lost luggage management operations.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li><strong>Data Retrieval:</strong> Comprehensive lost luggage data collection from the controller system</li>
     *   <li><strong>Table Configuration:</strong> Professional table setup with custom styling and interactive features</li>
     *   <li><strong>Event Handler Setup:</strong> Mouse event handling for interactive luggage management operations</li>
     *   <li><strong>Visual Styling:</strong> Professional appearance configuration with proper fonts and colors</li>
     *   <li><strong>Layout Management:</strong> Dialog sizing and positioning for optimal administrative use</li>
     *   <li><strong>Navigation Integration:</strong> Seamless integration with administrative interface hierarchy</li>
     * </ul>
     * <p>
     * Data retrieval leverages the controller's getLostLuggages method to populate multiple
     * data collections with comprehensive information about all lost luggage items including
     * flight IDs, booking dates, passenger names and identification, and luggage tracking
     * information necessary for administrative management operations.
     * </p>
     * <p>
     * Table configuration includes creating the specialized {@link LostBaggageTableModel}
     * with retrieved data and establishing either a standard JTable for populated data
     * or a custom {@link JTableWithEmptyMessage} for empty state handling with user-friendly
     * messaging when no lost luggage items are present.
     * </p>
     * <p>
     * Event handler setup establishes comprehensive mouse event handling for interactive
     * luggage management operations. Click detection on management buttons triggers navigation
     * to detailed administrative interfaces through the {@link BookingPageAdmin} component
     * with proper resource management and interface state coordination.
     * </p>
     * <p>
     * Visual styling includes comprehensive table appearance configuration through the
     * setTableAppearance method, establishing professional fonts, colors, alignment,
     * and rendering for optimal administrative interface presentation and usability.
     * </p>
     * <p>
     * Layout management configures the dialog with appropriate sizing (1200x400), minimum
     * size constraints (1000x120), center positioning relative to the parent frame, and
     * proper disposal behavior for standard modal dialog operation patterns.
     * </p>
     * <p>
     * Navigation integration ensures that luggage management operations maintain proper
     * integration with the administrative interface hierarchy, supporting seamless
     * transitions between lost luggage overview and detailed management interfaces
     * while maintaining operational context and resource management.
     * </p>
     * <p>
     * The dialog layout uses BorderLayout with the table header positioned at PAGE_START
     * and the scrollable table content in the CENTER, providing optimal space utilization
     * and standard dialog organization patterns for administrative interfaces.
     * </p>
     *
     * @param owner the parent frame that owns this modal dialog for proper window management
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management
     * @param controller the system controller providing access to lost luggage data and management functionality
     */
    public LostLuggageDialog(Frame owner, List<DisposableObject> callingObjects, Controller controller) {

        super(owner, "Gestione Bagagli Smarriti", true);

        List<String> flightIds = new ArrayList<>();
        List<Date> bookingDates = new ArrayList<>();
        List<String> firstNames = new ArrayList<>();
        List<String> lastNames = new ArrayList<>();
        List<String> passengerSSNs = new ArrayList<>();
        List<String> luggageIds = new ArrayList<>();

        controller.getLostLuggages(flightIds, bookingDates, firstNames, lastNames, passengerSSNs, luggageIds);

        tableModel = new LostBaggageTableModel(flightIds, bookingDates, firstNames, lastNames, passengerSSNs, luggageIds);

        if (luggageIds.isEmpty())
            luggageTable = new LostLuggageDialog.JTableWithEmptyMessage(tableModel, "Nessun bagaglio smarrito.");
        else
            luggageTable = new JTable(tableModel);

        luggageTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                int col = table.columnAtPoint(point);


                if (row != -1 && col == tableModel.getColumnCount() - 1) {

                    int index = table.rowAtPoint(point);   //index of the selectedBooking

                    controller.setBookingResultSelectedFlightForLostLuaggages(index);

                    new BookingPageAdmin(callingObjects, controller, callingObjects.getLast().getFrame().getSize(),
                            callingObjects.getLast().getFrame().getLocation(), callingObjects.getLast().getFrame().getExtendedState());

                    dispose();
                    callingObjects.get(callingObjects.size() - 2).getFrame().setVisible(false);

                }
            }
        });

        setTableAppearance();

        JScrollPane scrollPane = new JScrollPane(luggageTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JTableHeader header = luggageTable.getTableHeader();
        this.add(header, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);

        this.setSize(1200, 400);
        this.setMinimumSize(new Dimension(1000, 120));

        this.setLocationRelativeTo(owner);

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }

    /**
     * Configures comprehensive visual styling and presentation for the lost luggage display table.
     * <p>
     * This method establishes professional table appearance including fonts, colors, alignment,
     * and custom rendering to ensure optimal readability and administrative usability. The styling
     * creates a clean, organized presentation of lost luggage data with enhanced visual hierarchy
     * and interactive elements for efficient administrative operations.
     * </p>
     * <p>
     * Table appearance configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Header Styling:</strong> Professional header fonts and background colors for clear section identification</li>
     *   <li><strong>Content Styling:</strong> Readable content fonts and row height optimization for data presentation</li>
     *   <li><strong>Row Color Alternation:</strong> Subtle alternating row backgrounds for enhanced readability</li>
     *   <li><strong>Content Alignment:</strong> Center alignment for structured data presentation and visual consistency</li>
     *   <li><strong>Interactive Elements:</strong> Custom button rendering for management action columns</li>
     *   <li><strong>Grid and Border Management:</strong> Grid color configuration and border management for clean presentation</li>
     * </ul>
     * <p>
     * Header styling includes configuring the table header with bold Segoe UI font (14pt)
     * and light gray background (230, 230, 230) for clear visual separation between header
     * and content areas. Header reordering is disabled to maintain consistent column
     * organization and prevent administrative confusion.
     * </p>
     * <p>
     * Content styling establishes readable content presentation through plain Segoe UI font
     * (14pt), optimized row height (35px) for comfortable reading, and disabled row selection
     * to prevent interface confusion during administrative operations. The table fills the
     * viewport height for optimal space utilization.
     * </p>
     * <p>
     * Row color alternation is implemented through a custom {@link DefaultTableCellRenderer}
     * that applies alternating background colors (light gray 248, 248, 248 for even rows
     * and white for odd rows) to enhance data readability and provide visual structure
     * for large datasets of lost luggage information.
     * </p>
     * <p>
     * Content alignment includes center alignment for all data columns to provide structured,
     * professional presentation of lost luggage information including flight IDs, dates,
     * names, and identification codes with consistent visual organization.
     * </p>
     * <p>
     * Interactive elements include custom button rendering for the management action column
     * through the {@link ButtonRenderer} class, providing clear visual indication of
     * clickable management options while maintaining integration with table styling.
     * </p>
     * <p>
     * Grid and border management includes setting appropriate grid colors (220, 220, 220)
     * for subtle visual structure without overwhelming the data presentation, ensuring
     * clean, professional appearance suitable for administrative interfaces.
     * </p>
     */
    private void setTableAppearance() {

        luggageTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        luggageTable.getTableHeader().setBackground(new Color(230, 230, 230));

        luggageTable.getTableHeader().setReorderingAllowed(false);

        luggageTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        luggageTable.setRowHeight(35);
        luggageTable.setRowSelectionAllowed(false);

        luggageTable.setFillsViewportHeight(true);

        luggageTable.setGridColor(new Color(220, 220, 220));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(248, 248, 248) : Color.WHITE);
                }
                return c;
            }
        };
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < luggageTable.getColumnCount() - 1; i++) {
            luggageTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        int manageColumnIndex = tableModel.getColumnCount() - 1;
        luggageTable.getColumnModel().getColumn(manageColumnIndex).setCellRenderer(new ButtonRenderer());
    }

    /**
     * Specialized table model for managing lost luggage data display and organization.
     * <p>
     * This class extends {@link AbstractTableModel} to provide comprehensive data management
     * for lost luggage information display. It handles the organization of multiple data
     * collections into a structured table format with proper column definitions, data
     * access methods, and integration with table display components.
     * </p>
     * <p>
     * The LostBaggageTableModel provides specialized functionality including:
     * </p>
     * <ul>
     *   <li><strong>Multi-Collection Data Management:</strong> Organization of flight IDs, booking dates, passenger information, and luggage IDs</li>
     *   <li><strong>Column Structure Definition:</strong> Seven-column layout with descriptive headers for comprehensive data display</li>
     *   <li><strong>Data Access Methods:</strong> Efficient data retrieval for table rendering and display operations</li>
     *   <li><strong>Italian Localization:</strong> Column headers and interface elements localized for Italian administrative users</li>
     * </ul>
     * <p>
     * The table model manages seven distinct data collections corresponding to different
     * aspects of lost luggage information: flight identifiers for operational context,
     * booking dates for timeline tracking, passenger names for identification, fiscal
     * codes for verification, and luggage IDs for tracking purposes.
     * </p>
     * <p>
     * Column structure includes: "ID Volo" (Flight ID), "Data Prenotazione" (Booking Date),
     * "Nome" (First Name), "Cognome" (Last Name), "Codice Fiscale" (Fiscal Code),
     * "ID Bagaglio" (Luggage ID), and "Azione" (Action) for comprehensive lost luggage
     * information presentation and management access.
     * </p>
     * <p>
     * Data access methods provide efficient retrieval of information for table rendering,
     * including proper data type handling for dates, string formatting for display,
     * and management button text generation for interactive administrative operations.
     * </p>
     *
     * @see AbstractTableModel
     * @see JTable
     */
    private static class LostBaggageTableModel extends AbstractTableModel {

        /**
         * Collection of flight identifiers associated with lost luggage items.
         * <p>
         * This list contains flight IDs for all lost luggage items, providing
         * operational context and enabling flight-based tracking and management
         * of lost luggage recovery operations.
         * </p>
         */
        private final List<String> flightIds;
        
        /**
         * Collection of booking dates associated with lost luggage items.
         * <p>
         * This list contains booking dates for lost luggage items, providing
         * timeline context for luggage loss events and supporting chronological
         * organization of recovery operations and customer service activities.
         * </p>
         */
        private final List<Date> bookingDates;
        
        /**
         * Collection of passenger first names associated with lost luggage items.
         * <p>
         * This list contains first names of passengers whose luggage has been
         * reported as lost, enabling passenger identification and supporting
         * customer service operations and recovery coordination.
         * </p>
         */
        private final List<String> names;
        
        /**
         * Collection of passenger surnames associated with lost luggage items.
         * <p>
         * This list contains surnames of passengers whose luggage has been
         * reported as lost, providing complete passenger identification for
         * customer service operations and luggage recovery coordination.
         * </p>
         */
        private final List<String> surnames;
        
        /**
         * Collection of passenger fiscal codes associated with lost luggage items.
         * <p>
         * This list contains fiscal codes (Italian tax identification numbers)
         * of passengers whose luggage has been reported as lost, providing
         * definitive passenger identification for administrative and legal purposes.
         * </p>
         */
        private final List<String> fiscalCodes;
        
        /**
         * Collection of luggage identifiers for lost luggage items.
         * <p>
         * This list contains unique luggage IDs for all lost luggage items,
         * enabling precise luggage tracking, recovery operations, and administrative
         * management of individual luggage cases throughout the recovery process.
         * </p>
         */
        private final List<String> baggageIds;

        /**
         * Column names array defining the table structure and headers.
         * <p>
         * This array contains the Italian-localized column headers for the lost
         * luggage table: Flight ID, Booking Date, First Name, Last Name, Fiscal Code,
         * Luggage ID, and Action column for comprehensive data display and management.
         * </p>
         */
        private final String[] colNames = {"ID Volo", "Data Prenotazione", "Nome", "Cognome", "Codice Fiscale", "ID Bagaglio", "Azione"};

        /**
         * Constructs a new LostBaggageTableModel with comprehensive lost luggage data collections.
         * <p>
         * This constructor initializes the table model with all necessary data collections
         * for comprehensive lost luggage display and management. The constructor establishes
         * proper data organization and ensures that all collections are properly synchronized
         * for accurate table rendering and administrative operations.
         * </p>
         * <p>
         * Data collection initialization includes:
         * </p>
         * <ul>
         *   <li><strong>Flight Information:</strong> Flight IDs and booking dates for operational context</li>
         *   <li><strong>Passenger Identification:</strong> Names and fiscal codes for customer identification</li>
         *   <li><strong>Luggage Tracking:</strong> Luggage IDs for precise tracking and management</li>
         *   <li><strong>Data Synchronization:</strong> Ensuring all collections maintain proper index correlation</li>
         * </ul>
         * <p>
         * The constructor establishes immutable references to all data collections,
         * ensuring data integrity and consistency throughout the table model lifecycle.
         * All collections are expected to be pre-populated and properly synchronized
         * by the calling code for accurate table display and operations.
         * </p>
         *
         * @param flightIds list of flight identifiers associated with lost luggage items
         * @param bookingDates list of booking dates for lost luggage timeline context
         * @param names list of passenger first names for identification purposes
         * @param surnames list of passenger surnames for complete identification
         * @param fiscalCodes list of passenger fiscal codes for administrative verification
         * @param baggageIds list of luggage identifiers for tracking and management
         */
        public LostBaggageTableModel(List<String> flightIds, List<Date> bookingDates, List<String> names, List<String> surnames, List<String> fiscalCodes, List<String> baggageIds) {

            this.flightIds = flightIds;
            this.bookingDates = bookingDates;
            this.names = names;
            this.surnames = surnames;
            this.fiscalCodes = fiscalCodes;
            this.baggageIds = baggageIds;

        }

        /**
         * Returns the total number of rows in the lost luggage table.
         * <p>
         * This method provides the row count for table rendering by returning
         * the size of the luggage IDs collection, which represents the total
         * number of lost luggage items available for display and management.
         * </p>
         *
         * @return the number of lost luggage items (rows) in the table
         */
        @Override
        public int getRowCount() {
            return baggageIds.size();
        }

        /**
         * Returns the total number of columns in the lost luggage table.
         * <p>
         * This method provides the column count for table rendering by returning
         * the length of the column names array, ensuring proper table structure
         * and header display for comprehensive lost luggage information presentation.
         * </p>
         *
         * @return the number of columns in the lost luggage table (7 columns)
         */
        @Override
        public int getColumnCount() {
            return colNames.length;
        }

        /**
         * Returns the display name for the specified column in the lost luggage table.
         * <p>
         * This method provides localized Italian column headers for table display,
         * ensuring proper identification of data categories and maintaining consistency
         * with Italian administrative interface standards and user expectations.
         * </p>
         *
         * @param column the zero-based column index for header retrieval
         * @return the localized Italian column name for display in the table header
         */
        @Override
        public String getColumnName(int column) {
            return colNames[column];
        }

        /**
         * Returns the data value for the specified cell in the lost luggage table.
         * <p>
         * This method provides data access for table rendering by retrieving the
         * appropriate value from the corresponding data collection based on row
         * and column indices. The method handles different data types appropriately
         * and provides formatted output for optimal display presentation.
         * </p>
         * <p>
         * Column data mapping includes:
         * </p>
         * <ul>
         *   <li><strong>Column 0:</strong> Flight ID from the flightIds collection</li>
         *   <li><strong>Column 1:</strong> Booking date as formatted string from bookingDates collection</li>
         *   <li><strong>Column 2:</strong> Passenger first name from the names collection</li>
         *   <li><strong>Column 3:</strong> Passenger surname from the surnames collection</li>
         *   <li><strong>Column 4:</strong> Passenger fiscal code from the fiscalCodes collection</li>
         *   <li><strong>Column 5:</strong> Luggage ID from the baggageIds collection</li>
         *   <li><strong>Column 6:</strong> Management action button text ("Gestisci")</li>
         * </ul>
         * <p>
         * The method includes proper data type handling, converting Date objects
         * to string representation for display purposes and providing static text
         * for management action buttons to ensure consistent user interface presentation.
         * </p>
         *
         * @param rowIndex the zero-based row index for data retrieval
         * @param columnIndex the zero-based column index for data retrieval
         * @return the formatted data value for display in the specified table cell, or null for invalid indices
         */
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {

                case 0:
                    return flightIds.get(rowIndex);
                case 1:
                    return bookingDates.get(rowIndex).toString();
                case 2:
                    return names.get(rowIndex);
                case 3:
                    return surnames.get(rowIndex);
                case 4:
                    return fiscalCodes.get(rowIndex);
                case 5:
                    return baggageIds.get(rowIndex);
                case 6:
                    return "Gestisci";
                default:
                    return null;
            }
        }

    }

    /**
     * Custom button renderer for displaying interactive management buttons in the lost luggage table.
     * <p>
     * This class extends {@link JButton} and implements {@link TableCellRenderer} to provide
     * specialized button rendering within table cells. It creates interactive management buttons
     * that maintain consistent styling with the table while providing clear visual indication
     * of clickable administrative actions for lost luggage management operations.
     * </p>
     * <p>
     * The ButtonRenderer provides specialized functionality including:
     * </p>
     * <ul>
     *   <li><strong>Professional Button Styling:</strong> Consistent fonts and appearance matching administrative interface standards</li>
     *   <li><strong>Selection State Handling:</strong> Proper visual feedback for button states including selection and focus</li>
     *   <li><strong>Theme Integration:</strong> Integration with system UI themes and color schemes</li>
     *   <li><strong>Accessibility Support:</strong> Proper button behavior and visual cues for administrative users</li>
     * </ul>
     * <p>
     * Button styling includes bold Segoe UI font (12pt) for clear text visibility and
     * opaque rendering to ensure proper integration with table cell rendering and
     * visual consistency throughout the administrative interface.
     * </p>
     * <p>
     * Selection state handling provides appropriate visual feedback by adjusting foreground
     * and background colors based on cell selection state, ensuring that button appearance
     * integrates properly with table selection behavior and administrative user expectations.
     * </p>
     * <p>
     * Theme integration leverages UIManager color properties to maintain consistency
     * with system UI themes, ensuring that button appearance adapts appropriately
     * to different visual themes and accessibility configurations.
     * </p>
     *
     * @see JButton
     * @see TableCellRenderer
     * @see UIManager
     */
    private static class ButtonRenderer extends JButton implements TableCellRenderer {

        /**
         * Constructs a new ButtonRenderer with professional styling for table cell button display.
         * <p>
         * This constructor initializes the button renderer with appropriate styling
         * including opaque rendering for proper table integration and bold font
         * configuration for clear text visibility in administrative interfaces.
         * The constructor establishes the foundation for consistent button appearance
         * throughout the lost luggage management table.
         * </p>
         */
        public ButtonRenderer() {
            setOpaque(true);
            setFont(new Font("Segoe UI", Font.BOLD, 12));
        }

        /**
         * Provides the button component configured for display in the specified table cell.
         * <p>
         * This method implements the TableCellRenderer interface to provide properly
         * configured button components for table cell display. The method handles
         * text content assignment, selection state styling, and theme integration
         * to ensure consistent button appearance and behavior within the table context.
         * </p>
         * <p>
         * Component configuration includes:
         * </p>
         * <ul>
         *   <li><strong>Text Assignment:</strong> Button text from cell value with null safety handling</li>
         *   <li><strong>Selection Styling:</strong> Appropriate colors based on cell selection state</li>
         *   <li><strong>Theme Integration:</strong> System UI color integration for consistent appearance</li>
         *   <li><strong>State Management:</strong> Proper button state configuration for table context</li>
         * </ul>
         * <p>
         * Text assignment includes safe handling of null values and proper string
         * conversion to ensure that button text displays correctly regardless of
         * data content or potential null values in the table model.
         * </p>
         * <p>
         * Selection styling applies appropriate foreground and background colors
         * based on the cell's selection state, using table selection colors for
         * selected cells and standard table colors for unselected cells to maintain
         * visual consistency with table behavior.
         * </p>
         * <p>
         * Theme integration leverages UIManager color properties to ensure that
         * button appearance adapts appropriately to system themes and accessibility
         * settings while maintaining button functionality and visual clarity.
         * </p>
         *
         * @param table the JTable containing the cell being rendered
         * @param value the cell value to display as button text
         * @param isSelected true if the cell is selected, false otherwise
         * @param hasFocus true if the cell has focus, false otherwise
         * @param row the row index of the cell being rendered
         * @param column the column index of the cell being rendered
         * @return the configured button component for display in the table cell
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
     * Specialized table component that displays a custom message when no data is available.
     * <p>
     * This class extends {@link JTable} to provide enhanced user experience when the lost
     * luggage table contains no data. Instead of displaying an empty table, it renders
     * a centered, styled message informing administrators that no lost luggage items are
     * currently present in the system, preventing confusion and providing clear system status.
     * </p>
     * <p>
     * The JTableWithEmptyMessage provides specialized functionality including:
     * </p>
     * <ul>
     *   <li><strong>Custom Empty State Display:</strong> Professional message rendering when no data is present</li>
     *   <li><strong>High-Quality Text Rendering:</strong> Anti-aliased text for optimal visual presentation</li>
     *   <li><strong>Centered Message Layout:</strong> Precise centering calculations for optimal message positioning</li>
     *   <li><strong>Professional Typography:</strong> Italic styling with appropriate font size for message display</li>
     *   <li><strong>Seamless Integration:</strong> Standard table behavior when data is present</li>
     * </ul>
     * <p>
     * Empty state display includes professional message rendering with gray color (Color.GRAY)
     * and italic Segoe UI font (16pt) to provide clear visual distinction between the empty
     * state message and regular table content while maintaining readability and professional appearance.
     * </p>
     * <p>
     * High-quality text rendering leverages Graphics2D anti-aliasing capabilities
     * (RenderingHints.VALUE_ANTIALIAS_ON) to ensure optimal text presentation across
     * different display configurations and resolutions, maintaining professional
     * appearance standards for administrative interfaces.
     * </p>
     * <p>
     * Centered message layout includes precise mathematical calculations using FontMetrics
     * to determine exact text positioning, ensuring that empty state messages are perfectly
     * centered both horizontally and vertically within the available table space regardless
     * of table size or message length.
     * </p>
     * <p>
     * The component maintains standard JTable behavior and functionality when data is present,
     * ensuring that empty state handling does not interfere with normal table operations,
     * rendering, or administrative functionality when lost luggage items are available.
     * </p>
     *
     * @see JTable
     * @see Graphics2D
     * @see FontMetrics
     */
    private static class JTableWithEmptyMessage extends JTable {

        /**
         * The message text to display when the table contains no data.
         * <p>
         * This field stores the localized message that will be rendered
         * in the center of the table when no lost luggage items are present,
         * providing clear communication to administrators about the current
         * system state and preventing interface confusion.
         * </p>
         */
        private final String emptyMessage;

        /**
         * Constructs a new JTableWithEmptyMessage with specified table model and empty state message.
         * <p>
         * This constructor initializes the enhanced table component with standard JTable
         * functionality plus custom empty state message capability. The constructor
         * establishes both the table model for data display and the message text for
         * empty state presentation, creating a comprehensive table component for
         * administrative interfaces.
         * </p>
         * <p>
         * Component initialization includes:
         * </p>
         * <ul>
         *   <li><strong>Standard Table Setup:</strong> JTable initialization with provided AbstractTableModel</li>
         *   <li><strong>Message Configuration:</strong> Empty state message storage for custom rendering</li>
         *   <li><strong>Rendering Preparation:</strong> Foundation setup for custom paint component behavior</li>
         * </ul>
         * <p>
         * The constructor ensures that the table component maintains all standard JTable
         * functionality while adding the enhanced empty state display capability,
         * providing a seamless user experience that adapts appropriately to data availability.
         * </p>
         *
         * @param model the AbstractTableModel providing data structure and content for the table
         * @param emptyMessage the localized message to display when no data is available in the table
         */
        public JTableWithEmptyMessage(AbstractTableModel model, String emptyMessage) {

            super(model);
            this.emptyMessage = emptyMessage;

        }

        /**
         * Renders the table component with custom empty state message display when no data is present.
         * <p>
         * This method overrides the standard JTable paint behavior to provide enhanced empty
         * state handling. When the table contains no data rows, it renders a professionally
         * styled, centered message informing administrators about the empty state. When data
         * is present, it delegates to standard JTable rendering for normal table display.
         * </p>
         * <p>
         * Custom rendering process includes:
         * </p>
         * <ul>
         *   <li><strong>Standard Rendering:</strong> Normal JTable paint behavior when data is present</li>
         *   <li><strong>Empty State Detection:</strong> Row count checking to determine rendering mode</li>
         *   <li><strong>Graphics Configuration:</strong> High-quality text rendering setup with anti-aliasing</li>
         *   <li><strong>Typography Setup:</strong> Professional font configuration for message display</li>
         *   <li><strong>Positioning Calculation:</strong> Precise centering mathematics for optimal message placement</li>
         *   <li><strong>Message Rendering:</strong> Professional text rendering with appropriate styling</li>
         * </ul>
         * <p>
         * Empty state detection uses getRowCount() to determine whether custom message
         * rendering is required, ensuring that empty state display only occurs when
         * no lost luggage data is available for presentation.
         * </p>
         * <p>
         * Graphics configuration includes casting to Graphics2D for advanced rendering
         * capabilities and enabling anti-aliasing (RenderingHints.VALUE_ANTIALIAS_ON)
         * to ensure optimal text quality across different display configurations.
         * </p>
         * <p>
         * Typography setup includes gray color configuration (Color.GRAY) for appropriate
         * visual prominence and italic Segoe UI font (16pt) for professional message
         * presentation that distinguishes empty state text from regular table content.
         * </p>
         * <p>
         * Positioning calculation leverages FontMetrics to determine exact text dimensions
         * and calculate precise horizontal and vertical centering coordinates, ensuring
         * that empty state messages appear perfectly centered regardless of table size
         * or message length variations.
         * </p>
         * <p>
         * Message rendering includes drawing the configured message text at calculated
         * coordinates with proper baseline adjustment for optimal text positioning
         * and visual presentation within the table component bounds.
         * </p>
         *
         * @param g the Graphics context for custom rendering operations
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