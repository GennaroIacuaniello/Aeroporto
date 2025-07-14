package implementazioniPostgresDAO;

import dao.FlightDAO;
import database.ConnessioneDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class FlightDAOImpl implements FlightDAO {

    private Connection connection;

    public FlightDAOImpl() throws SQLException {

        try {

            connection = ConnessioneDatabase.getInstance().getConnection();
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public void getImminentArrivingFlights (ArrayList<String> parId, ArrayList<String> parCompanyName, ArrayList<Date> parDate,
                                            ArrayList<Time> parDepartureTime, ArrayList<Time> parArrivalTime, ArrayList<String> parStatus,
                                            ArrayList<Integer> parMaxSeats, ArrayList<Integer> parFreeSeats, ArrayList<Integer> parGate) {

        String query = "SELECT id_flight, company_name, flight_date, departure_time, arrival_time, flight_status," +
                       "max_seats, free_seats, destination_or_origin, flight_delay, id_gate" +
                       "FROM FLIGHT" +
                       "WHERE flight_type = false AND flight_status <> 'landed' AND flight_status <> 'cancelled'" +
                       "ORDER BY flight_date" +
                       "LIMIT 10;";

        try {

            PreparedStatement preparedQuery = connection.prepareStatement(query);

            ResultSet resultSet = preparedQuery.executeQuery();

            while (resultSet.next()) {

                parId.add(resultSet.getString("id_flight"));
                parCompanyName.add(resultSet.getString("company_name"));
                parDate.add(resultSet.getDate("flight_date"));
                parDepartureTime.add(resultSet.getTime("departure_time"));
                parArrivalTime.add(resultSet.getTime("arrival_time"));
                parStatus.add(resultSet.getString("flight_status"));
                parMaxSeats.add(resultSet.getInt("max_seats"));
                parFreeSeats.add(resultSet.getInt("free_seats"));
                parGate.add(resultSet.getInt("gate"));
            }

            resultSet.close();

        } catch (SQLException sqle) {

            sqle.printStackTrace();
        }
    }
}
