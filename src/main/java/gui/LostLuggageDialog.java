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

    private final JTable luggageTable;
    private final LostBaggageTableModel tableModel;

    public LostLuggageDialog(Frame owner, ArrayList<DisposableObject> callingObjects, Controller controller) {

        super(owner, "Gestione Bagagli Smarriti", true);

        List<String> flightIds = new ArrayList<>();
        List<Date> bookingDates = new ArrayList<>();
        List<String> firstNames = new ArrayList<>();
        List<String> lastNames = new ArrayList<>();
        List<String> passengerSSNs = new ArrayList<>();
        List<String> luggageIds = new ArrayList<>();

        controller.getLostLuggages(flightIds, bookingDates, firstNames, lastNames, passengerSSNs, luggageIds);

        tableModel = new LostBaggageTableModel(controller, flightIds, bookingDates, firstNames, lastNames, passengerSSNs, luggageIds);

        if (luggageIds.isEmpty())
            luggageTable = new LostLuggageDialog.JTableWithEmptyMessage(tableModel, "Nessun bagaglio smarrito.");
        else
            luggageTable = new JTable(tableModel);

        luggageTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                int col = table.columnAtPoint(point);


                if (row != -1 && col == tableModel.getColumnCount() - 1) {

                    int index = table.rowAtPoint(point);   //index of the selectedBooking

                    controller.setBookingResultSelectedFlightForLostLuaggages(index);

                    //controller.getBookingController().setBookingResultSelectedBooking(index);

                    //new BookingPageCustomer(callingObjects, controller, callingObjects.getLast().getFrame().getSize(),
                    //        callingObjects.getLast().getFrame().getLocation(), callingObjects.getLast().getFrame().getExtendedState());

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

        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

    }

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
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < luggageTable.getColumnCount() - 1; i++) {
            luggageTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        int manageColumnIndex = tableModel.getColumnCount() - 1;
        luggageTable.getColumnModel().getColumn(manageColumnIndex).setCellRenderer(new ButtonRenderer());
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

    private static class JTableWithEmptyMessage extends JTable {

        private final String emptyMessage;

        public JTableWithEmptyMessage(AbstractTableModel model, String emptyMessage) {

            super(model);
            this.emptyMessage = emptyMessage;

        }

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