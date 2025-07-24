package dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Data Access Object interface for managing ticket operations in the airport management system.
 * <p>
 * This interface defines the contract for all ticket-related database operations, providing
 * methods for retrieving ticket information and managing ticket associations with bookings.
 * It serves as the primary interface for ticket data persistence and retrieval operations,
 * supporting both customer service functions and administrative ticket management tasks.
 * </p>
 * <p>
 * The TicketDAO interface provides comprehensive ticket management capabilities including:
 * </p>
 * <ul>
 *   <li>Retrieval of all tickets associated with specific bookings</li>
 *   <li>Ticket number generation and management for new bookings</li>
 *   <li>Comprehensive ticket data retrieval including passenger and seat information</li>
 *   <li>Integration with booking, passenger, and flight data systems</li>
 * </ul>
 * <p>
 * The interface supports complex queries that provide complete ticket information including
 * seat assignments, check-in status, passenger details, and booking associations. All retrieval
 * methods populate multiple lists with related data to provide comprehensive ticket information
 * for display and processing purposes.
 * </p>
 * <p>
 * Ticket data includes detailed information about ticket numbers, seat assignments, check-in
 * status, and associated passenger information. The interface handles tickets throughout their
 * lifecycle from initial booking through check-in and boarding processes.
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
 * @see model.Ticket
 * @see implementazioni_postgres_dao.TicketDAOImpl
 * @see model.Booking
 * @see model.Passenger
 * @see model.Flight
 * @see controller.TicketController
 */
public interface TicketDAO {

    /**
     * Retrieves all tickets associated with a specific booking.
     * <p>
     * This method fetches comprehensive ticket information for all tickets
     * associated with a particular booking. It provides detailed ticket data
     * including seat assignments, check-in status, and associated passenger
     * information for customer service operations and booking management purposes.
     * </p>
     * <p>
     * The method joins multiple database tables (TICKET, PASSENGER) to provide
     * complete ticket information for the specified booking. All lists are
     * populated with corresponding data at the same index positions, enabling easy
     * correlation between tickets and their associated passenger information.
     * </p>
     * <p>
     * The retrieved data includes:
     * </p>
     * <ul>
     *   <li>Ticket numbers for unique identification</li>
     *   <li>Seat assignments with 0-based indexing (null if not assigned)</li>
     *   <li>Check-in status indicators for boarding eligibility</li>
     *   <li>Passenger SSN identifiers for passenger association</li>
     *   <li>Passenger personal information including names and birth dates</li>
     * </ul>
     * <p>
     * Seat numbers are converted from 1-based database storage to 0-based indexing
     * for consistency with the application's seat management system. Seats with
     * value 0 or negative in the database are returned as null to indicate no
     * seat assignment.
     * </p>
     * <p>
     * This method is particularly useful for booking management operations,
     * check-in procedures, passenger manifest generation, and customer service
     * inquiries where complete ticket information for a booking is required.
     * </p>
     *
     * @param bookingId the unique identifier of the booking to retrieve tickets for
     * @param ticketNumbers list to be populated with ticket numbers
     * @param seats list to be populated with seat assignments (0-based indexing, null if not assigned)
     * @param checkedIns list to be populated with check-in status indicators
     * @param passengerSSNs list to be populated with passenger SSN identifiers
     * @param firstNames list to be populated with passenger first names
     * @param lastNames list to be populated with passenger last names
     * @param birthDates list to be populated with passenger birth dates
     * @throws SQLException if a database access error occurs during the data retrieval operation
     */
    void getAllTicketBooking(int bookingId, List<String> ticketNumbers, List<Integer> seats, List<Boolean> checkedIns,
                                    List<String> passengerSSNs, List<String> firstNames,
                                    List<String> lastNames, List<Date> birthDates) throws SQLException;

    /**
     * Generates a new unique ticket number by incrementing the maximum existing ticket number.
     * <p>
     * This method implements an atomic ticket number generation system that retrieves the
     * highest ticket number currently in the database and generates a new unique number
     * by applying the specified offset plus one additional increment. This ensures that
     * generated ticket numbers are always unique and sequential.
     * </p>
     * <p>
     * The generation process follows these steps:
     * </p>
     * <ol>
     *   <li>Queries the database for the maximum existing ticket number</li>
     *   <li>Applies the specified offset using the increment function</li>
     *   <li>Adds one final increment to generate the new ticket number</li>
     *   <li>Returns the formatted 13-digit ticket number with zero-padding</li>
     * </ol>
     * <p>
     * The offset parameter allows for bulk ticket number generation scenarios where
     * multiple consecutive ticket numbers need to be reserved. For single ticket
     * generation, an offset of 0 should be used.
     * </p>
     * <p>
     * The method uses database-level maximum value retrieval to ensure thread-safety
     * and prevent duplicate ticket number generation in concurrent environments.
     * Error handling includes comprehensive logging for operational monitoring.
     * </p>
     * <p>
     * Generated ticket numbers follow a 13-digit format with leading zero padding
     * to ensure consistent formatting and proper sorting in database operations
     * and user interfaces.
     * </p>
     *
     * @param offset the number of additional increments to apply before generating the final ticket number
     * @return a new unique 13-digit ticket number, or empty string if generation fails
     */
    String generateTicketNumber(int offset);
}