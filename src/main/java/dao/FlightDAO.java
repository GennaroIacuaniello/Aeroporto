package dao;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object interface for managing flight operations in the airport management system.
 * <p>
 * This interface defines the contract for all flight-related database operations, providing
 * methods for flight retrieval, searching, creation, and comprehensive flight data management.
 * It serves as the primary interface for flight data persistence and retrieval operations,
 * supporting both customer flight searches and administrative flight management functions.
 * </p>
 * <p>
 * The FlightDAO interface provides comprehensive flight management capabilities including:
 * </p>
 * <ul>
 *   <li>Retrieval of imminent arriving and departing flights for dashboard displays</li>
 *   <li>Advanced flight search functionality with multiple filtering criteria</li>
 *   <li>Comprehensive flight data retrieval including bookings, tickets, passengers, and luggage</li>
 *   <li>Flight creation and insertion operations for administrative purposes</li>
 * </ul>
 * <p>
 * The interface supports complex search operations that allow customers and administrators
 * to find flights based on departure/arrival cities, travel dates, times, and other criteria.
 * All retrieval methods populate multiple lists with related data to provide complete flight
 * information for display and processing purposes.
 * </p>
 * <p>
 * Flight data includes detailed information about schedules, seating capacity, delays,
 * gate assignments, and associated business entities such as bookings and passengers.
 * The interface handles both arriving flights (flight_type = false) and departing flights
 * (flight_type = true) with specialized methods for each type.
 * </p>
 * <p>
 * The interface follows the DAO pattern to provide a clean separation between business logic
 * and data persistence layer, enabling different implementations for various database systems
 * while maintaining consistent functionality across the application.
 * </p>
 * <p>
 * Implementation classes should handle all database-specific operations, connection management,
 * error handling, and ensure proper transaction handling for data consistency and integrity.
 * Complex operations involving multiple entities should be handled atomically to maintain
 * system consistency.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see model.Flight
 * @see implementazioni_postgres_dao.FlightDAOImpl
 * @see model.Arriving
 * @see model.Departing
 * @see model.Booking
 * @see model.Ticket
 * @see model.Passenger
 * @see controller.FlightController
 */
public interface FlightDAO {

    /**
     * Retrieves imminent arriving flights for dashboard display purposes.
     * <p>
     * This method fetches up to 6 arriving flights that are scheduled to arrive soon
     * and haven't landed yet. The results are ordered by arrival time and filtered
     * to exclude cancelled and already landed flights. This method is primarily used
     * for airport dashboard displays and real-time flight information systems.
     * </p>
     * <p>
     * The method populates multiple lists with flight data including flight identification,
     * airline information, scheduling details, seating capacity, gate assignments, and
     * delay information. All lists are populated with corresponding data at the same
     * index positions.
     * </p>
     * <p>
     * Only flights with flight_type = false (arriving flights) are retrieved, and the
     * query filters out flights with status 'LANDED' or 'CANCELLED'. The arrival time
     * calculation includes any flight delays to provide accurate real-time information.
     * </p>
     *
     * @param parId list to be populated with flight identifiers
     * @param parCompanyName list to be populated with airline company names
     * @param parDate list to be populated with flight dates derived from departure timestamps
     * @param parDepartureTime list to be populated with departure times from origin airports
     * @param parArrivalTime list to be populated with scheduled arrival times
     * @param parStatus list to be populated with current flight status values
     * @param parMaxSeats list to be populated with maximum seating capacity
     * @param parFreeSeats list to be populated with available seats count
     * @param origin list to be populated with origin airport or city names
     * @param delay list to be populated with arrival delay values in minutes
     * @param parGate list to be populated with assigned gate numbers (null if not assigned)
     * @throws SQLException if a database access error occurs during the retrieval operation
     */
    void getImminentArrivingFlights (List<String> parId, List<String> parCompanyName, List<Date> parDate,
                                     List<Time> parDepartureTime, List<Time> parArrivalTime, List<String> parStatus,
                                     List<Integer> parMaxSeats, List<Integer> parFreeSeats, List<String> origin,
                                     List<Integer> delay, List<Integer> parGate) throws SQLException;

    /**
     * Retrieves imminent departing flights for dashboard display purposes.
     * <p>
     * This method fetches up to 6 departing flights that are scheduled to depart soon
     * and are still at the airport. The results are ordered by arrival time and filtered
     * to include only flights with status 'PROGRAMMED', 'ABOUT_TO_DEPART', or 'DELAYED'.
     * This method is primarily used for airport dashboard displays and gate management systems.
     * </p>
     * <p>
     * The method populates multiple lists with flight data including flight identification,
     * airline information, scheduling details, seating capacity, gate assignments, and
     * delay information. All lists are populated with corresponding data at the same
     * index positions.
     * </p>
     * <p>
     * Only flights with flight_type = true (departing flights) are retrieved, and the
     * query includes flights that haven't departed yet. The departure time calculation
     * includes any flight delays to provide accurate real-time information.
     * </p>
     *
     * @param parId list to be populated with flight identifiers
     * @param parCompanyName list to be populated with airline company names
     * @param parDate list to be populated with flight dates derived from departure timestamps
     * @param parDepartureTime list to be populated with scheduled departure times
     * @param parArrivalTime list to be populated with arrival times at destination airports
     * @param parStatus list to be populated with current flight status values
     * @param parMaxSeats list to be populated with maximum seating capacity
     * @param parFreeSeats list to be populated with available seats count
     * @param origin list to be populated with destination airport or city names
     * @param delay list to be populated with departure delay values in minutes
     * @param parGate list to be populated with assigned gate numbers (null if not assigned)
     * @throws SQLException if a database access error occurs during the retrieval operation
     */
    void getImminentDepartingFlights (List<String> parId, List<String> parCompanyName, List<Date> parDate,
                                             List<Time> parDepartureTime, List<Time> parArrivalTime, List<String> parStatus,
                                             List<Integer> parMaxSeats, List<Integer> parFreeSeats, List<String> origin,
                                             List<Integer> delay, List<Integer> parGate) throws SQLException;

    /**
     * Searches for flights based on various filtering criteria.
     * <p>
     * This method performs comprehensive flight searches using flexible filtering criteria
     * including departure/arrival cities, travel dates, departure times, and other flight
     * characteristics. It supports both customer flight searches and administrative flight
     * management operations.
     * </p>
     * <p>
     * The method handles city-based filtering with special logic for "Napoli" as the airport's
     * base location. When searching for departing flights from Napoli, it retrieves flights
     * with flight_type = true (departing). When searching for arriving flights to Napoli,
     * it retrieves flights with flight_type = false (arriving).
     * </p>
     * <p>
     * Date and time filtering supports flexible ranges:
     * </p>
     * <ul>
     *   <li>Date filtering uses inclusive ranges between initial and final dates</li>
     *   <li>Time filtering handles both same-day and overnight time spans</li>
     *   <li>When initial time is after final time, it searches for flights departing after initial time OR before final time</li>
     * </ul>
     * <p>
     * Results are ordered by departure time in descending order to show the most recent flights first.
     * All filter parameters are optional and the method constructs appropriate SQL queries based on provided criteria.
     * </p>
     *
     * @param departingCity the departure city name for filtering (null or empty for no filter)
     * @param arrivingCity the arrival city name for filtering (null or empty for no filter)
     * @param initialDate the start date for date range filtering (null for no date filter)
     * @param finalDate the end date for date range filtering (null for no date filter)
     * @param initialTime the start time for time range filtering (null for no time filter)
     * @param finalTime the end time for time range filtering (null for no time filter)
     * @param ids list to be populated with flight identifiers
     * @param companyNames list to be populated with airline company names
     * @param dates list to be populated with flight dates
     * @param departureTimes list to be populated with departure times
     * @param arrivalTimes list to be populated with arrival times
     * @param delays list to be populated with flight delay values in minutes
     * @param status list to be populated with flight status values
     * @param maxSeats list to be populated with maximum seating capacity
     * @param freeSeats list to be populated with available seats count
     * @param cities list to be populated with destination or origin city names
     * @param types list to be populated with flight type indicators (true for departing, false for arriving)
     * @throws SQLException if a database access error occurs during the search operation
     */
    void searchFlight (String departingCity, String arrivingCity, LocalDate initialDate, LocalDate finalDate, LocalTime initialTime, LocalTime finalTime,
                              List<String> ids, List<String> companyNames, List<java.sql.Date> dates, List<Time> departureTimes, List<Time> arrivalTimes,
                              List<Integer> delays, List<String> status, List<Integer> maxSeats, List<Integer> freeSeats, List<String> cities, List<Boolean> types) throws SQLException;

    /**
     * Retrieves comprehensive data for a specific flight including all associated information.
     * <p>
     * This method performs a complex query to fetch complete flight information including
     * gate assignments, customer details, booking information, ticket data, passenger details,
     * and luggage information. It provides a comprehensive view of all entities associated
     * with a specific flight for administrative and operational purposes.
     * </p>
     * <p>
     * The method joins multiple database tables (FLIGHT, BOOKING, TICKET, PASSENGER, LUGGAGE, CUSTOMER)
     * to provide complete flight information. All lists are populated with corresponding data
     * at the same index positions, allowing for easy data correlation and processing.
     * </p>
     * <p>
     * The retrieved data includes:
     * </p>
     * <ul>
     *   <li>Gate assignment information for operational management</li>
     *   <li>Customer account details for all passengers with bookings</li>
     *   <li>Complete booking information including status and timestamps</li>
     *   <li>Ticket details with seat assignments and check-in status</li>
     *   <li>Passenger personal information and identification details</li>
     *   <li>Associated luggage information including types and status</li>
     * </ul>
     * <p>
     * This method is particularly useful for check-in operations, flight manifest generation,
     * and comprehensive flight management tasks that require complete flight information.
     * </p>
     *
     * @param flightId the unique identifier of the flight to retrieve data for
     * @param flightGates list to be populated with gate assignments (null if not assigned)
     * @param buyerIds list to be populated with customer identifiers who made bookings
     * @param usernames list to be populated with customer usernames
     * @param mails list to be populated with customer email addresses
     * @param hashedPasswords list to be populated with customer password hashes
     * @param bookingDates list to be populated with booking creation timestamps
     * @param bookingStatus list to be populated with booking status values
     * @param bookingIds list to be populated with unique booking identifiers
     * @param ticketNumbers list to be populated with ticket numbers
     * @param seats list to be populated with seat assignments (null if not assigned, 0-based indexing)
     * @param checkedIns list to be populated with check-in status indicators
     * @param firstNames list to be populated with passenger first names
     * @param lastNames list to be populated with passenger last names
     * @param passengerSSNs list to be populated with passenger SSN identifiers
     * @param birthDates list to be populated with passenger birth dates
     * @param luggageIds list to be populated with luggage identifiers (null if no luggage)
     * @param luggageTypes list to be populated with luggage type descriptions
     * @param luggageStatus list to be populated with luggage status values
     * @param luggageIdsAfterCheckin list to be populated with post-checkin luggage identifiers
     * @throws SQLException if a database access error occurs during the data retrieval operation
     */
    void getAllDataForAFlight(String flightId, List<Integer> flightGates, List<Integer> buyerIds,
                              List<String> usernames, List<String> mails, List<String> hashedPasswords,
                              List<java.sql.Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds,
                              List<String> ticketNumbers, List<Integer> seats, List<Boolean> checkedIns,
                              List<String> firstNames, List<String> lastNames, List<String> passengerSSNs, List<java.sql.Date> birthDates,
                              List<Integer> luggageIds, List<String> luggageTypes, List<String> luggageStatus, List<String> luggageIdsAfterCheckin) throws SQLException;

    /**
     * Inserts a new flight into the database with complete flight information.
     * <p>
     * This method creates a new flight record with all essential flight details including
     * identification, scheduling, seating capacity, and route information. The flight is
     * created with default status 'PROGRAMMED' and free seats equal to maximum seats.
     * </p>
     * <p>
     * The method handles both arriving and departing flights based on the flightType parameter:
     * </p>
     * <ul>
     *   <li>flightType = true: departing flight (otherCity represents destination)</li>
     *   <li>flightType = false: arriving flight (otherCity represents origin)</li>
     * </ul>
     * <p>
     * The flight insertion operation is atomic and uses database transactions to ensure
     * data consistency. The method sets appropriate default values for status and seating
     * availability to ensure flights are created in a consistent operational state.
     * </p>
     * <p>
     * Timestamp parameters should include both date and time information for accurate
     * scheduling. The method assumes that departure and arrival timestamps are provided
     * in the appropriate timezone for the airport operations.
     * </p>
     *
     * @param flightId the unique identifier for the new flight (must be unique in the system)
     * @param companyName the name of the airline company operating the flight
     * @param departureTimestamp the complete departure date and time
     * @param arrivalTimestamp the complete arrival date and time
     * @param maxSeats the maximum number of seats available on the flight (must be positive)
     * @param otherCity the destination city (for departing flights) or origin city (for arriving flights)
     * @param flightType true for departing flights, false for arriving flights
     * @throws SQLException if a database access error occurs during the flight insertion operation
     */
    void InsertAFlight(String flightId, String companyName, Timestamp departureTimestamp, Timestamp arrivalTimestamp,
                       int maxSeats, String otherCity, boolean flightType) throws SQLException;

    /**
     * Retrieves all currently booked seats for a specific flight, excluding cancelled bookings.
     * <p>
     * This method queries the database to find all seat assignments for tickets associated
     * with a specific flight. It excludes bookings with 'CANCELLED' status to provide
     * accurate seat availability information for booking and seat selection operations.
     * </p>
     * <p>
     * The method handles seat number conversion from database storage (1-based) to
     * application indexing (0-based) for consistency with the application's seat
     * management system. Seats with value 0 or null in the database are not included
     * in the results as they represent unassigned seats.
     * </p>
     * <p>
     * An optional booking ID parameter allows excluding a specific booking from the
     * results, which is useful during booking modifications where the current booking's
     * seats should not be considered as occupied.
     * </p>
     *
     * @param flightId the unique identifier of the flight to check seat availability for
     * @param bookingId optional booking ID to exclude from results (can be null)
     * @param bookedSeats list to be populated with booked seat numbers (0-based indexing)
     */
    void getBookedSeats(String flightId, Integer bookingId, List<Integer> bookedSeats);

    /**
     * Initiates the check-in process for a flight by updating its status to 'ABOUT_TO_DEPART'.
     * <p>
     * This method updates a flight's status to indicate that check-in procedures have
     * begun and the flight is preparing for departure. This status change typically
     * triggers various operational processes including gate assignments, passenger
     * notifications, and boarding preparations.
     * </p>
     * <p>
     * The status change from 'PROGRAMMED' to 'ABOUT_TO_DEPART' is a critical operational
     * transition that affects passenger check-in availability, seat assignments, and
     * various airport management systems.
     * </p>
     *
     * @param flightId the unique identifier of the flight to start check-in for
     * @return the number of rows affected by the update operation (1 if successful, 0 if flight not found)
     * @throws SQLException if a database access error occurs during the status update operation
     */
    int startCheckin(String flightId) throws SQLException;

    /**
     * Searches for an available gate and assigns it to the specified flight.
     * <p>
     * This method implements automatic gate assignment by iterating through gates 1-20
     * and finding the first available gate (not assigned to any non-cancelled flight).
     * Once an available gate is found, it is immediately assigned to the specified flight.
     * </p>
     * <p>
     * The method uses database transactions to ensure atomicity of the gate assignment
     * process. It checks each gate's availability and assigns the first free gate,
     * preventing race conditions in concurrent gate assignment operations.
     * </p>
     * <p>
     * Gate availability is determined by checking if any flights are currently assigned
     * to the gate with a status other than 'CANCELLED'. This ensures that cancelled
     * flights do not block gate availability for operational flights.
     * </p>
     *
     * @param idFlight the unique identifier of the flight to assign a gate to
     * @return the assigned gate number (1-20) if successful, -1 if no gates are available or an error occurs
     */
    int searchGate(String idFlight);

    /**
     * Assigns a specific gate to a flight.
     * <p>
     * This method updates the gate assignment for a specified flight, allowing manual
     * gate assignment or reassignment operations. The method uses database transactions
     * to ensure the assignment is completed atomically.
     * </p>
     * <p>
     * Unlike {@link #searchGate(String)}, this method does not check gate availability
     * and will assign the specified gate regardless of its current status. This allows
     * for administrative override of automatic gate assignments when necessary.
     * </p>
     *
     * @param idGate the gate number to assign to the flight
     * @param idFlight the unique identifier of the flight to assign the gate to
     */
    void setGate(int idGate, String idFlight);

    /**
     * Updates the status of a specific flight.
     * <p>
     * This method allows updating a flight's operational status to reflect current
     * conditions such as delays, boarding, departure, arrival, or cancellation.
     * The method uses PostgreSQL's enum casting to ensure proper status validation.
     * </p>
     * <p>
     * Common flight status transitions include:
     * </p>
     * <ul>
     *   <li>PROGRAMMED → ABOUT_TO_DEPART (check-in started)</li>
     *   <li>ABOUT_TO_DEPART → DELAYED (departure delayed)</li>
     *   <li>ABOUT_TO_DEPART → DEPARTED (flight has left)</li>
     *   <li>Any status → CANCELLED (flight cancelled)</li>
     * </ul>
     * <p>
     * The method uses database transactions to ensure status changes are applied
     * atomically and consistently across the system.
     * </p>
     *
     * @param status the new flight status to set (must be a valid FlightStatus enum value)
     * @param idFlight the unique identifier of the flight to update
     * @return the number of rows affected by the update (1 if successful, 0 if flight not found), -1 if an error occurs
     */
    int setStatus (String status, String idFlight);

    /**
     * Adds additional delay to a flight's current delay value.
     * <p>
     * This method increases the flight's delay by the specified number of minutes,
     * allowing for cumulative delay tracking. The delay is added to any existing
     * delay value, providing accurate delay information for passengers and operations.
     * </p>
     * <p>
     * Flight delays affect various system calculations including arrival and departure
     * time displays, gate scheduling, and passenger notifications. The method uses
     * database transactions to ensure delay updates are applied consistently.
     * </p>
     *
     * @param delay the number of minutes to add to the current flight delay (must be positive)
     * @param idFlight the unique identifier of the flight to add delay to
     * @return the number of rows affected by the update (1 if successful, 0 if flight not found), -1 if an error occurs
     */
    int addDelay(int delay, String idFlight);

    /**
     * Updates check-in status for multiple tickets in batch operations.
     * <p>
     * This method efficiently updates the check-in status for multiple tickets using
     * separate lists for tickets to be checked in (true) and tickets to be unchecked
     * (false). This batch approach reduces database round trips and ensures consistent
     * check-in status updates.
     * </p>
     * <p>
     * The method uses database transactions to ensure all check-in status changes
     * are applied atomically. This is particularly important for group bookings
     * where multiple passengers' check-in status needs to be updated simultaneously.
     * </p>
     *
     * @param trueTickets list of ticket numbers to set check-in status to true
     * @param falseTickets list of ticket numbers to set check-in status to false
     */
    void setCheckins (ArrayList<String> trueTickets, ArrayList<String> falseTickets);

    /**
     * Retrieves post-checkin luggage identifiers for multiple tickets.
     * <p>
     * This method queries the database to retrieve luggage identifiers assigned after
     * check-in for a list of tickets. These identifiers are used for luggage tracking
     * during the baggage handling process after passengers have checked in.
     * </p>
     * <p>
     * The method returns a nested list structure where each inner list contains all
     * luggage identifiers associated with a specific ticket. This allows for proper
     * correlation between tickets and their associated luggage items.
     * </p>
     * <p>
     * Post-checkin luggage identifiers are typically assigned during the check-in
     * process and are used for physical luggage tracking throughout the airport
     * baggage handling system.
     * </p>
     *
     * @param tickets list of ticket numbers to retrieve luggage identifiers for
     * @return nested list where each inner list contains luggage identifiers for the corresponding ticket, null if an error occurs
     */
    ArrayList<ArrayList<String>> getLuggagesCheckins (ArrayList<String> tickets);
}