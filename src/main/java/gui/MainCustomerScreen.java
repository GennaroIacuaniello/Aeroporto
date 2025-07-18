package gui;

import controller.Controller;
import gui.ImminentFlightsTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MainCustomerScreen extends DisposableObject {

    private JFrame mainFrame;
    private TitlePanel titlePanel;
    private NavigatorBarPanel navigatorBarPanel;
    private MenuPanelCustomer hamburgerPanel;
    private UserPanel userPanel;
    private FooterPanel footerPanel;
    private gui.ImminentFlightsTable arrivingTable;
    private ImminentFlightsTable departingTable;
    private JPanel arrivingPanel;
    private JPanel departingPanel;
    Constraints constraints;

    public MainCustomerScreen(ArrayList<DisposableObject> callingObjects, Controller controller) {

        super();

        constraints = new Constraints();

        //makes this the operating frame
        this.setMainFrame(callingObjects);
        //callingFrame.dispose();

        //Setting surrounding panels
        this.addTitlePanel("AEROPORTO DI NAPOLI", controller);
        this.addNavigatorBarPanel(callingObjects, controller);
        this.addHamburgerPanel(callingObjects, controller);
        this.addUserPanel(callingObjects, controller);
        this.addFooterPanel();

        this.addArrivingPanel(controller);
        this.addDepartingPanel(controller);
        mainFrame.setVisible(true);
    }

    private void setMainFrame(ArrayList<DisposableObject> callingObjects) {

        mainFrame = new JFrame("Home");
        callingObjects.addLast(this);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridBagLayout());
        mainFrame.setSize(1080, 720);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setBackground(Color.BLACK);
    }

    private void addTitlePanel(String title, Controller controller) {

        titlePanel = new TitlePanel(title, controller);
        constraints.setConstraints(0, 0, 2, 1, GridBagConstraints.BOTH,
                0, 125, GridBagConstraints.PAGE_START);
        mainFrame.add(titlePanel, constraints.getConstraints());
        titlePanel.setVisible(true);
    }

    private void addNavigatorBarPanel(ArrayList<DisposableObject> callingObjects, Controller controller) {

        navigatorBarPanel = new NavigatorBarPanel(callingObjects, controller);
        constraints.setConstraints(0, 1, 2, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.PAGE_START);
        mainFrame.add(navigatorBarPanel, constraints.getConstraints());
        navigatorBarPanel.setVisible(true);
    }

    private void addHamburgerPanel(ArrayList<DisposableObject> callingObjects, Controller controller) {

        hamburgerPanel = new MenuPanelCustomer(callingObjects, controller);
        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.FIRST_LINE_START);
        mainFrame.add(hamburgerPanel, constraints.getConstraints());
        hamburgerPanel.setVisible(true);
    }

    private void addUserPanel(ArrayList<DisposableObject> callingObjects, Controller controller) {

        userPanel = new UserPanel(callingObjects, controller);
        constraints.setConstraints(1, 2, 1, 1, GridBagConstraints.VERTICAL,
                0, 0, GridBagConstraints.LINE_END);
        mainFrame.add(userPanel, constraints.getConstraints());
        userPanel.setVisible(true);
    }

    private void addFooterPanel() {

        footerPanel = new FooterPanel();
        constraints.setConstraints(0, 5, 2, 1, GridBagConstraints.BOTH,
                0, 75, GridBagConstraints.PAGE_END);
        mainFrame.add(footerPanel, constraints.getConstraints());
        footerPanel.setVisible(true);
    }

    private void addArrivingPanel(Controller controller) {

        arrivingPanel = new JPanel();
        arrivingPanel.setLayout(new GridBagLayout());
        arrivingPanel.setBackground(Color.LIGHT_GRAY);

        setArrivingTable(arrivingPanel, controller);

        constraints.setConstraints(0, 4, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.PAGE_START, 1, 1);

        arrivingPanel.setBorder(BorderFactory.createEmptyBorder(0, 64, 0, 32));
        mainFrame.add(arrivingPanel, constraints.getConstraints());

        JLabel arrivingLabel = new JLabel("Aerei in arrivo");
        arrivingLabel.setFont(new Font(null, Font.BOLD, 18));
        arrivingLabel.setLabelFor(arrivingTable);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.NORTHWEST, 1, 0);
        arrivingPanel.add(arrivingLabel, constraints.getConstraints());

        arrivingPanel.setVisible(true);

    }

    private void setArrivingTable(JPanel tablePanel, Controller controller) {

        String[] columnTitle = {"Id", "Company", "From", "Day", "Arrival Time", ""};
        Object[][] data = controller.getImminentArrivingFlights();
        arrivingTable = new ImminentFlightsTable(data, columnTitle);

        arrivingTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                showFlightInfoEvent(mouseEvent);
            }
        });

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.CENTER);
        tablePanel.add(arrivingTable.getScrollContainer(), constraints.getConstraints());
    }

    private void addDepartingPanel(Controller controller) {

        departingPanel = new JPanel();
        departingPanel.setLayout(new GridBagLayout());
        departingPanel.setBackground(Color.LIGHT_GRAY);

        setDepartingTable(departingPanel, controller);

        constraints.setConstraints(1, 4, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.PAGE_START, 1, 1);

        departingPanel.setBorder(BorderFactory.createEmptyBorder(0, 32, 0, 64));
        mainFrame.add(departingPanel, constraints.getConstraints());

        JLabel departingLabel = new JLabel("Aerei in Partenza");
        departingLabel.setFont(new Font(null, Font.BOLD, 18));
        departingLabel.setLabelFor(departingTable);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.NORTHWEST, 1, 0);
        departingPanel.add(departingLabel, constraints.getConstraints());

        departingPanel.setVisible(true);

    }

    private void setDepartingTable(JPanel tablePanel, Controller controller) {

        String[] columnTitles = {"Company", "Going to", "Day", "Departing Time", "Gate"};
        Object[][] data = controller.getImminentDepartingFlights();
        departingTable = new ImminentFlightsTable(data, columnTitles);

        departingTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                showFlightInfoEvent(mouseEvent);

            }
        });

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.CENTER);
        tablePanel.add(departingTable.getScrollContainer(), constraints.getConstraints());
    }

    private void showFlightInfoEvent(MouseEvent mouseEvent) {
        JTable table = (JTable) mouseEvent.getSource();
        Point point = mouseEvent.getPoint();
        int row = table.rowAtPoint(point);
        int col = table.columnAtPoint(point);
        if(table.getSelectedRow() != -1 && row != -1){
            if (mouseEvent.getClickCount() == 2 || col == table.getColumnCount()-1) {//double click on a row or click on last column
                // the row number is the visual row number
                // when filtering or sorting it is not the model's row number
                // this line takes care of that
                // int modelRow = table.convertRowIndexToModel(row);
                JOptionPane.showMessageDialog(mainFrame, "Show info of the flight" + table.getColumnCount());
            }
        }
    }

    @Override
    public void doOnDispose(ArrayList<DisposableObject> callingObjects, Controller controller) {
        controller.getCustomerController().setCustomer(null);
        controller.getUserController().setUser(null);
    }

    public JFrame getFrame() {
        return this.mainFrame;
    }

}
