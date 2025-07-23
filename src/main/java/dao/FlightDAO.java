package dao;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Date;
import java.util.List;

/**
 * The interface Flight dao.
 */
public interface FlightDAO {

    /**
     * Gets imminent arriving flights.
     *
     * @param parId            the par id
     * @param parCompanyName   the par company name
     * @param parDate          the par date
     * @param parDepartureTime the par departure time
     * @param parArrivalTime   the par arrival time
     * @param parStatus        the par status
     * @param parMaxSeats      the par max seats
     * @param parFreeSeats     the par free seats
     * @param origin           the origin
     * @param delay            the delay
     * @param parGate          the par gate
     * @throws SQLException the sql exception
     */
    void getImminentArrivingFlights (List<String> parId, List<String> parCompanyName, List<Date> parDate,
                                     List<Time> parDepartureTime, List<Time> parArrivalTime, List<String> parStatus,
                                     List<Integer> parMaxSeats, List<Integer> parFreeSeats, List<String> origin,
                                     List<Integer> delay, List<Integer> parGate) throws SQLException;

    /**
     * Gets imminent departing flights.
     *
     * @param parId            the par id
     * @param parCompanyName   the par company name
     * @param parDate          the par date
     * @param parDepartureTime the par departure time
     * @param parArrivalTime   the par arrival time
     * @param parStatus        the par status
     * @param parMaxSeats      the par max seats
     * @param parFreeSeats     the par free seats
     * @param origin           the origin
     * @param delay            the delay
     * @param parGate          the par gate
     * @throws SQLException the sql exception
     */
    void getImminentDepartingFlights (List<String> parId, List<String> parCompanyName, List<Date> parDate,
                                             List<Time> parDepartureTime, List<Time> parArrivalTime, List<String> parStatus,
                                             List<Integer> parMaxSeats, List<Integer> parFreeSeats, List<String> origin,
                                             List<Integer> delay, List<Integer> parGate) throws SQLException;

    /**
     * Search flight.
     *
     * @param departingCity  the departing city
     * @param arrivingCity   the arriving city
     * @param initialDate    the initial date
     * @param finalDate      the final date
     * @param initialTime    the initial time
     * @param finalTime      the final time
     * @param ids            the ids
     * @param companyNames   the company names
     * @param dates          the dates
     * @param departureTimes the departure times
     * @param arrivalTimes   the arrival times
     * @param delays         the delays
     * @param status         the status
     * @param maxSeats       the max seats
     * @param freeSeats      the free seats
     * @param cities         the cities
     * @param types          the types
     * @throws SQLException the sql exception
     */
    void searchFlight (String departingCity, String arrivingCity, LocalDate initialDate, LocalDate finalDate, LocalTime initialTime, LocalTime finalTime,
                              List<String> ids, List<String> companyNames, List<java.sql.Date> dates, List<Time> departureTimes, List<Time> arrivalTimes,
                              List<Integer> delays, List<String> status, List<Integer> maxSeats, List<Integer> freeSeats, List<String> cities, List<Boolean> types) throws SQLException;


    /**
     * Gets all data for a flight.
     *
     * @param flightId               the flight id
     * @param flightGates            the flight gates
     * @param buyerIds               the buyer ids
     * @param usernames              the usernames
     * @param mails                  the mails
     * @param hashedPasswords        the hashed passwords
     * @param bookingDates           the booking dates
     * @param bookingStatus          the booking status
     * @param bookingIds             the booking ids
     * @param ticketNumbers          the ticket numbers
     * @param seats                  the seats
     * @param checkedIns             the checked ins
     * @param firstNames             the first names
     * @param lastNames              the last names
     * @param passengerSSNs          the passenger ss ns
     * @param birthDates             the birth dates
     * @param luggageIds             the luggage ids
     * @param luggageTypes           the luggage types
     * @param luggageStatus          the luggage status
     * @param luggageIdsAfterCheckin the luggage ids after checkin
     * @throws SQLException the sql exception
     */
    void getAllDataForAFlight(String flightId, List<Integer> flightGates, List<Integer> buyerIds,
                              List<String> usernames, List<String> mails, List<String> hashedPasswords,
                              List<java.sql.Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds,
                              List<String> ticketNumbers, List<Integer> seats, List<Boolean> checkedIns,
                              List<String> firstNames, List<String> lastNames, List<String> passengerSSNs, List<java.sql.Date> birthDates,
                              List<Integer> luggageIds, List<String> luggageTypes, List<String> luggageStatus, List<String> luggageIdsAfterCheckin) throws SQLException;


    /**
     * Insert a flight.
     *
     * @param flightId           the flight id
     * @param companyName        the company name
     * @param departureTimestamp the departure timestamp
     * @param arrivalTimestamp   the arrival timestamp
     * @param maxSeats           the max seats
     * @param otherCity          the other city
     * @param flightType         the flight type
     * @throws SQLException the sql exception
     */
    void InsertAFlight(String flightId, String companyName, Timestamp departureTimestamp, Timestamp arrivalTimestamp,
                       int maxSeats, String otherCity, boolean flightType) throws SQLException;

}

