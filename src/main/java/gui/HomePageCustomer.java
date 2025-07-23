package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HomePageCustomer extends DisposableObject {

    private JFrame mainFrame;
    private UserPanel userPanel;

    private JPanel arrivingPanel;
    private JPanel departingPanel;

    private ImminentFlightsTable arrivingTable;
    private ImminentFlightsTable departingTable;

    private Constraints constraints;


    public HomePageCustomer(List<DisposableObject> callingObjects, Controller controller) {

        super();

        constraints = new Constraints();

        //makes this the operating frame
        this.setMainFrame(callingObjects);


        //Setting surrounding panels
        this.addTitlePanel();
        this.addNavigatorBarPanel(callingObjects, controller);
        this.addMenuPanel(callingObjects, controller);
        this.addUserPanel(callingObjects, controller);

        this.addArrivingPanel(controller);
        this.addDepartingPanel(controller);
        mainFrame.setVisible(true);
    }

    private void setMainFrame(List<DisposableObject> callingObjects) {

        mainFrame = new JFrame("Home");
        callingObjects.addLast(this);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridBagLayout());
        mainFrame.setSize(1080, 720);
        mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        mainFrame.getContentPane().setBackground(new Color(240, 242, 245));
    }

    private void addTitlePanel() {

        TitlePanel titlePanel = new TitlePanel("AEROPORTO DI NAPOLI");
        constraints.setConstraints(0, 0, 2, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 0.0f, new Insets(5, 10, 0, 10));
        mainFrame.add(titlePanel, constraints.getGridBagConstraints());
        titlePanel.setVisible(true);
    }

    private void addNavigatorBarPanel(List<DisposableObject> callingObjects, Controller controller) {

        NavigatorBarPanel navigatorBarPanel = new NavigatorBarPanel(callingObjects, controller);
        constraints.setConstraints(0, 1, 2, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.PAGE_START);
        mainFrame.add(navigatorBarPanel, constraints.getGridBagConstraints());
        navigatorBarPanel.setBackground(new Color(240, 242, 245));
        navigatorBarPanel.setVisible(true);
    }

    private void addMenuPanel(List<DisposableObject> callingObjects, Controller controller) {

        MenuPanelCustomer hamburgerPanel = new MenuPanelCustomer(callingObjects, controller);
        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.FIRST_LINE_START);
        mainFrame.add(hamburgerPanel, constraints.getGridBagConstraints());
        hamburgerPanel.setVisible(true);
    }

    private void addUserPanel(List<DisposableObject> callingObjects, Controller controller) {

        userPanel = new UserPanel(callingObjects, controller);
        constraints.setConstraints(1, 2, 1, 1, GridBagConstraints.VERTICAL,
                0, 0, GridBagConstraints.LINE_END);
        mainFrame.add(userPanel, constraints.getGridBagConstraints());
        userPanel.setVisible(true);
    }


    private void addArrivingPanel(Controller controller) {

        arrivingPanel = new JPanel();
        arrivingPanel.setLayout(new GridBagLayout());
        arrivingPanel.setBackground(new Color(240, 242, 245));

        setArrivingTable(arrivingPanel, controller);

        constraints.setConstraints(0, 4, 2, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.PAGE_START, 1, 1, new Insets(0, 32, 0, 32));

        arrivingPanel.setBorder(BorderFactory.createEmptyBorder(16, 32, 0, 32));
        mainFrame.add(arrivingPanel, constraints.getGridBagConstraints());

        JLabel arrivingLabel = new JLabel("Aerei in arrivo");
        arrivingLabel.setFont(new Font(null, Font.BOLD, 18));
        arrivingLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        arrivingLabel.setLabelFor(arrivingTable);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.NORTHWEST, 1, 0);
        arrivingPanel.add(arrivingLabel, constraints.getGridBagConstraints());

        arrivingPanel.setVisible(true);

    }

    private void setArrivingTable(JPanel tablePanel, Controller controller) {

        String[] columnTitle = {"Id", "Compagnia", "Data", "Tratta", "Orario di arrivo", "Stato del volo", "Gate"};
        Object[][] data = controller.getFlightController().getImminentArrivingFlights();
        arrivingTable = new ImminentFlightsTable(data, columnTitle);

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.CENTER);
        tablePanel.add(arrivingTable.getScrollContainer(), constraints.getGridBagConstraints());
    }

    private void addDepartingPanel(Controller controller) {

        departingPanel = new JPanel();
        departingPanel.setLayout(new GridBagLayout());
        departingPanel.setBackground(new Color(240, 242, 245));

        setDepartingTable(departingPanel, controller);

        constraints.setConstraints(0, 5, 2, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.PAGE_START, 1, 1, new Insets(8, 32, 32, 32));

        departingPanel.setBorder(BorderFactory.createEmptyBorder(16, 32, 0, 32));
        mainFrame.add(departingPanel, constraints.getGridBagConstraints());

        JLabel departingLabel = new JLabel("Aerei in Partenza");
        departingLabel.setFont(new Font(null, Font.BOLD, 18));
        departingLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        departingLabel.setLabelFor(departingTable);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.NORTHWEST, 1, 0);
        departingPanel.add(departingLabel, constraints.getGridBagConstraints());

        departingPanel.setVisible(true);

    }

    private void setDepartingTable(JPanel tablePanel, Controller controller) {

        String[] columnTitles = {"id", "Compagnia", "Data", "Tratta", "Orario di partenza", "Stato del volo", "Gate"};
        Object[][] data = controller.getFlightController().getImminentDepartingFlights();
        departingTable = new ImminentFlightsTable(data, columnTitles);

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.CENTER);
        tablePanel.add(departingTable.getScrollContainer(), constraints.getGridBagConstraints());
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

        arrivingPanel.setVisible(false);
        departingPanel.setVisible(false);
        mainFrame.remove(arrivingPanel);
        mainFrame.remove(departingPanel);
        addArrivingPanel(controller);
        addDepartingPanel(controller);
    }

    @Override
    public JFrame getFrame() {
        return this.mainFrame;
    }

}
