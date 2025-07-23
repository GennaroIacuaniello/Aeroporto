package controller;

import implementazioni_postgres_dao.BookingDAOImpl;
import model.*;

import java.sql.Date;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * The BookingController class manages flight booking operations and related data.
 * This controller handles the creation, retrieval, and management of booking information,
 * including passenger details, ticket information, and booking status. It serves as an
 * intermediary between the user interface and the booking data model, providing methods
 * to access and manipulate booking information in a controlled manner.
 * 
 * The controller maintains both the current active booking and search results from
 * booking queries, allowing the application to display and interact with multiple
 * bookings while working with a specific selected booking.
 */
public class BookingController {

    /**
     * The currently active booking being managed by the controller.
     * This represents the booking that is currently being viewed, edited, or processed.
     * It contains all the information about the booking, including customer, flight, and tickets.
     */
    private Booking booking;

    /**
     * The database identifier of the currently active booking.
     * This ID corresponds to the booking's record in the database and is used
     * for database operations related to the booking.
     */
    private Integer id;

    /**
     * A collection of booking search results.
     * This list stores the results of booking search operations, allowing the application
     * to display multiple bookings and select one for detailed viewing or editing.
     */
    private ArrayList<Booking> searchBookingResult;

    /**
     * The database identifiers corresponding to the search result bookings.
     * This list maintains the database IDs for each booking in the searchBookingResult list,
     * allowing for efficient database operations on selected bookings.
     */
    private ArrayList<Integer> searchBookingResultIds;

    /**
     * Creates a new booking with the specified customer, flight, and tickets.
     * This method constructs a new Booking object with the provided parameters and sets it
     * as the currently active booking in the controller. It uses a default booking date
     * and validates the input parameters through the Booking constructor.
     *
     * @param customer the customer who is making the booking
     * @param flight   the flight being booked
     * @param tickets  the list of tickets associated with the booking, one for each passenger
     * @throws InvalidPassengerNumber if the number of passengers (tickets) is invalid
     * @throws InvalidBuyer           if the customer information is invalid or incomplete
     * @throws InvalidFlight          if the flight information is invalid or the flight cannot be booked
     */
    public void setBooking(Customer customer, Flight flight, List<Ticket> tickets) throws InvalidPassengerNumber, InvalidBuyer, InvalidFlight {
        try {
            booking = new Booking(customer, flight, new Date(10, 0, 0), tickets);
        } catch (InvalidPassengerNumber e) {
            throw new InvalidPassengerNumber("Invalid passenger number");
        } catch (InvalidBuyer e) {
            throw new InvalidBuyer("Invalid buyer");
        } catch (InvalidFlight e) {
            throw new InvalidFlight("Invalid flight");
        }
    }

    /**
     * Sets an existing booking as the currently active booking.
     * This method allows the controller to work with a pre-constructed Booking object,
     * typically used when loading an existing booking from the database or when
     * switching between different bookings in the user interface.
     *
     * @param booking the pre-constructed Booking object to set as the active booking
     */
    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    /**
     * Retrieves the currently active booking.
     * This method returns the Booking object that is currently being managed by the controller,
     * providing access to all booking details including customer information, flight details,
     * tickets, and booking status.
     *
     * @return the currently active Booking object, or null if no booking is set
     */
    public Booking getBooking() {
        return booking;
    }

    /**
     * Retrieves a list of all passengers in the current booking.
     * This method extracts passenger information from each ticket in the booking
     * and returns them as a consolidated list. This is useful for displaying
     * passenger information or performing operations on all passengers.
     *
     * @return a list of Passenger objects associated with the current booking
     * @throws NullPointerException if called when no booking is set or if the booking has no tickets
     */
    public List<Passenger> getPassengers() {
        ArrayList<Passenger> passengers = new ArrayList<>();

        for (Ticket ticket : booking.getTickets()) {
            passengers.add(ticket.getPassenger());
        }

        return passengers;
    }

    /**
     * Sets the database identifier for the currently active booking.
     * This method associates a database ID with the current booking, typically
     * after the booking has been saved to the database. The ID is used for
     * subsequent database operations such as updates or deletions.
     *
     * @param id the database identifier to associate with the current booking
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retrieves the database identifier of the currently active booking.
     * This method returns the ID that uniquely identifies the current booking
     * in the database, which can be used for database operations or to reference
     * the booking in other parts of the application.
     *
     * @return the database identifier of the current booking, or null if no ID is set
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Retrieves the number of tickets in the current booking.
     * This method returns the count of tickets associated with the current booking,
     * which corresponds to the number of passengers. This information is useful for
     * validating booking requirements, calculating costs, or displaying summary information.
     *
     * @return the number of tickets (passengers) in the current booking
     * @throws NullPointerException if called when no booking is set
     */
    public int getTicketsSize() {
        return booking.getTickets().size();
    }

    /**
     * Retrieves a specific passenger from the current booking by index.
     * This method provides access to a passenger at the specified position in the
     * booking's ticket list. It's useful for accessing individual passenger details
     * when displaying or processing passenger information.
     *
     * @param index the zero-based index of the passenger to retrieve
     * @return the Passenger object at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= getTicketsSize())
     * @throws NullPointerException if called when no booking is set
     */
    public Passenger getPassenger(int index) {
        return booking.getTickets().get(index).getPassenger();
    }

    /**
     * Retrieves the first name of a specific passenger from the current booking.
     * This method provides direct access to a passenger's first name without needing
     * to first retrieve the full Passenger object. It's a convenience method for
     * displaying or processing passenger names individually.
     *
     * @param index the zero-based index of the passenger whose name to retrieve
     * @return the first name of the passenger at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= getTicketsSize())
     * @throws NullPointerException if called when no booking is set
     */
    public String getPassengerName(int index) {
        return getPassenger(index).getFirstName();
    }

    /**
     * Retrieves the last name of a specific passenger from the current booking.
     * This method provides direct access to a passenger's last name without needing
     * to first retrieve the full Passenger object. It's a convenience method for
     * displaying or processing passenger surnames individually.
     *
     * @param index the zero-based index of the passenger whose last name to retrieve
     * @return the last name of the passenger at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= getTicketsSize())
     * @throws NullPointerException if called when no booking is set
     */
    public String getPassengerLastName(int index) {
        return getPassenger(index).getLastName();
    }

    /**
     * Gets passenger ssn.
     *
     * @param index the index
     * @return the passenger ssn
     */
    public String getPassengerSSN(int index) {
        return getPassenger(index).getPassengerSSN();
    }

    /**
     * Gets passenger ticket number.
     *
     * @param index the index
     * @return the passenger ticket number
     */
    public String getPassengerTicketNumber(int index) {
        return getTicket(index).getTicketNumber();
    }

    /**
     * Gets passenger seat.
     *
     * @param index the index
     * @return the passenger seat
     */
    public int getPassengerSeat (int index) {
        return getTicket(index).getSeat();
    }

    /**
     * Gets passenger luggages.
     *
     * @param index the index
     * @return the passenger luggages
     */
    public List<Luggage> getPassengerLuggages (int index) {
        return getTicket(index).getLuggages();
    }

    /**
     * Gets passenger luggages types.
     *
     * @param index the index
     * @return the passenger luggages types
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
     * Gets passenger luggages tickets.
     *
     * @param index the index
     * @return the passenger luggages tickets
     */
    public List<String> getPassengerLuggagesTickets (int index) {
        ArrayList<String> tickets = new ArrayList<>();

        for (Luggage luggage : getPassengerLuggages(index)) tickets.add(luggage.getId());

        return tickets;
    }

    /**
     * Gets passenger luggages status.
     *
     * @param index the index
     * @return the passenger luggages status
     */
    public List<String> getPassengerLuggagesStatus (int index) {
        ArrayList<String> status = new ArrayList<>();

        for (Luggage luggage : getPassengerLuggages(index)) status.add(luggage.getStatus().name());

        return status;
    }

    /**
     * Check pending button boolean.
     *
     * @return the boolean
     */
    public boolean checkPendingButton () {
        return this.booking == null || this.booking.getStatus() == BookingStatus.PENDING;
    }

    /**
     * Gets booking status.
     *
     * @return the booking status
     */
    public BookingStatus getBookingStatus() {
        return this.booking.getStatus();
    }

    /**
     * Delete booking.
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
     * Gets ticket.
     *
     * @param index the index
     * @return the ticket
     */
    public Ticket getTicket(int index) {
        return booking.getTickets().get(index);
    }

    /**
     * Gets passenger date.
     *
     * @param index the index
     * @return the passenger date
     */
    public Date getPassengerDate(int index) {
        return getPassenger(index).getBirthDate();
    }

    /**
     * Gets search booking result.
     *
     * @return the search booking result
     */
    public List<Booking> getSearchBookingResult() {
        return searchBookingResult;
    }

    /**
     * Sets search booking result.
     *
     * @param searchBookingResult the search booking result
     */
    public void setSearchBookingResult(List<Booking> searchBookingResult) {
        this.searchBookingResult = (ArrayList<Booking>) searchBookingResult;
    }

    /**
     * Gets search booking result ids.
     *
     * @return the search booking result ids
     */
    public List<Integer> getSearchBookingResultIds() {
        return searchBookingResultIds;
    }

    /**
     * Sets search booking result ids.
     *
     * @param searchBookingResultIds the search booking result ids
     */
    public void setSearchBookingResultIds(List<Integer> searchBookingResultIds) {
        this.searchBookingResultIds = (ArrayList<Integer>) searchBookingResultIds;
    }

    /**
     * Sets booking result selected booking.
     *
     * @param index the index
     */
    public void setBookingResultSelectedBooking(Integer index) {

        this.booking = searchBookingResult.get(index);
        this.id = searchBookingResultIds.get(index);

    }

    /**
     * Gets search booking result dates.
     *
     * @return the search booking result dates
     */
    public List<Date> getSearchBookingResultDates() {

        ArrayList<Date> bookingDates = new ArrayList<>();

        for(Booking b: searchBookingResult){
            bookingDates.add(b.getBookingDate());
        }

        return bookingDates;

    }

    /**
     * Gets search booking result status.
     *
     * @return the search booking result status
     */
    public List<String> getSearchBookingResultStatus() {

        ArrayList<String> bookingStatus = new ArrayList<>();

        for(Booking b: searchBookingResult){
            bookingStatus.add(b.getStatus().toString());
        }

        return bookingStatus;

    }

    /**
     * Gets search booking result books by id.
     *
     * @param id the id
     * @return the search booking result books by id
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
