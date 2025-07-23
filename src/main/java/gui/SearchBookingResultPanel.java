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

public class SearchBookingResultPanel extends JPanel {

    private final JTable resultsTable;
    private final BookingTableModel tableModel;

    public SearchBookingResultPanel(List<DisposableObject> callingObjects, Controller controller, List<Date> bookingDates, List<String> bookingStatus,
                                    List<String> ids) {
                                    //la lista di id è in parallelo con quella delle prenotazioni, e in base a quelli prendo poi il volo associato dal FlightController

        super(new BorderLayout());
        this.setBackground(Color.WHITE);

        tableModel = new BookingTableModel( controller, bookingDates, bookingStatus, ids);

        boolean hasResults = (ids != null && !ids.isEmpty());

        if (!hasResults)
            resultsTable = new JTableWithEmptyMessage(tableModel, "Nessun risultato per i parametri di ricerca impostati");
        else
            resultsTable = new JTable(tableModel);

        resultsTable.addMouseListener(new MouseAdapter() {

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

    private static class BookingTableModel extends AbstractTableModel {

        private final Controller controller;
        private final ArrayList<Date> bookingDates;
        private final ArrayList<String> bookingStatus;
        private final ArrayList<String> ids;

        private final String[] colNames = {"Data prenotazione", "Stato prenotazione", "Compagnia", "Tratta", "Data volo", "Partenza", "Arrivo", "Stato del volo", "Info"};

        public BookingTableModel( Controller controller, List<Date> parBookingDates, List<String> bookingStatus, List<String> parIds) {

            this.controller = controller;
            this.bookingDates = (ArrayList<Date>) parBookingDates;
            this.bookingStatus = (ArrayList<String>) bookingStatus;
            this.ids = (ArrayList<String>) parIds;

        }

        @Override
        public int getRowCount() {
            return bookingDates.size();
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
                    switch (controller.getBookingController().getSearchBookingResult().get(row).getBookedFlight().getStatus().toString().toUpperCase()){
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