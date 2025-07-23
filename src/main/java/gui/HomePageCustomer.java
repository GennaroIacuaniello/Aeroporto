package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Customer home page interface for airport flight information display and system navigation.
 * <p>
 * This class extends {@link DisposableObject} to provide the primary customer dashboard
 * for the airport management system. It serves as the central hub for customer users
 * to view real-time flight information, access navigation controls, and manage their
 * account within a unified and intuitive interface designed for customer workflows.
 * </p>
 * <p>
 * The HomePageCustomer class supports comprehensive customer functionality including:
 * </p>
 * <ul>
 *   <li><strong>Flight Information Display:</strong> Real-time arriving and departing flight tables with current status</li>
 *   <li><strong>Navigation Management:</strong> Customer navigation controls for accessing different system areas</li>
 *   <li><strong>User Session Management:</strong> Current customer information display and account controls</li>
 *   <li><strong>Customer Menu Access:</strong> Customer-specific menu with booking and search options</li>
 *   <li><strong>System Branding:</strong> Consistent airport branding and visual identity throughout the interface</li>
 *   <li><strong>State Management:</strong> Advanced state restoration and disposal for navigation efficiency</li>
 * </ul>
 * <p>
 * The interface is designed with customer workflow optimization, providing users with:
 * </p>
 * <ul>
 *   <li><strong>Flight Information Access:</strong> Comprehensive display of imminent arriving and departing flights</li>
 *   <li><strong>Navigation Integration:</strong> Seamless navigation to booking, search, and account management interfaces</li>
 *   <li><strong>Real-time Updates:</strong> Current flight status information including delays, cancellations, and gate changes</li>
 *   <li><strong>User Management:</strong> Current customer session monitoring and account management capabilities</li>
 *   <li><strong>Booking Access:</strong> Direct access to booking search and management functions through customer menu</li>
 * </ul>
 * <p>
 * Layout architecture utilizes {@link GridBagLayout} for precise component positioning and
 * responsive design that adapts to different screen sizes while maintaining optimal usability.
 * The interface is structured with a header section containing branding and navigation,
 * middle section with customer menu and user controls, and main content area with flight information tables.
 * </p>
 * <p>
 * Flight information integration provides customers with real-time access to flight data
 * through dedicated arriving and departing flight tables. The tables display comprehensive
 * flight details including airline, routes, schedules, status, and gate information for
 * optimal travel planning and airport navigation.
 * </p>
 * <p>
 * Navigation integration includes comprehensive navigation controls that enable smooth transitions
 * between different customer areas while maintaining proper state management and resource
 * cleanup throughout navigation operations.
 * </p>
 * <p>
 * User management functionality displays current customer information and provides access to
 * customer-specific controls and settings. The system includes automatic customer session validation
 * and display updates to ensure accurate user information throughout customer sessions.
 * </p>
 * <p>
 * Customer menu integration provides access to customer-specific functions including flight
 * search, booking management, and account services through the {@link MenuPanelCustomer} component.
 * </p>
 * <p>
 * Visual design maintains consistency with the overall airport management system aesthetic
 * through proper color schemes, layout patterns, and component styling. The interface uses
 * a clean, professional design optimized for customer workflows and public use.
 * </p>
 * <p>
 * State management includes sophisticated restoration capabilities that maintain customer context
 * and flight information currency across navigation operations. The system automatically refreshes
 * flight tables and customer information to ensure data accuracy and operational relevance.
 * </p>
 * <p>
 * Resource management follows the disposable object pattern with proper cleanup of customer
 * session data and flight information caches during navigation transitions. This ensures optimal
 * system performance and maintains customer privacy through proper session management.
 * </p>
 * <p>
 * The class integrates seamlessly with the broader airport management system through the
 * {@link Controller} interface, ensuring proper data access, business logic execution,
 * and system state coordination throughout customer operations.
 * </p>
 * <p>
 * Window management includes full-screen optimization with responsive layout constraints to
 * ensure optimal visibility and usability across different display configurations while
 * maintaining interface functionality and customer workflow efficiency.
 * </p>
 * <p>
 * The home page serves as the foundation for customer navigation throughout the system,
 * providing consistent access patterns and interface behavior that support efficient
 * customer operations and user experience continuity.
 * </p>
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
     * <p>
     * This JFrame serves as the primary container for all customer home
     * components, configured with full-screen optimization and proper
     * background styling for optimal customer workflow support.
     * </p>
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
     * <p>
     * This panel houses the arriving flights table and associated labels,
     * providing customers with real-time information about incoming flights
     * including schedules, status updates, and gate assignments.
     * </p>
     */
    private JPanel arrivingPanel;
    
    /**
     * Panel container for departing flights information display.
     * <p>
     * This panel houses the departing flights table and associated labels,
     * providing customers with real-time information about outgoing flights
     * including schedules, status updates, and gate assignments.
     * </p>
     */
    private JPanel departingPanel;

    /**
     * Table component displaying imminent arriving flight information.
     * <p>
     * This specialized table provides detailed information about flights
     * arriving at the airport including flight IDs, airlines, routes,
     * arrival times, current status, and assigned gates for customer
     * travel planning and airport navigation.
     * </p>
     */
    private ImminentFlightsTable arrivingTable;
    
    /**
     * Table component displaying imminent departing flight information.
     * <p>
     * This specialized table provides detailed information about flights
     * departing from the airport including flight IDs, airlines, routes,
     * departure times, current status, and assigned gates for customer
     * travel planning and airport navigation.
     * </p>
     */
    private ImminentFlightsTable departingTable;

    /**
     * Layout constraints utility for precise component positioning and sizing.
     * <p>
     * This utility provides consistent layout management throughout the customer
     * home interface, ensuring proper component placement, spacing, and responsive
     * behavior across different display configurations.
     * </p>
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
     * <p>
     * Component infrastructure initialization includes creating the {@link Constraints} utility
     * for consistent layout management and establishing the foundation for component positioning
     * and sizing throughout the customer interface.
     * </p>
     * <p>
     * Window configuration establishes the main application frame through the setMainFrame method,
     * configuring window properties including size, maximization, and integration with the
     * application navigation hierarchy for proper resource management.
     * </p>
     * <p>
     * Interface assembly proceeds through systematic addition of interface components including
     * title panel, navigation controls, customer menu, user panel, and flight information
     * displays in the optimal order for visual hierarchy and functional dependencies.
     * </p>
     * <p>
     * Flight information setup includes initializing both arriving and departing flight tables
     * with current data from the flight controller, ensuring customers have immediate access
     * to relevant flight information upon interface loading.
     * </p>
     * <p>
     * Navigation integration ensures that the customer home interface is properly registered
     * within the application's navigation hierarchy, enabling seamless transitions to and from
     * other customer interfaces while maintaining proper state management.
     * </p>
     * <p>
     * The constructor completes by making the complete interface visible, ensuring that
     * customers have immediate access to all home interface functionality without requiring
     * additional initialization or configuration steps.
     * </p>
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
     * <p>
     * Window properties include setting the window title to "Home" for clear identification,
     * configuring initial size (1080x720) with immediate maximization for optimal screen
     * utilization, and establishing proper window close behavior for application lifecycle management.
     * </p>
     * <p>
     * Navigation integration includes adding the customer home interface to the calling objects list,
     * enabling proper resource management and navigation state tracking throughout the
     * application's operational lifecycle.
     * </p>
     * <p>
     * Layout management configuration establishes {@link GridBagLayout} as the primary
     * layout manager, providing precise control over component positioning and sizing
     * necessary for complex customer interface organization with flight information tables.
     * </p>
     * <p>
     * Display optimization includes immediate window maximization to ensure that flight
     * information tables and customer controls utilize available screen space effectively
     * for optimal customer experience and information accessibility.
     * </p>
     * <p>
     * Visual styling includes configuring the background color (240, 242, 245) to provide
     * a clean, professional appearance optimized for customer use while maintaining visual
     * consistency with the overall airport management system design.
     * </p>
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
     * <p>
     * Branding display provides clear identification of the airport management system through
     * the standardized "AEROPORTO DI NAPOLI" title, ensuring consistent system identification
     * across all customer interfaces and maintaining visual brand consistency.
     * </p>
     * <p>
     * Layout positioning uses {@link GridBagConstraints} to span the title panel across
     * two columns of the parent grid layout, providing appropriate visual weight and
     * prominence for the airport branding at the top of the customer interface.
     * </p>
     * <p>
     * Spacing configuration includes proper margins (5, 10, 0, 10) to ensure appropriate
     * visual separation from interface edges while maintaining balanced spacing throughout
     * the header area of the customer interface.
     * </p>
     * <p>
     * The title panel is immediately made visible and properly integrated into the main
     * frame layout structure, ensuring immediate availability and proper display hierarchy
     * within the customer interface organization.
     * </p>
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
     * <p>
     * Navigation integration provides customers with access to different system areas
     * through the NavigatorBarPanel component, which manages navigation state, resource
     * cleanup, and transition logic for seamless movement between customer interfaces.
     * </p>
     * <p>
     * Customer access ensures that navigation options are appropriate for customer
     * user permissions and workflows, providing access to relevant system areas while
     * maintaining security and operational integrity.
     * </p>
     * <p>
     * Visual integration includes configuring the navigation panel with matching background
     * color (240, 242, 245) to ensure seamless integration with the main interface design
     * while maintaining navigation control visibility and accessibility.
     * </p>
     * <p>
     * Layout positioning spans the navigation bar across two columns with both horizontal
     * and vertical expansion capability, ensuring that navigation controls utilize available
     * screen width while maintaining proper visual proportions and spacing.
     * </p>
     * <p>
     * Controller integration provides the navigation bar with access to system functionality,
     * customer permissions, and application state necessary for proper navigation logic and
     * customer workflow support.
     * </p>
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
     * <p>
     * Customer functions include access to flight search capabilities, booking management
     * interfaces, account services, and other customer-specific tools through the
     * MenuPanelCustomer component that provides dropdown navigation options.
     * </p>
     * <p>
     * Operational integration ensures that customer menu functions are properly integrated
     * with the application navigation hierarchy and resource management systems for
     * seamless workflow transitions and proper state management.
     * </p>
     * <p>
     * Controller access provides the customer menu with full access to system functionality,
     * data management capabilities, and business logic necessary for customer operations
     * and service delivery.
     * </p>
     * <p>
     * Layout positioning places the customer menu in the left area of the interface with
     * FIRST_LINE_START alignment, following conventional interface design patterns for
     * menu placement and providing intuitive access for customer workflows.
     * </p>
     * <p>
     * The menu panel reference is maintained as "hamburgerPanel" in the local variable
     * for immediate configuration and integration into the main frame layout structure.
     * </p>
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
     * <p>
     * Session management provides real-time display of current customer user information
     * including customer identification, session status, and relevant customer-specific data
     * necessary for customer workflow identification and session tracking.
     * </p>
     * <p>
     * Account controls enable customers to access customer account management functionality,
     * session controls, and customer-specific settings through integrated user panel controls
     * that maintain consistency with overall customer interface design.
     * </p>
     * <p>
     * Navigation integration ensures that user panel operations are properly coordinated
     * with the application navigation hierarchy, enabling seamless customer management
     * operations while maintaining proper resource management and workflow continuity.
     * </p>
     * <p>
     * Controller access provides the user panel with necessary access to customer management
     * functionality, authentication systems, and customer data management capabilities
     * through the integrated system controller.
     * </p>
     * <p>
     * Layout positioning places the user panel in the right area of the interface with
     * LINE_END alignment and vertical fill capability, following conventional interface
     * design patterns for user information display and providing intuitive access for
     * customer-related functions.
     * </p>
     * <p>
     * The user panel reference is maintained as an instance variable for state management
     * and restoration operations, enabling dynamic customer information updates and session
     * validation throughout customer interface usage.
     * </p>
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
     * <p>
     * Panel infrastructure includes creating a JPanel with GridBagLayout and matching
     * background color (240, 242, 245) to ensure seamless integration with the main
     * interface design while providing proper container structure for flight information.
     * </p>
     * <p>
     * Flight data integration leverages the setArrivingTable method to populate the panel
     * with current arriving flight information retrieved through the controller's flight
     * management system, ensuring customers have access to real-time flight data.
     * </p>
     * <p>
     * Visual labeling includes creating a prominent "Aerei in arrivo" label with bold
     * typography (18pt font) and appropriate spacing to clearly identify the section
     * and provide intuitive navigation for customers seeking arrival information.
     * </p>
     * <p>
     * Layout positioning spans the panel across two columns with both horizontal and
     * vertical expansion, utilizing comprehensive margins (0, 32, 0, 32) and borders
     * (16, 32, 0, 32) to ensure proper visual separation and optimal content presentation.
     * </p>
     * <p>
     * Accessibility support includes associating the section label with the arriving
     * flights table through the setLabelFor method, improving screen reader compatibility
     * and overall interface accessibility for customers with assistive technology needs.
     * </p>
     * <p>
     * The arriving panel reference is maintained as an instance variable to support
     * state management operations and enable panel refresh during restoration processes.
     * </p>
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
     * <p>
     * Column structure provides customers with comprehensive flight information through
     * seven distinct columns: flight ID for identification, airline company for carrier
     * information, flight date for scheduling, route information for origin/destination,
     * arrival time for precise scheduling, current flight status for operational updates,
     * and gate assignment for airport navigation.
     * </p>
     * <p>
     * Data integration leverages the controller's getFlightController().getImminentArrivingFlights()
     * method to retrieve current arriving flight data, ensuring that customers have access
     * to real-time flight information including status updates, delays, and gate changes.
     * </p>
     * <p>
     * Table implementation uses the specialized {@link ImminentFlightsTable} class that
     * provides optimized display and interaction capabilities for flight data, including
     * proper formatting, sorting capabilities, and customer-friendly data presentation.
     * </p>
     * <p>
     * Layout integration positions the table within the panel using GridBagConstraints
     * with both horizontal and vertical expansion (BOTH fill) and center alignment to
     * ensure optimal table presentation and utilization of available panel space.
     * </p>
     * <p>
     * Scroll support is provided through the table's integrated scroll container,
     * enabling customers to navigate through large numbers of arriving flights while
     * maintaining interface responsiveness and usability.
     * </p>
     * <p>
     * The arriving table reference is maintained as an instance variable to support
     * label association, accessibility requirements, and potential future table operations.
     * </p>
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
     * <p>
     * Panel infrastructure includes creating a JPanel with GridBagLayout and matching
     * background color (240, 242, 245) to ensure seamless integration with the main
     * interface design while providing proper container structure for flight information.
     * </p>
     * <p>
     * Flight data integration leverages the setDepartingTable method to populate the panel
     * with current departing flight information retrieved through the controller's flight
     * management system, ensuring customers have access to real-time departure data.
     * </p>
     * <p>
     * Visual labeling includes creating a prominent "Aerei in Partenza" label with bold
     * typography (18pt font) and appropriate spacing to clearly identify the section
     * and provide intuitive navigation for customers seeking departure information.
     * </p>
     * <p>
     * Layout positioning spans the panel across two columns with both horizontal and
     * vertical expansion, utilizing comprehensive margins (8, 32, 32, 32) and borders
     * (16, 32, 0, 32) to ensure proper visual separation and optimal content presentation.
     * </p>
     * <p>
     * Accessibility support includes associating the section label with the departing
     * flights table through the setLabelFor method, improving screen reader compatibility
     * and overall interface accessibility for customers with assistive technology needs.
     * </p>
     * <p>
     * The departing panel reference is maintained as an instance variable to support
     * state management operations and enable panel refresh during restoration processes.
     * </p>
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
     * <p>
     * Column structure provides customers with comprehensive flight information through
     * seven distinct columns: flight ID for identification, airline company for carrier
     * information, flight date for scheduling, route information for origin/destination,
     * departure time for precise scheduling, current flight status for operational updates,
     * and gate assignment for airport navigation.
     * </p>
     * <p>
     * Data integration leverages the controller's getFlightController().getImminentDepartingFlights()
     * method to retrieve current departing flight data, ensuring that customers have access
     * to real-time flight information including status updates, delays, and gate changes.
     * </p>
     * <p>
     * Table implementation uses the specialized {@link ImminentFlightsTable} class that
     * provides optimized display and interaction capabilities for flight data, including
     * proper formatting, sorting capabilities, and customer-friendly data presentation.
     * </p>
     * <p>
     * Layout integration positions the table within the panel using GridBagConstraints
     * with both horizontal and vertical expansion (BOTH fill) and center alignment to
     * ensure optimal table presentation and utilization of available panel space.
     * </p>
     * <p>
     * Scroll support is provided through the table's integrated scroll container,
     * enabling customers to navigate through large numbers of departing flights while
     * maintaining interface responsiveness and usability.
     * </p>
     * <p>
     * The departing table reference is maintained as an instance variable to support
     * label association, accessibility requirements, and potential future table operations.
     * </p>
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
     * <p>
     * Customer session cleanup leverages the controller's setLoggedCustomer method with null
     * parameters to clear the current customer session, ensuring that customer-specific data
     * and session information are properly removed from system memory for privacy protection.
     * </p>
     * <p>
     * User session termination includes clearing the user controller session through the
     * setLoggedUser method with null parameters, providing complete session cleanup that
     * ensures no customer data remains accessible after interface disposal.
     * </p>
     * <p>
     * Security management ensures that customer sessions are properly terminated during
     * navigation transitions, preventing unauthorized access to customer information and
     * maintaining system security standards throughout application operation.
     * </p>
     * <p>
     * The disposal process is designed to be comprehensive and secure, ensuring that all
     * customer-related session data is properly cleared while maintaining system integrity
     * and preparing for potential new customer sessions.
     * </p>
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
     * <p>
     * Customer session validation compares the currently displayed customer information with
     * the current system customer session, detecting changes that may have occurred during
     * navigation operations or customer workflows. When discrepancies are detected, the user
     * panel is dynamically refreshed to display current information.
     * </p>
     * <p>
     * User panel refresh includes removing the existing user panel from the interface,
     * creating a new user panel with current customer session information, and integrating
     * the refreshed panel into the interface layout to ensure accurate customer information display.
     * </p>
     * <p>
     * Flight information update includes complete removal and recreation of both arriving
     * and departing flight panels to ensure that customers have access to the most current
     * flight information available from the system. This comprehensive refresh prevents
     * the display of stale flight data that could impact customer travel decisions.
     * </p>
     * <p>
     * Interface synchronization includes hiding existing flight panels, removing them from
     * the main frame layout, and recreating them with current data through the addArrivingPanel
     * and addDepartingPanel methods. This ensures complete data currency and interface accuracy.
     * </p>
     * <p>
     * The restoration process is designed to be comprehensive and efficient, ensuring that
     * interface updates provide customers with current information while maintaining
     * interface responsiveness and user experience quality.
     * </p>
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
     * <p>
     * This method returns the main JFrame instance that contains the customer home interface,
     * enabling external components to perform window management operations such as sizing,
     * positioning, visibility control, and resource management. The method supports the
     * DisposableObject pattern by providing frame access for navigation operations.
     * </p>
     * <p>
     * Frame access enables:
     * </p>
     * <ul>
     *   <li><strong>Navigation Operations:</strong> Window management during interface transitions</li>
     *   <li><strong>Resource Management:</strong> Frame access for proper disposal and cleanup operations</li>
     *   <li><strong>State Management:</strong> Window state preservation and restoration during navigation</li>
     *   <li><strong>Display Control:</strong> Visibility and positioning management for optimal customer experience</li>
     * </ul>
     * <p>
     * The returned frame reference provides access to standard JFrame operations including
     * visibility control, sizing, positioning, and state management that are essential
     * for proper navigation system integration and customer workflow support.
     * </p>
     *
     * @return the main JFrame instance containing the customer home interface
     */
    @Override
    public JFrame getFrame() {
        return this.mainFrame;
    }

}