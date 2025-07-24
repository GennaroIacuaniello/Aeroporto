package dao;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

/**
 * Data Access Object interface for managing luggage operations in the airport management system.
 * <p>
 * This interface defines the contract for all luggage-related database operations, providing
 * methods for retrieving lost luggage information and managing luggage associations with bookings.
 * It serves as the primary interface for luggage data persistence and retrieval operations,
 * supporting both customer service functions and administrative luggage management tasks.
 * </p>
 * <p>
 * The LuggageDAO interface provides comprehensive luggage management capabilities including:
 * </p>
 * <ul>
 *   <li>Retrieval of all lost luggage items across the system for recovery operations</li>
 *   <li>Comprehensive luggage data retrieval including passenger and flight information</li>
 *   <li>Booking-specific luggage retrieval for customer service and operational purposes</li>
 *   <li>Integration with flight, booking, ticket, and passenger data systems</li>
 * </ul>
 * <p>
 * The interface supports complex queries that join multiple database tables to provide
 * complete luggage information including associated flight details, passenger information,
 * booking data, and luggage status tracking. All retrieval methods populate multiple lists
 * with related data to provide comprehensive luggage information for display and processing.
 * </p>
 * <p>
 * Luggage data includes detailed information about luggage types (carry-on or checked),
 * current status throughout the handling lifecycle, associated tickets and passengers,
 * and identification codes for tracking purposes. The interface handles luggage in various
 * states from initial booking through to final delivery or loss reporting.
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
 * @see model.Luggage
 * @see implementazioni_postgres_dao.LuggageDAOImpl
 * @see model.LuggageType
 * @see model.LuggageStatus
 * @see model.Ticket
 * @see model.Booking
 * @see controller.LuggageController
 */
public interface LuggageDAO {

    /**
     * Retrieves comprehensive information about all lost luggage items in the system.
     * <p>
     * This method performs a complex query to fetch complete information about all luggage
     * items that have been reported as lost (luggage_status = 'LOST'). It provides a
     * comprehensive view of lost luggage including associated flight details, passenger
     * information, booking data, and luggage specifications for recovery and customer
     * service operations.
     * </p>
     * <p>
     * The method joins multiple database tables (FLIGHT, BOOKING, TICKET, PASSENGER, LUGGAGE, CUSTOMER)
     * to provide complete lost luggage information. All lists are populated with corresponding data
     * at the same index positions, allowing for easy data correlation and processing.
     * </p>
     * <p>
     * The retrieved data includes:
     * </p>
     * <ul>
     *   <li>Complete flight information including schedules and airline details</li>
     *   <li>Customer account information for notification and contact purposes</li>
     *   <li>Booking details including status and creation timestamps</li>
     *   <li>Ticket information with seat assignments and check-in status</li>
     *   <li>Passenger personal information for identification and contact</li>
     *   <li>Luggage specifications including type, status, and tracking identifiers</li>
     * </ul>
     * <p>
     * Results are ordered by flight departure time in descending order to prioritize
     * recent lost luggage items. Only luggage associated with non-deleted customer
     * accounts is included in the results to maintain data integrity.
     * </p>
     * <p>
     * This method is particularly useful for lost luggage recovery operations,
     * customer service inquiries, and administrative reporting on baggage handling
     * performance and recovery statistics.
     * </p>
     *
     * @param flightIds list to be populated with flight identifiers
     * @param companyNames list to be populated with airline company names
     * @param flightDates list to be populated with flight dates derived from departure timestamps
     * @param departureTimes list to be populated with flight departure times
     * @param arrivalTimes list to be populated with flight arrival times
     * @param flightStatus list to be populated with current flight status values
     * @param maxSeats list to be populated with flight maximum seating capacity
     * @param freeSeats list to be populated with available seats count
     * @param cities list to be populated with destination or origin city names
     * @param flightTypes list to be populated with flight type indicators (true for departing, false for arriving)
     * @param buyerIds list to be populated with customer identifiers who made the bookings
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
     * @param luggageTypes list to be populated with luggage type descriptions (CARRY_ON or CHECKED)
     * @param luggageStatus list to be populated with luggage status values (should be 'LOST')
     * @param luggageIdsAfterCheckin list to be populated with post-checkin luggage identifiers
     * @throws SQLException if a database access error occurs during the data retrieval operation
     */
    void getAllLostLuggages(List<String> flightIds, List<String> companyNames, List<Date> flightDates,
                            List<Time> departureTimes, List<Time> arrivalTimes, List<String> flightStatus,
                            List<Integer> maxSeats, List<Integer> freeSeats,
                            List<String> cities, List<Boolean> flightTypes,
                            List<Integer> buyerIds, List<String> usernames, List<String> mails, List<String> hashedPasswords,
                            List<Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds,
                            List<String> ticketNumbers, List<Integer> seats, List<Boolean> checkedIns,
                            List<String> firstNames, List<String> lastNames, List<String> passengerSSNs, List<Date> birthDates,
                            List<Integer> luggageIds, List<String> luggageTypes, List<String> luggageStatus, List<String> luggageIdsAfterCheckin) throws SQLException;

    /**
     * Retrieves all luggage items associated with a specific booking.
     * <p>
     * This method fetches comprehensive luggage information for all luggage items
     * associated with a particular booking. It provides detailed luggage data
     * including identification, type, status, and associated ticket information
     * for customer service operations and booking management purposes.
     * </p>
     * <p>
     * The method joins multiple database tables (BOOKING, TICKET, PASSENGER, LUGGAGE, CUSTOMER)
     * to provide complete luggage information for the specified booking. All lists are
     * populated with corresponding data at the same index positions, enabling easy
     * correlation between luggage items and their associated tickets.
     * </p>
     * <p>
     * The retrieved data includes:
     * </p>
     * <ul>
     *   <li>Ticket numbers associated with each luggage item</li>
     *   <li>Luggage database identifiers for system tracking</li>
     *   <li>Post-checkin luggage identifiers for physical tracking</li>
     *   <li>Luggage type classification (CARRY_ON or CHECKED)</li>
     *   <li>Current luggage status throughout handling lifecycle</li>
     * </ul>
     * <p>
     * Results are ordered by ticket number to provide consistent ordering and
     * enable easy correlation with passenger and ticket information. The method
     * handles cases where luggage may not have been assigned a physical ID yet
     * by including null values in the luggageIds list.
     * </p>
     * <p>
     * This method is particularly useful for customer service operations,
     * check-in procedures, luggage management tasks, and booking modification
     * processes where complete luggage information is required.
     * </p>
     *
     * @param bookingId the unique identifier of the booking to retrieve luggage for
     * @param ticketNumbers list to be populated with ticket numbers associated with each luggage item
     * @param luggageIds list to be populated with luggage database identifiers (null if not assigned)
     * @param luggageTypes list to be populated with luggage type descriptions (CARRY_ON or CHECKED)
     * @param luggageStatus list to be populated with luggage status values (BOOKED, LOADED, WITHDRAWABLE, LOST)
     * @param luggageIdsAfterCheckin list to be populated with post-checkin luggage identifiers for physical tracking
     * @throws SQLException if a database access error occurs during the data retrieval operation
     */
    void getAllLuggagesOfBooking(Integer bookingId, List<String> ticketNumbers, List<Integer> luggageIds, List<String> luggageTypes, List<String> luggageStatus, List<String> luggageIdsAfterCheckin) throws SQLException;

    void lostLuggage(String ticket, String luggageStatus);
}