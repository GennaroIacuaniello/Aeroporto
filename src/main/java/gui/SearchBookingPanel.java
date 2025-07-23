package gui;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;
import controller.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Comprehensive booking search panel providing advanced search and filtering capabilities for customer booking management in the airport management system.
 * <p>
 * This class extends {@link JPanel} to provide sophisticated booking search functionality within the
 * {@link MyBookingsCustomerMainFrame} interface. The SearchBookingPanel serves as the primary search
 * and filtering interface for customers to locate their existing bookings using various criteria
 * including flight information and passenger details, with comprehensive result display and
 * management capabilities throughout customer booking workflows.
 * </p>
 * <p>
 * The SearchBookingPanel class provides comprehensive booking search functionality including:
 * </p>
 * <ul>
 *   <li><strong>Dual Search Modes:</strong> Flight-based and passenger-based filtering with dynamic interface switching</li>
 *   <li><strong>Advanced Flight Filtering:</strong> Origin/destination cities, date ranges, and time range filtering capabilities</li>
 *   <li><strong>Comprehensive Passenger Search:</strong> First name, last name, SSN, and ticket number filtering options</li>
 *   <li><strong>Dynamic Result Display:</strong> Real-time search result presentation through integrated SearchBookingResultPanel</li>
 *   <li><strong>Menu Integration:</strong> Specialized behavior for menu-initiated access with automatic data loading</li>
 *   <li><strong>Error Handling:</strong> Comprehensive input validation with user-friendly error messaging through FloatingMessage</li>
 * </ul>
 * <p>
 * The interface is designed with customer workflow optimization, providing users with:
 * </p>
 * <ul>
 *   <li><strong>Intuitive Filter Selection:</strong> Clear flight/passenger filter buttons with dynamic panel switching</li>
 *   <li><strong>Comprehensive Input Fields:</strong> All necessary search criteria with proper validation and formatting</li>
 *   <li><strong>Real-time Search:</strong> Immediate search execution with dynamic result updating and error feedback</li>
 *   <li><strong>Professional Styling:</strong> Consistent visual design with airport management system branding and usability standards</li>
 *   <li><strong>Responsive Layout:</strong> Adaptive interface layout that maintains functionality across different window sizes</li>
 * </ul>
 * <p>
 * Search architecture utilizes a sophisticated dual-mode system that enables customers to search
 * for their bookings using either flight-specific criteria or passenger-specific information.
 * The architecture includes dynamic interface switching, comprehensive input validation, and
 * real-time result presentation through the integrated {@link SearchBookingResultPanel} component.
 * </p>
 * <p>
 * Flight-based search capabilities include origin and destination city filtering, comprehensive
 * date range selection with {@link DatePicker} components, and time range filtering with
 * {@link TimePicker} components. The flight search validates input consistency and provides
 * comprehensive error messaging for invalid search criteria combinations.
 * </p>
 * <p>
 * Passenger-based search functionality enables customers to locate bookings using personal
 * information including first name, last name, passenger SSN (codice fiscale), and ticket
 * number. The passenger search provides flexible filtering options that enable partial
 * information searches for enhanced customer convenience.
 * </p>
 * <p>
 * Result display integration utilizes the {@link SearchBookingResultPanel} component to present
 * search results in a comprehensive, customer-friendly format. The result display includes
 * booking information, flight details, passenger data, and interactive elements for detailed
 * booking management and modification operations.
 * </p>
 * <p>
 * Menu integration provides specialized functionality when the panel is accessed through the
 * customer menu system, automatically loading all customer bookings for immediate display.
 * The menu integration enhances customer experience by providing immediate booking access
 * without requiring manual search operations.
 * </p>
 * <p>
 * Controller integration enables comprehensive search operations through the {@link Controller}
 * system, providing access to booking search functionality, flight data management, and
 * customer-specific booking retrieval. The integration ensures data consistency and proper
 * error handling throughout search operations.
 * </p>
 * <p>
 * Layout management utilizes {@link GridBagLayout} for precise component positioning and
 * optimal space utilization. The layout includes proper component spacing, alignment management,
 * and responsive design principles that maintain functionality across different display
 * configurations and customer usage scenarios.
 * </p>
 * <p>
 * Error handling includes comprehensive input validation with {@link FloatingMessage} integration
 * for user-friendly error presentation. The error handling covers invalid date ranges, incomplete
 * city specifications, incorrect time ranges, and other input validation scenarios with clear
 * Italian-language error messages for customer guidance.
 * </p>
 * <p>
 * Visual design follows airport management system standards with professional styling,
 * consistent color schemes, and optimal typography. The design includes proper border
 * management, background styling, and component appearance configuration that maintains
 * visual consistency throughout the customer booking management interface.
 * </p>
 * <p>
 * Performance optimization includes efficient search operations, optimized result updating,
 * and responsive user interface management. The optimization ensures smooth customer
 * interaction during search operations while maintaining data accuracy and system
 * responsiveness throughout booking management workflows.
 * </p>
 * <p>
 * The class integrates seamlessly with the broader customer booking management ecosystem
 * including {@link MyBookingsCustomerMainFrame}, {@link SearchBookingResultPanel}, and
 * {@link Controller} systems while maintaining clean separation of concerns and reusable
 * component design patterns throughout the airport management system.
 * </p>
 * <p>
 * State management includes comprehensive tracking of active filter modes, search
 * performance status, and result data persistence throughout navigation operations
 * and interface transitions. The state management ensures consistent behavior and
 * data integrity during customer booking search and management activities.
 * </p>
 * <p>
 * The SearchBookingPanel serves as a critical component of the customer booking
 * management system, providing essential search and filtering capabilities that
 * enable efficient booking location and management while maintaining interface
 * consistency, user experience quality, and operational effectiveness throughout
 * customer interactions with the airport management system.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see JPanel
 * @see MyBookingsCustomerMainFrame
 * @see SearchBookingResultPanel
 * @see Controller
 * @see DatePicker
 * @see TimePicker
 * @see FloatingMessage
 */
public class SearchBookingPanel extends JPanel {

    /**
     * Scroll container for search results display with overflow handling and navigation support.
     * <p>
     * This JScrollPane contains the {@link SearchBookingResultPanel} and provides scrolling
     * capability when search results exceed the visible area. The container includes
     * optimized scroll policies and smooth scrolling configuration for enhanced user
     * experience during result browsing and booking selection operations.
     * </p>
     */
    private JScrollPane resultsScrollPane;

    /**
     * Layout constraints utility for precise component positioning throughout the search interface.
     * <p>
     * This final Constraints helper object provides standardized {@link GridBagConstraints}
     * configuration for optimal component layout and positioning. The constraints ensure
     * consistent spacing, alignment, and visual organization throughout all search interface
     * components and dynamic filter panel switching operations.
     * </p>
     */
    private final Constraints constraints;

    /**
     * Current active filter mode identifier for dynamic interface behavior management.
     * <p>
     * This string field tracks the currently selected search mode, containing values of
     * "NONE", "FLIGHT", or "PASSENGER". The field enables proper filter panel visibility
     * management, search operation routing, and user interface state coordination throughout
     * booking search workflows and filter switching operations.
     * </p>
     */
    //Contiene il filtro attuale, può essere: "NONE", "FLIGHT", "PASSENGER"
    private String activeFilter = "NONE";

    /**
     * Origin city input field for flight-based booking search operations.
     * <p>
     * This text field accepts departure city input for flight filtering, enabling customers
     * to search for bookings based on flight origin locations. The field includes proper
     * validation integration and supports partial city name matching for enhanced search
     * flexibility and customer convenience during booking location operations.
     * </p>
     */
    private JTextField fromField;
    
    /**
     * Destination city input field for flight-based booking search operations.
     * <p>
     * This text field accepts arrival city input for flight filtering, enabling customers
     * to search for bookings based on flight destination locations. The field works in
     * conjunction with the origin field to provide comprehensive route-based booking
     * search capabilities with proper validation and error handling.
     * </p>
     */
    private JTextField toField;
    
    /**
     * Start date picker component for flight date range filtering in booking searches.
     * <p>
     * This DatePicker component enables customers to specify the beginning of a date range
     * for flight-based booking searches. The component includes proper date validation,
     * user-friendly date selection interface, and integration with date range validation
     * logic to ensure valid search criteria specification.
     * </p>
     */
    private DatePicker dateFrom;
    
    /**
     * End date picker component for flight date range filtering in booking searches.
     * <p>
     * This DatePicker component enables customers to specify the end of a date range
     * for flight-based booking searches. The component works with dateFrom to provide
     * comprehensive date range filtering with validation to ensure logical date
     * range specification and proper search criteria handling.
     * </p>
     */
    private DatePicker dateTo;
    
    /**
     * Start time picker component for flight time range filtering in booking searches.
     * <p>
     * This TimePicker component enables customers to specify the beginning of a time range
     * for flight-based booking searches. The component provides precise time selection
     * capabilities and integrates with time range validation logic to ensure consistent
     * and valid time range specification for flight filtering operations.
     * </p>
     */
    private TimePicker timeFrom;
    
    /**
     * End time picker component for flight time range filtering in booking searches.
     * <p>
     * This TimePicker component enables customers to specify the end of a time range
     * for flight-based booking searches. The component works with timeFrom to provide
     * comprehensive time range filtering with validation to ensure logical time
     * range specification and proper flight time-based search functionality.
     * </p>
     */
    private TimePicker timeTo;

    /**
     * First name input field for passenger-based booking search operations.
     * <p>
     * This text field accepts passenger first name input for passenger filtering,
     * enabling customers to search for bookings based on passenger personal information.
     * The field supports partial name matching and case-insensitive search operations
     * for enhanced search flexibility and customer convenience during booking location.
     * </p>
     */
    private JTextField firstNameField;

    /**
     * Last name input field for passenger-based booking search operations.
     * <p>
     * This text field accepts passenger last name input for passenger filtering,
     * providing surname-based booking search capabilities. The field integrates with
     * other passenger search criteria to enable comprehensive passenger information
     * matching and flexible booking location based on passenger identification.
     * </p>
     */
    private JTextField lastNameField;

    /**
     * Passenger SSN (Codice Fiscale) input field for precise passenger identification in booking searches.
     * <p>
     * This text field accepts Italian fiscal code (codice fiscale) input for precise
     * passenger identification during booking searches. The field provides exact
     * passenger matching capabilities and integrates with passenger validation
     * systems for accurate booking location and customer service operations.
     * </p>
     */
    private JTextField passengerSSNField;
    
    /**
     * Ticket number input field for direct ticket-based booking identification and search.
     * <p>
     * This text field accepts ticket number input for direct booking location,
     * providing the most precise booking search method available. The field enables
     * immediate booking access through unique ticket identification and supports
     * exact matching for efficient customer service and booking management operations.
     * </p>
     */
    private JTextField ticketNumberField;

    /**
     * Primary search button for executing booking search operations with comprehensive validation and error handling.
     * <p>
     * This JButton initiates booking search operations based on the currently selected
     * filter mode and input criteria. The button includes dynamic state management,
     * comprehensive input validation, and integrates with the FloatingMessage system
     * for user feedback during search operations and error scenarios.
     * </p>
     */
    private JButton searchButton;

    /**
     * Search performance status flag for tracking search operation execution and interface state management.
     * <p>
     * This boolean field indicates whether search operations have been performed,
     * supporting interface state management and search result persistence throughout
     * navigation operations. The flag enables proper search state tracking and
     * interface behavior coordination during customer booking management workflows.
     * </p>
     */
    private boolean searchPerformed = false;

    /**
     * Collection of booking dates from search results for comprehensive booking information management.
     * <p>
     * This ArrayList contains booking creation dates retrieved from search operations,
     * providing temporal information for booking identification and organization.
     * The collection is synchronized with booking status and flight ID collections
     * to maintain data consistency throughout search result processing and display.
     * </p>
     */
    ArrayList<Date> bookingDates = new ArrayList<>();
    
    /**
     * Collection of booking status information from search results for booking state tracking and display.
     * <p>
     * This ArrayList contains booking status strings (CONFIRMED, PENDING, CANCELLED)
     * retrieved from search operations, providing current booking state information
     * for customer awareness and booking management decisions. The collection maintains
     * synchronization with other result data for consistent information presentation.
     * </p>
     */
    ArrayList<String> bookingStatus = new ArrayList<>();
    
    /**
     * Collection of flight identifiers from search results for flight information correlation and management.
     * <p>
     * This ArrayList contains flight ID strings associated with booking search results,
     * enabling correlation with flight information and detailed booking data presentation.
     * The collection supports result display operations and booking detail access
     * throughout customer booking management and modification workflows.
     * </p>
     */
    ArrayList<String> flightIds = new ArrayList<>();

    /**
     * Constructs a new SearchBookingPanel with comprehensive search functionality and customer workflow integration.
     * <p>
     * This constructor initializes the complete booking search interface by establishing layout
     * management, configuring visual styling, and setting up comprehensive search functionality
     * for customer booking location and management. The constructor creates a fully functional
     * search panel ready for immediate integration within customer booking management workflows
     * with support for both menu-initiated and direct access patterns.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li><strong>Layout Configuration:</strong> GridBagLayout establishment with proper spacing and component organization</li>
     *   <li><strong>Visual Styling:</strong> Professional background colors and border configuration for airport system integration</li>
     *   <li><strong>Constraints Setup:</strong> Layout management utility initialization for precise component positioning</li>
     *   <li><strong>Border Management:</strong> Compound border creation with line borders and padding for visual definition</li>
     *   <li><strong>Component Assembly:</strong> Complete search interface creation through setComponents method delegation</li>
     * </ul>
     * <p>
     * Layout configuration establishes {@link GridBagLayout} as the primary layout manager,
     * providing precise control over component positioning, spacing, and alignment throughout
     * the search interface. The layout supports dynamic component switching between filter
     * modes while maintaining consistent visual organization and user experience.
     * </p>
     * <p>
     * Visual styling includes white background configuration for optimal content readability
     * and professional appearance that integrates seamlessly with the airport management
     * system's visual design standards. The styling ensures consistent branding and user
     * experience throughout customer booking management interfaces.
     * </p>
     * <p>
     * Constraints setup initializes the {@link Constraints} utility object that provides
     * standardized {@link GridBagConstraints} configuration throughout the search interface.
     * The constraints ensure consistent component spacing, alignment, and positioning across
     * all search interface elements and dynamic filter panel operations.
     * </p>
     * <p>
     * Border management creates a compound border system combining line borders for visual
     * definition with empty borders for proper padding. The border configuration includes
     * light gray line borders (200, 200, 200) and comprehensive padding (40, 50, 40, 50)
     * that provides optimal visual separation and content organization.
     * </p>
     * <p>
     * Component assembly delegates to the {@link #setComponents(List, Controller, boolean)}
     * method for complete search interface creation including filter panels, search buttons,
     * result display areas, and menu integration functionality. The assembly process ensures
     * proper component initialization and event handling setup.
     * </p>
     * <p>
     * Menu integration support enables specialized behavior when the search panel is accessed
     * through customer menu navigation, providing automatic booking data loading and immediate
     * result display for enhanced customer convenience and workflow efficiency.
     * </p>
     * <p>
     * The constructor completes by establishing a fully functional search interface that
     * provides comprehensive booking search capabilities with professional styling, responsive
     * layout management, and seamless integration with the airport management system's
     * customer booking management infrastructure and workflow requirements.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and workflow coordination
     * @param controller the system controller providing access to booking search functionality and data management services
     * @param ifOpenedFromMenu flag indicating whether the panel was accessed through customer menu for specialized behavior and automatic data loading
     */
    public SearchBookingPanel(List<DisposableObject> callingObjects, Controller controller, boolean ifOpenedFromMenu) {

        super();

        this.setLayout(new GridBagLayout());
        this.setBackground(Color.WHITE);

        this.constraints = new Constraints();

        Border lineBorder = BorderFactory.createLineBorder(new Color(200, 200, 200));
        Border emptyBorder = BorderFactory.createEmptyBorder(40, 50, 40, 50);

        this.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));

        setComponents(callingObjects, controller, ifOpenedFromMenu);
    }

    /**
     * Configures and assembles all search interface components with comprehensive functionality and menu integration support.
     * <p>
     * This private method establishes the complete search interface by creating and positioning
     * all necessary components including filter panels, search buttons, and result display areas.
     * The method handles both menu-initiated and direct access scenarios, providing appropriate
     * data loading and result presentation based on the access context while ensuring optimal
     * user experience and comprehensive search functionality throughout customer booking workflows.
     * </p>
     * <p>
     * The component assembly process includes:
     * </p>
     * <ul>
     *   <li><strong>Filter Panel Creation:</strong> Main filter panel establishment with flight and passenger filtering options</li>
     *   <li><strong>Search Button Setup:</strong> Primary search button creation with comprehensive event handling and validation</li>
     *   <li><strong>Results Container Configuration:</strong> Scroll pane establishment for dynamic result display and navigation</li>
     *   <li><strong>Layout Management:</strong> Precise component positioning using GridBagConstraints for optimal interface organization</li>
     *   <li><strong>Data Loading Logic:</strong> Conditional data loading based on menu access patterns and existing search results</li>
     *   <li><strong>Result Display Initialization:</strong> Initial result panel setup and display for immediate user interaction</li>
     * </ul>
     * <p>
     * Filter panel creation utilizes the {@link #createMainFilterPanel()} method to establish
     * the dual-mode filtering interface with flight-based and passenger-based search options.
     * The filter panel includes dynamic switching capabilities and comprehensive input validation
     * for all search criteria types supported by the booking search system.
     * </p>
     * <p>
     * Search button setup creates the primary search execution button through the
     * {@link #createSearchButton(List, Controller)} method, including comprehensive event
     * handling, input validation, and integration with the {@link FloatingMessage} system
     * for user feedback during search operations and error scenarios.
     * </p>
     * <p>
     * Results container configuration establishes a {@link JScrollPane} for dynamic result
     * display, providing scrolling capabilities when search results exceed visible area.
     * The container supports smooth scrolling and optimal result presentation throughout
     * customer booking search and selection operations.
     * </p>
     * <p>
     * Layout management applies {@link GridBagConstraints} through the constraints utility
     * to ensure optimal component positioning and spacing. The layout includes:
     * </p>
     * <ul>
     *   <li><strong>Filter Panel Positioning:</strong> Top area with horizontal expansion and proper margins</li>
     *   <li><strong>Search Button Placement:</strong> Center positioning with appropriate spacing and visual prominence</li>
     *   <li><strong>Results Area Configuration:</strong> Full expansion with both horizontal and vertical filling</li>
     * </ul>
     * <p>
     * Data loading logic provides conditional behavior based on the ifOpenedFromMenu parameter:
     * </p>
     * <ul>
     *   <li><strong>Menu Access:</strong> Automatic loading of all customer bookings through controller.getAllBooksLoogedCustomer()</li>
     *   <li><strong>Direct Access:</strong> Retrieval of existing search results from controller caching for result persistence</li>
     * </ul>
     * <p>
     * Menu access enables immediate booking display for customers accessing the search interface
     * through navigation menus, providing convenient access to all booking information without
     * requiring manual search operations. The automatic loading enhances customer experience
     * and reduces interaction complexity for common booking management tasks.
     * </p>
     * <p>
     * Direct access supports navigation continuity by retrieving cached search results from
     * previous operations, maintaining search context and result persistence throughout
     * customer navigation between different interface components and booking management areas.
     * </p>
     * <p>
     * Result display initialization completes the setup process by calling
     * {@link #updateResultsPanel(List, Controller)} to establish initial result presentation.
     * The initialization ensures that customers see immediate booking information based on
     * the access context and available data, providing responsive interface behavior.
     * </p>
     * <p>
     * Error button configuration integrates the search button with the controller's error
     * handling system, enabling proper error message positioning and user feedback during
     * search operations and validation failures throughout booking search workflows.
     * </p>
     * <p>
     * The method ensures complete search interface functionality with proper event handling,
     * data management, and user experience optimization for both menu-initiated and direct
     * access patterns throughout customer booking management and search operations.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and navigation coordination
     * @param controller the system controller providing access to booking search services and data management functionality
     * @param ifOpenedFromMenu flag indicating menu-initiated access for automatic booking data loading and specialized interface behavior
     */
    private void setComponents(List<DisposableObject> callingObjects, Controller controller, boolean ifOpenedFromMenu) {

        JPanel parametersPanel = createMainFilterPanel();

        searchButton = createSearchButton(callingObjects, controller);

        resultsScrollPane = new JScrollPane();


        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.NORTH, 1.0f, 0.0f, new Insets(0, 0, 0, 0));
        this.add(parametersPanel, constraints.getGridBagConstraints());

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER, 1.0f, 0.0f, new Insets(30, 0, 30, 0));
        this.add(searchButton, constraints.getGridBagConstraints());

        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.CENTER, 1.0f, 1.0f, new Insets(0, 0, 0, 0));
        this.add(resultsScrollPane, constraints.getGridBagConstraints());

        if(ifOpenedFromMenu){
            controller.getAllBooksLoogedCustomer(bookingDates, bookingStatus, flightIds, searchButton);
        }else{
            this.bookingDates = (ArrayList<Date>) controller.getBookingController().getSearchBookingResultDates();
            this.bookingStatus = (ArrayList<String>) controller.getBookingController().getSearchBookingResultStatus();
            this.flightIds = (ArrayList<String>) controller.getFlightController().getSearchBookingResultIds();
        }

        updateResultsPanel(callingObjects, controller);
    }

        /**
     * Creates and configures the main filter panel with dual-mode filtering capabilities and dynamic panel switching.
     * <p>
     * This private method establishes the primary filtering interface that enables customers to choose
     * between flight-based and passenger-based search modes. The method creates a comprehensive filtering
     * system with dynamic panel visibility management, button-based mode selection, and seamless switching
     * between different search criteria types while maintaining consistent visual organization and
     * user experience throughout booking search operations.
     * </p>
     * <p>
     * The main filter panel creation process includes:
     * </p>
     * <ul>
     *   <li><strong>Container Setup:</strong> Main panel creation with GridBagLayout for precise component positioning</li>
     *   <li><strong>Mode Selection Buttons:</strong> Flight and passenger filter buttons with professional styling</li>
     *   <li><strong>Filter Panel Creation:</strong> Specialized flight and passenger filter panels with comprehensive input fields</li>
     *   <li><strong>Dynamic Visibility Management:</strong> Initial visibility configuration with proper panel state management</li>
     *   <li><strong>Event Handler Integration:</strong> ActionListener setup for dynamic panel switching and state coordination</li>
     * </ul>
     * <p>
     * Container setup creates the main panel with transparent background and GridBagLayout configuration
     * for optimal component organization. The container includes a button panel with GridLayout for
     * uniform button distribution and proper spacing (40 pixels) between filter mode selection buttons
     * to ensure clear visual separation and professional interface appearance.
     * </p>
     * <p>
     * Mode selection buttons include professionally styled flight and passenger filtering buttons
     * with Italian localization ("Filtra per voli" and "Filtra per passeggeri"). The buttons utilize
     * the {@link #setButtonApperance(JButton)} method for consistent styling and provide clear
     * visual indication of available filtering modes for customer selection.
     * </p>
     * <p>
     * Filter panel creation utilizes specialized methods {@link #createFlightFilterPanel()} and
     * {@link #createPassengerFilterPanel()} to establish comprehensive input interfaces for each
     * search mode. The panels are created with complete functionality but initially hidden to
     * provide clean interface presentation before user selection.
     * </p>
     * <p>
     * Dynamic visibility management includes initial configuration where both filter panels are
     * set to invisible, providing a clean initial interface state. The panels are positioned
     * using overlapping layout constraints, enabling efficient space utilization while maintaining
     * proper visual presentation when activated through user interaction.
     * </p>
     * <p>
     * Event handler integration includes comprehensive ActionListener implementation for both
     * filter mode buttons that provides:
     * </p>
     * <ul>
     *   <li><strong>Flight Button Handler:</strong> Sets activeFilter to "FLIGHT", shows flight filter panel, hides passenger panel</li>
     *   <li><strong>Passenger Button Handler:</strong> Sets activeFilter to "PASSENGER", shows passenger filter panel, hides flight panel</li>
     *   <li><strong>Interface Updates:</strong> Automatic panel revalidation and repainting for smooth visual transitions</li>
     *   <li><strong>State Management:</strong> Active filter mode tracking for proper search operation routing</li>
     * </ul>
     * <p>
     * The flight button event handler configures the interface for flight-based booking searches
     * by updating the activeFilter state variable, managing panel visibility for flight-specific
     * input fields, and triggering interface updates to ensure immediate visual feedback for
     * user selection and proper interface state presentation.
     * </p>
     * <p>
     * The passenger button event handler configures the interface for passenger-based booking
     * searches by updating the activeFilter state variable, managing panel visibility for
     * passenger-specific input fields, and ensuring proper interface coordination for passenger
     * information-based search operations throughout the booking management workflow.
     * </p>
     * <p>
     * Layout management utilizes precise GridBagConstraints configuration through the constraints
     * utility to ensure optimal component positioning. The button panel is positioned at the top
     * with horizontal expansion and appropriate bottom margin (35 pixels), while filter panels
     * are positioned below with overlapping constraints for efficient space utilization.
     * </p>
     * <p>
     * Interface state management ensures that only one filter panel is visible at any time,
     * providing clean user experience and preventing interface confusion. The dynamic switching
     * includes proper revalidation and repainting to ensure smooth visual transitions and
     * immediate user feedback during filter mode selection.
     * </p>
     * <p>
     * The method returns a fully configured main panel ready for integration within the broader
     * search interface, providing comprehensive dual-mode filtering capabilities with professional
     * visual presentation and seamless user interaction throughout booking search operations.
     * </p>
     *
     * @return the configured main filter panel with dual-mode filtering capabilities and dynamic switching functionality
     */
    private JPanel createMainFilterPanel() {
        JButton passengerButton;
        JButton flightButton;
        JPanel passengerFilterPanel;
        JPanel flightFilterPanel;

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);


        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 40, 0));
        buttonPanel.setOpaque(false);

        flightButton = new JButton("Filtra per voli");
        setButtonApperance(flightButton);
        buttonPanel.add(flightButton);

        passengerButton = new JButton("Filtra per passeggeri");
        setButtonApperance(passengerButton);
        buttonPanel.add(passengerButton);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.CENTER, 1.0f, 0.0f, new Insets(0, 0, 35, 0));
        mainPanel.add(buttonPanel, constraints.getGridBagConstraints());


        flightFilterPanel = createFlightFilterPanel();
        passengerFilterPanel = createPassengerFilterPanel();

        //Imposto i panel per applicare i filtri inizialmente non visibili, compariranno quando si scegli per cosa filtrare
        flightFilterPanel.setVisible(false);
        passengerFilterPanel.setVisible(false);

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.CENTER, 1.0f, 0.0f, new Insets(0, 0, 0, 0));

        //I panel di filtri sono sovrapposti, ma visibili solo uno per volta
        mainPanel.add(flightFilterPanel, constraints.getGridBagConstraints());
        mainPanel.add(passengerFilterPanel, constraints.getGridBagConstraints());


        flightButton.addActionListener(e -> {
            activeFilter = "FLIGHT";
            flightFilterPanel.setVisible(true);
            passengerFilterPanel.setVisible(false);
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        passengerButton.addActionListener(e -> {
            activeFilter = "PASSENGER";
            passengerFilterPanel.setVisible(true);
            flightFilterPanel.setVisible(false);
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        return mainPanel;
    }

    /**
     * Creates and configures the comprehensive flight filter panel with advanced flight search criteria and date/time range selection.
     * <p>
     * This private method establishes the specialized flight filtering interface that enables customers
     * to search for bookings using flight-specific criteria including origin/destination cities, date
     * ranges, and time ranges. The method creates a sophisticated dual-column layout with comprehensive
     * input validation support, professional styling, and optimal space utilization for enhanced user
     * experience during flight-based booking search operations.
     * </p>
     * <p>
     * The flight filter panel creation process includes:
     * </p>
     * <ul>
     *   <li><strong>Container Architecture:</strong> Dual-column layout with GridLayout container and GridBagLayout panels</li>
     *   <li><strong>Origin/Destination Fields:</strong> City input fields with professional styling and validation integration</li>
     *   <li><strong>Date Range Selection:</strong> DatePicker components with range validation and user-friendly interface</li>
     *   <li><strong>Time Range Selection:</strong> TimePicker components with time range validation and precise selection</li>
     *   <li><strong>Visual Organization:</strong> Label styling, separator elements, and optimal spacing for professional presentation</li>
     * </ul>
     * <p>
     * Container architecture utilizes a sophisticated layout system with a main GridLayout container
     * (1 row, 2 columns, 40-pixel gap) that houses left and right panels configured with GridBagLayout
     * for precise component positioning. All panels use transparent backgrounds to maintain visual
     * integration with the parent container and overall interface design consistency.
     * </p>
     * <p>
     * Origin/destination fields include professionally styled text fields with appropriate sizing
     * (15 characters) and Segoe UI font configuration (plain, 16pt) for optimal readability. The
     * left panel houses the "Da:" (From) field while the right panel contains the "A:" (To) field,
     * providing intuitive left-to-right flight route specification that matches natural user expectations.
     * </p>
     * <p>
     * Date range selection utilizes {@link DatePicker} components with custom font configuration
     * (Segoe UI, plain, 14pt) for the date text fields. The date pickers are positioned with a
     * visual separator ("--") between them to clearly indicate range specification, and include
     * proper constraint configuration for optimal space distribution (0.5f weight each) within
     * the available layout space.
     * </p>
     * <p>
     * Time range selection employs {@link TimePicker} components with matching font configuration
     * and layout principles as the date range selection. The time pickers enable precise time
     * specification for flight filtering and include visual separation with consistent spacing
     * and alignment throughout the interface for professional presentation.
     * </p>
     * <p>
     * Visual organization includes comprehensive label styling through {@link #setLabelApperance(JLabel)}
     * for consistent typography and professional appearance. Labels include Italian localization:
     * </p>
     * <ul>
     *   <li><strong>"Da:" and "A:":</strong> Origin and destination city labels with clear identification</li>
     *   <li><strong>"Range date:":</strong> Date range section label for temporal filtering</li>
     *   <li><strong>"Fascia oraria:":</strong> Time range section label for temporal precision</li>
     *   <li><strong>"--" Separators:</strong> Visual separators between range components for clarity</li>
     * </ul>
     * <p>
     * Layout management utilizes precise GridBagConstraints configuration through the constraints
     * utility to ensure optimal component positioning and spacing. The layout includes:
     * </p>
     * <ul>
     *   <li><strong>Left Panel Layout:</strong> Origin field and date range components with proper alignment</li>
     *   <li><strong>Right Panel Layout:</strong> Destination field and time range components with consistent spacing</li>
     *   <li><strong>Spacing Management:</strong> 30-pixel bottom margins for visual separation between sections</li>
     *   <li><strong>Alignment Control:</strong> LINE_END and LINE_START alignment for optimal visual organization</li>
     * </ul>
     * <p>
     * Component positioning ensures that labels are right-aligned (LINE_END) for clean visual
     * organization, while input fields are left-aligned (LINE_START) with horizontal expansion
     * for optimal space utilization. Separators are center-aligned to provide clear visual
     * indication of range relationships between paired components.
     * </p>
     * <p>
     * The method integrates field initialization with proper styling and positioning to create
     * a comprehensive flight filtering interface that supports advanced search criteria specification
     * while maintaining professional visual presentation and intuitive user interaction patterns
     * throughout flight-based booking search operations.
     * </p>
     * <p>
     * Validation integration is prepared through proper field configuration and styling that
     * supports the comprehensive input validation implemented in {@link #filteredFlightSearch}
     * for city specification consistency, date range logic, and time range validation throughout
     * the booking search workflow.
     * </p>
     *
     * @return the configured flight filter panel with comprehensive flight search criteria and professional visual presentation
     */
    private JPanel createFlightFilterPanel() {

        JLabel timeSep;
        JLabel timeLabel;
        JLabel dateSep;
        JLabel dateLabel;
        JLabel toLabel;
        JLabel fromLabel;

        JPanel container = new JPanel(new GridLayout(1, 2, 40, 0));
        container.setOpaque(false);

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setOpaque(false);

        fromLabel = new JLabel("Da:");
        setLabelApperance(fromLabel);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(fromLabel, constraints.getGridBagConstraints());

        fromField = new JTextField(15);
        fromField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        constraints.setConstraints(1, 0, 3, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(0, 0, 30, 0));
        leftPanel.add(fromField, constraints.getGridBagConstraints());

        dateLabel = new JLabel("Range date:");
        setLabelApperance(dateLabel);

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(dateLabel, constraints.getGridBagConstraints());

        dateFrom = new DatePicker();
        dateFrom.getComponentDateTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));

        constraints.setConstraints(1, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.CENTER, 0.5f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(dateFrom, constraints.getGridBagConstraints());

        dateSep = new JLabel("--");
        setLabelApperance(dateSep);

        constraints.setConstraints(2, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(dateSep, constraints.getGridBagConstraints());

        dateTo = new DatePicker();
        dateTo.getComponentDateTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));

        constraints.setConstraints(3, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.CENTER, 0.5f, 0.0f, new Insets(0, 0, 30, 0));
        leftPanel.add(dateTo, constraints.getGridBagConstraints());


        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);

        toLabel = new JLabel("A:");
        setLabelApperance(toLabel);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(toLabel, constraints.getGridBagConstraints());

        toField = new JTextField(15);
        toField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        constraints.setConstraints(1, 0, 3, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(0, 0, 30, 0));
        rightPanel.add(toField, constraints.getGridBagConstraints());

        timeLabel = new JLabel("Fascia oraria:");
        setLabelApperance(timeLabel);

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(timeLabel, constraints.getGridBagConstraints());

        timeFrom = new TimePicker();
        timeFrom.getComponentTimeTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));

        constraints.setConstraints(1, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.CENTER, 0.5f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(timeFrom, constraints.getGridBagConstraints());

        timeSep = new JLabel("--");
        setLabelApperance(timeSep);

        constraints.setConstraints(2, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(timeSep, constraints.getGridBagConstraints());

        timeTo = new TimePicker();
        timeTo.getComponentTimeTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));

        constraints.setConstraints(3, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.CENTER, 0.5f, 0.0f, new Insets(0, 0, 30, 0));
        rightPanel.add(timeTo, constraints.getGridBagConstraints());

        container.add(leftPanel);
        container.add(rightPanel);

        return container;
    }

    /**
     * Creates and configures the comprehensive passenger filter panel with personal information search criteria and ticket-based identification.
     * <p>
     * This private method establishes the specialized passenger filtering interface that enables customers
     * to search for bookings using passenger-specific information including personal details and ticket
     * identification. The method creates a professional dual-column layout with comprehensive input fields
     * for flexible passenger-based booking searches, supporting both partial information searches and
     * precise ticket-based identification throughout customer booking management operations.
     * </p>
     * <p>
     * The passenger filter panel creation process includes:
     * </p>
     * <ul>
     *   <li><strong>Container Architecture:</strong> Dual-column layout with GridLayout container and GridBagLayout panels for optimal organization</li>
     *   <li><strong>Personal Information Fields:</strong> First name, last name, and SSN input fields with professional styling</li>
     *   <li><strong>Ticket Identification:</strong> Ticket number field for direct booking access and precise identification</li>
     *   <li><strong>Italian Localization:</strong> Comprehensive Italian language labels for customer-friendly interface presentation</li>
     *   <li><strong>Professional Styling:</strong> Consistent typography, spacing, and visual organization throughout the interface</li>
     * </ul>
     * <p>
     * Container architecture utilizes a sophisticated layout system with a main GridLayout container
     * (1 row, 2 columns, 40-pixel gap) that houses left and right panels configured with GridBagLayout
     * for precise component positioning. All panels use transparent backgrounds to maintain visual
     * integration with the parent container and consistent interface design throughout the application.
     * </p>
     * <p>
     * Personal information fields include comprehensive passenger identification options with
     * professional styling and appropriate sizing. All text fields are configured with 15-character
     * width and Segoe UI font (plain, 16pt) for optimal readability and consistent visual presentation
     * that matches the flight filter panel styling for interface coherence.
     * </p>
     * <p>
     * The left panel houses essential personal information fields:
     * </p>
     * <ul>
     *   <li><strong>First Name Field:</strong> "Nome:" label with corresponding text input for passenger first name searches</li>
     *   <li><strong>SSN Field:</strong> "Codice Fiscale:" label with text input for Italian fiscal code-based precise identification</li>
     * </ul>
     * <p>
     * The right panel contains complementary identification fields:
     * </p>
     * <ul>
     *   <li><strong>Last Name Field:</strong> "Cognome:" label with corresponding text input for passenger surname searches</li>
     *   <li><strong>Ticket Number Field:</strong> "N. Biglietto:" label with text input for direct ticket-based booking identification</li>
     * </ul>
     * <p>
     * Italian localization provides customer-friendly interface presentation with comprehensive
     * Italian language labels that match the customer base and operational context of the airport
     * management system. The localization includes proper terminology for passenger identification
     * fields and ticket-based search functionality that aligns with Italian aviation standards.
     * </p>
     * <p>
     * Professional styling utilizes {@link #setLabelApperance(JLabel)} for consistent typography
     * and visual presentation across all label elements. The styling ensures professional appearance
     * with proper font configuration, alignment, and spacing that maintains visual consistency
     * with other interface components throughout the booking management system.
     * </p>
     * <p>
     * Layout management utilizes precise GridBagConstraints configuration through the constraints
     * utility to ensure optimal component positioning and spacing. The layout includes:
     * </p>
     * <ul>
     *   <li><strong>Label Positioning:</strong> Right-aligned labels (LINE_END) for clean visual organization</li>
     *   <li><strong>Field Positioning:</strong> Left-aligned input fields (LINE_START) with horizontal expansion</li>
     *   <li><strong>Spacing Management:</strong> 30-pixel bottom margins and 10-pixel horizontal spacing for professional presentation</li>
     *   <li><strong>Weight Distribution:</strong> Proper weight configuration (1.0f for fields) for optimal space utilization</li>
     * </ul>
     * <p>
     * Component positioning ensures consistent visual organization with labels positioned for
     * optimal readability and input fields configured for efficient data entry. The layout
     * maintains consistent spacing (30-pixel vertical margins, 10-pixel horizontal margins)
     * throughout the interface for professional appearance and user-friendly interaction patterns.
     * </p>
     * <p>
     * Field configuration includes proper font styling and sizing that supports various types
     * of passenger searches from partial name matching to precise fiscal code and ticket number
     * identification. The flexible search capability enables customers to locate bookings using
     * whatever passenger information they have available for enhanced convenience and accessibility.
     * </p>
     * <p>
     * The method creates a comprehensive passenger filtering interface that supports the diverse
     * search requirements of customer booking management while maintaining professional visual
     * presentation and intuitive user interaction patterns that align with the broader airport
     * management system interface design and functionality standards.
     * </p>
     * <p>
     * Integration with {@link #filteredPassengerSearch} enables comprehensive validation and
     * search execution based on the passenger criteria specified through this interface, providing
     * flexible booking location capabilities that enhance customer service and booking management
     * efficiency throughout the airport management system operations.
     * </p>
     *
     * @return the configured passenger filter panel with comprehensive personal information search criteria and professional presentation
     */
    private JPanel createPassengerFilterPanel() {

        JLabel passengerSSNLabel;
        JLabel lastNameLabel;
        JLabel firstNameLabel;

        JPanel container = new JPanel(new GridLayout(1, 2, 40, 0));
        container.setOpaque(false);


        firstNameLabel = new JLabel("Nome:");
        setLabelApperance(firstNameLabel);

        firstNameField = new JTextField(15);
        firstNameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        lastNameLabel = new JLabel("Cognome:");
        setLabelApperance(lastNameLabel);

        lastNameField = new JTextField(15);
        lastNameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        passengerSSNLabel = new JLabel("Codice Fiscale:");
        setLabelApperance(passengerSSNLabel);

        passengerSSNField = new JTextField(15);
        passengerSSNField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel ticketNumberLabel = new JLabel("N. Biglietto:");
        setLabelApperance(ticketNumberLabel);

        ticketNumberField = new JTextField(15);
        ticketNumberField.setFont(new Font("Segoe UI", Font.PLAIN, 16));


        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setOpaque(false);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(firstNameLabel, constraints.getGridBagConstraints());

        constraints.setConstraints(1, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(0, 0, 30, 0));
        leftPanel.add(firstNameField, constraints.getGridBagConstraints());

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(passengerSSNLabel, constraints.getGridBagConstraints());

        constraints.setConstraints(1, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(0, 0, 30, 0));
        leftPanel.add(passengerSSNField, constraints.getGridBagConstraints());


        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(lastNameLabel, constraints.getGridBagConstraints());

        constraints.setConstraints(1, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(0, 0, 30, 0));
        rightPanel.add(lastNameField, constraints.getGridBagConstraints());

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(ticketNumberLabel, constraints.getGridBagConstraints());

        constraints.setConstraints(1, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(0, 0, 30, 0));
        rightPanel.add(ticketNumberField, constraints.getGridBagConstraints());

        container.add(leftPanel);
        container.add(rightPanel);

        return container;
    }

        /**
     * Creates and configures the primary search button with comprehensive event handling and professional styling.
     * <p>
     * This private method establishes the main search execution button that serves as the central
     * interaction point for initiating booking search operations. The method creates a professionally
     * styled button with comprehensive event handling that routes search operations based on the
     * currently selected filter mode, providing comprehensive input validation and user feedback
     * through the integrated {@link FloatingMessage} system for optimal user experience.
     * </p>
     * <p>
     * The search button creation process includes:
     * </p>
     * <ul>
     *   <li><strong>Visual Configuration:</strong> Professional styling with airport system branding and optimal sizing</li>
     *   <li><strong>Font Styling:</strong> Bold Segoe UI typography with appropriate sizing for clear readability</li>
     *   <li><strong>Color Scheme:</strong> Blue background with white text for professional appearance and clear visibility</li>
     *   <li><strong>Interactive Elements:</strong> Hand cursor and focus management for enhanced user interaction</li>
     *   <li><strong>Event Handling:</strong> Comprehensive ActionListener implementation with filter mode routing</li>
     *   <li><strong>Error Integration:</strong> Controller error button registration for proper error message positioning</li>
     * </ul>
     * <p>
     * Visual configuration establishes professional button appearance with Italian "Cerca" (Search)
     * label that provides clear action indication for customers. The button utilizes modern design
     * principles with appropriate sizing (200x50 pixels) for optimal clickability and visual prominence
     * within the search interface layout.
     * </p>
     * <p>
     * Font styling applies bold Segoe UI font (18pt) for optimal readability and professional
     * appearance that maintains consistency with the airport management system's typography
     * standards. The font configuration ensures clear text visibility across different display
     * configurations and user interface scaling scenarios.
     * </p>
     * <p>
     * Color scheme utilizes professional blue background (0, 120, 215) with white foreground
     * text that provides excellent contrast and visual prominence. The color configuration
     * follows modern interface design principles and integrates seamlessly with the overall
     * airport management system visual branding and user experience standards.
     * </p>
     * <p>
     * Interactive elements include hand cursor configuration for clear interaction indication
     * and focus painting disabled for clean visual presentation. The interactive configuration
     * enhances user experience by providing immediate visual feedback for button interaction
     * while maintaining professional interface appearance.
     * </p>
     * <p>
     * Event handling implements comprehensive ActionListener functionality that provides
     * intelligent search operation routing based on the current activeFilter state:
     * </p>
     * <ul>
     *   <li><strong>NONE State:</strong> Displays error message requiring filter selection before search execution</li>
     *   <li><strong>FLIGHT State:</strong> Routes to filteredFlightSearch method for flight-based booking searches</li>
     *   <li><strong>PASSENGER State:</strong> Routes to filteredPassengerSearch method for passenger-based booking searches</li>
     *   <li><strong>Default Handling:</strong> Provides fallback behavior for unexpected filter states</li>
     * </ul>
     * <p>
     * The NONE state handler displays Italian-language error message "Selezionare un tipo di
     * filtro prima di cercare." (Select a filter type before searching) through the FloatingMessage
     * system, providing clear user guidance when no filter mode has been selected. The error
     * handling prevents invalid search operations and guides users toward proper interface usage.
     * </p>
     * <p>
     * The FLIGHT state handler delegates search execution to the {@link #filteredFlightSearch}
     * method, providing comprehensive flight-based booking search functionality with origin/destination
     * city filtering, date range validation, and time range specification throughout the booking
     * search workflow with proper error handling and result presentation.
     * </p>
     * <p>
     * The PASSENGER state handler delegates search execution to the {@link #filteredPassengerSearch}
     * method, enabling comprehensive passenger-based booking searches using personal information
     * including first name, last name, SSN, and ticket number criteria for flexible booking
     * location capabilities throughout customer service operations.
     * </p>
     * <p>
     * Error integration includes controller error button registration through the setErrorButton
     * method, enabling proper error message positioning and user feedback during search operations
     * and validation failures. The integration ensures consistent error presentation throughout
     * booking search workflows and maintains professional user experience standards.
     * </p>
     * <p>
     * Search performance tracking includes automatic searchPerformed flag setting to true,
     * supporting interface state management and search result persistence throughout navigation
     * operations. The performance tracking enables proper search state coordination and interface
     * behavior management during customer booking management workflows.
     * </p>
     * <p>
     * The method returns a fully configured search button ready for immediate integration within
     * the booking search interface, providing comprehensive search execution capabilities with
     * professional styling, intelligent routing, and seamless user experience throughout
     * customer booking management and search operations.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and search coordination
     * @param controller the system controller providing access to booking search services and error handling functionality
     * @return the configured search button with comprehensive event handling and professional styling
     */
    private JButton createSearchButton(List<DisposableObject> callingObjects, Controller controller) {

        searchButton = new JButton("Cerca");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 18));

        searchButton.setBackground(new Color(0, 120, 215));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);

        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        searchButton.setPreferredSize(new Dimension(200, 50));

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (activeFilter) {
                    case "NONE":
                        new FloatingMessage("Selezionare un tipo di filtro prima di cercare.", searchButton, FloatingMessage.ERROR_MESSAGE);
                        break;
                    case "FLIGHT":
                        filteredFlightSearch(callingObjects, controller, searchButton);
                        break;
                    case "PASSENGER":
                        filteredPassengerSearch(callingObjects, controller, searchButton);
                        break;
                    default:
                        break;
                }

            }
        });

        searchPerformed = true;
        controller.setErrorButton(searchButton);

        return searchButton;
    }

    /**
     * Executes comprehensive flight-based booking search operations with advanced input validation and error handling.
     * <p>
     * This public method performs sophisticated flight-based booking searches using origin/destination
     * cities, date ranges, and time ranges as search criteria. The method includes comprehensive
     * input validation with Italian-language error messaging, proper search parameter extraction,
     * and coordinated result presentation through the integrated result display system for optimal
     * customer experience throughout flight-based booking location workflows.
     * </p>
     * <p>
     * The flight search execution process includes:
     * </p>
     * <ul>
     *   <li><strong>Parameter Extraction:</strong> Comprehensive retrieval of all flight search criteria from input fields</li>
     *   <li><strong>Input Validation:</strong> Multi-level validation logic with specific error messaging for each validation scenario</li>
     *   <li><strong>Search Execution:</strong> Controller-coordinated search operations with proper data collection management</li>
     *   <li><strong>Result Presentation:</strong> Dynamic result panel updates with immediate user feedback and data display</li>
     *   <li><strong>Error Handling:</strong> Comprehensive error messaging through FloatingMessage system integration</li>
     * </ul>
     * <p>
     * Parameter extraction includes comprehensive retrieval of all flight search criteria:
     * </p>
     * <ul>
     *   <li><strong>Origin City:</strong> Departure city text from fromField input component</li>
     *   <li><strong>Destination City:</strong> Arrival city text from toField input component</li>
     *   <li><strong>Date Range:</strong> Start and end dates from dateFrom and dateTo DatePicker components</li>
     *   <li><strong>Time Range:</strong> Start and end times from timeFrom and timeTo TimePicker components</li>
     * </ul>
     * <p>
     * Input validation implements comprehensive multi-level validation logic with specific
     * Italian-language error messaging for each validation scenario:
     * </p>
     * <ul>
     *   <li><strong>City Consistency:</strong> Validates that both origin and destination cities are specified or both are empty</li>
     *   <li><strong>Date Range Logic:</strong> Ensures complete date range specification when partial dates are provided</li>
     *   <li><strong>Date Sequence Validation:</strong> Verifies that end date is after or equal to start date</li>
     *   <li><strong>Time Range Consistency:</strong> Validates complete time range specification when partial times are provided</li>
     * </ul>
     * <p>
     * City consistency validation prevents incomplete route specifications by ensuring that
     * customers provide both origin and destination cities or leave both fields empty for
     * broader search results. The validation displays Italian error message "Se si specifica
     * una città, vanno specificate entrambe!" (If you specify a city, both must be specified!)
     * for incomplete city specifications.
     * </p>
     * <p>
     * Date range logic validation ensures logical date range specification by preventing
     * partial date range entries where only start or end date is provided. The validation
     * displays Italian error message "Errore nel range di date!" (Error in date range!)
     * for incomplete date range specifications that could lead to invalid search results.
     * </p>
     * <p>
     * Date sequence validation ensures logical temporal ordering by verifying that the
     * end date is not before the start date. The validation displays Italian error message
     * "La seconda data deve essere successiva alla prima!" (The second date must be after
     * the first!) for invalid date sequence specifications.
     * </p>
     * <p>
     * Time range consistency validation ensures complete time range specification by
     * preventing partial time range entries where only start or end time is provided.
     * The validation displays Italian error message "Errore nella fascia oraria!"
     * (Error in time range!) for incomplete time specifications.
     * </p>
     * <p>
     * Search execution includes controller-coordinated search operations when all validation
     * checks pass successfully. The execution process includes:
     * </p>
     * <ul>
     *   <li><strong>Data Collection Initialization:</strong> Creates new ArrayList instances for search results</li>
     *   <li><strong>Controller Search Invocation:</strong> Calls searchBooksLoogedCustomerFilteredFlights with all search parameters</li>
     *   <li><strong>Result Processing:</strong> Handles search result data in synchronized collections</li>
     *   <li><strong>Interface Updates:</strong> Triggers result panel updates for immediate user feedback</li>
     * </ul>
     * <p>
     * Data collection initialization creates fresh ArrayList instances for bookingDates,
     * bookingStatus, and flightIds to ensure clean search result storage without contamination
     * from previous search operations. The initialization provides proper data isolation
     * and consistent search result presentation.
     * </p>
     * <p>
     * Controller search invocation delegates actual search operations to the controller's
     * searchBooksLoogedCustomerFilteredFlights method, providing all extracted search parameters
     * and result collection references. The invocation includes proper error button integration
     * for comprehensive error handling and user feedback.
     * </p>
     * <p>
     * Result presentation includes immediate result panel updates through the updateResultsPanel
     * method, ensuring that customers see search results immediately upon successful search
     * execution. The presentation provides responsive user experience and immediate feedback
     * for search operations throughout booking management workflows.
     * </p>
     * <p>
     * Error handling ensures that result panel updates occur even when validation errors
     * prevent search execution, maintaining interface consistency and providing proper
     * error message display through the FloatingMessage system integration.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and search coordination
     * @param controller the system controller providing access to flight-based booking search functionality and error handling services
     * @param searchButton the search button component for error message positioning and user feedback during validation failures
     */
    public void filteredFlightSearch(List<DisposableObject> callingObjects, Controller controller, JButton searchButton) {

        String origin = fromField.getText();
        String destination = toField.getText();
        LocalDate dateBefore = dateFrom.getDate();
        LocalDate dateAfter = dateTo.getDate();
        LocalTime timeBefore = timeFrom.getTime();
        LocalTime timeAfter = timeTo.getTime();

        if ((fromField.getText().isEmpty() && !toField.getText().isEmpty()) || (!fromField.getText().isEmpty() && toField.getText().isEmpty())) {

            new FloatingMessage("Se si specifica una città, vanno specificate entrambe!", searchButton, FloatingMessage.ERROR_MESSAGE);

        } else if ((dateBefore != null && dateAfter == null) || (dateBefore == null && dateAfter != null)) {

            new FloatingMessage("Errore nel range di date!", searchButton, FloatingMessage.ERROR_MESSAGE);

        } else if (dateBefore != null && dateAfter.isBefore(dateBefore)) {

            new FloatingMessage("La seconda data deve essere successiva alla prima!", searchButton, FloatingMessage.ERROR_MESSAGE);

        } else if ((timeBefore != null && timeAfter == null) || (timeBefore == null && timeAfter != null)) {

            new FloatingMessage("Errore nella fascia oraria!", searchButton, FloatingMessage.ERROR_MESSAGE);

        } else {

            if (controller != null) {

                bookingDates = new ArrayList<>();
                bookingStatus = new ArrayList<>();
                flightIds = new ArrayList<>();

                controller.searchBooksLoogedCustomerFilteredFlights(origin, destination, dateBefore, dateAfter, timeBefore, timeAfter, bookingDates, bookingStatus, flightIds, searchButton);

                updateResultsPanel(callingObjects, controller);

            }
            return;
        }

        updateResultsPanel(callingObjects, controller);
    }

    /**
     * Executes comprehensive passenger-based booking search operations with flexible personal information matching.
     * <p>
     * This public method performs sophisticated passenger-based booking searches using personal
     * information including first name, last name, passenger SSN (codice fiscale), and ticket
     * number as search criteria. The method provides flexible search capabilities that enable
     * customers to locate bookings using whatever passenger information they have available,
     * supporting both partial information searches and precise identification for enhanced
     * customer convenience and service efficiency.
     * </p>
     * <p>
     * The passenger search execution process includes:
     * </p>
     * <ul>
     *   <li><strong>Parameter Extraction:</strong> Comprehensive retrieval of all passenger search criteria from input fields</li>
     *   <li><strong>Flexible Search Logic:</strong> Accommodates partial information searches for enhanced customer convenience</li>
     *   <li><strong>Search Execution:</strong> Controller-coordinated search operations with proper data collection management</li>
     *   <li><strong>Result Presentation:</strong> Dynamic result panel updates with immediate user feedback and booking information display</li>
     *   <li><strong>Data Management:</strong> Proper collection initialization and synchronization for consistent result handling</li>
     * </ul>
     * <p>
     * Parameter extraction includes comprehensive retrieval of all passenger search criteria:
     * </p>
     * <ul>
     *   <li><strong>First Name:</strong> Passenger first name text from firstNameField input component</li>
     *   <li><strong>Last Name:</strong> Passenger surname text from lastNameField input component</li>
     *   <li><strong>Passenger SSN:</strong> Italian fiscal code text from passengerSSNField input component</li>
     *   <li><strong>Ticket Number:</strong> Unique ticket identifier text from ticketNumberField input component</li>
     * </ul>
     * <p>
     * Flexible search logic accommodates various search scenarios without requiring complete
     * passenger information specification. The method supports:
     * </p>
     * <ul>
     *   <li><strong>Partial Name Searches:</strong> First name only, last name only, or both names for flexible matching</li>
     *   <li><strong>Precise Identification:</strong> SSN-based searches for exact passenger identification</li>
     *   <li><strong>Direct Ticket Access:</strong> Ticket number-based searches for immediate booking location</li>
     *   <li><strong>Combined Criteria:</strong> Multiple search parameters for refined search results</li>
     * </ul>
     * <p>
     * Search execution includes controller-coordinated search operations when valid controller
     * reference is available. The execution process includes:
     * </p>
     * <ul>
     *   <li><strong>Data Collection Initialization:</strong> Creates new ArrayList instances for passenger search results</li>
     *   <li><strong>Controller Search Invocation:</strong> Calls searchBooksLoogedCustomerFilteredPassengers with all search parameters</li>
     *   <li><strong>Result Processing:</strong> Handles passenger search result data in synchronized collections</li>
     *   <li><strong>Interface Updates:</strong> Triggers result panel updates for immediate passenger booking display</li>
     * </ul>
     * <p>
     * Data collection initialization creates fresh ArrayList instances for bookingDates,
     * bookingStatus, and flightIds to ensure clean passenger search result storage without
     * contamination from previous search operations. The initialization provides proper data
     * isolation and consistent passenger booking result presentation.
     * </p>
     * <p>
     * Controller search invocation delegates actual passenger search operations to the
     * controller's searchBooksLoogedCustomerFilteredPassengers method, providing all extracted
     * passenger search parameters and result collection references. The invocation includes
     * proper error button integration for comprehensive error handling and user feedback.
     * </p>
     * <p>
     * Result processing ensures that passenger search results are properly stored in the
     * synchronized collection system, maintaining data consistency and enabling accurate
     * booking information presentation through the integrated result display components
     * throughout passenger-based booking management workflows.
     * </p>
     * <p>
     * Interface updates include immediate result panel updates through the updateResultsPanel
     * method, ensuring that customers see passenger booking search results immediately upon
     * search execution. The updates provide responsive user experience and immediate feedback
     * for passenger-based search operations throughout booking management workflows.
     * </p>
     * <p>
     * The method provides enhanced customer service capabilities by enabling flexible
     * passenger information searches that accommodate various customer knowledge scenarios,
     * from complete passenger information to partial details or unique identifiers,
     * supporting comprehensive booking location and customer assistance throughout
     * airport management system operations.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and search coordination
     * @param controller the system controller providing access to passenger-based booking search functionality and data management services
     * @param searchButton the search button component for error message positioning and user feedback during search operations
     */
    public void filteredPassengerSearch(List<DisposableObject> callingObjects, Controller controller, JButton searchButton) {

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String passengerSSN = passengerSSNField.getText();
        String ticketNumber = ticketNumberField.getText();

        if (controller != null) {

            bookingDates = new ArrayList<>();
            bookingStatus = new ArrayList<>();
            flightIds = new ArrayList<>();

            controller.searchBooksLoogedCustomerFilteredPassengers(firstName, lastName, passengerSSN, ticketNumber, bookingDates, bookingStatus, flightIds, searchButton);

            updateResultsPanel(callingObjects, controller);
        }
    }

    /**
     * Updates and refreshes the search results display panel with comprehensive scroll management and visual optimization.
     * <p>
     * This private method manages the dynamic updating of the search results display area by
     * creating new {@link SearchBookingResultPanel} instances with current search data and
     * configuring optimal scroll pane behavior for enhanced user experience during result
     * browsing and booking selection operations. The method ensures seamless result presentation
     * with proper scroll policies, optimized scrolling performance, and immediate visual updates
     * throughout booking search and management workflows.
     * </p>
     * <p>
     * The result panel update process includes:
     * </p>
     * <ul>
     *   <li><strong>Result Panel Creation:</strong> New SearchBookingResultPanel instantiation with current search data</li>
     *   <li><strong>Viewport Configuration:</strong> Scroll pane viewport updates with the new results panel</li>
     *   <li><strong>Border Management:</strong> Empty border application for clean visual presentation</li>
     *   <li><strong>Scroll Policy Configuration:</strong> Optimal scroll bar visibility policies for different content scenarios</li>
     *   <li><strong>Scroll Performance Optimization:</strong> Enhanced scrolling unit increments for smooth user interaction</li>
     *   <li><strong>Interface Refresh:</strong> Component revalidation and repainting for immediate visual updates</li>
     * </ul>
     * <p>
     * Result panel creation instantiates new SearchBookingResultPanel with comprehensive
     * parameter passing including calling objects hierarchy, controller reference, and all
     * current search result data collections (bookingDates, bookingStatus, flightIds).
     * The creation ensures that results display reflects the most current search data
     * with proper navigation integration and interactive booking management capabilities.
     * </p>
     * <p>
     * Viewport configuration updates the scroll pane's viewport to display the newly
     * created results panel, ensuring that customers see immediate result updates following
     * search operations. The viewport configuration provides seamless transitions between
     * different search result displays and maintains proper component hierarchy integration.
     * </p>
     * <p>
     * Border management applies empty border configuration to the scroll pane, ensuring
     * clean visual presentation without unnecessary border artifacts that could interfere
     * with the professional interface appearance. The border management maintains consistency
     * with the overall airport management system visual design standards.
     * </p>
     * <p>
     * Scroll policy configuration establishes optimal scroll bar visibility policies:
     * </p>
     * <ul>
     *   <li><strong>Vertical Scrolling:</strong> AS_NEEDED policy for automatic scroll bar display when content exceeds visible area</li>
     *   <li><strong>Horizontal Scrolling:</strong> AS_NEEDED policy for automatic horizontal scroll support when required</li>
     * </ul>
     * <p>
     * The AS_NEEDED scroll policies ensure that scroll bars appear only when necessary,
     * providing clean interface presentation for small result sets while automatically
     * enabling scrolling capabilities when search results exceed the available display
     * area, optimizing space utilization and user experience.
     * </p>
     * <p>
     * Scroll performance optimization configures enhanced vertical scrolling with 30-pixel
     * unit increments, providing smooth and responsive scrolling behavior during result
     * browsing operations. The optimized scrolling enhances user experience by enabling
     * precise navigation through search results with comfortable scrolling speeds.
     * </p>
     * <p>
     * Interface refresh includes comprehensive component revalidation and repainting to
     * ensure immediate visual updates following result panel changes. The refresh process
     * triggers proper layout recalculation and visual rendering to provide responsive
     * interface behavior and immediate user feedback during search result updates.
     * </p>
     * <p>
     * The method ensures that search result presentation maintains optimal user experience
     * through professional visual design, responsive scrolling behavior, and immediate
     * result display updates that support efficient booking location and management
     * throughout customer interaction with the airport management system.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and result panel integration
     * @param controller the system controller providing access to booking data and result management functionality
     */
    private void updateResultsPanel(List<DisposableObject> callingObjects, Controller controller) {

        SearchBookingResultPanel resultsPanel = new SearchBookingResultPanel(callingObjects, controller,
                                                                             bookingDates, bookingStatus, flightIds);

        resultsScrollPane.setViewportView(resultsPanel);

        resultsScrollPane.setBorder(BorderFactory.createEmptyBorder());

        resultsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        resultsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        resultsScrollPane.getVerticalScrollBar().setUnitIncrement(30);

        resultsScrollPane.revalidate();
        resultsScrollPane.repaint();
    }

    /**
     * Configures professional button appearance with consistent styling throughout the search interface.
     * <p>
     * This private method applies standardized button styling that ensures visual consistency
     * across all button components within the search interface. The method implements professional
     * appearance standards with focus management, background colors, typography, and sizing
     * that align with the airport management system's design principles and user experience
     * requirements for optimal interface presentation and user interaction.
     * </p>
     * <p>
     * The button appearance configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Focus Management:</strong> Disables focus painting for clean visual presentation</li>
     *   <li><strong>Background Styling:</strong> Light gray background color for professional interface integration</li>
     *   <li><strong>Typography Configuration:</strong> Bold Segoe UI font with appropriate sizing for optimal readability</li>
     *   <li><strong>Size Optimization:</strong> Consistent height specification with dynamic width adaptation</li>
     * </ul>
     * <p>
     * Focus management disables focus painting (setFocusPainted(false)) to prevent focus
     * rectangles from appearing around buttons during keyboard navigation, maintaining clean
     * visual presentation and professional interface appearance. The focus management ensures
     * consistent button appearance across different interaction methods and usage scenarios.
     * </p>
     * <p>
     * Background styling applies light gray background color (235, 235, 235) that provides
     * subtle visual definition while maintaining professional appearance and optimal integration
     * with the overall interface color scheme. The background color ensures proper contrast
     * and readability while supporting the airport management system's visual branding.
     * </p>
     * <p>
     * Typography configuration utilizes bold Segoe UI font with 14-point sizing for optimal
     * readability and professional presentation. The font configuration ensures consistent
     * typography throughout the search interface while maintaining excellent readability
     * across different display configurations and user interface scaling scenarios.
     * </p>
     * <p>
     * Size optimization establishes consistent 35-pixel height for all styled buttons while
     * maintaining dynamic width based on button content and preferred size calculations.
     * The size optimization ensures uniform button appearance throughout the interface
     * while accommodating varying button text lengths and content requirements.
     * </p>
     * <p>
     * The method supports the overall interface design consistency by providing standardized
     * button styling that enhances user experience through professional visual presentation,
     * clear visual hierarchy, and optimal interaction design throughout the booking search
     * and management interface components.
     * </p>
     *
     * @param button the JButton component to be styled with professional appearance configuration
     */
    private void setButtonApperance(JButton button) {

        button.setFocusPainted(false);
        button.setBackground(new Color(235, 235, 235));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(button.getPreferredSize().width, 35));

    }

    /**
     * Configures professional label appearance with consistent typography throughout the search interface.
     * <p>
     * This private method applies standardized label styling that ensures visual consistency
     * across all label components within the search interface. The method implements professional
     * typography standards with bold font configuration and appropriate sizing that align with
     * the airport management system's design principles and readability requirements for
     * optimal user interface presentation and information clarity.
     * </p>
     * <p>
     * The label appearance configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Typography Standardization:</strong> Bold Segoe UI font with 14-point sizing for optimal readability</li>
     *   <li><strong>Visual Hierarchy:</strong> Bold weight provides clear distinction between labels and input content</li>
     *   <li><strong>Readability Optimization:</strong> Appropriate font sizing for comfortable reading across display configurations</li>
     *   <li><strong>Interface Consistency:</strong> Uniform label appearance throughout all search interface components</li>
     * </ul>
     * <p>
     * Typography standardization utilizes bold Segoe UI font with 14-point sizing to ensure
     * excellent readability and professional presentation across all label elements within
     * the search interface. The font configuration maintains consistency with the overall
     * airport management system typography standards while providing clear text presentation
     * for various search criteria labels and interface guidance text.
     * </p>
     * <p>
     * Visual hierarchy establishment through bold font weight provides clear distinction
     * between label text and input field content, enhancing interface usability by enabling
     * users to quickly identify input field purposes and navigate through search criteria
     * specification with improved visual organization and information clarity.
     * </p>
     * <p>
     * Readability optimization ensures that label text remains clearly legible across
     * different display configurations, screen resolutions, and user interface scaling
     * scenarios. The 14-point font sizing provides optimal balance between space efficiency
     * and reading comfort for diverse user demographics and usage environments.
     * </p>
     * <p>
     * Interface consistency through uniform label styling creates cohesive visual presentation
     * throughout all search interface components, including filter panels, input field labels,
     * and section headings. The consistency enhances user experience by providing predictable
     * visual patterns and professional interface organization throughout booking search workflows.
     * </p>
     * <p>
     * The method supports the overall interface design coherence by ensuring that all textual
     * elements maintain professional appearance standards and contribute to the comprehensive
     * user experience optimization throughout the booking search and management interface
     * components within the airport management system.
     * </p>
     *
     * @param label the JLabel component to be styled with professional typography configuration
     */
    private void setLabelApperance(JLabel label) {

        label.setFont(new Font("Segoe UI", Font.BOLD, 14));

    }

    /**
     * Returns the current search performance status for interface state management and navigation coordination.
     * <p>
     * This public method provides access to the search performance status flag that indicates
     * whether search operations have been executed within the current session. The method
     * supports interface state management, navigation coordination, and search result persistence
     * throughout customer booking management workflows by enabling other components to determine
     * search execution status and adjust their behavior accordingly.
     * </p>
     * <p>
     * Search performance status tracking includes:
     * </p>
     * <ul>
     *   <li><strong>Execution Tracking:</strong> Boolean flag indicating whether search operations have been performed</li>
     *   <li><strong>State Management:</strong> Supports interface behavior coordination based on search execution status</li>
     *   <li><strong>Navigation Support:</strong> Enables proper navigation state management during interface transitions</li>
     *   <li><strong>Result Persistence:</strong> Supports search result persistence and context maintenance across navigation operations</li>
     * </ul>
     * <p>
     * Execution tracking provides essential information about search operation status that
     * enables other components to understand whether the search interface has been actively
     * used for booking searches. The tracking supports intelligent interface behavior and
     * proper workflow coordination throughout customer booking management operations.
     * </p>
     * <p>
     * State management utilizes the search performance status to enable proper interface
     * behavior coordination, including result display management, navigation control states,
     * and user experience optimization based on search execution history within the current
     * session and interface usage patterns.
     * </p>
     * <p>
     * Navigation support enables other components to make informed decisions about interface
     * transitions, back navigation behavior, and context preservation based on search execution
     * status. The support ensures smooth navigation experiences and proper state coordination
     * throughout customer booking management workflows.
     * </p>
     * <p>
     * Result persistence support enables proper search context maintenance during navigation
     * operations, ensuring that search results and interface states are properly preserved
     * and restored when customers navigate between different booking management areas and
     * return to search functionality.
     * </p>
     *
     * @return true if search operations have been performed within the current session, false otherwise
     */
    public boolean isSearchPerformed() {
        return searchPerformed;
    }

    /**
     * Returns the search button component for external access and interface coordination.
     * <p>
     * This public method provides access to the primary search button component, enabling
     * external components to coordinate with search functionality, manage button states,
     * and integrate search operations with broader interface workflows. The method supports
     * error handling integration, interface state management, and comprehensive user experience
     * coordination throughout booking search and management operations within the airport
     * management system.
     * </p>
     * <p>
     * Search button access enables:
     * </p>
     * <ul>
     *   <li><strong>Error Integration:</strong> External error handling systems can position error messages relative to the search button</li>
     *   <li><strong>State Management:</strong> Interface components can manage button enabled/disabled states based on system conditions</li>
     *   <li><strong>Workflow Coordination:</strong> External systems can coordinate search operations with broader booking management workflows</li>
     *   <li><strong>Event Handling:</strong> Additional event listeners can be attached for specialized interface behaviors</li>
     * </ul>
     * <p>
     * Error integration provides the search button reference to error handling systems,
     * particularly the {@link FloatingMessage} system, enabling proper error message
     * positioning and visual coordination during validation failures and search operation
     * errors. The integration ensures consistent user feedback and professional error
     * presentation throughout booking search workflows.
     * </p>
     * <p>
     * State management enables external components to control search button availability
     * based on system conditions, user permissions, or interface states. The state management
     * supports comprehensive workflow control and user experience optimization throughout
     * different booking management scenarios and operational contexts.
     * </p>
     * <p>
     * Workflow coordination allows external systems to integrate search operations with
     * broader booking management workflows, including navigation systems, user session
     * management, and comprehensive customer service operations. The coordination ensures
     * seamless integration within the overall airport management system architecture.
     * </p>
     * <p>
     * Event handling support enables attachment of additional event listeners for specialized
     * interface behaviors, system integration, and custom workflow requirements that extend
     * beyond the standard search functionality while maintaining compatibility with existing
     * search operations and user experience standards.
     * </p>
     *
     * @return the JButton component serving as the primary search execution control
     */
    public JButton getSearchButton() {
        return searchButton;
    }

    /**
     * Returns the current active filter mode for search operation routing and interface state coordination.
     * <p>
     * This public method provides access to the current active filter mode that determines
     * search operation behavior and interface presentation. The method supports external
     * components in understanding search interface state, coordinating with search operations,
     * and maintaining proper workflow integration throughout booking search and management
     * operations within the airport management system.
     * </p>
     * <p>
     * Active filter mode access includes:
     * </p>
     * <ul>
     *   <li><strong>State Identification:</strong> Returns current filter mode string for interface state determination</li>
     *   <li><strong>Search Routing:</strong> Enables external components to understand search operation routing logic</li>
     *   <li><strong>Interface Coordination:</strong> Supports proper interface behavior based on selected filter mode</li>
     *   <li><strong>Workflow Integration:</strong> Enables external systems to coordinate with search filter selection and behavior</li>
     * </ul>
     * <p>
     * State identification provides the current activeFilter string value that indicates
     * which search mode is currently selected or if no filter mode has been chosen. The
     * possible return values include:
     * </p>
     * <ul>
     *   <li><strong>"NONE":</strong> No filter mode selected, search operations will display error message</li>
     *   <li><strong>"FLIGHT":</strong> Flight-based search mode active, searches use flight criteria</li>
     *   <li><strong>"PASSENGER":</strong> Passenger-based search mode active, searches use passenger criteria</li>
     * </ul>
     * <p>
     * Search routing information enables external components to understand how search
     * operations will be processed based on the current filter mode, supporting intelligent
     * interface behavior and proper workflow coordination throughout booking search and
     * management operations.
     * </p>
     * <p>
     * Interface coordination support enables external components to adjust their behavior
     * and presentation based on the active filter mode, ensuring consistent user experience
     * and proper interface state management throughout the booking search workflow and
     * related customer service operations.
     * </p>
     * <p>
     * Workflow integration enables external systems to coordinate their operations with
     * the search interface state, including navigation systems, help systems, and customer
     * service tools that need to understand current search context and filter selection
     * for optimal user experience and system integration.
     * </p>
     *
     * @return the current active filter mode string indicating search operation routing behavior
     */
    public String getActiveFilter() {
        return activeFilter;
    }

}