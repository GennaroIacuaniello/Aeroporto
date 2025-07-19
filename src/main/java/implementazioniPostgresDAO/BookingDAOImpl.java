package implementazioniPostgresDAO;

import dao.BookingDAO;
import database.ConnessioneDatabase;
import model.Customer;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public void getAllBooksCustomer(int loggedUserId, List<String> flightIds, ArrayList<String> companyNames, List<Date> dates,
                                    List<Time> departureTimes, List<Time> arrivalTimes,
                                    List<String> status, List<Integer> maxSeats, List<Integer> freeSeats,
                                    List<String> cities, List<Boolean> types,
                                    List<Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds) throws SQLException{


        String query = "SELECT id_flight, company_name, departure_time, arrival_time, flight_status, max_seats, free_seats, destination_or_origin, flight_type, id_booking, booking_status, booking_time " +
                        "FROM FLIGHT NATURAL JOIN BOOKING " +
                        "WHERE buyer = ? "+
                        "ORDER BY departure_time";


        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, loggedUserId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()){

                flightIds.add(rs.getString("id_flight"));
                companyNames.add(rs.getString("company_name"));

                Timestamp tmpTS = rs.getTimestamp("departure_time");
                dates.add(new java.sql.Date(tmpTS.getTime()));
                departureTimes.add(new java.sql.Time(tmpTS.getTime()));

                tmpTS = rs.getTimestamp("arrival_time");
                arrivalTimes.add(new java.sql.Time(tmpTS.getTime()));

                status.add(rs.getString("flight_status"));

                //delays.add(rs.getInt("flight_delay"));

                maxSeats.add(rs.getInt("max_seats"));
                freeSeats.add(rs.getInt("free_seats"));

                cities.add(rs.getString("destination_or_origin"));

                types.add(rs.getBoolean("flight_type"));

            }

            rs.close();

            //connection.close(); non serve perchè la fa in automatico il try-with-resources


        }




    }
}
