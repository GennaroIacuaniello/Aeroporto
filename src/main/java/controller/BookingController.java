package controller;

import dao.BookingDAO;
import gui.FloatingMessage;
import implementazioniPostgresDAO.BookingDAOImpl;
import model.*;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class BookingController {
    private Booking booking;

    public BookingController() {}


    public void getAllBooksLoogedCustomer(List<Date> bookingDates, List<Integer> numPassengers, List<String> ids) {

        ArrayList<Boolean> types = new ArrayList<>();
        ArrayList<Flight>  searchBookingFlightsResult = new ArrayList<>(0);

        ArrayList<String> companyNames = new ArrayList<>();
        ArrayList<Date> dates = new ArrayList<>();
        ArrayList<Time> departureTimes = new ArrayList<>();
        ArrayList<Time> arrivalTimes = new ArrayList<>();
        ArrayList<Integer> delays = new ArrayList<>();
        ArrayList<String> status = new ArrayList<>();
        ArrayList<Integer> maxSeats = new ArrayList<>();
        ArrayList<Integer> freeSeats = new ArrayList<>();
        ArrayList<String> cities = new ArrayList<>();

        try{
            BookingDAO bookingDAO = new BookingDAOImpl();

            flightDAO.searchFlight(departingCity, arrivingCity, initialDate, finalDate, initialTime, finalTime, ids, companyNames,
                    dates, departureTimes, arrivalTimes, delays, status, maxSeats, freeSeats, cities, types);



        } catch (SQLException e) {
            new FloatingMessage("Errore nella connessione al Database!", searchButton, FloatingMessage.ERROR_MESSAGE);
        }

        ArrayList<String> actualIds = new ArrayList<>();

        for(int i = 0; i < ids.size(); i++){

            if(types.get(i)){   //alloco Departing

                searchResult.add(new Departing( ids.get(i), companyNames.get(i), dates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                        FlightStatus.valueOf(status.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i), delays.get(i)));

            }else{              //alloco Arriving

                searchResult.add(new Arriving( ids.get(i), companyNames.get(i), dates.get(i), departureTimes.get(i), arrivalTimes.get(i),
                        FlightStatus.valueOf(status.get(i).toUpperCase()), maxSeats.get(i), freeSeats.get(i), cities.get(i), delays.get(i)));


            }

        }

    }







    public void setBooking(Customer customer, Flight flight, ArrayList<Ticket> tickets) {
        try {
            booking = new Booking(customer, flight, new Date(10, 0, 0), tickets);
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
}
