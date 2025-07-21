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
                            List<Integer> buyerIds, List<String> usernames, List<String> mails, List<String> hashedPasswords,
                            List<Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds,
                            List<String> ticketNumbers, List<Integer> seats, List<Boolean> checkedIns,
                            List<String> firstNames, List<String> lastNames, List<String> passengerSSNs, List<Date> birthDates,
                            List<Integer> luggageIds, List<String> luggageTypes, List<String> luggageStatus, List<String> luggageIdsAfterCheckin) throws SQLException;

    void getAllLuggagesOfBooking(Integer bookingId, List<String> ticketNumbers, List<Integer> luggageIds, List<String> luggageTypes, List<String> luggageStatus, List<String> luggageIdsAfterCheckin) throws SQLException;
}
