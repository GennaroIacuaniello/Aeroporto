package controller;

import dao.*;
import gui.DisposableObject;
import gui.FloatingMessage;

import gui.LuggagePanel;
import gui.PassengerPanel;
import implementazioni_postgres_dao.*;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Central controller class for the airport management system that coordinates between GUI components and data access objects.
 * <p>
 * This class serves as the main controller in the MVC (Model-View-Controller) architecture, managing all interactions
 * between the user interface components and the underlying business logic. It orchestrates operations across multiple
 * specialized controller classes and provides a unified interface for all system operations.
 * </p>
 * <p>
 * The Controller class manages the following core functionalities:
 * </p>
 * <ul>
 *   <li>User authentication and session management for both customers and administrators</li>
 *   <li>Flight search, booking, and reservation management operations</li>
 *   <li>Navigation between different GUI components and application states</li>
 *   <li>Integration with database access objects for persistent data operations</li>
 *   <li>Error handling and user notification through floating messages</li>
 *   <li>Coordination between specialized controllers for different domain objects</li>
 * </ul>
 * <p>
 * The class follows a delegation pattern, utilizing specialized controller classes for different domain areas:
 * </p>
 * <ul>
 *   <li>{@link AdminController} - Administrative user management and operations</li>
 *   <li>{@link BookingController} - Booking creation, modification, and management</li>
 *   <li>{@link CustomerController} - Customer account and profile management</li>
 *   <li>{@link FlightController} - Flight information and operations</li>
 *   <li>{@link LuggageController} - Luggage tracking and management</li>
 *   <li>{@link PassengerController} - Passenger information handling</li>
 *   <li>{@link TicketController} - Ticket generation and management</li>
 *   <li>{@link UserController} - General user operations and validation</li>
 * </ul>
 * <p>
 * Navigation management is handled through a stack-based approach using {@link DisposableObject} instances,
 * allowing for proper window lifecycle management, state preservation, and seamless transitions between
 * different application screens while maintaining user context and system resources.
 * </p>
 * <p>
 * Error handling is centralized through comprehensive exception catching and user-friendly message display
 * using {@link FloatingMessage} components. The class maintains a logger for system monitoring and
 * debugging purposes.
 * </p>
 * <p>
 * Database operations are performed through DAO implementations that provide abstraction from the underlying
 * PostgreSQL database, ensuring clean separation of concerns and maintainable code architecture.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see AdminController
 * @see BookingController
 * @see CustomerController
 * @see FlightController
 * @see LuggageController
 * @see PassengerController
 * @see TicketController
 * @see UserController
 * @see DisposableObject
 * @see FloatingMessage
 */
public class Controller {

    /**
     * Controller for administrative user operations and management.
     */
    private final AdminController adminController;

    /**
     * Controller for booking operations including creation, modification, and search.
     */
    private final BookingController bookingController;

    /**
     * Controller for customer account management and operations.
     */
    private final CustomerController customerController;

    /**
     * Controller for flight information management and operations.
     */
    private final FlightController flightController;

    /**
     * Controller for airport gate management operations.
     */
    private final GateController gateController;

    /**
     * Controller for luggage tracking and management operations.
     */
    private final LuggageController luggageController;

    /**
     * Controller for passenger information management.
     */
    private final PassengerController passengerController;

    /**
     * Controller for general user operations and validation.
     */
    private final UserController userController;

    /**
     * Controller for ticket generation and management operations.
     */
    private final TicketController ticketController;

    /**
     * Button reference used for displaying error messages in appropriate context.
     */
    private JButton errorButton;

    /**
     * Logger instance for recording system operations and errors.
     */
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());

    /**
     * Constructs a new Controller instance and initializes all specialized controller components.
     * <p>
     * This constructor creates instances of all specialized controllers that handle specific
     * domain operations within the airport management system. Each controller is responsible
     * for managing its respective domain objects and operations.
     * </p>
     * <p>
     * The initialization follows the dependency injection pattern, ensuring that all
     * controllers are properly instantiated and ready for use throughout the application
     * lifecycle.
     * </p>
     */
    public Controller() {
        adminController = new AdminController();
        bookingController = new BookingController();
        customerController = new CustomerController();
        flightController = new FlightController();
        gateController = new GateController();
        luggageController = new LuggageController();
        passengerController = new PassengerController();
        userController = new UserController();
        ticketController = new TicketController();
    }

    /**
     * Returns the booking controller for managing reservation operations.
     *
     * @return the {@link BookingController} instance for booking management
     */
    public BookingController getBookingController() {
        return bookingController;
    }

    /**
     * Returns the customer controller for managing customer account operations.
     *
     * @return the {@link CustomerController} instance for customer management
     */
    public CustomerController getCustomerController() {
        return customerController;
    }

    /**
     * Returns the flight controller for managing flight operations.
     *
     * @return the {@link FlightController} instance for flight management
     */
    public FlightController getFlightController() {
        return flightController;
    }

    /**
     * Returns the gate controller for managing airport gate operations.
     *
     * @return the {@link GateController} instance for gate management
     */
    public GateController getGateController() {
        return gateController;
    }

    /**
     * Returns the luggage controller for managing luggage tracking operations.
     *
     * @return the {@link LuggageController} instance for luggage management
     */
    public LuggageController getLuggageController() {
        return luggageController;
    }

    /**
     * Returns the user controller for managing general user operations.
     *
     * @return the {@link UserController} instance for user management
     */
    public UserController getUserController() {
        return userController;
    }

    /**
     * Navigates to the login screen by disposing of all intermediate windows and restoring the login window.
     * <p>
     * This method implements a complete navigation reset to the login screen, preserving the window
     * dimensions, location, and state of the current window for a smooth visual transition. All
     * intermediate windows in the navigation stack are properly disposed of to free system resources.
     * </p>
     * <p>
     * The navigation process follows these steps:
     * </p>
     * <ol>
     *   <li>Captures current window properties (size, location, maximized state)</li>
     *   <li>Calls dispose methods on all windows except the base login window</li>
     *   <li>Removes disposed windows from the navigation stack</li>
     *   <li>Restores the login window with preserved visual properties</li>
     *   <li>Calls restore methods to reinitialize the login window state</li>
     * </ol>
     * <p>
     * This method is typically used when users want to return to the login screen from any
     * point in the application, such as switching between user accounts or accessing the
     * system from a different user perspective.
     * </p>
     *
     * @param callingObjects stack of {@link DisposableObject} instances representing the current navigation state
     */
    public void goToLogin(List<DisposableObject> callingObjects){

        Dimension sourceDimension = callingObjects.getLast().getFrame().getSize();
        Point sourceLocation = callingObjects.getLast().getFrame().getLocation();
        int sourceExtendedState = callingObjects.getLast().getFrame().getExtendedState();

        for (int i = callingObjects.size() - 1; i > 0; i--) {

            callingObjects.get(i).doOnDispose(callingObjects, this);
            callingObjects.getLast().getFrame().dispose();
            callingObjects.removeLast();
        }

        if(sourceExtendedState != Frame.MAXIMIZED_BOTH){ //if frame is maximized size and location are automatic
            callingObjects.getLast().getFrame().setSize(sourceDimension);
            callingObjects.getLast().getFrame().setLocation(sourceLocation);
        }
        callingObjects.getLast().getFrame().setExtendedState(sourceExtendedState);

        callingObjects.getLast().doOnRestore(callingObjects, this);

        callingObjects.getLast().getFrame().setVisible(true);
    }

    /**
     * Navigates to the home screen by disposing of intermediate windows while preserving the base navigation structure.
     * <p>
     * This method provides navigation to the user's home screen, which is typically the second window
     * in the navigation stack (after login). It preserves window visual properties and properly
     * manages the disposal of intermediate windows to maintain system resource efficiency.
     * </p>
     * <p>
     * The navigation process maintains the login window as the base layer and the home screen
     * as the primary user interface, disposing of all intermediate screens that may have been
     * opened during the user's session.
     * </p>
     * <p>
     * Visual continuity is maintained by preserving the current window's dimensions, position,
     * and maximization state, providing a seamless user experience during navigation transitions.
     * </p>
     * <p>
     * This method is commonly used for "Home" button functionality throughout the application,
     * allowing users to quickly return to their main dashboard or starting point while maintaining
     * their session and user context.
     * </p>
     *
     * @param callingObjects stack of {@link DisposableObject} instances representing the current navigation state
     */
    public void goHome (List<DisposableObject> callingObjects) {

        Dimension sourceDimension = callingObjects.getLast().getFrame().getSize();
        Point sourceLocation = callingObjects.getLast().getFrame().getLocation();
        int sourceExtendedState = callingObjects.getLast().getFrame().getExtendedState();

        for (int i = callingObjects.size() - 1; i > 1; i--) {

            callingObjects.get(i).doOnDispose(callingObjects, this);
            callingObjects.getLast().getFrame().dispose();
            callingObjects.removeLast();
        }

        if(sourceExtendedState != Frame.MAXIMIZED_BOTH){ //if frame is maximized size and location are automatic
            callingObjects.getLast().getFrame().setSize(sourceDimension);
            callingObjects.getLast().getFrame().setLocation(sourceLocation);
        }
        callingObjects.getLast().getFrame().setExtendedState(sourceExtendedState);

        callingObjects.getLast().doOnRestore(callingObjects, this);

        callingObjects.getLast().getFrame().setVisible(true);

    }

    /**
     * Navigates back to the previous screen in the navigation stack.
     * <p>
     * This method implements a standard "back" navigation pattern by disposing of the current
     * window and returning to the previous window in the navigation stack. It preserves the
     * visual properties of the current window and applies them to the previous window for
     * consistent user experience.
     * </p>
     * <p>
     * The back navigation process includes proper resource cleanup through the disposal
     * pattern and state restoration for the previous window. This ensures that both the
     * departing and arriving windows maintain their expected state and functionality.
     * </p>
     * <p>
     * Window properties such as size, location, and maximization state are preserved
     * and transferred to provide visual continuity during the navigation transition.
     * </p>
     * <p>
     * This method is typically bound to "Back" buttons throughout the application interface,
     * providing consistent navigation behavior across all application screens.
     * </p>
     *
     * @param callingObjects stack of {@link DisposableObject} instances representing the current navigation state
     */
    public void goBack (List<DisposableObject> callingObjects) {

        Dimension sourceDimension = callingObjects.getLast().getFrame().getSize();
        Point sourceLocation = callingObjects.getLast().getFrame().getLocation();
        int sourceExtendedState = callingObjects.getLast().getFrame().getExtendedState();

        callingObjects.getLast().doOnDispose(callingObjects, this);
        callingObjects.getLast().getFrame().dispose();
        callingObjects.removeLast();

        if(sourceExtendedState != Frame.MAXIMIZED_BOTH){ //if frame is maximized size and location are automatic
            callingObjects.getLast().getFrame().setSize(sourceDimension);
            callingObjects.getLast().getFrame().setLocation(sourceLocation);
        }
        callingObjects.getLast().getFrame().setExtendedState(sourceExtendedState);

        callingObjects.getLast().doOnRestore(callingObjects, this);

        callingObjects.getLast().getFrame().setVisible(true);

    }

    /**
     * Performs user logout by clearing the session and returning to the login screen.
     * <p>
     * This method implements a complete logout process that disposes of all application
     * windows except the base login window, effectively clearing the user's session
     * and returning the application to its initial state.
     * </p>
     * <p>
     * The logout process ensures that all user-specific data and interface components
     * are properly cleaned up, preventing any session data leakage or memory issues.
     * The login window is restored to its initial state, ready for a new user session.
     * </p>
     * <p>
     * Unlike other navigation methods, logout does not preserve window properties
     * since the user context is being completely reset. The login window returns
     * to its default state and configuration.
     * </p>
     * <p>
     * This method is typically triggered by "Logout" buttons or menu items throughout
     * the application, providing users with a clean way to end their session and
     * allow other users to access the system.
     * </p>
     *
     * @param callingObjects stack of {@link DisposableObject} instances representing the current navigation state
     */
    public void logOut (List<DisposableObject> callingObjects) {

        for (int i = callingObjects.size() - 1; i > 0; i--) {
            callingObjects.getLast().doOnDispose(callingObjects, this);
            callingObjects.getLast().getFrame().dispose();
            callingObjects.removeLast();
        }

        callingObjects.getLast().doOnRestore(callingObjects, this);
        callingObjects.getLast().getFrame().setVisible(true);
    }

    /**
     * Creates a new booking in the system using passenger information from the provided panels.
     * <p>
     * This method orchestrates the complete booking creation process by collecting passenger
     * information from GUI panels, generating ticket numbers, and persisting the booking
     * data to the database through the appropriate DAO layer.
     * </p>
     * <p>
     * The booking creation process includes:
     * </p>
     * <ul>
     *   <li>Processing passenger panel data to extract passenger information</li>
     *   <li>Generating unique ticket numbers for each passenger</li>
     *   <li>Handling seat assignments and luggage information</li>
     *   <li>Creating database records for bookings, tickets, passengers, and luggage</li>
     *   <li>Maintaining data integrity through proper transaction handling</li>
     * </ul>
     * <p>
     * The method delegates the actual data extraction to {@link #preparePassengers} and
     * uses the {@link BookingDAOImpl} for database persistence. Error handling includes
     * comprehensive logging for system monitoring and debugging purposes.
     * </p>
     * <p>
     * All booking operations are performed atomically to ensure data consistency and
     * prevent partial booking creation in case of system failures.
     * </p>
     *
     * @param passengerPanels list of {@link PassengerPanel} instances containing passenger information and preferences
     * @param bookingStatus the initial status for the new booking (e.g., "PENDING", "CONFIRMED")
     */
    public void addBooking(List<PassengerPanel> passengerPanels, String bookingStatus) {

        try {

            BookingDAOImpl bookingDAO = new BookingDAOImpl();

            ArrayList<String> ticketsNumbers = new ArrayList<>();
            ArrayList<Integer> seats = new ArrayList<>();
            ArrayList<String> firstNames = new ArrayList<>();
            ArrayList<String> lastNames = new ArrayList<>();
            ArrayList<Date> birthDates = new ArrayList<>();
            ArrayList<String> passengerSSNs = new ArrayList<>();
            ArrayList<String> luggagesTypes = new ArrayList<>();
            ArrayList<String> ticketsForLuggagesTypes = new ArrayList<>();

            preparePassengers(passengerPanels, ticketsNumbers, seats, firstNames, lastNames, birthDates, passengerSSNs, luggagesTypes, ticketsForLuggagesTypes);

            bookingDAO.addBooking(getUserController().getLoggedUserId(), flightController.getId(), bookingStatus, ticketsNumbers,
                    seats, firstNames, lastNames, birthDates, passengerSSNs, luggagesTypes, ticketsForLuggagesTypes);

        } catch (SQLException e) {
            Controller.getLogger().log(Level.SEVERE, e.getSQLState());
        }
    }

    /**
     * Translates internal flight status enum values to user-friendly Italian display strings.
     * <p>
     * This utility method provides localization support for flight status display in the
     * user interface by converting internal enum values to appropriate Italian language
     * representations. This ensures consistent and user-friendly status display throughout
     * the application.
     * </p>
     * <p>
     * The translation mapping includes all standard flight status values:
     * </p>
     * <ul>
     *   <li>PROGRAMMED → "In programma" (Scheduled)</li>
     *   <li>CANCELLED → "Cancellato" (Cancelled)</li>
     *   <li>DELAYED → "In ritardo" (Delayed)</li>
     *   <li>ABOUT_TO_DEPART → "In partenza" (About to depart)</li>
     *   <li>DEPARTED → "Partito" (Departed)</li>
     *   <li>ABOUT_TO_ARRIVE → "In arrivo" (About to arrive)</li>
     *   <li>LANDED → "Atterrato" (Landed)</li>
     * </ul>
     * <p>
     * This method is essential for maintaining a consistent Italian user interface
     * while using English enum values internally for system operations and database
     * storage.
     * </p>
     *
     * @param status the {@link FlightStatus} enum value to be translated
     * @return the Italian translation of the flight status, or null if the status is not recognized
     */
    public static String translateFlightStatus(FlightStatus status){

        switch (status.toString()){
            case "PROGRAMMED":
                return "In programma";
            case "CANCELLED":
                return "Cancellato";
            case "DELAYED":
                return "In ritardo";
            case "ABOUT_TO_DEPART":
                return "In partenza";
            case "DEPARTED":
                return "Partito";
            case "ABOUT_TO_ARRIVE":
                return "In arrivo";
            case "LANDED":
                return "Atterrato";
            default:
                return null;
        }
    }

    /**
     * Modifies an existing booking using updated passenger information from the provided panels.
     * <p>
     * This method handles the complex process of updating an existing booking while maintaining
     * data integrity and consistency. The modification process involves updating passenger
     * information, ticket details, seat assignments, and luggage information while preserving
     * the booking's core identity and relationships.
     * </p>
     * <p>
     * The modification process includes:
     * </p>
     * <ul>
     *   <li>Processing updated passenger information from GUI panels</li>
     *   <li>Generating new ticket numbers as needed for additional passengers</li>
     *   <li>Updating seat assignments and luggage preferences</li>
     *   <li>Using temporary ticket mechanisms to maintain referential integrity</li>
     *   <li>Updating the booking status as specified</li>
     * </ul>
     * <p>
     * The method uses {@link BookingDAOImpl#modifyBooking} which implements a sophisticated
     * temporary ticket approach to ensure that all database constraints are maintained
     * during the modification process. A temporary ticket number is generated to facilitate
     * the update process without violating foreign key constraints.
     * </p>
     * <p>
     * All modification operations are performed atomically to ensure data consistency
     * and prevent corruption of booking data in case of system failures.
     * </p>
     *
     * @param passengerPanels list of {@link PassengerPanel} instances containing updated passenger information
     * @param bookingStatus the new status for the modified booking
     */
    public void modifyBooking (List<PassengerPanel> passengerPanels, String bookingStatus) {

        try {

            BookingDAOImpl bookingDAO = new BookingDAOImpl();

            ArrayList<String> ticketsNumbers = new ArrayList<>();
            ArrayList<Integer> seats = new ArrayList<>();
            ArrayList<String> firstNames = new ArrayList<>();
            ArrayList<String> lastNames = new ArrayList<>();
            ArrayList<Date> birthDates = new ArrayList<>();
            ArrayList<String> passengerSSNs = new ArrayList<>();
            ArrayList<String> luggagesTypes = new ArrayList<>();
            ArrayList<String> ticketsForLuggagesTypes = new ArrayList<>();

            preparePassengers(passengerPanels, ticketsNumbers, seats, firstNames, lastNames, birthDates, passengerSSNs, luggagesTypes, ticketsForLuggagesTypes);

            bookingDAO.modifyBooking(flightController.getId(), getBookingController().getId(), ticketsNumbers,
                    seats, firstNames, lastNames, birthDates, passengerSSNs, luggagesTypes, ticketsForLuggagesTypes, generateTicketNumber(passengerPanels.size() + 1), bookingStatus);

        } catch (SQLException e) {
            Controller.getLogger().log(Level.SEVERE, e.getSQLState());
        }
    }

    /**
     * Extracts and prepares passenger data from GUI panels for database operations.
     * <p>
     * This private utility method processes passenger information panels to extract all
     * necessary data for booking creation or modification operations. It handles the
     * complex task of correlating passenger information with ticket numbers, seat
     * assignments, and luggage preferences.
     * </p>
     * <p>
     * The preparation process includes:
     * </p>
     * <ul>
     *   <li>Generating or retrieving ticket numbers for each passenger</li>
     *   <li>Extracting seat preferences and assignments</li>
     *   <li>Collecting passenger personal information (names, birth dates, SSN)</li>
     *   <li>Processing luggage selections and associating them with tickets</li>
     *   <li>Maintaining proper correlation between passengers and their associated data</li>
     * </ul>
     * <p>
     * Ticket number generation is handled intelligently - if a passenger panel already
     * contains a ticket number (for modifications), it is preserved; otherwise, a new
     * unique ticket number is generated using the offset-based generation system.
     * </p>
     * <p>
     * Luggage information is processed by examining each luggage panel within passenger
     * panels, extracting selected luggage types, and associating them with the
     * appropriate ticket numbers for proper database relationships.
     * </p>
     *
     * @param passengerPanels list of {@link PassengerPanel} instances containing passenger information
     * @param ticketsNumbers list to be populated with ticket numbers for each passenger
     * @param seats list to be populated with seat assignments for each passenger
     * @param firstNames list to be populated with passenger first names
     * @param lastNames list to be populated with passenger last names
     * @param birthDates list to be populated with passenger birth dates
     * @param passengerSSNs list to be populated with passenger SSN identifiers
     * @param luggagesTypes list to be populated with selected luggage types
     * @param ticketsForLuggagesTypes list to be populated with ticket numbers associated with each luggage item
     */
    private void preparePassengers (List<PassengerPanel> passengerPanels, List<String> ticketsNumbers, List<Integer> seats, List<String> firstNames, List<String> lastNames,
                                    List<Date> birthDates, List<String> passengerSSNs, List<String> luggagesTypes, List<String> ticketsForLuggagesTypes) {

        int i = 0;

        for (PassengerPanel passengerPanel : passengerPanels) {

            if(passengerPanel.getTicketNumber() == null) ticketsNumbers.add(generateTicketNumber(i++));
            else ticketsNumbers.add(passengerPanel.getTicketNumber());

            seats.add(passengerPanel.getSeat());
            firstNames.add(passengerPanel.getPassengerName());
            lastNames.add(passengerPanel.getPassengerSurname());
            birthDates.add(passengerPanel.getPassengerDate());
            passengerSSNs.add(passengerPanel.getPassengerCF());

            for (LuggagePanel luggagePanel : passengerPanel.getLuggagesPanels()) {

                if (luggagePanel.getComboBox().getSelectedIndex() != 0 && luggagePanel.getComboBox().getSelectedItem() != null){
                        luggagesTypes.add(luggagePanel.getComboBox().getSelectedItem().toString());
                        ticketsForLuggagesTypes.add(ticketsNumbers.getLast());
                    }


            }
        }
    }

    /**
     * Generates a unique ticket number using the offset-based generation system.
     * <p>
     * This method provides a wrapper around the ticket generation functionality
     * implemented in {@link TicketDAOImpl}. It ensures that generated ticket
     * numbers are unique across the system and follow the established 13-digit
     * format with appropriate zero-padding.
     * </p>
     * <p>
     * The offset parameter allows for batch ticket generation scenarios where
     * multiple consecutive ticket numbers need to be generated for a single
     * booking operation. This prevents conflicts and ensures sequential numbering.
     * </p>
     * <p>
     * Ticket number generation is thread-safe and uses database-level maximum
     * value queries to ensure uniqueness even in concurrent environments.
     * </p>
     *
     * @param offset the number of increments to apply before generating the final ticket number
     * @return a unique 13-digit ticket number formatted with leading zeros
     */
    public String generateTicketNumber (int offset) {

        TicketDAOImpl ticketDAO = new TicketDAOImpl();

        return ticketDAO.generateTicketNumber(offset);
    }

    /**
     * Sets the error button reference for displaying contextual error messages.
     * <p>
     * This method allows GUI components to register a button that should be used
     * as the reference point for displaying error messages through {@link FloatingMessage}
     * components. This ensures that error messages appear in the appropriate context
     * relative to the user's current action.
     * </p>
     *
     * @param errorButton the {@link JButton} to use as a reference for error message positioning
     */
    public void setErrorButton(JButton errorButton) {
        this.errorButton = errorButton;
    }

    /**
     * Retrieves all bookings for the currently logged-in customer.
     * <p>
     * This method performs a comprehensive search for all bookings associated with the
     * currently logged-in customer account. It retrieves complete booking information
     * including flight details, passenger information, and ticket data, organizing
     * the results for display in the user interface.
     * </p>
     * <p>
     * The retrieval process includes:
     * </p>
     * <ul>
     *   <li>Querying the database for all customer bookings</li>
     *   <li>Retrieving associated flight information and details</li>
     *   <li>Loading ticket information for each booking</li>
     *   <li>Creating appropriate flight objects (Arriving/Departing) based on flight type</li>
     *   <li>Organizing data in controller result caches for GUI consumption</li>
     * </ul>
     * <p>
     * Flight objects are created as either {@link Arriving} or {@link Departing} instances
     * based on the flight type flag, ensuring that the correct flight behavior and
     * information display is available for each booking.
     * </p>
     * <p>
     * The method handles complex data relationships by maintaining separate result
     * collections for flights, bookings, tickets, and passengers, allowing the GUI
     * to access related information efficiently.
     * </p>
     * <p>
     * Error handling includes database connection failure notification through
     * {@link FloatingMessage} components, providing user feedback when operations
     * cannot be completed.
     * </p>
     *
     * @param bookingDates list to be populated with booking creation dates
     * @param bookingStatus list to be populated with booking status values
     * @param flightIds list to be populated with flight identifiers
     * @param searchButton the button to use as reference for error message display
     */
    public void getAllBooksLoogedCustomer(List<Date> bookingDates, List<String> bookingStatus, List<String> flightIds, JButton searchButton) {


        ArrayList<String> companyNames = new ArrayList<>();
        ArrayList<Date> dates = new ArrayList<>();
        ArrayList<Time> departureTimes = new ArrayList<>();
        ArrayList<Time> arrivalTimes = new ArrayList<>();
        ArrayList<String> status = new ArrayList<>();
        ArrayList<Integer> maxSeats = new ArrayList<>();
        ArrayList<Integer> freeSeats = new ArrayList<>();
        ArrayList<String> cities = new ArrayList<>();

        ArrayList<Boolean> types = new ArrayList<>();

        ArrayList<Integer> bookingIds = new ArrayList<>();

        try{
            BookingDAO bookingDAO = new BookingDAOImpl();

            bookingDAO.getAllBooksCustomer(getCustomerController().getLoggedCustomerId(), flightIds, companyNames, dates, departureTimes, arrivalTimes, status, maxSeats, freeSeats,
                    cities, types, bookingDates, bookingStatus, bookingIds);


        } catch (SQLException e) {
            new FloatingMessage("Errore nella connessione al Database (Prenotazioni)!", searchButton, FloatingMessage.ERROR_MESSAGE);
        }

        ArrayList<String> actualFlightIds = new ArrayList<>();

        bookingController.setSearchBookingResult(new ArrayList<>());
        bookingController.setSearchBookingResultIds(new ArrayList<>());
        flightController.setSearchBookingResult(new ArrayList<>());

        ticketController.setSearchBookingResult(new ArrayList<>());
        passengerController.setSearchBookingResult(new ArrayList<>());

        ArrayList<String> actualSSNs = new ArrayList<>();

        for(int i = 0; i < flightIds.size(); i++){

            if(!actualFlightIds.contains(flightIds.get(i))){

                actualFlightIds.add(flightIds.get(i));

                if(Boolean.TRUE.equals(types.get(i))){   //alloco Departing

                    flightController.getSearchBookingResult().add(new Departing( flightIds.get(i), companyNames.get(i), dates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                            FlightStatus.valueOf(status.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i)));

                }else{              //alloco Arriving

                    flightController.getSearchBookingResult().add(new Arriving( flightIds.get(i), companyNames.get(i), dates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                            FlightStatus.valueOf(status.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i)));


                }
            }

            ArrayList<String> ticketNumbers = new ArrayList<>();
            ArrayList<Integer> seats = new ArrayList<>();
            ArrayList<Boolean> checkedIns = new ArrayList<>();
            ArrayList<String> passengerSSNs = new ArrayList<>();
            ArrayList<String> firstNames = new ArrayList<>();
            ArrayList<String> lastNames = new ArrayList<>();
            ArrayList<Date> birthDates = new ArrayList<>();

            TicketDAO ticketDao = new TicketDAOImpl();

            try{

                ticketDao.getAllTicketBooking(bookingIds.get(i), ticketNumbers, seats, checkedIns, passengerSSNs, firstNames, lastNames, birthDates);

            } catch (SQLException e) {

                new FloatingMessage("Errore nella connessione al Database (Biglietti)!", searchButton, FloatingMessage.ERROR_MESSAGE);
            }

            try{
                if(!ticketNumbers.isEmpty()) {

                    bookingController.getSearchBookingResult().add(new Booking(BookingStatus.valueOf(bookingStatus.get(i)), bookingDates.get(i),
                            customerController.getLoggedCustomer(), flightController.getSearchBookingResult().getLast(),
                            ticketNumbers.getFirst(), seats.getFirst(), checkedIns.getFirst(),
                            firstNames.getFirst(), lastNames.getFirst(), passengerSSNs.getFirst(), birthDates.getFirst()));
                    bookingController.getSearchBookingResultIds().add(bookingIds.get(i));
                }else{
                    throw new InvalidTicket("");
                }

            }catch (Exception e){
                new FloatingMessage("Errore nella connessione al Database (Biglietti)!", searchButton, FloatingMessage.ERROR_MESSAGE);
            }

            for(int j = 1; j < ticketNumbers.size(); j++){
                try{
                    bookingController.getSearchBookingResult().getLast().getTickets().add(new Ticket(ticketNumbers.get(j), seats.get(j), checkedIns.get(j),
                            flightController.getSearchBookingResult().getLast(), bookingController.getSearchBookingResult().getLast(),
                            firstNames.get(j), lastNames.get(j), passengerSSNs.get(j), birthDates.get(j)));

                }catch (Exception e){
                    new FloatingMessage("Errore nella connessione al Database!", searchButton, FloatingMessage.ERROR_MESSAGE);
                }
            }

            ticketController.getSearchBookingResult().addAll(bookingController.getSearchBookingResult().getLast().getTickets());
            for(Ticket x: bookingController.getSearchBookingResult().getLast().getTickets()){
                if(!actualSSNs.contains(x.getPassenger().getPassengerSSN())){
                    actualSSNs.add(x.getPassenger().getPassengerSSN());
                    passengerController.getSearchBookingResult().add(x.getPassenger());
                }

            }

        }

    }

    /**
     * Searches for customer bookings using flight-based filtering criteria.
     * <p>
     * This method performs a filtered search for bookings belonging to the currently
     * logged-in customer based on flight characteristics such as origin/destination
     * cities, dates, and times. It provides flexible search capabilities to help
     * customers find specific bookings among their reservation history.
     * </p>
     * <p>
     * The search supports the following filtering criteria:
     * </p>
     * <ul>
     *   <li>Origin city - for flights departing from a specific location</li>
     *   <li>Destination city - for flights arriving at a specific location</li>
     *   <li>Date range - for flights within a specific time period</li>
     *   <li>Time range - for flights departing within specific hours</li>
     * </ul>
     * <p>
     * The method handles the special case of "Napoli" as the airport's base city,
     * implementing appropriate logic for local departures and arrivals. Search
     * results include complete booking information with associated flight details,
     * passenger information, and ticket data.
     * </p>
     * <p>
     * Results are organized in controller caches and processed to create appropriate
     * flight objects (Arriving/Departing) based on flight types, ensuring that the
     * GUI receives properly structured data for display purposes.
     * </p>
     * <p>
     * The method maintains data consistency by avoiding duplicate entries and
     * properly correlating related information across different domain objects.
     * </p>
     *
     * @param origin the departure city name for filtering (null for no filter)
     * @param destination the arrival city name for filtering (null for no filter)
     * @param dateBefore the start date for date range filtering (null for no filter)
     * @param dateAfter the end date for date range filtering (null for no filter)
     * @param timeBefore the start time for time range filtering (null for no filter)
     * @param timeAfter the end time for time range filtering (null for no filter)
     * @param bookingDates list to be populated with booking creation dates
     * @param bookingStatus list to be populated with booking status values
     * @param flightIds list to be populated with flight identifiers
     * @param searchButton the button to use as reference for error message display
     */
    public void searchBooksLoogedCustomerFilteredFlights(String origin, String destination, LocalDate dateBefore, LocalDate dateAfter, LocalTime timeBefore, LocalTime timeAfter,
                                                         List<Date> bookingDates, List<String> bookingStatus, List<String> flightIds, JButton searchButton) {

        ArrayList<String> companyNames = new ArrayList<>();
        ArrayList<Date> dates = new ArrayList<>();
        ArrayList<Time> departureTimes = new ArrayList<>();
        ArrayList<Time> arrivalTimes = new ArrayList<>();
        ArrayList<String> status = new ArrayList<>();
        ArrayList<Integer> maxSeats = new ArrayList<>();
        ArrayList<Integer> freeSeats = new ArrayList<>();
        ArrayList<String> cities = new ArrayList<>();

        ArrayList<Boolean> types = new ArrayList<>();

        ArrayList<Integer> bookingIds = new ArrayList<>();

        try{
            BookingDAO bookingDAO = new BookingDAOImpl();

            bookingDAO.searchBooksCustomerFilteredFlights(origin, destination, dateBefore, dateAfter, timeBefore, timeAfter,
                    getCustomerController().getLoggedCustomerId(), flightIds, companyNames, dates, departureTimes, arrivalTimes, status, maxSeats, freeSeats,
                    cities, types, bookingDates, bookingStatus, bookingIds);


        } catch (SQLException e) {
            new FloatingMessage("Errore nella connessione al Database (Prenotazioni)!", searchButton, FloatingMessage.ERROR_MESSAGE);
        }

        ArrayList<String> actualFlightIds = new ArrayList<>();

        bookingController.setSearchBookingResult(new ArrayList<>());
        bookingController.setSearchBookingResultIds(new ArrayList<>());
        flightController.setSearchBookingResult(new ArrayList<>());

        ticketController.setSearchBookingResult(new ArrayList<>());
        passengerController.setSearchBookingResult(new ArrayList<>());

        ArrayList<String> actualSSNs = new ArrayList<>();

        for(int i = 0; i < flightIds.size(); i++){

            if(!actualFlightIds.contains(flightIds.get(i))){

                actualFlightIds.add(flightIds.get(i));

                if(Boolean.TRUE.equals(types.get(i))){   //alloco Departing

                    flightController.getSearchBookingResult().add(new Departing( flightIds.get(i), companyNames.get(i), dates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                            FlightStatus.valueOf(status.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i)));

                }else{              //alloco Arriving

                    flightController.getSearchBookingResult().add(new Arriving( flightIds.get(i), companyNames.get(i), dates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                            FlightStatus.valueOf(status.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i)));


                }
            }

            ArrayList<String> ticketNumbers = new ArrayList<>();
            ArrayList<Integer> seats = new ArrayList<>();
            ArrayList<Boolean> checkedIns = new ArrayList<>();
            ArrayList<String> passengerSSNs = new ArrayList<>();
            ArrayList<String> firstNames = new ArrayList<>();
            ArrayList<String> lastNames = new ArrayList<>();
            ArrayList<Date> birthDates = new ArrayList<>();

            TicketDAO ticketDao = new TicketDAOImpl();

            try{

                ticketDao.getAllTicketBooking(bookingIds.get(i), ticketNumbers, seats, checkedIns, passengerSSNs, firstNames, lastNames, birthDates);

            } catch (SQLException e) {

                new FloatingMessage("Errore nella connessione al Database (Biglietti)!", searchButton, FloatingMessage.ERROR_MESSAGE);
            }

            try{
                if(!ticketNumbers.isEmpty()) {

                    bookingController.getSearchBookingResult().add(new Booking(BookingStatus.valueOf(bookingStatus.get(i)), bookingDates.get(i),
                            customerController.getLoggedCustomer(), flightController.getSearchBookingResult().getLast(),
                            ticketNumbers.getFirst(), seats.getFirst(), checkedIns.getFirst(),
                            firstNames.getFirst(), lastNames.getFirst(), passengerSSNs.getFirst(), birthDates.getFirst()));
                    bookingController.getSearchBookingResultIds().add(bookingIds.get(i));
                }else{
                    throw new InvalidTicket("");
                }

            }catch (Exception e){
                new FloatingMessage("Errore nella connessione al Database!", searchButton, FloatingMessage.ERROR_MESSAGE);
            }

            for(int j = 1; j < ticketNumbers.size(); j++){
                try{
                    bookingController.getSearchBookingResult().getLast().getTickets().add(new Ticket(ticketNumbers.get(j), seats.get(j), checkedIns.get(j),
                            flightController.getSearchBookingResult().getLast(), bookingController.getSearchBookingResult().getLast(),
                            firstNames.get(j), lastNames.get(j), passengerSSNs.get(j), birthDates.get(j)));

                }catch (Exception e){
                    new FloatingMessage("Errore nella connessione al Database!", searchButton, FloatingMessage.ERROR_MESSAGE);
                }
            }

            ticketController.getSearchBookingResult().addAll(bookingController.getSearchBookingResult().getLast().getTickets());
            for(Ticket x: bookingController.getSearchBookingResult().getLast().getTickets()){
                if(!actualSSNs.contains(x.getPassenger().getPassengerSSN())){
                    actualSSNs.add(x.getPassenger().getPassengerSSN());
                    passengerController.getSearchBookingResult().add(x.getPassenger());
                }

            }

        }


    }

    /**
     * Searches for customer bookings using passenger-based filtering criteria.
     * <p>
     * This method performs a filtered search for bookings belonging to the currently
     * logged-in customer based on passenger information such as names, SSN, and
     * ticket numbers. This search method is particularly useful when customers
     * need to find bookings but don't remember flight details.
     * </p>
     * <p>
     * The search supports the following passenger-based filtering criteria:
     * </p>
     * <ul>
     *   <li>First name - partial matching with case-insensitive search</li>
     *   <li>Last name - partial matching with case-insensitive search</li>
     *   <li>Passenger SSN - partial matching for identification</li>
     *   <li>Ticket number - exact or partial matching for ticket lookup</li>
     * </ul>
     * <p>
     * The search uses flexible matching techniques (ILIKE in PostgreSQL) to provide
     * user-friendly search capabilities that don't require exact matches. This allows
     * customers to find their bookings even with partial or approximate information.
     * </p>
     * <p>
     * Results include complete booking information with associated flight details,
     * full passenger information, and ticket data. The method processes complex
     * relationships between passengers, tickets, and bookings to provide comprehensive
     * search results.
     * </p>
     * <p>
     * Flight objects are created as either {@link Arriving} or {@link Departing}
     * instances based on flight types, ensuring proper display and behavior in
     * the user interface. Duplicate results are avoided through careful tracking
     * of processed records.
     * </p>
     *
     * @param firstName the passenger first name for filtering (null for no filter)
     * @param lastName the passenger last name for filtering (null for no filter)
     * @param passengerSSN the passenger SSN for filtering (null for no filter)
     * @param ticketNumber the ticket number for filtering (null for no filter)
     * @param bookingDates list to be populated with booking creation dates
     * @param bookingStatus list to be populated with booking status values
     * @param flightIds list to be populated with flight identifiers
     * @param searchButton the button to use as reference for error message display
     */
    public void searchBooksLoogedCustomerFilteredPassengers(String firstName, String lastName, String passengerSSN, String ticketNumber,
                                                            List<Date> bookingDates, List<String> bookingStatus, List<String> flightIds, JButton searchButton) {

        ArrayList<String> companyNames = new ArrayList<>();
        ArrayList<Date> dates = new ArrayList<>();
        ArrayList<Time> departureTimes = new ArrayList<>();
        ArrayList<Time> arrivalTimes = new ArrayList<>();
        ArrayList<String> status = new ArrayList<>();
        ArrayList<Integer> maxSeats = new ArrayList<>();
        ArrayList<Integer> freeSeats = new ArrayList<>();
        ArrayList<String> cities = new ArrayList<>();

        ArrayList<Boolean> types = new ArrayList<>();

        ArrayList<Integer> bookingIds = new ArrayList<>();

        try{
            BookingDAO bookingDAO = new BookingDAOImpl();

            bookingDAO.searchBooksCustomerFilteredPassengers(firstName, lastName, passengerSSN, ticketNumber,
                    getCustomerController().getLoggedCustomerId(), flightIds, companyNames, dates, departureTimes, arrivalTimes, status, maxSeats, freeSeats,
                    cities, types, bookingDates, bookingStatus, bookingIds);


        } catch (SQLException e) {
            new FloatingMessage("Errore nella connessione al Database (Prenotazioni)!", searchButton, FloatingMessage.ERROR_MESSAGE);
        }

        ArrayList<String> actualFlightIds = new ArrayList<>();

        bookingController.setSearchBookingResult(new ArrayList<>());
        bookingController.setSearchBookingResultIds(new ArrayList<>());
        flightController.setSearchBookingResult(new ArrayList<>());

        ticketController.setSearchBookingResult(new ArrayList<>());
        passengerController.setSearchBookingResult(new ArrayList<>());

        ArrayList<String> actualSSNs = new ArrayList<>();

        for(int i = 0; i < flightIds.size(); i++){

            if(!actualFlightIds.contains(flightIds.get(i))){

                actualFlightIds.add(flightIds.get(i));

                if(Boolean.TRUE.equals(types.get(i))){   //alloco Departing

                    flightController.getSearchBookingResult().add(new Departing( flightIds.get(i), companyNames.get(i), dates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                            FlightStatus.valueOf(status.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i)));

                }else{              //alloco Arriving

                    flightController.getSearchBookingResult().add(new Arriving( flightIds.get(i), companyNames.get(i), dates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                            FlightStatus.valueOf(status.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i)));


                }
            }

            ArrayList<String> ticketNumbers = new ArrayList<>();
            ArrayList<Integer> seats = new ArrayList<>();
            ArrayList<Boolean> checkedIns = new ArrayList<>();
            ArrayList<String> passengerSSNs = new ArrayList<>();
            ArrayList<String> firstNames = new ArrayList<>();
            ArrayList<String> lastNames = new ArrayList<>();
            ArrayList<Date> birthDates = new ArrayList<>();

            TicketDAO ticketDao = new TicketDAOImpl();

            try{

                ticketDao.getAllTicketBooking(bookingIds.get(i), ticketNumbers, seats, checkedIns, passengerSSNs, firstNames, lastNames, birthDates);

            } catch (SQLException e) {

                new FloatingMessage("Errore nella connessione al Database (Biglietti)!", searchButton, FloatingMessage.ERROR_MESSAGE);
            }

            try{
                if(!ticketNumbers.isEmpty()) {

                    bookingController.getSearchBookingResult().add(new Booking(BookingStatus.valueOf(bookingStatus.get(i)), bookingDates.get(i),
                            customerController.getLoggedCustomer(), flightController.getSearchBookingResult().getLast(),
                            ticketNumbers.getFirst(), seats.getFirst(), checkedIns.getFirst(),
                            firstNames.getFirst(), lastNames.getFirst(), passengerSSNs.getFirst(), birthDates.getFirst()));
                    bookingController.getSearchBookingResultIds().add(bookingIds.get(i));
                }else{
                    throw new InvalidTicket("");
                }

            }catch (Exception e){
                new FloatingMessage("Errore nella connessione al Database (Biglietti)!", searchButton, FloatingMessage.ERROR_MESSAGE);
            }

            for(int j = 1; j < ticketNumbers.size(); j++){
                try{
                    bookingController.getSearchBookingResult().getLast().getTickets().add(new Ticket(ticketNumbers.get(j), seats.get(j), checkedIns.get(j),
                            flightController.getSearchBookingResult().getLast(), bookingController.getSearchBookingResult().getLast(),
                            firstNames.get(j), lastNames.get(j), passengerSSNs.get(j), birthDates.get(j)));

                }catch (Exception e){
                    new FloatingMessage("Errore nella connessione al Database!", searchButton, FloatingMessage.ERROR_MESSAGE);
                }
            }

            ticketController.getSearchBookingResult().addAll(bookingController.getSearchBookingResult().getLast().getTickets());
            for(Ticket x: bookingController.getSearchBookingResult().getLast().getTickets()){
                if(!actualSSNs.contains(x.getPassenger().getPassengerSSN())){
                    actualSSNs.add(x.getPassenger().getPassengerSSN());
                    passengerController.getSearchBookingResult().add(x.getPassenger());
                }

            }

        }

    }

    /**
     * Loads and checks for existing bookings for the current flight and logged-in customer.
     * <p>
     * This method determines whether the customer should be directed to view existing
     * bookings or create a new booking for the selected flight. It searches for any
     * existing bookings the customer has made for the current flight and loads the
     * complete booking information if found.
     * </p>
     * <p>
     * The method performs a comprehensive data loading process including:
     * </p>
     * <ul>
     *   <li>Searching for existing bookings for the current flight and customer</li>
     *   <li>Loading complete ticket information for each found booking</li>
     *   <li>Creating booking objects with associated passenger data</li>
     *   <li>Setting up controller caches with loaded data</li>
     *   <li>Organizing data for immediate GUI consumption</li>
     * </ul>
     * <p>
     * The return value indicates whether existing bookings were found, allowing
     * the calling code to decide whether to show the "My Bookings" interface
     * or proceed with new booking creation.
     * </p>
     * <p>
     * Flight information is set up in the search results to ensure consistency
     * between the selected flight and the booking data, maintaining proper
     * relationships throughout the application flow.
     * </p>
     * <p>
     * Passenger data is processed to avoid duplicates while ensuring that all
     * passengers associated with the customer's bookings are properly loaded
     * and available for display or modification operations.
     * </p>
     *
     * @return true if existing bookings were found for the current flight and customer, false otherwise
     */
    public boolean loadAndCheckIfOpenMyBookingsOrNewBooking() {

        String flightId = flightController.getId();

        ArrayList<Integer> bookingIds = new ArrayList<>();
        ArrayList<Date> bookingDates = new ArrayList<>();
        ArrayList<String> bookingStatus = new ArrayList<>();

        try{
            BookingDAO bookingDAO = new BookingDAOImpl();

            bookingDAO.searchBooksCustomerForAFlight(flightId, getCustomerController().getLoggedCustomerId(),
                                                        bookingDates, bookingStatus, bookingIds);


        } catch (SQLException e) {
            new FloatingMessage("Errore nella connessione al Database (Prenotazioni)!", errorButton, FloatingMessage.ERROR_MESSAGE);
        }

        flightController.setSearchBookingResult(new ArrayList<>());
        flightController.getSearchBookingResult().add(flightController.getFlight());

        bookingController.setSearchBookingResult(new ArrayList<>());
        bookingController.setSearchBookingResultIds(new ArrayList<>());

        ticketController.setSearchBookingResult(new ArrayList<>());
        passengerController.setSearchBookingResult(new ArrayList<>());

        ArrayList<String> actualSSNs = new ArrayList<>();

        for(int i = 0; i < bookingIds.size(); i++){

            ArrayList<String> ticketNumbers = new ArrayList<>();
            ArrayList<Integer> seats = new ArrayList<>();
            ArrayList<Boolean> checkedIns = new ArrayList<>();
            ArrayList<String> passengerSSNs = new ArrayList<>();
            ArrayList<String> firstNames = new ArrayList<>();
            ArrayList<String> lastNames = new ArrayList<>();
            ArrayList<Date> birthDates = new ArrayList<>();

            TicketDAO ticketDao = new TicketDAOImpl();

            try{

                ticketDao.getAllTicketBooking(bookingIds.get(i), ticketNumbers, seats, checkedIns, passengerSSNs, firstNames, lastNames, birthDates);

            } catch (SQLException e) {

                new FloatingMessage("Errore nella connessione al Database (Biglietti)!", errorButton, FloatingMessage.ERROR_MESSAGE);
            }

            try{
                if(!ticketNumbers.isEmpty()) {

                    bookingController.getSearchBookingResult().add(new Booking(BookingStatus.valueOf(bookingStatus.get(i)), bookingDates.get(i),
                            customerController.getLoggedCustomer(), flightController.getFlight(),
                            ticketNumbers.getFirst(), seats.getFirst(), checkedIns.getFirst(),
                            firstNames.getFirst(), lastNames.getFirst(), passengerSSNs.getFirst(), birthDates.getFirst()));
                    bookingController.getSearchBookingResultIds().add(bookingIds.get(i));
                }else{
                    throw new InvalidTicket("");
                }

            }catch (Exception e){
                new FloatingMessage("Errore nella connessione al Database (Biglietti)!", errorButton, FloatingMessage.ERROR_MESSAGE);
            }

            for(int j = 1; j < ticketNumbers.size(); j++){
                try{
                    bookingController.getSearchBookingResult().getLast().getTickets().add(new Ticket(ticketNumbers.get(j), seats.get(j), checkedIns.get(j),
                            flightController.getFlight(), bookingController.getSearchBookingResult().getLast(),
                            firstNames.get(j), lastNames.get(j), passengerSSNs.get(j), birthDates.get(j)));

                }catch (Exception e){
                    new FloatingMessage("Errore nella connessione al Database!", errorButton, FloatingMessage.ERROR_MESSAGE);
                }
            }

            ticketController.getSearchBookingResult().addAll(bookingController.getSearchBookingResult().getLast().getTickets());
            for(Ticket x: bookingController.getSearchBookingResult().getLast().getTickets()){
                if(!actualSSNs.contains(x.getPassenger().getPassengerSSN())){
                    actualSSNs.add(x.getPassenger().getPassengerSSN());
                    passengerController.getSearchBookingResult().add(x.getPassenger());
                }

            }

        }

        return !bookingIds.isEmpty();

    }

    /**
     * Clears the search booking result cache to free memory and reset state.
     * <p>
     * This utility method clears all cached booking search results from the
     * various controllers, ensuring that stale data doesn't interfere with
     * new search operations. It resets the state of booking, flight, and
     * related result collections.
     * </p>
     * <p>
     * Cache clearing is important for memory management and ensuring that
     * users always see current data when performing new searches or
     * navigating between different application sections.
     * </p>
     */
    public void clearSearchBookingResultCache() {

        bookingController.setSearchBookingResult(null);
        bookingController.setSearchBookingResultIds(null);
        flightController.setSearchBookingResult(null);

    }

    /**
     * Clears the search flights result cache to free memory and reset state.
     * <p>
     * This utility method clears cached flight search results, ensuring that
     * memory is freed and that stale flight data doesn't interfere with new
     * search operations. This is particularly important when switching between
     * different flight search contexts or user sessions.
     * </p>
     */
    public void clearSearchFlightsResultCache() {

        flightController.setSearchResult(null);

    }

    /**
     * Verifies user credentials and establishes user session for both customers and administrators.
     * <p>
     * This method handles the complete authentication process for the airport management system,
     * supporting both username/password and email/password authentication methods for both
     * customer and administrator user types.
     * </p>
     * <p>
     * The authentication process follows these steps:
     * </p>
     * <ol>
     *   <li>Validates input format (email vs username) using client-side validation</li>
     *   <li>Attempts administrator authentication first</li>
     *   <li>Falls back to customer authentication if admin authentication fails</li>
     *   <li>Establishes user session with appropriate controller instances</li>
     *   <li>Provides user feedback for authentication results</li>
     * </ol>
     * <p>
     * The method implements a hierarchical authentication approach where administrator
     * accounts are checked first, followed by customer accounts. This ensures that
     * administrative users have priority access and that the system can distinguish
     * between different user types automatically.
     * </p>
     * <p>
     * Input validation includes email format checking and username format validation
     * to prevent unnecessary database queries for obviously invalid credentials.
     * This improves performance and provides immediate feedback for format errors.
     * </p>
     * <p>
     * Upon successful authentication, user sessions are established in multiple
     * controllers (AdminController/CustomerController and UserController) to ensure
     * that user context is available throughout the application.
     * </p>
     * <p>
     * Error handling provides specific feedback through {@link FloatingMessage}
     * components, distinguishing between validation errors, authentication failures,
     * and database connectivity issues.
     * </p>
     *
     * @param loggingInfo the username or email address provided by the user
     * @param hashedPassword the pre-hashed password provided by the user
     * @param loginButton the button to use as reference for error message display
     * @return true if authentication was successful, false otherwise
     */
    public boolean verifyUser(String loggingInfo, String hashedPassword, JButton loginButton){

        //Avoid opening DB if it is obvious that it won't contain the user
        if(loggingInfo.contains("@")){
            if (userController.isInvalidMail(loggingInfo)){
                new FloatingMessage("<html>User o mail non valida</html>", loginButton, FloatingMessage.WARNING_MESSAGE);
                return false;
            }
        } else if(userController.isInvalidUsername(loggingInfo)){
            new FloatingMessage("<html>User o mail non valida</html>", loginButton, FloatingMessage.WARNING_MESSAGE);
            return false;
        }

        ArrayList<Integer> userID = new ArrayList<>();
        ArrayList<String> mail = new ArrayList<>();
        ArrayList<String> username = new ArrayList<>();

        try{
            AdminDAOImpl adminDAO = new AdminDAOImpl();
            if(loggingInfo.contains("@")){
                adminDAO.searchUserByMail(userID, username, loggingInfo, hashedPassword);
                adminController.setLoggedAdmin(new Admin(username.getFirst(), loggingInfo, hashedPassword),// filepath: src/main/java/controller/Controller.java
package controller;

import dao.*;
import gui.DisposableObject;
import gui.FloatingMessage;

import gui.LuggagePanel;
import gui.PassengerPanel;
import implementazioni_postgres_dao.*;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Central controller class for the airport management system that coordinates between GUI components and data access objects.
 * <p>
 * This class serves as the main controller in the MVC (Model-View-Controller) architecture, managing all interactions
 * between the user interface components and the underlying business logic. It orchestrates operations across multiple
 * specialized controller classes and provides a unified interface for all system operations.
 * </p>
 * <p>
 * The Controller class manages the following core functionalities:
 * </p>
 * <ul>
 *   <li>User authentication and session management for both customers and administrators</li>
 *   <li>Flight search, booking, and reservation management operations</li>
 *   <li>Navigation between different GUI components and application states</li>
 *   <li>Integration with database access objects for persistent data operations</li>
 *   <li>Error handling and user notification through floating messages</li>
 *   <li>Coordination between specialized controllers for different domain objects</li>
 * </ul>
 * <p>
 * The class follows a delegation pattern, utilizing specialized controller classes for different domain areas:
 * </p>
 * <ul>
 *   <li>{@link AdminController} - Administrative user management and operations</li>
 *   <li>{@link BookingController} - Booking creation, modification, and management</li>
 *   <li>{@link CustomerController} - Customer account and profile management</li>
 *   <li>{@link FlightController} - Flight information and operations</li>
 *   <li>{@link LuggageController} - Luggage tracking and management</li>
 *   <li>{@link PassengerController} - Passenger information handling</li>
 *   <li>{@link TicketController} - Ticket generation and management</li>
 *   <li>{@link UserController} - General user operations and validation</li>
 * </ul>
 * <p>
 * Navigation management is handled through a stack-based approach using {@link DisposableObject} instances,
 * allowing for proper window lifecycle management, state preservation, and seamless transitions between
 * different application screens while maintaining user context and system resources.
 * </p>
 * <p>
 * Error handling is centralized through comprehensive exception catching and user-friendly message display
 * using {@link FloatingMessage} components. The class maintains a logger for system monitoring and
 * debugging purposes.
 * </p>
 * <p>
 * Database operations are performed through DAO implementations that provide abstraction from the underlying
 * PostgreSQL database, ensuring clean separation of concerns and maintainable code architecture.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see AdminController
 * @see BookingController
 * @see CustomerController
 * @see FlightController
 * @see LuggageController
 * @see PassengerController
 * @see TicketController
 * @see UserController
 * @see DisposableObject
 * @see FloatingMessage
 */
public class Controller {

    /**
     * Controller for administrative user operations and management.
     */
    private final AdminController adminController;

    /**
     * Controller for booking operations including creation, modification, and search.
     */
    private final BookingController bookingController;

    /**
     * Controller for customer account management and operations.
     */
    private final CustomerController customerController;

    /**
     * Controller for flight information management and operations.
     */
    private final FlightController flightController;

    /**
     * Controller for airport gate management operations.
     */
    private final GateController gateController;

    /**
     * Controller for luggage tracking and management operations.
     */
    private final LuggageController luggageController;

    /**
     * Controller for passenger information management.
     */
    private final PassengerController passengerController;

    /**
     * Controller for general user operations and validation.
     */
    private final UserController userController;

    /**
     * Controller for ticket generation and management operations.
     */
    private final TicketController ticketController;

    /**
     * Button reference used for displaying error messages in appropriate context.
     */
    private JButton errorButton;

    /**
     * Logger instance for recording system operations and errors.
     */
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());

    /**
     * Constructs a new Controller instance and initializes all specialized controller components.
     * <p>
     * This constructor creates instances of all specialized controllers that handle specific
     * domain operations within the airport management system. Each controller is responsible
     * for managing its respective domain objects and operations.
     * </p>
     * <p>
     * The initialization follows the dependency injection pattern, ensuring that all
     * controllers are properly instantiated and ready for use throughout the application
     * lifecycle.
     * </p>
     */
    public Controller() {
        adminController = new AdminController();
        bookingController = new BookingController();
        customerController = new CustomerController();
        flightController = new FlightController();
        gateController = new GateController();
        luggageController = new LuggageController();
        passengerController = new PassengerController();
        userController = new UserController();
        ticketController = new TicketController();
    }

    /**
     * Returns the booking controller for managing reservation operations.
     *
     * @return the {@link BookingController} instance for booking management
     */
    public BookingController getBookingController() {
        return bookingController;
    }

    /**
     * Returns the customer controller for managing customer account operations.
     *
     * @return the {@link CustomerController} instance for customer management
     */
    public CustomerController getCustomerController() {
        return customerController;
    }

    /**
     * Returns the flight controller for managing flight operations.
     *
     * @return the {@link FlightController} instance for flight management
     */
    public FlightController getFlightController() {
        return flightController;
    }

    /**
     * Returns the gate controller for managing airport gate operations.
     *
     * @return the {@link GateController} instance for gate management
     */
    public GateController getGateController() {
        return gateController;
    }

    /**
     * Returns the luggage controller for managing luggage tracking operations.
     *
     * @return the {@link LuggageController} instance for luggage management
     */
    public LuggageController getLuggageController() {
        return luggageController;
    }

    /**
     * Returns the user controller for managing general user operations.
     *
     * @return the {@link UserController} instance for user management
     */
    public UserController getUserController() {
        return userController;
    }

    /**
     * Navigates to the login screen by disposing of all intermediate windows and restoring the login window.
     * <p>
     * This method implements a complete navigation reset to the login screen, preserving the window
     * dimensions, location, and state of the current window for a smooth visual transition. All
     * intermediate windows in the navigation stack are properly disposed of to free system resources.
     * </p>
     * <p>
     * The navigation process follows these steps:
     * </p>
     * <ol>
     *   <li>Captures current window properties (size, location, maximized state)</li>
     *   <li>Calls dispose methods on all windows except the base login window</li>
     *   <li>Removes disposed windows from the navigation stack</li>
     *   <li>Restores the login window with preserved visual properties</li>
     *   <li>Calls restore methods to reinitialize the login window state</li>
     * </ol>
     * <p>
     * This method is typically used when users want to return to the login screen from any
     * point in the application, such as switching between user accounts or accessing the
     * system from a different user perspective.
     * </p>
     *
     * @param callingObjects stack of {@link DisposableObject} instances representing the current navigation state
     */
    public void goToLogin(List<DisposableObject> callingObjects){

        Dimension sourceDimension = callingObjects.getLast().getFrame().getSize();
        Point sourceLocation = callingObjects.getLast().getFrame().getLocation();
        int sourceExtendedState = callingObjects.getLast().getFrame().getExtendedState();

        for (int i = callingObjects.size() - 1; i > 0; i--) {

            callingObjects.get(i).doOnDispose(callingObjects, this);
            callingObjects.getLast().getFrame().dispose();
            callingObjects.removeLast();
        }

        if(sourceExtendedState != Frame.MAXIMIZED_BOTH){ //if frame is maximized size and location are automatic
            callingObjects.getLast().getFrame().setSize(sourceDimension);
            callingObjects.getLast().getFrame().setLocation(sourceLocation);
        }
        callingObjects.getLast().getFrame().setExtendedState(sourceExtendedState);

        callingObjects.getLast().doOnRestore(callingObjects, this);

        callingObjects.getLast().getFrame().setVisible(true);
    }

    /**
     * Navigates to the home screen by disposing of intermediate windows while preserving the base navigation structure.
     * <p>
     * This method provides navigation to the user's home screen, which is typically the second window
     * in the navigation stack (after login). It preserves window visual properties and properly
     * manages the disposal of intermediate windows to maintain system resource efficiency.
     * </p>
     * <p>
     * The navigation process maintains the login window as the base layer and the home screen
     * as the primary user interface, disposing of all intermediate screens that may have been
     * opened during the user's session.
     * </p>
     * <p>
     * Visual continuity is maintained by preserving the current window's dimensions, position,
     * and maximization state, providing a seamless user experience during navigation transitions.
     * </p>
     * <p>
     * This method is commonly used for "Home" button functionality throughout the application,
     * allowing users to quickly return to their main dashboard or starting point while maintaining
     * their session and user context.
     * </p>
     *
     * @param callingObjects stack of {@link DisposableObject} instances representing the current navigation state
     */
    public void goHome (List<DisposableObject> callingObjects) {

        Dimension sourceDimension = callingObjects.getLast().getFrame().getSize();
        Point sourceLocation = callingObjects.getLast().getFrame().getLocation();
        int sourceExtendedState = callingObjects.getLast().getFrame().getExtendedState();

        for (int i = callingObjects.size() - 1; i > 1; i--) {

            callingObjects.get(i).doOnDispose(callingObjects, this);
            callingObjects.getLast().getFrame().dispose();
            callingObjects.removeLast();
        }

        if(sourceExtendedState != Frame.MAXIMIZED_BOTH){ //if frame is maximized size and location are automatic
            callingObjects.getLast().getFrame().setSize(sourceDimension);
            callingObjects.getLast().getFrame().setLocation(sourceLocation);
        }
        callingObjects.getLast().getFrame().setExtendedState(sourceExtendedState);

        callingObjects.getLast().doOnRestore(callingObjects, this);

        callingObjects.getLast().getFrame().setVisible(true);

    }

    /**
     * Navigates back to the previous screen in the navigation stack.
     * <p>
     * This method implements a standard "back" navigation pattern by disposing of the current
     * window and returning to the previous window in the navigation stack. It preserves the
     * visual properties of the current window and applies them to the previous window for
     * consistent user experience.
     * </p>
     * <p>
     * The back navigation process includes proper resource cleanup through the disposal
     * pattern and state restoration for the previous window. This ensures that both the
     * departing and arriving windows maintain their expected state and functionality.
     * </p>
     * <p>
     * Window properties such as size, location, and maximization state are preserved
     * and transferred to provide visual continuity during the navigation transition.
     * </p>
     * <p>
     * This method is typically bound to "Back" buttons throughout the application interface,
     * providing consistent navigation behavior across all application screens.
     * </p>
     *
     * @param callingObjects stack of {@link DisposableObject} instances representing the current navigation state
     */
    public void goBack (List<DisposableObject> callingObjects) {

        Dimension sourceDimension = callingObjects.getLast().getFrame().getSize();
        Point sourceLocation = callingObjects.getLast().getFrame().getLocation();
        int sourceExtendedState = callingObjects.getLast().getFrame().getExtendedState();

        callingObjects.getLast().doOnDispose(callingObjects, this);
        callingObjects.getLast().getFrame().dispose();
        callingObjects.removeLast();

        if(sourceExtendedState != Frame.MAXIMIZED_BOTH){ //if frame is maximized size and location are automatic
            callingObjects.getLast().getFrame().setSize(sourceDimension);
            callingObjects.getLast().getFrame().setLocation(sourceLocation);
        }
        callingObjects.getLast().getFrame().setExtendedState(sourceExtendedState);

        callingObjects.getLast().doOnRestore(callingObjects, this);

        callingObjects.getLast().getFrame().setVisible(true);

    }

    /**
     * Performs user logout by clearing the session and returning to the login screen.
     * <p>
     * This method implements a complete logout process that disposes of all application
     * windows except the base login window, effectively clearing the user's session
     * and returning the application to its initial state.
     * </p>
     * <p>
     * The logout process ensures that all user-specific data and interface components
     * are properly cleaned up, preventing any session data leakage or memory issues.
     * The login window is restored to its initial state, ready for a new user session.
     * </p>
     * <p>
     * Unlike other navigation methods, logout does not preserve window properties
     * since the user context is being completely reset. The login window returns
     * to its default state and configuration.
     * </p>
     * <p>
     * This method is typically triggered by "Logout" buttons or menu items throughout
     * the application, providing users with a clean way to end their session and
     * allow other users to access the system.
     * </p>
     *
     * @param callingObjects stack of {@link DisposableObject} instances representing the current navigation state
     */
    public void logOut (List<DisposableObject> callingObjects) {

        for (int i = callingObjects.size() - 1; i > 0; i--) {
            callingObjects.getLast().doOnDispose(callingObjects, this);
            callingObjects.getLast().getFrame().dispose();
            callingObjects.removeLast();
        }

        callingObjects.getLast().doOnRestore(callingObjects, this);
        callingObjects.getLast().getFrame().setVisible(true);
    }

    /**
     * Creates a new booking in the system using passenger information from the provided panels.
     * <p>
     * This method orchestrates the complete booking creation process by collecting passenger
     * information from GUI panels, generating ticket numbers, and persisting the booking
     * data to the database through the appropriate DAO layer.
     * </p>
     * <p>
     * The booking creation process includes:
     * </p>
     * <ul>
     *   <li>Processing passenger panel data to extract passenger information</li>
     *   <li>Generating unique ticket numbers for each passenger</li>
     *   <li>Handling seat assignments and luggage information</li>
     *   <li>Creating database records for bookings, tickets, passengers, and luggage</li>
     *   <li>Maintaining data integrity through proper transaction handling</li>
     * </ul>
     * <p>
     * The method delegates the actual data extraction to {@link #preparePassengers} and
     * uses the {@link BookingDAOImpl} for database persistence. Error handling includes
     * comprehensive logging for system monitoring and debugging purposes.
     * </p>
     * <p>
     * All booking operations are performed atomically to ensure data consistency and
     * prevent partial booking creation in case of system failures.
     * </p>
     *
     * @param passengerPanels list of {@link PassengerPanel} instances containing passenger information and preferences
     * @param bookingStatus the initial status for the new booking (e.g., "PENDING", "CONFIRMED")
     */
    public void addBooking(List<PassengerPanel> passengerPanels, String bookingStatus) {

        try {

            BookingDAOImpl bookingDAO = new BookingDAOImpl();

            ArrayList<String> ticketsNumbers = new ArrayList<>();
            ArrayList<Integer> seats = new ArrayList<>();
            ArrayList<String> firstNames = new ArrayList<>();
            ArrayList<String> lastNames = new ArrayList<>();
            ArrayList<Date> birthDates = new ArrayList<>();
            ArrayList<String> passengerSSNs = new ArrayList<>();
            ArrayList<String> luggagesTypes = new ArrayList<>();
            ArrayList<String> ticketsForLuggagesTypes = new ArrayList<>();

            preparePassengers(passengerPanels, ticketsNumbers, seats, firstNames, lastNames, birthDates, passengerSSNs, luggagesTypes, ticketsForLuggagesTypes);

            bookingDAO.addBooking(getUserController().getLoggedUserId(), flightController.getId(), bookingStatus, ticketsNumbers,
                    seats, firstNames, lastNames, birthDates, passengerSSNs, luggagesTypes, ticketsForLuggagesTypes);

        } catch (SQLException e) {
            Controller.getLogger().log(Level.SEVERE, e.getSQLState());
        }
    }

    /**
     * Translates internal flight status enum values to user-friendly Italian display strings.
     * <p>
     * This utility method provides localization support for flight status display in the
     * user interface by converting internal enum values to appropriate Italian language
     * representations. This ensures consistent and user-friendly status display throughout
     * the application.
     * </p>
     * <p>
     * The translation mapping includes all standard flight status values:
     * </p>
     * <ul>
     *   <li>PROGRAMMED → "In programma" (Scheduled)</li>
     *   <li>CANCELLED → "Cancellato" (Cancelled)</li>
     *   <li>DELAYED → "In ritardo" (Delayed)</li>
     *   <li>ABOUT_TO_DEPART → "In partenza" (About to depart)</li>
     *   <li>DEPARTED → "Partito" (Departed)</li>
     *   <li>ABOUT_TO_ARRIVE → "In arrivo" (About to arrive)</li>
     *   <li>LANDED → "Atterrato" (Landed)</li>
     * </ul>
     * <p>
     * This method is essential for maintaining a consistent Italian user interface
     * while using English enum values internally for system operations and database
     * storage.
     * </p>
     *
     * @param status the {@link FlightStatus} enum value to be translated
     * @return the Italian translation of the flight status, or null if the status is not recognized
     */
    public static String translateFlightStatus(FlightStatus status){

        switch (status.toString()){
            case "PROGRAMMED":
                return "In programma";
            case "CANCELLED":
                return "Cancellato";
            case "DELAYED":
                return "In ritardo";
            case "ABOUT_TO_DEPART":
                return "In partenza";
            case "DEPARTED":
                return "Partito";
            case "ABOUT_TO_ARRIVE":
                return "In arrivo";
            case "LANDED":
                return "Atterrato";
            default:
                return null;
        }
    }

    /**
     * Modifies an existing booking using updated passenger information from the provided panels.
     * <p>
     * This method handles the complex process of updating an existing booking while maintaining
     * data integrity and consistency. The modification process involves updating passenger
     * information, ticket details, seat assignments, and luggage information while preserving
     * the booking's core identity and relationships.
     * </p>
     * <p>
     * The modification process includes:
     * </p>
     * <ul>
     *   <li>Processing updated passenger information from GUI panels</li>
     *   <li>Generating new ticket numbers as needed for additional passengers</li>
     *   <li>Updating seat assignments and luggage preferences</li>
     *   <li>Using temporary ticket mechanisms to maintain referential integrity</li>
     *   <li>Updating the booking status as specified</li>
     * </ul>
     * <p>
     * The method uses {@link BookingDAOImpl#modifyBooking} which implements a sophisticated
     * temporary ticket approach to ensure that all database constraints are maintained
     * during the modification process. A temporary ticket number is generated to facilitate
     * the update process without violating foreign key constraints.
     * </p>
     * <p>
     * All modification operations are performed atomically to ensure data consistency
     * and prevent corruption of booking data in case of system failures.
     * </p>
     *
     * @param passengerPanels list of {@link PassengerPanel} instances containing updated passenger information
     * @param bookingStatus the new status for the modified booking
     */
    public void modifyBooking (List<PassengerPanel> passengerPanels, String bookingStatus) {

        try {

            BookingDAOImpl bookingDAO = new BookingDAOImpl();

            ArrayList<String> ticketsNumbers = new ArrayList<>();
            ArrayList<Integer> seats = new ArrayList<>();
            ArrayList<String> firstNames = new ArrayList<>();
            ArrayList<String> lastNames = new ArrayList<>();
            ArrayList<Date> birthDates = new ArrayList<>();
            ArrayList<String> passengerSSNs = new ArrayList<>();
            ArrayList<String> luggagesTypes = new ArrayList<>();
            ArrayList<String> ticketsForLuggagesTypes = new ArrayList<>();

            preparePassengers(passengerPanels, ticketsNumbers, seats, firstNames, lastNames, birthDates, passengerSSNs, luggagesTypes, ticketsForLuggagesTypes);

            bookingDAO.modifyBooking(flightController.getId(), getBookingController().getId(), ticketsNumbers,
                    seats, firstNames, lastNames, birthDates, passengerSSNs, luggagesTypes, ticketsForLuggagesTypes, generateTicketNumber(passengerPanels.size() + 1), bookingStatus);

        } catch (SQLException e) {
            Controller.getLogger().log(Level.SEVERE, e.getSQLState());
        }
    }

    /**
     * Extracts and prepares passenger data from GUI panels for database operations.
     * <p>
     * This private utility method processes passenger information panels to extract all
     * necessary data for booking creation or modification operations. It handles the
     * complex task of correlating passenger information with ticket numbers, seat
     * assignments, and luggage preferences.
     * </p>
     * <p>
     * The preparation process includes:
     * </p>
     * <ul>
     *   <li>Generating or retrieving ticket numbers for each passenger</li>
     *   <li>Extracting seat preferences and assignments</li>
     *   <li>Collecting passenger personal information (names, birth dates, SSN)</li>
     *   <li>Processing luggage selections and associating them with tickets</li>
     *   <li>Maintaining proper correlation between passengers and their associated data</li>
     * </ul>
     * <p>
     * Ticket number generation is handled intelligently - if a passenger panel already
     * contains a ticket number (for modifications), it is preserved; otherwise, a new
     * unique ticket number is generated using the offset-based generation system.
     * </p>
     * <p>
     * Luggage information is processed by examining each luggage panel within passenger
     * panels, extracting selected luggage types, and associating them with the
     * appropriate ticket numbers for proper database relationships.
     * </p>
     *
     * @param passengerPanels list of {@link PassengerPanel} instances containing passenger information
     * @param ticketsNumbers list to be populated with ticket numbers for each passenger
     * @param seats list to be populated with seat assignments for each passenger
     * @param firstNames list to be populated with passenger first names
     * @param lastNames list to be populated with passenger last names
     * @param birthDates list to be populated with passenger birth dates
     * @param passengerSSNs list to be populated with passenger SSN identifiers
     * @param luggagesTypes list to be populated with selected luggage types
     * @param ticketsForLuggagesTypes list to be populated with ticket numbers associated with each luggage item
     */
    private void preparePassengers (List<PassengerPanel> passengerPanels, List<String> ticketsNumbers, List<Integer> seats, List<String> firstNames, List<String> lastNames,
                                    List<Date> birthDates, List<String> passengerSSNs, List<String> luggagesTypes, List<String> ticketsForLuggagesTypes) {

        int i = 0;

        for (PassengerPanel passengerPanel : passengerPanels) {

            if(passengerPanel.getTicketNumber() == null) ticketsNumbers.add(generateTicketNumber(i++));
            else ticketsNumbers.add(passengerPanel.getTicketNumber());

            seats.add(passengerPanel.getSeat());
            firstNames.add(passengerPanel.getPassengerName());
            lastNames.add(passengerPanel.getPassengerSurname());
            birthDates.add(passengerPanel.getPassengerDate());
            passengerSSNs.add(passengerPanel.getPassengerCF());

            for (LuggagePanel luggagePanel : passengerPanel.getLuggagesPanels()) {

                if (luggagePanel.getComboBox().getSelectedIndex() != 0 && luggagePanel.getComboBox().getSelectedItem() != null){
                        luggagesTypes.add(luggagePanel.getComboBox().getSelectedItem().toString());
                        ticketsForLuggagesTypes.add(ticketsNumbers.getLast());
                    }


            }
        }
    }

    /**
     * Generates a unique ticket number using the offset-based generation system.
     * <p>
     * This method provides a wrapper around the ticket generation functionality
     * implemented in {@link TicketDAOImpl}. It ensures that generated ticket
     * numbers are unique across the system and follow the established 13-digit
     * format with appropriate zero-padding.
     * </p>
     * <p>
     * The offset parameter allows for batch ticket generation scenarios where
     * multiple consecutive ticket numbers need to be generated for a single
     * booking operation. This prevents conflicts and ensures sequential numbering.
     * </p>
     * <p>
     * Ticket number generation is thread-safe and uses database-level maximum
     * value queries to ensure uniqueness even in concurrent environments.
     * </p>
     *
     * @param offset the number of increments to apply before generating the final ticket number
     * @return a unique 13-digit ticket number formatted with leading zeros
     */
    public String generateTicketNumber (int offset) {

        TicketDAOImpl ticketDAO = new TicketDAOImpl();

        return ticketDAO.generateTicketNumber(offset);
    }

    /**
     * Sets the error button reference for displaying contextual error messages.
     * <p>
     * This method allows GUI components to register a button that should be used
     * as the reference point for displaying error messages through {@link FloatingMessage}
     * components. This ensures that error messages appear in the appropriate context
     * relative to the user's current action.
     * </p>
     *
     * @param errorButton the {@link JButton} to use as a reference for error message positioning
     */
    public void setErrorButton(JButton errorButton) {
        this.errorButton = errorButton;
    }


    /**
     * Retrieves all bookings for the currently logged-in customer.
     * <p>
     * This method performs a comprehensive search for all bookings associated with the
     * currently logged-in customer account. It retrieves complete booking information
     * including flight details, passenger information, and ticket data, organizing
     * the results for display in the user interface.
     * </p>
     * <p>
     * The retrieval process includes:
     * </p>
     * <ul>
     *   <li>Querying the database for all customer bookings</li>
     *   <li>Retrieving associated flight information and details</li>
     *   <li>Loading ticket information for each booking</li>
     *   <li>Creating appropriate flight objects (Arriving/Departing) based on flight type</li>
     *   <li>Organizing data in controller result caches for GUI consumption</li>
     * </ul>
     * <p>
     * Flight objects are created as either {@link Arriving} or {@link Departing} instances
     * based on the flight type flag, ensuring that the correct flight behavior and
     * information display is available for each booking.
     * </p>
     * <p>
     * The method handles complex data relationships by maintaining separate result
     * collections for flights, bookings, tickets, and passengers, allowing the GUI
     * to access related information efficiently.
     * </p>
     * <p>
     * Error handling includes database connection failure notification through
     * {@link FloatingMessage} components, providing user feedback when operations
     * cannot be completed.
     * </p>
     *
     * @param bookingDates list to be populated with booking creation dates
     * @param bookingStatus list to be populated with booking status values
     * @param flightIds list to be populated with flight identifiers
     * @param searchButton the button to use as reference for error message display
     */
    public void getAllBooksLoogedCustomer(List<Date> bookingDates, List<String> bookingStatus, List<String> flightIds, JButton searchButton) {


        ArrayList<String> companyNames = new ArrayList<>();
        ArrayList<Date> dates = new ArrayList<>();
        ArrayList<Time> departureTimes = new ArrayList<>();
        ArrayList<Time> arrivalTimes = new ArrayList<>();
        ArrayList<String> status = new ArrayList<>();
        ArrayList<Integer> maxSeats = new ArrayList<>();
        ArrayList<Integer> freeSeats = new ArrayList<>();
        ArrayList<String> cities = new ArrayList<>();

        ArrayList<Boolean> types = new ArrayList<>();

        ArrayList<Integer> bookingIds = new ArrayList<>();

        try{
            BookingDAO bookingDAO = new BookingDAOImpl();

            bookingDAO.getAllBooksCustomer(getCustomerController().getLoggedCustomerId(), flightIds, companyNames, dates, departureTimes, arrivalTimes, status, maxSeats, freeSeats,
                    cities, types, bookingDates, bookingStatus, bookingIds);


        } catch (SQLException e) {
            new FloatingMessage("Errore nella connessione al Database (Prenotazioni)!", searchButton, FloatingMessage.ERROR_MESSAGE);
        }

        ArrayList<String> actualFlightIds = new ArrayList<>();

        bookingController.setSearchBookingResult(new ArrayList<>());
        bookingController.setSearchBookingResultIds(new ArrayList<>());
        flightController.setSearchBookingResult(new ArrayList<>());

        ticketController.setSearchBookingResult(new ArrayList<>());
        passengerController.setSearchBookingResult(new ArrayList<>());

        ArrayList<String> actualSSNs = new ArrayList<>();

        for(int i = 0; i < flightIds.size(); i++){

            if(!actualFlightIds.contains(flightIds.get(i))){

                actualFlightIds.add(flightIds.get(i));

                if(Boolean.TRUE.equals(types.get(i))){   //alloco Departing

                    flightController.getSearchBookingResult().add(new Departing( flightIds.get(i), companyNames.get(i), dates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                            FlightStatus.valueOf(status.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i)));

                }else{              //alloco Arriving

                    flightController.getSearchBookingResult().add(new Arriving( flightIds.get(i), companyNames.get(i), dates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                            FlightStatus.valueOf(status.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i)));


                }
            }

            ArrayList<String> ticketNumbers = new ArrayList<>();
            ArrayList<Integer> seats = new ArrayList<>();
            ArrayList<Boolean> checkedIns = new ArrayList<>();
            ArrayList<String> passengerSSNs = new ArrayList<>();
            ArrayList<String> firstNames = new ArrayList<>();
            ArrayList<String> lastNames = new ArrayList<>();
            ArrayList<Date> birthDates = new ArrayList<>();

            TicketDAO ticketDao = new TicketDAOImpl();

            try{

                ticketDao.getAllTicketBooking(bookingIds.get(i), ticketNumbers, seats, checkedIns, passengerSSNs, firstNames, lastNames, birthDates);

            } catch (SQLException e) {

                new FloatingMessage("Errore nella connessione al Database (Biglietti)!", searchButton, FloatingMessage.ERROR_MESSAGE);
            }

            try{
                if(!ticketNumbers.isEmpty()) {

                    bookingController.getSearchBookingResult().add(new Booking(BookingStatus.valueOf(bookingStatus.get(i)), bookingDates.get(i),
                            customerController.getLoggedCustomer(), flightController.getSearchBookingResult().getLast(),
                            ticketNumbers.getFirst(), seats.getFirst(), checkedIns.getFirst(),
                            firstNames.getFirst(), lastNames.getFirst(), passengerSSNs.getFirst(), birthDates.getFirst()));
                    bookingController.getSearchBookingResultIds().add(bookingIds.get(i));
                }else{
                    throw new InvalidTicket("");
                }

            }catch (Exception e){
                new FloatingMessage("Errore nella connessione al Database (Biglietti)!", searchButton, FloatingMessage.ERROR_MESSAGE);
            }

            for(int j = 1; j < ticketNumbers.size(); j++){
                try{
                    bookingController.getSearchBookingResult().getLast().getTickets().add(new Ticket(ticketNumbers.get(j), seats.get(j), checkedIns.get(j),
                            flightController.getSearchBookingResult().getLast(), bookingController.getSearchBookingResult().getLast(),
                            firstNames.get(j), lastNames.get(j), passengerSSNs.get(j), birthDates.get(j)));

                }catch (Exception e){
                    new FloatingMessage("Errore nella connessione al Database!", searchButton, FloatingMessage.ERROR_MESSAGE);
                }
            }

            ticketController.getSearchBookingResult().addAll(bookingController.getSearchBookingResult().getLast().getTickets());
            for(Ticket x: bookingController.getSearchBookingResult().getLast().getTickets()){
                if(!actualSSNs.contains(x.getPassenger().getPassengerSSN())){
                    actualSSNs.add(x.getPassenger().getPassengerSSN());
                    passengerController.getSearchBookingResult().add(x.getPassenger());
                }

            }

        }

    }


    /**
     * Searches for customer bookings using flight-based filtering criteria.
     * <p>
     * This method performs a filtered search for bookings belonging to the currently
     * logged-in customer based on flight characteristics such as origin/destination
     * cities, dates, and times. It provides flexible search capabilities to help
     * customers find specific bookings among their reservation history.
     * </p>
     * <p>
     * The search supports the following filtering criteria:
     * </p>
     * <ul>
     *   <li>Origin city - for flights departing from a specific location</li>
     *   <li>Destination city - for flights arriving at a specific location</li>
     *   <li>Date range - for flights within a specific time period</li>
     *   <li>Time range - for flights departing within specific hours</li>
     * </ul>
     * <p>
     * The method handles the special case of "Napoli" as the airport's base city,
     * implementing appropriate logic for local departures and arrivals. Search
     * results include complete booking information with associated flight details,
     * passenger information, and ticket data.
     * </p>
     * <p>
     * Results are organized in controller caches and processed to create appropriate
     * flight objects (Arriving/Departing) based on flight types, ensuring that the
     * GUI receives properly structured data for display purposes.
     * </p>
     * <p>
     * The method maintains data consistency by avoiding duplicate entries and
     * properly correlating related information across different domain objects.
     * </p>
     *
     * @param origin the departure city name for filtering (null for no filter)
     * @param destination the arrival city name for filtering (null for no filter)
     * @param dateBefore the start date for date range filtering (null for no filter)
     * @param dateAfter the end date for date range filtering (null for no filter)
     * @param timeBefore the start time for time range filtering (null for no filter)
     * @param timeAfter the end time for time range filtering (null for no filter)
     * @param bookingDates list to be populated with booking creation dates
     * @param bookingStatus list to be populated with booking status values
     * @param flightIds list to be populated with flight identifiers
     * @param searchButton the button to use as reference for error message display
     */
    public void searchBooksLoogedCustomerFilteredFlights(String origin, String destination, LocalDate dateBefore, LocalDate dateAfter, LocalTime timeBefore, LocalTime timeAfter,
                                                         List<Date> bookingDates, List<String> bookingStatus, List<String> flightIds, JButton searchButton) {

        ArrayList<String> companyNames = new ArrayList<>();
        ArrayList<Date> dates = new ArrayList<>();
        ArrayList<Time> departureTimes = new ArrayList<>();
        ArrayList<Time> arrivalTimes = new ArrayList<>();
        ArrayList<String> status = new ArrayList<>();
        ArrayList<Integer> maxSeats = new ArrayList<>();
        ArrayList<Integer> freeSeats = new ArrayList<>();
        ArrayList<String> cities = new ArrayList<>();

        ArrayList<Boolean> types = new ArrayList<>();

        ArrayList<Integer> bookingIds = new ArrayList<>();

        try{
            BookingDAO bookingDAO = new BookingDAOImpl();

            bookingDAO.searchBooksCustomerFilteredFlights(origin, destination, dateBefore, dateAfter, timeBefore, timeAfter,
                    getCustomerController().getLoggedCustomerId(), flightIds, companyNames, dates, departureTimes, arrivalTimes, status, maxSeats, freeSeats,
                    cities, types, bookingDates, bookingStatus, bookingIds);


        } catch (SQLException e) {
            new FloatingMessage("Errore nella connessione al Database (Prenotazioni)!", searchButton, FloatingMessage.ERROR_MESSAGE);
        }

        ArrayList<String> actualFlightIds = new ArrayList<>();

        bookingController.setSearchBookingResult(new ArrayList<>());
        bookingController.setSearchBookingResultIds(new ArrayList<>());
        flightController.setSearchBookingResult(new ArrayList<>());

        ticketController.setSearchBookingResult(new ArrayList<>());
        passengerController.setSearchBookingResult(new ArrayList<>());

        ArrayList<String> actualSSNs = new ArrayList<>();

        for(int i = 0; i < flightIds.size(); i++){

            if(!actualFlightIds.contains(flightIds.get(i))){

                actualFlightIds.add(flightIds.get(i));

                if(Boolean.TRUE.equals(types.get(i))){   //alloco Departing

                    flightController.getSearchBookingResult().add(new Departing( flightIds.get(i), companyNames.get(i), dates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                            FlightStatus.valueOf(status.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i)));

                }else{              //alloco Arriving

                    flightController.getSearchBookingResult().add(new Arriving( flightIds.get(i), companyNames.get(i), dates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                            FlightStatus.valueOf(status.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i)));


                }
            }

            ArrayList<String> ticketNumbers = new ArrayList<>();
            ArrayList<Integer> seats = new ArrayList<>();
            ArrayList<Boolean> checkedIns = new ArrayList<>();
            ArrayList<String> passengerSSNs = new ArrayList<>();
            ArrayList<String> firstNames = new ArrayList<>();
            ArrayList<String> lastNames = new ArrayList<>();
            ArrayList<Date> birthDates = new ArrayList<>();

            TicketDAO ticketDao = new TicketDAOImpl();

            try{

                ticketDao.getAllTicketBooking(bookingIds.get(i), ticketNumbers, seats, checkedIns, passengerSSNs, firstNames, lastNames, birthDates);

            } catch (SQLException e) {

                new FloatingMessage("Errore nella connessione al Database (Biglietti)!", searchButton, FloatingMessage.ERROR_MESSAGE);
            }

            try{
                if(!ticketNumbers.isEmpty()) {

                    bookingController.getSearchBookingResult().add(new Booking(BookingStatus.valueOf(bookingStatus.get(i)), bookingDates.get(i),
                            customerController.getLoggedCustomer(), flightController.getSearchBookingResult().getLast(),
                            ticketNumbers.getFirst(), seats.getFirst(), checkedIns.getFirst(),
                            firstNames.getFirst(), lastNames.getFirst(), passengerSSNs.getFirst(), birthDates.getFirst()));
                    bookingController.getSearchBookingResultIds().add(bookingIds.get(i));
                }else{
                    throw new InvalidTicket("");
                }

            }catch (Exception e){
                new FloatingMessage("Errore nella connessione al Database!", searchButton, FloatingMessage.ERROR_MESSAGE);
            }

            for(int j = 1; j < ticketNumbers.size(); j++){
                try{
                    bookingController.getSearchBookingResult().getLast().getTickets().add(new Ticket(ticketNumbers.get(j), seats.get(j), checkedIns.get(j),
                            flightController.getSearchBookingResult().getLast(), bookingController.getSearchBookingResult().getLast(),
                            firstNames.get(j), lastNames.get(j), passengerSSNs.get(j), birthDates.get(j)));

                }catch (Exception e){
                    new FloatingMessage("Errore nella connessione al Database!", searchButton, FloatingMessage.ERROR_MESSAGE);
                }
            }

            ticketController.getSearchBookingResult().addAll(bookingController.getSearchBookingResult().getLast().getTickets());
            for(Ticket x: bookingController.getSearchBookingResult().getLast().getTickets()){
                if(!actualSSNs.contains(x.getPassenger().getPassengerSSN())){
                    actualSSNs.add(x.getPassenger().getPassengerSSN());
                    passengerController.getSearchBookingResult().add(x.getPassenger());
                }

            }

        }


    }


     /**
     * Searches for customer bookings using passenger-based filtering criteria.
     * <p>
     * This method performs a filtered search for bookings belonging to the currently
     * logged-in customer based on passenger information such as names, SSN, and
     * ticket numbers. This search method is particularly useful when customers
     * need to find bookings but don't remember flight details.
     * </p>
     * <p>
     * The search supports the following passenger-based filtering criteria:
     * </p>
     * <ul>
     *   <li>First name - partial matching with case-insensitive search</li>
     *   <li>Last name - partial matching with case-insensitive search</li>
     *   <li>Passenger SSN - partial matching for identification</li>
     *   <li>Ticket number - exact or partial matching for ticket lookup</li>
     * </ul>
     * <p>
     * The search uses flexible matching techniques (ILIKE in PostgreSQL) to provide
     * user-friendly search capabilities that don't require exact matches. This allows
     * customers to find their bookings even with partial or approximate information.
     * </p>
     * <p>
     * Results include complete booking information with associated flight details,
     * full passenger information, and ticket data. The method processes complex
     * relationships between passengers, tickets, and bookings to provide comprehensive
     * search results.
     * </p>
     * <p>
     * Flight objects are created as either {@link Arriving} or {@link Departing}
     * instances based on flight types, ensuring proper display and behavior in
     * the user interface. Duplicate results are avoided through careful tracking
     * of processed records.
     * </p>
     *
     * @param firstName the passenger first name for filtering (null for no filter)
     * @param lastName the passenger last name for filtering (null for no filter)
     * @param passengerSSN the passenger SSN for filtering (null for no filter)
     * @param ticketNumber the ticket number for filtering (null for no filter)
     * @param bookingDates list to be populated with booking creation dates
     * @param bookingStatus list to be populated with booking status values
     * @param flightIds list to be populated with flight identifiers
     * @param searchButton the button to use as reference for error message display
     */
    public void searchBooksLoogedCustomerFilteredPassengers(String firstName, String lastName, String passengerSSN, String ticketNumber,
                                                            List<Date> bookingDates, List<String> bookingStatus, List<String> flightIds, JButton searchButton) {

        ArrayList<String> companyNames = new ArrayList<>();
        ArrayList<Date> dates = new ArrayList<>();
        ArrayList<Time> departureTimes = new ArrayList<>();
        ArrayList<Time> arrivalTimes = new ArrayList<>();
        ArrayList<String> status = new ArrayList<>();
        ArrayList<Integer> maxSeats = new ArrayList<>();
        ArrayList<Integer> freeSeats = new ArrayList<>();
        ArrayList<String> cities = new ArrayList<>();

        ArrayList<Boolean> types = new ArrayList<>();

        ArrayList<Integer> bookingIds = new ArrayList<>();

        try{
            BookingDAO bookingDAO = new BookingDAOImpl();

            bookingDAO.searchBooksCustomerFilteredPassengers(firstName, lastName, passengerSSN, ticketNumber,
                    getCustomerController().getLoggedCustomerId(), flightIds, companyNames, dates, departureTimes, arrivalTimes, status, maxSeats, freeSeats,
                    cities, types, bookingDates, bookingStatus, bookingIds);


        } catch (SQLException e) {
            new FloatingMessage("Errore nella connessione al Database (Prenotazioni)!", searchButton, FloatingMessage.ERROR_MESSAGE);
        }

        ArrayList<String> actualFlightIds = new ArrayList<>();

        bookingController.setSearchBookingResult(new ArrayList<>());
        bookingController.setSearchBookingResultIds(new ArrayList<>());
        flightController.setSearchBookingResult(new ArrayList<>());

        ticketController.setSearchBookingResult(new ArrayList<>());
        passengerController.setSearchBookingResult(new ArrayList<>());

        ArrayList<String> actualSSNs = new ArrayList<>();

        for(int i = 0; i < flightIds.size(); i++){

            if(!actualFlightIds.contains(flightIds.get(i))){

                actualFlightIds.add(flightIds.get(i));

                if(Boolean.TRUE.equals(types.get(i))){   //alloco Departing

                    flightController.getSearchBookingResult().add(new Departing( flightIds.get(i), companyNames.get(i), dates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                            FlightStatus.valueOf(status.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i)));

                }else{              //alloco Arriving

                    flightController.getSearchBookingResult().add(new Arriving( flightIds.get(i), companyNames.get(i), dates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                            FlightStatus.valueOf(status.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i)));


                }
            }

            ArrayList<String> ticketNumbers = new ArrayList<>();
            ArrayList<Integer> seats = new ArrayList<>();
            ArrayList<Boolean> checkedIns = new ArrayList<>();
            ArrayList<String> passengerSSNs = new ArrayList<>();
            ArrayList<String> firstNames = new ArrayList<>();
            ArrayList<String> lastNames = new ArrayList<>();
            ArrayList<Date> birthDates = new ArrayList<>();

            TicketDAO ticketDao = new TicketDAOImpl();

            try{

                ticketDao.getAllTicketBooking(bookingIds.get(i), ticketNumbers, seats, checkedIns, passengerSSNs, firstNames, lastNames, birthDates);

            } catch (SQLException e) {

                new FloatingMessage("Errore nella connessione al Database (Biglietti)!", searchButton, FloatingMessage.ERROR_MESSAGE);
            }

            try{
                if(!ticketNumbers.isEmpty()) {

                    bookingController.getSearchBookingResult().add(new Booking(BookingStatus.valueOf(bookingStatus.get(i)), bookingDates.get(i),
                            customerController.getLoggedCustomer(), flightController.getSearchBookingResult().getLast(),
                            ticketNumbers.getFirst(), seats.getFirst(), checkedIns.getFirst(),
                            firstNames.getFirst(), lastNames.getFirst(), passengerSSNs.getFirst(), birthDates.getFirst()));
                    bookingController.getSearchBookingResultIds().add(bookingIds.get(i));
                }else{
                    throw new InvalidTicket("");
                }

            }catch (Exception e){
                new FloatingMessage("Errore nella connessione al Database (Biglietti)!", searchButton, FloatingMessage.ERROR_MESSAGE);
            }

            for(int j = 1; j < ticketNumbers.size(); j++){
                try{
                    bookingController.getSearchBookingResult().getLast().getTickets().add(new Ticket(ticketNumbers.get(j), seats.get(j), checkedIns.get(j),
                            flightController.getSearchBookingResult().getLast(), bookingController.getSearchBookingResult().getLast(),
                            firstNames.get(j), lastNames.get(j), passengerSSNs.get(j), birthDates.get(j)));

                }catch (Exception e){
                    new FloatingMessage("Errore nella connessione al Database!", searchButton, FloatingMessage.ERROR_MESSAGE);
                }
            }

            ticketController.getSearchBookingResult().addAll(bookingController.getSearchBookingResult().getLast().getTickets());
            for(Ticket x: bookingController.getSearchBookingResult().getLast().getTickets()){
                if(!actualSSNs.contains(x.getPassenger().getPassengerSSN())){
                    actualSSNs.add(x.getPassenger().getPassengerSSN());
                    passengerController.getSearchBookingResult().add(x.getPassenger());
                }

            }

        }

    }


    /**
     * Loads and checks for existing bookings for the current flight and logged-in customer.
     * <p>
     * This method determines whether the customer should be directed to view existing
     * bookings or create a new booking for the selected flight. It searches for any
     * existing bookings the customer has made for the current flight and loads the
     * complete booking information if found.
     * </p>
     * <p>
     * The method performs a comprehensive data loading process including:
     * </p>
     * <ul>
     *   <li>Searching for existing bookings for the current flight and customer</li>
     *   <li>Loading complete ticket information for each found booking</li>
     *   <li>Creating booking objects with associated passenger data</li>
     *   <li>Setting up controller caches with loaded data</li>
     *   <li>Organizing data for immediate GUI consumption</li>
     * </ul>
     * <p>
     * The return value indicates whether existing bookings were found, allowing
     * the calling code to decide whether to show the "My Bookings" interface
     * or proceed with new booking creation.
     * </p>
     * <p>
     * Flight information is set up in the search results to ensure consistency
     * between the selected flight and the booking data, maintaining proper
     * relationships throughout the application flow.
     * </p>
     * <p>
     * Passenger data is processed to avoid duplicates while ensuring that all
     * passengers associated with the customer's bookings are properly loaded
     * and available for display or modification operations.
     * </p>
     *
     * @return true if existing bookings were found for the current flight and customer, false otherwise
     */
    public boolean loadAndCheckIfOpenMyBookingsOrNewBooking() {

        String flightId = flightController.getId();

        ArrayList<Integer> bookingIds = new ArrayList<>();
        ArrayList<Date> bookingDates = new ArrayList<>();
        ArrayList<String> bookingStatus = new ArrayList<>();

        try{
            BookingDAO bookingDAO = new BookingDAOImpl();

            bookingDAO.searchBooksCustomerForAFlight(flightId, getCustomerController().getLoggedCustomerId(),
                                                        bookingDates, bookingStatus, bookingIds);


        } catch (SQLException e) {
            new FloatingMessage("Errore nella connessione al Database (Prenotazioni)!", errorButton, FloatingMessage.ERROR_MESSAGE);
        }

        flightController.setSearchBookingResult(new ArrayList<>());
        flightController.getSearchBookingResult().add(flightController.getFlight());

        bookingController.setSearchBookingResult(new ArrayList<>());
        bookingController.setSearchBookingResultIds(new ArrayList<>());

        ticketController.setSearchBookingResult(new ArrayList<>());
        passengerController.setSearchBookingResult(new ArrayList<>());

        ArrayList<String> actualSSNs = new ArrayList<>();

        for(int i = 0; i < bookingIds.size(); i++){

            ArrayList<String> ticketNumbers = new ArrayList<>();
            ArrayList<Integer> seats = new ArrayList<>();
            ArrayList<Boolean> checkedIns = new ArrayList<>();
            ArrayList<String> passengerSSNs = new ArrayList<>();
            ArrayList<String> firstNames = new ArrayList<>();
            ArrayList<String> lastNames = new ArrayList<>();
            ArrayList<Date> birthDates = new ArrayList<>();

            TicketDAO ticketDao = new TicketDAOImpl();

            try{

                ticketDao.getAllTicketBooking(bookingIds.get(i), ticketNumbers, seats, checkedIns, passengerSSNs, firstNames, lastNames, birthDates);

            } catch (SQLException e) {

                new FloatingMessage("Errore nella connessione al Database (Biglietti)!", errorButton, FloatingMessage.ERROR_MESSAGE);
            }

            try{
                if(!ticketNumbers.isEmpty()) {

                    bookingController.getSearchBookingResult().add(new Booking(BookingStatus.valueOf(bookingStatus.get(i)), bookingDates.get(i),
                            customerController.getLoggedCustomer(), flightController.getFlight(),
                            ticketNumbers.getFirst(), seats.getFirst(), checkedIns.getFirst(),
                            firstNames.getFirst(), lastNames.getFirst(), passengerSSNs.getFirst(), birthDates.getFirst()));
                    bookingController.getSearchBookingResultIds().add(bookingIds.get(i));
                }else{
                    throw new InvalidTicket("");
                }

            }catch (Exception e){
                new FloatingMessage("Errore nella connessione al Database (Biglietti)!", errorButton, FloatingMessage.ERROR_MESSAGE);
            }

            for(int j = 1; j < ticketNumbers.size(); j++){
                try{
                    bookingController.getSearchBookingResult().getLast().getTickets().add(new Ticket(ticketNumbers.get(j), seats.get(j), checkedIns.get(j),
                            flightController.getFlight(), bookingController.getSearchBookingResult().getLast(),
                            firstNames.get(j), lastNames.get(j), passengerSSNs.get(j), birthDates.get(j)));

                }catch (Exception e){
                    new FloatingMessage("Errore nella connessione al Database!", errorButton, FloatingMessage.ERROR_MESSAGE);
                }
            }

            ticketController.getSearchBookingResult().addAll(bookingController.getSearchBookingResult().getLast().getTickets());
            for(Ticket x: bookingController.getSearchBookingResult().getLast().getTickets()){
                if(!actualSSNs.contains(x.getPassenger().getPassengerSSN())){
                    actualSSNs.add(x.getPassenger().getPassengerSSN());
                    passengerController.getSearchBookingResult().add(x.getPassenger());
                }

            }

        }

        return !bookingIds.isEmpty();

    }


    /**
     * Clears the search booking result cache to free memory and reset state.
     * <p>
     * This utility method clears all cached booking search results from the
     * various controllers, ensuring that stale data doesn't interfere with
     * new search operations. It resets the state of booking, flight, and
     * related result collections.
     * </p>
     * <p>
     * Cache clearing is important for memory management and ensuring that
     * users always see current data when performing new searches or
     * navigating between different application sections.
     * </p>
     */
    public void clearSearchBookingResultCache() {

        bookingController.setSearchBookingResult(null);
        bookingController.setSearchBookingResultIds(null);
        flightController.setSearchBookingResult(null);

    }


    /**
     * Clears the search flights result cache to free memory and reset state.
     * <p>
     * This utility method clears cached flight search results, ensuring that
     * memory is freed and that stale flight data doesn't interfere with new
     * search operations. This is particularly important when switching between
     * different flight search contexts or user sessions.
     * </p>
     */
    public void clearSearchFlightsResultCache() {

        flightController.setSearchResult(null);

    }


    /**
     * Verifies user credentials and establishes user session for both customers and administrators.
     * <p>
     * This method handles the complete authentication process for the airport management system,
     * supporting both username/password and email/password authentication methods for both
     * customer and administrator user types.
     * </p>
     * <p>
     * The authentication process follows these steps:
     * </p>
     * <ol>
     *   <li>Validates input format (email vs username) using client-side validation</li>
     *   <li>Attempts administrator authentication first</li>
     *   <li>Falls back to customer authentication if admin authentication fails</li>
     *   <li>Establishes user session with appropriate controller instances</li>
     *   <li>Provides user feedback for authentication results</li>
     * </ol>
     * <p>
     * The method implements a hierarchical authentication approach where administrator
     * accounts are checked first, followed by customer accounts. This ensures that
     * administrative users have priority access and that the system can distinguish
     * between different user types automatically.
     * </p>
     * <p>
     * Input validation includes email format checking and username format validation
     * to prevent unnecessary database queries for obviously invalid credentials.
     * This improves performance and provides immediate feedback for format errors.
     * </p>
     * <p>
     * Upon successful authentication, user sessions are established in multiple
     * controllers (AdminController/CustomerController and UserController) to ensure
     * that user context is available throughout the application.
     * </p>
     * <p>
     * Error handling provides specific feedback through {@link FloatingMessage}
     * components, distinguishing between validation errors, authentication failures,
     * and database connectivity issues.
     * </p>
     *
     * @param loggingInfo the username or email address provided by the user
     * @param hashedPassword the pre-hashed password provided by the user
     * @param loginButton the button to use as reference for error message display
     * @return true if authentication was successful, false otherwise
     */
    public boolean verifyUser(String loggingInfo, String hashedPassword, JButton loginButton){

        //Avoid opening DB if it is obvious that it won't contain the user
        if(loggingInfo.contains("@")){
            if (userController.isInvalidMail(loggingInfo)){
                new FloatingMessage("<html>User o mail non valida</html>", loginButton, FloatingMessage.WARNING_MESSAGE);
                return false;
            }
        } else if(userController.isInvalidUsername(loggingInfo)){
            new FloatingMessage("<html>User o mail non valida</html>", loginButton, FloatingMessage.WARNING_MESSAGE);
            return false;
        }

        ArrayList<Integer> userID = new ArrayList<>();
        ArrayList<String> mail = new ArrayList<>();
        ArrayList<String> username = new ArrayList<>();

        try{
            AdminDAOImpl adminDAO = new AdminDAOImpl();
            if(loggingInfo.contains("@")){
                adminDAO.searchUserByMail(userID, username, loggingInfo, hashedPassword);
                adminController.setLoggedAdmin(new Admin(username.getFirst(), loggingInfo, hashedPassword), userID.getFirst());
                userController.setLoggedUser(new Admin(username.getFirst(), loggingInfo, hashedPassword), userID.getFirst());

            }else{
                adminDAO.searchUserByUsername(userID, loggingInfo, mail, hashedPassword);
                adminController.setLoggedAdmin(new Admin(loggingInfo, mail.getFirst(), hashedPassword), userID.getFirst());
                userController.setLoggedUser(new Admin(loggingInfo, mail.getFirst(), hashedPassword), userID.getFirst());
            }
        } catch (UserNotFoundException e){
            try{
                CustomerDAOImpl customerDAO = new CustomerDAOImpl();
                if(loggingInfo.contains("@")){
                    customerDAO.searchUserByMail(userID, username, loggingInfo, hashedPassword);
                    customerController.setLoggedCustomer(new Customer(username.getFirst(), loggingInfo, hashedPassword), userID.getFirst());
                    userController.setLoggedUser(new Customer (username.getFirst(), loggingInfo, hashedPassword), userID.getFirst());

                }else{
                    customerDAO.searchUserByUsername(userID, loggingInfo, mail, hashedPassword);
                    customerController.setLoggedCustomer(new Customer(loggingInfo, mail.getFirst(), hashedPassword), userID.getFirst());
                    userController.setLoggedUser(new Customer (loggingInfo, mail.getFirst(), hashedPassword), userID.getFirst());
                }
            } catch (UserNotFoundException ex){
                new FloatingMessage("<html>User o password errati</html>", loginButton, FloatingMessage.WARNING_MESSAGE);
                return false;
            } catch (SQLException ex){
                new FloatingMessage("<html>Errore nel collegamento al DB(Customer)" + ex.getMessage() + "</html>", loginButton, FloatingMessage.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e){
            new FloatingMessage("<html>Errore nel collegamento al DB(Admin)" + e.getMessage() + "</html>", loginButton, FloatingMessage.ERROR_MESSAGE);
            return false;
        }
        return true;
    }


    /**
     * Retrieves all currently booked seats for the selected flight, excluding the current booking.
     * <p>
     * This method queries the database to find all seat assignments for the currently
     * selected flight, excluding seats from the current booking (if any). This is
     * essential for seat selection interfaces where customers need to see which
     * seats are available for booking or modification.
     * </p>
     * <p>
     * The method uses the {@link FlightDAOImpl#getBookedSeats} method to retrieve
     * seat information while properly excluding the current booking's seats during
     * modification operations. This prevents customers from seeing their own seats
     * as unavailable when modifying their booking.
     * </p>
     * <p>
     * Seat information is converted from database format (1-based) to application
     * format (0-based) for consistency with the GUI seat selection components.
     * </p>
     *
     * @param bookedSeats list to be populated with currently booked seat numbers (0-based indexing)
     */
    public void setBookedSeats (List<Integer> bookedSeats) {
        FlightDAOImpl flightDAO = new FlightDAOImpl();

        flightDAO.getBookedSeats(flightController.getId(), bookingController.getId(), bookedSeats);
    }


    /**
     * Checks if the currently logged-in user is an administrator.
     * <p>
     * This utility method determines the user type by checking the class type
     * of the currently logged user object. It's used throughout the application
     * to control access to administrative features and to customize the user
     * interface based on user privileges.
     * </p>
     * <p>
     * The method uses instanceof checking to determine if the logged user is
     * an instance of the {@link Admin} class, providing a simple boolean
     * result for conditional logic in GUI components and business operations.
     * </p>
     *
     * @return true if the current user is an administrator, false if customer or not logged in
     */
    public boolean isLoggedAdmin() {

        return userController.getLoggedUser() instanceof Admin;

    }


    /**
     * Retrieves all lost luggage information from the database and organizes it for display.
     * <p>
     * This method performs a comprehensive search for all luggage items marked with
     * 'LOST' status in the system. It retrieves complete information including flight
     * details, customer information, booking data, passenger details, and luggage
     * tracking information to support lost luggage recovery operations.
     * </p>
     * <p>
     * The method processes complex multi-table relationships to provide complete
     * context for each lost luggage item, including:
     * </p>
     * <ul>
     *   <li>Flight information (schedules, destinations, status)</li>
     *   <li>Customer contact information for recovery coordination</li>
     *   <li>Booking details and passenger information</li>
     *   <li>Luggage type, status, and tracking identifiers</li>
     *   <li>Ticket and seat assignment information</li>
     * </ul>
     * <p>
     * The method organizes data in controller result caches, avoiding duplicates
     * and maintaining proper relationships between related entities. Flight objects
     * are created as appropriate {@link Arriving} or {@link Departing} instances
     * based on flight types.
     * </p>
     * <p>
     * This method is essential for administrative luggage management operations,
     * providing complete information needed for customer contact and luggage
     * recovery coordination.
     * </p>
     *
     * @param flightIds list to be populated with flight identifiers
     * @param bookingDates list to be populated with booking creation dates
     * @param firstNames list to be populated with passenger first names
     * @param lastNames list to be populated with passenger last names
     * @param passengerSSNs list to be populated with passenger SSN identifiers
     * @param luggageIdsAfterCheckin list to be populated with post-checkin luggage tracking identifiers
     */
    public void getLostLuggages(List<String> flightIds, List<Date> bookingDates, List<String> firstNames, List<String> lastNames, List<String> passengerSSNs, List<String> luggageIdsAfterCheckin) {

        ArrayList<String> companyNames = new ArrayList<>();
        ArrayList<Date> flightDates = new ArrayList<>();
        ArrayList<Time> departureTimes = new ArrayList<>();
        ArrayList<Time> arrivalTimes = new ArrayList<>();
        ArrayList<String> flightStatus = new ArrayList<>();
        ArrayList<Integer> maxSeats = new ArrayList<>();
        ArrayList<Integer> freeSeats = new ArrayList<>();
        ArrayList<String> cities = new ArrayList<>();

        ArrayList<Boolean> flightTypes = new ArrayList<>();

        ArrayList<Integer> buyerIds = new ArrayList<>();
        ArrayList<String> usernames = new ArrayList<>();
        ArrayList<String> mails = new ArrayList<>();
        ArrayList<String> hashedPasswords = new ArrayList<>();

        ArrayList<Integer> bookingIds = new ArrayList<>();
        ArrayList<String> bookingStatus = new ArrayList<>();

        ArrayList<String> ticketNumbers = new ArrayList<>();
        ArrayList<Integer> seats = new ArrayList<>();
        ArrayList<Boolean> checkedIns = new ArrayList<>();

        ArrayList<Date> birthDates = new ArrayList<>();

        ArrayList<Integer> luggageIds = new ArrayList<>();
        ArrayList<String> luggageTypes = new ArrayList<>();
        ArrayList<String> luggageStatus = new ArrayList<>();

        try{
            LuggageDAO luggageDAO = new LuggageDAOImpl();

            luggageDAO.getAllLostLuggages(flightIds, companyNames, flightDates, departureTimes, arrivalTimes,
                                          flightStatus, maxSeats, freeSeats, cities, flightTypes,
                                          buyerIds, usernames, mails, hashedPasswords,
                                          bookingDates, bookingStatus, bookingIds,
                                          ticketNumbers, seats, checkedIns,
                                          firstNames, lastNames, passengerSSNs, birthDates,
                                          luggageIds, luggageTypes, luggageStatus, luggageIdsAfterCheckin);



        } catch (SQLException e) {
            new FloatingMessage("Errore nella connessione al Database (Bagagli)!", errorButton, FloatingMessage.ERROR_MESSAGE);
        }


        flightController.setSearchBookingResult(new ArrayList<>());

        customerController.setSearchBookingResultCustomers(new ArrayList<>());
        customerController.setSearchBookingResultCustomersIds(new ArrayList<>());

        bookingController.setSearchBookingResult(new ArrayList<>());
        bookingController.setSearchBookingResultIds(new ArrayList<>());


        ticketController.setSearchBookingResult(new ArrayList<>());
        passengerController.setSearchBookingResult(new ArrayList<>());

        luggageController.setSearchBookingResult(new ArrayList<>());
        luggageController.setSearchBookingResultIds(new ArrayList<>());


        ArrayList<String> actualFlightIds = new ArrayList<>();
        ArrayList<Integer> actualBuyersIds = new ArrayList<>();
        ArrayList<Integer> actualBookingIds = new ArrayList<>();
        ArrayList<String> actualTicketNumbers = new ArrayList<>();
        ArrayList<String> actualSSNs = new ArrayList<>();

        try{

            for(int i = 0; i < luggageIds.size(); i++){

                if(!actualFlightIds.contains(flightIds.get(i))){

                    actualFlightIds.add(flightIds.get(i));

                    if(Boolean.TRUE.equals(flightTypes.get(i))){   //alloco Departing

                        flightController.getSearchBookingResult().add(new Departing( flightIds.get(i), companyNames.get(i), flightDates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                                FlightStatus.valueOf(flightStatus.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i)));

                    }else{              //alloco Arriving

                        flightController.getSearchBookingResult().add(new Arriving( flightIds.get(i), companyNames.get(i), flightDates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                                FlightStatus.valueOf(flightStatus.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i)));


                    }
                }

                if(!actualBuyersIds.contains(buyerIds.get(i))){

                    actualBuyersIds.add(buyerIds.get(i));

                    customerController.getSearchBookingResultCustomers().add(new Customer(usernames.get(i), mails.get(i), hashedPasswords.get(i)));
                    customerController.getSearchBookingResultCustomersIds().add(buyerIds.get(i));
                }


                if(!actualBookingIds.contains(bookingIds.get(i))){

                    actualBookingIds.add(bookingIds.get(i));

                    bookingController.getSearchBookingResult().add(new Booking(BookingStatus.valueOf(bookingStatus.get(i)), bookingDates.get(i),
                            customerController.getSearchBookingResultCustomerById(buyerIds.get(i)), flightController.getSearchBookingResultFlightById(flightIds.get(i)),
                            ticketNumbers.get(i), seats.get(i), checkedIns.get(i),
                            firstNames.get(i), lastNames.get(i), passengerSSNs.get(i), birthDates.get(i)));

                    bookingController.getSearchBookingResultIds().add(bookingIds.get(i));

                    actualTicketNumbers.add(ticketNumbers.get(i));
                    ticketController.getSearchBookingResult().add(bookingController.getSearchBookingResult().getLast().getTickets().getLast());
                    Passenger tmp = ticketController.getSearchBookingResult().getLast().getPassenger();
                    if(!actualSSNs.contains(tmp.getPassengerSSN())){
                        actualSSNs.add(tmp.getPassengerSSN());
                        passengerController.getSearchBookingResult().add(tmp);
                    }
                }else{
                    if(!actualTicketNumbers.contains(ticketNumbers.get(i))) {
                        actualTicketNumbers.add(ticketNumbers.get(i));
                        ticketController.getSearchBookingResult().add(new Ticket(ticketNumbers.get(i), seats.get(i), checkedIns.get(i), flightController.getSearchBookingResultFlightById(flightIds.get(i)),
                                bookingController.getSearchBookingResultBooksById(bookingIds.get(i)),
                                firstNames.get(i), lastNames.get(i), passengerSSNs.get(i), birthDates.get(i)));
                        Passenger tmp = ticketController.getSearchBookingResult().getLast().getPassenger();
                        if (!actualSSNs.contains(tmp.getPassengerSSN())) {
                            actualSSNs.add(tmp.getPassengerSSN());
                            passengerController.getSearchBookingResult().add(tmp);
                        }
                    }
                }

                if(luggageTypes.get(i) != null){
                    luggageController.getSearchBookingResult().add(new Luggage(luggageIdsAfterCheckin.get(i), LuggageType.valueOf(luggageTypes.get(i)), LuggageStatus.valueOf(luggageStatus.get(i)),
                            ticketController.getSearchBookingResultTicketByTicketNumber(ticketNumbers.get(i))));
                }else{
                    luggageController.getSearchBookingResult().add(new Luggage(luggageIdsAfterCheckin.get(i), LuggageStatus.valueOf(luggageStatus.get(i)),
                            ticketController.getSearchBookingResultTicketByTicketNumber(ticketNumbers.get(i))));
                }

                ticketController.getSearchBookingResultTicketByTicketNumber(ticketNumbers.get(i)).getLuggages().add(luggageController.getSearchBookingResult().getLast());


            }

            luggageController.getSearchBookingResultIds().addAll(luggageIds);

        }catch (Exception e){
            Controller.getLogger().log(Level.SEVERE, e.getMessage());
            new FloatingMessage("Errore nella connessione al Database (Bagagli smmarriti)!", errorButton, FloatingMessage.ERROR_MESSAGE);

        }


    }


    /**
     * Sets the selected flight for lost luggage operations based on luggage index.
     * <p>
     * This method establishes the flight context for lost luggage operations by
     * setting the current flight based on the selected luggage item. It retrieves
     * the flight associated with a specific luggage item and sets up the flight
     * controller with complete flight information including associated bookings.
     * </p>
     * <p>
     * The method ensures that the flight context is properly established for
     * subsequent operations such as luggage status updates, customer notifications,
     * or administrative actions related to the lost luggage item.
     * </p>
     * <p>
     * Flight information is retrieved from the luggage item's associated ticket
     * and booking, maintaining proper data relationships throughout the operation.
     * </p>
     *
     * @param luggageIndex the index of the luggage item in the search results to select
     */
    public void setBookingResultSelectedFlightForLostLuaggages(Integer luggageIndex) {

        flightController.setFlight(luggageController.getSearchBookingResult().get(luggageIndex).getTicket().getFlight());

        flightController.getFlight().getBookings().add(luggageController.getSearchBookingResult().get(luggageIndex).getTicket().getBooking());

    }


    /**
     * Retrieves comprehensive flight data including all associated bookings, passengers, and luggage.
     * <p>
     * This method performs a complete data retrieval operation for a specific flight,
     * loading all associated information including customer details, bookings, tickets,
     * passenger information, and luggage data. This provides administrators with a
     * complete view of flight operations and passenger manifest information.
     * </p>
     * <p>
     * The comprehensive data retrieval includes:
     * </p>
     * <ul>
     *   <li>Flight details and gate assignments</li>
     *   <li>Complete customer information for all bookings</li>
     *   <li>Booking details, status, and timestamps</li>
     *   <li>Ticket information including seat assignments and check-in status</li>
     *   <li>Passenger personal information and travel documents</li>
     *   <li>Luggage information including tracking and status data</li>
     * </ul>
     * <p>
     * The method processes complex relationships between entities while avoiding
     * duplicates and maintaining proper data organization. Gate information is
     * set on the flight object if available, and all related data is organized
     * in controller result caches for efficient GUI access.
     * </p>
     * <p>
     * This method is essential for administrative flight management operations,
     * check-in procedures, passenger manifest generation, and operational reporting.
     * </p>
     *
     * @param index the index of the flight in the search results to retrieve data for
     */
    public void getAllForAFlight(Integer index) {

        String flightId = flightController.getSearchResult().get(index).getId();

        flightController.setFlight(flightController.getSearchResult().get(index));

        ArrayList<Integer> flightGates = new ArrayList<>();

        ArrayList<Integer> buyerIds = new ArrayList<>();
        ArrayList<String> usernames = new ArrayList<>();
        ArrayList<String> mails = new ArrayList<>();
        ArrayList<String> hashedPasswords = new ArrayList<>();

        ArrayList<Integer> bookingIds = new ArrayList<>();
        ArrayList<String> bookingStatus = new ArrayList<>();
        ArrayList<Date> bookingDates = new ArrayList<>();

        ArrayList<String> ticketNumbers = new ArrayList<>();
        ArrayList<Integer> seats = new ArrayList<>();
        ArrayList<Boolean> checkedIns = new ArrayList<>();

        ArrayList<String> firstNames = new ArrayList<>();
        ArrayList<String> lastNames = new ArrayList<>();
        ArrayList<String> passengerSSNs = new ArrayList<>();
        ArrayList<Date> birthDates = new ArrayList<>();

        ArrayList<Integer> luggageIds = new ArrayList<>();
        ArrayList<String> luggageIdsAfterCheckin = new ArrayList<>();
        ArrayList<String> luggageTypes = new ArrayList<>();
        ArrayList<String> luggageStatus = new ArrayList<>();

        try{
            FlightDAO flightDAO = new FlightDAOImpl();


            flightDAO.getAllDataForAFlight(flightId, flightGates, buyerIds, usernames, mails, hashedPasswords,
                                           bookingDates, bookingStatus, bookingIds,
                                           ticketNumbers, seats, checkedIns,
                                           firstNames, lastNames, passengerSSNs, birthDates,
                                           luggageIds, luggageTypes, luggageStatus, luggageIdsAfterCheckin);



        } catch (SQLException e) {
            new FloatingMessage("Errore nella connessione al Database (Voli)!", errorButton, FloatingMessage.ERROR_MESSAGE);
        }

        customerController.setSearchBookingResultCustomers(new ArrayList<>());
        customerController.setSearchBookingResultCustomersIds(new ArrayList<>());

        bookingController.setSearchBookingResult(new ArrayList<>());
        bookingController.setSearchBookingResultIds(new ArrayList<>());


        ticketController.setSearchBookingResult(new ArrayList<>());
        passengerController.setSearchBookingResult(new ArrayList<>());

        luggageController.setSearchBookingResult(new ArrayList<>());
        luggageController.setSearchBookingResultIds(new ArrayList<>());


        ArrayList<Integer> actualBuyersIds = new ArrayList<>();
        ArrayList<Integer> actualBookingIds = new ArrayList<>();
        ArrayList<String> actualTicketNumbers = new ArrayList<>();
        ArrayList<String> actualSSNs = new ArrayList<>();

        try{
            if(!flightGates.isEmpty()){
                if(flightGates.getFirst() != null){
                    flightController.getFlight().setGate(new Gate(flightGates.getFirst().byteValue()));
                }
            }


            for(int i = 0; i < luggageIds.size(); i++){


                if(!actualBuyersIds.contains(buyerIds.get(i))){

                    actualBuyersIds.add(buyerIds.get(i));

                    customerController.getSearchBookingResultCustomers().add(new Customer(usernames.get(i), mails.get(i), hashedPasswords.get(i)));
                    customerController.getSearchBookingResultCustomersIds().add(buyerIds.get(i));
                }


                if(!actualBookingIds.contains(bookingIds.get(i))){

                    actualBookingIds.add(bookingIds.get(i));

                    bookingController.getSearchBookingResult().add(new Booking(BookingStatus.valueOf(bookingStatus.get(i)), bookingDates.get(i),
                            customerController.getSearchBookingResultCustomerById(buyerIds.get(i)), flightController.getFlight(),
                            ticketNumbers.get(i), seats.get(i), checkedIns.get(i),
                            firstNames.get(i), lastNames.get(i), passengerSSNs.get(i), birthDates.get(i)));

                    bookingController.getSearchBookingResultIds().add(bookingIds.get(i));

                    actualTicketNumbers.add(ticketNumbers.get(i));
                    ticketController.getSearchBookingResult().add(bookingController.getSearchBookingResult().getLast().getTickets().getLast());
                    Passenger tmp = ticketController.getSearchBookingResult().getLast().getPassenger();
                    if(!actualSSNs.contains(tmp.getPassengerSSN())){
                        actualSSNs.add(tmp.getPassengerSSN());
                        passengerController.getSearchBookingResult().add(tmp);
                    }
                }else{
                    if(!actualTicketNumbers.contains(ticketNumbers.get(i))) {
                        actualTicketNumbers.add(ticketNumbers.get(i));
                        ticketController.getSearchBookingResult().add(new Ticket(ticketNumbers.get(i), seats.get(i), checkedIns.get(i), flightController.getFlight(),
                                bookingController.getSearchBookingResultBooksById(bookingIds.get(i)),
                                firstNames.get(i), lastNames.get(i), passengerSSNs.get(i), birthDates.get(i)));
                        Passenger tmp = ticketController.getSearchBookingResult().getLast().getPassenger();
                        if (!actualSSNs.contains(tmp.getPassengerSSN())) {
                            actualSSNs.add(tmp.getPassengerSSN());
                            passengerController.getSearchBookingResult().add(tmp);
                        }
                        bookingController.getSearchBookingResultBooksById(bookingIds.get(i)).getTickets().add(ticketController.getSearchBookingResult().getLast());
                    }
                }

                if(luggageIds.get(i) != null){
                    if(luggageTypes.get(i) != null){
                        luggageController.getSearchBookingResult().add(new Luggage(luggageIdsAfterCheckin.get(i), LuggageType.valueOf(luggageTypes.get(i)), LuggageStatus.valueOf(luggageStatus.get(i)),
                                ticketController.getSearchBookingResultTicketByTicketNumber(ticketNumbers.get(i))));
                    }else{
                        luggageController.getSearchBookingResult().add(new Luggage(luggageIdsAfterCheckin.get(i), LuggageStatus.valueOf(luggageStatus.get(i)),
                                ticketController.getSearchBookingResultTicketByTicketNumber(ticketNumbers.get(i))));
                    }

                    ticketController.getSearchBookingResultTicketByTicketNumber(ticketNumbers.get(i)).getLuggages().add(luggageController.getSearchBookingResult().getLast());
                }

            }

            flightController.getFlight().getBookings().addAll(bookingController.getSearchBookingResult());
            flightController.getFlight().getTickets().addAll(ticketController.getSearchBookingResult());

            luggageController.getSearchBookingResultIds().addAll(luggageIds);

        }catch (Exception e){
            Controller.getLogger().log(Level.SEVERE, e.getMessage());
            new FloatingMessage("Errore nella connessione al Database!", errorButton, FloatingMessage.ERROR_MESSAGE);

        }


    }


    /**
     * Retrieves all luggage information associated with a specific booking.
     * <p>
     * This method loads complete luggage information for a selected booking,
     * including luggage types, status, and tracking identifiers. It provides
     * detailed luggage information needed for customer service operations,
     * booking modifications, and luggage management tasks.
     * </p>
     * <p>
     * The method processes luggage data and associates it with the corresponding
     * tickets within the booking, maintaining proper relationships between
     * passengers, tickets, and their associated luggage items.
     * </p>
     * <p>
     * Luggage information includes:
     * </p>
     * <ul>
     *   <li>Luggage type (CARRY_ON or CHECKED)</li>
     *   <li>Current luggage status (BOOKED, LOADED, WITHDRAWABLE, LOST)</li>
     *   <li>Post-checkin tracking identifiers for physical luggage handling</li>
     *   <li>Association with specific tickets and passengers</li>
     * </ul>
     * <p>
     * The method sets up the flight and booking context for subsequent operations
     * and organizes luggage data in controller result caches for GUI consumption.
     * </p>
     *
     * @param index the index of the booking in the search results to retrieve luggage for
     */
    public void getAllLuggagesForABooking(Integer index) {


        flightController.setBookingResultSelectedFlight(bookingController.getSearchBookingResult().get(index).getBookedFlight().getId());

        bookingController.setBookingResultSelectedBooking(index);

        ArrayList<String> ticketNumbers = new ArrayList<>();
        ArrayList<Integer> luggageIds = new ArrayList<>();
        ArrayList<String> luggageIdsAfterCheckin = new ArrayList<>();
        ArrayList<String> luggageTypes = new ArrayList<>();
        ArrayList<String> luggageStatus = new ArrayList<>();

        try{
            LuggageDAO luggageDAO = new LuggageDAOImpl();


            luggageDAO.getAllLuggagesOfBooking(bookingController.getId(), ticketNumbers, luggageIds, luggageTypes, luggageStatus, luggageIdsAfterCheckin);


        } catch (SQLException e) {
            Controller.getLogger().log(Level.SEVERE, e.getSQLState());
            new FloatingMessage("Errore nella connessione al Database (Bagagli)!", errorButton, FloatingMessage.ERROR_MESSAGE);
        }

        try{

            luggageController.setSearchBookingResult(new ArrayList<>());
            luggageController.setSearchBookingResultIds(new ArrayList<>());

            for(int i = 0; i < ticketNumbers.size(); i++){

                if(luggageIds.get(i) != null){

                    if(luggageTypes.get(i) != null){
                        luggageController.getSearchBookingResult().add(new Luggage(luggageIdsAfterCheckin.get(i), LuggageType.valueOf(luggageTypes.get(i)), LuggageStatus.valueOf(luggageStatus.get(i)),
                                ticketController.getSearchBookingResultTicketByTicketNumber(ticketNumbers.get(i))));
                    }else{
                        luggageController.getSearchBookingResult().add(new Luggage(luggageIdsAfterCheckin.get(i), LuggageStatus.valueOf(luggageStatus.get(i)),
                                ticketController.getSearchBookingResultTicketByTicketNumber(ticketNumbers.get(i))));
                    }

                    ticketController.getSearchBookingResultTicketByTicketNumber(ticketNumbers.get(i)).getLuggages().add(luggageController.getSearchBookingResult().getLast());

                }
            }

            luggageController.getSearchBookingResultIds().addAll(luggageIds);

        }catch (Exception e){
            Controller.getLogger().log(Level.SEVERE, e.getMessage());
            new FloatingMessage("Errore nella connessione al Database (Bagagli)!", errorButton, FloatingMessage.ERROR_MESSAGE);

        }

    }


    /**
     * Returns the logger instance for the Controller class.
     * <p>
     * This static method provides access to the centralized logger for the Controller
     * class, enabling consistent logging throughout the application. The logger is
     * used for recording system operations, errors, and debugging information.
     * </p>
     * <p>
     * The logger instance is shared across all Controller operations and provides
     * standardized logging capabilities for system monitoring and troubleshooting.
     * </p>
     *
     * @return the {@link Logger} instance for the Controller class
     */
    public static Logger getLogger(){ return LOGGER;}


    /**
     * Updates user account information for both customers and administrators.
     * <p>
     * This method handles account information updates for the currently logged-in user,
     * supporting both customer and administrator account types. It performs input
     * validation, updates the appropriate database records, and maintains session
     * consistency across all relevant controllers.
     * </p>
     * <p>
     * The update process includes:
     * </p>
     * <ul>
     *   <li>Input validation for email format and username constraints</li>
     *   <li>Database update using appropriate DAO implementations</li>
     *   <li>Session update in relevant controller instances</li>
     *   <li>User feedback through success or error messages</li>
     * </ul>
     * <p>
     * Input validation ensures that:
     * </p>
     * <ul>
     *   <li>Email addresses follow valid format patterns</li>
     *   <li>Usernames meet system requirements (alphanumeric with limited special characters)</li>
     *   <li>Username format rules are enforced (start with letter, end with letter/number)</li>
     * </ul>
     * <p>
     * The method distinguishes between administrator and customer accounts using
     * instanceof checking and calls the appropriate DAO methods for each user type.
     * Session information is updated in multiple controllers to maintain consistency
     * throughout the application.
     * </p>
     * <p>
     * Error handling provides specific feedback for validation failures, database
     * errors, and successful updates through {@link FloatingMessage} components.
     * </p>
     *
     * @param mail the new email address for the user account (validated for format)
     * @param username the new username for the user account (validated for constraints)
     * @param hashedPassword the new hashed password for the user account
     * @param button the button to use as reference for message display positioning
     * @return true if the update was successful, false if validation failed or database error occurred
     */
    public boolean updateUser(String mail, String username, String hashedPassword, JButton button){
        if (userController.isInvalidMail(mail)) {
            new FloatingMessage("<html>Mail non valida</html>", button, FloatingMessage.WARNING_MESSAGE);
            return false;
        }
        if (userController.isInvalidUsername(username)) {
            new FloatingMessage("<html>Username non valido.<br>Il nome utente deve iniziare con una lettera, " +
                    "finire con una lettera o un numero e può contenere solo lettere, numeri, trattini (-), underscore(_) e punti(.)</html>",
                    button, FloatingMessage.WARNING_MESSAGE);
            return false;
        }

        try {
            if (userController.getLoggedUser() instanceof Admin) {
                AdminDAOImpl adminDAO = new AdminDAOImpl();
                adminDAO.updateAdmin(userController.getLoggedUserId(), username, hashedPassword);
                adminController.setLoggedAdmin(new Admin(username, userController.getLoggedUser().getEmail(), hashedPassword), userController.getLoggedUserId());
                userController.setLoggedUser(new Admin(username, userController.getLoggedUser().getEmail(), hashedPassword), userController.getLoggedUserId());
            } else {
                CustomerDAOImpl customerDAO = new CustomerDAOImpl();
                customerDAO.updateCustomer(userController.getLoggedUserId(), mail, username, hashedPassword);
                customerController.setLoggedCustomer(new Customer(username, userController.getLoggedUser().getEmail(), hashedPassword), userController.getLoggedUserId());
                userController.setLoggedUser(new Customer(username, userController.getLoggedUser().getEmail(), hashedPassword), userController.getLoggedUserId());
            }

            new FloatingMessage("<html>Informazioni aggiornate con successo</html>", button, FloatingMessage.SUCCESS_MESSAGE);
            return true;
        } catch (SQLException e) {
            new FloatingMessage("<html>Errore nel collegamento al DB(Customer) o DB(Admin)<br>" + e.getMessage() + "</html>", button, FloatingMessage.ERROR_MESSAGE);
            return false;
        }

    }
}
