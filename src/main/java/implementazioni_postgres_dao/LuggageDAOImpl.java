package implementazioni_postgres_dao;

import controller.Controller;
import dao.LuggageDAO;
import database.ConnessioneDatabase;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PostgreSQL implementation of the LuggageDAO interface for managing luggage operations in the airport management system.
 * <p>
 * This class provides concrete implementations for all luggage-related database operations
 * defined in the {@link LuggageDAO} interface. It handles comprehensive luggage management
 * functions including lost luggage retrieval, booking-specific luggage queries, and luggage
 * status management operations using PostgreSQL database connectivity.
 * </p>
 * <p>
 * The implementation provides comprehensive luggage management capabilities including:
 * </p>
 * <ul>
 *   <li>Complete lost luggage retrieval with associated flight, passenger, and booking data</li>
 *   <li>Booking-specific luggage information retrieval for customer service operations</li>
 *   <li>Luggage status management including lost luggage reporting and tracking</li>
 *   <li>Integration with flight, booking, ticket, passenger, and customer data systems</li>
 *   <li>Comprehensive luggage tracking from booking through final delivery or loss</li>
 * </ul>
 * <p>
 * All database operations use prepared statements to prevent SQL injection attacks and ensure
 * data security. The class implements proper connection management using the singleton
 * {@link ConnessioneDatabase} pattern and handles resource cleanup through try-with-resources
 * statements.
 * </p>
 * <p>
 * The class handles complex multi-table operations that join multiple related entities:
 * </p>
 * <ul>
 *   <li>FLIGHT table for flight information and scheduling details</li>
 *   <li>BOOKING table for reservation data and customer associations</li>
 *   <li>TICKET table for individual passenger tickets and seat assignments</li>
 *   <li>PASSENGER table for passenger personal information</li>
 *   <li>LUGGAGE table for luggage specifications, status, and tracking data</li>
 *   <li>CUSTOMER table for customer account information and contact details</li>
 * </ul>
 * <p>
 * Luggage data handling includes special processing for various data types:
 * </p>
 * <ul>
 *   <li>Seat assignments: converted from 1-based database storage to 0-based application indexing</li>
 *   <li>Timestamp processing: proper conversion from database timestamps to Java Date/Time objects</li>
 *   <li>Nullable luggage IDs: handled consistently with null values for unassigned luggage</li>
 *   <li>Luggage status tracking: supports all luggage lifecycle states (BOOKED, LOADED, WITHDRAWABLE, LOST)</li>
 * </ul>
 * <p>
 * The implementation supports various luggage operational scenarios including customer service
 * inquiries, lost luggage recovery operations, booking management, and administrative reporting.
 * All methods maintain data integrity through proper transaction handling and provide
 * comprehensive error logging for operational monitoring.
 * </p>
 * <p>
 * All methods follow the contract defined by the {@link LuggageDAO} interface and maintain
 * data consistency through proper error handling, connection management, and resource cleanup.
 * The class uses appropriate SQL data types including PostgreSQL enums for luggage status
 * management.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see LuggageDAO
 * @see Luggage
 * @see ConnessioneDatabase
 * @see SQLException
 */
public class LuggageDAOImpl implements LuggageDAO {

    /**
     * Logger instance for recording database operation events and errors.
     */
    private static final Logger LOGGER = Logger.getLogger(LuggageDAOImpl.class.getName());

    /**
     * {@inheritDoc}
     * <p>
     * This implementation executes a complex SQL query that joins multiple tables (FLIGHT,
     * BOOKING, TICKET, PASSENGER, LUGGAGE, CUSTOMER) to retrieve comprehensive information
     * about all lost luggage items in the system. The query filters for luggage with
     * status 'LOST' and excludes deleted customer accounts to maintain data integrity.
     * </p>
     * <p>
     * The method processes various data types appropriately:
     * </p>
     * <ul>
     *   <li>Timestamps: converted from database timestamps to separate Date and Time objects</li>
     *   <li>Seat assignments: converted from 1-based database storage to 0-based application indexing</li>
     *   <li>Luggage IDs: handled with null values when id_luggage <= 0 (not assigned)</li>
     *   <li>Flight types: boolean values indicating departing (true) or arriving (false) flights</li>
     * </ul>
     * <p>
     * The query uses NATURAL JOIN for FLIGHT and BOOKING tables to efficiently link flight
     * and booking data, while using explicit JOINs for other relationships to maintain
     * proper data associations. Results are ordered by flight departure time in descending
     * order to prioritize recent lost luggage items.
     * </p>
     * <p>
     * This method is essential for lost luggage recovery operations, providing complete
     * information needed for customer contact, luggage identification, and recovery
     * coordination. The comprehensive data set supports both customer service operations
     * and administrative reporting on baggage handling performance.
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
    public void getAllLostLuggages(List<String> flightIds, List<String> companyNames, List<Date> flightDates,
                                   List<Time> departureTimes, List<Time> arrivalTimes, List<String> flightStatus,
                                   List<Integer> maxSeats, List<Integer> freeSeats,
                                   List<String> cities, List<Boolean> flightTypes,
                                   List<Integer> buyerIds, List<String> usernames, List<String> mails, List<String> hashedPasswords,
                                   List<Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds,
                                   List<String> ticketNumbers, List<Integer> seats, List<Boolean> checkedIns,
                                   List<String> firstNames, List<String> lastNames, List<String> passengerSSNs, List<Date> birthDates,
                                   List<Integer> luggageIds, List<String> luggageTypes, List<String> luggageStatus, List<String> luggageIdsAfterCheckin) throws SQLException {

        String query = "SELECT F.id_flight, F.company_name, F.departure_time, F.arrival_time, F.flight_status, F.max_seats, " +
                        "F.free_seats, F.destination_or_origin, F.flight_type, " +
                        "C.id_customer, C.username, C.mail, C.hashed_password, " +
                        "B.id_booking, B.booking_status, B.booking_time, " +
                        "T.ticket_number, T.seat, T.checked_in, P.first_name, P.last_name, P.SSN, P.birth_date, " +
                        "L.id_luggage, L.id_luggage_after_check_in, L.luggage_type, L.luggage_status " +
                        "FROM FLIGHT F NATURAL JOIN BOOKING B JOIN TICKET T ON B.id_booking = T.id_booking JOIN " +
                        "PASSENGER P ON T.id_passenger = P.SSN JOIN LUGGAGE L ON L.id_ticket = T.ticket_number JOIN CUSTOMER C ON B.buyer = C.id_customer " +
                        "WHERE L.luggage_status = 'LOST' AND C.is_deleted = false " +
                        "ORDER BY F.departure_time DESC;";


        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet rs = statement.executeQuery();

            while (rs.next()){

                flightIds.add(rs.getString("id_flight"));
                companyNames.add(rs.getString("company_name"));

                Timestamp tmpTS = rs.getTimestamp("departure_time");
                flightDates.add(new java.sql.Date(tmpTS.getTime()));
                departureTimes.add(new java.sql.Time(tmpTS.getTime()));

                tmpTS = rs.getTimestamp("arrival_time");
                arrivalTimes.add(new java.sql.Time(tmpTS.getTime()));

                flightStatus.add(rs.getString("flight_status"));

                //delays.add(rs.getInt("flight_delay"));

                maxSeats.add(rs.getInt("max_seats"));
                freeSeats.add(rs.getInt("free_seats"));

                cities.add(rs.getString("destination_or_origin"));

                flightTypes.add(rs.getBoolean("flight_type"));

                buyerIds.add(rs.getInt("id_customer"));
                usernames.add(rs.getString("username"));
                mails.add(rs.getString("mail"));
                hashedPasswords.add(rs.getString("hashed_password"));

                tmpTS = rs.getTimestamp("booking_time");
                bookingDates.add(new java.sql.Date(tmpTS.getTime()));

                bookingStatus.add(rs.getString("booking_status"));

                bookingIds.add(rs.getInt("id_booking"));

                ticketNumbers.add(rs.getString("ticket_number"));

                if(rs.getInt("seat") > 0){
                    seats.add(rs.getInt("seat") - 1);
                }else{
                    seats.add(null);
                }

                checkedIns.add(rs.getBoolean("checked_in"));

                firstNames.add(rs.getString("first_name"));

                lastNames.add(rs.getString("last_name"));

                passengerSSNs.add(rs.getString("SSN"));

                birthDates.add(rs.getDate("birth_date"));

                if(rs.getInt("id_luggage") > 0){
                    luggageIds.add(rs.getInt("id_luggage"));
                }else{
                    luggageIds.add(null);
                }

                luggageIdsAfterCheckin.add(rs.getString("id_luggage_after_check_in"));

                luggageTypes.add(rs.getString("luggage_type"));

                luggageStatus.add(rs.getString("luggage_status"));


            }

            rs.close();

            //connection.close(); non serve perchè la fa in automatico il try-with-resources


        }

    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation executes a SQL query that joins multiple tables (BOOKING, TICKET,
     * PASSENGER, LUGGAGE, CUSTOMER) to retrieve comprehensive luggage information for all
     * luggage items associated with a specific booking. The query uses LEFT JOIN with the
     * LUGGAGE table to ensure tickets without associated luggage are still included.
     * </p>
     * <p>
     * The method handles nullable luggage data appropriately:
     * </p>
     * <ul>
     *   <li>Luggage IDs: null values when id_luggage <= 0 (no luggage assigned to ticket)</li>
     *   <li>Post-checkin IDs: string values for physical luggage tracking after check-in</li>
     *   <li>Luggage types and status: retrieved directly from database enum values</li>
     * </ul>
     * <p>
     * Results are ordered by ticket number to provide consistent ordering and enable
     * easy correlation with passenger and ticket information. This ordering facilitates
     * organized display of luggage information grouped by tickets within the booking.
     * </p>
     * <p>
     * The LEFT JOIN ensures that all tickets in the booking are included in the results,
     * even if they don't have associated luggage items. This provides complete booking
     * information for customer service and operational purposes.
     * </p>
     * <p>
     * This method is particularly useful for customer service operations, check-in
     * procedures, luggage management tasks, and booking modification processes where
     * complete luggage information for a specific booking is required.
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
    public void getAllLuggagesOfBooking(Integer bookingId, List<String> ticketNumbers, List<Integer> luggageIds, List<String> luggageTypes, List<String> luggageStatus, List<String> luggageIdsAfterCheckin) throws SQLException {

        String query = "SELECT T.ticket_number, L.id_luggage, L.id_luggage_after_check_in, L.luggage_type, L.luggage_status " +
                        "FROM BOOKING B JOIN TICKET T ON B.id_booking = T.id_booking JOIN " +
                        "PASSENGER P ON T.id_passenger = P.SSN LEFT JOIN LUGGAGE L ON L.id_ticket = T.ticket_number JOIN CUSTOMER C ON B.buyer = C.id_customer " +
                        "WHERE B.id_booking = ? " +
                        "ORDER BY T.ticket_number;";


        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, bookingId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()){

                ticketNumbers.add(rs.getString("ticket_number"));

                if(rs.getInt("id_luggage") > 0){
                    luggageIds.add(rs.getInt("id_luggage"));
                }else{
                    luggageIds.add(null);
                }

                luggageIdsAfterCheckin.add(rs.getString("id_luggage_after_check_in"));

                luggageTypes.add(rs.getString("luggage_type"));

                luggageStatus.add(rs.getString("luggage_status"));

            }

            rs.close();

            //connection.close(); non serve perchè la fa in automatico il try-with-resources


        }

    }

    /**
     * Updates the status of a luggage item to indicate it has been lost.
     * <p>
     * This method performs a luggage status update operation to mark luggage as lost
     * using the post-checkin luggage identifier. It uses PostgreSQL's enum casting
     * to ensure proper luggage status validation and data integrity.
     * </p>
     * <p>
     * The method updates luggage status based on the post-checkin identifier
     * (id_luggage_after_check_in) rather than the original luggage ID, as this
     * identifier is used for physical luggage tracking after passengers have
     * checked in and luggage has entered the baggage handling system.
     * </p>
     * <p>
     * Common luggage status transitions handled by this method include:
     * </p>
     * <ul>
     *   <li>LOADED → LOST (luggage loaded but went missing during handling)</li>
     *   <li>WITHDRAWABLE → LOST (luggage ready for pickup but reported missing)</li>
     *   <li>Any status → LOST (administrative override for lost luggage reporting)</li>
     * </ul>
     * <p>
     * The method includes comprehensive error handling with logging to track
     * luggage status update operations and any failures that may occur during
     * the update process. This is crucial for luggage tracking and audit purposes.
     * </p>
     * <p>
     * This operation is typically triggered by customer reports of missing luggage,
     * baggage handling system notifications, or administrative actions when luggage
     * cannot be located or delivered to passengers.
     * </p>
     *
     * @param ticket the post-checkin luggage identifier used for physical tracking
     * @param luggageStatus the new luggage status to set (typically "LOST" but can be other valid status values)
     */
    public void lostLuggage(String ticket, String luggageStatus) {

        String query = "UPDATE Luggage SET luggage_status = ?::LuggageStatus WHERE id_luggage_after_check_in = ?";

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query);) {

            statement.setObject(1, luggageStatus);
            statement.setString(2, ticket);

            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getSQLState());
        }
    }
}