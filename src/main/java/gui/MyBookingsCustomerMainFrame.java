package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Customer booking management interface providing comprehensive booking search, display, and management capabilities.
 * <p>
 * This class extends {@link DisposableObject} to provide a dedicated customer interface for managing
 * existing flight bookings within the airport management system. The interface offers comprehensive
 * booking search functionality, detailed booking information display, and booking management operations
 * through an intuitive interface designed specifically for customer self-service operations.
 * </p>
 * <p>
 * The MyBookingsCustomerMainFrame class supports comprehensive customer booking operations including:
 * </p>
 * <ul>
 *   <li><strong>Booking Search Functionality:</strong> Advanced search capabilities with multiple filter options for locating specific bookings</li>
 *   <li><strong>Booking Display Management:</strong> Comprehensive display of booking details including passenger information and flight data</li>
 *   <li><strong>Customer Navigation:</strong> Integrated navigation controls for accessing different customer areas and functions</li>
 *   <li><strong>User Session Management:</strong> Current customer information display and account management integration</li>
 *   <li><strong>Menu-Based Access:</strong> Support for both direct navigation and menu-initiated access patterns</li>
 *   <li><strong>State Restoration:</strong> Advanced state management for maintaining search results and interface continuity</li>
 * </ul>
 * <p>
 * The interface is designed with customer workflow optimization, providing users with:
 * </p>
 * <ul>
 *   <li><strong>Comprehensive Search Interface:</strong> Advanced booking search with flight-based and passenger-based filtering options</li>
 *   <li><strong>Intuitive Navigation:</strong> Seamless integration with application navigation hierarchy and customer menu systems</li>
 *   <li><strong>Professional Presentation:</strong> Clean, organized layout with consistent airport management system branding</li>
 *   <li><strong>Responsive Design:</strong> Adaptive interface that maintains functionality across different screen configurations</li>
 *   <li><strong>Context Preservation:</strong> Intelligent state management that preserves search results during navigation operations</li>
 * </ul>
 * <p>
 * Layout architecture utilizes {@link GridBagLayout} for precise component positioning and optimal
 * space utilization. The interface maintains a structured five-section layout with title branding,
 * navigation controls, customer menu access, user information display, and comprehensive search
 * functionality in the main content area.
 * </p>
 * <p>
 * Booking search integration provides sophisticated search capabilities through the {@link SearchBookingPanel}
 * component, enabling customers to locate their bookings using various criteria including flight information
 * and passenger details. The search functionality includes filter persistence and result caching for
 * optimal user experience during navigation operations.
 * </p>
 * <p>
 * Navigation integration includes comprehensive customer navigation controls through the {@link NavigatorBarPanel}
 * and customer-specific menu functionality through the {@link MenuPanelCustomer} component. The navigation
 * supports seamless transitions between different customer areas while maintaining booking search context.
 * </p>
 * <p>
 * User session management displays current customer information through the {@link UserPanel} component,
 * providing account access, session management, and customer-specific functionality. The integration
 * ensures that booking search results are properly associated with the authenticated customer session.
 * </p>
 * <p>
 * State management includes sophisticated restoration capabilities that maintain search results, filter
 * settings, and interface state across navigation operations. The system automatically restores previous
 * search operations and results when customers return to the booking management interface from other areas.
 * </p>
 * <p>
 * The interface supports both direct navigation access and menu-initiated access patterns, providing
 * flexibility for different customer workflows. Menu-initiated access includes special configuration
 * for optimal integration with customer menu navigation patterns.
 * </p>
 * <p>
 * Resource management follows the disposable object pattern with proper cleanup of search result caches
 * and customer session data during navigation transitions. This ensures optimal system performance and
 * maintains customer privacy through proper session management.
 * </p>
 * <p>
 * The class integrates seamlessly with the broader airport management system through the {@link Controller}
 * interface, ensuring proper data access, business logic execution, and system state coordination throughout
 * customer booking management operations.
 * </p>
 * <p>
 * Visual design maintains consistency with the overall airport management system aesthetic through
 * standardized colors, fonts, and component styling. The interface uses professional design elements
 * optimized for customer self-service operations and public interface requirements.
 * </p>
 * <p>
 * Window management includes minimum size constraints (1420x1080) to ensure optimal functionality across
 * different display configurations while maintaining interface usability and component accessibility
 * throughout customer booking management workflows.
 * </p>
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
     * components, configured with Italian localization ("Le mie prenotazioni") and
     * optimized sizing for comprehensive booking search and display operations.
     * </p>
     */
    private JFrame mainFrame;

    /**
     * Booking search panel providing comprehensive search and filtering capabilities.
     * <p>
     * This SearchBookingPanel component enables customers to search for their bookings
     * using various criteria including flight information and passenger details.
     * The panel includes state management for search result persistence and filter
     * configuration throughout navigation operations.
     * </p>
     */
    private SearchBookingPanel searchBookingPanel;
    
    /**
     * Layout constraints utility for precise component positioning throughout the interface.
     * <p>
     * This final Constraints helper object provides standardized GridBagConstraints
     * configuration for optimal component layout and positioning. The constraints
     * ensure consistent spacing, alignment, and visual organization across all
     * interface components and booking management elements.
     * </p>
     */
    private final Constraints constraints;

    /**
     * Constructs a new MyBookingsCustomerMainFrame interface for comprehensive customer booking management.
     * <p>
     * This constructor initializes the complete customer booking management interface by creating
     * the main application window, configuring all interface components, and establishing
     * comprehensive search functionality for customer booking operations. The constructor creates
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
     * <p>
     * Component infrastructure initialization creates the {@link Constraints} utility for consistent
     * layout management and establishes the foundation for component positioning and sizing
     * throughout the customer booking management interface.
     * </p>
     * <p>
     * Window management establishes the main application frame through the setMainFrame method,
     * configuring window properties including size, location, extended state, and integration
     * with the application navigation hierarchy for proper resource management and workflow coordination.
     * </p>
     * <p>
     * Interface assembly includes sequential addition of all customer interface components:
     * title panel for airport branding, navigator bar for system navigation, customer menu
     * for specialized functions, user panel for session management, and search panel for
     * comprehensive booking search and management capabilities.
     * </p>
     * <p>
     * Search integration configures the booking search panel with menu access flag support,
     * enabling different interface behaviors based on access patterns. Menu-initiated access
     * provides optimized integration with customer menu navigation while direct access
     * supports standard application navigation patterns.
     * </p>
     * <p>
     * Navigation setup ensures proper integration with the application navigation hierarchy
     * through the calling objects list, enabling seamless resource management and state
     * coordination throughout customer booking management workflows and navigation operations.
     * </p>
     * <p>
     * Visibility activation completes the initialization by making the complete interface
     * visible and ready for customer interaction, ensuring that all components are properly
     * configured and accessible for immediate booking search and management operations.
     * </p>
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
     * <p>
     * Window identification uses Italian localization to provide clear customer interface
     * identification consistent with customer-facing system interfaces and Italian language
     * support throughout the airport management system.
     * </p>
     * <p>
     * Size and position management applies provided dimensions and screen coordinates to ensure
     * consistent window placement relative to calling interfaces while supporting different
     * screen configurations and customer workflow contexts.
     * </p>
     * <p>
     * State configuration manages window maximization and display state based on customer
     * preferences and calling context, ensuring optimal screen utilization for booking
     * search and management operations across different usage scenarios.
     * </p>
     * <p>
     * Navigation integration registers the interface with the calling objects list, enabling
     * proper resource management, navigation state tracking, and seamless transitions between
     * customer interfaces throughout booking management workflows.
     * </p>
     * <p>
     * Layout management establishes GridBagLayout as the primary layout manager, providing
     * precise control over component positioning and enabling responsive design that adapts
     * to different window sizes while maintaining optimal component organization.
     * </p>
     * <p>
     * Visual styling includes professional background color (240, 242, 245) for consistent
     * airport management system appearance and minimum size constraints (1420x1080) to ensure
     * optimal functionality and component accessibility across different display configurations.
     * </p>
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
     *   <li><strong>Branding Display:</strong> "AEROPORTO DI NAPOLI" title for clear airport system identification</li>
     *   <li><strong>Layout Positioning:</strong> Horizontal span across interface top with appropriate spacing and margins</li>
     *   <li><strong>Visual Integration:</strong> Transparent background for seamless integration with main frame styling</li>
     *   <li><strong>Immediate Visibility:</strong> Panel becomes visible immediately upon creation for customer recognition</li>
     * </ul>
     * <p>
     * Branding display provides clear identification of the airport management system through
     * the standardized "AEROPORTO DI NAPOLI" title, ensuring consistent system identification
     * across all customer interfaces and maintaining visual brand consistency throughout
     * customer booking management operations.
     * </p>
     * <p>
     * Layout positioning uses {@link GridBagConstraints} to span the title panel across
     * two columns of the parent grid layout, providing appropriate visual weight and
     * prominence for the airport branding at the top of the customer booking interface.
     * </p>
     * <p>
     * Visual integration includes transparent background configuration to ensure seamless
     * integration with the main frame's professional background styling while maintaining
     * clear title visibility and airport brand recognition throughout customer interactions.
     * </p>
     * <p>
     * The title panel is immediately made visible and properly integrated into the main
     * frame layout structure, ensuring immediate availability and proper display hierarchy
     * within the customer booking management interface organization.
     * </p>
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
     * maintaining booking management context. The navigation bar uses the standardized
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
     * <p>
     * Navigation integration provides customers with access to different system areas
     * through the NavigatorBarPanel component, which manages navigation state, resource
     * cleanup, and transition logic for seamless movement between customer interfaces
     * while maintaining booking management context.
     * </p>
     * <p>
     * Customer access ensures that navigation options are appropriate for customer
     * user permissions and workflows, providing access to relevant system areas while
     * maintaining security and operational integrity during booking management operations.
     * </p>
     * <p>
     * Visual integration includes configuring the navigation panel with transparent background
     * to ensure seamless integration with the main interface design while maintaining
     * navigation control visibility and accessibility throughout customer interactions.
     * </p>
     * <p>
     * Layout positioning spans the panel across two columns with horizontal expansion
     * and appropriate margins (0, 10, 10, 10) to ensure proper visual separation and
     * optimal navigation access within the booking management interface structure.
     * </p>
     * <p>
     * Controller integration provides the navigation panel with access to system functionality,
     * data management capabilities, and business logic necessary for customer navigation
     * operations and booking management coordination throughout application workflows.
     * </p>
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
     * <p>
     * Customer functions include access to comprehensive customer tools through the
     * MenuPanelCustomer component, providing dropdown navigation for flight search,
     * booking management, and other customer-specific operations. The menu integrates
     * with the current booking management context for optimal workflow continuity.
     * </p>
     * <p>
     * Operational integration ensures that customer menu functions are properly integrated
     * with the application navigation hierarchy and resource management systems for
     * seamless workflow transitions and proper state management during customer operations.
     * </p>
     * <p>
     * Controller access provides the customer menu with full access to system functionality,
     * data management capabilities, and business logic necessary for customer operations
     * and booking management coordination throughout application workflows.
     * </p>
     * <p>
     * Layout positioning places the customer menu in the left area of the interface with
     * FIRST_LINE_START alignment, following conventional interface design patterns for
     * menu placement and providing intuitive access for customer workflows.
     * </p>
     * <p>
     * Visual integration includes transparent background configuration to ensure seamless
     * integration with the main interface design while maintaining menu visibility and
     * accessibility throughout customer booking management operations.
     * </p>
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
     * <p>
     * Session management displays current customer information including login status,
     * account details, and session-specific controls. The panel integrates with customer
     * session validation to ensure that booking search results are properly associated
     * with the authenticated customer account.
     * </p>
     * <p>
     * Account controls provide customers with access to account management functionality
     * including profile updates, password changes, and other customer-specific settings
     * directly from the booking management interface for convenient account administration.
     * </p>
     * <p>
     * Navigation integration ensures proper integration with the application navigation
     * hierarchy for seamless resource management and state coordination throughout
     * customer account operations and booking management workflows.
     * </p>
     * <p>
     * Layout positioning places the user panel in the right area of the interface with
     * FIRST_LINE_END alignment, following conventional interface design patterns for
     * user information display and providing intuitive access for account management.
     * </p>
     * <p>
     * Visual integration includes transparent background configuration to ensure seamless
     * integration with the main interface design while maintaining user panel visibility
     * and accessibility throughout customer booking management operations.
     * </p>
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
     * <p>
     * Comprehensive search functionality enables customers to locate their bookings using
     * various criteria including flight information, passenger details, and booking metadata.
     * The search panel includes state management for filter persistence and result caching
     * throughout navigation operations and interface transitions.
     * </p>
     * <p>
     * Menu access integration provides specialized behavior when the interface is accessed
     * through customer menu navigation, enabling optimized integration with customer menu
     * workflows while supporting standard application navigation patterns for direct access.
     * </p>
     * <p>
     * Result display management presents booking search results in customer-friendly format
     * with comprehensive booking details, passenger information, and flight data. The display
     * supports booking selection and detailed view access for complete booking management.
     * </p>
     * <p>
     * Navigation integration ensures proper integration with the application navigation
     * hierarchy for seamless resource management and state coordination throughout
     * customer booking search and management workflows.
     * </p>
     * <p>
     * Layout optimization spans the panel across two columns with both horizontal and
     * vertical expansion, utilizing comprehensive margins (20, 40, 40, 40) to ensure
     * proper visual separation and optimal content presentation within the booking
     * management interface structure.
     * </p>
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
     * Performs cleanup operations when the booking management interface is disposed.
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
     * <p>
     * Search cache cleanup utilizes the controller's clearSearchBookingResultCache method
     * to ensure that all cached booking search results are properly removed from system
     * memory, protecting customer privacy and preventing data persistence beyond the
     * interface lifecycle.
     * </p>
     * <p>
     * The disposal operation integrates with the broader application resource management
     * system to ensure comprehensive cleanup during navigation transitions and interface
     * replacement operations throughout customer workflow management.
     * </p>
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
     * <p>
     * Search state detection examines the search booking panel to determine if previous
     * search operations were performed, enabling intelligent restoration only when
     * customers have active search contexts that should be preserved during navigation.
     * </p>
     * <p>
     * Filter-based restoration automatically determines the appropriate restoration method
     * based on the active filter type, supporting both "FLIGHT" and "PASSENGER" filter
     * categories with their respective search parameter preservation and re-execution.
     * </p>
     * <p>
     * Flight search restoration re-executes flight-based booking searches using preserved
     * search parameters and filter configurations, ensuring that customers return to their
     * previous flight-based search results without requiring manual re-entry of search criteria.
     * </p>
     * <p>
     * Passenger search restoration re-executes passenger-based booking searches with
     * preserved criteria and configurations, maintaining customer workflow continuity
     * for passenger-specific booking location and management operations.
     * </p>
     * <p>
     * Interface refresh includes component repainting and revalidation to ensure that
     * restored search results are properly displayed and interface components are
     * updated to reflect the current search state and result availability.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for context management
     * @param controller the system controller providing access to search functionality and state management
     */
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

    /**
     * Provides access to the main application frame for external window management and integration.
     * <p>
     * This method returns the primary JFrame instance that contains the customer booking
     * management interface, enabling external components to access window properties,
     * manage window state, and integrate the interface with broader application window
     * management systems and navigation hierarchies.
     * </p>
     * <p>
     * Frame access enables:
     * </p>
     * <ul>
     *   <li><strong>Window Management:</strong> External control over window visibility, positioning, and state</li>
     *   <li><strong>Navigation Integration:</strong> Integration with application navigation systems and calling object hierarchies</li>
     *   <li><strong>Resource Coordination:</strong> Access for resource management and cleanup operations</li>
     *   <li><strong>State Management:</strong> Window state preservation and restoration during navigation operations</li>
     * </ul>
     * <p>
     * The returned frame reference provides access to the complete booking management
     * interface window, enabling external systems to manage window lifecycle, coordinate
     * navigation operations, and maintain proper resource management throughout
     * customer booking management workflows.
     * </p>
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