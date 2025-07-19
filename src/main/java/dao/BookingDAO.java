package dao;

import model.Customer;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public interface BookingDAO {

    void addBooking (String idCustomer, String idFlight, String bookingStatus, ArrayList<String> ticketNumbers, ArrayList<Integer> seats, ArrayList<String> firstNames,
                            ArrayList<String> lastNames, ArrayList<Date> birthDates, ArrayList<String> SSNs, ArrayList<String> luggagesTypes, ArrayList<String> ticketForLuggages) throws SQLException;

    void getAllBooksCustomer(int loggedUserId, List<String> flightIds, ArrayList<String> companyNames, List<Date> dates,
                             List<Time> departureTimes, List<Time> arrivalTimes,
                             List<String> status, List<Integer> maxSeats, List<Integer> freeSeats,
                             List<String> cities, List<Boolean> types,
                             List<Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds) throws SQLException;
}
