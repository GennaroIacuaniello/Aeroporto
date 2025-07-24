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
     *
     * @param ticket the {@link Ticket} object to set as the current session
     */
    public void setTicket (Ticket ticket) {
        this.ticket = ticket;
    }

    /**
     * Retrieves the unique ticket number of the currently active ticket.
     *
     * @return the unique identifier string for the current ticket
     */
    public String getTicketNumber () {
        return ticket.getTicketNumber();
    }

    /**
     * Retrieves the seat assignment of the currently active ticket.
     *
     * @return the seat assignment for the current ticket, or null if no seat is assigned
     */
    public Integer getSeat () {
        return ticket.getSeat();
    }

    /**
     * Retrieves the flight associated with the currently active ticket.
     *
     * @return the {@link Flight} object associated with the current ticket
     */
    public Flight getFlight  () {
        return ticket.getFlight();
    }

    /**
     * Retrieves the passenger associated with the currently active ticket.
     *
     * @return the {@link Passenger} object associated with the current ticket
     */
    public Passenger getPassenger () {
        return ticket.getPassenger();
    }

    /**
     * Retrieves the booking associated with the currently active ticket.
     *
     * @return the {@link Booking} object associated with the current ticket
     */
    public Booking getBooking () {
        return ticket.getBooking();
    }

    /**
     * Retrieves the first name of the passenger associated with the currently active ticket.
     *
     * @return the first name of the passenger associated with the current ticket
     */
    public String getFirstName () {
        return ticket.getPassenger().getFirstName();
    }

    /**
     * Retrieves the last name of the passenger associated with the currently active ticket.
     *
     * @return the last name of the passenger associated with the current ticket
     */
    public String getLastName () {
        return ticket.getPassenger().getLastName();
    }

    /**
     * Retrieves the SSN (Social Security Number) of the passenger associated with the currently active ticket.
     *
     * @return the SSN of the passenger associated with the current ticket
     */
    public String getSSN () {
        return ticket.getPassenger().getPassengerSSN();
    }

    /**
     * Retrieves the collection of ticket objects from search results.
     *
     * @return list of {@link Ticket} objects from search operations, or null if no results are available
     */
    public List<Ticket> getSearchBookingResult() {
        return searchBookingResult;
    }

    /**
     * Sets the collection of ticket objects for search results.
     *
     * @param searchBookingResult list of {@link Ticket} objects to set as search results
     */
    public void setSearchBookingResult(List<Ticket> searchBookingResult) {
        this.searchBookingResult = (ArrayList<Ticket>) searchBookingResult;
    }

    /**
     * Retrieves a specific ticket from search results by its ticket number.
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