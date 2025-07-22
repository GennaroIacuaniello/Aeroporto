package dao;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Date;
import java.util.List;

public interface FlightDAO {

    void getImminentArrivingFlights (List<String> parId, List<String> parCompanyName, List<Date> parDate,
                                     List<Time> parDepartureTime, List<Time> parArrivalTime, List<String> parStatus,
                                     List<Integer> parMaxSeats, List<Integer> parFreeSeats, List<String> origin,
                                     List<Integer> delay, List<Integer> parGate) throws SQLException;

    void searchFlight (String departingCity, String arrivingCity, LocalDate initialDate, LocalDate finalDate, LocalTime initialTime, LocalTime finalTime,
                              List<String> ids, List<String> companyNames, List<java.sql.Date> dates, List<Time> departureTimes, List<Time> arrivalTimes,
                              List<Integer> delays, List<String> status, List<Integer> maxSeats, List<Integer> freeSeats, List<String> cities, List<Boolean> types) throws SQLException;


    void getAllDataForAFlight(String flightId, List<Integer> flightGates, List<Integer> buyerIds,
                              List<String> usernames, List<String> mails, List<String> hashedPasswords,
                              List<java.sql.Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds,
                              List<String> ticketNumbers, List<Integer> seats, List<Boolean> checkedIns,
                              List<String> firstNames, List<String> lastNames, List<String> passengerSSNs, List<java.sql.Date> birthDates,
                              List<Integer> luggageIds, List<String> luggageTypes, List<String> luggageStatus, List<String> luggageIdsAfterCheckin) throws SQLException;


    void InsertAFlight(String flightId, String companyName, Timestamp departureTimestamp, Timestamp arrivalTimestamp,
                       int maxSeats, String otherCity, boolean flightType) throws SQLException;

}

