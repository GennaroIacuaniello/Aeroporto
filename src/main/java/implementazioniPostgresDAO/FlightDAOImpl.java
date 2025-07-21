package implementazioniPostgresDAO;

import dao.FlightDAO;
import database.ConnessioneDatabase;


import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FlightDAOImpl implements FlightDAO {

    public void getImminentArrivingFlights (ArrayList<String> parId, ArrayList<String> parCompanyName, ArrayList<Date> parDate,
                                            ArrayList<Time> parDepartureTime, ArrayList<Time> parArrivalTime, ArrayList<String> parStatus,
                                            ArrayList<Integer> parMaxSeats, ArrayList<Integer> parFreeSeats, ArrayList<Integer> parGate) {

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection()) {

            String query = "SELECT id_flight, company_name, flight_date, departure_time, arrival_time, flight_status," +
                           "max_seats, free_seats, destination_or_origin, flight_delay, id_gate " +
                           "FROM FLIGHT " +
                           "WHERE flight_type = false AND flight_status <> 'landed' AND flight_status <> 'cancelled' " +
                           "ORDER BY flight_date " +
                           "LIMIT 10;";

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
            //connection.close(); non serve perchè la fa in automatico il try-with-resources

        } catch (SQLException sqle) {

            sqle.printStackTrace();
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
            query += " ORDER BY departure_time;";


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


    public void getBookedSeats(String flightId, Integer bookingId,ArrayList<Integer> bookedSeats) {

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection()) {

            String query = "SELECT seat FROM Ticket NATURAL JOIN Booking WHERE id_flight LIKE ? AND booking_status <> 'CANCELLED'";

            if (bookingId != null) query += " AND id_booking <> " + bookingId;
            query += ";";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, flightId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()){

                int tmpSeat = rs.getInt("seat");

                if (tmpSeat != 0) bookedSeats.add(tmpSeat - 1);
            }

            rs.close();

            ConnessioneDatabase.getInstance().closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void startCheckin(String flightId) throws SQLException {

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection()) {

            String query = "UPDATE Flight SET flight_status = 'ABOUT_TO_DEPART' WHERE id_flight = ?;";
            PreparedStatement preparedQuery = connection.prepareStatement(query);
            preparedQuery.setString(1, flightId);
            preparedQuery.executeUpdate();
        }
    }




}
