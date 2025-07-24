package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Customer booking management interface providing booking search, display, and management capabilities.
 * <p>
 * This class extends {@link DisposableObject}.
 * </p>
 * <p>
 * The MyBookingsCustomerMainFrame class supports customer booking operations including:
 * </p>
 * <ul>
 *   <li><strong>Booking Search Functionality:</strong> Search capabilities with multiple filter options for locating specific bookings</li>
 *   <li><strong>Booking Display Management:</strong> Comprehensive display of booking details including passenger information and flight data</li>
 *   <li><strong>Customer Navigation:</strong> Integrated navigation controls for accessing different customer areas and functions</li>
 *   <li><strong>User Session Management:</strong> Current customer information display and account management integration</li>
 *   <li><strong>Menu-Based Access:</strong> Support for both direct navigation and menu-initiated access patterns</li>
 *   <li><strong>State Restoration:</strong> State management for maintaining search results and interface continuity</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see DisposableObject
 * @see Controller
 * @see SearchBookingPanel
 * @see NavigatorBarPanel
 * @see MenuPanelCustomer
 * @see UserPanel
 * @see TitlePanel
 * @see Constraints
 */
public class MyBookingsCustomerMainFrame extends DisposableObject {

    /**
     * Main application window frame for the customer booking management interface.
     * <p>
     * This JFrame serves as the primary container for all customer booking management
     * components.
     * </p>
     */
    private JFrame mainFrame;

    /**
     * Booking search panel providing comprehensive search and filtering capabilities.
     * <p>
     * This SearchBookingPanel component enables customers to search for their bookings
     * using various criteria, including flight information and passenger details.
     * The panel includes state management for search result persistence and filter
     * configuration throughout navigation operations.
     * </p>
     */
    private SearchBookingPanel searchBookingPanel;
    
    /**
     * Layout constraints utility for precise component positioning throughout the interface.
     */
    private final Constraints constraints;

    /**
     * Constructs a new MyBookingsCustomerMainFrame interface for comprehensive customer booking management.
     * <p>
     * This constructor initializes the complete customer booking management interface by creating
     * the main application window, configuring all interface components, and establishing
     * search functionality for customer booking operations. The constructor creates
     * a fully functional booking management interface ready for immediate customer interaction
     * with integrated navigation, search capabilities, and session management.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li><strong>Component Infrastructure:</strong> Constraints utility initialization and main frame configuration</li>
     *   <li><strong>Window Management:</strong> Main application window setup with proper sizing and display properties</li>
     *   <li><strong>Interface Assembly:</strong> Sequential addition of all customer interface components in proper order</li>
     *   <li><strong>Search Integration:</strong> Booking search panel configuration with filter and menu access support</li>
     *   <li><strong>Navigation Setup:</strong> Complete navigation system integration with customer workflow support</li>
     *   <li><strong>Visibility Activation:</strong> Final interface visibility activation for immediate customer interaction</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and navigation
     * @param controller the system controller providing access to booking data, search functionality, and customer operations
     * @param dimension the preferred window size for the booking management interface
     * @param point the screen position where the booking management window should be displayed
     * @param fullScreen the window state indicating whether the interface should be maximized or in normal window mode
     * @param ifOpenedFromMenu flag indicating whether the interface was accessed through customer menu navigation for specialized behavior
     */
    public MyBookingsCustomerMainFrame(List<DisposableObject> callingObjects, Controller controller, Dimension dimension,
                                       Point point, int fullScreen, boolean ifOpenedFromMenu) {

        super();
        constraints = new Constraints();
        this.setMainFrame(callingObjects, dimension, point, fullScreen);

        this.addTitlePanel();
        this.addNavigatorBarPanel(callingObjects, controller);
        this.addMenuPanel(callingObjects, controller);
        this.addUserPanel(callingObjects, controller);
        this.addSearchPanel(callingObjects, controller, ifOpenedFromMenu);

        mainFrame.setVisible(true);
    }

    /**
     * Configures and initializes the main application window with comprehensive booking management properties.
     * <p>
     * This private method establishes the primary application window with Italian localization,
     * proper sizing constraints, and integration with the application navigation hierarchy. The
     * method configures window properties including size, position, state, and visual styling
     * to provide optimal customer booking management experience across different system configurations.
     * </p>
     * <p>
     * Main frame configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Window Identification:</strong> Italian title "Le mie prenotazioni" (My Bookings) for clear customer interface identification</li>
     *   <li><strong>Size and Position Management:</strong> Dynamic window dimensions and screen positioning based on calling context</li>
     *   <li><strong>State Configuration:</strong> Extended window state management for maximized or normal display modes</li>
     *   <li><strong>Navigation Integration:</strong> Registration with calling objects hierarchy for proper resource management</li>
     *   <li><strong>Layout Management:</strong> GridBagLayout configuration for precise component positioning and organization</li>
     *   <li><strong>Visual Styling:</strong> Professional background color and minimum size constraints for optimal customer experience</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management
     * @param dimension the preferred window size for optimal booking management interface display
     * @param point the screen position where the booking management window should be positioned
     * @param fullScreen the window state indicating maximized or normal display configuration
     */
    private void setMainFrame(List<DisposableObject> callingObjects, Dimension dimension, Point point, int fullScreen) {

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

    /**
     * Creates and configures the title panel with airport branding and professional identification.
     * <p>
     * This private method establishes the title section of the customer booking management interface,
     * providing consistent airport branding and system identification. The title panel uses the
     * standardized {@link TitlePanel} component configured with "AEROPORTO DI NAPOLI" branding
     * and positioned appropriately within the interface layout hierarchy.
     * </p>
     * <p>
     * Title panel configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Layout Positioning:</strong> Horizontal span across interface top with appropriate spacing and margins</li>
     *   <li><strong>Visual Integration:</strong> Transparent background for seamless integration with main frame styling</li>
     *   <li><strong>Immediate Visibility:</strong> Panel becomes visible immediately upon creation for customer recognition</li>
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
     * Creates and configures the navigation bar panel for system-wide navigation and interface access.
     * <p>
     * This private method establishes the navigation section of the customer booking management interface,
     * providing customers with access to different areas of the airport management system while
     * maintaining the booking management context. The navigation bar uses the standardized
     * {@link NavigatorBarPanel} component configured for customer workflows and integrated
     * with the application navigation hierarchy.
     * </p>
     * <p>
     * Navigation bar configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Navigation Integration:</strong> Full integration with application navigation hierarchy and state management</li>
     *   <li><strong>Customer Access:</strong> Navigation options appropriate for customer workflows and booking management</li>
     *   <li><strong>Visual Integration:</strong> Transparent background for seamless integration with main frame styling</li>
     *   <li><strong>Layout Positioning:</strong> Horizontal span with proper spacing for optimal navigation access</li>
     *   <li><strong>Controller Integration:</strong> Access to system controller for navigation logic and state coordination</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and navigation
     * @param controller the system controller providing access to navigation functionality and customer operations
     */
    private void addNavigatorBarPanel(List<DisposableObject> callingObjects, Controller controller) {

        NavigatorBarPanel navigatorBarPanel = new NavigatorBarPanel(callingObjects, controller);

        navigatorBarPanel.setOpaque(false);

        constraints.setConstraints(0, 1, 2, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 0.0f, new Insets(0, 10, 10, 10));

        mainFrame.add(navigatorBarPanel, constraints.getGridBagConstraints());
        navigatorBarPanel.setVisible(true);
    }

    /**
     * Creates and configures the customer menu panel for specialized customer functions and navigation.
     * <p>
     * This private method establishes the customer menu section of the booking management interface,
     * providing customers with access to customer-specific functions including booking search,
     * flight search, and account management. The menu uses the {@link MenuPanelCustomer} component
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
     *   <li><strong>Visual Integration:</strong> Transparent background for seamless interface integration</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper workflow management
     * @param controller the system controller providing access to customer functions and booking management operations
     */
    private void addMenuPanel(List<DisposableObject> callingObjects, Controller controller) {

        MenuPanelCustomer menu = new MenuPanelCustomer(callingObjects, controller);

        menu.setOpaque(false);

        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.FIRST_LINE_START, 0.0f, 0.0f, new Insets(0, 10, 0, 0));

        mainFrame.add(menu, constraints.getGridBagConstraints());
        menu.setVisible(true);
    }

    /**
     * Creates and configures the user information panel for customer session management and account access.
     * <p>
     * This private method establishes the user information section of the booking management interface,
     * providing current customer session information, account controls, and customer-specific
     * functionality access. The panel uses the {@link UserPanel} component configured for customer
     * workflows and positioned for optimal accessibility and account management.
     * </p>
     * <p>
     * User panel configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Session Management:</strong> Current customer information display and session validation</li>
     *   <li><strong>Account Controls:</strong> Access to customer account management and settings functionality</li>
     *   <li><strong>Navigation Integration:</strong> Integration with calling objects hierarchy for proper resource management</li>
     *   <li><strong>Layout Positioning:</strong> Right-aligned positioning for conventional user interface patterns</li>
     *   <li><strong>Visual Integration:</strong> Transparent background for seamless interface integration</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management
     * @param controller the system controller providing access to customer session management and account functionality
     */
    private void addUserPanel(List<DisposableObject> callingObjects, Controller controller) {

        UserPanel userPanel = new UserPanel(callingObjects, controller);

        userPanel.setOpaque(false);

        constraints.setConstraints(1, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.FIRST_LINE_END, 0.0f, 0.0f, new Insets(0, 0, 0, 10));

        mainFrame.add(userPanel, constraints.getGridBagConstraints());
        userPanel.setVisible(true);
    }

    /**
     * Creates and configures the comprehensive booking search panel for customer booking management operations.
     * <p>
     * This private method establishes the main content area of the booking management interface,
     * providing comprehensive booking search functionality with multiple filter options and
     * result display capabilities. The panel uses the {@link SearchBookingPanel} component
     * configured for customer workflows and menu access integration.
     * </p>
     * <p>
     * Search panel configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Comprehensive Search Functionality:</strong> Advanced booking search with flight-based and passenger-based filtering</li>
     *   <li><strong>Menu Access Integration:</strong> Specialized behavior configuration based on menu-initiated access patterns</li>
     *   <li><strong>Result Display Management:</strong> Comprehensive booking information display with customer-friendly formatting</li>
     *   <li><strong>Navigation Integration:</strong> Integration with calling objects hierarchy for proper workflow management</li>
     *   <li><strong>Layout Optimization:</strong> Full-area expansion with appropriate margins for optimal content presentation</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper workflow management
     * @param controller the system controller providing access to booking search functionality and data management
     * @param ifOpenedFromMenu flag indicating whether the interface was accessed through customer menu for specialized behavior
     */
    private void addSearchPanel(List<DisposableObject> callingObjects, Controller controller, boolean ifOpenedFromMenu) {

        searchBookingPanel = new SearchBookingPanel(callingObjects, controller, ifOpenedFromMenu);

        constraints.setConstraints(0, 3, 2, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.CENTER, 1.0f, 1.0f, new Insets(20, 40, 40, 40));

        mainFrame.add(searchBookingPanel, constraints.getGridBagConstraints());
        searchBookingPanel.setVisible(true);
    }

    /**
     * Performs cleanup operations when the booking management interface is disposed of.
     * <p>
     * This method implements the disposal pattern by clearing search booking result caches
     * and performing resource cleanup to ensure optimal system performance and customer
     * privacy protection. The cleanup prevents memory leaks and ensures that customer
     * search data is properly removed when the interface is closed or replaced.
     * </p>
     * <p>
     * Disposal operations include:
     * </p>
     * <ul>
     *   <li><strong>Search Cache Cleanup:</strong> Clearing booking search result caches to free memory and protect customer data</li>
     *   <li><strong>Resource Management:</strong> Proper cleanup of interface resources and component references</li>
     *   <li><strong>Privacy Protection:</strong> Ensuring customer search data is removed from system memory</li>
     *   <li><strong>Performance Optimization:</strong> Preventing memory leaks during interface lifecycle management</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for context management
     * @param controller the system controller providing access to cache management and cleanup functionality
     */
    @Override
    public void doOnDispose (List<DisposableObject> callingObjects, Controller controller) {
        controller.clearSearchBookingResultCache();
    }

    /**
     * Restores interface state and search functionality when returning to the booking management interface.
     * <p>
     * This method implements sophisticated state restoration by checking for previous search
     * operations and automatically restoring search results and filter configurations when
     * customers return to the booking management interface from other application areas.
     * The restoration ensures continuity of customer workflow and maintains search context
     * across navigation operations.
     * </p>
     * <p>
     * State restoration includes:
     * </p>
     * <ul>
     *   <li><strong>Search State Detection:</strong> Checking for previously performed search operations and active filters</li>
     *   <li><strong>Filter-Based Restoration:</strong> Automatic re-execution of search operations based on active filter types</li>
     *   <li><strong>Flight Search Restoration:</strong> Re-executing flight-based booking searches with preserved parameters</li>
     *   <li><strong>Passenger Search Restoration:</strong> Re-executing passenger-based booking searches with preserved criteria</li>
     *   <li><strong>Interface Refresh:</strong> Updating interface components to reflect restored search results and state</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for context management
     * @param controller the system controller providing access to search functionality and state management
     */
    @Override
    public void doOnRestore (List<DisposableObject> callingObjects, Controller controller) {

        if(searchBookingPanel.isSearchPerformed()){

            controller.clearSearchBookingResultCache();

            if ( searchBookingPanel.getActiveFilter().equals("FLIGHT")) {

                searchBookingPanel.filteredFlightSearch(callingObjects, controller, searchBookingPanel.getSearchButton());

            } else if (searchBookingPanel.getActiveFilter().equals("PASSENGER")) {

                searchBookingPanel.filteredPassengerSearch(callingObjects, controller, searchBookingPanel.getSearchButton());

            }else{

                new MyBookingsCustomerMainFrame(callingObjects, controller, callingObjects.getLast().getFrame().getSize(),
                        callingObjects.getLast().getFrame().getLocation(), callingObjects.getLast().getFrame().getExtendedState(), true);


                callingObjects.remove(callingObjects.size() - 2);
                mainFrame.dispose();

            }
            searchBookingPanel.revalidate();
            searchBookingPanel.repaint();
        }

    }

    /**
     * Provides access to the main application frame for external window management and integration.
     *
     * @return the main JFrame instance containing the customer booking management interface
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