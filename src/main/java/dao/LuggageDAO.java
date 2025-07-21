package dao;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public interface LuggageDAO {


    void getAllLostLuggages(List<String> flightIds, List<String> companyNames, List<Date> flightDates,
                            List<Time> departureTimes, List<Time> arrivalTimes, List<String> flightStatus,
                            List<Integer> maxSeats, List<Integer> freeSeats,
                            List<String> cities, List<Boolean> flightTypes,
                            List<Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds,
                            List<String> ticketNumbers, List<Integer> seats, List<Boolean> checkedIns,
                            List<String> firstNames, List<String> lastNames, List<String> passengerSSNs, List<Date> birthDates,
                            List<String> luggageIds, List<String> luggageTypes, List<String> luggageStatus) throws SQLException;

}
