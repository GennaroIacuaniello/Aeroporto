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
 * about lost luggage, including associated passenger details, flight information, and booking data
 * in a structured table format with interactive management capabilities.
 * </p>
 * <p>
 * The LostLuggageDialog class supports comprehensive lost luggage management functionality, including:
 * </p>
 * <ul>
 *   <li><strong>Comprehensive Data Display:</strong> Complete lost luggage information with passenger and flight details</li>
 *   <li><strong>Interactive Management:</strong> Click-to-manage functionality for individual luggage items</li>
 *   <li><strong>Professional Table Layout:</strong> Structured display with alternating row colors and proper alignment</li>
 *   <li><strong>Empty State Handling:</strong> Custom empty message display when no lost luggage items exist</li>
 *   <li><strong>Navigation Integration:</strong> Seamless integration with administrative booking management interfaces</li>
 *   <li><strong>Responsive Design:</strong> Adaptive sizing and layout for different screen configurations</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Controller
 * @see BookingPageAdmin
 * @see DisposableObject
 * @see controller.LuggageController
 * @see AbstractTableModel
 */
public class LostLuggageDialog extends JDialog {

    /**
     * The main table component displaying lost luggage information and management options.
     * <p>
     * This JTable displays comprehensive information about all lost luggage items
     * in the system, including passenger details, flight information, and
     * management buttons. The table supports custom rendering and empty state handling.
     * </p>
     */
    private final JTable luggageTable;
    
    /**
     * Custom table model managing lost luggage data display and structure.
     * <p>
     * This specialized AbstractTableModel handles the organization and presentation
     * of lost luggage data including column definitions, data access methods, and
     * integration with the table display components for lost luggage management functionality.
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
     *   <li><strong>Layout Management:</strong> Dialog sizing and positioning for optimal administrative use</li>
     *   <li><strong>Navigation Integration:</strong> Seamless integration with administrative interface hierarchy</li>
     * </ul>
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
     * This method establishes table appearance including fonts, colors, alignment,
     * and rendering to ensure readability and usability.
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
     * </ul>
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
     * </ul>
     *
     * @see AbstractTableModel
     * @see JTable
     */
    private static class LostBaggageTableModel extends AbstractTableModel {

        /**
         * Collection of flight identifiers associated with lost luggage items.
         */
        private final List<String> flightIds;
        
        /**
         * Collection of booking dates associated with lost luggage items.
         */
        private final List<Date> bookingDates;
        
        /**
         * Collection of passenger first names associated with lost luggage items.
         */
        private final List<String> names;
        
        /**
         * Collection of passenger surnames associated with lost luggage items.
         */
        private final List<String> surnames;
        
        /**
         * Collection of passenger fiscal codes associated with lost luggage items.
         */
        private final List<String> fiscalCodes;
        
        /**
         * Collection of luggage identifiers for lost luggage items.
         */
        private final List<String> baggageIds;

        /**
         * Column names array defining the table structure and headers.
         */
        private final String[] colNames = {"ID Volo", "Data Prenotazione", "Nome", "Cognome", "Codice Fiscale", "ID Bagaglio", "Azione"};

        /**
         * Constructs a new LostBaggageTableModel with lost luggage data collections.
         * <p>
         * This constructor initializes the table model with all necessary data collections
         * for comprehensive lost luggage display and management.
         * </p>
         * <p>
         * Data collection initialization includes:
         * </p>
         * <ul>
         *   <li><strong>Flight Information:</strong> Flight IDs and booking dates for operational context</li>
         *   <li><strong>Passenger Identification:</strong> Names and fiscal codes for customer identification</li>
         *   <li><strong>Luggage Tracking:</strong> Luggage IDs for precise tracking and management</li>
         * </ul>
         *
         * @param flightIds list of flight's identifiers associated with lost luggage items
         * @param bookingDates list of booking dates for lost luggage timeline context
         * @param names list of passenger's first-names for identification purposes
         * @param surnames list of passenger's surnames for complete identification
         * @param fiscalCodes list of passenger's fiscal codes for administrative verification
         * @param baggageIds list of luggage's identifiers for tracking and management
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
         *
         * @return the number of lost luggage items (rows) in the table
         */
        @Override
        public int getRowCount() {
            return baggageIds.size();
        }

        /**
         * Returns the total number of columns in the lost luggage table.
         *
         * @return the number of columns in the lost luggage table (7 columns)
         */
        @Override
        public int getColumnCount() {
            return colNames.length;
        }

        /**
         * Returns the display name for the specified column in the lost luggage table.
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
         * and column indices.
         * </p>
         * <p>
         * Column data mapping includes:
         * </p>
         * <ul>
         *   <li><strong>Column 0:</strong> Flight ID from the flightIds collection</li>
         *   <li><strong>Column 1:</strong> Booking date as formatted string from bookingDates collection</li>
         *   <li><strong>Column 2:</strong> Passenger first name from the name collection</li>
         *   <li><strong>Column 3:</strong> Passenger surname from the surname collection</li>
         *   <li><strong>Column 4:</strong> Passenger fiscal code from the fiscalCodes collection</li>
         *   <li><strong>Column 5:</strong> Luggage ID from the baggageIds collection</li>
         *   <li><strong>Column 6:</strong> Management action button text ("Gestisci")</li>
         * </ul>
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
     * specialized button rendering within table cells.
     * </p>
     * <p>
     * The ButtonRenderer provides specialized functionality including:
     * </p>
     * <ul>
     *   <li><strong>Professional Button Styling:</strong> Consistent fonts and appearance matching administrative interface standards</li>
     *   <li><strong>Selection State Handling:</strong> Proper visual feedback for button states including selection and focus</li>
     * </ul>
     *
     * @see JButton
     * @see TableCellRenderer
     * @see UIManager
     */
    private static class ButtonRenderer extends JButton implements TableCellRenderer {

        /**
         * Constructs a new ButtonRenderer with professional styling for table cell button display.
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
     *   <li><strong>Empty State Display:</strong> Professional message rendering when no data is present</li>
     *   <li><strong>Message Layout:</strong> Precise centering calculations for optimal message positioning</li>
     * </ul>
     *
     * @see JTable
     * @see Graphics2D
     * @see FontMetrics
     */
    private static class JTableWithEmptyMessage extends JTable {

        /**
         * The message text to display when the table contains no data.
         */
        private final String emptyMessage;

        /**
         * Constructs a new JTableWithEmptyMessage with a specified table model and empty state message.
         * <p>
         * Component initialization includes:
         * </p>
         * <ul>
         *   <li><strong>Standard Table Setup:</strong> JTable initialization with provided AbstractTableModel</li>
         *   <li><strong>Message Configuration:</strong> Empty state message storage for custom rendering</li>
         * </ul>

         *
         * @param model the AbstractTableModel providing data structure and content for the table
         * @param emptyMessage the localized message to display when no data is available in the table
         */
        public JTableWithEmptyMessage(AbstractTableModel model, String emptyMessage) {

            super(model);
            this.emptyMessage = emptyMessage;

        }

        /**
         * Renders the table component with a custom empty state message display when no data is present.
         * <p>
         * This method overrides the standard JTable paint behavior to provide empty
         * state handling. When the table contains no data rows, it renders a message
         *  informing administrators about the empty state.
         * </p>
         * <p>
         * The custom rendering process includes:
         * </p>
         * <ul>
         *   <li><strong>Standard Rendering:</strong> Normal JTable paint behavior when data is present</li>
         *   <li><strong>Empty State Detection:</strong> Row count checking to determine rendering mode</li>
         * </ul>
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