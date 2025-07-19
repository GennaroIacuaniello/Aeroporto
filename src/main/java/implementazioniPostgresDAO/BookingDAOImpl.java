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

            int generatedId;

            //inserisci in booking
            query = "INSERT INTO Booking (booking_status, booking_time, buyer, id_flight) VALUES (?, ?, ?, ?);";

            preparedQuery = connection.prepareStatement(query);

            preparedQuery.setString(1, bookingStatus);
            preparedQuery.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            preparedQuery.setString(3, idCustomer);
            preparedQuery.setString(4, idFlight);

            preparedQuery.executeUpdate();

            generatedId = preparedQuery.getGeneratedKeys().getInt(0);

            //eventuale inserimento in passenger
            for (int i = 0; i < SSNs.size(); i++) {

                query = "NOT EXISTS(SELECT SSN FROM Passenger WHERE SSN = '" + SSNs.get(i) + "');";

                preparedQuery = connection.prepareStatement(query);
                resultSet = preparedQuery.executeQuery();

                if (resultSet.getBoolean(0)) {

                    String firstNameInsert = "first_name, ";
                    String firstNameValue = firstNames.get(i);
                    if (firstNameValue == null) {
                        firstNameInsert = "";
                        firstNameValue = "";
                    } else firstNameValue += ", ";

                    String lastNameInsert = "last_name, ";
                    String lastNameValue = lastNames.get(i);
                    if (lastNameValue == null) {
                        lastNameInsert = "";
                        lastNameValue = "";
                    } else lastNameValue += ", ";

                    String birthDateInsert = "birth_date, ";
                    String birthDateValue = ", ";
                    Date birthDate = birthDates.get(i);
                    if (birthDate == null) {
                        birthDateInsert = "";
                        birthDateValue = "";
                    }

                    query = "INSERT INTO Passenger (" + firstNameInsert + lastNameInsert + birthDateInsert + "SSN) VALUES (" + firstNameValue + lastNameValue + "?" + birthDateValue + SSNs.get(i) + ");";
                    preparedQuery = connection.prepareStatement(query);

                    if (birthDate == null) preparedQuery.setString(1, "");
                    else preparedQuery.setDate(1, birthDate);

                    preparedQuery.setDate(3, birthDates.get(i));
                    preparedQuery.setString(4, SSNs.get(i));

                    preparedQuery.executeUpdate();
                }
            }

            //inserisci in ticket
            for (int i = 0; i < ticketNumbers.size(); i++) {

                query = "INSERT INTO Ticket (ticket_number, seat, id_booking, id_passenger, id_flight) VALUES (?, ?, ?, ?, ?);";
                preparedQuery = connection.prepareStatement(query);

                preparedQuery.setString(1, ticketNumbers.get(i));
                preparedQuery.setInt(2, seats.get(i));
                preparedQuery.setInt(3, generatedId);
                preparedQuery.setString(4, SSNs.get(i));
                preparedQuery.setString(5, idFlight);

                preparedQuery.executeUpdate();
            }

            //inserisci in luggage
            for (int i = 0; i < ticketForLuggages.size(); i++) {

                query = "INSERT INTO Lugages (luggage_type, luggage_status, id_ticket) VALUES (?, ?, ?);";
                preparedQuery = connection.prepareStatement(query);

                preparedQuery.setString(1, luggagesTypes.get(i));
                preparedQuery.setString(2, "BOOKED");
                preparedQuery.setString(3, ticketForLuggages.get(i));

                preparedQuery.executeUpdate();
            }

            ConnessioneDatabase.getInstance().closeConnection();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}
