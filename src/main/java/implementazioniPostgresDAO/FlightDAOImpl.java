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

    public void getBookedSeats(String flightId, Integer bookingId,ArrayList<Integer> bookedSeats) {

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection()) {

            String query = "SELECT T.seat FROM Ticket NATURAL JOIN Booking WHERE id_flight LIKE ? AND booking_status <> 'cancelled'";

            if (bookingId != null) query += " AND id_booking <> " + bookingId + ";";
            else query += ";";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, flightId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()){

                int tmpSeat = rs.getInt("seat");

                if (tmpSeat != 0) bookedSeats.add(tmpSeat);
            }

            rs.close();

            ConnessioneDatabase.getInstance().closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
