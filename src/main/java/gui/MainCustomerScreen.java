package gui;

import model.Arriving;
import controller.Controller;
import model.Departing;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class MainCustomerScreen extends DisposableObject {

    private JFrame mainFrame;
    private TitlePanel titlePanel;
    private NavigatorBarPanel navigatorBarPanel;
    private MenuPanelCustomer hamburgerPanel;
    private UserPanel userPanel;
    private FooterPanel footerPanel;
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
        if (controller.developerMode) arrivingPanel.setBackground(Color.ORANGE);

        setArrivingTitleLabels(arrivingPanel);
        setArrivingFlightLabels(controller, arrivingPanel);
        constraints.setConstraints(0, 4, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.PAGE_START);

        Border padding = BorderFactory.createEmptyBorder(0, 128, 0, 64);
        arrivingPanel.setBorder(padding);
        mainFrame.add(arrivingPanel, constraints.getConstraints());
        arrivingPanel.setVisible(true);

    }

    private void setArrivingTitleLabels(JPanel tablePanel) {

        ArrayList<JLabel> titleLabels = new ArrayList<JLabel>();

        titleLabels.add(new JLabel("Company"));
        titleLabels.add(new JLabel("From"));
        titleLabels.add(new JLabel("Day"));
        titleLabels.add(new JLabel("Arrival Time"));

        for (int i = 0; i < titleLabels.size(); i++) {
            constraints.setConstraints(i, 0, 1, 1, GridBagConstraints.NONE,
                    0, 0, GridBagConstraints.CENTER);
            tablePanel.add(titleLabels.get(i), constraints.getConstraints());
        }
    }

    private void setArrivingFlightLabels(Controller controller, JPanel tablePanel) {

        ArrayList<Arriving> imminentArrivingFlights = controller.getImminentArrivingFlights();

        for (int i = 0; i < imminentArrivingFlights.size(); i++) {

            JLabel companyLabel = new JLabel(imminentArrivingFlights.get(i).get_company_name());
            JLabel originLabel = new JLabel(imminentArrivingFlights.get(i).get_origin());
            JLabel dateLabel = new JLabel(Integer.valueOf(imminentArrivingFlights.get(i).get_date().getDate()).toString() +
                    " " + imminentArrivingFlights.get(i).getMonthName());
            JLabel timeLabel = new JLabel(imminentArrivingFlights.get(i).get_arrival_time());

            constraints.setConstraints(0, i + 1, 1, 1, GridBagConstraints.NONE,
                    0, 0, GridBagConstraints.CENTER);
            tablePanel.add(companyLabel, constraints.getConstraints());

            constraints.setConstraints(1, i + 1, 1, 1, GridBagConstraints.NONE,
                    0, 0, GridBagConstraints.CENTER);
            tablePanel.add(originLabel, constraints.getConstraints());

            constraints.setConstraints(2, i + 1, 1, 1, GridBagConstraints.NONE,
                    0, 0, GridBagConstraints.CENTER);
            tablePanel.add(dateLabel, constraints.getConstraints());

            constraints.setConstraints(3, i + 1, 1, 1, GridBagConstraints.NONE,
                    0, 0, GridBagConstraints.CENTER);
            tablePanel.add(timeLabel, constraints.getConstraints());

        }

    }

    private void addDepartingPanel(Controller controller) {

        departingPanel = new JPanel();
        departingPanel.setLayout(new GridBagLayout());
        departingPanel.setBackground(Color.LIGHT_GRAY);
        if (controller.developerMode) departingPanel.setBackground(Color.ORANGE);

        setDepartingTitleLabels(departingPanel);
        setDepartingFlightLabels(controller, departingPanel);
        constraints.setConstraints(1, 4, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.PAGE_START);

        Border padding = BorderFactory.createEmptyBorder(0, 64, 0, 128);
        departingPanel.setBorder(padding);
        mainFrame.add(departingPanel, constraints.getConstraints());
        departingPanel.setVisible(true);

    }

    private void setDepartingTitleLabels(JPanel tablePanel) {

        ArrayList<JLabel> titleLabels = new ArrayList<JLabel>();

        titleLabels.add(new JLabel("Company"));
        titleLabels.add(new JLabel("Going to"));
        titleLabels.add(new JLabel("Day"));
        titleLabels.add(new JLabel("Departing Time"));
        titleLabels.add(new JLabel("Gate"));

        for (int i = 0; i < titleLabels.size(); i++) {
            constraints.setConstraints(i, 0, 1, 1, GridBagConstraints.NONE,
                    0, 0, GridBagConstraints.CENTER);
            tablePanel.add(titleLabels.get(i), constraints.getConstraints());
        }
    }

    private void setDepartingFlightLabels(Controller controller, JPanel tablePanel) {

        ArrayList<Departing> imminentDepartingFlights = controller.getImminentDepartingFlights();

        for (int i = 0; i < imminentDepartingFlights.size(); i++) {

            JLabel companyLabel = new JLabel(imminentDepartingFlights.get(i).get_company_name());
            JLabel originLabel = new JLabel(imminentDepartingFlights.get(i).get_destination());
            JLabel dateLabel = new JLabel(Integer.valueOf(imminentDepartingFlights.get(i).get_date().getDate()).toString() +
                    " " + imminentDepartingFlights.get(i).getMonthName());
            JLabel timeLabel = new JLabel(imminentDepartingFlights.get(i).get_arrival_time());
            JLabel gateLabel = new JLabel(Integer.valueOf(imminentDepartingFlights.get(i).get_gate().get_Id()).toString());

            constraints.setConstraints(0, i + 1, 1, 1, GridBagConstraints.NONE,
                    0, 0, GridBagConstraints.CENTER);
            tablePanel.add(companyLabel, constraints.getConstraints());

            constraints.setConstraints(1, i + 1, 1, 1, GridBagConstraints.NONE,
                    0, 0, GridBagConstraints.CENTER);
            tablePanel.add(originLabel, constraints.getConstraints());

            constraints.setConstraints(2, i + 1, 1, 1, GridBagConstraints.NONE,
                    0, 0, GridBagConstraints.CENTER);
            tablePanel.add(dateLabel, constraints.getConstraints());

            constraints.setConstraints(3, i + 1, 1, 1, GridBagConstraints.NONE,
                    0, 0, GridBagConstraints.CENTER);
            tablePanel.add(timeLabel, constraints.getConstraints());

            constraints.setConstraints(4, i + 1, 1, 1, GridBagConstraints.NONE,
                    0, 0, GridBagConstraints.CENTER);
            tablePanel.add(gateLabel, constraints.getConstraints());

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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    }
}
