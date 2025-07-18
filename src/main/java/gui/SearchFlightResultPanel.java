package gui;

import controller.Controller;
import model.Arriving;
import model.Departing;
import model.Flight;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class SearchFlightResultPanel extends JPanel {

    private JTable resultsTable;
    private FlightTableModel tableModel;

    public SearchFlightResultPanel(ArrayList<DisposableObject> callingObjects, Controller controller, ArrayList<Flight> searchedFlights, boolean ifSearched) {

        super(new BorderLayout());
        this.setBackground(Color.WHITE);

        //se searchedFlights fosse null, darebbe nullPointerException, quindi gli passo una lista vuota
        tableModel = new FlightTableModel(searchedFlights != null ? searchedFlights : new ArrayList<>());

        boolean hasResults = (searchedFlights != null && !searchedFlights.isEmpty());

        if (ifSearched && !hasResults)
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

                    Flight selectedFlight = searchedFlights.get(table.rowAtPoint(point));

                    if (selectedFlight.getFreeSeats() > 0 && (selectedFlight .getStatus().toString()).equals("PROGRAMMED")){

                        controller.getFlightController().setFlight(selectedFlight);

                        new Book(callingObjects, controller, callingObjects.getLast().getFrame().getSize(),
                                callingObjects.getLast().getFrame().getLocation(), callingObjects.getLast().getFrame().getExtendedState());
                        callingObjects.get(callingObjects.size() - 2).getFrame().setVisible(false);

                    }

                }
            }
        });

        setTableApperance(callingObjects, controller);

        JTableHeader header = resultsTable.getTableHeader();

        //Per mostrare l'intestazione solo dopo avere effettivamente premuto "Cerca" almeno una volta
        header.setVisible(ifSearched);

        this.add(header, BorderLayout.NORTH);
        this.add(resultsTable, BorderLayout.CENTER);

    }

    private void setTableApperance(ArrayList<DisposableObject> callingObjects, Controller controller) {

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
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < resultsTable.getColumnCount() - 1; i++) {
            resultsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        int bookColumnIndex = tableModel.getColumnCount() - 1;
        resultsTable.getColumnModel().getColumn(bookColumnIndex).setCellRenderer(new ButtonRenderer());

    }

    private static class FlightTableModel extends AbstractTableModel {

        private final List<Flight> flights;
        private final String[] colNames = {"Compagnia", "Tratta", "Data", "Partenza", "Ritardo", "Arrivo", "Stato", "Posti", "Prenota"};

        public FlightTableModel(List<Flight> flights) {
            this.flights = flights;
        }

        public List<Flight> getFlights() {
            return flights;
        }

        @Override
        public int getRowCount() {
            return flights.size();
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

            Flight flight = flights.get(row);

            int hours;
            int minutes;

            switch (col) {
                case 0:
                    return flight.getCompanyName();
                case 1:
                    if (flight instanceof Arriving)
                        return ((Arriving) flight).getOrigin() + " → Napoli";
                    else
                        return "Napoli → " + ((Departing) flight).getDestination();
                case 2:
                    return flight.getDate().toString();
                case 3:
                    hours = flight.getDepartureTime().getHours();
                    minutes = flight.getDepartureTime().getMinutes();

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
                    int delay = (flight instanceof Arriving) ? ((Arriving) flight).getArrivalDelay() : ((Departing) flight).getDepartureDelay();
                    return delay > 0 ? delay + " min" : "--";
                case 5:
                    hours = flight.getArrivalTime().getHours();
                    minutes = flight.getArrivalTime().getMinutes();

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
                    return flight.getStatus().toString().toUpperCase();
                case 7:
                    return flight.getFreeSeats() + "/" + flight.getMaxSeats();
                case 8:
                    return "Prenota";
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

            FlightTableModel model = (FlightTableModel) table.getModel();
            Flight flight = model.getFlights().get(row);

            setEnabled(flight.getFreeSeats() > 0 && (flight.getStatus().toString()).equals("PROGRAMMED"));

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