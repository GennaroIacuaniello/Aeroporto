package dao;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public interface BookingDAO {

    void addBooking (int idCustomer, String idFlight, String bookingStatus, List<String> ticketNumbers, List<Integer> seats, List<String> firstNames,
                     List<String> lastNames, List<Date> birthDates, List<String> passengerSSNs, List<String> luggagesTypes, List<String> ticketForLuggages) throws SQLException;


    void searchBooksCustomerForAFlight(String flightId, Integer loggedCustomerId,
                                              List<Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds) throws SQLException;

    void getAllBooksCustomer(Integer loggedCustomerId, List<String> flightIds, ArrayList<String> companyNames, List<Date> flightDates,
                             List<Time> departureTimes, List<Time> arrivalTimes,
                             List<String> flightStatus, List<Integer> maxSeats, List<Integer> freeSeats,
                             List<String> cities, List<Boolean> types,
                             List<Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds) throws SQLException;

    void searchBooksCustomerFilteredFlights(String departingCity, String arrivingCity, LocalDate initialDate, LocalDate finalDate, LocalTime initialTime, LocalTime finalTime,
                                            Integer loggedCustomerId, List<String> flightIds, List<String> companyNames, List<Date> flightDates, List<Time> departureTimes, List<Time> arrivalTimes,
                                            List<String> flightStatus, List<Integer> maxSeats, List<Integer> freeSeats, List<String> cities, List<Boolean> types,
                                            List<Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds) throws SQLException;

    void searchBooksCustomerFilteredPassengers(String firstName, String lastName, String passengerSSN, String ticketNumber,
                                               Integer loggedCustomerId, List<String> flightIds, List<String> companyNames, List<Date> flightDates,
                                               List<Time> departureTimes, List<Time> arrivalTimes, List<String> flightStatus,
                                               List<Integer> maxSeats, List<Integer> freeSeats, List<String> cities, List<Boolean> types,
                                               List<Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds) throws SQLException;

}

