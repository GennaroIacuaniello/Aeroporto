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

/**
 * PostgreSQL implementation of the FlightDAO interface for managing flight operations in the airport management system.
 * <p>
 * This class provides concrete implementations for all flight-related database operations
 * defined in the {@link FlightDAO} interface. It handles comprehensive flight management
 * functions including flight retrieval, searching, creation, and data management operations
 * using PostgreSQL database connectivity.
 * </p>
 * <p>
 * The implementation provides comprehensive flight management capabilities including:
 * </p>
 * <ul>
 *   <li>Dashboard flight retrieval for imminent arrivals and departures</li>
 *   <li>Advanced flight search functionality with flexible filtering criteria</li>
 *   <li>Complete flight data retrieval including associated bookings, tickets, passengers, and luggage</li>
 *   <li>Flight creation and insertion operations for administrative purposes</li>
 *   <li>Operational flight management including gate assignments, status updates, and delays</li>
 *   <li>Check-in management and seat assignment operations</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see FlightDAO
 * @see model.Flight
 * @see ConnessioneDatabase
 * @see SQLException
 */
public class FlightDAOImpl implements FlightDAO {

    /**
     * Logger instance for recording database operation events and errors.
     */
    private static final Logger LOGGER = Logger.getLogger(FlightDAOImpl.class.getName());

    /**
     * {@inheritDoc}
     * <p>
     * This implementation executes a SQL query that retrieves up to 6 arriving flights
     * that are scheduled to arrive soon and haven't landed yet. The query filters flights
     * by type (flight_type = false for arriving flights) and excludes flights with status
     * 'LANDED' or 'CANCELLED'.
     * </p>
     *
     * @param parId list to be populated with flight identifiers
     * @param parCompanyName list to be populated with airline company names
     * @param parDate list to be populated with flight dates derived from departure timestamps
     * @param parDepartureTime list to be populated with departure times from origin airports
     * @param parArrivalTime list to be populated with scheduled arrival times
     * @param parStatus list to be populated with current flight status values
     * @param parMaxSeats list to be populated with maximum seating capacity
     * @param parFreeSeats list to be populated with available seats count
     * @param origin list to be populated with origin airport or city names
     * @param delay list to be populated with arrival delay values in minutes
     * @param parGate list to be populated with assigned gate numbers (null if not assigned)
     * @throws SQLException if a database access error occurs during the retrieval operation
     */
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

    /**
     * {@inheritDoc}
     * <p>
     * This implementation executes a SQL query that retrieves up to 6 departing flights
     * that are scheduled to depart soon and are still at the airport. The query filters
     * flights by type (flight_type = true for departing flights) and includes only flights
     * with operational statuses: 'PROGRAMMED', 'ABOUT_TO_DEPART', or 'DELAYED'.
     * </p>
     *
     * @param parId list to be populated with flight identifiers
     * @param parCompanyName list to be populated with airline company names
     * @param parDate list to be populated with flight dates derived from departure timestamps
     * @param parDepartureTime list to be populated with scheduled departure times
     * @param parArrivalTime list to be populated with arrival times at destination airports
     * @param parStatus list to be populated with current flight status values
     * @param parMaxSeats list to be populated with maximum seating capacity
     * @param parFreeSeats list to be populated with available seats count
     * @param origin list to be populated with destination airport or city names
     * @param delay list to be populated with departure delay values in minutes
     * @param parGate list to be populated with assigned gate numbers (null if not assigned)
     * @throws SQLException if a database access error occurs during the retrieval operation
     */
    public void getImminentDepartingFlights (List<String> parId, List<String> parCompanyName, List<Date> parDate,
                                             List<Time> parDepartureTime, List<Time> parArrivalTime, List<String> parStatus,
                                             List<Integer> parMaxSeats, List<Integer> parFreeSeats, List<String> origin,
                                             List<Integer> delay, List<Integer> parGate) throws SQLException{
        String query = "SELECT id_flight, company_name, departure_time, arrival_time, flight_status, " +
                "max_seats, free_seats, destination_or_origin, flight_delay, id_gate " +
                "FROM FLIGHT " +
                "WHERE flight_type = true AND (flight_status = 'PROGRAMMED' OR flight_status = 'ABOUT_TO_DEPART' " +
                "OR flight_status = 'DELAYED') " +
                "AND departure_time + (flight_delay * interval '1 minute') > now() AT TIME ZONE current_setting('TimeZone') " +
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

    /**
     * {@inheritDoc}
     * <p>
     * This implementation constructs a dynamic SQL query based on the provided filtering
     * criteria, with special handling for "Napoli" as the airport's base location. The
     * method supports flexible filtering across multiple flight characteristics including
     * cities, dates, and times.
     * </p>
     * <p>
     * The city filtering logic includes special cases:
     * </p>
     * <ul>
     *   <li>When departing city is "Napoli" or null/empty: no city filter applied (local departures)</li>
     *   <li>When arriving city is "Napoli" or null/empty: no city filter applied (local arrivals)</li>
     *   <li>When departing city is not "Napoli": filters for arriving flights from that city</li>
     *   <li>When arriving city is not "Napoli": filters for departing flights to that city</li>
     * </ul>
     *
     * @param departingCity the departure city name for filtering (null or empty for no filter)
     * @param arrivingCity the arrival city name for filtering (null or empty for no filter)
     * @param initialDate the start date for date range filtering (null for no date filter)
     * @param finalDate the end date for date range filtering (null for no date filter)
     * @param initialTime the start time for time range filtering (null for no time filter)
     * @param finalTime the end time for time range filtering (null for no time filter)
     * @param ids list to be populated with flight identifiers
     * @param companyNames list to be populated with airline company names
     * @param dates list to be populated with flight dates
     * @param departureTimes list to be populated with departure times
     * @param arrivalTimes list to be populated with arrival times
     * @param delays list to be populated with flight delay values in minutes
     * @param status list to be populated with flight status values
     * @param maxSeats list to be populated with maximum seating capacity
     * @param freeSeats list to be populated with available seats count
     * @param cities list to be populated with destination or origin city names
     * @param types list to be populated with flight type indicators (true for departing, false for arriving)
     * @throws SQLException if a database access error occurs during the search operation
     */
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

    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation executes a complex SQL query that joins multiple tables (FLIGHT,
     * BOOKING, TICKET, PASSENGER, LUGGAGE, CUSTOMER) to provide comprehensive flight
     * information. The query uses NATURAL JOIN for FLIGHT and BOOKING tables, and explicit
     * JOINs for other relationships to maintain data integrity.
     * </p>
     * <p>
     * The method handles various data types and nullable fields appropriately:
     * </p>
     * <ul>
     *   <li>Gate assignments: null when id_gate <span>&#8804;</span> 0, otherwise the gate number</li>
     *   <li>Seat assignments: converted from 1-based database storage to 0-based application indexing</li>
     *   <li>Luggage information: null when id_luggage <span>&#8804;</span> 0, otherwise the luggage ID</li>
     *   <li>Timestamps: properly converted to Date objects for consistent handling</li>
     * </ul>
     * <p>
     * The LEFT JOIN with LUGGAGE table ensures that tickets without associated luggage
     * are still included in the results, providing complete flight manifest information
     * regardless of luggage status.
     * </p>
     *
     * @param flightId the unique identifier of the flight to retrieve data for
     * @param flightGates list to be populated with gate assignments (null if not assigned)
     * @param buyerIds list to be populated with customer identifiers who made bookings
     * @param usernames list to be populated with customer usernames
     * @param mails list to be populated with customer email addresses
     * @param hashedPasswords list to be populated with customer password hashes
     * @param bookingDates list to be populated with booking creation timestamps
     * @param bookingStatus list to be populated with booking status values
     * @param bookingIds list to be populated with unique booking identifiers
     * @param ticketNumbers list to be populated with ticket numbers
     * @param seats list to be populated with seat assignments (null if not assigned, 0-based indexing)
     * @param checkedIns list to be populated with check-in status indicators
     * @param firstNames list to be populated with passenger first names
     * @param lastNames list to be populated with passenger last names
     * @param passengerSSNs list to be populated with passenger SSN identifiers
     * @param birthDates list to be populated with passenger birth dates
     * @param luggageIds list to be populated with luggage identifiers (null if no luggage)
     * @param luggageTypes list to be populated with luggage type descriptions
     * @param luggageStatus list to be populated with luggage status values
     * @param luggageIdsAfterCheckin list to be populated with post-checkin luggage identifiers
     * @throws SQLException if a database access error occurs during the data retrieval operation
     */
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

    /**
     * {@inheritDoc}
     * <p>
     * This implementation creates a new flight record with all essential flight details
     * using a prepared statement to ensure data security and consistency. The method
     * inserts flights with default operational settings appropriate for new flights.
     * </p>
     * <p>
     * Default values set for new flights:
     * </p>
     * <ul>
     *   <li>Flight status: 'PROGRAMMED' (indicating a scheduled but not yet operational flight)</li>
     *   <li>Free seats: equal to max seats (no bookings yet)</li>
     *   <li>Flight delay: 0 minutes (no delays initially)</li>
     *   <li>Gate assignment: null (to be assigned later)</li>
     * </ul>
     *
     * @param flightId the unique identifier for the new flight (must be unique in the system)
     * @param companyName the name of the airline company operating the flight
     * @param departureTimestamp the complete departure date and time
     * @param arrivalTimestamp the complete arrival date and time
     * @param maxSeats the maximum number of seats available on the flight (must be positive)
     * @param otherCity the destination city (for departing flights) or origin city (for arriving flights)
     * @param flightType true for departing flights, false for arriving flights
     * @throws SQLException if a database access error occurs during the flight insertion operation
     */
    public void InsertAFlight(String flightId, String companyName, Timestamp departureTimestamp, Timestamp arrivalTimestamp,
                              int maxSeats, String otherCity, boolean flightType) throws SQLException {


        String query = "INSERT INTO FLIGHT (id_flight, company_name, departure_time, arrival_time, flight_status, max_seats, free_seats, destination_or_origin, flight_type) VALUES "+
                       "(?, ?, ?, ?, ?::FlightStatus, ?, ?, ?, ?);";


        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, flightId);
            statement.setString(2, companyName);
            statement.setTimestamp(3, departureTimestamp);
            statement.setTimestamp(4, arrivalTimestamp);
            statement.setString(5, "PROGRAMMED");
            statement.setInt(6, maxSeats);
            statement.setInt(7, maxSeats);
            statement.setString(8, otherCity);
            statement.setBoolean(9, flightType);

            statement.executeUpdate();

            //connection.close(); non serve perchè la fa in automatico il try-with-resources

        }



    }

    /**
     * {@inheritDoc}
     * <p>
     * This method queries the database to find all seat assignments for tickets associated
     * with a specific flight. It excludes bookings with 'CANCELLED' status to provide
     * accurate seat availability information for booking and seat selection operations.
     * </p>
     * <p>
     * The method handles seat number conversion from database storage (1-based) to
     * application indexing (0-based) for consistency with the application's seat
     * management system. Seats with value 0 or null in the database are not included
     * in the results as they represent unassigned seats.
     * </p>
     * <p>
     * An optional booking ID parameter allows excluding a specific booking from the
     * results, which is useful during booking modifications where the current booking's
     * seats should not be considered as occupied.
     * </p>
     *
     * @param flightId the unique identifier of the flight to check seat availability for
     * @param bookingId optional booking ID to exclude from results (can be null)
     * @param bookedSeats list to be populated with booked seat numbers (0-based indexing)
     */
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

    /**
     * {@inheritDoc}
     * <p>
     * This method updates a flight's status to indicate that check-in procedures have
     * begun and the flight is preparing for departure. This status change typically
     * triggers various operational processes including gate assignments, passenger
     * notifications, and boarding preparations.
     * </p>
     * <p>
     * The status change from 'PROGRAMMED' to 'ABOUT_TO_DEPART' is a critical operational
     * transition that affects passenger check-in availability, seat assignments, and
     * various airport management systems.
     * </p>
     *
     * @param flightId the unique identifier of the flight to start check-in for
     * @return the number of rows affected by the update operation (1 if successful, 0 if flight not found)
     * @throws SQLException if a database access error occurs during the status update operation
     */
    public int startCheckin(String flightId) throws SQLException {

        String query = "UPDATE Flight SET flight_status = 'ABOUT_TO_DEPART' WHERE id_flight = ?;";

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement preparedQuery = connection.prepareStatement(query)) {

            preparedQuery.setString(1, flightId);
            return preparedQuery.executeUpdate();
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method implements automatic gate assignment by iterating through gates 1-20
     * and finding the first available gate (not assigned to any non-cancelled flight).
     * Once an available gate is found, it is immediately assigned to the specified flight.
     * </p>
     * <p>
     * The method uses database transactions to ensure atomicity of the gate assignment
     * process. It checks each gate's availability and assigns the first free gate,
     * preventing race conditions in concurrent gate assignment operations.
     * </p>
     * <p>
     * Gate availability is determined by checking if any flights are currently assigned
     * to the gate with a status other than 'CANCELLED'. This ensures that cancelled
     * flights do not block gate availability for operational flights.
     * </p>
     *
     * @param idFlight the unique identifier of the flight to assign a gate to
     * @return the assigned gate number (1-20) if successful, -1 if no gates are available or an error occurs
     */
    public int searchGate(String idFlight) {

        String query = "SELECT id_gate FROM Flight WHERE id_gate <> null AND " +
                "((flight_type = true AND (flight_status = 'ABOUT_TO_DEPART' OR flight_status = 'DELAYED') ) " +
                "OR (flight_type = false AND (flight_status = 'DEPARTED' OR flight_status = 'ABOUT_TO_ARRIVE' OR (flight_status = 'LANDED' AND arrival_time > CURRENT_TIMESTAMP - INTERVAL '1 HOUR'))));";
        ResultSet resultSet;

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
            PreparedStatement preparedSelectStatement = connection.prepareStatement(query)) {

            connection.setAutoCommit(false);

            resultSet = preparedSelectStatement.executeQuery();

            if (!resultSet.next()) {

                resultSet.close();

                query = "UPDATE Flight SET id_gate = 1 WHERE id_flight = ?;";

                try (PreparedStatement preparedUpdateStatement = connection.prepareStatement(query)) {

                    preparedUpdateStatement.setString(1, idFlight);

                    preparedUpdateStatement.executeUpdate();
                }

                connection.commit();

                return 1;
            }

            ArrayList<Integer> gates = new ArrayList<Integer>();

            while (resultSet.next()) gates.add(resultSet.getInt("id_gate"));

            for (int i = 1; i <= 20; i++) {

                boolean flag = false;

                for (Integer gate : gates) {

                    if (gate.equals(i)) {
                        flag = true;
                        break;
                    }
                }

                if (!flag) {

                    query = "UPDATE Flight SET id_gate = ? WHERE id_flight = ?;";

                    try (PreparedStatement preparedUpdateStatement = connection.prepareStatement(query)) {

                        preparedUpdateStatement.setInt(1, i);
                        preparedUpdateStatement.setString(2, idFlight);

                        preparedUpdateStatement.executeUpdate();
                    }

                    connection.commit();

                    return i;
                }
            }

            return -1;

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getSQLState());

            return -1;
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method updates the gate assignment for a specified flight, allowing manual
     * gate assignment or reassignment operations. The method uses database transactions
     * to ensure the assignment is completed atomically.
     * </p>
     * <p>
     * Unlike {@link #searchGate(String)}, this method does not check gate availability
     * and will assign the specified gate regardless of its current status. This allows
     * for administrative override of automatic gate assignments when necessary.
     * </p>
     *
     * @param idGate the gate number to assign to the flight
     * @param idFlight the unique identifier of the flight to assign the gate to
     */
    public void setGate(int idGate, String idFlight) {

        String query = "UPDATE Flight SET id_gate = ? WHERE id_flight = ?;";

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            connection.setAutoCommit(false);

            preparedStatement.setInt(1, idGate);
            preparedStatement.setString(2, idFlight);

            preparedStatement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getSQLState());
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method allows updating a flight's operational status to reflect current
     * conditions such as delays, boarding, departure, arrival, or cancellation.
     * The method uses PostgreSQL's enum casting to ensure proper status validation.
     * </p>
     * <p>
     * Common flight status transitions include:
     * </p>
     * <ul>
     *   <li>PROGRAMMED → ABOUT_TO_DEPART (check-in started)</li>
     *   <li>ABOUT_TO_DEPART → DELAYED (departure delayed)</li>
     *   <li>ABOUT_TO_DEPART → DEPARTED (flight has left)</li>
     *   <li>Any status → CANCELLED (flight cancelled)</li>
     * </ul>
     * <p>
     * The method uses database transactions to ensure status changes are applied
     * atomically and consistently across the system.
     * </p>
     *
     * @param status the new flight status to set (must be a valid FlightStatus enum value)
     * @param idFlight the unique identifier of the flight to update
     * @return the number of rows affected by the update (1 if successful, 0 if flight not found), -1 if an error occurs
     */
    public int setStatus (String status, String idFlight) {

        String query = "UPDATE Flight SET flight_status = ?::FlightStatus WHERE id_flight = ? ;";

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement preparedStatement =  connection.prepareStatement(query)) {

            connection.setAutoCommit(false);

            preparedStatement.setString(1, status);
            preparedStatement.setString(2, idFlight);

            int result = preparedStatement.executeUpdate();

            connection.commit();

            return result;

        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method increases the flight's delay by the specified number of minutes,
     * allowing for cumulative delay tracking. The delay is added to any existing
     * delay value, providing accurate delay information for passengers and operations.
     * </p>
     * <p>
     * Flight delays affect various system calculations including arrival and departure
     * time displays, gate scheduling, and passenger notifications. The method uses
     * database transactions to ensure delay updates are applied consistently.
     * </p>
     *
     * @param delay the number of minutes to add to the current flight delay (must be positive)
     * @param idFlight the unique identifier of the flight to add delay to
     * @return the number of rows affected by the update (1 if successful, 0 if flight not found), -1 if an error occurs
     */
    public int addDelay(int delay, String idFlight) {

        String query = "UPDATE Flight SET flight_delay = flight_delay + ? WHERE id_flight = ?;";

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            connection.setAutoCommit(false);

            preparedStatement.setInt(1, delay);
            preparedStatement.setString(2, idFlight);

            int result = preparedStatement.executeUpdate();

            connection.commit();

            return result;

        } catch (SQLException e) {

            return -1;
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method efficiently updates the check-in status for multiple tickets using
     * separate lists for tickets to be checked in (true) and tickets to be unchecked
     * (false). This batch approach reduces database round trips and ensures consistent
     * check-in status updates.
     * </p>
     * <p>
     * The method uses database transactions to ensure all check-in status changes
     * are applied atomically. This is particularly important for group bookings
     * where multiple passengers' check-in status needs to be updated simultaneously.
     * </p>
     *
     * @param trueTickets list of ticket numbers to set check-in status to true
     * @param falseTickets list of ticket numbers to set check-in status to false
     */
    public void setCheckins (ArrayList<String> trueTickets, ArrayList<String> falseTickets) {

        String query = "UPDATE Ticket SET checked_in = ? WHERE ticket_number = ?;";

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection()) {

            connection.setAutoCommit(false);

            for (String ticket : trueTickets) {

                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                    preparedStatement.setBoolean(1, true);
                    preparedStatement.setString(2, ticket);

                    preparedStatement.executeUpdate();
                }
            }

            for (String ticket : falseTickets) {

                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                    preparedStatement.setBoolean(1, false);
                    preparedStatement.setString(2, ticket);

                    preparedStatement.executeUpdate();
                }
            }

            connection.commit();

        } catch (SQLException e) {

            LOGGER.log(Level.SEVERE, e.getSQLState());
        }

    }

    /**
     * {@inheritDoc}]
     * <p>
     * This method queries the database to retrieve luggage identifiers assigned after
     * check-in for a list of tickets. These identifiers are used for luggage tracking
     * during the baggage handling process after passengers have checked in.
     * </p>
     * <p>
     * The method returns a nested list structure where each inner list contains all
     * luggage identifiers associated with a specific ticket. This allows for proper
     * correlation between tickets and their associated luggage items.
     * </p>
     * <p>
     * Post-checkin luggage identifiers are typically assigned during the check-in
     * process and are used for physical luggage tracking throughout the airport
     * baggage handling system.
     * </p>
     *
     * @param tickets list of ticket numbers to retrieve luggage identifiers for
     * @return nested list where each inner list contains luggage identifiers for the corresponding ticket, null if an error occurs
     */
    public ArrayList<ArrayList<String>> getLuggagesCheckins (ArrayList<String> tickets) {

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection()) {

            ArrayList<ArrayList<String>> idLuggages = new ArrayList<ArrayList<String>>();

            String query = "SELECT id_luggage_after_check_in FROM Luggage WHERE id_ticket = ?;";

            for (String ticket : tickets) {

                ArrayList<String> tmpArrayList = new ArrayList<String>();

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, ticket);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) tmpArrayList.add(resultSet.getString("id_luggage_after_check_in"));

                idLuggages.add(tmpArrayList);

                resultSet.close();
            }

            return idLuggages;

        } catch (SQLException e) {
            e.printStackTrace();

            return null;
        }
    }
}