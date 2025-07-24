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

/**
 * PostgreSQL implementation of the BookingDAO interface for managing booking operations in the airport management system.
 * <p>
 * This class provides concrete implementations for all booking-related database operations
 * defined in the {@link BookingDAO} interface. It handles comprehensive booking management
 * functions including booking creation, modification, searching, and deletion operations
 * using PostgreSQL database connectivity.
 * </p>
 * <p>
 * The implementation provides comprehensive booking management capabilities including:
 * </p>
 * <ul>
 *   <li>Atomic booking creation with passenger, ticket, and luggage associations</li>
 *   <li>Complex booking modification operations with transactional integrity</li>
 *   <li>Advanced booking search functionality with multiple filtering criteria</li>
 *   <li>Customer-specific booking retrieval and management operations</li>
 *   <li>Integration with flight, passenger, ticket, and luggage management systems</li>
 * </ul>
 * <p>
 * All database operations use prepared statements to prevent SQL injection attacks and ensure
 * data security. The class implements proper connection management using the singleton
 * {@link ConnessioneDatabase} pattern and handles resource cleanup through try-with-resources
 * statements.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see BookingDAO
 * @see model.Booking
 * @see ConnessioneDatabase
 * @see SQLException
 */
public class BookingDAOImpl implements BookingDAO {

    /**
     * Logger instance for recording database operation events and errors.
     */
    private static final Logger LOGGER = Logger.getLogger(BookingDAOImpl.class.getName());

    /**
     * {@inheritDoc}
     * <p>
     * This implementation performs a comprehensive atomic booking creation operation that
     * includes creating the booking record, managing passenger information, generating
     * tickets with seat assignments, and creating associated luggage records.
     * </p>
     * <p>
     * The operation follows this sequence:
     * </p>
     * <ol>
     *   <li>Creates the booking record with generated timestamp and specified status</li>
     *   <li>Retrieves the generated booking ID for subsequent operations</li>
     *   <li>Inserts or updates passenger information as needed</li>
     *   <li>Creates tickets with seat assignments for each passenger</li>
     *   <li>Creates luggage records associated with specific tickets</li>
     * </ol>
     *
     * @param idCustomer the unique identifier of the customer making the booking
     * @param idFlight the unique identifier of the flight being booked
     * @param bookingStatus the initial status of the booking (e.g., "PENDING", "CONFIRMED")
     * @param ticketNumbers list of unique ticket numbers for each passenger
     * @param seats list of seat assignments for each ticket (can contain -1 for no assignment)
     * @param firstNames list of passenger first names (can contain null values)
     * @param lastNames list of passenger last names (can contain null values)
     * @param birthDates list of passenger birthdates (can contain null values)
     * @param passengerSSNs list of passenger SSN identifiers (required, cannot be null)
     * @param luggagesTypes list of luggage types for each luggage item
     * @param ticketForLuggages list of ticket numbers associated with each luggage item
     * @throws SQLException if a database access error occurs during the booking creation process
     */
    public void addBooking (int idCustomer, String idFlight, String bookingStatus, List<String> ticketNumbers, List<Integer> seats, List<String> firstNames,
                            List<String> lastNames, List<Date> birthDates, List<String> passengerSSNs, List<String> luggagesTypes, List<String> ticketForLuggages) throws SQLException {

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
            insertPassengers(connection, firstNames, lastNames, birthDates, passengerSSNs);

            //inserisci in ticket
            insertTickets(connection, generatedId, idFlight, ticketNumbers, passengerSSNs, seats);

            //inserisci in luggage
            insertLuggages(connection, ticketForLuggages, luggagesTypes);

            connection.commit();

        }
    }

    /**
     * Modifies an existing booking with new passenger, ticket, and luggage information.
     * <p>
     * This method performs a complex booking modification operation that maintains data
     * consistency through the use of temporary tickets and transactional operations.
     * The modification process completely replaces existing tickets and luggage while
     * preserving the original booking record.
     * </p>
     * <p>
     * The operation follows this sequence:
     * </p>
     * <ol>
     *   <li>Creates a temporary ticket to maintain foreign key relationships</li>
     *   <li>Deletes all existing tickets except the temporary one</li>
     *   <li>Updates or inserts passenger information as needed</li>
     *   <li>Creates new tickets with updated seat assignments</li>
     *   <li>Creates new luggage records for the updated tickets</li>
     *   <li>Removes the temporary ticket</li>
     *   <li>Updates the booking status</li>
     * </ol>
     * <p>
     * The temporary ticket mechanism ensures that the booking always has at least one
     * associated ticket during the modification process, preventing foreign key violations
     * and maintaining referential integrity. All operations are performed within a
     * single transaction to ensure atomicity.
     * </p>
     *
     * @param idFlight the flight identifier for the booking
     * @param idBooking the unique identifier of the booking to modify
     * @param ticketNumbers list of new ticket numbers for the booking
     * @param seats list of new seat assignments (can contain -1 for no assignment)
     * @param firstNames list of passenger first names (can contain null values)
     * @param lastNames list of passenger last names (can contain null values)
     * @param birthDates list of passenger birth dates (can contain null values)
     * @param passengerSSNs list of passenger SSN identifiers (required)
     * @param luggagesTypes list of luggage types for new luggage items
     * @param ticketForLuggages list of ticket numbers associated with each luggage item
     * @param tmpTicket temporary ticket number used during the modification process
     * @param bookingStatus new status for the booking after modification
     * @throws SQLException if a database access error occurs during the modification process
     */
    public void modifyBooking (String idFlight, Integer idBooking, List<String> ticketNumbers, List<Integer> seats, List<String> firstNames,
                               List<String> lastNames, List<Date> birthDates, List<String> passengerSSNs, List<String> luggagesTypes, List<String> ticketForLuggages, String tmpTicket, String bookingStatus) throws SQLException {

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection()) {

            connection.setAutoCommit(false);

            String query;
            ResultSet resultSet;

            //aggiungi ticket temporaneo
            //prendo un passeggero per aggiungere ticket temporaneo
            query = "SELECT id_passenger FROM Ticket WHERE id_booking = ? LIMIT 1;";

            try (PreparedStatement preparedSelectQuery = connection.prepareStatement(query)) {

                preparedSelectQuery.setInt(1, idBooking);

                resultSet = preparedSelectQuery.executeQuery();

                if (!resultSet.next()) {
                    throw new SQLException();
                }

                query = "INSERT INTO Ticket (ticket_number, id_booking, id_passenger, id_flight) VALUES (?, ?, ?, ?);";

                try (PreparedStatement preparedInsertQuery = connection.prepareStatement(query)) {

                    preparedInsertQuery.setString(1, tmpTicket);
                    preparedInsertQuery.setInt(2, idBooking);
                    preparedInsertQuery.setString(3, resultSet.getString("id_passenger"));
                    preparedInsertQuery.setString(4, idFlight);

                    preparedInsertQuery.executeUpdate();

                    resultSet.close();
                }
            }

            //cancellazione vecchi tickets
            query = "DELETE FROM Ticket WHERE id_booking = ? AND ticket_number <> ?;";

            try (PreparedStatement preparedQuery = connection.prepareStatement(query)) {

                preparedQuery.setInt(1, idBooking);
                preparedQuery.setString(2, tmpTicket);
                preparedQuery.executeUpdate();
            }

            //eventuale inserimento in passenger
            insertPassengers(connection, firstNames, lastNames, birthDates, passengerSSNs);
            //inserisci in ticket
            insertTickets(connection, idBooking, idFlight, ticketNumbers, passengerSSNs, seats);
            //inserisci in luggage
            insertLuggages(connection, ticketForLuggages,  luggagesTypes);
            //cancellazione ticket temporaneo
            query = "DELETE FROM Ticket WHERE ticket_number = ?;";

            try (PreparedStatement preparedQuery = connection.prepareStatement(query)) {

                preparedQuery.setString(1, tmpTicket);

                preparedQuery.executeUpdate();
            }

            //modifica stato prenotazione
            query = "UPDATE Booking SET booking_status = ?::BookingStatus WHERE id_booking = ?;";

            try (PreparedStatement preparedQuery = connection.prepareStatement(query)) {

                preparedQuery.setString(1, bookingStatus);
                preparedQuery.setInt(2, idBooking);

                preparedQuery.executeUpdate();
            }

            connection.commit();
        }
    }

    /**
     * Inserts or updates passenger information in the database.
     * <p>
     * This private helper method manages passenger data by either creating new passenger
     * records or updating existing ones based on SSN. It handles optional passenger
     * information fields (first name, last name, birthdate) by including them in the
     * SQL query only when non-null values are provided.
     * </p>
     * <p>
     * The method constructs dynamic SQL queries based on available data:
     * </p>
     * <ul>
     *   <li>For new passengers: INSERT with only non-null fields</li>
     *   <li>For existing passengers: UPDATE with only non-null fields</li>
     *   <li>SSN is always required and used as the primary identifier</li>
     * </ul>
     *
     * @param connection the database connection to use for the operation
     * @param firstNames list of passenger first names (can contain null values)
     * @param lastNames list of passenger last names (can contain null values)
     * @param birthDates list of passenger birthdates (can contain null values)
     * @param passengerSSNs list of passenger SSN identifiers (required)
     * @throws SQLException if a database access error occurs during passenger insertion/update
     */
    private void insertPassengers (Connection connection, List<String> firstNames, List<String> lastNames, List<Date> birthDates, List<String> passengerSSNs) throws SQLException {

        String query;
        ResultSet resultSet;

        for (int i = 0; i < passengerSSNs.size(); i++) {

            query = "SELECT 1 FROM Passenger WHERE SSN = ?;";

            try (PreparedStatement preparedSelectQuery = connection.prepareStatement(query)){

                preparedSelectQuery.setString(1, passengerSSNs.get(i));

                resultSet = preparedSelectQuery.executeQuery();

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

                    try (PreparedStatement preparedInsertQuery = connection.prepareStatement(query)) {

                        int index = 1;

                        preparedInsertQuery.setString(index++, passengerSSNs.get(i));
                        if (firstNameValue != null) preparedInsertQuery.setString(index++, firstNameValue);
                        if (lastNameValue != null) preparedInsertQuery.setString(index++, lastNameValue);
                        if (birthDateValue != null) preparedInsertQuery.setDate(index, birthDateValue);

                        preparedInsertQuery.executeUpdate();
                    }
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

                    try (PreparedStatement preparedUpdateQuery = connection.prepareStatement(query)) {

                        int index = 1;

                        preparedUpdateQuery.setString(index++, passengerSSNs.get(i));
                        if (firstNameValue != null) preparedUpdateQuery.setString(index++, firstNameValue);
                        if (lastNameValue != null) preparedUpdateQuery.setString(index++, lastNameValue);
                        if (birthDateValue != null) preparedUpdateQuery.setDate(index++, birthDateValue);
                        preparedUpdateQuery.setString(index, passengerSSNs.get(i));

                        preparedUpdateQuery.executeUpdate();
                    }
                }

                resultSet.close();
            }
        }
    }

    /**
     * Inserts ticket records into the database with optional seat assignments.
     * <p>
     * This private helper method creates ticket records for a booking, handling both
     * seated and unseated tickets. It constructs dynamic SQL queries based on whether
     * seat assignments are provided, and converts application seat numbering (0-based)
     * to database storage format (1-based).
     * </p>
     * <p>
     * Seat handling logic:
     * </p>
     * <ul>
     *   <li>Seat value -1: No seat assignment, seat column excluded from INSERT</li>
     *   <li>Seat value >= 0: Seat assignment, converted to 1-based for database storage</li>
     * </ul>
     *
     * @param connection the database connection to use for the operation
     * @param idBooking the booking identifier to associate tickets with
     * @param idFlight the flight identifier for the tickets
     * @param ticketNumbers list of unique ticket numbers
     * @param passengerSSNs list of passenger SSN identifiers
     * @param seats list of seat assignments (-1 for no assignment, 0+ for seat number)
     * @throws SQLException if a database access error occurs during ticket insertion
     */
    private void insertTickets (Connection connection, int idBooking, String idFlight, List<String> ticketNumbers, List<String> passengerSSNs, List<Integer> seats) throws SQLException {

        String query;

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

            try (PreparedStatement preparedQuery = connection.prepareStatement(query)) {

                int index = 1;

                preparedQuery.setString(index++, ticketNumbers.get(i));
                if (seatValue != -1) preparedQuery.setInt(index++, seatValue + 1);
                preparedQuery.setInt(index++, idBooking);
                preparedQuery.setString(index++, passengerSSNs.get(i));
                preparedQuery.setString(index, idFlight);

                preparedQuery.executeUpdate();
            }
        }
    }

    /**
     * Inserts luggage records associated with specific tickets.
     * <p>
     * This private helper method creates luggage records with the initial status of
     * 'BOOKED' for each luggage item. Each luggage item is associated with a specific
     * ticket through the ticket number.
     * </p>
     * <p>
     * All luggage items are created with:
     * </p>
     * <ul>
     *   <li>Specified luggage type (CARRY_ON or CHECKED)</li>
     *   <li>Initial status of 'BOOKED'</li>
     *   <li>Association with the corresponding ticket</li>
     * </ul>
     *
     * @param connection the database connection to use for the operation
     * @param ticketForLuggages list of ticket numbers to associate luggage with
     * @param luggagesTypes list of luggage types for each luggage item
     * @throws SQLException if a database access error occurs during luggage insertion
     */
    private void insertLuggages (Connection connection, List<String> ticketForLuggages, List<String> luggagesTypes) throws SQLException {

        for (int i = 0; i < ticketForLuggages.size(); i++) {

            String query = "INSERT INTO Luggage (luggage_type, luggage_status, id_ticket) VALUES (?::LuggageType, 'BOOKED'::LuggageStatus, ?);";

            try (PreparedStatement preparedQuery = connection.prepareStatement(query)) {

                preparedQuery.setObject(1, luggagesTypes.get(i));
                preparedQuery.setString(2, ticketForLuggages.get(i));

                preparedQuery.executeUpdate();
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation executes a SQL query that joins the FLIGHT and BOOKING tables
     * to retrieve bookings for a specific customer and flight combination. The results
     * are ordered by flight departure time to provide chronological organization.
     * </p>
     * <p>
     * The method populates the provided lists with booking information including:
     * </p>
     * <ul>
     *   <li>Booking creation timestamps converted to Date objects</li>
     *   <li>Current booking status values</li>
     *   <li>Unique booking identifiers for further operations</li>
     * </ul>
     * <p>
     * This method is particularly useful for customer service operations where
     * representatives need to view all bookings a customer has made for a specific
     * flight, enabling booking modifications and customer support functions.
     * </p>
     *
     * @param flightId the unique identifier of the flight to search for
     * @param loggedCustomerId the unique identifier of the customer whose bookings to retrieve
     * @param bookingDates list to be populated with booking creation dates
     * @param bookingStatus list to be populated with current booking status values
     * @param bookingIds list to be populated with unique booking identifiers
     * @throws SQLException if a database access error occurs during the search operation
     */
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

    /**
     * {@inheritDoc}
     * <p>
     * This implementation executes a comprehensive SQL query that joins the FLIGHT and
     * BOOKING tables to retrieve all bookings associated with a specific customer. The
     * query extracts both flight details and booking information, providing a complete
     * view of the customer's travel history.
     * </p>
     * <p>
     * The method processes timestamp data by extracting both date and time components
     * from departure and arrival timestamps, and converts booking timestamps to Date
     * objects for consistent data handling. Results are ordered by flight departure
     * time to provide chronological organization.
     * </p>
     * <p>
     * Retrieved data includes comprehensive flight information (schedules, capacity,
     * destinations) and booking details (status, creation time, identifiers) to
     * support customer service and self-service booking management operations.
     * </p>
     *
     * @param loggedCustomerId the unique identifier of the customer whose bookings to retrieve
     * @param flightIds list to be populated with flight identifiers
     * @param companyNames list to be populated with airline company names
     * @param flightDates list to be populated with flight dates
     * @param departureTimes list to be populated with flight departure times
     * @param arrivalTimes list to be populated with flight arrival times
     * @param flightStatus list to be populated with current flight status values
     * @param maxSeats list to be populated with flight maximum seat capacity
     * @param freeSeats list to be populated with available seats count
     * @param cities list to be populated with destination or origin city names
     * @param types list to be populated with flight type indicators (true for arriving, false for departing)
     * @param bookingDates list to be populated with booking creation dates
     * @param bookingStatus list to be populated with current booking status values
     * @param bookingIds list to be populated with unique booking identifiers
     * @throws SQLException if a database access error occurs during the retrieval operation
     */
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

    /**
     * {@inheritDoc}
     * <p>
     * This implementation constructs a dynamic SQL query based on the provided flight
     * filtering criteria. It handles special logic for "Napoli" as the airport's base
     * city and supports flexible filtering across multiple flight characteristics.
     * </p>
     * <p>
     * The filtering logic includes:
     * </p>
     * <ul>
     *   <li>City filtering with special handling for "Napoli" (airport base city)</li>
     *   <li>Date range filtering using inclusive BETWEEN operations</li>
     *   <li>Time range filtering supporting both same-day and overnight spans</li>
     *   <li>Dynamic query construction based on provided parameters</li>
     * </ul>
     *
     * @param departingCity the departure city name for filtering (can be null or empty)
     * @param arrivingCity the arrival city name for filtering (can be null or empty)
     * @param initialDate the start date for date range filtering (can be null)
     * @param finalDate the end date for date range filtering (can be null)
     * @param initialTime the start time for time range filtering (can be null)
     * @param finalTime the end time for time range filtering (can be null)
     * @param loggedCustomerId the unique identifier of the customer whose bookings to search
     * @param flightIds list to be populated with matching flight identifiers
     * @param companyNames list to be populated with airline company names
     * @param flightDates list to be populated with flight dates
     * @param departureTimes list to be populated with flight departure times
     * @param arrivalTimes list to be populated with flight arrival times
     * @param flightStatus list to be populated with current flight status values
     * @param maxSeats list to be populated with flight maximum seat capacity
     * @param freeSeats list to be populated with available seats count
     * @param cities list to be populated with destination or origin city names
     * @param types list to be populated with flight type indicators (true for departing, false for arriving)
     * @param bookingDates list to be populated with booking creation dates
     * @param bookingStatus list to be populated with current booking status values
     * @param bookingIds list to be populated with unique booking identifiers
     * @throws SQLException if a database access error occurs during the search operation
     */
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

    /**
     * {@inheritDoc}
     * <p>
     * This implementation constructs a complex SQL query that joins FLIGHT, BOOKING,
     * TICKET, and PASSENGER tables to enable searching based on passenger information.
     * It uses DISTINCT to avoid duplicate results when multiple passengers in the same
     * booking match the search criteria.
     * </p>
     * <p>
     * The search supports flexible filtering with the following criteria:
     * </p>
     * <ul>
     *   <li>First name matching with case-insensitive partial matching (ILIKE)</li>
     *   <li>Last name matching with case-insensitive partial matching (ILIKE)</li>
     *   <li>SSN matching with case-insensitive partial matching (ILIKE)</li>
     *   <li>Ticket number matching with case-insensitive partial matching (ILIKE)</li>
     * </ul>
     *
     * @param firstName the passenger first name for filtering (can be null or empty)
     * @param lastName the passenger last name for filtering (can be null or empty)
     * @param passengerSSN the passenger SSN for filtering (can be null or empty)
     * @param ticketNumber the ticket number for filtering (can be null or empty)
     * @param loggedCustomerId the unique identifier of the customer whose bookings to search
     * @param flightIds list to be populated with matching flight identifiers
     * @param companyNames list to be populated with airline company names
     * @param flightDates list to be populated with flight dates
     * @param departureTimes list to be populated with flight departure times
     * @param arrivalTimes list to be populated with flight arrival times
     * @param flightStatus list to be populated with current flight status values
     * @param maxSeats list to be populated with flight maximum seat capacity
     * @param freeSeats list to be populated with available seats count
     * @param cities list to be populated with destination or origin city names
     * @param types list to be populated with flight type indicators
     * @param bookingDates list to be populated with booking creation dates
     * @param bookingStatus list to be populated with current booking status values
     * @param bookingIds list to be populated with unique booking identifiers
     * @throws SQLException if a database access error occurs during the search operation
     */
    public void searchBooksCustomerFilteredPassengers(String firstName, String lastName, String passengerSSN, String ticketNumber,
                                               Integer loggedCustomerId, List<String> flightIds, List<String> companyNames, List<Date> flightDates,
                                               List<Time> departureTimes, List<Time> arrivalTimes, List<String> flightStatus,
                                               List<Integer> maxSeats, List<Integer> freeSeats, List<String> cities, List<Boolean> types,
                                               List<Date> bookingDates, List<String> bookingStatus, List<Integer> bookingIds) throws SQLException {

        String query = "SELECT DISTINCT F.id_flight, F.company_name, F.departure_time, F.arrival_time, F.flight_status, F.max_seats, " +
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

    /**
     * {@inheritDoc}
     * <p>
     * This method implements soft deletion by updating the booking status rather than
     * physically removing the booking record from the database. This approach preserves
     * data integrity and maintains audit trails for administrative and compliance purposes
     * while effectively removing the booking from active operations.
     * </p>
     * <p>
     * The soft deletion process:
     * </p>
     * <ul>
     *   <li>Updates the booking_status to 'CANCELLED' for the specified booking</li>
     *   <li>Preserves all booking data for audit and historical purposes</li>
     *   <li>Maintains referential integrity with related tickets, passengers, and luggage</li>
     *   <li>Allows the booking to be excluded from active booking queries when needed</li>
     * </ul>
     *
     * @param bookingId the unique identifier of the booking to delete/cancel
     * @throws SQLException if a database access error occurs during the deletion operation
     */
    public void deleteBooking (int bookingId) throws SQLException {

        String query = "UPDATE Booking SET booking_status = 'CANCELLED' WHERE id_booking = ?;";

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement preparedQuery = connection.prepareStatement(query)) {

            preparedQuery.setInt(1, bookingId);
            preparedQuery.executeUpdate();
        }
    }
}