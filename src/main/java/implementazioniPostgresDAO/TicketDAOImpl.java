package implementazioniPostgresDAO;

import dao.TicketDAO;
import database.ConnessioneDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAOImpl implements TicketDAO {

    public void getAllTicketBooking(int bookingId, List<String> ticketNumbers, List<Integer> seats, List<Boolean> checkedIns,
                                    List<String> SSNs, List<String> firstNames,
                                    List<String> lastNames, List<Date> birthDates) throws SQLException {


        String query = "SELECT T.ticket_number, T.seat, T.checked_in, T.id_passenger, P.first_name, P.last_name, P.birth_date " +
                "FROM TICKET T JOIN PASSENGER P ON T.id_passenger = P.SSN " +
                "WHERE T.id_booking = ?;";


        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, bookingId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()){

                ticketNumbers.add(rs.getString("ticket_number"));
                if(rs.getInt("seat") > 0){
                    seats.add(rs.getInt("seat") - 1);
                }else{
                    seats.add(null);
                }
                checkedIns.add(rs.getBoolean("checked_in"));
                SSNs.add(rs.getString("id_passenger"));
                firstNames.add(rs.getString("first_name"));
                lastNames.add(rs.getString("last_name"));
                birthDates.add(rs.getDate("birth_date"));

            }

            rs.close();

            //connection.close(); non serve perchè la fa in automatico il try-with-resources

        }

    }

}
