package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Specialized table component for displaying imminent flight information in the airport management system.
 * <p>
 * This class extends {@link JTable} to provide a customized table specifically designed for displaying
 * real-time flight information including arriving and departing flights. It implements read-only
 * behavior with integrated scroll container functionality for
 * flight information display in customer and administrative interfaces.
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
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see JTable
 * @see DefaultTableModel
 * @see JScrollPane
 * @see HomePageCustomer
 * @see controller.FlightController
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
        this.setShowVerticalLines(false);

        this.setBackground(Color.WHITE);
        this.getTableHeader().setBackground(Color.LIGHT_GRAY);

        this.getColumnModel().getColumn(this.getColumnCount() - 1).setMaxWidth(50);

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
     *
     * @return the JScrollPane container that encapsulates the flight information table with complete scrolling functionality
     */
    public JScrollPane getScrollContainer(){
        return tableScrollContainer;
    }
}