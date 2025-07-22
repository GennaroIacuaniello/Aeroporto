package implementazioni_postgres_dao;

import dao.BookingDAO;
import database.ConnessioneDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookingDAOImpl implements BookingDAO {

    private static final Logger LOGGER = Logger.getLogger(BookingDAOImpl.class.getName());

    public void addBooking (int idCustomer, String idFlight, String bookingStatus, List<String> ticketNumbers, List<Integer> seats, List<String> firstNames,
                            List<String> lastNames, List<Date> birthDates, List<String> SSNs, List<String> luggagesTypes, List<String> ticketForLuggages) throws SQLException {

        String query = "INSERT INTO Booking (booking_status, booking_time, buyer, id_flight) VALUES (?::BookingStatus, ?, ?, ?);";


        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement preparedQuery = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {

            connection.setAutoCommit(false);

            ResultSet resultSet;

            int generatedId;

            //inserisci in booking

            preparedQuery.setObject(1, bookingStatus);
            preparedQuery.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            preparedQuery.setInt(3, idCustomer);
            preparedQuery.setString(4, idFlight);

            int generatedRows = preparedQuery.executeUpdate();

            if (generatedRows == 1) {

                resultSet = preparedQuery.getGeneratedKeys();

                resultSet.next();

                generatedId = resultSet.getInt(1);

                resultSet.close();

            } else throw new SQLException();

            //eventuale inserimento in passenger
            insertPassengers(connection, firstNames, lastNames, birthDates, SSNs);

            //inserisci in ticket
            insertTickets(connection, generatedId, idFlight, ticketNumbers, SSNs, seats);

            //inserisci in luggage
            insertLuggages(connection, ticketForLuggages, luggagesTypes);

            connection.commit();

        } catch (SQLException e) {

            LOGGER.log(Level.SEVERE, e.getSQLState());

        }
    }

    public void modifyBooking (String idFlight, Integer idBooking, List<String> ticketNumbers, List<Integer> seats, List<String> firstNames,
                               List<String> lastNames, List<Date> birthDates, List<String> passengerSSNs, List<String> luggagesTypes, List<String> ticketForLuggages, String tmpTicket, String bookingStatus) throws SQLException {

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection()) {

            connection.setAutoCommit(false);

            String query;
            PreparedStatement preparedQuery;
            ResultSet resultSet;

            //aggiungi ticket temporaneo
            //prendo un passeggero per aggiungere ticket temporaneo
            query = "SELECT id_passenger FROM Ticket WHERE id_booking = ? LIMIT 1;";
            preparedQuery = connection.prepareStatement(query);
            preparedQuery.setInt(1, idBooking);

            resultSet = preparedQuery.executeQuery();

            if (!resultSet.next()) throw new SQLException();

            query = "INSERT INTO Ticket (ticket_number, id_booking, id_passenger, id_flight) VALUES (?, ?, ?, ?);";
            preparedQuery = connection.prepareStatement(query);
            preparedQuery.setString(1, tmpTicket);
            preparedQuery.setInt(2, idBooking);
            preparedQuery.setString(3, resultSet.getString("id_passenger"));
            preparedQuery.setString(4, idFlight);

            preparedQuery.executeUpdate();

            resultSet.close();

            //cancellazione vecchi tickets
            query = "DELETE FROM Ticket WHERE id_booking = ? AND ticket_number <> ?;";
            preparedQuery = connection.prepareStatement(query);
            preparedQuery.setInt(1, idBooking);
            preparedQuery.setString(2, tmpTicket);
            preparedQuery.executeUpdate();

            //eventuale inserimento in passenger
            insertPassengers(connection, firstNames, lastNames, birthDates, passengerSSNs);

            //inserisci in ticket
            insertTickets(connection, idBooking, idFlight, ticketNumbers, passengerSSNs, seats);

            //inserisci in luggage
            insertLuggages(connection, ticketForLuggages,  luggagesTypes);

            //cancellazione ticket temporaneo
            query = "DELETE FROM Ticket WHERE ticket_number = ?;";
            preparedQuery = connection.prepareStatement(query);
            preparedQuery.setString(1, tmpTicket);

            preparedQuery.executeUpdate();

            //modifica stato prenotazione
            query = "UPDATE Booking SET booking_status = ?::BookingStatus WHERE id_booking = ?;";
            preparedQuery = connection.prepareStatement(query);
            preparedQuery.setString(1, bookingStatus);
            preparedQuery.setInt(2, idBooking);

            preparedQuery.executeUpdate();

            connection.commit();

        } catch (SQLException e) {

            LOGGER.log(Level.SEVERE, e.getSQLState());
        }
    }

    private void insertPassengers (Connection connection, List<String> firstNames, List<String> lastNames, List<Date> birthDates, List<String> passengerSSNs) throws SQLException {

        String query;
        PreparedStatement preparedQuery;
        ResultSet resultSet;

        for (int i = 0; i < passengerSSNs.size(); i++) {

            query = "SELECT 1 FROM Passenger WHERE SSN = ?;";
            preparedQuery = connection.prepareStatement(query);

            preparedQuery.setString(1, passengerSSNs.get(i));

            resultSet = preparedQuery.executeQuery();

            if (!resultSet.next()) {

                String firstNameValue = firstNames.get(i);
                String lastNameValue = lastNames.get(i);
                Date birthDateValue = birthDates.get(i);


                String query1 = "INSERT INTO Passenger (SSN";
                String query2 = ") VALUES (?";
                String query3 = ");";

                if (firstNameValue != null) {
                    query1 += ", first_name";
                    query2 += ", ?";
                }

                if (lastNameValue != null) {
                    query1 += ", last_name";
                    query2 += ", ?";
                }

                if (birthDateValue != null) {
                    query1 += ", birth_date";
                    query2 += ", ?";
                }

                query = query1 + query2 + query3;
                preparedQuery = connection.prepareStatement(query);

                int index = 1;

                preparedQuery.setString(index++, passengerSSNs.get(i));
                if (firstNameValue != null) preparedQuery.setString(index++, firstNameValue);
                if (lastNameValue != null) preparedQuery.setString(index++, lastNameValue);
                if (birthDateValue != null) preparedQuery.setDate(index, birthDateValue);

                preparedQuery.executeUpdate();
            } else {

                String firstNameValue = firstNames.get(i);
                String lastNameValue = lastNames.get(i);
                Date birthDateValue = birthDates.get(i);


                String query1 = "UPDATE Passenger set SSN = ?";
                String query2 = " WHERE SSN = ?;";

                if (firstNameValue != null) {
                    query1 += ", first_name = ?";
                }

                if (lastNameValue != null) {
                    query1 += ", last_name = ?";
                }

                if (birthDateValue != null) {
                    query1 += ", birth_date = ?";
                }

                query = query1 + query2;
                preparedQuery = connection.prepareStatement(query);

                int index = 1;

                preparedQuery.setString(index++, passengerSSNs.get(i));
                if (firstNameValue != null) preparedQuery.setString(index++, firstNameValue);
                if (lastNameValue != null) preparedQuery.setString(index++, lastNameValue);
                if (birthDateValue != null) preparedQuery.setDate(index++, birthDateValue);
                preparedQuery.setString(index, passengerSSNs.get(i));

                preparedQuery.executeUpdate();
            }

            resultSet.close();
        }
    }

    private void insertTickets (Connection connection, int idBooking, String idFlight, List<String> ticketNumbers, List<String> passengerSSNs, List<Integer> seats) throws SQLException {

        String query;
        PreparedStatement preparedQuery;

        for (int i = 0; i < ticketNumbers.size(); i++) {

            int seatValue = seats.get(i);

            String query1 = "INSERT INTO Ticket (ticket_number, ";
            String query2 = "id_booking, id_passenger, id_flight) VALUES (?, ";
            String query3 = "?, ?, ?);";

            if (seatValue != -1) {
                query1 += "seat, ";
                query2 += "?, ";
            }

            query = query1 + query2 + query3;
            preparedQuery = connection.prepareStatement(query);

            int index = 1;

            preparedQuery.setString(index++, ticketNumbers.get(i));
            if (seatValue != -1) preparedQuery.setInt(index++, seatValue + 1);
            preparedQuery.setInt(index++, idBooking);
            preparedQuery.setString(index++, passengerSSNs.get(i));
            preparedQuery.setString(index, idFlight);

            preparedQuery.executeUpdate();
        }
    }

    private void insertLuggages (Connection connection, List<String> ticketForLuggages, List<String> luggagesTypes) throws SQLException {

        for (int i = 0; i < ticketForLuggages.size(); i++) {

            String query;
            PreparedStatement preparedQuery;

            query = "INSERT INTO Luggage (luggage_type, luggage_status, id_ticket) VALUES (?::LuggageType, 'BOOKED'::LuggageStatus, ?);";
            preparedQuery = connection.prepareStatement(query);

            preparedQuery.setObject(1, luggagesTypes.get(i));
            //preparedQuery.setObject(2, "BOOKED");
            preparedQuery.setString(2, ticketForLuggages.get(i));

            preparedQuery.executeUpdate();
        }
    }

    public void searchBooksCustomerForAFlight(String flightId, Integer loggedCustomerId,
                                       List<Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds) throws SQLException {

        String query = "SELECT B.id_booking, B.booking_status, B.booking_time " +
                        "FROM FLIGHT F NATURAL JOIN BOOKING B " +
                        "WHERE F.id_flight = ? AND B.buyer = ? "+
                        "ORDER BY F.departure_time;";


        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, flightId);
            statement.setInt(2, loggedCustomerId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()){

                Timestamp tmpTS = rs.getTimestamp("booking_time");
                bookingDates.add(new java.sql.Date(tmpTS.getTime()));

                bookingStatus.add(rs.getString("booking_status"));

                bookingIds.add(rs.getInt("id_booking"));


            }

            rs.close();

            //connection.close(); non serve perchè la fa in automatico il try-with-resources

        }


    }

    public void getAllBooksCustomer(Integer loggedCustomerId, List<String> flightIds, ArrayList<String> companyNames, List<Date> flightDates,
                                   List<Time> departureTimes, List<Time> arrivalTimes,
                                   List<String> flightStatus, List<Integer> maxSeats, List<Integer> freeSeats,
                                   List<String> cities, List<Boolean> types,
                                   List<Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds) throws SQLException{


        String query = "SELECT F.id_flight, F.company_name, F.departure_time, F.arrival_time, F.flight_status, F.max_seats, " +
                "F.free_seats, F.destination_or_origin, F.flight_type, B.id_booking, B.booking_status, B.booking_time " +
                "FROM FLIGHT F NATURAL JOIN BOOKING B " +
                "WHERE B.buyer = ? "+
                "ORDER BY F.departure_time;";


        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, loggedCustomerId);

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

    public void searchBooksCustomerFilteredFlights(String departingCity, String arrivingCity, LocalDate initialDate, LocalDate finalDate, LocalTime initialTime, LocalTime finalTime,
                                                   Integer loggedCustomerId, List<String> flightIds, List<String> companyNames, List<Date> flightDates, List<Time> departureTimes, List<Time> arrivalTimes,
                                                   List<String> flightStatus, List<Integer> maxSeats, List<Integer> freeSeats, List<String> cities, List<Boolean> types,
                                                   List<Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds) throws SQLException {

        String query = "SELECT F.id_flight, F.company_name, F.departure_time, F.arrival_time, F.flight_status, F.max_seats, " +
                "F.free_seats, F.destination_or_origin, F.flight_type, B.id_booking, B.booking_status, B.booking_time " +
                "FROM FLIGHT F NATURAL JOIN BOOKING B " +
                "WHERE B.buyer = ? ";

        ArrayList<Object> searchParam = new ArrayList<>(0);

        java.sql.Date dInitialDate;
        java.sql.Date dFinalDate;
        java.sql.Time dInitialTime;
        java.sql.Time dFinalTime;

        if(  "Napoli".equalsIgnoreCase(departingCity)   || "Napoli".equalsIgnoreCase(arrivingCity)  ||  ((departingCity == null || departingCity.trim().isEmpty()) && (arrivingCity == null || arrivingCity.trim().isEmpty()) ) ){

            if( departingCity != null && !departingCity.trim().isEmpty() && !"Napoli".equalsIgnoreCase(departingCity) ){

                query += "AND (F.destination_or_origin ILIKE ? AND F.flight_type = false) ";
                searchParam.add(departingCity);

            }else if( arrivingCity != null && !arrivingCity.trim().isEmpty() && !"Napoli".equalsIgnoreCase(arrivingCity)){

                query += "AND (F.destination_or_origin ILIKE ? AND F.flight_type = true) ";
                searchParam.add(arrivingCity);
            }

            if(initialDate != null && finalDate != null){

                dInitialDate = java.sql.Date.valueOf(initialDate);
                dFinalDate = java.sql.Date.valueOf(finalDate);


                query += "AND (F.departure_time::date BETWEEN ? AND ?) ";

                searchParam.add(dInitialDate);
                searchParam.add(dFinalDate);

            }

            if(initialTime != null && finalTime != null){

                dInitialTime = java.sql.Time.valueOf(initialTime);
                dFinalTime = java.sql.Time.valueOf(finalTime);

                String tmpCompare = "";

                if(initialTime.isBefore(finalTime)){

                    tmpCompare += "(F.departure_time::time BETWEEN ? AND ?) ";

                }else{
                    tmpCompare += "(F.departure_time::time >= ? OR F.departure_time::time <= ?)";
                }

                query += "AND " + tmpCompare;

                searchParam.add(dInitialTime);
                searchParam.add(dFinalTime);

            }

            query = query.trim();

            query += " ORDER BY F.departure_time DESC;";


        }

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, loggedCustomerId);

            for(int i = 0; i < searchParam.size(); i++){

                statement.setObject( i + 2, searchParam.get(i));

            }

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

    public void searchBooksCustomerFilteredPassengers(String firstName, String lastName, String passengerSSN, String ticketNumber,
                                               Integer loggedCustomerId, List<String> flightIds, List<String> companyNames, List<Date> flightDates,
                                               List<Time> departureTimes, List<Time> arrivalTimes, List<String> flightStatus,
                                               List<Integer> maxSeats, List<Integer> freeSeats, List<String> cities, List<Boolean> types,
                                               List<Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds) throws SQLException {

        String query = "SELECT F.id_flight, F.company_name, F.departure_time, F.arrival_time, F.flight_status, F.max_seats, " +
                       "F.free_seats, F.destination_or_origin, F.flight_type, B.id_booking, B.booking_status, B.booking_time " +
                       "FROM FLIGHT F NATURAL JOIN BOOKING B JOIN TICKET T ON B.id_booking  = T.id_booking JOIN PASSENGER P ON T.id_passenger = P.SSN " +
                       "WHERE B.buyer = ? ";

        ArrayList<Object> searchParam = new ArrayList<>(0);

        if(firstName != null && !firstName.trim().isEmpty()){

            query += "AND (P.first_name ILIKE ?) ";
            searchParam.add(firstName);

        }

        if(lastName != null && !lastName.trim().isEmpty()){

            query += "AND (P.last_name ILIKE ?) ";
            searchParam.add(lastName);

        }

        if(passengerSSN != null && !passengerSSN.trim().isEmpty()){

            query += "AND (P.SSN ILIKE ?) ";
            searchParam.add(passengerSSN);

        }

        if(ticketNumber != null && !ticketNumber.trim().isEmpty()){

            query += "AND (T.ticket_number ILIKE ?) ";
            searchParam.add(ticketNumber);

        }

        query = query.trim();

        query += " ORDER BY F.departure_time DESC;";

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, loggedCustomerId);

            for(int i = 0; i < searchParam.size(); i++){

                statement.setObject( i + 2, searchParam.get(i));

            }

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

    public void deleteBooking (int bookingId) throws SQLException {

        String query = "UPDATE Booking SET booking_status = 'CANCELLED' WHERE id_booking = ?;";

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement preparedQuery = connection.prepareStatement(query)) {

            preparedQuery.setInt(1, bookingId);
            preparedQuery.executeUpdate();
        }
    }
}
