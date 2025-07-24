package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

/**
 * Abstract base class for comprehensive booking management interfaces within the airport management system.
 * <p>
 * This class provides the foundational framework for all booking-related user interfaces, implementing
 * common functionality for flight booking operations, passenger management, and administrative controls.
 * It serves as the parent class for specialized booking interfaces including customer booking management,
 * administrative booking operations, and booking modification workflows.
 * </p>
 * <p>
 * The BookingPage class establishes a comprehensive interface architecture including:
 * </p>
 * <ul>
 *   <li><strong>Hierarchical UI Structure:</strong> Well-organized panel hierarchy for consistent layout and navigation</li>
 *   <li><strong>Flight Information Display:</strong> Comprehensive flight details presentation with real-time status updates</li>
 *   <li><strong>Passenger Management:</strong> Dynamic passenger panel system with pagination and search capabilities</li>
 *   <li><strong>Navigation Controls:</strong> Integrated navigation bar and user panel for seamless application flow</li>
 *   <li><strong>Search Functionality:</strong> Built-in passenger search by ticket number for efficient passenger location</li>
 *   <li><strong>Pagination System:</strong> Advanced pagination for managing large numbers of passengers with intuitive controls</li>
 *   <li><strong>Resource Management:</strong> Comprehensive disposal and cleanup mechanisms for proper resource handling</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see DisposableObject
 * @see Controller
 * @see PassengerPanel
 * @see BookingPageCustomer
 * @see BookingPageAdmin
 * @see BookingModifyPage
 * @see TitlePanel
 * @see NavigatorBarPanel
 * @see UserPanel
 * @see Constraints
 */
public abstract class BookingPage extends DisposableObject {

    /**
     * Main application frame that serves as the primary container for the booking interface.
     * <p>
     * This frame houses all booking-related components and provides the main window for
     * user interaction with the booking system. It is configured with appropriate size,
     * position, and behavior settings for optimal user experience.
     * </p>
     */
    protected JFrame mainFrame;

        /**
         * Top panel container that houses the application header components.
         * <p>
         * This panel contains the title, navigation bar, and user information components,
         * providing consistent header functionality across all booking interfaces.
         * </p>
         */
        protected JPanel topPanel;

            /**
             * Title panel displaying the airport name and branding.
             * <p>
             * This component provides consistent branding and identification across
             * all booking interfaces within the airport management system.
             * </p>
             */
            protected TitlePanel titlePanel;
            
            /**
             * Navigation bar panel providing application-wide navigation controls.
             * <p>
             * This component enables users to navigate between different sections of
             * the airport management system while maintaining booking context.
             * </p>
             */
            protected NavigatorBarPanel navigatorBarPanel;
            
            /**
             * User panel displaying current user information and account controls.
             * <p>
             * This component provides user-specific information and controls,
             * including login status, user identification, and account management options.
             * </p>
             */
            protected UserPanel userPanel;

        /**
         * Main panel container that houses the primary booking interface components.
         * <p>
         * This panel contains all core booking functionality including flight information,
         * passenger management, search capabilities, and control interfaces.
         * </p>
         */
        protected JPanel mainPanel;

            /**
             * Flight information panel displaying comprehensive flight details.
             * <p>
             * This panel provides structured display of flight information including
             * company details, route information, scheduling, and operational status.
             * </p>
             */
            protected JPanel flightInfoPanel;

                /**
                 * Flight information table presenting flight details in structured format.
                 * <p>
                 * This table displays comprehensive flight information including company name,
                 * destination/origin city, flight date, departure and arrival times, flight
                 * duration, current status, and available seat count.
                 * </p>
                 */
                protected JTable flightInfoTable;

            /**
             * Search panel providing passenger search functionality.
             * <p>
             * This panel contains search controls that allow users to locate specific
             * passengers within the booking system using ticket number identification.
             * </p>
             */
            protected JPanel searchPanel;

                /**
                 * Search input field for entering ticket numbers.
                 * <p>
                 * This text field accepts ticket number input for passenger search
                 * operations, enabling quick navigation to specific passengers.
                 * </p>
                 */
                protected JTextField searchField;
                
                /**
                 * Search button that triggers passenger search operations.
                 * <p>
                 * This button initiates search functionality based on the ticket
                 * number entered in the search field, providing quick access to
                 * specific passengers within the booking interface.
                 * </p>
                 */
                protected JButton searchButton;

            /**
             * Passenger page container that displays passenger panels with pagination support.
             * <p>
             * This panel manages the display of passenger information panels using a
             * grid-based layout system with support for pagination when passenger
             * counts exceed the three-per-page display limit.
             * </p>
             */
            protected JPanel passengerPage;

                /**
                 * List of booked seats to prevent double-booking conflicts.
                 * <p>
                 * This collection maintains seat numbers that are already allocated
                 * to prevent booking conflicts and ensure seat availability accuracy
                 * during booking operations.
                 * </p>
                 */
                protected ArrayList<Integer> bookedSeats;
                
                /**
                 * Collection of passenger panels representing individual passenger information.
                 * <p>
                 * This list contains all passenger panels associated with the current
                 * booking operation, providing complete passenger management capabilities
                 * including information display, editing, and validation.
                 * </p>
                 */
                protected ArrayList<PassengerPanel> passengerPanels;

            /**
             * Modification panel container for booking operation controls.
             * <p>
             * This panel houses controls and buttons specific to booking modifications,
             * including pagination controls and specialized booking operation buttons
             * defined by concrete implementations.
             * </p>
             */
            protected JPanel modifyPanel;

                /**
                 * Flow panel for organizing control buttons with proper alignment.
                 * <p>
                 * This panel provides structured layout for pagination controls and
                 * other booking operation buttons, ensuring consistent alignment and
                 * spacing throughout the interface.
                 * </p>
                 */
                protected JPanel flowPanel;

                    /**
                     * Current page index for pagination system.
                     * <p>
                     * This value tracks the currently displayed page in the passenger
                     * pagination system, enabling proper navigation and display state
                     * management across page transitions.
                     * </p>
                     */
                    protected int currPage = 0;
                    
                    /**
                     * Navigation button for moving to the previous page of passengers.
                     * <p>
                     * This button enables users to navigate backward through passenger
                     * pages when the passenger count exceeds the three-per-page display
                     * limit, providing intuitive pagination control.
                     * </p>
                     */
                    protected JButton prevPageButton;
                    
                    /**
                     * Navigation button for moving to the next page of passengers.
                     * <p>
                     * This button enables users to navigate forward through passenger
                     * pages when the passenger count exceeds the three-per-page display
                     * limit, providing intuitive pagination control.
                     * </p>
                     */
                    protected JButton nextPageButton;
                    
                    /**
                     * Label displaying the current page number for user orientation.
                     * <p>
                     * This label provides visual feedback about the current page position
                     * within the passenger pagination system, helping users understand
                     * their location within the passenger list.
                     * </p>
                     */
                    protected JLabel currentPageLabel;

    /**
     * Layout constraints utility for managing component positioning and sizing.
     * <p>
     * This utility provides consistent layout management throughout the booking
     * interface, ensuring proper component alignment, spacing, and responsive
     * behavior across different screen configurations.
     * </p>
     */
    protected Constraints constraints;
    
    /**
     * Flag indicating whether controller resources should be disposed when the interface closes.
     * <p>
     * This flag controls resource cleanup behavior, determining whether controller
     * resources should be released when the booking interface is disposed. This
     * is particularly useful for managing shared controller instances across
     * multiple interface components.
     * </p>
     */
    protected boolean controllerDisposeFlag = true;

    /**
     * Constructs a new BookingPage with comprehensive interface initialization.
     * <p>
     * This constructor establishes the complete booking interface framework including
     * all UI components, layout management, and integration with the airport management
     * system. The constructor sets up the hierarchical panel structure, initializes
     * all interface components, and prepares the interface for user interaction.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li><strong>Main Frame Setup:</strong> Configures the primary application window</li>
     *   <li><strong>Top Panel Creation:</strong> Establishes header components and navigation</li>
     *   <li><strong>Main Panel Setup:</strong> Creates core booking interface components</li>
     *   <li><strong>Initial Navigation:</strong> Sets the interface to display the first page</li>
     *   <li><strong>Visibility Management:</strong> Makes the interface visible and ready for interaction</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and navigation
     * @param controller the system controller providing access to flight management, booking operations, and data persistence
     * @param dimension the preferred window size for the booking interface
     * @param point the screen position where the booking window should be displayed
     * @param fullScreen the window state indicating whether the interface should be maximized or in normal window mode
     */
    protected BookingPage (List<DisposableObject> callingObjects, Controller controller,
                        Dimension dimension, Point point, int fullScreen) {

        super();

        constraints = new Constraints();

        //makes this the operating frame
        setMainFrame(callingObjects, dimension, point, fullScreen);

        //setting top panels
        addTopPanel(callingObjects, controller);

        //setting mainPanel
        addMainPanel(callingObjects, controller);

        goToPage(0);

        mainFrame.setVisible(true);
    }

    /**
     * Configures and initializes the main application frame with proper window settings.
     * <p>
     * This method establishes the primary application window with appropriate configuration
     * for the booking interface. It sets up window properties including size, position,
     * state, and behavior settings to provide optimal user experience across different
     * system configurations.
     * </p>
     * <p>
     * Frame configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Window Title:</strong> Sets descriptive title for the booking interface</li>
     *   <li><strong>Size and Position:</strong> Configures window dimensions and screen position</li>
     *   <li><strong>Window State:</strong> Sets maximized or normal window state based on user preference</li>
     *   <li><strong>Close Behavior:</strong> Configures application termination behavior</li>
     *   <li><strong>Layout Manager:</strong> Establishes GridBagLayout for precise component positioning</li>
     *   <li><strong>Navigation Integration:</strong> Adds the booking page to the calling objects hierarchy</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper integration
     * @param dimension the preferred window size for optimal display
     * @param point the screen position for window placement
     * @param fullScreen the window state flag for maximized or normal display mode
     */
    protected void setMainFrame (List<DisposableObject> callingObjects, Dimension dimension, Point point, int fullScreen) {

        mainFrame = new JFrame("BookingPage");

        mainFrame.setSize(dimension);
        mainFrame.setLocation(point);
        mainFrame.setExtendedState(fullScreen);

        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mainFrame.setLayout(new GridBagLayout());

        callingObjects.addLast(this);
    }

    /**
     * Creates and configures the top panel containing header components and navigation.
     * <p>
     * This method establishes the header section of the booking interface, which provides
     * consistent branding, navigation, and user information across all booking operations.
     * The top panel uses a structured layout to organize header components in an intuitive
     * and accessible manner.
     * </p>
     * <p>
     * Top panel components include:
     * </p>
     * <ul>
     *   <li><strong>Title Panel:</strong> Airport branding and identification display</li>
     *   <li><strong>Navigator Bar Panel:</strong> Application-wide navigation controls</li>
     *   <li><strong>User Panel:</strong> Current user information and account controls</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper navigation integration
     * @param controller the system controller providing access to navigation and user management capabilities
     */
    protected void addTopPanel (List<DisposableObject> callingObjects, Controller controller) {

        topPanel = new JPanel();

        topPanel.setLayout(new GridBagLayout());

        addTitlePanel();
        addNavigatorBarPanel (callingObjects, controller);

        addUserPanel(callingObjects, controller);

        constraints.setConstraints (0, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START);
        mainFrame.add(topPanel, constraints.getGridBagConstraints());

        topPanel.setVisible(true);
    }

    /**
     * Creates and configures the title panel with airport branding.
     * <p>
     * This method establishes the title section of the interface header, providing
     * consistent airport branding and identification across all booking interfaces.
     * The title panel uses the standardized TitlePanel component to ensure visual
     * consistency throughout the application.
     * </p>
     */
    protected void addTitlePanel () {

        titlePanel = new TitlePanel("AEROPORTO DI NAPOLI");

        constraints.setConstraints(0, 0, 2, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START);
        topPanel.add(titlePanel, constraints.getGridBagConstraints());

        titlePanel.setVisible(true);
    }

    /**
     * Creates and configures the navigation bar panel for application-wide navigation.
     * <p>
     * This method establishes the navigation section of the interface header, providing
     * users with access to different areas of the airport management system while
     * maintaining booking context. The navigation bar enables seamless transitions
     * between different application functions.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper navigation state management
     * @param controller the system controller providing access to application state and navigation capabilities
     */
    protected void addNavigatorBarPanel (List<DisposableObject> callingObjects, Controller controller) {

        navigatorBarPanel = new NavigatorBarPanel (callingObjects, controller);

        constraints.setConstraints (0, 1, 2, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.CENTER);
        topPanel.add (navigatorBarPanel, constraints.getGridBagConstraints());

        navigatorBarPanel.setOpaque(false);
        navigatorBarPanel.setVisible (true);
    }

    /**
     * Creates and configures the user panel for displaying current user information.
     * <p>
     * This method establishes the user information section of the interface header,
     * providing users with access to their account information, login status, and
     * user-specific controls. The user panel enables account management functionality
     * while maintaining booking workflow continuity.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper user action management
     * @param controller the system controller providing access to user information and authentication state
     */
    protected void addUserPanel(List<DisposableObject> callingObjects, Controller controller) {

        userPanel = new UserPanel(callingObjects, controller);

        constraints.setConstraints(1, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.FIRST_LINE_END);
        topPanel.add(userPanel, constraints.getGridBagConstraints());

        userPanel.setVisible (true);
    }

    /**
     * Creates and configures the main panel containing core booking interface components.
     * <p>
     * This method establishes the primary content area of the booking interface, housing
     * all essential booking functionality including flight information display, passenger
     * management, search capabilities, and control interfaces. The main panel serves as
     * the central workspace for all booking operations.
     * </p>
     * <p>
     * Main panel components initialization includes:
     * </p>
     * <ul>
     *   <li><strong>Pagination Controls:</strong> Previous/next page buttons and current page label</li>
     *   <li><strong>Flight Information Panel:</strong> Comprehensive flight details display</li>
     *   <li><strong>Search Panel:</strong> Passenger search functionality</li>
     *   <li><strong>Passenger Page:</strong> Dynamic passenger panel container with pagination</li>
     *   <li><strong>Modify Panel:</strong> Control interface for booking operations</li>
     *   <li><strong>Confirm Panel:</strong> Abstract confirmation interface for concrete implementations</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper integration
     * @param controller the system controller providing access to flight management, booking operations, and system functionality
     */
    protected void addMainPanel (List<DisposableObject> callingObjects, Controller controller) {

        mainPanel = new JPanel();

        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);

        //inizializzo componenti
        prevPageButton = new JButton("←");
        nextPageButton = new JButton("→");
        currentPageLabel = new JLabel(Integer.toString(currPage + 1));

        addFlightInfoPanel (controller);
        addSearchPanel ();
        addPassengerPage (controller);
        addModifyPanel (controller);
        addConfirmPanel (callingObjects, controller);

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.PAGE_START, 1, 1);
        mainFrame.add (mainPanel, constraints.getGridBagConstraints());

        mainPanel.setVisible (true);
    }

    /**
     * Creates and configures the flight information panel with comprehensive flight details.
     * <p>
     * This method establishes the flight information display section, providing users with
     * complete flight details including operational information, scheduling data, and
     * real-time status updates. The flight information is presented in a structured table
     * format for clear visibility and easy reference during booking operations.
     * </p>
     * <p>
     * Flight information display includes:
     * </p>
     * <ul>
     *   <li><strong>Company Name:</strong> Airline operating the flight</li>
     *   <li><strong>City:</strong> Destination or origin city information</li>
     *   <li><strong>Date:</strong> Flight date in user-friendly format</li>
     *   <li><strong>Departure Time:</strong> Scheduled departure time</li>
     *   <li><strong>Arrival Time:</strong> Scheduled arrival time</li>
     *   <li><strong>Duration:</strong> Calculated flight duration between departure and arrival</li>
     *   <li><strong>Status:</strong> Current flight operational status</li>
     *   <li><strong>Free Seats:</strong> Real-time available seat count</li>
     * </ul>
     *
     * @param controller the system controller providing access to flight information and real-time data
     */
    protected void addFlightInfoPanel (Controller controller) {

        flightInfoPanel = new JPanel(new BorderLayout());

        flightInfoPanel.setOpaque(false);

        String[] columnNames = {"COMPANY", "CITY", "DATE", "DEPARTURE TIME", "ARRIVAL TIME", "DURATION", "STATUS", "FREE SEATS"};

        Object[][] data = new Object[1][columnNames.length];

        data[0][0] = controller.getFlightController().getCompanyName();
        data[0][1] = controller.getFlightController().getCity();
        data[0][2] = controller.getFlightController().getDateString();
        data[0][3] = controller.getFlightController().getDepartureTime();
        data[0][4] = controller.getFlightController().getArrivalTime();
        LocalTime departureTime = controller.getFlightController().getDepartureTime().toLocalTime();
        LocalTime arrivalTime = controller.getFlightController().getArrivalTime().toLocalTime();
        data[0][5] = Duration.between(departureTime, arrivalTime).toString();
        data[0][6] = controller.getFlightController().getFlightStatus();
        data[0][7] = controller.getFlightController().getFreeSeats();

        flightInfoTable = new JTable(data, columnNames);
        flightInfoTable.setEnabled(false);
        flightInfoTable.getTableHeader().setReorderingAllowed(false);

        flightInfoPanel.add(flightInfoTable.getTableHeader(), BorderLayout.NORTH);
        flightInfoPanel.add(flightInfoTable, BorderLayout.CENTER);

        constraints.setConstraints(0, 0, 1, 1,
                GridBagConstraints.HORIZONTAL, 0, 0, GridBagConstraints.CENTER);
        mainPanel.add(flightInfoPanel, constraints.getGridBagConstraints());
    }

    /**
     * Creates and configures the search panel for passenger location functionality.
     * <p>
     * This method establishes the passenger search interface, providing users with
     * the ability to quickly locate specific passengers within the booking system
     * using ticket number identification. The search functionality enables efficient
     * navigation to specific passengers when dealing with large passenger lists.
     * </p>
     * <p>
     * The search field is configured with appropriate width to accommodate typical
     * ticket number formats while maintaining visual balance with other interface
     * components. Users can enter ticket numbers for precise passenger location.
     * </p>
     * <p>
     * The search button is configured with an action listener that triggers the
     * passenger search functionality when activated. The button is set as non-focusable
     * to prevent interference with keyboard navigation patterns.
     * </p>
     */
    protected void addSearchPanel () {

        searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        searchPanel.setOpaque(false);

        searchField = new JTextField(16);

        searchButton = new JButton("Search");
        searchButton.addActionListener (new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {

                searchPassenger();
            }
        });
        searchButton.setFocusable(false);

        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        constraints.setConstraints(0, 1, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_START);
        mainPanel.add(searchPanel, constraints.getGridBagConstraints());

        searchPanel.setVisible (true);
    }

    /**
     * Executes passenger search functionality using ticket number identification.
     * <p>
     * This method performs passenger location operations within the current booking
     * interface by searching through all passenger panels for a matching ticket number.
     * When a match is found, the interface automatically navigates to the appropriate
     * page to display the located passenger.
     * </p>
     * <p>
     * Search process includes:
     * </p>
     * <ul>
     *   <li><strong>Ticket Number Comparison:</strong> Iterates through all passenger panels comparing ticket numbers</li>
     *   <li><strong>Page Calculation:</strong> Determines the appropriate page for the located passenger</li>
     *   <li><strong>Automatic Navigation:</strong> Navigates to the page containing the searched passenger</li>
     *   <li><strong>Error Feedback:</strong> Displays appropriate error messages for unsuccessful searches</li>
     * </ul>
     */
    protected void searchPassenger () {

        boolean flag = true;

        for (int i = 0; (i < passengerPanels.size()) && flag; i++) {

            if (passengerPanels.get(i).getTicketNumber().equals(searchField.getText())) {

                goToPage(i / 3);
                flag = false;
            }
        }

        if (flag) new FloatingMessage("Nessun passeggero trovato con ticket number: " + searchField.getText(), searchButton, FloatingMessage.ERROR_MESSAGE);
    }

    /**
     * Manages passenger display pagination with visibility control and navigation state management.
     * <p>
     * This method handles the complex task of managing passenger panel visibility across
     * multiple pages, ensuring that only the appropriate passengers are displayed for
     * the current page while maintaining proper navigation state. The pagination system
     * supports up to 3 passengers per page for optimal screen utilization and usability.
     * </p>
     * <p>
     * Page navigation process includes:
     * </p>
     * <ul>
     *   <li><strong>Current Page Cleanup:</strong> Hides all passenger panels on the current page</li>
     *   <li><strong>Target Page Setup:</strong> Shows appropriate passenger panels for the target page</li>
     *   <li><strong>Page State Update:</strong> Updates current page tracking and display label</li>
     *   <li><strong>Navigation Control Management:</strong> Enables/disables navigation buttons based on page position</li>
     * </ul>
     *
     * @param page the zero-based target page index to navigate to
     */
    protected void goToPage (int page) {

        //sistemo visibilità
        for (int i = 0; i < 3; i++) {

            if (i + currPage * 3 < passengerPanels.size()) passengerPanels.get(i + currPage * 3).setVisible(false);
            if (i + page * 3 < passengerPanels.size()) passengerPanels.get(i + page * 3).setVisible(true);
        }

        //sistemo currPage
        currPage = page;
        currentPageLabel.setText(Integer.toString(currPage + 1));

        //sistemo attivabilità bottoni
        prevPageButton.setEnabled(currPage > 0);
        nextPageButton.setEnabled(currPage < ((passengerPanels.size() - 1) / 3));
    }

    /**
     * Creates and configures the passenger page container with dynamic passenger management.
     * <p>
     * This method establishes the passenger display area of the booking interface,
     * creating a dynamic container that manages passenger panels with support for
     * pagination and seat management. The passenger page serves as the primary
     * workspace for passenger information display and management.
     * </p>
     * <p>
     * Passenger page initialization includes:
     * </p>
     * <ul>
     *   <li><strong>Layout Configuration:</strong> GridBagLayout setup for precise passenger panel positioning</li>
     *   <li><strong>Collections Initialization:</strong> Creates collections for booked seats and passenger panels</li>
     *   <li><strong>Seat Management Setup:</strong> Initializes booked seats tracking through controller integration</li>
     *   <li><strong>Passenger Population:</strong> Loads initial passenger data through abstract method implementation</li>
     * </ul>
     *
     * @param controller the system controller providing access to seat management and passenger data
     */
    protected void addPassengerPage (Controller controller) {

        passengerPage = new JPanel();

        passengerPage.setLayout(new GridBagLayout());

        passengerPage.setOpaque(false);

        bookedSeats = new ArrayList<>();
        passengerPanels = new ArrayList<>();

        setBookedSeats(controller);
        insertPassengers(controller);

        constraints.setConstraints (0, 2, 1, 1,
                GridBagConstraints.BOTH, 0, 0, GridBagConstraints.CENTER, 0.9f, 0.9f);
        mainPanel.add (passengerPage, constraints.getGridBagConstraints());
    }

    /**
     * Initializes booked seats tracking through controller integration.
     * <p>
     * This method establishes seat availability tracking by delegating to the
     * controller's seat management system. The controller populates the booked
     * seats collection with current seat allocations to prevent double-booking
     * conflicts during passenger management operations.
     * </p>
     * <p>
     * Seat tracking integration ensures that all passenger panels have access
     * to current seat availability information, enabling accurate seat selection
     * and preventing booking conflicts across multiple passengers.
     * </p>
     * <p>
     * The method provides a clean interface between the booking page's seat
     * tracking requirements and the controller's comprehensive seat management
     * system, ensuring proper data flow and consistency.
     * </p>
     *
     * @param controller the system controller providing seat management and availability tracking capabilities
     */
    protected void setBookedSeats (Controller controller) {
        controller.setBookedSeats(bookedSeats);
    }

    /**
     * Abstract method for passenger data insertion and display.
     * <p>
     * This method must be implemented by concrete subclasses to provide specific
     * passenger loading and display functionality appropriate for their booking
     * context. Different booking page types (customer, admin, modification) require
     * different approaches to passenger data management and display.
     * </p>
     *
     * @param controller the system controller providing access to passenger data and booking information
     */
    protected abstract void insertPassengers (Controller controller);

    /**
     * Inserts a passenger panel into the interface with proper layout and visibility management.
     * <p>
     * This method handles the addition of individual passenger panels to the passenger
     * page, managing layout positioning, visibility states, and panel configuration.
     * The method ensures that passenger panels are properly integrated into the
     * pagination system and display correctly within the interface structure.
     * </p>
     * <p>
     * Passenger panel insertion includes:
     * </p>
     * <ul>
     *   <li><strong>Layout Positioning:</strong> Calculates appropriate grid position based on passenger count</li>
     *   <li><strong>Panel Registration:</strong> Adds the panel to the passenger panels collection</li>
     *   <li><strong>Visibility Management:</strong> Sets initial visibility based on pagination requirements</li>
     *   <li><strong>Panel Configuration:</strong> Configures panel state for the booking context</li>
     * </ul>
     *
     * @param passengerPanel the passenger panel to be inserted into the interface
     */
    protected void insertPassengerPanel (PassengerPanel passengerPanel) {

        constraints.setConstraints(0, (passengerPanels.size() % 3), 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        passengerPage.add(passengerPanel, constraints.getGridBagConstraints());

        passengerPanels.addLast(passengerPanel);

        passengerPanel.setVisible(passengerPanels.size() < 4);

        passengerPanel.setPanelEnabled(false);
    }

    /**
     * Creates and configures the modification panel with pagination controls.
     * <p>
     * This method establishes the control interface for booking operations, providing
     * pagination controls and space for specialized booking operation buttons defined
     * by concrete implementations. The modification panel serves as the primary
     * control center for booking-related actions and navigation.
     * </p>
     * <p>
     * The flow panel within the modification panel provides structured layout for
     * control buttons with right alignment, following conventional interface design
     * patterns for action controls. The flow panel ensures proper button spacing
     * and alignment regardless of the number of controls added.
     * </p>
     * <p>
     * Pagination controls are added through the dedicated page change buttons method,
     * ensuring consistent pagination functionality across all booking page implementations.
     * These controls enable users to navigate through large passenger lists efficiently.
     * </p>
     */
    protected void addModifyPanel (Controller controller) {

        modifyPanel = new JPanel();

        modifyPanel.setLayout(new GridBagLayout());

        modifyPanel.setOpaque(false);

        flowPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        flowPanel.setOpaque(false);

        constraints.setConstraints (1, 0, 1, 1,
                GridBagConstraints.HORIZONTAL, 0, 0, GridBagConstraints.LINE_END);
        modifyPanel.add(flowPanel, constraints.getGridBagConstraints());

        flowPanel.setVisible(true);

        addPageChangeButtons();

        constraints.setConstraints (0, 3, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.CENTER);
        mainPanel.add (modifyPanel, constraints.getGridBagConstraints());
        modifyPanel.setVisible (true);
    }

    /**
     * Creates and configures pagination control buttons with proper event handling.
     * <p>
     * This method establishes the pagination navigation system that enables users to
     * navigate through multiple pages of passengers when the passenger count exceeds
     * the three-per-page display limit. The pagination controls provide intuitive
     * navigation with clear visual feedback about current position and available options.
     * </p>
     * <p>
     * Pagination controls include:
     * </p>
     * <ul>
     *   <li><strong>Previous Page Button:</strong> Navigates to the preceding page of passengers</li>
     *   <li><strong>Current Page Label:</strong> Displays the current page number for user orientation</li>
     *   <li><strong>Next Page Button:</strong> Navigates to the following page of passengers</li>
     * </ul>
     * <p>
     * Action listeners are configured for both navigation buttons to trigger
     * appropriate page navigation operations. The listeners use the current page
     * tracking to calculate target pages and delegate to the page navigation method.
     * </p>
     * <p>
     * Initial button state management disables the previous page button (since
     * pagination starts on the first page) and enables the next page button only
     * if more than 3 passengers are present, requiring pagination functionality.
     * </p>
     * <p>
     * The current page label provides immediate visual feedback about pagination
     * state, displaying page numbers in user-friendly format (1-based indexing)
     * for intuitive user understanding.
     * </p>
     */
    protected void addPageChangeButtons () {

        prevPageButton.setFocusable(false);
        nextPageButton.setFocusable(false);

        //aggiungo action listeners
        prevPageButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {

                goToPage(currPage - 1);

            }
        });

        nextPageButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {

                goToPage(currPage + 1);

            }
        });

        prevPageButton.setEnabled (false);
        nextPageButton.setEnabled(passengerPanels.size() > 3);

        //aggiungo bottoni
        flowPanel.add (prevPageButton);
        flowPanel.add (currentPageLabel);
        flowPanel.add (nextPageButton);
    }

    /**
     * Abstract method for confirmation panel creation and configuration.
     * <p>
     * This method must be implemented by concrete subclasses to provide specific
     * confirmation interface functionality appropriate for their booking context.
     * Different booking page types require different confirmation options and
     * workflow management based on user roles and booking operations.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper navigation management
     * @param controller the system controller providing access to booking operations and validation capabilities
     */
    protected abstract void addConfirmPanel (List<DisposableObject> callingObjects, Controller controller);

    /**
     * Sets the controller disposal flag for resource management configuration.
     * <p>
     * This method configures whether controller resources should be disposed when
     * the booking interface is closed. This is particularly useful for managing
     * shared controller instances across multiple interface components where
     * premature disposal could affect other active interfaces.
     * </p>
     *
     * @param flag true to enable controller disposal on interface cleanup, false to preserve controller resources
     */
    public void setControllerDisposeFlag (boolean flag) {

        controllerDisposeFlag = flag;
    }

    /**
     * Decreases the current page index with navigation state management.
     * <p>
     * This utility method handles backward page navigation by decrementing the
     * current page counter and updating associated navigation controls. The method
     * ensures proper navigation state management and provides appropriate visual
     * feedback for pagination operations.
     * </p>
     */
    protected void decreaseCurrPage () {
        currPage--;
        currentPageLabel.setText (Integer.toString(currPage + 1));

        if (currPage == 0) prevPageButton.setEnabled (false);
    }

    /**
     * Retrieves the current page index for pagination state queries.
     * <p>
     * This method provides access to the current page position within the
     * pagination system, enabling other components to query pagination state
     * for display or navigation purposes.
     * </p>
     *
     * @return the zero-based current page index
     */
    protected int getCurrPage () {

        return currPage;
    }

    /**
     * Retrieves the next page navigation button for external control access.
     * <p>
     * This method provides access to the next page button component, enabling
     * external components to modify its state, appearance, or behavior based
     * on specific booking context requirements.
     * </p>
     * @return the next page navigation button component
     */
    protected JButton getNextButton () {

        return nextPageButton;
    }

    /**
     * Retrieves the passenger page panel for external component access.
     * <p>
     * This method provides access to the passenger page container, enabling
     * external components to add additional elements, modify layout properties,
     * or integrate specialized functionality with the passenger display system.
     * </p>
     *
     * @return the passenger page panel container
     */
    protected JPanel getPassengerPage () {

        return passengerPage;
    }

    /**
     * Performs comprehensive resource cleanup and disposal operations.
     * <p>
     * This method implements the disposal interface contract by cleaning up all
     * resources associated with the booking interface, including controller resources,
     * GUI components, and sub-component cleanup. The disposal process ensures that
     * no resources are leaked when the booking interface is closed or replaced.
     * </p>
     * <p>
     * Disposal operations include:
     * </p>
     * <ul>
     *   <li><strong>Controller Resource Management:</strong> Clears flight and booking data if disposal flag is set</li>
     *   <li><strong>Passenger Panel Cleanup:</strong> Disposes seat choosers and luggage views for all passenger panels</li>
     *   <li><strong>Component Disposal:</strong> Ensures proper cleanup of all GUI components and resources</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper cleanup coordination
     * @param controller the system controller for resource management and state cleanup
     */
    @Override
    public void doOnDispose (List<DisposableObject> callingObjects, Controller controller) {

        if (controllerDisposeFlag) {

            controller.getFlightController().setFlight(null);
            controller.getBookingController().setBooking(null);
        }

        for (PassengerPanel passengerPanel : passengerPanels) {
            if (passengerPanel.getSeatChooser() != null) passengerPanel.getSeatChooser().dispose();

            if (passengerPanel.getLuggagesView() != null) passengerPanel.getLuggagesView().dispose();
        }
    }

    /**
     * Retrieves the main application frame for external frame access.
     * <p>
     * This method provides access to the main application frame, enabling
     * external components to query frame properties, modify frame behavior,
     * or integrate with frame-level operations such as positioning, sizing,
     * or state management.
     * </p>
     *
     * @return the main application frame for the booking interface
     */
    @Override
    public JFrame getFrame() {

        return this.mainFrame;
    }
}