package controller;

import implementazioni_postgres_dao.BookingDAOImpl;
import model.*;

import java.sql.Date;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class BookingController {

    private Booking booking;
    private Integer id;
    private ArrayList<Booking> searchBookingResult;
    private ArrayList<Integer> searchBookingResultIds;

    public void setBooking(Customer customer, Flight flight, List<Ticket> tickets) throws InvalidPassengerNumber, InvalidBuyer, InvalidFlight {
        try {
            booking = new Booking(customer, flight, new Date(10, 0, 0), (ArrayList<Ticket>) tickets);
        } catch (InvalidPassengerNumber e) {
            throw new InvalidPassengerNumber("Invalid passenger number");
        } catch (InvalidBuyer e) {
            throw new InvalidBuyer("Invalid buyer");
        } catch (InvalidFlight e) {
            throw new InvalidFlight("Invalid flight");
        }
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Booking getBooking() {
        return booking;
    }

    public List<Passenger> getPassengers() {

        ArrayList<Passenger> passengers = new ArrayList<>();

        for (Ticket ticket : booking.getTickets()) {

            passengers.add(ticket.getPassenger());
        }

        return passengers;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public int getTicketsSize() {
        return booking.getTickets().size();
    }

    public Passenger getPassenger(int index) {
        return booking.getTickets().get(index).getPassenger();
    }

    public String getPassengerName(int index) {
        return getPassenger(index).getFirstName();
    }

    public String getPassengerLastName(int index) {
        return getPassenger(index).getLastName();
    }

    public String getPassengerSSN(int index) {
        return getPassenger(index).getPassengerSSN();
    }

    public String getPassengerTicketNumber(int index) {
        return getTicket(index).getTicketNumber();
    }

    public int getPassengerSeat (int index) {
        return getTicket(index).getSeat();
    }

    public List<Luggage> getPassengerLuggages (int index) {
        return getTicket(index).getLuggages();
    }

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

    public List<String> getPassengerLuggagesTickets (int index) {
        ArrayList<String> tickets = new ArrayList<>();

        for (Luggage luggage : getPassengerLuggages(index)) tickets.add(luggage.getId());

        return tickets;
    }

    public List<String> getPassengerLuggagesStatus (int index) {
        ArrayList<String> status = new ArrayList<>();

        for (Luggage luggage : getPassengerLuggages(index)) status.add(luggage.getStatus().name());

        return status;
    }

    public boolean checkPendingButton () {
        return this.booking == null || this.booking.getStatus() == BookingStatus.PENDING;
    }

    public BookingStatus getBookingStatus() {
        return this.booking.getStatus();
    }

    public void deleteBooking() {

        try {
            BookingDAOImpl bookingDAO = new BookingDAOImpl();

            bookingDAO.deleteBooking(id);
        } catch (SQLException e) {

            Controller.getLogger().log(Level.SEVERE, e.getSQLState());

        }
    }

    public Ticket getTicket(int index) {
        return booking.getTickets().get(index);
    }

    public Date getPassengerDate(int index) {
        return getPassenger(index).getBirthDate();
    }

    public List<Booking> getSearchBookingResult() {
        return searchBookingResult;
    }

    public void setSearchBookingResult(List<Booking> searchBookingResult) {
        this.searchBookingResult = (ArrayList<Booking>) searchBookingResult;
    }

    public List<Integer> getSearchBookingResultIds() {
        return searchBookingResultIds;
    }

    public void setSearchBookingResultIds(List<Integer> searchBookingResultIds) {
        this.searchBookingResultIds = (ArrayList<Integer>) searchBookingResultIds;
    }

    public void setBookingResultSelectedBooking(Integer index) {

        this.booking = searchBookingResult.get(index);
        this.id = searchBookingResultIds.get(index);

    }

    public List<Date> getSearchBookingResultDates() {

        ArrayList<Date> bookingDates = new ArrayList<>();

        for(Booking b: searchBookingResult){
            bookingDates.add(b.getBookingDate());
        }

        return bookingDates;

    }

    public List<String> getSearchBookingResultStatus() {

        ArrayList<String> bookingStatus = new ArrayList<>();

        for(Booking b: searchBookingResult){
            bookingStatus.add(b.getStatus().toString());
        }

        return bookingStatus;

    }

    public Booking getSearchBookingResultBooksById(Integer id) {
        for(int i = 0; i < searchBookingResult.size(); i++){

            if(searchBookingResultIds.get(i).equals(id)){
                return searchBookingResult.get(i);
            }

        }
        return null;
    }
}
