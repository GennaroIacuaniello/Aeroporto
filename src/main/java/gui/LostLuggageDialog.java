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

public class LostLuggageDialog extends JDialog {

    private JTable baggageTable;
    private LostBaggageTableModel tableModel;

    public LostLuggageDialog(Frame owner, Controller controller) {

        super(owner, "Gestione Bagagli Smarriti", true);


        List<String> flightIds = new ArrayList<>();
        List<Date> bookingDates = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<String> surnames = new ArrayList<>();
        List<String> fiscalCodes = new ArrayList<>();
        List<String> baggageIds = new ArrayList<>();

        flightIds.add("AZ-123");
        bookingDates.add(Date.valueOf("2024-05-20"));
        names.add("Mario");
        surnames.add("Rossi");
        fiscalCodes.add("RSSMRA80A01H501U");
        baggageIds.add("BG-98765");

        flightIds.add("FR-456");
        bookingDates.add(Date.valueOf("2024-05-21"));
        names.add("Luigi");
        surnames.add("Verdi");
        fiscalCodes.add("VRDLGU85B22F839T");
        baggageIds.add("BG-12345");

        flightIds.add("EK-789");
        bookingDates.add(Date.valueOf("2024-05-22"));
        names.add("Anna");
        surnames.add("Bianchi");
        fiscalCodes.add("BNCANNA90C63A662Y");
        baggageIds.add("BG-67890");


        tableModel = new LostBaggageTableModel(controller, flightIds, bookingDates, names, surnames, fiscalCodes, baggageIds);
        baggageTable = new JTable(tableModel);

        baggageTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                int col = table.columnAtPoint(point);


                if (row != -1 && col == tableModel.getColumnCount() - 1) {

                    // TODO: aprire pagina di gestione del bagaglio smarrito cliccato
                }
            }
        });

        setTableAppearance();

        JScrollPane scrollPane = new JScrollPane(baggageTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JTableHeader header = baggageTable.getTableHeader();
        this.add(header, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);

        this.setSize(1200, 400);
        this.setMinimumSize(new Dimension(1000, 120));

        this.setLocationRelativeTo(owner);

        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

    }

    private void setTableAppearance() {

        baggageTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        baggageTable.getTableHeader().setBackground(new Color(230, 230, 230));

        baggageTable.getTableHeader().setReorderingAllowed(false);

        baggageTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        baggageTable.setRowHeight(35);
        baggageTable.setRowSelectionAllowed(false);

        baggageTable.setFillsViewportHeight(true);

        baggageTable.setGridColor(new Color(220, 220, 220));

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
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < baggageTable.getColumnCount() - 1; i++) {
            baggageTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        int manageColumnIndex = tableModel.getColumnCount() - 1;
        baggageTable.getColumnModel().getColumn(manageColumnIndex).setCellRenderer(new ButtonRenderer());
    }

    private static class LostBaggageTableModel extends AbstractTableModel {

        private final Controller controller;
        private final List<String> flightIds;
        private final List<Date> bookingDates;
        private final List<String> names;
        private final List<String> surnames;
        private final List<String> fiscalCodes;
        private final List<String> baggageIds;

        private final String[] colNames = {"ID Volo", "Data Prenotazione", "Nome", "Cognome", "Codice Fiscale", "ID Bagaglio", "Azione"};

        public LostBaggageTableModel(Controller controller, List<String> flightIds, List<Date> bookingDates, List<String> names, List<String> surnames, List<String> fiscalCodes, List<String> baggageIds) {

            this.controller = controller;
            this.flightIds = flightIds;
            this.bookingDates = bookingDates;
            this.names = names;
            this.surnames = surnames;
            this.fiscalCodes = fiscalCodes;
            this.baggageIds = baggageIds;

        }

        @Override
        public int getRowCount() {
            return baggageIds.size();
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

        public String getBaggageIdAt(int row) {
            return baggageIds.get(row);
        }
    }

    private static class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
            setFont(new Font("Segoe UI", Font.BOLD, 12));
        }

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
}