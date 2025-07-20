package implementazioniPostgresDAO;

import dao.BookingDAO;
import database.ConnessioneDatabase;
import model.Customer;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {

    public void addBooking (int idCustomer, String idFlight, String bookingStatus, ArrayList<String> ticketNumbers, ArrayList<Integer> seats, ArrayList<String> firstNames,
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
            preparedQuery.setInt(3, idCustomer);
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

    public void getAllBooksCustomer(int loggedUserId, List<String> flightIds, ArrayList<String> companyNames, List<Date> flightDates,
                                    List<Time> departureTimes, List<Time> arrivalTimes,
                                    List<String> flightStatus, List<Integer> maxSeats, List<Integer> freeSeats,
                                    List<String> cities, List<Boolean> types,
                                    List<Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds) throws SQLException{


        String query = "SELECT F.id_flight, F.company_name, F.departure_time, F.arrival_time, F.flight_status, F.max_seats, " +
                        "F.free_seats, F.destination_or_origin, F.flight_type, B.id_booking, B.booking_status, B.booking_time " +
                        "FROM FLIGHT F NATURAL JOIN BOOKING B " +
                        "WHERE B.buyer = ? "+
                        "ORDER BY F.departure_time";


        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, loggedUserId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()){

                flightIds.add(rs.getString("id_flight"));
                companyNames.add(rs.getString("company_name"));

                Timestamp tmpTS = rs.getTimestamp("departure_time");
                flightDates.add(new java.sql.Date(tmpTS.getTime()));
                departureTimes.add(new java.sql.Time(tmpTS.getTime()));

                tmpTS = rs.getTimestamp("arrival_time");
                arrivalTimes.add(new java.sql.Time(tmpTS.getTime()));

                flightStatus.add(rs.getString("flight_status"));

                //delays.add(rs.getInt("flight_delay"));

                maxSeats.add(rs.getInt("max_seats"));
                freeSeats.add(rs.getInt("free_seats"));

                cities.add(rs.getString("destination_or_origin"));

                types.add(rs.getBoolean("flight_type"));

                tmpTS = rs.getTimestamp("booking_time");
                bookingDates.add(new java.sql.Date(tmpTS.getTime()));

                bookingStatus.add(rs.getString("booking_status"));

                bookingIds.add(rs.getInt("id_booking"));


            }

            rs.close();

            //connection.close(); non serve perchè la fa in automatico il try-with-resources


        }




    }
}
