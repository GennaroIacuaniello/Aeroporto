package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Administrative home page interface for airport management system operations and flight oversight.
 * <p>
 * This class extends {@link DisposableObject} to provide the primary administrative dashboard
 * for airport management operations. It serves as the central hub for administrative staff
 * to access flight search capabilities, system navigation, user management, and operational
 * controls within a unified and intuitive interface designed for administrative workflows.
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
 * The interface is designed with administrative workflow optimization, providing operators with:
 * </p>
 * <ul>
 *   <li><strong>Centralized Access:</strong> Single point of entry for all administrative operations</li>
 *   <li><strong>Flight Management:</strong> Direct access to flight search and booking management through integrated search functionality</li>
 *   <li><strong>System Navigation:</strong> Seamless navigation to specialized administrative interfaces</li>
 *   <li><strong>User Management:</strong> Current user session monitoring and account management capabilities</li>
 *   <li><strong>Operational Tools:</strong> Access to specialized administrative functions through dedicated menu systems</li>
 * </ul>
 * <p>
 * Layout architecture utilizes {@link GridBagLayout} for precise component positioning and
 * responsive design that adapts to different screen sizes while maintaining optimal usability.
 * The interface is structured with a header section containing branding and navigation,
 * middle section with user and menu controls, and main content area with flight search functionality.
 * </p>
 * <p>
 * Search functionality integration provides administrators with powerful flight search capabilities
 * directly from the home interface. The search panel includes result caching, state persistence,
 * and direct transition to detailed flight management interfaces for operational efficiency.
 * </p>
 * <p>
 * Navigation integration includes comprehensive navigation controls that enable smooth transitions
 * between different administrative areas while maintaining proper state management and resource
 * cleanup throughout navigation operations.
 * </p>
 * <p>
 * User management functionality displays current user information and provides access to
 * user-specific controls and settings. The system includes automatic user session validation
 * and display updates to ensure accurate user information throughout administrative sessions.
 * </p>
 * <p>
 * Administrative menu integration provides access to specialized administrative functions
 * including system management, operational tools, and advanced configuration options through
 * the {@link MenuPanelAdmin} component.
 * </p>
 * <p>
 * Visual design maintains consistency with the overall airport management system aesthetic
 * through proper color schemes, layout patterns, and component styling. The interface uses
 * a clean, professional design optimized for administrative workflows and extended usage.
 * </p>
 * <p>
 * State management includes sophisticated restoration capabilities that maintain user context
 * and search state across navigation operations. The system automatically refreshes user
 * information and search results to ensure data currency and operational accuracy.
 * </p>
 * <p>
 * Resource management follows the disposable object pattern with proper cleanup of search
 * result caches and related resources during navigation transitions. This ensures optimal
 * system performance and prevents memory leaks during extended administrative sessions.
 * </p>
 * <p>
 * The class integrates seamlessly with the broader airport management system through the
 * {@link Controller} interface, ensuring proper data access, business logic execution,
 * and system state coordination throughout administrative operations.
 * </p>
 * <p>
 * Window management includes full-screen optimization with minimum size constraints to
 * ensure optimal visibility and usability across different display configurations while
 * maintaining interface functionality and administrative workflow efficiency.
 * </p>
 * <p>
 * The home page serves as the foundation for administrative navigation throughout the system,
 * providing consistent access patterns and interface behavior that support efficient
 * administrative operations and user experience continuity.
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
     * components, configured with full-screen optimization, minimum size constraints,
     * and proper background styling for optimal administrative workflow support.
     * </p>
     */
    private JFrame mainFrame;
    
    /**
     * User information panel displaying current administrative user details.
     * <p>
     * This panel provides current user session information, account controls,
     * and user-specific functionality access. The panel integrates with user
     * session management for automatic updates and validation.
     * </p>
     */
    private UserPanel userPanel;
    
    /**
     * Flight search panel providing comprehensive flight search capabilities.
     * <p>
     * This panel enables administrators to search for flights, view search results,
     * and transition to detailed flight management interfaces. The panel includes
     * result caching and state persistence for operational efficiency.
     * </p>
     */
    private SearchFlightPanel searchFlightPanel;
    
    /**
     * Layout constraints utility for precise component positioning and sizing.
     * <p>
     * This utility provides consistent layout management throughout the home
     * interface, ensuring proper component placement, spacing, and responsive
     * behavior across different display configurations.
     * </p>
     */
    private final Constraints constraints;

    /**
     * Constructs a new HomePageAdmin interface for administrative system access and flight management.
     * <p>
     * This constructor initializes the complete administrative home interface by establishing
     * the main application window and configuring all essential administrative components
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
     * <p>
     * Component infrastructure initialization includes creating the {@link Constraints} utility
     * for consistent layout management and establishing the foundation for component positioning
     * and sizing throughout the administrative interface.
     * </p>
     * <p>
     * Window configuration establishes the main application frame through the setMainFrame method,
     * configuring window properties including size, position, display state, and integration
     * with the application navigation hierarchy for proper resource management.
     * </p>
     * <p>
     * Interface assembly proceeds through systematic addition of interface components in the
     * optimal order for visual hierarchy and functional dependencies. Components are added
     * from top to bottom including title panel, navigation controls, user and menu panels,
     * and the main search functionality panel.
     * </p>
     * <p>
     * Navigation integration ensures that the home interface is properly registered within
     * the application's navigation hierarchy, enabling seamless transitions to and from
     * other administrative interfaces while maintaining proper state management.
     * </p>
     * <p>
     * The constructor completes by making the complete interface visible, ensuring that
     * administrators have immediate access to all home interface functionality without
     * requiring additional initialization or configuration steps.
     * </p>
     * <p>
     * All component initialization leverages the provided controller for data access and
     * business logic integration, ensuring that interface components have proper access
     * to system functionality and current operational state.
     * </p>
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
     * <p>
     * Window properties include setting the window title to "Home" for clear identification,
     * configuring initial size (1080x720) with immediate maximization for optimal screen
     * utilization, and establishing proper window close behavior for application lifecycle management.
     * </p>
     * <p>
     * Navigation integration includes adding the home interface to the calling objects list,
     * enabling proper resource management and navigation state tracking throughout the
     * application's operational lifecycle.
     * </p>
     * <p>
     * Layout management configuration establishes {@link GridBagLayout} as the primary
     * layout manager, providing precise control over component positioning and sizing
     * necessary for complex administrative interface organization.
     * </p>
     * <p>
     * Size constraints include setting minimum window dimensions (1420x1080) to ensure
     * that all administrative interface components remain functional and accessible
     * across different display configurations and resolutions.
     * </p>
     * <p>
     * Visual styling includes configuring the background color (240, 242, 245) to provide
     * a professional, clean appearance optimized for extended administrative use while
     * maintaining visual consistency with the overall airport management system design.
     * </p>
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
     * <p>
     * Branding display provides clear identification of the airport management system through
     * the standardized "AEROPORTO DI NAPOLI" title, ensuring consistent system identification
     * across all administrative interfaces and maintaining visual brand consistency.
     * </p>
     * <p>
     * Visual integration includes configuring the title panel with transparent background
     * (setOpaque(false)) to ensure seamless integration with the main frame background
     * while maintaining title visibility and professional appearance.
     * </p>
     * <p>
     * Layout positioning uses {@link GridBagConstraints} to span the title panel across
     * two columns of the parent grid layout, providing appropriate visual weight and
     * prominence for the airport branding at the top of the administrative interface.
     * </p>
     * <p>
     * Spacing configuration includes proper margins (5, 10, 0, 10) to ensure appropriate
     * visual separation from interface edges while maintaining balanced spacing throughout
     * the header area of the administrative interface.
     * </p>
     * <p>
     * The title panel is immediately made visible and properly integrated into the main
     * frame layout structure, ensuring immediate availability and proper display hierarchy
     * within the administrative interface organization.
     * </p>
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
     * <p>
     * Navigation integration provides administrators with access to different system areas
     * through the NavigatorBarPanel component, which manages navigation state, resource
     * cleanup, and transition logic for seamless movement between administrative interfaces.
     * </p>
     * <p>
     * Administrative access ensures that navigation options are appropriate for administrative
     * user permissions and workflows, providing access to relevant system areas while
     * maintaining security and operational integrity.
     * </p>
     * <p>
     * Visual integration includes configuring the navigation panel with transparent background
     * to ensure seamless integration with the main interface design while maintaining
     * navigation control visibility and accessibility.
     * </p>
     * <p>
     * Layout positioning spans the navigation bar across two columns with horizontal
     * expansion capability, ensuring that navigation controls utilize available screen
     * width while maintaining proper visual proportions and spacing.
     * </p>
     * <p>
     * Controller integration provides the navigation bar with access to system functionality,
     * user permissions, and application state necessary for proper navigation logic and
     * administrative workflow support.
     * </p>
     * <p>
     * Spacing configuration includes appropriate margins (0, 10, 10, 10) to ensure proper
     * visual separation from other interface components while maintaining balanced layout
     * throughout the administrative interface header area.
     * </p>
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
     * <p>
     * Administrative functions include access to specialized tools such as system management,
     * operational monitoring, configuration options, and other advanced administrative
     * capabilities through the MenuPanelAdmin component.
     * </p>
     * <p>
     * Operational integration ensures that administrative menu functions are properly
     * integrated with the application navigation hierarchy and resource management
     * systems for seamless workflow transitions and proper state management.
     * </p>
     * <p>
     * Controller access provides the administrative menu with full access to system
     * functionality, data management capabilities, and business logic necessary for
     * administrative operations and system management tasks.
     * </p>
     * <p>
     * Visual integration includes transparent background configuration to ensure
     * seamless integration with the main interface design while maintaining menu
     * visibility and accessibility for administrative operations.
     * </p>
     * <p>
     * Layout positioning places the administrative menu in the left area of the
     * interface with FIRST_LINE_START alignment, following conventional interface
     * design patterns for menu placement and providing intuitive access for
     * administrative workflows.
     * </p>
     * <p>
     * Spacing configuration includes left margin (10) to ensure proper visual
     * separation from interface edges while maintaining balanced layout throughout
     * the administrative interface middle section.
     * </p>
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
     * <p>
     * Session management provides real-time display of current administrative user information
     * including user identification, session status, and relevant user-specific data necessary
     * for administrative workflow identification and session tracking.
     * </p>
     * <p>
     * Account controls enable administrators to access user account management functionality,
     * session controls, and user-specific settings through integrated user panel controls
     * that maintain consistency with overall administrative interface design.
     * </p>
     * <p>
     * Navigation integration ensures that user panel operations are properly coordinated
     * with the application navigation hierarchy, enabling seamless user management
     * operations while maintaining proper resource management and workflow continuity.
     * </p>
     * <p>
     * Controller access provides the user panel with necessary access to user management
     * functionality, authentication systems, and user data management capabilities
     * through the integrated system controller.
     * </p>
     * <p>
     * Visual integration includes transparent background configuration to ensure seamless
     * integration with the main interface design while maintaining user panel visibility
     * and accessibility for administrative operations.
     * </p>
     * <p>
     * Layout positioning places the user panel in the right area of the interface with
     * FIRST_LINE_END alignment, following conventional interface design patterns for
     * user information display and providing intuitive access for user-related functions.
     * </p>
     * <p>
     * The user panel reference is maintained for state management and restoration
     * operations, enabling dynamic user information updates and session validation
     * throughout administrative interface usage.
     * </p>
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
     * <p>
     * Search capabilities provide administrators with comprehensive flight search functionality
     * including various search criteria, filtering options, and result sorting capabilities
     * necessary for efficient flight management and operational oversight.
     * </p>
     * <p>
     * Result management includes search result display, caching mechanisms, and state
     * persistence that enable efficient administrative workflows by maintaining search
     * context across navigation operations and interface transitions.
     * </p>
     * <p>
     * Navigation integration enables seamless transitions from search results to detailed
     * flight management interfaces, providing administrators with direct access to
     * comprehensive flight operations and passenger management capabilities.
     * </p>
     * <p>
     * Controller access provides the search panel with full access to flight management
     * systems, search functionality, and data management capabilities necessary for
     * comprehensive administrative flight operations.
     * </p>
     * <p>
     * Layout optimization spans the search panel across two columns with both horizontal
     * and vertical expansion (BOTH fill, 1.0f weights), ensuring that the search panel
     * utilizes the full available content area for optimal search interface presentation.
     * </p>
     * <p>
     * State persistence maintains search panel reference for restoration operations,
     * enabling dynamic search state updates and result persistence throughout
     * administrative interface usage and navigation operations.
     * </p>
     * <p>
     * Spacing configuration includes comprehensive margins (20, 40, 40, 40) to provide
     * appropriate visual separation from interface edges while ensuring optimal content
     * area utilization for search functionality.
     * </p>
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
     * <p>
     * Search cache cleanup leverages the controller's cache management functionality to
     * clear flight search result caches, ensuring that memory is properly reclaimed and
     * that subsequent search operations begin with current data rather than potentially
     * stale cached results.
     * </p>
     * <p>
     * Resource management includes clearing temporary data, cached search results, and
     * other resources that may have been accumulated during administrative home interface
     * operations, ensuring clean disposal and optimal system resource utilization.
     * </p>
     * <p>
     * Performance optimization ensures that the disposal process contributes to overall
     * system performance by reclaiming memory and resources that are no longer needed,
     * supporting efficient resource management throughout extended administrative sessions.
     * </p>
     * <p>
     * The disposal process is designed to be safe and efficient, ensuring that cleanup
     * operations do not interfere with ongoing system operations while effectively
     * managing resources and maintaining system performance standards.
     * </p>
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
     * <p>
     * User session validation compares the currently displayed user information with the
     * current system user session, detecting changes that may have occurred during navigation
     * operations or administrative workflows. When discrepancies are detected, the user
     * panel is dynamically refreshed to display current information.
     * </p>
     * <p>
     * User panel refresh includes removing the existing user panel from the interface,
     * creating a new user panel with current session information, and integrating the
     * refreshed panel into the interface layout to ensure accurate user information display.
     * </p>
     * <p>
     * Search state restoration examines the search panel state to determine if previous
     * searches were performed and should be re-executed to maintain operational context.
     * When search restoration is required, the system re-executes previous search operations
     * to ensure that search results remain current and relevant.
     * </p>
     * <p>
     * Interface synchronization includes component refresh (repaint) and layout revalidation
     * to ensure that all interface components properly reflect current system state and
     * display updated information accurately for administrative operations.
     * </p>
     * <p>
     * The restoration process is designed to be efficient and non-disruptive, ensuring
     * that interface updates occur smoothly without interfering with administrative
     * workflows or causing performance degradation.
     * </p>
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
     * <p>
     * This method returns the main JFrame instance that contains the administrative home
     * interface, enabling external components to perform window management operations
     * such as sizing, positioning, visibility control, and resource management. The method
     * supports the DisposableObject pattern by providing frame access for navigation operations.
     * </p>
     * <p>
     * Frame access enables:
     * </p>
     * <ul>
     *   <li><strong>Navigation Operations:</strong> Window management during interface transitions</li>
     *   <li><strong>Resource Management:</strong> Frame access for proper disposal and cleanup operations</li>
     *   <li><strong>State Management:</strong> Window state preservation and restoration during navigation</li>
     *   <li><strong>Display Control:</strong> Visibility and positioning management for optimal user experience</li>
     * </ul>
     * <p>
     * The returned frame reference provides access to standard JFrame operations including
     * visibility control, sizing, positioning, and state management that are essential
     * for proper navigation system integration and administrative workflow support.
     * </p>
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