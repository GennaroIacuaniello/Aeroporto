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
 * This class extends {@link JPanel} to provide booking search functionality within the
 * {@link MyBookingsCustomerMainFrame} frame. The SearchBookingPanel serves as the primary search
 * and filtering interface for customers to locate their existing bookings using various criteria,
 * including flight information and passenger details.
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
     */
    private JScrollPane resultsScrollPane;

    /**
     * Layout constraints utility for precise component positioning throughout the search interface.
     */
    private final Constraints constraints;

    /**
     * Current active filter mode identifier for dynamic interface behavior management.
     * <p>
     * This string field tracks the currently selected search mode, containing values of
     * "NONE", "FLIGHT", or "PASSENGER".
     * </p>
     */
    private String activeFilter = "NONE";

    /**
     * Origin city input field for flight-based booking search operations.
     */
    private JTextField fromField;
    
    /**
     * Destination city input field for flight-based booking search operations.
     */
    private JTextField toField;
    
    /**
     * Start date picker component for flight date range filtering in booking searches.
     */
    private DatePicker dateFrom;
    
    /**
     * End date picker component for flight date range filtering in booking searches.
     */
    private DatePicker dateTo;
    
    /**
     * Start time picker component for flight time range filtering in booking searches.
     */
    private TimePicker timeFrom;
    
    /**
     * End time picker component for flight time range filtering in booking searches.
     */
    private TimePicker timeTo;

    /**
     * First name input field for passenger-based booking search operations.
     */
    private JTextField firstNameField;

    /**
     * Last name input field for passenger-based booking search operations.
     */
    private JTextField lastNameField;

    /**
     * Passenger SSN (Codice Fiscale) input field for precise passenger identification in booking searches.
     */
    private JTextField passengerSSNField;
    
    /**
     * Ticket number input field for direct ticket-based booking identification and search.
     */
    private JTextField ticketNumberField;

    /**
     * Primary search button for executing booking search operations with comprehensive validation and error handling.
     */
    private JButton searchButton;

    /**
     * Search performance status flag for tracking search operation execution and interface state management.
     */
    private boolean searchPerformed = false;

    /**
     * Collection of booking dates from search results for comprehensive booking information management.
     */
    ArrayList<Date> bookingDates = new ArrayList<>();
    
    /**
     * Collection of booking status information from search results for booking state tracking and display.
     */
    ArrayList<String> bookingStatus = new ArrayList<>();
    
    /**
     * Collection of flight identifiers from search results for flight information correlation and management.
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
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and navigation coordination
     * @param controller the system controller providing access to booking search services and data management functionality
     * @param ifOpenedFromMenu flag indicating menu-initiated access for automatic booking data loading and specialized interface behavior
     */
    public void setComponents(List<DisposableObject> callingObjects, Controller controller, boolean ifOpenedFromMenu) {

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
            bookingDates = new ArrayList<>();
            bookingStatus = new ArrayList<>();
            flightIds = new ArrayList<>();
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
     * Configures button appearance with consistent styling throughout the search interface.
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
     *
     * @param label the JLabel component to be styled with professional typography configuration
     */
    private void setLabelApperance(JLabel label) {

        label.setFont(new Font("Segoe UI", Font.BOLD, 14));

    }

    /**
     * Returns the current search performance status for interface state management and navigation coordination.
     *
     * @return true if search operations have been performed within the current session, false otherwise
     */
    public boolean isSearchPerformed() {
        return searchPerformed;
    }

    /**
     * Returns the search button component for external access and interface coordination.
     *
     * @return the JButton component serving as the primary search execution control
     */
    public JButton getSearchButton() {
        return searchButton;
    }

    /**
     * Returns the current active filter mode for search operation routing and interface state coordination.
     *
     * @return the current active filter mode string indicating search operation routing behavior
     */
    public String getActiveFilter() {
        return activeFilter;
    }

}