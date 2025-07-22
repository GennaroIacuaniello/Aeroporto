package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HomePageAdmin extends DisposableObject {

    private JFrame mainFrame;
    private TitlePanel titlePanel;
    private NavigatorBarPanel navigatorBarPanel;
    private MenuPanelAdmin menu;
    private UserPanel userPanel;
    private SearchFlightPanel searchFlightPanel;
    private Constraints constraints;

    public HomePageAdmin(List<DisposableObject> callingObjects, Controller controller) {

        super();
        constraints = new Constraints();
        this.setMainFrame((ArrayList<DisposableObject>)callingObjects);

        this.addTitlePanel(controller);
        this.addNavigatorBarPanel((ArrayList<DisposableObject>)callingObjects, controller);
        this.addMenuPanel((ArrayList<DisposableObject>)callingObjects, controller);
        this.addUserPanel((ArrayList<DisposableObject>)callingObjects, controller);
        this.addSearchPanel((ArrayList<DisposableObject>)callingObjects, controller);

        mainFrame.setVisible(true);
    }

    private void setMainFrame(ArrayList<DisposableObject> callingObjects) {

        mainFrame = new JFrame("Home");
        mainFrame.setSize(1080, 720);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        callingObjects.addLast(this);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridBagLayout());

        mainFrame.setMinimumSize(new Dimension(1420, 1080));

        mainFrame.getContentPane().setBackground(new Color(240, 242, 245));

    }

    private void addTitlePanel(Controller controller) {

        titlePanel = new TitlePanel("AEROPORTO DI NAPOLI", controller);

        titlePanel.setOpaque(false);

        constraints.setConstraints(0, 0, 2, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 0.0f, new Insets(5, 10, 0, 10));

        mainFrame.add(titlePanel, constraints.getConstraints());
        titlePanel.setVisible(true);
    }

    private void addNavigatorBarPanel(ArrayList<DisposableObject> callingObjects, Controller controller) {

        navigatorBarPanel = new NavigatorBarPanel(callingObjects, controller);

        navigatorBarPanel.setOpaque(false);

        constraints.setConstraints(0, 1, 2, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 0.0f, new Insets(0, 10, 10, 10));

        mainFrame.add(navigatorBarPanel, constraints.getConstraints());
        navigatorBarPanel.setVisible(true);
    }

    private void addMenuPanel(ArrayList<DisposableObject> callingObjects, Controller controller) {

        menu = new MenuPanelAdmin(callingObjects, controller);

        menu.setOpaque(false);

        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.FIRST_LINE_START, 0.0f, 0.0f, new Insets(0, 10, 0, 0));

        mainFrame.add(menu, constraints.getConstraints());
        menu.setVisible(true);
    }

    private void addUserPanel(ArrayList<DisposableObject> callingObjects, Controller controller) {

        userPanel = new UserPanel(callingObjects, controller);

        userPanel.setOpaque(false);

        constraints.setConstraints(1, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.FIRST_LINE_END, 0.0f, 0.0f, new Insets(0, 0, 0, 10));

        mainFrame.add(userPanel, constraints.getConstraints());
        userPanel.setVisible(true);
    }

    private void addSearchPanel(ArrayList<DisposableObject> callingObjects, Controller controller) {

        searchFlightPanel = new SearchFlightPanel(callingObjects, controller);

        constraints.setConstraints(0, 3, 2, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.CENTER, 1.0f, 1.0f, new Insets(20, 40, 40, 40));

        mainFrame.add(searchFlightPanel, constraints.getConstraints());
        searchFlightPanel.setVisible(true);
    }

    @Override
    public void doOnDispose (ArrayList<DisposableObject> callingObjects, Controller controller) {
        //if (search_panel.getSearch_result() != null) search_panel.getSearch_result().getMain_frame().dispose();
        controller.clearSearchFlightsResultCache();
    }

    @Override
    public void doOnRestore (ArrayList<DisposableObject> callingObjects, Controller controller) {
        //if (search_panel.getSearch_result() != null) search_panel.getSearch_result().getMain_frame().setVisible(true);
        if(!userPanel.getUserGreeted().equals(controller.getUserController().getUsername())){
            userPanel.setVisible(false);
            mainFrame.remove(userPanel);
            addUserPanel(callingObjects, controller);
        }
        searchFlightPanel.executeResearch(callingObjects, controller, searchFlightPanel.getSearchButton());
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
