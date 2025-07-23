package controller;

import implementazioni_postgres_dao.BookingDAOImpl;
import model.*;

import java.sql.Date;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * The type Booking controller.
 */
public class BookingController {

    private Booking booking;
    private Integer id;
    private ArrayList<Booking> searchBookingResult;
    private ArrayList<Integer> searchBookingResultIds;

    /**
     * Sets booking.
     *
     * @param customer the customer
     * @param flight   the flight
     * @param tickets  the tickets
     * @throws InvalidPassengerNumber the invalid passenger number
     * @throws InvalidBuyer           the invalid buyer
     * @throws InvalidFlight          the invalid flight
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
     * Sets booking.
     *
     * @param booking the booking
     */
    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    /**
     * Gets booking.
     *
     * @return the booking
     */
    public Booking getBooking() {
        return booking;
    }

    /**
     * Gets passengers.
     *
     * @return the passengers
     */
    public List<Passenger> getPassengers() {

        ArrayList<Passenger> passengers = new ArrayList<>();

        for (Ticket ticket : booking.getTickets()) {

            passengers.add(ticket.getPassenger());
        }

        return passengers;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Gets tickets size.
     *
     * @return the tickets size
     */
    public int getTicketsSize() {
        return booking.getTickets().size();
    }

    /**
     * Gets passenger.
     *
     * @param index the index
     * @return the passenger
     */
    public Passenger getPassenger(int index) {
        return booking.getTickets().get(index).getPassenger();
    }

    /**
     * Gets passenger name.
     *
     * @param index the index
     * @return the passenger name
     */
    public String getPassengerName(int index) {
        return getPassenger(index).getFirstName();
    }

    /**
     * Gets passenger last name.
     *
     * @param index the index
     * @return the passenger last name
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
