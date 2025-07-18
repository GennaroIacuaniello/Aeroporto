package dao;

import model.*;

import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface FlightDAO {

    void getImminentArrivingFlights (ArrayList<String> parId, ArrayList<String> parCompanyName, ArrayList<Date> parDate,
                                            ArrayList<Time> parDepartureTime, ArrayList<Time> parArrivalTime, ArrayList<String> parStatus,
                                            ArrayList<Integer> parMaxSeats, ArrayList<Integer> parFreeSeats, ArrayList<Integer> parGate);

    void searchFlight (String departingCity, String arrivingCity, LocalDate initialDate, LocalDate finalDate, LocalTime initialTime, LocalTime finalTime,
                              List<String> ids, List<String> companyNames, List<java.sql.Date> dates, List<Time> departureTimes, List<Time> arrivalTimes,
                              List<Integer> delays, List<FlightStatus> status, List<Integer> maxSeats, List<Integer> freeSeats, List<String> cities, List<Boolean> types) throws SQLException;

    }
