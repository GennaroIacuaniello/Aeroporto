package dao;

import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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

    }
