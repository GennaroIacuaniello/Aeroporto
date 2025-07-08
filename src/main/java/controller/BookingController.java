package controller;

import model.*;

import java.util.ArrayList;

public class BookingController {
    private Booking booking;

    public BookingController() {}

    public void setBooking(Customer customer, Flight flight, ArrayList<Passenger> passengers) {
        try {
            booking = new Booking(customer, flight, passengers);
        } catch (InvalidPassengerNumber e) {
            throw new RuntimeException(e);
        }
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Booking getBooking() {
        return booking;
    }

    public ArrayList<Passenger> getPassengers() {
        return booking.get_passengers();
    }

    public int getPassengersSize() {
        return booking.get_passengers().size();
    }

    public Passenger getPassenger(int index) {
        return booking.get_passengers().get(index);
    }

    public String getPassengerName(int index) {
        return getPassenger(index).get_First_name();
    }

    public String getPassengerLastName(int index) {
        return getPassenger(index).get_Last_name();
    }

    public String getPassengerSSN(int index) {
        return getPassenger(index).get_SSN();
    }

    public String getPassengerTicketNumber(int index) {
        return getPassenger(index).get_Ticket_number();
    }

    public int getPassengerSeat (int index) {
        return getPassenger(index).get_Seat();
    }

    public ArrayList<Luggage> getPassengerLuggages (int index) {
        return getPassenger(index).get_Luggages();
    }

    public ArrayList<Integer> getPassengerLuggagesTypes (int index) {
        ArrayList<Integer> types = new ArrayList<Integer>();

        for (Luggage luggage : getPassengerLuggages(index)) {
            switch (luggage.get_type()) {
                case LuggageType.carry_on -> types.add(0);
                case LuggageType.checked -> types.add(1);
            }
        }

        return types;
    }

    public boolean checkPendingButton () {
        return this.booking == null || (this.booking != null && this.booking.get_status() == BookingStatus.pending);
    }

    public BookingStatus getBookingStatus() {
        return this.booking.get_status();
    }

    public void deleteBooking() {}
}
