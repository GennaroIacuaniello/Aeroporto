package controller;

import model.Passenger;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for managing passenger operations and search results in the airport management system.
 * <p>
 * This class serves as the specialized controller for passenger-related operations within the MVC
 * architecture of the airport management system. It maintains passenger search results and provides
 * methods for managing passenger data retrieved from various search and administrative operations.
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
 * <p>
 * The class follows a search result management pattern, maintaining collections of {@link Passenger}
 * objects that are populated by various search operations throughout the system. This enables
 * efficient passenger data handling for administrative interfaces, customer service operations,
 * and booking management workflows.
 * </p>
 * <p>
 * Passenger search results are typically populated by operations such as:
 * </p>
 * <ul>
 *   <li>Flight manifest retrieval for administrative oversight</li>
 *   <li>Booking-related searches for customer service operations</li>
 *   <li>Lost luggage operations requiring passenger identification</li>
 *   <li>Administrative reporting and passenger management functions</li>
 * </ul>
 * <p>
 * The controller maintains passenger data in ArrayList format for efficient access and manipulation,
 * while providing List interface methods for broader compatibility with GUI components and
 * business logic that require passenger information processing.
 * </p>
 * <p>
 * Integration with other controllers enables comprehensive passenger data management across
 * different system operations, ensuring that passenger information is consistently available
 * and properly organized for various administrative and operational requirements.
 * </p>
 * <p>
 * All passenger operations maintain data integrity and support the complex relationships
 * between passengers, tickets, bookings, and flights within the airport management system.
 * </p>
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
     * <p>
     * This method returns the list of {@link Passenger} objects that were retrieved
     * from search operations, typically used for administrative functions, customer
     * service operations, or passenger management tasks where passenger information
     * needs to be displayed or processed.
     * </p>
     * <p>
     * The search results contain passenger objects with complete information including
     * personal details, identification data, and associations with tickets and bookings,
     * enabling comprehensive passenger management and administrative support.
     * </p>
     * <p>
     * The returned collection is used by GUI components for displaying passenger
     * lists, administrative interfaces for passenger management, and business logic
     * operations that require access to passenger data from search operations.
     * </p>
     *
     * @return list of {@link Passenger} objects from search operations, or null if no results are available
     */
    public List<Passenger> getSearchBookingResult() {
        return searchBookingResult;
    }

    /**
     * Sets the collection of passenger objects for search results.
     * <p>
     * This method establishes the list of {@link Passenger} objects returned from
     * search operations, converting the input list to an ArrayList for internal
     * consistency and performance optimization. The search results are used for
     * administrative functions and passenger management operations.
     * </p>
     * <p>
     * The method ensures that passenger search results are properly stored and
     * available for subsequent retrieval and manipulation operations throughout
     * passenger management workflows and administrative interfaces.
     * </p>
     * <p>
     * This method is typically called by search operations in the main controller
     * or by administrative functions that need to populate passenger result sets
     * for display or processing purposes. The passenger data is organized to
     * support efficient access and correlation with related booking and flight
     * information.
     * </p>
     * <p>
     * The conversion to ArrayList ensures optimal performance for indexed access
     * operations while maintaining compatibility with the List interface for
     * broader system integration and GUI component support.
     * </p>
     *
     * @param searchBookingResult list of {@link Passenger} objects to set as search results
     */
    public void setSearchBookingResult(List<Passenger> searchBookingResult) {
        this.searchBookingResult = (ArrayList<Passenger>) searchBookingResult;
    }

}