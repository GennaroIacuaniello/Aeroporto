package dao;

import database.ConnessioneDatabase;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object interface for managing booking operations in the airport management system.
 * <p>
 * This interface defines the contract for all booking-related database operations, providing
 * methods for creating, searching, and managing flight reservations throughout their lifecycle.
 * It serves as the primary interface for booking data persistence and retrieval operations,
 * supporting both customer and administrative booking management functions.
 * </p>
 * <p>
 * The BookingDAO interface provides comprehensive booking management capabilities including:
 * </p>
 * <ul>
 *   <li>Booking creation with passenger and luggage associations</li>
 *   <li>Customer booking searches with various filtering criteria</li>
 *   <li>Flight-specific booking retrieval operations</li>
 *   <li>Passenger-based booking searches and filtering</li>
 *   <li>Comprehensive booking data retrieval for display and reporting</li>
 * </ul>
 * <p>
 * The interface follows the DAO pattern to provide a clean separation between business logic
 * and data persistence layer, enabling different implementations for various database systems
 * while maintaining consistent functionality across the application.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see model.Booking
 * @see implementazioni_postgres_dao.BookingDAOImpl
 * @see model.Ticket
 * @see model.Passenger
 * @see model.Flight
 * @see model.Customer
 * @see SQLException
 */
public interface BookingDAO {

    /**
     * Creates a new booking with associated tickets, passengers, and luggages.
     * <p>
     * This method performs a comprehensive booking creation operation that includes
     * inserting the booking record, creating associated passenger records (if they
     * don't exist), generating tickets, and creating luggage associations. The
     * operation is performed atomically to ensure data consistency.
     * </p>
     * <p>
     * The method handles the complete booking workflow:
     * </p>
     * <ul>
     *   <li>Creates the booking record with the specified status and customer</li>
     *   <li>Inserts or updates passenger information as needed</li>
     *   <li>Generates tickets with seat assignments for each passenger</li>
     *   <li>Creates luggage records associated with specific tickets</li>
     * </ul>
     *
     * @param idCustomer the unique identifier of the customer making the booking
     * @param idFlight the unique identifier of the flight being booked
     * @param bookingStatus the initial status of the booking (e.g., "PENDING", "CONFIRMED")
     * @param ticketNumbers list of unique ticket numbers for each passenger
     * @param seats list of seat assignments for each ticket (can contain -1 for no assignment)
     * @param firstNames list of passenger first names (can contain null values)
     * @param lastNames list of passenger last names (can contain null values)
     * @param birthDates list of passenger birthdates (can contain null values)
     * @param passengerSSNs list of passenger SSN identifiers (required, cannot be null)
     * @param luggagesTypes list of luggage types for each luggage item
     * @param ticketForLuggages list of ticket numbers associated with each luggage item
     * @throws SQLException if a database access error occurs during the booking creation process
     */
    void addBooking (int idCustomer, String idFlight, String bookingStatus, List<String> ticketNumbers, List<Integer> seats, List<String> firstNames,
                     List<String> lastNames, List<Date> birthDates, List<String> passengerSSNs, List<String> luggagesTypes, List<String> ticketForLuggages) throws SQLException;

    /**
     * Searches for bookings made by a specific customer for a particular flight.
     * <p>
     * This method retrieves all bookings associated with a specific customer and flight
     * combination, providing essential booking information for customer service and
     * booking management operations. The results are ordered by flight departure time
     * to provide chronological organization.
     * </p>
     * <p>
     * The method populates the provided lists with matching booking data including
     * booking dates, status information, and unique booking identifiers. This
     * information is typically used for displaying customer booking history or
     * enabling booking modifications.
     * </p>
     *
     * @param flightId the unique identifier of the flight to search for
     * @param loggedCustomerId the unique identifier of the customer whose bookings to retrieve
     * @param bookingDates list to be populated with booking creation dates
     * @param bookingStatus list to be populated with current booking status values
     * @param bookingIds list to be populated with unique booking identifiers
     * @throws SQLException if a database access error occurs during the search operation
     */
    void searchBooksCustomerForAFlight(String flightId, Integer loggedCustomerId,
                                              List<Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds) throws SQLException;

    /**
     * Retrieves all bookings made by a specific customer across all flights.
     * <p>
     * This method performs a comprehensive retrieval of all bookings associated with
     * a specific customer, including complete flight information and booking details.
     * The results provide a complete overview of the customer's travel history and
     * current reservations for customer service and self-service applications.
     * </p>
     * <p>
     * The method populates multiple lists with related data to provide complete
     * information about each booking including flight details, timing information,
     * and booking status. Results are ordered by flight departure time to provide
     * chronological organization.
     * </p>
     *
     * @param loggedCustomerId the unique identifier of the customer whose bookings to retrieve
     * @param flightIds list to be populated with flight identifiers
     * @param companyNames list to be populated with airline company names
     * @param flightDates list to be populated with flight dates
     * @param departureTimes list to be populated with flight departure times
     * @param arrivalTimes list to be populated with flight arrival times
     * @param flightStatus list to be populated with current flight status values
     * @param maxSeats list to be populated with flight maximum seat capacity
     * @param freeSeats list to be populated with available seats count
     * @param cities list to be populated with destination or origin city names
     * @param types list to be populated with flight type indicators (true for arriving, false for departing)
     * @param bookingDates list to be populated with booking creation dates
     * @param bookingStatus list to be populated with current booking status values
     * @param bookingIds list to be populated with unique booking identifiers
     * @throws SQLException if a database access error occurs during the retrieval operation
     */
    void getAllBooksCustomer(Integer loggedCustomerId, List<String> flightIds, ArrayList<String> companyNames, List<Date> flightDates,
                             List<Time> departureTimes, List<Time> arrivalTimes,
                             List<String> flightStatus, List<Integer> maxSeats, List<Integer> freeSeats,
                             List<String> cities, List<Boolean> types,
                             List<Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds) throws SQLException;

    /**
     * Searches for customer bookings with flight-based filtering criteria.
     * <p>
     * This method performs advanced booking searches based on flight characteristics
     * such as departure/arrival cities, travel dates, and departure times. It enables
     * customers to find their bookings using travel-related criteria rather than
     * booking-specific information.
     * </p>
     * <p>
     * The method supports flexible filtering with the following logic:
     * </p>
     * <ul>
     *   <li>City filtering handles "Napoli" as the airport's base city</li>
     *   <li>Date filtering uses inclusive ranges between initial and final dates</li>
     *   <li>Time filtering supports both same-day and overnight time ranges</li>
     *   <li>Results are ordered by flight departure time in descending order</li>
     * </ul>
     * <p>
     * All filter parameters are optional (can be null or empty) and the method
     * constructs appropriate SQL queries based on provided criteria.
     * </p>
     *
     * @param departingCity the departure city name for filtering (can be null or empty)
     * @param arrivingCity the arrival city name for filtering (can be null or empty)
     * @param initialDate the start date for date range filtering (can be null)
     * @param finalDate the end date for date range filtering (can be null)
     * @param initialTime the start time for time range filtering (can be null)
     * @param finalTime the end time for time range filtering (can be null)
     * @param loggedCustomerId the unique identifier of the customer whose bookings to search
     * @param flightIds list to be populated with matching flight identifiers
     * @param companyNames list to be populated with airline company names
     * @param flightDates list to be populated with flight dates
     * @param departureTimes list to be populated with flight departure times
     * @param arrivalTimes list to be populated with flight arrival times
     * @param flightStatus list to be populated with current flight status values
     * @param maxSeats list to be populated with flight maximum seat capacity
     * @param freeSeats list to be populated with available seats count
     * @param cities list to be populated with destination or origin city names
     * @param types list to be populated with flight type indicators
     * @param bookingDates list to be populated with booking creation dates
     * @param bookingStatus list to be populated with current booking status values
     * @param bookingIds list to be populated with unique booking identifiers
     * @throws SQLException if a database access error occurs during the search operation
     */
    void searchBooksCustomerFilteredFlights(String departingCity, String arrivingCity, LocalDate initialDate, LocalDate finalDate, LocalTime initialTime, LocalTime finalTime,
                                            Integer loggedCustomerId, List<String> flightIds, List<String> companyNames, List<Date> flightDates, List<Time> departureTimes, List<Time> arrivalTimes,
                                            List<String> flightStatus, List<Integer> maxSeats, List<Integer> freeSeats, List<String> cities, List<Boolean> types,
                                            List<Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds) throws SQLException;

    /**
     * Searches for customer bookings with passenger-based filtering criteria.
     * <p>
     * This method performs advanced booking searches based on passenger information
     * such as names, SSN, or ticket numbers. It enables customers and customer service
     * representatives to find bookings using passenger-specific information when
     * flight details are not readily available.
     * </p>
     * <p>
     * The method supports flexible passenger-based filtering including:
     * </p>
     * <ul>
     *   <li>First name matching with case-insensitive partial matching</li>
     *   <li>Last name matching with case-insensitive partial matching</li>
     *   <li>SSN matching with case-insensitive partial matching</li>
     *   <li>Ticket number matching with case-insensitive partial matching</li>
     * </ul>
     * <p>
     * All filter parameters are optional and use SQL ILIKE for flexible matching.
     * The method returns distinct results to avoid duplicates when multiple
     * passengers in the same booking match the criteria. Results are ordered by
     * flight departure time in descending order.
     * </p>
     *
     * @param firstName the passenger first name for filtering (can be null or empty)
     * @param lastName the passenger last name for filtering (can be null or empty)
     * @param passengerSSN the passenger SSN for filtering (can be null or empty)
     * @param ticketNumber the ticket number for filtering (can be null or empty)
     * @param loggedCustomerId the unique identifier of the customer whose bookings to search
     * @param flightIds list to be populated with matching flight identifiers
     * @param companyNames list to be populated with airline company names
     * @param flightDates list to be populated with flight dates
     * @param departureTimes list to be populated with flight departure times
     * @param arrivalTimes list to be populated with flight arrival times
     * @param flightStatus list to be populated with current flight status values
     * @param maxSeats list to be populated with flight maximum seat capacity
     * @param freeSeats list to be populated with available seats count
     * @param cities list to be populated with destination or origin city names
     * @param types list to be populated with flight type indicators
     * @param bookingDates list to be populated with booking creation dates
     * @param bookingStatus list to be populated with current booking status values
     * @param bookingIds list to be populated with unique booking identifiers
     * @throws SQLException if a database access error occurs during the search operation
     */
    void searchBooksCustomerFilteredPassengers(String firstName, String lastName, String passengerSSN, String ticketNumber,
                                               Integer loggedCustomerId, List<String> flightIds, List<String> companyNames, List<Date> flightDates,
                                               List<Time> departureTimes, List<Time> arrivalTimes, List<String> flightStatus,
                                               List<Integer> maxSeats, List<Integer> freeSeats, List<String> cities, List<Boolean> types,
                                               List<Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds) throws SQLException;

    /**
     * Modifies an existing booking with new passenger, ticket, and luggage information.
     * <p>
     * This method performs a complex booking modification operation that maintains data
     * consistency through the use of temporary tickets and transactional operations.
     * The modification process completely replaces existing tickets and luggage while
     * preserving the original booking record.
     * </p>
     * <p>
     * The operation follows this sequence:
     * </p>
     * <ol>
     *   <li>Creates a temporary ticket to maintain foreign key relationships</li>
     *   <li>Deletes all existing tickets except the temporary one</li>
     *   <li>Updates or inserts passenger information as needed</li>
     *   <li>Creates new tickets with updated seat assignments</li>
     *   <li>Creates new luggage records for the updated tickets</li>
     *   <li>Removes the temporary ticket</li>
     *   <li>Updates the booking status</li>
     * </ol>
     * <p>
     * The temporary ticket mechanism ensures that the booking always has at least one
     * associated ticket during the modification process, preventing foreign key violations
     * and maintaining referential integrity. All operations are performed within a
     * single transaction to ensure atomicity.
     * </p>
     *
     * @param idFlight the flight identifier for the booking
     * @param idBooking the unique identifier of the booking to modify
     * @param ticketNumbers list of new ticket numbers for the booking
     * @param seats list of new seat assignments (can contain -1 for no assignment)
     * @param firstNames list of passenger first names (can contain null values)
     * @param lastNames list of passenger last names (can contain null values)
     * @param birthDates list of passenger birth dates (can contain null values)
     * @param passengerSSNs list of passenger SSN identifiers (required)
     * @param luggagesTypes list of luggage types for new luggage items
     * @param ticketForLuggages list of ticket numbers associated with each luggage item
     * @param tmpTicket temporary ticket number used during the modification process
     * @param bookingStatus new status for the booking after modification
     * @throws SQLException if a database access error occurs during the modification process
     */
    void modifyBooking (String idFlight, Integer idBooking, List<String> ticketNumbers, List<Integer> seats, List<String> firstNames,
                               List<String> lastNames, List<Date> birthDates, List<String> passengerSSNs, List<String> luggagesTypes, List<String> ticketForLuggages, String tmpTicket, String bookingStatus) throws SQLException;

    /**
     * Soft deletion of a booking by setting its status to cancelled
     * <p>
     * This method implements soft deletion by updating the booking status rather than
     * physically removing the booking record from the database. This approach preserves
     * data integrity and maintains audit trails for administrative and compliance purposes
     * while effectively removing the booking from active operations.
     * </p>
     * <p>
     * The soft deletion process:
     * </p>
     * <ul>
     *   <li>Updates the booking_status to 'CANCELLED' for the specified booking</li>
     *   <li>Preserves all booking data for audit and historical purposes</li>
     *   <li>Maintains referential integrity with related tickets, passengers, and luggage</li>
     *   <li>Allows the booking to be excluded from active booking queries when needed</li>
     * </ul>
     * <p>
     * Cancelled bookings are typically filtered out of customer-facing queries but
     * remain accessible for administrative reporting and audit purposes. This approach
     * ensures compliance with data retention requirements while providing clear booking
     * lifecycle management.
     * </p>
     *
     * @param bookingId the unique identifier of the booking to delete/cancel
     * @throws SQLException if a database access error occurs during the deletion operation
     */
    void deleteBooking (int bookingId) throws SQLException;
}