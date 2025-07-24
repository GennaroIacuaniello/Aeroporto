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
     * <p>
     * This method establishes a booking session using a pre-constructed and validated
     * {@link Booking} object. It is typically used when a booking object has already
     * been created and needs to be set as the current session, such as when loading
     * existing bookings from search results or database retrieval operations.
     * </p>
     *
     * @param booking the {@link Booking} object to set as the current session
     */
    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    /**
     * Retrieves the currently active booking object.
     * <p>
     * This method returns the {@link Booking} object representing the current
     * booking session. The returned object contains complete booking information
     * including status, customer details, flight information, and all associated
     * tickets with passenger and luggage data.
     * </p>
     *
     * @return the {@link Booking} object for the current session, or null if no booking is active
     */
    public Booking getBooking() {
        return booking;
    }

    /**
     * Extracts and returns all passengers from the current booking.
     * <p>
     * This method processes all tickets in the current booking to extract the
     * associated passenger information, returning a consolidated list of all
     * passengers involved in the booking. This is useful for operations that
     * need to process all passengers collectively.
     * </p>
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
     * <p>
     * This method establishes the database ID for the current booking, enabling
     * database operations and correlation with search results. The ID is used
     * for update, deletion, and retrieval operations that require unique
     * booking identification.
     * </p>
     *
     * @param id the unique database identifier for the current booking
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retrieves the database identifier for the current booking session.
     * <p>
     * This method returns the unique database identifier for the current booking
     * session. The ID is used for database operations that require booking
     * identification, such as updating booking details, processing payments,
     * or associating additional services with the booking.
     * </p>
     *
     * @return the unique database identifier for the current booking session, or null if no booking is active
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Returns the number of tickets in the current booking.
     * <p>
     * This method provides a count of tickets associated with the current booking,
     * which corresponds to the number of passengers included in the reservation.
     * This information is essential for various operations including seat management,
     * passenger processing, and booking validation.
     * </p>
     *
     * @return the number of tickets in the current booking
     */
    public int getTicketsSize() {
        return booking.getTickets().size();
    }

    /**
     * Retrieves the passenger associated with a specific ticket in the current booking.
     * <p>
     * This method returns the {@link Passenger} object for the ticket at the specified
     * index within the current booking. It provides direct access to passenger
     * information including personal details, identification, and travel document data.
     * </p>
     *
     * @param index the zero-based index of the ticket/passenger within the booking
     * @return the {@link Passenger} object associated with the ticket at the specified index
     */
    public Passenger getPassenger(int index) {
        return booking.getTickets().get(index).getPassenger();
    }

    /**
     * Retrieves the first name of the passenger at the specified index.
     * <p>
     * This convenience method provides direct access to the first name of a
     * specific passenger within the current booking without requiring explicit
     * passenger object retrieval. It simplifies access to commonly needed
     * passenger information for display and processing purposes.
     * </p>
     *
     * @param index the zero-based index of the passenger within the booking
     * @return the first name of the passenger at the specified index
     */
    public String getPassengerName(int index) {
        return getPassenger(index).getFirstName();
    }

    /**
     * Retrieves the last name of the passenger at the specified index.
     * <p>
     * This convenience method provides direct access to the last name of a
     * specific passenger within the current booking. It simplifies passenger
     * name retrieval for display purposes, form population, and passenger
     * identification operations.
     * </p>
     *
     * @param index the zero-based index of the passenger within the booking
     * @return the last name of the passenger at the specified index
     */
    public String getPassengerLastName(int index) {
        return getPassenger(index).getLastName();
    }

    /**
     * Retrieves the SSN (Social Security Number) of the passenger at the specified index.
     * <p>
     * This method provides access to the passenger's unique identification number,
     * which serves as the primary key for passenger records in the system. The SSN
     * is used for passenger identification, duplicate prevention, and correlation
     * with government travel document requirements.
     * </p>
     *
     * @param index the zero-based index of the passenger within the booking
     * @return the SSN of the passenger at the specified index
     */
    public String getPassengerSSN(int index) {
        return getPassenger(index).getPassengerSSN();
    }

    /**
     * Retrieves the ticket number for the ticket at the specified index.
     * <p>
     * This method provides access to the unique ticket identifier for a specific
     * ticket within the current booking. Ticket numbers are used for check-in
     * operations, luggage association, boarding passes, and various airport
     * processing systems.
     * </p>
     *
     * @param index the zero-based index of the ticket within the booking
     * @return the unique ticket number for the ticket at the specified index
     */
    public String getPassengerTicketNumber(int index) {
        return getTicket(index).getTicketNumber();
    }

    /**
     * Retrieves the seat assignment for the ticket at the specified index.
     * <p>
     * This method returns the seat number assigned to a specific ticket within
     * the current booking. Seat assignments are managed using zero-based indexing
     * to maintain consistency with the application's seat management system.
     * </p>
     *
     * @param index the zero-based index of the ticket within the booking
     * @return the seat number assigned to the ticket, or -1 if no seat is assigned
     */
    public int getPassengerSeat (int index) {
        return getTicket(index).getSeat();
    }

    /**
     * Retrieves all luggage items associated with the ticket at the specified index.
     * <p>
     * This method returns a complete list of luggage items that have been associated
     * with a specific ticket within the current booking. This includes both carry-on
     * and checked luggage items, providing comprehensive luggage information for
     * the passenger.
     * </p>
     *
     * @param index the zero-based index of the ticket within the booking
     * @return list of {@link Luggage} objects associated with the ticket at the specified index
     */
    public List<Luggage> getPassengerLuggages (int index) {
        return getTicket(index).getLuggages();
    }

    /**
     * Retrieves luggage type indicators for all luggage associated with the specified ticket.
     * <p>
     * This method processes all luggage items associated with a specific ticket and
     * returns a list of integer codes representing the luggage types. The mapping
     * follows the convention: 0 for CARRY_ON luggage and 1 for CHECKED luggage.
     * </p>
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
     * <p>
     * This method processes all luggage items associated with a specific ticket and
     * returns a list of tracking identifiers used for luggage management and tracking
     * throughout the baggage handling process. These identifiers are typically assigned
     * during check-in and used for physical luggage tracking.
     * </p>
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
     * <p>
     * This method processes all luggage items associated with a specific ticket and
     * returns a list of status strings representing the current state of each luggage
     * item. Status values include states such as BOOKED, LOADED, WITHDRAWABLE, and LOST.
     * </p>
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
     * <p>
     * This method returns the {@link BookingStatus} enum value representing the
     * current state of the booking. Status values include PENDING, CONFIRMED,
     * CANCELLED, and other states that reflect the booking's position in the
     * reservation and travel lifecycle.
     * </p>
     *
     * @return the {@link BookingStatus} of the current booking
     */
    public BookingStatus getBookingStatus() {
        return this.booking.getStatus();
    }

    /**
     * Deletes the current booking from the database.
     * <p>
     * This method performs a booking deletion operation using the {@link BookingDAOImpl}
     * to set the current bookinStatus to CANCELLED. The deletion is performed
     * using the booking's database ID, ensuring precise targeting of the correct
     * booking record.
     * </p>
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
     * <p>
     * This method provides direct access to {@link Ticket} objects within the
     * current booking, enabling detailed ticket information retrieval and
     * manipulation. The ticket object contains complete information including
     * ticket number, seat assignment, check-in status, and associated passenger
     * and luggage data.
     * </p>
     *
     * @param index the zero-based index of the ticket within the booking
     * @return the {@link Ticket} object at the specified index
     */
    public Ticket getTicket(int index) {
        return booking.getTickets().get(index);
    }

    /**
     * Retrieves the birthdate of the passenger at the specified index.
     * <p>
     * This convenience method provides direct access to the birthdate of a
     * specific passenger within the current booking. Birthdate information
     * is essential for age verification, special service requirements, and
     * compliance with travel regulations.
     * </p>
     *
     * @param index the zero-based index of the passenger within the booking
     * @return the birthdate of the passenger at the specified index
     */
    public Date getPassengerDate(int index) {
        return getPassenger(index).getBirthDate();
    }

    /**
     * Retrieves the collection of booking search results.
     * <p>
     * This method returns the list of {@link Booking} objects that were retrieved
     * from search operations. The search results maintain synchronized indexing
     * with the corresponding database IDs, enabling efficient data correlation
     * and selection operations.
     * </p>
     *
     * @return list of {@link Booking} objects from search operations
     */
    public List<Booking> getSearchBookingResult() {
        return searchBookingResult;
    }

    /**
     * Sets the collection of booking search results.
     * <p>
     * This method establishes the list of {@link Booking} objects returned from
     * search operations, converting the input list to an ArrayList for internal
     * consistency and performance optimization. The search results are used for
     * displaying booking information and enabling user selection operations.
     * </p>
     *
     * @param searchBookingResult list of {@link Booking} objects to set as search results
     */
    public void setSearchBookingResult(List<Booking> searchBookingResult) {
        this.searchBookingResult = (ArrayList<Booking>) searchBookingResult;
    }

    /**
     * Retrieves the collection of database IDs corresponding to booking search results.
     * <p>
     * This method returns the list of database identifiers that correspond to the
     * booking objects in the search results. The IDs maintain synchronized indexing
     * with the booking objects, enabling efficient correlation between display
     * data and database operations.
     * </p>
     *
     * @return list of database identifiers corresponding to search result bookings
     */
    public List<Integer> getSearchBookingResultIds() {
        return searchBookingResultIds;
    }

    /**
     * Sets the collection of database IDs corresponding to booking search results.
     * <p>
     * This method establishes the list of database identifiers that correspond to
     * booking search results, converting the input list to an ArrayList for internal
     * consistency. The IDs must maintain synchronized indexing with the booking
     * objects to ensure proper correlation between display data and database operations.
     * </p>
     *
     * @param searchBookingResultIds list of database identifiers to set for search results
     */
    public void setSearchBookingResultIds(List<Integer> searchBookingResultIds) {
        this.searchBookingResultIds = (ArrayList<Integer>) searchBookingResultIds;
    }

    /**
     * Sets the active booking session based on a search result selection.
     * <p>
     * This method establishes a booking session by selecting a specific booking
     * from the search results based on the provided index. It sets both the
     * booking object and its corresponding database ID, creating a complete
     * session context for subsequent operations.
     * </p>
     *
     * @param index the zero-based index of the booking to select from search results
     */
    public void setBookingResultSelectedBooking(Integer index) {

        this.booking = searchBookingResult.get(index);
        this.id = searchBookingResultIds.get(index);

    }

    /**
     * Extracts and returns booking dates from all search result bookings.
     * <p>
     * This method processes all booking objects in the search results and extracts
     * their booking dates, returning a consolidated list for display and processing
     * purposes. The dates maintain correlation with the original booking order for
     * consistent data presentation.
     * </p>
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
     * <p>
     * This method processes all booking objects in the search results and extracts
     * their status values as strings, returning a consolidated list for display
     * and filtering purposes. The status values maintain correlation with the
     * original booking order for consistent data presentation.
     * </p>
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
     * <p>
     * This method searches through the booking search results to find a booking
     * with the specified database ID, returning the corresponding {@link Booking}
     * object if found. This enables efficient booking retrieval based on database
     * identifiers without requiring index-based access.
     * </p>
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