package controller;

import model.*;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class BookingController {
    private Booking booking;

    public BookingController() {}

    public void setBooking(Customer customer, Flight flight, ArrayList<Ticket> tickets) {
        try {
            booking = new Booking(customer, flight, new Time(10, 0, 0), tickets);
        } catch (InvalidPassengerNumber e) {
            throw new RuntimeException("Invalid passenger number", e);
        } catch (InvalidBuyer e) {
            throw new RuntimeException("Invalid buyer", e);
        } catch (InvalidFlight e) {
            throw new RuntimeException("Invalid flight", e);
        }
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Booking getBooking() {
        return booking;
    }

    public ArrayList<Passenger> getPassengers() {

        ArrayList<Passenger> passengers = new ArrayList<Passenger>();

        for (Ticket ticket : booking.getTickets()) {

            passengers.add(ticket.getPassenger());
        }

        return passengers;
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
        return getPassenger(index).getSSN();
    }

    public String getPassengerTicketNumber(int index) {
        return getTicket(index).getTicketNumber();
    }

    public int getPassengerSeat (int index) {
        return getTicket(index).getSeat();
    }

    public ArrayList<Luggage> getPassengerLuggages (int index) {
        return getTicket(index).getLuggages();
    }

    public ArrayList<Integer> getPassengerLuggagesTypes (int index) {
        ArrayList<Integer> types = new ArrayList<Integer>();

        for (Luggage luggage : getPassengerLuggages(index)) {
            switch (luggage.getType()) {
                case LuggageType.CARRY_ON -> types.add(0);
                case LuggageType.CHECKED -> types.add(1);
            }
        }

        return types;
    }

    public boolean checkPendingButton () {
        return this.booking == null || this.booking.getStatus() == BookingStatus.PENDING;
    }

    public BookingStatus getBookingStatus() {
        return this.booking.getStatus();
    }

    public void deleteBooking() {}

    public Ticket getTicket(int index) {
        return booking.getTickets().get(index);
    }

    public Date getPassengerDate(int index) {
        return getPassenger(index).getBirthDate();
    }
}
