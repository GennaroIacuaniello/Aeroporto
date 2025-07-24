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
 * and administrators to search for flights using multiple criteria including origin/destination
 * cities, date ranges, and time ranges. The SearchFlightPanel serves as the primary flight discovery
 * component within the airport management system, offering search functionality with
 * real-time result display and integration with booking workflows.
 * </p>
 * <p>
 * The SearchFlightPanel class provides comprehensive flight search capabilities including:
 * </p>
 * <ul>
 *   <li><strong>Advanced Search Criteria:</strong> Multi-parameter flight search with city, date, and time filtering options</li>
 *   <li><strong>Dual Interface Support:</strong> Separate result panels for customer and administrator workflows with appropriate functionality</li>
 *   <li><strong>Real-time Validation:</strong> Comprehensive input validation with Italian-language error messaging and user guidance</li>
 *   <li><strong>Interactive Controls:</strong> Quick-fill buttons for common Naples-based flight searches with automatic field population</li>
 *   <li><strong>Result Management:</strong> Dynamic result panel updates with scroll support and optimized display performance</li>
 *   <li><strong>State Persistence:</strong> Search state maintenance for navigation restoration and workflow continuity</li>
 * </ul>
 * <p>
 * Layout architecture utilizes {@link GridBagLayout} for precise component positioning and optimal
 * space utilization.
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
     * This JScrollPane component provides the scrollable container for flight search results.
     * </p>
     */
    private JScrollPane resultsScrollPane;

    /**
     * Layout constraints utility for precise component positioning throughout the search interface.
     */
    private final Constraints constraints;

    /**
     * Origin city input field for departure location specification in flight searches.
     */
    private JTextField fromField;
    
    /**
     * Destination city input field for arrival location specification in flight searches.
     */
    private JTextField toField;
    
    /**
     * Start date picker for flight search date range specification.
     */
    private DatePicker dateFrom;
    
    /**
     * End date picker for flight search date range specification.
     */
    private DatePicker dateTo;
    
    /**
     * Start time picker for flight search time range specification.
     */
    private TimePicker timeFrom;
    
    /**
     * End time picker for flight search time range specification.
     */
    private TimePicker timeTo;

    /**
     * Primary search execution button with professional styling and comprehensive event handling.
     */
    private JButton searchButton;

    /**
     * Collection of flight identifiers for search result management and correlation.
     */
    ArrayList<String> ids = new ArrayList<>();
    
    /**
     * Collection of airline company names for flight search result display.
     */
    ArrayList<String> companyNames = new ArrayList<>();
    
    /**
     * Collection of flight dates for temporal information display and organization.
     */
    ArrayList<Date> dates = new ArrayList<>();
    
    /**
     * Collection of flight departure times for scheduling information display.
     */
    ArrayList<Time> departureTimes = new ArrayList<>();
    
    /**
     * Collection of flight arrival times for comprehensive scheduling information.
     */
    ArrayList<Time> arrivalTimes = new ArrayList<>();
    
    /**
     * Collection of flight delay information for current status presentation.
     */
    ArrayList<Integer> delays = new ArrayList<>();
    
    /**
     * Collection of flight status information for operational state presentation.
     */
    ArrayList<String> status = new ArrayList<>();
    
    /**
     * Collection of maximum seat capacity information for flight availability assessment.
     */
    ArrayList<Integer> maxSeats = new ArrayList<>();
    
    /**
     * Collection of available seat counts for real-time availability information.
     */
    ArrayList<Integer> freeSeats = new ArrayList<>();
    
    /**
     * Collection of destination city names for route information display.
     */
    ArrayList<String> cities = new ArrayList<>();

    /**
     * Search execution state tracking for navigation restoration and workflow continuity.
     */
    private boolean searchPerformed = false;

    /**
     * Constructs a new SearchFlightPanel with comprehensive flight search functionality and professional interface design.
     * <p>
     * This constructor initializes the complete flight search interface by establishing layout
     * management, configuring visual styling, and setting up comprehensive search functionality
     * for customer and administrative flight discovery operations. The constructor creates a
     * fully functional search panel ready for immediate integration within flight search workflows
     * with support for advanced search criteria and real-time validation.
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
     * flight search operations.
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
     *   <li><strong>Layout Management:</strong> Precise GridBagConstraints positioning for optimal component organization</li>
     *   <li><strong>Event Handling:</strong> Quick-fill button functionality for Naples-based flight search automation</li>
     * </ul>
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
     *   <li><strong>Interactive Elements:</strong> Hand cursor configuration and focus painting disabled for clean presentation</li>
     *   <li><strong>Event Handling:</strong> Comprehensive ActionListener with search execution and state management</li>
     *   <li><strong>Workflow Integration:</strong> Seamless integration with search validation and result display systems</li>
     * </ul>
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
     *   <li><strong>Interface Refresh:</strong> Component revalidation and repainting for immediate visual updates</li>
     * </ul>
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
     * Configures button appearance with consistent styling throughout the flight search interface.
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
     * Validation error handling utilizes the {@link FloatingMessage} system for user-friendly
     * error presentation.
     * <p>
     * Search execution coordination utilizes the controller's flight controller for
     * flight search operations through the searchFlightCustomer method. The execution includes:
     * </p>
     * <ul>
     *   <li><strong>Parameter Passing:</strong> Complete search criteria transfer to flight controller</li>
     *   <li><strong>Collection References:</strong> ArrayList references for dynamic result population</li>
     *   <li><strong>Error Button Integration:</strong> Search button reference for error message coordination</li>
     * </ul>
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
     * @return true if a flight search has been performed, false otherwise
     */
    public boolean isSearchPerformed() {
        return searchPerformed;
    }

    /**
     * Returns the primary search button component for integration with error handling and navigation systems.
     *
     * @return the primary search button component for error handling and navigation integration
     */
    public JButton getSearchButton() {
        return searchButton;
    }

}