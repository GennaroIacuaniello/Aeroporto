package controller;

import implementazioni_postgres_dao.BookingDAOImpl;
import model.*;

import java.sql.Date;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Controller class for managing booking operations and session state in the airport management system.
 * <p>
 * This class serves as the specialized controller for booking-related operations within the MVC
 * architecture of the airport management system. It maintains the current booking session state,
 * manages search results, and provides comprehensive methods for accessing booking information,
 * passenger details, and ticket data.
 * </p>
 * <p>
 * The BookingController is responsible for:
 * </p>
 * <ul>
 *   <li>Managing the currently selected booking session information</li>
 *   <li>Handling booking search results and associated metadata</li>
 *   <li>Providing access to passenger and ticket information within bookings</li>
 *   <li>Managing luggage information associated with tickets</li>
 *   <li>Coordinating booking operations such as creation, modification, and deletion</li>
 *   <li>Supporting booking status management and validation</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Booking
 * @see Ticket
 * @see Passenger
 * @see Luggage
 * @see BookingDAOImpl
 * @see Controller
 */
public class BookingController {

    /**
     * The currently active booking session object containing complete booking information
     * including status, customer details, flight information, and associated tickets.
     */
    private Booking booking;
    
    /**
     * The unique database identifier for the currently active booking session.
     * Used for database operations and correlation with booking search results.
     */
    private Integer id;
    
    /**
     * Collection of booking objects returned from search operations.
     * Maintains synchronized indexing with searchBookingResultIds for efficient data correlation.
     */
    private ArrayList<Booking> searchBookingResult;
    
    /**
     * Collection of database identifiers corresponding to bookings in searchBookingResult.
     * Maintains synchronized indexing to enable efficient booking retrieval and selection.
     */
    private ArrayList<Integer> searchBookingResultIds;

    /**
     * Sets the booking session using an existing {@link Booking} object.
     *
     * @param booking the {@link Booking} object to set as the current session
     */
    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    /**
     * Retrieves the currently active booking object.
     *
     * @return the {@link Booking} object for the current session, or null if no booking is active
     */
    public Booking getBooking() {
        return booking;
    }

    /**
     * Extracts and returns all passengers from the current booking.
     *
     * @return list of {@link Passenger} objects representing all passengers in the current booking
     */
    public List<Passenger> getPassengers() {

        ArrayList<Passenger> passengers = new ArrayList<>();

        for (Ticket ticket : booking.getTickets()) {

            passengers.add(ticket.getPassenger());
        }

        return passengers;
    }

    /**
     * Sets the database identifier for the current booking session.
     *
     * @param id the unique database identifier for the current booking
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retrieves the database identifier for the current booking session.
     *
     * @return the unique database identifier for the current booking session, or null if no booking is active
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Returns the number of tickets in the current booking.
     *
     * @return the number of tickets in the current booking
     */
    public int getTicketsSize() {
        return booking.getTickets().size();
    }

    /**
     * Retrieves the passenger associated with a specific ticket in the current booking.
     *
     * @param index the zero-based index of the ticket/passenger within the booking
     * @return the {@link Passenger} object associated with the ticket at the specified index
     */
    public Passenger getPassenger(int index) {
        return booking.getTickets().get(index).getPassenger();
    }

    /**
     * Retrieves the first name of the passenger at the specified index.
     *
     * @param index the zero-based index of the passenger within the booking
     * @return the first name of the passenger at the specified index
     */
    public String getPassengerName(int index) {
        return getPassenger(index).getFirstName();
    }

    /**
     * Retrieves the last name of the passenger at the specified index.
     *
     * @param index the zero-based index of the passenger within the booking
     * @return the last name of the passenger at the specified index
     */
    public String getPassengerLastName(int index) {
        return getPassenger(index).getLastName();
    }

    /**
     * Retrieves the SSN (Social Security Number) of the passenger at the specified index.
     *
     * @param index the zero-based index of the passenger within the booking
     * @return the SSN of the passenger at the specified index
     */
    public String getPassengerSSN(int index) {
        return getPassenger(index).getPassengerSSN();
    }

    /**
     * Retrieves the ticket number for the ticket at the specified index.
     *
     * @param index the zero-based index of the ticket within the booking
     * @return the unique ticket number for the ticket at the specified index
     */
    public String getPassengerTicketNumber(int index) {
        return getTicket(index).getTicketNumber();
    }

    /**
     * Retrieves the seat assignment for the ticket at the specified index.
     *
     * @param index the zero-based index of the ticket within the booking
     * @return the seat number assigned to the ticket, or -1 if no seat is assigned
     */
    public int getPassengerSeat (int index) {
        return getTicket(index).getSeat();
    }

    /**
     * Retrieves all luggage items associated with the ticket at the specified index.
     *
     * @param index the zero-based index of the ticket within the booking
     * @return list of {@link Luggage} objects associated with the ticket at the specified index
     */
    public List<Luggage> getPassengerLuggages (int index) {
        return getTicket(index).getLuggages();
    }

    /**
     * Retrieves luggage type indicators for all luggage associated with the specified ticket.
     *
     * @param index the zero-based index of the ticket within the booking
     * @return list of integers representing luggage types (0 = CARRY_ON, 1 = CHECKED)
     */
    public List<Integer> getPassengerLuggagesTypes (int index) {

        ArrayList<Integer> types = new ArrayList<>();

        for (Luggage luggage : getPassengerLuggages(index)) {
            if(luggage.getType().equals(LuggageType.CARRY_ON)){
                types.add(0);
            }
            if(luggage.getType().equals(LuggageType.CHECKED)){
                types.add(1);
            }
        }

        return types;
    }

    /**
     * Retrieves luggage tracking identifiers for all luggage associated with the specified ticket.
     *
     * @param index the zero-based index of the ticket within the booking
     * @return list of luggage tracking identifiers for the ticket at the specified index
     */
    public List<String> getPassengerLuggagesTickets (int index) {
        ArrayList<String> tickets = new ArrayList<>();

        for (Luggage luggage : getPassengerLuggages(index)) tickets.add(luggage.getId());

        return tickets;
    }

    /**
     * Retrieves luggage status information for all luggage associated with the specified ticket.
     *
     * @param index the zero-based index of the ticket within the booking
     * @return list of luggage status strings for the ticket at the specified index
     */
    public List<String> getPassengerLuggagesStatus (int index) {
        ArrayList<String> status = new ArrayList<>();

        for (Luggage luggage : getPassengerLuggages(index)) status.add(luggage.getStatus().name());

        return status;
    }

    /**
     * Retrieves the current status of the active booking.
     *
     * @return the {@link BookingStatus} of the current booking
     */
    public BookingStatus getBookingStatus() {
        return this.booking.getStatus();
    }

    /**
     * Deletes the current booking from the database.
     *
     */
    public void deleteBooking() {

        try {
            BookingDAOImpl bookingDAO = new BookingDAOImpl();

            bookingDAO.deleteBooking(id);
        } catch (SQLException e) {

            Controller.getLogger().log(Level.SEVERE, e.getSQLState());

        }
    }

    /**
     * Retrieves the ticket object at the specified index within the current booking.
     *
     * @param index the zero-based index of the ticket within the booking
     * @return the {@link Ticket} object at the specified index
     */
    public Ticket getTicket(int index) {
        return booking.getTickets().get(index);
    }

    /**
     * Retrieves the birthdate of the passenger at the specified index.
     *
     * @param index the zero-based index of the passenger within the booking
     * @return the birthdate of the passenger at the specified index
     */
    public Date getPassengerDate(int index) {
        return getPassenger(index).getBirthDate();
    }

    /**
     * Retrieves the collection of booking search results.
     *
     * @return list of {@link Booking} objects from search operations
     */
    public List<Booking> getSearchBookingResult() {
        return searchBookingResult;
    }

    /**
     * Sets the collection of booking search results.
     *
     * @param searchBookingResult list of {@link Booking} objects to set as search results
     */
    public void setSearchBookingResult(List<Booking> searchBookingResult) {
        this.searchBookingResult = (ArrayList<Booking>) searchBookingResult;
    }

    /**
     * Retrieves the collection of database IDs corresponding to booking search results.
     *
     * @return list of database identifiers corresponding to search result bookings
     */
    public List<Integer> getSearchBookingResultIds() {
        return searchBookingResultIds;
    }

    /**
     * Sets the collection of database IDs corresponding to booking search results.
     *
     * @param searchBookingResultIds list of database identifiers to set for search results
     */
    public void setSearchBookingResultIds(List<Integer> searchBookingResultIds) {
        this.searchBookingResultIds = (ArrayList<Integer>) searchBookingResultIds;
    }

    /**
     * Sets the active booking session based on a search result selection.
     *
     * @param index the zero-based index of the booking to select from search results
     */
    public void setBookingResultSelectedBooking(Integer index) {

        this.booking = searchBookingResult.get(index);
        this.id = searchBookingResultIds.get(index);

    }

    /**
     * Extracts and returns booking dates from all search result bookings.
     *
     * @return list of booking dates from all search result bookings
     */
    public List<Date> getSearchBookingResultDates() {

        ArrayList<Date> bookingDates = new ArrayList<>();

        for(Booking b: searchBookingResult){
            bookingDates.add(b.getBookingDate());
        }

        return bookingDates;

    }

    /**
     * Extracts and returns booking status values from all search result bookings.
     *
     * @return list of booking status strings from all search result bookings
     */
    public List<String> getSearchBookingResultStatus() {

        ArrayList<String> bookingStatus = new ArrayList<>();

        for(Booking b: searchBookingResult){
            bookingStatus.add(b.getStatus().toString());
        }

        return bookingStatus;

    }

    /**
     * Retrieves a specific booking from search results by its database ID.
     *
     * @param id the database identifier of the booking to retrieve
     * @return the {@link Booking} object with the specified ID, or null if not found
     */
    public Booking getSearchBookingResultBooksById(Integer id) {
        for(int i = 0; i < searchBookingResult.size(); i++){

            if(searchBookingResultIds.get(i).equals(id)){
                return searchBookingResult.get(i);
            }

        }
        return null;
    }
}