package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * Customer home page interface for airport flight information display and system navigation.
 * <p>
 * This class extends {@link DisposableObject}.
 * </p>
 * <p>
 * The HomePageCustomer class supports comprehensive customer functionality, including:
 * </p>
 * <ul>
 *   <li><strong>Flight Information Display:</strong> Real-time arriving and departing flight tables with current status</li>
 *   <li><strong>Navigation Management:</strong> Customer navigation controls for accessing different system areas</li>
 *   <li><strong>User Session Management:</strong> Current customer information display and account controls</li>
 *   <li><strong>Customer Menu Access:</strong> Customer-specific menu with booking and search options</li>
 *   <li><strong>System Branding:</strong> Consistent airport branding and visual identity throughout the interface</li>
 *   <li><strong>State Management:</strong> Advanced state restoration and disposal for navigation efficiency</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see DisposableObject
 * @see Controller
 * @see ImminentFlightsTable
 * @see NavigatorBarPanel
 * @see MenuPanelCustomer
 * @see UserPanel
 * @see TitlePanel
 * @see Constraints
 */
public class HomePageCustomer extends DisposableObject {

    /**
     * Main application window frame for the customer home interface.
     */
    private JFrame mainFrame;
    
    /**
     * User information panel displaying current customer user details.
     * <p>
     * This panel provides current customer session information, account controls,
     * and customer-specific functionality access. The panel integrates with customer
     * session management for automatic updates and validation.
     * </p>
     */
    private UserPanel userPanel;

    /**
     * Panel container for arriving flights information display.
     */
    private JPanel arrivingPanel;
    
    /**
     * Panel container for departing flights information display.
     */
    private JPanel departingPanel;

    /**
     * Table component displaying imminent arriving flight information.
     * <p>
     * This specialized table provides detailed information about flights
     * arriving at the airport, including flight IDs, airlines, routes,
     * arrival times, current status, and assigned gates.
     * </p>
     */
    private ImminentFlightsTable arrivingTable;
    
    /**
     * Table component displaying imminent departing flight information.
     * <p>
     * This specialized table provides detailed information about flights
     * departing from the airport, including flight IDs, airlines, routes,
     * departure times, current status, and assigned gates.
     * </p>
     */
    private ImminentFlightsTable departingTable;

    /**
     * Layout constraints utility for precise component positioning and sizing.
     */
    private final Constraints constraints;

    /**
     * Constructs a new HomePageCustomer interface for customer system access and flight information display.
     * <p>
     * This constructor initializes the complete customer home interface by establishing
     * the main application window and configuring all essential customer components
     * including navigation, flight information tables, user management, and system branding.
     * The constructor creates a fully functional customer dashboard ready for immediate use.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li><strong>Component Infrastructure:</strong> Initializing layout constraints and fundamental interface components</li>
     *   <li><strong>Window Configuration:</strong> Establishing the main application frame with proper sizing and display properties</li>
     *   <li><strong>Interface Assembly:</strong> Adding and configuring all customer interface components in proper order</li>
     *   <li><strong>Flight Information Setup:</strong> Initializing arriving and departing flight tables with current data</li>
     *   <li><strong>Navigation Integration:</strong> Integrating the interface with the application navigation hierarchy</li>
     *   <li><strong>Visibility Activation:</strong> Making the complete interface visible and ready for customer interaction</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and navigation
     * @param controller the system controller providing access to flight data, customer operations, and system functionality
     */
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


        //resizing logic
        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                resizeTable(arrivingPanel, arrivingTable);
                resizeTable(departingPanel, departingTable);

            }
        });
        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                resizeTable(arrivingPanel, arrivingTable);
                resizeTable(departingPanel, departingTable);
            }
        });

        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                super.componentMoved(e);
                resizeTable(arrivingPanel, arrivingTable);
                resizeTable(departingPanel, departingTable);
            }
        });

        mainFrame.addWindowStateListener(new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                super.windowStateChanged(e);
                resizeTable(arrivingPanel, arrivingTable);
                resizeTable(departingPanel, departingTable);
            }
        });
    }

    /**
     * Initializes and configures the main application window frame with customer interface properties.
     * <p>
     * This method establishes the primary window container for the customer home interface,
     * configuring window properties optimized for customer workflows including sizing,
     * positioning, display state, and visual styling. The method also integrates the window
     * with the application navigation hierarchy for proper resource management.
     * </p>
     * <p>
     * Main frame configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Window Properties:</strong> Title, size, and display state configuration for optimal customer interface</li>
     *   <li><strong>Navigation Integration:</strong> Registration with calling objects hierarchy for proper resource management</li>
     *   <li><strong>Layout Management:</strong> GridBagLayout configuration for precise component positioning</li>
     *   <li><strong>Display Optimization:</strong> Full-screen maximization for optimal content visibility</li>
     *   <li><strong>Visual Styling:</strong> Background color and aesthetic properties for professional customer appearance</li>
     *   <li><strong>Application Lifecycle:</strong> Proper exit behavior and window management configuration</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper integration and resource management
     */
    private void setMainFrame(List<DisposableObject> callingObjects) {

        mainFrame = new JFrame("Home");
        callingObjects.addLast(this);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridBagLayout());
        mainFrame.setSize(1080, 720);
        mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        mainFrame.getContentPane().setBackground(new Color(240, 242, 245));
    }

    /**
     * Creates and configures the title panel with airport branding for customer interface identification.
     * <p>
     * This method establishes the title section of the customer home interface, providing
     * consistent airport branding and system identification. The title panel uses the standardized
     * {@link TitlePanel} component configured with airport-specific branding and positioned
     * appropriately within the interface layout hierarchy.
     * </p>
     * <p>
     * Title panel configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Branding Display:</strong> "AEROPORTO DI NAPOLI" title for clear system identification</li>
     *   <li><strong>Layout Positioning:</strong> Horizontal span across interface top for prominent display</li>
     *   <li><strong>Spacing Configuration:</strong> Appropriate margins and insets for visual balance</li>
     *   <li><strong>Immediate Visibility:</strong> Panel becomes visible immediately upon creation</li>
     * </ul>
     */
    private void addTitlePanel() {

        TitlePanel titlePanel = new TitlePanel("AEROPORTO DI NAPOLI");
        constraints.setConstraints(0, 0, 2, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 0.0f, new Insets(5, 10, 0, 10));
        mainFrame.add(titlePanel, constraints.getGridBagConstraints());
        titlePanel.setVisible(true);
    }

    /**
     * Creates and configures the navigation bar panel for system-wide navigation and interface access.
     * <p>
     * This method establishes the navigation section of the customer home interface,
     * providing customers with access to different areas of the airport management system.
     * The navigation bar uses the standardized {@link NavigatorBarPanel} component configured
     * for customer workflows and integrated with the application navigation hierarchy.
     * </p>
     * <p>
     * Navigation bar configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Navigation Integration:</strong> Full integration with application navigation hierarchy and state management</li>
     *   <li><strong>Customer Access:</strong> Navigation options appropriate for customer workflows and permissions</li>
     *   <li><strong>Visual Integration:</strong> Background color matching for seamless interface integration</li>
     *   <li><strong>Layout Positioning:</strong> Horizontal span with proper spacing for optimal navigation access</li>
     *   <li><strong>Controller Integration:</strong> Access to system controller for navigation logic and state management</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper navigation state management
     * @param controller the system controller providing access to navigation logic and application state
     */
    private void addNavigatorBarPanel(List<DisposableObject> callingObjects, Controller controller) {

        NavigatorBarPanel navigatorBarPanel = new NavigatorBarPanel(callingObjects, controller);
        constraints.setConstraints(0, 1, 2, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.PAGE_START);
        mainFrame.add(navigatorBarPanel, constraints.getGridBagConstraints());
        navigatorBarPanel.setBackground(new Color(240, 242, 245));
        navigatorBarPanel.setVisible(true);
    }

    /**
     * Creates and configures the customer menu panel for customer-specific functions and navigation.
     * <p>
     * This method establishes the customer menu section of the home interface, providing
     * customers with access to customer-specific functions including flight search, booking
     * management, and account services. The menu uses the {@link MenuPanelCustomer} component
     * configured specifically for customer workflows and positioned for optimal access.
     * </p>
     * <p>
     * Customer menu configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Customer Functions:</strong> Access to customer-specific tools including flight search and booking management</li>
     *   <li><strong>Operational Integration:</strong> Integration with calling objects hierarchy for proper workflow management</li>
     *   <li><strong>Controller Access:</strong> Full access to system controller for customer operations and data management</li>
     *   <li><strong>Layout Positioning:</strong> Left-aligned positioning for intuitive customer access patterns</li>
     *   <li><strong>Immediate Visibility:</strong> Panel becomes visible immediately upon creation for customer interaction</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper workflow integration
     * @param controller the system controller providing access to customer functions and system management capabilities
     */
    private void addMenuPanel(List<DisposableObject> callingObjects, Controller controller) {

        MenuPanelCustomer hamburgerPanel = new MenuPanelCustomer(callingObjects, controller);
        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.FIRST_LINE_START);
        mainFrame.add(hamburgerPanel, constraints.getGridBagConstraints());
        hamburgerPanel.setVisible(true);
    }

    /**
     * Creates and configures the user information panel for current customer user display and management.
     * <p>
     * This method establishes the user information section of the customer home interface,
     * providing display of current customer session information, account controls, and
     * customer-specific functionality access. The user panel integrates with customer session
     * management systems for automatic updates and validation throughout customer sessions.
     * </p>
     * <p>
     * User panel configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Session Management:</strong> Display of current customer user information and session status</li>
     *   <li><strong>Account Controls:</strong> Access to customer account management and session controls</li>
     *   <li><strong>Navigation Integration:</strong> Integration with calling objects hierarchy for proper customer workflow management</li>
     *   <li><strong>Controller Access:</strong> Access to customer management functionality through system controller</li>
     *   <li><strong>Layout Positioning:</strong> Right-aligned positioning following conventional user panel placement patterns</li>
     *   <li><strong>State Persistence:</strong> User panel reference maintained for state management and restoration operations</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper customer workflow integration
     * @param controller the system controller providing access to customer management and session functionality
     */
    private void addUserPanel(List<DisposableObject> callingObjects, Controller controller) {

        userPanel = new UserPanel(callingObjects, controller);
        constraints.setConstraints(1, 2, 1, 1, GridBagConstraints.VERTICAL,
                0, 0, GridBagConstraints.LINE_END);
        mainFrame.add(userPanel, constraints.getGridBagConstraints());
        userPanel.setVisible(true);
    }

    /**
     * Creates and configures the arriving flights information panel with real-time flight data display.
     * <p>
     * This method establishes the arriving flights section of the customer home interface,
     * providing customers with comprehensive information about flights arriving at the airport.
     * The panel includes a dedicated table for flight data display and proper labeling for
     * customer navigation and information accessibility.
     * </p>
     * <p>
     * Arriving flights panel configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Panel Infrastructure:</strong> GridBagLayout container with matching background styling</li>
     *   <li><strong>Flight Data Integration:</strong> Real-time arriving flights table through controller integration</li>
     *   <li><strong>Visual Labeling:</strong> Clear section identification with appropriate typography</li>
     *   <li><strong>Layout Positioning:</strong> Full-width panel placement with proper spacing and margins</li>
     *   <li><strong>Accessibility Support:</strong> Label-to-table association for improved accessibility</li>
     *   <li><strong>Immediate Visibility:</strong> Panel becomes visible immediately upon creation</li>
     * </ul>
     *
     * @param controller the system controller providing access to current arriving flight data and flight management functionality
     */
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

    /**
     * Creates and configures the arriving flights table with comprehensive flight information display.
     * <p>
     * This method establishes the arriving flights data table within the provided panel container,
     * retrieving current arriving flight information from the flight controller and presenting
     * it in a structured, customer-friendly format. The table provides essential flight details
     * for customer travel planning and airport navigation.
     * </p>
     * <p>
     * Arriving flights table configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Column Structure:</strong> Comprehensive flight information columns including ID, airline, date, route, arrival time, status, and gate</li>
     *   <li><strong>Data Integration:</strong> Real-time data retrieval through controller's flight management system</li>
     *   <li><strong>Table Implementation:</strong> Specialized ImminentFlightsTable for optimal flight data presentation</li>
     *   <li><strong>Layout Integration:</strong> Proper table positioning with full expansion within panel container</li>
     *   <li><strong>Scroll Support:</strong> Integrated scroll container for handling large flight data sets</li>
     * </ul>
     *
     * @param tablePanel the panel container where the arriving flights table will be positioned and displayed
     * @param controller the system controller providing access to current arriving flight data and flight management functionality
     */
    private void setArrivingTable(JPanel tablePanel, Controller controller) {

        String[] columnTitle = {"Id", "Compagnia", "Data", "Tratta", "Orario di arrivo", "Stato del volo", "Gate"};
        Object[][] data = controller.getFlightController().getImminentArrivingFlights();
        arrivingTable = new ImminentFlightsTable(data, columnTitle);

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.CENTER);
        tablePanel.add(arrivingTable.getScrollContainer(), constraints.getGridBagConstraints());
    }

    /**
     * Creates and configures the departing flights information panel with real-time flight data display.
     * <p>
     * This method establishes the departing flights section of the customer home interface,
     * providing customers with comprehensive information about flights departing from the airport.
     * The panel includes a dedicated table for flight data display and proper labeling for
     * customer navigation and information accessibility.
     * </p>
     * <p>
     * Departing flights panel configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Panel Infrastructure:</strong> GridBagLayout container with matching background styling</li>
     *   <li><strong>Flight Data Integration:</strong> Real-time departing flights table through controller integration</li>
     *   <li><strong>Visual Labeling:</strong> Clear section identification with appropriate typography</li>
     *   <li><strong>Layout Positioning:</strong> Full-width panel placement with proper spacing and margins</li>
     *   <li><strong>Accessibility Support:</strong> Label-to-table association for improved accessibility</li>
     *   <li><strong>Immediate Visibility:</strong> Panel becomes visible immediately upon creation</li>
     * </ul>
     *
     * @param controller the system controller providing access to current departing flight data and flight management functionality
     */
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

    /**
     * Creates and configures the departing flights table with comprehensive flight information display.
     * <p>
     * This method establishes the departing flights data table within the provided panel container,
     * retrieving current departing flight information from the flight controller and presenting
     * it in a structured, customer-friendly format. The table provides essential flight details
     * for customer travel planning and airport navigation.
     * </p>
     * <p>
     * Departing flights table configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Column Structure:</strong> Comprehensive flight information columns including ID, airline, date, route, departure time, status, and gate</li>
     *   <li><strong>Data Integration:</strong> Real-time data retrieval through controller's flight management system</li>
     *   <li><strong>Table Implementation:</strong> Specialized ImminentFlightsTable for optimal flight data presentation</li>
     *   <li><strong>Layout Integration:</strong> Proper table positioning with full expansion within panel container</li>
     *   <li><strong>Scroll Support:</strong> Integrated scroll container for handling large flight data sets</li>
     * </ul>
     *
     * @param tablePanel the panel container where the departing flights table will be positioned and displayed
     * @param controller the system controller providing access to current departing flight data and flight management functionality
     */
    private void setDepartingTable(JPanel tablePanel, Controller controller) {

        String[] columnTitles = {"id", "Compagnia", "Data", "Tratta", "Orario di partenza", "Stato del volo", "Gate"};
        Object[][] data = controller.getFlightController().getImminentDepartingFlights();
        departingTable = new ImminentFlightsTable(data, columnTitles);

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.CENTER);
        tablePanel.add(departingTable.getScrollContainer(), constraints.getGridBagConstraints());
    }

    /**
     * Resize the table in input.
     *
     * @param parentPanel the panel that contains the ImminentFlightsTable table
     * @param table table to resize
     */
    private void resizeTable(JPanel parentPanel, ImminentFlightsTable table) {
        table.setRowHeight(parentPanel.getHeight() / (table.getRowCount() + 3));
    }
    
    /**
     * Performs customer session cleanup and resource management during interface disposal operations.
     * <p>
     * This method implements specialized disposal functionality for the customer home interface
     * by clearing customer session data and performing necessary logout operations to ensure
     * proper security and privacy protection. The disposal process maintains system security
     * by properly terminating customer sessions during navigation transitions or application exit.
     * </p>
     * <p>
     * The disposal operations include:
     * </p>
     * <ul>
     *   <li><strong>Customer Session Cleanup:</strong> Clearing customer controller session data to ensure privacy protection</li>
     *   <li><strong>User Session Termination:</strong> Clearing user controller session data for complete logout</li>
     *   <li><strong>Security Management:</strong> Ensuring proper session termination for customer privacy and system security</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper disposal coordination
     * @param controller the system controller providing access to customer session management and security functionality
     */
    @Override
    public void doOnDispose(List<DisposableObject> callingObjects, Controller controller) {
        controller.getCustomerController().setLoggedCustomer(null, null);
        controller.getUserController().setLoggedUser(null, null);
    }

    /**
     * Performs state restoration and interface refresh operations when returning to the customer home interface.
     * <p>
     * This method implements sophisticated state restoration functionality that ensures the
     * customer home interface displays current and accurate information when returning from
     * navigation operations. The restoration process includes customer session validation,
     * flight information refresh, and interface component updates for optimal customer
     * experience continuity.
     * </p>
     * <p>
     * The restoration operations include:
     * </p>
     * <ul>
     *   <li><strong>Customer Session Validation:</strong> Verification and refresh of current customer information display</li>
     *   <li><strong>User Panel Refresh:</strong> Dynamic user panel replacement when customer session changes are detected</li>
     *   <li><strong>Flight Information Update:</strong> Complete refresh of arriving and departing flight tables</li>
     *   <li><strong>Interface Synchronization:</strong> Panel removal and recreation for current data display</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper restoration coordination
     * @param controller the system controller providing access to current customer session and flight information
     */
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

    /**
     * Provides access to the main application window frame for navigation and resource management.
     *
     * @return the main JFrame instance containing the customer home interface
     */
    @Override
    public JFrame getFrame() {
        return this.mainFrame;
    }

}