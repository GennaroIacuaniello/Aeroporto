package controller;

import model.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for managing ticket operations and session state in the airport management system.
 * <p>
 * This class serves as the specialized controller for ticket-related operations within the
 * architecture of the airport management system. It maintains the current ticket session state,
 * manages ticket search results, and provides comprehensive methods for accessing ticket
 * information, passenger details, and ticket management operations.
 * </p>
 * <p>
 * The TicketController is responsible for:
 * </p>
 * <ul>
 *   <li>Managing the currently selected ticket session information</li>
 *   <li>Handling ticket search results and associated metadata</li>
 *   <li>Providing access to ticket details, passenger information, and travel data</li>
 *   <li>Managing ticket-related operations within booking contexts</li>
 *   <li>Coordinating ticket information retrieval for administrative and customer service functions</li>
 *   <li>Supporting ticket lookup and identification operations</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Ticket
 * @see Passenger
 * @see Flight
 * @see Booking
 * @see Luggage
 * @see Controller
 */
public class TicketController {

    /**
     * The currently active ticket session object containing complete ticket information
     * including passenger data, flight details, booking associations, and luggage information.
     */
    private Ticket ticket;
    
    /**
     * Collection of ticket objects returned from search operations.
     * Used for displaying ticket information and enabling administrative
     * operations on ticket data within various system contexts.
     */
    private ArrayList<Ticket> searchBookingResult;

    /**
     * Sets the current ticket session using an existing {@link Ticket} object.
     * <p>
     * This method establishes a ticket session by directly assigning a pre-constructed
     * {@link Ticket} object as the current active ticket. It is typically used when
     * a ticket object has already been created and validated, and needs to be set
     * as the active session for subsequent operations.
     * </p>
     *
     * @param ticket the {@link Ticket} object to set as the current session
     */
    public void setTicket (Ticket ticket) {
        this.ticket = ticket;
    }

    /**
     * Retrieves the unique ticket number of the currently active ticket.
     * <p>
     * This method returns the ticket number for the current ticket session, which
     * serves as the unique identifier for the ticket in the database and throughout
     * the system. The ticket number is used for database operations, correlation
     * with other data records, and ticket identification purposes.
     * </p>
     *
     * @return the unique identifier string for the current ticket
     */
    public String getTicketNumber () {
        return ticket.getTicketNumber();
    }

    /**
     * Retrieves the seat assignment of the currently active ticket.
     * <p>
     * This method returns the seat number assigned to the current ticket. Seat
     * assignments are managed using zero-based indexing consistent with the
     * application's seat management system. The method returns null if no seat
     * has been assigned to the ticket.
     * </p>
     *
     * @return the seat assignment for the current ticket, or null if no seat is assigned
     */
    public Integer getSeat () {
        return ticket.getSeat();
    }

    /**
     * Retrieves the flight associated with the currently active ticket.
     * <p>
     * This method returns the {@link Flight} object representing the flight for
     * which the current ticket is valid. The flight object contains complete
     * flight information including schedules, capacity, status, and operational
     * details.
     * </p>
     *
     * @return the {@link Flight} object associated with the current ticket
     */
    public Flight getFlight  () {
        return ticket.getFlight();
    }

    /**
     * Retrieves the passenger associated with the currently active ticket.
     * <p>
     * This method returns the {@link Passenger} object representing the person
     * who will travel using the current ticket. The passenger object contains
     * complete personal information, identification data, and travel documents.
     * </p>
     *
     * @return the {@link Passenger} object associated with the current ticket
     */
    public Passenger getPassenger () {
        return ticket.getPassenger();
    }

    /**
     * Retrieves the booking associated with the currently active ticket.
     * <p>
     * This method returns the {@link Booking} object that contains the current
     * ticket. The booking object provides access to booking-related information
     * including customer details, booking status, creation date, and other
     * tickets that may be part of the same reservation.
     * </p>
     *
     * @return the {@link Booking} object associated with the current ticket
     */
    public Booking getBooking () {
        return ticket.getBooking();
    }

    /**
     * Retrieves the first name of the passenger associated with the currently active ticket.
     * <p>
     * This convenience method provides direct access to the first name of the
     * passenger without requiring explicit navigation through the ticket-passenger
     * relationship. This simplifies code that needs passenger identification
     * information for display or processing purposes.
     * </p>
     *
     * @return the first name of the passenger associated with the current ticket
     */
    public String getFirstName () {
        return ticket.getPassenger().getFirstName();
    }

    /**
     * Retrieves the last name of the passenger associated with the currently active ticket.
     * <p>
     * This convenience method provides direct access to the surname of the
     * passenger without requiring explicit navigation through the ticket-passenger
     * relationship. This information is essential for passenger identification
     * and administrative operations that require complete passenger names.
     * </p>
     *
     * @return the last name of the passenger associated with the current ticket
     */
    public String getLastName () {
        return ticket.getPassenger().getLastName();
    }

    /**
     * Retrieves the SSN (Social Security Number) of the passenger associated with the currently active ticket.
     * <p>
     * This convenience method provides direct access to the unique identification
     * number of the passenger without requiring explicit navigation through the
     * ticket-passenger relationship. The SSN serves as the primary key for
     * passenger identification throughout the system.
     * </p>
     *
     * @return the SSN of the passenger associated with the current ticket
     */
    public String getSSN () {
        return ticket.getPassenger().getPassengerSSN();
    }

    /**
     * Retrieves the collection of ticket objects from search results.
     * <p>
     * This method returns the list of {@link Ticket} objects that were retrieved
     * from search operations, typically used for administrative functions, customer
     * service operations, or ticket management tasks where ticket information
     * needs to be displayed or processed.
     * </p>
     * <p>
     * The search results contain ticket objects with complete information including
     * ticket numbers, seat assignments, check-in status, and associations with
     * passengers, flights, bookings, and luggage, enabling comprehensive ticket
     * management and administrative support.
     * </p>
     * <p>
     * The returned collection is used by GUI components for displaying ticket
     * lists, administrative interfaces for ticket management, and business logic
     * operations that require access to multiple tickets from search operations.
     * </p>
     *
     * @return list of {@link Ticket} objects from search operations, or null if no results are available
     */
    public List<Ticket> getSearchBookingResult() {
        return searchBookingResult;
    }

    /**
     * Sets the collection of ticket objects for search results.
     * <p>
     * This method establishes the list of {@link Ticket} objects returned from
     * search operations, converting the input list to an ArrayList for internal
     * consistency and performance optimization. The search results are used for
     * administrative functions and ticket management operations.
     * </p>
     * <p>
     * The method ensures that ticket search results are properly stored and
     * available for subsequent retrieval and manipulation operations throughout
     * ticket management workflows and administrative interfaces.
     * </p>
     * <p>
     * This method is typically called by search operations in the main controller
     * or by administrative functions that need to populate ticket result sets
     * for display or processing purposes. The ticket data is organized to
     * support efficient access and correlation with related booking and flight
     * information.
     * </p>
     * <p>
     * The conversion to ArrayList ensures optimal performance for indexed access
     * operations while maintaining compatibility with the List interface for
     * broader system integration and GUI component support.
     * </p>
     *
     * @param searchBookingResult list of {@link Ticket} objects to set as search results
     */
    public void setSearchBookingResult(List<Ticket> searchBookingResult) {
        this.searchBookingResult = (ArrayList<Ticket>) searchBookingResult;
    }

    /**
     * Retrieves a specific ticket from search results by its ticket number.
     * <p>
     * This method searches through the ticket search results to find a ticket
     * with the specified ticket number, returning the corresponding {@link Ticket}
     * object if found. This enables efficient ticket retrieval based on ticket
     * numbers without requiring index-based access.
     * </p>
     * <p>
     * The method performs a linear search through the search results, comparing
     * ticket numbers to find the matching ticket. It maintains correlation
     * between ticket objects and their unique identifiers for accurate retrieval
     * operations within administrative and customer service contexts.
     * </p>
     * <p>
     * This functionality is essential for ticket lookup operations, customer
     * service inquiries, administrative functions that need to locate specific
     * tickets based on customer-provided ticket numbers, and operations that
     * require ticket correlation across different system components.
     * </p>
     * <p>
     * The method handles cases where the search results may be empty or null,
     * and where the specified ticket number may not exist in the current
     * search result set, returning null in such cases to indicate that no
     * matching ticket was found.
     * </p>
     *
     * @param ticketNumber the unique ticket number to search for
     * @return the {@link Ticket} object with the specified ticket number, or null if not found
     */
    public Ticket getSearchBookingResultTicketByTicketNumber(String ticketNumber) {

        for (Ticket value : searchBookingResult) {

            if (value.getTicketNumber().equals(ticketNumber)) {
                return value;
            }

        }
        return null;

    }
}