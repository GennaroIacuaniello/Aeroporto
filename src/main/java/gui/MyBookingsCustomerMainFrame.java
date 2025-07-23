package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MyBookingsCustomerMainFrame extends DisposableObject {

    private JFrame mainFrame;

    private SearchBookingPanel searchBookingPanel;
    Constraints constraints;

    public MyBookingsCustomerMainFrame(List<DisposableObject> callingObjects, Controller controller, Dimension dimension, Point point, int fullScreen, boolean ifOpenedFromMenu) {

        super();
        constraints = new Constraints();
        this.setMainFrame((ArrayList<DisposableObject>)callingObjects, dimension, point, fullScreen);

        this.addTitlePanel(controller);
        this.addNavigatorBarPanel((ArrayList<DisposableObject>)callingObjects, controller);
        this.addMenuPanel((ArrayList<DisposableObject>)callingObjects, controller);
        this.addUserPanel((ArrayList<DisposableObject>)callingObjects, controller);
        this.addSearchPanel((ArrayList<DisposableObject>)callingObjects, controller, ifOpenedFromMenu);

        mainFrame.setVisible(true);
    }

    private void setMainFrame(ArrayList<DisposableObject> callingObjects, Dimension dimension, Point point, int fullScreen) {

        mainFrame = new JFrame("Le mie prenotazioni");
        mainFrame.setSize(dimension);
        mainFrame.setLocation(point);
        mainFrame.setExtendedState(fullScreen);
        callingObjects.addLast(this);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridBagLayout());

        mainFrame.setMinimumSize(new Dimension(1420, 1080));

        mainFrame.getContentPane().setBackground(new Color(240, 242, 245));

    }

    private void addTitlePanel(Controller controller) {
        TitlePanel titlePanel;

        titlePanel = new TitlePanel("AEROPORTO DI NAPOLI");

        titlePanel.setOpaque(false);

        constraints.setConstraints(0, 0, 2, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 0.0f, new Insets(5, 10, 0, 10));

        mainFrame.add(titlePanel, constraints.getGridBagConstraints());
        titlePanel.setVisible(true);
    }

    private void addNavigatorBarPanel(ArrayList<DisposableObject> callingObjects, Controller controller) {

        NavigatorBarPanel navigatorBarPanel = new NavigatorBarPanel(callingObjects, controller);

        navigatorBarPanel.setOpaque(false);

        constraints.setConstraints(0, 1, 2, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 0.0f, new Insets(0, 10, 10, 10));

        mainFrame.add(navigatorBarPanel, constraints.getGridBagConstraints());
        navigatorBarPanel.setVisible(true);
    }

    private void addMenuPanel(ArrayList<DisposableObject> callingObjects, Controller controller) {

        MenuPanelCustomer menu = new MenuPanelCustomer(callingObjects, controller);

        menu.setOpaque(false);

        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.FIRST_LINE_START, 0.0f, 0.0f, new Insets(0, 10, 0, 0));

        mainFrame.add(menu, constraints.getGridBagConstraints());
        menu.setVisible(true);
    }

    private void addUserPanel(ArrayList<DisposableObject> callingObjects, Controller controller) {

        UserPanel userPanel = new UserPanel(callingObjects, controller);

        userPanel.setOpaque(false);

        constraints.setConstraints(1, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.FIRST_LINE_END, 0.0f, 0.0f, new Insets(0, 0, 0, 10));

        mainFrame.add(userPanel, constraints.getGridBagConstraints());
        userPanel.setVisible(true);
    }

    private void addSearchPanel(ArrayList<DisposableObject> callingObjects, Controller controller, boolean ifOpenedFromMenu) {

        searchBookingPanel = new SearchBookingPanel(callingObjects, controller, ifOpenedFromMenu);

        constraints.setConstraints(0, 3, 2, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.CENTER, 1.0f, 1.0f, new Insets(20, 40, 40, 40));

        mainFrame.add(searchBookingPanel, constraints.getGridBagConstraints());
        searchBookingPanel.setVisible(true);
    }

    @Override
    public void doOnDispose (List<DisposableObject> callingObjects, Controller controller) {
        controller.clearSearchBookingResultCache();
    }

    @Override
    public void doOnRestore (List<DisposableObject> callingObjects, Controller controller) {

        if(searchBookingPanel.isSearchPerformed()){
            if ( searchBookingPanel.getActiveFilter().equals("FLIGHT")) {

                searchBookingPanel.filteredFlightSearch(callingObjects, controller, searchBookingPanel.getSearchButton());

            } else if (searchBookingPanel.getActiveFilter().equals("PASSENGER")) {

                searchBookingPanel.filteredPassengerSearch(callingObjects, controller, searchBookingPanel.getSearchButton());

            }
            searchBookingPanel.repaint();
            searchBookingPanel.revalidate();
        }

    }

    @Override
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
        panel1.setLayout(new GridBagLayout());
    }
}

