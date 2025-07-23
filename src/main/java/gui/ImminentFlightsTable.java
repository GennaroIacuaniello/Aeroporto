package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * The type Imminent flights table.
 */
public class ImminentFlightsTable extends JTable {

    /**
     * The Table scroll container.
     */
    JScrollPane tableScrollContainer;

    /**
     * Instantiates a new Imminent flights table.
     *
     * @param data        the data
     * @param columnNames the column names
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
     * Get scroll container j scroll pane.
     *
     * @return the j scroll pane
     */
    public JScrollPane getScrollContainer(){
        return tableScrollContainer;
    }
}
