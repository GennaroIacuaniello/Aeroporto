package implementazioni_postgres_dao;

import dao.FlightDAO;
import database.ConnessioneDatabase;


import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlightDAOImpl implements FlightDAO {

    private static final Logger LOGGER = Logger.getLogger(FlightDAOImpl.class.getName());

    public void getImminentArrivingFlights (List<String> parId, List<String> parCompanyName, List<Date> parDate,
                                            List<Time> parDepartureTime, List<Time> parArrivalTime, List<String> parStatus,
                                            List<Integer> parMaxSeats, List<Integer> parFreeSeats, List<String> origin,
                                            List<Integer> delay, List<Integer> parGate) throws SQLException{

        String query = "SELECT id_flight, company_name, departure_time, arrival_time, flight_status, " +
                "max_seats, free_seats, destination_or_origin, flight_delay, id_gate " +
                "FROM FLIGHT " +
                "WHERE flight_type = false AND flight_status <> 'LANDED' AND flight_status <> 'CANCELLED' " +
                "AND arrival_time + (flight_delay * interval '1 minute') > now() AT TIME ZONE current_setting('TimeZone') " +
                "ORDER BY arrival_time " +
                "LIMIT 6 ";

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement preparedQuery = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedQuery.executeQuery();

            while (resultSet.next()) {

                parId.add(resultSet.getString("id_flight"));
                parCompanyName.add(resultSet.getString("company_name"));

                Timestamp tmpTS = resultSet.getTimestamp("departure_time");
                parDate.add(new Date(tmpTS.getTime()));
                parDepartureTime.add(new Time(tmpTS.getTime()));

                tmpTS = resultSet.getTimestamp("arrival_time");
                parArrivalTime.add(new Time(tmpTS.getTime()));

                parStatus.add(resultSet.getString("flight_status"));

                parMaxSeats.add(resultSet.getInt("max_seats"));
                parFreeSeats.add(resultSet.getInt("free_seats"));

                origin.add(resultSet.getString("destination_or_origin"));

                delay.add(resultSet.getInt("flight_delay"));

                if(resultSet.getInt("id_gate") > 0){
                    parGate.add(resultSet.getInt("id_gate"));
                }else{
                    parGate.add(null);
                }
            }

            resultSet.close();
            //connection.close(); non serve perchè la fa in automatico il try-with-resources

        } catch (SQLException sqle) {

            LOGGER.log(Level.SEVERE, sqle.getSQLState());
        }
    }

    public void searchFlight (String departingCity, String arrivingCity, LocalDate initialDate, LocalDate finalDate, LocalTime initialTime, LocalTime finalTime,
                              List<String> ids, List<String> companyNames, List<java.sql.Date> dates, List<Time> departureTimes, List<Time> arrivalTimes,
                              List<Integer> delays, List<String> status, List<Integer> maxSeats, List<Integer> freeSeats, List<String> cities, List<Boolean> types) throws SQLException{


        String query = "SELECT id_flight, company_name, departure_time, arrival_time, flight_status, max_seats, free_seats, destination_or_origin, flight_delay, flight_type " +
                        "FROM FLIGHT " +
                        "WHERE ";

        ArrayList<Object> searchParam = new ArrayList<>(0);

        java.sql.Date dInitialDate;
        java.sql.Date dFinalDate;
        java.sql.Time dInitialTime;
        java.sql.Time dFinalTime;

        if(  "Napoli".equalsIgnoreCase(departingCity)   || "Napoli".equalsIgnoreCase(arrivingCity)  ||  ((departingCity == null || departingCity.trim().isEmpty()) && (arrivingCity == null || arrivingCity.trim().isEmpty()) ) ){

            if( departingCity != null && !departingCity.trim().isEmpty() && !"Napoli".equalsIgnoreCase(departingCity) ){

                query += "(destination_or_origin ILIKE ? AND flight_type = false) ";
                searchParam.add(departingCity);

            }else if( arrivingCity != null && !arrivingCity.trim().isEmpty() && !"Napoli".equalsIgnoreCase(arrivingCity)){

                query += "(destination_or_origin ILIKE ? AND flight_type = true) ";
                searchParam.add(arrivingCity);
            }

            if(initialDate != null && finalDate != null){

                dInitialDate = java.sql.Date.valueOf(initialDate);
                dFinalDate = java.sql.Date.valueOf(finalDate);

                if(query.trim().endsWith("WHERE")){

                    query += "(departure_time::date BETWEEN ? AND ?) ";

                }else{

                    query += "AND (departure_time::date BETWEEN ? AND ?) ";

                }

                searchParam.add(dInitialDate);
                searchParam.add(dFinalDate);

            }

            if(initialTime != null && finalTime != null){

                dInitialTime = java.sql.Time.valueOf(initialTime);
                dFinalTime = java.sql.Time.valueOf(finalTime);

                String tmpCompare = "";

                if(initialTime.isBefore(finalTime)){

                    tmpCompare += "(departure_time::time BETWEEN ? AND ?) ";

                }else{
                    tmpCompare += "(departure_time::time >= ? OR departure_time::time <= ?)";
                }

                if(query.trim().endsWith("WHERE")){

                    query += tmpCompare;

                }else{

                    query += "AND " + tmpCompare;

                }

                searchParam.add(dInitialTime);
                searchParam.add(dFinalTime);

            }

            if(!searchParam.isEmpty()){
                query = query.trim();
            }else{
                query = query.trim();
                query = query.substring( 0, query.length() - ("WHERE".length()) );
            }
            query += " ORDER BY departure_time DESC;";


        }

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            for(int i = 0; i < searchParam.size(); i++){

                statement.setObject( i + 1, searchParam.get(i));

            }

            ResultSet rs = statement.executeQuery();

            while (rs.next()){

                ids.add(rs.getString("id_flight"));
                companyNames.add(rs.getString("company_name"));

                Timestamp tmpTS = rs.getTimestamp("departure_time");
                dates.add(new java.sql.Date(tmpTS.getTime()));
                departureTimes.add(new java.sql.Time(tmpTS.getTime()));

                tmpTS = rs.getTimestamp("arrival_time");
                arrivalTimes.add(new java.sql.Time(tmpTS.getTime()));

                status.add(rs.getString("flight_status"));

                delays.add(rs.getInt("flight_delay"));

                maxSeats.add(rs.getInt("max_seats"));
                freeSeats.add(rs.getInt("free_seats"));

                cities.add(rs.getString("destination_or_origin"));

                types.add(rs.getBoolean("flight_type"));

            }

            rs.close();

            //connection.close(); non serve perchè la fa in automatico il try-with-resources


        }

    }


    public void getAllDataForAFlight(String flightId, List<Integer> flightGates, List<Integer> buyerIds,
                                     List<String> usernames, List<String> mails, List<String> hashedPasswords,
                                     List<java.sql.Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds,
                                     List<String> ticketNumbers, List<Integer> seats, List<Boolean> checkedIns,
                                     List<String> firstNames, List<String> lastNames, List<String> passengerSSNs, List<java.sql.Date> birthDates,
                                     List<Integer> luggageIds, List<String> luggageTypes, List<String> luggageStatus, List<String> luggageIdsAfterCheckin) throws SQLException {

        String query = "SELECT F.id_gate, C.id_customer, C.username, C.mail, C.hashed_password, " +
                        "B.id_booking, B.booking_status, B.booking_time, " +
                        "T.ticket_number, T.seat, T.checked_in, P.first_name, P.last_name, P.SSN, P.birth_date, " +
                        "L.id_luggage, L.id_luggage_after_check_in, L.luggage_type, L.luggage_status " +
                        "FROM FLIGHT F NATURAL JOIN BOOKING B JOIN TICKET T ON B.id_booking = T.id_booking JOIN " +
                        "PASSENGER P ON T.id_passenger = P.SSN LEFT JOIN LUGGAGE L ON L.id_ticket = T.ticket_number JOIN CUSTOMER C ON B.buyer = C.id_customer " +
                        "WHERE F.id_flight = ? " +
                        "ORDER BY B.id_booking;";


        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, flightId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()){

                if(rs.getInt("id_gate") > 0){
                    flightGates.add(rs.getInt("id_gate"));
                }else{
                    flightGates.add(null);
                }

                buyerIds.add(rs.getInt("id_customer"));
                usernames.add(rs.getString("username"));
                mails.add(rs.getString("mail"));
                hashedPasswords.add(rs.getString("hashed_password"));

                Timestamp tmpTS = rs.getTimestamp("booking_time");
                bookingDates.add(new java.sql.Date(tmpTS.getTime()));

                bookingStatus.add(rs.getString("booking_status"));

                bookingIds.add(rs.getInt("id_booking"));

                ticketNumbers.add(rs.getString("ticket_number"));

                if(rs.getInt("seat") > 0){
                    seats.add(rs.getInt("seat") - 1);
                }else{
                    seats.add(null);
                }

                checkedIns.add(rs.getBoolean("checked_in"));

                firstNames.add(rs.getString("first_name"));

                lastNames.add(rs.getString("last_name"));

                passengerSSNs.add(rs.getString("SSN"));

                birthDates.add(rs.getDate("birth_date"));

                if(rs.getInt("id_luggage") > 0){
                    luggageIds.add(rs.getInt("id_luggage"));
                }else{
                    luggageIds.add(null);
                }

                luggageIdsAfterCheckin.add(rs.getString("id_luggage_after_check_in"));

                luggageTypes.add(rs.getString("luggage_type"));

                luggageStatus.add(rs.getString("luggage_status"));

            }

            rs.close();

            //connection.close(); non serve perchè la fa in automatico il try-with-resources


        }

    }


    public void getBookedSeats(String flightId, Integer bookingId, List<Integer> bookedSeats) {

        String query = "SELECT seat FROM Ticket NATURAL JOIN Booking WHERE id_flight LIKE ? AND booking_status <> 'CANCELLED'";

        if (bookingId != null) query += " AND id_booking <> " + bookingId;
        query += ";";

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, flightId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()){

                int tmpSeat = rs.getInt("seat");

                if (tmpSeat != 0) bookedSeats.add(tmpSeat - 1);
            }

            rs.close();

            ConnessioneDatabase.getInstance().closeConnection();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getSQLState());
        }
    }

    public int startCheckin(String flightId) throws SQLException {

        String query = "UPDATE Flight SET flight_status = 'ABOUT_TO_DEPART' WHERE id_flight = ?;";

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement preparedQuery = connection.prepareStatement(query)) {

            preparedQuery.setString(1, flightId);
            return preparedQuery.executeUpdate();
        }
    }

    public int searchGate(String idFlight) {

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();) {

            connection.setAutoCommit(false);

            String query = "SELECT * FROM Flight WHERE id_gate = ? AND flight_status <> 'CANCELLED';";

            for (int i = 1; i <= 20; i++) {

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, i);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (!resultSet.next()) {

                    resultSet.close();

                    query = "UPDATE Flight SET id_gate = ? WHERE id_flight = ?;";
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, i);
                    preparedStatement.setString(2, idFlight);

                    preparedStatement.executeUpdate();

                    connection.commit();

                    return i;
                }

                resultSet.close();
            }

            return -1;

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getSQLState());

            return -1;
        }
    }

    public void setGate(int idGate, String idFlight) {

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection()) {

            connection.setAutoCommit(false);

            String query = "UPDATE Flight SET id_gate = ? WHERE id_flight = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idGate);
            preparedStatement.setString(2, idFlight);

            preparedStatement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getSQLState());
        }
    }

    public int setStatus (String status, String idFlight) {

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();) {

            connection.setAutoCommit(false);

            String query = "UPDATE Flight SET flight_status = ?::FlightStatus WHERE id_flight = ? ;";
            PreparedStatement preparedStatement =  connection.prepareStatement(query);

            preparedStatement.setString(1, status);
            preparedStatement.setString(2, idFlight);

            int result = preparedStatement.executeUpdate();

            connection.commit();

            return result;

        } catch (Exception e) {
            return -1;
        }
    }

    public int addDelay(int delay, String idFlight) {

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection()) {

            connection.setAutoCommit(false);

            String query = "UPDATE Flight SET flight_delay = flight_delay + ? WHERE id_flight = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, delay);
            preparedStatement.setString(2, idFlight);

            int result = preparedStatement.executeUpdate();

            connection.commit();

            return result;

        } catch (SQLException e) {

            return -1;
        }
    }
}
