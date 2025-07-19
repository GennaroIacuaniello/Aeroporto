package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ImminentFlightsTable extends JTable {

    JScrollPane tableScrollContainer;

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
        this.getColumnModel().getColumn(this.getColumnCount()-1).setPreferredWidth(16);
        this.getColumnModel().getColumn(this.getColumnCount()-1).setMaxWidth(16);

        this.setBackground(Color.LIGHT_GRAY);
        this.getTableHeader().setBackground(Color.GRAY);

        //Making the header visible and adding a scrollbar as needed
        tableScrollContainer = new JScrollPane(this);
        tableScrollContainer.setBorder((BorderFactory.createEmptyBorder(0,0,0,0)));
        tableScrollContainer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tableScrollContainer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        tableScrollContainer.setWheelScrollingEnabled(true);
        this.setFillsViewportHeight(true);
    }

    public JScrollPane getScrollContainer(){
        return tableScrollContainer;
    }
}
