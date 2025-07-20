package dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TicketDAO {

    void getAllTicketBooking(int bookingId, List<String> ticketNumbers, List<Integer> seats, List<Boolean> checkedIns,
                                    List<String> SSNs, List<String> firstNames,
                                    List<String> lastNames, List<Date> birthDates) throws SQLException;

}
