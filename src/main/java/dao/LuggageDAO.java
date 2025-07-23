package dao;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

/**
 * The interface Luggage dao.
 */
public interface LuggageDAO {


    /**
     * Gets all lost luggages.
     *
     * @param flightIds              the flight ids
     * @param companyNames           the company names
     * @param flightDates            the flight dates
     * @param departureTimes         the departure times
     * @param arrivalTimes           the arrival times
     * @param flightStatus           the flight status
     * @param maxSeats               the max seats
     * @param freeSeats              the free seats
     * @param cities                 the cities
     * @param flightTypes            the flight types
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
    void getAllLostLuggages(List<String> flightIds, List<String> companyNames, List<Date> flightDates,
                            List<Time> departureTimes, List<Time> arrivalTimes, List<String> flightStatus,
                            List<Integer> maxSeats, List<Integer> freeSeats,
                            List<String> cities, List<Boolean> flightTypes,
                            List<Integer> buyerIds, List<String> usernames, List<String> mails, List<String> hashedPasswords,
                            List<Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds,
                            List<String> ticketNumbers, List<Integer> seats, List<Boolean> checkedIns,
                            List<String> firstNames, List<String> lastNames, List<String> passengerSSNs, List<Date> birthDates,
                            List<Integer> luggageIds, List<String> luggageTypes, List<String> luggageStatus, List<String> luggageIdsAfterCheckin) throws SQLException;

    /**
     * Gets all luggages of booking.
     *
     * @param bookingId              the booking id
     * @param ticketNumbers          the ticket numbers
     * @param luggageIds             the luggage ids
     * @param luggageTypes           the luggage types
     * @param luggageStatus          the luggage status
     * @param luggageIdsAfterCheckin the luggage ids after checkin
     * @throws SQLException the sql exception
     */
    void getAllLuggagesOfBooking(Integer bookingId, List<String> ticketNumbers, List<Integer> luggageIds, List<String> luggageTypes, List<String> luggageStatus, List<String> luggageIdsAfterCheckin) throws SQLException;
}
