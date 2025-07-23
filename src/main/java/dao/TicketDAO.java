package dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * The interface Ticket dao.
 */
public interface TicketDAO {

    /**
     * Gets all ticket booking.
     *
     * @param bookingId     the booking id
     * @param ticketNumbers the ticket numbers
     * @param seats         the seats
     * @param checkedIns    the checked ins
     * @param passengerSSNs the passenger ss ns
     * @param firstNames    the first names
     * @param lastNames     the last names
     * @param birthDates    the birth dates
     * @throws SQLException the sql exception
     */
    void getAllTicketBooking(int bookingId, List<String> ticketNumbers, List<Integer> seats, List<Boolean> checkedIns,
                                    List<String> passengerSSNs, List<String> firstNames,
                                    List<String> lastNames, List<Date> birthDates) throws SQLException;

}
