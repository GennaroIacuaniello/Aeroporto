package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SearchFlightCustomerMainFrame extends DisposableObject {

    private JFrame mainFrame;
    private TitlePanel titlePanel;
    private NavigatorBarPanel navigatorBarPanel;
    //private HamburgerPanel hamburgerPanel;
    private MenuPanelCustomer menu;
    private UserPanel userPanel;
    private FooterPanel footerPanel;
    private SearchPanel search_panel;
    Constraints constraints;


    public SearchFlightCustomerMainFrame(ArrayList<DisposableObject> callingObjects, Controller controller, Dimension dimension, Point point) {

        super();

        constraints = new Constraints();

        //makes this the operating frame
        this.setMainFrame(callingObjects, dimension, point);
        /*
        if (controller.developerMode) {
            for (JFrame frame : callingFrames) {
                System.out.println(frame.getName());
            }
            System.out.println();
        }*/
        //callingFrame.dispose();

        //Setting surrounding panels
        this.addTitlePanel("AEROPORTO DI NAPOLI", controller);
        this.addNavigatorBarPanel(callingObjects, controller);
        //this.addHamburgerPanel(callingFrames, controller);
        this.add_menu_panel(callingObjects, controller);
        this.addUserPanel(callingObjects, controller);
        this.addFooterPanel();
        this.add_search_panel(callingObjects, controller);

        mainFrame.setVisible(true);
    }

    private void setMainFrame(ArrayList<DisposableObject> callingObjects, Dimension dimension, Point point) {

        mainFrame = new JFrame("Cerca voli");
        mainFrame.setSize(dimension);
        mainFrame.setLocation(point);
        callingObjects.addLast(this);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridBagLayout());
        //mainFrame.setSize(1080, 720);
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

    //private void addHamburgerPanel(ArrayList<JFrame> callingFrames, Controller controller){

    //hamburgerPanel = new HamburgerPanel(callingFrames, controller);
    //constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.NONE,
    //        0, 0, GridBagConstraints.FIRST_LINE_START);
    //mainFrame.add(hamburgerPanel, constraints.getConstraints());
    //hamburgerPanel.setVisible(true);
    //}

    private void add_menu_panel(ArrayList<DisposableObject> callingObjects, Controller controller) {

        menu = new MenuPanelCustomer(callingObjects, controller);
        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.FIRST_LINE_START);
        mainFrame.add(menu, constraints.getConstraints());
        menu.setVisible(true);
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
        constraints.setConstraints(0, 4, 3, 1, GridBagConstraints.BOTH,
                0, 75, GridBagConstraints.PAGE_END);
        mainFrame.add(footerPanel, constraints.getConstraints());
        footerPanel.setVisible(true);
    }

    private void add_search_panel(ArrayList<DisposableObject> callingObjects, Controller controller) {

        search_panel = new SearchPanel(callingObjects, controller);
        constraints.setConstraints(0, 3, 3, 1, GridBagConstraints.HORIZONTAL,
                0, 75, GridBagConstraints.CENTER);

        mainFrame.add(search_panel, constraints.getConstraints());
        search_panel.setVisible(true);
    }

    @Override
    public void doOnDispose (ArrayList<DisposableObject> callingObjects, Controller controller) {
        if (search_panel.getSearch_result() != null) search_panel.getSearch_result().getMain_frame().dispose();
    }

    @Override
    public void doOnRestore (ArrayList<DisposableObject> callingObjects, Controller controller) {
        if (search_panel.getSearch_result() != null) search_panel.getSearch_result().getMain_frame().setVisible(true);
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
