package implementazioniPostgresDAO;

import dao.LuggageDAO;
import database.ConnessioneDatabase;

import java.sql.*;
import java.util.List;

public class LuggageDAOImpl implements LuggageDAO {

    public void getAllLostLuggages(List<String> flightIds, List<String> companyNames, List<Date> flightDates,
                                   List<Time> departureTimes, List<Time> arrivalTimes, List<String> flightStatus,
                                   List<Integer> maxSeats, List<Integer> freeSeats,
                                   List<String> cities, List<Boolean> flightTypes,
                                   List<Integer> buyerIds, List<String> usernames, List<String> mails, List<String> hashedPasswords,
                                   List<Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds,
                                   List<String> ticketNumbers, List<Integer> seats, List<Boolean> checkedIns,
                                   List<String> firstNames, List<String> lastNames, List<String> passengerSSNs, List<Date> birthDates,
                                   List<Integer> luggageIds, List<String> luggageTypes, List<String> luggageStatus, List<String> luggageIdsAfterCheckin) throws SQLException {

        String query = "SELECT F.id_flight, F.company_name, F.departure_time, F.arrival_time, F.flight_status, F.max_seats, " +
                        "F.free_seats, F.destination_or_origin, F.flight_type, " +
                        "C.id_customer, C.username, C.mail, C.hashed_password, " +
                        "B.id_booking, B.booking_status, B.booking_time, " +
                        "T.ticket_number, T.seat, T.checked_in, P.first_name, P.last_name, P.SSN, P.birth_date, " +
                        "L.id_luggage, L.id_luggage_after_check_in, L.luggage_type, L.luggage_status " +
                        "FROM FLIGHT F NATURAL JOIN BOOKING B JOIN TICKET T ON B.id_booking = T.id_booking JOIN " +
                        "PASSENGER P ON T.id_passenger = P.SSN JOIN LUGGAGE L ON L.id_ticket = T.ticket_number JOIN CUSTOMER C ON B.buyer = C.id_customer " +
                        "WHERE L.luggage_status = 'LOST' AND C.is_deleted = false " +
                        "ORDER BY F.departure_time;";


        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

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

                flightTypes.add(rs.getBoolean("flight_type"));

                buyerIds.add(rs.getInt("id_customer"));
                usernames.add(rs.getString("username"));
                mails.add(rs.getString("mail"));
                hashedPasswords.add(rs.getString("hashed_password"));

                tmpTS = rs.getTimestamp("booking_time");
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

    public void getAllLuggagesOfBooking(Integer bookingId, List<String> ticketNumbers, List<Integer> luggageIds, List<String> luggageTypes, List<String> luggageStatus, List<String> luggageIdsAfterCheckin) throws SQLException {

        String query = "SELECT T.ticket_number, L.id_luggage, L.id_luggage_after_check_in, L.luggage_type, L.luggage_status " +
                        "FROM BOOKING B JOIN TICKET T ON B.id_booking = T.id_booking JOIN " +
                        "PASSENGER P ON T.id_passenger = P.SSN LEFT JOIN LUGGAGE L ON L.id_ticket = T.ticket_number JOIN CUSTOMER C ON B.buyer = C.id_customer " +
                        "WHERE B.id_booking = ? " +
                        "ORDER BY T.ticket_number;";


        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, bookingId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()){

                ticketNumbers.add(rs.getString("ticket_number"));

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

}
