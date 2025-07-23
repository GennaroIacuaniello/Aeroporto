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
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Comprehensive flight search panel providing advanced flight discovery capabilities with professional interface design for the airport management system.
 * <p>
 * This class extends {@link JPanel} to provide a dedicated flight search interface that enables customers
 * and administrators to search for available flights using multiple criteria including origin/destination
 * cities, date ranges, and time ranges. The SearchFlightPanel serves as the primary flight discovery
 * component within the airport management system, offering sophisticated search functionality with
 * real-time result display and seamless integration with booking workflows.
 * </p>
 * <p>
 * The SearchFlightPanel class provides comprehensive flight search capabilities including:
 * </p>
 * <ul>
 *   <li><strong>Advanced Search Criteria:</strong> Multi-parameter flight search with city, date, and time filtering options</li>
 *   <li><strong>Dual Interface Support:</strong> Separate result panels for customer and administrator workflows with appropriate functionality</li>
 *   <li><strong>Real-time Validation:</strong> Comprehensive input validation with Italian-language error messaging and user guidance</li>
 *   <li><strong>Interactive Controls:</strong> Quick-fill buttons for common Naples-based flight searches with automatic field population</li>
 *   <li><strong>Professional Styling:</strong> Consistent visual design with airport management system branding and typography standards</li>
 *   <li><strong>Result Management:</strong> Dynamic result panel updates with scroll support and optimized display performance</li>
 *   <li><strong>State Persistence:</strong> Search state maintenance for navigation restoration and workflow continuity</li>
 * </ul>
 * <p>
 * The interface is designed with user experience optimization, providing customers and administrators with:
 * </p>
 * <ul>
 *   <li><strong>Intuitive Search Interface:</strong> Symmetric dual-column layout with logical field organization and clear labeling</li>
 *   <li><strong>Flexible Search Options:</strong> Support for partial criteria specification and intelligent default handling</li>
 *   <li><strong>Clear Visual Feedback:</strong> Professional error messaging and result display with immediate user feedback</li>
 *   <li><strong>Efficient Navigation:</strong> Quick-access buttons for common flight search patterns and route selection</li>
 *   <li><strong>Responsive Design:</strong> Adaptive interface that maintains functionality across different window configurations</li>
 *   <li><strong>Comprehensive Results:</strong> Detailed flight information display with integrated booking and management capabilities</li>
 * </ul>
 * <p>
 * Layout architecture utilizes {@link GridBagLayout} for precise component positioning and optimal
 * space utilization. The interface maintains a structured three-section layout with parameter input
 * controls at the top, centralized search button for action initiation, and scrollable results area
 * for comprehensive flight information display and interaction.
 * </p>
 * <p>
 * Search functionality includes sophisticated validation logic that ensures data consistency and
 * provides clear user guidance through Italian-language error messages. The validation covers:
 * </p>
 * <ul>
 *   <li><strong>City Specification Logic:</strong> Ensures both origin and destination are specified when either is provided</li>
 *   <li><strong>Date Range Validation:</strong> Verifies proper date range specification and chronological order consistency</li>
 *   <li><strong>Time Range Validation:</strong> Ensures complete time range specification when temporal filtering is used</li>
 *   <li><strong>Input Completeness:</strong> Validates that required search parameters are properly formatted and accessible</li>
 * </ul>
 * <p>
 * The dual interface support enables specialized result presentation for different user types through
 * integration with {@link SearchFlightResultPanel} for customer workflows and {@link SearchFlightResultPanelAdmin}
 * for administrative operations. The interface automatically detects user authentication level and
 * provides appropriate result display with corresponding functionality and interaction capabilities.
 * </p>
 * <p>
 * Interactive controls include specialized quick-fill buttons that enable efficient flight search
 * setup for common Naples-based routes. The "Cerca voli in arrivo" (Search Arriving Flights) and
 * "Cerca voli in partenza" (Search Departing Flights) buttons automatically configure the search
 * interface for flights arriving at or departing from Naples, streamlining the search process
 * for the most common flight search scenarios within the airport management system.
 * </p>
 * <p>
 * Professional styling utilizes consistent typography through Segoe UI font family with appropriate
 * sizing for different interface elements. The styling includes specialized appearance methods for
 * buttons and labels that ensure visual consistency throughout the flight search interface while
 * maintaining optimal readability and professional presentation standards.
 * </p>
 * <p>
 * Result management includes sophisticated display optimization through scrollable result panels
 * with dynamic content updates and performance enhancements. The result system supports both
 * empty state messaging and comprehensive flight information display with proper scroll behavior,
 * unit increment optimization, and visual border management for professional presentation.
 * </p>
 * <p>
 * State persistence enables search workflow continuity through the {@link #isSearchPerformed()}
 * method and search button access through {@link #getSearchButton()}, supporting integration with
 * navigation restoration systems and maintaining search context across different areas of the
 * airport management system interface.
 * </p>
 * <p>
 * The class integrates seamlessly with the broader airport management system through comprehensive
 * {@link Controller} integration, enabling access to flight search services, user authentication
 * management, and error handling coordination. The integration supports both customer and
 * administrative workflows while maintaining proper separation of concerns and security protocols.
 * </p>
 * <p>
 * Data management utilizes extensive ArrayList collections for flight information storage including
 * flight IDs, company names, dates, times, delays, status information, seat availability, and
 * destination cities. The data management ensures efficient search result handling and supports
 * dynamic result updating for optimal user experience throughout flight discovery operations.
 * </p>
 * <p>
 * Border management includes sophisticated compound border system combining line borders for visual
 * definition with empty borders for proper content padding. The border configuration (40, 50, 40, 50)
 * provides optimal visual separation and content organization while maintaining professional
 * appearance standards consistent with airport management system design principles.
 * </p>
 * <p>
 * The SearchFlightPanel serves as a critical component of the flight discovery ecosystem,
 * providing essential search capabilities that enable efficient flight location and booking
 * while maintaining interface consistency, user experience quality, and operational effectiveness
 * throughout customer and administrative interactions with the airport management system.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see JPanel
 * @see Controller
 * @see SearchFlightResultPanel
 * @see SearchFlightResultPanelAdmin
 * @see DatePicker
 * @see TimePicker
 * @see GridBagLayout
 * @see Constraints
 */
public class SearchFlightPanel extends JPanel {

    /**
     * Scrollable container for flight search results display and navigation.
     * <p>
     * This JScrollPane component provides the scrollable container for flight search results,
     * enabling customers and administrators to browse through search results with proper
     * scroll behavior and performance optimization. The scroll pane supports both vertical
     * and horizontal scrolling as needed while maintaining optimal display performance
     * through enhanced scroll unit increments and professional visual presentation.
     * </p>
     */
    private JScrollPane resultsScrollPane;

    /**
     * Layout constraints utility for precise component positioning throughout the search interface.
     * <p>
     * This final Constraints helper object provides standardized GridBagConstraints
     * configuration for optimal component layout and positioning. The constraints utility
     * ensures consistent spacing, alignment, and component organization throughout the
     * flight search interface while supporting dynamic layout management and responsive
     * design principles for different window configurations and usage scenarios.
     * </p>
     */
    private final Constraints constraints;

    /**
     * Origin city input field for departure location specification in flight searches.
     * <p>
     * This JTextField component enables users to specify the departure city for flight
     * searches, configured with professional styling (Segoe UI, 16pt, 15 characters width)
     * and integrated with validation logic to ensure consistent search parameter specification.
     * The field supports both manual entry and automatic population through quick-fill buttons
     * for common Naples-based flight search scenarios.
     * </p>
     */
    private JTextField fromField;
    
    /**
     * Destination city input field for arrival location specification in flight searches.
     * <p>
     * This JTextField component enables users to specify the destination city for flight
     * searches, configured with professional styling (Segoe UI, 16pt, 15 characters width)
     * and integrated with validation logic to ensure consistent search parameter specification.
     * The field supports both manual entry and automatic population through quick-fill buttons
     * for common Naples-based flight search scenarios.
     * </p>
     */
    private JTextField toField;
    
    /**
     * Start date picker for flight search date range specification.
     * <p>
     * This DatePicker component enables users to specify the beginning date for flight
     * search date ranges, configured with professional typography (Segoe UI, 14pt) and
     * integrated with comprehensive validation logic to ensure proper date range specification
     * and chronological order consistency throughout flight search operations.
     * </p>
     */
    private DatePicker dateFrom;
    
    /**
     * End date picker for flight search date range specification.
     * <p>
     * This DatePicker component enables users to specify the ending date for flight
     * search date ranges, configured with professional typography (Segoe UI, 14pt) and
     * integrated with comprehensive validation logic to ensure proper date range specification
     * and chronological order consistency throughout flight search operations.
     * </p>
     */
    private DatePicker dateTo;
    
    /**
     * Start time picker for flight search time range specification.
     * <p>
     * This TimePicker component enables users to specify the beginning time for flight
     * search time ranges, configured with professional typography (Segoe UI, 14pt) and
     * integrated with validation logic to ensure complete time range specification when
     * temporal filtering is used in flight search operations.
     * </p>
     */
    private TimePicker timeFrom;
    
    /**
     * End time picker for flight search time range specification.
     * <p>
     * This TimePicker component enables users to specify the ending time for flight
     * search time ranges, configured with professional typography (Segoe UI, 14pt) and
     * integrated with validation logic to ensure complete time range specification when
     * temporal filtering is used in flight search operations.
     * </p>
     */
    private TimePicker timeTo;

    /**
     * Primary search execution button with professional styling and comprehensive event handling.
     * <p>
     * This JButton component serves as the main search action trigger, configured with
     * professional styling including blue background (0, 120, 215), white text, bold
     * Segoe UI font (18pt), hand cursor, and optimal sizing (200x50). The button integrates
     * with comprehensive search validation and execution logic while providing clear
     * visual feedback for user interaction throughout flight search operations.
     * </p>
     */
    private JButton searchButton;

    /**
     * Collection of flight identifiers for search result management and correlation.
     * <p>
     * This ArrayList stores unique flight identifiers retrieved from search operations,
     * enabling proper result correlation, detailed flight access, and integration with
     * booking workflows. The collection is synchronized with other flight data collections
     * to maintain consistent result presentation and support dynamic result updating
     * throughout flight search and selection operations.
     * </p>
     */
    ArrayList<String> ids = new ArrayList<>();
    
    /**
     * Collection of airline company names for flight search result display.
     * <p>
     * This ArrayList stores airline company names associated with flight search results,
     * providing essential flight information for user decision-making and result presentation.
     * The collection supports dynamic result updating and integrates with result display
     * panels for comprehensive flight information presentation throughout search workflows.
     * </p>
     */
    ArrayList<String> companyNames = new ArrayList<>();
    
    /**
     * Collection of flight dates for temporal information display and organization.
     * <p>
     * This ArrayList stores flight dates retrieved from search operations, providing
     * essential temporal information for flight scheduling and user travel planning.
     * The collection supports date-based result organization and integrates with
     * comprehensive result display systems for optimal user experience throughout
     * flight search and selection operations.
     * </p>
     */
    ArrayList<Date> dates = new ArrayList<>();
    
    /**
     * Collection of flight departure times for scheduling information display.
     * <p>
     * This ArrayList stores departure time information for flight search results,
     * providing essential scheduling details for user travel planning and flight
     * selection. The collection supports time-based result organization and integrates
     * with result display formatting for professional time presentation throughout
     * flight search operations.
     * </p>
     */
    ArrayList<Time> departureTimes = new ArrayList<>();
    
    /**
     * Collection of flight arrival times for comprehensive scheduling information.
     * <p>
     * This ArrayList stores arrival time information for flight search results,
     * providing complete flight scheduling details for user travel planning and
     * decision-making. The collection supports comprehensive result display and
     * integrates with professional time formatting for optimal presentation
     * throughout flight search and selection workflows.
     * </p>
     */
    ArrayList<Time> arrivalTimes = new ArrayList<>();
    
    /**
     * Collection of flight delay information for current status presentation.
     * <p>
     * This ArrayList stores delay information in minutes for flight search results,
     * providing real-time status updates for user awareness and travel planning.
     * The collection supports dynamic status display with proper formatting (delay
     * minutes or "--" for no delay) and integrates with comprehensive result
     * presentation systems throughout flight search operations.
     * </p>
     */
    ArrayList<Integer> delays = new ArrayList<>();
    
    /**
     * Collection of flight status information for operational state presentation.
     * <p>
     * This ArrayList stores current flight status information including states such as
     * PROGRAMMED, CANCELLED, DELAYED, ABOUT_TO_DEPART, DEPARTED, ABOUT_TO_ARRIVE, and
     * LANDED. The collection supports Italian localization for user-friendly status
     * display and integrates with result presentation systems for comprehensive
     * flight information throughout search and selection operations.
     * </p>
     */
    ArrayList<String> status = new ArrayList<>();
    
    /**
     * Collection of maximum seat capacity information for flight availability assessment.
     * <p>
     * This ArrayList stores maximum seat capacity for each flight in search results,
     * providing essential capacity information for availability assessment and booking
     * decision-making. The collection integrates with seat availability calculations
     * and supports comprehensive result display for user awareness throughout
     * flight search and booking workflows.
     * </p>
     */
    ArrayList<Integer> maxSeats = new ArrayList<>();
    
    /**
     * Collection of available seat counts for real-time availability information.
     * <p>
     * This ArrayList stores current available seat counts for flight search results,
     * providing real-time availability information for booking decisions and user
     * travel planning. The collection supports dynamic availability display and
     * integrates with booking workflow validation to ensure accurate availability
     * presentation throughout flight search and selection operations.
     * </p>
     */
    ArrayList<Integer> freeSeats = new ArrayList<>();
    
    /**
     * Collection of destination city names for route information display.
     * <p>
     * This ArrayList stores destination city names for flight search results,
     * providing essential route information for travel planning and flight selection.
     * The collection supports route-based result organization and integrates with
     * directional route display formatting (including Naples integration) for
     * comprehensive flight information throughout search operations.
     * </p>
     */
    ArrayList<String> cities = new ArrayList<>();

    /**
     * Search execution state tracking for navigation restoration and workflow continuity.
     * <p>
     * This boolean flag tracks whether a search operation has been performed, enabling
     * proper navigation restoration and interface state management throughout customer
     * and administrative workflows. The flag supports search state persistence and
     * integrates with navigation systems for seamless user experience across different
     * areas of the airport management system.
     * </p>
     */
    private boolean searchPerformed = false;

    /**
     * Constructs a new SearchFlightPanel with comprehensive flight search functionality and professional interface design.
     * <p>
     * This constructor initializes the complete flight search interface by establishing layout
     * management, configuring visual styling, and setting up comprehensive search functionality
     * for customer and administrative flight discovery operations. The constructor creates a
     * fully functional search panel ready for immediate integration within flight search workflows
     * with support for advanced search criteria, real-time validation, and professional presentation.
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
     * the search interface. The layout supports dynamic component organization and maintains
     * consistent visual presentation across different usage scenarios and window configurations.
     * </p>
     * <p>
     * Visual styling includes white background configuration for optimal content readability
     * and professional appearance that integrates seamlessly with the airport management
     * system's visual design standards. The styling ensures consistent branding and user
     * experience throughout flight search and discovery interfaces.
     * </p>
     * <p>
     * Constraints setup initializes the {@link Constraints} utility object that provides
     * standardized {@link GridBagConstraints} configuration throughout the search interface.
     * The constraints ensure consistent component spacing, alignment, and positioning for
     * optimal visual organization and user experience throughout flight search operations.
     * </p>
     * <p>
     * Border management creates a sophisticated compound border system combining line borders
     * for visual definition with empty borders for proper content padding. The border
     * configuration includes light gray line borders (200, 200, 200) and comprehensive
     * padding (40, 50, 40, 50) that provides optimal visual separation and content organization.
     * </p>
     * <p>
     * Component assembly delegates to the {@link #setComponents(List, Controller)} method
     * for complete search interface creation including parameter input panels, search buttons,
     * result display areas, and validation integration. The assembly process ensures proper
     * component initialization and event handling setup throughout the flight search interface.
     * </p>
     * <p>
     * The constructor establishes a fully functional flight search panel that provides
     * comprehensive search capabilities, professional visual presentation, and seamless
     * integration with the airport management system's flight discovery and booking workflows
     * for both customer and administrative usage scenarios.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and workflow coordination
     * @param controller the system controller providing access to flight search services and comprehensive system functionality
     */
    public SearchFlightPanel(List<DisposableObject> callingObjects, Controller controller) {

        super();

        this.setLayout(new GridBagLayout());
        this.setBackground(Color.WHITE);

        this.constraints = new Constraints();

        Border lineBorder = BorderFactory.createLineBorder(new Color(200, 200, 200));
        Border emptyBorder = BorderFactory.createEmptyBorder(40, 50, 40, 50);

        this.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));

        setComponents(callingObjects, controller);
    }

    /**
     * Configures and assembles all flight search interface components with comprehensive layout management and functionality integration.
     * <p>
     * This private method establishes the complete flight search interface by creating and positioning
     * all necessary components including parameter input panels, search execution buttons, and result
     * display areas. The method handles comprehensive layout management through GridBagConstraints
     * configuration, ensures proper component integration with controller systems, and establishes
     * optimal user experience through professional spacing and component organization throughout
     * flight search and discovery operations.
     * </p>
     * <p>
     * The component assembly process includes:
     * </p>
     * <ul>
     *   <li><strong>Parameter Panel Creation:</strong> Symmetric form panel establishment with flight search criteria input fields</li>
     *   <li><strong>Search Button Setup:</strong> Primary search button creation with comprehensive event handling and professional styling</li>
     *   <li><strong>Results Container Configuration:</strong> Scroll pane establishment for dynamic result display and optimal navigation</li>
     *   <li><strong>Layout Management:</strong> Precise component positioning using GridBagConstraints for optimal interface organization</li>
     *   <li><strong>Controller Integration:</strong> Error button configuration and initial result panel setup for comprehensive functionality</li>
     *   <li><strong>Interface Initialization:</strong> Initial result panel update for proper interface state and user experience optimization</li>
     * </ul>
     * <p>
     * Parameter panel creation utilizes the {@link #createSymmetricFormPanel()} method to establish
     * the comprehensive input interface with dual-column layout, professional styling, and integrated
     * validation support. The panel includes origin/destination fields, date range selectors, time
     * range pickers, and quick-fill buttons for common Naples-based flight search scenarios.
     * </p>
     * <p>
     * Search button setup delegates to the {@link #createSearchButton(List, Controller)} method
     * for comprehensive button configuration including professional styling, event handler integration,
     * and search execution coordination. The button serves as the primary action trigger for flight
     * search operations with integrated validation and error handling capabilities.
     * </p>
     * <p>
     * Results container configuration establishes the JScrollPane for dynamic result display
     * with optimal scroll behavior and performance optimization. The container supports both
     * customer and administrative result panels with appropriate functionality and professional
     * presentation throughout flight search and selection workflows.
     * </p>
     * <p>
     * Layout management utilizes comprehensive GridBagConstraints configuration to ensure optimal
     * component positioning and spacing throughout the search interface:
     * </p>
     * <ul>
     *   <li><strong>Parameters Panel:</strong> Horizontal expansion at top (row 0) with NORTH alignment for optimal input accessibility</li>
     *   <li><strong>Search Button:</strong> Center positioning (row 1) with 30-pixel vertical margins for clear action identification</li>
     *   <li><strong>Results Container:</strong> Full expansion (row 2) with BOTH fill for comprehensive result display and navigation</li>
     * </ul>
     * <p>
     * Component positioning ensures that the parameter panel utilizes horizontal space efficiently
     * while maintaining optimal input field accessibility, the search button is prominently
     * positioned for clear action identification, and the results container maximizes available
     * space for comprehensive flight information display and user interaction.
     * </p>
     * <p>
     * Controller integration includes error button configuration through the controller's
     * setErrorButton method, enabling comprehensive error handling and user feedback throughout
     * flight search operations. The integration ensures proper error messaging coordination
     * and validation feedback for optimal user experience during search execution.
     * </p>
     * <p>
     * Interface initialization includes initial result panel update through the
     * {@link #updateResultsPanel(List, Controller, boolean)} method with ifSearched set to false,
     * establishing proper initial interface state and preparing the results container for
     * dynamic content updates throughout flight search and discovery operations.
     * </p>
     * <p>
     * The method establishes a fully functional flight search interface ready for immediate
     * user interaction, providing comprehensive flight discovery capabilities, professional
     * visual presentation, and seamless integration with the airport management system's
     * flight search and booking workflows for both customer and administrative usage scenarios.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and search coordination
     * @param controller the system controller providing access to flight search services and comprehensive error handling functionality
     */
    private void setComponents(List<DisposableObject> callingObjects, Controller controller) {

        JPanel parametersPanel = createSymmetricFormPanel();

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

        controller.setErrorButton(searchButton);
        updateResultsPanel(callingObjects, controller, false);
    }

        /**
     * Creates and configures the symmetric form panel with dual-column layout for comprehensive flight search criteria input.
     * <p>
     * This private method establishes the primary input interface for flight search operations by creating
     * a sophisticated dual-panel layout with comprehensive search criteria fields and quick-fill buttons
     * for common Naples-based flight searches. The method provides symmetric left and right panels with
     * matching functionality and professional styling throughout the flight search parameter specification
     * interface for optimal user experience and efficient flight discovery workflows.
     * </p>
     * <p>
     * The symmetric form panel creation includes:
     * </p>
     * <ul>
     *   <li><strong>Container Architecture:</strong> GridLayout-based container with dual panels and professional spacing</li>
     *   <li><strong>Left Panel Configuration:</strong> Origin fields, date range selection, and arriving flights quick-fill button</li>
     *   <li><strong>Right Panel Configuration:</strong> Destination fields, time range selection, and departing flights quick-fill button</li>
     *   <li><strong>Component Styling:</strong> Consistent typography and professional appearance throughout all input elements</li>
     *   <li><strong>Layout Management:</strong> Precise GridBagConstraints positioning for optimal component organization</li>
     *   <li><strong>Event Handling:</strong> Quick-fill button functionality for Naples-based flight search automation</li>
     * </ul>
     * <p>
     * Container architecture utilizes a main GridLayout container (1 row, 2 columns, 40-pixel gap)
     * that houses left and right panels configured with GridBagLayout for precise component positioning.
     * All panels use transparent backgrounds to maintain visual integration with the parent container
     * and consistent interface design throughout the flight search application.
     * </p>
     * <p>
     * Left panel configuration includes comprehensive origin flight search functionality:
     * </p>
     * <ul>
     *   <li><strong>Arriving Flights Button:</strong> "Cerca voli in arrivo" quick-fill for flights arriving at Naples</li>
     *   <li><strong>Origin Field:</strong> "Da:" label with 15-character text field for departure city specification</li>
     *   <li><strong>Date Range Selection:</strong> "Range date:" label with DatePicker components and visual separator</li>
     *   <li><strong>Professional Styling:</strong> Consistent typography and spacing throughout all components</li>
     * </ul>
     * <p>
     * Right panel configuration provides symmetric destination functionality:
     * </p>
     * <ul>
     *   <li><strong>Departing Flights Button:</strong> "Cerca voli in partenza" quick-fill for flights departing from Naples</li>
     *   <li><strong>Destination Field:</strong> "A:" label with 15-character text field for arrival city specification</li>
     *   <li><strong>Time Range Selection:</strong> "Fascia oraria:" label with TimePicker components and visual separator</li>
     *   <li><strong>Matching Layout:</strong> Symmetric positioning and styling to match left panel organization</li>
     * </ul>
     * <p>
     * Component styling includes comprehensive typography configuration through {@link #setLabelApperance(JLabel)}
     * and {@link #setButtonApperance(JButton)} methods for consistent visual presentation. Text fields
     * are configured with Segoe UI font (plain, 16pt) and 15-character width, while date/time pickers
     * use Segoe UI font (plain, 14pt) for optimal readability and professional interface standards.
     * </p>
     * <p>
     * Layout management utilizes sophisticated GridBagConstraints configuration for precise component
     * positioning with appropriate weights, anchoring, and insets. The layout ensures optimal space
     * distribution with 0.5f weight for date/time picker components and proper alignment throughout
     * the dual-panel interface structure.
     * </p>
     * <p>
     * Event handling includes comprehensive ActionListener implementation for quick-fill buttons:
     * </p>
     * <ul>
     *   <li><strong>Arriving Button:</strong> Sets destination to "Napoli", clears origin field, and focuses origin for user input</li>
     *   <li><strong>Departing Button:</strong> Sets origin to "Napoli", clears destination field, and focuses destination for user input</li>
     * </ul>
     * <p>
     * Quick-fill functionality streamlines common flight search scenarios by automatically populating
     * Naples as either origin or destination based on user selection, enabling efficient search setup
     * for the most frequent airport management system usage patterns while maintaining flexibility
     * for custom route specifications.
     * </p>
     * <p>
     * Visual separators ("--") are positioned between date range and time range components to provide
     * clear visual indication of range specifications while maintaining professional interface
     * presentation and intuitive user experience throughout flight search parameter configuration.
     * </p>
     * <p>
     * The method returns a fully configured symmetric form panel ready for integration into the
     * flight search interface, providing comprehensive input capabilities with professional styling
     * and efficient workflow support for both customer and administrative flight discovery operations
     * within the airport management system.
     * </p>
     *
     * @return the configured symmetric form panel with dual-column layout and comprehensive flight search input capabilities
     */
    private JPanel createSymmetricFormPanel() {

        JButton departingButton;
        JButton arrivingButton;
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

        arrivingButton = new JButton("Cerca voli in arrivo");
        setButtonApperance(arrivingButton);

        constraints.setConstraints(0, 0, 4, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.CENTER, 1.0f, 0.0f, new Insets(0, 0, 35, 0));
        leftPanel.add(arrivingButton, constraints.getGridBagConstraints());

        fromLabel = new JLabel("Da:");
        setLabelApperance(fromLabel);

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(fromLabel, constraints.getGridBagConstraints());

        fromField = new JTextField(15);
        fromField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        constraints.setConstraints(1, 1, 3, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(0, 0, 30, 0));
        leftPanel.add(fromField, constraints.getGridBagConstraints());

        dateLabel = new JLabel("Range date:");
        setLabelApperance(dateLabel);

        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(dateLabel, constraints.getGridBagConstraints());

        dateFrom = new DatePicker();
        dateFrom.getComponentDateTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));

        constraints.setConstraints(1, 2, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.CENTER, 0.5f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(dateFrom, constraints.getGridBagConstraints());

        dateSep = new JLabel("--");
        setLabelApperance(dateSep);

        constraints.setConstraints(2, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        leftPanel.add(dateSep, constraints.getGridBagConstraints());

        dateTo = new DatePicker();
        dateTo.getComponentDateTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));

        constraints.setConstraints(3, 2, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.CENTER, 0.5f, 0.0f, new Insets(0, 0, 30, 0));
        leftPanel.add(dateTo, constraints.getGridBagConstraints());


        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);

        departingButton = new JButton("Cerca voli in partenza");
        setButtonApperance(departingButton);

        constraints.setConstraints(0, 0, 4, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.CENTER, 1.0f, 0.0f, new Insets(0, 0, 35, 0));
        rightPanel.add(departingButton, constraints.getGridBagConstraints());

        toLabel = new JLabel("A:");
        setLabelApperance(toLabel);

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(toLabel, constraints.getGridBagConstraints());

        toField = new JTextField(15);
        toField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        constraints.setConstraints(1, 1, 3, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(0, 0, 30, 0));
        rightPanel.add(toField, constraints.getGridBagConstraints());

        timeLabel = new JLabel("Fascia oraria:");
        setLabelApperance(timeLabel);

        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(timeLabel, constraints.getGridBagConstraints());

        timeFrom = new TimePicker();
        timeFrom.getComponentTimeTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));

        constraints.setConstraints(1, 2, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.CENTER, 0.5f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(timeFrom, constraints.getGridBagConstraints());

        timeSep = new JLabel("--");
        setLabelApperance(timeSep);

        constraints.setConstraints(2, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER, 0.0f, 0.0f, new Insets(0, 0, 30, 10));
        rightPanel.add(timeSep, constraints.getGridBagConstraints());

        timeTo = new TimePicker();
        timeTo.getComponentTimeTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));

        constraints.setConstraints(3, 2, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 10, GridBagConstraints.CENTER, 0.5f, 0.0f, new Insets(0, 0, 30, 0));
        rightPanel.add(timeTo, constraints.getGridBagConstraints());

        container.add(leftPanel);
        container.add(rightPanel);


        arrivingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                toField.setText("Napoli");
                fromField.setText("");
                fromField.requestFocusInWindow();

            }
        });

        departingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                fromField.setText("Napoli");
                toField.setText("");
                toField.requestFocusInWindow();

            }
        });

        return container;
    }

    /**
     * Creates and configures the primary search execution button with comprehensive styling and event handling for flight search operations.
     * <p>
     * This private method establishes the main search action trigger for flight search operations by creating
     * a professionally styled button with comprehensive event handling integration and optimal visual presentation.
     * The method configures the search button with airport management system branding, user interaction feedback,
     * and complete ActionListener functionality that coordinates with the search validation and execution workflow
     * throughout customer and administrative flight discovery operations.
     * </p>
     * <p>
     * The search button creation includes:
     * </p>
     * <ul>
     *   <li><strong>Visual Styling:</strong> Professional button appearance with blue background, white text, and bold typography</li>
     *   <li><strong>Interactive Elements:</strong> Hand cursor configuration and focus painting disabled for clean presentation</li>
     *   <li><strong>Size Configuration:</strong> Optimal button dimensions (200x50) for clear action identification</li>
     *   <li><strong>Event Handling:</strong> Comprehensive ActionListener with search execution and state management</li>
     *   <li><strong>Typography Standards:</strong> Bold Segoe UI font (18pt) for clear button text presentation</li>
     *   <li><strong>Workflow Integration:</strong> Seamless integration with search validation and result display systems</li>
     * </ul>
     * <p>
     * Visual styling includes comprehensive button appearance configuration with professional blue background
     * (0, 120, 215) that aligns with airport management system branding standards. The white text color
     * provides optimal contrast and readability, while the bold typography ensures clear action identification
     * throughout flight search operations and customer interactions.
     * </p>
     * <p>
     * Interactive elements include hand cursor configuration (HAND_CURSOR) that provides immediate visual
     * feedback for user interaction and clearly indicates button functionality. Focus painting is disabled
     * (setFocusPainted(false)) to maintain clean visual presentation and professional interface appearance
     * throughout button interaction and search workflow operations.
     * </p>
     * <p>
     * Size configuration establishes optimal button dimensions (200x50 pixels) that provide sufficient
     * visual prominence for the primary search action while maintaining proportional balance within
     * the overall flight search interface layout. The sizing ensures clear action identification
     * and supports accessibility standards for interactive interface elements.
     * </p>
     * <p>
     * Event handling includes comprehensive ActionListener implementation that coordinates complete
     * search workflow execution:
     * </p>
     * <ul>
     *   <li><strong>Search Execution:</strong> Delegates to {@link #executeResearch(List, Controller, JButton)} for comprehensive search processing</li>
     *   <li><strong>State Management:</strong> Sets searchPerformed flag to true for navigation restoration support</li>
     *   <li><strong>Parameter Passing:</strong> Provides calling objects, controller, and button references for complete workflow coordination</li>
     * </ul>
     * <p>
     * Search execution coordination ensures that button activation triggers the complete flight search
     * workflow including parameter validation, search operation execution, and result display updates.
     * The delegation pattern maintains clean separation of concerns while providing comprehensive
     * search functionality throughout customer and administrative flight discovery operations.
     * </p>
     * <p>
     * State management includes setting the searchPerformed flag to enable proper navigation restoration
     * and interface state tracking throughout customer workflows. The state management supports seamless
     * user experience during navigation between different areas of the airport management system
     * while preserving search context and results.
     * </p>
     * <p>
     * Typography standards utilize bold Segoe UI font (18pt) for optimal button text presentation
     * and clear action identification. The typography configuration maintains consistency with
     * the overall airport management system interface standards while providing appropriate
     * visual weight for the primary search action trigger.
     * </p>
     * <p>
     * Workflow integration ensures seamless coordination with the broader flight search ecosystem
     * including validation systems, result display components, and navigation management. The
     * integration supports both customer and administrative workflows while maintaining proper
     * resource management and interface state coordination throughout search operations.
     * </p>
     * <p>
     * The method returns a fully configured search button ready for immediate integration into
     * the flight search interface, providing professional visual presentation, comprehensive
     * event handling, and seamless workflow coordination for efficient flight discovery operations
     * within the airport management system.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper workflow coordination and resource management
     * @param controller the system controller providing access to flight search services and comprehensive system functionality
     * @return the configured search button with professional styling and comprehensive event handling for flight search operations
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

                executeResearch(callingObjects, controller, searchButton);
                searchPerformed = true;

            }
        });

        return searchButton;
    }

    /**
     * Updates and configures the flight search results display panel with dynamic user type detection and comprehensive result presentation.
     * <p>
     * This private method manages the dynamic creation and configuration of flight search result display
     * components based on user authentication level and search state. The method provides intelligent
     * switching between customer and administrative result panels while maintaining comprehensive
     * scroll behavior, professional visual presentation, and optimal user experience throughout
     * flight search result browsing and interaction operations within the airport management system.
     * </p>
     * <p>
     * The results panel update process includes:
     * </p>
     * <ul>
     *   <li><strong>User Type Detection:</strong> Automatic identification of administrative vs customer authentication for appropriate panel selection</li>
     *   <li><strong>Result Panel Creation:</strong> Dynamic instantiation of specialized result panels with comprehensive flight data</li>
     *   <li><strong>Viewport Configuration:</strong> Scroll pane viewport updates with the newly created results panel</li>
     *   <li><strong>Scroll Behavior Setup:</strong> Professional scroll bar policies and performance optimization configuration</li>
     *   <li><strong>Visual Presentation:</strong> Border management and professional appearance standards application</li>
     *   <li><strong>Interface Refresh:</strong> Component revalidation and repainting for immediate visual updates</li>
     * </ul>
     * <p>
     * User type detection utilizes the controller's {@code isLoggedAdmin()} method to determine
     * the appropriate result panel type for the current user session. Administrative users receive
     * {@link SearchFlightResultPanelAdmin} with management functionality, while customer users
     * receive {@link SearchFlightResultPanel} with booking-focused capabilities throughout
     * flight search and selection workflows.
     * </p>
     * <p>
     * Result panel creation includes comprehensive parameter passing for both panel types:
     * </p>
     * <ul>
     *   <li><strong>Navigation Hierarchy:</strong> Calling objects list for proper resource management and workflow coordination</li>
     *   <li><strong>Controller Integration:</strong> System controller reference for data access and business logic execution</li>
     *   <li><strong>Flight Data Collections:</strong> Complete flight information including IDs, companies, dates, times, delays, status, seats, and cities</li>
     *   <li><strong>Search State Flag:</strong> Boolean indicator for proper interface state management and header visibility</li>
     * </ul>
     * <p>
     * Administrative result panel (SearchFlightResultPanelAdmin) provides comprehensive flight
     * management capabilities including flight status modification, passenger management, and
     * operational oversight functionality. The panel integrates with administrative workflows
     * and provides specialized interaction patterns for airport management operations.
     * </p>
     * <p>
     * Customer result panel (SearchFlightResultPanel) focuses on booking functionality with
     * seat availability display, booking initiation capabilities, and customer-friendly
     * interface elements. The panel supports seamless integration with booking workflows
     * and provides optimal user experience for travel planning and reservation operations.
     * </p>
     * <p>
     * Viewport configuration updates the scroll pane's viewport view to display the newly
     * created results panel, ensuring immediate visual transition and proper component
     * hierarchy integration. The viewport update maintains scroll position and provides
     * seamless user experience during result display transitions.
     * </p>
     * <p>
     * Scroll behavior setup includes comprehensive configuration for optimal user experience:
     * </p>
     * <ul>
     *   <li><strong>Border Management:</strong> Empty border application for clean visual presentation without distracting borders</li>
     *   <li><strong>Scroll Policies:</strong> AS_NEEDED policies for both vertical and horizontal scroll bars for space optimization</li>
     *   <li><strong>Unit Increment:</strong> Enhanced vertical scroll unit increment (30 pixels) for smooth scrolling performance</li>
     * </ul>
     * <p>
     * Border management applies empty borders to the scroll pane to ensure clean visual
     * presentation without unnecessary border artifacts that could interfere with the
     * professional interface appearance and result content focus throughout flight
     * search result browsing operations.
     * </p>
     * <p>
     * Scroll policies utilize AS_NEEDED configuration for both vertical and horizontal
     * scroll bars, ensuring that scroll bars appear only when content exceeds the
     * available display area. This approach optimizes screen space utilization while
     * providing necessary navigation capabilities for extensive flight search results.
     * </p>
     * <p>
     * Unit increment optimization sets the vertical scroll bar unit increment to 30 pixels
     * for smooth and responsive scrolling performance during result browsing. The increment
     * provides optimal balance between scroll precision and efficient navigation through
     * large result sets throughout flight search and selection operations.
     * </p>
     * <p>
     * Interface refresh includes comprehensive component revalidation and repainting to
     * ensure immediate visual updates and proper component hierarchy refresh following
     * result panel updates. The refresh operations provide responsive user interface
     * behavior and immediate feedback for search result presentation throughout
     * customer and administrative flight discovery workflows.
     * </p>
     * <p>
     * The method ensures comprehensive result display management with appropriate user
     * type handling, professional visual presentation, and optimal performance throughout
     * flight search result browsing and interaction operations within the airport
     * management system interface architecture.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and workflow coordination
     * @param controller the system controller providing access to user authentication status and comprehensive system functionality
     * @param ifSearched the boolean flag indicating whether a search has been performed for proper interface state management and header visibility
     */
    private void updateResultsPanel(List<DisposableObject> callingObjects, Controller controller, boolean ifSearched) {

        if(controller.isLoggedAdmin()){

            SearchFlightResultPanelAdmin resultsPanel = new SearchFlightResultPanelAdmin(callingObjects, controller,
                                                                ids, companyNames, dates, departureTimes, arrivalTimes,
                                                                delays, status, maxSeats, freeSeats, cities, ifSearched);

            resultsScrollPane.setViewportView(resultsPanel);

        }else{
            SearchFlightResultPanel resultsPanel = new SearchFlightResultPanel(callingObjects, controller,
                                                        ids, companyNames, dates, departureTimes, arrivalTimes,
                                                        delays, status, maxSeats, freeSeats, cities, ifSearched);

            resultsScrollPane.setViewportView(resultsPanel);
        }


        resultsScrollPane.setBorder(BorderFactory.createEmptyBorder());

        resultsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        resultsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        resultsScrollPane.getVerticalScrollBar().setUnitIncrement(30);

        resultsScrollPane.revalidate();
        resultsScrollPane.repaint();
    }

        /**
     * Configures professional button appearance with consistent styling throughout the flight search interface.
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
     * clear visual hierarchy, and optimal interaction design throughout the flight search
     * and parameter specification interface components.
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
     * Configures professional label appearance with consistent typography throughout the flight search interface.
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
     * visual patterns and reducing cognitive load during flight search parameter specification.
     * </p>
     * <p>
     * The method supports comprehensive interface design standards by ensuring that all
     * textual guidance elements maintain professional appearance and optimal readability,
     * contributing to the overall user experience quality and interface effectiveness
     * throughout flight search and discovery operations within the airport management system.
     * </p>
     *
     * @param label the JLabel component to be styled with professional typography configuration
     */
    private void setLabelApperance(JLabel label) {

        label.setFont(new Font("Segoe UI", Font.BOLD, 14));

    }

    /**
     * Executes comprehensive flight search operations with advanced input validation and error handling for optimal user experience.
     * <p>
     * This public method performs sophisticated flight search operations using comprehensive input validation,
     * parameter extraction, and coordinated result presentation throughout the airport management system.
     * The method includes multi-level validation logic with Italian-language error messaging, proper search
     * execution coordination with the flight controller, and dynamic result display updates for optimal
     * customer and administrative experience throughout flight discovery and selection workflows.
     * </p>
     * <p>
     * The flight search execution process includes:
     * </p>
     * <ul>
     *   <li><strong>Parameter Extraction:</strong> Comprehensive retrieval of all flight search criteria from interface input components</li>
     *   <li><strong>Input Validation:</strong> Multi-level validation logic with specific error messaging for each validation scenario</li>
     *   <li><strong>Data Collection Management:</strong> Dynamic ArrayList initialization for comprehensive flight information storage</li>
     *   <li><strong>Search Execution:</strong> Controller-coordinated flight search operations with proper error handling integration</li>
     *   <li><strong>Result Presentation:</strong> Dynamic result panel updates with immediate user feedback and comprehensive data display</li>
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
     * Input validation implements comprehensive multi-level validation logic with specific error
     * messaging for different validation scenarios:
     * </p>
     * <ul>
     *   <li><strong>City Specification Logic:</strong> Ensures both origin and destination are specified when either is provided</li>
     *   <li><strong>Date Range Validation:</strong> Verifies proper date range specification and chronological order consistency</li>
     *   <li><strong>Time Range Validation:</strong> Ensures complete time range specification when temporal filtering is used</li>
     *   <li><strong>Chronological Validation:</strong> Confirms that end dates occur after start dates for logical range specification</li>
     * </ul>
     * <p>
     * Validation error handling utilizes the {@link FloatingMessage} system for user-friendly
     * error presentation with Italian-language messaging:
     * </p>
     * <ul>
     *   <li><strong>"Se si specifica una città, vanno specificate entrambe!":</strong> City specification requirement message</li>
     *   <li><strong>"Errore nel range di date!":</strong> Date range specification error message</li>
     *   <li><strong>"La seconda data deve essere successiva alla prima!":</strong> Chronological date order error message</li>
     *   <li><strong>"Errore nella fascia oraria!":</strong> Time range specification error message</li>
     * </ul>
     * <p>
     * Data collection management includes dynamic initialization of comprehensive ArrayList collections
     * for flight information storage: ids, companyNames, dates, departureTimes, arrivalTimes, delays,
     * status, maxSeats, freeSeats, and cities. The initialization ensures clean data state for each
     * search operation and supports efficient result management throughout search workflows.
     * </p>
     * <p>
     * Search execution coordination utilizes the controller's flight controller for comprehensive
     * flight search operations through the searchFlightCustomer method. The execution includes:
     * </p>
     * <ul>
     *   <li><strong>Parameter Passing:</strong> Complete search criteria transfer to flight controller</li>
     *   <li><strong>Collection References:</strong> ArrayList references for dynamic result population</li>
     *   <li><strong>Error Button Integration:</strong> Search button reference for error message coordination</li>
     * </ul>
     * <p>
     * Result presentation includes dynamic result panel updates through the updateResultsPanel
     * method with ifSearched set to true, ensuring that search results are immediately displayed
     * with proper interface state management and user feedback throughout flight discovery operations.
     * </p>
     * <p>
     * Error handling integration ensures that all validation errors trigger appropriate result
     * panel updates, maintaining consistent interface state and providing immediate user feedback
     * regardless of validation outcomes throughout flight search parameter specification workflows.
     * </p>
     * <p>
     * The method provides comprehensive flight search capabilities that support both customer
     * and administrative workflows while maintaining optimal user experience through professional
     * error handling, clear validation messaging, and efficient search result presentation
     * throughout the airport management system's flight discovery ecosystem.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and workflow coordination
     * @param controller the system controller providing access to flight search services and comprehensive error handling functionality
     * @param searchButton the search button component for error message coordination and user feedback integration
     */
    public void executeResearch(List<DisposableObject> callingObjects, Controller controller, JButton searchButton) {

        String origin = fromField.getText();
        String destination = toField.getText();
        LocalDate dateBefore = dateFrom.getDate();
        LocalDate dateAfter = dateTo.getDate();
        LocalTime timeBefore = timeFrom.getTime();
        LocalTime timeAfter = timeTo.getTime();

        controller.setErrorButton(searchButton);

        boolean flag = false;
        String msg = "";

        if ((fromField.getText().isEmpty() && !toField.getText().isEmpty()) || (!fromField.getText().isEmpty() && toField.getText().isEmpty())) {

            msg = "Se si specifica una città, vanno specificate entrambe!";
            flag = true;
        } else if ((dateBefore != null && dateAfter == null) || (dateBefore == null && dateAfter != null)) {

            msg = "Errore nel range di date!";
            flag = true;
        } else if (dateBefore != null /*&& dateAfter != null*/ && dateAfter.isBefore(dateBefore)) {

            msg = "La seconda data deve essere successiva alla prima!";
            flag = true;
        } else if ((timeBefore != null && timeAfter == null) || (timeBefore == null && timeAfter != null)) {

            msg = "Errore nella fascia oraria!";
            flag = true;
        }

        if (flag) {

            new FloatingMessage(msg, searchButton, FloatingMessage.ERROR_MESSAGE);

        } else {

            ids = new ArrayList<>();
            companyNames = new ArrayList<>();
            dates = new ArrayList<>();
            departureTimes = new ArrayList<>();
            arrivalTimes = new ArrayList<>();
            delays = new ArrayList<>();
            status = new ArrayList<>();
            maxSeats = new ArrayList<>();
            freeSeats = new ArrayList<>();
            cities = new ArrayList<>();

            controller.getFlightController().searchFlightCustomer(origin, destination, dateBefore, dateAfter, timeBefore, timeAfter,
                    ids, companyNames, dates, departureTimes, arrivalTimes, delays, status,
                    maxSeats, freeSeats, cities, searchButton);

            searchPerformed = true;

        }

        updateResultsPanel(callingObjects, controller, true);
    }

    /**
     * Returns the current search execution state for navigation restoration and workflow continuity management.
     * <p>
     * This public method provides access to the search execution state tracking functionality,
     * enabling integration with navigation restoration systems and interface state management
     * throughout customer and administrative workflows. The method supports seamless user
     * experience during navigation between different areas of the airport management system
     * while preserving flight search context and maintaining workflow continuity.
     * </p>
     * <p>
     * Search state tracking enables:
     * </p>
     * <ul>
     *   <li><strong>Navigation Restoration:</strong> Proper interface state restoration when returning to flight search</li>
     *   <li><strong>Workflow Continuity:</strong> Seamless user experience across different system areas</li>
     *   <li><strong>State Management:</strong> Intelligent interface behavior based on search history</li>
     *   <li><strong>Context Preservation:</strong> Maintenance of search results and filter settings during navigation</li>
     * </ul>
     * <p>
     * Navigation restoration utilizes the search performed state to determine whether previous
     * search operations should be restored when customers return to the flight search interface
     * from other areas of the airport management system. The restoration ensures that customers
     * can continue their flight discovery workflows without losing search progress or results.
     * </p>
     * <p>
     * Workflow continuity support enables the interface to maintain appropriate state across
     * different customer and administrative workflow scenarios, providing consistent user
     * experience regardless of navigation patterns and system area transitions throughout
     * airport management system usage.
     * </p>
     * <p>
     * State management integration allows calling components to make intelligent decisions
     * about interface behavior, result display, and user guidance based on whether flight
     * search operations have been performed, supporting optimal user experience and
     * interface responsiveness throughout flight discovery workflows.
     * </p>
     * <p>
     * Context preservation supports the maintenance of search results, filter configurations,
     * and interface state during navigation operations, enabling customers to explore different
     * system areas while preserving their flight search progress and travel planning context
     * for continued use upon return to the flight search interface.
     * </p>
     * <p>
     * The method provides essential state information that enables sophisticated navigation
     * management and user experience optimization throughout the airport management system's
     * flight search and booking ecosystem.
     * </p>
     *
     * @return true if a flight search has been performed, false otherwise
     */
    public boolean isSearchPerformed() {
        return searchPerformed;
    }

    /**
     * Returns the primary search button component for integration with error handling and navigation systems.
     * <p>
     * This public method provides access to the main search execution button for integration
     * with error handling systems, navigation management, and interface coordination throughout
     * the airport management system. The method enables external components to access the search
     * button for error message positioning, state management, and workflow coordination during
     * flight search and discovery operations.
     * </p>
     * <p>
     * Search button access enables:
     * </p>
     * <ul>
     *   <li><strong>Error Message Integration:</strong> Proper positioning and coordination of error messages relative to the search button</li>
     *   <li><strong>Navigation Restoration:</strong> Button state management during interface restoration and workflow continuity</li>
     *   <li><strong>Workflow Coordination:</strong> Integration with broader system workflows and state management operations</li>
     *   <li><strong>Interface Integration:</strong> Seamless coordination with parent interface components and navigation systems</li>
     * </ul>
     * <p>
     * Error message integration utilizes the search button reference for optimal error message
     * positioning through the FloatingMessage system, ensuring that validation errors and
     * system messages are properly positioned relative to the primary action trigger for
     * clear user feedback and professional presentation throughout flight search operations.
     * </p>
     * <p>
     * Navigation restoration enables parent components to access the search button for
     * state management during interface restoration operations, supporting the sophisticated
     * navigation management capabilities that preserve search context and workflow continuity
     * throughout customer interactions with different areas of the airport management system.
     * </p>
     * <p>
     * Workflow coordination supports integration with broader system workflows including
     * booking management, administrative operations, and customer service functions that
     * may need to coordinate with flight search operations or manage interface state
     * transitions throughout comprehensive airport management workflows.
     * </p>
     * <p>
     * Interface integration enables seamless coordination with parent interface components,
     * navigation systems, and workflow management operations that require access to the
     * primary search action trigger for state management, event coordination, and user
     * experience optimization throughout the flight search ecosystem.
     * </p>
     * <p>
     * The method provides essential component access that enables sophisticated interface
     * coordination and user experience management throughout the airport management system's
     * comprehensive flight search and booking capabilities.
     * </p>
     *
     * @return the primary search button component for error handling and navigation integration
     */
    public JButton getSearchButton() {
        return searchButton;
    }

}