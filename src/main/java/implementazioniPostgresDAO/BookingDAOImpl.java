package implementazioniPostgresDAO;

import dao.BookingDAO;
import database.ConnessioneDatabase;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class BookingDAOImpl implements BookingDAO {

    public BookingDAOImpl() throws SQLException {}

    public void addBooking (String idCustomer, String idFlight, String bookingStatus, ArrayList<String> ticketNumbers, ArrayList<Integer> seats, ArrayList<String> firstNames,
                            ArrayList<String> lastNames, ArrayList<Date> birthDates, ArrayList<String> SSNs, ArrayList<String> luggagesTypes, ArrayList<String> ticketForLuggages) throws SQLException {

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection()) {

            String query;
            PreparedStatement preparedQuery;
            ResultSet resultSet;

            long generatedId;

            //inserisci in booking
            query = "INSERT INTO Booking VALUES ('" + bookingStatus + "', " + LocalDateTime.now() + ", '" + idCustomer + "', '" + idFlight + "');";

            preparedQuery = connection.prepareStatement(query);
            preparedQuery.executeUpdate();

            generatedId = preparedQuery.getGeneratedKeys().getLong(0);

            //eventuale inserimento in passenger
            for (int i = 0; i < SSNs.size(); i++) {

                query = "NOT EXISTS(SELECT SSN FROM Passenger WHERE SSN = '" + SSNs.get(i) + "');";

                preparedQuery = connection.prepareStatement(query);
                resultSet = preparedQuery.executeQuery();

                if (resultSet.getBoolean(0)) {

                    query = "INSERT INTO Passenger VALUES ('" + firstNames.get(i) + "', '" + lastNames.get(i) + "', " + birthDates.get(i) + ", '" + SSNs.get(i) + "');";
                    preparedQuery = connection.prepareStatement(query);
                    preparedQuery.executeUpdate();
                }
            }

            //inserisci in ticket
            for (int i = 0; i < ticketNumbers.size(); i++) {

                query = "INSERT INTO Ticket VALUES ('" + ticketNumbers.get(i) + "', " + seats.get(i) + ", " + false + ", " + generatedId + ", '" + SSNs.get(i) + "', '" + idFlight + "');";

                preparedQuery = connection.prepareStatement(query);
                preparedQuery.executeUpdate();
            }

            //inserisci in luggage
            for (int i = 0; i < ticketForLuggages.size(); i++) {

                query = "INSERT INTO Lugages VALUES ('', " + luggagesTypes.get(i) + ", BOOKED, '" + ticketForLuggages.get(i) + "');";

                preparedQuery = connection.prepareStatement(query);
                preparedQuery.executeUpdate();
            }

            //aggiusta free seats
            query = " UPDATE TABLE Flight" +
                    " SET free_seats = free_seats - " + seats.size() +
                    " WHERE id_flight like '" + idFlight + "';";

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}
