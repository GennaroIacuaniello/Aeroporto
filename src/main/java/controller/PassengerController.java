package controller;

import model.Passenger;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for managing passenger operations and search results in the airport management system.
 * <p>
 * This class serves as the specialized controller for passenger-related operations of the airport management system.
 * It maintains passenger search results and provides methods for managing passenger data
 * retrieved from various search and administrative operations.
 * </p>
 * <p>
 * The PassengerController is responsible for:
 * </p>
 * <ul>
 *   <li>Managing collections of passenger objects from search operations</li>
 *   <li>Providing access to passenger information for display and processing</li>
 *   <li>Supporting administrative operations that require passenger data management</li>
 *   <li>Maintaining passenger collections for booking and flight management workflows</li>
 *   <li>Facilitating passenger data organization and retrieval for GUI components</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Passenger
 * @see Controller
 * @see BookingController
 * @see FlightController
 * @see TicketController
 */
public class PassengerController {

    /**
     * Collection of passenger objects returned from search operations.
     * Used for displaying passenger information and enabling administrative
     * operations on passenger data within various system contexts.
     */
    private ArrayList<Passenger> searchBookingResult;

    /**
     * Retrieves the collection of passenger objects from search results.
     *
     * @return list of {@link Passenger} objects from search operations, or null if no results are available
     */
    public List<Passenger> getSearchBookingResult() {
        return searchBookingResult;
    }

    /**
     * Sets the collection of passenger objects for search results.
     *
     * @param searchBookingResult list of {@link Passenger} objects to set as search results
     */
    public void setSearchBookingResult(List<Passenger> searchBookingResult) {
        this.searchBookingResult = (ArrayList<Passenger>) searchBookingResult;
    }

}