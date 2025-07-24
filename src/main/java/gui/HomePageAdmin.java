package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Administrative home page interface for airport management system operations and flight oversight.
 * <p>
 * This class extends {@link DisposableObject}.
 * </p>
 * <p>
 * The HomePageAdmin class supports comprehensive administrative functionality including:
 * </p>
 * <ul>
 *   <li><strong>Flight Search Integration:</strong> Advanced flight search capabilities through dedicated search panel</li>
 *   <li><strong>Navigation Management:</strong> Centralized navigation controls for accessing different system areas</li>
 *   <li><strong>User Session Management:</strong> Current user information display and account controls</li>
 *   <li><strong>Administrative Menu Access:</strong> Specialized administrative menu with system management options</li>
 *   <li><strong>System Branding:</strong> Consistent airport branding and visual identity throughout the interface</li>
 *   <li><strong>State Management:</strong> Advanced state restoration and disposal for navigation efficiency</li>
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
 * @see MenuPanelAdmin
 * @see UserPanel
 * @see TitlePanel
 * @see Constraints
 */
public class HomePageAdmin extends DisposableObject {

    /**
     * Main application window frame for the administrative home interface.
     * <p>
     * This JFrame serves as the primary container for all administrative home
     * components.
     * </p>
     */
    private JFrame mainFrame;
    
    /**
     * User information panel displaying current administrative user details.
     * <p>
     * This panel provides current user session information, account controls,
     * and user-specific functionality access.
     * </p>
     */
    private UserPanel userPanel;
    
    /**
     * Flight search panel providing flight search capabilities.
     * <p>
     * This panel enables administrators to search for flights, view search results,
     * and transition to detailed flight management interfaces.
     * </p>
     */
    private SearchFlightPanel searchFlightPanel;
    
    /**
     * Layout constraints utility for precise component positioning and sizing.
     */
    private final Constraints constraints;

    /**
     * Constructs a new HomePageAdmin interface for administrative system access and flight management.
     * <p>
     * This constructor initializes the complete administrative home interface by establishing
     * the main application window and configuring all essential administrative components,
     * including navigation, search functionality, user management, and system branding.
     * The constructor creates a fully functional administrative dashboard ready for immediate use.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li><strong>Component Infrastructure:</strong> Initializing layout constraints and fundamental interface components</li>
     *   <li><strong>Window Configuration:</strong> Establishing the main application frame with proper sizing and display properties</li>
     *   <li><strong>Interface Assembly:</strong> Adding and configuring all administrative interface components in proper order</li>
     *   <li><strong>Navigation Integration:</strong> Integrating the interface with the application navigation hierarchy</li>
     *   <li><strong>Visibility Activation:</strong> Making the complete interface visible and ready for administrative interaction</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and navigation
     * @param controller the system controller providing access to business logic, data management, and operational capabilities
     */
    public HomePageAdmin(List<DisposableObject> callingObjects, Controller controller) {

        super();
        constraints = new Constraints();
        this.setMainFrame(callingObjects);

        this.addTitlePanel();
        this.addNavigatorBarPanel(callingObjects, controller);
        this.addMenuPanel(callingObjects, controller);
        this.addUserPanel(callingObjects, controller);
        this.addSearchPanel(callingObjects, controller);

        mainFrame.setVisible(true);
    }

    /**
     * Initializes and configures the main application window frame with administrative interface properties.
     * <p>
     * This method establishes the primary window container for the administrative home interface,
     * configuring window properties optimized for administrative workflows including sizing,
     * positioning, display state, and visual styling. The method also integrates the window
     * with the application navigation hierarchy for proper resource management.
     * </p>
     * <p>
     * Main frame configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Window Properties:</strong> Title, size, and display state configuration for optimal administrative interface</li>
     *   <li><strong>Navigation Integration:</strong> Registration with calling objects hierarchy for proper resource management</li>
     *   <li><strong>Layout Management:</strong> GridBagLayout configuration for precise component positioning</li>
     *   <li><strong>Size Constraints:</strong> Minimum size requirements ensuring interface functionality across display configurations</li>
     *   <li><strong>Visual Styling:</strong> Background color and aesthetic properties for professional administrative appearance</li>
     *   <li><strong>Application Lifecycle:</strong> Proper exit behavior and window management configuration</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper integration and resource management
     */
    private void setMainFrame(List<DisposableObject> callingObjects) {

        mainFrame = new JFrame("Home");
        mainFrame.setSize(1080, 720);
        mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        callingObjects.addLast(this);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridBagLayout());

        mainFrame.setMinimumSize(new Dimension(1420, 1080));

        mainFrame.getContentPane().setBackground(new Color(240, 242, 245));

    }

    /**
     * Creates and configures the title panel with airport branding for administrative interface identification.
     * <p>
     * This method establishes the title section of the administrative home interface, providing
     * consistent airport branding and system identification. The title panel uses the standardized
     * {@link TitlePanel} component configured with airport-specific branding and positioned
     * appropriately within the interface layout hierarchy.
     * </p>
     * <p>
     * Title panel configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Branding Display:</strong> "AEROPORTO DI NAPOLI" title for clear system identification</li>
     *   <li><strong>Visual Integration:</strong> Transparent background for seamless interface integration</li>
     *   <li><strong>Layout Positioning:</strong> Horizontal span across interface top for prominent display</li>
     *   <li><strong>Spacing Configuration:</strong> Appropriate margins and insets for visual balance</li>
     *   <li><strong>Immediate Visibility:</strong> Panel becomes visible immediately upon creation</li>
     * </ul>
     */
    private void addTitlePanel() {

        TitlePanel titlePanel = new TitlePanel("AEROPORTO DI NAPOLI");

        titlePanel.setOpaque(false);

        constraints.setConstraints(0, 0, 2, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 0.0f, new Insets(5, 10, 0, 10));

        mainFrame.add(titlePanel, constraints.getGridBagConstraints());
        titlePanel.setVisible(true);
    }

    /**
     * Creates and configures the navigation bar panel for system-wide navigation and interface access.
     * <p>
     * This method establishes the navigation section of the administrative home interface,
     * providing administrators with access to different areas of the airport management system.
     * The navigation bar uses the standardized {@link NavigatorBarPanel} component configured
     * for administrative workflows and integrated with the application navigation hierarchy.
     * </p>
     * <p>
     * Navigation bar configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Navigation Integration:</strong> Full integration with application navigation hierarchy and state management</li>
     *   <li><strong>Administrative Access:</strong> Navigation options appropriate for administrative workflows and permissions</li>
     *   <li><strong>Visual Integration:</strong> Transparent background for seamless interface integration</li>
     *   <li><strong>Layout Positioning:</strong> Horizontal span with proper spacing for optimal navigation access</li>
     *   <li><strong>Controller Integration:</strong> Access to system controller for navigation logic and state management</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper navigation state management
     * @param controller the system controller providing access to navigation logic and application state
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
     * Creates and configures the administrative menu panel for specialized administrative functions.
     * <p>
     * This method establishes the administrative menu section of the home interface, providing
     * administrators with access to specialized system management functions, operational tools,
     * and advanced configuration options. The menu uses the {@link MenuPanelAdmin} component
     * configured specifically for administrative workflows and positioned for optimal access.
     * </p>
     * <p>
     * Administrative menu configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Administrative Functions:</strong> Access to specialized administrative tools and system management capabilities</li>
     *   <li><strong>Operational Integration:</strong> Integration with calling objects hierarchy for proper workflow management</li>
     *   <li><strong>Controller Access:</strong> Full access to system controller for administrative operations and data management</li>
     *   <li><strong>Visual Integration:</strong> Transparent background for seamless interface integration</li>
     *   <li><strong>Layout Positioning:</strong> Left-aligned positioning for intuitive administrative access patterns</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper workflow integration
     * @param controller the system controller providing access to administrative functions and system management capabilities
     */
    private void addMenuPanel(List<DisposableObject> callingObjects, Controller controller) {

        MenuPanelAdmin menu = new MenuPanelAdmin(callingObjects, controller);

        menu.setOpaque(false);

        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.FIRST_LINE_START, 0.0f, 0.0f, new Insets(0, 10, 0, 0));

        mainFrame.add(menu, constraints.getGridBagConstraints());
        menu.setVisible(true);
    }

    /**
     * Creates and configures the user information panel for current administrative user display and management.
     * <p>
     * This method establishes the user information section of the administrative home interface,
     * providing display of current user session information, account controls, and user-specific
     * functionality access. The user panel integrates with user session management systems
     * for automatic updates and validation throughout administrative sessions.
     * </p>
     * <p>
     * User panel configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Session Management:</strong> Display of current administrative user information and session status</li>
     *   <li><strong>Account Controls:</strong> Access to user account management and session controls</li>
     *   <li><strong>Navigation Integration:</strong> Integration with calling objects hierarchy for proper user workflow management</li>
     *   <li><strong>Controller Access:</strong> Access to user management functionality through system controller</li>
     *   <li><strong>Visual Integration:</strong> Transparent background for seamless interface integration</li>
     *   <li><strong>Layout Positioning:</strong> Right-aligned positioning following conventional user panel placement patterns</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper user workflow integration
     * @param controller the system controller providing access to user management and session functionality
     */
    private void addUserPanel(List<DisposableObject> callingObjects, Controller controller) {

        userPanel = new UserPanel(callingObjects, controller);

        userPanel.setOpaque(false);

        constraints.setConstraints(1, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.FIRST_LINE_END, 0.0f, 0.0f, new Insets(0, 0, 0, 10));

        mainFrame.add(userPanel, constraints.getGridBagConstraints());
        userPanel.setVisible(true);
    }

    /**
     * Creates and configures the flight search panel for comprehensive flight search and management capabilities.
     * <p>
     * This method establishes the main content area of the administrative home interface,
     * providing administrators with powerful flight search capabilities, result display,
     * and direct transition to detailed flight management interfaces. The search panel
     * serves as the primary operational tool for flight-related administrative tasks.
     * </p>
     * <p>
     * Flight search panel configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Search Capabilities:</strong> Comprehensive flight search functionality with advanced filtering and criteria options</li>
     *   <li><strong>Result Management:</strong> Search result display, caching, and state persistence for operational efficiency</li>
     *   <li><strong>Navigation Integration:</strong> Seamless transition to detailed flight management interfaces from search results</li>
     *   <li><strong>Controller Access:</strong> Full access to flight management and search functionality through system controller</li>
     *   <li><strong>Layout Optimization:</strong> Full content area utilization with both horizontal and vertical expansion</li>
     *   <li><strong>State Persistence:</strong> Search state maintenance for restoration and navigation operations</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper search workflow integration
     * @param controller the system controller providing access to flight search and management functionality
     */
    private void addSearchPanel(List<DisposableObject> callingObjects, Controller controller) {

        searchFlightPanel = new SearchFlightPanel(callingObjects, controller);

        constraints.setConstraints(0, 3, 2, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.CENTER, 1.0f, 1.0f, new Insets(20, 40, 40, 40));

        mainFrame.add(searchFlightPanel, constraints.getGridBagConstraints());
        searchFlightPanel.setVisible(true);
    }

    /**
     * Performs resource cleanup and cache management during interface disposal operations.
     * <p>
     * This method implements specialized disposal functionality for the administrative home
     * interface by clearing search result caches and performing necessary cleanup operations
     * to ensure optimal system performance and prevent resource leaks during navigation
     * transitions or application termination.
     * </p>
     * <p>
     * The disposal operations include:
     * </p>
     * <ul>
     *   <li><strong>Search Cache Cleanup:</strong> Clearing flight search result caches to free memory and ensure data currency</li>
     *   <li><strong>Resource Management:</strong> Proper cleanup of cached data and temporary resources</li>
     *   <li><strong>Performance Optimization:</strong> Memory management to maintain system performance during extended operations</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper disposal coordination
     * @param controller the system controller providing access to cache management and resource cleanup functionality
     */
    @Override
    public void doOnDispose (List<DisposableObject> callingObjects, Controller controller) {
        controller.clearSearchFlightsResultCache();
    }

    /**
     * Performs state restoration and interface refresh operations when returning to the administrative home interface.
     * <p>
     * This method implements sophisticated state restoration functionality that ensures the
     * administrative home interface displays current and accurate information when returning
     * from navigation operations. The restoration process includes user session validation,
     * search state management, and interface component refresh for optimal administrative
     * workflow continuity.
     * </p>
     * <p>
     * The restoration operations include:
     * </p>
     * <ul>
     *   <li><strong>User Session Validation:</strong> Verification and refresh of current user information display</li>
     *   <li><strong>User Panel Refresh:</strong> Dynamic user panel replacement when user session changes are detected</li>
     *   <li><strong>Search State Restoration:</strong> Re-execution of previous searches to maintain operational context</li>
     *   <li><strong>Interface Synchronization:</strong> Component refresh and revalidation for current data display</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper restoration coordination
     * @param controller the system controller providing access to current system state and user session information
     */
    @Override
    public void doOnRestore (List<DisposableObject> callingObjects, Controller controller) {
        if(!userPanel.getUserGreeted().equals(controller.getUserController().getUsername())){
            userPanel.setVisible(false);
            mainFrame.remove(userPanel);
            addUserPanel(callingObjects, controller);
        }
        if(searchFlightPanel.isSearchPerformed()){

            searchFlightPanel.executeResearch(callingObjects, controller, searchFlightPanel.getSearchButton());

            searchFlightPanel.repaint();
            searchFlightPanel.revalidate();

        }

    }

    /**
     * Provides access to the main application window frame for navigation and resource management.
     *
     * @return the main JFrame instance containing the administrative home interface
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