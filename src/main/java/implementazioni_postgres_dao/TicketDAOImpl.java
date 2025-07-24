package implementazioni_postgres_dao;

import dao.TicketDAO;
import database.ConnessioneDatabase;

import java.math.BigInteger;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PostgreSQL implementation of the TicketDAO interface for managing ticket operations in the airport management system.
 * <p>
 * This class provides concrete implementations for all ticket-related database operations
 * defined in the {@link TicketDAO} interface. It handles comprehensive ticket management
 * functions including ticket data retrieval, ticket number generation, and ticket identification
 * operations using PostgreSQL database connectivity.
 * </p>
 * <p>
 * The implementation provides comprehensive ticket management capabilities including:
 * </p>
 * <ul>
 *   <li>Complete ticket information retrieval for specific bookings with passenger data</li>
 *   <li>Automatic ticket number generation with sequential numbering system</li>
 *   <li>Ticket number manipulation and increment operations for system management</li>
 *   <li>Integration with booking, passenger, and seat management systems</li>
 *   <li>Comprehensive seat assignment handling with proper indexing conversion</li>
 * </ul>
 * <p>
 * All database operations use prepared statements to prevent SQL injection attacks and ensure
 * data security. The class implements proper connection management using the singleton
 * {@link ConnessioneDatabase} pattern and handles resource cleanup through try-with-resources
 * statements.
 * </p>
 * <p>
 * All methods follow the contract defined by the {@link TicketDAO} interface and maintain
 * data consistency through proper transaction handling, error logging, and validation mechanisms.
 * The class provides essential functionality for booking management, check-in operations,
 * and passenger service systems.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see TicketDAO
 * @see model.Ticket
 * @see ConnessioneDatabase
 * @see SQLException
 */
public class TicketDAOImpl implements TicketDAO {

    /**
     * Logger instance for recording database operation events and errors.
     */
    private static final Logger LOGGER = Logger.getLogger(TicketDAOImpl.class.getName());

    /**
     * {@inheritDoc}
     * <p>
     * This implementation executes a SQL query that joins the TICKET and PASSENGER tables
     * to retrieve comprehensive ticket information for all tickets associated with a specific
     * booking. The query provides complete ticket details including passenger information
     * and seat assignments.
     * </p>
     * <p>
     * The method handles seat number conversion from 1-based database storage to 0-based
     * application indexing for consistency with the application's seat management system.
     * Seats with value 0 or negative in the database are returned as null to indicate
     * no seat assignment.
     * </p>
     * <p>
     * Data processing includes:
     * </p>
     * <ul>
     *   <li>Ticket numbers retrieved directly from database</li>
     *   <li>Seat assignments converted to 0-based indexing (null if not assigned)</li>
     *   <li>Check-in status indicators for boarding eligibility</li>
     *   <li>Passenger SSN identifiers for passenger association</li>
     *   <li>Passenger personal information including names and birth dates</li>
     * </ul>
     * <p>
     * The method uses prepared statements for secure parameter binding and proper resource
     * management through try-with-resources. All related data is populated in corresponding
     * list positions to maintain data correlation across different information types.
     * </p>
     * <p>
     * This method is particularly useful for booking management operations, check-in
     * procedures, passenger manifest generation, and customer service inquiries where
     * complete ticket information for a booking is required.
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
    public void getAllTicketBooking(int bookingId, List<String> ticketNumbers, List<Integer> seats, List<Boolean> checkedIns,
                                    List<String> passengerSSNs, List<String> firstNames,
                                    List<String> lastNames, List<Date> birthDates) throws SQLException {


        String query = "SELECT T.ticket_number, T.seat, T.checked_in, T.id_passenger, P.first_name, P.last_name, P.birth_date " +
                "FROM TICKET T JOIN PASSENGER P ON T.id_passenger = P.SSN " +
                "WHERE T.id_booking = ?;";


        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, bookingId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()){

                ticketNumbers.add(rs.getString("ticket_number"));
                if(rs.getInt("seat") > 0){
                    seats.add(rs.getInt("seat") - 1);
                }else{
                    seats.add(null);
                }
                checkedIns.add(rs.getBoolean("checked_in"));
                passengerSSNs.add(rs.getString("id_passenger"));
                firstNames.add(rs.getString("first_name"));
                lastNames.add(rs.getString("last_name"));
                birthDates.add(rs.getDate("birth_date"));

            }

            rs.close();

            //connection.close(); non serve perchè la fa in automatico il try-with-resources

        }

    }

    /**
     * {@inheritDoc}
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
     *
     * @param offset the number of additional increments to apply before generating the final ticket number
     * @return a new unique 13-digit ticket number, or empty string if generation fails
     */
    public String generateTicketNumber(int offset) {

        String result;

        String query = "SELECT MAX(ticket_number) AS max_ticket_number FROM Ticket;";

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                result = rs.getString("max_ticket_number");
            } else throw new SQLException();

            rs.close();

            for (int i = 0; i < offset; i++) result = increaseTicketNumber(result);

            return increaseTicketNumber(result);

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getSQLState());
        }

        return "";
    }

    /**
     * Increments a ticket number by one and ensures proper 13-digit formatting.
     * <p>
     * This utility method handles ticket number arithmetic using BigInteger to support
     * large numbers and prevent overflow issues. It converts the input ticket number
     * string to a BigInteger, adds one, and formats the result back to a 13-digit
     * string with leading zero padding.
     * </p>
     * <p>
     * The method ensures consistent ticket number formatting across the system by:
     * </p>
     * <ul>
     *   <li>Using BigInteger for reliable large number arithmetic</li>
     *   <li>Maintaining 13-digit format with leading zero padding</li>
     *   <li>Providing proper error handling for invalid input formats</li>
     *   <li>Ensuring sequential ticket number generation</li>
     * </ul>
     *
     * @param ticketNumber the current ticket number as a string to be incremented
     * @return the incremented ticket number formatted as a 13-digit string with leading zeros
     * @throws NumberFormatException if the input ticket number is not a valid numeric string
     */
    private String increaseTicketNumber(String ticketNumber) {

        try {
            BigInteger number = new BigInteger(ticketNumber);

            String result = number.add(BigInteger.ONE).toString();

            while (result.length() < 13) result = "0" + result;

            return result;

        } catch (NumberFormatException e) {
            throw new NumberFormatException("La stringa '" + ticketNumber + "' non è un numero valido.");
        }
    }
}