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
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Search flight result panel.
 */
public class SearchFlightResultPanel extends JPanel {

    private final JTable resultsTable;
    private final FlightTableModel tableModel;

    /**
     * Instantiates a new Search flight result panel.
     *
     * @param callingObjects the calling objects
     * @param controller     the controller
     * @param ids            the ids
     * @param companyNames   the company names
     * @param dates          the dates
     * @param departureTimes the departure times
     * @param arrivalTimes   the arrival times
     * @param delays         the delays
     * @param status         the status
     * @param maxSeats       the max seats
     * @param freeSeats      the free seats
     * @param cities         the cities
     * @param ifSearched     the if searched
     */
    public SearchFlightResultPanel(List<DisposableObject> callingObjects, Controller controller, List<String> ids, List<String> companyNames, List<Date> dates, List<Time> departureTimes, List<Time> arrivalTimes,
                                   List<Integer> delays, List<String> status, List<Integer> maxSeats, List<Integer> freeSeats, List<String> cities, boolean ifSearched) {

        super(new BorderLayout());
        this.setBackground(Color.WHITE);

        //se searchedFlights fosse null, darebbe nullPointerException, quindi gli passo una lista vuota
        tableModel = new FlightTableModel( controller, ids, companyNames, dates, departureTimes, arrivalTimes,
                                          delays, status, maxSeats, freeSeats, cities);

        boolean hasResults = (ids != null && !ids.isEmpty());

        if (ifSearched && !hasResults)
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


                    int index = table.rowAtPoint(point);   //index of the selectedFlight


                    if(freeSeats.get(index) > 0 && status.get(index).equalsIgnoreCase("PROGRAMMED")){

                        controller.getFlightController().setFlight(index);

                        if(controller.loadAndCheckIfOpenMyBookingsOrNewBooking()){
                            new MyBookingsCustomerMainFrame(callingObjects, controller, callingObjects.getLast().getFrame().getSize(),
                                    callingObjects.getLast().getFrame().getLocation(), callingObjects.getLast().getFrame().getExtendedState(), false);
                        }else{
                            new Book(callingObjects, controller, callingObjects.getLast().getFrame().getSize(),
                                    callingObjects.getLast().getFrame().getLocation(), callingObjects.getLast().getFrame().getExtendedState());
                        }

                        callingObjects.get(callingObjects.size() - 2).getFrame().setVisible(false);

                    }

                }
            }
        });

        setTableApperance();

        JTableHeader header = resultsTable.getTableHeader();

        //Per mostrare l'intestazione solo dopo avere effettivamente premuto "Cerca" almeno una volta
        header.setVisible(ifSearched);

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

    private static class FlightTableModel extends AbstractTableModel {

        private final Controller controller;
        private final ArrayList<String> ids;
        private final ArrayList<String> companyNames;
        private final ArrayList<Date> dates;
        private final ArrayList<Time> departureTimes;
        private final ArrayList<Time> arrivalTimes;
        private final ArrayList<Integer> delays;
        private final ArrayList<String> status;
        private final ArrayList<Integer> maxSeats;
        private final ArrayList<Integer> freeSeats;
        private final ArrayList<String> cities;

        private final String[] colNames = {"Compagnia", "Tratta", "Data", "Partenza", "Ritardo", "Arrivo", "Stato", "Posti", "Prenotazione"};

        /**
         * Instantiates a new Flight table model.
         *
         * @param controller        the controller
         * @param parIds            the par ids
         * @param parCompanyNames   the par company names
         * @param parDates          the par dates
         * @param parDepartureTimes the par departure times
         * @param parArrivalTimes   the par arrival times
         * @param parDelays         the par delays
         * @param parStatus         the par status
         * @param parMaxSeats       the par max seats
         * @param parFreeSeats      the par free seats
         * @param parCities         the par cities
         */
        public FlightTableModel( Controller controller, List<String> parIds, List<String> parCompanyNames, List<Date> parDates, List<Time> parDepartureTimes, List<Time> parArrivalTimes,
                                List<Integer> parDelays, List<String> parStatus, List<Integer> parMaxSeats, List<Integer> parFreeSeats, List<String> parCities) {

            this.controller = controller;
            this.ids = (ArrayList<String>) parIds;
            this.companyNames= (ArrayList<String>) parCompanyNames;
            this.dates = (ArrayList<Date>) parDates;
            this.departureTimes = (ArrayList<Time>) parDepartureTimes;
            this.arrivalTimes = (ArrayList<Time>) parArrivalTimes;
            this.delays = (ArrayList<Integer>) parDelays;
            this.status = (ArrayList<String>) parStatus;
            this.maxSeats = (ArrayList<Integer>) parMaxSeats;
            this.freeSeats = (ArrayList<Integer>) parFreeSeats;
            this.cities = (ArrayList<String>) parCities;

        }

        /**
         * Get free seats list.
         *
         * @return the list
         */
        public List<Integer> getFreeSeats(){
            return freeSeats;
        }

        /**
         * Get status list.
         *
         * @return the list
         */
        public List<String> getStatus(){
            return status;
        }

        @Override
        public int getRowCount() {
            return ids.size();
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
                    return companyNames.get(row);
                case 1:
                    if (controller.getFlightController().getFlightType(row))
                        return "Napoli → " + cities.get(row);
                    else
                        return cities.get(row) + " → Napoli";

                case 2:
                    return dates.get(row).toString();
                case 3:
                    hours = departureTimes.get(row).getHours();
                    minutes = departureTimes.get(row).getMinutes();

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

                case 4:
                    int delay = delays.get(row);
                    return delay > 0 ? delay + " min" : "--";
                case 5:
                    hours = arrivalTimes.get(row).getHours();
                    minutes = arrivalTimes.get(row).getMinutes();

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
                    switch (status.get(row).toUpperCase()){
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
                case 7:
                    return freeSeats.get(row) + "/" + maxSeats.get(row);
                case 8:
                    return "Prenota";
                default:
                    return null;
            }
        }
    }


    private static class ButtonRenderer extends JButton implements TableCellRenderer {

        /**
         * Instantiates a new Button renderer.
         */
        public ButtonRenderer() {
            setOpaque(true);
            setFont(new Font("Segoe UI", Font.BOLD, 12));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {


            setText((value == null) ? "" : value.toString());

            FlightTableModel model = (FlightTableModel) table.getModel();

            setEnabled(model.getFreeSeats().get(row) > 0 && (model.getStatus().get(row)).equalsIgnoreCase("PROGRAMMED"));

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

        /**
         * Instantiates a new J table with empty message.
         *
         * @param model        the model
         * @param emptyMessage the empty message
         */
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