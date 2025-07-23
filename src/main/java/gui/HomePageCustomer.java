package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HomePageCustomer extends DisposableObject {

    private JFrame mainFrame;
    private UserPanel userPanel;
    private ImminentFlightsTable arrivingTable;
    private ImminentFlightsTable departingTable;
    Constraints constraints;

    public HomePageCustomer(ArrayList<DisposableObject> callingObjects, Controller controller) {

        super();

        constraints = new Constraints();

        //makes this the operating frame
        this.setMainFrame(callingObjects);
        //callingFrame.dispose();

        //Setting surrounding panels
        this.addTitlePanel(controller);
        this.addNavigatorBarPanel(callingObjects, controller);
        this.addMenuPanel(callingObjects, controller);
        this.addUserPanel(callingObjects, controller);
        //this.addFooterPanel();

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

    private void addTitlePanel(Controller controller) {

        TitlePanel titlePanel = new TitlePanel("AEROPORTO DI NAPOLI", controller);
        constraints.setConstraints(0, 0, 2, 1, GridBagConstraints.BOTH,
                0, 125, GridBagConstraints.PAGE_START);
        mainFrame.add(titlePanel, constraints.getConstraints());
        titlePanel.setVisible(true);
    }

    private void addNavigatorBarPanel(ArrayList<DisposableObject> callingObjects, Controller controller) {

        NavigatorBarPanel navigatorBarPanel = new NavigatorBarPanel(callingObjects, controller);
        constraints.setConstraints(0, 1, 2, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.PAGE_START);
        mainFrame.add(navigatorBarPanel, constraints.getConstraints());
        navigatorBarPanel.setVisible(true);
    }

    private void addMenuPanel(ArrayList<DisposableObject> callingObjects, Controller controller) {

        MenuPanelCustomer hamburgerPanel = new MenuPanelCustomer(callingObjects, controller);
        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.FIRST_LINE_START);
        mainFrame.add(hamburgerPanel, constraints.getConstraints());
        hamburgerPanel.setVisible(true);
    }

    private void addUserPanel(List<DisposableObject> callingObjects, Controller controller) {

        userPanel = new UserPanel(callingObjects, controller);
        constraints.setConstraints(1, 2, 1, 1, GridBagConstraints.VERTICAL,
                0, 0, GridBagConstraints.LINE_END);
        mainFrame.add(userPanel, constraints.getConstraints());
        userPanel.setVisible(true);
    }


    private void addArrivingPanel(Controller controller) {

        JPanel arrivingPanel = new JPanel();
        arrivingPanel.setLayout(new GridBagLayout());
        arrivingPanel.setBackground(Color.LIGHT_GRAY);

        setArrivingTable(arrivingPanel, controller);

        constraints.setConstraints(0, 4, 2, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.PAGE_START, 1, 1);

        arrivingPanel.setBorder(BorderFactory.createEmptyBorder(16, 32, 0, 32));
        mainFrame.add(arrivingPanel, constraints.getConstraints());

        JLabel arrivingLabel = new JLabel("Aerei in arrivo");
        arrivingLabel.setFont(new Font(null, Font.BOLD, 18));
        arrivingLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        arrivingLabel.setLabelFor(arrivingTable);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.NORTHWEST, 1, 0);
        arrivingPanel.add(arrivingLabel, constraints.getConstraints());

        arrivingPanel.setVisible(true);

    }

    private void setArrivingTable(JPanel tablePanel, Controller controller) {

        String[] columnTitle = {"Id", "Compagnia", "Data", "Tratta", "Orario di arrivo", "Stato del volo", "Gate"};
        Object[][] data = controller.getImminentArrivingFlights();
        arrivingTable = new ImminentFlightsTable(data, columnTitle);

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.CENTER);
        tablePanel.add(arrivingTable.getScrollContainer(), constraints.getConstraints());
    }

    private void addDepartingPanel(Controller controller) {

        JPanel departingPanel = new JPanel();
        departingPanel.setLayout(new GridBagLayout());
        departingPanel.setBackground(Color.LIGHT_GRAY);

        setDepartingTable(departingPanel, controller);

        constraints.setConstraints(0, 5, 2, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.PAGE_START, 1, 1);

        departingPanel.setBorder(BorderFactory.createEmptyBorder(16, 32, 0, 32));
        mainFrame.add(departingPanel, constraints.getConstraints());

        JLabel departingLabel = new JLabel("Aerei in Partenza");
        departingLabel.setFont(new Font(null, Font.BOLD, 18));
        departingLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
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


        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.CENTER);
        tablePanel.add(departingTable.getScrollContainer(), constraints.getConstraints());
    }

    @Override
    public void doOnDispose(List<DisposableObject> callingObjects, Controller controller) {
        controller.getCustomerController().setLoggedCustomer(null, null);
        controller.getUserController().setLoggedUser(null, null);
    }

    @Override
    public void doOnRestore(java.util.List<DisposableObject> callingObjects, Controller controller){
        if(!userPanel.getUserGreeted().equals(controller.getUserController().getUsername())){
            userPanel.setVisible(false);
            mainFrame.remove(userPanel);
            addUserPanel(callingObjects, controller);
        }
    }

    @Override
    public JFrame getFrame() {
        return this.mainFrame;
    }

}
