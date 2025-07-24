package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Customer flight search interface providing flight search and booking capabilities for the airport management system.
 * <p>
 * This class extends {@link DisposableObject}.
 * </p>
 * <p>
 * The SearchFlightCustomerMainFrame class supports comprehensive customer flight operations including:
 * </p>
 * <ul>
 *   <li><strong>Flight Search Functionality:</strong> Advanced search capabilities with multiple criteria including routes, dates, and times</li>
 *   <li><strong>Real-time Availability:</strong> Current flight availability and seat information display with dynamic updates</li>
 *   <li><strong>Booking Integration:</strong> Direct booking initiation from search results with seamless workflow transitions</li>
 *   <li><strong>Customer Navigation:</strong> Integrated navigation controls for accessing different customer areas and functions</li>
 *   <li><strong>User Session Management:</strong> Current customer information display and account management integration</li>
 *   <li><strong>Menu-Based Access:</strong> Support for customer menu navigation and direct interface access patterns</li>
 *   <li><strong>State Restoration:</strong> Advanced state management for maintaining search results and interface continuity</li>
 * </ul>
 * <p>
 * Layout architecture utilizes {@link GridBagLayout} for precise component positioning and optimal
 * space utilization.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see DisposableObject
 * @see Controller
 * @see SearchFlightPanel
 * @see NavigatorBarPanel
 * @see MenuPanelCustomer
 * @see UserPanel
 * @see TitlePanel
 * @see Constraints
 */
public class SearchFlightCustomerMainFrame extends DisposableObject {

    /**
     * Main application window frame for the customer flight search interface.
     * <p>
     * This JFrame serves as the primary container for all customer flight search
     * components.
     * </p>
     */
    private JFrame mainFrame;

    /**
     * Flight search panel providing search and filtering capabilities.
     * <p>
     * This SearchFlightPanel component enables customers to search flights
     * using various criteria including routes, dates, times.
     * The panel includes state management for search result persistence and filter
     * configuration throughout navigation operations.
     * </p>
     */
    private SearchFlightPanel searchFlightPanel;

    /**
     * Layout constraints utility for precise component positioning throughout the interface.
     */
    Constraints constraints;

    /**
     * Constructs a new SearchFlightCustomerMainFrame with comprehensive flight search functionality and customer workflow integration.
     * <p>
     * This constructor initializes the complete customer flight search interface by establishing
     * the main application window and configuring all essential customer components, including
     * navigation, flight search capabilities, user management. The constructor
     * creates a fully functional flight search interface ready for immediate customer use with
     * a booking initiation section.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li><strong>Component Infrastructure:</strong> Initializing layout constraints and fundamental interface components</li>
     *   <li><strong>Window Configuration:</strong> Establishing the main application frame with proper sizing and display properties</li>
     *   <li><strong>Interface Assembly:</strong> Adding and configuring all customer interface components in proper order</li>
     *   <li><strong>Flight Search Setup:</strong> Initializing comprehensive flight search functionality with result caching</li>
     *   <li><strong>Navigation Integration:</strong> Integrating the interface with the application navigation hierarchy</li>
     *   <li><strong>Visibility Activation:</strong> Making the complete interface visible and ready for customer interaction</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and workflow coordination
     * @param controller the system controller providing access to flight search functionality and booking management services
     * @param dimension the preferred window size for the customer flight search interface
     * @param point the initial window position coordinates for optimal screen placement
     * @param fullScreen the window extended state configuration for display optimization
     */
    public SearchFlightCustomerMainFrame(List<DisposableObject> callingObjects, Controller controller, Dimension dimension, Point point, int fullScreen) {

        super();
        constraints = new Constraints();
        this.setMainFrame((ArrayList<DisposableObject>)callingObjects, dimension, point, fullScreen);

        this.addTitlePanel();
        this.addNavigatorBarPanel((ArrayList<DisposableObject>)callingObjects, controller);
        this.addMenuPanel((ArrayList<DisposableObject>)callingObjects, controller);
        this.addUserPanel((ArrayList<DisposableObject>)callingObjects, controller);
        this.addSearchPanel((ArrayList<DisposableObject>)callingObjects, controller);

        mainFrame.setVisible(true);
    }

    /**
     * Configures and initializes the main application frame with comprehensive window management and navigation integration.
     * <p>
     * This private method establishes the primary application window for customer flight search
     * operations by configuring window properties, integrating with the navigation hierarchy,
     * and establishing visual presentation standards. The method creates a fully configured
     * JFrame ready for component integration with proper sizing, positioning, and customer-friendly
     * interface presentation throughout flight search and booking workflows.
     * </p>
     * <p>
     * The frame configuration process includes:
     * </p>
     * <ul>
     *   <li><strong>Window Creation:</strong> JFrame instantiation with Italian localization for customer interface identification</li>
     *   <li><strong>Size and Position Management:</strong> Dimension and coordinate configuration for optimal screen placement</li>
     *   <li><strong>State Configuration:</strong> Extended state management for window maximization and display optimization</li>
     *   <li><strong>Navigation Integration:</strong> Calling objects hierarchy registration for proper resource management</li>
     *   <li><strong>Window Behavior Setup:</strong> Close operation and layout manager configuration for interface consistency</li>
     *   <li><strong>Size Constraints:</strong> Minimum size establishment for optimal functionality across display configurations</li>
     *   <li><strong>Visual Styling:</strong> Background color configuration for professional customer interface presentation</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and workflow coordination
     * @param dimension the preferred window size for optimal flight search interface presentation
     * @param point the initial window position coordinates for optimal screen placement and customer accessibility
     * @param fullScreen the window extended state configuration for display optimization and customer screen utilization
     */
    private void setMainFrame(ArrayList<DisposableObject> callingObjects, Dimension dimension, Point point, int fullScreen) {

        mainFrame = new JFrame("Cerca voli");
        mainFrame.setSize(dimension);
        mainFrame.setLocation(point);
        mainFrame.setExtendedState(fullScreen);
        callingObjects.addLast(this);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridBagLayout());

        mainFrame.setMinimumSize(new Dimension(1420, 1080));

        mainFrame.getContentPane().setBackground(new Color(240, 242, 245));

    }

    /**
     * Creates and configures the title panel with airport branding for customer flight search interface identification.
     * <p>
     * This private method establishes the title section of the customer flight search interface,
     * providing consistent airport branding and system identification. The title panel uses the
     * standardized {@link TitlePanel} component configured with airport-specific branding and
     * positioned appropriately within the interface layout hierarchy.
     * </p>
     * <p>
     * The title panel configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Branding Display:</strong> "AEROPORTO DI NAPOLI" title for clear system identification and customer awareness</li>
     *   <li><strong>Layout Positioning:</strong> Horizontal span across interface top with proper alignment and spacing</li>
     *   <li><strong>Visual Integration:</strong> Transparent background for seamless interface integration and professional appearance</li>
     *   <li><strong>Spacing Configuration:</strong> Appropriate margins and insets for visual balance and optimal presentation</li>
     *   <li><strong>Immediate Visibility:</strong> Panel becomes visible immediately upon creation for customer interface identification</li>
     * </ul>
     */
    private void addTitlePanel() {

        TitlePanel titlePanel;

        titlePanel = new TitlePanel("AEROPORTO DI NAPOLI");

        titlePanel.setOpaque(false);

        constraints.setConstraints(0, 0, 2, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 0.0f, new Insets(5, 10, 0, 10));

        mainFrame.add(titlePanel, constraints.getGridBagConstraints());
        titlePanel.setVisible(true);
    }

    /**
     * Creates and configures the navigation bar panel for system-wide navigation and customer interface access.
     * <p>
     * This private method establishes the navigation section of the customer flight search interface,
     * providing customers with access to different areas of the airport management system while
     * maintaining the current flight search context. The navigation bar uses the {@link NavigatorBarPanel}
     * component configured for customer workflows and positioned for optimal accessibility throughout
     * flight search and booking operations.
     * </p>
     * <p>
     * The navigation bar configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Navigation Controls:</strong> System-wide navigation capabilities for customer area access</li>
     *   <li><strong>Context Preservation:</strong> Maintains flight search state during navigation operations</li>
     *   <li><strong>Layout Integration:</strong> Horizontal spanning with proper alignment and spacing for optimal accessibility</li>
     *   <li><strong>Visual Consistency:</strong> Transparent background for seamless interface integration</li>
     *   <li><strong>Customer Workflow Support:</strong> Navigation patterns optimized for customer travel planning workflows</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and workflow coordination
     * @param controller the system controller providing access to navigation functionality and system state management
     */
    private void addNavigatorBarPanel(ArrayList<DisposableObject> callingObjects, Controller controller) {

        NavigatorBarPanel navigatorBarPanel = new NavigatorBarPanel(callingObjects, controller);

        navigatorBarPanel.setOpaque(false);

        constraints.setConstraints(0, 1, 2, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 0.0f, new Insets(0, 10, 10, 10));

        mainFrame.add(navigatorBarPanel, constraints.getGridBagConstraints());
        navigatorBarPanel.setVisible(true);
    }

    /**
     * Creates and configures the customer menu panel for specialized customer functions and service access.
     * <p>
     * This private method establishes the customer menu section of the flight search interface,
     * providing customers with access to specialized customer functions including flight search,
     * booking management, and account services. The menu uses the {@link MenuPanelCustomer} component
     * configured specifically for customer workflows and positioned for optimal access throughout
     * flight search and booking operations.
     * </p>
     * <p>
     * The customer menu configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Customer Functions:</strong> Access to customer-specific tools including flight search and booking management</li>
     *   <li><strong>Operational Integration:</strong> Integration with calling objects hierarchy for proper workflow management</li>
     *   <li><strong>Controller Access:</strong> Full access to system controller for customer operations and data management</li>
     *   <li><strong>Layout Positioning:</strong> Left-aligned positioning for intuitive customer access patterns</li>
     *   <li><strong>Visual Integration:</strong> Transparent background for seamless interface integration</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper workflow management and resource coordination
     * @param controller the system controller providing access to customer operations and comprehensive system functionality
     */
    private void addMenuPanel(ArrayList<DisposableObject> callingObjects, Controller controller) {

        MenuPanelCustomer menu = new MenuPanelCustomer(callingObjects, controller);

        menu.setOpaque(false);

        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.FIRST_LINE_START, 0.0f, 0.0f, new Insets(0, 10, 0, 0));

        mainFrame.add(menu, constraints.getGridBagConstraints());
        menu.setVisible(true);
    }

    /**
     * Creates and configures the user panel for customer session management and account information display.
     * <p>
     * This private method establishes the user information section of the customer flight search
     * interface, providing current customer session information, account controls, and customer-specific
     * functionality access. The user panel uses the {@link UserPanel} component configured for
     * customer workflows and positioned for optimal accessibility throughout flight search and
     * booking operations while maintaining customer privacy and session security.
     * </p>
     * <p>
     * The user panel configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Session Information:</strong> Current customer account details and authentication status display</li>
     *   <li><strong>Account Controls:</strong> Customer-specific functionality access and account management capabilities</li>
     *   <li><strong>Workflow Integration:</strong> Integration with calling objects hierarchy for proper session management</li>
     *   <li><strong>Controller Access:</strong> Full access to customer management functionality through system controller</li>
     *   <li><strong>Layout Positioning:</strong> Right-aligned positioning for intuitive customer account access patterns</li>
     *   <li><strong>Visual Integration:</strong> Transparent background for seamless interface integration and professional presentation</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper session management and workflow coordination
     * @param controller the system controller providing access to customer management functionality and account services
     */
    private void addUserPanel(ArrayList<DisposableObject> callingObjects, Controller controller) {

        UserPanel userPanel = new UserPanel(callingObjects, controller);

        userPanel.setOpaque(false);

        constraints.setConstraints(1, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.FIRST_LINE_END, 0.0f, 0.0f, new Insets(0, 0, 0, 10));

        mainFrame.add(userPanel, constraints.getGridBagConstraints());
        userPanel.setVisible(true);
    }

    /**
     * Creates and configures the comprehensive flight search panel for customer flight discovery and booking capabilities.
     * <p>
     * This private method establishes the main content area of the customer flight search interface,
     * providing comprehensive flight search functionality with multiple filter options, real-time
     * availability checking, and direct booking initiation capabilities. The panel uses the
     * {@link SearchFlightPanel} component configured for customer workflows and positioned for
     * optimal space utilization throughout flight search and booking operations.
     * </p>
     * <p>
     * The flight search panel configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Comprehensive Search Functionality:</strong> Advanced flight search with route-based, temporal, and availability filtering</li>
     *   <li><strong>Real-time Data Access:</strong> Current flight availability and scheduling information with dynamic updates</li>
     *   <li><strong>Booking Integration:</strong> Direct booking initiation from search results with seamless workflow transitions</li>
     *   <li><strong>Navigation Integration:</strong> Integration with calling objects hierarchy for proper workflow management</li>
     *   <li><strong>Controller Access:</strong> Full access to flight search functionality and booking management services</li>
     *   <li><strong>Layout Optimization:</strong> Full content area utilization with both horizontal and vertical expansion</li>
     *   <li><strong>State Persistence:</strong> Search state maintenance for restoration and navigation operations</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper workflow management and resource coordination
     * @param controller the system controller providing access to flight search functionality and booking management services
     */
    private void addSearchPanel(ArrayList<DisposableObject> callingObjects, Controller controller) {

        searchFlightPanel = new SearchFlightPanel(callingObjects, controller);

        constraints.setConstraints(0, 3, 2, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.CENTER, 1.0f, 1.0f, new Insets(20, 40, 40, 40));

        mainFrame.add(searchFlightPanel, constraints.getGridBagConstraints());
        searchFlightPanel.setVisible(true);
    }

    /**
     * Performs cleanup operations when the flight search interface is disposed of.
     * <p>
     * This method implements the disposal pattern by clearing flight search result caches
     * and performing resource cleanup to ensure optimal system performance and customer
     * privacy protection. The cleanup prevents memory leaks and ensures that customer
     * search data is properly removed when the interface is closed or replaced during
     * navigation operations within the airport management system.
     * </p>
     * <p>
     * Disposal operations include:
     * </p>
     * <ul>
     *   <li><strong>Search Cache Cleanup:</strong> Clearing flight search result caches to free memory and protect customer data</li>
     *   <li><strong>Resource Management:</strong> Proper cleanup of interface resources and component references</li>
     *   <li><strong>Privacy Protection:</strong> Ensuring customer search data is removed from system memory</li>
     *   <li><strong>Performance Optimization:</strong> Preventing memory leaks during interface lifecycle management</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper cleanup coordination
     * @param controller the system controller providing access to cache clearing functionality and resource management services
     */
    @Override
    public void doOnDispose (List<DisposableObject> callingObjects, Controller controller) {
        controller.clearSearchFlightsResultCache();
    }

    /**
     * Performs interface restoration operations when the flight search interface is reactivated.
     * <p>
     * This method implements sophisticated state restoration functionality that maintains customer
     * flight search context and results across navigation operations. The restoration process
     * examines the current search state and automatically re-executes previous search operations
     * to ensure customers return to their previous flight search results and filter configurations
     * when navigating back to the flight search interface from other areas of the airport management system.
     * </p>
     * <p>
     * The restoration operations include:
     * </p>
     * <ul>
     *   <li><strong>Search State Detection:</strong> Examination of previous search operations for intelligent restoration</li>
     *   <li><strong>Search Re-execution:</strong> Automatic re-execution of previous flight searches with preserved parameters</li>
     *   <li><strong>Interface Updates:</strong> Component repainting and revalidation for immediate visual feedback</li>
     *   <li><strong>Context Preservation:</strong> Maintenance of search filters and result state for workflow continuity</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper restoration coordination
     * @param controller the system controller providing access to flight search functionality and state management services
     */
    @Override
    public void doOnRestore (List<DisposableObject> callingObjects, Controller controller) {
        if(searchFlightPanel.isSearchPerformed()){

            searchFlightPanel.executeResearch(callingObjects, controller, searchFlightPanel.getSearchButton());

            searchFlightPanel.repaint();
            searchFlightPanel.revalidate();
        }



    }

    /**
     * Returns the main application frame for integration with the navigation hierarchy.
     * <p>
     * This method provides access to the primary JFrame component for integration with
     * the application navigation system, enabling proper window management, state coordination,
     * and resource management throughout customer flight search operations. The frame reference
     * supports navigation hierarchy integration and enables seamless transitions between
     * different areas of the airport management system while maintaining flight search context.
     * </p>
     * <p>
     * Frame access enables:
     * </p>
     * <ul>
     *   <li><strong>Navigation Integration:</strong> Proper window management during navigation operations and interface transitions</li>
     *   <li><strong>State Coordination:</strong> Window state management for restoration and disposal operations</li>
     *   <li><strong>Resource Management:</strong> Proper resource cleanup and memory management during interface lifecycle operations</li>
     *   <li><strong>Visual Management:</strong> Window visibility coordination during navigation transitions and customer workflow management</li>
     * </ul>
     *
     * @return the main JFrame component for navigation integration and window management operations
     */
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