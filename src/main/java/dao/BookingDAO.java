package dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public interface BookingDAO {

    public void addBooking (String idCustomer, String idFlight, String bookingStatus, ArrayList<String> ticketNumbers, ArrayList<Integer> seats, ArrayList<String> firstNames,
                            ArrayList<String> lastNames, ArrayList<Date> birthDates, ArrayList<String> SSNs, ArrayList<String> luggagesTypes, ArrayList<String> ticketForLuggages) throws SQLException;
}
