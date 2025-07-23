package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Specialized table component for displaying imminent flight information in the airport management system.
 * <p>
 * This class extends {@link JTable} to provide a customized table specifically designed for displaying
 * real-time flight information including arriving and departing flights. It implements read-only
 * behavior with optimized visual styling and integrated scroll container functionality for
 * comprehensive flight information display in customer and administrative interfaces.
 * </p>
 * <p>
 * The ImminentFlightsTable class provides specialized functionality including:
 * </p>
 * <ul>
 *   <li><strong>Read-Only Display:</strong> Complete cell editing prevention for data integrity</li>
 *   <li><strong>Integrated Scrolling:</strong> Built-in scroll container with adaptive scroll policies</li>
 *   <li><strong>Optimized Visual Design:</strong> Clean, professional styling optimized for flight information</li>
 *   <li><strong>Fixed Table Structure:</strong> Non-reorderable columns and headers for consistent information layout</li>
 *   <li><strong>Responsive Layout:</strong> Automatic column resizing for optimal content display</li>
 *   <li><strong>Accessibility Support:</strong> Proper focus management and viewport height optimization</li>
 * </ul>
 * <p>
 * The table is specifically designed to display flight information with standardized column structures
 * for both arriving and departing flights. Common column configurations include flight ID, airline
 * company, flight date, route information, departure/arrival times, flight status, and gate assignments.
 * </p>
 * <p>
 * Visual design features include:
 * </p>
 * <ul>
 *   <li><strong>Clean Appearance:</strong> White background with light gray headers for professional presentation</li>
 *   <li><strong>Grid-Free Display:</strong> Hidden grid lines for clean data presentation</li>
 *   <li><strong>Border Management:</strong> Empty borders for seamless interface integration</li>
 *   <li><strong>Header Styling:</strong> Light gray header background for clear section identification</li>
 * </ul>
 * <p>
 * Behavioral configuration ensures optimal user experience through:
 * </p>
 * <ul>
 *   <li><strong>Selection Prevention:</strong> Disabled column and row selection to prevent user confusion</li>
 *   <li><strong>Focus Management:</strong> Non-focusable design for streamlined interface navigation</li>
 *   <li><strong>Header Protection:</strong> Non-reorderable and non-resizable headers for consistent layout</li>
 *   <li><strong>Auto-Resize Mode:</strong> Automatic column width distribution for optimal content display</li>
 * </ul>
 * <p>
 * The integrated scroll container provides comprehensive scrolling functionality with:
 * </p>
 * <ul>
 *   <li><strong>Adaptive Scroll Policies:</strong> Horizontal and vertical scrollbars appear as needed</li>
 *   <li><strong>Wheel Scrolling:</strong> Mouse wheel support for intuitive navigation</li>
 *   <li><strong>Viewport Optimization:</strong> Automatic viewport height filling for optimal space utilization</li>
 *   <li><strong>Clean Integration:</strong> Borderless scroll container for seamless visual integration</li>
 * </ul>
 * <p>
 * The table uses a custom {@link DefaultTableModel} implementation that enforces read-only behavior
 * by overriding the {@code isCellEditable} method to return false for all cells. This ensures
 * data integrity while providing comprehensive flight information display capabilities.
 * </p>
 * <p>
 * Usage patterns include integration with customer home interfaces for flight information display,
 * administrative dashboards for operational oversight, and flight information panels throughout
 * the airport management system. The table handles both small and large datasets efficiently
 * through its integrated scrolling capabilities.
 * </p>
 * <p>
 * The class is designed for immediate instantiation with flight data arrays and column name arrays,
 * providing complete table functionality without requiring additional configuration. The integrated
 * scroll container can be accessed through the {@link #getScrollContainer()} method for embedding
 * in parent interface components.
 * </p>
 * <p>
 * Thread safety is provided through Swing's Event Dispatch Thread execution model, ensuring
 * proper integration with the broader airport management system's GUI components and data
 * update mechanisms.
 * </p>
 * <p>
 * The table's design philosophy emphasizes data presentation clarity, user experience optimization,
 * and seamless integration with the airport management system's visual design standards while
 * providing robust functionality for flight information display requirements.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see JTable
 * @see DefaultTableModel
 * @see JScrollPane
 * @see HomePageCustomer
 * @see FlightController
 */
public class ImminentFlightsTable extends JTable {

    /**
     * Integrated scroll container providing scrolling functionality for the flight information table.
     * <p>
     * This JScrollPane component encapsulates the table and provides comprehensive scrolling
     * capabilities including horizontal and vertical scrollbars as needed, mouse wheel support,
     * and viewport optimization. The scroll container is configured with empty borders for
     * seamless visual integration with parent interface components.
     * </p>
     * <p>
     * The scroll container features:
     * </p>
     * <ul>
     *   <li><strong>Adaptive Scroll Policies:</strong> Scrollbars appear only when content exceeds viewport dimensions</li>
     *   <li><strong>Mouse Wheel Support:</strong> Enabled wheel scrolling for intuitive user navigation</li>
     *   <li><strong>Border Management:</strong> Empty borders to prevent visual conflicts with parent layouts</li>
     *   <li><strong>Viewport Integration:</strong> Automatic viewport height filling for optimal space utilization</li>
     * </ul>
     */
    JScrollPane tableScrollContainer;

    /**
     * Constructs a new ImminentFlightsTable with specified flight data and column configuration.
     * <p>
     * This constructor initializes a complete flight information display table by configuring
     * the table model, visual appearance, behavioral properties, and integrated scroll container.
     * The constructor creates a fully functional read-only table optimized for flight information
     * display with professional styling and comprehensive scrolling capabilities.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li><strong>Table Model Configuration:</strong> Custom DefaultTableModel with read-only cell editing prevention</li>
     *   <li><strong>Behavioral Setup:</strong> Column and row selection disabling, focus management, and header protection</li>
     *   <li><strong>Visual Styling:</strong> Background colors, border management, and grid display configuration</li>
     *   <li><strong>Layout Optimization:</strong> Automatic column resizing and viewport height optimization</li>
     *   <li><strong>Scroll Container Integration:</strong> JScrollPane creation with adaptive scroll policies</li>
     * </ul>
     * <p>
     * Table model configuration creates a specialized DefaultTableModel that overrides the
     * {@code isCellEditable} method to return false for all cells, ensuring complete read-only
     * behavior while maintaining full display functionality for flight information.
     * </p>
     * <p>
     * Behavioral setup includes disabling column and row selection to prevent user confusion,
     * making the table non-focusable for streamlined interface navigation, and protecting
     * table headers from reordering and resizing to maintain consistent information layout.
     * </p>
     * <p>
     * Visual styling applies professional appearance settings including white background for
     * data cells, light gray background for headers, empty borders for clean integration,
     * and hidden grid lines for uncluttered data presentation.
     * </p>
     * <p>
     * Layout optimization configures automatic column resizing to distribute available width
     * among all columns, ensuring optimal content display across different screen sizes and
     * data content variations.
     * </p>
     * <p>
     * Scroll container integration creates a JScrollPane with the table as its viewport view,
     * configuring adaptive scroll policies that show scrollbars only when needed, enabling
     * mouse wheel scrolling, and setting empty borders for seamless visual integration.
     * </p>
     * <p>
     * The constructor completes by configuring viewport height filling to ensure the table
     * utilizes available vertical space efficiently, providing optimal user experience for
     * flight information viewing.
     * </p>
     *
     * @param data two-dimensional Object array containing flight information data organized by rows and columns
     * @param columnNames String array containing column header names for flight information display
     */
    public ImminentFlightsTable(Object[][] data, String[] columnNames){
        super(new DefaultTableModel(data, columnNames){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        });

        //Setting Table behaviour
        this.setColumnSelectionAllowed(false);
        this.setRowSelectionAllowed(false);
        this.setFocusable(false);

        this.getTableHeader().setReorderingAllowed(false);
        this.getTableHeader().setResizingAllowed(false);
        this.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        //setting Table looks
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.setShowGrid(false);

        this.setBackground(Color.WHITE);
        this.getTableHeader().setBackground(Color.LIGHT_GRAY);

        //Making the header visible and adding a scrollbar as needed
        tableScrollContainer = new JScrollPane(this);
        tableScrollContainer.setBorder((BorderFactory.createEmptyBorder(0,0,0,0)));
        tableScrollContainer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tableScrollContainer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        tableScrollContainer.setWheelScrollingEnabled(true);
        this.setFillsViewportHeight(true);
    }

    /**
     * Provides access to the integrated scroll container for parent component embedding.
     * <p>
     * This method returns the JScrollPane that contains the flight information table,
     * enabling parent interface components to embed the complete scrollable table
     * within their layout structures. The scroll container includes the table with
     * all configured visual styling and scrolling capabilities.
     * </p>
     * <p>
     * The returned scroll container features:
     * </p>
     * <ul>
     *   <li><strong>Complete Table Integration:</strong> Fully configured table with flight data display</li>
     *   <li><strong>Adaptive Scrolling:</strong> Horizontal and vertical scrollbars as needed</li>
     *   <li><strong>Mouse Wheel Support:</strong> Enabled wheel scrolling for user convenience</li>
     *   <li><strong>Clean Visual Integration:</strong> Empty borders for seamless parent component embedding</li>
     *   <li><strong>Viewport Optimization:</strong> Automatic height filling for optimal space utilization</li>
     * </ul>
     * <p>
     * Usage patterns include adding the scroll container to parent panels using layout
     * managers such as GridBagLayout, BorderLayout, or other Swing layout management
     * systems. The container handles all scrolling requirements automatically based
     * on content size and viewport dimensions.
     * </p>
     * <p>
     * The method enables integration with various interface components throughout the
     * airport management system including customer home interfaces, administrative
     * dashboards, and flight information display panels.
     * </p>
     *
     * @return the JScrollPane container that encapsulates the flight information table with complete scrolling functionality
     */
    public JScrollPane getScrollContainer(){
        return tableScrollContainer;
    }
}