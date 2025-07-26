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
 * Central controller class serving as the main coordinator for the airport management system.
 * <p>
 * This class implements the facade pattern to provide a unified interface for managing all
 * system operations and serves as the primary integration point between the GUI layer,
 * business logic, and data access layers. It coordinates operations across multiple
 * specialized controllers and maintains system-wide state and logging.
 * </p>
 * <p>
 * The Controller is responsible for:
 * </p>
 * <ul>
 *   <li>Coordinating operations between specialized controllers (Admin, Booking, Customer, etc.)</li>
 *   <li>Managing navigation and window lifecycle through {@link DisposableObject} chains</li>
 *   <li>Handling complex business operations that span multiple domains</li>
 *   <li>Providing centralized logging and error handling capabilities</li>
 *   <li>Managing user authentication and session state across the application</li>
 *   <li>Coordinating database operations for complex multi-table transactions</li>
 * </ul>
 * <p>
 * The class maintains instances of all specialized controllers, providing access to their
 * functionality while orchestrating interactions between them. This design ensures loose
 * coupling between GUI components and business logic while maintaining centralized control
 * over system operations.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see AdminController
 * @see BookingController
 * @see CustomerController
 * @see FlightController
 * @see GateController
 * @see LuggageController
 * @see PassengerController
 * @see UserController
 * @see TicketController
 * @see DisposableObject
 */
public class Controller {

    /**
     * Specialized controller for administrator operations and session management.
     * Handles administrator authentication, profile management, and admin-specific
     * operations throughout the system.
     */
    private final AdminController adminController;
    
    /**
     * Specialized controller for booking operations and reservation management.
     * Manages booking creation, modification, deletion, and search operations
     * with comprehensive passenger and luggage handling.
     */
    private final BookingController bookingController;
    
    /**
     * Specialized controller for customer operations and session management.
     * Handles customer authentication, profile management, and customer-specific
     * operations including booking history and account management.
     */
    private final CustomerController customerController;
    
    /**
     * Specialized controller for flight operations and management.
     * Manages flight information, scheduling, capacity, status updates,
     * and passenger manifest operations for both arriving and departing flights.
     */
    private final FlightController flightController;
    
    /**
     * Specialized controller for gate assignment and management operations.
     * Handles gate allocation, availability checking, and integration with
     * flight check-in procedures and operational workflows.
     */
    private final GateController gateController;
    
    /**
     * Specialized controller for luggage operations and status tracking.
     * Manages luggage search results, status updates, lost luggage reporting,
     * and administrative luggage management functions.
     */
    private final LuggageController luggageController;
    
    /**
     * Specialized controller for passenger data management and operations.
     * Handles passenger search results and provides access to passenger
     * information for administrative and customer service operations.
     */
    private final PassengerController passengerController;
    
    /**
     * Specialized controller for user operations, authentication, and validation.
     * Manages user session state, input validation, account registration,
     * and account deletion for both administrators and customers.
     */
    private final UserController userController;
    
    /**
     * Specialized controller for ticket operations and session management.
     * Manages ticket information, passenger associations, and provides
     * comprehensive access to ticket-related data and operations.
     */
    private final TicketController ticketController;
    
    /**
     * Reference button for displaying system-wide error messages.
     * Used as a fallback for operations that don't have a specific UI context
     * but need to display error information to users.
     */
    private JButton errorButton;
    
    /**
     * Centralized logger instance for system-wide logging operations.
     * Provides consistent logging across all system components with
     * appropriate severity levels for operational monitoring and debugging.
     */
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());

    /**
     * Constructs a new Controller instance and initializes all specialized controllers.
     *
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
     * Retrieves the booking controller instance for booking-related operations.
     *
     * @return the {@link BookingController} instance for booking operations
     */
    public BookingController getBookingController() {
        return bookingController;
    }

    /**
     * Retrieves the customer controller instance for customer-related operations.
     *
     * @return the {@link CustomerController} instance for customer operations
     */
    public CustomerController getCustomerController() {
        return customerController;
    }

    /**
     * Retrieves the flight controller instance for flight-related operations.
     *
     * @return the {@link FlightController} instance for flight operations
     */
    public FlightController getFlightController() {
        return flightController;
    }

    /**
     * Retrieves the gate controller instance for gate management operations.
     *
     * @return the {@link GateController} instance for gate operations
     */
    public GateController getGateController() {
        return gateController;
    }

    /**
     * Retrieves the luggage controller instance for luggage management operations.
     *
     * @return the {@link LuggageController} instance for luggage operations
     */
    public LuggageController getLuggageController() {
        return luggageController;
    }

    /**
     * Retrieves the user controller instance for user management operations.
     *
     * @return the {@link UserController} instance for user operations
     */
    public UserController getUserController() {
        return userController;
    }

    /**
     * Navigates to the login screen and clears the application stack.
     * <p>
     * This method implements navigation to the login screen by disposing of all
     * windows in the calling objects stack except the first one (login screen),
     * then restoring the login window with preserved size, position, and state
     * information from the current window.
     * </p>
     * <p>
     * The navigation process includes:
     * </p>
     * <ul>
     *   <li>Capturing current window state (size, position, maximization)</li>
     *   <li>Disposing all windows from the stack except the login window</li>
     *   <li>Calling disposal handlers for proper cleanup</li>
     *   <li>Restoring window state to the login window</li>
     *   <li>Calling restoration handlers for proper initialization</li>
     *   <li>Making the login window visible</li>
     * </ul>
     *
     * @param callingObjects the stack of {@link DisposableObject} windows representing the navigation hierarchy
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
     * Navigates to the home screen and clears intermediate windows.
     * <p>
     * This method implements navigation to the home screen by disposing of all
     * windows in the calling objects stack except the first two (login and home),
     * then restoring the home window with preserved size, position, and state
     * information from the current window.
     * </p>
     * <p>
     * The navigation process follows the same pattern as {@link #goToLogin(List)}
     * but preserves both the login window (index 0) and home window (index 1) in
     * the navigation stack, disposing only windows at index 2 and higher.
     * </p>
     *
     * @param callingObjects the stack of {@link DisposableObject} windows representing the navigation hierarchy
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
     * Navigates back to the previous screen in the navigation hierarchy.
     * <p>
     * This method implements standard back navigation by disposing the current
     * window and restoring the previous window in the calling objects stack.
     * It preserves window state information from the current window and applies
     * it to the previous window for visual continuity.
     * </p>
     * <p>
     * The back navigation process includes:
     * </p>
     * <ul>
     *   <li>Capturing current window state (size, position, maximization)</li>
     *   <li>Calling disposal handler for the current window</li>
     *   <li>Disposing the current window and removing it from the stack</li>
     *   <li>Applying captured state to the previous window</li>
     *   <li>Calling restoration handler for the previous window</li>
     *   <li>Making the previous window visible</li>
     * </ul>
     *
     * @param callingObjects the stack of {@link DisposableObject} windows representing the navigation hierarchy
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
     * Performs user logout by clearing all application windows except the login screen.
     * <p>
     * This method implements complete logout functionality by disposing all application
     * windows except the login screen, effectively clearing the user session and
     * returning to the authentication interface. Unlike other navigation methods,
     * this method does not preserve window state since logout represents a session
     * termination.
     * </p>
     * <p>
     * The logout process includes:
     * </p>
     * <ul>
     *   <li>Disposing all windows except the login window (index 0)</li>
     *   <li>Calling disposal handlers for proper cleanup</li>
     *   <li>Removing all windows from the navigation stack except login</li>
     *   <li>Calling restoration handler for the login window</li>
     *   <li>Making the login window visible</li>
     * </ul>
     *
     * @param callingObjects the stack of {@link DisposableObject} windows representing the navigation hierarchy
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
     * Creates a new booking with passenger information and specified status.
     * <p>
     * This method handles the complete booking creation process by extracting passenger
     * information from GUI panels, preparing the data for database insertion, and
     * coordinating with the {@link BookingDAOImpl} to persist the booking with all
     * associated tickets, passengers, and luggage information.
     * </p>
     * <p>
     * The booking creation process includes:
     * </p>
     * <ul>
     *   <li>Extracting passenger details from {@link PassengerPanel} components</li>
     *   <li>Generating unique ticket numbers for each passenger</li>
     *   <li>Processing seat assignments and passenger personal information</li>
     *   <li>Handling luggage associations with tickets</li>
     *   <li>Database persistence through the DAO layer</li>
     * </ul>
     *
     * @param passengerPanels list of {@link PassengerPanel} objects containing passenger information and preferences
     * @param bookingStatus the initial status for the booking (e.g., "CONFIRMED", "PENDING")
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
     * Translates flight status enumeration values to localized Italian strings.
     *
     * @param status the {@link FlightStatus} enumeration value to translate
     * @return the Italian translation of the flight status, or null if the status is not recognized
     */
    public static String translateFlightStatus(String status){

        switch (status){
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
     * Modifies an existing booking with updated passenger information and status.
     * <p>
     * This method handles the complete booking modification process by extracting
     * updated passenger information from GUI panels, preparing the data for database
     * updates, and coordinating with the {@link BookingDAOImpl} to persist changes
     * to the booking and all associated records.
     * </p>
     * <p>
     * The booking modification process includes:
     * </p>
     * <ul>
     *   <li>Extracting updated passenger details from {@link PassengerPanel} components</li>
     *   <li>Preserving existing ticket numbers where possible</li>
     *   <li>Generating new ticket numbers for additional passengers</li>
     *   <li>Processing updated seat assignments and personal information</li>
     *   <li>Handling luggage modifications and associations</li>
     *   <li>Database updates through the DAO layer</li>
     * </ul>
     *
     * @param passengerPanels list of {@link PassengerPanel} objects containing updated passenger information
     * @param bookingStatus the updated status for the booking (e.g., "CONFIRMED", "PENDING", "CANCELLED")
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
            e.printStackTrace();

            Controller.getLogger().log(Level.SEVERE, e.getSQLState());
        }
    }

    /**
     * Extracts and organizes passenger information from GUI panels for database operations.
     * <p>
     * This private utility method processes a collection of {@link PassengerPanel} objects
     * to extract all necessary passenger information and organize it into separate lists
     * for efficient database operations. It handles ticket number generation, passenger
     * details extraction, and luggage association processing.
     * </p>
     * <p>
     * The method performs several key operations:
     * </p>
     * <ul>
     *   <li>Ticket number assignment using existing numbers or generating new ones</li>
     *   <li>Seat assignment extraction from passenger panels</li>
     *   <li>Personal information extraction (names, birthdates, SSNs)</li>
     *   <li>Luggage type processing and ticket association</li>
     *   <li>Data validation and consistency checking</li>
     * </ul>
     *
     * @param passengerPanels list of {@link PassengerPanel} objects containing passenger information
     * @param ticketsNumbers list to be populated with ticket numbers (existing or generated)
     * @param seats list to be populated with seat assignments
     * @param firstNames list to be populated with passenger first names
     * @param lastNames list to be populated with passenger last names
     * @param birthDates list to be populated with passenger birth dates
     * @param passengerSSNs list to be populated with passenger Social Security Numbers
     * @param luggagesTypes list to be populated with selected luggage types
     * @param ticketsForLuggagesTypes list to be populated with ticket numbers associated with luggage
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
     * Generates a unique ticket number with an offset for numbering consistency.
     * <p>
     * This method delegates ticket number generation to the {@link TicketDAOImpl}
     * which implements the actual generation logic based on system requirements
     * and database constraints. The offset parameter enables consistent numbering
     * within booking contexts where multiple tickets need sequential identification.
     * </p>
     *
     * @param offset the numerical offset to apply during ticket number generation
     * @return a unique ticket number string incorporating the specified offset
     */
    public String generateTicketNumber (int offset) {

        TicketDAOImpl ticketDAO = new TicketDAOImpl();

        return ticketDAO.generateTicketNumber(offset);
    }

    /**
     * Sets the reference button for displaying system-wide error messages.
     * <p>
     * This method establishes a UI button reference that can be used as a fallback
     * for displaying error messages when operations don't have a specific UI context
     * but still need to provide user feedback.
     * </p>
     *
     * @param errorButton the {@link JButton} to use as a reference for error message display
     */
    public void setErrorButton(JButton errorButton) {
        this.errorButton = errorButton;
    }

    /**
     * Retrieves all bookings for the currently logged-in customer with comprehensive flight information.
     * <p>
     * This method performs a search for all bookings associated with the
     * currently logged-in customer, retrieving complete flight information, passenger
     * details, and organizing the data into synchronized collections for display and
     * processing purposes.
     * </p>
     * <p>
     * The method populates search results across multiple specialized controllers
     * ({@link BookingController}, {@link FlightController}, {@link TicketController},
     * {@link PassengerController}) to enable comprehensive data access from different
     * application components.
     * </p>
     *
     * @param bookingDates list to be populated with booking creation dates
     * @param bookingStatus list to be populated with current booking status values
     * @param flightIds list to be populated with flight identifiers associated with bookings
     * @param searchButton UI button reference for displaying error messages if operations fail
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
     * Searches customer bookings with flight-based filtering criteria and comprehensive data retrieval.
     * <p>
     * This method performs an advanced search for bookings belonging to the currently
     * logged-in customer using flight-specific filtering criteria such as origin,
     * destination, dates, and times. It retrieves complete booking information including
     * flight details, passenger data, and organizes the results for display and processing.
     * </p>
     * <p>
     * The filtering capabilities include:
     * </p>
     * <ul>
     *   <li><strong>Geographic filtering:</strong> Origin and destination city matching</li>
     *   <li><strong>Temporal filtering:</strong> Date range and time window constraints</li>
     *   <li><strong>Customer-specific results:</strong> Limited to current user's bookings</li>
     *   <li><strong>Comprehensive data retrieval:</strong> Complete flight and passenger information</li>
     * </ul>
     * <p>
     * The method follows the same comprehensive data processing pattern as
     * {@link #getAllBooksLoogedCustomer(List, List, List, JButton)} but applies
     * additional filtering constraints through the {@link BookingDAO#searchBooksCustomerFilteredFlights}
     * method to limit results to flights matching the specified criteria.
     * </p>
     *
     * @param origin the origin city name for filtering (null or empty for no filter)
     * @param destination the destination city name for filtering (null or empty for no filter)
     * @param dateBefore the earliest date for filtering (null for no lower bound)
     * @param dateAfter the latest date for filtering (null for no upper bound)
     * @param timeBefore the earliest time for filtering (null for no lower bound)
     * @param timeAfter the latest time for filtering (null for no upper bound)
     * @param bookingDates list to be populated with booking creation dates from filtered results
     * @param bookingStatus list to be populated with booking status values from filtered results
     * @param flightIds list to be populated with flight identifiers from filtered results
     * @param searchButton UI button reference for displaying error messages if operations fail
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
     * Searches customer bookings with passenger-based filtering criteria and comprehensive data retrieval.
     * <p>
     * This method performs an advanced search for bookings belonging to the currently
     * logged-in customer using passenger-specific filtering criteria such as first name,
     * last name, SSN, and ticket number. It retrieves complete booking information including
     * flight details, passenger data, and organizes the results for display and processing.
     * </p>
     * <p>
     * The filtering capabilities include:
     * </p>
     * <ul>
     *   <li><strong>Personal information filtering:</strong> First name and last name matching</li>
     *   <li><strong>Identity filtering:</strong> SSN (Social Security Number) matching</li>
     *   <li><strong>Ticket-specific filtering:</strong> Exact ticket number matching</li>
     *   <li><strong>Customer-specific results:</strong> Limited to current user's bookings</li>
     *   <li><strong>Comprehensive data retrieval:</strong> Complete flight and passenger information</li>
     * </ul>
     * <p>
     * The method follows the same comprehensive data processing pattern as other booking
     * search methods but applies passenger-specific filtering constraints through the
     * {@link BookingDAO#searchBooksCustomerFilteredPassengers} method to limit results
     * to bookings containing passengers matching the specified criteria.
     * </p>
     * <p>
     * The search results are populated across multiple controllers ({@link BookingController},
     * {@link FlightController}, {@link TicketController}, {@link PassengerController}) to
     * enable comprehensive data access from different application components, maintaining
     * the same data structure and relationships as other search operations.
     * </p>
     *
     * @param firstName the first name for filtering (null or empty for no filter)
     * @param lastName the last name for filtering (null or empty for no filter)
     * @param passengerSSN the passenger SSN for filtering (null or empty for no filter)
     * @param ticketNumber the ticket number for exact matching (null or empty for no filter)
     * @param bookingDates list to be populated with booking creation dates from filtered results
     * @param bookingStatus list to be populated with booking status values from filtered results
     * @param flightIds list to be populated with flight identifiers from filtered results
     * @param searchButton UI button reference for displaying error messages if operations fail
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
     * Loads and checks if existing customer bookings should open booking management or new booking creation.
     * <p>
     * This method performs a specialized search to determine whether the currently logged-in
     * customer has existing bookings for the currently selected flight. It retrieves all
     * customer bookings for the specific flight and populates the search result collections
     * to enable either booking modification workflows or new booking creation processes.
     * </p>
     * <p>
     * The method serves dual purposes in the booking workflow:
     * </p>
     * <ul>
     *   <li><strong>Existing Booking Detection:</strong> Identifies if customer has bookings for the current flight</li>
     *   <li><strong>Data Population:</strong> Loads complete booking information for modification workflows</li>
     *   <li><strong>Workflow Direction:</strong> Determines whether to show modification or creation interfaces</li>
     *   <li><strong>Session Preparation:</strong> Prepares controller states for subsequent operations</li>
     * </ul>
     * <p>
     * The method initializes search result collections across multiple specialized controllers
     * ({@link FlightController}, {@link BookingController}, {@link TicketController},
     * {@link PassengerController}) to ensure that subsequent booking operations have access
     * to complete and properly organized data structures.
     * </p>
     *
     * @return true if the customer has existing bookings for the current flight, false if no bookings exist
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
     * Clears all cached search result data from booking-related controllers.
     * <p>
     * Controllers affected by this cleanup include:
     * </p>
     * <ul>
     *   <li>{@link BookingController} - booking search results and corresponding IDs</li>
     *   <li>{@link FlightController} - flight search result collections</li>
     * </ul>
     *
     */
    public void clearSearchBookingResultCache() {

        bookingController.setSearchBookingResult(null);
        bookingController.setSearchBookingResultIds(null);
        flightController.setSearchBookingResult(null);

    }

    /**
     * Clears cached flight search result data from the flight controller.
     *
     */
    public void clearSearchFlightsResultCache() {

        flightController.setSearchResult(null);

    }

    /**
     * Verifies user credentials and establishes authenticated session for administrators or customers.
     * <p>
     * This method performs comprehensive user authentication by validating credentials
     * against both administrator and customer databases, establishing appropriate user
     * sessions upon successful authentication. It supports both email and username-based
     * login with proper input validation and user feedback.
     * </p>
     * <p>
     * The authentication process includes:
     * </p>
     * <ul>
     *   <li><strong>Input Validation:</strong> Email format and username format validation</li>
     *   <li><strong>Database Queries:</strong> Administrator database search followed by customer database search</li>
     *   <li><strong>Session Establishment:</strong> User object creation and controller state initialization</li>
     *   <li><strong>Error Handling:</strong> Comprehensive error feedback for various failure scenarios</li>
     * </ul>
     * <p>
     * Input validation prevents unnecessary database queries for obviously invalid
     * credentials, improving performance and reducing database load. Validation
     * is performed using the methods from {@link UserController}.
     * </p>
     * <p>
     * Error handling provides specific user feedback for different failure scenarios:
     * </p>
     * <ul>
     *   <li><strong>Validation Errors:</strong> Invalid email or username format</li>
     *   <li><strong>Authentication Errors:</strong> Incorrect credentials</li>
     *   <li><strong>Database Errors:</strong> Connection or query execution problems</li>
     * </ul>
     *
     * @param loggingInfo the login identifier (email address or username)
     * @param hashedPassword the pre-hashed password for authentication
     * @param loginButton UI button reference for displaying authentication feedback messages
     * @return true if authentication was successful and user session was established, false otherwise
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
     * Retrieves and populates booked seat information for the current flight and booking context.
     *
     * @param bookedSeats list to be populated with seat numbers that are currently booked for the flight
     */
    public void setBookedSeats (List<Integer> bookedSeats) {
        FlightDAOImpl flightDAO = new FlightDAOImpl();

        flightDAO.getBookedSeats(flightController.getId(), bookingController.getId(), bookedSeats);
    }

    /**
     * Determines whether the currently logged-in user is an administrator.
     *
     * @return true if the currently logged-in user is an administrator, false if the user is a customer or no user is logged in
     */
    public boolean isLoggedAdmin() {

        return userController.getLoggedUser() instanceof Admin;

    }
    /**
     * Retrieves comprehensive information about all lost luggage items in the system for recovery operations.
     * <p>
     * This method performs a complex database operation to fetch complete information about all luggage
     * items that have been reported as lost throughout the airport management system. It provides a
     * comprehensive view of lost luggage including associated flight details, passenger information,
     * booking data, and luggage specifications for recovery and customer service operations.
     * </p>
     * <p>
     * The operation involves multiple complex processes:
     * </p>
     * <ul>
     *   <li>Database query through {@link LuggageDAOImpl} to retrieve all lost luggage items</li>
     *   <li>Flight object creation based on flight type (Departing/Arriving) for lost luggage context</li>
     *   <li>Customer information retrieval for lost luggage owners</li>
     *   <li>Complete booking and ticket object construction with all associations</li>
     *   <li>Luggage object creation with proper status and type information</li>
     *   <li>Search result population across multiple controllers for comprehensive data access</li>
     * </ul>
     * <p>
     * The method populates search results across multiple specialized controllers
     * ({@link FlightController}, {@link CustomerController}, {@link BookingController},
     * {@link TicketController}, {@link PassengerController}, {@link LuggageController})
     * to enable comprehensive data access from different application components for
     * lost luggage recovery operations.
     * </p>
     *
     * @param flightIds list to be populated with flight identifiers associated with lost luggage
     * @param bookingDates list to be populated with booking creation dates for lost luggage context
     * @param firstNames list to be populated with passenger first names for customer contact
     * @param lastNames list to be populated with passenger last names for customer contact
     * @param passengerSSNs list to be populated with passenger SSNs for identity verification
     * @param luggageIdsAfterCheckin list to be populated with physical luggage identifiers for tracking
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
     * Sets the booking result selected flight context for lost luggage recovery operations.
     *
     * @param luggageIndex the zero-based index of the lost luggage item in the search results
     */
    public void setBookingResultSelectedFlightForLostLuaggages(Integer luggageIndex) {

        flightController.setFlight(luggageController.getSearchBookingResult().get(luggageIndex).getTicket().getFlight());

        flightController.getFlight().getBookings().add(luggageController.getSearchBookingResult().get(luggageIndex).getTicket().getBooking());

    }

    /**
     * Retrieves comprehensive flight information including all associated bookings, passengers, and luggage.
     * <p>
     * This method performs a comprehensive database operation to fetch complete information about
     * a specific flight including all associated bookings, passengers, tickets, and luggage items.
     * It provides a complete view of flight operations including passenger manifests, booking details,
     * luggage tracking, and customer information for administrative and operational purposes.
     * </p>
     * <p>
     * The operation involves multiple complex processes:
     * </p>
     * <ul>
     *   <li>Database query through {@link FlightDAOImpl} to retrieve all flight-related data</li>
     *   <li>Gate assignment retrieval and flight context establishment</li>
     *   <li>Customer information processing for all flight passengers</li>
     *   <li>Complete booking object construction with passenger associations</li>
     *   <li>Ticket object creation with proper booking and passenger relationships</li>
     *   <li>Luggage object creation with status and type information</li>
     *   <li>Search result population across multiple controllers for comprehensive data access</li>
     * </ul>
     * <p>
     * The method populates search results across multiple specialized controllers and
     * integrates all data into the flight object's collections, providing both controller-based
     * access for GUI components and flight-based access for business logic operations.
     * </p>
     *
     * @param index the zero-based index of the flight in the search results to retrieve comprehensive data for
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
     * Retrieves comprehensive luggage information for a specific booking.
     * <p>
     * This method performs a specialized database operation to fetch complete luggage
     * information for all luggage items associated with a specific booking. It provides
     * detailed luggage data including identification, type, status, and ticket associations
     * for customer service operations and booking management purposes.
     * </p>
     * <p>
     * The operation involves several key processes:
     * </p>
     * <ul>
     *   <li>Flight context establishment based on the selected booking's flight</li>
     *   <li>Booking selection for focused luggage operations</li>
     *   <li>Database query through {@link LuggageDAOImpl} to retrieve booking-specific luggage</li>
     *   <li>Luggage object creation with proper type and status information</li>
     *   <li>Ticket-luggage association establishment for tracking purposes</li>
     *   <li>Search result population for administrative access</li>
     * </ul>
     * <p>
     * The method populates search results in {@link LuggageController} to enable
     * administrative interfaces to access and manage booking-specific luggage information.
     * Database IDs are also maintained for efficient luggage selection and operation
     * targeting in administrative workflows.
     * </p>
     *
     * @param index the zero-based index of the booking in the search results to retrieve luggage information for
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
     * Retrieves the centralized logger instance for system-wide logging operations.
     *
     * @return the centralized {@link Logger} instance for system-wide logging operations
     */
    public static Logger getLogger(){ return LOGGER;}

    /**
     * Updates user profile information with comprehensive validation and type-appropriate database operations.
     * <p>
     * This method handles user profile updates for both administrators and customers by validating
     * input data, determining user type, and coordinating with appropriate DAO implementations
     * to persist the changes. It provides comprehensive validation feedback and maintains session
     * consistency across all user controllers.
     * </p>
     * <p>
     * The update process includes several key operations:
     * </p>
     * <ul>
     *   <li>Input validation for email format and username format constraints</li>
     *   <li>User type determination based on current session information</li>
     *   <li>Type-appropriate database update operations through specialized DAOs</li>
     *   <li>Session state synchronization across multiple controllers</li>
     *   <li>User feedback through floating message components</li>
     * </ul>
     *
     * @param mail the updated email address for the user (used only for customer accounts)
     * @param username the updated username for the user
     * @param hashedPassword the updated pre-hashed password for the user
     * @param button UI button reference for displaying feedback messages
     * @return true if the profile update was successful, false if validation failed or database errors occurred
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
