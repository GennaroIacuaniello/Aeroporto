package dao;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The interface Booking dao.
 */
public interface BookingDAO {

    /**
     * Add booking.
     *
     * @param idCustomer        the id customer
     * @param idFlight          the id flight
     * @param bookingStatus     the booking status
     * @param ticketNumbers     the ticket numbers
     * @param seats             the seats
     * @param firstNames        the first names
     * @param lastNames         the last names
     * @param birthDates        the birth dates
     * @param passengerSSNs     the passenger ss ns
     * @param luggagesTypes     the luggages types
     * @param ticketForLuggages the ticket for luggages
     * @throws SQLException the sql exception
     */
    void addBooking (int idCustomer, String idFlight, String bookingStatus, List<String> ticketNumbers, List<Integer> seats, List<String> firstNames,
                     List<String> lastNames, List<Date> birthDates, List<String> passengerSSNs, List<String> luggagesTypes, List<String> ticketForLuggages) throws SQLException;


    /**
     * Search books customer for a flight.
     *
     * @param flightId         the flight id
     * @param loggedCustomerId the logged customer id
     * @param bookingDates     the booking dates
     * @param bookingStatus    the booking status
     * @param bookingIds       the booking ids
     * @throws SQLException the sql exception
     */
    void searchBooksCustomerForAFlight(String flightId, Integer loggedCustomerId,
                                              List<Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds) throws SQLException;

    /**
     * Gets all books customer.
     *
     * @param loggedCustomerId the logged customer id
     * @param flightIds        the flight ids
     * @param companyNames     the company names
     * @param flightDates      the flight dates
     * @param departureTimes   the departure times
     * @param arrivalTimes     the arrival times
     * @param flightStatus     the flight status
     * @param maxSeats         the max seats
     * @param freeSeats        the free seats
     * @param cities           the cities
     * @param types            the types
     * @param bookingDates     the booking dates
     * @param bookingStatus    the booking status
     * @param bookingIds       the booking ids
     * @throws SQLException the sql exception
     */
    void getAllBooksCustomer(Integer loggedCustomerId, List<String> flightIds, ArrayList<String> companyNames, List<Date> flightDates,
                             List<Time> departureTimes, List<Time> arrivalTimes,
                             List<String> flightStatus, List<Integer> maxSeats, List<Integer> freeSeats,
                             List<String> cities, List<Boolean> types,
                             List<Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds) throws SQLException;

    /**
     * Search books customer filtered flights.
     *
     * @param departingCity    the departing city
     * @param arrivingCity     the arriving city
     * @param initialDate      the initial date
     * @param finalDate        the final date
     * @param initialTime      the initial time
     * @param finalTime        the final time
     * @param loggedCustomerId the logged customer id
     * @param flightIds        the flight ids
     * @param companyNames     the company names
     * @param flightDates      the flight dates
     * @param departureTimes   the departure times
     * @param arrivalTimes     the arrival times
     * @param flightStatus     the flight status
     * @param maxSeats         the max seats
     * @param freeSeats        the free seats
     * @param cities           the cities
     * @param types            the types
     * @param bookingDates     the booking dates
     * @param bookingStatus    the booking status
     * @param bookingIds       the booking ids
     * @throws SQLException the sql exception
     */
    void searchBooksCustomerFilteredFlights(String departingCity, String arrivingCity, LocalDate initialDate, LocalDate finalDate, LocalTime initialTime, LocalTime finalTime,
                                            Integer loggedCustomerId, List<String> flightIds, List<String> companyNames, List<Date> flightDates, List<Time> departureTimes, List<Time> arrivalTimes,
                                            List<String> flightStatus, List<Integer> maxSeats, List<Integer> freeSeats, List<String> cities, List<Boolean> types,
                                            List<Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds) throws SQLException;

    /**
     * Search books customer filtered passengers.
     *
     * @param firstName        the first name
     * @param lastName         the last name
     * @param passengerSSN     the passenger ssn
     * @param ticketNumber     the ticket number
     * @param loggedCustomerId the logged customer id
     * @param flightIds        the flight ids
     * @param companyNames     the company names
     * @param flightDates      the flight dates
     * @param departureTimes   the departure times
     * @param arrivalTimes     the arrival times
     * @param flightStatus     the flight status
     * @param maxSeats         the max seats
     * @param freeSeats        the free seats
     * @param cities           the cities
     * @param types            the types
     * @param bookingDates     the booking dates
     * @param bookingStatus    the booking status
     * @param bookingIds       the booking ids
     * @throws SQLException the sql exception
     */
    void searchBooksCustomerFilteredPassengers(String firstName, String lastName, String passengerSSN, String ticketNumber,
                                               Integer loggedCustomerId, List<String> flightIds, List<String> companyNames, List<Date> flightDates,
                                               List<Time> departureTimes, List<Time> arrivalTimes, List<String> flightStatus,
                                               List<Integer> maxSeats, List<Integer> freeSeats, List<String> cities, List<Boolean> types,
                                               List<Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds) throws SQLException;

}

